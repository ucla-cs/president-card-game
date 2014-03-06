package gui;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Event;
import model.Player;


public class SocialStatusView implements model.Observer{

	private JInternalFrame socialFrame;
	private JPanel socialPanel;
	private JPanel internalPanel;
	
	private ArrayList<JLabel> nameLabels;
	
	public SocialStatusView(int numOfPlayers){
		nameLabels = new ArrayList<JLabel>();
		socialFrame = new JInternalFrame("Social Status");
		socialFrame.setBackground(Color.white);
		socialPanel = new JPanel();
		internalPanel = new JPanel();
		internalPanel.setLayout(new GridLayout(numOfPlayers,2));
		
		String[] titles = {"Senator","Mayor","Professor","TA","Concordia Grad"};
		int neutralIndex = 1;
		for(int i = 1; i <= numOfPlayers ; i++){
			String title = "";
			if(i==1){title = "President : ";}
			else if(i==2){title = "Vice-President : ";}
			else if(i==numOfPlayers-1){title = "Secretary : ";}
			else if(i==numOfPlayers){title = "Bum : ";}
			else{
				title = titles[neutralIndex] + " : ";
				neutralIndex++;
			}
			
			internalPanel.add(new JLabel(title));
			JLabel name = new JLabel("Not yet assigned");	//this will be overridden
			internalPanel.add(name);
			nameLabels.add(name);
		}

		socialPanel.add(socialFrame);
		
		socialFrame.add(internalPanel);
		socialFrame.setVisible(true);
	}
	
	public JPanel getSocialStatusPanel(){
		return socialPanel;
	}
	
	public void update(Event e){
		if(e.getEvent() == Event.EVENT.newGame){
			for(JLabel jL : nameLabels){
				jL.setText("Unassigned");
			}
		}
		else if(e.getEvent() == Event.EVENT.playerOut){
			
		}		
		else if(e.getEvent() == Event.EVENT.turnOver){
			ArrayList<Player> players = e.getPlayers();
			int i = 0;
			for(JLabel jL : nameLabels){
				jL.setText(players.get(i).getName());
				i++;
			}			
		}
	}	
}
