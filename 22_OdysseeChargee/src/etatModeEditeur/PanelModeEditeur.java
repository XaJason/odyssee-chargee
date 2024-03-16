package etatModeEditeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import niveau.GestionnaireDeNiveau;
import niveau.Niveau;
import niveau.Sauvegarder;
import tuile.Carre;
import tuile.Drapeau;
import tuile.Pics;
import tuile.Portail;
import tuile.TriangleEquilateral;
import tuile.TriangleRectangle;
import tuile.Vaisseau;
import utilis.OutilsImage;

/**
 * Panel du mode éditeur
 * 
 * @author Jason Xa
 * 
 */

public class PanelModeEditeur extends JPanel {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -1637257199908540129L;

	/**
	 * Grille du mode éditeur
	 */
	private Grille grille;

	/**
	 * ajouter le support pour lancer des evenements de type PropertyChange
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/** bouton permettant la sélection de la tuile de type carré */
	private JButton btnCarre;
	/** bouton permettant la sélection de la tuile de type triangle rectangle */
	private JButton btnTriangleRectangle;
	/** bouton permettant la sélection de la tuile de type triangle équilatéral */
	private JButton btnTriangleEquilateral;
	/** bouton permettant la sélection de la tuile de type pics */
	private JButton btnPics;
	/** bouton permettant la sélection de la tuile de type portail */
	private JButton btnPortail;
	/** bouton permettant la sélection de la tuile de type drapeau */
	private JButton btnDrapeau;
	/** étiquette servant à afficher le type de tuile sélectionné */
	private JLabel lblTypeSelectionne;

	/**
	 * chaine de caractères pour l'étiquette d'affichage de type de tuile
	 * sélectionné
	 */
	private String preTexteTypeSelectionne = "Type de tuile sélectionné: ";
	/** bouton permettant la réinitialisation de la grille */
	private JButton btnReinitialiser;
	/** bouton permettant de gérer la suppression de tuile */
	private JButton btnSupprimer;
	/** bouton permettant de gérer la rotation de nouvelles tuiles */
	private JButton btnRotation;
	/** bouton permettant de gérer la sauvegarde du niveau associée à la grille */
	private JButton btnSauvegarder;
	/** bouton permettant de la sélection de la tuile de type vaisseau */
	private JButton btnVaisseau;

	/**
	 * methode qui permettra de s'ajouter en tant qu'ecouteur
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Implémente le panel et ses fonctionnalités
	 */
	// Kitimir Yim
	public PanelModeEditeur() {
		setLayout(null);

		grille = new Grille();
		grille.setBounds(410, 38, 916, 916);
		add(grille);

		JButton btnGrille = new JButton("Afficher la grille");
		btnGrille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.afficherGrille();
			}
		});
		btnGrille.setBounds(858, 5, 421, 23);
		add(btnGrille);

		// JSpinner spinnerQttCarre = new JSpinner();
		// spinnerQttCarre.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// Object objetInit = spinnerQttCarre.getValue();
		// Number chiffreTransfo = (Number) objetInit;
		// grille.changerQttCarre(chiffreTransfo.intValue());
		//
		// }
		// });
		// spinnerQttCarre.setModel(new SpinnerNumberModel(grille.getNbCarre(), 2, 64,
		// 1));
		// spinnerQttCarre.setBounds(715, 6, 54, 20);
		// add(spinnerQttCarre);

		// JLabel lblQttCarre = new JLabel("Combien de carré par ligne:");
		// lblQttCarre.setBounds(530, 9, 199, 14);
		// add(lblQttCarre);

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
				grille.setTuile(new Carre());
				afficherSelection();
			}
		});
		btnCarre.setBounds(64, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("carre.jpg", btnCarre);
		add(btnCarre);

		btnTriangleRectangle = new JButton();
		btnTriangleRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new TriangleRectangle());
				afficherSelection();
			}
		});
		btnTriangleRectangle.setBounds(188, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_rectangle.png", btnTriangleRectangle);
		add(btnTriangleRectangle);

		btnTriangleEquilateral = new JButton();
		btnTriangleEquilateral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new TriangleEquilateral());
				afficherSelection();
			}
		});
		btnTriangleEquilateral.setBounds(315, 39, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_equilateral.png", btnTriangleEquilateral);
		add(btnTriangleEquilateral);

		btnPics = new JButton();
		btnPics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Pics());
				afficherSelection();
			}

		});
		btnPics.setBounds(64, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("pics.png", btnPics);
		add(btnPics);

		btnPortail = new JButton();
		btnPortail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Portail());
				afficherSelection();
			}
		});
		btnPortail.setBounds(188, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("portail.png", btnPortail);
		add(btnPortail);

		btnDrapeau = new JButton();
		btnDrapeau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Drapeau());
				afficherSelection();
			}
		});
		btnDrapeau.setBounds(315, 134, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("drapeau.png", btnDrapeau);
		add(btnDrapeau);

		lblTypeSelectionne = new JLabel(preTexteTypeSelectionne);
		lblTypeSelectionne.setBounds(64, 332, 336, 14);
		add(lblTypeSelectionne);
		btnReinitialiser = new JButton();
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.reinitialiser();
				grille.setSupprimer(false);
			}
		});
		btnReinitialiser.setBounds(64, 356, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("reinitialiser.png", btnReinitialiser);
		add(btnReinitialiser);

		btnSupprimer = new JButton();
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.gererSupprimer();
			}
		});
		btnSupprimer.setBounds(188, 356, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("supprimer.png", btnSupprimer);
		add(btnSupprimer);

		btnRotation = new JButton();
		btnRotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setSupprimer(false);
				grille.rotation();
			}
		});
		btnRotation.setBounds(315, 356, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("rotation.png", btnRotation);
		add(btnRotation);

		btnSauvegarder = new JButton();
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nom = JOptionPane.showInputDialog("Veuillez entrer un nom de niveau :");
				Niveau niveauParDefaut = new Niveau(grille.getTableau(),nom);
				sauvegarder(niveauParDefaut);

			}

		});
		btnSauvegarder.setBounds(188, 451, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("sauvegarder.png", btnSauvegarder);
		add(btnSauvegarder);

		btnVaisseau = new JButton();
		btnVaisseau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Vaisseau());
				afficherSelection();
			}
		});
		btnVaisseau.setBounds(188, 229, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("vaisseau.png", btnVaisseau);
		add(btnVaisseau);
	}

	// Kitimir Yim
	private void sauvegarder(Niveau niveau) {
		
		GestionnaireDeNiveau.ajouter(niveau);
		Sauvegarder.sauvegarderNiveau(niveau, niveau.getNomNiveau());

	}

	/**
	 * Affiche le type de tuile sélectionné et désactive le mode de suppression de
	 * tuile
	 */
	// Jason Xa
	private void afficherSelection() {
		lblTypeSelectionne.setText(preTexteTypeSelectionne + grille.getTuile().getType());
		grille.setSupprimer(false);
	}
}