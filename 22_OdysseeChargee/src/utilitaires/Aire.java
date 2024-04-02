package utilitaires;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * 
 * @author Jason Xa
 */
public class Aire {
	/***/
	private Path2D.Double forme;
	/***/
	private Point2D.Double[] pointsExterieurs;

	/**
	 * @param point1
	 * @param pointMilieu
	 * @param point2
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
	}

}
