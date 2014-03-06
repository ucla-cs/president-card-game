package util;

/**
 * An immutable description of a playing card.
 */
public final class Card implements Comparable<Card>
{
	/**
	 * Enum type representing the rank of the card.
	 */
	public enum Rank 
	{THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE, TWO}
	
	/**
	 * Enum type representing the suit of the card.
	 */
	public enum Suit 
	{ CLUBS, DIAMONDS, HEARTS, SPADES }
	
	private final Rank fRank;
	private final Suit fSuit;

	/**
	 * Create a new card object (not a Joker). 
	 * @param rank The rank of the card.
	 * @param suit The suit of the card.
	 */
	public Card(Rank rank, Suit suit)
	{
		fRank = rank;
		fSuit = suit;
	}
	
	/**
	 * Create a Joker.
	 */
	public Card()
	{
		fRank = null;
		fSuit = null;
	}
	
	/**
	 * Obtain the rank of the card.
	 * @return An object representing the rank of the card, or null if a Joker.
	 */
	public Rank getRank()
	{
		return fRank;
	}
	
	/**
	 * Obtain the suit of the card.
	 * @return An object representing the suit of the card, or null if a Joker.
	 */
	public Suit getSuit()
	{
		return fSuit;
	}
	
	/**
	 * @return True if this card is a Joker
	 */
	public boolean isJoker()
	{
		return fSuit == null;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * @return See above.
	 */
	public String toString()
	{
		if(fSuit == null)
		{
			return "JOKER";
		}
		else
		{
			return fRank + " of " + fSuit;
		}
	}
	
	/**
	 * Compares two cards according to game rules (two is high, suits 
	 * are equal, jokers beat everything.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @param card The card to compare to
	 * @return Returns a negative integer, zero, or a positive integer 
	 * as this object is less than, equal to, or greater than pCard
	 */
	public int compareTo(Card card)
	{
		int lReturn = 0;
		
		if(isJoker())
		{
			if(card.isJoker())
			{
				lReturn = 0;
			}
			else
			{
				lReturn = 1;
			}
		}
		else
		{		
			if(card.isJoker())
			{
				lReturn = -1;
			}
			else
			{
				lReturn = getRank().ordinal() - card.getRank().ordinal();
			}
		}
		return lReturn;
	}

	/**
	 * Two cards are equal if they have the same suit and rank.
	 * Note that this is different from the behavior or compareTo, 
	 * which returns 0 (equal) independently of the suits.
	 * @param gCards The card to test.
	 * @return true if the two cards are equal
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object gCards) 
	{
		if(gCards == null)
		{
			return false;
		}
		if(gCards == this)
		{
			return true;
		}
		if(gCards.getClass() != getClass())
		{
			return false;
		}
		return (((Card)gCards).getRank() == getRank()) && (((Card)gCards).getSuit() == getSuit());
	}

	/** 
	 * The hashcode for a card is the suit*13 + that of the rank (perfect hash).
	 * @return the hashcode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		if(isJoker())
		{
			return "JOKER".hashCode();
		}
		else
		{
			return getSuit().ordinal() * Rank.values().length + getRank().ordinal();
		}
	}
}
