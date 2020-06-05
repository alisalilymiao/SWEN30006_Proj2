package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import Whist;

public interface IStrategy {
    public Card selectCard(Hand hand, Hand trick, Whist.Suit trump);
}
