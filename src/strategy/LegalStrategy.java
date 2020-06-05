package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import Whist;
import utils.RandomUtil;

public class LegalStrategy implements IStrategy{

    private Whist.Suit leadSuit;  //Define the current lead suit

    @Override
    public Card selectCard(Hand hand, Hand trick, Whist.Suit trump) {

        Card selected;

        if (trick.getNumberOfCards() != 0) {
            leadSuit = (Whist.Suit) trick.get(0).getSuit();
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
