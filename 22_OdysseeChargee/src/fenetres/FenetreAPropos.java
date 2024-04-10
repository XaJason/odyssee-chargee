package fenetres;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * Panel qui affiche dans des onglets de l'information
 * sur les auteurs et sur les sources
 * 
 * @author Caroline Houle
 * @author Kitimir Yim
 */

public class FenetreAPropos extends JPanel {
	private static final long serialVersionUID = -3110011146750233775L;

	/**
	 * Cr�ation du panel
	 */
	// Kitimir Yim
	public FenetreAPropos() {
		// noter: aucun layout précisé: le conteneur à onglets prendra la largeur de la
		// plus longue ligne de texte
		JTabbedPane tabOnglets = new JTabbedPane(JTabbedPane.TOP);
		tabOnglets.setBounds(0, 0, 500, 250);
		add(tabOnglets);

		JPanel pnlAuteurs = new JPanel();
		tabOnglets.addTab("Auteurs", null, pnlAuteurs, null);

		JLabel lblAuteurs = new JLabel("<html>" + "Équipe 22 " + "<br>" + "<br> Enuel René Valentin Kizozo Izia"
				+ "<br>Pierre-Olivier Giroux" + "<br>Jason Xa" + "<br>Kitimir Yim" + "<br><br>Cours 420-SCD"
				+ "<br>Intégration des apprentissages en SIM" + "<br>Hiver 2024</html>");
		lblAuteurs.setVerticalAlignment(SwingConstants.TOP);
		pnlAuteurs.add(lblAuteurs);

		JPanel pnlSources = new JPanel();
		tabOnglets.addTab("Sources", null, pnlSources, null);

		JLabel lblSources = new JLabel("<html>" + "<br>Image des carrés: " + "<br>Image du drapeau: "
				+ "<br>Image des pics: " + "<br>Image des portails: " + "<br>Image des triangles équilatérals: "
				+ "<br>Image des triangles rectangles: " + "<br>Image du vaisseau: " + "<br>Musique de fond: "

		);

		lblSources.setVerticalAlignment(SwingConstants.TOP);
		pnlSources.add(lblSources);
	}
}
