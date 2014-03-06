package gui;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.Deck.NumberOfJokers;
import util.Deck.NumberOfPacks;

import model.GameEngine;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import gui.titleIcon;
public class Settings {
	
	private static int numberOfJokers = 0;
	private static int numberOfDecks = 1;
	private static int numberOfPlayers = 3;
	private static int advantageNumber = 1;
	private static int points = 15;
	private static String playerName = "PLAYER";
	
	private static final JFrame frame = new JFrame("Select Your Settings");
	
	public static int getNumberOfJokers(){
		return numberOfJokers;
	}
	public static int getNumberOfDecks(){
		return numberOfDecks;
	}
	public static int getNumberOfPlayers(){
		return numberOfPlayers;
	}
	public static int getAdvantageNumber(){
		return advantageNumber;
	}
	public static int getPoints(){
		return points ;
	}
	public static String getPlayerName(){
		return playerName;
	}
	
	// constructor
	public Settings(){
		BufferedImage image = null;
        try {
           image = ImageIO.read(new File("src/images/Star.JPG"));
        } catch (IOException e) {
             e.printStackTrace();
        }
        frame.setIconImage(image);
	}

	
	public void settingsIcon() {
		// settingPanel will be used for the input values
		JPanel settingPanel = new JPanel();
		// frame will contain the settingPanel, a button, and all three images
		//final JFrame frame = new JFrame("Select Your Settings");
		// label and label2 will be used to store the icons of Obama and Palin
		JLabel label = new JLabel();
		JLabel label2 = new JLabel();
		ImageIcon obamaIcon = new ImageIcon("obama.jpg");
		ImageIcon palinIcon = new ImageIcon("palin.jpg");
		// label3 will be used to store the icon of the title header
		ImageIcon title = new ImageIcon("title.jpg");
		// set the icons into their labels
		JLabel label3 = new JLabel(title, JLabel.CENTER);
		label2.setIcon(palinIcon);
		label.setIcon(obamaIcon);
		// use a gridLayout for our panel
		settingPanel.setLayout(new GridLayout(6, 2));
		
		JLabel name = new JLabel("Your Name : ");
		
		settingPanel.add(name);
		final JTextField nameBox = new JTextField();
		nameBox.setText("HUMAN PLAYER");
		nameBox.setSize(30, 30);
		nameBox.setSelectionStart(0);
		nameBox.setSelectionEnd(12);
		
		settingPanel.add(nameBox);
		// radiobuttons for the number of CPUs
		settingPanel.add(new JLabel(" How many CPU players?"));
		final JRadioButton player3 = new JRadioButton("3");
		player3.setActionCommand("3");
		player3.setSelected(true);
		player3.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String playerNumber = event.getActionCommand();
						numberOfPlayers = (Integer.valueOf(playerNumber)).intValue(); 
					}
				}
				);
		final JRadioButton player4 = new JRadioButton("4");
		player4.setActionCommand("4");
		player4.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String playerNumber = event.getActionCommand();
						numberOfPlayers = (Integer.valueOf(playerNumber)).intValue(); 
					}
				}
				);
		final JRadioButton player5 = new JRadioButton("5");
		player5.setActionCommand("5");
		player5.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String playerNumber = event.getActionCommand();
						numberOfPlayers = (Integer.valueOf(playerNumber)).intValue(); 
					}
				}
				);
		final JRadioButton player6 = new JRadioButton("6");
		player6.setActionCommand("6");
		player6.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String playerNumber = event.getActionCommand();
						numberOfPlayers = (Integer.valueOf(playerNumber)).intValue(); 
					}
				}
				);
		final JRadioButton player7 = new JRadioButton("7");
		player7.setActionCommand("7");
		player7.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String playerNumber = event.getActionCommand();
						numberOfPlayers = (Integer.valueOf(playerNumber)).intValue(); 
					}
				}
				);
		ButtonGroup playerGroup = new ButtonGroup();
		playerGroup.add(player3);
		playerGroup.add(player4);
		playerGroup.add(player5);
		playerGroup.add(player6);
		playerGroup.add(player7);
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(1, 5));
		playerPanel.add(player3);
		playerPanel.add(player4);
		playerPanel.add(player5);
		playerPanel.add(player6);
		playerPanel.add(player7);
		settingPanel.add(playerPanel);
		// radiobuttons for number of decks
		settingPanel.add(new JLabel(" How many decks do you want to play with?"));
		final JRadioButton deck1 = new JRadioButton("1");
		deck1.setActionCommand("1");
		deck1.setSelected(true);
		deck1.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String deckNumber = event.getActionCommand();
						numberOfDecks = (Integer.valueOf(deckNumber)).intValue(); 
					}
				}
				);
		final JRadioButton deck2 = new JRadioButton("2");
		deck2.setActionCommand("2");
		deck2.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String deckNumber = event.getActionCommand();
						numberOfDecks = (Integer.valueOf(deckNumber)).intValue(); 
					}
				}
				);
		JPanel deckPanel = new JPanel();
		deckPanel.setLayout(new GridLayout(1,2));
		deckPanel.add(deck1);
		deckPanel.add(deck2);
		ButtonGroup deckGroup = new ButtonGroup();
		deckGroup.add(deck1);
		deckGroup.add(deck2);
		settingPanel.add(deckPanel);
		// radiobuttons for number of Jokers
		settingPanel.add(new JLabel(" How many jokers per deck?"));
		final JRadioButton joker0 = new JRadioButton("0");
		joker0.setActionCommand("0");
		joker0.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String jokerNumber = event.getActionCommand();
						numberOfJokers = (Integer.valueOf(jokerNumber)).intValue(); 
					}
				}
				);
		joker0.setSelected(true);
		final JRadioButton joker1 = new JRadioButton("1");
		joker1.setActionCommand("1");
		joker1.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String jokerNumber = event.getActionCommand();
						numberOfJokers = (Integer.valueOf(jokerNumber)).intValue(); 
					}
				}
				);
		final JRadioButton joker2 = new JRadioButton("2");
		joker2.setActionCommand("2");
		joker2.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String jokerNumber = event.getActionCommand();
						numberOfJokers = (Integer.valueOf(jokerNumber)).intValue(); 
					}
				}
				);
		JPanel jokerPanel = new JPanel();
		jokerPanel.setLayout(new GridLayout(1,3));
		jokerPanel.add(joker0);
		jokerPanel.add(joker1);
		jokerPanel.add(joker2);
		ButtonGroup jokerGroup = new ButtonGroup();
		jokerGroup.add(joker0);
		jokerGroup.add(joker1);
		jokerGroup.add(joker2);
		settingPanel.add(jokerPanel);
		// set up slide for number of points
		settingPanel.add(new JLabel(" How many points do you want to play until?"));
		final JSlider pointSlide = new JSlider(JSlider.HORIZONTAL, 3, 100, 15);
		JPanel pointPanel = new JPanel();
		pointPanel.setLayout(new GridLayout(2,1));
		final JLabel display = new JLabel();
		display.setText("Points: 15");
		pointSlide.addChangeListener(new
				ChangeListener()
				{
					public void stateChanged(ChangeEvent e) {
						points = pointSlide.getValue();
						Integer i = points;
						display.setText("Points: " + i.toString());
					}
				}
				);
		pointSlide.setPaintTicks(true);
		pointSlide.setPaintLabels(true);
		pointPanel.add(display);
		pointPanel.add(pointSlide);
		settingPanel.add(pointPanel);
		// set up radiobuttons for advantage
		settingPanel.add(new JLabel(" Please choose an advantage: "));
		final JRadioButton adv1 = new JRadioButton("1");
		adv1.setActionCommand("1");
		adv1.setSelected(true);
		adv1.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String advNumber = event.getActionCommand();
						advantageNumber = (Integer.valueOf(advNumber)).intValue(); 
					}
				}
				);
		final JRadioButton adv2 = new JRadioButton("2");
		adv2.setActionCommand("2");
		adv2.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String advNumber = event.getActionCommand();
						advantageNumber = (Integer.valueOf(advNumber)).intValue(); 
					}
				}
				);
		JPanel advPanel = new JPanel();
		advPanel.setLayout(new GridLayout(1,2));
		advPanel.add(adv1);
		advPanel.add(adv2);
		ButtonGroup advGroup = new ButtonGroup();
		advGroup.add(adv1);
		advGroup.add(adv2);
		settingPanel.add(advPanel);
		// this will be our button
		JButton setButton = new JButton("Click Here Once Settings Are Ok");
		JButton backButton = new JButton("Go Back");
		setButton.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{		
						playerName = nameBox.getText();
						
						
						frame.setVisible(false);
						GameView.setDone(true);
					}
				}
				);
		backButton.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				frame.setVisible(false);
				titleIcon icon = new titleIcon();
				icon.title();
			}
		}
		);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(backButton);
		buttonPanel.add(setButton);
		// use a border layout for the frame
		frame.setLayout(new BorderLayout());
		frame.add(label3, BorderLayout.NORTH);
		frame.add(settingPanel, BorderLayout.CENTER);
		frame.add(label, BorderLayout.WEST);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.add(label2, BorderLayout.EAST);
		// set the background color
		frame.getContentPane().setBackground(new Color(76, 143, 195));
		// set the main thread and visibility
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	


}
