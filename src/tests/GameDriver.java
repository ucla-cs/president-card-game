package tests;

import model.GameEngine;

/**
 * Highly incomplete basic example of how to go about implementing 
 * the test driver.
 */
public class GameDriver
{
	private static final int DEFAULT_ITERATIONS = 100;
	
	public static void main( String[] args )
	{
		/*
		GameEngine lEngine = new GameEngine();
		int lIterations = DEFAULT_ITERATIONS;
		int stupid1 =0;
		int stupid2 = 0;
		int smart1 = 0;
		int smart2 = 0;
		
		if( args.length == 1 )
		{
			lIterations = Integer.parseInt(args[0]);
		}
		for( int i = 0; i < lIterations; i++ )
		{
			lEngine.newGame();
			System.out.println("Game"+i+",Winner is");
			if( lEngine.getWinner().getName().equals("smartAI1") ){
				smart1++;
				System.out.print("smartAI1");
			}
			else if( 
				lEngine.getWinner().getName().equals("smartAI2") ){
				smart2++;
				System.out.print("smartAI2");
			}
			else if( lEngine.getWinner().getName().equals("dumbAI1") ){
				stupid1++;
				System.out.print("dumbAI1");
			}
			else if( lEngine.getWinner().getName().equals("dumbAI2") ){
				stupid2++;
				System.out.print("dumbAI2");
			}
		}
		System.out.println("Advanced AI 1 won " + smart1 + " games");
		System.out.println("Advanced AI 2 won " + smart2 + " games");
		System.out.println("Dumb     AI 1 won " + stupid1 + " games");
		System.out.println("Dumb     AI 2 won " + stupid2 + " games");
		
		System.out.println("Total for Advanced AI is " + (smart1 + smart2)+ " games");
		System.out.println("Total for Dumb     AI is " + (stupid1 + stupid2)+ " games");
		System.out.println( lIterations + " game(s) played");
		// Printout additional info
		 * *
		 */
	}
}
