package niveau;

import java.awt.Graphics2D;
import java.io.Serializable;

import javax.swing.JPanel;

import dessin.Grille;

/**
 * Composant illustrant la simulation : Un niveau est une scène physique où sont
 * représentés des objets intéractifs physique et des tuiles
 *
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 *
 */
public class Niveau extends JPanel implements Serializable {

	/** Numéro d'identification pour la sérialisation **/
	private static final long serialVersionUID = 7452459522695790433L;

	/** Objet représentant la grille ainsi que toutes ses tuiles **/
	private Grille grille;
	/** Nom du niveau **/
	private String nom;

	/**
	 * Constructeur du niveau
	 *
	 * @param grille Objet représentant la grille ainsi que toutes ses tuiles
	 * @param nom    Nom que l'on donne au niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public Niveau(Grille grille, String nom) {
		this.grille = grille;
		this.nom = nom;
	}

	/**
	 * Permet de dessiner des les tuiles de l'objet niveau
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {
		grille.dessinerLesTuiles(g2d);
	}

	/**
	 * Retourne la grille du niveau
	 *
	 * @return La grille du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Permet d'avoir le nom du niveau
	 *
	 * @return Le nom du niveau
	 */
	// Giroux
	public String getNom() {
		return nom;
	}

	/**
	 * Modifie la grille du niveau
	 *
	 * @param grille La nouvelle grille du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	/**
	 * Permet de changer le nom du niveau
	 *
	 * @param nom Le nouveau nom du niveau
	 */
	// Giroux
	public void setNom(String nom) {
		this.nom = nom;
	}

}
