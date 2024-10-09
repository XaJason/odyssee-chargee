package fenetres;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * Panel qui affiche dans des onglets de l'information sur les auteurs et sur
 * les sources
 *
 * @author Kitimir Yim
 */

public class FenetreAPropos extends JPanel {
	/** Constante permettant la sérialisation de la classe **/
	private static final long serialVersionUID = -3110011146750233775L;
	/** Source du fond du menu éditeur */
	private String fondEditeur = "https://www.reddit.com/r/PixelArt/comments/fbz129/oc_inside_a_spaceship_cockpit_i_might_do_more_of/?rdt=65513";
	/** Source du fond du menu jeu */
	private String fondJeu = "https://www.pinterest.com/pin/solar-eclipse-illustration-space-planet-abstract-space-art-digital-art-cyan-2k-wallpaper-hdwallpaper-d--733664595531549031/";
	/** Source fond du menu principal */
	private String fondMenu = "https://www.peakpx.com/en/hd-wallpaper-desktop-avwgs";
	/** Source du fond du menu réglages */
	private String fondReglages = "https://in.pinterest.com/pin/713328028467976805/";
	/** Source des boutons en style pixel */
	private String sourceBouton = "https://www.textstudio.com/logo/831/PIXEL";
	/** Source des boutons d'animation en style tron */
	private String sourceBoutonAnimation = "https://fontmeme.com/fonts/tron-font/";
	/** Source du drapeau */
	private String sourceDrapeau = "https://game-icons.net/1x1/lorc/flying-flag.html";
	/** Source de l'icône pour essayer */
	private String sourceEssayer = "https://game-icons.net/1x1/guard13007/play-button.html";
	/** Source de la texture d'obsidienne pour les blocs */
	private String sourceObsidienne = "https://i.pinimg.com/originals/03/49/c2/0349c21114b361fdc9a442e582fd27c7.jpg";
	/** Source des pics */
	private String sourcePics = "https://game-icons.net/1x1/delapouite/spiky-pit.html";
	/** Source de la texture d'obsidienne pour les blocs */
	private String sourcePortail = "https://game-icons.net/1x1/lorc/portal.html";
	/** Source de l'icône pour réinitialiser dans l'éditeur de niveau */
	private String sourceReinitialiser = "https://game-icons.net/1x1/lorc/cycle.html";
	/** Source de l'icône pour la rotation pré-placement */
	private String sourceRotationPrePlacement = "https://game-icons.net/1x1/delapouite/clockwise-rotation.html";
	/** Source de l'icône pour sauvegarder */
	private String sourceSauvegarder = "https://game-icons.net/1x1/delapouite/save.html";
	/** Source de l'icône pour la suppression */
	private String sourceSupprimer = "https://game-icons.net/1x1/delapouite/trash-can.html";
	/** Source du vaisseau */
	private String sourceVaisseau = "https://iconduck.com/icons/2658/unidentified-code-object-ufo";

	/**
	 * Création du panel
	 */
	// Kitimir Yim
	public FenetreAPropos() {
		// noter: aucun layout précisé: le conteneur à onglets prendra la largeur de la
		// plus longue ligne de texte
		JTabbedPane tabOnglets = new JTabbedPane(SwingConstants.TOP);
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

		JLabel lblSources = new JLabel("<html>" + "<br>Image des carrés: " + sourceObsidienne + "<br>Image du drapeau: "
				+ sourceDrapeau + "<br>Image des pics: " + sourcePics + "<br>Image des portails: " + sourcePortail
				+ "<br>Image des triangles équilatéraux: " + sourceObsidienne + "<br>Image des triangles rectangles: "
				+ sourceObsidienne + "<br>Image du vaisseau: " + sourceVaisseau + "<br>Image du bouton essayer: "
				+ sourceEssayer + "<br>Image du bouton réinitialiser: " + sourceReinitialiser
				+ "<br>Image du bouton rotation: " + sourceRotationPrePlacement + "<br>Image du bouton supprimer: "
				+ sourceSupprimer + "<br>Bouton de sauvegarde: " + sourceSauvegarder
				+ "<br>Source des boutons style pixel: " + sourceBouton + "<br>Source fond du menu principal: "
				+ fondMenu + "<br>Source du fond du menu éditeur: " + fondEditeur
				+ "<br>Source du fond du menu réglages: " + fondReglages + "<br>Source du fond du menu jeu: " + fondJeu
				+ "<br>Source des boutons d'animation: " + sourceBoutonAnimation + "</html>");

		lblSources.setVerticalAlignment(SwingConstants.TOP);
		pnlSources.add(lblSources);
	}
}
