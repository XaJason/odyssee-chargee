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
	/**
	 * Image de la du panel si ce n'est pas une tuile
	 */
	private Image image;
	/**
	 * Indique si la grille est en mode rotation ou pas
	 */
	private boolean rotation = false;
	/**
	 * Indique si la grille est en mode supprimer
	 */
	private boolean supprimer;
	/** Tuile qui conteint l'instance de la tuile à placer **/
	private Tuile tuile;

	/**
	 * Gère la condition de suppression
	 */
	// Giroux
	public void gererSupprimer() {
		supprimer = !supprimer;
		repaint();
	}

	/**
	 * permet de dessiner la tuile
	 *
	 * @param g contexte graphique
	 */
	// Giroux
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (tuile != null && !rotation && !supprimer) {
			Graphics2D g2dPrive = (Graphics2D) g2d.create();
			g2dPrive.scale((getWidth() / Tuile.getHauteurTuile()) / 2, (getHeight() / Tuile.getHauteurTuile()) / 2);
			g2dPrive.translate(Tuile.getHauteurTuile() / 2, Tuile.getHauteurTuile() / 2);
			tuile.dessiner(g2dPrive, 0, 0);
		}

		else if (rotation) {
			image = OutilsImage.lireImageEtRedimensionner("rotationPostPlacementVert.png", (this.getWidth()),
					(this.getHeight()));
			g2d.drawImage(image, 0, 0, null);
		} else if (supprimer) {
			image = OutilsImage.lireImageEtRedimensionner("supprimer.png", (this.getWidth()), (this.getHeight()));
			g2d.drawImage(image, 0, 0, null);
		}
	}

	/**
	 * Applique un ou plusieurs 16e de rotation dans le sens horaire selon le
	 * facteur
	 *
	 * @param facteur de 16e de rotation dans le sens horaire à appliquer
	 */
	// Jason Xa
	public void rotation(int facteur) {
		if (tuile != null && tuile.getType() != "Vaisseau") {
			tuile.setAngleRotation(tuile.getAngleRotation() + facteur * 0.125 * Math.PI);
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

	/**
	 * Permet de mettre l'état de la fenêtre en suppression
	 *
	 * @param supprimer Vrai si en supprimer, faux sinon
	 *
	 */
	// Giroux
	public void setSupprimer(boolean supprimer) {
		this.supprimer = supprimer;
		repaint();
	}

	/**
	 * Permet de changer le type de la tuile
	 *
	 * @param tuile le type de la nouvelle tuile
	 */
	// Giroux
	public void setTuile(Tuile tuile) {
		this.tuile = tuile;
		repaint();
	}
}
