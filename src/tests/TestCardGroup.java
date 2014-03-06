package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tests.AllCards.a2C;
import static tests.AllCards.a2H;
import static tests.AllCards.a3C;
import static tests.AllCards.a3H;
import static tests.AllCards.a4D;
import static tests.AllCards.a4H;
import static tests.AllCards.a5C;
import static tests.AllCards.aAC;
import static tests.AllCards.aAD;
import static tests.AllCards.aAH;
import static tests.AllCards.aAS;
import static tests.AllCards.aJC;
import static tests.AllCards.aJH;
import static tests.AllCards.aKH;
import static tests.AllCards.aKS;
import static tests.AllCards.aQD;
import static tests.AllCards.aQH;
import static tests.AllCards.aQS;
import static tests.AllCards.aTC;
import static tests.AllCards.aJOKER;

import org.junit.Test;

import util.CardGroup;
import util.Card;
import util.Card.Rank;
import tests.AllCards;

public class TestCardGroup {
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException1(){
		Card[] cards = new Card[3];
		cards[0] = a2C;
		cards[1] = a2H;
		cards[2] = a3C;
		
		CardGroup g1 = new CardGroup(cards);
		//CardGroup c = new CardGroup( cards );
		
	}
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException2(){
		Card[] cards = { aJOKER, aJOKER};
		CardGroup g1 = new CardGroup(cards);
		//CardGroup c = new CardGroup( cards );
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException3(){
		Card[] cards = { aJOKER, aAD};
		CardGroup g1 = new CardGroup(cards);
		//CardGroup c = new CardGroup( cards );
	}
	
	@Test
	public void testContains(){
		Card[] cards = { a2C, a2H };
		CardGroup g1 = new CardGroup(cards);
		assertTrue( g1.contains(a2C));
		assertTrue( g1.contains(a2H));
		assertFalse(g1.contains(a4D));
		//CardGroup c = new CardGroup( cards );
		
	}
	@Test
	public void testSize(){
		Card[] cards = { a2C, a2H };
		Card[] cards1 = { a2C};
		Card[] cards2 = {aQD, aQH, aQS};
		CardGroup g1 = new CardGroup(cards);
		CardGroup g2 = new CardGroup(cards1);
		CardGroup g3 = new CardGroup(cards2);
		
		assertEquals( g1.size(), 2);
		assertEquals( g2.size(), 1);
		assertEquals( g3.size(), 3);
		//CardGroup c = new CardGroup( cards );
	}
	
	@Test
	public void testGetRank(){
		Card[] cards = { a2C, a2H };
		CardGroup g1 = new CardGroup(cards);
		assertEquals(g1.getRank(), Rank.TWO);
	}
	
	@Test
	public void testIsJoker(){
		Card[] cards = { a2C, a2H };
		CardGroup g1 = new CardGroup(cards);
		assertFalse(g1.isJoker());
		
		g1 = new CardGroup(aJOKER);
		assertTrue(g1.isJoker());
	}
	
	public void testEquals(){
		Card[] cards1 = { a2C, a2H };
		CardGroup g1 = new CardGroup(cards1);
		Card[] cards2 = { a2C, a2H };
		CardGroup g2 = new CardGroup(cards2);
		Card[] cards3 = { a2C};
		CardGroup g3 = new CardGroup(cards3);
		assertTrue(g1.equals(g2));
		assertFalse(g1.equals(g3));
	}	
	
}
