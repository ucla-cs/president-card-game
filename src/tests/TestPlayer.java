package tests;

import static org.junit.Assert.*;
import model.Hand;
import model.Player;

import org.junit.Test;

import robots.BasicAI;
import robots.DeterministicNPAI;
import robots.IRobot;
import util.Card;
import util.Card.Rank;
import util.Card.Suit;

/*
public class TestPlayer{
	
	@Test
	public void creator(){
		Player p = new Player("name");
		assertEquals(p.getName(), "name");	
	}
	@Test
	public void TestGetAI(){
		Player p = new Player("name");
		IRobot i = new BasicAI("name");
		p.setAI(i);
		assertEquals( p.getAI(), i);
		
		i = new DeterministicNPAI("name");
		p.setAI(i);
		
		assertEquals(p.getAI(), i);
		
	}
	
		
	@Test
	public void TestPosition(){
		Player p = new Player("name");
		
		p.setPosition(4);
		
		assertEquals(p.getPosition(), 4);
	}
	
	@Test
	public void TestPoints(){
		Player p = new Player("name");
		
		p.addPoints(8);
		
		assertEquals(p.getPoints(), 8);
		
		p.addPoints(8);
		
		assertEquals(p.getPoints(), 16);
		
		p.clearPoints();
		assertEquals(p.getPoints(), 0);
		
	}
	
	@Test
	public void TestHand(){
		
		Player p = new Player("name");
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.FIVE, Suit.HEARTS);
		Card c3 = new Card(Rank.KING, Suit.SPADES);
 		p.addToHand(c1);
 		p.addToHand(c2);
 		p.addToHand(c3);
 		
 		assertTrue( p.getHand().contains(c1));
 		assertTrue( p.getHand().contains(c2));
 		assertTrue( p.getHand().contains(c3));
 		assertEquals(p.getHand().size(),3);		
		
	}
	
	@Test
	public void TestClone(){
		
		Player p = new Player("name");
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.FIVE, Suit.HEARTS);
		Card c3 = new Card(Rank.KING, Suit.SPADES);
 		p.addToHand(c1);
 		p.addToHand(c2);
 		p.addToHand(c3);
 		
 		Player t = p.clone();
 		 
 		assertTrue( t.getHand().contains(c1));
 		assertTrue( t.getHand().contains(c2));
 		assertTrue( t.getHand().contains(c3));
 				
		assertEquals(t.getHand().size(),3);
		
	}
}
*/
