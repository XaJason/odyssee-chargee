package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import interactif.PlaqueChargee;
import interactif.Vaisseau;
import physique.MoteurPhysique;
import physique.Vecteur2D;

public class ZoneAnimationPhysiqueTest extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2070816724471388200L;
	
	// PROPRIÉTÉS //
	/** Largeur du composant d'animation (en mètre) **/
	private double largeurDuComposantEnMetres = 500;
	/** Hauteur du composant d'animation (en mètre) **/
	private double hauteurDuComposantEnMetres = 100;
	
	/** Pas de simulation (en seconde) **/
	private double deltaT = 0.1;
	/** Temps total écoulé, simulé (en seconde) **/
	private double tempsTotalEcoule = 0;
	/** Nombre de milliseconde dans une seconde **/
	private double millisecondeParSeconde = 1000;
	/** Temps de la pause du thread d'animation (en milliseconde) **/
	private int tempsDuSleep = 50;
	/** Booléen permettant de savoir si l'animation est en cours **/
	private boolean enCoursDAnimation = false;
	/** Booléen permettant de savoir s'il y a eu une collision **/
	private boolean collision = false;
	
	/** Indique si c'est la première fois. **/
	private boolean premiereFois = true;

	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre;
	
	Vaisseau vaisseauBleu;


	double rayonInitial = 5, chargeInitialeA = -3, masseInitialeA = 0.035;
	double posInitialeVaisseauEnX = 200;
	Vecteur2D posInitialeA = new Vecteur2D (posInitialeVaisseauEnX, hauteurDuComposantEnMetres/2);
	Vecteur2D vitInitialeA = new Vecteur2D (0, 0); //Vitesse nulle
	Vecteur2D accelInitialeA = new Vecteur2D (); //Accélération nulle
	
	PlaqueChargee plaqueRouge;
	double longueurInitiale = 25, chargeInitialeB = 20, masseInitialeB = 100;
	Vecteur2D posB = new Vecteur2D (largeurDuComposantEnMetres/2, hauteurDuComposantEnMetres/2);
	Vecteur2D normale = new Vecteur2D (-1, 0);
	
	
	// CONSTRUCTEURS //
	/**
	 * Constructeur de la zone d'animation
	 */
	public ZoneAnimationPhysiqueTest() {
		setBackground(Color.black);
		vaisseauBleu = new Vaisseau(posInitialeA, vitInitialeA, accelInitialeA, rayonInitial, chargeInitialeA, masseInitialeA);
		plaqueRouge = new PlaqueChargee(posB, normale, longueurInitiale, chargeInitialeB, masseInitialeB);
		
	}

	// SOUS-PROGRAMMES //
	/**
	 * Permet de dessiner des objets sur le composant
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		// Transformations affines pour que l'origine soit en bas à droite
		g2d.translate(0, getHeight());
		g2d.scale(1, -1);
		
		if (premiereFois) {
			pixelsParMetre = getWidth()/largeurDuComposantEnMetres;
			hauteurDuComposantEnMetres = getHeight()/pixelsParMetre;
			
			vaisseauBleu.setPosition( new Vecteur2D (posInitialeVaisseauEnX, hauteurDuComposantEnMetres*2/4) );
			plaqueRouge.setPosition( new Vecteur2D (largeurDuComposantEnMetres/2, hauteurDuComposantEnMetres/2) );

			premiereFois = false;
		}//fin condition dans paintComponent
		
		dessinerVaisseau(g2d);
		dessinerPlaque(g2d);
	}
	
	/**
	 * Permet de dessiner le vaisseau
	 * @param g2d Le contexte graphique
	 */
	private void dessinerVaisseau(Graphics2D g2d) {
		vaisseauBleu.setPixelsParMetre(pixelsParMetre);
		vaisseauBleu.dessiner(g2d);
	}
	
	/**
	 * Permet de dessiner la ou les plaques chargées
	 * @param g2d Le contexte graphique
	 */
	private void dessinerPlaque(Graphics2D g2d) {
		plaqueRouge.setPixelsParMetre(pixelsParMetre);
		plaqueRouge.dessiner(g2d);
	}
	
	/**
	 * Permet d'effectuer l'animation
	 */
	public void run() {
		while (enCoursDAnimation) {	
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			//System.out.println("Le vaisseau bleu : " + vaisseauBleu.toString(3));
			calculerUneIterationPhysique(deltaT);
			testerCollisionsEtAjusterVitesses();
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//fin while
		System.out.println("Le thread est mort...!");
	}
	
	/**
	 * Calcul de la nouvelle position du vaisseau
	 * @param deltaT Le pas de simulation
	 */
	//Enuel René Valentin Kizozo Izia
	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		System.out.println("Temps total écoulé: "  + String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé!)");
		
		
		Vecteur2D forceElec = MoteurPhysique.calculForceElectriqueGenereeParPlaque(vaisseauBleu, plaqueRouge);
		Vecteur2D forceGrav = MoteurPhysique.calculForceGrav(vaisseauBleu.getMasse());
		/*
		 * Éventuellement il faudra initialiser
		 * les forces de frottement (statique et cinétique)
		 * les forces d'autres plaques 
		 */
		
		vaisseauBleu.setSommeDesForces(forceElec.additionne(forceGrav));
		
		vaisseauBleu.avancerUnPas(deltaT);
		
		//System.out.println("Temps total écoulé: "  + String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé!)");
		System.out.println("Le vaisseau bleu : " + vaisseauBleu.toString(3));
		System.out.println("La plaque rouge : " + plaqueRouge.toString(3));
		System.out.println(" ");
	}
	
	/**
	 * Teste si des objets de la scene sont en collision.
	 * Si oui : on calcule les rebonds et on en déduit les nouvelles vitesses.
	 * Lors du prochain pas, ces nouvelles vitesses entrainent les objets dans de nouvelles directions.
	 */
	private void testerCollisionsEtAjusterVitesses() {
		/* Faire une boucle pour vérifier toutes les instances (murs, plaques, sol) avec lesquelles le vaisseau
		 * pourrait entrer en collision
		 * Peut être passer à travers une liste contenant tous les objets de la scène?
		 */
		vaisseauBleu.gererCollision(plaqueRouge);
	}
	
	/**
	 * Démarre le thread s'il n'est pas deja demarré
	 */
	//Enuel René Valentin Kizozo Izia
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
	//Enuel René Valentin Kizozo Izia
	public void arreter() {
		enCoursDAnimation = false;
	}//fin methode
	
	/**
	 * Permet d'avancer d'une image
	 */
	//Enuel René Valentin Kizozo Izia
	public void prochaineImage() {
		enCoursDAnimation = false;
		System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
		calculerUneIterationPhysique(deltaT);
		testerCollisionsEtAjusterVitesses();
		repaint();
	}//fin methode prochaineImage
	
	/**
	 * Permet de repositioner les objets de la scène à leur état initial
	 */
	//Enuel René Valentin Kizozo Izia
	public void recommencer() {
		vaisseauBleu.setPosition(posInitialeA);
		vaisseauBleu.setVitesse(vitInitialeA);
		vaisseauBleu.setAccel(accelInitialeA);
		vaisseauBleu.setSommeDesForces(new Vecteur2D(0, 0));
		
		enCoursDAnimation = false;
		premiereFois = true;
		tempsTotalEcoule = 0;
		
		repaint();
	}
	
	/**
	 * Permet de réinitialiser l'application
	 */
	public void reinitialiser() {
		vaisseauBleu.setRayon(rayonInitial);
		vaisseauBleu.setCharge(chargeInitialeA);
		vaisseauBleu.setMasse(masseInitialeA);
		
		recommencer();
	}
}
