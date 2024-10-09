package physique;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import utilitaires.Dessinable;

/**
 * Classe segment: permet de placer des segment sur des tuiles
 *
 * @author Enuel René Valentin Kizozo Izia
 */
public class Segment implements Dessinable, Serializable {

	/** Constante permettant la sérialisation de la classe **/
	private static final long serialVersionUID = 5445463236556642807L;

	/** Vecteur passant par l'axe du segment **/
	private Vecteur2D axe; // Normalisé

	/** Position de l'extrémité A du segment **/
	private Vecteur2D extremiteA;

	/** Position de l'extrémité B du segment **/
	private Vecteur2D extremiteB;

	/** Longueur du segment **/
	private double longueur;

	/** Vecteur normal du segment **/
	private Vecteur2D normale; // Doit être normalisé

	/** Position d'un point quelconque sur le segment **/
	private Vecteur2D pointQuelconque;

	/** Objet Path2D permettant de représenter le segment **/
	private Path2D.Double segment;

	/**
	 * Constructeur du segment
	 *
	 * @param p0 La coordonnée du premier point
	 * @param p1 La coordonnée du second point
	 */
	// Enuel René Valentin Kizozo Izia
	public Segment(Point2D.Double p0, Point2D.Double p1) {
		try {
			extremiteA = new Vecteur2D(p0.getX(), p0.getY());
			extremiteB = new Vecteur2D(p1.getX(), p1.getY());

			longueur = extremiteB.soustrait(extremiteA).module();
			axe = extremiteB.soustrait(extremiteA).normalise();
			normale = new Vecteur2D(axe.getY(), -axe.getX());
			pointQuelconque = extremiteA.additionne(axe);

			creerLaGeometrie();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Permet de créer la géométrie du segment.
	 */
	// Enuel René Valentin Kizozo Izia
	public void creerLaGeometrie() {
		segment = new Path2D.Double();

		segment.moveTo(extremiteA.getX(), extremiteA.getY());
		segment.lineTo(extremiteB.getX(), extremiteB.getY());

	}

	/**
	 * Permet de dessiner un segment, sur le contexte graphique passé en parametre.
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
	public void dessiner(Graphics2D g2d) {
		/*
		 * À retirer éventuellement, mais permet de savoir si le segment est placé au
		 * bon endroit
		 */
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		g2dPrive.setColor(Color.red);
		g2dPrive.draw(segment);
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
	 * Retourne l'extrémité A du segment
	 *
	 * @return L'extrémité A du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteA() {
		return extremiteA;
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
	 * Retourne la longueur du segment
	 *
	 * @return La longueur du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLongueur() {
		return longueur;
	}

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
	 * Retourne la position d'un point quelconque sur le segment
	 *
	 * @return La position d'un point quelconque sur le segment
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getPointQuelconque() {
		return pointQuelconque;
	}

	/**
	 * Modifie le vecteur passant par l'axe du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setAxe() {
		try {
			this.axe = extremiteB.soustrait(extremiteA).normalise();
			setNormale();
			setPointQuelconque();
			creerLaGeometrie();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Modifie l'extrémité A du segment
	 *
	 * @param point L'objet point possédant les nouvelles coordonnées de l'extrémité
	 *              A
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteA(Point2D point) {
		this.extremiteA = new Vecteur2D(point.getX(), point.getY());
		setLongueur();
		setAxe();
		creerLaGeometrie();
	}

	/**
	 * Modifie l'extrémité B du segment
	 *
	 * @param point L'objet point possédant les nouvelles coordonnées de l'extrémité
	 *              B
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteB(Point2D point) {
		this.extremiteB = new Vecteur2D(point.getX(), point.getY());
		setLongueur();
		setAxe();
		creerLaGeometrie();
	}

	/**
	 * Modifie la longueur du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setLongueur() {
		this.longueur = extremiteB.soustrait(extremiteA).module();
		creerLaGeometrie();
	}

	/**
	 * Modifie le vecteur normal du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNormale() {
		this.normale = new Vecteur2D(axe.getY(), -axe.getX());
		creerLaGeometrie();
	}

	/**
	 * Modifie la position du point quelconque sur le segment
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPointQuelconque() {
		pointQuelconque = extremiteA.additionne(axe);
		creerLaGeometrie();
	}

	/**
	 * Permet d'afficher quelques caractéristiques du segment : Sa position et la
	 * position ses extrémités
	 *
	 * !!! La méthode provient d'anciens projets (auteur : Caroline Houle) mais a
	 * été implementé et modifier pour notre code !!!
	 *
	 * @param nbDecimales Le nombre souhaité de décimales après la virgule
	 * @return Une chaine présentant quelques caractéristiques du segment
	 */
	// Enuel René Valentin Kizozo Izia
	public String toString(int nbDecimales) {
		String s = " point A=[ " + String.format("%." + nbDecimales + "f", getExtremiteA().getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getExtremiteA().getY()) + "]";
		s += " point B=[ " + String.format("%." + nbDecimales + "f", getExtremiteB().getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getExtremiteB().getY()) + "]";
		return (s);
	}
}
