package gui;
	import java.awt.*;

	import javax.swing.*;

	import java.awt.event.*;
	import java.util.ArrayList;
	import java.util.Iterator;
	import java.util.Stack;

	import model.GameEngine;
	import model.Hand;
	import model.Status;
	//import model.Status;
	import util.Card;
	import util.CardGroup;
	import util.CardImages;
	// TODO remove these after test works
	import static tests.AllCards.a2C;
	import static tests.AllCards.a2S;
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
	import static tests.AllCards.aKH;
	import static tests.AllCards.aKS;
	import static tests.AllCards.aQD;
	import static tests.AllCards.aQH;
	import static tests.AllCards.aQC;
	import static tests.AllCards.aQS;
	import static tests.AllCards.aTC;
import static tests.AllCards.aJOKER;

	/*
	 * This class, as is the advantage view, are used as containers that provide
	 * methods to display the various panels required for proper human interaction with the game.
	 * this provides methods in which the returned panels allows the player to do nothing, and 
	 * another that allows the player to choose cards to beat the last play.
	 */
	public class HumanPlayerPanel {
		// humanHand is the hand of the player
		private Hand humanHand;
		// cardGroupTop is the CardGroup that the user is trying to beat
		private CardGroup cardGroupTop;
		private Stack<CardGroup> cardGroupStack;
		// ArrayList of Card objects that the user has in his hand
		private final ArrayList<Card> cardsInHand;
		// ArrayList of Card objects that the user has selected
		private final ArrayList<Card> selectedCards;
		// ArrayList of Label objects that the cardPane contains
		private final ArrayList<JLabel> list;
		// ArrayList of integers that store the x-axis of each label in the cardPanel
		private final ArrayList<Integer> intList;
		// ArrayList of Card objects that the user will submit into the gameEngine
		public final ArrayList<Card> submittedCards;

		// constructor creates empty Arraylits and Stack - it also assigns the Hand and CardGroup from params
		public HumanPlayerPanel(Hand humanHand, CardGroup cardGroupTop) {
			
			//if(cardGroupTop!=null)JOptionPane.showMessageDialog(null, cardGroupTop.size());
			this.humanHand = humanHand;
			this.cardGroupTop = cardGroupTop;
			cardGroupStack = new Stack<CardGroup>();
			cardsInHand = new ArrayList<Card>();
			selectedCards = new ArrayList<Card>();
			list = new ArrayList<JLabel>();
			intList = new ArrayList<Integer>();
			submittedCards = new ArrayList<Card>();

		}

		// this method will only display the cards without any button/mouse activation
		public JPanel displayCards() {
			cardsInHand.clear();
			// frame will contain all the labels and panels - the frame will be a grid
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
			// cardPanel will contain all the cards that the user has in his Hand
			final JLayeredPane cardPanel = new JLayeredPane();
			
			// submitPane will contain the cards that the user wants to submit after clicking on the card(s)
			final JLayeredPane submitPane = new JLayeredPane();
			// yourCardsLabel is the title heading for the cardPanel
			JLabel yourCardsLabel = new JLabel("Your cards");
			// submitLabel is the title heading for the submitPane
			JLabel submitLabel = new JLabel("Card(s) selected: ");
			// this is the origin that will be used to paint the cards onto the GUI
			final Point origin = new Point(0, 30);
			// submitPanel will be a BorderLayout that contains the submitLabel and the submitPane
			JPanel submitPanel = new JPanel();
			submitPanel.setLayout(new BorderLayout());
			// zDepth will be used to layer the cards in cardPanel
			int zDepth = 0;
			// obtain an iterator of the human hand
			Iterator<Card> cardIterator = humanHand.iterator();
			// the button Submit will send the cards in the submittedCards arrayList to the gameEngine
			final JButton submitButton = new JButton("Submit");
			// the button Pass will be used if the user wants to pass
			final JButton passButton = new JButton("Pass");
			// it is originally disabled
			submitButton.setEnabled(false);
			passButton.setEnabled(false);
			// change the iterator into an arrayList of Card objects called cardsInHand
			while (cardIterator.hasNext()) {
				Card card = cardIterator.next();
				cardsInHand.add(card);
			}
			// set up the labels and the objects
			for (Card curCard : cardsInHand) {
				// get the card image from the curCard
				final JLabel label = new JLabel(CardImages.getCard(curCard));
				// set the bounds of the label
				label.setBounds(origin.x, origin.y, 73, 97);
				// add the label to the cardPanel with the zDpeth
				cardPanel.add(label, new Integer(zDepth));
				origin.x+=15;
				zDepth++;
			}
			
			// set up submitPanel
			submitPanel.add(submitLabel, BorderLayout.NORTH);
			submitPanel.add(submitPane, BorderLayout.CENTER);
			submitPanel.add(submitButton,BorderLayout.SOUTH);

			JPanel humanHandPanel = new JPanel();
			humanHandPanel.setLayout(new BorderLayout());
			humanHandPanel.add(yourCardsLabel, BorderLayout.NORTH);
			humanHandPanel.add(cardPanel, BorderLayout.CENTER);
			humanHandPanel.add(passButton,BorderLayout.SOUTH);
			panel.add(humanHandPanel);
			JPanel empty1 = new JPanel();
			empty1.setPreferredSize(new Dimension(100,50));
			panel.add(empty1);
			panel.add(submitPanel);
			return panel;
		}
		
		public final void submitButtonAction(JButton submitButton, JButton passButton, JLayeredPane submitPane, JLayeredPane cardPanel) {
			// if statement is activated when there are some cards in submittedCards
			CardGroup cardGroup = new CardGroup();
			if (!(submittedCards.isEmpty())) {
				// create an array from the arrayList
				int numCards = cardsInHand.size();
				int i = submittedCards.size()-1;
				Card[] cardArray = new Card[submittedCards.size()];
				for (Card c : submittedCards) {
					cardArray[i] = c;
					cardsInHand.remove(c);
					/*
					System.out.println(c.toString()); */
					i--;
				}
				// create a CardGroup from this array
				cardGroup = new CardGroup(cardArray);
				submitButton.setEnabled(false);
				passButton.setEnabled(false);
				// now paint the cardPanel and the submitPane
				// paint the submitPane blank
				submitPane.removeAll();
				Point point = new Point(0,30);
				// this first part will clear out the cards on the pane
				// this will be done by creating a layer of blank JLabels
				for (int k = 0; k < 8; k++) {
					JLabel blankLabel = new JLabel();
					blankLabel.setBounds(point.x, point.y, 73, 97);
					submitPane.add(blankLabel, new Integer(k));
					point.x += 15;
				}
				// now update/paint the cards in the human hand
				int zAxis = 0;
				Point newPoint = new Point(0,30);
				int numberOfCards = 0;
				cardPanel.removeAll();
				for (Card curCard : cardsInHand) {
						// use the if statement to obtain cards that are not part of the submitted cards
						// get the card image from the curCard
						final JLabel label = new JLabel(CardImages.getCard(curCard));
						// set the bounds of the label
						label.setBounds(newPoint.x, newPoint.y, 73, 97);
						// add the label to the cardPanel with the zDpeth
						cardPanel.add(label, new Integer(zAxis));
						newPoint.x += 15;
						numberOfCards++;
						zAxis++;
				}
				for (int l = numberOfCards; l < numCards; l++) {
					JLabel blank = new JLabel();
					blank.setBounds(newPoint.x, newPoint.y, 73, 97);
					cardPanel.add(blank, new Integer(zAxis));
					newPoint.x += 15;
					zAxis++;
				}
				// clear out all ArrayLists and Stack
				submittedCards.clear();
				selectedCards.clear();
				intList.clear();
				cardsInHand.clear();
				list.clear();
				cardGroupStack.clear();
			}
			Status.setPlayerCardGroup(cardGroup);
			Status.setPlayerChoseCards(true);
		}
		
		public final void passButtonAction(JButton submitButton, JButton passButton) {
			CardGroup pass = new CardGroup();
			// after this button has been pressed, disable the buttons
			submitButton.setEnabled(false);
			passButton.setEnabled(false);
			submittedCards.clear();
			intList.clear();
			cardsInHand.clear();
			list.clear();
			cardGroupStack.clear();
			Status.setPlayerCardGroup(pass);
			Status.setPlayerChoseCards(true);			
		}
		
		public final void hoverCardEffect(JLabel label, Point origin, JLayeredPane cardPanel){
			// clear out the ArrayList selectedCards
			selectedCards.clear();
			// search for the matching label to the one the mouse is hovering over
			if (!(cardGroupTop.contains(aJOKER))) {
			for (JLabel matchLabel : list) {
				int index = list.indexOf(matchLabel);
				int startIndex = index;
				// isMatch will only be true if the labels are a match
				boolean isMatch = false;
				// isPlayable is true if the label is a playable card
				boolean isPlayable = false;
				// isPlayable2 is true if the cardGroup (more than 2 cards) is a playable card
				boolean isPlayable2 = false;
				// check to see if there is a match to the label in the cardPanel
				if (matchLabel == label) {
					isMatch = true;
					boolean cannotPlay = false;
					// get the possiblePlays from the cardGroup that is in play
					CardGroup[] possiblePlays = humanHand.playChoices(cardGroupTop);
					if (cardGroupTop.isPass() && cardsInHand.get(index).getRank() == null) {
						label.setBounds(intList.get(index), origin.y-30, 73, 97);
						CardGroup cp = new CardGroup(cardsInHand.get(index));
						cardGroupStack.add(cp);
						selectedCards.add(cardsInHand.get(index));
						break;
					}
					else if (cardGroupTop.isPass()) {
						ArrayList<Card> rankList = new ArrayList<Card>();
						int rankMatch = 0;
						CardGroup cG = new CardGroup();
						for (int i = 0; i <= startIndex; i++) {
							Card c = cardsInHand.get(i);
							if (c.getRank() == cardsInHand.get(index).getRank()) {
								rankMatch++;
								rankList.add(c);
							}
						}
						// create cardGroup
						Card[] cardArrayGroup = new Card[rankMatch];
						int counter = 0;
						for (Card c : rankList) {
							cardArrayGroup[rankMatch - counter - 1] = c;
							selectedCards.add(c);
							counter++;
						}
						cG = new CardGroup(cardArrayGroup);
						cardGroupStack.add(cG);
						int cloneIndex = startIndex;
						while (cloneIndex != startIndex - rankMatch) {
							JLabel moveLabel = list.get(cloneIndex);
							moveLabel.setBounds(intList.get(cloneIndex), origin.y-30, 73, 97);
							cloneIndex--;
						}
						isPlayable = false;
						isMatch = false;
					}
					/*
					System.out.println(possiblePlays.length);
					*/
					if (!cardGroupTop.isPass()) { 
					if (cardGroupTop.getRank()== Card.Rank.TWO) {
						// if the current card is not a joker, then you cannot select the card
						if ((cardsInHand.get(list.indexOf(label)).getRank() != null)) {
							cannotPlay = true;
						}
					}
					}
					// case in which we are playing aganist a single TWO card
					// see if the single card (in which it's label matched) is a playable card
					if (!cardGroupTop.isPass()) {
						for (CardGroup cp : possiblePlays) {
						if ((cp.contains(cardsInHand.get(list.indexOf(label)))) && (cp.size() == 1) && !cannotPlay) {
							// if the playable cardGroup is a single two, then isMatch = false, but isPlayable  = true
							if (cardsInHand.get(index).getRank() == Card.Rank.TWO) {
								isMatch = false;
							}
							// if this is true, then add the cardGroup to the cardGroupStack 
							cardGroupStack.add(cp);
							// isPlayable is now true
							isPlayable = true;
							break;
						}
					}
					}
					// if the card is Playable, then shift the card up and add it to the selectedCards arrayList
					if (isPlayable) {
				label.setBounds(intList.get(list.indexOf(matchLabel)), origin.y-30, 73, 97);
					selectedCards.add(cardsInHand.get(index));
					isMatch = false;
					}
				}
					// if this is not the case, then reposition the cards to their original place
				else {
					matchLabel.setBounds(intList.get(list.indexOf(matchLabel)), origin.y, 73, 97);	
				}
				// this while loop is accessed only if the if there is a match of the labels from the first if statement
			    while (index-1 != -1 && isMatch) {
			    	// check to see if card is a joker
			    	if (cardsInHand.get(index).getRank() == null) {
			    		break;
			    	}
			    	// check to see if selectedCards is empty (case in which CardGroup at play is more than one card)
			    	if (selectedCards.isEmpty()) {
			    		// if it is empty then add the current card at index into it
			    		// this is in the case the player needs to play two or more cards, but not one card
			    		selectedCards.add(cardsInHand.get(index));
			    	}
			    	// check to see if the card on the left is a match in rank
					if (cardsInHand.get(index-1).getRank() == cardsInHand.get(index).getRank()) {
						// the subCardArray will contain the cards from selectedCards plus the card at index-1 from the cardsInHand
						selectedCards.add(cardsInHand.get(index-1));
						Card[] subCardArray = new Card[selectedCards.size()];
						int arrayIndex = 0;
						for (Card c : selectedCards) {
							subCardArray[arrayIndex] = c;
							arrayIndex++;
						}
						/*System.out.println(subCardArray.length);*/
						// get possiblePlays from the card that is in play
						CardGroup[] possiblePlays = humanHand.playChoices(cardGroupTop);
						/*
						for (CardGroup cp : possiblePlays) {
							Iterator<Card> iterator = cp.iterator();
							System.out.println("Cards in CardGroup");
							while (iterator.hasNext()) {
								System.out.println(iterator.next().toString());
							}
						}
						*/
						// check for a cardGroup that matches the cards in the subCardsArray
						for (CardGroup cp : possiblePlays) {
							int match = 0;
							int Passmatch = 0;
							ArrayList<Card> rankList = new ArrayList<Card>();
							int rankMatch = 0;
							for (Card c : subCardArray) {
								if (cp.contains(c)) {
									match++;
								}
							}
							if (match == cp.size() && cp.size() == subCardArray.length) {
								/*
								System.out.println("this is a good play"); */
								// isPlayable2 becomes true
								isPlayable2 = true;
								// add the cardGroup to the stack
								cardGroupStack.add(cp);
								break;
							}
						}
						// if the cardGroup is playable, then shift the cards in subCardsArray up
						// clear the selectedCards and add the cards to selectedCards
						if (isPlayable2) {
							selectedCards.clear();
							int localIndex = startIndex;
							for (int i = 0; i < subCardArray.length; i++) {
							cardPanel.getComponent(list.size() - localIndex - 1).setBounds(intList.get(localIndex), origin.y-30, 73, 97);
							selectedCards.add(cardsInHand.get(localIndex));
							localIndex--;
							}
							isMatch = false;
						}
						// subtract index by 1 and isPlayable is re-set back to false
						index--;
						isPlayable2 = false;
					}
					else {
						isMatch = false;
					}
				}
				}
			}
		}
		// this method will create the cardPanel for the user
		public final JPanel createCardPanel() {
			// frame will contain all the labels and panels - the frame will be a grid
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
			//panel.setLayout(new BorderLayout());
			
			// cardPanel will contain all the cards that the user has in his Hand
			final JLayeredPane cardLayeredPane = new JLayeredPane();
			
			// submitPane will contain the cards that the user wants to submit after clicking on the card(s)
			final JLayeredPane submitLayeredPane = new JLayeredPane();
			
			// yourCardsLabel is the title heading for the cardPanel
			JLabel yourCardsLabel = new JLabel("Your cards");
			// submitLabel is the title heading for the submitPane
			JLabel submitLabel = new JLabel("Card(s) selected: ");
			// this is the origin that will be used to paint the cards onto the GUI
			final Point origin = new Point(0, 30);
			// submitPanel will be a BorderLayout that contains the submitLabel and the submitPane
			JPanel submitPanel = new JPanel();
			submitPanel.setOpaque(false);
			submitPanel.setLayout(new BorderLayout());
			// zDepth will be used to layer the cards in cardPanel
			int zDepth = 0;
			// obtain an iterator of the human hand
			Iterator<Card> cardIterator = humanHand.iterator();
			// the button Submit will send the cards in the submittedCards arrayList to the gameEngine
			final JButton submitButton = new JButton("Submit");
			submitButton.setEnabled(false);
			// the button Pass will be used if the user wants to pass
			
			final JButton passButton = new JButton("Pass");
		
			// add action listener to submitButton
			submitButton.addActionListener(new
					ActionListener()
			{
				public void actionPerformed(final ActionEvent event) {
					// call on the hoverCardsAction
					submitButtonAction(submitButton, passButton, submitLayeredPane, cardLayeredPane);
				}
			}
			);
			// the passButton will be used if the user wants to pass
			// it will be first activated when the method is called
			passButton.setEnabled(true);
			// add the action event to the pass button
			passButton.addActionListener(new
					ActionListener()
			{
				public void actionPerformed(final ActionEvent event) {
					passButtonAction(submitButton, passButton);
				}
			}
			);
			// change the iterator into an arrayList of Card objects called cardsInHand
			while (cardIterator.hasNext()) {
				Card card = cardIterator.next();
				cardsInHand.add(card);
			}
			// set up the labels and the objects
			
			for (Card curCard : cardsInHand) {
				//JOptionPane.showMessageDialog(null, "in the loop");
				// get the card image from the curCard
				final JLabel label = new JLabel(CardImages.getCard(curCard));
				// set the bounds of the label
				label.setBounds(origin.x, origin.y, 73, 97);
				// add the label's x-axis and label to the appropiate arrayLists
				intList.add(origin.x);
				list.add(label);
				// add the label to the cardPanel with the zDpeth
				cardLayeredPane.add(label, new Integer(zDepth));
				// now add mouse events
				// add a mouse entered event - this will all the cards to shift up if the card can by played
				label.addMouseListener(new
						MouseAdapter()
					{
						public void mouseEntered(final MouseEvent event)
						{
							hoverCardEffect(label, origin, cardLayeredPane);
							}
						}
						);
				// add a mouseExited event that shifts all the cards in the GUI back to their original place
				label.addMouseListener(new
						MouseAdapter()
					{
						public void mouseExited(final MouseEvent event)
						{
							selectedCards.clear();
							for (JLabel matchLabel : list) {
								matchLabel.setBounds(intList.get(list.indexOf(matchLabel)), origin.y, 73, 97);
								}
							// clear the stack
							cardGroupStack.clear();
							}
						}
						);
				label.addMouseListener(new
						MouseAdapter()
					{
						public void mouseClicked(final MouseEvent event)
						{
							if (cardGroupStack.size() != 0 && selectedCards.size() != 0) {
							int j = 0;
							int matchCards = 0;
							// check for the number of matchedCards in the cardGroup in the cardGroupStack
							for (Card c : selectedCards) {
								if (cardGroupStack.peek().contains(c)) {
									matchCards++;
								}
								j++;
							}
							// if the cards in selectedCards are all a match to the cardGroup and cardGroup.size() is equal to
							// selectedCards.size(), then the submitButton is enabeled and the cards are displayed onto the submitPane
							if (matchCards == cardGroupStack.peek().size() && cardGroupStack.peek().size()== selectedCards.size()) {
								submitButton.setEnabled(true);
							// display the cards in the "Selected Cards" field
							// this will allow the cards to appear over the previous selected cards
							submitLayeredPane.removeAll();
							int zDep = 0;
							Point point = new Point(0,30);
							// this first part will clear out the cards on the pane
							submittedCards.clear();
							for (int i = 0; i < 8; i++) {
								JLabel blankLabel = new JLabel();
								blankLabel.setBounds(point.x, point.y, 73, 97);
								submitLayeredPane.add(blankLabel, new Integer(zDep));
								zDep++;
								point.x += 15;
							}
							zDep = 0;
							point = new Point(0,30);
							// this for loop will show the cards that the user clicked on
							// add the cards from selectedCards to submittedCards
							for (Card c : selectedCards) {
								submittedCards.add(c);
								JLabel displayLabel = new JLabel(CardImages.getCard(c));
								displayLabel.setBounds(point.x, point.y, 73, 97);
								submitLayeredPane.add(displayLabel, new Integer(zDep));
								zDep++;
								point.x += 15;
								}

							cardGroupStack.clear();
							}
							}
						}
					}
						);
				zDepth++;
				origin.x += 15;
			}
			
			// set up submitPanel
			submitPanel.add(submitLabel, BorderLayout.NORTH);
			submitPanel.add(submitLayeredPane, BorderLayout.CENTER);
			submitLayeredPane.setBounds(submitLayeredPane.getX() + 100, submitLayeredPane.getY(), submitLayeredPane.getWidth(), submitLayeredPane.getHeight());
			submitPanel.add(submitButton,BorderLayout.SOUTH);

			JPanel humanHandPanel = new JPanel();
			humanHandPanel.setOpaque(false);
			humanHandPanel.setPreferredSize(cardLayeredPane.getSize());
			humanHandPanel.setLayout(new BorderLayout());
			humanHandPanel.add(yourCardsLabel, BorderLayout.NORTH);
			humanHandPanel.add(cardLayeredPane, BorderLayout.CENTER);
			humanHandPanel.add(passButton,BorderLayout.SOUTH);
			
			panel.add(humanHandPanel);
			JPanel empty1 = new JPanel();
			empty1.setOpaque(false);
			empty1.setPreferredSize(new Dimension(25,50));
			panel.add(empty1);
			panel.add(submitPanel);
			return panel;
		}
		
		
		/*
		 public static void main(final String[] args) {
		   
			Card cardOnTop = new Card(Card.Rank.THREE, Card.Suit.SPADES);
			Stack<CardGroup> cardGroupStack = new Stack<CardGroup>();
			Card cardOnTop2 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
			Card cardOnTop3 = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);
			Card two = a2C;
			Card six = new Card(Card.Rank.SIX, Card.Suit.CLUBS);
			Card[] cardOnTopArray = {six, six};
			CardGroup cardGroupTop = new CardGroup(six);
			Hand hand = new Hand();
			hand.add(aJC);
			hand.add(aJH); 
			hand.add(aJH);
			HumanPlayerPanel hp = new HumanPlayerPanel(hand, cardGroupTop);
			JFrame frame = new JFrame();
			frame.add(hp.createCardPanel());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(300,150));
			frame.validate();
			//frame.pack();
			frame.setVisible(true);
		} 
		 */
	}