package panneaux;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dessin.ZoneAnimationPhysique;
import utilitaires.OutilsImage;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import physique.MoteurPhysique;
import javax.swing.JToggleButton;

/**
 * Panel du mode de jeu
 * 
 * @author Kitimir Yim
 * @author Enuel René Valentin Kizozo Izia
 */
public class PanelModeJeu extends JPanel {
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
	
	/** Tourniquet pour définir l'accélération gravtitationnelle présente dans le niveau **/
	private JSpinner spnGravite;
	
	/** Tourniquet pour définir le coéfficient de frottement statique des surfaces du niveau **/
	private JSpinner spnCoefFrictionStat;
	
	/** Tourniquet pour définir le coéfficient de frottement cinétique des surfaces du niveau **/
	private JSpinner spnCoefFrictionCine;
	
	/** Panneau de regroupement des entrées **/
	private JPanel panelEntree;
	
	/**
	 * Label pour le nom du niveau
	 */
	private JLabel labelNomNiveau;
	/** Panel qui regroupe les boutons pour la plaque chargée**/
	private JPanel panelPlaque;
	/** Bouton à deux états pour sélectionner la plaque**/
	private JToggleButton tglbtnPlaque;
	/** Bouton pour mettre la plaque positive**/
	private JButton btnChargePositive;
	/** Bouton pour mettre la plaque negative**/
	private JButton btnChargeNegative;
	/** Étiquette qui indique la charge de la plaque**/
	private JLabel lblEtatPlaque;
	/** Boolean qui indique la nature de la charge de la plaque **/
	private Boolean plaquePositive = true;
	/** Nombre restant de plaque**/
	private int nbPlaqueRestante = 4;
	
	/**
	 * Implémente le panel et ses fonctionnalités
	 */
	// Kitimir Yim
	public PanelModeJeu() {
		setLayout(null);
		
		panelEntree = new JPanel();
		panelEntree.setBorder(BorderFactory.createTitledBorder("Entrées"));

		panelEntree.setBounds(1271, 31, 376, 480);
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
		panelSortie.setBounds(1271, 522, 376, 274);
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

		JTextArea textAreaVitesse = new JTextArea();
		textAreaVitesse.setBounds(216, 37, 150, 22);
		panelSortie.add(textAreaVitesse);

		JTextArea textAreaAcceleration = new JTextArea();
		textAreaAcceleration.setBounds(216, 73, 150, 22);
		panelSortie.add(textAreaAcceleration);

		JTextArea textAreaForceElectrique = new JTextArea();
		textAreaForceElectrique.setEditable(false);
		textAreaForceElectrique.setBounds(216, 113, 150, 22);
		panelSortie.add(textAreaForceElectrique);

		JTextArea textAreaForceGravite = new JTextArea();
		textAreaForceGravite.setEditable(false);
		textAreaForceGravite.setBounds(216, 153, 150, 22);
		panelSortie.add(textAreaForceGravite);

		JTextArea textAreaChampElectrique = new JTextArea();
		textAreaChampElectrique.setEditable(false);
		textAreaChampElectrique.setBounds(216, 197, 150, 22);
		panelSortie.add(textAreaChampElectrique);

		JTextArea textAreaPosition = new JTextArea();
		textAreaPosition.setEditable(false);
		textAreaPosition.setBounds(216, 233, 150, 22);
		panelSortie.add(textAreaPosition);

		labelNomNiveau = new JLabel();
		labelNomNiveau.setBounds(50, 20, 400, 30);
		add(labelNomNiveau);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 324, 447, 187);
		add(panel);
		panel.setLayout(null);
		
		panelPlaque = new JPanel();
		panelPlaque.setBounds(6, 16, 431, 165);
		panel.add(panelPlaque);
		panelPlaque.setLayout(null);

		tglbtnPlaque = new JToggleButton("");
		tglbtnPlaque.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				zoneAnimationPhysique.setPlacementPlaque(tglbtnPlaque.isSelected());
			}
		});
		tglbtnPlaque.setBounds(155, 63, 212, 31);
		OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargePositive.png", tglbtnPlaque);
		panelPlaque.add(tglbtnPlaque);
		
		btnChargePositive = new JButton("");
		btnChargePositive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changementStatutPlaque(true);
				zoneAnimationPhysique.setPlaquePositive(true);
			}
		});
		btnChargePositive.setBounds(41, 36, 33, 31);
		OutilsImage.lireImageEtPlacerSurBouton("ChargePositive.png", btnChargePositive);
		panelPlaque.add(btnChargePositive);
		
		btnChargeNegative = new JButton("");
		btnChargeNegative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changementStatutPlaque(false);
				zoneAnimationPhysique.setPlaquePositive(false);
			}
		});
		btnChargeNegative.setBounds(41, 78, 33, 31);
		OutilsImage.lireImageEtPlacerSurBouton("ChargeNegative.png", btnChargeNegative);
		panelPlaque.add(btnChargeNegative);
		
		lblEtatPlaque = new JLabel("La plaque est: positive ");
		lblEtatPlaque.setBounds(41, 11, 154, 14);
		panelPlaque.add(lblEtatPlaque);
		
		JLabel lblNbDePlaqueRestante = new JLabel("");
		lblNbDePlaqueRestante.setText("Il vous reste: " + nbPlaqueRestante + " restante(s)");
		lblNbDePlaqueRestante.setBounds(205, 11, 154, 14);
		panelPlaque.add(lblNbDePlaqueRestante);

	}
	/**
	 * Affiche le nom du niveau choisi
	 * @param nomNiveau niveau choisi
	 */
	//Kitimir Yim
	public void niveauAfficher(String nomNiveau) {
		labelNomNiveau.setText("Niveau sélectionné : " + nomNiveau);
	}
	
	/**
	 * Modifie le niveau de la zone d'animation physique
	 * @param nomNiveau Le nom du niveau
	 */
	// Enuel René Valentin Kizozo Izia
	public void modifierNiveauDeZoneAnimationPhysique(String nomNiveau) {
		zoneAnimationPhysique.setNiveau(nomNiveau);
	}
	
	/**
	 * Créer la zone d'animation physique et
	 * les boutons d'animation sur le panneau mode jeu
	 */
	// Enuel René Valentin Kizozo Izia
	private void creerZoneAnimationPhysiqueEtBoutonsDAnimation() {
		zoneAnimationPhysique = new ZoneAnimationPhysique();
		zoneAnimationPhysique.setBounds(499, 33, 704, 659);
		add(zoneAnimationPhysique);
		
		btnDemarrer = new JButton("Démarrer");
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.demarrer();
				btnProchaineImage.setEnabled(false);
				btnDemarrer.setEnabled(false);
				// fin
			}
		});
		btnDemarrer.setBounds(499, 773, 89, 23);
		add(btnDemarrer);

		btnArreter = new JButton("Arrêter");
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.arreter();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
				// fin
			}
		});
		btnArreter.setBounds(617, 773, 89, 23);
		add(btnArreter);

		btnRedemarrer = new JButton("Redémarrer");
		btnRedemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.recommencer();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
				// fin
			}
		});
		btnRedemarrer.setBounds(917, 773, 129, 23);
		add(btnRedemarrer);

		btnProchaineImage = new JButton("Prochaine image");
		btnProchaineImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.prochaineImage();
				// fin
			}
		});
		btnProchaineImage.setBounds(738, 773, 154, 23);
		add(btnProchaineImage);

		btnReinitialiser = new JButton("Réinitialiser");
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				reinitialiserZoneAnimation();
				// fin
			}
		});
		btnReinitialiser.setBounds(1074, 773, 129, 23);
		add(btnReinitialiser);
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
				// fin
			}
		});
		spnMasseVaisseau.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getMasseVaisseau(), 0.01, 10.0, 0.01));
		spnMasseVaisseau.setBounds(225, 11, 140, 35);
		panelEntree.add(spnMasseVaisseau);

		spnChargeVaisseau = new JSpinner();
		spnChargeVaisseau.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setChargeVaisseau((double) spnChargeVaisseau.getValue());
				// fin
			}
		});
		spnChargeVaisseau.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getChargeVaisseau(), -50.0, 50.0, 1.0));
		spnChargeVaisseau.setBounds(225, 81, 140, 35);
		panelEntree.add(spnChargeVaisseau);

		spnGravite = new JSpinner();
		spnGravite.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				MoteurPhysique.setAccelGrav((double) spnGravite.getValue());
				zoneAnimationPhysique.setForceGrav();
				// fin
			}
		});
		spnGravite.setModel(new SpinnerNumberModel(MoteurPhysique.accelGrav, -24.8, -1.6, 0.1));
		spnGravite.setBounds(225, 151, 140, 35);
		panelEntree.add(spnGravite);

		spnChargePlaque = new JSpinner();
		spnChargePlaque.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				if (plaquePositive) {
					zoneAnimationPhysique.setChargeDesPlaques((double) spnChargePlaque.getValue());
				} else {
					zoneAnimationPhysique.setChargeDesPlaques(-1*((double) spnChargePlaque.getValue()));
				}
				// fin
			}
		});
		spnChargePlaque.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getChargeDesPlaques(), 0.0, 50.0, 1.0));
		spnChargePlaque.setBounds(225, 221, 140, 35);
		panelEntree.add(spnChargePlaque);

		spnCoefFrictionStat = new JSpinner();
		spnCoefFrictionStat.setModel(new SpinnerNumberModel(MoteurPhysique.coeffFrotStat, 0.50, 1.0, 0.05));
		spnCoefFrictionStat.setBounds(225, 291, 140, 35);
		panelEntree.add(spnCoefFrictionStat);
		
		spnCoefFrictionCine = new JSpinner();
		spnCoefFrictionCine.setModel(new SpinnerNumberModel(MoteurPhysique.coeffFrotCine, 0.35, 0.70, 0.05));
		spnCoefFrictionCine.setBounds(225, 361, 140, 35);
		panelEntree.add(spnCoefFrictionCine);
		
		spnDeltaT = new JSpinner();
		spnDeltaT.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setDeltaT((double) spnDeltaT.getValue());
				// fin
			}
		});
		spnDeltaT.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getDeltaT(), 0.001, 0.1, 0.001));
		spnDeltaT.setBounds(225, 431, 140, 35);
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
	 * Méthode qui effectue les changements des propriétés lors des changements de statut de la plaque
	 * @param positif Indique true si la plaque devient positive, false si négative
	 */
	//Giroux
	private void changementStatutPlaque(Boolean positif) {
		if(positif) {
			plaquePositive = true;
			lblEtatPlaque.setText("La plaque est: positive");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargePositive.png", tglbtnPlaque);
		} else {
			plaquePositive = false;
			lblEtatPlaque.setText("La plaque est: négative");
			OutilsImage.lireImageEtPlacerSurBouton("PlaqueChargeNegative.png", tglbtnPlaque);
		}
	}
	
}
