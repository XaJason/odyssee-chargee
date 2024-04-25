package panneaux;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dessin.Grille;
import niveau.GestionnaireDeNiveaux;
import niveau.Niveau;
import niveau.Sauvegarder;
import tuile.Carre;
import tuile.Drapeau;
import tuile.Pics;
import tuile.Portail;
import tuile.TriangleEquilateral;
import tuile.TriangleRectangle;
import tuile.VaisseauImage;
import utilitaires.OutilsImage;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel du mode éditeur
 * 
 * @author Jason Xa
 * @author Kitimir Yim
 * @author Enuel René Valentin Kizozo Izia
 * 
 */

public class PanelEditeur extends JPanel {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -1637257199908540129L;

	/**
	 * Grille du mode éditeur
	 */
	private Grille grille;
	/**
	 * compteur pour le nombre max de niveau
	 */
	private int compteur = 0;

	/**
	 * nombre max de niveau possible dans la liste
	 */
	private static final int MAX_NIVEAUX = 10;

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
	private JButton btnRotationPrePlacement;
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
	private JLabel lblRotationPrePlacement;
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
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);

	/** Case à cocher pour l'affichage du quadrillage */
	private JCheckBox chckbxGrille;
	/** Type de la tuile selectionnée **/
	private PanelTuileTemp panelTuileTemp;
	private JToggleButton btnRotationPostPlacement;
	private JLabel lblRotationPostPlacement;

	/**
	 * Voici la méthode qui permettra à un objet de s'ajouter en tant qu'écouteur
	 * 
	 * @param listener L'objet PropertyChangeListener à ajouter comme écouteur de
	 *                 propriété.
	 */
	// Kitimir Yim
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}

	/**
	 * Implémente le panel et ses fonctionnalités
	 */
	// Kitimir Yim
	public PanelEditeur() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1) {
					grille.gererSupprimer();
				}
			}
		});
		setLayout(null);

		grille = new Grille();
		grille.setBounds(410, 38, 1000, 750);
		add(grille);

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
		leveeEvenement();
		btnCarre = new JButton();
		btnCarre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Carre());
				panelTuileTemp.setTuile(new Carre());
				afficherSelection();
				repaint();
			}
		});
		btnCarre.setBounds(50, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("carre.jpg", btnCarre);
		add(btnCarre);

		btnTriangleRectangle = new JButton();
		btnTriangleRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new TriangleRectangle());
				panelTuileTemp.setTuile(new TriangleRectangle());
				afficherSelection();
				repaint();
			}
		});
		btnTriangleRectangle.setBounds(178, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_rectangle.png", btnTriangleRectangle);
		add(btnTriangleRectangle);

		btnTriangleEquilateral = new JButton();
		btnTriangleEquilateral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new TriangleEquilateral());
				panelTuileTemp.setTuile(new TriangleEquilateral());
				afficherSelection();
				repaint();

			}
		});
		btnTriangleEquilateral.setBounds(302, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_equilateral.png", btnTriangleEquilateral);
		add(btnTriangleEquilateral);

		btnPics = new JButton();
		btnPics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Pics());
				panelTuileTemp.setTuile(new Pics());
				afficherSelection();
				repaint();
			}

		});
		btnPics.setBounds(118, 215, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("pics.png", btnPics);
		add(btnPics);

		btnPortail = new JButton();
		btnPortail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Portail());
				panelTuileTemp.setTuile(new Portail());
				afficherSelection();
				repaint();

			}
		});
		btnPortail.setBounds(247, 215, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("portail.png", btnPortail);
		add(btnPortail);

		btnDrapeau = new JButton();
		btnDrapeau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new Drapeau());
				panelTuileTemp.setTuile(new Drapeau());
				afficherSelection();
				repaint();
			}
		});
		btnDrapeau.setBounds(118, 340, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("drapeau.png", btnDrapeau);
		add(btnDrapeau);

		lblTypeSelectionne = new JLabel("Type de la tuile sélectionnée: ");
		lblTypeSelectionne.setBounds(10, 191, 156, 14);
		add(lblTypeSelectionne);
		btnReinitialiser = new JButton();
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.reinitialiser();
				grille.setSupprimer(false);
				grille.setTuile(null);
				panelTuileTemp.setTuile(null);
				repaint();
				btnDrapeau.setEnabled(true);
				btnVaisseau.setEnabled(true);
			}
		});
		btnReinitialiser.setBounds(118, 599, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("reinitialiser.png", btnReinitialiser);
		add(btnReinitialiser);

		btnSupprimer = new JButton();
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.gererSupprimer();
			}
		});
		btnSupprimer.setBounds(247, 599, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("supprimer.png", btnSupprimer);
		add(btnSupprimer);

		btnRotationPrePlacement = new JButton();
		btnRotationPrePlacement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setSupprimer(false);
				grille.rotation();
				panelTuileTemp.rotation();
				panelTuileTemp.repaint();
				repaint();
			}
		});
		btnRotationPrePlacement.setBounds(118, 480, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("rotation.png", btnRotationPrePlacement);
		add(btnRotationPrePlacement);

		btnSauvegarder = new JButton();
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (niveauBienConstruit()) {
					sauvegarderNiveau();
				}
			}

		});
		btnSauvegarder.setBounds(178, 727, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("sauvegarder.png", btnSauvegarder);
		add(btnSauvegarder);

		btnVaisseau = new JButton();
		btnVaisseau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setTuile(new VaisseauImage());
				panelTuileTemp.setTuile(new VaisseauImage());
				afficherSelection();
				repaint();
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
		lblBlocs.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlocs.setBounds(198, 38, 45, 13);
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

		lblDrapeau = new JLabel("Drapeau d'arrivée");
		lblDrapeau.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrapeau.setBounds(101, 435, 119, 13);
		add(lblDrapeau);

		lblVaisseau = new JLabel("Vaisseau (personnage)");
		lblVaisseau.setHorizontalAlignment(SwingConstants.CENTER);
		lblVaisseau.setBounds(215, 435, 148, 13);
		add(lblVaisseau);

		lblReinitialiser = new JLabel("Réinitialiser le niveau");
		lblReinitialiser.setHorizontalAlignment(SwingConstants.CENTER);
		lblReinitialiser.setBounds(89, 694, 142, 13);
		add(lblReinitialiser);

		lblSupprimer = new JLabel("Supprimer");
		lblSupprimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupprimer.setBounds(247, 694, 85, 13);
		add(lblSupprimer);

		lblRotationPrePlacement = new JLabel("Rotation Pre-Placement");
		lblRotationPrePlacement.setHorizontalAlignment(SwingConstants.CENTER);
		lblRotationPrePlacement.setBounds(89, 575, 131, 13);
		add(lblRotationPrePlacement);

		lblSauvegarder = new JLabel("Sauvegarder");
		lblSauvegarder.setHorizontalAlignment(SwingConstants.CENTER);
		lblSauvegarder.setBounds(178, 822, 85, 13);
		add(lblSauvegarder);

		lblInteractifs = new JLabel("Interactifs");
		lblInteractifs.setHorizontalAlignment(SwingConstants.CENTER);
		lblInteractifs.setBounds(178, 192, 85, 13);
		add(lblInteractifs);

		lblActions = new JLabel("Actions");
		lblActions.setHorizontalAlignment(SwingConstants.CENTER);
		lblActions.setBounds(10, 481, 85, 13);
		add(lblActions);

		chckbxGrille = new JCheckBox("Afficher la grille");
		chckbxGrille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.afficherGrille();
			}
		});
		chckbxGrille.setSelected(true);
		chckbxGrille.setBounds(1117, 122, 200, 21);
		add(chckbxGrille);

		panelTuileTemp = new PanelTuileTemp();
		panelTuileTemp.setBounds(8, 599, 100, 100);
		add(panelTuileTemp);

		btnRotationPostPlacement = new JToggleButton("");
		btnRotationPostPlacement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setRotationPostPlacement();
			}
		});
		btnRotationPostPlacement.setBounds(243, 480, 89, 85);
		add(btnRotationPostPlacement);

		lblRotationPostPlacement = new JLabel("Rotation Post-Placement");
		lblRotationPostPlacement.setBounds(226, 574, 124, 14);
		add(lblRotationPostPlacement);

	}

	/**
	 * Sauvegardé le niveau crée dans le mode éditeur
	 * 
	 * @param niveau Objet représentant le niveau
	 */
	// Kitimir Yim
	private void sauvegarder(Niveau niveau) {

		GestionnaireDeNiveaux.ajouter(niveau);
		Sauvegarder.sauvegarderNiveau(niveau, niveau.getNom());
		PCS.firePropertyChange("niveauCree", null, niveau);
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

	/**
	 * Retourne l'objet grille
	 * 
	 * @return grille
	 */
	// Enuel René Valentin Kizozo Izia
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Sauvegarde un niveau selon certaines conditions non reliées à la construction
	 * du niveau
	 * 
	 * @throws HeadlessException l'exception indirecte
	 */
	// Kitimir Yim
	private void sauvegarderNiveau() throws HeadlessException {
		if (compteur < MAX_NIVEAUX) {
			Object[] options = { "1", "2", "3" };
			String nom = (String) JOptionPane.showInputDialog(null, "Veuillez choisir un niveau:", "Choix du niveau",
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			if (nom != null) {
				Niveau niveauParDefaut = new Niveau(grille, "Niveau " + nom);
				sauvegarder(niveauParDefaut);
				compteur++;
			}
		} else {
			System.out.println("Nombre maximal de niveaux atteint!");
		}
	}

	/**
	 * Retourne vrai si le niveau contient un vaisseau et un drapeau
	 * 
	 * @return vrai si le niveau contient un vaisseau et un drapeau
	 */
	// Jason Xa
	private boolean niveauBienConstruit() {
		String tuilesManquantes = "";
		boolean vaisseauPresent = grille.contientVaisseau();
		boolean drapeauPresent = grille.contientDrapeau();

		if (!vaisseauPresent) {
			tuilesManquantes = tuilesManquantes + "\nVaisseau (personnage)";
		}
		if (!drapeauPresent) {
			tuilesManquantes = tuilesManquantes + "\nDrapeau d'arrivée";
		}
		if (!tuilesManquantes.isBlank()) {
			JOptionPane.showMessageDialog(null,
					"Objets à placer:" + tuilesManquantes + "\n\nVeuillez le(s) placer avant de sauvegarder le niveau.",
					"Niveau inadéquat", 2, null);
		}
		return grille.contientVaisseau() && grille.contientDrapeau();
	}

	/**
	 * S'occupe de la levée d'évenement
	 */
	// Jason Xa
	private void leveeEvenement() {

		grille.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
				case "Drapeau":
					btnDrapeau.setEnabled((boolean) evt.getNewValue());
					panelTuileTemp.setTuile(grille.getTuile());
					break;
				case "Vaisseau":
					btnVaisseau.setEnabled((boolean) evt.getNewValue());
					panelTuileTemp.setTuile(grille.getTuile());
					break;
				case "FocusGrille":
					grille.requestFocusInWindow();
					break;
				}
			}

		});

	}
}
