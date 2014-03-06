package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/*
 * BackgroundJPanel used for background pic inside a JPanel
 * This is done so that we can have one background spanning multiple nested panels which are not opaque.
 */
public class BackgroundJPanel extends JPanel {
		  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Image image;

		  // constructor from string of image
		  public BackgroundJPanel (String image) {
		    this(new ImageIcon(image).getImage());
		  }

		  // constructor for Image (or ImageIcon)
		  public BackgroundJPanel (Image image) {
		    this.image = image;
		    Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
		    setPreferredSize(size); 
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		  }

		  // override paintComponent
		  public void paintComponent(Graphics g) {
		    g.drawImage(image, 0, 0, null);
		  }
}

