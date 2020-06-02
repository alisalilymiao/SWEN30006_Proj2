package players;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Whist;
import strategy.*;

public class Player {
    private String playerType;
    private int score;
    private Hand hand;
    private int[] allscores;
    private IStrategy playStrategy;

    private Card selected;

    public Player(String playerType){
        this.playerType = playerType;
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public void updateHand(Hand hand){
        this.hand = hand;
    }

    public void setPlayStrategy(){

        switch (playerType) {
            case "interactive":
                playStrategy = new InteractiveStrategy(hand);
                break;
            case "random":
                playStrategy = new RandomStrategy();
                break;
            case "legal":
                playStrategy = new LegalStrategy();
                break;
            case "smart":
                playStrategy = new SmartStrategy();
                break;
        }
    }

    public Card playerSelectCard(Hand trick, Whist.Suit trump){
        return playStrategy.selectCard(hand,trick,trump);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public IStrategy getPlayStrategy() {
        return playStrategy;
    }

    public void setAllscores(int[] allscores) {
        this.allscores = allscores;
    }
}
