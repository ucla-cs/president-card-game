package model;

import java.util.ArrayList;

import util.CardGroup;
import util.Card;

public class Status {
	private static boolean playerChoseCards = false;
	
	private static ArrayList<Card> cardsToDiscardAdv = new ArrayList<Card>();
	
	private static CardGroup cardsToBeat = null;

	private  static CardGroup cardsToPlay = null;

	public static boolean getPlayerChoseCards(){
		return playerChoseCards;
	}
	
	public static CardGroup getCardsToPlay(){
		return cardsToPlay;
	}
	public static void setPlayerChoseCards(boolean b){
		playerChoseCards = b;
	}
	
	public static void setPlayerCardGroup(CardGroup g){
		cardsToPlay = g;
	}

	
	public static void setCardsToBeat(CardGroup g){
		cardsToBeat = g;
	}
	
	public static CardGroup getCardsToBeat(){
		return cardsToBeat;
	}
	public static ArrayList<Card> getCardsToDiscardAdv(){
		return cardsToDiscardAdv;
	}
	public static void setCardsToDiscardAdv(ArrayList<Card> cardList) {
		cardsToDiscardAdv = cardList;
	}
}
