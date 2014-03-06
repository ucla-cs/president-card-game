package util;
import java.util.Iterator;

import java.util.ArrayList;
import util.Card.Rank;
//import util.Card.Suit;


/**
 * Contains a list of cards of the same rank (for examples,
 * three queens). Tolerates duplicates
 * Immutable.
 */
public final class CardGroup implements Iterable<Card>
{
	
	private Card[] cardGroup;	//NOT SURE ABOUT THE IMMUTABILITY
	
	/**
	 * Constructs a new card set from an array or cards.
	 * @param cards The cards to put in the set.
	 * @pre cards !=null && cards.length > 0
	 * @throws IllegalArgumentException if the cards in cards 
	 * are not all of the same rank, or if more than one Joker is
	 * in the input
	 */
	
	public CardGroup(){
		cardGroup = new Card[0];
	}
	
	public CardGroup(Card[] cards)
	{		
		Rank r = cards[0].getRank(); //Gets the rank of the first element
		if(cards[0].isJoker() && cards.length != 1){
			System.out.println("Error: Attempting to create a group with a Joker as well as other cards.");
			throw new IllegalArgumentException();
		}
		
		int i = 1;
		while (i < cards.length){
			if(cards[i].isJoker() || !cards[i].getRank().equals(r)){
				System.out.println("Error: Cards attempting to be put into the card group are not all the same rank.");
				throw new IllegalArgumentException();
			}
			i++;
		}
		cardGroup = cards.clone();		
	}
	
	/**
	 * Constructs a singleton card set.
	 * @param card The card to put in the set.
	 * @pre card !=null 
	 */
	public CardGroup(Card card)
	{
		/** Handled by @pre
		if(card == null){
			System.out.println("Error in attmepting to add a singleton that was null");
			return;
		}
		**/
		
		cardGroup = new Card[1];
		cardGroup[0] = card;
	}
	
	/**
	 * @param card A card to check
	 * @return True if pCard is in this set
	 * @pre card != null
	 */
	public boolean contains(Card card)
	{
		for (Card c : cardGroup){
			if (c.equals(card)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @return The size of the group.
	 */
	public int size()
	{
		return cardGroup.length;
	}
	
	public boolean isPass(){
		return this.cardGroup.length==0;
	}
	
	/**
	 * 
	 * @return The rank for this card set
	 */
	public Card.Rank getRank()
	{
		if(this.equals(new CardGroup())){	//Then its a pass
			System.out.println("!!!!!!!!!!!ERROR --- Can't get the card group of a pass. this should be handled by the @pre !!!!!!!!!!!!!");
		}

		return cardGroup[0].getRank();
	}
	
	/**
	 * @return true if this is a singleton Joker
	 */
	public boolean isJoker()
	{
		return cardGroup[0].isJoker();
	}

	/**
	 * @return An iterator over an ArrayList copy of the array cardGroup as there is no such thing as an iterator for a simple array
	 * 
	 */
	public Iterator<Card> iterator() {
		ArrayList<Card> cGroup = new ArrayList<Card>();
		for(Card c : this.cardGroup){
			cGroup.add(c);
		}
		return cGroup.iterator();
	}

	public boolean equals(CardGroup cG){	//Done to facilitate testing
		
		if(cardGroup.length != cG.cardGroup.length){
			return false;
		}
		int i = 0;
		for(Card c : cardGroup){
			if(!c.equals(cG.cardGroup[i])){
				return false;
			}
			i++;
		}
		return true;
	}
	
	/*
	public String toString(){
		String result = "";
		for(Card c : cardGroup){
			result += c.toString() +", ";
		}
		return result.substring(0, result.length()-2);
	}
	*/
}
