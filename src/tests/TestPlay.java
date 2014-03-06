package tests;

import static org.junit.Assert.assertEquals;
/*
import static tests.AllCards.a3C;
import static tests.AllCards.aAC;
import static tests.AllCards.aJC;
import static tests.AllCards.aJOKER;
import static tests.AllCards.aKS;
import static tests.AllCards.aQD;
import static tests.AllCards.aQH;
import static tests.AllCards.aTC;
*/
import org.junit.Test;
import model.Play;
import util.CardGroup;
public class TestPlay
{
	@Test
	public void testPlay()
	{
		CardGroup g = new CardGroup();
		Play p = new Play("BOB",g );
		
		assertEquals( "BOB", p.getPlayerID());
		assertEquals( g , p.getCardGroup());
		
	}
	
}
	