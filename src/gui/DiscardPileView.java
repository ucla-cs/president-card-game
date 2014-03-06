package gui;

//import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import util.CardImages;

import model.Event;

/*
 * Container class to hold and provide a method to return a panel representing the 
 * discard pile. Once everyone has passed in any particular turn, this can be set to having a the 
 * back of a card to represent the discard pile.
 */
public class DiscardPileView implements model.Observer{
	
	private JPanel ContainerPanel;
	private JLayeredPane Pile;
	
	public DiscardPileView(){
		ContainerPanel = new JPanel();
		ContainerPanel.setOpaque(false);
		ContainerPanel.setLayout(new BorderLayout());
		Pile = new JLayeredPane();
		Pile.setOpaque(false);
		JLabel discardPile = new JLabel("Discard Pile");
		discardPile.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		ContainerPanel.add(discardPile,BorderLayout.NORTH);
		ContainerPanel.add(Pile,BorderLayout.CENTER);
	}

	public void update(Event e) {
		if(e.getEvent() == Event.EVENT.newGame){
			//blank the pile
			ContainerPanel.remove(Pile);
			Pile = new JLayeredPane();
			Pile.validate();
			ContainerPanel.add(Pile,BorderLayout.CENTER);
			ContainerPanel.validate();
		}
		else if(e.getEvent() == Event.EVENT.deal){
			//blank the pile
			ContainerPanel.remove(Pile);
			Pile = new JLayeredPane();
			Pile.validate();
			ContainerPanel.add(Pile,BorderLayout.CENTER);
			ContainerPanel.validate();
		}	
		else if(e.getEvent() == Event.EVENT.allPassed){
			//make sure the discard pile has a card with its back turned 
			ContainerPanel.remove(Pile);
			Pile = new JLayeredPane();

			Point origin = new Point(15,55);
			int z = 0;
			
			JLabel j = new JLabel(CardImages.getBack());
			j.setBounds(origin.x, origin.y, 73, 100);
			Pile.add(j,new Integer(z));
			
			Pile.validate();
			ContainerPanel.add(Pile,BorderLayout.CENTER);
			ContainerPanel.validate();	//not sure if this is required
		}	
	}
	
	public JPanel getDiscardPileView(){
		return ContainerPanel;
	}
	
	/*
	public static void main(String[]args){
		Event ev1 = new Event(Event.EVENT.turnOver);
		Event ev3 = new Event(Event.EVENT.deal);
		JFrame j = new JFrame();
		
		j.setSize(150, 150);
		
		DiscardPileView d = new DiscardPileView();
		j.add(d.getDiscardPileView());
		j.validate();
		j.setVisible(true);
		d.update(ev3);
		
        long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
        
        d.update(ev1);
        
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
        
        d.update(ev3);

        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
        
        d.update(ev1);
        
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
        
        
        d.update(ev3);       
	}
	*/
}
