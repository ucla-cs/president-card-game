package tests.tests;

import java.util.Stack;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import model.Hand;
import model.Play;
import model.TurnHistory;

import org.junit.Before;
import org.junit.Test;

import robots.IRobot;
import util.Card;
import util.CardGroup;
import util.Card.Rank;

public class TestRobotImpl {

	private IRobot aRobot;
	
	@Before
	public void setUp() {
		aRobot = null;
	}
	
	@Test
	public void testRobot1()
	{
		Hand lRefHand = new Hand();
		
		CardGroup lCanPlay  = new CardGroup(new Card[]{AllCards.aTC, AllCards.aTD, AllCards.aTH});
		
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.a2D);
		lRefHand.add(AllCards.a2S);
		lRefHand.add(AllCards.a3H);
		lRefHand.add(AllCards.a5C);
		lRefHand.add(AllCards.a7D);
		lRefHand.add(AllCards.a7C);
		lRefHand.add(AllCards.aTD);
		lRefHand.add(AllCards.aJC);
		lRefHand.add(AllCards.aJD);
		lRefHand.add(AllCards.aJS);
		
		// Standard move required
		Stack<Play> lPlays = new Stack<Play>();
		lPlays.push(new Play("dkawry", lCanPlay));
		TurnHistory lHistory = new TurnHistory(lPlays);
		
		CardGroup lResult = aRobot.chooseCards(lHistory, lRefHand);
		
		assertNotNull(lResult);
		assertTrue(lResult.equals(new CardGroup(new Card[]{AllCards.a2C, AllCards.a2D})) ||
				   lResult.equals(new CardGroup(new Card[]{AllCards.a2C, AllCards.a2S})) ||
				   lResult.equals(new CardGroup(new Card[]{AllCards.a2D, AllCards.a2S})) ||
				   lResult.equals(new CardGroup(new Card[]{AllCards.aJC, AllCards.aJD, AllCards.aJS})));
		assertEquals(lResult, aRobot.chooseCards(lHistory, lRefHand));
	}
	
	@Test
	public void testRobot2()
	{
		Hand lRefHand = new Hand();
		
		CardGroup lCanPlay  = new CardGroup(new Card[]{AllCards.aTC, AllCards.aTD, AllCards.aTH});
		CardGroup lCantPlay = new CardGroup(new Card[]{AllCards.a2C, AllCards.a2D, AllCards.a2H});
		
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.a2D);
		lRefHand.add(AllCards.a2S);
		lRefHand.add(AllCards.a3H);
		lRefHand.add(AllCards.a5C);
		lRefHand.add(AllCards.a7D);
		lRefHand.add(AllCards.a7C);
		lRefHand.add(AllCards.aTD);
		lRefHand.add(AllCards.aJC);
		lRefHand.add(AllCards.aJD);
		lRefHand.add(AllCards.aJS);
		
		// Cannot make a move
		Stack<Play> lPlays = new Stack<Play>();
		lPlays.push(new Play("dkawry", lCanPlay));
		TurnHistory lHistory = new TurnHistory(lPlays);
		CardGroup lResult;
		
		lPlays.clear();
		lPlays.add(new Play("dkawry", lCantPlay));
		lHistory = new TurnHistory(lPlays);
		
		lResult = aRobot.chooseCards(lHistory, lRefHand);
		assertTrue(lResult.size() == 0);
	}
	
	@Test
	public void testRobot() 
	{
		Hand lRefHand = new Hand();
		
		CardGroup lCanPlay  = new CardGroup(new Card[]{AllCards.aTC, AllCards.aTD, AllCards.aTH});
		
		lRefHand.add(AllCards.a2C);
		lRefHand.add(AllCards.a2D);
		lRefHand.add(AllCards.a2S);
		lRefHand.add(AllCards.a3H);
		lRefHand.add(AllCards.a5C);
		lRefHand.add(AllCards.a7D);
		lRefHand.add(AllCards.a7C);
		lRefHand.add(AllCards.aTD);
		lRefHand.add(AllCards.aJC);
		lRefHand.add(AllCards.aJD);
		lRefHand.add(AllCards.aJS);
		
		Stack<Play> lPlays = new Stack<Play>();
		lPlays.push(new Play("dkawry", lCanPlay));
		TurnHistory lHistory = new TurnHistory(lPlays);
		CardGroup lResult;
		
		// Must make any move
		lPlays.clear();
		lHistory = new TurnHistory(lPlays);
		lResult = aRobot.chooseCards(lHistory, lRefHand);
		assertNotNull(lResult);
		assertTrue(lResult.size() > 0);
		assertFalse(lResult.isJoker());
		
		Rank lRank = lResult.iterator().next().getRank();
		assertTrue(lRank.equals(Rank.TWO) || lRank.equals(Rank.THREE) || lRank.equals(Rank.FIVE)
				 ||lRank.equals(Rank.SEVEN)|| lRank.equals(Rank.TEN) || lRank.equals(Rank.JACK));
		
	}
}
