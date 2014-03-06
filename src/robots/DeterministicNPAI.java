package robots;

import model.Hand;
import model.TurnHistory;
import model.Play;
import util.Card;
import util.CardGroup;
import util.Card.Rank;
import util.Card.Suit;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Stack;


public class DeterministicNPAI implements IRobot {
	
	private String name;
	private TurnHistory history;
	private Hand hand;
	private Play playToBeat;
	private CardGroup[] pChoices;
	
	//private int playersLeft;
	
	public DeterministicNPAI(String name) 
	{
		this.name = name;
	}
	
	/*
	 * Decides which of the possible CardGroups to play, if any.
	 * @see robots.AI#chooseCards(model.TurnHistory, model.Hand)
	 */
	public CardGroup chooseCards(TurnHistory history, Hand hand)
	{
		this.history = history;
		this.hand = hand;

		Iterator<Play> i = history.reverseIterator();
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
			//then p should be a pass
		}
		else if(p.getPlayerID().equals(name)){
			p = new Play("Someone",new CardGroup());	//a pass
		}
		else{
			//then they have to beat p;
		}
		
		
		//this.playToBeat = p;
		
		this.pChoices = hand.playChoices(p.getCardGroup());
		
		return basicStrategyPlay();
	}
	
	public ArrayList<Card> getCardsToGiveAway(int n, Hand h){
		ArrayList<Card> c = new ArrayList<Card>();
		Iterator<Card> i = h.iterator();
		c.add(i.next());
		if(n==1){
			return c;
		}
		c.add(i.next());
		return c;
	}
	
	private CardGroup basicStrategyPlay()
	{
		if(pChoices.length>0){
			return pChoices[(pChoices.length-1)/4];
		}
		//return pChoices[pChoices.length-1];
		
		
		CardGroup toPlay = new CardGroup(); //pass by default
		for (int j = 0; j < pChoices.length; j++) {
			CardGroup c1 = pChoices[j];
			boolean playIt = true;
			CardGroup[] cardGroupsInHand = hand.playChoices(new CardGroup());	//All the card groups possibilities in the hand
			
			for (CardGroup cg : cardGroupsInHand){
				if(!cg.isJoker() && !c1.isJoker() && cg.getRank().compareTo(c1.getRank()) == 0 &&  cg.size() > c1.size()){ //commented out: && cg.size() > c1.size()
					playIt = false;	//don't want to play as it will break a bigger group
					break;
				}
			}
			if(playIt){
				toPlay = pChoices[j];
				break;
			}
		}
		return toPlay;
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public static void main(String []args){
		
		DeterministicNPAI ai = new DeterministicNPAI("HAL5000");
		Hand h = new Hand();
		h.add(new Card(Rank.FIVE,Suit.CLUBS));
		h.add(new Card(Rank.FIVE,Suit.HEARTS));
		h.add(new Card(Rank.SIX,Suit.CLUBS));
		h.add(new Card(Rank.EIGHT,Suit.CLUBS));
		h.add(new Card(Rank.EIGHT,Suit.DIAMONDS));
		h.add(new Card(Rank.TEN,Suit.CLUBS));
		h.add(new Card());
		
		ai.hand = h;
		
		ArrayList<Card> a = ai.getCardsToGiveAway(2,ai.hand);
		
		for(Card c : a ) {
			System.out.println(c + ",");
		}
		
		Stack<Play> s = new Stack<Play>();
		s.add(new Play("Someone",new CardGroup(new Card(Rank.THREE,Suit.CLUBS))));
		TurnHistory th = new TurnHistory(s);
		CardGroup cG = ai.chooseCards(th,ai.hand);
		System.out.println(cG.getRank() + " of size : " + cG.size());
		
		/*
		//BasicAI ai = new BasicAI("HAL5000");
		Stack<Play> s = new Stack<Play>();
		s.push(new Play("HAL5000",new CardGroup(new Card(Rank.FIVE,Suit.HEARTS))));
		s.push(new Play("me",new CardGroup(new Card(Rank.SIX,Suit.HEARTS))));
		s.push(new Play("you",new CardGroup(new Card(Rank.SEVEN,Suit.HEARTS))));
		s.push(new Play("someone",new CardGroup(new Card(Rank.EIGHT,Suit.HEARTS))));
		s.push(new Play("HAL5000",new CardGroup(new Card(Rank.TEN,Suit.HEARTS))));
		s.push(new Play("me",new CardGroup(new Card(Rank.JACK,Suit.HEARTS))));
		s.push(new Play("you",new CardGroup(new Card(Rank.QUEEN,Suit.HEARTS))));
		s.push(new Play("someone",new CardGroup(new Card(Rank.ACE,Suit.HEARTS))));
		s.push(new Play("HAL5000",new CardGroup(new Card(Rank.TWO,Suit.HEARTS))));
		s.push(new Play("me",new CardGroup()));
		s.push(new Play("you",new CardGroup()));
		s.push(new Play("someone",new CardGroup()));
		//s.push(new Play("someone",new CardGroup(new Card(Rank.FIVE,Suit.HEARTS))));
		TurnHistory th = new TurnHistory(s);
		*/
	}
}
