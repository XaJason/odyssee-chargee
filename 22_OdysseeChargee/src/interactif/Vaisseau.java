package interactif;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;
import utilitaires.Dessinable;

/**
 * Classe Vaisseau: représentation sommaire d'un vaisseau à l'aide d'un cercles.
 * Un vaisseau mémorise sa masse, sa charge, son rayon, sa position, sa vitesse,
 * son accélération
 * et la somme des forces qui s'applique sur lui.
 * 
 * Une vaisseau a une methode qui permet de s'avancer d'un pas
 * ainsi que des méthodes pour gérer les collisions
 * 
 * @author Enuel René Valentin Kizozo Izia
 */
public class Vaisseau extends InteractifPhysique implements Dessinable {

	// PROPRIÉTÉS //
	/** Vitesse du vaisseau **/
	private Vecteur2D vitesse;

	/** Accélération du vaisseau **/
	private Vecteur2D accel;

	/** Somme des forces appliquée sur le vaisseau **/
	private Vecteur2D sommeForces = new Vecteur2D(0, 0); // par defaut

	/** Rayon du vaisseau **/
	private double rayon;

	/** Masse du vaisseau **/
	private double masse;
	
	/** Forme servant de primitive pour le vaisseau **/
	private Ellipse2D.Double cercle;

	// CONSTRUCTEUR //
	/**
	 * Constructeur du vaisseau
	 * 
	 * @param position La position du vaisseau
	 * @param vitesse  La vitesse du vaisseau
	 * @param accel    L'accélération du vaisseau
	 * @param rayon    Le rayon du vaisseau
	 * @param charge   La charge du vaisseau
	 * @param masse    La masse du vaisse
	 */
	// Enuel René Valentin Kizozo Izia
	public Vaisseau(Vecteur2D position, Vecteur2D vitesse, Vecteur2D accel, double rayon, double charge, double masse) {
		super(position, charge);
		this.vitesse = new Vecteur2D(vitesse);
		this.accel = new Vecteur2D(accel);
		this.rayon = rayon;
		this.masse = masse;
		creerLaGeometrie();
	}

	// SOUS-PROGRAMMES //
	/**
	 * Permet de créer la géométrie du vaisseau.
	 */
	// Enuel René Valentin Kizozo Izia
	public void creerLaGeometrie() {
		double coinx = getPosition().getX() - rayon;
		double coiny = getPosition().getY() - rayon;
		cercle = new Ellipse2D.Double(coinx, coiny, 2 * rayon, 2 * rayon);
	}

	/**
	 * Permet de dessiner un vaisseau, sur le contexte graphique passé en parametre.
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		g2dPrive.setColor(Color.cyan);
		// g2dPrive.scale(getPixelsParMetre(), getPixelsParMetre());
		g2dPrive.fill(cercle);
	}

	/**
	 * Calcule la nouvelle vitesse et la nouvelle position du vaisseau après
	 * un certain intervalle de temps.
	 * 
	 * @param deltaT intervalle de temps (pas de simulation)
	 */
	// Enuel René Valentin Kizozo Izia
	public void avancerUnPas(double deltaT) {
		// System.out.println("Vitesse avant : "+vitesse);
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		// System.out.println("Vitesse après : "+vitesse);

		// System.out.println("Position avant : "+getPosition());
		super.setPosition(MoteurPhysique.calculPosition(deltaT, getPosition(), vitesse));
		// System.out.println("Position après : "+getPosition());

		creerLaGeometrie();
	}

	/**
	 * Détermine s'il y a collision avec une plaque,
	 * puis modifie la vitesse du vaisseau en conséquence
	 * 
	 * @param plaque La plaque avec laquelle le vaisseau entre en collision
	 */
	// Enuel René Valentin Kizozo Izia
	public void gererCollisionAvecPlaque(PlaqueChargee plaque) {
		/*
		 * À modifier éventuellement, car il n'y a pas que des collisions avec des
		 * plaques
		 * Faire de la surdéfinition (même méthodes avec comme paramètre, chaque objet
		 * différent)
		 * OU
		 * Faire du polymorphisme, une méthode avec comme paramètre InteractifPhysique,
		 * mais faudra gérer différement les paramètres des méthodes
		 * detectionCollisions et calculVitesseApresCollision
		 */
		vitesse = MoteurPhysique.detectionCollisionsAvecPlaqueEtCalculeVitesse(this, plaque);
		creerLaGeometrie();
	}

	/**
	 * Détermine s'il y a une collision avec les bordures de la zone d'animation,
	 * puis modifie la vitesse en conséquence
	 * 
	 * @param largeurComposant La largeur de la zone d'animation, en mètre
	 * @param hauteurComposant La hauteur de la zone d'animation, en mètre
	 */
	// Enuel René Valentin Kizozo Izia
	public void gererCollisionAvecBordures(double largeurComposant, double hauteurComposant) {
		vitesse = MoteurPhysique.detectionCollisionsBorduresEtCalculVitesse(this, largeurComposant, hauteurComposant);
		creerLaGeometrie();
	}

	/**
	 * Permet d'afficher quelques caractéristiques du vaisseau :
	 * Sa position, sa vitesse, son accélération, la somme des forces agissant sur
	 * lui et sa charge
	 * 
	 * !!! La méthode provient d'anciens projets (ex-auteur : Caroline Houle) mais a
	 * été implementé et
	 * modifier pour notre code !!!
	 * 
	 * @param nbDecimales Le nombre souhaité de décimales après la virgule
	 * @return Une chaine présentant quelques caractéristiques du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public String toString(int nbDecimales) {
		String s = " position=[ " + String.format("%." + nbDecimales + "f", getPosition().getX()) + ", "
				+ String.format("%." + nbDecimales + "f", getPosition().getY()) + "]";
		s += " vitesse=[ " + String.format("%." + nbDecimales + "f", vitesse.getX()) + ", "
				+ String.format("%." + nbDecimales + "f", vitesse.getY()) + "]";
		s += " accel=[ " + String.format("%." + nbDecimales + "f", accel.getX()) + ", "
				+ String.format("%." + nbDecimales + "f", accel.getY()) + "]";
		s += " forces=[ " + String.format("%." + nbDecimales + "f", sommeForces.getX()) + ", "
				+ String.format("%." + nbDecimales + "f", sommeForces.getY()) + "]";
		s += " charge=[ " + String.format("%." + nbDecimales + "f", getCharge()) + "]";
		return (s);
	}

	// GETTERS ET SETTERS //
	/**
	 * Recalcule l'accélération du vaisseau à l'aide de la nouvelle somme des forces
	 * passée en paramètre
	 * Ceci aura pour conséquence de modifier l'accélération
	 * 
	 * !!! La méthode provient d'anciens projets (ex-auteur : Caroline Houle) mais a
	 * été implementé et
	 * modifier pour notre code !!!
	 * 
	 * @param sommeForcesSurVaisseau La somme des forces exercées sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setSommeDesForces(Vecteur2D sommeForcesSurVaisseau) {
		try {
			sommeForces = new Vecteur2D(sommeForcesSurVaisseau);
			accel = MoteurPhysique.calculAcceleration(sommeForces, getMasse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne la vitesse du vaisseau
	 * 
	 * @return La vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getVitesse() {
		return vitesse;
	}

	/**
	 * Modifie la vitesse du vaisseau
	 * 
	 * @param vitesse Vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setVitesse(Vecteur2D vitesse) {
		this.vitesse = new Vecteur2D(vitesse);
	}

	/**
	 * Retourne l'accélération du vaisseau
	 * 
	 * @return L'accélération du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getAccel() {
		return accel;
	}

	/**
	 * Modifie l'accélération du vaisseau
	 * 
	 * @param accel Accélération du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setAccel(Vecteur2D accel) {
		this.accel = new Vecteur2D(accel);
	}

	/**
	 * Retourne le rayon du vaisseau
	 * 
	 * @return Le rayon du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getRayon() {
		return rayon;
	}

	/**
	 * Modifie le rayon du vaisseau
	 * 
	 * @param rayon Rayon du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setRayon(double rayon) {
		this.rayon = rayon;
		creerLaGeometrie();
	}

	/**
	 * Retourne la masse du vaisseau
	 * 
	 * @return La masse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getMasse() {
		return masse;
	}

	/**
	 * Modifie la masse du vaisseau
	 * 
	 * @param masse Masse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setMasse(double masse) {
		this.masse = masse;
	}
}
