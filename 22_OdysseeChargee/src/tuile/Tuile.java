package tuile;

import java.awt.Graphics2D;
import java.awt.Image;

import utilis.Dessinable;

/**
 * 
 */
//Jason Xa
public class Tuile implements Dessinable {

	protected final int LARGEUR = 64;
	protected final int HAUTEUR = 64;

	protected int x;
	protected int y;

	Image image = null;

	/**
	 * @param image 
	 * @param x 
	 * @param y 
	 * 
	 */
	public Tuile(Image image, int x, int y) {
		this.x = x;
		this.y = y;
	}


	public void dessiner(Graphics2D g2d) {
		g2d.drawImage(image, x, y, null);
	}

}
