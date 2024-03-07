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
	private double deltaT = 0.05;
	/** Temps total écoulé, simulé (en seconde) **/
	private double tempsTotalEcoule = 0;
	/** Nombre de milliseconde dans une seconde **/
	private double millisecondeParSeconde = 1000;
	/** Temps de la pause du thread d'animation (en milliseconde) **/
	private int tempsDuSleep = 1000;
	/** Booléen permettant de savoir si l'animation est en cours **/
	private boolean enCoursDAnimation = false;
	/** Booléen permettant de savoir s'il y a eu une collision **/
	private boolean collision = false;
	
	/** Indique si c'est la première fois. **/
	private boolean premiereFois = true;

	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre;
	
	Vaisseau vaisseauBleu;
	PlaqueChargee plaqueRouge;
	
	// CONSTRUCTEURS //
	/**
	 * Constructeur de la zone d'animation
	 */
	public ZoneAnimationPhysiqueTest() {

		setBackground(Color.black);
	}

	// SOUS-PROGRAMMES //
	/**
	 * Permet de dessiner des objets sur le composant
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		if (premiereFois) {
			pixelsParMetre = getWidth()/largeurDuComposantEnMetres;
			hauteurDuComposantEnMetres = getHeight()/pixelsParMetre;

			premiereFois = false;
		}//fin condition dans paintComponent
		
		dessinerVaisseau(g2d);
		dessinerPlaque(g2d);
	}
	
	private void dessinerVaisseau(Graphics2D g2d) {
		
		double rayon = 5, chargeA = -3, masseA = 10;
		Vecteur2D posA = new Vecteur2D (150, hauteurDuComposantEnMetres/2);
		Vecteur2D vitA = new Vecteur2D (5, 0);
		Vecteur2D accelA = new Vecteur2D (0, 0);
		
		vaisseauBleu = new Vaisseau(posA, vitA, accelA, rayon, chargeA, masseA);
		vaisseauBleu.setPixelsParMetre(pixelsParMetre);
		vaisseauBleu.dessiner(g2d);
	}
	
	private void dessinerPlaque(Graphics2D g2d) {
		
		double longueur = 50, chargeB = 10000, masseB = 100;
		Vecteur2D posB = new Vecteur2D (largeurDuComposantEnMetres/2, hauteurDuComposantEnMetres/2);
		Vecteur2D normale = new Vecteur2D (-1, 0);
		
		plaqueRouge = new PlaqueChargee(posB, normale, longueur, chargeB, masseB);
		plaqueRouge.setPixelsParMetre(pixelsParMetre);
		plaqueRouge.dessiner(g2d);
	}
	
	public void run() {
		while (enCoursDAnimation) {	
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			//System.out.println("Le vaisseau bleu : " + vaisseauBleu.toString(3));
			calculerUneIterationPhysique(deltaT);
			//testerCollisionsEtAjusterVitesses();
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
		
		Vecteur2D forceElec = MoteurPhysique.calculForceElectriqueGenereeParPlaque(vaisseauBleu, plaqueRouge);
		/*
		 * Éventuellement il faudra initialiser
		 * les forces de frottement (statique et cinétique)
		 * la force gravitationnelle
		 * les forces d'autres plaques 
		 */
		vaisseauBleu.setSommeDesForces(forceElec);
		
		vaisseauBleu.avancerUnPas(deltaT);
		
		System.out.println("Temps total écoulé: "  + String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé!)");
		System.out.println("Le vaisseau bleu : " + vaisseauBleu.toString(3));
		System.out.println("La plaque rouge : " + plaqueRouge.toString(3));
		System.out.println(" ");
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
		//testerCollisionsEtAjusterVitesses();
		repaint();
	}//fin methode prochaineImage
	
	/**
	 * Permet de repositioner les objets de la scène à leur état initial
	 */
	//Enuel René Valentin Kizozo Izia
	public void recommencer() {

		enCoursDAnimation = false;
		tempsTotalEcoule = 0;
				
		//collision=false;
		
		repaint();
	}
	
	/**
	 * Permet de réinitialiser l'application
	 */
	public void reinitialiser() {
		recommencer();
		
	}
}
