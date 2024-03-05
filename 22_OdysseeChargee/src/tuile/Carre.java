package tuile;

import java.awt.Image;

/**
 * 
 */
//Jason Xa
public class Carre extends Tuile {
	private static Image imageRef;

	/**
	 * @param x
	 * @param y
	 * 
	 */
	public Carre(int x, int y) {
		super(imageRef, x, y);
	}

	/**
	 * @param imageRef the imageRef to set
	 */
	public static void setImageRef(Image imageRef) {
		Carre.imageRef = imageRef;
	}

}
