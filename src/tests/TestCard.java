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

import util.Card;

public class TestCard
{
	@Test
	public void testToString()
	{
		assertEquals( "ACE of CLUBS", aAC.toString());
		assertEquals( "THREE of CLUBS", a3C.toString());
		assertEquals( "TEN of CLUBS", aTC.toString());
		assertEquals( "JACK of CLUBS", aJC.toString());
		assertEquals( "QUEEN of HEARTS", aQH.toString());
		assertEquals( "KING of SPADES", aKS.toString());
		assertEquals( "QUEEN of DIAMONDS", aQD.toString());
		assertEquals( "JOKER", aJOKER.toString());
	}
	
	@Test
	public void testCompareTo()
	{
		assertTrue( aAC.compareTo(a2C) < 0);
		assertTrue( a2C.compareTo(a2C) == 0);
		assertTrue( a2C.compareTo(aAC) > 0);
		assertTrue( aAC.compareTo(a2C) < 0);
		assertTrue( a2C.compareTo(a2H) == 0);
		assertTrue( a3C.compareTo(a3H) == 0);
		assertTrue( a3C.compareTo(aJOKER) <= 0 );
		assertTrue( aJOKER.compareTo(a3C) > 0);
		assertTrue( aJOKER.compareTo(aJOKER) == 0);
	}
	
	@Test
	public void testGetSuit()
	{
		assertEquals( Card.Suit.CLUBS, aAC.getSuit() );
		assertEquals( Card.Suit.DIAMONDS, aAD.getSuit() );
		assertEquals( Card.Suit.HEARTS, aAH.getSuit() );
		assertEquals( Card.Suit.SPADES, aAS.getSuit() );
	}
	
	@Test
	public void testGetRank()
	{
		assertEquals( Card.Rank.ACE, aAC.getRank() );
		assertEquals( Card.Rank.FOUR, a4D.getRank() );
		assertEquals( Card.Rank.JACK, aJH.getRank() );
		assertEquals( Card.Rank.QUEEN, aQS.getRank() );
	}
	
	@Test
	public void testEquals()
	{
		assertTrue( aAC.equals( aAC ));
		assertTrue( aAC.equals( new Card(Card.Rank.ACE, Card.Suit.CLUBS )));
		assertFalse( aAC.equals( aAH ));
		assertFalse( aKH.equals( a4H ));
		assertTrue( aJOKER.equals( aJOKER ));
		assertTrue( aJOKER.equals( new Card() ));
		assertFalse( aKH.equals( aJOKER ));
		assertFalse( aKH.equals( null ));
		assertFalse(aJOKER.equals(null));
		assertFalse( aJOKER.equals( "JOKER"));
	}
	
	@Test
	public void testHashCode()
	{
		assertTrue( aAC.hashCode() == new Card(Card.Rank.ACE, Card.Suit.CLUBS ).hashCode());
		assertFalse( aAC.hashCode() == aAH.hashCode() );
		assertEquals( a5C.getSuit().ordinal()*13 + a5C.getRank().ordinal(), a5C.hashCode() );
	}
}
