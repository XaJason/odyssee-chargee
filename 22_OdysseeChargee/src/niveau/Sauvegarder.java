package niveau;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

/**
 * Classe servant à sauvegarder des niveaux Inspirée par le matériel
 * d'appoint, mais grandement modifier pour fonctionner pour notre jeu
 * 
 * @author Kitimir Yim
 */
public class Sauvegarder {
	/**
	 * Chemin du dossier où sont sauvegardés les niveaux sur l'ordinateur de
	 * l'utilisateur
	 */
	private static final String sousDossierDeTravail = "MesTrucs";
	private static final String DOSSIER_SAUVEGARDE = new File(
			javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\"
					+ sousDossierDeTravail)
			.getPath();
	/**
	 * Type de fichier
	 */
	private static final String EXTENSION_FICHIER = ".dat";

	/**
	 * Nom du dossier où est sauvegarder les niveaux
	 */
	private static final String DOSSIER_SAUVEGARDE_RESSOURCE = "ressources/";

	// Méthode pas utilisé encore
	/**
	 * Méthode static permettant la sauvegarde de niveau dans mesTrucs
	 * 
	 * @param niveau       Objet niveau que l'on veut sauvegarder
	 * @param numeroNiveau numéro du niveau
	 */
	// Kitimir Yim
	public static void sauvegarderNiveauMesTrucs(Niveau niveau, String numeroNiveau) {

		String nomFichier = DOSSIER_SAUVEGARDE + "\\" + numeroNiveau + EXTENSION_FICHIER;

		File dossierNiveaux = new File(DOSSIER_SAUVEGARDE);
		if (!dossierNiveaux.exists()) {
			dossierNiveaux.mkdirs(); // Créer les dossiers parents si nécessaire
		}

		File fichierDeTravail = new File(nomFichier);

		ObjectOutputStream oos = null;
		if (fichierDeTravail.exists()) {
			JOptionPane.showMessageDialog(null,
					" Le fichier " + fichierDeTravail.toString() + " existe déjà... Il va être modifié");
		}
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichierDeTravail));
			oos.writeObject(niveau);
			oos.close();
			System.out.println("Niveau sauvegardé avec succès.");
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauvegarde du niveau : " + e.getMessage());
		} finally {

			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}

	// Méthode pas utilisé encore
	/**
	 * Méthode static permettant le chargement de niveau dans mesTrucs
	 * 
	 * @param identifiantNiveau L'identifiant du niveau (son numéro d'index ou son
	 *                          nom)
	 * @return niveau Le niveau que l'on souhaite charger
	 */
	// Kitimir Yim
	public static Niveau chargerNiveauMesTrucs(String identifiantNiveau) {
		String nomFichier = DOSSIER_SAUVEGARDE + "\\" + identifiantNiveau + EXTENSION_FICHIER;

		File dossierNiveaux = new File(DOSSIER_SAUVEGARDE);
		if (!dossierNiveaux.exists()) {
			dossierNiveaux.mkdirs(); // Créer les dossiers parents si nécessaire
		}
		File fichierDeTravail = new File(nomFichier);

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));

			Niveau niveau = (Niveau) ois.readObject();

			GestionnaireDeNiveaux.ajouter(niveau);

			System.out.println("Niveau chargé avec succès.");
			return niveau;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erreur lors du chargement du niveau : " + e.getMessage());
			return null;
		} finally {

			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode static permettant la sauvegarde de niveau
	 * 
	 * @param niveau       Objet niveau que l'on veut sauvegarder
	 * @param numeroNiveau numéro du niveau
	 */
	// Kitimir Yim
	public static void sauvegarderNiveau(Niveau niveau, String numeroNiveau) {

		String nomFichier = DOSSIER_SAUVEGARDE_RESSOURCE + numeroNiveau + EXTENSION_FICHIER;

		File fichierDeTravail = new File(nomFichier);

		ObjectOutputStream oos = null;
		if (fichierDeTravail.exists()) {
			JOptionPane.showMessageDialog(null,
					" Le fichier " + fichierDeTravail.toString() + " existe déjà: il sera remplacé!");
		}
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichierDeTravail));
			oos.writeObject(niveau);
			oos.close();
			System.out.println("Niveau sauvegardé avec succès!");
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauvegarde du niveau : " + e.getMessage());
		} finally {

			try {
				oos.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode static permettant le chargement de niveau
	 * 
	 * @param identifiantNiveau L'identifiant du niveau (son numéro d'index ou son
	 *                          nom)
	 * @return niveau Le niveau que l'on souhaite charger
	 */
	// Kitimir Yim
	public static Niveau chargerNiveau(String identifiantNiveau) {
		String nomFichier = DOSSIER_SAUVEGARDE_RESSOURCE + identifiantNiveau + EXTENSION_FICHIER;

		File fichierDeTravail = new File(nomFichier);

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));

			Niveau niveau = (Niveau) ois.readObject();

			GestionnaireDeNiveaux.ajouter(niveau);

			System.out.println("Niveau chargé avec succès.");
			return niveau;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erreur lors du chargement du niveau : " + e.getMessage());
			return null;
		} finally {

			try {
				ois.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}
}
