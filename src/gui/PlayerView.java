package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import util.Card;
import util.CardGroup;
import util.Card.Rank;
import util.Card.Suit;

import model.Event;
import model.Observer;
import model.Player;
import model.Event.EVENT;

/*
 * A Player view is a representation of a player in their position
 * around the game table. It is usd for both human and AI controlled players alike.
 */

public class PlayerView implements Observer{
	private ImageIcon card = new ImageIcon("src/images/back_card.jpg");
	
	private JPanel cardPanel;
	private Border border;	
	private JLabel cardsRemaining;	
	private JLabel lastMove;	
	private JLayeredPane cardPane ;	
	private JLabel playerIconLabel;
	private ImageIcon icon;
	
	private Player player;
	
	// constructor for playerIcon
	public PlayerView(Player p) {
		
		player = p;
		
		cardPanel = new JPanel();
		cardPanel.setOpaque(false);
		border = new EmptyBorder(3,3,3,3);
		cardPanel.setBorder(border);
		cardPanel.setOpaque(false);

	}
		
	// creatIconLabel is a method that will create a JLabel backside card at the appropiate origin
	public JLabel createIconLabel(Point origin) {
		JLabel label = new JLabel(card);
		label.setBounds(origin.x, origin.y, 73, 97);
		return label;
	}
	
	public JPanel getCardPanel(){
		return cardPanel;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	/*
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		Player p = new Player("Darth Vader", "vader_icon.jpg","vader_icon.jpg");
		PlayerView view = new PlayerView(p);
		
		Event e = new Event(EVENT.newGame);
		
		//Player p = new Player("Darth Vader", "vader_icon.jpg","vader_icon.jpg");
		//p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		e.setPlayer(p);	
		
		
		view.update(e);
		
		frame.add(view.getCardPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(240, 180);
		frame.setVisible(true);
		
		long t0, t1;
		t0 = System.currentTimeMillis();
		do{
			t1=System.currentTimeMillis();
		}
		while(t1-t0<1000);
		
		e = new Event(EVENT.playCardGroup);
		
		//p = new Player("Darth Vader", "vader_icon.jpg","vader_icon.jpg");
		
		System.out.println("done");
		
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		p.addToHand(new Card(Rank.ACE,Suit.CLUBS));
		
		
		e.setPlayer(p);
		
		CardGroup g = new CardGroup(new Card(Rank.ACE,Suit.CLUBS));
		
		e.setCardGroup(g);
		
		view.update(e);
		
		System.out.println(p.getHand().size());
	}
	*/
	
	
	@Override
	public void update(Event e) throws ArrayIndexOutOfBoundsException{
		
		if(e.getEvent().equals(EVENT.newGame)){
			paintPlayer();
		}
		else if(e.getEvent().equals(EVENT.playCardGroup) && e.getPlayer().getName().equals(player.getName())){
			CardGroup cG = e.getCardGroup();
			lastMove.setText(player.getName() + (cG.size()==0?" passed":" played " + cG.size()+ " " +(cG.getRank()==null? "Joker" : cG.getRank().toString()) + (cG.size()==1? "":"\'s")));
			border = new EmptyBorder(3,3,3,3);
			updateView(e);
		}
		else if(e.getEvent().equals(EVENT.needAPlay) && e.getPlayer().getName().equals(player.getName())){
			lastMove.setText("Thinking...");
			if(player.getAI()==null){
				border = new LineBorder(Color.RED,3,true);
			}
			else{
				border = new LineBorder(Color.BLACK,3,true);
			}
			updateView(e);
		}
		else if(e.getEvent().equals(EVENT.deal)){ 
			e.setPlayer(player);
			updateView(e);
			lastMove.setText(player.getName());		//TODO this doesn't seem to work for some reason
		}
		else if(e.getEvent().equals(EVENT.playerOut)){
			lastMove.setText(player.getName() + " is out of cards");
		}
		else if(e.getEvent().equals(EVENT.turnOver)){
			//cardPanel = new JPanel();
			//paintPlayer();
		}
	}
	
	public void paintPlayer(){

		// playerPanel will be a JPanel containing the icon and the name of the player
		JPanel playerPanel = new JPanel();
		playerPanel.setOpaque(false);
		
		// cardPanel will be a JPanel containing the playerPanel and the cards
		cardPanel = new JPanel();
		cardPanel.setOpaque(false);
	
		
		// statPanel will be a GridLayout containing a previous move and a number of cards remaining text field
		JPanel statPanel = new JPanel();
		statPanel.setOpaque(false);
		statPanel.setLayout(new GridLayout(2,1));
		
		cardPanel.setLayout(new BorderLayout());
		
		playerPanel.setLayout(new BorderLayout());
		// set up the statsPanel
		// set up the number of cards remaining label
		cardsRemaining = new JLabel("Cards Remaining: "+player.getHand().size());
		cardsRemaining.setOpaque(false);
		lastMove = new JLabel( player.getName());
		lastMove.setOpaque(false);
		
		statPanel.add(cardsRemaining);
		statPanel.add(lastMove);
		// set up the player icon
		icon = new ImageIcon(player.getImageString());
		playerIconLabel = new JLabel(icon);
		// set up the player name
		//JLabel playerName = new JLabel(player.getName());
		// now set the cards up using a LayeredPane
		cardPane = new JLayeredPane();
		cardPane.setOpaque(false);
		// origin will be at 0,0
		Point origin = new Point(-15, -20);
		
		if(player.getHand().size()>13){
			for (int i = 0; i < 13; i++) {
				// this will create the card at the origin
				JLabel backCard = createIconLabel(origin);
				// this will set the z-axis and create the layering
				cardPane.add(backCard, new Integer(i));
				// add 15 pixels to the x-axis of the origin
	            origin.x += 15;
			}
		}
		else{
			for (int i = 0; i < player.getHand().size(); i++) {
				// this will create the card at the origin
				JLabel backCard = createIconLabel(origin);
				// this will set the z-axis and create the layering
				cardPane.add(backCard, new Integer(i));
				// add 15 pixels to the x-axis of the origin
	            origin.x += 15;
			}
		}
		// now add these labels to the playerPanel
		playerPanel.add(playerIconLabel, BorderLayout.WEST);
		playerPanel.add(statPanel, BorderLayout.CENTER);
		//playerPanel.add(playerName, BorderLayout.SOUTH);
		cardPanel.add(playerPanel, BorderLayout.NORTH);
		cardPanel.add(cardPane, BorderLayout.CENTER);
		cardPanel.setBorder(border);
		
		cardPanel.validate();
	}
	
	public void updateView(Event e){

		cardPanel.remove(cardPane);
		
		cardPane = new JLayeredPane();
		cardPane.setOpaque(false);

		// origin will be at 0,0
		Point origin = new Point(-15, -20);
		
		if(e.getPlayer().getHand().size()>=13){
			for (int i = 0; i < 13; i++) {
				// this will create the card at the origin
				JLabel backCard = createIconLabel(origin);
				// this will set the z-axis and create the layering
				cardPane.add(backCard, new Integer(i));
				// add 15 pixels to the x-axis of the origin
	            origin.x += 15;
			}
		}
		else{
			for (int i = 0; i < e.getPlayer().getHand().size(); i++) {
				// this will create the card at the origin
				JLabel backCard = createIconLabel(origin);
				// this will set the z-axis and create the layering
				cardPane.add(backCard, new Integer(i));
				// add 15 pixels to the x-axis of the origin
	            origin.x += 15;
			}
		}
		
		cardPanel.add(cardPane, BorderLayout.CENTER);
		
		cardPanel.setBorder(border);
		
		cardsRemaining.setText("Cards Remaining: "+e.getPlayer().getHand().size());
		
		cardPanel.validate();
	}
}
