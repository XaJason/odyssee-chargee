package panneaux;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import javax.swing.border.LineBorder;

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
 * @author Enuel René Valentin Kizozo Izia
 * @author Giroux
 * @author Jason Xa
 * @author Kitimir Yim
 */
public class PanelEditeur extends JPanel {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -1637257199908540129L;

	/** bouton permettant la sélection de la tuile de type carré */
	private JButton btnCarre;

	/** bouton permettant la sélection de la tuile de type drapeau */
	private JButton btnDrapeau;
	/**
	 * Bouton pour essayer le niveau créé
	 */
	private JButton btnEssayer;
	/** bouton permettant la sélection de la tuile de type pics */
	private JButton btnPics;
	/** bouton permettant la sélection de la tuile de type portail */
	private JButton btnPortail;
	/** bouton permettant la réinitialisation de la grille */
	private JButton btnReinitialiser;
	/** Bouton à deux états pour gérer le mode de rotation post-placement */
	private JToggleButton btnRotationPostPlacement;
	/** bouton permettant de gérer la rotation de nouvelles tuiles */
	private JButton btnRotationPrePlacement;
	/** bouton permettant de gérer la sauvegarde du niveau associée à la grille */
	private JButton btnSauvegarder;
	/** bouton permettant de gérer la suppression de tuile */
	private JButton btnSupprimer;
	/** bouton permettant la sélection de la tuile de type triangle équilatéral */
	private JButton btnTriangleEquilateral;
	/** bouton permettant la sélection de la tuile de type triangle rectangle */
	private JButton btnTriangleRectangle;
	/** bouton permettant de la sélection de la tuile de type vaisseau */
	private JButton btnVaisseau;
	/** Case à cocher pour l'affichage du quadrillage */
	private JCheckBox chckbxGrille;
	/**
	 * Grille du mode éditeur
	 */
	private Grille grille;
	/** étiquette servant à identifier le regroupement de boutons d'action */
	private JLabel lblActions;
	/**
	 * étiquette servant à identifier le regroupement de boutons permettant la
	 * sélection de tuile (blocs)
	 */
	private JLabel lblBlocs;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type carré
	 */
	private JLabel lblCarre;
	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type drapeau
	 */
	private JLabel lblDrapeau;
	/**
	 * Étiquette servant à identifier le bouton pour essayer le niveau construit
	 */
	private JLabel lblEssayer;
	/**
	 * étiquette servant à identifier le regroupement de boutons permettant la
	 * sélection de tuile interactive
	 */
	private JLabel lblInteractifs;
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
	 * étiquette servant à identifier bouton permettant la réinitialisation de la
	 * grille
	 */
	private JLabel lblReinitialiser;
	/**
	 * Étiquette servant à identifier le bouton à deux états pour la rotation
	 * post-placement
	 */
	private JLabel lblRotationPostPlacement;
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
	 * étiquette servant à identifier le bouton permettant de gérer la suppression
	 * de tuile
	 */
	private JLabel lblSupprimer;

	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type triangle équilatéral
	 */
	private JLabel lblTriangleEquilateral;

	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type triangle rectangle
	 */
	private JLabel lblTriangleRectangle;
	/** étiquette servant à afficher le type de tuile sélectionné */
	private JLabel lblTypeSelectionne;

	/**
	 * étiquette servant à identifier le bouton permettant la sélection de la tuile
	 * de type vaisseau
	 */
	private JLabel lblVaisseau;

	/** Panneau d'affichage servant à afficher la tuile ou le mode sélectionné **/
	private PanelTuileTemp panelTuileTemp;
	/**
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);
	/**
	 * Boolean de si sauvegarder ou pas
	 */
	private boolean sauvegarde = true;

	/**
	 * Implémente le panel et ses fonctionnalités
	 */
	// Jason Xa
	public PanelEditeur() {
		setBackground(new Color(238, 246, 180));

		setBounds(new Rectangle(0, 0, 1920, 1080));
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				rotationnerAvant(Integer.signum(e.getWheelRotation()));
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					supprimer();
				}
			}
		});
		setLayout(null);

		grille = new Grille();
		grille.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		grille.setOpaque(false);
		grille.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				grille.requestFocusInWindow();
			}
		});
		grille.setBounds(10, 10, 1000, 750);
		add(grille);

		creerSectionBoutons();

		modificationGrille();
	}

	/**
	 * Voici la méthode qui permettra à un objet de s'ajouter en tant qu'écouteur
	 *
	 * @param listener L'objet PropertyChangeListener à ajouter comme écouteur de
	 *                 propriété.
	 */
	// Kitimir Yim
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
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
	 * Getter pour le boolean de sauvegarde
	 *
	 * @return sauvegarde boolean
	 */
	// Kitimir Yim
	public boolean getSauvegarde() {

		return this.sauvegarde;
	}

	/**
	 * Setter pour le boolean de sauvegarde
	 *
	 * @param choix nouvelle valeur de vérité pour la sauvegarde
	 */
	// Kitimir Yim
	public void setSauvegarde(boolean choix) {

		this.sauvegarde = choix;
	}

	/**
	 * Affiche le type de tuile sélectionné et désactive le mode de suppression de
	 * tuile
	 */
	// Jason Xa
	private void afficherSelection() {
		grille.setSupprimer(false);
	}

	/**
	 * Vérifie que tous les portails ont une paire
	 *
	 * @param tuilesManquantes Une chaîne de caractères indiquant les tuiles
	 *                         manquantes
	 * @return La chaîne de caractères mise à jour avec la nouvelle tuille manquante
	 *         s'il y a lieu
	 */
	// Enuel René Valentin Kizozo Izia
	private String conditionPortails(String tuilesManquantes) {
		if (!grille.portailsTousLies()) {
			tuilesManquantes = tuilesManquantes + "\n     - Portail";
		}
		return tuilesManquantes;
	}

	/**
	 * Création des boutons d'action pour la création du niveau
	 */
	// Jason Xa
	private void creerSectionBoutons() {
		leveeEvenement();

		chckbxGrille = new JCheckBox("Afficher la grille");
		chckbxGrille.setForeground(new Color(51, 51, 51));
		chckbxGrille.setContentAreaFilled(false);
		chckbxGrille.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		chckbxGrille.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gererGrille();
			}
		});
		chckbxGrille.setSelected(true);
		chckbxGrille.setBounds(1226, 27, 121, 27);
		add(chckbxGrille);
		btnCarre = new JButton();
		btnCarre.setBounds(1095, 243, 60, 60);
		add(btnCarre);
		btnCarre.setText("Q");
		btnCarre.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectionnerCarre();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("carre.jpg", btnCarre);

		btnTriangleRectangle = new JButton();
		btnTriangleRectangle.setBounds(1207, 243, 60, 60);
		add(btnTriangleRectangle);
		btnTriangleRectangle.setText("W");
		btnTriangleRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectionnerTriangleRectangle();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("triangle_rectangle.png", btnTriangleRectangle);

		btnTriangleEquilateral = new JButton();
		btnTriangleEquilateral.setBounds(1317, 243, 60, 60);
		add(btnTriangleEquilateral);
		btnTriangleEquilateral.setText("E");
		btnTriangleEquilateral.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectionnerTriangleEquilateral();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("triangle_equilateral.png", btnTriangleEquilateral);

		lblBlocs = new JLabel("BLOCS");
		lblBlocs.setForeground(new Color(102, 51, 0));
		lblBlocs.setBounds(1065, 197, 51, 22);
		add(lblBlocs);
		lblBlocs.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblBlocs.setHorizontalAlignment(SwingConstants.CENTER);

		lblCarre = new JLabel("[Q]");
		lblCarre.setForeground(new Color(51, 51, 51));
		lblCarre.setBounds(1115, 321, 21, 18);
		add(lblCarre);
		lblCarre.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblCarre.setHorizontalAlignment(SwingConstants.CENTER);

		lblTriangleRectangle = new JLabel("[W]");
		lblTriangleRectangle.setForeground(new Color(51, 51, 51));
		lblTriangleRectangle.setBounds(1217, 321, 41, 18);
		add(lblTriangleRectangle);
		lblTriangleRectangle.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblTriangleRectangle.setHorizontalAlignment(SwingConstants.CENTER);

		lblTriangleEquilateral = new JLabel("[E]");
		lblTriangleEquilateral.setForeground(new Color(51, 51, 51));
		lblTriangleEquilateral.setBounds(1339, 321, 18, 18);
		add(lblTriangleEquilateral);
		lblTriangleEquilateral.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblTriangleEquilateral.setHorizontalAlignment(SwingConstants.CENTER);

		lblInteractifs = new JLabel("INTERACTIFS");
		lblInteractifs.setForeground(new Color(102, 51, 0));
		lblInteractifs.setBounds(1065, 356, 111, 22);
		add(lblInteractifs);
		lblInteractifs.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblInteractifs.setHorizontalAlignment(SwingConstants.CENTER);

		btnPics = new JButton();
		btnPics.setBounds(1097, 395, 60, 60);
		add(btnPics);
		btnPics.setText("A");
		btnPics.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectionnerPics();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("pics.png", btnPics);

		btnPortail = new JButton();
		btnPortail.setBounds(1182, 395, 60, 60);
		add(btnPortail);
		btnPortail.setText("S");
		btnPortail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectionnerPortail();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("portail.png", btnPortail);

		lblPics = new JLabel("[A]");
		lblPics.setForeground(new Color(51, 51, 51));
		lblPics.setBounds(1118, 470, 18, 18);
		add(lblPics);
		lblPics.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblPics.setHorizontalAlignment(SwingConstants.CENTER);

		lblPortail = new JLabel("[S]");
		lblPortail.setForeground(new Color(51, 51, 51));
		lblPortail.setBounds(1193, 470, 39, 18);
		add(lblPortail);
		lblPortail.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblPortail.setHorizontalAlignment(SwingConstants.CENTER);

		lblTypeSelectionne = new JLabel("<html><center>TUILE OU MODE<br>\r\nSÉLECTIONNÉ<html>");
		lblTypeSelectionne.setForeground(new Color(102, 51, 0));
		lblTypeSelectionne.setBounds(1050, 10, 131, 66);
		add(lblTypeSelectionne);
		lblTypeSelectionne.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		btnDrapeau = new JButton();
		btnDrapeau.setBounds(1267, 395, 60, 60);
		add(btnDrapeau);
		btnDrapeau.setText("D");
		btnDrapeau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectionnerDrapeau();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("drapeau.png", btnDrapeau);

		btnVaisseau = new JButton();
		btnVaisseau.setBounds(1352, 395, 60, 60);
		add(btnVaisseau);
		btnVaisseau.setText("F");
		btnVaisseau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectionnerVaisseau();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("vaisseau.png", btnVaisseau);

		lblDrapeau = new JLabel("[D]");
		lblDrapeau.setForeground(new Color(51, 51, 51));
		lblDrapeau.setBounds(1288, 470, 19, 18);
		add(lblDrapeau);
		lblDrapeau.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblDrapeau.setHorizontalAlignment(SwingConstants.CENTER);

		lblVaisseau = new JLabel("[F]");
		lblVaisseau.setForeground(new Color(51, 51, 51));
		lblVaisseau.setBounds(1374, 470, 17, 18);
		add(lblVaisseau);
		lblVaisseau.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblVaisseau.setHorizontalAlignment(SwingConstants.CENTER);

		panelTuileTemp = new PanelTuileTemp();
		panelTuileTemp.setOpaque(false);
		panelTuileTemp.setRotation(false);
		panelTuileTemp.setBounds(1065, 76, 100, 100);
		add(panelTuileTemp);

		lblActions = new JLabel("ACTIONS");
		lblActions.setForeground(new Color(102, 51, 0));
		lblActions.setBounds(1065, 510, 75, 22);
		add(lblActions);
		lblActions.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblActions.setHorizontalAlignment(SwingConstants.CENTER);

		btnRotationPrePlacement = new JButton();
		btnRotationPrePlacement.setBounds(1205, 101, 50, 50);
		add(btnRotationPrePlacement);
		btnRotationPrePlacement.setText("R");
		btnRotationPrePlacement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rotationnerAvant(4); // quart de rotation
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("rotation.png", btnRotationPrePlacement);

		btnRotationPostPlacement = new JToggleButton("T");
		btnRotationPostPlacement.setBounds(1095, 542, 60, 60);
		add(btnRotationPostPlacement);
		btnRotationPostPlacement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gererRotationPostPlacement();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("rotationPostPlacement.png", btnRotationPostPlacement);

		lblRotationPostPlacement = new JLabel("<html><center>ROTATION<br>POST-PLACEMENT<br>\r\n[T]<html>");
		lblRotationPostPlacement.setForeground(new Color(51, 51, 51));
		lblRotationPostPlacement.setBounds(1085, 610, 84, 39);
		add(lblRotationPostPlacement);
		lblRotationPostPlacement.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));

		lblRotationPrePlacement = new JLabel(
				"<html><trailing>ROTATION<br>PRÉ-PLACEMENT<br>\r\n[R] OU DÉFILEMENT<br>MOLETTE<html>");
		lblRotationPrePlacement.setForeground(new Color(102, 51, 0));
		lblRotationPrePlacement.setBounds(1285, 81, 130, 90);
		add(lblRotationPrePlacement);
		lblRotationPrePlacement.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblRotationPrePlacement.setHorizontalAlignment(SwingConstants.CENTER);

		btnSauvegarder = new JButton();
		btnSauvegarder.setBounds(1317, 659, 60, 60);
		add(btnSauvegarder);
		btnSauvegarder.setText("Sauvegarder");
		btnSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sauvegarder();
			}

		});
		OutilsImage.lireImageEtPlacerSurBouton("sauvegarder.png", btnSauvegarder);

		lblSauvegarder = new JLabel("<html><center>SAUVEGARDER<br>\r\n[CTRL + S]<html>");
		lblSauvegarder.setForeground(new Color(51, 51, 51));
		lblSauvegarder.setBounds(1314, 721, 69, 26);
		add(lblSauvegarder);
		lblSauvegarder.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));
		lblSauvegarder.setHorizontalAlignment(SwingConstants.CENTER);
		btnReinitialiser = new JButton();
		btnReinitialiser.setBounds(1317, 542, 60, 60);
		add(btnReinitialiser);
		btnReinitialiser.setText("Réinitialiser");
		btnReinitialiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reinitialiser();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("reinitialiser.png", btnReinitialiser);

		lblReinitialiser = new JLabel("<html><center>RÉINITIALISER<br>\r\n[CTRL + R]<html>");
		lblReinitialiser.setForeground(new Color(51, 51, 51));
		lblReinitialiser.setBounds(1311, 610, 77, 26);
		add(lblReinitialiser);
		lblReinitialiser.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));
		lblReinitialiser.setHorizontalAlignment(SwingConstants.CENTER);

		btnSupprimer = new JButton();
		btnSupprimer.setBounds(1207, 542, 60, 60);
		add(btnSupprimer);
		btnSupprimer.setText("Suppression");
		btnSupprimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				supprimer();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("supprimer.png", btnSupprimer);

		lblSupprimer = new JLabel("<html><center>SUPPRESSION<br>\r\n[ESPACE],<br>CLIC DROIT<br><html>");
		lblSupprimer.setForeground(new Color(51, 51, 51));
		lblSupprimer.setBounds(1200, 610, 68, 39);
		add(lblSupprimer);
		lblSupprimer.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));
		lblSupprimer.setHorizontalAlignment(SwingConstants.CENTER);

		btnEssayer = new JButton("ESSAYER LE NIVEAU");
		btnEssayer.setBounds(1095, 659, 60, 60);
		add(btnEssayer);
		btnEssayer.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		btnEssayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				essayerNiveau();
			}

		});
		OutilsImage.lireImageEtPlacerSurBouton("essayer.png", btnEssayer);

		lblEssayer = new JLabel("<html><center>ESSAYER LE NIVEAU<br>\r\n[CTRL + E]<html>");
		lblEssayer.setForeground(new Color(51, 51, 51));
		lblEssayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblEssayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));
		lblEssayer.setBounds(1079, 721, 97, 39);
		add(lblEssayer);

	}

	/**
	 * Méthode qui désactive les booléans de rotation post placement de grille et de
	 * la fenêtre de la tuile temporaire
	 */
	// Giroux
	private void desactiveBooleanPostRotation() {
		btnRotationPostPlacement.setEnabled(true);
		grille.setRotationPostPlacement(false);
		panelTuileTemp.setRotation(false);
		OutilsImage.lireImageEtPlacerSurBouton("rotationPostPlacement.png", btnRotationPostPlacement);
	}

	/**
	 * Méthode qui désactive les booléans de rotation supprimer de grille et de la
	 * fenêtre de la tuile temporaire
	 */
	// Giroux
	private void desactiveBooleanSupprimer() {
		grille.setSupprimer(false);
		panelTuileTemp.setSupprimer(false);
	}

	/**
	 * Vérifie les conditions pour essayer un niveau, lance un événement pour
	 * accéder au mode jeu et réinitialise certains paramètres de la grille
	 */
	// Enuel René Valentin Kizozo Izia
	private void essayerNiveau() {
		if (niveauBienConstruit("d'essayer")) {
			transfertVersModeJeu();
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
	 * Méthode qui met la grille en mode post-rotation et qui change la couleur du
	 * bouton
	 */
	// Giroux
	private void gererRotationPostPlacement() {
		grille.setRotationPostPlacement();
		if (grille.getRotationPostPlacement()) {
			OutilsImage.lireImageEtPlacerSurBouton("rotationPostPlacementSelectionner.jpg", btnRotationPostPlacement);
			panelTuileTemp.setRotation(true);
		} else {
			OutilsImage.lireImageEtPlacerSurBouton("rotationPostPlacement.png", btnRotationPostPlacement);
			panelTuileTemp.setRotation(false);
		}
	}

	/**
	 * S'occupe de la levée d'évenement
	 */
	// Jason Xa
	private void leveeEvenement() {

		grille.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
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
					rotationnerAvant(4);
					break;
				case "Rotation post-placement":
					rotationnerApresPlacement();
					break;
				case "Réinitialiser":
					reinitialiser();
					break;
				case "Essayer le niveau":
					transfertVersModeJeu();
					break;
				case "Supprimer":
					supprimer();
					break;
				}
			}

		});

	}

	/**
	 * Récupere le message de la grille
	 */
	// Kitimir Yim
	private void modificationGrille() {
		grille.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("Modification")) {
					sauvegarde = false;

				}

			}
		});
	}

	/**
	 * Retourne vrai si le niveau contient un vaisseau, un drapeau et un nombre pair
	 * de portail. Affiche également une fenêtre pop-up lorsque le niveau n'est pas
	 * bien construit comme avertissement.
	 *
	 * @param tacheAEffectuer La tâche à effectuer qui nécessite que le niveau soit
	 *                        bien construit
	 * @return vrai si le niveau contient un vaisseau et un drapeau
	 */
	// Jason Xa
	private boolean niveauBienConstruit(String tacheAEffectuer) {
		String tuilesManquantes = "";
		boolean vaisseauPresent = grille.contientVaisseau();
		boolean drapeauPresent = grille.contientDrapeau();

		tuilesManquantes = conditionPortails(tuilesManquantes);
		if (!vaisseauPresent) {
			tuilesManquantes = tuilesManquantes + "\n     - Vaisseau (personnage)";
		}
		if (!drapeauPresent) {
			tuilesManquantes = tuilesManquantes + "\n     - Drapeau d'arrivée";
		}
		if (!tuilesManquantes.isBlank()) {
			JOptionPane.showMessageDialog(null, "Objets à placer:" + tuilesManquantes
					+ "\n\nVeuillez le(s) placer avant " + tacheAEffectuer + " le niveau.", "Niveau inadéquat", 2,
					null);
		}
		return (grille.contientVaisseau() && grille.contientDrapeau() && grille.portailsTousLies());
	}

	/**
	 * Réinitialise cet éditeur de niveau
	 */
	// Giroux
	private void reinitialiser() {
		if (JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir réinitialiser l'éditeur de niveau?",
				"Confirmation de réinitialisation", JOptionPane.YES_NO_OPTION) == 0) {
			grille.reinitialiser();
			grille.setSupprimer(false);
			grille.setTuile(null);
			panelTuileTemp.setTuile(null);
			grille.setRotationPostPlacement(false);
			repaint();
			btnDrapeau.setEnabled(true);
			btnVaisseau.setEnabled(true);
			desactiveBooleanPostRotation();
			desactiveBooleanSupprimer();
		}
	}

	/**
	 * Réinitialise cet éditeur de niveau sauf le niveau
	 */
	// Jason Xa
	private void reinitialiserSaufNiveau() {
		grille.setSupprimer(false);
		grille.setTuile(null);
		panelTuileTemp.setTuile(null);
		grille.setExterieurComposant(true);
		grille.setRotationPostPlacement(false);
		desactiveBooleanSupprimer();
		repaint();
	}

	/**
	 * Gère la rotation d'une tuile déjà placée
	 */
	// Giroux
	private void rotationnerApresPlacement() {
		gererRotationPostPlacement();

	}

	/**
	 * Gère la rotation de la tuile actuelle
	 *
	 * @param facteur nombre de seixième de rotation horaire à appliquer
	 */
	// Giroux
	private void rotationnerAvant(int facteur) {
		grille.setSupprimer(false);
		grille.rotation(facteur);
		panelTuileTemp.rotation(facteur);
		panelTuileTemp.repaint();
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
		repaint();
	}

	/**
	 * Gère la sauvegarde du niveau
	 *
	 * @throws HeadlessException exception sans tête
	 */
	// Jason Xa
	private void sauvegarder() throws HeadlessException {
		if (niveauBienConstruit("de sauvegarder")) {
			sauvegarde = true;
			sauvegarderNiveau();
			desactiveBooleanPostRotation();
			desactiveBooleanSupprimer();
		}
	}

	/**
	 * Sauvegarder le niveau en argument
	 *
	 * @param niveau le niveau à sauvegarder
	 */
	// Kitimir Yim
	private void sauvegarder(Niveau niveau) {

		GestionnaireDeNiveaux.ajouter(niveau);
		Sauvegarder.sauvegarderNiveauMesTrucs(niveau, niveau.getNom());

	}

	/**
	 * Sauvegarde un niveau selon certaines conditions non reliées à la construction
	 * du niveau
	 *
	 */
	// Kitimir Yim
	private void sauvegarderNiveau() {
		String nom = JOptionPane.showInputDialog(null, "Veuillez entrer le nom du niveau:", "Nom du niveau",
				JOptionPane.PLAIN_MESSAGE);

		if (nom != null && !nom.trim().isEmpty()) {
			Niveau niveauParDefaut = new Niveau(grille, nom);
			sauvegarder(niveauParDefaut);
		} else {

			JOptionPane.showMessageDialog(null, "La sauvegarde est annulée.", "Annulation de sauvegarde",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Sélectionne le bloc carré
	 */
	// Jason Xa
	private void selectionnerCarre() {
		if (!(grille.getTuile() == null) && grille.getTuile().getType() == "Carré") {
			grille.setTuile(null);
			panelTuileTemp.setTuile(null);
		} else {

			grille.setTuile(new Carre());
			panelTuileTemp.setTuile(new Carre());
		}
		afficherSelection();
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
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
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
		repaint();

	}

	/**
	 * Sélectionne les pics
	 */
	// Jason Xa
	private void selectionnerPics() {

		if (!(grille.getTuile() == null) && grille.getTuile().getType() == "Pics") {
			grille.setTuile(null);
			panelTuileTemp.setTuile(null);
		} else {
			grille.setTuile(new Pics());
			panelTuileTemp.setTuile(new Pics());
		}
		afficherSelection();
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
		repaint();
	}

	/**
	 * Sélectionne le portail
	 */
	// Jason Xa
	private void selectionnerPortail() {
		if (!(grille.getTuile() == null) && grille.getTuile().getType() == "Portail") {
			grille.setTuile(null);
			panelTuileTemp.setTuile(null);
		} else {
			grille.setTuile(new Portail());
			panelTuileTemp.setTuile(new Portail());
		}
		afficherSelection();
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
		repaint();
	}

	/**
	 * Sélectionne le bloc triangle équilatéral
	 */
	// Jason Xa
	private void selectionnerTriangleEquilateral() {
		if (!(grille.getTuile() == null) && grille.getTuile().getType() == "Triangle équilatéral") {
			grille.setTuile(null);
			panelTuileTemp.setTuile(null);
		} else {
			grille.setTuile(new TriangleEquilateral());
			panelTuileTemp.setTuile(new TriangleEquilateral());
		}
		afficherSelection();
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
		repaint();
	}

	/**
	 * Sélectionne le bloc triangle rectangle
	 */
	// Jason Xa
	private void selectionnerTriangleRectangle() {
		if (!(grille.getTuile() == null) && grille.getTuile().getType() == "Triangle rectangle") {
			grille.setTuile(null);
			panelTuileTemp.setTuile(null);
		} else {
			grille.setTuile(new TriangleRectangle());
			panelTuileTemp.setTuile(new TriangleRectangle());
		}
		afficherSelection();
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
		repaint();
	}

	/**
	 * Sélectionne le vaisseau
	 */
	// Jason Xa
	private void selectionnerVaisseau() {
		grille.setTuile(new VaisseauImage());
		panelTuileTemp.setTuile(new VaisseauImage());
		afficherSelection();
		desactiveBooleanPostRotation();
		desactiveBooleanSupprimer();
		repaint();
	}

	/**
	 * Gère la suppression
	 */
	// Giroux
	private void supprimer() {
		grille.gererSupprimer();
		panelTuileTemp.gererSupprimer();
		desactiveBooleanPostRotation();
	}

	/**
	 * Permet d'accéder au mode jeu et réinitialise certains paramètres de la grille
	 */
	// Kitimir Yim
	private void transfertVersModeJeu() {
		Niveau niveauParDefaut = new Niveau(grille, "Niveau d'essai");
		PCS.fireIndexedPropertyChange("niveau essai", 0, 0, niveauParDefaut);
		reinitialiserSaufNiveau();
	}
}
