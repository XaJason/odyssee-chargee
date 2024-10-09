package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interactif.PlaqueChargee;
import interactif.Vaisseau;
import niveau.Niveau;
import niveau.Sauvegarder;
import physique.MoteurPhysique;
import physique.Segment;
import physique.Vecteur2D;
import tuile.Portail;
import tuile.Tuile;
import tuile.VaisseauImage;
import utilitaires.Aire;
import utilitaires.OutilsImage;

/**
 * Composant illustrant la simulation : La scène physique où sont représentés
 * les objets intéractifs physique ainsi que le niveau et ses tuiles
 *
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 * @author Giroux
 * @author Jason Xa
 *
 */
public class ZoneAnimationPhysique extends JPanel implements Runnable {

	/** Numéro d'identification pour la sérialisation **/
	private static final long serialVersionUID = -8878846015876118047L;
	/** Largeur du niveau (en mètre) **/
	private double largeurDuComposantEnMetres = 400.0;
	/** Hauteur du niveau (en mètre) **/
	private double hauteurDuComposantEnMetres = 300.0;

	/** Pas de simulation initial (en seconde) **/
	private final double DELTA_T_INITIAL = 0.01;
	/** Temps de la pause du thread d'animation initiale (en milliseconde) **/
	private final int TEMPS_DU_SLEEP_INITIAL = 8;

	/** Pas de simulation (en seconde) **/
	private double deltaT = DELTA_T_INITIAL;
	/** Temps de la pause du thread d'animation (en milliseconde) **/
	private int tempsDuSleep = TEMPS_DU_SLEEP_INITIAL;
	/** Booléen permettant de savoir si l'animation est en cours **/
	private boolean enCoursDAnimation = false;
	/** Booléen indiquant si c'est la première fois. **/
	private boolean premiereFois = true;
	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre;
	/** Vecteur nul **/
	private final Vecteur2D VEC_ZERO = new Vecteur2D();

	// Caractéristiques de la souris
	/** Coordonnée en X du curseur de la souris sur le composant (en mètre) **/
	private double sourisEnMetreX = -30; // Initialement à l'extérieur du composant
	/** Coordonnée en Y du curseur de la souris sur le composant (en metre) **/
	private double sourisEnMetreY = -30; // Initialement à l'extérieur du composant
	/** Représente le curseur de la souris (en mètre) **/
	private Point2D curseurSouris = new Point2D.Double(sourisEnMetreX, sourisEnMetreY);
	/** Indique que le curseur de la souris est à l'intérieur du composant **/
	private boolean sourisDansComposant = false;

	// Caractéristiques du niveau
	/** Objet représentant la grille ainsi que toutes ses tuiles **/
	private Niveau niveau;
	/** Force appliquée par le réacteur dorsal sur le vaisseau (en Newton) **/
	private Vecteur2D forceJetpack = new Vecteur2D(VEC_ZERO);
	/** Booléen qui indique si le mode réacteur dorsal est activé **/
	private boolean modeJetpack = false;

	// Caractéristiques du clavier
	/** Booléen qui indique l'état d'enfoncement de la touche flèche gauche **/
	private boolean gauche;
	/** Booléen qui indique l'état d'enfoncement de la touche flèche droite **/
	private boolean droite;
	/** Booléen qui indique l'état d'enfoncement de la touche flèche haut **/
	private boolean haut;
	/** Booléen qui indique l'état d'enfoncement de la touche flèche bas **/
	private boolean bas;

	// pour ne pas la voir
	/** Determine si la plaque est positive ou non **/
	private boolean plaquePositive = true;
	/** Détermine le signe de la plaque chargée (1 si positive, -1 si négative) **/
	private double signePlaque = 1;

	// Caractéristiques des plaques chargées
	/** Charge initiale des plaques du niveau (en Coulomb) **/
	private final double CHARGE_INITIALE_DES_PLAQUES = 10.0;
	/** Charge des plaques du niveau (en Coulomb) **/
	private double chargeDesPlaques = CHARGE_INITIALE_DES_PLAQUES;
	/** Liste des plaques chargées **/
	private ArrayList<PlaqueChargee> listePlaquesChargees = new ArrayList<>();
	/** Plaque chargée **/
	private PlaqueChargee plaque = new PlaqueChargee(chargeDesPlaques); // Placée par défaut à l'extérieur du composant
	/** Nombre de plaques restantes à placer **/
	private int nbPlaquesRestantes = 10;
	/** Booléen qui indique si le bouton de la plaque est actionnée **/
	private boolean placementPlaque = false;
	/**
	 * Booléen qui indique si l'on souhaite fixer une plaque sur tuile (après avoir
	 * cliqué dessus)
	 **/
	private boolean fixerPlaqueSurTuile = false;
	/** Booléen qui indique si l'on souhaite supprimer une plaque **/
	private boolean supprimerPlaque = false;
	/** Booléen qui indique si l'on souhaite réinitialiser l'état des tuiles **/
	private boolean reinitialiserEtatTuiles = false;

	// Caractéristiques du vaisseau (Constantes)
	/** Charge initiale du vaisseau (en Coulomb) **/
	private final double CHARGE_INITIALE_VAISSEAU = -10.0;
	/** Masse initiale du vaisseau (en kilogramme) **/
	private final double MASSE_INITIALE_VAISSEAU = 0.020;
	/** Composante en X de la position initiale du vaisseau (en mètre) **/
	private final double POS_INITIALE_VAISSEAU_EN_X = 90; // Impossible à définir en constante, car ne peut être
															// ré-initialié
	/** Composante en Y de la position initiale du vaisseau (en mètre) **/
	private final double POS_INITIALE_VAISSEAU_EN_Y = 165; // Impossible à définir en constante, car ne peut être
															// ré-initialié

	// Caractéristiques du vaisseau
	/** Objet représentant le vaisseau **/
	private Vaisseau vaisseau;
	/** Charge du vaisseau (en Coulomb) **/
	private double chargeVaisseau = CHARGE_INITIALE_VAISSEAU;
	/** Dernière charge du vaisseau non nulle */
	private double chargeVaisseauNonNulle = chargeVaisseau;
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
	/** Forces électriques agissant sur le vaisseau **/
	private Vecteur2D forcesElec = VEC_ZERO;
	/** Force de frottement agissant sur le vaisseau **/
	private Vecteur2D forceFrot = VEC_ZERO;
	/** Somme des forces agissant sur le vaisseau **/
	private Vecteur2D sommeForcesSurVaisseau;
	/** Compteur de collision non trouvée **/
	private double cptrCollisionNonTrouvee;
	/** Constante de récurrence de collision trouvée **/
	private final int RECURRENCE_COLLISION = 3;

	/**
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);

	/**
	 * Constructeur de la zone d'animation physique
	 */
	// Enuel René Valentin Kizozo Izia
	public ZoneAnimationPhysique() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lancerFocusPerdu();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (modeJetpack) {
					actualiserTouchesEnfoncees(e, true);
				}
				gererChargeClavier(e);

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (enCoursDAnimation) {
					actualiserTouchesEnfoncees(e, false);
				}

			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				if (placementPlaque) {
					transformerCoordonneesSouris(e);
					survolerTuilesPourTrouverCurseur();
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

				transformerCoordonneesSouris(e);
				gererPlaquesAvecSouris(e);

			}
		});
		setBackground(Color.lightGray);
		setBounds(29, 31, 1232, 617);

		niveau = Sauvegarder.chargerNiveauDeBase("Niveau_base1");
		placerVaisseauPourDebutAnimation(niveau);
		niveau.getGrille().setDansModeJeu(true);

	}

	/**
	 * Voici la méthode qui permettra à un objet de s'ajouter en tant qu'écouteur
	 *
	 * @param listener L'objet PropertyChangeListener à ajouter comme écouteur de
	 *                 propriété.
	 */
	// Kitimir Yim
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}

	/**
	 * Cause la fin du thread
	 */
	// Enuel René Valentin Kizozo Izia
	public void arreter() {
		enCoursDAnimation = false;
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
	 * Retourne la charge des plaques du niveau
	 *
	 * @return La charge des plaques du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargeDesPlaques() {
		return chargeDesPlaques;
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
	 * Retourne la charge initiale du vaisseau
	 *
	 * @return La charge initiale du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getChargeInitialeVaisseau() {
		return CHARGE_INITIALE_VAISSEAU;
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
	 * Retourne la valeur du pas de simulation (deltaT)
	 *
	 * @return La pas de simulation (deltaT)
	 */
	// Enuel René Valentin Kizozo Izia
	public double getDeltaT() {
		return deltaT;
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
	 * Retourne la masse initiale du vaisseau
	 *
	 * @return La masse initiale du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public double getMasseInitialeVaisseau() {
		return MASSE_INITIALE_VAISSEAU;
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
	 * Retourne le nombre de plaques restantes
	 *
	 * @return Le nombre de plaques restantes
	 */
	// Enuel René Valentin Kizozo Izia
	public int getNbPlaquesRestantes() {
		return nbPlaquesRestantes;
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
	 * Retourne la pause du thread d'animation (temps du sleep)
	 *
	 * @return La pause du thread d'animation
	 */
	// Enuel René Valentin Kizozo Izia
	public int getTempsSleep() {
		return tempsDuSleep;
	}

	/**
	 * Retourne le temps du sleep initial
	 *
	 * @return Le temps du sleep initial
	 */
	// Enuel René Valentin Kizozo Izia
	public double getTempsSleepInitial() {
		return TEMPS_DU_SLEEP_INITIAL;
	}

	/**
	 * Envoie le message pour mettre à jour les boutons de contrôle d'animation et
	 * la zone d'animation selon le bouton Recommencer
	 */
	// Kitimir Yim
	public void miseAJourEtatBoutonsPourRecommencer() {
		PCS.firePropertyChange("Recommencer", null, null);

	}

	/**
	 * Envoie le message pour mettre à jour les boutons de contrôle d'animation et
	 * la zone d'animation selon le bouton Réinitialiser
	 */
	// Kitimir Yim
	public void miseAJourEtatBoutonsPourReinitialiser() {
		PCS.firePropertyChange("retournerNiveau", null, 0);

	}

	/**
	 * Permet de dessiner des objets sur le composant
	 *
	 * @param g Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
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

			definirDimensionsTuilesEtlireImages();

			premiereFois = false;
		}

		Graphics2D g2dPrive = (Graphics2D) g2d.create();

		g2dPrive.scale(pixelsParMetre, pixelsParMetre);

		dessinerNiveau(g2dPrive);
		dessinerVaisseau(g2dPrive);
		dessinerPlaques(g2dPrive);
		dessinerPlaqueLorsSurvol(g2dPrive);

	}

	/**
	 * Permet d'avancer d'une image
	 */
	// Enuel René Valentin Kizozo Izia
	public void prochaineImage() {
		enCoursDAnimation = false;

		calculerUneIterationPhysique(deltaT);
		leveeSorties();
		repaint();
		testerCollisionsEtAjusterVitesses();
		leveeSorties();
		repaint();
	}

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
		vaisseau.setForceNormale(VEC_ZERO);

		sommeForcesSurVaisseau = new Vecteur2D(forceGrav);
		vaisseau.setSommeDesForces(sommeForcesSurVaisseau);

		vaisseau.setEnCollision(false);
		vaisseau.setCollisionTrouvee(false);
		cptrCollisionNonTrouvee = 0;
		gauche = false;
		droite = false;
		haut = false;
		bas = false;

		enCoursDAnimation = false;
		leveeSorties();
		repaint();

		// On pause l'application pour que le thread ait le temps de mourir avant d'en
		// relancer un autre
		try {
			Thread.sleep(tempsDuSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		vaisseau.setForceNormale(VEC_ZERO);

		sommeForcesSurVaisseau = new Vecteur2D(forceGrav);
		vaisseau.setSommeDesForces(sommeForcesSurVaisseau);
		cptrCollisionNonTrouvee = 0;
		vaisseau.setEnCollision(false);
		vaisseau.setCollisionTrouvee(false);

		retirerPlaquesDesTuiles();
		listePlaquesChargees.clear();
		nbPlaquesRestantes = 10;
		leveeNbPlaquesRestantes();
		gauche = false;
		droite = false;
		haut = false;
		bas = false;

		enCoursDAnimation = false;
		premiereFois = true;
		leveeSorties();
		repaint();
	}

	/**
	 * Permet d'effectuer l'animation
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
	public void run() {
		while (enCoursDAnimation) {

			calculerUneIterationPhysique(deltaT);
			repaint();
			testerCollisionsEtAjusterVitesses();
			leveeSorties();
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Modifie la charge de la plaque
	 *
	 * @param chargePlaques La charge de la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public void setChargeDesPlaques(double chargePlaques) {
		this.chargeDesPlaques = signePlaque * chargePlaques;
		repaint();
	}

	/**
	 * Modifie la charge du vaisseau
	 *
	 * @param chargeVaisseau La charge du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setChargeVaisseau(double chargeVaisseau) {
		if (chargeVaisseau != 0 && chargeVaisseau != -0) {
			chargeVaisseauNonNulle = chargeVaisseau;
		}
		this.chargeVaisseau = chargeVaisseau;
		vaisseau.setCharge(chargeVaisseau);
		repaint();
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
	 * Modifie la force gravitationnelle agissant sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void setForceGrav() {
		forceGrav = MoteurPhysique.calculForceGravEnY(masseVaisseau);

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
	 * Méthode qui déterminer si le mode JetPack est activé ou non
	 *
	 * @param modeJetpack Vrai si on l'active, faux sinon
	 */
	// Giroux
	public void setModeJetpack(boolean modeJetpack) {
		this.modeJetpack = modeJetpack;
	}

	/**
	 * Modifie le nombre de plaques restantes
	 *
	 * @param nbPlaquesRestantes Le nombre de plaques restantes
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNbPlaquesRestantes(int nbPlaquesRestantes) {
		this.nbPlaquesRestantes = nbPlaquesRestantes;
	}

	/**
	 * Modifie le niveau en le remplaçant par un autre
	 *
	 * @param niveau Le nouveau niveau
	 */
	// Kitimir Yim
	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
		modificationSupplementaireSetNiveau(niveau);
		repaint();
	}

	/**
	 * Modifie le niveau en en chargeant un nouveau niveau
	 *
	 * @param nomNiveau Le nom du niveau (une chaîne de caractère)
	 */
	// Enuel René Valentin Kizozo Izia
	public void setNiveau(String nomNiveau) {
		this.niveau = Sauvegarder.chargerNiveauMesTrucs(nomNiveau);
		modificationSupplementaireSetNiveau(niveau);
		repaint();
	}

	/**
	 * Modifie le niveau en en chargeant un nouveau niveau
	 *
	 * @param nomNiveau Le nom du niveau (une chaîne de caractère)
	 */
	// Kitimir Yim
	public void setNiveauDeBase(String nomNiveau) {
		this.niveau = Sauvegarder.chargerNiveauDeBase(nomNiveau);
		modificationSupplementaireSetNiveau(niveau);
		repaint();
	}

	/**
	 * Modifie la valeur du booléen permettant de placer une plaque dans le niveau
	 *
	 * @param btnActionnee La nouvelle valeur du booléen permettant de placer une
	 *                     plaque dans le niveau (vrai si le bouton est enclenché)
	 */
	// Enuel René Valentin Kizozo Izia
	public void setPlacementPlaque(boolean btnActionnee) {
		this.placementPlaque = btnActionnee;
	}

	/**
	 * Méthode qui change la nature de la plaque
	 *
	 * @param valeur True si elle devient poitive, false sinon
	 */
	// Giroux
	public void setPlaquePositive(boolean valeur) {
		if (valeur) {
			plaquePositive = true;
			setSignePlaque();
		} else {
			plaquePositive = false;
			setSignePlaque();
		}

	}

	/**
	 * Modifie la pause du thread d'animation (temps du sleep)
	 *
	 * @param tempsDuSleep La pause du thread d'animation
	 */
	// Enuel René Valentin Kizozo Izia
	public void setTempsSleep(int tempsDuSleep) {
		this.tempsDuSleep = tempsDuSleep;
		setCooldownPortail();
	}

	/**
	 * Actualise les valeurs des booléens correspondant aux touches des flèches sur
	 * le clavier
	 *
	 * @param e              L'événement du clavier
	 * @param toucheEnfoncee Le booléen indiquant si la touche correspondant à
	 *                       l'événement du clavier est enfoncée
	 */
	// Enuel René Valentin Kizozo Izia
	private void actualiserTouchesEnfoncees(KeyEvent e, boolean toucheEnfoncee) {
		int code = e.getKeyCode();

		switch (code) {
		case KeyEvent.VK_LEFT:
			demarrerAvecFleches();
			gauche = toucheEnfoncee;
			break;

		case KeyEvent.VK_RIGHT:
			demarrerAvecFleches();
			droite = toucheEnfoncee;
			break;

		case KeyEvent.VK_UP:
			demarrerAvecFleches();
			haut = toucheEnfoncee;
			break;

		case KeyEvent.VK_DOWN:
			demarrerAvecFleches();
			bas = toucheEnfoncee;
			break;
		}
	}

	/**
	 * Applique des forces constantes sur le vaisseau selon l'état d'enfoncement des
	 * flèches sur le clavier
	 */
	// Enuel René Valentin Kizozo Izia
	private void appliquerForcesDuJetpack() {
		Vecteur2D forceTemp = new Vecteur2D(VEC_ZERO);

		if (gauche) {
			forceTemp = forceTemp.additionne(MoteurPhysique.appliqueForceVersGauche(vaisseau.getMasse()));
		} else {
			forceTemp = forceTemp.additionne(VEC_ZERO);
		}

		if (droite) {
			forceTemp = forceTemp.additionne(MoteurPhysique.appliqueForceVersDroite(vaisseau.getMasse()));
		} else {
			forceTemp = forceTemp.additionne(VEC_ZERO);
		}

		if (haut) {
			forceTemp = forceTemp.additionne(MoteurPhysique.appliqueForceVersHaut(vaisseau.getMasse()));
		} else {
			forceTemp = forceTemp.additionne(VEC_ZERO);
		}

		if (bas) {
			forceTemp = forceTemp.additionne(MoteurPhysique.appliqueForceVersBas(vaisseau.getMasse()));
		} else {
			forceTemp = forceTemp.additionne(VEC_ZERO);
		}

		forceJetpack = new Vecteur2D(forceTemp);
	}

	/**
	 * Calcul de la nouvelle position du vaisseau
	 *
	 * @param deltaT Le pas de simulation
	 */
	// Enuel René Valentin Kizozo Izia
	private void calculerUneIterationPhysique(double deltaT) {
		// Initialise la force gravitationnelle agissant sur le vaisseau
		sommeForcesSurVaisseau = new Vecteur2D(forceGrav);

		// Initialise les forces électriques agissant sur le vaisseau
		forcesElec = new Vecteur2D(VEC_ZERO);
		for (PlaqueChargee p : listePlaquesChargees) {
			forcesElec = forcesElec.additionne(MoteurPhysique.calculForceElectriqueGenereeParPlaque(vaisseau, p));
		}
		sommeForcesSurVaisseau = sommeForcesSurVaisseau.additionne(forcesElec);

		// Initialise les forces du réacteur dorsal agissant sur le vaisseau
		appliquerForcesDuJetpack();
		sommeForcesSurVaisseau = sommeForcesSurVaisseau.additionne(forceJetpack);

		// Initialise la force de frottement agissant sur le vaisseau

		if (vaisseau.getDureeCollision() > 2000) {
			// Initialise la force normale agissant sur le vaisseau
			sommeForcesSurVaisseau = sommeForcesSurVaisseau.additionne(vaisseau.getForceNormale());

			forceFrot = MoteurPhysique.calculForceFrottement(vaisseau, sommeForcesSurVaisseau);
			sommeForcesSurVaisseau = sommeForcesSurVaisseau.additionne(forceFrot);
		}

		vaisseau.setSommeDesForces(sommeForcesSurVaisseau);
		vaisseau.avancerUnPas(deltaT);

	}

	/**
	 * Définit les dimensions des tuiles et lit leur image
	 */
	// Enuel René Valentin Kizozo Izia
	private void definirDimensionsTuilesEtlireImages() {
		Tuile.setLargeurTuile(niveau.getGrille().getLargeurCase());
		Tuile.setHauteurTuile(niveau.getGrille().getHauteurCase());
		OutilsImage.lireImagesDesTuiles(niveau.getGrille().getLargeurCase(), niveau.getGrille().getHauteurCase(),
				pixelsParMetre);
	}

	/**
	 * Démarrer l'animation à l'aide des flèches du clavier et met à jour le panneau
	 * de jeu en conséquence (changement d'état des boutons « Démarrer » et «
	 * Prochaine image »)
	 */
	// Enuel René Valentin Kizozo Izia
	private void demarrerAvecFleches() {
		if (!enCoursDAnimation) {
			demarrer();
			PCS.firePropertyChange("Démarrer", null, null);
		}
	}

	/**
	 * Permet de dessiner le niveau
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerNiveau(Graphics2D g2d) {
		niveau.getGrille().dessinerLesTuiles(g2d);
	}

	/**
	 * Permet de dessiner une plaque lorsqu'on survole les tuiles
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerPlaqueLorsSurvol(Graphics2D g2d) {
		if (placementPlaque & sourisDansComposant & nbPlaquesRestantes > 0) {
			Tuile[][] tabTuiles = niveau.getGrille().getTableau();

			for (Tuile[] tabTuile : tabTuiles) {
				for (Tuile tuile : tabTuile) {
					if (tuile != null) {

						if (tuile != null
								&& (tuile.getType().equals("Carré") | tuile.getType().equals("Triangle rectangle")
										| tuile.getType().equals("Triangle équilatéral"))) {

							if (tuile.contient(curseurSouris)) {
								plaque.dessiner(g2d);
							}
						}
					}
				}
			}
		}
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
	 * Permet de dessiner le vaisseau
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	private void dessinerVaisseau(Graphics2D g2d) {
		vaisseau.dessiner(g2d);
	}

	/**
	 * Permet de définir l'emplacemet d'une plaque sur le composant d'animation en
	 * fonction d'où se situe le curseur de la souris
	 *
	 * @param e L'événement de la souris
	 */
	// Enuel René Valentin Kizozo Izia
	private void fixerPlaque(MouseEvent e) {
		if (placementPlaque) {
			fixerPlaqueSurTuile = true;
			survolerTuilesPourTrouverCurseur();
			fixerPlaqueSurTuile = false;
		}
	}

	/**
	 * Gère les cas où une touche du clavier affecte la charge du vaisseau
	 *
	 * @param e l'évènement du clavier
	 */
	// Jason Xa
	private void gererChargeClavier(KeyEvent e) {
		int code = e.getKeyCode();

		switch (code) {
		case KeyEvent.VK_A:
			rafraichirChargeVaisseau(-Math.abs(chargeVaisseauNonNulle));
			break;
		case KeyEvent.VK_S:
			rafraichirChargeVaisseau(0);
			break;
		case KeyEvent.VK_D:
			rafraichirChargeVaisseau(Math.abs(chargeVaisseauNonNulle));
			break;

		}

	}

	/**
	 * Gère l'état de collision et la force normale du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	private void gererEtatCollisionEtForceNormale() {
		if (vaisseau.getCollisionTrouvee()) {
			cptrCollisionNonTrouvee = 0;
		}

		if (!vaisseau.getCollisionTrouvee()) {
			vaisseau.setForceNormale(VEC_ZERO);
			cptrCollisionNonTrouvee++;
			if (cptrCollisionNonTrouvee % RECURRENCE_COLLISION == 0) {
				vaisseau.setEnCollision(false);

			}
		}
	}

	/**
	 * Gère le placement ou la suppression des plaques lors des cliques de souris
	 *
	 * @param e L'événement de la souris
	 */
	// Enuel René Valentin Kizozo Izia
	private void gererPlaquesAvecSouris(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			fixerPlaque(e);
			break;
		case MouseEvent.BUTTON3:
			supprimerPlaque = true;
			survolerTuilesPourTrouverCurseur();
			supprimerPlaque = false;
			break;
		}
		repaint();
	}

	/**
	 * Lance l'évènement qui demande le focus dans la fenêtre
	 */
	// Jason Xa
	private void lancerFocusPerdu() {
		PCS.firePropertyChange("FocusZoneAnimationPhysique", null, null);
	}

	/**
	 * Met à jour l'étiquette du nombre de plaques restantes dans le panneau de jeu
	 */
	// Enuel René Valentin Kizozo Izia
	private void leveeNbPlaquesRestantes() {
		PCS.firePropertyChange("Plaques restantes", null, nbPlaquesRestantes);
	}

	/**
	 * Envoie le message de levée d'évenements
	 */
	// Kitimir Yim
	private void leveeSorties() {
		PCS.firePropertyChange("changerVitesse", vaisseau.getVitesse().getX(), vaisseau.getVitesse().getY());
		PCS.firePropertyChange("changerAcceleration", null, vaisseau.getAccel());
		PCS.firePropertyChange("changerForceElec", null, this.forcesElec);
		PCS.firePropertyChange("changerForceGravite", null, this.forceGrav.getY());
		PCS.firePropertyChange("changerChampElec", null, this.forcesElec.multiplie(1 / Math.abs(vaisseau.getCharge())));
		PCS.firePropertyChange("changerPosition", vaisseau.getPosition().getX(), vaisseau.getPosition().getY());

	}

	/**
	 * Affiche le message après la fin du jeu
	 *
	 * @param typeTuile La chaîne de caractère indiquant le type de la tuile
	 */
	// Kitimir Yim
	private void messageMort(String typeTuile) {
		Object[] options = { "Recommencer", "Sélecteur de niveau" };
		int choix = 0;
		if (typeTuile.equals("Pic")) {
			choix = JOptionPane.showOptionDialog(null, "Vous avez touché les pics! Que voulez-vous faire?", "Game Over",
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		} else if (typeTuile.equals("Drapeau")) {
			choix = JOptionPane.showOptionDialog(null, "Vous avez atteint le drapeau! Que voulez-vous faire?",
					"Niveau Terminé", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
					options[0]);
		}

		switch (choix) {
		case 0:
			miseAJourEtatBoutonsPourRecommencer();
			break;
		case 1:
			miseAJourEtatBoutonsPourReinitialiser();
			break;
		}
	}

	/**
	 * Modification supplémentaire à effectuer lorsque l'on modifie un niveau
	 *
	 * @param niveau Le nouveau niveau
	 */
	// Enuel René Valentin Kizozo Izia
	private void modificationSupplementaireSetNiveau(Niveau niveau) {
		niveau.getGrille().setDansModeJeu(true);
		placerVaisseauPourDebutAnimation(niveau);
		listePlaquesChargees.clear();
	}

	/**
	 * Placer une plaque sur une tuile, à l'aide de la position de l'objet Aire sur
	 * lequel le curseur de la souris se trouve
	 *
	 * @param tuile La tuile sur laquelle on souhaite placer une plaque
	 */
	// Enuel René Valentin Kizozo Izia
	private void placerPlaque(Tuile tuile) {

		// Recréer les aires des tuiles, car elles ne sont pas chargeables dans un
		// fichier binaire
		if (tuile.getAires() == null) {
			tuile.creerAires(tuile.getPointMilieu());
		}

		Aire aireOuEstCurseur = tuile.survolerAiresDeTuile(curseurSouris);
		if (aireOuEstCurseur != null) {
			plaque.setPosition(new Vecteur2D(aireOuEstCurseur.getPointMilieuDeTuile().getX(),
					aireOuEstCurseur.getPointMilieuDeTuile().getY()));
			plaque.miseAJourExtremiteA();
			plaque.miseAJourExtremiteB();

			/*
			 * On prend la valeur absolue de la charge des plaques pour s'assurer que c'est
			 * bien la variable signePlaque qui lui attribue son signe
			 */
			chargeDesPlaques = Math.abs(chargeDesPlaques);
			plaque.setCharge(signePlaque * chargeDesPlaques);

			if (fixerPlaqueSurTuile & nbPlaquesRestantes == 0) {
				JOptionPane.showMessageDialog(null,
						"Il ne vous reste plus de plaques !"
								+ "\nRetirez-en si vous souhaitez en placer à d'autres endroits.",
						"Avertissement", JOptionPane.WARNING_MESSAGE, null);
			}

			if (fixerPlaqueSurTuile & nbPlaquesRestantes > 0) {
				Vecteur2D positionNouvellePlaque = new Vecteur2D(plaque.getPosition().getX(),
						plaque.getPosition().getY());
				PlaqueChargee plaqueAPlacer = new PlaqueChargee(positionNouvellePlaque, plaque.getCharge());
				listePlaquesChargees.add(plaqueAPlacer);
				tuile.setPlaque(plaqueAPlacer);
				nbPlaquesRestantes--;
				leveeNbPlaquesRestantes();
				repaint();
				if (nbPlaquesRestantes == 0) {
					JOptionPane.showMessageDialog(null, "Attention! Vous venez de placer votre dernière plaque."
							+ "\nRetirez-en si vous souhaitez en placer à d'autres endroits ou commencez à jouer!",
							"Avertissement", JOptionPane.WARNING_MESSAGE, null);
				}
			}
		}
	}

	/**
	 * Place le vaisseau au bon endroit dans la grille pour le début de l'animation,
	 * en fonction de la position de la tuile du vaisseau dans la grille du niveau
	 *
	 * @param niveau Le niveau dans lequel se trouve le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	private void placerVaisseauPourDebutAnimation(Niveau niveau) {

		Tuile[][] tabTuiles = niveau.getGrille().getTableau();

		for (Tuile[] tabTuile : tabTuiles) {
			for (Tuile tuile : tabTuile) {
				if (tuile != null && tuile.getType().equals("Vaisseau")) {
					vaisseau = new Vaisseau(posVaisseau, chargeVaisseau, masseVaisseau, (VaisseauImage) tuile);

					// Définir la position initial du vaisseau à l'aide de son emplacement dans le
					// niveau (sa tuile)
					posDeSauvegardeX = tuile.getPointZero().getX() + vaisseau.getRayon();
					posDeSauvegardeY = tuile.getPointZero().getY() + vaisseau.getRayon();
					posVaisseau = new Vecteur2D(posDeSauvegardeX, posDeSauvegardeY);
					vaisseau.setPosition(posVaisseau);
				}
			}
		}
	}

	/**
	 * Met à jour la charge du vaisseau partout (levée d'évènement pour le
	 * tourniquet d'entrée du panneau de jeu)
	 *
	 * @param chargeVaisseau la nouvelle charge électrique du vaisseau (Coulombs)
	 */
	// Jason Xa
	private void rafraichirChargeVaisseau(double chargeVaisseau) {
		setChargeVaisseau(chargeVaisseau);
		PCS.firePropertyChange("Charge négative", null, chargeVaisseau);
	}

	/**
	 * Passe au travers de toutes les tuiles pour retirer leur plaque, s'il y a lieu
	 */
	// Enuel René Valentin Kizozo Izia
	private void retirerPlaquesDesTuiles() {
		reinitialiserEtatTuiles = true;
		survolerTuilesPourTrouverCurseur();
		reinitialiserEtatTuiles = false;
	}

	/**
	 * Modifie le temps de refroidissement des portails en fonction du temps du
	 * sleep
	 */
	// Enuel René Valentin Kizozo Izia
	private void setCooldownPortail() {
		switch (tempsDuSleep) {
		case 2:
			Portail.setCooldownPortail(1000);
			break;
		case 5:
			Portail.setCooldownPortail(1500);
			break;
		case 8:
			Portail.setCooldownPortail(2000);
			break;
		case 16:
			Portail.setCooldownPortail(2500);
			break;
		case 20:
			Portail.setCooldownPortail(3000);
			break;
		}
	}

	/**
	 * Modifie le signe de la plaque Et met à jour sa charge
	 */
	// Enuel René Valentin Kizozo Izia
	private void setSignePlaque() {
		if (plaquePositive) {
			signePlaque = 1;
		} else {
			signePlaque = -1;
		}
		repaint();
	}

	/**
	 * Permet de supprimer la plaque sur laquelle on vient de cliquer à l'aide de la
	 * souris
	 *
	 * @param tuile La tuile sur laquelle se trouve la plaque que l'on souhaite
	 *              supprimer
	 */
	// Enuel René Valentin Kizozo Izia
	private void supprimerPlaque(Tuile tuile) {
		if (tuile.getPlaque() != null) {
			listePlaquesChargees.remove(tuile.getPlaque());
			tuile.setPlaque(null);
			nbPlaquesRestantes++;
			leveeNbPlaquesRestantes();
		}
	}

	/**
	 * Permet de survoler toutes les tuiles du niveau pour trouver sur quelle tuile
	 * se situe le curseur de la souris.
	 *
	 * Ainsi, on sait où placer la plaque, à l'aide de la position de l'objet Aire
	 * sur lequel le curseur se trouve
	 */
	// Enuel René Valentin Kizozo Izia
	private void survolerTuilesPourTrouverCurseur() {
		Tuile[][] tabTuiles = niveau.getGrille().getTableau();

		for (Tuile[] tabTuile : tabTuiles) {
			for (Tuile tuile : tabTuile) {
				if (tuile != null && (tuile.getType().equals("Carré") | tuile.getType().equals("Triangle rectangle")
						| tuile.getType().equals("Triangle équilatéral"))) {

					if (reinitialiserEtatTuiles) {
						tuile.setPlaque(null);
					} else {

						if (tuile.contient(curseurSouris)) {

							if (tuile.getPlaque() != null & fixerPlaqueSurTuile) {
								fixerPlaqueSurTuile = false;
								JOptionPane.showMessageDialog(null,
										"Vous ne pouvez pas placer plusieurs plaques au même endroit !",
										"Avertissement", JOptionPane.WARNING_MESSAGE, null);
							}

							if (supprimerPlaque) {
								supprimerPlaque(tuile);
							} else {
								placerPlaque(tuile);
							}

						}
					}
				}
			}
		}

	}

	/**
	 * Teste la collision avec la surface des blocs (carrés et triangles) Donc avec
	 * tous leurs segments
	 */
	// Enuel René Valentin Kizozo Izia
	private void testerCollisionAvecSurfaceDesBlocs() {
		Tuile[][] tabTuiles = niveau.getGrille().getTableau();

		for (Tuile[] tabTuile : tabTuiles) {
			for (Tuile tuile : tabTuile) {
				if (tuile != null && (tuile.getType().equals("Carré") | tuile.getType().equals("Triangle rectangle")
						| tuile.getType().equals("Triangle équilatéral"))) {
					// Gère collision avec les coins
					for (Point2D.Double coin : tuile.getPointsCoin()) {
						vaisseau.gererCollisionAvecCoin(coin);
					}
					// Gère collisions avec les faces latérales
					if (!vaisseau.getCollisionTrouvee()) {
						for (Segment segment : tuile.getListeSegments()) {
							vaisseau.gererCollisionAvecSegment(segment);
						}
					}
				}
			}
		}

	}

	/**
	 * Teste la collision avec toutes les bordures du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	private void testerCollisionsAvecBordures() {
		vaisseau.gererCollisionAvecBordures(largeurDuComposantEnMetres, hauteurDuComposantEnMetres);
	}

	/**
	 * Méthode qui teste si le vaisseau entre en collision avec des objets spéciaux
	 * (drapeau, pics, portail)
	 */
	// Kitimir Yim
	private void testerCollisionsAvecObjetsSpeciaux() {

		Tuile[][] tab = niveau.getGrille().getTableau();

		for (Tuile[] element : tab) {
			for (Tuile tuile : element) {
				if (tuile != null && (tuile.getType().equals("Drapeau") | tuile.getType().equals("Pics")
						| tuile.getType().equals("Portail"))) {

					if (MoteurPhysique.detecteCollisionsAvecObjetsSpeciaux(vaisseau, tuile)) {
						switch (tuile.getType()) {
						case "Drapeau":
							enCoursDAnimation = false;
							messageMort("Drapeau");
							break;
						case "Pics":
							enCoursDAnimation = false;
							messageMort("Pic");

							break;
						case "Portail":
							Portail portailInitial = (Portail) tuile;
							portailInitial.teleportation(vaisseau);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Teste la collision avec toutes les plaques du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	private void testerCollisionsAvecPlaque() {
		for (PlaqueChargee p : listePlaquesChargees) {
			vaisseau.gererCollisionAvecPlaque(p);
		}
	}

	/**
	 * Teste si des objets de la scene sont en collision. Si oui : on calcule les
	 * rebonds et on en déduit les nouvelles vitesses. Lors du prochain pas, ces
	 * nouvelles vitesses entrainent les objets dans de nouvelles directions.
	 */
	// Enuel René Valentin Kizozo Izia
	private void testerCollisionsEtAjusterVitesses() {
		/*
		 * Éventuellement, faire une boucle pour vérifier toutes les instances (murs,
		 * plaques, sol) avec lesquelles le vaisseau pourrait entrer en collision
		 * Peut-être passer à travers une liste contenant tous les objets de la scène?
		 */
		vaisseau.setCollisionTrouvee(false);
		testerCollisionsAvecPlaque();
		testerCollisionAvecSurfaceDesBlocs();
		testerCollisionsAvecBordures();
		testerCollisionsAvecObjetsSpeciaux();
		gererEtatCollisionEtForceNormale();
	}

	/**
	 * Permet de transformer les coordonnées de la souris en fonction des
	 * transformations du paintComponent: Soit, en mètre et avec l'origine en bas à
	 * gauche
	 *
	 * @param e L'événement de la souris
	 */
	// Enuel René Valentin Kizozo Izia
	private void transformerCoordonneesSouris(MouseEvent e) {
		curseurSouris = e.getPoint();
		sourisEnMetreX = e.getX() / pixelsParMetre;
		sourisEnMetreY = hauteurDuComposantEnMetres - e.getY() / pixelsParMetre;
		curseurSouris.setLocation(sourisEnMetreX, sourisEnMetreY);
	}

}
