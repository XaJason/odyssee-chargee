package blocs;

import physique.Vecteur2D;

/**
 * Permet de dessiner une plaque
 * @author Enuel René Valentin Kizozo Izia
 */
public class plaqueChargee extends blocs {

	// PROPRIÉTÉS //
	/** Vecteur normal de la plaque **/
	private Vecteur2D normale; //Doit être normalisé
	
	/** Vecteur passant par l'axe de la plaque **/
	private Vecteur2D axe = new Vecteur2D(normale.getY(), -normale.getX()); //normalisé
	
	/** Longueur de la plaque **/
	private double longueur;
	
	/** Charge de la plaque **/
	private double charge;
	
	/** Position de l'extrémité A de la plaque **/
	private Vecteur2D extremiteA = super.getPosition().additionne( axe.multiplie(longueur/2) );
	
	/** Position de l'extrémité B de la plaque **/
	private Vecteur2D extremiteB = super.getPosition().additionne( axe.multiplie(-longueur/2) );

	
	// CONSTRUCTEUR //
	/**
	 * Constructeur
	 * @param position
	 * @param normale
	 * @param longueur
	 * @param charge
	 */
	//Enuel René Valentin Kizozo Izia
	public plaqueChargee(Vecteur2D position, Vecteur2D normale, double longueur, double charge) {
		super(position);
		this.normale = normale;
		this.longueur = longueur;
		this.charge = charge;
	}

	/**
	 * Retourne le vecteur normal de la plaque
	 * @return Le vecteur normal de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getNormale() {
		return normale;
	}

	/**
	 * Modifie le vecteur normal de la plaque
	 * @param normale Vecteur incluant les composantes en x et y 
	 */
	//Enuel René Valentin Kizozo Izia
	public void setNormale(Vecteur2D normale) {
		this.normale = normale;
	}

	/**
	 * Retourne le vecteur passant par l'axe de la plaque
	 * @return Le vecteur passant par l'axe de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getAxe() {
		return axe;
	}

	/**
	 * Modifie le vecteur passant par l'axe de la plaque
	 * @param axe Vecteur incluant les composantes en x et y 
	 */
	//Enuel René Valentin Kizozo Izia
	public void setAxe(Vecteur2D axe) {
		this.axe = axe;
	}

	/**
	 * Retourne la longueur de la plaque
	 * @return La longueur de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public double getLongueur() {
		return longueur;
	}

	/**
	 * Modifie la longueur de la plaque
	 * @param longueur Longueur de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	/**
	 * Retourne la charge de la plaque
	 * @return La charge de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public double getCharge() {
		return charge;
	}

	/**
	 * Modifie la charge de la plaque
	 * @param charge Charge de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public void setCharge(double charge) {
		this.charge = charge;
	}

	/**
	 * Retourne l'extrémité A de la plaque
	 * @return L'extrémité A de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteA() {
		return extremiteA;
	}

	/**
	 * Modifie l'extrémité A de la plaque
	 * @param extremiteA Extrémité A de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public void setExtremiteA(Vecteur2D extremiteA) {
		this.extremiteA = extremiteA;
	}

	/**
	 * Retourne l'extrémité B de la plaque
	 * @return L'extrémité B de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteB() {
		return extremiteB;
	}

	/**
	 * Modifie l'extrémité B de la plaque
	 * @param extremiteB Extrémité B de la plaque
	 */
	//Enuel René Valentin Kizozo Izia
	public void setExtremiteB(Vecteur2D extremiteB) {
		this.extremiteB = extremiteB;
	}
	
}
