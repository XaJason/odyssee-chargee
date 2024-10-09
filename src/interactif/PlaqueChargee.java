package interactif;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import physique.Vecteur2D;
import utilitaires.Dessinable;

/**
 * Classe plaque: représentation sommaire d'une plaque à l'aide d'un segment de
 * droite
 *
 * @author Enuel René Valentin Kizozo Izia
 */
public class PlaqueChargee extends InteractifPhysique implements Dessinable, Serializable {

	/** Constante permettant la sérialisation de la classe **/
	private static final long serialVersionUID = -2406092356655374864L;

	/** Vecteur passant par l'axe de la plaque **/
	private Vecteur2D axe = new Vecteur2D(0, 1); // Normalisé, vecteur unitaire j par défaut

	/** Position du coin inféieur droit **/
	private Vecteur2D coinInfDroit;
	/** Position du coin inférieur gauche **/
	private Vecteur2D coinInfGauche;
	/** Position du coin supérieur droit **/
	private Vecteur2D coinSupDroit;
	/** Position du coin supérieur gauche **/
	private Vecteur2D coinSupGauche;

	/** Largeur de la plaque **/
	private double epaisseur = 1.5;
	/** Position de l'extrémité A de la plaque **/
	private Vecteur2D extremiteA;
	/** Position de l'extrémité B de la plaque **/
	private Vecteur2D extremiteB;

	/** Objet Path2D permettant de représenter la plaque **/
	private Path2D.Double formePlaque;
	/** Longueur de la plaque **/
	private double longueur = 10;
	/** Vecteur normal de la plaque **/
	private Vecteur2D normale = new Vecteur2D(1, 0); // Normalisé, vecteur unitaire i par défaut

	/**
	 * Constructeur de la plaque chargée
	 *
	 * @param charge La charge de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public PlaqueChargee(double charge) {
		super(charge);
		try {
			this.extremiteA = getPosition().additionne(axe.multiplie(longueur / 2));
			this.extremiteB = getPosition().additionne(axe.multiplie(-longueur / 2));

			this.coinSupGauche = extremiteA.additionne(normale.multiplie(epaisseur / 2));
			this.coinSupDroit = extremiteA.additionne(normale.multiplie(-epaisseur / 2));
			this.coinInfGauche = extremiteB.additionne(normale.multiplie(epaisseur / 2));
			this.coinInfDroit = extremiteB.additionne(normale.multiplie(-epaisseur / 2));

			creerLaGeometrie();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Constructeur de la plaque chargée
	 *
	 * @param charge La charge de la plaque
	 * @param p0     La coordonnée du premier point
	 * @param p1     La coordonnée du second point
	 */
	// Enuel René Valentin Kizozo Izia
	public PlaqueChargee(double charge, Point2D.Double p0, Point2D.Double p1) {
		super(charge);
		try {
			this.extremiteA = new Vecteur2D(p0.getX(), p0.getY());
			this.extremiteB = new Vecteur2D(p1.getX(), p1.getY());

			this.longueur = extremiteB.soustrait(extremiteA).module();
			this.axe = extremiteB.soustrait(extremiteA).normalise();
			this.normale = new Vecteur2D(axe.getY(), -axe.getX());
			setPosition(extremiteA.additionne(axe.multiplie(longueur / 2.0)));

			this.coinSupGauche = extremiteA.additionne(normale.multiplie(epaisseur / 2));
			this.coinSupDroit = extremiteA.additionne(normale.multiplie(-epaisseur / 2));
			this.coinInfGauche = extremiteB.additionne(normale.multiplie(epaisseur / 2));
			this.coinInfDroit = extremiteB.additionne(normale.multiplie(-epaisseur / 2));

			creerLaGeometrie();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Constructeur de la plaque chargée
	 *
	 * @param position La position de la plaque
	 * @param charge   La charge de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public PlaqueChargee(Vecteur2D position, double charge) {
		super(position, charge);
		try {
			this.extremiteA = position.additionne(axe.multiplie(longueur / 2));
			this.extremiteB = position.additionne(axe.multiplie(-longueur / 2));

			this.coinSupGauche = extremiteA.additionne(normale.multiplie(epaisseur / 2));
			this.coinSupDroit = extremiteA.additionne(normale.multiplie(-epaisseur / 2));
			this.coinInfGauche = extremiteB.additionne(normale.multiplie(epaisseur / 2));
			this.coinInfDroit = extremiteB.additionne(normale.multiplie(-epaisseur / 2));

			creerLaGeometrie();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Constructeur de la plaque chargée
	 *
	 * @param position La position de la plaque
	 * @param charge   La charge de la plaque
	 * @param p0       La coordonnée du premier point
	 * @param p1       La coordonnée du second point
	 */
	// Enuel René Valentin Kizozo Izia
	public PlaqueChargee(Vecteur2D position, double charge, Point2D.Double p0, Point2D.Double p1) {
		super(position, charge);
		try {
			this.extremiteA = new Vecteur2D(p0.getX(), p0.getY());
			this.extremiteB = new Vecteur2D(p1.getX(), p1.getY());

			this.longueur = extremiteB.soustrait(extremiteA).module();
			this.axe = extremiteB.soustrait(extremiteA).normalise();
			this.normale = new Vecteur2D(axe.getY(), -axe.getX());

			this.coinSupGauche = extremiteA.additionne(normale.multiplie(epaisseur / 2));
			this.coinSupDroit = extremiteA.additionne(normale.multiplie(-epaisseur / 2));
			this.coinInfGauche = extremiteB.additionne(normale.multiplie(epaisseur / 2));
			this.coinInfDroit = extremiteB.additionne(normale.multiplie(-epaisseur / 2));

			creerLaGeometrie();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Permet de savoir si le curseur de la souris est à l'intérieur de la plaque
	 * chargée
	 *
	 * @param x La coordonnée X du curseur de la souris
	 * @param y La coordonnée Y du curseur de la souris
	 * @return Un booléen indiquant si le curseur se trouve à l'intérieur de la
	 *         plaque chargée
	 */
	// Enuel René Valentin Kizozo Izia
	public boolean contient(double x, double y) {
		return formePlaque.contains(x, y);
	}

	/**
	 * Permet de créer la géométrie de la plaque chargée.
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
	public void creerLaGeometrie() {
		formePlaque = new Path2D.Double();

		formePlaque.moveTo(coinInfGauche.getX(), coinInfGauche.getY());
		formePlaque.lineTo(coinSupGauche.getX(), coinSupGauche.getY());
		formePlaque.lineTo(coinSupDroit.getX(), coinSupDroit.getY());
		formePlaque.lineTo(coinInfDroit.getX(), coinInfDroit.getY());
		formePlaque.closePath();
	}

	/**
	 * Permet de dessiner un objet intéractif physique, sur le contexte graphique
	 * passé en parametre.
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		if (Math.signum(getCharge()) > 0) {
			g2dPrive.setColor(Color.green);
		} else {
			g2dPrive.setColor(Color.magenta);
		}

		g2dPrive.fill(formePlaque);
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
	 * Retourne le coin inférieur droit de la plaque
	 *
	 * @return Le coin inférieur droit de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getCoinInfDroit() {
		return coinInfDroit;
	}

	/**
	 * Retourne le coin inférieur gauche de la plaque
	 *
	 * @return Le coin inférieur gauche de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getCoinInfGauche() {
		return coinInfGauche;
	}

	/**
	 * Retourne le coin supérieur droit de la plaque
	 *
	 * @return Le coin supérieur droit de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getCoinSupDroit() {
		return coinSupDroit;
	}

	/**
	 * Retourne le coin supérieur gauche de la plaque
	 *
	 * @return Le coin supérieur gauche de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getCoinSupGauche() {
		return coinSupGauche;
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
	 * Retourne l'extrémité B de la plaque
	 *
	 * @return L'extrémité B de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getExtremiteB() {
		return extremiteB;
	}

	/**
	 * Retourne la largeur de la plaque
	 *
	 * @return La largeur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLargeur() {
		return epaisseur;
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
	 * Retourne le vecteur normal de la plaque
	 *
	 * @return Le vecteur normal de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getNormale() {
		return normale;
	}

	/**
	 * Modifie le vecteur passant par l'axe de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void miseAJourAxe() {
		try {
			this.axe = extremiteB.soustrait(extremiteA).normalise();
			setNormale();
			creerLaGeometrie();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Modifie l'extrémité A de la plaque (lorsque l'axe ou la longuer ont été
	 * modifiés)
	 */
	// Enuel René Valentin Kizozo Izia
	public void miseAJourExtremiteA() {
		this.extremiteA = getPosition().additionne(axe.multiplie(longueur / 2));
		setCoinSupGauche();
		setCoinSupDroit();
		creerLaGeometrie();
	}

	/**
	 * Modifie l'extrémité B de la plaque (lorsque l'axe ou la longuer ont été
	 * modifiés)
	 */
	// Enuel René Valentin Kizozo Izia
	public void miseAJourExtremiteB() {
		this.extremiteB = getPosition().additionne(axe.multiplie(-longueur / 2));
		setCoinInfGauche();
		setCoinInfDroit();
		creerLaGeometrie();
	}

	/**
	 * Modifie la longueur de la plaque (lorsque les extrémités ont été modifiées)
	 */
	// Enuel René Valentin Kizozo Izia
	public void miseAJourLongueur() {
		this.longueur = extremiteB.soustrait(extremiteA).module();
		creerLaGeometrie();
	}

	/**
	 * Modifie la largeur de la plaque
	 *
	 * @param epaisseur L'épaisseur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setEpaisseur(double epaisseur) {
		this.epaisseur = epaisseur;
		setCoinSupGauche();
		setCoinSupDroit();
		setCoinInfGauche();
		setCoinInfDroit();
		creerLaGeometrie();
	}

	/**
	 * Modifie l'extrémité A de la plaque
	 *
	 * @param point L'objet point possédant les nouvelles coordonnées de l'extrémité
	 *              A
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteA(Point point) {
		this.extremiteA = new Vecteur2D(point.getX(), point.getY());
		miseAJourLongueur();
		miseAJourAxe(); // Modifie également la normale
		setCoinSupGauche();
		setCoinSupDroit();
		creerLaGeometrie();
	}

	/**
	 * Modifie l'extrémité B de la plaque
	 *
	 * @param point L'objet point possédant les nouvelles coordonnées de l'extrémité
	 *              A
	 */
	// Enuel René Valentin Kizozo Izia
	public void setExtremiteB(Point point) {
		this.extremiteB = new Vecteur2D(point.getX(), point.getY());
		miseAJourLongueur();
		miseAJourAxe(); // Modifie également la normale
		setCoinInfGauche();
		setCoinInfDroit();
		creerLaGeometrie();
	}

	/**
	 * Modifie la longueur de la plaque
	 *
	 * @param longueur Longueur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setLongueur(double longueur) {
		this.longueur = longueur;
		miseAJourExtremiteA(); // Modifie les coins supérieurs
		miseAJourExtremiteB(); // Modifie les coins inférieurs
		creerLaGeometrie();
	}

	/**
	 * Modifie le vecteur normal de la plaque (lorsque l'axe a été modifié)
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNormale() {
		this.normale = new Vecteur2D(axe.getY(), -axe.getX());
		creerLaGeometrie();
	}

	/**
	 * Modifie le vecteur normal de la plaque
	 *
	 * @param normale Vecteur incluant les composantes en x et y
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNormale(Vecteur2D normale) {
		this.normale = new Vecteur2D(normale);
		setAxe(normale); // Modifie aussi les extrémités A et B
		setCoinSupGauche();
		setCoinSupDroit();
		setCoinInfGauche();
		setCoinInfDroit();
		creerLaGeometrie();
	}

	/**
	 * Permet d'afficher quelques caractéristiques de la plaque : Sa position, sa
	 * charge et la position ses extrémités
	 *
	 * !!! La méthode provient d'anciens projets (auteur : Caroline Houle) mais a
	 * été implementé et modifier pour notre code !!!
	 *
	 * @param nbDecimales Le nombre souhaité de décimales après la virgule
	 * @return Une chaine présentant quelques caractéristiques de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public String toString(int nbDecimales) {
		String s = " position=[ " + String.format("%." + nbDecimales + "f", getPosition().getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getPosition().getY()) + "]";
		s += " charge=[ " + String.format("%." + nbDecimales + "f", getCharge()) + "]";
		s += " longueur=[ " + String.format("%." + nbDecimales + "f", longueur) + "]";
		s += " épaisseur=[ " + String.format("%." + nbDecimales + "f", epaisseur) + "]";
		s += "\n\t extrémité A=[ " + String.format("%." + nbDecimales + "f", extremiteA.getX()) + ", "
				+ String.format("%." + nbDecimales + "f", extremiteA.getY()) + "]";
		s += " extrémité B=[ " + String.format("%." + nbDecimales + "f", extremiteB.getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getExtremiteB().getY()) + "]";
		s += " normale=[ " + String.format("%." + nbDecimales + "f", normale.getX()) + ", "
				+ String.format("%." + nbDecimales + "f", normale.getY()) + "]";
		s += " axe=[ " + String.format("%." + nbDecimales + "f", axe.getX()) + ", "
				+ String.format("%." + nbDecimales + "f", axe.getY()) + "]";
		return (s);
	}

	/**
	 * Modifie le vecteur passant par l'axe de la plaque
	 *
	 * @param normale La normale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	private void setAxe(Vecteur2D normale) {
		this.axe = new Vecteur2D(normale.getY(), -normale.getX());
		miseAJourExtremiteA(); // Modifie les coins supérieurs
		miseAJourExtremiteB(); // Modifie les coins inférieurs
		creerLaGeometrie();
	}

	/**
	 * Modifie le coin inférieur droit de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	private void setCoinInfDroit() {
		this.coinInfDroit = extremiteB.additionne(normale.multiplie(-epaisseur / 2));
	}

	/**
	 * Modifie le coin inférieur gauche de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	private void setCoinInfGauche() {
		this.coinInfGauche = extremiteB.additionne(normale.multiplie(epaisseur / 2));
	}

	/**
	 * Modifie le coin supérieur droit de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	private void setCoinSupDroit() {
		this.coinSupDroit = extremiteA.additionne(normale.multiplie(-epaisseur / 2));
	}

	/**
	 * Modifie le coin supérieur gauche de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	private void setCoinSupGauche() {
		this.coinSupGauche = extremiteA.additionne(normale.multiplie(epaisseur / 2));
	}

}
