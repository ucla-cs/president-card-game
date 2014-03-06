package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import javax.swing.JPanel;

import util.Card;
import util.CardGroup;
import util.CardImages;


import model.Event;

/*
 * Container class to hold and provide a method to return a panel representing the 
 * top card group to beat. The card group played goes 'on top' of the previous one if 
 * it is not a pass.
 */
public class CardsOnTopView implements model.Observer{
	
	JPanel ContainerPanel;
	JLayeredPane CardGroupToBeat;
	
	public CardsOnTopView(){
		ContainerPanel = new JPanel();
		ContainerPanel.setOpaque(false);
		ContainerPanel.setLayout(new BorderLayout());
		CardGroupToBeat = new JLayeredPane();
		JLabel toBeat = new JLabel("Card Group To Beat");
		toBeat.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		ContainerPanel.add(toBeat,BorderLayout.NORTH);
		ContainerPanel.add(CardGroupToBeat,BorderLayout.CENTER);
	}
	
	public JPanel getCardsOnTop(){
		return ContainerPanel;
	}
	
	/*
	 * Like all of our container-type classes, an object of this class is an observer
	 * All the cases in the update method allow it to remain a faithful representation
	 * of what if happening in the overall game/game engine.
	 */
	public void update(Event e) {
		if(e.getEvent() == Event.EVENT.newGame){
			ContainerPanel.remove(CardGroupToBeat);
			CardGroupToBeat = new JLayeredPane();
			CardGroupToBeat.validate();
			ContainerPanel.add(CardGroupToBeat,BorderLayout.CENTER);
			ContainerPanel.validate();
		}
		else if(e.getEvent() == Event.EVENT.allPassed){
			ContainerPanel.remove(CardGroupToBeat);
			CardGroupToBeat = new JLayeredPane();
			CardGroupToBeat.validate();
			ContainerPanel.add(CardGroupToBeat,BorderLayout.CENTER);
			ContainerPanel.validate();	//not sure if this is required
		}
		else if(e.getEvent() == Event.EVENT.turnOver){
			ContainerPanel.remove(CardGroupToBeat);
			CardGroupToBeat = new JLayeredPane();
			CardGroupToBeat.validate();
			ContainerPanel.add(CardGroupToBeat,BorderLayout.CENTER);
			ContainerPanel.validate();
		}
		else if(e.getEvent() == Event.EVENT.playCardGroup){
			if(e.getCardGroup().isPass()){
				//then we don't want to update the card on top
				return;
			}
			
			//else we will get rid of the previous layered pane that was the 'top', and replace it with the new group to beat
			
			ContainerPanel.remove(CardGroupToBeat);
			CardGroupToBeat = new JLayeredPane();

			Point origin = new Point(25,55);
			int z = 0;
			CardGroup cg = e.getCardGroup();
			
			for(Card c : cg){
				final JLabel j = new JLabel(CardImages.getCard(c));
				j.setBounds(origin.x, origin.y, 73, 97);
				CardGroupToBeat.add(j,new Integer(z));
				z++;
				origin.x += 10;
			}
			CardGroupToBeat.validate();
			ContainerPanel.add(CardGroupToBeat,BorderLayout.CENTER);
			ContainerPanel.validate();
		}		
	}
}
