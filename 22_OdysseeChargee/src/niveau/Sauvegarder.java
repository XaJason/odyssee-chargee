package niveau;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Sauvegarder {



	public static void CreeFichier() {
		File fichier = new File("ressources/testFichier.txt");
		try {
			fichier.createNewFile();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur d'écriture!");
			e.printStackTrace();


		}

	}
	public static void ecrireFichierTexte() {
		File fichier = new File("ressources/testFichier.txt");

		try {
			PrintWriter pw = new PrintWriter(fichier);
			pw.println("lebronjames");
			pw.println("fahim");
			pw.close();

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	public static void lireFichier() {
		File fichier = new File("ressources/test.txt");
		try {


			Scanner sc = new Scanner(fichier);

			while(sc.hasNextLine()) {
				System.out.println(sc.nextLine());
			}


		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	public static void CreeNiveau(String nom, int []tab) {
		File nouveauNiveau = new File("ressources/" + nom +".txt");
		if(nouveauNiveau.exists()) {
			System.out.println("Le fichier " + nom + "existe déja");
			return;
			
		}else {
			try {
				nouveauNiveau.createNewFile();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
