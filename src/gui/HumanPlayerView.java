package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import model.Event;
import model.Observer;
import model.Player;
import model.Event.EVENT;

import gui.HumanPlayerPanel;

//the container class to hold the cards of a human player and allow them 
// to properly intereact with their carsd at the aproppriate times.
public class HumanPlayerView implements Observer {

	private Player player;
	private JPanel cardPanel;
	private JPanel newPanel;

	public HumanPlayerView(Player player) {
		this.player = player;
		cardPanel = new JPanel(new BorderLayout());
		cardPanel = new JPanel();
		
		cardPanel.setPreferredSize(new Dimension(500,140));
	}
	public JPanel getCardPanel(){
		return cardPanel;
	}

	public void update(Event e) {
		if(e.getEvent().equals(EVENT.needHumanPlay)){			
			HumanPlayerPanel hp = new HumanPlayerPanel(player.getHand(), e.getCardGroup());
			newPanel = hp.createCardPanel();
			
			cardPanel = newPanel;
			cardPanel.setPreferredSize(new Dimension(500,140));
		}
		else if (e.getEvent().equals(EVENT.afterSwap)|| e.getEvent().equals(EVENT.deal) || e.getEvent().equals(EVENT.playCardGroup)|| e.getEvent().equals(EVENT.newGame)) {
		
			HumanPlayerPanel hp = new HumanPlayerPanel(player.getHand(), e.getCardGroup());
			newPanel = hp.displayCards();
			
			cardPanel = newPanel;
			cardPanel.setPreferredSize(new Dimension(500,140));
		}
		
		else if(e.getEvent().equals(EVENT.chooseAdvantageCards)){
			AdvantageView advantageView = new AdvantageView(e.getNumberOfCardsToSwap());
			newPanel = advantageView.advantage(player.getHand());
			
			cardPanel = newPanel;
			cardPanel.setPreferredSize(new Dimension(500,140));
		}
		
		newPanel.validate();
		cardPanel.validate();
	}	
}
