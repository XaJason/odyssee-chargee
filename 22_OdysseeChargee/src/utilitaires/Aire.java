package utilitaires;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Un objet Aire représente une aire triangulaire intérieure d'une tuile qui
 * peut porter une plaque chargée. Une tuile a autant d'aires que de côtés.
 * 
 * @author Jason Xa
 */
public class Aire {
	/** le triangle formé par les trois points */
	private Path2D.Double forme;
	/**
	 * les points extérieurs, les points qui sont adjacents aux bordures de la tuile
	 */
	private Point2D.Double[] pointsExterieurs;

	private Point2D.Double pointMilieu;

	/**
	 * @param point1      le premier point extérieur
	 * @param pointMilieu le point milieu, le point qui se situe à l'intérieur de la
	 *                    tuile
	 * @param point2      le deuxième point extérieur
	 * 
	 */
	// Jason Xa
	public Aire(Point2D.Double point1, Point2D.Double pointMilieu, Point2D.Double point2) {
		forme = new Path2D.Double();
		forme.moveTo(point1.getX(), point1.getY());
		forme.lineTo(pointMilieu.getX(), pointMilieu.getY());
		forme.lineTo(point2.getX(), point2.getY());
		forme.closePath();

		pointsExterieurs = new Point2D.Double[] { point1, point2 };

		this.pointMilieu = pointMilieu;
	}

}
