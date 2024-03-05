package utilis;

/**
 * Interface qui definit la methode qu'un objet doit implementer pour pouvoir
 * etre selectionne
 *
 * @author Caroline Houle
 */

public interface Selectionnable {

	/**
	 * Retourne vrai si le point passe en parametre fait partie de l'objet
	 * dessinable
	 * sur lequel cette methode sera appelee
	 *
	 * @param xPix Coordonnee en x du point (exprime en pixels)
	 * @param yPix Coordonnee en y du point (exprime en pixels)
	 * @return vrai si le point fait partie de l'objet dessinable
	 */
	// Caroline Houle
	public boolean contient(double xPix, double yPix);

}
