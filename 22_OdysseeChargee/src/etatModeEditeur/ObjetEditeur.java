package etatModeEditeur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import tuile.Tuile;

// Giroux
// Jason Xa
public class ObjetEditeur extends JPanel {

	/**
	 * Forme Rectangulaire qui sert de base au area et qui reste à son endroit
	 * d'origine
	 **/
	Rectangle2D.Double rectAreaOrigine;
	/** Area qui se fait déplacer **/
	Area areaDeplacement;
	/** Forme Rectangulaire qui sert de base au area **/
	Rectangle2D.Double rectAreaDep;
	/** Pos X initiale de la forme **/
	int posXIni = 100;
	/** Pos Y initiale de la forme **/
	int posYIni = 50;
	/** Pos X de la forme **/
	int posXActuelle = 100;
	/** Pos Y de la forme **/
	int posYActuelle = 50;
	/** Si forme sélectionnée **/
	boolean selectionne = false;

	/** tuile sélectionnée */
	private Tuile tuileActuelle;

	/** angle de rotation (rad) */
	private double angleRotation = 0;

	private int xPrecedent, yPrecedent;

	/** condition de placement */
	private boolean placementValide;

	/**
	 * Create the panel.
	 */
	public ObjetEditeur() {
		/*
		 * addMouseMotionListener(new MouseMotionAdapter() {
		 * 
		 * @Override
		 * public void mouseDragged(MouseEvent e) {
		 * if(selectionne) {
		 * posXActuelle+=(e.getX()-xPrecedent);
		 * posYActuelle+=(e.getY()-yPrecedent);
		 * xPrecedent=e.getX();
		 * yPrecedent=e.getY();
		 * repaint();
		 * 
		 * }
		 * 
		 * }
		 * });
		 */
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point2D pointClique = new Point2D.Double(e.getX(), e.getY());
				if (areaDeplacement.contains(pointClique)) {
					selectionne = true;
					// xPrecedent=e.getX();
					// yPrecedent=e.getY();

				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				selectionne = false;
				// posXActuelle=posXIni;
				// posYActuelle=posYIni;
				repaint();
			}
		});

		setBackground(Color.blue);

	}

	/**
	 * Méthode qui dessine au bon endroit la grille
	 * 
	 * @param g Contexte graphique
	 */
	// Giroux
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		instancierForme();
		g2d.setColor(Color.black);
		g2d.fill(areaDeplacement);
		g2d.fill(rectAreaOrigine);

	}

	/**
	 * Méthode qui initialise les formes et area
	 */
	void instancierForme() {
		rectAreaOrigine = new Rectangle2D.Double(posXIni, posYIni, 150, 150);
		rectAreaDep = new Rectangle2D.Double(posXActuelle, posYActuelle, 150, 150);
		areaDeplacement = new Area(rectAreaDep);
	}

}
