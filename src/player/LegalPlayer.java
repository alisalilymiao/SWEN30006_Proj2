package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Whist;

public class LegalPlayer implements Player
{
    private Hand hand;
    private Card selected;


    Whist.Suit leadSuit;  //Define the current lead suit


    @Override
    public Card selectCard(Hand trick,Whist.Suit trump)
    {
        if (trick.getNumberOfCards() != 0)
        {
            leadSuit = (Whist.Suit) trick.get(0).getSuit();
            if (hand.getCardsWithSuit(leadSuit).size() > 0)
            {
                selected = Whist.randomCard(hand.getCardsWithSuit(leadSuit));
            } else
            {
                selected = Whist.randomCard(hand);
            }
        } else
        {
            selected = Whist.randomCard(hand);
        }

        return selected;
    }

    @Override
    public void setHand(Hand hand)
    {
        this.hand = hand;
    }

    @Override
    public void updateHand(Hand hand)
    {
        this.hand = hand;
    }


}
