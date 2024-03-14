package niveau;


import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;



/**
 * Classe servant à sauvegarder des niveaux 
 * Inspirée par le matériel d'appoint, mais grandement modifier pour fonctionner pour notre jeu
 * @author Kitimir Yim
 */
public class Sauvegarder {
	/**
	 * Nom du dossier où est sauvegarder les niveaux
	 */
	private static final String DOSSIER_SAUVEGARDE = "ressources/";
	/**
	 * Type de fichier
	 */
	private static final String EXTENSION_FICHIER = ".dat";
	/**
	 * Méthode static permettant la sauvegarde de niveau
	 * @param niveau Objet niveau que l'on veut sauvegarder
	 * @param numeroNiveau numéro du niveau 
	 */
	//Kitimir Yim
	public static void sauvegarderNiveau(Niveau niveau, int numeroNiveau) {

		String nomFichier = DOSSIER_SAUVEGARDE + "niveau_" + numeroNiveau + EXTENSION_FICHIER;

		File fichierDeTravail = new File(nomFichier);

		ObjectOutputStream oos = null;
		if (fichierDeTravail.exists()) {
			JOptionPane.showMessageDialog(null,	"Probléme! Le fichier " + fichierDeTravail.toString() + " existe déja...");
		}
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichierDeTravail));
			oos.writeObject(niveau);
			oos.close();
			System.out.println("Niveau sauvegardé avec succès.");
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauvegarde du niveau : " + e.getMessage());
		}
		finally {

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
	 * @param numeroNiveau numéro du niveau
	 * @return niveau niveau que l'on souhaite charger
	 */
	//Kitimir Yim
	public static Niveau chargerNiveau(int numeroNiveau) {
		String nomFichier = DOSSIER_SAUVEGARDE + "niveau_" + numeroNiveau + EXTENSION_FICHIER;

		File fichierDeTravail = new File(nomFichier);
		
		
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));

			Niveau niveau = (Niveau) ois.readObject();

			System.out.println("Niveau chargé avec succès.");
			return niveau;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erreur lors du chargement du niveau : " + e.getMessage());
			return null;
		}
		finally {

			try {
				ois.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}
}

