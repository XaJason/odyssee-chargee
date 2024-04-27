package application;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import utilitaires.OutilsImage;

public class FondEcran extends JPanel {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = -6371823752957467232L;
	private Image backgroundImage;

	public FondEcran(String fond) {
		backgroundImage = OutilsImage.lireImage(fond);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
	}
}
