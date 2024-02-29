package etatModeEditeur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author Giroux
 */

import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Grille extends JPanel {

	/** Hauteur du composant **/
	private int hauteur;
	/** Largeur du composant **/
	private int largeur;
	/** Hauteur de chaque carré **/
	private int hauteurCarre;
	/** Largeur du de chaque carré **/
	private int largeurCarre;
	/** Nombre de ligne et colonne. Ex: 3 donerait une grille 3x3 **/
	private int nbCarre = 16;
	/** Rectangle qui conrespond à la section de la grille où se trouve la sourie **/
	Rectangle2D.Double emplacementActuel;
	/**Quand il dessine pour le première fois**/
	Boolean premiereFois=true;

	/**
	 * Création du panel
	 */
	public Grille() {
		
		setBackground(Color.lightGray);
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
					dessinerCarre(e.getX(), e.getY());
					repaint();
			}
		});
		
	}//Fin constructeur
	/**
	 * Méthode qui dessine au bon endroit le rectangle
	 * @param g Contexte graphique
	 */
	//Giroux
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(premiereFois) {
			hauteur = this.getHeight();
			largeur = this.getWidth();
			dimensionCarre();
			premiereFois=false;
		}
		g2d.setColor(Color.cyan);
		g2d.fill(emplacementActuel);
		

	}//Fin méthode
	/**
	 * Méthode qui détermine la grandeur de chaque carré et qui crée le rectangle conrespondant
	 */
	//Giroux
	void dimensionCarre() {
		hauteurCarre = hauteur / nbCarre;
		largeurCarre = largeur / nbCarre;
		emplacementActuel= new Rectangle2D.Double (0,0,largeurCarre,hauteurCarre);

	}//Fin méthode
	
	/**
	 * Méthode qui dessine le rectangle selon l'emplacement passée en paramètre
	 * @param posX Position x de l'emplacement
	 * @param posY Position y de l'emplacement
	 */
	//Giroux
	void dessinerCarre(int posX, int posY) {

		for (int i = 0; i < nbCarre; i++) {
			if (posY >= i * hauteurCarre && posY < ((i + 1) * hauteurCarre)) {
				for (int j = 0; j < nbCarre; j++) {
					if (posX >= j * largeurCarre && posX < ((j + 1) * largeurCarre)) {
						emplacementActuel.setFrame(largeurCarre*j, hauteurCarre*i,largeurCarre,hauteurCarre);
						System.out.println("Ligne: " + i + "Col: " + j);
					}
				}

			}

		}

	}//Fin méthode
}//Fin classe 
