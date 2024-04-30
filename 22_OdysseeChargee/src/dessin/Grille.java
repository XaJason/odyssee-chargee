package dessin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Random;

/**
 * @author Giroux
 * @author Jason Xa
 */
import javax.swing.JPanel;

import tuile.Carre;
import tuile.Drapeau;
import tuile.Pics;
import tuile.Portail;
import tuile.TriangleEquilateral;
import tuile.TriangleRectangle;
import tuile.Tuile;
import tuile.VaisseauImage;
import utilitaires.OutilsImage;

/**
 * Grille permettant le placement de différentes tuiles (éditeur de niveaux)
 * 
 * @author Giroux
 * @author Jason Xa
 * @author Kitimir Yim
 * @author Enuel René Valentin Kizozo Izia
 */
public class Grille extends JPanel implements Serializable {
	/** Numéro d'identification pour la sérialisation **/
	private static final long serialVersionUID = -977837790552954988L;
	/** Nombre de pixels par mètre. **/
	private double pixelsParMetre;
	/** Hauteur du composant en mètre **/
	private double hauteurDuComposantEnMetre;
	/** Largeur du composant en mètre **/
	private double largeurDuComposantEnMetre = 400;
	/** Hauteur de chaque case (en mètre) **/
	private double hauteurCase;
	/** Largeur du de chaque case (en mètre) **/
	private double largeurCase;
	/** Nombre de ligne **/
	private int nbCaseVerticale;
	/** Nombre de colonne **/
	private int nbCaseHorizontale = 20;

	/**
	 * Rectangle qui correspond à la section de la grille où se trouve la sourie
	 **/
	private Rectangle2D.Double emplacementActuel;
	/** Quand il dessine pour le première fois **/
	private Boolean premiereFois = true;
	/** Quadrillage verticale **/
	private Path2D.Double quadVerti;
	/** Quadrillage horizontal **/
	private Path2D.Double quadHori;
	/** Choix entre afficher la grille ou non **/
	private Boolean grille = true;
	/**
	 * Tableau qui contient la tuile si la case est occupée ou null si elle est vide
	 **/
	private Tuile tabEmplacement[][];
	/** Dernier endroit cliqué **/
	private Point2D clique;
	/** Dernière abscisse de la souris (en mètre) */
	private double sourisEnMetreX;
	/** Dernière ordonnée de la souris (en mètre) */
	private double sourisEnMetreY;
	/**
	 * Contient la tuile sélectionnée dans les boutons du panneau du mode éditeur
	 **/
	private Tuile tuile;
	/** Tuile qui correspond à celle dans le tableau des emplacements **/
	private Tuile tuileTableau;
	/**
	 * Tuile temporaire qui sauvegarde la tuile sélectionnée avec ses propriétés
	 **/
	private Tuile tuileTemp;
	/** Indique si l'emplacement est déjà pris **/
	private boolean placePrise = false;
	/** Indique s'il y a déjà un drapeau sur la grille **/
	private boolean drapeau = false;
	/** Indique s'il y a déjà un vaisseau sur la grille **/
	private boolean vaisseau = false;
	/** Indique si on est en mode supprimer ou non **/
	private boolean supprimer = false;
	/** Indique que la sourie est à l'exterieur du composant **/
	private boolean exterieurComposant = true;

	/** État du mode éditeur de la grille (faux si placement de plaques chargées) */
	private boolean modeEditeur = true;
	/** Booléan indiquant si l'on est dans le mode jeu **/
	private boolean dansModeJeu = false;
	/**
	 * Compteur du nombre de portail
	 */
	private int nbPortails = 0;
	/** premier portail **/
	private Portail premierPortail;
	/** Étendue des valeurs pour chacune des couleurs primaires (RGB) **/
	private int etendueRGB = 256;
	/** Opacité de la couleur des portails **/
	private int opacitePortails = 255;

	/**
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);
	/** Booléen qui indique l'état du mode de rotation post-placement **/
	private boolean rotationPostPlacement = false;
	/** Booléen qui indique l'état du déplacement d'une tuile unique **/
	private boolean deplacementTuileUnique = false;

	/**
	 * Voici la méthode qui permettra à un objet de s'ajouter en tant qu'écouteur
	 * 
	 * @param listener L'objet PropertyChangeListener à ajouter comme écouteur de
	 *                 propriété.
	 */
	// Kitimir Yim
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}

	/**
	 * Création du panneau
	 */
	// Giroux
	public Grille() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				gererTouchesClavierRelachees(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				gererTouchesClavierEnfoncees(e);
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				demanderFocusLevee();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if (modeEditeur) {
					exterieurComposant = true;
				}
			}// fin mouseExited

			@Override
			public void mouseEntered(MouseEvent e) {
				if (modeEditeur) {
					exterieurComposant = false;

				}
			}// fin mouseEntered

			@Override
			public void mousePressed(MouseEvent e) {
				gererSourisPesee(e);

			}// fin mousePressed

			@Override
			public void mouseReleased(MouseEvent e) {
				gererSourisRelachee(e);

			}
		});

		setLayout(null);
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				gererCurseur();
				transformerCoordonneesSouris(e);
				positionnerCaseEtTuile();
				repaint();

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (rotationPostPlacement) {
				} else {
					placerTuile(e);
				}
			}
		});
	}// Fin constructeur

	/**
	 * Permet de transformer les coordonnées de la souris en fonction des
	 * transformations du paintComponent: Soit, en mètre et avec l'origine en bas à
	 * gauche
	 */
	// Enuel René Valentin Kizozo Izia
	private void transformerCoordonneesSouris(MouseEvent e) {
		clique = e.getPoint();
		sourisEnMetreX = e.getX() / pixelsParMetre;
		sourisEnMetreY = hauteurDuComposantEnMetre - e.getY() / pixelsParMetre;
		clique.setLocation(sourisEnMetreX, sourisEnMetreY);
	}

	/**
	 * Gère les touches du clavier lorsqu'elles sont enfoncées
	 * 
	 * @param e l'évènement du clavier
	 */
	// Jason Xa
	private void gererTouchesClavierEnfoncees(KeyEvent e) {
		int code = e.getKeyCode();

		if (e.isControlDown() && code == KeyEvent.VK_S) {
			PCS.firePropertyChange("Sauvegarder", null, null);
		} else if (e.isControlDown() && code == KeyEvent.VK_R) {
			PCS.firePropertyChange("Réinitialiser", null, null);
		} else if (e.isControlDown() && code == KeyEvent.VK_E) {
			PCS.firePropertyChange("Essayer le niveau", null, null);
		} else {
			switch (code) {
			case KeyEvent.VK_Q:
				PCS.firePropertyChange("Sélectionner carré", null, null);
				break;
			case KeyEvent.VK_W:
				PCS.firePropertyChange("Sélectionner triangle rectangle", null, null);
				break;
			case KeyEvent.VK_E:
				PCS.firePropertyChange("Sélectionner triangle équilatéral", null, null);
				break;
			case KeyEvent.VK_A:
				PCS.firePropertyChange("Sélectionner pics", null, null);
				break;
			case KeyEvent.VK_S:
				PCS.firePropertyChange("Sélectionner portail", null, null);
				break;
			case KeyEvent.VK_D:
				if (!contientDrapeau()) {
					PCS.firePropertyChange("Sélectionner drapeau", null, null);
				}
				break;
			case KeyEvent.VK_F:
				if (!contientVaisseau()) {
					PCS.firePropertyChange("Sélectionner vaisseau", null, null);
				}
				break;

			}
		}
	}

	/**
	 * Gère les évènements de la souris lorsqu'elle est relâchée
	 * 
	 * @param e l'évènement de la souris
	 */
	// Jason Xa
	private void gererSourisRelachee(MouseEvent e) {
		transformerCoordonneesSouris(e);
		switch (e.getButton()) {
		case MouseEvent.BUTTON3:
			supprimer = false;
			positionnerCaseEtTuile();
			break;
		}
	}

	/**
	 * Permet de placer un tuile dans la grille
	 */
	// Giroux
	private void placerTuile(MouseEvent e) {
		transformerCoordonneesSouris(e);
		positionnerCaseEtTuile();
		if (!supprimer) {
			if (tuile != null) {
				sauvegarderEmplacement();

			}
		} else {
			supprimerCase();
		}

		repaint();

	}

	/**
	 * Méthode qui appelle le dessin de la grille et de ses composantes
	 * 
	 * @param g Contexte graphique
	 */
	// Giroux
	public void paintComponent(Graphics g) {
		if (modeEditeur) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			dessiner(g2d);
		}

	}// Fin méthode

	/**
	 * Méthode qui dessine la grille et ses composantes
	 * 
	 * @param g2d Contexte graphique
	 * 
	 */
	// Giroux
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		if (modeEditeur) {
			g2dPrive.translate(0, getHeight());
			g2dPrive.scale(1, -1);

			if (premiereFois) {
				pixelsParMetre = getWidth() / largeurDuComposantEnMetre;
				hauteurDuComposantEnMetre = getHeight() / pixelsParMetre;
				dimensionCase();
				dessinerQuadrillage();

				tabEmplacement = new Tuile[nbCaseVerticale][nbCaseHorizontale];
				definirDimensionsTuilesEtlireImages();

				premiereFois = false;
			}

			// Transformations pour être en mètre
			g2dPrive.scale(pixelsParMetre, pixelsParMetre);
			g2dPrive.setStroke(new BasicStroke((float) (1 / pixelsParMetre)));

//			if (supprimer) {
//				setBackground(Color.red);
//			} else {
			setBackground(new Color(255, 255, 128));
//			}

			if (supprimer) {
				g2dPrive.setColor(new Color(255, 0, 0, 100));
			} else if (placePrise && !supprimer) {
				g2dPrive.setColor(new Color(255, 200, 0, 100));
			} else /* if (!supprimer) */ {
				g2dPrive.setColor(new Color(0, 255, 255, 100));
			}

			dessinerLesTuiles(g2dPrive);
			dessinerTuileLorsSurvol(g2dPrive);

			if (grille) {
				g2dPrive.setColor(Color.black);
				g2dPrive.draw(quadHori);
				g2dPrive.draw(quadVerti);
			}
		}
	}// Fin méthode

	/**
	 * Définit les dimensions des tuiles et lit leur image
	 */
	// Enuel René Valentin Kizozo Izia
	private void definirDimensionsTuilesEtlireImages() {
		Tuile.setLargeurTuile(largeurCase);
		Tuile.setHauteurTuile(hauteurCase);
		OutilsImage.lireImagesDesTuiles(largeurCase, hauteurCase, pixelsParMetre);
	}

	/**
	 * Méthode qui détermine la grandeur de chaque case et qui crée le case
	 * conrespondant
	 */
	// Giroux
	private void dimensionCase() {
		largeurCase = (largeurDuComposantEnMetre / nbCaseHorizontale);
		hauteurCase = largeurCase;
		nbCaseVerticale = (int) (hauteurDuComposantEnMetre / hauteurCase);

		emplacementActuel = new Rectangle2D.Double(0, 0, largeurCase, hauteurCase);
	}// Fin méthode

	/**
	 * Méthode qui positionne la tuile et son fond bleu à l'emplacement de la souris
	 * passée en paramètre
	 */
	// Giroux
	private void positionnerCaseEtTuile() {

		for (int i = 0; i < nbCaseVerticale; i++) {
			if (sourisEnMetreY >= i * hauteurCase && sourisEnMetreY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (sourisEnMetreX >= j * largeurCase && sourisEnMetreX < ((j + 1) * largeurCase)) {
						emplacementActuel.setFrame(largeurCase * j, hauteurCase * i, largeurCase, hauteurCase);
						if (!supprimer && tuile != null) {
							// tuile.redimensionnerImage((int) hauteurCase, (int) largeurCase);
							tuile.setX(largeurCase * j);
							tuile.setY(hauteurCase * i);
						}
						if (tabEmplacement[i][j] != null) {
							placePrise = true;
						} else {
							placePrise = false;
						}
						// System.out.println("Ligne: " + (i + 1) + " Col: " + (j + 1));
					}
				}

			}

		}

	}// Fin méthode

	/**
	 * Méthode qui positionne la tuile et son fond bleu à l'emplacement de la souris
	 * passée en paramètre
	 */
	// Giroux
	private void positionnerCaseEtTuile(Tuile tuile) {

		for (int i = 0; i < nbCaseVerticale; i++) {
			if (sourisEnMetreY >= i * hauteurCase && sourisEnMetreY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (sourisEnMetreX >= j * largeurCase && sourisEnMetreX < ((j + 1) * largeurCase)) {
						emplacementActuel.setFrame(largeurCase * j, hauteurCase * i, largeurCase, hauteurCase);
						if (!supprimer && tuile != null) {
							// tuile.redimensionnerImage((int) hauteurCase, (int) largeurCase);
							tuile.setX(largeurCase * j);
							tuile.setY(hauteurCase * i);
						}
						if (tabEmplacement[i][j] != null) {
							placePrise = true;
						} else {
							placePrise = false;
						}
						// System.out.println("Ligne: " + (i + 1) + " Col: " + (j + 1));
					}
				}

			}

		}

	}// Fin méthode

	/**
	 * Méthode qui dessine le quadrillage de la grille
	 */
	// Giroux
	private void dessinerQuadrillage() {
		quadVerti = new Path2D.Double();
		quadHori = new Path2D.Double();
		for (int i = 0; i < nbCaseVerticale + 1; i++) {
			quadHori.moveTo(0, i * hauteurCase);
			quadHori.lineTo(largeurDuComposantEnMetre, i * hauteurCase);
			for (int j = 0; j < nbCaseHorizontale + 1; j++) {
				quadVerti.moveTo(j * largeurCase, 0);
				quadVerti.lineTo(j * largeurCase, hauteurDuComposantEnMetre);
			}

		}
	}// Fin méthode

	/**
	 * Méthode qui fait afficher la grille si elle n'y est pas, ou l'enlève si elle
	 * y est
	 */
	// Giroux
	public void afficherGrille() {
		grille = !grille;
		repaint();

	}// Fin méthode

	/**
	 * Méthode qui fait afficher la grille si elle n'y est pas, ou l'enlève si elle
	 * y est
	 * 
	 * @param condition vrai si l'on désire afficher le quadrillage
	 */
	// Jason Xa
	public void afficherGrille(boolean condition) {
		grille = condition;
		repaint();

	}// Fin méthode

	/**
	 * Méthode qui change le nombre de case par ligne (verticale)
	 * 
	 * @param nouvNbCase Le nouveau nombre de case par ligne
	 */
	// Giroux
	public void changerQttCaseVerticale(int nouvNbCase) {
		this.nbCaseVerticale = nouvNbCase;
		premiereFois = true;
		drapeau = false;
		vaisseau = false;
		repaint();
	} // Fin méthode

	/**
	 * Méthode qui change la valeur dans le tableau dépendant de si l'emplacement
	 * est libre 1 si non libre et 0 si libre
	 */
	// Giroux
	private void sauvegarderEmplacement() {
		for (int i = 0; i < nbCaseVerticale; i++) {
			if (sourisEnMetreY >= i * hauteurCase && sourisEnMetreY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (sourisEnMetreX >= j * largeurCase && sourisEnMetreX < ((j + 1) * largeurCase)) {
						clonerTuile();

						if ((tuileTemp.getDrapeau() && drapeau) || (tuileTemp.getVaisseau() && vaisseau)) {

							break;
						}
						tuileTemp.setX(largeurCase * j);
						tuileTemp.setY(hauteurCase * i);

						if (tabEmplacement[i][j] == null) {
							tabEmplacement[i][j] = tuileTemp;
							tuileTemp.setPoint();
							gererPortails();
							afficherTab();

							if (tuileTemp.getDrapeau() && !drapeau) {
								drapeau = true;
								tuile = null;
								PCS.firePropertyChange("Drapeau", null, false);
							} else if (tuileTemp.getVaisseau() && !vaisseau) {
								vaisseau = true;
								tuile = null;
								PCS.firePropertyChange("Vaisseau", null, false);
							}

						} else {
							System.out.println("Cet emplacement possède déjà un bloc");

						}
						System.out.println("Vous avez cliqué sur la col: " + (j + 1) + " et la ligne: " + (i + 1));
						System.out.println("Vous avez cliqué sur la col: " + (j) + " et la ligne: " + (i));
					}
				}

			}

		}
	}// Fin méthode

	/**
	 * Clone la tuile sélectionnée selon son type
	 */
	// Jason Xa
	private void clonerTuile() {
		switch (tuile.getType()) {
		case "Carré":
			tuileTemp = new Carre(tuile.getAngleRotation());
			break;
		case "Drapeau":
			tuileTemp = new Drapeau(tuile.getAngleRotation());
//			tuile = new Carre(tuile.getAngleRotation(), tuile.getX(), tuile.getY());

			break;
		case "Pics":
			tuileTemp = new Pics(tuile.getAngleRotation());
			break;
		case "Portail":
			tuileTemp = new Portail(tuile.getAngleRotation());
			break;
		case "Triangle équilatéral":
			tuileTemp = new TriangleEquilateral(tuile.getAngleRotation());
			break;
		case "Triangle rectangle":
			tuileTemp = new TriangleRectangle(tuile.getAngleRotation());
			break;
		case "Vaisseau":
			tuileTemp = new VaisseauImage(tuile.getAngleRotation());
//			tuile = new Carre(tuile.getAngleRotation(), tuile.getX(), tuile.getY());

			break;
		}

	}

	/**
	 * Ajoute un portail au compteur et le lie portail placé précédemment, s'il y a
	 * lieu
	 */
	// Kitimir Yim
	private void gererPortails() {
		if (tuileTemp.getType() == "Portail") {
			nbPortails++;
			lierPortail(tuileTemp);
		}
	}

	/**
	 * À fin de test, non permanent, imprime le tableau des emplacements
	 */
	// Giroux
	private void afficherTab() {
		for (int i = 0; i < nbCaseVerticale; i++) {
			System.out.print("\n");
			for (int j = 0; j < nbCaseHorizontale; j++) {
				if (tabEmplacement[i][j] == null) {
					System.out.print(tabEmplacement[i][j]);
				} else {
					System.out.print(tabEmplacement[i][j].toString());
				}

			}
		}
		System.out.print("\n\n");
	}// Fin méthode

	/**
	 * Méthode qui déssine les tuiles contenues dans le tableau des emplacements à
	 * la bonne place, lors qu'on clique sur la grille
	 * 
	 * @param g2d contexte graphique
	 */
	// Giroux
	public void dessinerLesTuiles(Graphics2D g2d) {
		for (int i = 0; i < nbCaseVerticale; i++) {
			for (int j = 0; j < nbCaseHorizontale; j++) {
				tuileTableau = tabEmplacement[i][j];
				if (tuileTableau != null) {
					if (!(dansModeJeu & tuileTableau.getType().equals("Vaisseau"))) {
						tuileTableau.dessiner(g2d);
					}
				}
			}
		}
		repaint();
	}// Fin méthode

	/**
	 * Méthode qui dessine la tuile et son fond bleu lors du survol de la grille
	 * avec la souris
	 * 
	 * @param g2d Le contexte graphique
	 */
	// Giroux
	public void dessinerTuileLorsSurvol(Graphics2D g2d) {
		if (!exterieurComposant) {
			g2d.fill(emplacementActuel);
			if (tuile != null && !supprimer && !rotationPostPlacement) {
				tuile.dessiner(g2d);
			}
		}
	}

	/**
	 * Méthode qui retourne la quantité de case verticale dans la grille
	 * 
	 * @return La quantité de case horizontale dans la grille
	 */
	// Giroux
	public int getNbCaseVerticale() {
		return nbCaseVerticale;
	}// Fin méthode

	/**
	 * Permet de réinitialiser le tableau
	 */
	// Giroux
	public void reinitialiser() {
		for (int i = 0; i < nbCaseVerticale; i++) {
			for (int j = 0; j < nbCaseHorizontale; j++) {
				tabEmplacement[i][j] = null;
			}
		}
		drapeau = false;
		vaisseau = false;
		nbPortails = 0;
		repaint();

	}

	/**
	 * Gère la condition de suppression
	 */
	// Giroux
	public void gererSupprimer() {
		supprimer = !supprimer;
		positionnerCaseEtTuile();
		repaint();
	}


	/**
	 * Permet de supprimer une tuile précise
	 */
	// Giroux
	public void supprimerCase() {
		for (int i = 0; i < nbCaseVerticale; i++) {
			if (sourisEnMetreY >= i * hauteurCase && sourisEnMetreY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (sourisEnMetreX >= j * largeurCase && sourisEnMetreX < ((j + 1) * largeurCase)) {

						if (tabEmplacement[i][j] == null) {
							break;

						} else if (tabEmplacement[i][j].getVaisseau()) {
							vaisseau = false;
							PCS.firePropertyChange("Vaisseau", null, true);

						} else if (tabEmplacement[i][j].getDrapeau()) {
							drapeau = false;
							PCS.firePropertyChange("Drapeau", null, true);

						}

						supprimerPortailAssocie(i, j);
						tabEmplacement[i][j] = null;

					}
				}
			}
		}

	}

	/**
	 * Permet de supprimer, s'il y a lieu, le portail associé à celui qui vient
	 * d'être supprimé
	 * 
	 * @param i Le numéro de la ligne courante dans la boucle for
	 * @param j Le numéro de la colonne courante dans la boucle for
	 */
	// Enuel René Valentin Kizozo Izia
	private void supprimerPortailAssocie(int i, int j) {
		// Vérifie que la tuile courante est un portail
		if (tabEmplacement[i][j].getType() == "Portail") {
			nbPortails--;
			Portail portail = (Portail) tabEmplacement[i][j];
			Portail portailAssocie = portail.getPortailAssocie();

			// Vérifie qu'il y ait un portail associé
			if (portailAssocie != null) {

				// Boucle pour passer au travers de toutes les tuiles
				for (int m = 0; m < nbCaseVerticale; m++) {
					for (int n = 0; n < nbCaseHorizontale; n++) {

						// Vérifie qu'il y ait une tuile
						if (tabEmplacement[m][n] != null) {

							// Vérifie si la tuile courante correspond au portail associé
							if (tabEmplacement[m][n].equals(portailAssocie)) {
								tabEmplacement[m][n] = null;
								nbPortails--;
								// portail.setPortailAssocie(null);
							} // fin 4e condition
						} // fin 3e condition
					} // fin 2e boucle
				} // fin 1er boucle
			} // fin 2e condition
		} // fin 1ere condition
	}

	/**
	 * Permet d'avoir l'emplacement des tuiles
	 * 
	 * @return L'emplacement des tuiles
	 */
	// Giroux
	public Tuile[][] getTableau() {
		return tabEmplacement;
	}

	/**
	 * Permet de changer le tableau
	 * 
	 * @param tab tableau des tuiles
	 */
	// Kitimir Yim
	public void setTableau(Tuile[][] tab) {
		this.tabEmplacement = tab;

	}

	/**
	 * Retourne la tuile sélectionnée
	 * 
	 * @return la tuile sélectionnée
	 */
	// Jason Xa
	public Tuile getTuile() {
		return tuile;
	}

	/**
	 * Définit le type de tuile sélectionné pour le placement
	 * 
	 * @param tuile le nouveau type de tuile sélectionné pour le placement
	 */
	// Jason Xa
	public void setTuile(Tuile tuile) {
		positionnerCaseEtTuile(tuile);
		this.tuile = tuile;
	}

	/**
	 * Retourne la hauteur du composant en mètre
	 * 
	 * @return La hauteur du composant en mètre
	 */
	// Enuel René Valentin Kizozo Izia
	public double getHauteurComposantEnMetre() {
		return hauteurDuComposantEnMetre;
	}

	/**
	 * Retourne la largeur du composant en mètre
	 * 
	 * @return La largeur du composant en mètre
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLargeurComposantEnMetre() {
		return largeurDuComposantEnMetre;
	}

	/**
	 * Retourne la hauteur d'une case (et donc d'une tuile)
	 * 
	 * @return La hauteur d'une case
	 */
	// Enuel René Valentin Kizozo Izia
	public double getHauteurCase() {
		return hauteurCase;
	}

	/**
	 * Retourne la largeur d'une case (et donc d'une tuile)
	 * 
	 * @return La largeur d'une case
	 */
	// Enuel René Valentin Kizozo Izia
	public double getLargeurCase() {
		return largeurCase;
	}

	/**
	 * Applique un quart de rotation horaire à la tuile sélectionnée selon le signe
	 * de l'argument
	 * 
	 * @param facteur Facteur scalaire déterminant l'angle et le sens de rotation
	 *                appliqué à un facteur de 16e de rotation
	 * 
	 */
	// Jason Xa
	public void rotation(int facteur) {
		if (tuile != null && tuile.getType() != "Vaisseau") {
			tuile.setAngleRotation(tuile.getAngleRotation() + facteur * 0.125 * Math.PI); // 1/16 de rotation
			repaint();
		}
	}

	/**
	 * Définit la condition de suppression
	 * 
	 * @param supprimer la nouvelle valeau de la condition de suppression
	 */
	// Jason Xa
	public void setSupprimer(boolean supprimer) {
		this.supprimer = supprimer;
	}

	/**
	 * Modifie la condition indiquant si l'on est dans le mode jeu En établissant
	 * qu'on est dans le mode jeu, la grille ne dessinera pas la tuile du vaisseau,
	 * donc son image. L'image du vaisseau le sera quand on dessinera le vaisseau
	 * (individuellement)
	 * 
	 * @param dansModeJeu La nouvelle valeur du booléan indiquant si l'on est dans
	 *                    le mode jeu
	 */
	// Enuel René Valentin Kizozo Izia
	public void setDansModeJeu(boolean dansModeJeu) {
		this.dansModeJeu = dansModeJeu;
	}

	/**
	 * Vérifie si la grille contient au moins une tuile du type spécifié.
	 * 
	 * @param typeTuile le type de tuile à rechercher dans la grille
	 * @return true si au moins une tuile du type spécifié est présente, sinon false
	 */
	// Kitimir Yim
	public boolean contientTuile(Class<?> typeTuile) {
		for (int i = 0; i < nbCaseVerticale; i++) {
			for (int j = 0; j < nbCaseHorizontale; j++) {
				if (tabEmplacement[i][j] != null && typeTuile.isInstance(tabEmplacement[i][j])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Recherche et retourne la première tuile du type spécifié dans la grille.
	 * 
	 * @param typeTuile le type de tuile à rechercher dans la grille
	 * @return la tuile du type spécifié, ou null si aucune tuile n'est trouvée
	 */
	// Kitimir Yim
	public Tuile chercherTuile(Class<?> typeTuile) {
		for (int i = 0; i < nbCaseVerticale; i++) {
			for (int j = 0; j < nbCaseHorizontale; j++) {
				if (tabEmplacement[i][j] != null && typeTuile.isInstance(tabEmplacement[i][j])) {
					return tabEmplacement[i][j];
				}
			}
		}
		return null;
	}

	/**
	 * Lie un portail si nécessaire
	 * 
	 * @param tuile L'autre tuile (contenant un portail) à laquelle lier un portail
	 */
	// Kitimir Yim
	private void lierPortail(Tuile tuile) {

		if (nbPortails % 2 == 0) {
			Portail deuxiemePortail = (Portail) tuile;
			if (premierPortail.getPortailAssocie() == null) {
				premierPortail.setPortailAssocie(deuxiemePortail);
				deuxiemePortail.setPortailAssocie(premierPortail);
				deuxiemePortail.setCouleur(premierPortail.getCouleur());
				System.out.println("Ce portail a maintenant un duo");
			}
		} else {
			System.out.println("Ce portail n'a pas de duo. N'oubliez pas de lui créer un partenaire.");
			premierPortail = (Portail) tuile;
			premierPortail.setCouleur(genererCouleurPortail());

		}
	}

	/**
	 * Permet de générer une couleur aléatoire pour un portail
	 */
	// Enuel René Valentin Kizozo Izia
	private Color genererCouleurPortail() {
        Random random = new Random();
        int rouge = random.nextInt(etendueRGB);
    	int vert = random.nextInt(etendueRGB);
    	int bleu = random.nextInt(etendueRGB);
        return new Color(rouge, vert, bleu, opacitePortails);
	}

	/**
	 * Retourne vrai si la grille contient un vaisseau
	 * 
	 * @return vrai si la grille contient un vaisseau
	 */
	// Jason Xa
	public boolean contientVaisseau() {
		boolean vaisseauPresent = false;
		VaisseauImage instanceVaisseau = new VaisseauImage();

		// Traverse le tableau au complet
		for (int i = 0; i < tabEmplacement.length; i++) {
			for (int j = 0; j < tabEmplacement[i].length; j++) {

				// Dès que le booléen est vrai, il ne changera jamais (1 ou [la tuile est un
				// vaisseau]). Enlève le besoin de vérifier si la tuile est une instance de
				// VaisseauImage lorsque la grille contient déjà un vaisseau.
				vaisseauPresent = vaisseauPresent || instanceVaisseau.getClass().isInstance(tabEmplacement[i][j]);
			}
		}
		return vaisseauPresent;
	}

	/**
	 * Retourne vrai si la grille contient un drapeau d'arrivée
	 * 
	 * @return vrai si la grille contient un drapeau d'arrivée
	 */
	// Jason Xa
	public boolean contientDrapeau() {
		boolean drapeauPresent = false;
		Drapeau instanceDrapeau = new Drapeau();

		// Traverse le tableau au complet
		for (int i = 0; i < tabEmplacement.length; i++) {
			for (int j = 0; j < tabEmplacement[i].length; j++) {

				// Dès que le booléen est vrai, il ne changera jamais (1 ou [la tuile est un
				// vaisseau]). Enlève le besoin de vérifier si la tuile est une instance de
				// Drapeau lorsque la grille contient déjà un vaisseau.
				drapeauPresent = drapeauPresent || instanceDrapeau.getClass().isInstance(tabEmplacement[i][j]);
			}
		}
		return drapeauPresent;
	}

	/**
	 * Retourne vrai si la grille ne contient que des portails liés Donc qu'il y a
	 * un nombre pair de portails
	 */
	// Enuel René Valentin Kizozo Izia
	public boolean portailsTousLies() {
		return (nbPortails%2 == 0);
	}

	/**
	 * Méthode qui permet de rotationner une tuile déjà placée
	 */
	// Giroux
	public void rotationPostPlacement() {
		for (int i = 0; i < nbCaseVerticale; i++) {
			if (sourisEnMetreY >= i * hauteurCase && sourisEnMetreY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (sourisEnMetreX >= j * largeurCase && sourisEnMetreX < ((j + 1) * largeurCase)) {

						if (tabEmplacement[i][j] == null || tabEmplacement[i][j].getVaisseau()) {
							break;

						} else {
							tabEmplacement[i][j]
									.setAngleRotation(tabEmplacement[i][j].getAngleRotation() + 0.5 * Math.PI);
							repaint();
						}

					}
				}
			}
		}

	}

	/**
	 * Méthode qui indique si en mode rotation ou non
	 * 
	 * @return Vrai si en rotation, ou faux dans le cas contraire
	 */
	// Giroux
	public boolean getRotationPostPlacement() {
		return rotationPostPlacement;
	}
	
	/**
	 * Méthode qui modifie l'état de la grille, met en mode rotation ou l'enlève
	 */
	// Giroux
	public void setRotationPostPlacement() {
		rotationPostPlacement = !rotationPostPlacement;
	}

	/**
	 * Méthode qui modifie l'état de la grille, met en mode rotation ou l'enlève
	 * 
	 * @param rotationPostPlacement vrai si la rotation post-placement est activée
	 */
	// Jason Xa
	public void setRotationPostPlacement(boolean rotationPostPlacement) {
		this.rotationPostPlacement = rotationPostPlacement;
	}

	/**
	 * Lance un évènement pour demander le focus
	 */
	// Jason Xa
	private void demanderFocusLevee() {
		PCS.firePropertyChange("FocusGrille", null, null);
	}

	/**
	 * Gère les différentes touches de la souris lorsqu'elles sont pesées
	 * 
	 * @param e l'évènement de souris
	 */
	// Jason Xa
	private void gererSourisPesee(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON3:
			supprimer = true;
			placerTuile(e);
			break;
		case MouseEvent.BUTTON1:
			if (!supprimer) {
				transformerCoordonneesSouris(e);
				reinitialiseStatutTuileUnique();
			}
			if (deplacementTuileUnique) {
				deplacementTuileUnique = false;
			} else {
				if (rotationPostPlacement) {
					transformerCoordonneesSouris(e);
					rotationPostPlacement();
				} else {
					placerTuile(e);
				}
			}
			break;
		}
		gererCurseur();
	}

	/**
	 * Gère les évènements au clavier (touche relâchée)
	 * 
	 * @param e l'évènement du clavier
	 */
	// Jason Xa
	private void gererTouchesClavierRelachees(KeyEvent e) {
		int code = e.getKeyCode();

		switch (code) {
		case KeyEvent.VK_SPACE:
			gererSupprimer();
			break;
		case KeyEvent.VK_R:
			PCS.firePropertyChange("Rotation pré-placement", null, null);
			break;
		case KeyEvent.VK_T:
			PCS.firePropertyChange("Rotation post-placement", null, null);
			break;
		}
		gererCurseur();
	}

	/**
	 * Gère la forme du curseur
	 */
	// Giroux
	private void gererCurseur() {
		if (supprimer) {
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (rotationPostPlacement) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage("ressources/rotationPostPlacementVert.png");
//			Point pointMilieuImage = new Point((int)(image.getWidth(null)/2),(int) (image.getHeight(null)/2));
			Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "Le curseur");
			setCursor(c);

		} else {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * Méthode qui permet de déplacer les tuiles uniques en réinitialisant leurs
	 * conditions pour repermettre le déplacement
	 */
	// Giroux
	private void reinitialiseStatutTuileUnique() {
		for (int i = 0; i < nbCaseVerticale; i++) {
			if (sourisEnMetreY >= i * hauteurCase && sourisEnMetreY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (sourisEnMetreX >= j * largeurCase && sourisEnMetreX < ((j + 1) * largeurCase)) {
						if (tabEmplacement[i][j] != null && !rotationPostPlacement) {
							if (tabEmplacement[i][j].getDrapeau() && drapeau) {
								deplacementTuileUnique = true;
								drapeau = false;
								tuileTemp = new Drapeau(tabEmplacement[i][j].getAngleRotation());
								setTuile(new Drapeau(tabEmplacement[i][j].getAngleRotation()));
								tabEmplacement[i][j] = null;
							} else if (tabEmplacement[i][j].getVaisseau() && vaisseau) {

								deplacementTuileUnique = true;
								vaisseau = false;
								tuileTemp = new Drapeau(tabEmplacement[i][j].getAngleRotation());
								setTuile(new VaisseauImage(tabEmplacement[i][j].getAngleRotation()));
								tabEmplacement[i][j] = null;
							}
						}

					}
				}
			}
		}

	}

	/**
	 * Associe une nouvelle valeur qui dit vrai si la souris est à l'extérieur de la
	 * grille
	 * 
	 * @param exterieurComposant vrai si la souris est à l'extérieur de la grille
	 */
	// Jason Xa
	public void setExterieurComposant(boolean exterieurComposant) {
		this.exterieurComposant = exterieurComposant;
	}

}
// Fin classe
