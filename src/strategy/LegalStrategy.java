package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.WhistGameEnum;
import utils.RandomUtil;

public class LegalStrategy implements IStrategy{

    private WhistGameEnum.Suit leadSuit;  //Define the current lead suit

    @Override
    public Card selectCard(Hand hand, Hand trick, WhistGameEnum.Suit trump) {

        Card selected;

        if (trick.getNumberOfCards() != 0) {
            leadSuit = (WhistGameEnum.Suit) trick.get(0).getSuit();
            if (hand.getCardsWithSuit(leadSuit).size() > 0) {
                selected = RandomUtil.randomCard(hand.getCardsWithSuit(leadSuit));
            } else {
                selected = RandomUtil.randomCard(hand);
            }
        } else {
            selected = RandomUtil.randomCard(hand);
        }

        return selected;
    }
}
