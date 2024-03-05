package tuile;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * 
 */
//Jason Xa
public class Portail extends Tuile {
	private static Image imageRef;

	/**
	 * @param x 
	 * @param y 
	 * 
	 */
	public Portail(int x, int y) {

		super(imageRef, x, y);

	}

	public void dessiner(Graphics2D g2d) {
	}

	/**
	 * @param imageRef the imageRef to set
	 */
	public static void setImageRef(Image imageRef) {
		Portail.imageRef = imageRef;
	}

}
