package editeur_niveaux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type carré
	 */
	private JLabel lblCarre;
	/**
	 * étiquette servant à identifier le regroupement de boutons permettant la
	 * sélection de
	 * tuile
	 */
	private JLabel lblObjets;
	/**
	 * étiquette servant à identifier le regroupement de boutons permettant la
	 * sélection de tuile (blocs)
	 */
	private JLabel lblBlocs;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type triangle rectangle
	 */
	private JLabel lblTriangleRectangle;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type triangle équilatéral
	 */
	private JLabel lblTriangleEquilateral;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type pics
	 */
	private JLabel lblPics;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type portail
	 */
	private JLabel lblPortail;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type drapeau
	 */
	private JLabel lblDrapeau;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type vaisseau
	 */
	private JLabel lblVaisseau;
	/**
	 * étiquette servant à identifier bouton permettant la réinitialisation de la
	 * grille
	 */
	private JLabel lblReinitialiser;
	/**
	 * étiquette servant à identifier le bouton permettant de gérer la suppression
	 * de tuile
	 */
	private JLabel lblSupprimer;
	/**
	 * étiquette servant à identifier le bouton permettant de gérer la rotation de
	 * nouvelles tuiles
	 */
	private JLabel lblRotation;
	/**
	 * étiquette servant à identifier le bouton permettant de gérer la sauvegarde du
	 * niveau associée à la grille
	 */
	private JLabel lblSauvegarder;
	/**
	 * étiquette servant à identifier le regroupement de boutons permettant la
	 * sélection de tuile interactive
	 */
	private JLabel lblInteractifs;
	/** étiquette servant à identifier le regroupement de boutons d'action */
	private JLabel lblActions;

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
		btnCarre.setBounds(50, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("carre.jpg", btnCarre);
		add(btnCarre);

		btnTriangleRectangle = new JButton();
		btnTriangleRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new TriangleRectangle());
				afficherSelection();
			}
		});
		btnTriangleRectangle.setBounds(178, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_rectangle.png", btnTriangleRectangle);
		add(btnTriangleRectangle);

		btnTriangleEquilateral = new JButton();
		btnTriangleEquilateral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new TriangleEquilateral());
				afficherSelection();
			}
		});
		btnTriangleEquilateral.setBounds(302, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_equilateral.png", btnTriangleEquilateral);
		add(btnTriangleEquilateral);

		btnPics = new JButton();
		btnPics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Pics());
				afficherSelection();
			}

		});
		btnPics.setBounds(118, 215, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("pics.png", btnPics);
		add(btnPics);

		btnPortail = new JButton();
		btnPortail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Portail());
				afficherSelection();
			}
		});
		btnPortail.setBounds(247, 215, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("portail.png", btnPortail);
		add(btnPortail);

		btnDrapeau = new JButton();
		btnDrapeau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Drapeau());
				afficherSelection();
			}
		});
		btnDrapeau.setBounds(118, 340, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("drapeau.png", btnDrapeau);
		add(btnDrapeau);

		lblTypeSelectionne = new JLabel(preTexteTypeSelectionne);
		lblTypeSelectionne.setBounds(410, 9, 336, 14);
		add(lblTypeSelectionne);
		btnReinitialiser = new JButton();
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.reinitialiser();
				grille.setSupprimer(false);
				grille.setTuile(null);
			}
		});
		btnReinitialiser.setBounds(50, 481, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("reinitialiser.png", btnReinitialiser);
		add(btnReinitialiser);

		btnSupprimer = new JButton();
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.gererSupprimer();
			}
		});
		btnSupprimer.setBounds(178, 481, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("supprimer.png", btnSupprimer);
		add(btnSupprimer);

		btnRotation = new JButton();
		btnRotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setSupprimer(false);
				grille.rotation();
			}
		});
		btnRotation.setBounds(302, 481, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("rotation.png", btnRotation);
		add(btnRotation);

		btnSauvegarder = new JButton();
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nom = JOptionPane.showInputDialog("Veuillez entrer un nom de niveau :");
				Niveau niveauParDefaut = new Niveau(grille.getTableau(), nom);
				sauvegarder(niveauParDefaut);

			}

		});
		btnSauvegarder.setBounds(178, 599, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("sauvegarder.png", btnSauvegarder);
		add(btnSauvegarder);

		btnVaisseau = new JButton();
		btnVaisseau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Vaisseau());
				afficherSelection();
			}
		});
		btnVaisseau.setBounds(247, 340, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("vaisseau.png", btnVaisseau);
		add(btnVaisseau);

		lblCarre = new JLabel("Carré");
		lblCarre.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarre.setBounds(50, 156, 85, 13);
		add(lblCarre);

		lblObjets = new JLabel("Objets");
		lblObjets.setBounds(30, 10, 45, 13);
		add(lblObjets);

		lblBlocs = new JLabel("Blocs");
		lblBlocs.setBounds(40, 38, 45, 13);
		add(lblBlocs);

		lblTriangleRectangle = new JLabel("Triangle rectangle");
		lblTriangleRectangle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTriangleRectangle.setBounds(168, 156, 104, 13);
		add(lblTriangleRectangle);

		lblTriangleEquilateral = new JLabel("Triangle équilatéral");
		lblTriangleEquilateral.setHorizontalAlignment(SwingConstants.CENTER);
		lblTriangleEquilateral.setBounds(289, 156, 110, 13);
		add(lblTriangleEquilateral);

		lblPics = new JLabel("Pics");
		lblPics.setHorizontalAlignment(SwingConstants.CENTER);
		lblPics.setBounds(118, 310, 85, 13);
		add(lblPics);

		lblPortail = new JLabel("Portail");
		lblPortail.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortail.setBounds(247, 310, 85, 13);
		add(lblPortail);

		lblDrapeau = new JLabel("Drapeau");
		lblDrapeau.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrapeau.setBounds(118, 435, 85, 13);
		add(lblDrapeau);

		lblVaisseau = new JLabel("Vaisseau");
		lblVaisseau.setHorizontalAlignment(SwingConstants.CENTER);
		lblVaisseau.setBounds(247, 435, 85, 13);
		add(lblVaisseau);

		lblReinitialiser = new JLabel("Réinitialiser");
		lblReinitialiser.setHorizontalAlignment(SwingConstants.CENTER);
		lblReinitialiser.setBounds(50, 576, 85, 13);
		add(lblReinitialiser);

		lblSupprimer = new JLabel("Supprimer");
		lblSupprimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupprimer.setBounds(178, 576, 85, 13);
		add(lblSupprimer);

		lblRotation = new JLabel("Rotation");
		lblRotation.setHorizontalAlignment(SwingConstants.CENTER);
		lblRotation.setBounds(302, 576, 85, 13);
		add(lblRotation);

		lblSauvegarder = new JLabel("Sauvegarder");
		lblSauvegarder.setHorizontalAlignment(SwingConstants.CENTER);
		lblSauvegarder.setBounds(178, 694, 85, 13);
		add(lblSauvegarder);

		lblInteractifs = new JLabel("Interactifs");
		lblInteractifs.setBounds(64, 192, 85, 13);
		add(lblInteractifs);

		lblActions = new JLabel("Actions");
		lblActions.setBounds(64, 458, 85, 13);
		add(lblActions);
/**
		JButton btnChargement = new JButton("Charger test");
		btnChargement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charger();

			}

		});
		btnChargement.setBounds(166, 20, 85, 21);
		add(btnChargement);
		*/
	}

	/**
	 * Charge le niveau test
	 */
	// Kitimir Yim
	private void charger() {
		Niveau charger = Sauvegarder.chargerNiveau("test");

		grille.setTableau(charger.getTabEmplacement());

	}

	/**
	 * Sauvegardé le niveau crée dans le mode éditeur
	 * 
	 * @param niveau
	 */
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