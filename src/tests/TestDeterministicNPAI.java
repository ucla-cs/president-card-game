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
import static tests.AllCards.aJD;
import static tests.AllCards.aJH;
import static tests.AllCards.aKD;
import static tests.AllCards.aKH;
import static tests.AllCards.aKS;
import static tests.AllCards.aQC;
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
import robots.DeterministicNPAI;
import java.util.Stack;

import model.Hand;
import model.Play;
import model.TurnHistory;

public class TestDeterministicNPAI {
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
	private Stack<Play> playlist = new Stack<Play>();
	
	
	@Test
	public void testCannotPlay(){
		
		Hand h = new Hand();
		playlist.push(playA);
		playlist.push(playB);
		playlist.push(playC);
		playlist.push(playD);
		TurnHistory history = new TurnHistory(playlist);
		DeterministicNPAI AI = new DeterministicNPAI("AI");
		assertEquals( AI.chooseCards(history, h ), null);
		
		h = new Hand();
		h.add(aJOKER);
		h.add(aQH);
		h.add(aQD);
		playlist.push(playA);
		playlist.push(playB);
		playlist.push(playC);
		playlist.push(playD);
		history = new TurnHistory(playlist);
		AI = new DeterministicNPAI("AI");
		assertEquals( AI.chooseCards(history, h ), null);
	}
	
	@Test
	public void testCanPlay(){
		
		Hand h = new Hand();
		h.add(aJOKER);
		h.add(aQH);
		h.add(aQD);
		h.add(aQC);
		
		playlist.push(playA);

		TurnHistory history = new TurnHistory(playlist);
		DeterministicNPAI AI = new DeterministicNPAI("AI");
		
		CardGroup g = new CardGroup(aQC);
		assertTrue( AI.chooseCards(history, h ).equals( g));

	}
	
	@Test
	public void testCanPlay1(){
		
		Hand h = new Hand();
		h.add(aJOKER);
		h.add(aQH);
		h.add(aQD);
		h.add(aQC);
		
		playlist.push(playC);

		TurnHistory history = new TurnHistory(playlist);
		DeterministicNPAI AI = new DeterministicNPAI("AI");
		
		CardGroup g = new CardGroup(aJOKER);
		assertTrue( AI.chooseCards(history, h ).equals( g));

	}
	
}
