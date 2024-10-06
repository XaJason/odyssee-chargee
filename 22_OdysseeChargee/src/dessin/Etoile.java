package dessin;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import utilitaires.Dessinable;

/**
 * Classe qui represente une etoile a 5 pointes. On specifie les rayons
 * exterieur et interieur imaginaires sur lesquels les pointes de l'etoile
 * seront placees.
 *
 * @author Caroline Houle
 * @author Nils Lahaye
 */
public class Etoile implements Dessinable {

	/**
	 * Demi etoile
	 */
	private boolean demiEtoile = false;
	/**
	 * Path de l'etoile
	 */
	private Path2D.Double pathEtoile;
	/**
	 * Rayon exterieur
	 */
	private double rayonExterieur;
	/**
	 * Rayon interieur
	 */
	private double rayonInterieur;
	/**
	 * Position x
	 */
	private double x;
	/**
	 * Position y
	 */
	private double y;

	/**
	 * Construire une etoile a cinq pointes, en specifiant les rayons exterieur et
	 * interieur imaginaires sur lesquels les pointes seront placees.
	 *
	 * @param x              Le x du coin superieur-gauche du rectangle englobant
	 *                       l'etoile
	 * @param y              Le y du coin superieur-gauche du rectangle englobant
	 *                       l'etoile
	 * @param rayonExterieur Rayon du cercle sur lequel les pointes exterieures
	 *                       seront disposees
	 * @param rayonInterieur Rayon du cercle sur lequel les pointes interieures
	 *                       seront disposees
	 */
	// Caroline Houle
	public Etoile(double x, double y, double rayonExterieur, double rayonInterieur) {
		this.x = x;
		this.y = y;
		this.rayonExterieur = rayonExterieur;
		this.rayonInterieur = rayonInterieur;

		creerLaGeometrie();
	}

	/**
	 * Construire une etoile a cinq pointes, en specifiant les rayons exterieur et
	 * interieur imaginaires sur lesquels les pointes seront placees.
	 *
	 * @param x              Le x du coin superieur-gauche du rectangle englobant
	 *                       l'etoile
	 * @param y              Le y du coin superieur-gauche du rectangle englobant
	 *                       l'etoile
	 * @param rayonExterieur Rayon du cercle sur lequel les pointes exterieures
	 *                       seront disposees
	 * @param rayonInterieur Rayon du cercle sur lequel les pointes interieures
	 *                       seront disposees
	 * @param demiEtoile     Si vrai, l'etoile sera dessinee a moitie
	 */
	// Caroline Houle
	public Etoile(double x, double y, double rayonExterieur, double rayonInterieur, boolean demiEtoile) {
		this(x, y, rayonExterieur, rayonInterieur);
		this.demiEtoile = demiEtoile;
	}

	/**
	 * Dessiner l'etoile. Cette methode doit garder le contexte graphique g2d
	 * intacte, car possiblement d'autres objets l'utiliseront par la suite.
	 *
	 * !!! La méthode provient d'anciens projets (ex-auteur : Caroline Houle) mais a
	 * été implementé et modifier pour notre code !!!
	 *
	 * @param g2d Le contexte graphique du composant sur lequel on dessine
	 */
	// Nils Lahaye
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		// Si l'etoile est une demi-etoile, on la dessine a moitie (horizontalement)
		if (demiEtoile) {
			g2dPrive.clipRect((int) x, (int) y, (int) rayonExterieur, (int) rayonExterieur * 2);
		}

		g2dPrive.fill(pathEtoile);

		g2dPrive.dispose();
	}

	/**
	 * Retourner le rayon du cercle exterieur sur lequel les pointes de l'etoile
	 * sont disposees
	 *
	 * @return Le rayon du cercle exterieur sur lequel les pointes de l'etoile sont
	 *         disposees
	 */
	// Caroline Houle
	public double getRayonExterieur() {
		return rayonExterieur;
	}

	/**
	 * Retourner le rayon du cercle interieur sur lequel les pointes de l'etoile
	 * sont disposees
	 *
	 * @return Le rayon du cercle interieur sur lequel les pointes de l'etoile sont
	 *         disposees
	 */
	// Caroline Houle
	public double getRayonInterieur() {
		return rayonInterieur;
	}

	/**
	 * Retourner la coordonnee en X du coin superieur-gauche du rectangle qui
	 * englobe l'etoile
	 *
	 * @return La coordonnee en X du coin superieur-gauche du rectangle qui englobe
	 *         l'etoile
	 */
	// Caroline Houle
	public double getX() {
		return x;
	}

	/**
	 * Retourne la coordonnee en Y du coin superieur-gauche du rectangle qui englobe
	 * l'etoile
	 *
	 * @return La coordonnee en Y du coin superieur-gauche du rectangle qui englobe
	 *         l'etoile
	 */
	// Caroline Houle
	public double getY() {
		return y;
	}

	/**
	 * Modifier l'etoile pour qu'elle soit une demi-etoile
	 *
	 * @param demiEtoile Si vrai, l'etoile sera dessinee a moitie
	 */
	// Nils Lahaye
	public void setDemiEtoile(boolean demiEtoile) {
		this.demiEtoile = demiEtoile;
	}

	/**
	 * Modifier le rayon du cercle exterieur sur lequel les pointes de l'etoile sont
	 * disposees La geometrie sera recree suite a ce changement.
	 *
	 * @param rayonExterieur Le rayon du cercle exterieur sur lequel les pointes de
	 *                       l'etoile sont disposees
	 */
	// Caroline Houle
	public void setRayonExterieur(double rayonExterieur) {
		this.rayonExterieur = rayonExterieur;
		creerLaGeometrie();
	}

	/**
	 * Modifier le rayon du cercle interieur sur lequel les pointes de l'etoile sont
	 * disposees La geometrie sera recree suite a ce changement.
	 *
	 * @param rayonInterieur Le rayon du cercle interieur sur lequel les pointes de
	 *                       l'etoile sont disposees
	 */
	// Caroline Houle
	public void setRayonInterieur(double rayonInterieur) {
		this.rayonInterieur = rayonInterieur;
		creerLaGeometrie();
	}

	/**
	 * Modifier la coordonnee en X du coin superieur-gauche du rectangle qui englobe
	 * l'etoile La geometrie sera recreee suite a ce changement.
	 *
	 * @param x La nouvelle coordonnee en X du coin superieur-gauche du rectangle
	 *          qui englobe l'etoile
	 */
	// Caroline Houle
	public void setX(double x) {
		this.x = x;
		creerLaGeometrie();
	}

	/**
	 * Modifier la coordonnee en Y du coin superieur-gauche du rectangle qui englobe
	 * l'etoile La geometrie sera recreee suite a ce changement.
	 *
	 * @param y La nouvelle coordonnee en Y du coin superieur-gauche du rectangle
	 *          qui englobe l'etoile
	 */
	// Caroline Houle
	public void setY(double y) {
		this.y = y;
		creerLaGeometrie();
	}

	/**
	 * Retourner une chaine de caracteres avec les caracteristiques de l'etoile
	 * Methode utile pour debugger.
	 *
	 * @return Une chaine contenant la position et les dimensions de l'etoile
	 */
	// Caroline Houle
	@Override
	public String toString() {
		return ("etoile: Le coin est e (" + x + ", " + y + "). Rayon exterieur = " + rayonExterieur
				+ " et rayon interieur = " + rayonInterieur);
	}

	/**
	 * Methode privee pour creer la forme de l'etoile (un path2D) Cette methode doit
	 * etre appelee de nouveau chaque fois que sa position ou dimension est modifiee
	 */
	// Caroline Houle
	private void creerLaGeometrie() {

		// avec ces angles, on cree une etoile e cinq pointes
		double angle1 = Math.PI / 10;
		double angle2 = Math.PI * 3 / 10;
		double ptX, ptY; // variables temporaires pour calculer les points e rejoindre entre eux

		pathEtoile = new Path2D.Double();

		ptX = x + rayonExterieur;
		ptY = y;
		pathEtoile.moveTo(ptX, ptY);

		ptX = x + rayonExterieur - rayonInterieur * Math.cos(angle2);
		ptY = y + rayonExterieur - rayonInterieur * Math.sin(angle2);
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur - rayonExterieur * Math.cos(angle1);
		ptY = y + rayonExterieur - rayonExterieur * Math.sin(angle1);
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur - rayonInterieur * Math.cos(angle1);
		ptY = y + rayonExterieur + rayonInterieur * Math.sin(angle1);
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur - rayonExterieur * Math.cos(angle2);
		ptY = y + rayonExterieur + rayonExterieur * Math.sin(angle2);
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur;
		ptY = y + rayonExterieur + rayonInterieur;
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur + rayonExterieur * Math.cos(angle2);
		ptY = y + rayonExterieur + rayonExterieur * Math.sin(angle2);
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur + rayonInterieur * Math.cos(angle1);
		ptY = y + rayonExterieur + rayonInterieur * Math.sin(angle1);
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur + rayonExterieur * Math.cos(angle1);
		ptY = y + rayonExterieur - rayonExterieur * Math.sin(angle1);
		pathEtoile.lineTo(ptX, ptY);

		ptX = x + rayonExterieur + rayonInterieur * Math.cos(angle2);
		ptY = y + rayonExterieur - rayonInterieur * Math.sin(angle2);
		pathEtoile.lineTo(ptX, ptY);

		pathEtoile.closePath();
	}

}
