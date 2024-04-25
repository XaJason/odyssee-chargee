package panneaux;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dessin.ZoneAnimationPhysique;
import physique.MoteurPhysique;
import utilitaires.OutilsImage;

/**
 * Panel du mode de jeu
 * 
 * @author Kitimir Yim
 * @author Enuel René Valentin Kizozo Izia
 * @author Giroux
 */
public class PanelJeu extends JPanel {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 7125958637120092540L;

	/** Zone d'animation physique utilisée pour les tests */
	private ZoneAnimationPhysique zoneAnimationPhysique;

	/** Bouton pour démarrer l'animation */
	private JButton btnDemarrer;

	/** Bouton pour arrêter l'animation */
	private JButton btnArreter;

	/** Bouton pour afficher la prochaine image de l'animation */
	private JButton btnProchaineImage;

	/** Bouton pour redémarrer l'animation */
	private JButton btnRedemarrer;

	/** Bouton pour réinitialiser les paramètres de l'animation */
	private JButton btnReinitialiser;

	/** Tourniquet pour définir le pas de simulation de l'animation */
	private JSpinner spnDeltaT;

	/** Tourniquet pour définir la masse du vaisseau */
	private JSpinner spnMasseVaisseau;

	/** Tourniquet pour définir la charge du vaisseau */
	private JSpinner spnChargeVaisseau;

	/** Tourniquet pour définir la charge de la plaque chargée */
	private JSpinner spnChargePlaque;

	/**
	 * Tourniquet pour définir l'accélération gravtitationnelle présente dans le
	 * niveau
	 **/
	private JSpinner spnGravite;

	/**
	 * Tourniquet pour définir le coéfficient de frottement statique des surfaces du
	 * niveau
	 **/
	private JSpinner spnCoefFrictionStat;

	/**
	 * Tourniquet pour définir le coéfficient de frottement cinétique des surfaces
	 * du niveau
	 **/
	private JSpinner spnCoefFrictionCine;

	/** Panneau de regroupement des entrées **/
	private JPanel panelEntree;

	/** Panel qui regroupe les boutons pour la plaque chargée **/
	private JPanel panelPlaque;
	/** Bouton à deux états pour sélectionner la plaque **/
	private JToggleButton tglbtnPlaque;
	/** Bouton pour mettre la plaque positive **/
	private JButton btnChargePositive;
	/** Bouton pour mettre la plaque negative **/
	private JButton btnChargeNegative;
	/** Étiquette qui indique la charge de la plaque **/
	private JLabel lblEtatPlaque;
	/** Boolean qui indique la nature de la charge de la plaque **/
	private boolean plaquePositive = true;
	/** Nombre restant de plaque **/
	private int nbPlaqueRestante = 4;

	/** Vitesse affichée **/
	private String vitesseString = "0";
	/** Force électrique affichée **/
	private String forceElecString = "0";
	/** Gravité affichée **/
	private String forceGravString = "0";
	/** Accélération affichée **/
	private String acceString = "0";
	/** Champ électrique affichée **/
	private String champElecString = "0";
	/** Position du vaisseau affichée **/
	private String positionString = "0";

	/** Label de la vitesse **/
	private JLabel labelVitesse;
	/** Label de l'accélération **/
	private JLabel labelAcceleration;
	/** Label de la force électrique **/
	private JLabel labelForceElectrique;
	/** Label de la force de gravité **/
	private JLabel labelForceGravite;
	/** Label du champ électrique **/
	private JLabel labelChampElectrique;
	/** Label de la position **/
	private JLabel labelPosition;

	private JLabel lblIndiceChargeVaisseau;

	/**
	 * Implémente le panel et ses fonctionnalités
	 */
	// Kitimir Yim
	public PanelJeu() {
		setLayout(null);

		panelEntree = new JPanel();
		panelEntree.setBorder(BorderFactory.createTitledBorder("Entrées"));

		panelEntree.setBounds(10, 242, 376, 480);
		add(panelEntree);
		panelEntree.setLayout(null);

		JLabel lblMasseVaisseau = new JLabel("Masse du vaisseau (kg) :");
		lblMasseVaisseau.setBounds(10, 15, 180, 26);
		panelEntree.add(lblMasseVaisseau);

		JLabel lblCharge = new JLabel("Charge du vaisseau (Coulomb) :");
		lblCharge.setBounds(10, 88, 180, 26);
		panelEntree.add(lblCharge);

		JLabel lblGravite = new JLabel("Gravité (m/s²) :");
		lblGravite.setBounds(10, 155, 120, 26);
		panelEntree.add(lblGravite);

		JLabel lblChargePlaques = new JLabel("Charge de la plaque");
		lblChargePlaques.setBounds(10, 215, 180, 26);
		panelEntree.add(lblChargePlaques);

		JLabel lblCoefFrotStat = new JLabel("Coefficient de frottement statique :");
		lblCoefFrotStat.setBounds(10, 295, 215, 26);
		panelEntree.add(lblCoefFrotStat);

		JLabel lblCoefFrotCine = new JLabel("Coefficient de frottement cinétique :");
		lblCoefFrotCine.setBounds(10, 365, 215, 26);
		panelEntree.add(lblCoefFrotCine);

		creerZoneAnimationPhysiqueEtBoutonsDAnimation();
		lierTourniquetsAvecNiveau();

		JPanel panelSortie = new JPanel();
		panelSortie.setLayout(null);
		panelSortie.setBorder(BorderFactory.createTitledBorder("Sorties"));
		panelSortie.setBounds(1181, 44, 376, 274);
		add(panelSortie);

		JLabel lblVitesse = new JLabel("Vitesse (m/s):");
		lblVitesse.setBounds(35, 37, 100, 26);
		panelSortie.add(lblVitesse);

		JLabel lblAcceleration = new JLabel("Accélération (m/s^2):");
		lblAcceleration.setBounds(35, 73, 110, 26);
		panelSortie.add(lblAcceleration);

		JLabel lblForceElectrique = new JLabel("Force électrique (N):");
		lblForceElectrique.setBounds(35, 113, 100, 26);
		panelSortie.add(lblForceElectrique);

		JLabel lblForceGravite = new JLabel("Force gravité (N):");
		lblForceGravite.setBounds(35, 153, 100, 26);
		panelSortie.add(lblForceGravite);

		JLabel lblChampElectrique = new JLabel("Champ électrique (N/m):");
		lblChampElectrique.setBounds(35, 197, 128, 26);
		panelSortie.add(lblChampElectrique);

		JLabel lblPosition = new JLabel("Position (m):");
		lblPosition.setBounds(35, 233, 100, 26);
		panelSortie.add(lblPosition);

		labelVitesse = new JLabel(vitesseString);
		labelVitesse.setBounds(216, 37, 150, 22);
		panelSortie.add(labelVitesse);

		labelAcceleration = new JLabel(acceString);
		labelAcceleration.setBounds(216, 73, 150, 22);
		panelSortie.add(labelAcceleration);

		labelForceElectrique = new JLabel(forceElecString);
		labelForceElectrique.setBounds(216, 113, 150, 22);
		panelSortie.add(labelForceElectrique);

		labelForceGravite = new JLabel(forceGravString);
		labelForceGravite.setBounds(216, 153, 150, 22);
		panelSortie.add(labelForceGravite);

		labelChampElectrique = new JLabel(champElecString);
		labelChampElectrique.setBounds(216, 197, 150, 22);
		panelSortie.add(labelChampElectrique);

		labelPosition = new JLabel(positionString);
		labelPosition.setBounds(216, 233, 150, 22);
		panelSortie.add(labelPosition);

		leveeEvenement();

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 44, 447, 187);
		add(panel);
		panel.setLayout(null);

		panelPlaque = new JPanel();
		panelPlaque.setBounds(6, 16, 276, 165);
		panel.add(panelPlaque);
		panelPlaque.setLayout(null);

		tglbtnPlaque = new JToggleButton("");
		tglbtnPlaque.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				zoneAnimationPhysique.setPlacementPlaque(tglbtnPlaque.isSelected());
				zoneAnimationPhysique.requestFocusInWindow();
			}
		});
		tglbtnPlaque.setBounds(53, 57, 212, 31);
		OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargePositive.png", tglbtnPlaque);
		panelPlaque.add(tglbtnPlaque);

		btnChargePositive = new JButton("");
		btnChargePositive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changementStatutPlaque(true);
				changerBoutonSignePositif();
				zoneAnimationPhysique.requestFocusInWindow();
			}
		});
		btnChargePositive.setBounds(10, 36, 33, 31);
		OutilsImage.lireImageEtPlacerSurBouton("ChargePositive.png", btnChargePositive);
		panelPlaque.add(btnChargePositive);

		btnChargeNegative = new JButton("");
		btnChargeNegative.addActionListener(new ActionListener() {
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
		lblEtatPlaque.setBounds(10, 11, 154, 14);
		panelPlaque.add(lblEtatPlaque);

		JLabel lblNbDePlaqueRestante = new JLabel("");
		lblNbDePlaqueRestante.setText("Il vous reste: " + nbPlaqueRestante + " restante(s)");
		lblNbDePlaqueRestante.setBounds(10, 120, 154, 14);
		panelPlaque.add(lblNbDePlaqueRestante);

		lblIndiceChargeVaisseau = new JLabel(
				"Utilisez les touches \"A\", \"S\", \"D\" pour contrôler la charge électrique du vaisseau!");
		lblIndiceChargeVaisseau.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblIndiceChargeVaisseau.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndiceChargeVaisseau.setBounds(550, 702, 603, 22);
		add(lblIndiceChargeVaisseau);

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
	 * Changer l'état du bouton positif et la charge de la plaque en conséquence
	 */
	// Enuel René Valentin Kizozo Izia
	private void changerBoutonSignePositif() {
		zoneAnimationPhysique.setPlaquePositive(true);
		btnChargePositive.setEnabled(false);
		btnChargeNegative.setEnabled(true);
	}

	/**
	 * Changer l'état du bouton négatif et la charge de la plaque en conséquence
	 */
	// Enuel René Valentin Kizozo Izia
	private void changerBoutonSigneNegatif() {
		zoneAnimationPhysique.setPlaquePositive(false);
		btnChargeNegative.setEnabled(false);
		btnChargePositive.setEnabled(true);
	}

	/**
	 * Créer la zone d'animation physique et
	 * les boutons d'animation sur le panneau mode jeu
	 */
	// Enuel René Valentin Kizozo Izia
	private void creerZoneAnimationPhysiqueEtBoutonsDAnimation() {
		zoneAnimationPhysique = new ZoneAnimationPhysique();
		zoneAnimationPhysique.setBounds(314, 31, 1000, 750);
		add(zoneAnimationPhysique);

		btnDemarrer = new JButton("Démarrer");
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.demarrer();
				btnProchaineImage.setEnabled(false);
				btnDemarrer.setEnabled(false);
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		btnDemarrer.setBounds(10, 10, 89, 23);
		add(btnDemarrer);

		btnArreter = new JButton("Arrêter");
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.arreter();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		btnArreter.setBounds(605, 809, 89, 23);
		add(btnArreter);

		btnRedemarrer = new JButton("Redémarrer");
		btnRedemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.recommencer();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		btnRedemarrer.setBounds(905, 809, 129, 23);
		add(btnRedemarrer);

		btnProchaineImage = new JButton("Prochaine image");
		btnProchaineImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.prochaineImage();
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		btnProchaineImage.setBounds(726, 809, 154, 23);
		add(btnProchaineImage);

		btnReinitialiser = new JButton("Réinitialiser");
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				reinitialiserZoneAnimation();
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		btnReinitialiser.setBounds(1062, 809, 129, 23);
		add(btnReinitialiser);
	}

	/**
	 * S'occupe de la levée d'évenement
	 */
	// Kitimir Yim
	private void leveeEvenement() {

		zoneAnimationPhysique.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("changerVitesse")) {
					double valeur = (double) evt.getNewValue();
					vitesseString = String.format("%.2f", valeur);
					labelVitesse.setText(vitesseString);
				}

				if (evt.getPropertyName().equals("changerAcceleration")) {
					double valeur = (double) evt.getNewValue();
					acceString = String.format("%.2f", valeur);
					labelAcceleration.setText(acceString);
				}

				if (evt.getPropertyName().equals("changerForceElec")) {
					double valeur = (double) evt.getNewValue();
					forceElecString = String.format("%.2f", valeur);
					labelForceElectrique.setText(forceElecString);
				}

				if (evt.getPropertyName().equals("changerForceGravite")) {
					double valeur = (double) evt.getNewValue();
					forceGravString = String.format("%.2f", valeur);
					labelForceGravite.setText(forceGravString);
				}
				if (evt.getPropertyName().equals("changerChampElec")) {
					double valeur = (double) evt.getNewValue();
					champElecString = String.format("%.2f", valeur);
					labelChampElectrique.setText(champElecString);
				}

				if (evt.getPropertyName().equals("changerPosition")) {
					double valeur = (double) evt.getNewValue();
					positionString = String.format("%.2f", valeur);
					labelPosition.setText(positionString);
				}
				if (evt.getPropertyName().equals("changementBouton")) {
					btnProchaineImage.setEnabled(true);
					btnDemarrer.setEnabled(true);

				}
				leveeEvenementCharge(evt);
			}

		});

	}

	/**
	 * Gère la levée d'évènement reliée à la gestion de la charge du vaisseau
	 * 
	 * @param evt l'évènement levé
	 */
	// Jason Xa
	private void leveeEvenementCharge(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("Charge négative")) {
			double valeur = (double) evt.getNewValue();
			spnChargeVaisseau.setValue(valeur);
		}

	}

	/**
	 * Lier les tourniques des entrées avec la zone d'animation physique (le niveau)
	 */
	// Enuel René Valentin Kizozo Izia
	private void lierTourniquetsAvecNiveau() {
		spnMasseVaisseau = new JSpinner();
		spnMasseVaisseau.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setMasseVaisseau((double) spnMasseVaisseau.getValue());
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		spnMasseVaisseau
				.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getMasseInitialeVaisseau(), 0.01, 20.0, 0.01));
		spnMasseVaisseau.setBounds(225, 11, 140, 35);
		((JSpinner.DefaultEditor) spnMasseVaisseau.getEditor()).getTextField().setEditable(false); // Désactive la zone
																									// d'entrée
		panelEntree.add(spnMasseVaisseau);

		spnChargeVaisseau = new JSpinner();
		spnChargeVaisseau.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setChargeVaisseau((double) spnChargeVaisseau.getValue());
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		spnChargeVaisseau.setModel(
				new SpinnerNumberModel(zoneAnimationPhysique.getChargeInitialeVaisseau(), -200.0, 200.0, 10.0));

		spnChargeVaisseau.setBounds(225, 81, 140, 35);
		((JSpinner.DefaultEditor) spnChargeVaisseau.getEditor()).getTextField().setEditable(false); // Désactive la zone
		// d'entrée
		panelEntree.add(spnChargeVaisseau);

		spnGravite = new JSpinner();
		spnGravite.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				MoteurPhysique.setAccelGrav((double) spnGravite.getValue());
				zoneAnimationPhysique.setForceGrav();
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		spnGravite.setModel(new SpinnerNumberModel(MoteurPhysique.getAccelGrav(), -24.8, -1.6, 0.1));
		spnGravite.setBounds(225, 151, 140, 35);
		((JSpinner.DefaultEditor) spnGravite.getEditor()).getTextField().setEditable(false); // Désactive la zone
																								// d'entrée
		panelEntree.add(spnGravite);

		spnChargePlaque = new JSpinner();
		spnChargePlaque.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setChargeDesPlaques((double) spnChargePlaque.getValue());
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		spnChargePlaque
				.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getChargeInitialePlaque(), 0.0, 100.0, 1.0));
		spnChargePlaque.setBounds(225, 221, 140, 35);
		((JSpinner.DefaultEditor) spnChargePlaque.getEditor()).getTextField().setEditable(false); // Désactive la zone
																									// d'entrée
		panelEntree.add(spnChargePlaque);

		spnCoefFrictionStat = new JSpinner();
		spnCoefFrictionStat.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// début
				MoteurPhysique.setCoeffFrotStat((double) spnCoefFrictionStat.getValue());
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		spnCoefFrictionStat.setModel(new SpinnerNumberModel(MoteurPhysique.getCoeffFrotStat(), 0.50, 1.0, 0.05));
		spnCoefFrictionStat.setBounds(225, 291, 140, 35);
		((JSpinner.DefaultEditor) spnCoefFrictionStat.getEditor()).getTextField().setEditable(false); // Désactive la
																										// zone d'entrée
		panelEntree.add(spnCoefFrictionStat);

		spnCoefFrictionCine = new JSpinner();
		spnCoefFrictionCine.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// début
				MoteurPhysique.setCoeffFrotCine((double) spnCoefFrictionCine.getValue());
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		spnCoefFrictionCine.setModel(new SpinnerNumberModel(MoteurPhysique.getCoeffFrotCine(), 0.35, 0.70, 0.05));
		spnCoefFrictionCine.setBounds(225, 361, 140, 35);
		((JSpinner.DefaultEditor) spnCoefFrictionCine.getEditor()).getTextField().setEditable(false); // Désactive la
																										// zone d'entrée
		panelEntree.add(spnCoefFrictionCine);

		spnDeltaT = new JSpinner();
		spnDeltaT.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setDeltaT((double) spnDeltaT.getValue());
				zoneAnimationPhysique.requestFocusInWindow();
				// fin
			}
		});
		spnDeltaT.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getDeltaT(), 0.001, 0.1, 0.001));
		spnDeltaT.setBounds(225, 431, 140, 35);
		((JSpinner.DefaultEditor) spnDeltaT.getEditor()).getTextField().setEditable(false); // Désactive la zone
																							// d'entrée
		panelEntree.add(spnDeltaT);

		JLabel lblDeltaT = new JLabel("Pas de simulation :");
		lblDeltaT.setBounds(10, 437, 180, 23);
		panelEntree.add(lblDeltaT);

		JLabel lblChargePlaqueSuite = new JLabel("en valeur absolue (Coulombs) :");
		lblChargePlaqueSuite.setBounds(10, 235, 205, 26);
		panelEntree.add(lblChargePlaqueSuite);
	}

	/**
	 * Reinitialise tout exactement dans l'etat de demarrage de l'application
	 */
	// Enuel René Valentin Kizozo Izia
	private void reinitialiserZoneAnimation() {
		zoneAnimationPhysique.reinitialiser();
		btnProchaineImage.setEnabled(true);
		btnDemarrer.setEnabled(true);

		spnDeltaT.setValue(zoneAnimationPhysique.getDeltaTInitial());
		spnChargeVaisseau.setValue(zoneAnimationPhysique.getChargeInitialeVaisseau());
		spnMasseVaisseau.setValue(zoneAnimationPhysique.getMasseInitialeVaisseau());
		spnChargePlaque.setValue(zoneAnimationPhysique.getChargeInitialePlaque());
	}// fin methode reinitialiserZoneAnimation

	/**
	 * Méthode qui effectue les changements des propriétés lors des changements de
	 * statut de la plaque
	 * 
	 * @param positif Indique true si la plaque devient positive, false si négative
	 */
	// Giroux
	private void changementStatutPlaque(boolean positif) {
		if (positif) {
			plaquePositive = true;
			lblEtatPlaque.setText("La plaque est: positive");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargePositive.png", tglbtnPlaque);
		} else {
			plaquePositive = false;
			lblEtatPlaque.setText("La plaque est: négative");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargeNegative.png", tglbtnPlaque);
		}
	}

	public ZoneAnimationPhysique getZoneAnimationPhysique() {
		return zoneAnimationPhysique;
	}

	public ZoneAnimationPhysique getZone() {
		return zoneAnimationPhysique;
	}
}
