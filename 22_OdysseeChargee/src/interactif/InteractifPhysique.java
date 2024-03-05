package interactif;

import java.awt.Graphics2D;

import physique.Vecteur2D;

public class InteractifPhysique {

	private Vecteur2D position;

	public InteractifPhysique(Vecteur2D position) {
		this.position = position;
	}



	public void dessiner(Graphics2D g2d) {	
	}

	public Vecteur2D getPosition() {
		return position;
	}

}
