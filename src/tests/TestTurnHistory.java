package tests;

import util.CardGroup;
import util.Card;
import model.Play;

import java.util.Iterator;
import java.util.Stack;
import org.junit.Test;
import model.TurnHistory;
import static tests.AllCards.aJOKER;
import static tests.AllCards.aKS;
import static tests.AllCards.aKH;
import static tests.AllCards.aKD;
import static tests.AllCards.aQC;
import static tests.AllCards.aQD;
import static tests.AllCards.aJD;

import static org.junit.Assert.assertEquals;


public class TestTurnHistory {
	
	// set up Play objects, Card arrays, and CardGroups
	private Card[] cardsC = {aKS, aKH, aKD};
	private CardGroup playerCGroup = new CardGroup(cardsC);
	private Play playC = new Play("PlayerC", playerCGroup);
	
	private Card[] cardsB = {aQC, aQD};
	private CardGroup playerBGroup = new CardGroup(cardsB);
	private Play playB = new Play("PlayerB", playerBGroup);
	
	private Card[] cardsA = {aJD};
	private CardGroup playerAGroup = new CardGroup(cardsA);
	private Play playA = new Play("PlayerA", playerAGroup);
	
	private Card[] cardsD = {aJOKER};
	private CardGroup playerDGroup = new CardGroup(cardsD);
	private Play playD = new Play("PlayerD", playerDGroup);
	
	private Iterator<Play> playIterator;
	private Stack<Play> playlist = makeStack();
	private Stack<Play> compareList = makeStack();
	private TurnHistory history = new TurnHistory(playlist);

		// this method creates a Stack of Play objects
	public Stack<Play> makeStack(){
		Stack<Play> list = new Stack<Play>();
		list.push(playA);
		list.push(playB);
		list.push(playC);
		list.push(playD);
		return list;
	}
		
	@Test public void testGetLastPlay() {
		// check to see if the last play is equal to the play in the stack
		Play lastPlay = history.getLastPlay();
		Play lastPlayStack = compareList.peek();
		assertEquals(lastPlay, lastPlayStack);
	}
		
	@Test public void testSize() {
		// check to see if the size of the TurnHistory object is equal to size of stack
		int sizeTurnHistory = history.size();
		int sizeStack = compareList.size();
		assertEquals(sizeTurnHistory, sizeStack);
	}
			
	@Test public void testIterator() {
		// check to see that the iterator functions properly
		Play playFromIterator;
		playIterator = history.iterator();
		// check to see if the objects in the stack are the same as the objects in the iterator
		while (playIterator.hasNext()) {
				playFromIterator = playIterator.next();
				assertEquals(playFromIterator, compareList.remove(0));
		}
		// check to see if the Stack is empty (to account for all Play objects)
		assertEquals(0, compareList.size());
	}
}
		
