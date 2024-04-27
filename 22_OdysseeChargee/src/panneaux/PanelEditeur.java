package panneaux;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
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
	 * Bouton pour essayer le niveau créé
	 */
	private JButton btnEssayer;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type carré
	 */
	private JLabel lblCarre;
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
					supprimer();
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

		btnEssayer = new JButton("<html><center>ESSAYER<br>\r\n[CTRL + E]<html>");
		btnEssayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				essayer();
			}

		});
		btnEssayer.setBounds(342, 599, 85, 85);
		add(btnEssayer);
	}

	/**
	 * Méthode pour essayer le niveau
	 */
	// Kitimir Yim
	private void essayer() {
		Niveau niveauParDefaut = new Niveau(grille, "Niveau d'essai");
		PCS.fireIndexedPropertyChange("niveau essai", 0, 0, niveauParDefaut);
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
				selectionnerCarre();
			}
		});
		btnCarre.setBounds(50, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("carre.jpg", btnCarre);
		add(btnCarre);

		btnTriangleRectangle = new JButton();
		btnTriangleRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionnerTriangleRectangle();
			}
		});
		btnTriangleRectangle.setBounds(178, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_rectangle.png", btnTriangleRectangle);
		add(btnTriangleRectangle);

		btnTriangleEquilateral = new JButton();
		btnTriangleEquilateral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionnerTriangleEquilateral();
			}
		});
		btnTriangleEquilateral.setBounds(302, 61, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("triangle_equilateral.png", btnTriangleEquilateral);
		add(btnTriangleEquilateral);

		btnPics = new JButton();
		btnPics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionnerPics();
			}

		});
		btnPics.setBounds(118, 215, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("pics.png", btnPics);
		add(btnPics);

		btnPortail = new JButton();
		btnPortail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionnerPortail();

			}
		});
		btnPortail.setBounds(247, 215, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("portail.png", btnPortail);
		add(btnPortail);

		btnDrapeau = new JButton();
		btnDrapeau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionnerDrapeau();
			}
		});
		btnDrapeau.setBounds(118, 340, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("drapeau.png", btnDrapeau);
		add(btnDrapeau);

		lblTypeSelectionne = new JLabel("TUILE ACTUELLE:");
		lblTypeSelectionne.setBounds(30, 321, 269, 14);
		add(lblTypeSelectionne);
		btnReinitialiser = new JButton();
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reinitialiser();
			}
		});
		btnReinitialiser.setBounds(118, 599, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("reinitialiser.png", btnReinitialiser);
		add(btnReinitialiser);

		btnSupprimer = new JButton();
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supprimer();
			}
		});
		btnSupprimer.setBounds(247, 599, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("supprimer.png", btnSupprimer);
		add(btnSupprimer);

		btnRotationPrePlacement = new JButton();
		btnRotationPrePlacement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rotationnerAvant();
			}
		});
		btnRotationPrePlacement.setBounds(118, 480, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("rotation.png", btnRotationPrePlacement);
		add(btnRotationPrePlacement);

		btnSauvegarder = new JButton();
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sauvegarder();
			}

		});
		btnSauvegarder.setBounds(10, 599, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("sauvegarder.png", btnSauvegarder);
		add(btnSauvegarder);

		btnVaisseau = new JButton();
		btnVaisseau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionnerVaisseau();
			}
		});
		btnVaisseau.setBounds(247, 340, 85, 85);
		OutilsImage.lireImageEtPlacerSurBouton("vaisseau.png", btnVaisseau);
		add(btnVaisseau);

		lblCarre = new JLabel("[Q]");
		lblCarre.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarre.setBounds(27, 156, 131, 13);
		add(lblCarre);

		lblBlocs = new JLabel("BLOCS");
		lblBlocs.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlocs.setBounds(198, 38, 45, 13);
		add(lblBlocs);

		lblTriangleRectangle = new JLabel("[W]");
		lblTriangleRectangle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTriangleRectangle.setBounds(155, 156, 131, 13);
		add(lblTriangleRectangle);

		lblTriangleEquilateral = new JLabel("[E]");
		lblTriangleEquilateral.setHorizontalAlignment(SwingConstants.CENTER);
		lblTriangleEquilateral.setBounds(285, 156, 119, 13);
		add(lblTriangleEquilateral);

		lblPics = new JLabel("[A]");
		lblPics.setHorizontalAlignment(SwingConstants.CENTER);
		lblPics.setBounds(118, 310, 85, 13);
		add(lblPics);

		lblPortail = new JLabel("[S]");
		lblPortail.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortail.setBounds(247, 310, 85, 13);
		add(lblPortail);

		lblDrapeau = new JLabel("[D]");
		lblDrapeau.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrapeau.setBounds(101, 435, 119, 13);
		add(lblDrapeau);

		lblVaisseau = new JLabel("[F]");
		lblVaisseau.setHorizontalAlignment(SwingConstants.CENTER);
		lblVaisseau.setBounds(215, 435, 148, 13);
		add(lblVaisseau);

		lblReinitialiser = new JLabel("<html><center>RÉINITIALISER<br>\r\n[CTRL + R]<html>");
		lblReinitialiser.setHorizontalAlignment(SwingConstants.CENTER);
		lblReinitialiser.setBounds(127, 694, 67, 26);
		add(lblReinitialiser);

		lblSupprimer = new JLabel("<html><center>GÉRER LA SUPPRESSION<br>\r\n[ESPACE]<html>");
		lblSupprimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupprimer.setBounds(232, 694, 114, 39);
		add(lblSupprimer);

		lblRotationPrePlacement = new JLabel("<html><center>ROTATION PRÉ-PLACEMENT<br>\r\n[R]<html>");
		lblRotationPrePlacement.setHorizontalAlignment(SwingConstants.CENTER);
		lblRotationPrePlacement.setBounds(65, 568, 190, 31);
		add(lblRotationPrePlacement);

		lblSauvegarder = new JLabel("<html><center>SAUVEGARDER<br>\r\n[CTRL + S]<html>");
		lblSauvegarder.setHorizontalAlignment(SwingConstants.CENTER);
		lblSauvegarder.setBounds(18, 707, 69, 26);
		add(lblSauvegarder);

		lblInteractifs = new JLabel("INTERACTIFS");
		lblInteractifs.setHorizontalAlignment(SwingConstants.CENTER);
		lblInteractifs.setBounds(178, 192, 85, 13);
		add(lblInteractifs);

		lblActions = new JLabel("ACTIONS");
		lblActions.setHorizontalAlignment(SwingConstants.CENTER);
		lblActions.setBounds(10, 481, 85, 13);
		add(lblActions);

		chckbxGrille = new JCheckBox("Afficher la grille");
		chckbxGrille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gererGrille();
			}
		});
		chckbxGrille.setSelected(true);
		chckbxGrille.setBounds(1117, 122, 200, 21);
		add(chckbxGrille);

		panelTuileTemp = new PanelTuileTemp();
		panelTuileTemp.setBounds(10, 348, 100, 100);
		add(panelTuileTemp);

		btnRotationPostPlacement = new JToggleButton("");
		btnRotationPostPlacement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.setRotationPostPlacement();
				if (grille.getRotationPostPlacement()) {
					OutilsImage.lireImageEtPlacerSurBouton("rotationPostPlacementSelectionner.jpg",
							btnRotationPostPlacement);
					panelTuileTemp.setRotation(true);
				} else {
					OutilsImage.lireImageEtPlacerSurBouton("rotationPostPlacement.png", btnRotationPostPlacement);
					panelTuileTemp.setRotation(false);
				}
			}
		});
		btnRotationPostPlacement.setBounds(243, 480, 89, 85);
		OutilsImage.lireImageEtPlacerSurBouton("rotationPostPlacement.png", btnRotationPostPlacement);
		add(btnRotationPostPlacement);

		lblRotationPostPlacement = new JLabel("<html><center>ROTATION POST-PLACEMENT<br>\r\n[R]<html>");
		lblRotationPostPlacement.setBounds(247, 568, 138, 26);
		add(lblRotationPostPlacement);

	}

	/**
	 * Sauvegarder le niveau en argument
	 * 
	 * @param niveau le niveau à sauvegarder
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
				case "Sauvegarder":
					sauvegarder();
					break;
				case "Sélectionner carré":
					selectionnerCarre();
					break;
				case "Sélectionner triangle rectangle":
					selectionnerTriangleRectangle();
					break;
				case "Sélectionner triangle équilatéral":
					selectionnerTriangleEquilateral();
					break;
				case "Sélectionner pics":
					selectionnerPics();
					break;
				case "Sélectionner portail":
					selectionnerPortail();
					break;
				case "Sélectionner drapeau":
					selectionnerDrapeau();
					break;
				case "Sélectionner vaisseau":
					selectionnerVaisseau();
					break;
				case "Rotation pré-placement":
					rotationnerAvant();
					break;
				case "Rotation post-placement":
					rotationnerApresPlacement();
					break;
				case "Réinitialiser":
					reinitialiser();
					break;
				case "Essayer le niveau":
					essayer();
					break;

				}
			}

		});

	}

	/**
	 * Sélectionne le vaisseau
	 */
	// Jason Xa
	private void selectionnerVaisseau() {
		grille.setTuile(new VaisseauImage());
		panelTuileTemp.setTuile(new VaisseauImage());
		afficherSelection();
		repaint();
	}

	/**
	 * Sélectionne le bloc carré
	 */
	// Jason Xa
	private void selectionnerCarre() {
		grille.setTuile(new Carre());
		panelTuileTemp.setTuile(new Carre());
		afficherSelection();
		repaint();
	}

	/**
	 * Sélectionne le bloc triangle rectangle
	 */
	// Jason Xa
	private void selectionnerTriangleRectangle() {
		grille.setTuile(new TriangleRectangle());
		panelTuileTemp.setTuile(new TriangleRectangle());
		afficherSelection();
		repaint();
	}

	/**
	 * Sélectionne le bloc triangle équilatéral
	 */
	// Jason Xa
	private void selectionnerTriangleEquilateral() {
		grille.setTuile(new TriangleEquilateral());
		panelTuileTemp.setTuile(new TriangleEquilateral());
		afficherSelection();
		repaint();
	}

	/**
	 * Sélectionne les pics
	 */
	// Jason Xa
	private void selectionnerPics() {
		grille.setTuile(new Pics());
		panelTuileTemp.setTuile(new Pics());
		afficherSelection();
		repaint();
	}

	/**
	 * Sélectionne le portail
	 */
	// Jason Xa
	private void selectionnerPortail() {
		grille.setTuile(new Portail());
		panelTuileTemp.setTuile(new Portail());
		afficherSelection();
		repaint();
	}

	/**
	 * Sélectionne le drapeau
	 */
	// Jason Xa
	private void selectionnerDrapeau() {

		grille.setTuile(new Drapeau());
		panelTuileTemp.setTuile(new Drapeau());
		afficherSelection();
		repaint();

	}

	/**
	 * Réinitialise cet éditeur de niveaux
	 */
	// Giroux
	private void reinitialiser() {
		grille.reinitialiser();
		grille.setSupprimer(false);
		grille.setTuile(null);
		panelTuileTemp.setTuile(null);
		repaint();
		btnDrapeau.setEnabled(true);
		btnVaisseau.setEnabled(true);
	}

	/**
	 * Gère la suppression
	 */
	// Giroux
	private void supprimer() {
		grille.gererSupprimer();
	}

	/**
	 * Gère la rotation de la tuile actuelle
	 */
	// Giroux
	private void rotationnerAvant() {
		grille.setSupprimer(false);
		grille.rotation();
		panelTuileTemp.rotation();
		panelTuileTemp.repaint();
		repaint();
	}

	/**
	 * Gère la sauvegarde du niveau
	 * 
	 * @throws HeadlessException
	 */
	// Jason Xa
	private void sauvegarder() throws HeadlessException {
		if (niveauBienConstruit()) {
			sauvegarderNiveau();
		}
	}

	/**
	 * Gère l'affichage de la grille
	 */
	// Giroux
	private void gererGrille() {
		grille.afficherGrille();
	}

	/**
	 * Gère la rotation d'une tuile déjà placée
	 */
	// Giroux
	private void rotationnerApresPlacement() {
		grille.setRotationPostPlacement();
	}
}
