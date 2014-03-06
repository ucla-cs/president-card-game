package gui;

import javax.swing.JFrame;

import model.GameEngine;

/**
 * Small UI just to demonstrate how the UI will interact with the API.
 * Should not be considered an example of the design you should follow for M3.
 */
public class President extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main( String[] args )
	{
		while(true){		
			titleIcon.title();			
			
			while(!GameView.isDone());
			
			GameView.setDone(false);
			
			GameEngine gE = new GameEngine();
			GameView gameview = new GameView(gE,Settings.getPlayerName(),Settings.getNumberOfPlayers()+1,Settings.getNumberOfJokers(), Settings.getNumberOfDecks(),Settings.getAdvantageNumber(),Settings.getPoints());
			gE.addObserver(gameview);
			gE.newGame();
			
			while(!GameView.isDone());
			
			GameView.setDone(false);
			
			if(GameView.isPlayAgain() == false) break;
			GameView.setPlayAgain(false);
			
		}
	}	
}
