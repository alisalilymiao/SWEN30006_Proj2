package player;

import java.util.ArrayList;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Whist;
import game.Whist.Suit;

public class SmartPlayer implements Player {

    private Hand hand;
    private Card selected;

	@Override
	public Card selectCard(Hand trick, Suit trump) {
		// TODO Auto-generated method stub
		Suit leadSuit;
		if(trick.getNumberOfCards()==0) {
			return Whist.randomCard(hand);
		}else {
			leadSuit= (Suit) trick.get(0).getSuit();
			//if players have cards of lead suit, they should play that. 
			if(hand.getNumberOfCardsWithSuit(leadSuit)>0) {
				ArrayList<Card> leadSuitList  = hand.getCardsWithSuit(leadSuit);
				//if someone has played a trump suit, player should play a smallest card
				if(trick.getCardsWithSuit(trump).size()>0 && trump != leadSuit) {
					return leadSuitList.get(leadSuitList.size()-1);
				}else {
					//choose a minimum biggest card
					Card currentBiggestCard = trick.getCardsWithSuit(leadSuit).get(0);
					Card target = selectMinBigTarget(currentBiggestCard, leadSuitList);
					if (target == null){
						return leadSuitList.get(leadSuitList.size()-1);
					}else {
						return target;
					}
				}
			}
//			if player do not have cards of the lead suit
			else {
//				if player has cards of the trump suit
				if(hand.getNumberOfCardsWithSuit(trump)>0) {
					ArrayList<Card> trumpCards = hand.getCardsWithSuit(trump);
					//someone plays cards of the trump suit
					if(trick.getNumberOfCardsWithSuit(trump)>0) {
						Card biggestTrumpCard = trick.getCardsWithSuit(trump).get(0);
						//choose a minimum biggest card
						Card target = selectMinBigTarget(biggestTrumpCard, trumpCards);
						if (target == null){
							return hand.sort(Hand.SortType.RANKPRIORITY,false);
						}else {
							return target;
						}

					}
					//none plays cards of the trump suit 
					else {
						return trumpCards.get(trumpCards.size()-1);
					}
					
				}
//				if player do not have cards of the trump suit 
				else {
					return hand.sort(Hand.SortType.RANKPRIORITY,false);
				}
			}
			
		}

	}


	@Override
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public void updateHand(Hand hand) {
        this.hand = hand;
    }


  //choose a card from  an arrraylist that is the minimum of the cards bigger than biggest card of the trick
    private Card selectMinBigTarget(Card biggestCard,ArrayList<Card> Cards){
    	if (Cards.size()==0)
    		return null;
		for(int i=0;i < Cards.size();i++) {
			if (Cards.get(i).getValue() <= biggestCard.getValue()) {
				if (i==0){
					return null;
				}
				return Cards.get(i - 1);
			}
		}
		return null;

	}


}
