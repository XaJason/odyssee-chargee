package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import interactif.PlaqueChargee;
import interactif.Vaisseau;
import niveau.Niveau;
import niveau.Sauvegarder;
import physique.MoteurPhysique;
import physique.Vecteur2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Composant illustrant la simulation :
 * C'est la scène physique où sont représentés le vaisseau et la plaque chargée
 * 
 * @author Enuel René Valentin Kizozo Izia
 */
public class ZoneAnimationPhysiqueTest extends JPanel implements Runnable {

	// PROPRIÉTÉS //
	/** Constante permettant la sérialisation de la classe **/
	private static final long serialVersionUID = -2070816724471388200L;

	/** Largeur du composant d'animation (en mètre) **/
	private double largeurDuComposantEnMetres = 500.0;
	/** Hauteur du composant d'animation (en mètre) **/
	private double hauteurDuComposantEnMetres = 150.0;

	/** Pas de simulation initial (en seconde) **/
	private final double DELTA_T_INITIAL = 0.05;
	/** Pas de simulation (en seconde) **/
	private double deltaT = DELTA_T_INITIAL;
	/** Temps total écoulé, simulé (en seconde) **/
	private double tempsTotalEcoule = 0;
	/** Temps de la pause du thread d'animation (en milliseconde) **/
	private int tempsDuSleep = 50;
	/** Booléen permettant de savoir si l'animation est en cours **/
	private boolean enCoursDAnimation = false;

	/** Booléan indiquant si c'est la première fois. **/
	private boolean premiereFois = true;
	/**
	 * Booléan indiquant si le bouton réinitialiser a été actionné (vrai la première
	 * fois)
	 **/
	private boolean reinitialiser = true;

	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre;

	// Constantes du vaisseau
	/** Rayon initial du vaisseau (en mètre) **/
	private final double RAYON_INITIAL_VAISSEAU = 4;

	/** Charge initiale du vaisseau (en Coulomb) **/
	private final double CHARGE_INITIALE_VAISSEAU = -5;

	/** Masse initiale du vaisseau (en kilogramme) **/
	private final double MASSE_INITIALE_VAISSEAU = 0.020;

	/** Composante en X de la position initiale du vaisseau (en mètre) **/
	private final double POS_INITIALE_VAISSEAU_EN_X = 200;

	/**
	 * Composante en Y de la position initiale du vaisseau (en mètre), définie à
	 * mi-hauteur du composant
	 **/
	private final double POS_INITIALE_VAISSEAU_EN_Y = hauteurDuComposantEnMetres / 2;

	/** Vitesse initiale du vaisseau selon l'axe X (en m/s) **/
	private final double VIT_INITIALE_VAISSEAU_X = 0;

	/** Vitesse initiale du vaisseau selon l'axe Y (en m/s) **/
	private final double VIT_INITIALE_VAISSEAU_Y = 0;

	/**
	 * Accélération initiale du vaisseau (en m/s^2), représentée par un vecteur 2D
	 **/
	private final Vecteur2D ACCEL_INITIALE_VAISSEAU = new Vecteur2D(0, 0);

	// Constantes de la plaque
	/** Longueur initiale de la plaque chargée (en mètre) **/
	private final double LONGUEUR_PLAQUE_INITIALE = 25;

	/** Largeur de la plaque chargée (en mètre) **/
	private final double LARGEUR_PLAQUE_INITIALE = 1;

	/** Charge initiale de la plaque chargée (en Coulomb) **/
	private final double CHARGE_INITIALE_PLAQUE = 20;

	/**
	 * Composante en X de la position initiale de la plaque chargée (en mètre),
	 * définie à mi-largeur du composant
	 **/
	private final double POS_INITIALE_PLAQUE_EN_X = largeurDuComposantEnMetres / 2;

	/**
	 * Composante en Y de la position initiale de la plaque chargée (en mètre),
	 * définie à mi-hauteur du composant
	 **/
	private final double POS_INITIALE_PLAQUE_EN_Y = hauteurDuComposantEnMetres / 2;

	/** Composante X de la normale initiale de la plaque chargée **/
	private final double NORMALE_INITIALE_PLAQUE_COMPOSANTE_X = -1;

	/** Composante Y de la normale initiale de la plaque chargée **/
	private final double NORMALE_INITIALE_PLAQUE_COMPOSANTE_Y = 0;

	// Caractéristiques du vaisseau
	/** Objet représentant le vaisseau bleu **/
	private Vaisseau vaisseauBleu;

	/** Rayon du vaisseau (en mètre) **/
	private double rayonVaisseau = RAYON_INITIAL_VAISSEAU;

	/** Charge du vaisseau (en Coulomb) **/
	private double chargeVaisseau = CHARGE_INITIALE_VAISSEAU;

	/** Masse du vaisseau (en kilogramme) **/
	private double masseVaisseau = MASSE_INITIALE_VAISSEAU;

	/** Composante en X de la position du vaisseau (en mètre) **/
	private double posVaisseauEnX = POS_INITIALE_VAISSEAU_EN_X;

	/** Composante en Y de la position du vaisseau (en mètre) **/
	private double posVaisseauEnY = POS_INITIALE_VAISSEAU_EN_Y;

	/** Vecteur position du vaisseau (en mètre) **/
	private Vecteur2D posVaisseau = new Vecteur2D(POS_INITIALE_VAISSEAU_EN_X, POS_INITIALE_VAISSEAU_EN_Y);

	/** Composante en X de la vitesse du vaisseau (en m/s) **/
	private double vitVaisseauEnX = VIT_INITIALE_VAISSEAU_X;

	/** Composante en Y de la vitesse du vaisseau (en m/s) **/
	private double vitVaisseauEnY = VIT_INITIALE_VAISSEAU_Y;

	/** Vecteur vitesse du vaisseau (en m/s) **/
	private Vecteur2D vitVaisseau = new Vecteur2D(VIT_INITIALE_VAISSEAU_X, VIT_INITIALE_VAISSEAU_Y);

	/** Vecteur accélération du vaisseau (en m/s^2) **/
	private Vecteur2D accelVaisseau = ACCEL_INITIALE_VAISSEAU;

	// Caractéristiques de la plaque
	/** Objet représentant la plaque rouge **/
	private PlaqueChargee plaqueRouge;

	/** Longueur de la plaque chargée (en mètre) **/
	private double longueurPlaque = LONGUEUR_PLAQUE_INITIALE;

	/** Largeur de la plaque chargée (en mètre) **/
	private double largeurPlaque = LARGEUR_PLAQUE_INITIALE;

	/** Charge de la plaque chargée (en Coulomb) **/
	private double chargePlaque = CHARGE_INITIALE_PLAQUE;

	/** Composante en X de la position de la plaque chargée (en mètre) **/
	private double posPlaqueEnX = POS_INITIALE_PLAQUE_EN_X;

	/** Composante en Y de la position de la plaque chargée (en mètre) **/
	private double posPlaqueEnY = POS_INITIALE_PLAQUE_EN_Y;

	/** Vecteur position de la plaque chargée (en mètre) **/
	private Vecteur2D posPlaque = new Vecteur2D(POS_INITIALE_PLAQUE_EN_X, POS_INITIALE_PLAQUE_EN_Y);

	/** Composante X de la normale de la plaque chargée **/
	private double normalePlaqueComposanteX = NORMALE_INITIALE_PLAQUE_COMPOSANTE_X;

	/** Composante Y de la normale de la plaque chargée **/
	private double normalePlaqueComposanteY = NORMALE_INITIALE_PLAQUE_COMPOSANTE_Y;

	/** Vecteur normal de la plaque chargée **/
	private Vecteur2D normalePlaque = new Vecteur2D(NORMALE_INITIALE_PLAQUE_COMPOSANTE_X,
			NORMALE_INITIALE_PLAQUE_COMPOSANTE_Y);

	// CONSTRUCTEUR //
	/**
	 * Constructeur de la zone d'animation
	 */
	// Enuel René Valentin Kizozo Izia
	public ZoneAnimationPhysiqueTest() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double xEnMetre = e.getX()/pixelsParMetre;
				double yEnMetre = e.getY()/pixelsParMetre;
				if (plaqueRouge.contient(xEnMetre, yEnMetre)) {
					System.out.println("Dans la plaqueeee");
				}
			}
		});
		setBackground(Color.black);

		vaisseauBleu = new Vaisseau(posVaisseau, vitVaisseau, accelVaisseau, rayonVaisseau, chargeVaisseau,
				masseVaisseau);
		plaqueRouge = new PlaqueChargee(posPlaque, normalePlaque, longueurPlaque, largeurPlaque, chargePlaque);

	}

	// SOUS-PROGRAMMES //
	/**
	 * Permet de dessiner des objets sur le composant
	 * 
	 * @param g Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Transformations affines pour que l'origine soit en bas à droite
		g2d.translate(0, getHeight());
		g2d.scale(1, -1);

		if (premiereFois) {
			pixelsParMetre = getWidth() / largeurDuComposantEnMetres;
			hauteurDuComposantEnMetres = getHeight() / pixelsParMetre;

			if (reinitialiser) {
				posVaisseauEnY = hauteurDuComposantEnMetres / 2;
				posPlaqueEnX = largeurDuComposantEnMetres / 2;
				posPlaqueEnY = hauteurDuComposantEnMetres / 2;
				reinitialiser = false;
			}

			vaisseauBleu.setPosition(new Vecteur2D(posVaisseauEnX, posVaisseauEnY));
			plaqueRouge.setPosition(new Vecteur2D(posPlaqueEnX, posPlaqueEnY));
			plaqueRouge.miseAJourExtremiteA();
			plaqueRouge.miseAJourExtremiteB();

			premiereFois = false;
		} // fin condition dans paintComponent

		g2d.scale(pixelsParMetre, pixelsParMetre);
		dessinerVaisseau(g2d);
		dessinerPlaque(g2d);
	}

	/**
	 * Permet de dessiner le vaisseau
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerVaisseau(Graphics2D g2d) {
		vaisseauBleu.setPixelsParMetre(pixelsParMetre);
		vaisseauBleu.dessiner(g2d);
	}

	/**
	 * Permet de dessiner la ou les plaques chargées
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerPlaque(Graphics2D g2d) {
		plaqueRouge.setPixelsParMetre(pixelsParMetre);
		plaqueRouge.dessiner(g2d);
	}
	
	/**
	 * Permet d'effectuer l'animation
	 */
	// Enuel René Valentin Kizozo Izia
	public void run() {
		while (enCoursDAnimation) {
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			calculerUneIterationPhysique(deltaT);
			testerCollisionsEtAjusterVitesses();
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // fin while
		System.out.println("Le thread est mort...!");
	}

	/**
	 * Calcul de la nouvelle position du vaisseau
	 * 
	 * @param deltaT Le pas de simulation
	 */
	// Enuel René Valentin Kizozo Izia
	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		System.out.println("Temps total écoulé: " + String.format("%.3f", tempsTotalEcoule) + "sec (en temps simulé!)");

		Vecteur2D forceElec = MoteurPhysique.calculForceElectriqueGenereeParPlaque(vaisseauBleu, plaqueRouge);
		Vecteur2D forceGrav = MoteurPhysique.calculForceGravEnY(vaisseauBleu.getMasse());
		/*
		 * Éventuellement il faudra initialiser
		 * les forces de frottement (statique et cinétique)
		 * les forces d'autres plaques
		 */

		vaisseauBleu.setSommeDesForces(forceElec.additionne(forceGrav));

		vaisseauBleu.avancerUnPas(deltaT);

		// System.out.println("Temps total écoulé: " +
		// String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé!)");
		System.out.println("Le vaisseau bleu : " + vaisseauBleu.toString(3));
		System.out.println("La plaque rouge : " + plaqueRouge.toString(3));
		System.out.println(" ");
	}

	/**
	 * Teste si des objets de la scene sont en collision.
	 * Si oui : on calcule les rebonds et on en déduit les nouvelles vitesses.
	 * Lors du prochain pas, ces nouvelles vitesses entrainent les objets dans de
	 * nouvelles directions.
	 */
	// Enuel René Valentin Kizozo Izia
	private void testerCollisionsEtAjusterVitesses() {
		/*
		 * Éventuellement, faire une boucle pour vérifier toutes les instances (murs,
		 * plaques, sol) avec lesquelles le vaisseau
		 * pourrait entrer en collision
		 * Peut-être passer à travers une liste contenant tous les objets de la scène?
		 */
		vaisseauBleu.gererCollisionAvecPlaque(plaqueRouge);
		vaisseauBleu.gererCollisionAvecBordures(largeurDuComposantEnMetres, hauteurDuComposantEnMetres);
	}

	/**
	 * Démarre le thread s'il n'est pas deja demarré
	 */
	// Enuel René Valentin Kizozo Izia
	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}

	/**
	 * Cause la fin du thread
	 */
	// Enuel René Valentin Kizozo Izia
	public void arreter() {
		enCoursDAnimation = false;
	}// fin methode

	/**
	 * Permet d'avancer d'une image
	 */
	// Enuel René Valentin Kizozo Izia
	public void prochaineImage() {
		enCoursDAnimation = false;
		System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
		calculerUneIterationPhysique(deltaT);
		// repaint();
		testerCollisionsEtAjusterVitesses();
		repaint();
	}// fin methode prochaineImage

	/**
	 * Permet de repositioner les objets de la scène à leur état initial
	 */
	// Enuel René Valentin Kizozo Izia
	public void recommencer() {
		vaisseauBleu.setRayon(rayonVaisseau);
		vaisseauBleu.setCharge(chargeVaisseau);
		vaisseauBleu.setMasse(masseVaisseau);
		vaisseauBleu.setPosition(new Vecteur2D(posVaisseauEnX, posVaisseauEnY));
		vaisseauBleu.setVitesse(new Vecteur2D(vitVaisseauEnX, vitVaisseauEnY));
		vaisseauBleu.setAccel(ACCEL_INITIALE_VAISSEAU);
		vaisseauBleu.setSommeDesForces(new Vecteur2D(0, 0));

		plaqueRouge.setLongueur(longueurPlaque);
		plaqueRouge.setCharge(chargePlaque);
		plaqueRouge.setPosition(new Vecteur2D(posPlaqueEnX, posPlaqueEnY));
		plaqueRouge.miseAJourExtremiteA();
		plaqueRouge.miseAJourExtremiteB();
		// À gérer plus tard si l'utilisateur fait n'importe nawak
		try {
			plaqueRouge.setNormale(new Vecteur2D(normalePlaqueComposanteX, normalePlaqueComposanteY).normalise());
		} catch (Exception e) {
			e.printStackTrace();
		}
		enCoursDAnimation = false;
		premiereFois = true;
		tempsTotalEcoule = 0;

		repaint();
	}

	/**
	 * Permet de réinitialiser l'application
	 */
	// Enuel René Valentin Kizozo Izia
	public void reinitialiser() {
		vaisseauBleu.setRayon(RAYON_INITIAL_VAISSEAU);
		vaisseauBleu.setCharge(CHARGE_INITIALE_VAISSEAU);
		vaisseauBleu.setMasse(MASSE_INITIALE_VAISSEAU);
		vaisseauBleu.setPosition(new Vecteur2D(POS_INITIALE_VAISSEAU_EN_X, POS_INITIALE_VAISSEAU_EN_Y));
		vaisseauBleu.setVitesse(new Vecteur2D(VIT_INITIALE_VAISSEAU_X, VIT_INITIALE_VAISSEAU_Y));
		vaisseauBleu.setAccel(ACCEL_INITIALE_VAISSEAU);
		vaisseauBleu.setSommeDesForces(new Vecteur2D(0, 0));

		plaqueRouge.setLongueur(LONGUEUR_PLAQUE_INITIALE);
		plaqueRouge.setCharge(CHARGE_INITIALE_PLAQUE);
		plaqueRouge.setPosition(new Vecteur2D(POS_INITIALE_PLAQUE_EN_X, POS_INITIALE_PLAQUE_EN_Y));
		plaqueRouge.miseAJourExtremiteA();
		plaqueRouge.miseAJourExtremiteB();
		plaqueRouge
				.setNormale(new Vecteur2D(NORMALE_INITIALE_PLAQUE_COMPOSANTE_X, NORMALE_INITIALE_PLAQUE_COMPOSANTE_Y));

		enCoursDAnimation = false;
		premiereFois = true;
		reinitialiser = true;
		tempsTotalEcoule = 0;

		repaint();
	}

	// GETTERS ET SETTERS //
	/**
	 * Retourne le rayon du vaisseau
	 * 
	 * @return Le rayon du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getRayonVaisseau() {
		return rayonVaisseau;
	}

	/**
	 * Modifie le rayon du vaisseau
	 * 
	 * @param rayonVaisseau Le rayon du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setRayonVaisseau(double rayonVaisseau) {
		this.rayonVaisseau = rayonVaisseau;
		vaisseauBleu.setRayon(rayonVaisseau);
		repaint();
	}

	/**
	 * Retourne la charge du vaisseau
	 * 
	 * @return La charge du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargeVaisseau() {
		return chargeVaisseau;
	}

	/**
	 * Modifie la charge du vaisseau
	 * 
	 * @param chargeVaisseau La charge du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setChargeVaisseau(double chargeVaisseau) {
		this.chargeVaisseau = chargeVaisseau;
		vaisseauBleu.setCharge(chargeVaisseau);
		repaint();
	}

	/**
	 * Retourne la masse du vaisseau
	 * 
	 * @return La masse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getMasseVaisseau() {
		return masseVaisseau;
	}

	/**
	 * Modifie la masse du vaisseau
	 * 
	 * @param masseVaisseau La masse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setMasseVaisseau(double masseVaisseau) {
		this.masseVaisseau = masseVaisseau;
		vaisseauBleu.setMasse(masseVaisseau);
		repaint();
	}

	/**
	 * Retourne la composante en X de la position du vaisseau
	 * 
	 * @return La composante en X de la position du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosVaisseauEnX() {
		return posVaisseauEnX;
	}

	/**
	 * Modifie la composante en X de la position du vaisseau
	 * 
	 * @param posVaisseauEnX La composante en X de la position du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPosVaisseauEnX(double posVaisseauEnX) {
		this.posVaisseauEnX = posVaisseauEnX;
		if (!enCoursDAnimation) {
			vaisseauBleu.setPosition(new Vecteur2D(posVaisseauEnX, vaisseauBleu.getPosition().getY()));
			repaint();
		}
	}

	/**
	 * Retourne la composante en Y de la position du vaisseau
	 * 
	 * @return La composante en Y de la position du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosVaisseauEnY() {
		return posVaisseauEnY;
	}

	/**
	 * Modifie la composante en Y de la position du vaisseau
	 * 
	 * @param posVaisseauEnY La composante en Y de la position du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPosVaisseauEnY(double posVaisseauEnY) {
		this.posVaisseauEnY = posVaisseauEnY;
		if (!enCoursDAnimation) {
			vaisseauBleu.setPosition(new Vecteur2D(vaisseauBleu.getPosition().getX(), posVaisseauEnY));
			repaint();
		}
	}

	/**
	 * Retourne la composante en X de la vitesse du vaisseau
	 * 
	 * @return La composante en X de la vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getVitVaisseauEnX() {
		return vitVaisseauEnX;
	}

	/**
	 * Modifie la composante en X de la vitesse du vaisseau
	 * 
	 * @param vitVaisseauEnX La composante en X de la vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setVitVaisseauEnX(double vitVaisseauEnX) {
		this.vitVaisseauEnX = vitVaisseauEnX;
		if (!enCoursDAnimation) {
			vaisseauBleu.setVitesse(new Vecteur2D(vitVaisseauEnX, vaisseauBleu.getVitesse().getY()));
			repaint();
		}
	}

	/**
	 * Retourne la composante en Y de la vitesse du vaisseau
	 * 
	 * @return La composante en Y de la vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getVitVaisseauEnY() {
		return vitVaisseauEnY;
	}

	/**
	 * Modifie la composante en Y de la vitesse du vaisseau
	 * 
	 * @param vitVaisseauEnY La composante en Y de la vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setVitVaisseauEnY(double vitVaisseauEnY) {
		this.vitVaisseauEnY = vitVaisseauEnY;
		if (!enCoursDAnimation) {
			vaisseauBleu.setVitesse(new Vecteur2D(vaisseauBleu.getVitesse().getX(), vitVaisseauEnY));
			repaint();
		}
	}

	/**
	 * Retourne la longueur de la plaque
	 * 
	 * @return La longueur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLongueurPlaque() {
		return longueurPlaque;
	}

	/**
	 * Modifie la longueur de la plaque
	 * 
	 * @param longueurPlaque La longueur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setLongueurPlaque(double longueurPlaque) {
		this.longueurPlaque = longueurPlaque;
		plaqueRouge.setLongueur(longueurPlaque);
		repaint();
	}

	/**
	 * Retourne la charge de la plaque
	 * 
	 * @return La charge de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargePlaque() {
		return chargePlaque;
	}

	/**
	 * Modifie la charge de la plaque
	 * 
	 * @param chargePlaque La charge de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setChargePlaque(double chargePlaque) {
		this.chargePlaque = chargePlaque;
		plaqueRouge.setCharge(chargePlaque);
		repaint();
	}

	/**
	 * Retourne la composante en X de la position de la plaque
	 * 
	 * @return La composante en X de la position de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosPlaqueEnX() {
		return posPlaqueEnX;
	}

	/**
	 * Modifie la composante en X de la position de la plaque
	 * 
	 * @param posPlaqueEnX La composante en X de la position de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPosPlaqueEnX(double posPlaqueEnX) {
		this.posPlaqueEnX = posPlaqueEnX;
		if (!enCoursDAnimation) {
			plaqueRouge.setPosition(new Vecteur2D(posPlaqueEnX, plaqueRouge.getPosition().getY()));
			plaqueRouge.miseAJourExtremiteA();
			plaqueRouge.miseAJourExtremiteB();
			repaint();
		}
	}

	/**
	 * Retourne la composante en Y de la position de la plaque
	 * 
	 * @return La composante en Y de la position de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosPlaqueEnY() {
		return posPlaqueEnY;
	}

	/**
	 * Modifie la composante en Y de la position de la plaque
	 * 
	 * @param posPlaqueEnY La composante en Y de la position de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPosPlaqueEnY(double posPlaqueEnY) {
		this.posPlaqueEnY = posPlaqueEnY;
		if (!enCoursDAnimation) {
			plaqueRouge.setPosition(new Vecteur2D(plaqueRouge.getPosition().getX(), posPlaqueEnY));
			plaqueRouge.miseAJourExtremiteA();
			plaqueRouge.miseAJourExtremiteB();
			repaint();
		}
	}

	/**
	 * Retourne la composante en X de la normale de la plaque
	 * 
	 * @return La composante en X de la normale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getNormalePlaqueComposanteX() {
		return normalePlaqueComposanteX;
	}

	/**
	 * Modifie la composante en X de la normale de la plaque
	 * 
	 * @param normalePlaqueComposanteX La composante en X de la normale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNormalePlaqueComposanteX(double normalePlaqueComposanteX) {
		// if (!enCoursDAnimation) {
		this.normalePlaqueComposanteX = normalePlaqueComposanteX;
		// À gérer plus tard si l'utilisateur fait n'importe nawak
		try {
			plaqueRouge.setNormale(new Vecteur2D(normalePlaqueComposanteX, normalePlaqueComposanteY).normalise());
		} catch (Exception e) {
			e.printStackTrace();
		}
		repaint();
		// }
	}

	/**
	 * Retourne la composante en Y de la normale de la plaque
	 * 
	 * @return La composante en Y de la normale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getNormalePlaqueComposanteY() {
		return normalePlaqueComposanteY;
	}

	/**
	 * Modifie la composante en Y de la normale de la plaque
	 * 
	 * @param normalePlaqueComposanteY La composante en Y de la normale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNormalePlaqueComposanteY(double normalePlaqueComposanteY) {
		// if (!enCoursDAnimation) {
		this.normalePlaqueComposanteY = normalePlaqueComposanteY;
		// À gérer plus tard si l'utilisateur fait n'importe nawak
		try {
			plaqueRouge.setNormale(new Vecteur2D(normalePlaqueComposanteX, normalePlaqueComposanteY).normalise());
		} catch (Exception e) {
			e.printStackTrace();
		}
		repaint();
		// }
	}

	/**
	 * Retourne la valeur du pas de simulation (deltaT)
	 * 
	 * @return La pas de simulation (deltaT)
	 */
	// Enuel René Valentin Kizozo Izia
	public double getDeltaT() {
		return deltaT;
	}

	/**
	 * Modifie la valeur du pas de simulation (deltaT)
	 * 
	 * @param deltaT Le nouveau pas de simulation
	 */
	// Enuel René Valentin Kizozo Izia
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}

	/**
	 * Retourne le rayon initiale du vaisseau
	 * 
	 * @return Le rayon initiale du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getRayonInitialVaisseau() {
		return RAYON_INITIAL_VAISSEAU;
	}

	/**
	 * Retourne la charge initiale du vaisseau
	 * 
	 * @return La charge initiale du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargeInitialeVaisseau() {
		return CHARGE_INITIALE_VAISSEAU;
	}

	/**
	 * Retourne la masse initiale du vaisseau
	 * 
	 * @return La masse initiale du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getMasseInitialeVaisseau() {
		return MASSE_INITIALE_VAISSEAU;
	}

	/**
	 * Retourne la position initiale en X du vaisseau
	 * 
	 * @return La position initiale en X du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosInitialeVaisseauEnX() {
		return POS_INITIALE_VAISSEAU_EN_X;
	}

	/**
	 * Retourne la position initiale en Y du vaisseau
	 * 
	 * @return La position initiale en Y du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosinitialeVaisseauEnY() {
		return POS_INITIALE_VAISSEAU_EN_Y;
	}

	/**
	 * Retourne la vitesse initiale en X du vaisseau
	 * 
	 * @return La vitesse initiale en X du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getVitInitialeVaisseauX() {
		return VIT_INITIALE_VAISSEAU_X;
	}

	/**
	 * Retourne la vitesse initiale en Y du vaisseau
	 * 
	 * @return La vitesse initiale en Y du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getVitInitialeVaisseauY() {
		return VIT_INITIALE_VAISSEAU_Y;
	}

	/**
	 * Retourne la longueur de la plaque
	 * 
	 * @return La longueur de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLongueurPlaqueInitiale() {
		return LONGUEUR_PLAQUE_INITIALE;
	}

	/**
	 * Retourne la charge initiale de la plaque
	 * 
	 * @return La charge initiale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargeInitialePlaque() {
		return CHARGE_INITIALE_PLAQUE;
	}

	/**
	 * Retourne la position initiale en X de la plaque
	 * 
	 * @return La position initiale en X de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosInitialePlaqueEnX() {
		return POS_INITIALE_PLAQUE_EN_X;
	}

	/**
	 * Retourne la position initiale en Y de la plaque
	 * 
	 * @return La position initiale en Y de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getPosInitialePlaqueEnY() {
		return POS_INITIALE_PLAQUE_EN_Y;
	}

	/**
	 * Retourne la composante en X de la normale initiale de la plaque
	 * 
	 * @return La composante en X de la normale initiale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getNormaleInitialePlaqueComposanteX() {
		return NORMALE_INITIALE_PLAQUE_COMPOSANTE_X;
	}

	/**
	 * Retourne la composante en Y de la normale initiale de la plaque
	 * 
	 * @return La composante en Y de la normale initiale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getNormaleInitialePlaqueComposanteY() {
		return NORMALE_INITIALE_PLAQUE_COMPOSANTE_Y;
	}

	/**
	 * Retourne le pas de simulation (deltaT) initial
	 * 
	 * @return Le pas de simulation (deltaT) initial
	 */
	// Enuel René Valentin Kizozo Izia
	public double getDeltaTInitial() {
		return DELTA_T_INITIAL;
	}

}
