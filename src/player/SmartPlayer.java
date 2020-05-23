package player;

import java.util.ArrayList;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Whist;
import game.Whist.Suit;

public class SmartPlayer implements Player {
	
	private int index;
    private Hand hand;
    private Card selected;
    private boolean isLead;
    private boolean isEnforceRules;
    
    public SmartPlayer(int index, boolean enforceRules){
        this.index = index;
        this.isEnforceRules = enforceRules;
    }


	@Override
	public Card selectCard(Hand trick, Suit trumps) {
		// TODO Auto-generated method stub
		Suit leadSuit;
		if(trick.getNumberOfCards()==0) {
			return Whist.randomCard(trick);
		}else {
			leadSuit= (Suit) trick.get(0).getSuit();
//			if player has lead suit card
			if(this.hand.getNumberOfCardsWithRank(leadSuit)>0) {
				ArrayList<Card> leadSuitList  = this.hand.getCardsWithSuit(leadSuit);
				if(trick.getCardsWithSuit(trumps).size()>0) {
					return leadSuitList.get(leadSuitList.size()-1);
				}else {
					return leadSuitList.get(0);
				}
			}
//			if player do not have lead suit cards
			else {
//				if player has trump suit card
				if(this.hand.getNumberOfCardsWithSuit(trumps)>0) {
					ArrayList<Card> trumpsCard = this.hand.getCardsWithSuit(trumps);
//					someone plays trump suit card
					if(trick.getNumberOfCardsWithSuit(trumps)>0) {
						Card biggestTrumpCard = trick.getCardsWithSuit(trumps).get(0);
//						select the suitable card
						for(int i=0;i < trumpsCard.size();i++) {
							if(trumpsCard.get(i).getValue()<=biggestTrumpCard.getValue()) {
								return trumpsCard.get(i-1);
							}
						}
					}
//					none plays trump suit card
					else {
						return trumpsCard.get(trumpsCard.size()-1);
					}
					
				}
//				if player do not have trump suit card
				else {
					
				}
			}
			
		}
		
		return selected;
	}


	@Override
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public void updateHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public void isLeadOrNot(int firstDeal) {
        this.isLead = index == firstDeal;
    }


}
