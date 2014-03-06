package robots;

import model.Event;
import model.Hand;
import model.TurnHistory;
import model.Play;
import model.Event.EVENT;
import util.Card;
import util.CardGroup;
import util.Card.Rank;
import util.Card.Suit;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;


public class BasicAI implements IRobot {
	
	private String name;
	private TurnHistory history;
	private Hand hand;
	private Play playToBeat;
	private CardGroup[] pChoices;
	
	public BasicAI(String name) 
	{
		this.name = name;
	}
	
	/*
	 * Decides which of the possible CardGroups to play, if any.
	 * The main idea behind this AI is that it plays the lowest possible play it can without
	 * breaking up a set of cards. i.e. will not play 4,4 on 3,3 if they have Three 4's in their hand.
	 * However, this only holds up to a certain threshold at which point it is a better strategy to 
	 * simply break up the larger set.
	 * @return the CardGroup the AI determines to play
	 */
	public CardGroup chooseCards(TurnHistory history, Hand hand)
	{
		this.history = history;
		this.hand = hand;

		playToBeat = getPlayToBeat(history);
		if(history.size() == 0 || playToBeat.getPlayerID().equals(name))
		{
			return beginningSet();
		}
		else 
		{
			return continuingSet();
		}
	}
	
	/*
	 * chooses the cards to get rid of
	 * @pre n == 1 or n == 2 . Only called if there is an advantage
	 * @param n The number of cards to have the AI decide to give away.
	 * @return The cards the AI wants to give away.
	 */
	public ArrayList<Card> getCardsToGiveAway(int n,Hand h){
		ArrayList<Card> c = new ArrayList<Card>();
		
		Iterator<Card> iter = h.iterator();
		for(int i = 1; i <= n ; i++){
			c.add(iter.next());
		}
		return c;
		
		/*
		 * 	//TODO ADD IN THIS IMPLEMENTATION LATER AND IMPROVE IT TIME PERMITTING
		Card lowest = null;
		Card secondLowest = null;
		boolean needCards = true;
		this.hand = h.clone();
		
		for(Card card : hand){
			if(card.isJoker() || card.getRank().compareTo(Rank.SIX) >= 0){
				break;
			}
			if(h.cardsOfRank(card.getRank())==1 && !(lowest!=null && lowest==card)){
				if(lowest==null){
					lowest = card;
					//h.remove(card);
					if(n==1){
						needCards = false;
						break;
					}
				}
				else{
					secondLowest = card;
					needCards = false;
					break;
				}
			}
		}
		Iterator<Card> i = h.iterator();
		if(needCards && lowest==null){
			lowest = i.next();
			if(n==1) needCards = false;
		}
		if(needCards && secondLowest == null){
			secondLowest = i.next();
		}
		c.add(lowest);
		if(n==2){
			c.add(secondLowest);
		}
		return c;
		*/
	}
	
	/*
	 * Going to implement in the future more special cases.
	 * Currently, this checks for a couple obvious base cases for when the AI has 
	 * only a few cards left. It then plays in a way that will most likely play
	 * out to it's advantage. Another sticking point of this AI is that if he has a large group of low rank, 
	 * he gets rid of them as soon as possible
	 */
	private CardGroup beginningSet()
	{
		this.pChoices = hand.playChoices(playToBeat.getCardGroup());
		
		if(pChoices.length!=0){
			if(pChoices[pChoices.length-1].isJoker() || pChoices[pChoices.length-1].getRank().ordinal() > 11){
				//Deal with various joker/high card cases with only a few cards left. These don't occur that often. but make a big difference
				if(hand.ranksInHand()==1 || hand.ranksInHand()==2){
					return pChoices[pChoices.length-1];
				}
				if(hand.ranksInHand()==3 && pChoices.length >=2){
					return pChoices[pChoices.length-2];
				}
			}
		}
		
		int most = 0;
		Rank highestGroup = Rank.THREE;
		for(Rank r : Rank.values()){
			if(r.ordinal() > 5){
				break;
			}
			if(hand.cardsOfRank(r)>0){
				most = hand.cardsOfRank(r);
				highestGroup = r;
			}
		}
		
		if(most == 0 ){
			return basicStrategyPlay();
		}
		
		for(int i = pChoices.length-1 ; i >=0 ; i--){
			if(pChoices[i].getRank()==highestGroup){
				return pChoices[i];
			}
		}
		
		CardGroup p = basicStrategyPlay();
		return p;
	}
	
	/*
	 * Going to implement more strategies in the future to compute the probability that the AI can win.
	 * If the probability is above a certain threshhold, then the AI will take the risk and 
	 * play 
	 */
	private CardGroup continuingSet()
	{
		this.pChoices = hand.playChoices(playToBeat.getCardGroup());
		if (pChoices.length == 0) {
			return new CardGroup(); // pass
		}	
		return basicStrategyPlay();
	}
	
	private CardGroup basicStrategyPlay()
	{
		CardGroup toPlay = new CardGroup(); //pass by default
		for (int j = 0; j < pChoices.length; j++) {
			CardGroup c1 = pChoices[j];
			
			if(!playToBeat.isPass() && !c1.isJoker() && c1.getRank().ordinal() + 4 >= playToBeat.getCardGroup().getRank().ordinal()){
				return pChoices[0];
			}
			
			boolean playIt = true;
			CardGroup[] cardGroupsInHand = hand.playChoices(new CardGroup());	//All the card groups possibilities in the hand
			
			for (CardGroup cg : cardGroupsInHand){
				if(!cg.isJoker() && !c1.isJoker() && cg.getRank().compareTo(c1.getRank()) == 0 &&  cg.size() > c1.size()){ //commented out: && cg.size() > c1.size()
					playIt = false;	//don't want to play as it will break a bigger group
					break;
				}
			}
			if(playIt){
				toPlay = c1;
				break;
			}
		}
		return toPlay;
	}
	
	private Play getPlayToBeat(TurnHistory history){
		Iterator<Play> i = history.reverseIterator();
		//Play p = new Play("",new CardGroup());
		Play p;
		if(!(history.size()==0)){
			p = history.getLastPlay();
		}
		else{
			p = new Play("",new CardGroup());	//only in the event of beginning a new game/turn.
		}
		while (i.hasNext() && !p.getPlayerID().equals(name) && p.getCardGroup().isPass()) 
		{
			p = i.next();
		}
		
		if(!i.hasNext()){
			return p;	//this should be a pass
		}
		else if(p.getPlayerID().equals(name)){
			return new Play("Someone",new CardGroup());	//a pass
		}
		else{
			return p;
		}
	}
	
	public String getName()
	{
		return name;
	}
}

