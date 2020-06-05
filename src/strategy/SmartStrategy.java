package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import Whist;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.HashSet;

public class SmartStrategy implements IStrategy{

    private HashSet<Card> allUsedCard = new HashSet<>();

    @Override
    public Card selectCard(Hand hand, Hand trick, Whist.Suit trump) {
        // TODO Auto-generated method stub
        Whist.Suit leadSuit;
        if(trick.getNumberOfCards()==0) {
            return RandomUtil.randomCard(hand);
        }else {

            //remember other users' card
            for (int i=0;i<trick.getNumberOfCards();i++){
                allUsedCard.add(trick.get(i));
            }
            //System.out.println("The total number of Card I know"+allUsedCard.size());

            leadSuit= (Whist.Suit) trick.get(0).getSuit();
            //if players have cards of lead suit, they should play that.
            if(hand.getNumberOfCardsWithSuit(leadSuit)>0) {
                ArrayList<Card> leadSuitList  = hand.getCardsWithSuit(leadSuit);
                //if someone has played a trump suit, player should play a smallest card
                if(trick.getCardsWithSuit(trump).size()>0 && trump != leadSuit) {
                    return leadSuitList.get(leadSuitList.size()-1);
                }else {
                    //choose a minimum biggest card
                    Card currentBiggestCard = trick.getCardsWithSuit(leadSuit).get(0);
                    //System.out.println(leadSuitList.size());
                    //select a card from  an arraylist that is the minimum of the cards
                    // bigger than the currentBiggestCard
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

    //choose a card from  an arraylist that is the minimum of the cards bigger than biggest card of the biggest card
    private Card selectMinBigTarget(Card biggestCard,ArrayList<Card> Cards){
        if (Cards.size()==0)
            return null;
        for(int i=0;i < Cards.size();i++) {

            if (Cards.get(i).getRankId() > biggestCard.getRankId()) {
                if (i==0){
                    return null;
                }
                return Cards.get(i - 1);
            }
        }
        return null;
    }

    public void updateInformation(Hand trick){
        for (int i=0;i<trick.getNumberOfCards();i++){
            allUsedCard.add(trick.get(i));
        }
        System.out.println("The number of card that smart player knows: "+allUsedCard.size());
    }
}
