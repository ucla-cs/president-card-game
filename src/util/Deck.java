package util;

import java.util.LinkedList;
import util.Card.Rank;
import util.Card.Suit;
import java.util.Random;

/**
 * Models a deck of cards made up of n packs of 52 cards (with one or two Joker per pack optional).
 * Shuffled on initialization.
 */
public class Deck 
{
	/**
	 * Allowable number of Jokers per pack, between 0 and 2 inclusive.
	 */
	public enum NumberOfJokers
	{ ZERO, ONE, TWO }
	
	/**
	 * Allowable number of pack, between 1 and 2 inclusive.
	 */
	public enum NumberOfPacks
	{ ONE, TWO }
	
	private LinkedList<Card> deck;	//LinkedList member variable

	
	/**
	 * Builds a deck with a specified number of packs and Jokers per pack.
	 * After this method the deck is shuffled.
	 * @param numberOfJokers The required number of Jokers per pack
	 * @param numberOfPacks The number of packs
	 */
	public Deck(NumberOfPacks numberOfPacks, NumberOfJokers numberOfJokers)
	{
		//MAYBE CHECK IF THE PARAMETERS ARE VALID
		//this.numberOfJokers = numberOfJokers;
		//this.numberOfPacks = numberOfPacks;
		deck = new LinkedList<Card>();
		for(int i = 0; i <= numberOfPacks.ordinal();i++){
			for(Rank r : Rank.values()){
				for(Suit s : Suit.values()){
					deck.add(new Card(r,s));
				}
				
			}
			for(int j = 0; j < numberOfJokers.ordinal(); j++){
				deck.add(new Card());
			}
		}
		
		shuffle();
		
		return;
	}
	
	/**
	 * Shuffle the deck of cards by restoring all the cards and randomising
	 * their order.
	 */
	public void shuffle()
	{
		int size = deck.size();
		Card[] temp = new Card[size];
		Random randomCard = new Random();
		while(size > 0 ){
			temp[size - 1] = deck.remove(randomCard.nextInt(size));
			size--;
		}
		for (Card c : temp){
			deck.add(c);
		}		
		return;
	}
	
	/**
	 * Draws a card from the deck and removes the card from the deck.
	 * @return The card drawn.
	 * @post final.size() == initial.size() - 1 OR EmptyDeckException
	 * @throws EmptyDeckException if this method is called when size() == 0
	 */
	public Card draw() throws EmptyDeckException
	{
		if(deck.size()==0){
			throw new EmptyDeckException();
		}
		return deck.remove();
	}
	
	/**
	 * Returns the size of the deck.
	 * @return The number of cards in the deck.
	 */
	public int size()
	{
		return deck.size();
	}
	
	public Deck clone(){
		Deck d = new Deck(NumberOfPacks.ONE,NumberOfJokers.ONE);	//Just need to create a new one
		d.deck.clear();
		for (Card c : this.deck){
			d.deck.add(c);
		}
		return d;
	}
}
