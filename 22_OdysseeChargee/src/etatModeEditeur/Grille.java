package etatModeEditeur;

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

/**
 * 
 */
public class Grille extends JPanel {

	private static final long serialVersionUID = -977837790552954988L;
	/** Hauteur du composant **/
	private int hauteur;
	/** Largeur du composant **/
	private int largeur;
	/** Hauteur de chaque carré **/
	private int hauteurCarre;
	/** Largeur du de chaque carré **/
	private int largeurCarre;
	/** Nombre de ligne et colonne. Ex: 3 donerait une grille 3x3 **/
	private int nbCarre = 15;
	/**
	 * Rectangle qui conrespond à la section de la grille où se trouve la sourie
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
	Point2D clique;
	/**
	 * Contient la tuile sélectionnée dans les boutons du panneau du mode éditeur
	 **/
	private Tuile tuile;
	/** Tuile qui conrespond à celui dans le tableau des emplacements **/
	private Tuile tuileTableau;
	/**
	 * Tuile temporaire qui sauvegarde la tuile sélectionnée avec ses propriétés
	 **/
	private Tuile tuileTemp;
	/** Indique si l'emplacement est déjà pris **/
	boolean placePrise = false;
	/** Indique s'il y a déjà un drapeau sur la grille **/
	boolean drapeau = false;
	/** Indique si on est en mode supprimer ou non **/
	boolean supprimer = false;
	/** Indique que la sourie est à l'exterieur du composant **/
	boolean exterieurComposant = true;

	/**
	 * Création du panel
	 */
	public Grille() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clique = e.getPoint();
				if (!supprimer) {
					sauvegarderEmplacement();
				} else {
					supprimerCase();
				}
				afficherTab();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				exterieurComposant = true;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exterieurComposant = false;
			}
		});
		
		
		setLayout(null);
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (supprimer) {
					setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
				} else {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				dessinerCarre(e.getX(), e.getY());
				repaint();

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
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		dessiner(g2d);

	}// Fin méthode

	/**
	 * Méthode qui dessine de la grille et de ses composantes
	 * 
	 * @param g Contexte graphique
	 */
	// Giroux
	private void dessiner(Graphics2D g2d) {
		if (premiereFois) {

			tabEmplacement = new Tuile[nbCarre][nbCarre];
			hauteur = this.getHeight();
			largeur = this.getWidth();
			dimensionCarre();
			dessinerGrille();
			premiereFois = false;
		}
		if(supprimer) {
			setBackground(Color.red);
		} else {
			setBackground(new Color(255, 255, 128));
		}

		if (placePrise && !supprimer) {
			g2d.setColor(Color.orange);
		} else if(!supprimer) {
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

	}// Fin méthode

	/**
	 * Méthode qui détermine la grandeur de chaque carré et qui crée le carré
	 * conrespondant
	 */
	// Giroux
	private void dimensionCarre() {
		hauteurCarre = (hauteur / nbCarre);
		largeurCarre = (largeur / nbCarre);
		emplacementActuel = new Rectangle2D.Double(0, 0, largeurCarre, hauteurCarre);
		System.out.println(largeurCarre);
		System.out.println(hauteurCarre);

	}// Fin méthode

	/**
	 * Méthode qui dessine le rectangle selon l'emplacement passée en paramètre
	 * 
	 * @param posX Position x de l'emplacement
	 * @param posY Position y de l'emplacement
	 */
	// Giroux
	private void dessinerCarre(double posX, double posY) {

		for (int i = 0; i < nbCarre; i++) {
			if (posY >= i * hauteurCarre && posY < ((i + 1) * hauteurCarre)) {
				for (int j = 0; j < nbCarre; j++) {
					if (posX >= j * largeurCarre && posX < ((j + 1) * largeurCarre)) {
						emplacementActuel.setFrame(largeurCarre * j, hauteurCarre * i, largeurCarre, hauteurCarre);
						if (!supprimer) {
							tuile.redimensionnerImage((int) hauteurCarre, (int) largeurCarre);
							tuile.setX((int) largeurCarre * j);
							tuile.setY((int) hauteurCarre * i);
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
	private void dessinerGrille() {
		quadVerti = new Path2D.Double();
		quadHori = new Path2D.Double();
		for (int i = 0; i < nbCarre + 1; i++) {
			quadHori.moveTo(0, i * hauteurCarre);
			quadHori.lineTo(largeur, i * hauteurCarre);
			for (int j = 0; j < nbCarre + 1; j++) {
				quadVerti.moveTo(j * largeurCarre, 0);
				quadVerti.lineTo(j * largeurCarre, hauteur);
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
	 * Méthode qui change le nombre de carré par ligne
	 * 
	 * @param nouvNbCarre Le nouveau nombre de carré par ligne et colonne
	 */
	// Giroux
	public void changerQttCarre(int nouvNbCarre) {
		this.nbCarre = nouvNbCarre;
		premiereFois = true;
		repaint();
	} // Fin méthode

	/**
	 * Méthode qui change la valeur dans le tableau dépendant de si l'emplacement
	 * est libre 1 si non libre et 0 si libre
	 */
	// Giroux
	private void sauvegarderEmplacement() {
		for (int i = 0; i < nbCarre; i++) {
			if (clique.getY() >= i * hauteurCarre && clique.getY() < ((i + 1) * hauteurCarre)) {
				for (int j = 0; j < nbCarre; j++) {
					if (clique.getX() >= j * largeurCarre && clique.getX() < ((j + 1) * largeurCarre)) {
						clonerTuile();
						if (tuileTemp.getDrapeau() && drapeau) {	
							break;
						}
						tuileTemp.setX((int) largeurCarre * j);
						tuileTemp.setY((int) hauteurCarre * i);
						if (tabEmplacement[i][j] == null) {
							tabEmplacement[i][j] = tuileTemp;
							if (tuileTemp.getDrapeau() && !drapeau) {
								drapeau = true;
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
	 * 
	 */
	// Jason Xa
	private void clonerTuile() {
		switch (tuile.getType()) {
		case "Carré":
			tuileTemp = new Carre();
			break;
		case "Drapeau":
			tuileTemp = new Drapeau();
			break;
		case "Pics":
			tuileTemp = new Pics();
			break;
		case "Portail":
			tuileTemp = new Portail();
			break;
		case "Triangle équilatéral":
			tuileTemp = new TriangleEquilateral();
			break;
		case "Triangle rectangle":
			tuileTemp = new TriangleRectangle();
			break;
		}

	}

	/**
	 * À fin de test, non permanent, imprime le tableau des emplacements
	 */
	// Giroux
	private void afficherTab() {
		for (int i = 0; i < nbCarre; i++) {
			System.out.print("\n");
			for (int j = 0; j < nbCarre; j++) {
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
	private void dessinerTuile(Graphics2D g2d) {
		for (int i = 0; i < nbCarre; i++) {
			for (int j = 0; j < nbCarre; j++) {
				tuileTableau = tabEmplacement[i][j];
				if (tuileTableau != null) {
					tuileTableau.dessiner(g2d);
				}
			}
		}
		repaint();
	}// Fin méthode

	/**
	 * Méthode qui retourne la quantité de carré dans la grille
	 * 
	 * @return La qtt de carré dans la grille
	 */
	// Giroux

	public int getNbCarre() {
		return nbCarre;
	}// Fin méthode

	public void reinitialiser() {
		for (int i = 0; i < nbCarre; i++) {
			for (int j = 0; j < nbCarre; j++) {
				tabEmplacement[i][j] = null;
			}
		}
		drapeau = false;
		repaint();

	}

	public void setSupprimer() {
		if (supprimer == false) {
			supprimer = true;
		} else {
			supprimer = false;
		}
		repaint();
	}

	public void supprimerCase() {

		for (int i = 0; i < nbCarre; i++) {
			if (clique.getY() >= i * hauteurCarre && clique.getY() < ((i + 1) * hauteurCarre)) {
				for (int j = 0; j < nbCarre; j++) {
					if (clique.getX() >= j * largeurCarre && clique.getX() < ((j + 1) * largeurCarre)) {
						if (tabEmplacement[i][j].getDrapeau()) {
							drapeau = false;
						}
						tabEmplacement[i][j] = null;

					}
				}
			}
		}

	}

	/**
	 * Définir le type de tuile sélectionné pour le placement
	 * 
	 * @param tuile le nouveau type de tuile sélectionné pour le placement
	 */
	// Jason Xa
	public void setTuile(Tuile tuile) {
		this.tuile = tuile;
	}

	/**
	 * 
	 */
	// Jason Xa
	public void reinitialiserNiveau() {

	}

	/**
	 * 
	 */
	// Jason Xa
	public void rotationObjet() {
	}

	/**
	 * 
	 */
	// Jason Xa
	public void supprimerTuile() {
	}

}// Fin classe
