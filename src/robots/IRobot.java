package robots;

import util.Card;
import util.CardGroup;
import model.Hand;
import model.TurnHistory;
import model.GameEngine;

import java.util.ArrayList;

/**
 * Implements the decision behavior of a computer player.
 */
public interface IRobot 
{
	/**
	 * Returns a CardGroup that is a subset of Hand to play next
	 * given the history. This method has no side effect.
	 * @param history The turn history to date.
	 * @param hand The player's hand.
	 * @return A Card group to play.
	 */
	CardGroup chooseCards(TurnHistory history, Hand hand);
	ArrayList<Card> getCardsToGiveAway(int n,Hand hand);
}
