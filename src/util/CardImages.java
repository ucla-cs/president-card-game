package util;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * A class to store and manage images of the 52 cards.
 */
public final class CardImages 
{
	private static final String IMAGE_LOCATION = "images/";
	private static final String IMAGE_SUFFIX = ".gif";
	private static final String[] RANK_CODES = {"3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k", "a", "2", "x"};
	private static final String[] SUIT_CODES = {"c", "d", "h", "s", "x"};	
	
	private static Map<String, ImageIcon> gCards = new HashMap<String, ImageIcon>();
	
	private CardImages()
	{}
	
	/**
	 * Return the image of a card.
	 * @param pCard the target card
	 * @return An icon representing the chosen card.
	 */
	public static ImageIcon getCard(Card pCard)
	{
		if(pCard.isJoker())
		{
			return getJoker();
		}
		else
		{
			return getCard(getCode(pCard));
		}
	}
	
	/**
	 * Return an image of the back of a card.
	 * @return An icon representing the back of a card.
	 */
	public static ImageIcon getBack()
	{
		//return getCard("images/back_card_big.jpg");
		//return new ImageIcon("images/back_card_big.jpg");
		ImageIcon lIcon = new ImageIcon(CardImages.class.getClassLoader().getResource("images/back_card_big.jpg"));
		return lIcon;
	}
	
	/**
	 * Return an image of the joker.
	 * @return An icon representing the joker.
	 */
	public static ImageIcon getJoker()
	{
		return getCard("j");
	}
	
	private static String getCode(Card pCard)
	{
		return RANK_CODES[ pCard.getRank().ordinal() ] + SUIT_CODES[ pCard.getSuit().ordinal() ];		
	}
	
	private static ImageIcon getCard(String pCode)
	{
		ImageIcon lIcon = (ImageIcon) gCards.get(pCode);
		if(lIcon == null)
		{
			lIcon = new ImageIcon(CardImages.class.getClassLoader().getResource(IMAGE_LOCATION + pCode + IMAGE_SUFFIX));
			gCards.put(pCode, lIcon);
		}
		return lIcon;
	}
}
