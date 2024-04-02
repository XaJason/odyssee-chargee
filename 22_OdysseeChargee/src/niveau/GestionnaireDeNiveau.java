package niveau;

import java.util.ArrayList;

/**
 * Classe qui gère les différents niveaux
 * 
 * @author Giroux
 * @author Kitimir Yim
 */

public class GestionnaireDeNiveau {

	/** ArrayList contenant les niveaux **/
	private static ArrayList<Niveau> repertoireNiveau = new ArrayList<Niveau>();
	/**
	 * nombre max de niveau possible dans la liste
	 */
	private static final int MAX_NIVEAUX = 3;

	/**
	 * Ajoute un niveau à l'ArrayList
	 * 
	 * @param niveauAAjouter Le niveau à ajouter
	 */
	// Giroux
	public static void ajouter(Niveau niveauAAjouter) {
		
		if (repertoireNiveau.size() < MAX_NIVEAUX) { 
            repertoireNiveau.add(niveauAAjouter);
            System.out.println("Ajout fait avec succès");
        } else {
            System.out.println("Nombre maximal de niveaux atteint !");
        }
    }

	/**
	 * Permet d'aller chercher le niveau selon son nom
	 * 
	 * @param nom Le nom du niveau
	 * @return Le niveau voulu, ou rien si le nom de conrespond pas
	 */
	// Giroux
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
	 * Permet d'aller chercher le niveau selon son numéro
	 * 
	 * @param index Le numéro du niveau
	 * @return Le niveau voulu
	 */
	// Kitimir Yim
	public Niveau getNiveau(int index) {
		return repertoireNiveau.get(index);

	}

	/**
	 * Permet d'aller chercher le niveau
	 * 
	 * @param index  l'index à chercher
	 * @param niveau le niveau à chercher
	 */
	// Kitimir Yim
	public void setNiveau(int index, Niveau niveau) {
		repertoireNiveau.set(index, niveau);
	}

	/**
	 * Permet de supprimer un niveau
	 * 
	 * @param nom le nom du niveau à supprimer
	 */
	// Giroux
	public void supprimerNiveau(String nom) {
		for (int i = 0; i < repertoireNiveau.size(); i++) {
			if (repertoireNiveau.get(i).getNomNiveau() == nom) {
				repertoireNiveau.remove(i);
			}

		}

	}
	/**
	 * Renvoie la liste des niveaux.
	 *
	 * @return La liste des niveaux.
	 */
	// Kitimir Yim
	public static ArrayList<Niveau> getRepertoireNiveau() {
		return repertoireNiveau;
	}
}
