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
			return Whist.randomCard(trick);
		}else {
			leadSuit= (Suit) trick.get(0).getSuit();
			//if player has lead suit card （手里有满足要求的）
			if(hand.getNumberOfCardsWithRank(leadSuit)>0) {
				//手上的leadingSuit扑克牌的数量
				ArrayList<Card> leadSuitList  = hand.getCardsWithSuit(leadSuit);
				if(trick.getCardsWithSuit(trump).size()>0) {
					//从手里出一张最小的牌
					return leadSuitList.get(leadSuitList.size()-1);
				}else {
					//目前trick里面最大牌面是lead的牌
					Card currentBiggestCard = trick.getCardsWithSuit(leadSuit).get(0);
					//从手里出一张比当前牌所有牌要大的最小的牌
					Card target = selectMinBigTarget(currentBiggestCard, leadSuitList);
					if (target == null){
						return leadSuitList.get(leadSuitList.size()-1);
					}else {
						return target;
					}
				}
			}
//			if player do not have lead suit cards
			else {
//				if player has trump suit card
				if(hand.getNumberOfCardsWithSuit(trump)>0) {
					ArrayList<Card> trumpCards = hand.getCardsWithSuit(trump);
					//someone plays trump suit card
					if(trick.getNumberOfCardsWithSuit(trump)>0) {
						Card biggestTrumpCard = trick.getCardsWithSuit(trump).get(0);
						//select the suitable card
						Card target = selectMinBigTarget(biggestTrumpCard, trumpCards);
						if (target == null){
							return hand.sort(Hand.SortType.RANKPRIORITY,false);
						}else {
							return target;
						}

					}
					//none plays trump suit card
					else {
						return trumpCards.get(trumpCards.size()-1);
					}
					
				}
//				if player do not have trump suit card
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


    //选择一个比当前最大的面值 大一点的卡片
    private Card selectMinBigTarget(Card biggestCard,ArrayList<Card> Cards){
    	if (Cards.size()==0)
    		return null;
		for(int i=0;i < Cards.size();i++) {
			if (Cards.get(i).getValue() <= biggestCard.getValue()) {
				if (i==0){
					return Cards.get(i);
				}
				return Cards.get(i - 1);
			}
		}
		return null;

	}


}
