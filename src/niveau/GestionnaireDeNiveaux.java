package niveau;

import java.util.ArrayList;

/**
 * Classe qui gère les différents niveau
 *
 * @author Giroux
 * @author Kitimir Yim
 */

public class GestionnaireDeNiveaux {

	/** ArrayList contenant les niveau **/
	private static ArrayList<Niveau> repertoireNiveau = new ArrayList<>();

	/**
	 * Ajoute un niveau à l'ArrayList
	 *
	 * @param niveauAAjouter Le niveau à ajouter
	 */
	// Giroux
	public static void ajouter(Niveau niveauAAjouter) {
		repertoireNiveau.add(niveauAAjouter);

	}

	/**
	 * Renvoie la liste des niveau.
	 *
	 * @return La liste des niveau.
	 */
	// Kitimir Yim
	public static ArrayList<Niveau> getRepertoireNiveau() {
		return repertoireNiveau;
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
	 * Permet d'aller chercher le niveau selon son nom
	 *
	 * @param nom Le nom du niveau
	 * @return Le niveau voulu, ou rien si le nom de correspond pas
	 */
	// Giroux
	public Niveau getNiveau(String nom) {
		nom = nom.toLowerCase();
		Niveau niveauTrouver = null;
		for (Niveau element : repertoireNiveau) {
			if (element.getNom() == nom) {

				niveauTrouver = element;
			} else {

			}
		}
		return niveauTrouver;
	}

	/**
	 * Permet de remplacer le niveau se trouvant à l'emplacement passer en paramètre
	 * par l'objet niveau passer en paramètre
	 *
	 * @param index  L'index du niveau à remplacer
	 * @param niveau Le nouveau niveau (qui remplace l'ancien se trouvant à l'index)
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
			if (repertoireNiveau.get(i).getNom() == nom) {
				repertoireNiveau.remove(i);
			}

		}

	}
}
