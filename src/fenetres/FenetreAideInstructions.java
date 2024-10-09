package fenetres;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import panneaux.PanelImagesAvecDefilement;
import utilitaires.ConstanteComposantsSwing;

/**
 * Exemple de fenetre qui cree un JPanel dans lequel sera affichees (et bien
 * ajustees) une suite d'images representant du texte continu. Les boutons
 * places sur cette fenetre permettent de passer a l'image precedente/suicante.
 *
 * @author Kitimir Yim
 *
 */
public class FenetreAideInstructions extends JFrame {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Bouton pour changer de page
	 */
	private JButton btnPagePrecedente;
	/**
	 * Bouton pour changer de page
	 */
	private JButton btnPageSuivante;
	/**
	 * Panel
	 */
	private JPanel contentPane;

	/**
	 * Tableau des images
	 */
	private String tableauImages[] = { "22_Instructions-01.jpg", "22_Instructions-02.jpg", "22_Instructions-03.jpg",
			"22_Instructions-04.jpg", "22_Instructions-05.jpg", "22_Instructions-06.jpg", "22_Instructions-07.jpg",
			"22_Instructions-08.jpg", "22_Instructions-09.jpg", "22_Instructions-10.jpg", "22_Instructions-11.jpg",
			"22_Instructions-12.jpg", "22_Instructions-13.jpg", "22_Instructions-14.jpg", "22_Instructions-15.jpg",
			"22_Instructions-16.jpg", "22_Instructions-17.jpg", "22_Instructions-18.jpg", "22_Instructions-19.jpg",
			"22_Instructions-20.jpg", "22_Instructions-21.jpg", "22_Instructions-22.jpg", "22_Instructions-23.jpg",
			"22_Instructions-24.jpg", "22_Instructions-25.jpg" };

	/**
	 * Constructeur: cr�e une fen�tre qui inclut une instance d'image avec
	 * d�filement
	 */
	// Kitimir Yim
	public FenetreAideInstructions() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds((ConstanteComposantsSwing.DIM_HORIZONTALE_APP - ConstanteComposantsSwing.DIM_HORIZONTALE_SEC) / 2,
				(ConstanteComposantsSwing.DIM_VERTICALE_APP - 800) / 2, ConstanteComposantsSwing.DIM_HORIZONTALE_SEC,
				800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// creation du composant qui contiendra les pages d'aide
		PanelImagesAvecDefilement panAide = new PanelImagesAvecDefilement();
		// Pour modifier la largeur et la couleur du cadre autour des pages
		panAide.setLargeurCadre(10);
		panAide.setFichiersImages(tableauImages); // on precise quelles images seront utilisees
		panAide.setBounds(49, 88, 772, 606);
		contentPane.add(panAide);

		btnPagePrecedente = new JButton("Page pr\u00E9c\u00E9dente");
		btnPagePrecedente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPagePrecedente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPagePrecedente.setEnabled(panAide.precedente());
				btnPageSuivante.setEnabled(true);
			}
		});
		btnPagePrecedente.setBounds(49, 705, 165, 45);
		contentPane.add(btnPagePrecedente);

		btnPageSuivante = new JButton("Page suivante");
		btnPageSuivante.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPageSuivante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPageSuivante.setEnabled(panAide.suivante());
				btnPagePrecedente.setEnabled(true);
			}

		});

		if (tableauImages.length == 1) {
			btnPagePrecedente.setEnabled(false);
			btnPageSuivante.setEnabled(false);
		}
		btnPageSuivante.setBounds(656, 705, 165, 45);
		contentPane.add(btnPageSuivante);

		JLabel lblAideInstructions = new JLabel("Aide : instructions d'utilisation");
		lblAideInstructions.setHorizontalAlignment(SwingConstants.CENTER);
		lblAideInstructions.setForeground(Color.WHITE);
		lblAideInstructions.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAideInstructions.setBounds(264, 11, 342, 34);
		contentPane.add(lblAideInstructions);

	}
}
