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
import static tests.AllCards.aAS;
import static tests.AllCards.aJC;
import static tests.AllCards.aJH;
import static tests.AllCards.aKH;
import static tests.AllCards.aKS;
import static tests.AllCards.aKC;
import static tests.AllCards.aKD;
import static tests.AllCards.aTC;
import static tests.AllCards.aJOKER;

import org.junit.Test;
import org.junit.Before;

import util.Card;
import util.CardGroup;
import util.Card.Rank;
import util.Card.Suit;
import model.Hand;
import java.util.LinkedList;
import java.util.Iterator;

public class TestHand {
	
	LinkedList<Card> l;
	LinkedList<Card> l1;
	LinkedList<Card> l2;
	
	Hand h1;
	Hand h2;
	Hand h3;
	Hand h4;
	
	CardGroup cg;
	CardGroup cg1;
	CardGroup cg2;
	
	Card[] c;
	
	//@Before
	@Before public void initializeTestVariables(){
		
		//Test LinkedList 1
		l1 = new LinkedList<Card>();
		
		//Test LinkedList 2
		l2 = new LinkedList<Card>();
		l2.add(a4D);
		l2.add(a4H);
		l2.add(aJH);
		
		//Test Hand 1
		h1 = new Hand();
		
		//Test Hand 2
		h2 = new Hand();
		h2.add(a4D);
		h2.add(a4H);
		h2.add(aJH);
		
		//Test Hand 3
		h3 = new Hand();
		h3.add(a3H);
		h3.add(a4D);
		h3.add(a4H);
		h3.add(aTC);
		h3.add(aJH);
		h3.add(aAS);
		
		//Test Hand 4
		h4 = new Hand();
		h4.add(a3H);
		h4.add(a4D);
		h4.add(a4H);
		h4.add(aTC);
		h4.add(aJH);
		h4.add(aJC);
		h4.add(aKD);
		h4.add(aKH);
		h4.add(aKS);
		h4.add(aKC);
		h4.add(aAS);
		h4.add(a2H);
		h4.add(aJOKER);
		
		//Test CardGroup 1
		Card[] ca1 = new Card[1];
		ca1[0] = new Card();	//A JOKER
		cg1 = new CardGroup(ca1);
		
		//Test CardGroup2
		Card[] ca2 = new Card[4];
		ca2[0] = aKH;
		ca2[1] = aKD;
		ca2[2] = aKS;
		ca2[3] = aKC;
		cg2 = new CardGroup(ca2);
	}	
	
	
	@Test
	public void testConstructor(){	//THINK OF A BETTER WAY TO TEST THE CONSTRUCTOR THAT DOESN'T DEPEND ON THE equals(LinkedList<Card>) method
		Hand h = new Hand();
		assertTrue(h.equals(l1));
	}
	
	@Test
	public void testAddRegularCardToEmptyHand(){
		h1.add(a4D);
		l1.add(a4D);
		assertTrue(h1.equals(l1));
	}

	@Test
	public void testAddJoker(){
		h1.add(aJOKER);
		l1.add(aJOKER);
		h2.add(aJOKER);
		l2.add(aJOKER);
		assertTrue(h1.equals(l1));
		assertTrue(h2.equals(l2));
	}
	
	@Test
	public void testAddLowestCard(){	//A card that is lower than any of the cards in the hand.
		h2.add(a3H);
		l2.addFirst(a3H);
		assertTrue(h2.equals(l2));
	}
	
	@Test
	public void testAddMiddleCard(){	//A card that is lower than some and higher than some of the cards in the hand.
		h2.add(a5C);
		l2.add(2, a5C);
		assertTrue(h2.equals(l2));
	}
	
	@Test
	public void testAddHighestCard(){	//A card that is higher than any of the cards in the hand.
		h2.add(a2C);
		l2.addLast(a2C);
		assertTrue(h2.equals(l2));
	}
	
	@Test
	public void testAddDuplicateCard(){	//A card that is higher than any of the cards in the hand.
		h2.add(aJH);
		l2.addLast(aJH);
		assertTrue(h2.equals(l2));
	}
	
	//SAFE TO ASSUME THAT THE ADD METHOD WORKS NOW. 
	
	@Test
	public void testSize(){
		assertEquals(h1.size(),0);
		assertEquals(h2.size(),3);
		assertEquals(h3.size(),6);
	}
	
	@Test
	public void testContains(){
		assertTrue(h3.contains(a3H));
		assertTrue(h3.contains(aTC));
		assertTrue(h3.contains(aAS));
		assertFalse(h3.contains(a2H));
		assertFalse(h3.contains(aJOKER));
		h3.add(aJOKER);
		assertTrue(h3.contains(aJOKER));
	}
	
	
	@Test
	public void testRemoveFromMultiplePlaces(){
		//Removing the lowest card
		assertTrue(h3.contains(a3H));
		h3.remove(a3H);
		assertFalse(h3.contains(a3H));
		assertEquals(h3.size(),5);
		
		//Removing a middle card
		assertTrue(h3.contains(aTC));
		h3.remove(aTC);
		assertFalse(h3.contains(aTC));
		assertEquals(h3.size(),4);
		
		//Removing the highest card
		assertTrue(h3.contains(aAS));
		h3.remove(aAS);
		assertFalse(h3.contains(aAS));
		assertEquals(h3.size(),3);	
		
		//Removing a joker
		h3.add(aJOKER);
		assertTrue(h3.contains(aJOKER));
		h3.remove(aJOKER);
		assertFalse(h3.contains(aJOKER));
		assertEquals(h3.size(),3);
	}
	
	@Test
	public void testRemoveDuplicate(){
		//Removing a duplicate card
		assertTrue(h3.contains(aTC));
		h3.add(aTC);
		assertEquals(h3.size(),7);
		h3.remove(aTC);
		assertEquals(h3.size(),6);
		assertTrue(h3.contains(aTC));	
	}
	
	@Test
	public void testClear(){
		assertEquals(h3.size(),6);
		h3.clear();
		assertEquals(h3.size(),0);
		//Making sure calling it multiple times still works
		h3.clear();
		assertEquals(h3.size(),0);
	}
	
	@Test
	public void testCanPlayOnJoker(){
		assertFalse(h3.canPlayOn(cg1));
		h3.add(aJOKER);
		assertFalse(h3.canPlayOn(cg1));
	}
	
	@Test
	public void testCanPlayUsingJoker(){
		c = new Card[4];
		c[0] = a2H;
		c[1] = a2H;
		c[2] = a2H;
		c[3] = a2H;
		assertFalse(h3.canPlayOn(new CardGroup(c)));
		h3.add(aJOKER);
		assertTrue(h3.canPlayOn(new CardGroup(c)));
	}

	@Test
	public void testCanPlayOnUsingTwos(){
		assertFalse(h3.canPlayOn(cg2));
		h3.add(a2H);
		h3.add(a2H);
		assertFalse(h3.canPlayOn(cg2));
		h3.add(a2H);
		assertTrue(h3.canPlayOn(cg2));
	}	
	
	@Test
	public void testCanPlayOnUsingRegularCards(){	//REGULAR CARDS != 2'S OF JOKERS
		c = new Card[1];
		c[0] = a2H;
		assertFalse(h3.canPlayOn(new CardGroup(c)));
		
		c[0] = aJC;
		assertTrue(h3.canPlayOn(new CardGroup(c)));
		
		c = new Card[2];
		c[0] = a3H;
		c[1] = a3H;
		assertTrue(h3.canPlayOn(new CardGroup(c)));
	}	
	
	@Test
	public void testCanPlayOnUsingSingleTonWithNoTwos(){
		c = new Card[1];
		c[0] = aJH;
		assertFalse(h2.canPlayOn(new CardGroup(c)));
	}
	
	//Testing PlayGroup
	@Test (expected=IllegalArgumentException.class)
	public void testPlayGroupWithCardsNotInHand(){
		l = new LinkedList<Card>();
		l.add(aKC);
		l.add(aKD);
		c = new Card[2];
		c[0] = aKC;
		c[1] = aKD;
		assertEquals(h3.playGroup(l),new CardGroup(c));
	}
		
	@Test (expected=IllegalArgumentException.class)
	public void testPlayGroupWithInvalidCardGroup1(){
		l = new LinkedList<Card>();
		l.add(aKC);
		l.add(a4D);
		c = new Card[2];
		c[0] = aKC;
		c[1] = a4D;
		assertEquals(h3.playGroup(l),new CardGroup(c));
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testPlayGroupWithInvalidCardGroup2(){
		l = new LinkedList<Card>();
		l.add(aJOKER);
		l.add(a4D);
		c = new Card[2];
		c[0] = aJOKER;
		c[1] = a4D;
		assertEquals(h3.playGroup(l),new CardGroup(c));
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testPlayGroupWithInvalidCardGroup3(){
		l = new LinkedList<Card>();
		l.add(a4D);
		l.add(aJOKER);
		c = new Card[2];
		c[0] = a4D;
		c[1] = aJOKER;
		assertEquals(h3.playGroup(l),new CardGroup(c));
	}
	
	@Test
	public void testPlayGroupValid1(){
		l = new LinkedList<Card>();
		l.add(a3H);
		c = new Card[1];
		c[0] = a3H;
		cg = new CardGroup(c);
		Iterator<Card> i1 = cg.iterator();
		Iterator<Card> i2 = h3.playGroup(l).iterator();
		for(int i=0;i<cg.size();i++){
			assertEquals(i1.next(),i2.next());
		}
		assertFalse(i2.hasNext());
		assertEquals(h3.size(),5);
	}
	
	@Test
	public void testPlayGroupValid2(){
		l = new LinkedList<Card>();
		l.add(a4H);
		l.add(a4D);
		c = new Card[2];
		c[0] = a4H;
		c[1] = a4D;
		cg = new CardGroup(c);
		Iterator<Card> i1 = cg.iterator();
		Iterator<Card> i2 = h3.playGroup(l).iterator();
		for(int i=0;i<cg.size();i++){
			assertEquals(i1.next(),i2.next());
		}
		assertFalse(i2.hasNext());
		assertEquals(h3.size(),4);
	}
	
	@Test
	public void testPlayGroupValid3(){
		h3.add(aJOKER);
		l = new LinkedList<Card>();
		l.add(aJOKER);
		c = new Card[1];
		c[0] = aJOKER;
		cg = new CardGroup(c);
		Iterator<Card> i1 = cg.iterator();
		Iterator<Card> i2 = h3.playGroup(l).iterator();
		for(int i=0;i<cg.size();i++){
			assertEquals(i1.next(),i2.next());
		}
		assertFalse(i2.hasNext());
		assertEquals(h3.size(),6);
	}
	
	
	@Test
	public void testCantPlay2OnAnother2(){
		Hand h = new Hand();
		h.add(a2C);
		h.add(a2H);
		h.add(aJOKER);
		CardGroup c = new CardGroup(a2C);
		System.out.println("The card group is : " + c.getRank());
		CardGroup[] choices = h.playChoices(c);
		System.out.println("playChoices size is : " + choices.length);
		assertEquals(null,choices[0].getRank());
		c = new CardGroup();
		choices = h.playChoices(c);
		assertEquals(4,choices.length);
	}
	
	//No need to test the helper method playRankChoices as testPlayChoices doesn't work unless testPlayChoices does too.
	
	@Test
	public void testPlayChoices(){

		c = new Card[1];
		c[0] = aJOKER;
		cg = new CardGroup(c);
		CardGroup[] cGA = h3.playChoices(cg);
		assertEquals(cGA.length,0);
		
		c = new Card[1];
		c[0] = new Card(Rank.THREE,Suit.DIAMONDS);
		cg = new CardGroup(c);
		cGA = h3.playChoices(cg);
		assertEquals(cGA.length,5);
		
		Card[] cA = {a4D};
		assertTrue(cGA[0].equals(new CardGroup(cA)));
		cA[0] = a4H;
		assertTrue(cGA[1].equals(new CardGroup(cA)));
		cA[0] = aTC;
		assertTrue(cGA[2].equals(new CardGroup(cA)));
		cA[0] = aJH;
		assertTrue(cGA[3].equals(new CardGroup(cA)));
		cA[0] = aAS;
		assertTrue(cGA[4].equals(new CardGroup(cA)));

		c = new Card[2];
		c[0] = a3H;
		c[1] = a3C;
		cg = new CardGroup(c);
		cGA = h4.playChoices(cg);
		
		assertEquals(10,cGA.length);
		
		cA = new Card[2];
		cA[0] = a4D;
		cA[1] = a4H;
		assertTrue(cGA[0].equals(new CardGroup(cA)));
		
		//MUST PUT THE CARDS INTO THE CARD GROUP AS THEY WILL BE FOUND IN THE HAND AS IT SORTS THEM BY ENUM ordering
		cA[0] = aJC;
		cA[1] = aJH;
		assertTrue(cGA[1].equals(new CardGroup(cA)));
		cA[0] = aKC;
		cA[1] = aKD;
		assertTrue(cGA[2].equals(new CardGroup(cA)));
		cA[0] = aKC;
		cA[1] = aKH;
		assertTrue(cGA[3].equals(new CardGroup(cA)));
		cA[0] = aKC;
		cA[1] = aKS;
		assertTrue(cGA[4].equals(new CardGroup(cA)));
		cA[0] = aKD;
		cA[1] = aKH;
		assertTrue(cGA[5].equals(new CardGroup(cA)));
		cA[0] = aKD;
		cA[1] = aKS;
		assertTrue(cGA[6].equals(new CardGroup(cA)));
		cA[0] = aKH;
		cA[1] = aKS;
		assertTrue(cGA[7].equals(new CardGroup(cA)));
		cA = new Card[1];
		cA[0] = a2H;
		assertTrue(cGA[8].equals(new CardGroup(cA)));
		cA[0] = aJOKER;
		assertTrue(cGA[9].equals(new CardGroup(cA)));
		
	}
	
	
	//TESTING FOUR HELPER METHODS
	
	//APPARENTLY WE ARE NOT REQUIRED TO TEST HELPER METHODS AS TESTING THE
	//REGULAR METHODS BASICALLY DOES THE SAME THING.
	
	/*
	@Test
	public void testCardsOfRank(){
		h3.add(aJOKER);
		assertEquals(1,h3.cardsOfRank(Rank.THREE));
		assertEquals(2,h3.cardsOfRank(Rank.FOUR));
		assertEquals(1,h3.cardsOfRank(Rank.ACE));
		assertEquals(1,h3.cardsOfRank(null));
		assertEquals(0,h3.cardsOfRank(Rank.SEVEN));
	}
	
	@Test
	public void testFirstIndexOfRank(){
		assertEquals(0,h4.firstIndexOfRank(Rank.THREE));
		assertEquals(12,h4.firstIndexOfRank(null));
		assertEquals(-1,h3.firstIndexOfRank(null));
		assertEquals(11,h4.firstIndexOfRank(Rank.TWO));
		assertEquals(-1,h4.firstIndexOfRank(Rank.SEVEN));
	}
	*/
	
	//Remember that the hand is sorted by its suit as well as its rank. so we cannot test them to be the
	//same order that they were added
	
	
	@Test
	public void testIterator(){
		Iterator<Card> i = h4.iterator();
		assertTrue(a3H.equals(i.next()));
		assertTrue(a4D.equals(i.next()));
		assertTrue(a4H.equals(i.next()));
		assertTrue(aTC.equals(i.next()));
		assertTrue(aJC.equals(i.next()));
		assertTrue(aJH.equals(i.next()));
		assertTrue(aKC.equals(i.next()));
		assertTrue(aKD.equals(i.next()));
		assertTrue(aKH.equals(i.next()));
		assertTrue(aKS.equals(i.next()));
		assertTrue(aAS.equals(i.next()));
		assertTrue(a2H.equals(i.next()));
		assertTrue(aJOKER.equals(i.next()));
		assertFalse(i.hasNext());
	}
	
	@Test
	public void testEquals(){
		LinkedList<Card> l = new LinkedList<Card>();
		l.add(a3H);
		l.add(a4D);
		l.add(a4H);
		l.add(aTC);
		l.add(aJC);
		l.add(aJH);
		l.add(aKC);
		l.add(aKD);
		l.add(aKH);
		l.add(aKS);
		l.add(aAS);
		l.add(a2H);
		assertFalse(h4.equals(l));
		l.addFirst(a3H);
		assertFalse(h4.equals(l));
		l.removeFirst();
		l.add(aJOKER);
		assertTrue(h4.equals(l));
	}	
}
