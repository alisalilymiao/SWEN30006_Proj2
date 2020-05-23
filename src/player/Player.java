package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public interface Player {
    public Card selectCard(Hand trick);
    public void setHand(Hand hand);
    public void updateHand(Hand hand);
    public void isLeadOrNot(int firstDeal);
}
