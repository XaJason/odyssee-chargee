package niveau;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

/**
 * Classe servant à sauvegarder des niveau Inspirée par le matériel d'appoint,
 * mais grandement modifier pour fonctionner pour notre jeu
 *
 * @author Kitimir Yim
 */
public class Sauvegarder {
	/**
	 * Chemin du dossier où sont sauvegardés les niveau sur l'ordinateur de
	 * l'utilisateur
	 */
	private static final String SOUS_DOSSIER_DE_TRAVAIL = "MesTrucs";
	/**
	 * Chemin pour le dossier de sauvegarde
	 */
	private static final String DOSSIER_SAUVEGARDE = new File(
			System.getProperty("user.home") + "\\OneDrive\\Documents\\" + SOUS_DOSSIER_DE_TRAVAIL).getPath();
	/**
	 * Type de fichier
	 */
	private static final String EXTENSION_FICHIER = ".dat";

	/**
	 * Méthode static permettant le chargement de niveau
	 *
	 * @param identifiantNiveau L'identifiant du niveau (son numéro d'index ou son
	 *                          nom)
	 * @return niveau Le niveau que l'on souhaite charger
	 */
	public static Niveau chargerNiveauDeBase(String identifiantNiveau) {
		String nomFichier = identifiantNiveau + EXTENSION_FICHIER;

		InputStream is = null;
		String[] pathsToTry = { nomFichier, "/ressources/" + nomFichier, "ressources/" + nomFichier, "/" + nomFichier };

		for (String path : pathsToTry) {
			is = Sauvegarder.class.getClassLoader().getResourceAsStream(path);
			if (is != null) {
				break;
			}
		}

		if (is == null) {
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("Impossible de trouver le fichier: ").append(nomFichier).append("\n");
			errorMsg.append("Chemins essayés:\n");
			for (String path : pathsToTry) {
				errorMsg.append("- ").append(path).append("\n");
			}

			JOptionPane.showMessageDialog(null, errorMsg.toString());
			return null;
		}

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(is);
			Niveau niveau = (Niveau) ois.readObject();
			GestionnaireDeNiveaux.ajouter(niveau);
			return niveau;
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors du chargement du niveau: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode static permettant le chargement de niveau dans MesTrucs
	 *
	 * @param identifiantNiveau L'identifiant du niveau (son numéro d'index ou son
	 *                          nom)
	 * @return niveau Le niveau que l'on souhaite charger
	 */
	public static Niveau chargerNiveauMesTrucs(String identifiantNiveau) {
		String nomFichier = identifiantNiveau;

		File dossierNiveaux = new File(DOSSIER_SAUVEGARDE);
		if (!dossierNiveaux.exists()) {
			dossierNiveaux.mkdirs();
		}
		File fichierDeTravail = new File(dossierNiveaux + "\\" + nomFichier);

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));

			Niveau niveau = (Niveau) ois.readObject();

			GestionnaireDeNiveaux.ajouter(niveau);

			return niveau;
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors du chargement du niveau : " + e.getMessage());
			return null;
		} finally {

			try {
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode static permettant la sauvegarde de niveau dans MesTrucs
	 *
	 * @param niveau    Objet niveau que l'on veut sauvegarder
	 * @param nomNiveau le nom du niveau
	 */
	public static void sauvegarderNiveauMesTrucs(Niveau niveau, String nomNiveau) {

		String nomFichier = nomNiveau + EXTENSION_FICHIER;

		File dossierNiveaux = new File(DOSSIER_SAUVEGARDE);
		if (!dossierNiveaux.exists()) {
			dossierNiveaux.mkdirs();
		}

		File fichierDeTravail = new File(dossierNiveaux + "\\" + nomFichier);

		ObjectOutputStream oos = null;
		if (fichierDeTravail.exists()) {
			JOptionPane.showMessageDialog(null,
					"Le fichier " + fichierDeTravail.toString() + " existe déjà... Il va être modifié");
		}
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichierDeTravail));
			oos.writeObject(niveau);
			oos.close();
			JOptionPane.showMessageDialog(null, "Niveau sauvegardé avec succès.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors de la sauvegarde du niveau : " + e.getMessage());

		} finally {

			try {
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}
}
