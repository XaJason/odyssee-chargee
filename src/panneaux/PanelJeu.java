package panneaux;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dessin.ZoneAnimationPhysique;
import niveau.Niveau;
import physique.MoteurPhysique;
import physique.Vecteur2D;
import utilitaires.ConstanteComposantsSwing;
import utilitaires.OutilsImage;

/**
 * Panel du mode de jeu
 *
 * @author Enuel René Valentin Kizozo Izia
 * @author Giroux
 * @author Jason Xa
 * @author Kitimir Yim
 */
public class PanelJeu extends JPanel {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 7125958637120092540L;

	/** Accélération affichée **/
	private String acceString = "0";

	/** Bouton pour arrêter l'animation */
	private JButton btnArreter;

	/** Bouton pour mettre la plaque negative **/
	private JToggleButton btnChargeNegative;

	/** Bouton pour mettre la plaque positive **/
	private JToggleButton btnChargePositive;

	/** Bouton pour démarrer l'animation */
	private JButton btnDemarrer;

	/** Bouton pour afficher la prochaine image de l'animation */
	private JButton btnProchaineImage;

	/** Bouton pour redémarrer l'animation */
	private JButton btnRecommencer;

	/** Bouton pour réinitialiser les paramètres de l'animation */
	private JButton btnReinitialiser;

	/**
	 * Regroupe les deux boutons de charge
	 */
	private ButtonGroup buttonGroupCharges = new ButtonGroup();

	/** Champ électrique affichée **/
	private String champElecString = "0";

	/**
	 * Check box qui conrespond au mode jetpack
	 */
	private JCheckBox chckbxModeJetpack;

	/**
	 * Liste déroulante pour la sélection de la vitesse d'animation
	 */
	private JComboBox<Object> cmbVitesseAnimation;

	/** Le fond d'écran du panneau **/
	private FondEcran fondEcran;

	/** Force électrique affichée **/
	private String forceElecString = "0";
	/** Gravité affichée **/
	private String forceGravString = "0";
	/** Label de l'accélération **/
	private JLabel labelAcceleration;
	/** Label du champ électrique **/
	private JLabel labelChampElectrique;
	/** Label de la force électrique **/
	private JLabel labelForceElectrique;
	/** Label de la force de gravité **/
	private JLabel labelForceGravite;
	/** Label de la position **/
	private JLabel labelPosition;
	/** Label de la vitesse **/
	private JLabel labelVitesse;
	/** Étiquette qui indique la charge de la plaque **/
	private JLabel lblEtatPlaque;
	/**
	 * Étiquette pour indiquer à l'utilisateur les touches à utiliser pour contrôler
	 * la nature de la charge du vaisseau
	 */
	private JLabel lblIndiceChargeVaisseau;
	/**
	 * Étiquette d'indice pour l'utilisation des flèches du clavier lorsque le mode
	 * jetpack est activé
	 */
	private JLabel lblIndiceFleches;
	/** Étiquette qui indique le nombre de plaques disponibles à placer **/
	private JLabel lblNbDePlaqueRestante;

	/** Panneau de regroupement des entrées **/
	private JPanel panelEntree;
	/** Panel qui regroupe les boutons pour la plaque chargée **/
	private JPanel panelPlaque;
	/** Position du vaisseau affichée **/
	private String positionString = "0";
	/** Tourniquet pour définir la charge de la plaque chargée */
	private JSpinner spnChargePlaque;
	/** Tourniquet pour définir la charge du vaisseau */
	private JSpinner spnChargeVaisseau;
	/**
	 * Tourniquet pour définir le coéfficient de frottement cinétique des surfaces
	 * du niveau
	 **/
	private JSpinner spnCoefFrictionCine;
	/**
	 * Tourniquet pour définir le coéfficient de frottement statique des surfaces du
	 * niveau
	 **/
	private JSpinner spnCoefFrictionStat;
	/**
	 * Tourniquet pour définir l'accélération gravtitationnelle présente dans le
	 * niveau
	 **/
	private JSpinner spnGravite;
	/** Tourniquet pour définir la masse du vaisseau */
	private JSpinner spnMasseVaisseau;
	/** Bouton à deux états pour sélectionner la plaque **/
	private JToggleButton tglbtnPlaque;
	/** Vitesse affichée **/
	private String vitesseString = "0";

	/** Zone d'animation physique utilisée pour les tests */
	private ZoneAnimationPhysique zoneAnimationPhysique;

	/**
	 * Implémente le panel et ses fonctionnalités
	 */
	// Kitimir Yim
	public PanelJeu() {
		setLayout(null);
		setBounds(0, 25, ConstanteComposantsSwing.DIM_HORIZONTALE_APP, ConstanteComposantsSwing.DIM_VERTICALE_APP);

		panelEntree = new JPanel();
		panelEntree.setBorder(BorderFactory.createTitledBorder("Entrées"));

		panelEntree.setBounds(10, 242, 376, 480);
		add(panelEntree);
		panelEntree.setLayout(null);

		JLabel lblMasseVaisseau = new JLabel("Masse du vaisseau (kg) :");
		lblMasseVaisseau.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMasseVaisseau.setBounds(10, 15, 215, 26);
		panelEntree.add(lblMasseVaisseau);

		JLabel lblCharge = new JLabel("Charge du vaisseau (Coulomb) :");
		lblCharge.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCharge.setBounds(10, 88, 215, 26);
		panelEntree.add(lblCharge);

		JLabel lblGravite = new JLabel("Gravité (m/s²) :");
		lblGravite.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblGravite.setBounds(10, 155, 215, 26);
		panelEntree.add(lblGravite);

		JLabel lblChargePlaques = new JLabel("Charge des plaques");
		lblChargePlaques.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblChargePlaques.setBounds(10, 215, 215, 26);
		panelEntree.add(lblChargePlaques);

		JLabel lblCoefFrotStat = new JLabel("Coefficient de frottement statique :");
		lblCoefFrotStat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCoefFrotStat.setBounds(10, 295, 215, 26);
		panelEntree.add(lblCoefFrotStat);

		JLabel lblCoefFrotCine = new JLabel("Coefficient de frottement cinétique :");
		lblCoefFrotCine.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCoefFrotCine.setBounds(10, 365, 215, 26);
		panelEntree.add(lblCoefFrotCine);

		creerZoneAnimationPhysique();
		lierTourniquetsAvecNiveau();

		JPanel panelSortie = new JPanel();
		panelSortie.setLayout(null);
		panelSortie.setBorder(BorderFactory.createTitledBorder("Sorties"));
		panelSortie.setBounds(1406, 44, 376, 274);
		add(panelSortie);

		JLabel lblVitesse = new JLabel("Vitesse (m/s):");
		lblVitesse.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVitesse.setBounds(35, 37, 171, 26);
		panelSortie.add(lblVitesse);

		JLabel lblAcceleration = new JLabel("Accélération (m/s^2):");
		lblAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAcceleration.setBounds(35, 73, 171, 26);
		panelSortie.add(lblAcceleration);

		JLabel lblForceElectrique = new JLabel("Force électrique (N):");
		lblForceElectrique.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblForceElectrique.setBounds(35, 113, 171, 26);
		panelSortie.add(lblForceElectrique);

		JLabel lblForceGravite = new JLabel("Force gravité (N):");
		lblForceGravite.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblForceGravite.setBounds(35, 153, 171, 26);
		panelSortie.add(lblForceGravite);

		JLabel lblChampElectrique = new JLabel("Champ électrique (N/m):");
		lblChampElectrique.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblChampElectrique.setBounds(35, 197, 171, 26);
		panelSortie.add(lblChampElectrique);

		JLabel lblPosition = new JLabel("Position (m):");
		lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPosition.setBounds(35, 233, 171, 26);
		panelSortie.add(lblPosition);

		labelVitesse = new JLabel(vitesseString);
		labelVitesse.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelVitesse.setBounds(216, 37, 76, 22);
		panelSortie.add(labelVitesse);

		labelAcceleration = new JLabel(acceString);
		labelAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelAcceleration.setBounds(216, 73, 76, 22);
		panelSortie.add(labelAcceleration);

		labelForceElectrique = new JLabel(forceElecString);
		labelForceElectrique.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelForceElectrique.setBounds(216, 113, 76, 22);
		panelSortie.add(labelForceElectrique);

		labelForceGravite = new JLabel(forceGravString);
		labelForceGravite.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelForceGravite.setBounds(216, 153, 76, 22);
		panelSortie.add(labelForceGravite);

		labelChampElectrique = new JLabel(champElecString);
		labelChampElectrique.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelChampElectrique.setBounds(216, 197, 76, 22);
		panelSortie.add(labelChampElectrique);

		labelPosition = new JLabel(positionString);
		labelPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelPosition.setBounds(216, 233, 76, 22);
		panelSortie.add(labelPosition);

		leveesEvenements();

		JPanel panelInfosPlaque = new JPanel();
		panelInfosPlaque.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelInfosPlaque.setBounds(10, 44, 376, 187);
		add(panelInfosPlaque);
		panelInfosPlaque.setLayout(null);

		panelPlaque = new JPanel();
		panelPlaque.setBounds(6, 32, 276, 149);
		panelInfosPlaque.add(panelPlaque);
		panelPlaque.setLayout(null);

		tglbtnPlaque = new JToggleButton("");
		tglbtnPlaque.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				zoneAnimationPhysique.setPlacementPlaque(tglbtnPlaque.isSelected());
				zoneAnimationPhysique.requestFocusInWindow();

				imagePlaqueSelectionnee();
			}
		});
		tglbtnPlaque.setBounds(53, 57, 212, 31);
		OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargePositive.png", tglbtnPlaque);
		panelPlaque.add(tglbtnPlaque);

		btnChargePositive = new JToggleButton("");
		btnChargePositive.setSelected(true);
		buttonGroupCharges.add(btnChargePositive);
		btnChargePositive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changementStatutPlaque(true);
				changerBoutonSignePositif();
				zoneAnimationPhysique.requestFocusInWindow();
			}
		});
		btnChargePositive.setBounds(10, 36, 33, 31);
		OutilsImage.lireImageEtPlacerSurBouton("ChargePositive.png", btnChargePositive);
		panelPlaque.add(btnChargePositive);

		btnChargeNegative = new JToggleButton("");
		buttonGroupCharges.add(btnChargeNegative);
		btnChargeNegative.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changementStatutPlaque(false);
				changerBoutonSigneNegatif();
				zoneAnimationPhysique.requestFocusInWindow();
			}
		});
		btnChargeNegative.setBounds(10, 78, 33, 31);
		OutilsImage.lireImageEtPlacerSurBouton("ChargeNegative.png", btnChargeNegative);
		panelPlaque.add(btnChargeNegative);

		lblEtatPlaque = new JLabel("La plaque est: positive ");
		lblEtatPlaque.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEtatPlaque.setBounds(10, 11, 255, 14);
		panelPlaque.add(lblEtatPlaque);

		lblNbDePlaqueRestante = new JLabel("");
		lblNbDePlaqueRestante.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNbDePlaqueRestante
				.setText("Il vous reste " + zoneAnimationPhysique.getNbPlaquesRestantes() + " plaques à placer");
		lblNbDePlaqueRestante.setBounds(10, 120, 255, 14);
		panelPlaque.add(lblNbDePlaqueRestante);

		chckbxModeJetpack = new JCheckBox("Mode Jetpack");
		chckbxModeJetpack.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				gererModeJetpack(chckbxModeJetpack);
			}
		});
		chckbxModeJetpack.setBounds(6, 0, 286, 23);
		panelInfosPlaque.add(chckbxModeJetpack);

		fondEcran = new FondEcran("fondJeu.jpg", 1);
		fondEcran.setBounds(0, 0, 1920, 1080);
		add(fondEcran);
		fondEcran.setLayout(null);

		creerBoutonsDAnimationSurFondEcran();

	}

	/**
	 * Retourne la zone d'animation physique
	 *
	 * @return La zone d'animation physique
	 */
	// Enuel René Valentin Kizozo Izia
	public ZoneAnimationPhysique getZoneAnimationPhysique() {
		return zoneAnimationPhysique;
	}

	/**
	 * Modifie le niveau de la zone d'animation physique
	 *
	 * @param niveau Le niveau
	 */
	// Kitimir Yim
	public void modifierNiveauDeZoneAnimationPhysique(Niveau niveau) {
		zoneAnimationPhysique.setNiveau(niveau);
	}

	/**
	 * Modifie le niveau de la zone d'animation physique
	 *
	 * @param nomNiveau Le nom du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public void modifierNiveauDeZoneAnimationPhysique(String nomNiveau) {
		zoneAnimationPhysique.setNiveau(nomNiveau);
	}

	/**
	 * Modifie le niveau de la zone d'animation physique
	 *
	 * @param nomNiveau Le nom du niveau
	 */
	// Kitimir Yim
	public void modifierNiveauDeZoneAnimationPhysiqueDeBase(String nomNiveau) {
		zoneAnimationPhysique.setNiveauDeBase(nomNiveau);
	}

	/**
	 * Réinitialise le panneau et la zone d'animation du mode Jeu à l'état qu'il
	 * avait lors du démarrage de l'application
	 */
	// Enuel René Valentin Kizozo Izia
	public void reinitialiserPanneauEtZoneAnimation() {
		btnProchaineImage.setEnabled(true);
		btnDemarrer.setEnabled(true);
		btnRecommencer.setEnabled(false);

		cmbVitesseAnimation.setSelectedIndex(2);
		spnChargeVaisseau.setValue(zoneAnimationPhysique.getChargeInitialeVaisseau());
		spnMasseVaisseau.setValue(zoneAnimationPhysique.getMasseInitialeVaisseau());
		spnChargePlaque.setValue(zoneAnimationPhysique.getChargeInitialePlaque());
		spnGravite.setValue(MoteurPhysique.getAccelGravInitiale());
		spnCoefFrictionStat.setValue(MoteurPhysique.getCoeffFrotStatInitial());
		spnCoefFrictionCine.setValue(MoteurPhysique.getCoeffFrotCineInitial());

		zoneAnimationPhysique.reinitialiser();
	}

	/**
	 * Modifie le booléen indiquant si le mode jetpack est activé
	 *
	 * @param modeJetpack nouveau booléen indiquant si le mode jetpack est activé
	 */
	// Jason Xa
	public void setModeJetpack(boolean modeJetpack) {
		chckbxModeJetpack.setSelected(modeJetpack);
		zoneAnimationPhysique.setModeJetpack(modeJetpack);
		lblIndiceFleches.setVisible(modeJetpack);
	}

	/**
	 * Méthode qui effectue les changements des propriétés lors des changements de
	 * statut de la plaque
	 *
	 * @param positif Indique true si la plaque devient positive, false si négative
	 */
	// Giroux
	private void changementStatutPlaque(boolean positif) {
		if (positif && tglbtnPlaque.isSelected()) {
			zoneAnimationPhysique.setPlaquePositive(true);
			lblEtatPlaque.setText("La plaque est: positive");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargePositiveSelectionner.png", tglbtnPlaque);
		} else if (!positif && tglbtnPlaque.isSelected()) {
			zoneAnimationPhysique.setPlaquePositive(false);
			lblEtatPlaque.setText("La plaque est: négative");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargeNegativeSelectionner.png", tglbtnPlaque);
		} else if (positif && !tglbtnPlaque.isSelected()) {
			zoneAnimationPhysique.setPlaquePositive(false);
			lblEtatPlaque.setText("La plaque est: positive");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargePositive.png", tglbtnPlaque);
		} else {
			zoneAnimationPhysique.setPlaquePositive(true);
			lblEtatPlaque.setText("La plaque est: négative");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargeNegative.png", tglbtnPlaque);
		}

		repaint();
	}

	/**
	 * Changer l'état du bouton négatif et la charge de la plaque en conséquence
	 */
	// Enuel René Valentin Kizozo Izia
	private void changerBoutonSigneNegatif() {
		zoneAnimationPhysique.setPlaquePositive(false);

	}

	/**
	 * Changer l'état du bouton positif et la charge de la plaque en conséquence
	 */
	// Enuel René Valentin Kizozo Izia
	private void changerBoutonSignePositif() {
		zoneAnimationPhysique.setPlaquePositive(true);
	}

	/**
	 * Permet de changer la vitesse de l'animation à l'aide du temps du sleep
	 */
	// Enuel René Valentin Kizozo Izia
	private void changerVitesseAnimation() {
		int valeur;

		switch (cmbVitesseAnimation.getSelectedIndex()) {
		case 0:
			valeur = 2;
			break;
		case 1:
			valeur = 5;
			break;
		case 2:
			valeur = 8;
			break;
		case 3:
			valeur = 16;
			break;
		default:
			valeur = 20;
		}
		zoneAnimationPhysique.setTempsSleep(valeur);
	}

	/**
	 * Créer les boutons d'animation sur le fond d'écran du panneau mode jeu
	 */
	// Enuel René Valentin Kizozo Izia
	private void creerBoutonsDAnimationSurFondEcran() {
		btnDemarrer = new JButton("Démarrer");
		btnDemarrer.setBounds(420, 813, 175, 40);
		fondEcran.add(btnDemarrer);
		btnDemarrer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				zoneAnimationPhysique.demarrer();
				btnProchaineImage.setEnabled(false);
				btnDemarrer.setEnabled(false);
				btnRecommencer.setEnabled(true);
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("demarrer.png", btnDemarrer);

		btnArreter = new JButton("Arrêter");
		btnArreter.setBounds(620, 813, 175, 40);
		fondEcran.add(btnArreter);
		btnArreter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				zoneAnimationPhysique.arreter();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("arreter.png", btnArreter);

		btnProchaineImage = new JButton("Prochaine image");
		btnProchaineImage.setBounds(1220, 813, 190, 40);
		fondEcran.add(btnProchaineImage);
		btnProchaineImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				zoneAnimationPhysique.prochaineImage();
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("prochaineImage.png", btnProchaineImage);

		btnRecommencer = new JButton("Recommencer");
		btnRecommencer.setBounds(820, 813, 175, 40);
		fondEcran.add(btnRecommencer);
		btnRecommencer.setEnabled(false);
		btnRecommencer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				recommencerPanneauEtZoneAnimation();
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("recommencer.png", btnRecommencer);

		btnReinitialiser = new JButton("Réinitialiser");
		btnReinitialiser.setBounds(1020, 813, 175, 40);
		fondEcran.add(btnReinitialiser);
		btnReinitialiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				reinitialiserPanneauEtZoneAnimation();
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("réinitialiser.png", btnReinitialiser);

		lblIndiceChargeVaisseau = new JLabel(
				"Utilisez les touches \"A\", \"S\", \"D\" pour contrôler la charge électrique du vaisseau!");
		lblIndiceChargeVaisseau.setBounds(595, 891, 603, 22);
		fondEcran.add(lblIndiceChargeVaisseau);
		lblIndiceChargeVaisseau.setForeground(Color.GREEN);
		lblIndiceChargeVaisseau.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblIndiceChargeVaisseau.setHorizontalAlignment(SwingConstants.CENTER);

		lblIndiceFleches = new JLabel("Utilisez les flèches du clavier pour influencer l'accélération du vaisseau!");
		lblIndiceFleches.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndiceFleches.setForeground(Color.GREEN);
		lblIndiceFleches.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblIndiceFleches.setBounds(595, 924, 603, 22);
		fondEcran.add(lblIndiceFleches);
	}

	/**
	 * Créer la zone d'animation physique sur le panneau mode jeu
	 */
	// Enuel René Valentin Kizozo Izia
	private void creerZoneAnimationPhysique() {
		zoneAnimationPhysique = new ZoneAnimationPhysique();
		zoneAnimationPhysique.setBounds(396, 44, 1000, 750);
		add(zoneAnimationPhysique);
	}

	/**
	 * Gère la levée d'événement lié à la mise à jour du panneau du mode jeu suite
	 * au démarrage de l'animation à l'aide des touches du clavier
	 *
	 * @param evt L'événement qui a été lancé
	 */
	// Enuel René Valentin Kizozo Izia
	private void evenementMiseAJourDemarrage(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("Démarrer")) {
			btnProchaineImage.setEnabled(false);
			btnDemarrer.setEnabled(false);
			btnRecommencer.setEnabled(true);
			zoneAnimationPhysique.requestFocusInWindow();
		}
	}

	/**
	 * Gère la levée d'événement lié à la mise à jour de l'étiquette indiquant le
	 * nombre de plaques restantes à placer
	 *
	 * @param evt L'événement qui a été lancé
	 */
	// Enuel René Valentin Kizozo Izia
	private void evenementPlaquesRestantes(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("Plaques restantes")) {
			lblNbDePlaqueRestante.setText("Il vous reste " + evt.getNewValue() + " plaques à placer");
		}
	}

	/**
	 * Permet de déterminer s'il y a mode jetpack ou pas
	 *
	 * @param chckbxJetpack Le checkbox associé au mode jetpack
	 */
	// Giroux
	private void gererModeJetpack(JCheckBox chckbxJetpack) {
		zoneAnimationPhysique.setModeJetpack(chckbxJetpack.isSelected());
		lblIndiceFleches.setVisible(chckbxJetpack.isSelected());
		zoneAnimationPhysique.requestFocusInWindow();
	}

	/**
	 * Méthode qui met l'image de la plaque selectionnée lorsque celle-ci l'est
	 */
	// Giroux
	private void imagePlaqueSelectionnee() {
		if (btnChargePositive.isSelected()) {
			changementStatutPlaque(true);
		} else if (btnChargeNegative.isSelected()) {
			changementStatutPlaque(false);
		}
	}

	/**
	 * Gère la levée d'évènement reliée à la gestion de la charge du vaisseau
	 *
	 * @param evt l'évènement levé
	 */
	// Jason Xa
	private void leveeEvenementCharge(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "Charge négative":
			double valeur = (double) evt.getNewValue();
			spnChargeVaisseau.setValue(valeur);
			break;
		case "FocusZoneAnimationPhysique":
			zoneAnimationPhysique.requestFocusInWindow();
		}
	}

	/**
	 * S'occupe des levées d'évenements
	 */
	// Kitimir Yim
	private void leveesEvenements() {

		zoneAnimationPhysique.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("changerVitesse")) {
					double valeurX = (double) evt.getOldValue();
					String xString = String.format("%.2f", valeurX);

					double valeurY = (double) evt.getNewValue();
					String yString = String.format("%.2f", valeurY);

					labelVitesse.setText(xString + "i, " + yString + "j");
				}

				if (evt.getPropertyName().equals("changerAcceleration")) {
					double valeurX = ((Vecteur2D) evt.getNewValue()).getX();
					String xString = String.format("%.2f", valeurX);

					double valeurY = ((Vecteur2D) evt.getNewValue()).getY();
					String yString = String.format("%.2f", valeurY);

					labelAcceleration.setText(xString + "i, " + yString + "j");
				}

				if (evt.getPropertyName().equals("changerForceElec")) {
					double valeurX = ((Vecteur2D) evt.getNewValue()).getX();
					String xString = String.format("%.2f", valeurX);

					double valeurY = ((Vecteur2D) evt.getNewValue()).getY();
					String yString = String.format("%.2f", valeurY);

					labelForceElectrique.setText(xString + "i, " + yString + "j");
				}

				if (evt.getPropertyName().equals("changerForceGravite")) {
					double valeur = (double) evt.getNewValue();
					forceGravString = String.format("%.2f", valeur);
					labelForceGravite.setText(forceGravString + "j");
				}
				if (evt.getPropertyName().equals("changerChampElec")) {
					double valeurX = ((Vecteur2D) evt.getNewValue()).getX();
					String xString = String.format("%.2f", valeurX);

					double valeurY = ((Vecteur2D) evt.getNewValue()).getY();
					String yString = String.format("%.2f", valeurY);

					champElecString = xString + "i, " + yString + "j";
					labelChampElectrique.setText(champElecString);
				}

				if (evt.getPropertyName().equals("changerPosition")) {
					double valeurX = (double) evt.getOldValue();
					String xString = String.format("%.2f", valeurX);

					double valeurY = (double) evt.getNewValue();
					String yString = String.format("%.2f", valeurY);

					labelPosition.setText(xString + "i ," + yString + "j");
				}
				if (evt.getPropertyName().equals("Recommencer")) {
					recommencerPanneauEtZoneAnimation();

				}
				leveeEvenementCharge(evt);
				evenementMiseAJourDemarrage(evt);
				evenementPlaquesRestantes(evt);
			}

		});

	}

	/**
	 * Lier les tourniques des entrées avec la zone d'animation physique (le niveau)
	 */
	// Enuel René Valentin Kizozo Izia
	private void lierTourniquetsAvecNiveau() {
		spnMasseVaisseau = new JSpinner();
		spnMasseVaisseau.setFocusable(false);
		spnMasseVaisseau.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				zoneAnimationPhysique.setMasseVaisseau((double) spnMasseVaisseau.getValue());
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		spnMasseVaisseau
				.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getMasseInitialeVaisseau(), 0.01, 20.0, 0.01));
		spnMasseVaisseau.setBounds(225, 11, 140, 35);
		((JSpinner.DefaultEditor) spnMasseVaisseau.getEditor()).getTextField().setEditable(false); // Désactive la zone
																									// d'entrée
		panelEntree.add(spnMasseVaisseau);

		spnChargeVaisseau = new JSpinner();
		spnChargeVaisseau.setFocusable(false);
		spnChargeVaisseau.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				zoneAnimationPhysique.setChargeVaisseau((double) spnChargeVaisseau.getValue());
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		spnChargeVaisseau
				.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getChargeInitialeVaisseau(), -20.0, 20.0, 1.0));
		spnChargeVaisseau.setBounds(225, 81, 140, 35);
		((JSpinner.DefaultEditor) spnChargeVaisseau.getEditor()).getTextField().setEditable(false); // Désactive la zone
		// d'entrée
		panelEntree.add(spnChargeVaisseau);

		spnGravite = new JSpinner();
		spnGravite.setFocusable(false);
		spnGravite.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				MoteurPhysique.setAccelGrav((double) spnGravite.getValue());
				zoneAnimationPhysique.setForceGrav();
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		spnGravite.setModel(new SpinnerNumberModel(MoteurPhysique.getAccelGrav(), -25, 25, 1));
		spnGravite.setBounds(225, 151, 140, 35);
		((JSpinner.DefaultEditor) spnGravite.getEditor()).getTextField().setEditable(false); // Désactive la zone
																								// d'entrée
		panelEntree.add(spnGravite);

		spnChargePlaque = new JSpinner();
		spnChargePlaque.setFocusable(false);
		spnChargePlaque.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				zoneAnimationPhysique.setChargeDesPlaques((double) spnChargePlaque.getValue());
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		spnChargePlaque
				.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getChargeInitialePlaque(), 0.0, 20.0, 1.0));
		spnChargePlaque.setBounds(225, 221, 140, 35);
		((JSpinner.DefaultEditor) spnChargePlaque.getEditor()).getTextField().setEditable(false); // Désactive la zone
																									// d'entrée
		panelEntree.add(spnChargePlaque);

		spnCoefFrictionStat = new JSpinner();
		spnCoefFrictionStat.setFocusable(false);
		spnCoefFrictionStat.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				MoteurPhysique.setCoeffFrotStat((double) spnCoefFrictionStat.getValue());
				spnCoefFrictionCine.setModel(new SpinnerNumberModel(MoteurPhysique.getCoeffFrotCine(), 0.0,
						MoteurPhysique.getCoeffFrotStat(), 0.05));
				((JSpinner.DefaultEditor) spnCoefFrictionCine.getEditor()).getTextField().setEditable(false);
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		spnCoefFrictionStat.setModel(new SpinnerNumberModel(MoteurPhysique.getCoeffFrotStat(),
				MoteurPhysique.getCoeffFrotCine(), 1.0, 0.05));
		spnCoefFrictionStat.setBounds(225, 291, 140, 35);
		((JSpinner.DefaultEditor) spnCoefFrictionStat.getEditor()).getTextField().setEditable(false); // Désactive la

		panelEntree.add(spnCoefFrictionStat);

		spnCoefFrictionCine = new JSpinner();
		spnCoefFrictionCine.setFocusable(false);
		spnCoefFrictionCine.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				MoteurPhysique.setCoeffFrotCine((double) spnCoefFrictionCine.getValue());
				spnCoefFrictionStat.setModel(new SpinnerNumberModel(MoteurPhysique.getCoeffFrotStat(),
						MoteurPhysique.getCoeffFrotCine(), 1.0, 0.05));
				((JSpinner.DefaultEditor) spnCoefFrictionStat.getEditor()).getTextField().setEditable(false);
				zoneAnimationPhysique.requestFocusInWindow();

			}
		});
		spnCoefFrictionCine.setModel(new SpinnerNumberModel(MoteurPhysique.getCoeffFrotCine(), 0.0,
				MoteurPhysique.getCoeffFrotStat(), 0.05));
		spnCoefFrictionCine.setBounds(225, 361, 140, 35);
		((JSpinner.DefaultEditor) spnCoefFrictionCine.getEditor()).getTextField().setEditable(false); // Désactive la

		panelEntree.add(spnCoefFrictionCine);

		cmbVitesseAnimation = new JComboBox<>();
		cmbVitesseAnimation.setFocusable(false);
		cmbVitesseAnimation.setFocusTraversalKeysEnabled(false);
		cmbVitesseAnimation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changerVitesseAnimation();
				zoneAnimationPhysique.requestFocusInWindow();
			}
		});
		cmbVitesseAnimation.setModel(
				new DefaultComboBoxModel<>(new String[] { "Très rapide", "Rapide", "Moyen", "Lent", "Très lent" }));
		cmbVitesseAnimation.setSelectedIndex(2);
		cmbVitesseAnimation.setBounds(225, 431, 140, 35);
		panelEntree.add(cmbVitesseAnimation);

		JLabel lblVitesseAnimation = new JLabel("Vitesse d'animation :");
		lblVitesseAnimation.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVitesseAnimation.setBounds(10, 437, 215, 23);
		panelEntree.add(lblVitesseAnimation);

		JLabel lblChargePlaqueSuite = new JLabel("en valeur absolue (Coulombs) :");
		lblChargePlaqueSuite.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblChargePlaqueSuite.setBounds(10, 235, 215, 26);
		panelEntree.add(lblChargePlaqueSuite);

	}

	/**
	 * Reecommencer le panneau et la zone d'animation du mode Jeu à l'état qu'il
	 * avait lors du démarrage de l'application
	 */
	// Kitimir Yim
	private void recommencerPanneauEtZoneAnimation() {
		btnProchaineImage.setEnabled(true);
		btnDemarrer.setEnabled(true);
		btnRecommencer.setEnabled(false);

		zoneAnimationPhysique.recommencer();
	}
}
