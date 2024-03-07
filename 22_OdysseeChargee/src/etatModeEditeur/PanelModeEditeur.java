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

import tuile.Carre;
import tuile.Drapeau;
import tuile.Pics;
import tuile.Portail;
import tuile.TriangleEquilateral;
import tuile.TriangleRectangle;
import utilis.OutilsImage;

/**
 * Panel du mode éditeur
 * 
 * @author Jason Xa
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
	private JLabel lblTypeSelectionne;

	private String preTexteTypeSelectionne = "Type de tuile sélectionné: ";
	private JButton btnReinitialiser;
	private JButton btnSupprimer;
	private JButton btnRotation;
	private JButton btnSauvegarder;

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

		creerSectionBoutons();
	}

	/**
	 * Création des boutons d'action pour la création du niveau
	 */
	// Jason Xa
	private void creerSectionBoutons() {
		btnCarre = new JButton();
		btnCarre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTypeSelectionne.setText(preTexteTypeSelectionne + "Carré");
				grille.setTuile(new Carre());
			}
		});
		btnCarre.setBounds(64, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("carre.jpg", btnCarre);
		add(btnCarre);

		btnTriangleRectangle = new JButton();
		btnTriangleRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTypeSelectionne.setText(preTexteTypeSelectionne + "Triangle rectangle");
				grille.setTuile(new TriangleRectangle());
			}
		});
		btnTriangleRectangle.setBounds(188, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_rectangle.png", btnTriangleRectangle);
		add(btnTriangleRectangle);

		btnTriangleEquilateral = new JButton();
		btnTriangleEquilateral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTypeSelectionne.setText(preTexteTypeSelectionne + "Triangle équilatéral");
				grille.setTuile(new TriangleEquilateral());
			}
		});
		btnTriangleEquilateral.setBounds(315, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_equilateral.png", btnTriangleEquilateral);
		add(btnTriangleEquilateral);

		btnPics = new JButton();
		btnPics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTypeSelectionne.setText(preTexteTypeSelectionne + "Pics");
				grille.setTuile(new Pics());
			}

		});
		btnPics.setBounds(64, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("pics.png", btnPics);
		add(btnPics);

		btnPortail = new JButton();
		btnPortail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTypeSelectionne.setText(preTexteTypeSelectionne + "Portail");
				grille.setTuile(new Portail());
			}
		});
		btnPortail.setBounds(188, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("portail.png", btnPortail);
		add(btnPortail);

		btnDrapeau = new JButton();
		btnDrapeau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTypeSelectionne.setText(preTexteTypeSelectionne + "Drapeau");
				grille.setTuile(new Drapeau());
			}
		});
		btnDrapeau.setBounds(315, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("drapeau.png", btnDrapeau);
		add(btnDrapeau);

		lblTypeSelectionne = new JLabel(preTexteTypeSelectionne);
		lblTypeSelectionne.setBounds(64, 230, 336, 14);
		add(lblTypeSelectionne);
		
		btnReinitialiser = new JButton();
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReinitialiser.setBounds(64, 255, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("reinitialiser.png", btnReinitialiser);
		add(btnReinitialiser);
		
		btnSupprimer = new JButton();
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSupprimer.setBounds(188, 255, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("supprimer.png", btnSupprimer);
		add(btnSupprimer);
		
		btnRotation = new JButton();
		btnRotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRotation.setBounds(315, 255, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("rotation.png", btnRotation);
		add(btnRotation);
		
		btnSauvegarder = new JButton();
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSauvegarder.setBounds(188, 351, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("sauvegarder.png", btnSauvegarder);
		add(btnSauvegarder);
	}
}