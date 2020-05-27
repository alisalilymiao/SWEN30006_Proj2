package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Whist;
import utils.RandomUtil;

public class RandomPlayer implements Player{

    private Hand hand;
    private Card selected;


    @Override
    public Card selectCard(Hand trick, Whist.Suit trump) {
        selected = RandomUtil.randomCard(hand);
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

}
