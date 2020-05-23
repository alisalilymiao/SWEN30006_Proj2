package player;

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
			selected = Whist.randomCard(trick);
			leadSuit =(Suit) selected.getSuit();
		}else {
			leadSuit= (Suit) trick.get(0).getSuit();
//			if player has lead suit card
			if(this.hand.getNumberOfCardsWithRank(leadSuit)>0) {
				if(trick.getCardsWithSuit(trumps).size()>0) {
					
					selected = this.hand.getCardsWithSuit(leadSuit).get(0);
				}
			}
			else {
				
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
