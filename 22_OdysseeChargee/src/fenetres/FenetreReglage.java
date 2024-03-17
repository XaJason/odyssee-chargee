package fenetres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dessin.EvaluationEtoile;
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
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);
	/**
	 * Composant de l'évaluateur d'étoile
	 */
	private EvaluationEtoile evaluationEtoile;
	/**
	 * Ancienne valeur du slider pour le son
	 */
	private int ancienneValeurSlider = 0;

	/**
	 * Voici la méthode qui permettra à un objet de s'ajouter en tant qu'écouteur
	 * @param listener L'objet PropertyChangeListener à ajouter comme écouteur de propriété.
	 */
	//Kitimir Yim
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}

	/**
	 * Implémente la fenêtre et ses fonctionnalités
	 */
	// Kitimir Yim
	public FenetreReglage() {
		setTitle("Réglages");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds((ConstanteComposantsSwing.DIM_HORIZONTALE_APP - ConstanteComposantsSwing.DIM_HORIZONTALE_SEC) / 2,
				(ConstanteComposantsSwing.DIM_VERTICALE_APP - ConstanteComposantsSwing.DIM_VERTICALE_SEC) / 2,
				ConstanteComposantsSwing.DIM_HORIZONTALE_SEC, ConstanteComposantsSwing.DIM_VERTICALE_SEC);
		getContentPane().setLayout(null);

		evaluationEtoile = new EvaluationEtoile(0);
		evaluationEtoile.setBounds(160, 273, 160, 128);
		getContentPane().add(evaluationEtoile);

		JButton btnQuitter = new JButton("Retourner à l'application");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuQuitter();

			}
		});
		getContentPane().setLayout(null);
		btnQuitter.setBounds(0, 0, 149, 31);
		getContentPane().add(btnQuitter);

		JSlider sliderSon = new JSlider();
		sliderSon.setValue(100);
		sliderSon.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int nouvelleValeur = sliderSon.getValue();
				PCS.firePropertyChange("changerSon", ancienneValeurSlider, nouvelleValeur);
				ancienneValeurSlider = nouvelleValeur;

			}
		});
		sliderSon.setBounds(159, 132, 172, 31);
		getContentPane().add(sliderSon);
	}

	/**
	 * Bouton pour fermer la fenêtre
	 */
	// Kitimir Yim
	private void menuQuitter() {
		dispose();
	}
}
