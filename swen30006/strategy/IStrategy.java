package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.WhistGameEnum;

public interface IStrategy {
    public Card selectCard(Hand hand, Hand trick, WhistGameEnum.Suit trump);
}
