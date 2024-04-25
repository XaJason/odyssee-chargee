package dessin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

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

	/**
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);

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
				placerTuile(e);
			}// fin mousePressed
		});

		setLayout(null);
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (modeEditeur) {
					if (supprimer) {
						setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
					} else {
						setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					positionnerCaseEtTuile(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre);
					repaint();
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				placerTuile(e);
			}
		});
	}// Fin constructeur

	/**
	 * Permet de placer un tuile dans la grille
	 */
	// Giroux
	private void placerTuile(MouseEvent e) {
		clique = e.getPoint();
		positionnerCaseEtTuile(e.getX() / pixelsParMetre, e.getY() / pixelsParMetre);
		if (!supprimer) {
			if (tuile != null) {
				sauvegarderEmplacement();
			}
		} else {
			supprimerCase();
		}
		afficherTab();

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
//			g2d.translate(0, getHeight());
//			g2d.scale(1, -1);

			if (premiereFois) {
				pixelsParMetre = getWidth() / largeurDuComposantEnMetre;
				hauteurDuComposantEnMetre = getHeight() / pixelsParMetre;
				dimensionCase();
				dessinerQuadrillage();

				tabEmplacement = new Tuile[nbCaseVerticale][nbCaseHorizontale];
				Tuile.setLargeurTuile(largeurCase);
				Tuile.setHauteurTuile(hauteurCase);

				Carre.setImageRef("carre.jpg", (int) (largeurCase * pixelsParMetre),
						(int) (hauteurCase * pixelsParMetre));
				TriangleEquilateral.setImageRef("triangle_equilateral.png", (int) (largeurCase * pixelsParMetre),
						(int) (hauteurCase * pixelsParMetre));
				TriangleRectangle.setImageRef("triangle_rectangle.png", (int) (largeurCase * pixelsParMetre),
						(int) (hauteurCase * pixelsParMetre));
				Portail.setImageRef("portail.png", (int) (largeurCase * pixelsParMetre),
						(int) (hauteurCase * pixelsParMetre));
				Drapeau.setImageRef("drapeau.png", (int) (largeurCase * pixelsParMetre),
						(int) (hauteurCase * pixelsParMetre));
				Pics.setImageRef("pics.png", (int) (largeurCase * pixelsParMetre),
						(int) ((hauteurCase / 2.0) * pixelsParMetre));
				VaisseauImage.setImageRef("vaisseau.png", (int) ((largeurCase / 2.0) * pixelsParMetre),
						(int) ((hauteurCase / 2.0) * pixelsParMetre));

				premiereFois = false;
			}

			// Transformations pour être en mètre
			g2dPrive.scale(pixelsParMetre, pixelsParMetre);
			g2dPrive.setStroke(new BasicStroke((float) (1 / pixelsParMetre)));

			if (supprimer) {
				setBackground(Color.red);
			} else {
				setBackground(new Color(255, 255, 128));
			}

			if (placePrise && !supprimer) {
				g2dPrive.setColor(Color.orange);
			} else if (!supprimer) {
				g2dPrive.setColor(Color.cyan);
			}

			dessinerTuileLorsSurvol(g2dPrive);
			dessinerLesTuiles(g2dPrive);

			g2dPrive.setColor(Color.black);
			if (grille) {
				g2dPrive.setColor(Color.black);
				g2dPrive.draw(quadHori);
				g2dPrive.draw(quadVerti);
			}
		}
	}// Fin méthode

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
	 * 
	 * @param posX Position x de l'emplacement
	 * @param posY Position y de l'emplacement
	 */
	// Giroux
	private void positionnerCaseEtTuile(double posX, double posY) {

		for (int i = 0; i < nbCaseVerticale; i++) {
			if (posY >= i * hauteurCase && posY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (posX >= j * largeurCase && posX < ((j + 1) * largeurCase)) {
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
		if (grille == true) {
			grille = false;
		} else {
			grille = true;
		}
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
			if (clique.getY() / pixelsParMetre >= i * hauteurCase
					&& clique.getY() / pixelsParMetre < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (clique.getX() / pixelsParMetre >= j * largeurCase
							&& clique.getX() / pixelsParMetre < ((j + 1) * largeurCase)) {
						clonerTuile();

						if ((tuileTemp.getDrapeau() && drapeau) || (tuileTemp.getVaisseau() && vaisseau)) {

							break;
						}
						tuileTemp.setX(largeurCase * j);
						tuileTemp.setY(hauteurCase * i);

						if (tabEmplacement[i][j] == null) {
							tabEmplacement[i][j] = tuileTemp;
							tuileTemp.setPoint();

							if (tuileTemp.getDrapeau() && !drapeau) {
								drapeau = true;
							} else if (tuileTemp.getVaisseau() && !vaisseau) {
								vaisseau = true;
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
			tuile = new Carre(tuile.getAngleRotation(), tuile.getX(), tuile.getY());
			PCS.firePropertyChange("Drapeau", null, false);

			break;
		case "Pics":
			tuileTemp = new Pics(tuile.getAngleRotation());
			break;
		case "Portail":
			tuileTemp = new Portail(tuile.getAngleRotation());
			gererPortails();
			break;
		case "Triangle équilatéral":
			tuileTemp = new TriangleEquilateral(tuile.getAngleRotation());
			break;
		case "Triangle rectangle":
			tuileTemp = new TriangleRectangle(tuile.getAngleRotation());
			break;
		case "Vaisseau":
			tuileTemp = new VaisseauImage(tuile.getAngleRotation());
			tuile = new Carre(tuile.getAngleRotation(), tuile.getX(), tuile.getY());
			PCS.firePropertyChange("Vaisseau", null, false);
			break;
		}

	}

	/**
	 * Gére les portails
	 */
	// Kitimir Yim
	private void gererPortails() {
		nbPortails++;
		lierPortail(tuileTemp);
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
			if (tuile != null && !supprimer) {
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
		repaint();

	}

	/**
	 * Gère la condition de suppression
	 */
	// Giroux
	public void gererSupprimer() {
		if (supprimer == false) {
			supprimer = true;
		} else {
			supprimer = false;
		}
		repaint();
	}

	/**
	 * Permet de supprimer une tuile précise
	 */
	// Giroux
	public void supprimerCase() {
		for (int i = 0; i < nbCaseVerticale; i++) {
			if (clique.getY() / pixelsParMetre >= i * hauteurCase
					&& clique.getY() / pixelsParMetre < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCaseHorizontale; j++) {
					if (clique.getX() / pixelsParMetre >= j * largeurCase
							&& clique.getX() / pixelsParMetre < ((j + 1) * largeurCase)) {

						if (tabEmplacement[i][j] == null) {
							break;

						} else if (tabEmplacement[i][j].getVaisseau()) {
							vaisseau = false;
						} else if (tabEmplacement[i][j].getDrapeau()) {
							drapeau = false;

						}
						if (tabEmplacement[i][j].getType() == "Portail") {
							Portail portail = (Portail) tabEmplacement[i][j];
							Portail portailAssocie = portail.getPortailAssocie();
							portailAssocie = null;
						}
						tabEmplacement[i][j] = null;

					}
				}
			}
		}

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
	 * Applique un quart de rotation horaire à la tuile sélectionnée
	 */
	// Jason Xa
	public void rotation() {
		if (tuile != null) {
			tuile.setAngleRotation(tuile.getAngleRotation() + 0.5 * Math.PI);
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
	 * Modifie la condition indiquant si l'on est dans le mode jeu
	 * En établissant qu'on est dans le mode jeu, la grille ne dessinera pas la
	 * tuile du vaisseau, donc son image.
	 * L'image du vaisseau le sera quand on dessinera le vaisseau (individuellement)
	 * 
	 * @param dansModeJeu La nouvelle valeur du booléan indiquant si l'on est dans
	 *                    le mode jeu
	 */
	// Enuel René Valentin Kizozo Izia
	public void setDansModeJeu(boolean dansModeJeu) {
		this.dansModeJeu = dansModeJeu;
	}

	/**
	 * <<<<<<< HEAD
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
	 * =======
	 * >>>>>>> branch 'master' of https://gitlab.com/Kitimir/22_odysseechargee.git
	 * Lie un portail si nécessaire
	 * 
	 * @param tuile L'autre tuile (contenant un portail) à laquelle lier un portail
	 */
	// Kitimir Yim
	private void lierPortail(Tuile tuile) {

		if (nbPortails % 2 == 0) {
			Portail deuxiemePortail = (Portail) tuile;
			if (premierPortail.getPortailAssocie() == null) {
				premierPortail.definirPortailAssocie(deuxiemePortail);
				deuxiemePortail.definirPortailAssocie(premierPortail);
				System.out.println("Ce portail a maintenant un duo");
			}
		} else {
			System.out.println("Ce portail n'a pas de duo. N'oubliez pas de lui créer un partenaire.");
			premierPortail = (Portail) tuile;

		}
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

}
// Fin classe
