package niveau;

import java.io.Serializable;

import tuile.Tuile;

/**
 * Composant illustrant la simulation :
 * Un niveau est une scène physique où sont représentés des objets intéractifs
 * physique et des tuiles
 *
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 * 
 */
public class Niveau implements Serializable {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 7452459522695790433L;

	/** Nom du niveau **/
	private String nomNiveau;
	/** Array de l'ensemble des blocs qui est contenu dans le niveau **/
	private Tuile[][] tabEmplacement;

	/**
	 * Constructeur du niveau
	 * 
	 * @param tabEmplacement Array qui contient toutes les tuiles de la grille avec
	 *                       les bonnes propriétés
	 * @param nom            Le nom que l'on donne au niveau
	 */
	// Giroux
	public Niveau(Tuile[][] tabEmplacement, String nom) {

		this.tabEmplacement = tabEmplacement;
		this.nomNiveau = nom;
	}

	/**
	 * Permet d'avoir le nom du niveau
	 * 
	 * @return Le nom du niveau
	 */
	// Giroux
	public String getNomNiveau() {
		return nomNiveau;
	}

	/**
	 * permet de changer le nom du niveau
	 * 
	 * @param nomNiveau Le nouveau nom du niveau
	 */
	// Giroux
	public void setNomNiveau(String nomNiveau) {
		this.nomNiveau = nomNiveau;
	}

	/**
	 * Permet d'avoir les tuiles de la grille
	 * 
	 * @return Les tuiles de la grille
	 */
	// Giroux
	public Tuile[][] getTabEmplacement() {
		return tabEmplacement;
	}

	/**
	 * Permet de changer les tuiles de la grille
	 * 
	 * @param tabEmplacement Les nouvelles tuiles de la grille
	 */
	// Giroux
	public void setTabEmplacement(Tuile[][] tabEmplacement) {
		this.tabEmplacement = tabEmplacement;
	}

}
