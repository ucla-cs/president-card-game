package model;

import util.CardGroup;

/**
 * Models the play of a card group by a player.
 * Immutable.
 */
public final class Play 
{
	private String fPlayerID;
	private CardGroup fCardGroup;
	
	/**
	 * Constructs a new play.
	 * @param playerID The player ID
	 * @param group The group of cards to play
	 */
	public Play(String playerID, CardGroup group)
	{
		fPlayerID = playerID;
		fCardGroup = group;
	}
	
	/**
	 * @return The player ID
	 */
	public String getPlayerID()
	{
		return fPlayerID;
	}
	
	/**
	 * @return The CardGroup for this play.
	 */
	public CardGroup getCardGroup()
	{
		return fCardGroup;
	}
	
	/**
	 * @return True if this play contains a card group
	 * of size 0.
	 */
	public boolean isPass()
	{
		return fCardGroup.size() == 0;
	}
}
