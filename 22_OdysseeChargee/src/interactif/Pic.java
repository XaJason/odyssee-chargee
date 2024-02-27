package interactif;

/**
 * Classe qui va créer, positionner et dessiner les pics
 * 
 * @author Giroux
 */

public class Pic extends Interactif {

	/**
	 * Contructeur de pic qui va assigner un emplacement dans le tableau des
	 * positions
	 * ainsi que spécifier qu'il est mortel
	 * 
	 * @param position L'emplacment dans le tableau des positions
	 */
	// Giroux
	public Pic(int position) {
		super(position);
		this.setMeutrier(true);
	}/// Fin constructeur

}/// Fin classe
