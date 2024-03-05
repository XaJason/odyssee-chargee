package utilis;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Cette classe contient des utilitaires pour le
 * traitement des images. Elle sera enrichie au fil de la session.
 * Notez les méthodes permettant de redimensionner une image.
 *
 * @author Caroline Houle
 * @author Merieme Bouisri
 */

public class OutilsImage {

	/**
	 * Lit le fichier d'image donne en parametre et retourne
	 * un objet Image correspondant
	 *
	 * @param nomFichier Le nom du fichier d'image
	 * @return Un objet Image pour cette image
	 */
	// Caroline Houle
	public static Image lireImage(String nomFichier) {
		Image img = null;
		URL urlFichier = OutilsImage.class.getClassLoader().getResource(nomFichier);
		if (urlFichier == null) {
			JOptionPane.showMessageDialog(null, "Fichier d'image introuvable: " + nomFichier);
		} else {
			try {
				img = ImageIO.read(urlFichier);
			} catch (IOException e) {
				System.out.println("Erreur de lecture du fichier d'image: " + nomFichier);
			}
		}
		return (img);
	}// fin methode

	/**
	 * Lit le fichier d'image donne en parametre, redimensionne
	 * l'image en appliquant le meme facteur de redimensionnement en largeur et en
	 * hauteur
	 * (ce qui évite toute distortion dans l'image).
	 * <p>
	 * Retourne un objet Image correspondant.
	 * <p>
	 * Voir aussi la deuxieme signature de cette methode,
	 * qui permet de specifier des resolutions precises en largeur et hauteur.
	 *
	 * @param nomFichier  Le nom du fichier d'image
	 * @param facteurZoom Facteur de redimensionnement (1 signifie ne rien changer)
	 * @return Un objet Image pour cette image redimensionnee
	 */
	// Caroline Houle
	public static Image lireImageEtRedimensionner(String nomFichier, double facteurZoom) {
		Image img = null;
		Image imgRedim = null;

		img = lireImage(nomFichier);
		if (img != null) {
			imgRedim = img.getScaledInstance((int) (facteurZoom * img.getWidth(null)),
					(int) (facteurZoom * img.getHeight(null)), Image.SCALE_SMOOTH);
		}
		return (imgRedim);
	}// fin methode

	/**
	 * Lit le fichier d'image donne en parametre, redimensionne
	 * l'image a la nouvelles resolution desiree.
	 * <p>
	 * Retourne un objet Image correspondant
	 * <p>
	 * Attention : si resoX et resoY ne sont pas proportionnels aux dimensions
	 * initiales de
	 * l'image, cela introduit une distortion (semblera plus etiree dans une
	 * direction)
	 * Voir aussi la deuxieme signature de cette methode qui permet plutot de donner
	 * un facteur de redimensionnement.
	 *
	 * @param nomFichier Le nom du fichier d'image
	 * @param resoX      Nouvelle largeur en pixels
	 * @param resoY      Nouvelle hauteur en pixels
	 * @return Un objet Image pour cette image redimensionnee
	 */
	// Caroline Houle
	public static Image lireImageEtRedimensionner(String nomFichier, int resoX, int resoY) {
		Image img = null;
		Image imgRedim = null;

		img = lireImage(nomFichier);
		if (img != null) {
			imgRedim = img.getScaledInstance(resoX, resoY, Image.SCALE_SMOOTH);
		}
		return (imgRedim);
	}// fin methode

	/**
	 * Associe une image a un bouton en redimensionnant l'image adequatement.
	 *
	 * @param nomFichier Le nom du fichier d'image
	 * @param leBouton   Le bouton auquel on veut associer l'image.
	 */
	// Caroline Houle
	public static void lireImageEtPlacerSurBouton(String nomFichier, JButton leBouton) {
		Image imgRedim = null;

		// lire et redimensionner l'image de la meme grandeur que le bouton
		imgRedim = lireImageEtRedimensionner(nomFichier, leBouton.getWidth(), leBouton.getHeight());

		if (imgRedim != null) {
			// au cas ou le fond de l'image serait transparent
			leBouton.setOpaque(false);
			leBouton.setContentAreaFilled(false);
			leBouton.setBorderPainted(false);

			// associer l'image au bouton
			leBouton.setText("");
			leBouton.setIcon(new ImageIcon(imgRedim));
			leBouton.setBorderPainted(true);

			// on se debarrasse de l'image de base
			imgRedim.flush();
		}
	}// fin methode

	/**
	 * Placer une image existante sur un bouton
	 *
	 * @param image  {@code Image} a placer sur le bouton
	 * @param bouton {@code JButton} sur lequel l'image sera placee
	 */
	// Merieme Bouisri
	public static void placerImageSurBouton(Image image, JButton bouton) {
		Image imgRedim = null;

		imgRedim = image.getScaledInstance(bouton.getWidth(), bouton.getHeight(), Image.SCALE_DEFAULT);

		if (imgRedim == null)
			return;

		bouton.setOpaque(false);
		bouton.setContentAreaFilled(false);
		bouton.setBorderPainted(false);
		bouton.setText("");
		bouton.setIcon(new ImageIcon(imgRedim));
		bouton.setBorderPainted(true);
		imgRedim.flush();
	}

}
