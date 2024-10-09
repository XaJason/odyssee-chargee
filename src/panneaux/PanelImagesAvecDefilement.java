package panneaux;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Ce composant personnalise permet d'afficher une ou plusieurs images
 * accompagnees d'une barre de defilement vertical. Le tout est placee dans un
 * jpanel. Avec ses methodes suivant/precedent, il servira typiquement a
 * afficher des iamges qui representent des pages de texte cons�cutives.
 *
 * Pour utiliser ce composant: le placer sur l'interface (avec ou sans
 * WindowBuilder). Ensuite, executer sa methode setFichiersImages.
 *
 * L'image est redimensionnee de facon a cadrer exactement dans le jpanel.
 *
 * Caracteristiques modifiables: - la couleur du fond, c'est a dire du cadre
 * derriere le texte (via setBackground) - la largeur de ce cadre en pixels
 * (setLargeurCadre)
 *
 * @author Caroline Houle
 *
 */

public class PanelImagesAvecDefilement extends JPanel {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Largeur en pixels qu'occupe la barre de defilement vertical
	 */
	private final int LARG_BARRE_DEFILEMENT = 20;
	/**
	 * Nombre de pixels laisses vides autour du scrollpane (prendra la couleur du
	 * fond du jpanel)
	 */
	private int largeurCadre = 6;
	/**
	 * JLabel utilisé pour contenir l'image affichée.
	 */
	private JLabel lblPourContenirImage;
	/**
	 * Nombre de pages dans le document.
	 */
	private int nombrePages;

	/**
	 * Nom du fichier
	 */
	private String nomFichierCourant = null;

	/**
	 * Page courante affichée.
	 */
	private int pageCourante = 0;

	/**
	 * Indique si la taille des images est inconnue.
	 */
	private boolean pasDeSize = false;

	/**
	 * JScrollPane utilisé pour afficher les images avec défilement.
	 */
	private JScrollPane sp;

	/**
	 * Tableau contenant les chemins des fichiers images.
	 */
	private String[] tableauImages;

	/**
	 * Crée un composant destin� a contenir une serie de pages (images) S,Il n'y a
	 * qu'une seule image a montrer, on peut appeler setFichierImage S'il y a
	 * plusieurs fichiers a associer, dans lesquels on voudra naviguer avec
	 * precedente/suivante, alors on appellera setFichiersImages Il faut ensuite
	 * appeler la methode initialiser() pour indiquer le tableau des noms d'images a
	 * utiliser.
	 */
	// Caroline Houle
	public PanelImagesAvecDefilement() {
		super();
		lblPourContenirImage = new JLabel(); // l'image sera chargee dans une etiquette
		// on met un scrollpane autour de l'etiquette, pour avoir les barres de
		// defilement
		sp = new JScrollPane(lblPourContenirImage);
		sp.getVerticalScrollBar().setUnitIncrement(15);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setLayout(null);
		add(sp); // ajout du scrollpane au jpanel
	}

	/**
	 * Retourne la largeur courante du cadre autour du scrollpane Cet espace permet
	 * de voir la couleur de l'arriere plan du panel
	 *
	 * @return La laregur du cadre, en pixels
	 */
	// Caroline Houle
	public int getLargeurCadre() {
		return largeurCadre;
	}

	/**
	 * Permet dessiner le panneau
	 *
	 * @param g Le contexte graphique
	 */
	// Caroline Houle
	@Override
	public void paintComponent(Graphics g) {
		if (pasDeSize) {
			// si setFichiersdImage avait ete appele avant que laes dimensions du panel ne
			// soient connues, il faut l'appeler de nouveau
			changeImageCourante(tableauImages[pageCourante]);
			pasDeSize = false;
		}
	}

	/**
	 * Recule d'une page parmi celles fournies lors de l'initialisation.
	 *
	 * @return Un booleen qui vaut true si la nouvelle page est la premiere page
	 */
	// Caroline Houle
	public boolean precedente() {
		if (pageCourante > 0) {
			pageCourante--;
			changeImageCourante(tableauImages[pageCourante]);
		}
		if (pageCourante == 0) {
			return (false);
		} else {
			return (true);
		}
	}

	/**
	 * Permet d'indiquer quelles seront les multiples fichiers d'image � utiliser.
	 * On peut ensuite utiliser les methodes precedente() et suivante() pour
	 * naviguer parmi elles.
	 *
	 * @param tableauImages Le tableau des noms des images de texte, s�par�s par des
	 *                      virgules.
	 */
	// Caroline Houle
	public void setFichiersImages(String tableauImages[]) {
		this.tableauImages = tableauImages;
		nombrePages = tableauImages.length;
		pageCourante = 0;
		changeImageCourante(tableauImages[pageCourante]);
	}

	/**
	 * Modifie la largeur courante du cadre autour du scrollpane Cet espace permet
	 * de voir la couleur de l'arriere plan du panel
	 *
	 * @param largeurCadre La largeur desiree, en pixels
	 */
	// Caroline Houle
	public void setLargeurCadre(int largeurCadre) {
		this.largeurCadre = largeurCadre;
		if (nomFichierCourant != null) {
			changeImageCourante(nomFichierCourant); // car il faut refaire l'image et le scrollpane selon la taille
													// cadre
		}
	}

	/**
	 * Avance d'une page parmi celles fournies lors de l'initialisation.
	 *
	 * @return Un booleen qui vaut true si la nouvelle page est la derniere page
	 */
	// Caroline Houle
	public boolean suivante() {
		if (pageCourante < nombrePages - 1) {
			pageCourante++;
			changeImageCourante(tableauImages[pageCourante]);
		}
		if (pageCourante == nombrePages - 1) {
			return (false);
		} else {
			return (true);
		}

	}

	/**
	 * Methode privee qui place l'image dans le composant et lui associe une barre
	 * de defilement. L'image est redimensionnee de maniere a ce qu'elle occupe
	 * toute la largeur du composant (a part un petit cadre).
	 *
	 * @param nomFichierImage Le nom du fichier d'image a charger
	 */
	// Caroline Houle
	private void changeImageCourante(String nomFichierImage) {
		if (getWidth() == 0) {
			// si le setSize/setBounds n'a pas encore ete fait, le dimensionnement de
			// l'image aura lieu plus tard lors du painComponent
			pasDeSize = true;
		} else {
			// on charge l'image et on la redimensionne (note: on pourrait optimiser en ne
			// faisant �a qu'une fois...)
			URL urlImage = getClass().getClassLoader().getResource("ressources/" + nomFichierImage);
			if (urlImage == null) {
				JOptionPane.showMessageDialog(null,
						"Erreur setFichierImage : Fichier " + nomFichierImage + " introuvable!");
				return;
			}
			this.nomFichierCourant = nomFichierImage; // au cas ou on veut modifier par la suite la largeur du cadre
			ImageIcon imgIcon = new ImageIcon(urlImage);

			int largeurImageDesiree = this.getWidth() - LARG_BARRE_DEFILEMENT - 2 * largeurCadre;
			double ratio = imgIcon.getIconWidth() / (double) (largeurImageDesiree);
			int hauteurImageCalulee = (int) (imgIcon.getIconHeight() / ratio); // ainsi l'image reste proportionnelle,
																				// aucune distortion
			Image imgRedim = imgIcon.getImage().getScaledInstance(largeurImageDesiree, hauteurImageCalulee,
					Image.SCALE_SMOOTH);
			lblPourContenirImage.setIcon(new ImageIcon(imgRedim)); // on place l'image dans l'etiquette
			imgRedim.flush();

			// on fixe les dimensions du scrollpane pour qu'il occupe la largeur du
			// composant (moins un petit cadre)
			sp.setBounds(largeurCadre, largeurCadre, getWidth() - 2 * largeurCadre, getHeight() - 2 * largeurCadre);
		}

	}

}
