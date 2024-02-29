package tuile;

import java.awt.Graphics2D;

import physique.Vecteur2D;

/**
 * 
 */
public class Bloc extends Tuile {

private Vecteur2D position;
	
	public Bloc(Vecteur2D position) {
		this.position = position;
	}
	
	
	
	public void dessiner(Graphics2D g2d) {	
	}

	public Vecteur2D getPosition() {
		return position;
	}

}
