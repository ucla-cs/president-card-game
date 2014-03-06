package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;	//only using this to test
import java.util.Collections;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import util.Card;
import util.CardGroup;
import util.Card.Rank;
import util.Card.Suit;

import model.Event;
import model.Player;
import model.PlayerComparator;
import model.Event.EVENT;

/*
 * The container class to hold the panel with the historyview. Shows all the plays that
 * have transpired since the start of the game.
 */
public class HistoryView implements model.Observer{
	private JPanel mainPanel;
	
	private JInternalFrame jInternalFrame;
	
	ArrayList<String> hist;
	
	public HistoryView(){
		mainPanel = new JPanel();
	}
	
	public JPanel getPanel(){
		return mainPanel;
	}
	
	//Add to the histort depending on what the event was.
	public void update(Event e){
		if(e.getEvent() == Event.EVENT.newGame){
			paintView();
		}
		else if(e.getEvent() == Event.EVENT.deal){
			String s = "Dealing Cards";
			
			hist.add(s);
			
			repaintView();
		}
		else if(e.getEvent() == Event.EVENT.playCardGroup){
			String s;
			if(e.getCardGroup().size()!=0){
				if(e.getCardGroup().getRank()!= null){
					s = e.getPlayer().getName() + " played "+ e.getCardGroup().size() + " "+ e.getCardGroup().getRank();
				}
				else{
					s = e.getPlayer().getName() + " played a JOKER";
				}
			}
			else{
				s = e.getPlayer().getName() + " passed";
			}
			
			hist.add(s);
			repaintView();
		}
		else if(e.getEvent() == Event.EVENT.playerOut){
			String s = e.getPlayer().getName() + " is Out";
			
			hist.add(s);
			repaintView();
			
		}
		else if(e.getEvent() == Event.EVENT.allPassed){
			String s = e.getPlayer() + " won the set";
			hist.add(s);
			repaintView();
		}
		else if(e.getEvent() == Event.EVENT.turnOver){
			String s = "TURN OVER";
			hist.add(s);
			repaintView();
		}
		else if(e.getEvent() == Event.EVENT.gameOver){
			String s = "GAME OVER";
			hist.add(s);
			repaintView();
		}
		else if(e.getEvent() == Event.EVENT.afterSwap){
			String s = "Cards Swapped";
			hist.add(s);
			repaintView();
		}
		
		
	}
	
	//Paints the history view for the first time
	public void paintView(){

		jInternalFrame = new JInternalFrame("Play History",false,false,false,false);

		jInternalFrame.setSize(400,150);
				
		hist = new ArrayList<String>();
		hist.add("PRESIDENT");
		hist.add("---------                                                       ");
		
		String labels[] = new String[hist.size()];
		
		int i = 0;
		for(String h:hist){
			labels[i] = h;
			i++;
		}
		
		
		 JList jlistScroll = new JList(labels);
		 
		 JScrollPane sp = new JScrollPane(jlistScroll);
		 
		 AdjustmentListener a = new AdjustmentListener(){  
		    	public void adjustmentValueChanged(AdjustmentEvent e) {  
		    		e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		    	}};
		    	
		    sp.getVerticalScrollBar().addAdjustmentListener(a);
		 Container c = jInternalFrame.getContentPane();
		 c.add(sp, BorderLayout.CENTER);
		
		
		mainPanel.add(jInternalFrame);
		
		jInternalFrame.setVisible(true);
		
		sp.getVerticalScrollBar().removeAdjustmentListener(a);
	}
	
	/*
	 * repaints the history view so that it represents any newly added information.
	 */
	public void repaintView(){
		
		String labels[] = new String[hist.size()];
		
		int i = 0;
		for(String h:hist){
			labels[i] = h;
			i++;
		}
		
		JList jlistScroll = new JList(labels);
		 
		JScrollPane sp = new JScrollPane(jlistScroll);
		
		Container c = jInternalFrame.getContentPane();
		c.invalidate();
		c.removeAll();
		
		c.add(sp);
		
		AdjustmentListener a = new AdjustmentListener(){  
	    	public void adjustmentValueChanged(AdjustmentEvent e) {  
	    		e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	    	}};
	    	
	    sp.getVerticalScrollBar().addAdjustmentListener(a);
	    
	    c.validate();

		sp.getVerticalScrollBar().removeAdjustmentListener(a);
	}
}
