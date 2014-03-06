package model;

import java.util.Iterator;
import java.util.Stack;

import util.Card;
import util.Card.Rank;
import util.Card.Suit;
import util.CardGroup;

/**
 * Models the history of all the card groups 
 * played during a turn, and the ID of 
 * the players who played them.
 * Immutable. 
 */
public class TurnHistory implements Iterable<Play>
{	
	/**
	 * Initializes the list of plays in the history.
	 * @param plays The plays to add.
	 */
	private Stack<Play> history;
	
	public TurnHistory(Stack<Play> plays)
	{
		Stack<Play> clonePlays = new Stack<Play>();
		clonePlays = plays;
		history = clonePlays; 
	}
	
	/**
	 * @return The last play in the history.
	 */
	public Play getLastPlay()
	{
		return history.peek();
	}
	
	/**
	 * @return The number of Plays in this play history.
	 */
	public int size()
	{
		return history.size();
		
	}
	
	/**
	 * @return The iterator of the Play objects stack.
	 */
	public Iterator<Play> iterator() {
		Iterator<Play> playlist = history.iterator();
		return playlist;
	}
	
	/*
	public static void main(String[]args){
		Stack<Play> s = new Stack();
		s.push(new Play("me",new CardGroup(new Card(Rank.FIVE,Suit.CLUBS))));
		s.push(new Play("you",new CardGroup(new Card(Rank.SEVEN,Suit.CLUBS))));
		s.push(new Play("someone",new CardGroup(new Card(Rank.JACK,Suit.CLUBS))));
		s.push(new Play("a douche",new CardGroup(new Card(Rank.JACK,Suit.CLUBS))));
		TurnHistory t = new TurnHistory(s);
		Iterator<Play> i = t.iterator();
		Iterator<Play> rI = t.reverseIterator();
		
		while(i.hasNext()){
			System.out.println(i.next().getPlayerID());
		}
		System.out.println("");
		while(rI.hasNext()){
			System.out.println(rI.next().getPlayerID());
		}
	}
	*/
	
	
	public Iterator<Play> reverseIterator(){
		Stack<Play> temp = (Stack<Play>)history.clone();
		Stack<Play> reverse = new Stack<Play>();
		while(!temp.empty()){
			reverse.push(temp.pop());
		}
		return reverse.iterator();
	}
}