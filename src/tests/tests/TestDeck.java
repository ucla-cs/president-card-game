package tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import util.Card;
import util.Deck;
import util.EmptyDeckException;


public class TestDeck
{
	@Test(expected=EmptyDeckException.class)
	public void testOneDeck2Jokers()
	{
		Deck deck = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.TWO);
		assertEquals(54,deck.size());
		deck.shuffle();
		assertEquals(54,deck.size());
		Set<Card> cards = new HashSet<Card>();
		for(int i = 0; i < 54; i++)
		{
			Card card = deck.draw();
			if(!card.isJoker())
			{
				assertFalse(cards.contains(card));
			}
			cards.add(card);
			assertEquals(i, 53 - deck.size());
		}
		assertEquals(0, deck.size());
		deck.draw();
	}
	
	@Test(expected=EmptyDeckException.class)
	public void testOneDeck0Jokers()
	{
		Deck deck = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ZERO);
		assertEquals(52,deck.size());
		deck.shuffle();
		assertEquals(52,deck.size());
		Set<Card> cards = new HashSet<Card>();
		for(int i = 0; i < 54; i++)
		{
			Card card = deck.draw();
			assertFalse(cards.contains(card));
			cards.add(card);
			assertEquals(i, 51 - deck.size());
		}
		assertEquals(0, deck.size());
		deck.draw();
	}
	
	@Test(expected=EmptyDeckException.class)
	public void testOneDeck1Joker()
	{
		Deck deck = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ONE);
		assertEquals(53,deck.size());
		deck.shuffle();
		assertEquals(53,deck.size());
		Set<Card> cards = new HashSet<Card>();
		for(int i = 0; i < 53; i++)
		{
			Card card = deck.draw();
			assertFalse(cards.contains(card));
			cards.add(card);
			assertEquals(i, 52 - deck.size());
		}
		assertEquals(0, deck.size());
		deck.draw();
	}
	
	@Test(expected=EmptyDeckException.class)
	public void testTwoDecks2Jokers()
	{
		Deck deck = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.TWO);
		assertEquals(108,deck.size());
		deck.shuffle();
		assertEquals(108,deck.size());
		for(int i = 0; i < 108; i++)
		{
			deck.draw();
			assertEquals(i, 107 - deck.size());
		}
		assertEquals(0, deck.size());
		deck.draw();
	}
}
