package interactif;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import physique.Vecteur2D;
import utilis.Dessinable;

/**
 * Permet de dessiner une plaque
 * 
 * @author Enuel René Valentin Kizozo Izia
 */
public class PlaqueChargee extends InteractifPhysique implements Dessinable {

	// PROPRIÉTÉS //
	/** Vecteur normal de la plaque **/
	private Vecteur2D normale; // Doit être normalisé

	/** Vecteur passant par l'axe de la plaque **/
	private Vecteur2D axe; // Normalisé

	/** Longueur de la plaque **/
	private double longueur;
	
	/** Position de l'extrémité A de la plaque **/
	private Vecteur2D extremiteA;

	/** Position de l'extrémité B de la plaque **/
	private Vecteur2D extremiteB;
	
	
	// CONSTRUCTEUR //
	/**
	 * Constructeur de la plaque chargée
	 * 
	 * @param position
	 * @param normale
	 * @param longueur
	 * @param charge
	 */
	//Enuel René Valentin Kizozo Izia
	public PlaqueChargee(Vecteur2D position, Vecteur2D normale, double longueur, double charge, double masse) {
		super(position, charge, masse);
		this.normale = new Vecteur2D(normale);
		this.longueur = longueur;
		
		this.axe = new Vecteur2D(normale.getY(), -normale.getX());
		this.extremiteA = getPosition().additionne(axe.multiplie(longueur / 2));
		this.extremiteB = getPosition().additionne(axe.multiplie(-longueur / 2));
	}

	
	// SOUS-PROGRAMMES
	/**
	 * Permet de dessiner un objet intéractif physique, sur le contexte graphique passé en parametre.
	 * @param g2d
	 */
	//Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {	
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		double coinx = getPosition().getX();
		double coiny = getPosition().getY() - longueur/2;
		Path2D.Double plaque = new Path2D.Double();
		plaque.moveTo(coinx, coiny);
		plaque.lineTo(coinx, coiny+longueur);

		g2dPrive.setColor(Color.red);
		g2dPrive.scale(getPixelsParMetre(), getPixelsParMetre());
		g2dPrive.draw(plaque);
	}
	
	public String toString(int nbDecimales){
		String s =  " position=[ " +  String.format("%."+nbDecimales+"f", getPosition().getX()) + ", " + String.format("%."+nbDecimales+"f", getPosition().getY())  + "]" ;
		s+= " charge=[ " +  String.format("%."+nbDecimales+"f",getCharge()) + "]" ;
		return(s);
	}	
	
	// GETTERS & SETTERS //
	/**
	 * Retourne le vecteur normal de la plaque
	 * 
	 * @return Le vecteur normal de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getNormale() {
		return normale;
	}

	/**
	 * Modifie le vecteur normal de la plaque
	 * 
	 * @param normale Vecteur incluant les composantes en x et y
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNormale(Vecteur2D normale) {
		this.normale = new Vecteur2D(normale);
	}

	/**
	 * Retourne le vecteur passant par l'axe de la plaque
	 * 
	 * @return Le vecteur passant par l'axe de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getAxe() {
		return axe;
	}

	/**
	 * Modifie le vecteur passant par l'axe de la plaque
	 * 
	 * @param axe Vecteur incluant les composantes en x et y
	 */
	// Enuel René Valentin Kizozo Izia
	public void setAxe(Vecteur2D axe) {
		this.axe = new Vecteur2D(axe);
	}

	/**
	 * Retourne la longueur de la plaque
	 * 
	 * @return La longueur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLongueur() {
		return longueur;
	}

	/**
	 * Modifie la longueur de la plaque
	 * 
	 * @param longueur Longueur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	/**
	 * Retourne l'extrémité A de la plaque
	 * 
	 * @return L'extrémité A de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteA() {
		return extremiteA;
	}

	/**
	 * Modifie l'extrémité A de la plaque
	 * 
	 * @param extremiteA Extrémité A de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteA(Vecteur2D extremiteA) {
		this.extremiteA = new Vecteur2D(extremiteA);
	}

	/**
	 * Retourne l'extrémité B de la plaque
	 * 
	 * @return L'extrémité B de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteB() {
		return extremiteB;
	}

	/**
	 * Modifie l'extrémité B de la plaque
	 * 
	 * @param extremiteB Extrémité B de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteB(Vecteur2D extremiteB) {
		this.extremiteB = new Vecteur2D(extremiteB);
	}

}
