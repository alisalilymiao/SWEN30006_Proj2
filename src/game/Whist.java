package game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import player.*;
import player.InteractivePlayer;
import player.IPlayer;
import properties.Configure;
import utils.RandomUtil;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.*;

public class Whist extends CardGame
{

    public enum Suit
    {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    public enum Rank
    {
        // Reverse order of rank importance (see rankGreater() below)
        // Order of cards is tied to card images
        ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
    }

    final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

    public boolean rankGreater(Card card1, Card card2) {
        return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
    }

    private final String version = "1.0";
    public final int nbPlayers = 4;
    public final int nbStartCards;
    public final int winningScore;
    //尝试初始化所有的player
    private IPlayer[] IPlayers;
    //初始设定游戏的类型
    private int gameType;

    private final int handWidth = 400;
    private final int trickWidth = 40;
    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };
    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            new Location(650, 575)
    };
    private Actor[] scoreActors = {null, null, null, null };
    private final Location trickLocation = new Location(350, 350);
    private final Location textLocation = new Location(350, 450);
    private final int thinkingTime = 2000;
    private Hand[] hands;
    private Location hideLocation = new Location(-500, - 500);
    private Location trumpsActorLocation = new Location(50, 50);
    private boolean enforceRules=false;

    public void setStatus(String string) { setStatusText(string); }

    private int[] scores = new int[nbPlayers];

    Font bigFont = new Font("Serif", Font.BOLD, 36);

    private void initScore() {
        for (int i = 0; i < nbPlayers; i++) {
            scores[i] = 0;
            scoreActors[i] = new TextActor("0", Color.WHITE, bgColor, bigFont);
            addActor(scoreActors[i], scoreLocations[i]);
        }
    }

    private void updateScore(int player) {
        removeActor(scoreActors[player]);
        scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
        addActor(scoreActors[player], scoreLocations[player]);
    }

    private Card selected;

    private void initPlayers(int gameType) throws QuantityAnomalyException {
        PlayerFactory playerFactory = new PlayerFactory();
        IPlayers = playerFactory.initPlayer(gameType).toArray(new IPlayer[0]);
    }

    private void initRound() {
        hands = dealingOut(nbPlayers, nbStartCards,deck); // Last element of hands is leftover cards; these are ignored
        for (int i = 0; i < nbPlayers; i++) {
            hands[i].sort(Hand.SortType.SUITPRIORITY, true);
            IPlayers[i].setHand(hands[i]);
        }

        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            // layouts[i].setStepDelay(10);
            hands[i].setView(this, layouts[i]);
            hands[i].setTargetArea(new TargetArea(trickLocation));
            hands[i].draw();
        }
    }


    private Optional<Integer> playRound(){  // Returns winner, if any
        // Select and display trump suit
        final Suit trumps = RandomUtil.randomEnum(Suit.class);
        final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
        addActor(trumpsActor, trumpsActorLocation);
        // End trump suit

        Hand trick;
        int winner;
        Card winningCard;
        Suit lead;
        int nextPlayer = RandomUtil.random.nextInt(nbPlayers); // randomly select player to lead for this round

        for (int i = 0; i < nbStartCards; i++){
            trick = new Hand(deck);
            selected = null;

            if (0 == nextPlayer || IPlayers[nextPlayer] instanceof InteractivePlayer){
                setStatus("Player "+nextPlayer+" double-click on card to lead.");
            } else {
                setStatusText("Player " + nextPlayer + " thinking...");
                delay(thinkingTime);
            }
            selected = IPlayers[nextPlayer].selectCard(trick, trumps);

            // Lead with selected card
            trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
            trick.draw();
            selected.setVerso(false);
            // No restrictions on the card being lead
            lead = (Suit) selected.getSuit();
            //如果选中的卡转化到trick上之后，卡片的数量减少1
            selected.transfer(trick, true); // transfer to trick (includes graphic effect)
            IPlayers[nextPlayer].updateHand(hands[nextPlayer]);
            winner = nextPlayer;
            winningCard = selected;
            // End Lead

            for (int j = 1; j < nbPlayers; j++) {
                if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
                selected = null;

                if (0 == nextPlayer || IPlayers[nextPlayer] instanceof InteractivePlayer){
                    setStatus("Player "+nextPlayer+" double-click on card to choose the card.");
                } else {
                    setStatusText("Player " + nextPlayer + " thinking...");
                    delay(thinkingTime);
                }
                selected = IPlayers[nextPlayer].selectCard(trick, trumps);

                // Follow with selected card
                trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
                trick.draw();
                selected.setVerso(false);  // In case it is upside down
                // Check: Following card must follow suit if possible
                if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
                    // Rule violation
                    String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
                    System.out.println(violation);
                    if (enforceRules)
                        try {
                            throw(new BrokeRuleException(violation));
                        } catch (BrokeRuleException e) {
                            e.printStackTrace();
                            System.out.println("A cheating player spoiled the game!");
                            System.exit(0);
                        }
                }
                // End Check
                selected.transfer(trick, true); // transfer to trick (includes graphic effect)
                IPlayers[nextPlayer].updateHand(hands[nextPlayer]);
                System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + winningCard.getRankId());
                System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " +    selected.getRankId());
                if ( // beat current winner with higher card
                        (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
                                // trumped when non-trump was winning
                                (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
                    System.out.println("NEW WINNER");
                    winner = nextPlayer;
                    winningCard = selected;
                }
                // End Follow
            }

            if (gameType == 3){
                for (IPlayer player: IPlayers){
                    if (player instanceof SmartPlayer){
                        ((SmartPlayer) player).updateInformation(trick);
                    }
                }
            }

            delay(600);
            trick.setView(this, new RowLayout(hideLocation, 0));
            trick.draw();
            nextPlayer = winner;
            setStatusText("IPlayer " + nextPlayer + " wins trick.");
            scores[nextPlayer]++;
            updateScore(nextPlayer);
            if (winningScore == scores[nextPlayer]) return Optional.of(nextPlayer);
        }
        removeActor(trumpsActor);
        return Optional.empty();
    }

    public Whist() throws IOException, QuantityAnomalyException {
        super(700, 700, 30);
        setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");

        setStatusText("Users select one of the game pattern: 1.original 2.legal 3.smart");
        System.out.println("Users select one of the game pattern: 1.original 2.legal 3.smart");
        Scanner scanner = new Scanner(System.in);
        gameType = scanner.nextInt();

        Configure.getInstance().setGameProperties(gameType);
        int Seed = Integer.parseInt(Configure.getInstance().values("Seed"));
        int nbStartCards = Integer.parseInt(Configure.getInstance().values("nbStartCards"));
        int winningScore = Integer.parseInt(Configure.getInstance().values("winningScore"));
        enforceRules = Boolean.parseBoolean(Configure.getInstance().values("enforceRules"));
        this.nbStartCards = nbStartCards;
        this.winningScore = winningScore;
        RandomUtil.random.setSeed(Seed);
        initPlayers(gameType);
        initScore();
        Optional<Integer> winner;
        do {
            initRound();
            winner = playRound();
        } while (!winner.isPresent());
        addActor(new Actor("sprites/gameover.gif"), textLocation);
        setStatusText("Game over. Winner is player: " + winner.get());
        refresh();
    }

    public static void main(String[] args) throws IOException, QuantityAnomalyException {
        // System.out.println("Working Directory = " + System.getProperty("user.dir"));
        new Whist();
    }
    
    
    public Hand[] dealingOut(int nbPlayers, int nbCardsPerPlayer, Deck deck) {
        int nbCard = Suit.values().length*Rank.values().length;
    	if (nbPlayers * nbCardsPerPlayer > nbCard) {
    		fail("Error in Deck.dealing out.\n" + nbCard + " cards in deck. Not enough for" + "\n" + nbPlayers + ((nbPlayers > 1) ? " players with " : "player with ") + nbCardsPerPlayer + ((nbCardsPerPlayer > 1) ? " cards per player." : "card per player.") + "\nApplication will terminate.");
    	 }

       
    	ArrayList<Card> cards = new ArrayList<Card>();
    	for (Suit suit : Suit.values()) {      
    		for (Rank rank : Rank.values()) { 
    		Card card = new Card(deck, suit, rank);
    		cards.add(card);
    	    } 
    	} 
   
    	
    	    Collections.shuffle(cards,RandomUtil.random);
    	 
    	     Hand[] hands = new Hand[nbPlayers + 1];
    	     for (int i = 0; i < nbPlayers; i++) {
    	      
    	       hands[i] = new Hand(deck);
    	       for (int k = 0; k < nbCardsPerPlayer; k++)
    	         hands[i].insert((Card)cards.get(i * nbCardsPerPlayer + k), false); 
    	} 
    	    hands[nbPlayers] = new Hand(deck);
    	     for (int p = nbPlayers * nbCardsPerPlayer; p < nbCard; p++)
    	       hands[nbPlayers].insert((Card)cards.get(p), false); 
    	    return hands;
    	  }


}
