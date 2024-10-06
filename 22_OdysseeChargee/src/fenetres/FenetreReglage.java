package fenetres;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dessin.EvaluationEtoile;
import panneaux.FondEcran;
import utilitaires.ConstanteComposantsSwing;

/**
 * Fenêtre des réglages
 *
 * @author Kitimir Yim
 */
public class FenetreReglage extends JFrame {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -4125957740472303897L;

	/**
	 * Ancienne valeur du slider pour le son
	 */
	private int ancienneValeurSlider = 0;
	/**
	 * Composant de l'évaluateur d'étoile
	 */
	private EvaluationEtoile evaluationEtoile;
	/** étiquette identifiant l'évaluateur en étoiles */
	private JLabel lblEvalutation;

	/** étiquette identifiant le curseur associé au volume */
	private JLabel lblVolume;

	/**
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);

	/**
	 * Implémente la fenêtre et ses fonctionnalités
	 */
	// Kitimir Yim
	public FenetreReglage() {
		setTitle("Réglages");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds((ConstanteComposantsSwing.DIM_HORIZONTALE_APP - ConstanteComposantsSwing.DIM_HORIZONTALE_SEC) / 2,
				(ConstanteComposantsSwing.DIM_VERTICALE_APP - ConstanteComposantsSwing.DIM_VERTICALE_SEC) / 2,
				ConstanteComposantsSwing.DIM_HORIZONTALE_SEC, ConstanteComposantsSwing.DIM_VERTICALE_SEC);
		getContentPane().setLayout(null);

		evaluationEtoile = new EvaluationEtoile(0);
		evaluationEtoile.setOpaque(false);
		evaluationEtoile.setBounds(363, 273, 160, 128);
		getContentPane().add(evaluationEtoile);

		JButton btnQuitter = new JButton("Retourner à l'application");
		btnQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuQuitter();

			}
		});
		getContentPane().setLayout(null);
		btnQuitter.setBounds(0, 0, 180, 21);
		getContentPane().add(btnQuitter);

		JSlider sliderSon = new JSlider();
		sliderSon.setOpaque(false);
		sliderSon.setValue(100);
		sliderSon.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int nouvelleValeur = sliderSon.getValue();
				PCS.firePropertyChange("changerSon", ancienneValeurSlider, nouvelleValeur);
				ancienneValeurSlider = nouvelleValeur;

			}
		});
		sliderSon.setBounds(357, 149, 172, 31);
		getContentPane().add(sliderSon);

		lblVolume = new JLabel("Volume");
		lblVolume.setForeground(new Color(255, 255, 255));
		lblVolume.setHorizontalAlignment(SwingConstants.CENTER);
		lblVolume.setBounds(421, 126, 45, 13);
		getContentPane().add(lblVolume);

		lblEvalutation = new JLabel("Que pensez vous d'Odyssée chargée?");
		lblEvalutation.setForeground(new Color(255, 255, 255));
		lblEvalutation.setHorizontalAlignment(SwingConstants.CENTER);
		lblEvalutation.setBounds(306, 245, 275, 20);
		getContentPane().add(lblEvalutation);

		FondEcran fondEcran = new FondEcran("fondReglage.png", 1.7);
		fondEcran.setBounds(0, 0, ConstanteComposantsSwing.DIM_HORIZONTALE_SEC,
				ConstanteComposantsSwing.DIM_VERTICALE_SEC);
		getContentPane().add(fondEcran);
		fondEcran.setLayout(null);
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
	 * Bouton pour fermer la fenêtre
	 */
	// Kitimir Yim
	private void menuQuitter() {
		dispose();
	}
}
