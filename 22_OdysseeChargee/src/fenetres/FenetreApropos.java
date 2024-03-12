package fenetres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JFrame;

import constante.ConstanteComposantsSwing;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Fenêtre d'à propos
 * 
 * @author Kitimir YIm
 */
public class FenetreApropos extends JFrame {

	private static final long serialVersionUID = -2336396924514608396L;


	/**
	 * Implémente la fenêtre et ses fonctionnalités
	 */
	// Kitimir Yim
	public FenetreApropos() {
		setTitle("À propos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds((ConstanteComposantsSwing.DIM_HORIZONTALE_APP - ConstanteComposantsSwing.DIM_HORIZONTALE_SEC) / 2,
				(ConstanteComposantsSwing.DIM_VERTICALE_APP - ConstanteComposantsSwing.DIM_VERTICALE_SEC) / 2,
				ConstanteComposantsSwing.DIM_HORIZONTALE_SEC, ConstanteComposantsSwing.DIM_VERTICALE_SEC);

		JButton btnQuitter = new JButton("Retourner à l'application");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuQuitter();

			}
		});
		getContentPane().setLayout(null);
		btnQuitter.setBounds(0, 0, 174, 34);
		getContentPane().add(btnQuitter);
		
		JTextArea txtrA = new JTextArea();
		txtrA.setText(texte());
		txtrA.setBounds(46, 45, 692, 405);
		getContentPane().add(txtrA);
	}

	/**
	 * Bouton pour fermer la fenêtre
	 */
	// Kitimir Yim
	private void menuQuitter() {
		dispose();

	}
	/**
	 * Texte pour le message afficher dans la fenêtre
	 * @return message Message d'informations
	 */
	//Kitimir Yim
	private String texte() {
		String message = "Lorem ipsum dolor sit amet,consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
						+ "\n" + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
						 + "\n" +  "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
						 		+ "\n" + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
		return message;
		
	}
}
