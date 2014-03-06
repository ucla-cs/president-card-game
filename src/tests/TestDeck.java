package tests;

import util.Card;
import util.Deck;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import util.EmptyDeckException;
import static tests.AllCards.aJOKER;

public class TestDeck {
	
	/*possible combinations to test out:
	 * A.) One Deck, No Jokers
	 * B.) Two Decks, No Jokers
	 * C.) One Deck, One Joker
	 * D.) Two Decks, Two Jokers (one per deck)
	 * E.) One Deck, Two Jokers
	 * F.) Two Decks, 4 Jokers (two per deck)
	 */
		private int index = 0;
		private int isJoker = 0;
		private Card card;
		
		
		// instanitate the deck objects according to the cases listed above
		private Deck deckA = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ZERO);
		private Deck deckB = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.ZERO);
		private Deck deckC = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ONE);
		private Deck deckD = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.ONE);
		private Deck deckE = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.TWO);
		private Deck deckF = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.TWO);
		

		// check to see if the appropiate number of cards exist in the decks
		@Test public void testDeckSizes() {
			assertEquals(52, deckA.size());
			assertEquals(104, deckB.size());
			assertEquals(53, deckC.size());
			assertEquals(106, deckD.size());
			assertEquals(54, deckE.size());
			assertEquals(108, deckF.size());
		}
		// Check to see if deck is shuffled properly (i.e. no mutations) 
		@Test public void testShuffle() {
		// First store the orginal deck sizes into integer variables
			int deckAOriginalSize = deckA.size();
			int deckBOriginalSize = deckB.size();
			int deckCOriginalSize = deckC.size();
			int deckDOriginalSize = deckD.size();
			int deckEOriginalSize = deckE.size();
			int deckFOriginalSize = deckF.size();
		// then shuffle the deck 
			deckA.shuffle();
			deckB.shuffle();
			deckC.shuffle();
			deckD.shuffle();
			deckE.shuffle();
			deckF.shuffle();
		// Check to see if the size has not been mutated (
		// if it is, then there is an error in the shuffle method code
			assertEquals(deckAOriginalSize, deckA.size());
			assertEquals(deckBOriginalSize, deckB.size());
			assertEquals(deckCOriginalSize, deckC.size());
			assertEquals(deckDOriginalSize, deckD.size());
			assertEquals(deckEOriginalSize, deckE.size());
			assertEquals(deckFOriginalSize, deckF.size());	
		}
		
		// check to see if the appropiate number of Jokers exist in the decks
		@Test public void testDecks() {
		// For case A
			for (index = 0; index < 52; index++) {
				card = deckA.draw();	
				if (card.equals(aJOKER)) {
					isJoker++;
				}
			}
			assertEquals(0, isJoker);
		// reset the isJoker variable (this will be done after every case
			isJoker = 0;
		// For case B
			for (index = 0; index < 104; index++) {
				card = deckB.draw();	
				if (card.equals(aJOKER)) {
					isJoker++;
				}
			}
			assertEquals(0, isJoker);
			isJoker = 0;
		// For case C
			for (index = 0; index < 53; index++) {
				card = deckC.draw();	
				if (card.equals(aJOKER)) {
					isJoker++;
				}
			}
			assertEquals(1, isJoker);
			isJoker = 0;
		// For case D
			for (index = 0; index < 106; index++) {
				card = deckD.draw();	
				if (card.equals(aJOKER)) {
					isJoker++;
				}
			}
			assertEquals(2, isJoker);
			isJoker = 0;
		// For case E
			for (index = 0; index < 54; index++) {
				card = deckE.draw();	
				if (card.equals(aJOKER)) {
					isJoker++;
				}
			}
			assertEquals(2, isJoker);
			isJoker = 0;
			// For case F
			for (index = 0; index < 108; index++) {
				card = deckF.draw();	
				if (card.equals(aJOKER)) {
					isJoker++;
				}
			}
			assertEquals(4, isJoker);
		}
	// Check to see if deck empties the deck properly
		@Test(expected=EmptyDeckException.class)
		public void testDraw() throws Exception {
			// refill the deck objects 
			deckA = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ZERO);
			deckB = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.ZERO);
			deckC = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ONE);
			deckD = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.ONE);
			deckE = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.TWO);
			deckF = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.TWO);
			// Empty the decks without throwing an EmptyDeckException
			// for Deck A:
			for (index = 0; index < 52; index++) {
				deckA.draw();	
			}
			assertEquals(0, deckA.size());
			// for Deck B:
			for (index = 0; index < 104; index++) {
				deckB.draw();	
			}
			assertEquals(0, deckB.size());
			// for Deck C:
			for (index = 0; index < 53; index++) {
				deckC.draw();	
			}
			assertEquals(0, deckC.size());
			// for Deck D:
			for (index = 0; index < 106; index++) {
				deckD.draw();	
			}
			assertEquals(0, deckD.size());
			// for Deck E:
			for (index = 0; index < 54; index++) {
				deckE.draw();	
			}
			assertEquals(0, deckE.size());
			// for Deck F:
			for (index = 0; index < 108; index++) {
				deckF.draw();	
			}
			assertEquals(0, deckF.size());			

			// refill the decks
			deckA = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ZERO);
			deckB = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.ZERO);
			deckC = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.ONE);
			deckD = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.ONE);
			deckE = new Deck(Deck.NumberOfPacks.ONE, Deck.NumberOfJokers.TWO);
			deckF = new Deck(Deck.NumberOfPacks.TWO, Deck.NumberOfJokers.TWO);
		// Empty the decks with an index that exceeds their size amount
		// For Deck A (we do not need to do this for the others since we care about
		// whether the exception is thrown or not for any case):
			for (index = 0; index <= 52; index++) {
				deckA.draw();	
			}
		}
}




