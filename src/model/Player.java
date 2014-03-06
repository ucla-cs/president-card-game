package model;

//import robots.BasicAI;
//import robots.DeterministicNPAI;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import robots.BasicAI;
import robots.DeterministicNPAI;
import robots.IRobot;
import model.Hand;
import util.Card;
import util.CardGroup;
import util.Card.Rank;
import util.Card.Suit;

public class Player{
	private String name;
	private String imageString;
	private String winnerString;
	private ImageIcon icon;
	private ImageIcon winnerIcon;
	private int position;
	private boolean isDealer;
	private int totalPoints;
	private Hand hand;
	
	public int timesPassed;
	public int timesPlayed;
	public int setsWon;
	public ArrayList<Integer> prevPositions;
	
	private  IRobot iR;
	
	public Player(String name, String imageString, String winnerString){
		this.name = name;
		this.imageString = imageString;
		this.winnerString = winnerString;
		this.icon = new ImageIcon(this.imageString);
		this.winnerIcon = new ImageIcon(this.winnerString);
		this.totalPoints=0;
		hand = new Hand();
		iR = null;
		
		prevPositions = new ArrayList<Integer>();
		timesPassed = 0;
		timesPlayed = 0;
		setsWon = 0;
	}
	
	public void addToHand(Card c){
		hand.add(c);
	}
	
	public void setAI(IRobot i){
		this.iR = i;
	}
	
	
	public IRobot getAI(){
		return iR;
	}
	
	public boolean isHuman(){
		return iR==null;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPosition(){
		return position;
	}
	
	public CardGroup getPlay(TurnHistory tH, Hand h) {
		CardGroup toPlay = null;
		if(iR!=null && (iR.getClass() == new DeterministicNPAI("test").getClass() || iR.getClass() == new BasicAI("test").getClass())){
			toPlay = iR.chooseCards(tH, h);
		}
		else{       
			
			while(!Status.getPlayerChoseCards()){
				//System.out.println("waiting");
			}
			
			toPlay = Status.getCardsToPlay();
			
			Status.setPlayerChoseCards(false);
			
		}
		return toPlay;
	}
	
	public ArrayList<Card> getAdvantageCards(int num, Hand hand){
		ArrayList<Card> toSwap = null;
		if(iR!=null && (iR.getClass() == new DeterministicNPAI("test").getClass() || iR.getClass() == new BasicAI("test").getClass())){
			toSwap = iR.getCardsToGiveAway(num, hand);
			return toSwap;
		}
		else{
			
			//this is where the panel should pop up to give away the cards
			
			ArrayList<Card> c = new ArrayList<Card>();
			Iterator<Card> i = hand.iterator();
			c.add(i.next());
			if(num==1){
				return c;
			}
			c.add(i.next());
			return c;
		
			
		}
	}
	
	public void setPosition(int position){
		this.position = position;
	}
	
	public void setDealer(boolean b){
		this.isDealer=b;
	}
	
	public boolean isDealer(){
		return isDealer;
	}
	
	public void addPoints(int points){
		totalPoints += points;
	}
	
	public void clearPoints(){
		this.totalPoints = 0;
	}
	
	public Hand getHand(){
		return hand;
	}
	
	public int getPoints(){
		return totalPoints;
	}
	
	public String toString(){
		return this.name;
	}

	
	public String getImageString() {
		return this.imageString;
	}
	
	public String getWinnerString() {
		return this.winnerString;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public ImageIcon getWinnerIcon() {
		return this.winnerIcon;
	}
	
	public Player clone(){
		Player p = new Player(this.getName(), this.getImageString(), this.getWinnerString());
		p.setPosition(this.getPosition());
		p.isDealer = this.isDealer;
		p.addPoints(this.totalPoints);
		for(Card c : this.hand){
			p.hand.add(c);
		}
		return p;
	}
}
