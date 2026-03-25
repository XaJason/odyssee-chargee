package niveau;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

/**
 * Classe servant à sauvegarder des niveaux. Inspirée par le matériel d'appoint,
 * mais grandement modifiée pour fonctionner pour notre jeu.
 *
 * @author Kitimir Yim
 */
public class Sauvegarder {

	/** Nom du dossier racine de l'application dans Documents **/
	private static final String DOSSIER_NIVEAUX = "OdysseeChargee";

	/** Chemin résolu vers le dossier de sauvegarde des niveaux **/
	private static final Path CHEMIN_SAUVEGARDE = resoudreDossierSauvegarde();

	/** Extension des fichiers de niveau **/
	private static final String EXTENSION_FICHIER = ".dat";

	/**
	 * Résout le chemin du dossier de sauvegarde de manière portable.
	 * <p>
	 * Tente d'utiliser le dossier Documents de l'utilisateur, avec un repli
	 * vers le répertoire personnel si Documents n'existe pas. Crée
	 * automatiquement les répertoires manquants.
	 *
	 * @return Le chemin absolu vers le dossier de sauvegarde
	 */
	private static Path resoudreDossierSauvegarde() {
		Path dossierPersonnel = Paths.get(System.getProperty("user.home"));
		Path dossierDocuments = dossierPersonnel.resolve("Documents");

		Path racine;
		if (Files.isDirectory(dossierDocuments)) {
			racine = dossierDocuments;
		} else {
			racine = dossierPersonnel;
		}

		Path cheminComplet = racine.resolve(DOSSIER_NIVEAUX).resolve("niveaux");

		try {
			Files.createDirectories(cheminComplet);
		} catch (IOException e) {
			System.err.println("Impossible de créer le dossier de sauvegarde : " + cheminComplet);
			e.printStackTrace();
		}

		return cheminComplet;
	}

	/**
	 * Retourne le chemin du dossier de sauvegarde des niveaux
	 *
	 * @return Le chemin absolu vers le dossier de sauvegarde
	 */
	public static Path getCheminSauvegarde() {
		return CHEMIN_SAUVEGARDE;
	}

	/**
	 * Méthode statique permettant le chargement d'un niveau de base depuis le
	 * classpath
	 *
	 * @param identifiantNiveau L'identifiant du niveau (son numéro d'index ou son
	 *                          nom)
	 * @return niveau Le niveau que l'on souhaite charger
	 */
	public static Niveau chargerNiveauDeBase(String identifiantNiveau) {
		String nomFichierNiveau = identifiantNiveau + EXTENSION_FICHIER;

		ObjectInputStream ois = null;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(nomFichierNiveau);

		if (is == null) {
			JOptionPane.showMessageDialog(null,
					"Incapable de trouver ce fichier dans le BuildPath (ou dans le jar exécutable) : "
							+ nomFichierNiveau);
		}
		try {
			ois = new ObjectInputStream(is);
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
	 * Méthode statique permettant le chargement d'un niveau sauvegardé par
	 * l'utilisateur
	 *
	 * @param identifiantNiveau L'identifiant du niveau (son numéro d'index ou son
	 *                          nom)
	 * @return niveau Le niveau que l'on souhaite charger
	 */
	public static Niveau chargerNiveauMesTrucs(String identifiantNiveau) {
		String nomFichierNiveau = identifiantNiveau;

		Path cheminNiveaux = CHEMIN_SAUVEGARDE;
		File fichierDeTravail = cheminNiveaux.resolve(nomFichierNiveau).toFile();

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
	 * Méthode statique permettant la sauvegarde d'un niveau par l'utilisateur
	 *
	 * @param niveau    Objet niveau que l'on veut sauvegarder
	 * @param nomNiveau le nom du niveau
	 */
	public static void sauvegarderNiveauMesTrucs(Niveau niveau, String nomNiveau) {

		String nomFichierNiveau = nomNiveau + EXTENSION_FICHIER;

		Path cheminNiveaux = CHEMIN_SAUVEGARDE;
		File fichierDeTravail = cheminNiveaux.resolve(nomFichierNiveau).toFile();

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
