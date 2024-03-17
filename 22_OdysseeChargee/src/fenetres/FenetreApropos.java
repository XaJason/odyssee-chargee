package fenetres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import constante.ConstanteComposantsSwing;

import javax.swing.JTextArea;

/**
 * Fenêtre d'à propos
 * 
 * @author Kitimir YIm
 */
public class FenetreApropos extends JFrame {

	/**
	 *  Numéro d'identification pour la sérialisation
	 */
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
		txtrA.setBounds(30, 45, 826, 405);
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
		String message = "Voici les fonctionnalitées à explorer pour la version alpha:"
				+"\n" + "Navigation à travers différents panels et différentes fenêtre"
				+"\n" + "Éditeur de niveau: placement des objets, réinitialisation (complète), suppression d'objet (placement de tuile nulle), rotation (90 degrés dans le sens horaire)"
				+"\n" + "Démo d'une animation physique disponible dans le package test(et toutes les formules implémentées qui viennent avec)"
				+"\n" + "Sauvegarde de fichier disponible dans le mode éditeur"
				+"\n" + "Musique de fond et la possibilié d'ajuster le volume";


		return message;

	}
}
