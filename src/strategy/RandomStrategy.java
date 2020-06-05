package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.WhistGameEnum;
import utils.RandomUtil;

public class RandomStrategy implements IStrategy{

    @Override
    public Card selectCard(Hand hand, Hand trick, WhistGameEnum.Suit trump) {
        return RandomUtil.randomCard(hand);
    }
}
