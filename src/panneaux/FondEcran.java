package panneaux;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import utilitaires.OutilsImage;

/**
 * Classe qui gère les fond d'écrans
 *
 * @author Kitimir Yim
 */
public class FondEcran extends JPanel {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = -6371823752957467232L;
	/**
	 * Image du fond d'écran
	 */
	private Image fondImage;

	/**
	 * Constructeur du fond d'écran
	 *
	 * @param fond    nom du fichier contenant l'image
	 * @param facteur facteur de redimensionnement
	 */
	// Kitimir Yim
	public FondEcran(String fond, double facteur) {
		fondImage = OutilsImage.lireImageEtRedimensionner(fond, facteur);
	}

	/**
	 * Permet de dessiner l'image
	 *
	 * @param g Le contexte graphique
	 */
	// Kitimir Yim
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(fondImage, 0, 0, this);
	}
}
