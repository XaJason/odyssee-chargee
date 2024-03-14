package niveau;

import java.io.Serializable;

import tuile.Tuile;
/**
 * Classe niveau qui sert à regrouper tout ce que peut contenir un niveau
 * @author Kitimir Yim 
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
	
	
	public Niveau(Tuile [][]tabEmplacement, String nom ) {
		
		this.tabEmplacement = tabEmplacement;
		this.nomNiveau = nom;
	}
	
	
	public String getNomNiveau() {
		return nomNiveau;
	}


	public void setNomNiveau(String nomNiveau) {
		this.nomNiveau = nomNiveau;
	}


	public Tuile[][] getTabEmplacement() {
		return tabEmplacement;
	}


	public void setTabEmplacement(Tuile[][] tabEmplacement) {
		this.tabEmplacement = tabEmplacement;
	}

}
