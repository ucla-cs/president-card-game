package tests;

import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

import robots.BasicAI;
import model.TurnHistory;
import model.Hand;
import model.Play;
import util.Card;
import util.Card.Suit;
import util.Card.Rank;
import util.CardGroup;

//C,D,H,S

import static tests.AllCards.a3C;
import static tests.AllCards.a3H;
import static tests.AllCards.a4D;
import static tests.AllCards.a4H;
import static tests.AllCards.a4S;
import static tests.AllCards.a7C;
import static tests.AllCards.aTH;
import static tests.AllCards.aTS;
import static tests.AllCards.aQC;
import static tests.AllCards.aQD;
import static tests.AllCards.aKC;
import static tests.AllCards.aKS;
import static tests.AllCards.a2C;
import static tests.AllCards.a2D;
import static tests.AllCards.a2H;
import static tests.AllCards.a2S;
import static tests.AllCards.aJOKER;

public class TestBasicAI {
	
	Card jok;
	
	Hand h;
	Hand h1;
	
	Card[] ca1 = {aJOKER};
	Card[] ca2 = {a3C,a4D,a7C,aQC};	
	
	CardGroup c;
	
	CardGroup cg1;
	
	Stack<Play> passStack;
	Stack<Play> s;
	
	TurnHistory th;
	TurnHistory th1;
	
	@Before
	public void setup(){
		jok = new Card();
		
		//an empty hand to ass things to in more specific methods.
		h = new Hand();
		
		
		h1 = new Hand();
		h1.add(a3C);
		h1.add(a3H);
		h1.add(a4D);
		h1.add(a4S);
		h1.add(a7C);
		h1.add(aTS);
		h1.add(a2C);
		h1.add(a2S);
		
		cg1 = new CardGroup(ca1);	//A joker
		
		//passStack = new 
		
		s = new Stack<Play>();
		s.add(new Play("anotherplayer",cg1));
		th1 = new TurnHistory(s);
	}

	
	@Test
	public void testCanPlayOnNewRound(){
		s.clear();
		th1 = new TurnHistory(s);
		CardGroup CG = new BasicAI("AI").chooseCards(th1, h1);
		assertEquals(1,CG.size());
		assertEquals(Rank.SEVEN,CG.getRank());
	}
	
	@Test
	public void testCantPlayOnJokerOrTwo(){		
		assertEquals(0,new BasicAI("AI").chooseCards(th1, h1).size());
		s.clear();
		s.add(new Play("anotherplayer",new CardGroup(a2C)));
		th1 = new TurnHistory(s);
		assertEquals(0,new BasicAI("AI").chooseCards(th1, h1).size());	
	}
	
	@Test
	public void testCanPlayAnythingButChoosesSingleton(){
		th1 = new TurnHistory(new Stack<Play>());
		CardGroup CG = new BasicAI("AI").chooseCards(th1, h1);
		assertEquals(1,CG.size());
		assertEquals(Rank.SEVEN,CG.getRank());
	}
	
	@Test
	public void testBeatSingletonWithoutBreakingPair(){
		s.clear();
		s.add(new Play("SOMEONE",new CardGroup(a3H)));
		th1 = new TurnHistory(new Stack<Play>());
		CardGroup CG = new BasicAI("AI").chooseCards(th1, h1);
		assertEquals(1,CG.size());
		assertEquals(Rank.SEVEN,CG.getRank());
	}
	
	@Test
	public void testBeatPairWithoutBreakingTriple(){
		s.clear();
		Card[] c = {a3C,a3H};
		s.add(new Play("SOMEONE",new CardGroup(c)));
		h1.add(a4D);
		h1.add(aTH);
		th1 = new TurnHistory(s);
		CardGroup CG = new BasicAI("AI").chooseCards(th1, h1);
		assertEquals(2,CG.size());
		assertEquals(Rank.FOUR,CG.getRank());
	}
}
