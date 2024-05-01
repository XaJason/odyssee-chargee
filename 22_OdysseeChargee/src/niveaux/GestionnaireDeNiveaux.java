package niveaux;

import java.util.ArrayList;

/**
 * Classe qui gère les différents niveaux
 * 
 * @author Giroux
 * @author Kitimir Yim
 */

public class GestionnaireDeNiveaux {

	/** ArrayList contenant les niveaux **/
	private static ArrayList<Niveau> repertoireNiveau = new ArrayList<Niveau>();

	/**
	 * Ajoute un niveaux à l'ArrayList
	 * 
	 * @param niveauAAjouter Le niveaux à ajouter
	 */
	// Giroux
	public static void ajouter(Niveau niveauAAjouter) {
		repertoireNiveau.add(niveauAAjouter);
		System.out.println("Ajout fait avec succès");

	}

	/**
	 * Permet de supprimer un niveaux
	 * 
	 * @param nom le nom du niveaux à supprimer
	 */
	// Giroux
	public void supprimerNiveau(String nom) {
		for (int i = 0; i < repertoireNiveau.size(); i++) {
			if (repertoireNiveau.get(i).getNom() == nom) {
				repertoireNiveau.remove(i);
			}

		}

	}

	// GETTERS ET SETTERS
	/**
	 * Permet d'aller chercher le niveaux selon son nom
	 * 
	 * @param nom Le nom du niveaux
	 * @return Le niveaux voulu, ou rien si le nom de correspond pas
	 */
	// Giroux
	public Niveau getNiveau(String nom) {
		nom = nom.toLowerCase();
		Niveau niveauTrouver = null;
		for (int i = 0; i < repertoireNiveau.size(); i++) {
			if (repertoireNiveau.get(i).getNom() == nom) {
				System.out.println("Niveau trouver!");
				niveauTrouver = repertoireNiveau.get(i);
			} else {
				System.out.println("Niveau introuvable!");
			}
		}
		return niveauTrouver;
	}

	/**
	 * Permet d'aller chercher le niveaux selon son numéro
	 * 
	 * @param index Le numéro du niveaux
	 * @return Le niveaux voulu
	 */
	// Kitimir Yim
	public Niveau getNiveau(int index) {
		return repertoireNiveau.get(index);

	}

	/**
	 * Permet de remplacer le niveaux se trouvant à l'emplacement passer en paramètre
	 * par l'objet niveaux passer en paramètre
	 * 
	 * @param index  L'index du niveaux à remplacer
	 * @param niveaux Le nouveau niveaux (qui remplace l'ancien se trouvant à l'index)
	 */
	// Kitimir Yim
	public void setNiveau(int index, Niveau niveau) {
		repertoireNiveau.set(index, niveau);
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
