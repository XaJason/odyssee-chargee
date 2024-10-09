package utilitaires;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import tuile.Carre;
import tuile.Drapeau;
import tuile.Pics;
import tuile.Portail;
import tuile.TriangleEquilateral;
import tuile.TriangleRectangle;
import tuile.VaisseauImage;

/**
 * Cette classe contient des utilitaires pour le traitement des images. Elle
 * sera enrichie au fil de la session. Notez les méthodes permettant de
 * redimensionner une image.
 *
 * @author Caroline Houle
 * @author Enuel René Valentin Kizozo Izia
 */
public class OutilsImage {

	/**
	 * Lit le fichier d'image donne en paramètre et retourne un objet Image
	 * correspondant
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

			}
		}
		return (img);
	}

	/**
	 * Associe une image à un bouton en redimensionnant l'image adéquatement.
	 *
	 * @param nomFichier Le nom du fichier d'image
	 * @param leBouton   Le bouton auquel on veut associer l'image.
	 */
	// Caroline Houle
	public static void lireImageEtPlacerSurBouton(String nomFichier, JButton leBouton) {
		Image imgRedim = null;

		// lire et redimensionner l'image de la même grandeur que le bouton
		imgRedim = lireImageEtRedimensionner(nomFichier, leBouton.getWidth(), leBouton.getHeight());

		if (imgRedim != null) {
			// au cas où le fond de l'image serait transparent
			leBouton.setOpaque(false);
			leBouton.setContentAreaFilled(false);
			leBouton.setBorderPainted(false);

			// associer l'image au bouton
			leBouton.setText("");
			leBouton.setIcon(new ImageIcon(imgRedim));
			leBouton.setBorderPainted(true);

			// on se débarrasse de l'image de base
			imgRedim.flush();
		}
	}

	/**
	 * Associe une image a un bouton en redimensionnant l'image adéquatement.
	 *
	 * @param nomFichier Le nom du fichier d'image
	 * @param leBouton   Le bouton auquel on veut associer l'image.
	 */
	// Caroline Houle
	public static void lireImageEtPlacerSurBouton(String nomFichier, JToggleButton leBouton) {
		Image imgRedim = null;

		// lire et redimensionner l'image de la même grandeur que le bouton
		imgRedim = lireImageEtRedimensionner(nomFichier, leBouton.getWidth(), leBouton.getHeight());

		if (imgRedim != null) {
			// au cas où le fond de l'image serait transparent
			leBouton.setOpaque(false);
			leBouton.setContentAreaFilled(false);
			leBouton.setBorderPainted(false);

			// associer l'image au bouton
			leBouton.setText("");
			leBouton.setIcon(new ImageIcon(imgRedim));
			leBouton.setBorderPainted(true);

			// on se débarrasse de l'image de base
			imgRedim.flush();
		}
	}

	/**
	 * Lit le fichier d'image donne en paramètre, redimensionne l'image en
	 * appliquant le même facteur de redimensionnement en largeur et en hauteur (ce
	 * qui évite toute distortion dans l'image).
	 * <p>
	 * Retourne un objet Image correspondant.
	 * <p>
	 * Voir aussi la deuxième signature de cette méthode, qui permet de spécifier
	 * des résolutions précises en largeur et hauteur.
	 *
	 * @param nomFichier  Le nom du fichier d'image
	 * @param facteurZoom Facteur de redimensionnement (1 signifie ne rien changer)
	 * @return Un objet Image pour cette image redimensionnée
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
	}

	/**
	 * Lit le fichier d'image donné en paramètre, redimensionne l'image à la
	 * nouvelles résolution désirée.
	 * <p>
	 * Retourne un objet Image correspondant
	 * <p>
	 * Attention : si resoX et resoY ne sont pas proportionnels aux dimensions
	 * initiales de l'image, cela introduit une distortion (semblera plus etirée
	 * dans une direction) Voir aussi la deuxième signature de cette méthode qui
	 * permet plutôt de donner un facteur de redimensionnement.
	 *
	 * @param nomFichier Le nom du fichier d'image
	 * @param resoX      Nouvelle largeur en pixels
	 * @param resoY      Nouvelle hauteur en pixels
	 * @return Un objet Image pour cette image redimensionnée
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
	}

	/**
	 * Lit, redimensionne et définit l'image pour chaque type de tuile
	 *
	 * @param largeurCase    La largeur de la case de la tuile
	 * @param hauteurCase    La hauteur de la case de la tuile
	 * @param pixelsParMetre Le nombre de pixels par mètre
	 */
	// Enuel René Valentin Kizozo Izia
	public static void lireImagesDesTuiles(double largeurCase, double hauteurCase, double pixelsParMetre) {
		Carre.setImageRef("carre.jpg", (int) (largeurCase * pixelsParMetre), (int) (hauteurCase * pixelsParMetre));
		TriangleEquilateral.setImageRef("triangle_equilateral.png", (int) (largeurCase * pixelsParMetre),
				(int) (hauteurCase * pixelsParMetre));
		TriangleRectangle.setImageRef("triangle_rectangle.png", (int) (largeurCase * pixelsParMetre),
				(int) (hauteurCase * pixelsParMetre));
		Portail.setImageRef("portail.png", (int) (largeurCase * pixelsParMetre), (int) (hauteurCase * pixelsParMetre));
		Drapeau.setImageRef("drapeau.png", (int) (largeurCase * pixelsParMetre), (int) (hauteurCase * pixelsParMetre));
		Pics.setImageRef("pics.png", (int) (largeurCase * pixelsParMetre),
				(int) ((hauteurCase / 2.0) * pixelsParMetre));
		VaisseauImage.setImageRef("vaisseau.png", (int) ((largeurCase / 2.0) * pixelsParMetre),
				(int) ((hauteurCase / 2.0) * pixelsParMetre));
	}
}
