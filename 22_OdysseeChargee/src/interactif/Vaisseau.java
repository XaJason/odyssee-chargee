package interactif;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;
import utilis.Dessinable;

public class Vaisseau extends InteractifPhysique implements Dessinable {

	// PROPRIÉTÉS //
	/** Vitesse du vaisseau **/
	private Vecteur2D vitesse;

	/** Accélération du vaisseau **/
	private Vecteur2D accel;
	
	/** Somme des forces appliquée sur le vaisseau **/
	private Vecteur2D sommeForces = new Vecteur2D(0,0); //par defaut
	
	/** Rayon du vaisseau **/
	private double rayon;
	
	/** Forme servant de primitive pour le vaisseau **/
	Ellipse2D.Double cercle;
	
	// CONSTRUCTEUR //
	/**
	 * Constructeur du vaisseau
	 * 
	 * @param position
	 * @param vitesse
	 * @param accel
	 * @param rayon
	 * @param charge
	 * @param masse
	 */
	//Enuel René Valentin Kizozo Izia
	public Vaisseau(Vecteur2D position, Vecteur2D vitesse, Vecteur2D accel, double rayon, double charge, double masse) {
		super(position, charge, masse);
		this.vitesse = new Vecteur2D(vitesse);
		this.accel = new Vecteur2D(accel);
		this.rayon = rayon;
		creerLaGeometrie();
	}
	
	
	// SOUS-PROGRAMMES //
	private void creerLaGeometrie() {
		double coinx = getPosition().getX() - rayon;
		double coiny = getPosition().getY() - rayon;
		cercle = new Ellipse2D.Double(coinx, coiny, 2*rayon, 2*rayon);
	}
	
	/**
	 * Permet de dessiner un vaisseau, sur le contexte graphique passé en parametre.
	 * @param g2d
	 */
	//Enuel René Valentin Kizozo Izia
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		
		g2dPrive.setColor(Color.cyan);
		g2dPrive.scale(getPixelsParMetre(), getPixelsParMetre());
		g2dPrive.fill(cercle);
	}
	
	/**
	 * Calcule la nouvelle vitesse et la nouvelle position du vaisseau après
	 * un certain intervalle de temps.
	 * @param deltaT intervalle de temps (pas)
	 */
	//Enuel René Valentin Kizozo Izia
	public void avancerUnPas(double deltaT) {
		//System.out.println("Vitesse avant : "+vitesse);
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		//System.out.println("Vitesse après : "+vitesse);
		
		//System.out.println("Position avant : "+getPosition());
		super.setPosition( MoteurPhysique.calculPosition(deltaT, getPosition(), vitesse) );
		//System.out.println("Position après : "+getPosition());
		
		creerLaGeometrie();
	}
	
	public String toString(int nbDecimales){
		String s =  " position=[ " +  String.format("%."+nbDecimales+"f", getPosition().getX()) + ", " + String.format("%."+nbDecimales+"f", getPosition().getY())  + "]" ;
		s+= " vitesse=[ " +  String.format("%."+nbDecimales+"f", vitesse.getX()) + ", " + String.format("%."+nbDecimales+"f", vitesse.getY())  + "]" ;
		s+= " accel=[ " +  String.format("%."+nbDecimales+"f", accel.getX()) + ", " + String.format("%."+nbDecimales+"f", accel.getY())  + "]" ;
		s+= " forces=[ " +  String.format("%."+nbDecimales+"f", sommeForces.getX()) + ", " + String.format("%."+nbDecimales+"f", sommeForces.getY())  + "]" ;
		s+= " charge=[ " +  String.format("%."+nbDecimales+"f",getCharge()) + "]" ;
		return(s);
	}
	
	// GETTERS ET SETTERS //
	/**
	 * Recalcule l'accélération de la balle à l'aide de la nouvelle somme des forces passée en paramètre
	 * Ceci aura pour conséquence de modifier l'accélération
	 * @param sommeForcesSurVaisseau La somme des forces exercées sur la balle
	 */
	public void setSommeDesForces(Vecteur2D sommeForcesSurVaisseau) {
		//ici changer les forces signifie recalculer l'acceleration
		//on relegue cette tache au moteur physique. 
		try {
			sommeForces = new Vecteur2D(sommeForcesSurVaisseau); //on recopie à l'interne
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
	}
	
}
