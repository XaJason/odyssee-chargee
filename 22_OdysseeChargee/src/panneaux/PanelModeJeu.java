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

		JLabel lblChargePlaques = new JLabel("Charge Plaques (Coulombs) :");
		lblChargePlaques.setBounds(10, 225, 180, 26);
		panelEntree.add(lblChargePlaques);

		JLabel lblCoefFrotStat = new JLabel("Coefficient de frottement statique :");
		lblCoefFrotStat.setBounds(10, 295, 197, 26);
		panelEntree.add(lblCoefFrotStat);

		JLabel lblCoefFrotCine = new JLabel("Coefficient de frottement cinétique :");
		lblCoefFrotCine.setBounds(10, 365, 197, 26);
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
		zoneAnimationPhysique.setBounds(29, 31, 1232, 617);
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
		btnDemarrer.setBounds(172, 685, 89, 23);
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
		btnArreter.setBounds(348, 685, 89, 23);
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
		btnRedemarrer.setBounds(765, 685, 129, 23);
		add(btnRedemarrer);

		btnProchaineImage = new JButton("Prochaine image");
		btnProchaineImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysique.prochaineImage();
				// fin
			}
		});
		btnProchaineImage.setBounds(524, 685, 154, 23);
		add(btnProchaineImage);

		btnReinitialiser = new JButton("Réinitialiser");
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				reinitialiserZoneAnimation();
				// fin
			}
		});
		btnReinitialiser.setBounds(981, 685, 129, 23);
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
		spnMasseVaisseau.setBounds(206, 11, 160, 35);
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
		spnChargeVaisseau.setBounds(206, 81, 160, 35);
		panelEntree.add(spnChargeVaisseau);

		spnGravite = new JSpinner();
		spnGravite.setBounds(206, 151, 160, 35);
		panelEntree.add(spnGravite);

		spnChargePlaque = new JSpinner();
		spnChargePlaque.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setChargeDesPlaques((double) spnChargePlaque.getValue());
				// fin
			}
		});
		spnChargePlaque.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getChargeDesPlaques(), -50.0, 50.0, 1.0));
		spnChargePlaque.setBounds(206, 221, 160, 35);
		panelEntree.add(spnChargePlaque);

		spnCoefFrictionStat = new JSpinner();
		spnCoefFrictionStat.setBounds(206, 291, 160, 35);
		panelEntree.add(spnCoefFrictionStat);
		
		spnCoefFrictionCine = new JSpinner();
		spnCoefFrictionCine.setBounds(206, 361, 160, 35);
		panelEntree.add(spnCoefFrictionCine);
		
		spnDeltaT = new JSpinner();
		spnDeltaT.setBounds(206, 431, 160, 35);
		panelEntree.add(spnDeltaT);
		spnDeltaT.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysique.setDeltaT((double) spnDeltaT.getValue());
				// fin
			}
		});
		spnDeltaT.setModel(new SpinnerNumberModel(zoneAnimationPhysique.getDeltaT(), 0.001, 0.1, 0.001));
		
		JLabel lblDeltaT = new JLabel("Pas de simulation :");
		lblDeltaT.setBounds(10, 437, 180, 23);
		panelEntree.add(lblDeltaT);
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
}
