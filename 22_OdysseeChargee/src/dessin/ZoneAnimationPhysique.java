package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import interactif.PlaqueChargee;
import interactif.Vaisseau;
import niveau.Niveau;
import niveau.Sauvegarder;
import physique.MoteurPhysique;
import physique.Vecteur2D;
import tuile.Portail;
import tuile.Tuile;
import tuile.VaisseauImage;
import utilitaires.Aire;
import utilitaires.OutilsImage;

/**
 * Composant illustrant la simulation :
 * La scène physique où sont représentés les objets intéractifs physique ainsi
 * que le niveau et ses tuiles
 *
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 * 
 */
public class ZoneAnimationPhysique extends JPanel implements Runnable {

	// PROPRIÉTÉS //
	/** Numéro d'identification pour la sérialisation **/
	private static final long serialVersionUID = -8878846015876118047L;
	/** Largeur du niveau (en mètre) **/
	private double largeurDuComposantEnMetres = 900.0;
	/** Hauteur du niveau (en mètre) **/
	private double hauteurDuComposantEnMetres = 200.0;

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
	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre;
	/** Vecteur nul **/
	private final Vecteur2D VEC_ZERO = new Vecteur2D();

	/** Coordonnée en X du curseur de la souris sur le composant (en mètre) **/
	private double sourisEnMetreX = -30; // Initialement à l'extérieur du composant
	/** Coordonnée en Y du curseur de la souris sur le composant (en metre) **/
	private double sourisEnMetreY = -30; // Initialement à l'extérieur du composant
	/** Indique que le curseur de la souris est à l'intérieur du composant **/
	private boolean sourisDansComposant = false;
	/** Boolean qui indique si le bouton de la plaque est actionnée **/
	private boolean placementPlaque = false;
	/** Booléan qui indique si l'on souhaite fixer une plaque sur tuile (après avoir cliqué dessus) **/
	private boolean fixerPlaqueSurTuile = false;
	
	
	// Caractéristiques du niveau
	/** Objet représentant la grille ainsi que toutes ses tuiles **/
	private Niveau niveau;
	/** Charge initiale des plaques du niveau (en Coulomb) **/
	private final double CHARGE_INITIALE_DES_PLAQUES = 20;
	/** Charge des plaques du niveau (en Coulomb) **/
	private double chargeDesPlaques = CHARGE_INITIALE_DES_PLAQUES;
	/** Liste des plaques chargées **/
	private ArrayList<PlaqueChargee> listePlaquesChargees = new ArrayList<PlaqueChargee>();
	/** Plaque chargée **/
	private PlaqueChargee plaque = new PlaqueChargee(chargeDesPlaques); // Placée par défaut à l'extérieur du composant
																		// pour ne pas la voir
	/** Determine si la plaque est positive ou non**/
	private boolean plaquePositive = true;
	/** Position en x(en mètres) de la plaque fantôme**/
	private int posPlaqueX;
	/** Position en y(en mètres) de la plaque fantôme **/
	private int posPlaqueY;
	/** L'image de la plaque, par défaut plaque positive **/
	Image imagePlaque = OutilsImage.lireImage("PlaqueChargePositive.png");

	// Caractéristiques du vaisseau (Constantes)
	/** Charge initiale du vaisseau (en Coulomb) **/
	private final double CHARGE_INITIALE_VAISSEAU = -5;
	/** Masse initiale du vaisseau (en kilogramme) **/
	private final double MASSE_INITIALE_VAISSEAU = 0.020;
	/** Composante en X de la position initiale du vaisseau (en mètre) **/
	private final double POS_INITIALE_VAISSEAU_EN_X = 90; // Emplacement en x de la tuile du vaisseau image
	/** Composante en Y de la position initiale du vaisseau (en mètre) **/
	private final double POS_INITIALE_VAISSEAU_EN_Y = 165; // Emplacement en y de la tuile du vaisseau image

	// Caractéristiques du vaisseau
	/** Objet représentant le vaisseau **/
	private Vaisseau vaisseau;
	/** Charge du vaisseau (en Coulomb) **/
	private double chargeVaisseau = CHARGE_INITIALE_VAISSEAU;
	/** Masse du vaisseau (en kilogramme) **/
	private double masseVaisseau = MASSE_INITIALE_VAISSEAU;
	/** Composante en X de la position du vaisseau (en mètre) **/
	private double posDeSauvegardeX = POS_INITIALE_VAISSEAU_EN_X;
	/** Composante en Y de la position du vaisseau (en mètre) **/
	private double posDeSauvegardeY = POS_INITIALE_VAISSEAU_EN_Y;
	/** Vecteur position du vaisseau (en mètre) **/
	private Vecteur2D posVaisseau = new Vecteur2D(posDeSauvegardeX, posDeSauvegardeY);
	/** Force gravitationnelle agissant sur le vaisseau **/
	private Vecteur2D forceGrav = MoteurPhysique.calculForceGravEnY(masseVaisseau);
	/** Sommes des forces agissant sur le vaisseau **/
	private Vecteur2D sommeForcesSurVaisseau = new Vecteur2D(forceGrav);

	// /** Vecteur vitesse du vaisseau (en m/s) **/
	// private Vecteur2D vitVaisseau = new Vecteur2D(VEC_ZERO);
	// /** Vecteur accélération du vaisseau (en m/s^2) **/
	// private Vecteur2D accelVaisseau = new Vecteur2D(VEC_ZERO);
	// private VaisseauImage tuileDuVaisseau = null;
	/**
	 * Constructeur de la zone d'animation physique
	 */
	// Enuel René Valentin Kizozo Izia
	public ZoneAnimationPhysique() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (placementPlaque) {
					sourisEnMetreX = e.getX() / pixelsParMetre;
					sourisEnMetreY = e.getY() / pixelsParMetre;
					posPlaqueX = (int) (e.getX() / pixelsParMetre);
					posPlaqueY = (int) (e.getY() / pixelsParMetre);
					survolerToutesLesTuilesPourTrouverCurseur();
					repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDansComposant = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sourisDansComposant = false;
				repaint();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (placementPlaque) {
					fixerPlaqueSurTuile = true;
					survolerToutesLesTuilesPourTrouverCurseur();
					fixerPlaqueSurTuile = false;
					repaint();
				}
			}
		});
		setBackground(Color.lightGray);
		setBounds(29, 31, 1232, 617);

		niveau = Sauvegarder.chargerNiveau("Niveau 1");
		placerVaisseauPourDebutAnimation(niveau);
		niveau.getGrille().setDansModeJeu(true);
	}// fin constructeur

	// SOUS-PROGRAMMES //
	/**
	 * Place le vaisseau au bon endroit dans la grille pour le début de l'animation,
	 * en fonction de la position de la tuile du vaisseau dans la grille du niveau
	 * 
	 * @param niveau Le niveau dans lequel se trouve le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	private void placerVaisseauPourDebutAnimation(Niveau niveau) {
		Tuile[][] tabTuiles = niveau.getGrille().getTableau();

		for (int i = 0; i < tabTuiles.length; i++) {
			for (int j = 0; j < tabTuiles.length; j++) {
				Tuile tuile = tabTuiles[i][j];

				if (tuile != null && tuile.getType().equals("Vaisseau")) {
					// VaisseauImage tuileDuVaisseau = (VaisseauImage) tuile;
					vaisseau = new Vaisseau(posVaisseau, chargeVaisseau, masseVaisseau, (VaisseauImage) tuile);

					// Définir la position initial du vaisseau à l'aide de son emplacement dans le
					// niveau (sa tuile)
					posDeSauvegardeX = tuile.getPointZero().getX() + vaisseau.getRayon();
					posDeSauvegardeY = tuile.getPointZero().getY() + vaisseau.getRayon();
					posVaisseau = new Vecteur2D(posDeSauvegardeX, posDeSauvegardeY);
					vaisseau.setPosition(posVaisseau);
				} // fin if
			} // fin 2e boucle for
		} // fin 1re boucle for
	}

	/**
	 * Permet de survoler toutes les tuiles du niveau pour trouver l du curseur de
	 * la souris.
	 * Ainsi, on peut trouver où placer la plaque, à l'aide de la position de
	 * l'objet Aire sur lequel le curseur se trouve
	 */
	// Enuel René Valentin Kizozo Izia
	private void survolerToutesLesTuilesPourTrouverCurseur() {
		Tuile[][] tabTuiles = niveau.getGrille().getTableau();

		for (int i = 0; i < tabTuiles.length; i++) {
			for (int j = 0; j < tabTuiles.length; j++) {
				Tuile tuile = tabTuiles[i][j];

				if (tuile != null && (tuile.getType().equals("Carré") | tuile.getType().equals("Triangle rectangle")
						| tuile.getType().equals("Triangle équilatéral"))) {
					Point2D.Double curseurSouris = new Point2D.Double(sourisEnMetreX, sourisEnMetreY);

					if (tuile.contient(curseurSouris)) {
						Aire aireOuEstCurseur = tuile.survolerAiresDeTuile(curseurSouris);
						if (aireOuEstCurseur != null) {
							plaque.setPosition(new Vecteur2D(aireOuEstCurseur.getPointMilieuDeTuile().getX(),
									aireOuEstCurseur.getPointMilieuDeTuile().getY()));
							plaque.miseAJourExtremiteA();
							plaque.miseAJourExtremiteB();
							
							if (fixerPlaqueSurTuile) {
								Vecteur2D positionNouvellePlaque = new Vecteur2D(plaque.getPosition().getX(), plaque.getPosition().getY() );
								listePlaquesChargees.add( new PlaqueChargee(positionNouvellePlaque, plaque.getCharge()) );
							}
						}
					} // fin 2e if
				} // fin if
			} // fin 2e boucle for
		} // fin 1re boucle for

	}

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
		// g2d.translate(0, getHeight());
		// g2d.scale(1, -1);

		if (premiereFois) {
			pixelsParMetre = getWidth() / largeurDuComposantEnMetres;
			hauteurDuComposantEnMetres = getHeight() / pixelsParMetre;

			// vaisseau.setPosition(new Vecteur2D(posDeSauvegardeX, posDeSauvegardeY));

			premiereFois = false;
		} // fin condition dans paintComponent

		g2d.scale(pixelsParMetre, pixelsParMetre);

		dessinerNiveau(g2d);
		dessinerVaisseau(g2d);
		dessinerPlaques(g2d);
		dessinerPlaqueLorsSurvol(g2d);
		//dessinerPlaqueFantome(g2d);
	}

	/**
	 * Permet de dessiner le niveau
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerNiveau(Graphics2D g2d) {

		niveau.getGrille().dessinerTuile(g2d);
	}

	/**
	 * Permet de dessiner le vaisseau
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerVaisseau(Graphics2D g2d) {
		// vaisseau.setPixelsParMetre(pixelsParMetre);
		// vaisseau.setTuile(tuileDuVaisseau);
		vaisseau.dessiner(g2d);
	}

	/**
	 * Permet de dessiner la ou les plaques qui ont été placées dans le niveau
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerPlaques(Graphics2D g2d) {
		for (PlaqueChargee p : listePlaquesChargees) {
			p.dessiner(g2d);
		}
	}
	
	/**
	 * Permet de dessiner une plaque lorsqu'on survole les tuiles
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerPlaqueLorsSurvol(Graphics2D g2d) {
		if (placementPlaque & sourisDansComposant) {
			plaque.dessiner(g2d);
		}
	}

	/**
	 * Méthode qui dessine la plaque fantôme
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Giroux
	private void dessinerPlaqueFantome(Graphics2D g2d) {
		if (placementPlaque & sourisDansComposant) {
			if (plaquePositive) {
				imagePlaque = OutilsImage.lireImage("PlaqueChargePositive.png");
			} else {
				imagePlaque = OutilsImage.lireImage("PlaqueChargeNegative.png");
			}
			System.out.println("posPlaqueX : " + posPlaqueX / pixelsParMetre);
			System.out.println("posPlaqueY : " + posPlaqueY / pixelsParMetre);
			System.out.println("getPosX() : " + plaque.getPosition().getX());
			System.out.println("getPosY() : " + plaque.getPosition().getY());
			g2d.drawImage(imagePlaque, (int) (plaque.getPosition().getX()), (int) (plaque.getPosition().getY()), null);
		}
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

		Vecteur2D forceElec = VEC_ZERO;
		for (PlaqueChargee p : listePlaquesChargees) {
			forceElec = MoteurPhysique.calculForceElectriqueGenereeParPlaque(vaisseau, p);
		}
		/*
		 * Éventuellement il faudra initialiser
		 * les forces de frottement (statique et cinétique)
		 * les forces d'autres plaques
		 */
		sommeForcesSurVaisseau = sommeForcesSurVaisseau.additionne(forceElec);
		vaisseau.setSommeDesForces(sommeForcesSurVaisseau);
		vaisseau.avancerUnPas(deltaT);

		System.out.println("Le vaisseau bleu : " + vaisseau.toString(3));
		for (PlaqueChargee p : listePlaquesChargees) {
			System.out.println("Plaque : " + p.toString(3));
		}
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
		for (PlaqueChargee p : listePlaquesChargees) {
			vaisseau.gererCollisionAvecPlaque(p);
		}
		vaisseau.gererCollisionAvecBordures(largeurDuComposantEnMetres, hauteurDuComposantEnMetres);
		testerCollisionsAvecObjetsSpeciaux();
	}

	/**
	 * Méthode qui teste si le vaisseau entre en collision avec des objets spéciaux
	 * (drapeau, pics, portail)
	 */
	// Kitimir Yim
	private void testerCollisionsAvecObjetsSpeciaux() {

		Tuile[][] tab = niveau.getGrille().getTableau();

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab.length; j++) {
				Tuile tuile = tab[i][j];

				if (tuile != null && (tuile.getType().equals("Drapeau") | tuile.getType().equals("Pics")
						| tuile.getType().equals("Portail"))) {

					if (MoteurPhysique.detecteCollisionsAvecObjetsSpeciaux(vaisseau, tuile)) {
						switch (tuile.getType()) {
						case "Drapeau":
							enCoursDAnimation = false;
							break;
						case "Pics":
							enCoursDAnimation = false;
							System.out.println("Vous êtes mort!");
							recommencer();
							break;
						case "Portail":
							teleportation(tuile);
							break;
						}
					}
				} // fin if
			} // fin 2e boucle for
		} // fin 1re boucle for
	}// fin méthode

	private void teleportation(Tuile tuile) {
		Portail portailIni = (Portail) tuile;
		Portail portailFinal = portailIni.getPortailAssocie();
		int rayon = Tuile.getHauteurTuile() / 2;

		Double posDeXPortail = portailFinal.getPointZero().getX() + rayon;
		Double posDeYPortail = portailFinal.getPointZero().getY() + rayon;
		Vecteur2D posPortailFinal = new Vecteur2D(posDeXPortail, posDeYPortail);
		vaisseau.setPosition(posPortailFinal);
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
	 * Permet de repositioner le vaisseau à son dernier point de sauvegarde s'il est
	 * tué
	 */
	// Enuel René Valentin Kizozo Izia
	public void recommencer() {
		vaisseau.setCharge(chargeVaisseau);
		vaisseau.setMasse(masseVaisseau);
		vaisseau.setPosition(new Vecteur2D(posDeSauvegardeX, posDeSauvegardeY));
		vaisseau.setVitesse(VEC_ZERO);
		vaisseau.setAccel(VEC_ZERO);
		vaisseau.setSommeDesForces(VEC_ZERO);
		
		sommeForcesSurVaisseau = new Vecteur2D(forceGrav);

		// Désactiver les plaques chargées (charge neutre), éventuellement

		// À gérer plus tard si l'utilisateur fait n'importe nawak
		try {
			// plaqueRouge.setNormale( new Vecteur2D(normalePlaqueComposanteX,
			// normalePlaqueComposanteY).normalise() );
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
		vaisseau.setCharge(CHARGE_INITIALE_VAISSEAU);
		vaisseau.setMasse(MASSE_INITIALE_VAISSEAU);
		vaisseau.setPosition(new Vecteur2D(posDeSauvegardeX, posDeSauvegardeY));
		vaisseau.setVitesse(VEC_ZERO);
		vaisseau.setAccel(VEC_ZERO);
		vaisseau.setSommeDesForces(VEC_ZERO);

		listePlaquesChargees.clear();
		sommeForcesSurVaisseau = new Vecteur2D(forceGrav);

		enCoursDAnimation = false;
		premiereFois = true;
		// reinitialiser = true;
		tempsTotalEcoule = 0;

		repaint();
	}

	// GETTERS ET SETTERS //
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
		vaisseau.setCharge(chargeVaisseau);
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
		vaisseau.setMasse(masseVaisseau);
		setForceGrav();
		repaint();
	}

	/**
	 * Retourne la force gravitationnelle agissant sur le vaisseau
	 * 
	 * @return La force gravitationnelle agissant sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public Vecteur2D getForceGrav() {
		return forceGrav;
	}

	/**
	 * Modifie la force gravitationnelle agissant sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setForceGrav() {
		// sommeForcesSurVaisseau.soustrait(forceGrav);
		forceGrav = MoteurPhysique.calculForceGravEnY(masseVaisseau);
		// sommeForcesSurVaisseau.additionne(forceGrav);
		sommeForcesSurVaisseau = new Vecteur2D(forceGrav);
	}

	/**
	 * Retourne la charge des plaques du niveau
	 * 
	 * @return La charge des plaques du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargeDesPlaques() {
		return chargeDesPlaques;
	}

	/**
	 * Modifie la charge de la plaque
	 * 
	 * @param chargePlaques La charge de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setChargeDesPlaques(double chargePlaques) {
		this.chargeDesPlaques = chargePlaques;
		// Changer la charge de toutes les plaques du niveau OU Changer la charge de la plaque à placer ?
		// À déterminer, là ça les changes toutes (pour changer celle à placer il faut le faire lors de sa création)
		for (PlaqueChargee p : listePlaquesChargees) {
			p.setCharge(chargePlaques);
		}
		repaint();
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
	 * Retourne le niveau
	 * 
	 * @return Le niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public Niveau getNiveau() {
		return niveau;
	}

	/**
	 * Modifie le niveau en y chargeant un nouveau niveau
	 * 
	 * @param nomNiveau Le nom du niveau (une chaîne de caractère)
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNiveau(String nomNiveau) {
		this.niveau = Sauvegarder.chargerNiveau(nomNiveau);
		niveau.getGrille().setDansModeJeu(true);
		placerVaisseauPourDebutAnimation(niveau);
		repaint();
	}

	/**
	 * Modifie la valeur du booléen permettant de placer une plaque dans le niveau
	 * 
	 * @param btnActionnee La nouvelle valeur du booléen permettant de placer une
	 *                     plaque dans le niveau (vrai si le bouton est enclenché)
	 */
	public void setPlacementPlaque(boolean btnActionnee) {
		this.placementPlaque = btnActionnee;
	}

	// GETTERS DE CERTAINES CONSTANTES //
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
	 * Retourne la charge initiale de la plaque
	 * 
	 * @return La charge initiale de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargeInitialePlaque() {
		return CHARGE_INITIALE_DES_PLAQUES;
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

//	/**
//	 * Méthode qui change si la plaque est sélectionnée ou le contraire
//	 */
//	//Giroux
//	public void setPlaqueSelectionne() {
//		if(plaqueSelectionne) {
//			plaqueSelectionne =false;
//		} else {
//			plaqueSelectionne=true;
//		}
//	}

	/**
	 * Méthode qui change la nature de la plaque
	 * 
	 * @param positive True si elle devient poitive, false sinon
	 */
	// Giroux
	public void setPlaquePositive(Boolean positive) {
		if (positive) {
			plaquePositive = true;
		} else {
			plaquePositive = false;
		}
	}

}
