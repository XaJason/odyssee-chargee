package dessin;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
	private int hauteurDuComposantEnMetre;
	/** Largeur du composant en mètre **/
	private int largeurDuComposantEnMetre = 900;
	/** Hauteur de chaque case (en mètre) **/
	private int hauteurCase;
	/** Largeur du de chaque case (en mètre) **/
	private int largeurCase;
	/** Nombre de ligne et colonne. Ex: 3 donerait une grille 3x3 **/
	private int nbCase = 15;
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
	 * Tableau qui contient la tuile si la case est occupé ou null si elle est vide
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

	/**
	 * Création du panneau
	 */
	// Giroux
	public Grille() {

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clique = e.getPoint();
				if (!supprimer) {
					if (tuile != null) {

						sauvegarderEmplacement();

					}
				} else {
					supprimerCase();
				}
				afficherTab();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (modeEditeur) {
					exterieurComposant = true;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (modeEditeur) {
					exterieurComposant = false;
				}
			}
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
					dessinerCase(e.getX(), e.getY());
					repaint();
				}
				// dessinerCase(e.getX(), e.getY());
				// repaint();

			}
		});

	}// Fin constructeur

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
		if (modeEditeur) {
			if (premiereFois) {

				tabEmplacement = new Tuile[nbCase][nbCase];

				// pixelsParMetre = getWidth() / largeurDuComposantEnMetre;
				// hauteurDuComposantEnMetre = getHeight() / pixelsParMetre;

				hauteurDuComposantEnMetre = this.getHeight();
				largeurDuComposantEnMetre = this.getWidth();
				dimensionCase();
				dessinerQuadrillage();

				premiereFois = false;
			}
			if (supprimer) {
				setBackground(Color.red);
			} else {
				setBackground(new Color(255, 255, 128));
			}

			if (placePrise && !supprimer) {
				g2d.setColor(Color.orange);
			} else if (!supprimer) {
				g2d.setColor(Color.cyan);
			}
			if (!exterieurComposant) {
				g2d.fill(emplacementActuel);
			}
			if (tuile != null && !supprimer && !exterieurComposant) {
				tuile.dessiner(g2d);
			}

			dessinerTuile(g2d);

			g2d.setColor(Color.black);
			if (grille) {
				g2d.setColor(Color.black);
				g2d.draw(quadHori);
				g2d.draw(quadVerti);
			}
		}
	}// Fin méthode

	/**
	 * Méthode qui détermine la grandeur de chaque case et qui crée le case
	 * conrespondant
	 */
	// Giroux
	private void dimensionCase() {
		hauteurCase = (hauteurDuComposantEnMetre / nbCase);
		largeurCase = (largeurDuComposantEnMetre / nbCase);
		emplacementActuel = new Rectangle2D.Double(0, 0, largeurCase, hauteurCase);
		System.out.println(largeurCase);
		System.out.println(hauteurCase);

	}// Fin méthode

	/**
	 * Méthode qui dessine la tuile à l'emplacement passée en paramètre
	 * 
	 * @param posX Position x de l'emplacement
	 * @param posY Position y de l'emplacement
	 */
	// Giroux
	private void dessinerCase(double posX, double posY) {

		for (int i = 0; i < nbCase; i++) {
			if (posY >= i * hauteurCase && posY < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCase; j++) {
					if (posX >= j * largeurCase && posX < ((j + 1) * largeurCase)) {
						emplacementActuel.setFrame(largeurCase * j, hauteurCase * i, largeurCase, hauteurCase);
						if (!supprimer && tuile != null) {
							tuile.redimensionnerImage((int) hauteurCase, (int) largeurCase);
							tuile.setX((int) largeurCase * j);
							tuile.setY((int) hauteurCase * i);
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
		for (int i = 0; i < nbCase + 1; i++) {
			quadHori.moveTo(0, i * hauteurCase);
			quadHori.lineTo(largeurDuComposantEnMetre, i * hauteurCase);
			for (int j = 0; j < nbCase + 1; j++) {
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
	 * Méthode qui change le nombre de case par ligne
	 * 
	 * @param nouvNbCase Le nouveau nombre de case par ligne et colonne
	 */
	// Giroux
	public void changerQttCase(int nouvNbCase) {
		this.nbCase = nouvNbCase;
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
		for (int i = 0; i < nbCase; i++) {
			if (clique.getY() >= i * hauteurCase && clique.getY() < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCase; j++) {
					if (clique.getX() >= j * largeurCase && clique.getX() < ((j + 1) * largeurCase)) {
						clonerTuile();
						if ((tuileTemp.getDrapeau() && drapeau) || (tuileTemp.getVaisseau() && vaisseau)) {
							break;
						}

						tuileTemp.setX((int) largeurCase * j);
						tuileTemp.setY((int) hauteurCase * i);
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
			break;
		case "Pics":
			tuileTemp = new Pics(tuile.getAngleRotation());
			break;
		case "Portail":
			tuileTemp = new Portail(tuile.getAngleRotation());
			nbPortails++;
			lierPortail(tuileTemp);
			break;
		case "Triangle équilatéral":
			tuileTemp = new TriangleEquilateral(tuile.getAngleRotation());
			break;
		case "Triangle rectangle":
			tuileTemp = new TriangleRectangle(tuile.getAngleRotation());
			break;
		case "Vaisseau":
			tuileTemp = new VaisseauImage(tuile.getAngleRotation());
			break;
		}

	}

	/**
	 * À fin de test, non permanent, imprime le tableau des emplacements
	 */
	// Giroux
	private void afficherTab() {
		for (int i = 0; i < nbCase; i++) {
			System.out.print("\n");
			for (int j = 0; j < nbCase; j++) {
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
	 * la bonne place
	 * 
	 * @param g2d contexte graphique
	 */
	// Giroux
	public void dessinerTuile(Graphics2D g2d) {
		for (int i = 0; i < nbCase; i++) {
			for (int j = 0; j < nbCase; j++) {
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
	 * Méthode qui retourne la quantité de case dans la grille
	 * 
	 * @return La qtt de case dans la grille
	 */
	// Giroux
	public int getNbCase() {
		return nbCase;
	}// Fin méthode

	/**
	 * Permet de réinitialiser le tableau
	 */
	// Giroux
	public void reinitialiser() {
		for (int i = 0; i < nbCase; i++) {
			for (int j = 0; j < nbCase; j++) {
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
		for (int i = 0; i < nbCase; i++) {
			if (clique.getY() >= i * hauteurCase && clique.getY() < ((i + 1) * hauteurCase)) {
				for (int j = 0; j < nbCase; j++) {
					if (clique.getX() >= j * largeurCase && clique.getX() < ((j + 1) * largeurCase)) {

						if (tabEmplacement[i][j] == null) {
							break;

						} else if (tabEmplacement[i][j].getVaisseau()) {
							vaisseau = false;
						} else if (tabEmplacement[i][j].getDrapeau()) {
							drapeau = false;

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
	 * Applique un quart de rotation horaire à la tuile sélectionnée
	 */
	// Jason Xa
	public void rotation() {
		tuile.setAngleRotation(tuile.getAngleRotation() + 0.5 * Math.PI);
		repaint();
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
	 * Vérifie si la grille contient au moins une tuile du type spécifié.
	 * 
	 * @param typeTuile le type de tuile à rechercher dans la grille
	 * @return true si au moins une tuile du type spécifié est présente, sinon false
	 */
	// Kitimir Yim
	public boolean contientTuile(Class<?> typeTuile) {
		for (int i = 0; i < nbCase; i++) {
			for (int j = 0; j < nbCase; j++) {
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
		for (int i = 0; i < nbCase; i++) {
			for (int j = 0; j < nbCase; j++) {
				if (tabEmplacement[i][j] != null && typeTuile.isInstance(tabEmplacement[i][j])) {
					return tabEmplacement[i][j];
				}
			}
		}
		return null;
	}

	/**
	 * Lie un portail si nécessaire
	 * @param tuile L'autre tuile (contenant un portail) à laquelle lier un portail
	 */
	// Kitimir Yim
	private void lierPortail(Tuile tuile) {

		if (nbPortails % 2 == 0) {

			Portail portailTuile = (Portail) this.chercherTuile(Portail.class);
			Portail premierPortail = (Portail) portailTuile;
			Portail deuxiemePortail = (Portail) tuile;
			if (premierPortail.getPortailAssocie() == null) {
				premierPortail.definirPortailAssocie(deuxiemePortail);
			
				System.out.println("Ce portail a maintenant un duo");
			}
		} else {
			System.out.println("Ce portail n'a pas de duo. N'oubliez pas de lui créer un partenaire.");

		}
	}

}// Fin classe
