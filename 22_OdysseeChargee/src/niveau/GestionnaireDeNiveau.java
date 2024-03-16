package niveau;

import java.util.ArrayList;
/**
 * @author Giroux
 * @author Kitimir Yim
 */

public class GestionnaireDeNiveau {

	/** ArrayList contenant les niveaux **/
	public static ArrayList<Niveau> repertoireNiveau = new ArrayList<Niveau>();

	/**
	 * Ajoute un niveau à l'ArrayList
	 * @param niveauAAjouter Le niveau à ajouter
	 */
	//Giroux
	public static void ajouter(Niveau niveauAAjouter) {
		repertoireNiveau.add(niveauAAjouter);
		System.out.println("Ajout fait avec succès");
	}

	/**
	 * Permet d'aller chercher le niveau
	 * @param nom Le nom du niveau
	 * @return Le niveau voulu, ou rien si le nom de conrespond pas
	 */
	//Giroux
	public Niveau getNiveau(String nom) {
		nom = nom.toLowerCase();
		Niveau niveauTrouver = null;
		for (int i = 0; i < repertoireNiveau.size(); i++) {
			if (repertoireNiveau.get(i).getNomNiveau() == nom) {
				System.out.println("Niveau trouver!");
				niveauTrouver = repertoireNiveau.get(i);
			} else {
				System.out.println("Niveau introuvable!");
			}
		}
		return niveauTrouver;
	}
	/**
	 * Permet d'aller chercher le niveau
	 * @param index
	 * @return Le niveau voulu
	 */
	//Kitimir Yim
	public Niveau getNiveau(int index) {
		return repertoireNiveau.get(index);

	}
	/**
	 * Permet d'aller chercher le niveau
	 * @param index
	 * @return Le niveau voulu
	 */
	//Kitimir Yim
	public void setNiveau(int index, Niveau niveau) {
		repertoireNiveau.set(index, niveau);	
	}
	/**
	 * Permet de supprimer un niveau
	 * @param nom le nom du niveau à supprimer
	 */
	//Giroux
	public void supprimerNiveau(String nom) {
		for (int i = 0; i < repertoireNiveau.size(); i++) {
			if (repertoireNiveau.get(i).getNomNiveau() == nom) {
				repertoireNiveau.remove(i);
			}

		}


	}
}
