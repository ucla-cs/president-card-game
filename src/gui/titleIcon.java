package gui;
import gui.Settings;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class titleIcon {
	
	private final static Settings setIcon = new Settings();
	
	public titleIcon() {}
	
	public static void title() {
		final JFrame frame = new JFrame("President Card Game");
		BufferedImage image = null;
        try {
           image = ImageIO.read(new File("src/images/back_card.jpg"));
        } catch (IOException e) {
             e.printStackTrace();
        }
        frame.setIconImage(image);
		
		JLabel label = new JLabel();
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event) {
				frame.setVisible(false);
				setIcon.settingsIcon();
			}
		}
		);
		frame.setLayout(new BorderLayout());
		ImageIcon titleIcon = new ImageIcon("President.jpg");
		label.setIcon(titleIcon);
		frame.add(label, BorderLayout.NORTH);
		frame.add(startButton, BorderLayout.SOUTH);
		frame.getContentPane().setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension (600, 450));
		frame.setResizable(false);
		frame.setVisible(true);
	}

}