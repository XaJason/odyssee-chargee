package utilitaires;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Un objet Aire représente une aire triangulaire intérieure d'une tuile qui
 * peut porter une plaque chargée. Une tuile a autant d'aires que de côtés.
 * 
 * @author Jason Xa
 */
public class Aire implements Selectionnable {
	/** le triangle formé par les trois points */
	private Path2D.Double forme;
	/**
	 * les points extérieurs, les points qui sont adjacents aux bordures de la tuile
	 */
	private Point2D.Double[] pointsExterieurs;

	/**
	 * le point intérieur de l'aire, le point qui n'est pas adjacent à une bordure
	 * de la tuile
	 */
	private Point2D.Double pointInterieur;

	/**
	 * Constructeur
	 * 
	 * @param point1         le premier point extérieur
	 * @param pointInterieur le point milieu, le point qui se situe à l'intérieur de
	 *                       la
	 *                       tuile
	 * @param point2         le deuxième point extérieur
	 * 
	 */
	// Jason Xa
	public Aire(Point2D.Double point1, Point2D.Double pointInterieur, Point2D.Double point2) {
		forme = new Path2D.Double();
		forme.moveTo(point1.getX(), point1.getY());
		forme.lineTo(pointInterieur.getX(), pointInterieur.getY());
		forme.lineTo(point2.getX(), point2.getY());
		forme.closePath();

		pointsExterieurs = new Point2D.Double[] { point1, point2 };

		this.pointInterieur = pointInterieur;
	}

	/**
	 * Retourne la forme de l'aire
	 * 
	 * @return la forme de l'aire
	 */
	// Jason Xa
	public Path2D.Double getForme() {
		return forme;
	}

	/**
	 * Retourne les points extérieurs de l'aire
	 * 
	 * @return les points extérieurs de l'aire
	 */
	// Jason Xa
	public Point2D.Double[] getPointsExterieurs() {
		return pointsExterieurs;
	}

	/**
	 * Retourne le point intérieur de l'aire
	 * 
	 * @return le point intérieur de l'aire
	 */
	// Jason Xa
	public Point2D.Double getPointMilieu() {
		return pointInterieur;
	}

	/**
	 * Retourne vrai si le point passé en paramètre fait partie de l'objet
	 * dessinable sur lequel cette méthode sera appelée
	 *
	 * @param xPix Coordonnée en x du point (exprimée en pixels)
	 * @param yPix Coordonnée en y du point (exprimée en pixels)
	 * @return vrai si le point fait partie de l'objet dessinable
	 */
	// Jason Xa
	@Override
	public boolean contient(double xPix, double yPix) {
		return forme.contains(xPix, yPix);

	}

	/**
	 * Retourne vrai si le point passé en paramètre fait partie de l'objet
	 * dessinable sur lequel cette méthode sera appelée
	 * 
	 * @param point le point à vérifier
	 * @return vrai si le point fait est contenu dans l'objet dessinable
	 */
	public boolean contient(Point2D.Double point) {
		return forme.contains(point.getX(), point.getY());
	}
}
