package panneaux;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import tuile.Tuile;


/**
 * Classe qui permet d'afficher la tuile sélectionner sous forme de fenêtre
 *  @author Giroux
 */
public class PanelTuileTemp extends JPanel {

	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 2236980579144572642L;
	/** Tuile qui conteint l'instance de la tuile à placer **/
	private Tuile tuile;

	/**
	 * Création de la fenêtre
	 */
	public PanelTuileTemp() {
		
	}
	/**
	 * permet de dessiner la tuile
	 * @param g contexte graphique
	 */
	//Giroux
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (tuile != null) {
			g2d.scale(getWidth() / Tuile.getHauteurTuile(), getHeight() / Tuile.getHauteurTuile());
			tuile.dessiner(g2d, 0, 0);
		}

	}
	/**
	 * Permet de changer le type de la tuile
	 * @param tuile
	 */
	//Giroux
	public void setTuile(Tuile tuile) {
		this.tuile = tuile;
		repaint();
	}

	/**
	 * Applique un quart de rotation horaire à la tuile sélectionnée
	 */
	// Giroux
	public void rotation() {
		tuile.setAngleRotation(tuile.getAngleRotation() + 0.5 * Math.PI);
		repaint();
	}

}
