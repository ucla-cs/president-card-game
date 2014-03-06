package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import robots.BasicAI;

import util.Card;			//ONLY FOR TESTING
import util.CardGroup;		//ONLY FOR TESTING
import util.Card.Rank;
import util.Card.Suit;
import util.Deck.NumberOfJokers;
import util.Deck.NumberOfPacks;

import model.Event;
import model.GameEngine;
import model.Player;
import model.Event.EVENT;

/*
 * This is the main class for the GUI.
 * Subdivided into a Scores Panel, History Panel, PlayerHand Panel, SocialStatus Panel, and the TableView.
 * The TableView itself is further divided into 8 panels for the max players, and a place for the card to beat in the center
 * as well as a place for the discard pile.
 * The player hand panel is held in an internal frame in the south of the game screen.
 * the ScoresPanel, HistoryPanel, and SocialStatusPanel are also created and stored in internal frames.
 * These however, lie in the east sector of the game screen.
 */
public class GameView extends JFrame implements model.Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1618282047503758089L;	//to avoif compiler warning.
	
	
	//Declaration of all the stored fields
	private GameEngine gameEngine;
	private int numberOfPlayers;
	
	//how to keep track of the positions that player views are put into.
	private ArrayList<JPanel> playerPanels; 
	private JPanel position1;
	private JPanel position2;
	private JPanel position3;
	private JPanel position4;
	private JPanel position5;
	private JPanel position6;
	private JPanel position7;
	private JPanel position8;
	
	//the player views whose cardpanels are those that are stored in the various position panels
	private ArrayList<PlayerView> playerViews;
	
	//All of the Container/Observer objects to be utilized throughout the course of a game.
	private ScoresView scoresView;
	private SocialStatusView socialStatusView;
	private HumanPlayerView humanPlayerView;
	private HistoryView historyView;
	private CardsOnTopView cardsOnTopView;
	private DiscardPileView discardPileView;
	
	//the highest of all the JPanels in the GUI, it contains and divides the rest into smaller panels which house the other
	//individual components of the game;
	private JPanel TopView;
	
	//The internal frame with which to hold the players cards.
	private JInternalFrame HumanFrame;
	
	//The four main placeholders that aid in keeping player panels centered around the GUI game-table
	private JPanel northWest;
	private JPanel northEast;
	private JPanel southWest;
	private JPanel southEast;	
	
	private JFrame frame;
	
	private String playerName;
	
	private static boolean done;
	
	private static boolean playAgain;
	
	public static void setPlayAgain(boolean b){
		playAgain =b;
	}
	
	public static boolean isPlayAgain(){
		return playAgain;
	}
	
	public static void setDone(boolean b){
		done =b;
	}
	
	public static boolean isDone(){
		return done;
	}
	
	
	/*
	 * The enormous GameView constructor creates/delegates/ and places all the internal panels where they belong
	 * dividing the main game view into a human panel, the game-table, and the information panels.
	 */
	public GameView( GameEngine gE, String playerName, int numberOfPlayers,int  numberOfJokers, int numberOfDecks, int advantageNumber, int points){
		
		gameEngine = gE;
		this.playerName = playerName;
		
		//convert the passed parameters into the aproppriate enum type
		NumberOfJokers jokers = null;
		if(numberOfJokers==0){jokers = NumberOfJokers.ZERO;}
		else if(numberOfJokers==1){jokers = NumberOfJokers.ONE;}
		else {jokers = NumberOfJokers.TWO;}	
		gameEngine.setNumberOfJokers(jokers);
		
		NumberOfPacks packs = null;
		if(numberOfDecks==1){packs = NumberOfPacks.ONE;}
		else {packs = NumberOfPacks.TWO;}
		gameEngine.setNumberOfPacks(packs);
		
		gameEngine.setNumberOfPlayers(numberOfPlayers);
		gameEngine.setAdvantage(advantageNumber);
		gameEngine.setMaxPoints(points);
		
		this.gameEngine = gE;
		this.numberOfPlayers = numberOfPlayers;	
		
		//These will be the panels that will hold the playerViews.
		playerPanels = new ArrayList<JPanel>();
		
		//Let the Game Engine know who the players are.
		ArrayList<Player> players = createAllPlayers();
		gameEngine.setPlayers(players);		
		
		//create all the player views.
		playerViews = new ArrayList<PlayerView>();
		for(Player p : players){
			playerViews.add(new PlayerView(p));
		}
		
		//The background to be seen throughout the entire game-table as well as behind all the playerViews
		TopView = new BackgroundJPanel(new ImageIcon("src/images/backgroundFinal2.jpg").getImage());
		TopView.setLayout(new BorderLayout());		
		
		HumanFrame = new JInternalFrame("",false,false,false,false);
		
		HumanFrame.setOpaque(false);
		humanPlayerView = new HumanPlayerView(players.get(0));
		HumanFrame.getContentPane().add(humanPlayerView.getCardPanel());
	    HumanFrame.setPreferredSize(new Dimension(1050,200));
		HumanFrame.setVisible(true);
		TopView.add(HumanFrame,BorderLayout.SOUTH);

		frame = new JFrame();
		
		BufferedImage image = null;
        try {
           image = ImageIO.read(new File("src/images/back_card.jpg"));
        } catch (IOException e) {
             e.printStackTrace();
        }
        frame.setIconImage(image);
		
        
        /*
         * Creatings and assigning all the action performed of the JMenuBar that is seen 
         * at all times. Controls the speed/sound/music , shows statics and other useful information.
         */
		JMenuBar menuBar = new JMenuBar();			
		JMenu gameMenu = new JMenu("Game");	
		JMenu helpMenu = new JMenu("Help");				
		JMenuItem aboutUs = new JMenuItem("About Us");
		
		aboutUs.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						JFrame f = new JFrame();
						JTextArea text = new JTextArea("This project was developed By \nAndrew Smart,\n Jordan Catracchia &\n Meki Cherkaoui\n at MCGILL University for Comp 302.\nProf:Martin Robillard");
						text.setEditable(false);
						f.add(text);						
						f.setSize(250,250);
						f.setVisible(true);
					}
				}
				);
		
		helpMenu.add(aboutUs);
		
		JMenuItem instructions = new JMenuItem("Instructions");
		
		instructions.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						try {
	                        Process pc = Runtime.getRuntime().exec("cmd.exe /c start http://www.cs.mcgill.ca/~martin/teaching/comp303-fall-2009/rules.html");
	                    } catch (IOException ex) {
	                        System.out.println(ex.getMessage());
	                        System.out.println();
	                    }
					}
				}
				);
		
		helpMenu.add(instructions);
		
		JMenuItem stats = new JMenuItem("Statistics");
		
		stats.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						String stats = "Note - For the previous positions, the lower the number, the better the finish. \n They go in order from ealiest to most recent, where the number \nrepresents how many positions away from the president they fininshed.\n------------";
						ArrayList<Player> players = gameEngine.getPlayers();
						for(Player p : players){
							stats += "\n\n" + p.getName() + "\nPass Percentage : " +(((double)(p.timesPassed))/(p.timesPassed+p.timesPlayed)) + "\nSets Won : " + p.setsWon + "\nPrevious Finishing Position(s) : ";
							int j = 0;
							for(Integer i : p.prevPositions){
								if( j == p.prevPositions.size()-1){
									stats += i;
								}
								else{
									stats+= i + ", ";
								}
								j++;
							}
						}
						JTextArea textArea = new JTextArea(stats);
						textArea.setEditable(false);
						JFrame f = new JFrame();
						f.add(textArea);
						f.setSize(500, 500);
						f.setVisible(true);
					}
				}
				);
		
		gameMenu.add(stats);
		
		JMenu sub = new JMenu("Sound Effects");		
		JMenu sub1 = new JMenu("Speed");	
		JMenu sub2 = new JMenu("Music");
		
		gameMenu.add(sub1);
		gameMenu.add(sub2);
		
		
		
		final JRadioButtonMenuItem rbOn;

		rbOn = new JRadioButtonMenuItem("Sound ON");

		final JRadioButtonMenuItem rbOff;

		rbOff = new JRadioButtonMenuItem("Sound OFF");

		rbOn.setSelected(true);
		
		rbOn.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						MusicPlayer.setInGameSound(true);
						
						rbOn.setSelected(true);
						rbOff.setSelected(false);
						
					}
				}
				);
		rbOff.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						//MusicPlayer.setInGameSound(false);
						rbOn.setSelected(false);
						rbOff.setSelected(true);
						
					}
				}
			);
		
		sub.add(rbOn);
		sub.add(rbOff);
		
		final JRadioButtonMenuItem rbOn1;

		rbOn1 = new JRadioButtonMenuItem("Music ON");

		final JRadioButtonMenuItem rbOff1;

		rbOff1 = new JRadioButtonMenuItem("Music OFF");

		rbOn1.setSelected(true);
		
		rbOn1.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						//MusicPlayer.setStopPlayBack(true);
						MusicPlayer.playAudio("src/sounds/classical.wav");
						rbOn1.setSelected(true);
						rbOff1.setSelected(false);
						
					}
				}
				);
		rbOff1.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						MusicPlayer.setStopPlayBack(true);
						rbOn1.setSelected(false);
						rbOff1.setSelected(true);
						
					}
				}
			);
		
		sub2.add(rbOn1);
		sub2.add(rbOff1);
		
		final JRadioButtonMenuItem fast;
		fast = new JRadioButtonMenuItem("Fast");

		final JRadioButtonMenuItem medium;
		medium = new JRadioButtonMenuItem("Medium");
		
		final JRadioButtonMenuItem slow;
		slow = new JRadioButtonMenuItem("Slow");
			
		final JRadioButtonMenuItem random;
		random = new JRadioButtonMenuItem("Random");

		random.setSelected(true);
		GameEngine.setRandom(true);
		
		fast.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						GameEngine.setRandom(false);
						GameEngine.setSpeed(500);
						medium.setSelected(false);
						random.setSelected(false);
						fast.setSelected(true);
						slow.setSelected(false);
						
					}
				}
				);
		medium.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						GameEngine.setRandom(false);
						GameEngine.setSpeed(1000);
						medium.setSelected(true);
						random.setSelected(false);
						fast.setSelected(false);
						slow.setSelected(false);
						
					}
				}
			);
		slow.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						GameEngine.setRandom(false);
						GameEngine.setSpeed(2000);
						medium.setSelected(false);
						random.setSelected(false);
						fast.setSelected(false);
						slow.setSelected(true);
						
					}
				}
			);
		random.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						GameEngine.setRandom(true);
						GameEngine.setSpeed(1000);
						medium.setSelected(false);
						random.setSelected(true);
						fast.setSelected(false);
						slow.setSelected(false);
						
					}
				}
			);
		sub1.add(medium);
		sub1.add(fast);
		sub1.add(slow);
		sub1.add(random);
		
		gameMenu.add(sub);
		JMenuItem quit = new JMenuItem("Quit");
	
		quit.setMnemonic(KeyEvent.VK_Q);
		
		
		quit.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event) {
						System.exit(0);
					}
				}
				);
	
		gameMenu.addSeparator();		
		gameMenu.add(quit);		
		menuBar.add(gameMenu);		
		menuBar.add(helpMenu);		
		frame.setJMenuBar(menuBar);
		
		
		
		frame.setBackground(Color.BLUE);
	    frame.setTitle("President");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	    
	    frame.add(TopView);
	    	
	    //instantiating the observers
	    scoresView = new ScoresView(gE.getMaxPoint());
		JPanel ScoresPanel = scoresView.getPanel();
		ScoresPanel.setOpaque(false);		
		
		socialStatusView = new SocialStatusView(numberOfPlayers);
		JPanel SocialStatus = socialStatusView.getSocialStatusPanel();
		SocialStatus.setOpaque(false);	
		
		historyView = new HistoryView();
		JPanel PlayHistory = historyView.getPanel();
		PlayHistory.setOpaque(false);	
		
		JPanel PlayField = new JPanel();
		PlayField.setOpaque(false);
		PlayField.setLayout(new BorderLayout());
		
		ImageIcon tableIcon = new ImageIcon("BasicTable.jpg");
		JLabel tableLabel = new JLabel();
		tableLabel.setIcon(tableIcon);
		
		//NORTH
		JPanel North = new JPanel();
		North.setOpaque(false);
		North.setLayout(new BoxLayout(North,BoxLayout.X_AXIS));
		
			JPanel TopLeft = new JPanel();	
			TopLeft.setOpaque(false);
			TopLeft.setLayout(new BoxLayout(TopLeft,BoxLayout.X_AXIS));
			JPanel TopRight = new JPanel();
			TopRight.setOpaque(false);
			TopRight.setLayout(new BoxLayout(TopRight,BoxLayout.X_AXIS));
			
				northWest = new JPanel();
				northWest.setOpaque(false);
				northWest.setPreferredSize(new Dimension((numberOfPlayers>=5? 200 : 400),120));
				
				position1 = new JPanel();
				position1.setOpaque(false);
				position1.setPreferredSize(new Dimension(250,120));
				position1.setLayout(new BorderLayout());
				//position1.setOpaque(true);
				playerPanels.add(position1);
				
				position2 = new JPanel();
				position2.setOpaque(false);
				position2.setPreferredSize(new Dimension(250,120));
				position2.setLayout(new BorderLayout());
				if(numberOfPlayers >= 5){playerPanels.add(position2);}
				
				northEast = new JPanel();
				northEast.setOpaque(false);
				northEast.setPreferredSize(new Dimension(200,120));
				
				North.add(northWest);
				North.add(position1);
				North.add(position2);
				North.add(northEast);
		
		//EAST
		JPanel East = new JPanel();
		East.setOpaque(false);
		East.setLayout(new BoxLayout(East,BoxLayout.Y_AXIS));
		
		position3 = new JPanel();
		position3.setOpaque(false);
		position3.setPreferredSize(new Dimension(230,150));
		position3.setLayout(new BorderLayout());
		playerPanels.add(position3);
		
		position4 = new JPanel();
		position4.setOpaque(false);
		position4.setPreferredSize(new Dimension(230,150));
		position4.setLayout(new BorderLayout());
		if(numberOfPlayers >= 6){playerPanels.add(position4);}
		
		East.add(position3);
		East.add(position4);
			
		//SOUTH
		JPanel South = new JPanel();
		South.setOpaque(false);
		South.setLayout(new BoxLayout(South,BoxLayout.X_AXIS));
		
			JPanel BottomLeft = new JPanel();	
			BottomLeft.setOpaque(false);
			BottomLeft.setLayout(new BoxLayout(BottomLeft,BoxLayout.X_AXIS));
			JPanel BottomRight = new JPanel();
			BottomRight.setOpaque(false);
			BottomRight.setLayout(new BoxLayout(BottomRight,BoxLayout.X_AXIS));
		
					position5 = new JPanel();
					position5.setOpaque(false);
					position5.setPreferredSize(new Dimension(250,120));
					position5.setLayout(new BorderLayout());
					playerPanels.add(position5);
					
					southWest = new JPanel();
					southWest.setOpaque(false);
					southWest.setPreferredSize(new Dimension((numberOfPlayers>=7? 200 : 400),120));
					
					position6 = new JPanel();
					position6.setOpaque(false);
					position6.setPreferredSize(new Dimension(250,120));
					position6.setLayout(new BorderLayout());
					if(numberOfPlayers >= 7){playerPanels.add(position6);}
					
					southEast = new JPanel();
					southEast.setOpaque(false);
					southEast.setPreferredSize(new Dimension(200,120));
					
					South.add(southWest);
					if(numberOfPlayers >= 7){South.add(position6);}
					South.add(position5);
					if(numberOfPlayers < 7){South.add(position6);}
					South.add(southEast);		
			
		//WEST
		
		JPanel West = new JPanel();
		West.setOpaque(false);
		West.setLayout(new BoxLayout(West,BoxLayout.Y_AXIS));
		
		position7 = new JPanel();
		position7.setOpaque(false);
		position7.setPreferredSize(new Dimension(230,150));
		position7.setLayout(new BorderLayout());
		playerPanels.add(position7);
		
		position8 = new JPanel();
		position8.setOpaque(false);
		position8.setPreferredSize(new Dimension(230,150));
		position8.setLayout(new BorderLayout());
		if(numberOfPlayers == 8){playerPanels.add(position8);}
		//position 8 will be added or not there are 8 players, it the 2nd case however, it is simply a placeholder
		if(numberOfPlayers == 8){West.add(position8);}
		West.add(position7);
		if(numberOfPlayers!=8){West.add(position8);}
				
		PlayField.add(North,BorderLayout.NORTH);
		PlayField.add(South,BorderLayout.SOUTH);
		PlayField.add(East,BorderLayout.EAST);
		PlayField.add(West,BorderLayout.WEST);
		
		cardsOnTopView = new CardsOnTopView();	
		discardPileView = new DiscardPileView();
		
		JPanel cardsPanel = new BackgroundJPanel(new ImageIcon("src/images/table2.png").getImage());
		cardsPanel.setOpaque(false);
		cardsPanel.setLayout(new BoxLayout(cardsPanel,BoxLayout.X_AXIS));
		cardsPanel.add(cardsOnTopView.getCardsOnTop());
		cardsPanel.add(discardPileView.getDiscardPileView());
			
		PlayField.add(cardsPanel);
		PlayField.validate();
		
		JPanel MainEast = new JPanel();
		MainEast.setOpaque(false);
		MainEast.setPreferredSize(new Dimension(250,500));				//to stop it from shifting all the time due to the history view's dynamic size.
		
		MainEast.setLayout(new BoxLayout(MainEast,BoxLayout.Y_AXIS));
		MainEast.add(SocialStatus);
		MainEast.add(ScoresPanel);
		MainEast.add(PlayHistory);
		
		TopView.add(MainEast,BorderLayout.EAST);
		TopView.add(PlayField,BorderLayout.CENTER);
		TopView.validate();
		
		addAllObservers(players);
		
		frame.add(TopView);	//don't need to specify frame.
		frame.setSize(new Dimension(1050, 735));

	}


	/*
	 * Creates and returns the list of players to be used.
	 */
	private ArrayList<Player> createAllPlayers(){
		
		Player darthVader = new Player("Darth Vader", "src/images/vader_icon.jpg", "src/images/vader_winner2.jpg");
		Player oliviaWilde = new Player("Olivia Wilde", "src/images/wilde_icon.jpg", "src/images/wilde_winner2.jpg");
		Player optimusPrime = new Player("Optimus Prime", "src/images/prime_icon.jpg", "src/images/prime_winner2.jpg");
		Player stephenColbert = new Player("Stephen Colbert", "src/images/colbert_icon.jpg", "src/images/colbert_winner2.jpg");
		Player meganFox = new Player("Megan Fox", "src/images/fox_icon.jpg", "src/images/fox_winner2.jpg");
		Player houseMD = new Player("Dr. House", "src/images/house_icon.jpg", "src/images/house_winner2.jpg");
		Player taylorSwift = new Player("Taylor Swift", "src/images/swift_icon.jpg", "src/images/swift_winner2.jpg");
		
		darthVader.setAI(new BasicAI("Darth Vader"));
		oliviaWilde.setAI(new BasicAI("Olivia Wilde"));
		optimusPrime.setAI(new BasicAI("Optimus Prime"));
		stephenColbert.setAI(new BasicAI("Stephen Colbert"));
		meganFox.setAI(new BasicAI("Megan Fox"));
		houseMD.setAI(new BasicAI("Dr. House"));
		taylorSwift.setAI(new BasicAI("Taylor Swift"));
		
		Player humanPlayer = new Player(playerName, "src/images/human_icon.jpg", "src/images/human_winner.jpg");
		
		ArrayList<Player> possibleAI = new ArrayList<Player>();
		possibleAI.add(darthVader);
		possibleAI.add(oliviaWilde);
		possibleAI.add(optimusPrime);
		possibleAI.add(stephenColbert);
		possibleAI.add(meganFox);
		possibleAI.add(houseMD);
		possibleAI.add(taylorSwift);
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(humanPlayer);	//the human player will always be the first in this array
		Random r = new Random();
		for(int i = 1 ; i < numberOfPlayers; i++){
			players.add(possibleAI.remove(r.nextInt(possibleAI.size())));
		}	
		return players;
	}
	
	//adds the 6 main observes, and 4-8 others depending on the number of players.
	private void addAllObservers(ArrayList<Player> players){
		//add the info panels
		gameEngine.addObserver(scoresView);
		gameEngine.addObserver(socialStatusView);
		gameEngine.addObserver(historyView);
		
		//add the playfield panels
		gameEngine.addObserver(cardsOnTopView);
		gameEngine.addObserver(discardPileView);
		
		//add the human interaction panel
		gameEngine.addObserver(humanPlayerView);
		
		//add all the player panels depending on how many players there are.
		for(PlayerView pV : playerViews){
			//JOptionPane.showMessageDialog(null, "Adding as an observer the player : " + pV.getPlayer().getName());
			gameEngine.addObserver(pV);
		}
	}
	
	//the update method of this game view itself as an observer functions mainly to move the
	// playerviews around the table depending on their finishing place
	public void update(Event e){
			
		if(e.getEvent()==Event.EVENT.newGame){
			ArrayList<Player> players = e.getPlayers();
			Collections.shuffle(players);	//to randomize their starting order
			e.setPlayers(players);
			rearrangeViews(e);
			
			frame.setVisible(true);
			
			MusicPlayer.playAudio("src/sounds/classical.wav");
		}
		else if(e.getEvent()==Event.EVENT.turnOver){
			rearrangeViews(e);
		}
		else if(e.getEvent()==Event.EVENT.gameOver){
			if(e.getPlayers().get(0).getAI()==null){
				//MusicPlayer.playAudio("src/sounds/applause.wav");
			}
			else{
				//MusicPlayer.playAudio("src/sounds/looser.wav");
			}
			frame.setVisible(false);
			WinnerPanel winnerPanel = new WinnerPanel(e.getPlayers(),playerName);
			winnerPanel.setWinnerPanel();
			MusicPlayer.setStopPlayBack(true);
		}
		
		else if(e.getEvent()==Event.EVENT.needHumanPlay||e.getEvent()==Event.EVENT.deal || e.getEvent()==EVENT.chooseAdvantageCards){		
			HumanFrame.getContentPane().invalidate();
			HumanFrame.getContentPane().removeAll();
			HumanFrame.getContentPane().add(humanPlayerView.getCardPanel());
			HumanFrame.getContentPane().validate();
		}
		else if(e.getEvent()==Event.EVENT.deal){
			
			MusicPlayer.playMusic("src/sounds/shuffling-cards-1.wav");
			
		}
		else if(e.getEvent()==Event.EVENT.playCardGroup){
			if(e.getCardGroup().size()==0){
				MusicPlayer.playMusic("src/sounds/button.wav");
			}
			else{
				MusicPlayer.playRandomCard();
			}		
		}
	}
	
	/*
	 * Rearranges all the playerView's into the positions that they belong in according to social status
	 */
	private void rearrangeViews(Event e){
			ArrayList<Player> players = e.getPlayers();
			int i = 0;	//the player panel index that the next player should be placed in
			for(JPanel jP : playerPanels){
				Player p = players.get(i);	//The player whose view should be put into the current panel in playerPanel
				for(PlayerView pV : playerViews){
					if(pV.getPlayer().getName().equals(p.getName())){
						//then this is the player view which should be contained by the jpanel
						jP.add(pV.getCardPanel(),BorderLayout.CENTER);
					}
				}
				i++;
			}
			frame.validate();
		}			
}
