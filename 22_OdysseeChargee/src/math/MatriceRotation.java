package math;
/**
 * @author Giroux
 */

import java.awt.geom.Point2D;

public class MatriceRotation {
	

	/** Angle de rotation transformé en radian**/
	double angleRotation;
	/** Matrice de rotation**/
	double [][] rotation = new double[2][2];
	
	
	
	public MatriceRotation(double angleRotation) {
		this.angleRotation=angleRotation;
	}
	
	private void setRotation() {
		rotation[0][0] = Math.cos(angleRotation);
		rotation[0][1] = Math.sin(angleRotation);
		rotation[1][0] = Math.sin(angleRotation);
		rotation[1][1] = Math.cos(angleRotation);
	}
	
	public Point2D.Double rotationner(Point2D pointARotationner) {
		double [][] point = {{pointARotationner.getX()},  {pointARotationner.getY()}};
		setRotation();
		double x=0;
		double y=0;
		for(int i=0; i<rotation.length; i ++) {
			x+= rotation[0][i]*point[i][0];
		}
		for(int i=0; i<rotation.length; i ++) {
			y+= rotation[1][i]*point[i][0];
		}
		
		Point2D.Double pointFinal = new Point2D.Double(x,y);
		
		return pointFinal;
		
	}
	
	

}
