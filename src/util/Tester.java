package util;

import java.util.Random;

import javax.swing.JOptionPane;


/**
 * Test driver for GUI. Run main method to make sure a card matching the label appear.
 * @author martin
 */
public final class Tester 
{
	private Tester()
	{}
	
	/**
	 * Running this should pop a random card on the screen matching the label.
	 * @param pArgs Nothing goes here.
	 */
	public static void main(String[] pArgs)
	{
		Random random = new Random();
		Card card = new Card();          // Creates a joker
		if(random.nextInt(54) < 52)       // In 52/54 cases we'll pick a different card.
			card = new Card( Card.Rank.values()[random.nextInt(Card.Rank.values().length)],
					         Card.Suit.values()[random.nextInt(Card.Suit.values().length)]);
		System.out.println(card);
		JOptionPane.showMessageDialog(null, card, card.toString(), JOptionPane.INFORMATION_MESSAGE, CardImages.getCard(card));
	}
}
