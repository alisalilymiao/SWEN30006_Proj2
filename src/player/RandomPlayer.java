package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Whist;

public class RandomPlayer implements Player{

    private int index;
    private Hand hand;
    private Card selected;
    private boolean isLead;
    private boolean isEnforceRules;

    //初始传进来告知player是第几个
    public RandomPlayer(int index, boolean enforceRules){
        this.index = index;
        this.isEnforceRules = enforceRules;
    }

    @Override
    public Card selectCard(Hand trick, Whist.Suit trumps) {
        selected = Whist.randomCard(hand);
        //hand.remove(selected,false);
        return selected;
    }

    @Override
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public void updateHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public void isLeadOrNot(int firstDeal) {
        this.isLead = index == firstDeal;
    }
}
