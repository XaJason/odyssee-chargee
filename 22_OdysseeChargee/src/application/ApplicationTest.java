package application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dessin.ZoneAnimationPhysiqueTest;

/**
 * Application test permettant de visualiser des phénomènes d'attraction et de
 * répulsion électrique
 * 
 * @author Enuel René Valentin Kizozo Izia
 */
public class ApplicationTest extends JFrame {

	/** Constante permettant la sérialisation de la classe **/
	private static final long serialVersionUID = -5394946883191123518L;

	/** Paneau de regroupement de l'interface graphique */
	private JPanel panModeJeu;

	/** Zone d'animation physique utilisée pour les tests */
	private ZoneAnimationPhysiqueTest zoneAnimationPhysiqueTest;

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

	/** Tourniquet pour définir le rayon du vaisseau */
	private JSpinner spnRayonVaisseau;

	/** Tourniquet pour définir la charge du vaisseau */
	private JSpinner spnChargeVaisseau;

	/** Tourniquet pour définir la masse du vaisseau */
	private JSpinner spnMasseVaisseau;

	/** Tourniquet pour définir la position en X du vaisseau */
	private JSpinner spnPosVaisseauX;

	/** Tourniquet pour définir la position en Y du vaisseau */
	private JSpinner spnPosVaisseauY;

	/** Tourniquet pour définir la vitesse en X du vaisseau */
	private JSpinner spnVitVaisseauX;

	/** Tourniquet pour définir la vitesse en Y du vaisseau */
	private JSpinner spnVitVaisseauY;

	/** Tourniquet pour définir la longueur de la plaque chargée */
	private JSpinner spnLongueurPlaque;

	/** Tourniquet pour définir la charge de la plaque chargée */
	private JSpinner spnChargePlaque;

	/** Tourniquet pour définir la position en X de la plaque chargée */
	private JSpinner spnPosPlaqueX;

	/** Tourniquet pour définir la position en Y de la plaque chargée */
	private JSpinner spnPosPlaqueY;

	/**
	 * Tourniquet pour définir la composante X de la normale de la plaque chargée
	 */
	private JSpinner spnNormalePlaqueX;

	/**
	 * Tourniquet pour définir la composante Y de la normale de la plaque chargée
	 */
	private JSpinner spnNormalePlaqueY;

	/**
	 * Lance l'application
	 * 
	 * @param args Paramètre d'entrée de la commande de démarrage
	 * 
	 */
	// Enuel René Valentin Kizozo Izia
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationTest frame = new ApplicationTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crée la fenêtre de l'application test
	 */
	// Enuel René Valentin Kizozo Izia
	public ApplicationTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // check
		setBounds(100, 100, 1127, 730); // check
		panModeJeu = new JPanel(); // check
		panModeJeu.setBorder(new EmptyBorder(5, 5, 5, 5)); // pas check

		setContentPane(panModeJeu); // check
		panModeJeu.setLayout(null); // check

		// check
		zoneAnimationPhysiqueTest = new ZoneAnimationPhysiqueTest();
		zoneAnimationPhysiqueTest.setBounds(29, 31, 1046, 314);
		panModeJeu.add(zoneAnimationPhysiqueTest);

		// check
		btnDemarrer = new JButton("Démarrer");
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysiqueTest.demarrer();
				btnProchaineImage.setEnabled(false);
				btnDemarrer.setEnabled(false);
				// fin
			}
		});
		btnDemarrer.setBounds(87, 382, 89, 23);
		panModeJeu.add(btnDemarrer);

		// check
		btnArreter = new JButton("Arrêter");
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysiqueTest.arreter();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
				// fin
			}
		});
		btnArreter.setBounds(263, 382, 89, 23);
		panModeJeu.add(btnArreter);

		// check
		btnRedemarrer = new JButton("Redémarrer");
		btnRedemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysiqueTest.recommencer();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
				// fin
			}
		});
		btnRedemarrer.setBounds(680, 382, 129, 23);
		panModeJeu.add(btnRedemarrer);

		// check
		btnProchaineImage = new JButton("Prochaine image");
		btnProchaineImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneAnimationPhysiqueTest.prochaineImage();
				// fin
			}
		});
		btnProchaineImage.setBounds(439, 382, 154, 23);
		panModeJeu.add(btnProchaineImage);

		// check
		btnReinitialiser = new JButton("Réinitialiser");
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				reinitialiserZoneAnimation();
				// fin
			}
		});
		btnReinitialiser.setBounds(896, 382, 129, 23);
		panModeJeu.add(btnReinitialiser);

		// non nécessaire so check
		spnRayonVaisseau = new JSpinner();
		spnRayonVaisseau.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setRayonVaisseau((double) spnRayonVaisseau.getValue());
				// fin
			}
		});
		spnRayonVaisseau.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getRayonVaisseau(), 3.0, 10.0, 0.5));
		spnRayonVaisseau.setBounds(193, 468, 54, 20);
		panModeJeu.add(spnRayonVaisseau);

		// non nécessaire so check
		JLabel lblRayonVaisseau = new JLabel("Rayon du vaisseau :");
		lblRayonVaisseau.setBounds(53, 459, 130, 38);
		panModeJeu.add(lblRayonVaisseau);

		// check
		spnChargeVaisseau = new JSpinner();
		spnChargeVaisseau.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setChargeVaisseau((double) spnChargeVaisseau.getValue());
				// fin
			}
		});
		spnChargeVaisseau
				.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getChargeVaisseau(), -50.0, 50.0, 1.0));
		spnChargeVaisseau.setBounds(193, 499, 54, 20);
		panModeJeu.add(spnChargeVaisseau);

		// check
		JLabel lblChargeVaisseau = new JLabel("Charge du vaisseau :");
		lblChargeVaisseau.setBounds(53, 502, 130, 14);
		panModeJeu.add(lblChargeVaisseau);

		// check
		JLabel lblMasseVaisseau = new JLabel("Masse du vaisseau :");
		lblMasseVaisseau.setBounds(53, 532, 130, 14);
		panModeJeu.add(lblMasseVaisseau);

		// check
		spnMasseVaisseau = new JSpinner();
		spnMasseVaisseau.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setMasseVaisseau((double) spnMasseVaisseau.getValue());
				// fin
			}
		});
		spnMasseVaisseau
				.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getMasseVaisseau(), 0.01, 10.0, 0.01));
		spnMasseVaisseau.setBounds(193, 529, 54, 20);
		panModeJeu.add(spnMasseVaisseau);

		// à gérer en sortie
		JLabel lblPosVaisseauY = new JLabel("Position initiale en y du vaisseau :");
		lblPosVaisseauY.setBounds(270, 468, 200, 20);
		panModeJeu.add(lblPosVaisseauY);

		// à gérer en sortie
		JLabel lblPosVaisseauX = new JLabel("Position initiale en x du vaisseau :");
		lblPosVaisseauX.setBounds(270, 442, 200, 14);
		panModeJeu.add(lblPosVaisseauX);

		// à gérer en sortie
		JLabel lblVitVaisseauX = new JLabel("Vitesse initiale en x du vaisseau :");
		lblVitVaisseauX.setBounds(270, 502, 200, 14);
		panModeJeu.add(lblVitVaisseauX);

		// à gérer en sortie
		JLabel lblVitVaisseauY = new JLabel("Vitesse initiale en y du vaisseau :");
		lblVitVaisseauY.setBounds(270, 529, 200, 20);
		panModeJeu.add(lblVitVaisseauY);

		// non nécessaire so check
		spnVitVaisseauY = new JSpinner();
		spnVitVaisseauY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setVitVaisseauEnY((double) spnVitVaisseauY.getValue());
				// fin
			}
		});
		spnVitVaisseauY.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getVitVaisseauEnY(), -25, 25, 1));
		spnVitVaisseauY.setBounds(480, 529, 54, 20);
		panModeJeu.add(spnVitVaisseauY);

		// non nécessaire so check
		spnVitVaisseauX = new JSpinner();
		spnVitVaisseauX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setVitVaisseauEnX((double) spnVitVaisseauX.getValue());
				// fin
			}
		});
		spnVitVaisseauX.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getVitVaisseauEnX(), -25, 25, 1));
		spnVitVaisseauX.setBounds(480, 499, 54, 20);
		panModeJeu.add(spnVitVaisseauX);

		// non nécessaire so check
		spnPosVaisseauY = new JSpinner();
		spnPosVaisseauY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setPosVaisseauEnY((double) spnPosVaisseauY.getValue());
				// fin
			}
		});
		spnPosVaisseauY.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getPosVaisseauEnY(),
				zoneAnimationPhysiqueTest.getRayonVaisseau(), zoneAnimationPhysiqueTest.getHeight() - 10, 1));
		spnPosVaisseauY.setBounds(480, 468, 54, 20);
		panModeJeu.add(spnPosVaisseauY);

		// non nécessaire so check
		spnPosVaisseauX = new JSpinner();
		spnPosVaisseauX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setPosVaisseauEnX((double) spnPosVaisseauX.getValue());
				// fin
			}
		});
		spnPosVaisseauX.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getPosVaisseauEnX(),
				zoneAnimationPhysiqueTest.getRayonVaisseau(), zoneAnimationPhysiqueTest.getWidth() - 10, 1));
		spnPosVaisseauX.setBounds(480, 439, 54, 20);
		panModeJeu.add(spnPosVaisseauX);

		// non nécessaire so check
		JLabel lblLongueurPlaque = new JLabel("Longueur de la plaque :");
		lblLongueurPlaque.setBounds(571, 456, 144, 38);
		panModeJeu.add(lblLongueurPlaque);

		// check
		JLabel lblChargePlaque = new JLabel("Charge de la plaque :");
		lblChargePlaque.setBounds(571, 499, 130, 14);
		panModeJeu.add(lblChargePlaque);

		// non nécessaire so check
		spnLongueurPlaque = new JSpinner();
		spnLongueurPlaque.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setLongueurPlaque((double) spnLongueurPlaque.getValue());
				// fin
			}
		});
		spnLongueurPlaque
				.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getLongueurPlaque(), 15.0, 100.0, 1.0));
		spnLongueurPlaque.setBounds(711, 465, 54, 20);
		panModeJeu.add(spnLongueurPlaque);

		// check
		spnChargePlaque = new JSpinner();
		spnChargePlaque.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setChargePlaque((double) spnChargePlaque.getValue());
				// fin
			}
		});
		spnChargePlaque.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getChargePlaque(), -50.0, 50.0, 1.0));
		spnChargePlaque.setBounds(711, 496, 54, 20);
		panModeJeu.add(spnChargePlaque);

		// non nécessaire so check
		JLabel lblPosPlaqueX = new JLabel("Position en x de la plaque :");
		lblPosPlaqueX.setBounds(788, 439, 200, 14);
		panModeJeu.add(lblPosPlaqueX);

		// non nécessaire so check
		JLabel lblPosPlaqueY = new JLabel("Position en y de la plaque :");
		lblPosPlaqueY.setBounds(788, 465, 200, 20);
		panModeJeu.add(lblPosPlaqueY);

		// non nécessaire so check
		JLabel lblNormalePlaqueX = new JLabel("Normale en x de la plaque :");
		lblNormalePlaqueX.setBounds(788, 499, 200, 14);
		panModeJeu.add(lblNormalePlaqueX);

		// non nécessaire so check
		JLabel lblNormalePlaqueY = new JLabel("Normale en y de la plaque");
		lblNormalePlaqueY.setBounds(788, 526, 200, 20);
		panModeJeu.add(lblNormalePlaqueY);

		// non nécessaire so check
		spnPosPlaqueX = new JSpinner();
		spnPosPlaqueX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setPosPlaqueEnX((double) spnPosPlaqueX.getValue());
				// fin
			}
		});
		spnPosPlaqueX.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getPosPlaqueEnX(), 0.0, 500, 1.0));
		spnPosPlaqueX.setBounds(998, 436, 54, 20);
		panModeJeu.add(spnPosPlaqueX);

		// non nécessaire so check
		spnPosPlaqueY = new JSpinner();
		spnPosPlaqueY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setPosPlaqueEnY((double) spnPosPlaqueY.getValue());
				// fin
			}
		});
		spnPosPlaqueY.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getPosPlaqueEnY(), 0.0, 150, 1.0));
		spnPosPlaqueY.setBounds(998, 465, 54, 20);
		panModeJeu.add(spnPosPlaqueY);

		// non nécessaire so check
		spnNormalePlaqueX = new JSpinner();
		spnNormalePlaqueX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setNormalePlaqueComposanteX((double) spnNormalePlaqueX.getValue());
				// fin
			}
		});
		spnNormalePlaqueX.setModel(
				new SpinnerNumberModel(zoneAnimationPhysiqueTest.getNormalePlaqueComposanteX(), -10, 10, 1.0));
		spnNormalePlaqueX.setBounds(998, 496, 54, 20);
		panModeJeu.add(spnNormalePlaqueX);

		// non nécessaire so check
		spnNormalePlaqueY = new JSpinner();
		spnNormalePlaqueY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setNormalePlaqueComposanteY((double) spnNormalePlaqueY.getValue());
				// fin
			}
		});
		spnNormalePlaqueY.setModel(
				new SpinnerNumberModel(zoneAnimationPhysiqueTest.getNormalePlaqueComposanteY(), -10, 10, 1.0));
		spnNormalePlaqueY.setBounds(998, 526, 54, 20);
		panModeJeu.add(spnNormalePlaqueY);

		spnDeltaT = new JSpinner();
		spnDeltaT.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneAnimationPhysiqueTest.setDeltaT((double) spnDeltaT.getValue());
				// fin
			}
		});
		spnDeltaT.setModel(new SpinnerNumberModel(zoneAnimationPhysiqueTest.getDeltaT(), 0.001, 0.1, 0.001));
		spnDeltaT.setBounds(193, 579, 54, 20);
		panModeJeu.add(spnDeltaT);

		JLabel lblDeltaT = new JLabel("Pas de simulation :");
		lblDeltaT.setBounds(53, 576, 130, 23);
		panModeJeu.add(lblDeltaT);
	}

	/**
	 * Reinitialise tout exactement dans l'etat de demarrage de l'application
	 */
	// Enuel René Valentin Kizozo Izia
	private void reinitialiserZoneAnimation() {
		zoneAnimationPhysiqueTest.reinitialiser();
		btnProchaineImage.setEnabled(true);
		btnDemarrer.setEnabled(true);

		spnDeltaT.setValue(zoneAnimationPhysiqueTest.getDeltaTInitial());

		spnRayonVaisseau.setValue(zoneAnimationPhysiqueTest.getRayonInitialVaisseau());
		spnChargeVaisseau.setValue(zoneAnimationPhysiqueTest.getChargeInitialeVaisseau());
		spnMasseVaisseau.setValue(zoneAnimationPhysiqueTest.getMasseInitialeVaisseau());
		spnPosVaisseauX.setValue(zoneAnimationPhysiqueTest.getPosInitialeVaisseauEnX());
		spnPosVaisseauY.setValue(zoneAnimationPhysiqueTest.getPosinitialeVaisseauEnY());
		spnVitVaisseauX.setValue(zoneAnimationPhysiqueTest.getVitInitialeVaisseauX());
		spnVitVaisseauY.setValue(zoneAnimationPhysiqueTest.getVitInitialeVaisseauY());

		spnLongueurPlaque.setValue(zoneAnimationPhysiqueTest.getLongueurPlaqueInitiale());
		spnChargePlaque.setValue(zoneAnimationPhysiqueTest.getChargeInitialePlaque());
		spnPosPlaqueX.setValue(zoneAnimationPhysiqueTest.getPosInitialePlaqueEnX());
		spnPosPlaqueY.setValue(zoneAnimationPhysiqueTest.getPosInitialePlaqueEnY());
		spnNormalePlaqueX.setValue(zoneAnimationPhysiqueTest.getNormaleInitialePlaqueComposanteX());
		spnNormalePlaqueY.setValue(zoneAnimationPhysiqueTest.getNormaleInitialePlaqueComposanteY());
	}// fin methode reinitialiserZoneAnimation
}
