package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import ch.aplu.jcardgame.Hand;
import Whist;

import static ch.aplu.jgamegrid.GameGrid.delay;

public class InteractiveStrategy implements IStrategy{

    private Hand hand;
    private Card selected;
    private CardListener cardListener;

    public InteractiveStrategy(Hand hand){
        cardListener = new CardAdapter()  // Human IPlayer plays card
        {
            public void leftDoubleClicked(Card card) { selected = card; hand.setTouchEnabled(false); }
        };
        this.hand = hand;
        this.hand.addCardListener(cardListener);
    }

    @Override
    public Card selectCard(Hand hand, Hand trick, Whist.Suit trump) {
        selected = null;
        hand.setTouchEnabled(true);
        while (null == selected) delay(100);
        return selected;
    }

}
