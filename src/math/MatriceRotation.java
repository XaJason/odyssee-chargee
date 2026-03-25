package math;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Classe qui permet d'instancer des matrices de rotation pour permettre de
 * rotationner différents objets
 *
 * @author Giroux
 */
public class MatriceRotation implements Serializable {
	/** Numéro d'identification pour la sérialisation **/
	private static final long serialVersionUID = 2819875705094597074L;
	/** Angle de rotation transformé en radian **/
	private double angleRotation;
	/** Matrice de rotation **/
	private double[][] rotation = new double[2][2];

	/**
	 * Matrice qui possède un angle en radian pour rotationner les blocs
	 *
	 * @param angleRotation l'angle en radian
	 */
	// Giroux
	public MatriceRotation(double angleRotation) {
		this.angleRotation = angleRotation;
	}

	/**
	 * Méthode qui permet de retationner un point précis selon l'angle de rotation à
	 * l'aide de la matrice de rotation
	 *
	 * @param pointARotationner Le point à retationner
	 * @return Le point transformé
	 */
	// Giroux
	public Point2D.Double rotationner(Point2D pointARotationner) {
		double[][] point = { { pointARotationner.getX() }, { pointARotationner.getY() } };
		setRotation();
		double x = 0;
		double y = 0;
		for (int i = 0; i < rotation.length; i++) {
			x += rotation[0][i] * point[i][0];
		}
		for (int i = 0; i < rotation.length; i++) {
			y += rotation[1][i] * point[i][0];
		}

		Point2D.Double pointFinal = new Point2D.Double(x, y);

		return pointFinal;
	}

	/**
	 * Méthode qui permet d'instancier le tableau de rotation
	 */
	// Giroux
	private void setRotation() {
		rotation[0][0] = Math.cos(angleRotation);
		rotation[0][1] = -Math.sin(angleRotation);
		rotation[1][0] = Math.sin(angleRotation);
		rotation[1][1] = Math.cos(angleRotation);
	}
}
