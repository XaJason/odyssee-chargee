package panneaux;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import tuile.Tuile;

public class PanelTuileTemp extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2236980579144572642L;
	private Tuile tuile;
	//private Tuile tuileTemp;

	/**
	 * Create the panel.
	 */
	public PanelTuileTemp() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (tuile != null) {
			tuile.dessiner(g2d);
		}

	}

	public void setTuile(Tuile tuile) {
		this.tuile = tuile;
		repaint();
		
	}
	/**
	 * Applique un quart de rotation horaire à la tuile sélectionnée
	 */
	// Jason Xa
	public void rotation() {
		tuile.setAngleRotation(tuile.getAngleRotation() + 0.5 * Math.PI);
		repaint();
	}


}
