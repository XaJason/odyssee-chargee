package panneaux;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import tuile.Tuile;
import utilitaires.OutilsImage;

/**
 * Classe qui permet d'afficher la tuile sélectionner sous forme de fenêtre
 * 
 * @author Giroux
 * @author Jason Xa
 */
public class PanelTuileTemp extends JPanel {

	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 2236980579144572642L;
	/** Tuile qui conteint l'instance de la tuile à placer **/
	private Tuile tuile;
	/**
	 * Image de la rotation
	 */
	private Image image;
	/**
	 * Indique si la grille est en mode application ou pas
	 */
	private boolean rotation = false;

	/**
	 * Création de la fenêtre
	 */
	public PanelTuileTemp() {

	}

	/**
	 * permet de dessiner la tuile
	 * 
	 * @param g contexte graphique
	 */
	// Giroux
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (tuile != null) {
			Graphics2D g2dPrive = (Graphics2D) g2d.create();
			g2dPrive.scale(getWidth() / Tuile.getHauteurTuile(), getHeight() / Tuile.getHauteurTuile());
			tuile.dessiner(g2dPrive, 0, 0);
		}

		if (rotation) {
			image = OutilsImage.lireImageEtRedimensionner("rotationPostPlacementVert.png", (int) (this.getWidth()),
					(int) (this.getHeight()));
			g2d.drawImage(image, 0, 0, null);
		}
	}

	/**
	 * Permet de changer le type de la tuile
	 * 
	 * @param tuile
	 */
	// Giroux
	public void setTuile(Tuile tuile) {
		this.tuile = tuile;
		repaint();
	}

	/**
	 * Applique un quart de rotation horaire à la tuile sélectionnée selon le signe
	 * de l'argument
	 * 
	 * @param signum 1 si positif, 0 si nul, -1 si négatif
	 */
	// Jason Xa
	public void rotation(int signum) {
		if (tuile != null && tuile.getType() != "Vaisseau") {
			tuile.setAngleRotation(tuile.getAngleRotation() + signum * 0.5 * Math.PI);
			repaint();
		}
	}

	/**
	 * Permet de mettre l'état de la fenêtre en rotation
	 * 
	 * @param rotation Vrai si en rotation, faux sinon
	 */
	// Giroux
	public void setRotation(boolean rotation) {
		this.rotation = rotation;
		repaint();
	}

}
