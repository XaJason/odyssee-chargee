package physique;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import interactif.InteractifPhysique;
import utilitaires.Dessinable;

/**
 * Classe plaque: représentation sommaire d'une plaque à l'aide d'un segment de
 * droite
 * 
 * @author Enuel René Valentin Kizozo Izia
 */
public class Segment extends InteractifPhysique implements Dessinable {

	// PROPRIÉTÉS //
	/** Objet Path2D permettant de représenter le segment **/
	private Path2D.Double segment;
	
	/** Vecteur normal du segment **/
	private Vecteur2D normale; // Doit être normalisé

	/** Vecteur passant par l'axe du segment **/
	private Vecteur2D axe; // Normalisé
	
	/** Longueur du segment **/
	private double longueur;
	
	/** Position de l'extrémité A du segment **/
	private Vecteur2D extremiteA;
	
	/** Position de l'extrémité B du segment **/
	private Vecteur2D extremiteB;

	// CONSTRUCTEUR //
	/**
	 * Constructeur de la plaque chargée
	 * 
	 * @param position La position du segment
	 * @param normale  La normale du segment
	 * @param longueur La longueur du segment
	 * @param charge   La charge du segment
	 * @param masse    La masse du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public Segment(Vecteur2D position, Vecteur2D normale, double longueur, double charge, double masse) {
		super(position, charge);
		this.normale = new Vecteur2D(normale);
		this.longueur = longueur;
		
		this.axe = new Vecteur2D(normale.getY(), -normale.getX());		
		this.extremiteA = position.additionne(axe.multiplie(longueur / 2));
		this.extremiteB = position.additionne(axe.multiplie(-longueur / 2));

		creerLaGeometrie();
	}

	// SOUS-PROGRAMMES
	/**
	 * Permet de créer la géométrie du segment.
	 */
	// Enuel René Valentin Kizozo Izia
	public void creerLaGeometrie() {
		segment = new Path2D.Double();
		
		
		segment.moveTo(extremiteA.getX(), extremiteA.getY());
		segment.lineTo(extremiteB.getX(), extremiteB.getY());
		;
	}

	/**
	 * Permet de dessiner un segment, sur le contexte graphique
	 * passé en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		//g2dPrive.rotate(getPosition().getX(), getPosition().getY(), angleInclinaison);
		g2dPrive.setColor(Color.red);
		g2dPrive.draw(segment);
	}

	/**
	 * Permet d'afficher quelques caractéristiques du segment :
	 * Sa position et la position ses extrémités
	 * 
	 * !!! La méthode provient d'anciens projets (auteur : Caroline Houle) mais a
	 * été implementé et
	 * modifier pour notre code !!!
	 * 
	 * @param nbDecimales Le nombre souhaité de décimales après la virgule
	 * @return Une chaine présentant quelques caractéristiques du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public String toString(int nbDecimales) {
		String s = " position=[ " + String.format("%." + nbDecimales + "f", getPosition().getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getPosition().getY()) + "]";
		s += " charge=[ " + String.format("%." + nbDecimales + "f", getCharge()) + "]";
		s += " extrémité A=[ " + String.format("%." + nbDecimales + "f", getExtremiteA().getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getExtremiteA().getY()) + "]";
		s += " extrémité B=[ " + String.format("%." + nbDecimales + "f", getExtremiteB().getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getExtremiteB().getY()) + "]";
		return (s);
	}

	// GETTERS & SETTERS //
	/**
	 * Retourne le vecteur normal du segment
	 * 
	 * @return Le vecteur normal du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getNormale() {
		return normale;
	}

	/**
	 * Modifie le vecteur normal du segment
	 * 
	 * @param normale Vecteur incluant les composantes en x et y
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNormale(Vecteur2D normale) {
		this.normale = new Vecteur2D(normale);
		setAxe();
		creerLaGeometrie();
	}

	/**
	 * Retourne le vecteur passant par l'axe du segment
	 * 
	 * @return Le vecteur passant par l'axe du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getAxe() {
		return axe;
	}

	/**
	 * Modifie le vecteur passant par l'axe du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setAxe() {
		this.axe = new Vecteur2D(normale.getY(), -normale.getX());
		setExtremiteA();
		setExtremiteB();
		creerLaGeometrie();
	}

	/**
	 * Retourne la longueur du segment
	 * 
	 * @return La longueur du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLongueur() {
		return longueur;
	}

	/**
	 * Modifie la longueur du segment
	 * 
	 * @param longueur Longueur du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setLongueur(double longueur) {
		this.longueur = longueur;
		setExtremiteA();
		setExtremiteB();
		creerLaGeometrie();
	}
	
	/**
	 * Retourne l'extrémité A du segment
	 * 
	 * @return L'extrémité A du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteA() {
		return extremiteA;
	}

	/**
	 * Modifie l'extrémité A du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteA() {
		this.extremiteA = getPosition().additionne(axe.multiplie(longueur / 2));
		creerLaGeometrie();
	}

	/**
	 * Retourne l'extrémité B du segment
	 * 
	 * @return L'extrémité B du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteB() {
		return extremiteB;
	}

	/**
	 * Modifie l'extrémité B du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteB() {
		this.extremiteB = getPosition().additionne(axe.multiplie(-longueur / 2));
		creerLaGeometrie();
	}

}
