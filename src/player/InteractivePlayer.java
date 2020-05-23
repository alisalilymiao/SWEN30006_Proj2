package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import ch.aplu.jcardgame.Hand;
import game.Whist;

import static ch.aplu.jgamegrid.GameGrid.delay;

public class InteractivePlayer implements Player{

    private Hand hand;
    private Card selected;
    private CardListener cardListener;

    //初始传进来告知player是第几个
    public InteractivePlayer(){
        cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) { selected = card; hand.setTouchEnabled(false); }
        };

    }

    @Override
    public Card selectCard(Hand trick, Whist.Suit trump) {
        hand.setTouchEnabled(true);
        while (null == selected) delay(100);
        //hand.remove(selected,false);
        return selected;
    }

    @Override
    public void setHand(Hand hand) {
        this.hand = hand;
        hand.addCardListener(cardListener);
    }

    @Override
    public void updateHand(Hand hand) {
        this.hand = hand;
        selected = null;
    }

}
