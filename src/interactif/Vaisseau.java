package interactif;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import physique.MoteurPhysique;
import physique.Segment;
import physique.Vecteur2D;
import tuile.VaisseauImage;
import utilitaires.Dessinable;

/**
 * Classe Vaisseau: représentation sommaire d'un vaisseau à l'aide d'un cercles.
 * Un vaisseau mémorise sa masse, sa charge, son rayon, sa position, sa vitesse,
 * son accélération et la somme des forces qui s'applique sur lui.
 *
 * Une vaisseau a une methode qui permet de s'avancer d'un pas ainsi que des
 * méthodes pour gérer les collisions
 *
 * @author Enuel René Valentin Kizozo Izia
 */
public class Vaisseau extends InteractifPhysique implements Dessinable, Serializable {

	/** Constante permettant la sérialization de la classe **/
	private static final long serialVersionUID = -1773798144841043627L;

	/** Accélération du vaisseau (en m/s^2) **/
	private Vecteur2D accel = new Vecteur2D(0, 0); // par defaut
	/** Forme servant de primitive pour le vaisseau **/
	private Ellipse2D.Double cercle;
	/** Booléen qui indique si une collision avec un segment a été trouvée **/
	private boolean collisionTrouvee = false;
	/** Durée de la collision (en milliseconde) **/
	private double dureeCollision = 0;
	/** Booléen qui indique si le vaisseau est en collision **/
	private boolean enCollision = false;
	/** Force normale agissant sur le vaisseau **/
	private Vecteur2D forceNormale = new Vecteur2D(0, 0); // par defaut;

	/** Masse du vaisseau (en kg) **/
	private double masse;

	/** Module de la vitesse limite du vaisseau (en m/s) **/
	private final double MODULE_VITESSE_LIMITE = 300;
	/** Position du vaisseau à l'itération précédente **/
	private Vecteur2D positionPrecedente;

	/** Rayon du vaisseau (en mètre) **/
	private double rayon = 5.0;

	/** Somme des forces appliquée sur le vaisseau (en Newton) **/
	private Vecteur2D sommeForces = new Vecteur2D(0, 0); // par defaut
	/** Temps de la dernière collision (en milliseconde) **/
	private double tempsDerniereCollision;
	/**
	 * Objet VaisseauImage permettant d'accéder aux propriétés de la tuile du
	 * vaisseau
	 **/
	private VaisseauImage tuile;
	/** Vitesse du vaisseau (en m/s) **/
	private Vecteur2D vitesse = new Vecteur2D(0, 0); // par defaut

	/**
	 * Constructeur du vaisseau
	 *
	 * @param position        La position du vaisseau
	 * @param charge          La charge du vaisseau
	 * @param masse           La masse du vaisse
	 * @param tuileDuVaisseau L'objet tuile représentant le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vaisseau(Vecteur2D position, double charge, double masse, VaisseauImage tuileDuVaisseau) {
		super(position, charge);
		this.positionPrecedente = new Vecteur2D(position);
		this.masse = masse;
		this.tuile = tuileDuVaisseau;
		creerLaGeometrie();
	}

	/**
	 * Calcule la nouvelle vitesse et la nouvelle position du vaisseau après un
	 * certain intervalle de temps.
	 *
	 * @param deltaT intervalle de temps (pas de simulation)
	 */
	// Enuel René Valentin Kizozo Izia
	public void avancerUnPas(double deltaT) {
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		// Limite la vitesse du vaisseau
		if (vitesse.module() > MODULE_VITESSE_LIMITE) {
			vitesse = vitesse.changerModule(MODULE_VITESSE_LIMITE);
		}

		positionPrecedente = new Vecteur2D(getPosition());
		super.setPosition(MoteurPhysique.calculPosition(deltaT, getPosition(), vitesse));

		creerLaGeometrie();
	}

	/**
	 * Permet de créer la géométrie du vaisseau.
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
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
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		boolean dessinerCercle = true;

		// Choisit la couleur de fond du vaisseau (cercle) en fonction du signe de sa
		// charge
		if (Math.signum(getCharge()) > 0) {
			g2dPrive.setColor(Color.green);

		} else if (Math.signum(getCharge()) < 0) {
			g2dPrive.setColor(Color.magenta);

		} else {
			g2dPrive.setColor(Color.black);
			dessinerCercle = false;
		}

		// Dessine le fond du vaisseau
		if (dessinerCercle) {
			g2dPrive.fill(cercle);
		} else {
			g2dPrive.draw(cercle);
		}

		// Dessine l'image du vaisseau à l'aide de la méthode dessiner de sa tuile
		tuile.dessiner(g2d, (getPosition().getX() - rayon), (getPosition().getY() - rayon));
	}

	/**
	 * Méthode qui forme l'aire d'un objet vaisseau Utile pour les collisions avec
	 * des objets définis par un Area (pics, drapeau, portail)
	 *
	 * @return la forme du vaisseau dans un area
	 */
	// Enuel René Valentin Kizozo Izia
	public Area formerAireDuVaisseau() {
		return new Area(cercle);
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
	 * Détermine s'il y a collision avec un coin, puis modifie la vitesse du
	 * vaisseau en conséquence
	 *
	 * @param coin Le coin d'un bloc avec laquelle le vaisseau entre en collision
	 */
	// Enuel René Valentin Kizozo Izia
	public void gererCollisionAvecCoin(Point2D.Double coin) {
		vitesse = MoteurPhysique.detectionCollisionAvecCoinEtCalculeVitesse(this, coin);
		creerLaGeometrie();
	}

	/**
	 * Détermine s'il y a collision avec une plaque, puis modifie la vitesse du
	 * vaisseau en conséquence
	 *
	 * @param plaque La plaque avec laquelle le vaisseau entre en collision
	 */
	// Enuel René Valentin Kizozo Izia
	public void gererCollisionAvecPlaque(PlaqueChargee plaque) {
		vitesse = MoteurPhysique.detectionCollisionsAvecPlaqueEtCalculeVitesse(this, plaque);
		creerLaGeometrie();
	}

	/**
	 * Détermine s'il y a collision avec un segment, puis modifie la vitesse du
	 * vaisseau en conséquence
	 *
	 * @param segment Le segment avec laquelle le vaisseau entre en collision
	 */
	// Enuel René Valentin Kizozo Izia
	public void gererCollisionAvecSegment(Segment segment) {
		vitesse = MoteurPhysique.detectionCollisionsAvecSegmentEtCalculeVitesse(this, segment);
		creerLaGeometrie();
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
	 * Retourne le booléen qui indique si une collision avec un segment a été
	 * trouvée
	 *
	 * @return Le booléen qui indique si une collision avec un segment a été trouvée
	 */
	// Enuel René Valentin Kizozo Izia
	public boolean getCollisionTrouvee() {
		return collisionTrouvee;
	}

	/**
	 * Retourne la durée de la collision
	 *
	 * @return La durée de la collision
	 */
	// Enuel René Valentin Kizozo Izia
	public double getDureeCollision() {
		return dureeCollision;
	}

	/**
	 * Retourne le booléen qui indique si le vaisseau est en collision
	 *
	 * @return Le booléen qui indique si le vaisseau est en collision
	 */
	// Enuel René Valentin Kizozo Izia
	public boolean getEnCollision() {
		return enCollision;
	}

	/**
	 * Retourne la force normale agissant sur le vaisseau
	 *
	 * @return La force normale agissant sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getForceNormale() {
		return forceNormale;
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
	 * Retourne le module de la vitesse limite du vaisseau
	 *
	 * @return Le module de la vitesse limite du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getModuleVitesseLimite() {
		return MODULE_VITESSE_LIMITE;
	}

	/**
	 * Retourne la position du vaisseau à l'itération précédente
	 *
	 * @return La position du vaisseau à l'itération précédente
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getPositionPrecedente() {
		return positionPrecedente;
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
	 * Retourne la somme des forces agissant sur le vaisseau
	 *
	 * @return La somme des forces agissant sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getSommeDesForces() {
		return sommeForces;
	}

	/**
	 * Retourne l'objet VaisseauImage qui contient les propriétés de la tuile du
	 * vaisseau
	 *
	 * @return L'objet VaisseauImage
	 */
	// Enuel René Valentin Kizozo Izia
	public VaisseauImage getTuile() {
		return tuile;
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
	 * Modifie l'accélération du vaisseau
	 *
	 * @param accel Accélération du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setAccel(Vecteur2D accel) {
		this.accel = new Vecteur2D(accel);
	}

	/**
	 * Modifie le booléen qui indique si une collision avec un segment a été trouvée
	 *
	 * @param collisionTrouvee Le nouveau booléen qui indique si une collision avec
	 *                         un segment a été trouvée
	 */
	// Enuel René Valentin Kizozo Izia
	public void setCollisionTrouvee(boolean collisionTrouvee) {
		this.collisionTrouvee = collisionTrouvee;
	}

	/**
	 * Modifie la durée de la collision
	 *
	 * @param dureeCollision La nouvelle durée de la collision
	 */
	// Enuel René Valentin Kizozo Izia
	public void setDureeCollision(double dureeCollision) {
		this.dureeCollision = dureeCollision;
	}

	/**
	 * Modifie le booléen qui indique si le vaisseau est en collision
	 *
	 * @param nouvelEtatDeCollision Le nouveau booléen qui indique si le vaisseau
	 *                              est en collision
	 */
	// Enuel René Valentin Kizozo Izia
	public void setEnCollision(boolean nouvelEtatDeCollision) {
		double tempsActuel = System.currentTimeMillis();
		boolean ancienEtatDeCollision = enCollision;

		// Faux Vrai
		if (!ancienEtatDeCollision & nouvelEtatDeCollision) {
			this.enCollision = nouvelEtatDeCollision;
			tempsDerniereCollision = tempsActuel;
		}
		// Vrai Vrai
		else if (ancienEtatDeCollision & nouvelEtatDeCollision) {
			setDureeCollision(tempsActuel - tempsDerniereCollision);
		}
		// Vrai Faux
		else if (ancienEtatDeCollision & !nouvelEtatDeCollision) {
			this.enCollision = nouvelEtatDeCollision;
			setDureeCollision(0);
		}

		// Faux Faux
		else {
			setDureeCollision(0);
		}
	}

	/**
	 * Modifie la force normale agissant sur le vaisseau
	 *
	 * @param forceNormale La nouvelle force normale agissant sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setForceNormale(Vecteur2D forceNormale) {
		this.forceNormale = forceNormale;
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

	/**
	 * Modifie la position du vaisseau à l'itération précédente
	 *
	 * @param positionPrecedente La nouvelle position du vaisseau à l'itération
	 *                           précédente
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPositionPrecedente(Vecteur2D positionPrecedente) {
		this.positionPrecedente = new Vecteur2D(positionPrecedente);
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
	 * Recalcule l'accélération du vaisseau à l'aide de la nouvelle somme des forces
	 * passée en paramètre Ceci aura pour conséquence de modifier l'accélération
	 *
	 * !!! La méthode provient d'anciens projets (ex-auteur : Caroline Houle) mais a
	 * été implementé et modifier pour notre code !!!
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
	 * Modifie l'objet VaisseauImage qui contient les propriétés de la tuile du
	 * vaisseau
	 *
	 * @param tuileDuVaisseau Le nouvel objet VaisseauImage
	 */
	// Enuel René Valentin Kizozo Izia
	public void setTuile(VaisseauImage tuileDuVaisseau) {
		this.tuile = tuileDuVaisseau;
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
	 * Permet d'afficher quelques caractéristiques du vaisseau : Sa position, sa
	 * vitesse, son accélération, la somme des forces agissant sur lui et sa charge
	 *
	 * !!! La méthode provient d'anciens projets (ex-auteur : Caroline Houle) mais a
	 * été implementé et modifier pour notre code !!!
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
		s += " masse=[ " + String.format("%." + nbDecimales + "f", masse) + "]";
		return (s);
	}
}
