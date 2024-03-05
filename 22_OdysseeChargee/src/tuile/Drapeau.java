package tuile;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * 
 */
//Jason Xa
public class Drapeau extends Tuile {
	private static Image imageRef;

	/**
	 * @param x
	 * @param y
	 * 
	 */
	public Drapeau(int x, int y) {

		super(imageRef, x, y);
	}

	/**
	 * 
	 */
	public void dessiner(Graphics2D g2d) {
	}

}
