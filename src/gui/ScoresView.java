package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import model.Event;
import model.Player;
import model.PlayerComparator;
import model.Event.EVENT;

public class ScoresView implements model.Observer{
	
	private JPanel scorePanel;
	private JPanel internalPanel;
	private JInternalFrame scoresFrame;
	
	private ArrayList<JLabel> labels;
	
	public ScoresView(int maxPoints){
		scoresFrame = new JInternalFrame("SCORES (First to "+maxPoints+")",false,false,false,false);
		
		internalPanel = new JPanel();
		internalPanel.setLayout(new BoxLayout(internalPanel,BoxLayout.Y_AXIS));
		String statuses = "                                                                   ";
		scorePanel = new JPanel();	
		scorePanel.add(scoresFrame);
		internalPanel.add(new JLabel(statuses));	
		scoresFrame.add(internalPanel);
		scoresFrame.getContentPane().setBackground(Color.white);
		scoresFrame.setVisible(true);	
	}
	public JPanel getPanel(){
		return scorePanel;
	}
	
	public JInternalFrame getInternalFrame(){
		return scoresFrame;
	}
	
	public void update(Event e){
		if(e.getEvent() == Event.EVENT.newGame){
			ArrayList<Player> players = e.getPlayers();

			labels = new ArrayList<JLabel>();
			for(Player p:players){
				labels.add(new JLabel(p.getName()+":"+p.getPoints()));
			}

			for(JLabel jl : labels){
				internalPanel.add(jl);
			}			
		}
		else if(e.getEvent() == Event.EVENT.turnOver){
			ArrayList<Player> players = e.getPlayers();
			
			ArrayList<Player> playersClone = new ArrayList<Player>();

			for(Player p : players){
				playersClone.add(p.clone());
			}
			
			Collections.sort(playersClone, new PlayerComparator());
			Collections.reverse(playersClone);
			
			for(int i = 0; i< playersClone.size();i++){
				labels.get(i).setText(playersClone.get(i).getName()+":"+playersClone.get(i).getPoints());
			}
		}		
	}
	
	/*
	public static void main(String[] args){
		
		JFrame jf = new JFrame();
		
		JPanel main = new JPanel(new GridLayout(1,3));

		
		final ScoresView scores = new ScoresView(15);
		
		jf.add(main);
		
		JButton button = new JButton("New Game");
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Event e = new Event(EVENT.newGame);
				Player p1 = new Player("Alice","back_card.jpg","back_card.jpg");
				Player p2 = new Player("Bob","back_card.jpg","back_card.jpg");
				Player p3 = new Player("Charlie","back_card.jpg","back_card.jpg");
				Player p4 = new Player("Donnie","back_card.jpg","back_card.jpg");
				Player p5 = new Player("Estelle","back_card.jpg","back_card.jpg");
				
				ArrayList<Player> players = new ArrayList<Player>();
				
				players.add(p1);
				players.add(p2);
				players.add(p3);
				players.add(p4);
				players.add(p5);
				
				e.setPlayers(players);
				
				scores.update(e);
				
			}
			
		});
		main.add(button);
		
		JButton button1 = new JButton("Update");
		
		button1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Event e = new Event(EVENT.turnOver);
				Player p1 = new Player("Alice","back_card.jpg","back_card.jpg");
				Player p2 = new Player("Bob","back_card.jpg","back_card.jpg");
				Player p3 = new Player("Charlie","back_card.jpg","back_card.jpg");
				Player p4 = new Player("Donnie","back_card.jpg","back_card.jpg");
				Player p5 = new Player("Estelle","back_card.jpg","back_card.jpg");
				
				ArrayList<Player> players = new ArrayList<Player>();
				
				p1.addPoints(10);
				p2.addPoints(13);
				p3.addPoints(14);
				p4.addPoints(3);
				p5.addPoints(20);
				
				players.add(p1);
				players.add(p2);
				players.add(p3);
				players.add(p4);
				players.add(p5);
				
				e.setPlayers(players);
				
				Collections.sort(players, new PlayerComparator());
				Collections.reverse(players);
				scores.update(e);
				
			}
			
		});
		main.add(button1);
		
		JButton button2 = new JButton("Update");
		
		button2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Event e = new Event(EVENT.turnOver);
				Player p1 = new Player("Alice","back_card.jpg","back_card.jpg");
				Player p2 = new Player("Bob","back_card.jpg","back_card.jpg");
				Player p3 = new Player("Charlie","back_card.jpg","back_card.jpg");
				Player p4 = new Player("Donnie","back_card.jpg","back_card.jpg");
				Player p5 = new Player("Estelle","back_card.jpg","back_card.jpg");
				
				ArrayList<Player> players = new ArrayList<Player>();
				
				p1.addPoints(40);
				p2.addPoints(33);
				p3.addPoints(14);
				p4.addPoints(90);
				p5.addPoints(5);
				
				players.add(p1);
				players.add(p2);
				players.add(p3);
				players.add(p4);
				players.add(p5);
				
				e.setPlayers(players);
				
				Collections.sort(players, new PlayerComparator());
				Collections.reverse(players);
				scores.update(e);
				
			}
			
		});
		main.add(button2);
		
		main.add(scores.getPanel());

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);
	}
	*/	
}
