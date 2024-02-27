package fenetres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import composants.EvaluationEtoile;
import constante.ConstanteComposantsSwing;

/**
 * 
 */
//Kitimir Yim
public class FenetreReglage extends JFrame {

	private static final long serialVersionUID = -4125957740472303897L;

	/**
	 *  ajouter le support pour lancer des evenements de type PropertyChange
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	/**
	 * Composant de l'évaluateur d'étoile
	 */
	private  EvaluationEtoile evaluationEtoile ;
	/**
	 * voici la methode qui permettra � un objet de s'ajouter en tant qu'ecouteur
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * 
	 */
	public FenetreReglage() {
		setTitle("Réglages");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds((ConstanteComposantsSwing.DIM_HORIZONTALE_APP-ConstanteComposantsSwing.DIM_HORIZONTALE_SEC)/2, ( ConstanteComposantsSwing.DIM_VERTICALE_APP-ConstanteComposantsSwing.DIM_VERTICALE_SEC)/2, ConstanteComposantsSwing.DIM_HORIZONTALE_SEC, ConstanteComposantsSwing.DIM_VERTICALE_SEC);
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
	}

	/**
	 * Bouton pour fermer la fenêtre
	 */
	// Kitimir Yim
	public void menuQuitter() {
		dispose();
	}

}
