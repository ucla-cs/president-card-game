package tests.tests;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Stack;

import model.TurnHistory;
import model.Play;

import org.junit.Test;

import util.Card;
import util.CardGroup;
import util.Card.Rank;
import util.Card.Suit;

public class TestTurnHistory 
{
	@Test
	public void testTurnHistory()
	{
		String lPlayer1 = "FDR";
		String lPlayer2 = "JFK";
		String lPlayer3 = "Clinton";
		CardGroup lGroup1 = new CardGroup(new Card(Rank.ACE, Suit.SPADES));
		CardGroup lGroup2 = new CardGroup(new Card(Rank.KING, Suit.SPADES));
		CardGroup lGroup3 = new CardGroup(new Card(Rank.QUEEN, Suit.SPADES));
		
		Stack<model.Play> lStack = new Stack<Play>();
		Play lPlay1 = new Play(lPlayer1, lGroup3);
		Play lPlay2 = new Play(lPlayer2, lGroup2);
		Play lPlay3 = new Play(lPlayer3, lGroup1);
		lStack.push(lPlay1);
		lStack.push(lPlay2);
		lStack.push(lPlay3);
		
		TurnHistory lHistory = new TurnHistory(lStack);
		
		assertEquals(lPlay3,lHistory.getLastPlay());
		assertEquals(lPlayer3,lHistory.getLastPlay().getPlayerID());
		assertEquals(lGroup1,lHistory.getLastPlay().getCardGroup());
		
		Iterator<Play> lIter = lHistory.iterator();
		assertEquals(lPlay1,lIter.next());
		assertEquals(lPlay2,lIter.next());
		assertEquals(lPlay3,lIter.next());
	}
	
}