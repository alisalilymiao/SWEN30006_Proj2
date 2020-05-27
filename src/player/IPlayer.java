package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Whist;

public interface IPlayer {
    public Card selectCard(Hand trick, Whist.Suit trump);
    public void setHand(Hand hand);
    public void updateHand(Hand hand);
}
