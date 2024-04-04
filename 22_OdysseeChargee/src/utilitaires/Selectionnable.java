package utilitaires;

/**
 * Interface qui definit la methode qu'un objet doit implementer pour pouvoir
 * etre selectionne
 *
 * @author Caroline Houle
 */

public interface Selectionnable {

	/**
	 * Retourne vrai si le point passé en paramètre fait partie de l'objet
	 * dessinable sur lequel cette méthode sera appelée
	 *
	 * @param xPix Coordonnée en x du point (exprimée en pixels)
	 * @param yPix Coordonnée en y du point (exprimée en pixels)
	 * @return vrai si le point fait partie de l'objet dessinable
	 */
	// Caroline Houle
	public boolean contient(double xPix, double yPix);

}
