package niveau;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.JPanel;

import dessin.Grille;
import tuile.Tuile;
import utilitaires.OutilsImage;

/**
 * Composant illustrant la simulation :
 * Un niveau est une scène physique où sont représentés des objets intéractifs physique et des tuiles
 *
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 * 
 */
public class Niveau extends JPanel implements Serializable {

	// PROPRIÉTÉS //
	/** Numéro d'identification pour la sérialisation **/
	private static final long serialVersionUID = 7452459522695790433L;

	
	/** Nom du niveau **/
	private String nom;
	/** Objet représentant la grille ainsi que toutes ses tuiles **/
	private Grille grille;
	
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
		creerLaGeometrie();
	}
	
	/** 
	 * Permet de créer la géométrie d'un objet niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public void creerLaGeometrie() {
		/* Dessiner tous les bounding box des tuiles du niveau
		 * Ou du moins définir où les objets sont, mais de manière physique,
		 * pas à l'aide d'un tableau
		 * 
		 * Exemples/Idées:
		 * - Un path pour les bordures et les surfaces (côtés, carrés et triangles rectangles/isocèles)
		 * 		- En additionnant les Area des boundings box des objets ci-dessus on aurait une grosse Area « surfaces »
		 * 
		 * - Une rectangle pour les pics
		 * - Un cercle pour le portail
		 * 		- Possibilité de faire de même et d'avoir un Area avec les zones de pics/portail
		 * 
		 * - Un rectangle pour le drapeau
		 */
		
	}

	// SOUS-PROGRAMMES //
	/**
	 * Permet de dessiner des les tuiles de l'objet niveau
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {
		grille.dessinerTuile(g2d);
	}

	// GETTERS ET SETTERS //
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
	 * Permet de changer le nom du niveau
	 * 
	 * @param nom Le nouveau nom du niveau
	 */
	// Giroux
	public void setNom(String nom) {
		this.nom = nom;
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
	 * Modifie la grille du niveau
	 * 
	 * @param grille La nouvelle grille du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setGrille(Grille grille) {
		this.grille = grille;
	}

}
