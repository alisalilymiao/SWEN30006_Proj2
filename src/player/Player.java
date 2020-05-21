package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public interface Player {
    public Card selectCard();
    public void setHand(Hand hand);
    public void updateHand(Hand hand);

}
