package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Event.EVENT;

import util.CardGroup;
import util.CardImages;
import util.Deck;
import util.Deck.NumberOfJokers;
import util.Deck.NumberOfPacks;
import util.Card;

/**
 * An API for managing the game state.
 * The code therein is an example only. Feel
 * free to modify as desired.
 */
public class GameEngine
{
	/*
	 * Player array variables. Used to keep track off all the players, you is in the current round,
	 *  and who has already gotten rid of all their cards.
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Player> playersInTurn = new ArrayList<Player>();
	private ArrayList<Player> playersRank = new ArrayList<Player>();
	
	//The deck to be used
	private Deck deck;
	
	//User Defined Settings
	//private int numberOfRounds;
	private NumberOfPacks numberOfPacks;
	private NumberOfJokers numberOfJokers;	
	private int numberOfPlayers;
	private int advantage = 0;	//The President/Vice-President advantage. Only legal values are : {0,1,2}
	private int maxPoints ;		//The number of points to play to before a winner is found (The person that has more points than maxPoints)
	
	//the name of the human player
	private String humanPlayerName;
	
	private boolean fDealt = false;	
	
	//A list of names to be randomly chosen from when naming AI players
	private ArrayList<String> possibleAIPlayerNames;

	private Player winner;
	
	private Random r = new Random();
	private int gameSpeed = 1;
	
	private LinkedList<Observer> observers = new LinkedList<Observer>();
	
	private static boolean random = true;
	private static int speed = 500;
	
	public static void setRandom(boolean b){
		random = true;
	}
	public static void setSpeed(int i){
		speed = i;
	}
	
	/*
	 * Runs an entire game until someone surpasses the point limit.
	 * Uses whatever settings are found in it's local fields.
	 * @pre : the players arraylist has already been set by the GUI
	 */
	public void newGame()
	{			
		//Notify the GUI for a new Game
		Event newGame = new Event(EVENT.newGame);
		newGame.setPlayers(players);
		notify(newGame);
		playGame();
	
		//Ask if the user wants to play another game in the GUI
	}
	
	private void playGame(){
		
		//Denotes whether or not its the first turn of a game. in which case there is no social status to begin.
		boolean firstTurn = true;
		
		while(!gameOver()){
			
			if(firstTurn){
				//Add all the players into the ArrayList of players who have not gotten rid of their cards in the current turn
				for(Player p : players){
					playersInTurn.add(p);
				}
			}

			//Instantiate the new deck for the new turn
			deck = new Deck(numberOfPacks, numberOfJokers);
			deal();
			
			//Swap card(s) if there is an advantage
			if(advantage==1 && firstTurn == false){
				this.swapCards(0, playersInTurn.size()-1, 1);
			}
			else if(advantage==2 && firstTurn == false){
				this.swapCards(0, playersInTurn.size()-1, 2);
				this.swapCards(1, playersInTurn.size()-2, 1);
			}
					
			//denote that this is not the first turn of the game, so that the advantage, if there is any, will be handled
			if(firstTurn==true){firstTurn = false;}
			
			//Play the turn
			playTurn();

			/*
			 * The players will now have been added to playersRank. The players will be in order of who got rid of their cards first.
			 * (i.e. playersRank.get(0) will be the president for the next round)
			 * This loop adds to the total points of each player depending on their new social status after the previous turn was executed
			 * It then assigns them a new position according to where they finished.
			 */
			for (int i = 0; i < playersRank.size(); i++ ) {
				Player pl = playersRank.get(i);
				// add the points to the players
				pl.addPoints(playersRank.size() - i-1);
				// now we need to assign who's president, vice-president.... etc.
				pl.setPosition(i+1);
			}
			
			//Add the finished players back into the playersInTurn Arraylist for the next Turn, in case there is another.
			playersInTurn = new ArrayList<Player>();
			for(Player p : playersRank){
				playersInTurn.add(p);
			}

			Event turnOver = new Event(EVENT.turnOver);	//technically, this does nothing	
			turnOver.setPlayers(playersInTurn);								
			notify(turnOver);
			
			//Empty the playersRank ArrayList so that it can be added to for the next turn
			playersRank = new ArrayList<Player>();
		}
	}
	
	/*
	 * Plays out an entire turn. This goes until only one person has cards left in their hand.
	 */
	private void playTurn(){	
		int nextToPlay = 0;
		Stack<Play> plays = new Stack<Play>();
		
		//While the turn is not over, we get proceed to find what the card to be played by the next player is
		while(!turnOver())
		{		
			//The next person to play
			Player player = playersInTurn.get(nextToPlay);
			
			Event needAPlay = new Event(EVENT.needAPlay);
			needAPlay.setPlayer(player);
			notify(needAPlay);

			CardGroup groupToPlay;
			CardGroup toBeat = getCardGroupToBeat(player,new TurnHistory(plays));
			
			if(! (player.getAI()==null)){	//dont bother making the human wait at all.
				if(random){
					aWait();
				}
				else{
					long t0,t1;
		            t0=System.currentTimeMillis();
			        do{
			            t1=System.currentTimeMillis();
			        }
			        while (t1-t0< speed);
				}
			}
			
			if(player.isHuman()){	
				Event e = new Event(EVENT.needHumanPlay);
				e.setCardGroup(toBeat);
				notify(e);
			}
			
			groupToPlay = player.getPlay(new TurnHistory(plays), player.getHand());
			
			//Record the play by pushing it into the stack
			Play p = new Play(player.getName(), groupToPlay);
			plays.push(p);
			
			//If the play is not a pass, add the cards that will be played to an ArrayList in order to remove the cards from the hand using playGroup()
			if (groupToPlay.size() != 0) {
				player.timesPlayed ++;
				ArrayList<Card> cards = new ArrayList<Card>();
				for(Card c : groupToPlay){cards.add(c);}
				player.getHand().playGroup(cards);
			}
			else{
				player.timesPassed++;
			}
			
			//Notify the GUI for a new Game
			Event play = new Event(EVENT.playCardGroup);
			play.setPlayer(player);
			play.setCardGroup(groupToPlay);
			play.setPlay(p);
			notify(play);

			/*
			 * See if the player has now gotten rid of all his cards. If so, remove that platey from the list
			 * of those playing and add them to the list of those that have finished playing
			 */
			if (player.getHand().size() == 0)
			{
				if(playersRank.size()==0){
					//then this player just won the set
					player.setsWon++;
				}
				
				player.prevPositions.add(playersRank.size());
				
				playersRank.add(playersInTurn.remove(nextToPlay));
				if(nextToPlay == playersInTurn.size()){
					nextToPlay=0;
				}
				Event playerOut = new Event(EVENT.playerOut);
				playerOut.setPlayer(player);
				notify(playerOut);
			}
			else
			{
				if( nextToPlay >= playersInTurn.size() -1 ){
					nextToPlay=0;
				}
				else{
					nextToPlay++;
				}
			}
			
		}	
	}
	
	/*
	 * @ return : true if there is only one player left. false otherwise.
	 */
	private boolean turnOver(){
		if(playersInTurn.size()==1){
			// if there is only one player left, clear his cards because the game is over, and add him to the playersRank as the lowest social value 
			Player p = playersInTurn.get(0);
			p.getHand().clear();
			p.prevPositions.add(playersRank.size());
			playersRank.add(p);
			
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean gameOver(){
		int largest = 0;
		Player possibleWinner =null;// new Player("player",null,null);	//default initialization
		for (int i = 0; i < players.size(); i++ ) {
			Player p = players.get(i);
			if( p.getPoints() > largest){
				largest = p.getPoints();
				possibleWinner = p;
			}
		}
		if(largest >= maxPoints){
			Event gameOver = new Event(EVENT.gameOver);
			ArrayList<Player> sortedPlayers = players;
			Collections.sort(sortedPlayers,new PlayerComparator());
			Collections.reverse(sortedPlayers);
			gameOver.setPlayers(sortedPlayers);
			notify(gameOver);
			return true;
		}
		else{
			return false;
		}
	}
	
	//Adds all the players with cards into the playersInTurn ArrayList
	private void allPlayersInTurn(){
		playersInTurn= new ArrayList<Player>();
		for(Player p : players){
			if(p.getHand().size() != 0){
				playersInTurn.add(p);
			}
		}
	}
	
	/*
	 * Prompts the user for, and saves all information relevant to a game.
	 * Then creates the new game with the given setting.
	 */
	/*
	private void getSettings(){	
		// need to later implement whether to load or create new game
		// for now assume that only option is New Game
		System.out.println("New Game \n");
		// Obtain the number of CPUs for the game
		System.out.println("How many CPU players do you want(4-7)? \n");
		Scanner keyboard = new Scanner(System.in);
		numberOfPlayers = keyboard.nextInt();
		// need to check to see if numberOfPlayers is in the range of 4-7
		while ((numberOfPlayers < 4) || (numberOfPlayers > 7)) {
			System.out.println("Please choose a number from 4-7. Try again \n");
			System.out.println("How many CPU players do you want? \n");
			numberOfPlayers = keyboard.nextInt();
		}
		// Obtain the number of Decks for the game
		System.out.println("How many decks would you like to play with(1-2)? \n");
		int deckAmount = keyboard.nextInt();
		// need to check to see if the deckAmount is in the range of 1-2
		while ((deckAmount != 1) && (deckAmount != 2)) {
			System.out.println("Please choose a number from 1-2. Try again \n");
			System.out.println("How many decks would you like to play with?");
			deckAmount = keyboard.nextInt();
		}
		// now convert number into enum
		if (deckAmount == 1)numberOfPacks = NumberOfPacks.ONE;
		else numberOfPacks = NumberOfPacks.TWO;
		
		// Obtain the number of Jokers per deck
		System.out.println("How many Jokers per deck? \n");
		int jokerNumber = keyboard.nextInt();
		// check to see if it is the correct number of Jokers
		while (!(jokerNumber >= 0 && jokerNumber <= 2)) 
		{
			System.out.println("Please choose a number from 0-2. Try again \n");
			System.out.println("How many Jokers per deck? \n");
			jokerNumber = keyboard.nextInt();
		}
		// now convert number in enum
		if (jokerNumber == 0){numberOfJokers = NumberOfJokers.ZERO;}
		else if (jokerNumber == 1){numberOfJokers = NumberOfJokers.ONE;}
		else {numberOfJokers = NumberOfJokers.TWO;}
		// Obtain number of rounds
		System.out.println("How many points do you want to play till? \n");
		
		maxPoints = keyboard.nextInt();
		
		// ask user for advantage
		System.out.println("Please choose an advantage: 1 or 2?");
		advantage = keyboard.nextInt();
		while ((advantage != 1 && advantage != 2)) {
				System.out.println("Please choses a number between 1 and 2");
				System.out.println("Please choose an advantage: 1 or 2?");
		}
		// ask user if these setting are ok
		System.out.println("Settings\nNumber of Players: " + numberOfPlayers + "\nNumber of Decks: " + numberOfPacks + "\nNumber of Jokers per Pack: " + numberOfJokers + "\nTotal Number of Cards: " + (52+numberOfJokers.ordinal())*(numberOfPacks.ordinal()+1) + "\nNumber of Rounds : " + numberOfRounds + "\nadvantage : " + advantage+ "\n\nAre these settings ok? y/n \n");
		String response = keyboard.next();
		while (!(response.equals("y") || response.equals("n"))) {
			System.out.println("Please enter either y or n \n");
			System.out.println("Are these settings ok? y/n \n");
			response = keyboard.nextLine();
		}
		// if the user is not satisfied with changes, then use recursion to reset data
		if (response.equals("n")) {
			getSettings();
			return;
		}

		createPossibleAINames();
		Random randomGenerator = new Random();
		playerNames = new ArrayList<String>();
		for (int i = 0; i < numberOfPlayers; i++) {
			// obtain name from the string arraylist using the nextInt() 
			// NOTE: use the size of the name arraylist in the nextInt() to prevent errors
			playerNames.add(possibleAIPlayerNames.remove(randomGenerator.nextInt(possibleAIPlayerNames.size())));
		}
		createStupidPlayers();
		return;
	}
	*/
	
	/*
	 * Removes the top num cards from the disadvantaged player and stores them 
	 * to give to the advantaged player. Prompts the advantaged player to select
	 * num cards to give away. Executes the exchange of cards.
	 * @param adv : the index in the players array of the player with the advantage
	 * @param dis : the index in the players array of the player with the disadvantage
	 * @param num : the number of cards to exchange
	 */
	
	private void swapCards(int adv, int dis, int num)
	{
		Player disP = playersInTurn.get(dis);
		Player advP = playersInTurn.get(adv);
		
		JOptionPane.showMessageDialog(null, advP.getName() + " and " + disP.getName() + " are now exchanging " + num + " card(s).");
		
		ArrayList<Card> toAdv = new ArrayList<Card>();
		ArrayList<Card> toDis = new ArrayList<Card>();
		
		//get the num - highest cards from the disadvantaged player
		for(int i = 1 ; i <= num ; i++){
			Card c = disP.getHand().getHighestCard();
			disP.getHand().remove(c);
			toAdv.add(c);
		}
		
		//if the human was the player whose highest cards were taken, then we display them
		if(disP.getAI()==null){
			String cardRemoved = "Card removed from hand : ";
			for(Card card : toAdv){
				ImageIcon icon = CardImages.getCard(card);
				JOptionPane.showMessageDialog(null, cardRemoved + card.toString(), "Giving Card To " + advP.getName(), JOptionPane.PLAIN_MESSAGE, icon);
			}
		}
		
		
		//if the human is the player who chooses what cards to get rid of, we allow them to choose, then show them
		if(advP.getAI()==null){
			
			Event getCardsToSwap = new Event(EVENT.chooseAdvantageCards);
			getCardsToSwap.setNumberOfCardsToSwap(num);
			notify(getCardsToSwap);
			
			while(!Status.getPlayerChoseCards()){
				//System.out.println("waiting");
			}
								
			toDis = Status.getCardsToDiscardAdv();
			//toDis = advP.getAdvantageCards(num, advP.getHand() ); //.getAI().getCardsToGiveAway(num, advP.getHand());
			
			Status.setPlayerChoseCards(false);
			
			String cardRemoved = "Card removed from hand : ";
			for(Card card : toDis){
				ImageIcon icon = CardImages.getCard(card);
				JOptionPane.showMessageDialog(null, cardRemoved + card.toString(), "Giving Card To " + disP.getName(), JOptionPane.PLAIN_MESSAGE, icon);
			}
		}
		else{
			toDis = advP.getAdvantageCards(num, advP.getHand() ); //.getAI().getCardsToGiveAway(num, advP.getHand());
		}
		
		for(Card card : toDis){
			advP.getHand().remove(card);
			if(disP.getAI()==null){
				ImageIcon icon = CardImages.getCard(card);
				JOptionPane.showMessageDialog(null, "Card added to hand : " + card.toString(), "Receiving Card From " + advP.getName(), JOptionPane.PLAIN_MESSAGE, icon);
			}
			disP.getHand().add(card);
		}
		for(Card card : toAdv){
			if(advP.getAI()==null){
				ImageIcon icon = CardImages.getCard(card);
				JOptionPane.showMessageDialog(null, "Card added to hand : " + card.toString(), "Receiving Card From " + disP.getName(), JOptionPane.PLAIN_MESSAGE, icon);
			}
			advP.getHand().add(card);
		}
		Event afterSwap = new Event(EVENT.afterSwap);
		notify(afterSwap);
	}
	
	/**
	 * Deal the cards to the players, beginning with the president, who will always be at index 0 in the players ArrayList.
	 * @pre there is not game currently being played
	 * @pre none of the players have any cards yet
	 */
	private void deal()
	{
        JOptionPane.showMessageDialog(null, "About to Deal!");
		fDealt = !fDealt;
		int i = 0;
		while(deck.size() != 0){
			// deals to the president first
			playersInTurn.get(i).addToHand(deck.draw());
			if( i == playersInTurn.size() -1){
				i=0;
			}
			else{
				i++;
			}
		}
		//Notify the GUI for a new Game
		Event deal = new Event(EVENT.deal);
		deal.setPlayers(playersInTurn);
		notify(deal);
		JOptionPane.showMessageDialog(null, "Cards have been dealt. Ready to play??");
	}
	
	private void aWait(){
        long t0,t1;
        t0=System.currentTimeMillis();
        long gap = (((long)gameSpeed)*200) + r.nextInt(200)*2  ;
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0< gap);
	}
	
	public void setPlayers(ArrayList<Player> players){
		this.players = players;
	}

	/*
	private Player getWinner() {
		return winner;
	}
	*/
	
	/*
	 * Begins and runs a game between two basic AI's and two moderate AI's
	 * @param n : the number of games to simulate between the AI players.
	 */
	private static void simulateGame(int n)
	{
		GameEngine gE = new GameEngine();
		
		int stupid1 =0;
		int stupid2 = 0;
		int smart1 = 0;
		int smart2 = 0;	

		for(int i = 0; i<n;i++){
			gE.newGame();
			if( gE.winner.getName().equals("smartAI1") ){
				smart1++;
			}
			else if( gE.winner.getName().equals("smartAI2") ){
				smart2++;
			}
			else if( gE.winner.getName().equals("dumbAI1") ){
				stupid1++;
			}
			else if( gE.winner.getName().equals("dumbAI2") ){
				stupid2++;
			}
		}
		
		System.out.println("Advanced AI 1 won " + smart1 + " games");
		System.out.println("Advanced AI 2 won " + smart2 + " games");
		System.out.println("Dumb     AI 1 won " + stupid1 + " games");
		System.out.println("Dumb     AI 2 won " + stupid2 + " games");
		
		System.out.println("Total for Advanced AI is " + (smart1 + smart2)+ " games");
		System.out.println("Total for Dumb     AI is " + (stupid1 + stupid2)+ " games");
		
	}

	/*
	 * Used to preload the setting variables of a game in order to run randomized simulations between AI opponents for Milestone 2
	 */
	private void preSetSettings()
	{
		numberOfPlayers = 4;
		Random r = new Random();
		int i = r.nextInt(2)+1;
		advantage = i;
		
		i = r.nextInt(2);
		for(NumberOfPacks p : NumberOfPacks.values()){
			if(p.ordinal() == i){
				numberOfPacks = p;
			}
		}
		i = r.nextInt(3);
		for(NumberOfJokers j : NumberOfJokers.values()){
			if(j.ordinal() == i){
				numberOfJokers= j;
			}
		}
		
		i=15;//from 15 to 100 max points
		maxPoints= i;
		
		i = r.nextInt(4);
		ArrayList<Player> temp = new ArrayList<Player>();
		/*
		Player smartAI1 = new Player("smartAI1");
		Player smartAI2 = new Player("smartAI2");
		smartAI1.setAI(new BasicAI("smartAI1"));
		smartAI2.setAI(new BasicAI("smartAI2"));
		
		Player dumbAI1 = new Player("dumbAI1");
		Player dumbAI2 = new Player("dumbAI2");
		dumbAI1.setAI(new DeterministicNPAI("dumbAI1"));
		dumbAI2.setAI(new DeterministicNPAI("dumbAI2"));
		
		temp.add(smartAI1);
		temp.add(smartAI2);
		temp.add(dumbAI1);
		temp.add(dumbAI2);
		*/
		players = new ArrayList<Player>();
		players.add(temp.get(i));
		temp.remove(i);
		i = r.nextInt(3);
		players.add(temp.get(i));
		temp.remove(i);
		i = r.nextInt(2);
		players.add(temp.get(i));
		temp.remove(i);
		players.add(temp.get(0));
		
	}
	
	//The observer methods. Used to update the GUI components.
	private void notify(Event e){
		for(Observer o : observers){
				o.update(e);
		}
	}
	
	public void addObserver(Observer o){
		observers.add(o);
	}
	public void removeObserver(Observer o){
		observers.remove(o);
	}
	
	//All the mutator methods for the GUI to call upon as the user-defined settings are gathered.
	
	public void setNumberOfPacks(NumberOfPacks numberOfPacks){
		this.numberOfPacks = numberOfPacks;
	}
	
	public void setNumberOfJokers(NumberOfJokers numberOfJokers){
		this.numberOfJokers = numberOfJokers;
	}
	
	public void setNumberOfPlayers(int numberOfPlayers){
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void setHumanPlayerName(String humanPlayerName){
		this.humanPlayerName = humanPlayerName;
	}
	
	public void setAdvantage(int advantage){
		this.advantage = advantage;
	}
	
	public void setMaxPoints(int maxPoints){
		this.maxPoints = maxPoints;
	}
	

	public int getMaxPoint(){
		return maxPoints;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	private CardGroup getCardGroupToBeat(Player player,TurnHistory t){
		String name = player.getName();
		Iterator<Play> it = t.reverseIterator();
		//Play p = new Play("",new CardGroup());
		Play p;
		if(!(t.size()==0)){
			p = t.getLastPlay();
		}
		else{
			p = new Play("",new CardGroup());	//only in the event of beginning a new game/turn.
		}
		while(it.hasNext() && !p.getPlayerID().equals(name) && p.getCardGroup().isPass()){
			p=it.next();
			
		}

		if(p.getPlayerID().equals(name)){	//this never returns true!!!!
			//then everyone passed
			Event e = new Event(EVENT.allPassed);
			e.setPlayer(player);
			notify(e);
			
			//small wait
			aWait();
			
			return new CardGroup();
		}
		else if (!p.getCardGroup().isPass()){
			return p.getCardGroup();
		}
		else{
			return new CardGroup();
		}
	}
	
	/*
	public static void main(String[]args){
		GameEngine gE = new GameEngine();
		Player p = new Player("PLAYER","","");
		Stack<Play> plays = new Stack<Play>();
		Card[] c1 = {new Card(Rank.EIGHT,Suit.CLUBS),new Card(Rank.EIGHT,Suit.CLUBS),new Card(Rank.EIGHT,Suit.CLUBS)};
		//plays.push(new Play("p2",new CardGroup()));
		plays.push(new Play("p1",new CardGroup(c1)));
		plays.push(new Play("p2",new CardGroup()));
		plays.push(new Play("p3",new CardGroup()));
		TurnHistory t = new TurnHistory(plays);
		System.out.println("The size is : " + gE.getCardGroupToBeat(p, t).size());
	}
	*/
	
}