package etatModeEditeur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilis.OutilsImage;

/**
 * Panel du mode éditeur
 * 
 * @author Kitimir Yim
 */

public class PanelModeEditeur extends JPanel {
	private static final long serialVersionUID = -1637257199908540129L;

	/**
	 * Grille du mode éditeur
	 */
	private Grille grille;

	/**
	 * ajouter le support pour lancer des evenements de type PropertyChange
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private JButton btnCarre;
	private JButton btnTriangleRectangle;
	private JButton btnTriangleEquilateral;
	private JButton btnPics;
	private JButton btnPortail;
	private JButton btnDrapeau;

	/**
	 * methode qui permettra de s'ajouter en tant qu'ecouteur
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * 
	 */
	// Kitimir Yim
	public PanelModeEditeur() {
		setLayout(null);

		grille = new Grille();
		grille.setBackground(new Color(255, 255, 128));
		grille.setBounds(514, 39, 1025, 922);
		add(grille);

		JButton btnVersMenu = new JButton("Passer au menu");
		btnVersMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				pcs.firePropertyChange("passerVersMenu", 0, -1);
			}
		});
		btnVersMenu.setBounds(178, 5, 145, 23);
		add(btnVersMenu);

		JButton btnGrille = new JButton("Afficher la grille");
		btnGrille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.afficherGrille();
			}
		});
		btnGrille.setBounds(858, 5, 421, 23);
		add(btnGrille);

		JSpinner spinnerQttCarre = new JSpinner();
		spinnerQttCarre.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Object objetInit = spinnerQttCarre.getValue();
				Number chiffreTransfo = (Number) objetInit;
				grille.changerQttCarre(chiffreTransfo.intValue());

			}
		});
		spinnerQttCarre.setModel(new SpinnerNumberModel(grille.getNbCarre(), 2, 64, 1));
		spinnerQttCarre.setBounds(715, 6, 54, 20);
		add(spinnerQttCarre);

		JLabel lblQttCarre = new JLabel("Combien de carré par ligne:");
		lblQttCarre.setBounds(530, 9, 199, 14);
		add(lblQttCarre);

		
		creerBoutonsTuile();
	}

	/**
	 * Création des boutons représentant les différentes options de tuile possibles
	 */
	// Jason Xa
	private void creerBoutonsTuile() {
		btnCarre = new JButton();
		btnCarre.setBounds(64, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("carre.jpg", btnCarre);
		add(btnCarre);

		btnTriangleRectangle = new JButton();
		btnTriangleRectangle.setBounds(188, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_rectangle.png", btnTriangleRectangle);
		add(btnTriangleRectangle);

		btnTriangleEquilateral = new JButton();
		btnTriangleEquilateral.setBounds(315, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_equilateral.png", btnTriangleEquilateral);
		add(btnTriangleEquilateral);

		btnPics = new JButton();
		btnPics.setBounds(64, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("pics.png", btnPics);
		add(btnPics);

		btnPortail = new JButton();
		btnPortail.setBounds(188, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("portail.png", btnPortail);
		add(btnPortail);

		btnDrapeau = new JButton();
		btnDrapeau.setBounds(315, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("drapeau.png", btnDrapeau);
		add(btnDrapeau);
	}
}