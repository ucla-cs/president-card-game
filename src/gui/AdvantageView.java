package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import model.Status;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.Card;
import util.CardImages;

import model.Hand;

/*
 * This class is used to create and return the JPanel that is used to allow a human to select the card(s)
 * they wish to get rid of.
 * @pre : the player represented by the HUmanPlayerView object calling these mathods is indeed in an advanteaged position
 */
public class AdvantageView  {

	//private Player player;
	private int advantage;
	private final ArrayList<Card> cardsInHand;
	private final ArrayList<JLabel> list;
	private final ArrayList<Integer> intList;
	private final ArrayList<Card> selectedCard;
	private final ArrayList<Card> submitCard;
	private final ArrayList<JLabel> selectedLabels;
	private final ArrayList<JLabel> submitLabels;
	
	//initialize all the lists used in the following methods
	public AdvantageView(int advantage) {
		//this.player = player;
		this.advantage = advantage;
		cardsInHand = new ArrayList<Card>();
		list = new ArrayList<JLabel>();
		intList = new ArrayList<Integer>();
		selectedCard = new ArrayList<Card>();
		submitCard = new ArrayList<Card>();
		selectedLabels = new ArrayList<JLabel>();
		submitLabels = new ArrayList<JLabel>();
	}
	
	/*
	 * The action performed for when the submit button has been pressed.
	 * Repaints the cards in the passed JLayeredPane to represent the cards that were removed
	 */
	public final void submitButtonAction(JButton submitButton, JLayeredPane submitPane, JLayeredPane cardPanel) {
		// submitButton is activated when there are some cards in submittedCards
			submitButton.setEnabled(false);
			// now paint the cardPanel and the submitPane
			// paint the submitPane blank
			submitPane.removeAll();
			Point point = new Point(0,30);
			
			// this first part will clear out the cards on the pane
			// this will be done by creating a layer of blank JLabels
				JLabel blankLabel1 = new JLabel();
				blankLabel1.setBounds(point.x, point.y, 73, 97);
				submitPane.add(blankLabel1, new Integer(0));
				JLabel blankLabel2 = new JLabel();
				blankLabel2.setBounds(point.x+15,point.y, 73, 97);
				submitPane.add(blankLabel2, new Integer(1));
				
			// now update/paint the cards in the human hand
			int zAxis = 0;
			Point newPoint = new Point(0,30);
			int numberOfCards = 0;
			cardPanel.removeAll();
			int numCards = cardsInHand.size();
			for (Card c: submitCard) {
				cardsInHand.remove(c);	
			}
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

			Status.setCardsToDiscardAdv(submitCard);
			Status.setPlayerChoseCards(true);
		}
	
	/*
	 * Code to allow for a single card at a time to hover up, indicating that is will be selected if clicked.
	 */
	private void hoverEffect (JLabel label, Point origin, JLayeredPane cardPane) {
		selectedCard.clear();
		for (JLabel matchLabel : list) {
			// if the label has been found, then shift it up and add the card to selectedCard
			int index = list.indexOf(matchLabel);
			if (matchLabel == label) {
				label.setBounds(intList.get(index), origin.y-30, 73, 97);
				selectedCard.add(cardsInHand.get(index));
				break;
			}
		}
	}
	
	private void hoverEffect2 (JLabel label, Point origin, JLayeredPane cardPane) {
		for (JLabel matchLabel : list) {
			// if the label has been found, then shift it up and add the card to selectedCard
			int index = list.indexOf(matchLabel);
			boolean isActivated = true;
			if (submitLabels.size() == 1 && submitLabels.get(0) == label) {
				isActivated = false;
			}
			if (submitLabels.size() == 2 && submitLabels.get(1) == label) {
				isActivated = false;
			}
			if (matchLabel == label && isActivated) {
				selectedCard.clear();
				selectedLabels.clear();
				selectedCard.add(cardsInHand.get(index));
				selectedLabels.add(label);
				label.setBounds(intList.get(index), origin.y-30, 73, 97);
			}
		}
	}
	
	//selects the card and sets it as a possible card that will be gotten rid of
	private void selectCard (JLabel label, JLayeredPane submitPane, JButton submitButton) {
		submitCard.clear();
		submitButton.setEnabled(true);
		int index = list.indexOf(label);
		if (cardsInHand.get(index).equals(selectedCard.get(0))) {
			submitCard.add(selectedCard.get(0));
			submitPane.removeAll();
			int zDep = 0;
			Point point = new Point(0,30);
			JLabel blankLabel = new JLabel();
			blankLabel.setBounds(point.x, point.y, 73, 97);
			submitPane.add(blankLabel, new Integer(zDep));
			JLabel displayLabel = new JLabel(CardImages.getCard(submitCard.get(0)));
			displayLabel.setBounds(point.x, point.y, 73, 97);
			submitPane.add(displayLabel, new Integer(zDep));
		}
	}
	
	private void selectCard2 (JLabel label, JLayeredPane submitPane, JButton submitButton) {
		int index = list.indexOf(label);
		boolean isValid = true;
		if (label.getY() == 0) {
			
			// case in which there are no cards in the submitCard array
			if (submitCard.size() == 0) 
			{				
				// if this is the case then just add it to the appropiate linked lists
				submitCard.add(selectedCard.get(0));
				submitLabels.add(selectedLabels.get(0));
				label.setBounds(intList.get(index), 30, 73, 97);
				isValid = false;
			} 
			else if (submitCard.size() == 1 && submitLabels.get(0) != selectedLabels.get(0)) 
			{
					submitCard.add(selectedCard.get(selectedCard.size()-1));
					submitLabels.add(selectedLabels.get(0));
					/* System.out.println("adding card " + selectedCard.get(0).toString() +" to submitCard for second time"); */
					label.setBounds(intList.get(index), 30, 73, 97);
			}
			else if (submitCard.size() == 2 && submitLabels.get(1) != selectedLabels.get(0)) 
			{
				JLabel previousLabel = submitLabels.get(1);
				Card previousCard = submitCard.get(1);
				submitCard.clear();
				submitLabels.clear();
				submitCard.add(previousCard);
				submitLabels.add(previousLabel);
				submitCard.add(selectedCard.get(0));
				submitLabels.add(selectedLabels.get(0));
				label.setBounds(intList.get(index), 30, 73, 97);
			}
			else 
			{
				isValid = false;
			}
			
			if (isValid) 
			{
			submitButton.setEnabled(true);
			submitPane.removeAll();
			int zDep = 0;
			Point point = new Point(0,30);
			JLabel displayLabel1 = new JLabel(CardImages.getCard(submitCard.get(0)));
			displayLabel1.setBounds(point.x, point.y, 73, 97);
			submitPane.add(displayLabel1, new Integer(zDep));
			JLabel displayLabel2 = new JLabel(CardImages.getCard(submitCard.get(1)));
			displayLabel2.setBounds(point.x+15, point.y, 73, 97);
			submitPane.add(displayLabel2, new Integer(zDep+1));
			}
		}
	}
	
	public JPanel advantage(Hand hand) {
		Card[] cardRemoved = new Card[advantage];
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		// cardPanel will contain all the cards that the user has in his Hand
		final JLayeredPane cardPanel = new JLayeredPane();
		// submitPane will contain the cards that the user wants to submit after clicking on the card(s)
		final JLayeredPane submitPane = new JLayeredPane();
		// yourCardsLabel is the title heading for the cardPanel
		JLabel yourCardsLabel = new JLabel("Advantage Time: Discard " + advantage + " card(s)");
		// submitLabel is the title heading for the submitPane
		JLabel submitLabel = new JLabel("Card selected for removal: ");
		// this is the origin that will be used to paint the cards onto the GUI
		final Point origin = new Point(0, 30);
		// submitPanel will be a BorderLayout that contains the submitLabel and the submitPane
		JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new BorderLayout());
		// zDepth will be used to layer the cards in cardPanel
		int zDepth = 0;
		// obtain an iterator of the human hand
		Iterator<Card> cardIterator = hand.iterator();
		
		JButton passButton = new JButton("Pass");	//just so that it mimicks the other user panels
		passButton.setEnabled(false);
		// the button Submit will send the cards in the submittedCards arrayList to the gameEngine
		final JButton submitButton = new JButton("Give Away");
		submitButton.setEnabled(false);
		submitButton.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(final ActionEvent event) {
				// call on the hoverCardsAction
				submitButtonAction(submitButton, submitPane, cardPanel);
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
			// get the card image from the curCard
			final JLabel label = new JLabel(CardImages.getCard(curCard));
			// set the bounds of the label
			label.setBounds(origin.x, origin.y, 73, 97);
			// add the label's x-axis and label to the appropiate arrayLists
			intList.add(origin.x);
			list.add(label);
			// add the label to the cardPanel with the zDpeth
			cardPanel.add(label, new Integer(zDepth));
			// add a mouse entered event - this will the to shift up if the card can by played
			label.addMouseListener(new
					MouseAdapter()
				{
					public void mouseEntered(final MouseEvent event)
					{
						if (advantage == 1) {
						hoverEffect(label, origin, cardPanel);
						}
						else {
							hoverEffect2(label, origin, cardPanel);
						}
						}
					}
					);
			label.addMouseListener(new
					MouseAdapter()
				{
					public void mouseExited(final MouseEvent event)
					{
						if (advantage == 1) {
						selectedCard.clear();
						}
						for (JLabel matchLabel : list) {
							matchLabel.setBounds(intList.get(list.indexOf(matchLabel)), origin.y, 73, 97);
							}
						}
					}
					);
			label.addMouseListener(new
					MouseAdapter()
				{
					public void mouseClicked(final MouseEvent event)
					{
						if (advantage == 1) {
							selectCard(label, submitPane, submitButton);
						}
						else {
							selectCard2(label, submitPane, submitButton);
						}
						}
					}
					);
			zDepth++;
			origin.x += 15;
		}
		
		//add all of the individual components to the final panel to be returned
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
}

