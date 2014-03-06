package tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tests.AllCards.a4C;
import static tests.AllCards.a4D;
import static tests.AllCards.a4H;
import static tests.AllCards.a5C;
import static tests.AllCards.aJOKER;

import org.junit.Test;

import util.Card;
import util.CardGroup;

public class TestCardGroup
{
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorInvalid1()
	{
		Card[] cards = new Card[2];
		cards[0] = a4C;
		cards[1] = a5C;
		new CardGroup(cards);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorInvalid2()
	{
		Card[] cards = new Card[2];
		cards[0] = a4C;
		cards[1] = aJOKER;
		new CardGroup(cards);
	}
	
	@Test()
	public void testConstructor1()
	{
		CardGroup list = new CardGroup(a4C);
		assertEquals(1,list.size());
		assertTrue(list.contains(a4C));
		assertEquals( Card.Rank.FOUR, list.getRank());
		
		list = new CardGroup(aJOKER);
		assertEquals(1,list.size());
		assertTrue(list.contains(aJOKER));
		assertEquals( null, list.getRank());
		assertTrue( list.isJoker());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor2()
	{
		Card[] cards = new Card[4];
		cards[0] = a4C;
		cards[1] = a4D;
		cards[2] = a4D;
		cards[3] = a4H;
		CardGroup list = new CardGroup(cards);
		assertEquals(4, list.size());
		assertTrue(list.contains(a4C));
		assertTrue(list.contains(a4D));
		assertTrue(list.contains(a4H));
		assertFalse(list.isJoker());
		assertEquals(Card.Rank.FOUR,list.getRank());

		for( Card c : list )
		{
			assertTrue( list.contains(c));
		}
		
		cards = new Card[2];
		cards[0] = aJOKER;
		cards[1] = aJOKER;
		list = new CardGroup(cards);
		assertEquals(2, list.size());
		assertTrue(list.contains(aJOKER));
		assertTrue(list.isJoker());
		assertEquals(null,list.getRank());
	}
}
