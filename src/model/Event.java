package model;

import java.util.ArrayList;

import util.Card;
import util.CardGroup;

public class Event {
	public enum EVENT{playCardGroup,playerOut, turnOver, gameOver ,deal, newGame, allPassed, needAPlay,needHumanPlay, afterSwap, chooseAdvantageCards}
	
	private EVENT event;
	private Player player;
	private CardGroup group;
	
	private int numberOfCardsToSwap;
	
	private Play play;
	
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	
	public Event(EVENT e){
		event = e;
	}
	
	public EVENT getEvent(){
		return event;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player p){
		player = p;
	}
	
	public CardGroup getCardGroup(){
		return group;
	}
	
	public void setCardGroup(CardGroup p){
		group = p;
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	public void setCards(ArrayList<Card> c){
		cards = c;
	}
	
	public void setNumberOfCardsToSwap(int n){
		numberOfCardsToSwap = n;
	}
	
	public int getNumberOfCardsToSwap(){
		return numberOfCardsToSwap;
	}
	
	public void setPlayers(ArrayList<Player> players){
		this.players = players;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public Play getPlay(){
		return play;
	}
	
	public void setPlay(Play p){
		play=p;
	}
}
