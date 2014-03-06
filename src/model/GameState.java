package model;

//import model.GameEngine;
import util.Deck;
import util.Deck.NumberOfPacks;
import util.Deck.NumberOfJokers;

import model.TurnHistory;
import model.Play;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
		
public class GameState {
	//All the fields are public so that the AI can see what's going on. These will all be clones of the real things in the game engine 
	public int numberOfPlayers;
	public ArrayList<Player> players;
	public Deck deck;
	public TurnHistory turnHistory;
	public int numberOfRounds;
	public NumberOfPacks numberOfPacks;
	public NumberOfJokers numberOfJokers;
	public int dealer;
	public boolean fDealt;
	
	public Player humanPlayer;
	
	public GameState(String saveFileName){
		//TODO load and create a game engine from saveFile
	}
	
	public GameState(Player humanPlayer, ArrayList<Player> players, Deck deck, TurnHistory turnHistory,int numberOfRounds, NumberOfPacks numberOfPacks, NumberOfJokers numberOfJokers, int dealer, boolean fDealt){
		this.humanPlayer = humanPlayer.clone();
		this.numberOfPlayers = players.size();
		this.players = (ArrayList<Player>)players.clone();
		this.deck = deck.clone();
		
		Iterator<Play> i = turnHistory.iterator();
		Stack<Play> s = new Stack<Play>();
		while(i.hasNext()){
			s.push(i.next());
		}
		
		this.turnHistory = new TurnHistory(s);
		this.numberOfRounds = numberOfRounds;
		this.numberOfPacks = numberOfPacks;
		this.numberOfJokers = numberOfJokers;
		this.dealer = dealer;
		this.fDealt = fDealt;
	}
	
	public void saveGame(){
		//TODO implement save game to file.
	}
}
