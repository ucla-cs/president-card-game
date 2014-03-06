package tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.Hand;

import org.junit.Test;

import util.Card;
import util.CardGroup;
import util.Deck;
import util.Card.Rank;
import util.Card.Suit;
import util.Deck.NumberOfJokers;
import util.Deck.NumberOfPacks;

public class TestHand 
{

	private Card aCard = new Card(Rank.ACE, Suit.CLUBS);
	
	@Test
	public void testAdd()
	{
		Hand lHand = new Hand();
		lHand.add(new Card(Rank.ACE, Suit.CLUBS));
		assertTrue(lHand.size() == 1);
	}
	
	@Test
	public void testSort()
	{
		Hand lHand = new Hand();
		Deck lDeck = new Deck(NumberOfPacks.ONE, NumberOfJokers.ZERO);

		for (int i = 0; i < 10; i++)
		{
			lHand.add(lDeck.draw());
		}
		
		int lLastOrdinal;
		for (Card card : lHand) 
		{
			lLastOrdinal = card.getRank().ordinal();
			assertTrue(lLastOrdinal <= card.getRank().ordinal());
		}
	}
	
	@Test
	public void testRemoveClearAndSize()
	{
		Hand lHand = new Hand();
		lHand.add(aCard);
		lHand.remove(aCard);
		assertTrue(lHand.size() == 0);
		
		Hand lHand2 = new Hand();
		lHand2.add(aCard);
		lHand2.clear();
		assertTrue(lHand2.size() == 0);
		
		Hand lHand3 = new Hand();
		lHand3.add(aCard);
		assertTrue(lHand3.size() == 1);
	}

	@Test
	public void testContains()
	{
		Hand lHand = new Hand();
		lHand.add(aCard);
		assertTrue(lHand.contains(aCard));
	}
	
	@Test
	public void TestIterator()
	{
		Hand lHand = new Hand();
		Deck lDeck = new Deck(NumberOfPacks.ONE, NumberOfJokers.ZERO);

		for (int i = 0; i < 10; i++)
		{
			lHand.add(lDeck.draw());
		}
		
		for (Card lCard : lHand) 
		{
			assertTrue(lHand.contains(lCard));
		}
	}
	
	@Test 
	public void testCannotPlayOn()
	{
		// Reference Hand
		Hand lRefHand = new Hand();
		
		lRefHand.add(AllCards.a4C);
		lRefHand.add(AllCards.a4S);
		lRefHand.add(AllCards.a4H);
		lRefHand.add(AllCards.aTC);
		lRefHand.add(AllCards.a5C);
		lRefHand.add(AllCards.a5H);
		
		// CardGroups
		CardGroup lJokerSingleton = new CardGroup(AllCards.aJOKER);
		CardGroup lQueenSingleton = new CardGroup(new Card[]{AllCards.aQC});
		CardGroup lTenDouble = new CardGroup(new Card[]{AllCards.aTC, AllCards.aTD});
		CardGroup lQueenDouble  = new CardGroup(new Card[]{AllCards.aQC, AllCards.aQC});
		CardGroup lFourTriple = new CardGroup(new Card[]{AllCards.a4C, AllCards.a4C, AllCards.a4C});
		CardGroup lQueenTriple = new CardGroup(new Card[]{AllCards.aQC, AllCards.aQC, AllCards.aQC});
		
		// Test Cannot Play Singleton
		assertTrue(lRefHand.playChoices(lQueenSingleton).length == 0);
		
		// Test Cannot Play Doubles
		assertTrue(lRefHand.playChoices(lTenDouble).length == 0); 
		assertTrue(lRefHand.playChoices(lQueenDouble).length == 0);
		
		// Test Cannot Play Triples
		assertTrue(lRefHand.playChoices(lFourTriple).length == 0);
		assertTrue(lRefHand.playChoices(lQueenTriple).length == 0);
		
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.a2H);
		lRefHand.add(AllCards.aJOKER);
		
		// Test Cannot Play on Joker
		assertTrue(lRefHand.playChoices(lJokerSingleton).length == 0);
	}
	
	@Test
	public void testPlayObvious()
	{
		// Reference Hand
		Hand lRefHand = new Hand();
		
		lRefHand.add(AllCards.a4C);
		lRefHand.add(AllCards.a4S);
		lRefHand.add(AllCards.a4H);
		lRefHand.add(AllCards.aTC);
		lRefHand.add(AllCards.a5C);
		lRefHand.add(AllCards.a5H);
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.a2H);
		lRefHand.add(AllCards.aJOKER);
		
		// CardGroups
		CardGroup lNineSingleton = new CardGroup(new Card[]{AllCards.a9C});
		CardGroup lFourDouble = new CardGroup(new Card[]{AllCards.a4C, AllCards.a4H});		
		CardGroup lThreeTriple = new CardGroup(new Card[]{AllCards.a3C, AllCards.a3D, AllCards.a3H});
		
		// Test Can Play Straightforward Singleton
		CardGroup[] lResult = lRefHand.playChoices(lNineSingleton);
		assertTrue(lResult.length == 4);
		assertTrue(played(lResult, AllCards.aTC));
		assertTrue(played(lResult, AllCards.a2C));
		assertTrue(played(lResult, AllCards.a2H));
		assertTrue(played(lResult, AllCards.aJOKER));
		
		// Test Can Play Straightforward Double
		lResult = lRefHand.playChoices(lFourDouble);
		assertTrue(lResult.length == 5);
		assertTrue(played(lResult, AllCards.a5C, AllCards.a5H));
		assertTrue(played(lResult, AllCards.a2C));
		assertTrue(played(lResult, AllCards.a2H));
		assertTrue(played(lResult, AllCards.a2C, AllCards.a2H));
		assertTrue(played(lResult, AllCards.aJOKER));
		
		// Test Can Play Triple
		lResult = lRefHand.playChoices(lThreeTriple);
		assertTrue(lResult.length == 3);
		assertTrue(played(lResult, AllCards.a4C, AllCards.a4H, AllCards.a4S));
		assertTrue(played(lResult, AllCards.a2C, AllCards.a2H));
		assertTrue(played(lResult, AllCards.aJOKER));
	}
	
	@Test
	public void testEasyTricky()
	{
		// Reference Hand
		Hand lRefHand = new Hand();
		
		lRefHand.add(AllCards.a4C);
		lRefHand.add(AllCards.a4S);
		lRefHand.add(AllCards.a4H);
		lRefHand.add(AllCards.aTC);
		lRefHand.add(AllCards.a5C);
		lRefHand.add(AllCards.a5H);
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.a2H);
		lRefHand.add(AllCards.aJOKER);
		
		// CardGroups
		CardGroup lFourSingleton = new CardGroup(new Card[]{AllCards.a4C});
		CardGroup lThreeDouble = new CardGroup(new Card[]{AllCards.a3C, AllCards.a3D});
		
		CardGroup[] lResult;
		
		// Test Can Play Tricky Singleton
		lResult = lRefHand.playChoices(lFourSingleton);
		assertTrue(lResult.length == 6);
		assertTrue(played(lResult, AllCards.aTC));
		assertTrue(played(lResult, AllCards.a5C));
		assertTrue(played(lResult, AllCards.a5H));
		assertTrue(played(lResult, AllCards.a2C));
		assertTrue(played(lResult, AllCards.a2H));
		assertTrue(played(lResult, AllCards.aJOKER));
		
		// Test Can Play Tricky Double
		lResult = lRefHand.playChoices(lThreeDouble);
		assertTrue(lResult.length == 8);
		assertTrue(played(lResult, AllCards.a5C, AllCards.a5H));
		assertTrue(played(lResult, AllCards.a4C, AllCards.a4H));
		assertTrue(played(lResult, AllCards.a4C, AllCards.a4S));
		assertTrue(played(lResult, AllCards.a4H, AllCards.a4S));
		assertTrue(played(lResult, AllCards.a2C));
		assertTrue(played(lResult, AllCards.a2H));
		assertTrue(played(lResult, AllCards.a2C, AllCards.a2H));
		assertTrue(played(lResult, AllCards.aJOKER));
		
	}
	
	@Test
	public void testPlayChoicesMediumTricky()
	{
		// Reference Hand
		Hand lRefHand = new Hand();
		
		lRefHand.add(AllCards.a4C);
		lRefHand.add(AllCards.a4S);
		lRefHand.add(AllCards.a4H);
		lRefHand.add(AllCards.aTC);
		lRefHand.add(AllCards.a5C);
		lRefHand.add(AllCards.a5H);
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.a2H);
		lRefHand.add(AllCards.aJOKER);
		
		CardGroup lThreeSingleton = new CardGroup(new Card[]{AllCards.a3C});
		CardGroup[] lResult;
		
		// Test Can Play Everything (also tricky) 
		lResult = lRefHand.playChoices(lThreeSingleton);
		assertTrue(lResult.length == 9);
		assertTrue(played(lResult, AllCards.aTC));
		assertTrue(played(lResult, AllCards.a5C));
		assertTrue(played(lResult, AllCards.a5H));
		assertTrue(played(lResult, AllCards.a2C));
		assertTrue(played(lResult, AllCards.a2H));
		assertTrue(played(lResult, AllCards.a4H));
		assertTrue(played(lResult, AllCards.a4S));
		assertTrue(played(lResult, AllCards.a4C));
		assertTrue(played(lResult, AllCards.aJOKER));
	}
	
	@Test
	public void testPlayChoicesMassive()
	{
		// Reference Hand
		Hand lRefHand = new Hand();
		
		// CardGroups
		CardGroup lThreeDouble = new CardGroup(new Card[]{AllCards.a3C, AllCards.a3D});
		CardGroup[] lResult;
		
		// Test Massive King-Attack on Double
		lRefHand.clear();
		lRefHand.add(AllCards.aKC);
		lRefHand.add(AllCards.aKD);
		lRefHand.add(AllCards.aKH);
		lRefHand.add(AllCards.aKS);
		lRefHand.add(AllCards.aKC);
		lRefHand.add(AllCards.aKD);
		lRefHand.add(AllCards.aKH);
		lRefHand.add(AllCards.aKS);
		lRefHand.add(AllCards.aQC);
		lRefHand.add(AllCards.aQD);
		lRefHand.add(AllCards.aQH);
		lRefHand.add(AllCards.aQC);
		lRefHand.add(AllCards.aQD);
		lRefHand.add(AllCards.aQH);
		lRefHand.add(AllCards.a3C);
		lRefHand.add(AllCards.a3D);
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.aJOKER);
		
		lResult = lRefHand.playChoices(lThreeDouble);
		assertTrue(lResult.length == 45);
		for (CardGroup group : lResult) {
			assertFalse(group.contains(AllCards.a3C));
			assertFalse(group.contains(AllCards.a3D));
		}
	}
	
	private boolean played (CardGroup[] resultGroup, Card...expected) {
		return played (resultGroup, new CardGroup(expected));
	}
	
	private boolean played (CardGroup[] resultGroup, CardGroup expected) {
		for (int i=0; i<resultGroup.length; i++) {
			if (equals(resultGroup[i], expected)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void testCanPlayOn()
	{
		//Reference Hand
		Hand lRefHand = new Hand();
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		
		CardGroup lCardGroup;
		
		//Same Rank Different Cardinality
		Card[] lEqualTriple = {new Card(Rank.JACK, Suit.CLUBS),new Card(Rank.JACK, Suit.CLUBS),new Card(Rank.JACK, Suit.DIAMONDS)};
		
		//Same Cardinality Different Rank
		Card[] lLowDouble = {new Card(Rank.TEN, Suit.CLUBS),new Card(Rank.TEN, Suit.CLUBS)};
		Card[] lHighDouble = {new Card(Rank.QUEEN, Suit.CLUBS),new Card(Rank.QUEEN, Suit.CLUBS)};
		
		//Different Cardinality Different Rank
		Card[] lLowSingle = {new Card(Rank.TEN, Suit.CLUBS)};
		Card[] lHighSingle = {new Card(Rank.QUEEN, Suit.CLUBS)};
		Card[] lHighTriple = {new Card(Rank.QUEEN, Suit.CLUBS),new Card(Rank.QUEEN, Suit.CLUBS),new Card(Rank.QUEEN, Suit.DIAMONDS)};
		
		
		//LEGAL PLAYS
		
		//Opposing Hand with Same Cardinality Lower Rank
		lCardGroup = new CardGroup(lLowDouble);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		
		//Opposing Hand with Lower Cardinality Lower Rank
		lCardGroup = new CardGroup(lLowSingle);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		
		//Single Two Playing on Single
		lRefHand.clear();
		lRefHand.add(new Card(Rank.TWO, Suit.DIAMONDS));
		lCardGroup = new CardGroup(lHighSingle);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		
		//Single Two Playing on Double
		lRefHand.clear();
		lRefHand.add(new Card(Rank.TWO, Suit.DIAMONDS));
		lCardGroup = new CardGroup(lHighDouble);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		
		//Two Two's Playing on Triple
		lRefHand.clear();
		lRefHand.add(new Card(Rank.TWO, Suit.DIAMONDS));
		lRefHand.add(new Card(Rank.TWO, Suit.SPADES));
		lCardGroup = new CardGroup(lHighTriple);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		
		//Two Two's Playing on a Single
		lRefHand.clear();
		lRefHand.add(new Card(Rank.TWO, Suit.DIAMONDS));
		lRefHand.add(new Card(Rank.TWO, Suit.SPADES));
		lCardGroup = new CardGroup(lHighSingle);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		
		//Joker Hand playing single, double and triple
		lRefHand.clear();
		lRefHand.add(new Card());
		lCardGroup = new CardGroup(lHighSingle);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		lCardGroup = new CardGroup(lHighDouble);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		lCardGroup = new CardGroup(lEqualTriple);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		
		//Multiple Higher Hands
		lRefHand.clear();
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		lRefHand.add(new Card(Rank.QUEEN, Suit.CLUBS));
		lRefHand.add(new Card(Rank.QUEEN, Suit.CLUBS));
		lRefHand.add(new Card(Rank.QUEEN, Suit.CLUBS));
		lCardGroup = new CardGroup(lLowDouble);
		assertTrue(lRefHand.canPlayOn(lCardGroup));
		assertTrue(lRefHand.canPlayOn(lCardGroup));
	}
	
	@Test
	public void testCannotPlayOn2()
	{
		//Reference Hand
		Hand lRefHand = new Hand();
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		
		CardGroup lCardGroup;
		
		//Same Rank Different Cardinality
		Card[] lEqualSingle= {new Card(Rank.JACK, Suit.CLUBS)};
		Card[] lEqualDouble = {new Card(Rank.JACK, Suit.DIAMONDS),new Card(Rank.JACK, Suit.SPADES)};
		Card[] lEqualTriple = {new Card(Rank.JACK, Suit.CLUBS),new Card(Rank.JACK, Suit.CLUBS),new Card(Rank.JACK, Suit.DIAMONDS)};
		
		//Same Cardinality Different Rank
		Card[] lHighDouble = {new Card(Rank.QUEEN, Suit.CLUBS),new Card(Rank.QUEEN, Suit.CLUBS)};
		
		//Different Cardinality Different Rank
		Card[] lLowTriple = {new Card(Rank.TEN, Suit.CLUBS),new Card(Rank.TEN, Suit.CLUBS),new Card(Rank.TEN, Suit.DIAMONDS)};
		Card[] lHighTriple = {new Card(Rank.QUEEN, Suit.CLUBS),new Card(Rank.QUEEN, Suit.CLUBS),new Card(Rank.QUEEN, Suit.DIAMONDS)};
		
		//reset Hand
		lRefHand.clear();
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		lRefHand.add(new Card(Rank.JACK, Suit.CLUBS));
		
		//ILLEGAL PLAYS
		
		//Opposing Hand with Same Rank Lower Cardinality
		lCardGroup = new CardGroup(lEqualSingle);
		assertFalse(lRefHand.canPlayOn(lCardGroup));
		
		//Opposing Hand with Same Rank Equal Cardinality
		lCardGroup = new CardGroup(lEqualDouble);
		assertFalse(lRefHand.canPlayOn(lCardGroup));

		//Opposing Hand with Same Rank Higher Cardinality
		lCardGroup = new CardGroup(lEqualTriple);
		assertFalse(lRefHand.canPlayOn(lCardGroup));
		
		//Opposing Hand with Same Cardinality Equal Rank
		lCardGroup = new CardGroup(lEqualDouble);
		assertFalse(lRefHand.canPlayOn(lCardGroup));
		
		//Opposing Hand with Same Cardinality Higher Rank
		lCardGroup = new CardGroup(lHighDouble);
		assertFalse(lRefHand.canPlayOn(lCardGroup));
		
		
		//Opposing Hand with Higher Cardinality Lower Rank
		lCardGroup = new CardGroup(lLowTriple);
		assertFalse(lRefHand.canPlayOn(lCardGroup));
		
		//Opposing Hand with Higher Cardinality Higher Rank
		lCardGroup = new CardGroup(lHighTriple);
		assertFalse(lRefHand.canPlayOn(lCardGroup));
		
		//Single Two Hand Playing on Triple
		lRefHand.clear();
		lRefHand.add(new Card(Rank.TWO, Suit.DIAMONDS));
		lCardGroup = new CardGroup(lEqualTriple);
		assertFalse(lRefHand.canPlayOn(lCardGroup));
		
		//Opposing Single Two Hand
		CardGroup lTwoGroup = new CardGroup(new Card(Rank.TWO,Suit.DIAMONDS));
		assertFalse(lRefHand.canPlayOn(lTwoGroup));
		
		//Opposing Joker Hand
		CardGroup lJokerGroup = new CardGroup(new Card());
		assertFalse(lRefHand.canPlayOn(lJokerGroup));
		

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayGroup()
	{
		Hand lHand = new Hand();
		lHand.add(new Card(Rank.TEN, Suit.CLUBS));
		lHand.add(new Card(Rank.TEN, Suit.DIAMONDS));
		ArrayList<Card> lList = new ArrayList<Card>();
		lList.add(new Card(Rank.TEN, Suit.CLUBS));
		lList.add(new Card(Rank.TEN, Suit.DIAMONDS));
		CardGroup playGroup = lHand.playGroup(lList);
		assertTrue(playGroup != null);
		assertEquals(2,playGroup.size());
		assertTrue(playGroup.contains(new Card(Rank.TEN, Suit.CLUBS)));
		assertTrue(playGroup.contains(new Card(Rank.TEN, Suit.DIAMONDS)));
		assertEquals(0, lHand.size());
		
		try {
			lList.clear();
			lList.add(new Card(Rank.FIVE, Suit.HEARTS));
			lHand.playGroup(lList);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			
		}
		
		lHand = new Hand();
		lHand.add(new Card(Rank.TEN, Suit.CLUBS));
		lHand.add(new Card(Rank.TEN, Suit.DIAMONDS));
		lList = new ArrayList<Card>();
		lList.add(new Card(Rank.TEN, Suit.CLUBS));
		lList.add(new Card(Rank.EIGHT, Suit.CLUBS));
		lHand.playGroup(lList);
	}
	
	private boolean equals(CardGroup studentResult, CardGroup possible)
	{
		if(studentResult.isJoker() && !possible.isJoker()) {
			return false;
		}
		
		if(!studentResult.isJoker() && possible.isJoker()) {
			return false;
		}
		
		if(studentResult.isJoker() && possible.isJoker()) {
			return true;
		}
		
		if(studentResult.size() != possible.size()) {
			return false;
		}
		
		for (Card card : studentResult) {
			if(! possible.contains(card)) {
				return false;
			}
		}
		
		for (Card card : possible) {
			if(! studentResult.contains(card)) {
				return false;
			}
		}
		
		return true;
	}
	
	
}