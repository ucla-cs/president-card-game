package model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import util.Card;
import util.CardGroup;

//I added these import
import java.util.LinkedList;

import javax.swing.JOptionPane;

import util.Card.Rank;
import util.Card.Suit;

/**
 * Models a hand cards. The hand is kept sorted in the natural
 * sorting order of the card. Does not have to be 
 * thread safe. The hand supports duplicates: adding the same card twice will result
 * in two additions.
 */
public class Hand implements Iterable<Card>
{
	
	static int num = 1;
	
	private LinkedList<Card> hand;
	
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		hand = new LinkedList<Card>();
	}
	
	/**
	 * Adds card to the Hand in a way such that it remains sorted.
	 * @param card The card to add.
	 * @pre card != null
	 */
	public void add(Card card)
	{		

		if(hand.isEmpty()){
			hand.add(card);
			return;
		}
		
		if(card.isJoker()){
			hand.add(card);
			return;
		}
		
		for (Card c : hand){
			//System.out.println("Comparing " + c + " to " + card + ".\nc.getRank().compareTo(card.getRank()) : " + c.getRank().compareTo(card.getRank()) + "\nc.getSuit().compareTo(card.getSuit()) : " + c.getSuit().compareTo(card.getSuit()));
			if(c.isJoker() || c.getRank().compareTo(card.getRank()) > 0 || ( c.getRank().compareTo(card.getRank())== 0 && c.getSuit().compareTo(card.getSuit()) >= 0 )){	//current card is lower rank than the one to add
				hand.add(hand.indexOf(c), card);
				return;
			}
		}		
		hand.add(card);
	}
	
	/**
	 * Remove one instance of card from the hand. Does nothing if
	 * card is not in the hand.
	 * @param card The card to remove.
	 * @pre pCard != null
	 */
	public void remove(Card card)
	{
		if(card.isJoker() && hand.getLast().isJoker()){	//THIS IS REQUIRED AS JOKERS CANNOT BE PROPERLY COMPARED WITH .equals()
			hand.removeLast();
			return;
		}
		for (Card c : hand){
			if(c.equals(card)){
				hand.remove(c);
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		JOptionPane.showMessageDialog(null, "DID NOT REMOVE THE INTEDED CARD FROM THE HAND # " + num + " Card : "  +card.toString());
		num++;
	}
	
	/**
	 * Removes all the cards from the hand.
	 * @post size() == 0
	 */
	public void clear()
	{
		hand.clear();
	}
	
	/**
	 * @return The number of cards in the hand.
	 */
	public int size()
	{
		return hand.size();
	}
	
	/**
	 * Determines if card is already in the hand.
	 * @param card The card to check.
	 * @return true if the card is already in the hand.
	 * @pre pCard != null
	 */
	public boolean contains(Card card)
	{
		for (Card c : hand){
			if(c.equals(card)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns false if group is a Joker. Otherwise,
	 * Returns true if this hand can play a legal move
	 * following the group being played. For a legal move
	 * to be possible, this hand must contain either a) a Joker; b) 
	 * a group of cards of same rank as group and of same or higher
	 * cardinality; c) if the rank of group is less than 2, a group
	 * of twos of at least group.size() - 1. This method has no
	 * side effects.
	 * @param group the group to check against
	 * @return True if this can can play following group
	 */
	public boolean canPlayOn(CardGroup group)
	{
		if(group.size()==0){	//Then it was a pass
			return true;
		}
		if(group.isJoker()){
			return false;
		}
		if(hand.getLast().isJoker()){//If hand contains a joker, return true
			return true;
		}
		if(group.getRank()==Rank.TWO){
			return false;
		}
		
		//See if there are enough twos to play
		int twos = 0;
		while ((hand.size()-twos-1 >=0) && (hand.get(hand.size() - twos - 1).getRank() == Rank.TWO)) {
			twos++;
		}
		if(twos>=group.size() - 1 && twos!=0){	//Fixed this as it would think that 0 twos could beat a singleton
			return true;
		}
		
		Rank groupRank = group.getRank();	//The rank to beat
		
		for(Card c : hand){
			if(c.getRank().compareTo(groupRank)>0){	//Then the card has higher rank than the one to beat
				int i = hand.indexOf(c);	//Get the index of the card whose rank is higher than the one to beat
				int j = 0;
				while(i<=hand.size() - 1 && hand.get(i).getRank().compareTo(c.getRank())==0){
					i++;
					j++;
				}
				if(j >= group.size()){
					return true;
				}
			}
		}
		return false;		
	}
	
	/**
	 * Removes cards from the hand and construct a card group.
	 * @param cards The cards to play
	 * @return The CardGroup to play
	 * @throws IllegalArgumentException if the cards are not in the hand or
	 * do not form a proper group.
	 * @pre cards !=null
	 * @post cards in cardgroup are removed from hand
	 */
	public CardGroup playGroup(List<Card> cards) throws IllegalArgumentException
	{		
		Card[] temp = new Card[cards.size()];
		int i = 0;
		for(Card c : cards){	//Add the cards to a temp array to feed to the CardGroup constructor
			temp[i] = c;
			//System.out.println("temp["+i+"] = " +temp[i]);
			i++;
		}
		CardGroup group = new CardGroup(temp);	//THIS SHOULD THROW AN ILLEGALARGUMENTEXCEPTION IF NOT A PROPER GROUP
		
		LinkedList<Card> newHand = (LinkedList<Card>) hand.clone();	//CREATE A POSSIBLY TEMPORARY LIST
		
		for (Card c : cards){
			if(newHand.remove(c)){	//Tries to remove the card and returns true if it does
				continue;
			}
			throw new IllegalArgumentException();	//If the required cards are not in the hand
		}
		hand = newHand;	//Update the hand with the hand whose cards have been removed
		return group;	//Return the created group
	}
	
	/**
	 * @return A CardGroup for every legal move from this hand after group
	 * has been played, or an empty array if no move is possible. This
	 * method has no side effect.
	 * @param group An array of CardGroup representing legal moves.
	 */
	public CardGroup[] playChoices(CardGroup group)
	{
		CardGroup[] pChoices ;
		
		if(!canPlayOn(group)){
			pChoices = new CardGroup[0];
			//pChoices[0] = null;
			return pChoices;
		}
		
		//TODO make this return every possible number of cards
		if(group.size()==0){
			//Then we can play any permutation of any of our ranks in the hand.
			//System.out.println("Trying to beat anything. i.e. An opening move.");
			ArrayList<CardGroup> possiblePlays = new ArrayList<CardGroup>();
			ArrayList<CardGroup> temp = new ArrayList<CardGroup>();
			for(int i = 1 ; i <= 8 ; i++){	//need to play a cardGroup of at least 1 and the size will never be more than 8
				//System.out.println("Looking for card groups of size: " + i);
				for(Rank r : Rank.values()){
					if(r.compareTo(Rank.TWO)!=0){
						//System.out.println("Looking for Rank : " + r + ". Of size: " + i);
						int cardsOfRank = cardsOfRank(r);
						//System.out.println("cardsOfRank("+r+"): " + cardsOfRank(r));
						if(cardsOfRank >= i){
							//System.out.println("cardsOfRank>="+i+" !!!!!!");
							int j = firstIndexOfRank(r);
							temp = playRankChoices(j,j+cardsOfRank-1,i);
							//System.out.println("temp.size() : " + temp.size());
							for(CardGroup cG : temp){
								//System.out.println("Adding a card group to possiblePlays.");
								if(!possiblePlays.contains(cG)){
									possiblePlays.add(cG);
									//System.out.println("possiblePlays.size() : " + possiblePlays.size());
								}
								else{
									System.out.println("Tried to add a card group that was already in the possible plays.");
								}
							}
						}
					}
				}		
				//TODO make sure that the cardgroups to be added are not already in there (two's will be added multiple times)
				int cardsOfRank = cardsOfRank(Rank.TWO);
				if(cardsOfRank>=i-1 && cardsOfRank>0){
					int i1 = firstIndexOfRank(Rank.TWO);
					int i2 = i1 + cardsOfRank - 1;
					int n;
					if(i==1){
						for(CardGroup cg : playRankChoices(i1,i2,1)){
							if(!possiblePlays.contains(cg)){
								possiblePlays.add(cg);
							}
							else{//This will never happen the way i set it up
								System.out.println("tried to add a duplicate cg of twos");
							}
						}
					}
					else{
						//n=i-1;
						if(cardsOfRank>=i && cardsOfRank!=1){
							for(CardGroup cg : playRankChoices(i1,i2,i)){
								if(!possiblePlays.contains(cg)){
									possiblePlays.add(cg);
								}
								else{//This will never happen the way i set it up
									System.out.println("tried to add a duplicate cg of twos");
								}
							}	
						}
						
					}
					/*
					if(cardsOfRank>=i && cardsOfRank!=1){
						for(CardGroup cg : playRankChoices(i1,i2,n+1)){
							if(!possiblePlays.contains(cg)){
								possiblePlays.add(cg);
							}
							else{
								System.out.println("ttried to add a duplicate cg of twos");
							}
						}	
					}
					*/
				}
				
				//TODO make sure to add the possible joker
				/*
				int jokers = 0;
				for(Card c : hand){
					if(c.compareTo(new Card())==0){
						jokers++;
					}
				}

				if(jokers>=i){
					for(CardGroup cg : playRankChoices(hand.size()-jokers,hand.size()-1,i)){
						possiblePlays.add(cg);
					}
				}
				*/
				
			}
			
			//Adding a joker if need be
			if(hand.contains(new Card())){
				possiblePlays.add(new CardGroup(new Card()));
			}
			
			
			
			//Add all the possible choices to the array to be returned.
			pChoices = new CardGroup[possiblePlays.size()];
			int i = 0;
			for(CardGroup cg : possiblePlays){
				pChoices[i] = cg;
				i++;
			}
			return pChoices;
		}
		
		//System.out.println("Can play on the group. The rank to beat is: " + group.getRank() + " and the # of cards is " + group.size() + ". Also, canPlay on returns: " + this.canPlayOn(group));
		
		ArrayList<CardGroup> temp = new ArrayList<CardGroup>();
		
		for (Rank r : Rank.values()){
			if(r.compareTo(group.getRank())>0 && r.compareTo(Rank.TWO)!=0){
				//System.out.println("Testing to see the number of cards of rank:"+r+"in the hand. The are : " + cardsOfRank(r));
				if(cardsOfRank(r)>=group.size()){
					//THEN WE CAN PLAY A GROUP OF THIS RANK
					int i1 = firstIndexOfRank(r);
					int i2 = i1 + cardsOfRank(r) - 1;
					for(CardGroup cg : playRankChoices(i1,i2,group.size())){
						temp.add(cg);
					}
				}
			}
		}
		
		//ADD SPECIAL CASE FOR TWOS
		if(group.getRank().compareTo(Rank.TWO) < 0 && cardsOfRank(Rank.TWO)>=group.size() - 1 && cardsOfRank(Rank.TWO)>0){
			int i1 = firstIndexOfRank(Rank.TWO);
			int i2 = i1 + cardsOfRank(Rank.TWO) - 1;
			int n;
			
			//Check to make sure we don't attempt to make cardGroups of size 0
			if(group.size()==1){
				n=1;
			}
			else{
				n=group.size()-1;
			}
			
			//Add all possible combinations of twos that are cardinality one less than that of the group to beat.
			for(CardGroup cg : playRankChoices(i1,i2,n)){
				temp.add(cg);
			}
			
			//Add all possible combinations of twos that are cardinality equal to that of the group to beat.
			if(cardsOfRank(Rank.TWO)>=group.size() && group.size()!=1){
				for(CardGroup cg : playRankChoices(i1,i2,group.size())){
					temp.add(cg);
				}
			}
			
		}
		
		//ADD SPECIAL CASE FOR JOKERS
		if(hand.contains(new Card())){
			temp.add(new CardGroup(new Card()));
		}

		pChoices = new CardGroup[temp.size()];
		int i = 0;
		for(CardGroup cg : temp){
			pChoices[i] = cg;
			i++;
		}
		return pChoices;
	}
	
	 /**
	 * @return The number of cards in the hand with a certain rank.
	 * @param r The rank to look for in the hand.
	 */	
	public int cardsOfRank(Rank r){	//changed this to private
		int  i = 0;
		if(r==null){	//looking for jokers
			for (Card c : hand){
				if(c.isJoker()){
					i++;
				}
			}
			return i;
		}
		for (Card c : hand){
			if(!c.isJoker() && c.getRank().compareTo(r)==0){
				i++;
			}
		}
		return i;
	}
	

	public int ranksInHand(){
		//int numberOfRankTypes = 0;
		ArrayList<Rank> rankTypes = new ArrayList<Rank>();
		for(Card c : hand){
			if(!c.isJoker() && rankTypes.size()==0){
				rankTypes.add(c.getRank());
			}
			else if (!c.isJoker() && !(rankTypes.get(rankTypes.size()-1).compareTo(c.getRank())==0)){
				rankTypes.add(c.getRank());
			}
		}
		if(hand.get(hand.size()-1).isJoker()){
			return rankTypes.size()+1;	//account for the joker
		}
		return rankTypes.size();
	}
	
	/*
	 * return the card in the hand with the hishest value
	 * @pre : the hand is not empty.
	 */
	public Card getHighestCard(){
		return hand.getLast();
	}

/**
 * 
 * @param i1 the index of the first card to make combinations with
 * @param i2 the index of the last card to make combinations with
 * @param n what a size the combinations should be
 * @return an arraylist of all the possible n - sized combinations of the cards between i1 and i2 inclusively (it will be
 * of size = (i2 - i1 +1)!/(n! * (i2 - i1+1 - n)!)
 */
	
	private ArrayList<CardGroup> playRankChoices(int i1,int i2,int n){	//changed this to private
		
		ArrayList<CardGroup> rankChoices = new ArrayList<CardGroup>();
		
		int[] ind = new int[n]; 
		for(int i = 0; i < n ; i++){
			ind[i] = i1 + i;
		}
		
		Card[] cards = new Card[n];
		for(int i = 0 ; i < n;i++){
			cards[i] = hand.get(ind[i]);
		}
		
		rankChoices.add(new CardGroup(cards));
		
		while(ind[0]!=i2 - n+1){
			
			for(int i = n - 1 ; i >=0 ; i--){	//One index should always be able to be incremented.
				//find an index that can be bumped up
				//System.out.println("Checking to see: if(ind[n - 1]!=i2)");
				if(ind[n - 1]!=i2){
					ind[n - 1] = ind[n - 1] + 1;
					break;
				}
				//else ind[n - 1] == i2 and it cannot be incremented
				
				if(i==n - 1){
					//if current iteration is on n - 1 and ind[n - 1] cannot be incremented then we need to go on to the next
					continue;
				}
				//System.out.println("Checking to see: if(ind[i]<ind[i+1] - 1)");
				if(ind[i]<ind[i+1] - 1){	//then we found an index that we can increment
					ind[i] = ind[i] +1;
					int k = 1;
					for(int j = i+1; j < n ; j++,k++){
						ind[j] = ind[i]+k;	//ind[j] should never exceed i2
					}
					break;
				}
				//if we get here then it didn't break out and the ind[i] could not be incremented
			}		
			cards = new Card[n];
			for(int i = 0 ; i < n;i++){
				cards[i] = hand.get(ind[i]);
			}
			rankChoices.add(new CardGroup(cards));
		}
		return rankChoices;
	}
	
	 /**
	 * @return The first index in the hand that contains a card of Rank r.
	 * @param r The rank to look for in the hand.
	 * @pre There is a card of Rank r in the hand.
	 * @return The first index in the hand that contains a card of Rank r, or - 1 if none.
	 */		
	private int firstIndexOfRank(Rank r){	//changed this to private
		
		if(r==null){//Looking for joker
			for(Card c : hand){
				if(c.isJoker()) {
					return hand.indexOf(c);
				}
			}
			return - 1;
		}
		
		for(Card c :hand){
			if(!c.isJoker() && c.getRank().equals(r)){
				return hand.indexOf(c);
			}
		}
		return - 1;
	}
	
	/*
	 * @return : the number of card ranks in your hand. i.e. the least number of plays required to get rid of all your cards
	 */
	//private int leastPlaysPossible(){
	//	for(Rank r : Rank.values()){
	//		
	//	}
	//}
	
	public Hand clone(){
		Hand temp = new Hand();
		Iterator<Card> i = iterator();
		while(i.hasNext()){
			temp.add(i.next());
		}
		return temp;
	}
	
	public Iterator<Card> iterator() {
		return hand.iterator();
	}
	
	//A very bad idea to overload the equals method. Only done so for testing. REMOVE FOR FINAL DELIVERABLE AND CHANGE TESTS
	public boolean equals (LinkedList<Card> l){
		if(size()!=l.size()) return false;
		int i = 0;
		for (Card c : hand){
			if(!c.equals(l.get(i))) return false;
			i++;
		}
		return true;
	}
	
	public static void main(String[]args){
		Hand h = new Hand();
		h.add(new Card(Rank.THREE,Suit.CLUBS));
		h.add(new Card(Rank.THREE,Suit.DIAMONDS));
		h.add(new Card(Rank.FOUR,Suit.DIAMONDS));
		h.add(new Card(Rank.TEN,Suit.DIAMONDS));
		h.add(new Card(Rank.TWO,Suit.CLUBS));
		//h.add(new Card(Rank.TWO,Suit.DIAMONDS));
		//h.add(new Card(Rank.TWO,Suit.HEARTS));
		//h.add(new Card(Rank.TWO,Suit.SPADES));
		//h.add(new Card());
		//h.add(new Card());
		
		System.out.println( "The ranks in the hand are : " + h.ranksInHand() + "");
		
		//CardGroup cG = new CardGroup(new Card[0]);
		CardGroup[] c = h.playChoices(new CardGroup());
		System.out.println("The size of the carg group found was : "  + c.length);
		for(CardGroup cG : c){
			Iterator<Card> i = cG.iterator();
			while(i.hasNext()){
				System.out.print(i.next() + ",");
			}
			System.out.println();
		}
	}
}

