package gui;
import java.awt.*;
import javax.swing.*;
import gui.WinnerIconSize;
import java.awt.event.*;
import java.util.ArrayList;

import gui.titleIcon;
import model.Player;

public class WinnerPanel {
	private ArrayList<Player> players;
	private ArrayList<WinnerIconSize> iconSizes;
	private Player winner;
	final private titleIcon mainMenu = new titleIcon();
	String playerName;
	
	public WinnerPanel(ArrayList<Player> players, String playerName) {
		this.players = players;
		this.playerName = playerName;
		this.winner = this.players.get(0);
		iconSizes = new ArrayList<WinnerIconSize>();
	}
	
	private String setScoreFrame(ArrayList<Player> players) {
		String scoreString = "";
		for (int i = 0; i < players.size(); i++) {
			int totalPoints = players.get(i).getPoints();
			String name = players.get(i).getName();
			if (i == 0) {
				scoreString += "1st Place: " + name + " with " + totalPoints + " points \n";
			}
			else if (i == 1) {
				scoreString += "2nd Place: " + name + " with " + totalPoints + " points \n" ;
			}
			else if (i == 2) {
				scoreString += "3rd Place: " + name + " with " + totalPoints + " points \n";
			}
			else {
				scoreString += "" + (i +1) + "th Place: " + name + " with " + totalPoints + " points \n";			
			}
		}
		return scoreString;
	}
	
	private void getIconSizes() {
		iconSizes.add(new WinnerIconSize(640, 430, "Olivia Wilde"));
		iconSizes.add(new WinnerIconSize(638, 455, "Optimus Prime"));
		iconSizes.add(new WinnerIconSize(604, 560, "Darth Vader"));
		iconSizes.add(new WinnerIconSize(640, 512, "Dr. House"));
		iconSizes.add(new WinnerIconSize(518, 415, "Stephen Colbert"));
		iconSizes.add(new WinnerIconSize(640, 432, "Megan Fox"));
		iconSizes.add(new WinnerIconSize(516, 416, "Taylor Swift"));
		iconSizes.add(new WinnerIconSize(618,433, playerName));
	}
	// this method will return a frame
	public JFrame setWinnerPanel() {
		final JFrame frame = new JFrame("Winner");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		final String scoreString = setScoreFrame(players);
		JButton mainMenuButton = new JButton("Back To Main Menu");
		JButton scoresButton = new JButton("Show Scores");
		buttonPanel.add(mainMenuButton);
		buttonPanel.add(scoresButton);
		JLabel label = new JLabel();
		JLabel blank = new JLabel();
		frame.setLayout(new BorderLayout());
		label.setIcon(winner.getWinnerIcon());
		frame.add(label, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		int width = 0;
		int height = 0;
		getIconSizes();
		for (WinnerIconSize icon : iconSizes) {
			if (icon.getName() == winner.getName()) {
				width = icon.getWidth();
				height = icon.getHeight();
				break;
			}
		}
		frame.setSize(width, height+20);
		frame.setResizable(false);
		frame.setVisible(true);
		scoresButton.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event) {
				ImageIcon icon = new ImageIcon("src/images/seal_icon.png");
				JOptionPane.showMessageDialog(null, scoreString, "Final Scores", JOptionPane.PLAIN_MESSAGE, icon);
			}
		}
		);
		mainMenuButton.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event) {
				frame.setVisible(false);
				GameView.setPlayAgain(true);
				GameView.setDone(true);
				//mainMenu.title();
			}
		}
		);
		return frame;
	}

}
