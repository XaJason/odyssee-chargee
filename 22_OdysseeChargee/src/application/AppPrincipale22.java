package application;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constante.ConstanteComposantsSwing;
import etatModeEditeur.PanelModeEditeur;
import etatModeJeu.PanelModeJeu;
import etatSelecteurNiveaux.PanelSelecteurNiveaux;
import fenetres.FenetreApropos;
import fenetres.FenetreInstruction;
import fenetres.FenetreReglage;
import tuile.Carre;
import tuile.Drapeau;
import tuile.Pics;
import tuile.Portail;
import tuile.TriangleEquilateral;
import tuile.TriangleRectangle;
import tuile.Tuile;
import tuile.Vaisseau;
import utilis.OutilsImage;

/**
 * Application permettant d'accéder au jeu Odyssée chargée
 * 
 * @author Jason Xa
 * @author Kitimir Yim
 */
public class AppPrincipale22 extends JFrame {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -506870656338933836L;
	/**
	 * Zone des composants
	 */
	private JPanel contentPane;
	/**
	 * Fenêtre À propos
	 */
	private FenetreApropos fenApropos;
	/**
	 * Fenêtre Instructions
	 */
	private FenetreInstruction fenInstruction;
	/**
	 * Fenêtre Réglage
	 */
	private FenetreReglage fenReglage;
	/**
	 * Panel du mode éditeur
	 */
	private PanelModeEditeur panE;
	/**
	 * Panel du mode jeu
	 */
	private PanelModeJeu panJ;
	/**
	 * Panel du sélecteur de niveaux
	 */
	private PanelSelecteurNiveaux panS;

	private JMenuBar menuBar;

	private JMenuItem mntmEditeur;

	private JMenuItem mntmSelection;

	private Clip leClip = null;

	private final String NOM_FICHIER_SON_1 = "Musique_Fond.wav";
	private final String NOM_FICHIER_SON_2 = "Effet_son.wav";
	private AudioInputStream audioStr;
	private double volumeEntre0Et1 = 1;
	private String pathDeFichier = null;
	private File objetFichier = null;

	/** largeur d'une tuile */
	private final int LARGEUR_TUILE = 61;
	/** hauteur d'une tuile */
	private final int HAUTEUR_TUILE = 61;
	/** hauteur d'une demi-tuile */
	private final int HAUTEUR_DEMI_TUILE = HAUTEUR_TUILE / 2;

	/**
	 * Lance l'application
	 * 
	 * @param args Paramètre d'entrée de la commande de démarrage
	 */
	// Kitimir Yim
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppPrincipale22 frame = new AppPrincipale22();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creer la page principale.
	 */
	// Kitimir Yim
	public AppPrincipale22() {
		setTitle("Odyssée Chargée");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 25, ConstanteComposantsSwing.DIM_HORIZONTALE_APP, ConstanteComposantsSwing.DIM_VERTICALE_APP);

		creerBoutons();
		creerFenetres();
		creerPanels();
		creerMenu();
		lireImages();
		gererConstantes();

		if (leClip != null)
			leClip.close();
		chargerLeSon(NOM_FICHIER_SON_1);
		leClip.loop(Clip.LOOP_CONTINUOUSLY);

	}

	/**
	 * Gère les constantes réutilisées hors de cette classe
	 */
	// Jason Xa
	private void gererConstantes() {
		Tuile.setHauteurTuile(HAUTEUR_TUILE);
		Tuile.setLargeurTuile(LARGEUR_TUILE);
	}

	/**
	 * Lit, redimensionne et définit l'image pour chaque type de tuile
	 */
	// Jason Xa
	private void lireImages() {
		Carre.setImageRef(OutilsImage.lireImageEtRedimensionner("carre.jpg", LARGEUR_TUILE, HAUTEUR_TUILE));
		TriangleEquilateral.setImageRef(
				OutilsImage.lireImageEtRedimensionner("triangle_equilateral.png", LARGEUR_TUILE, HAUTEUR_TUILE));
		TriangleRectangle.setImageRef(
				OutilsImage.lireImageEtRedimensionner("triangle_rectangle.png", LARGEUR_TUILE, HAUTEUR_TUILE));
		Portail.setImageRef(OutilsImage.lireImageEtRedimensionner("portail.png", LARGEUR_TUILE, HAUTEUR_TUILE));
		Drapeau.setImageRef(OutilsImage.lireImageEtRedimensionner("drapeau.png", LARGEUR_TUILE, HAUTEUR_TUILE));
		Pics.setImageRef(OutilsImage.lireImageEtRedimensionner("pics.png", LARGEUR_TUILE, HAUTEUR_DEMI_TUILE));
		Vaisseau.setImageRef(OutilsImage.lireImageEtRedimensionner("vaisseau.png", LARGEUR_TUILE, HAUTEUR_TUILE));
	}

	/**
	 * Créer les boutons.
	 */
	// Kitimir Yim
	private void creerBoutons() {

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuQuitter();

			}
		});
		btnQuitter.setBounds(975, 175, 150, 100);
		contentPane.add(btnQuitter);

		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenInstruction.setVisible(true);
				if (leClip != null)
					leClip.close();
				chargerLeSon(NOM_FICHIER_SON_2);
				leClip.start();

			}
		});
		btnInstructions.setBounds(225, 470, 150, 100);
		contentPane.add(btnInstructions);

		JButton btnAPropos = new JButton("À propos");
		btnAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenApropos.setVisible(true);
				if (leClip != null)
					leClip.close();
				chargerLeSon(NOM_FICHIER_SON_2);
				leClip.start();

			}
		});

		btnAPropos.setBounds(975, 470, 150, 100);
		contentPane.add(btnAPropos);

		JButton btnReglages = new JButton("Réglages");
		btnReglages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenReglage.setVisible(true);
			}
		});
		btnReglages.setBounds(600, 470, 150, 100);
		contentPane.add(btnReglages);

		JButton btnModePrincipal = new JButton("Jouer");
		btnModePrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panS.setVisible(true);
				contentPane.setVisible(false);
				setContentPane(panS);
				menuBar.setVisible(true);
				mntmSelection.setSelected(true);
				mntmEditeur.setSelected(false);
			}
		});
		btnModePrincipal.setBounds(225, 175, 150, 100);
		contentPane.add(btnModePrincipal);

		JButton btnModeEditeur = new JButton("Mode éditeur");
		btnModeEditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panE.setVisible(true);
				contentPane.setVisible(false);
				setContentPane(panE);
				menuBar.setVisible(true);
				mntmSelection.setSelected(false);
				mntmEditeur.setSelected(true);
			}
		});
		btnModeEditeur.setBounds(600, 175, 150, 100);
		contentPane.add(btnModeEditeur);

	}

	/**
	 * Créer les fenêtres pour les réglages, les instructions et les À propos
	 */
	// Kitimir Yim
	public void creerFenetres() {

		fenApropos = new FenetreApropos();
		fenInstruction = new FenetreInstruction();
		fenReglage = new FenetreReglage();

		fenReglage.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("changerSon")) {
					int son = (int) evt.getNewValue();
					double nouvelleValeurSon = (double) son / 100;
					modifierVolume(nouvelleValeurSon);

				}
			}
		});

	}

	/**
	 * Créer les panels pour le mode éditeur et le mode jeu
	 */
	// Kitimir Yim
	public void creerPanels() {
		panE = new PanelModeEditeur();
		panS = new PanelSelecteurNiveaux();
		panJ = new PanelModeJeu();

		panS.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("passerVersJeu")) {
					panS.setVisible(false);
					panJ.setVisible(true);
					setContentPane(panJ);

				}
			}
		});

	}

	/**
	 * Bouton pour quitter l'application
	 */
	// Kitimir Yim
	public void menuQuitter() {
		int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir quitter l'application?",
				"Confirmation", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Creer le menu.
	 */
	// Kitimir Yim
	private void creerMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setVisible(false);

		JMenuItem mntmPrincipale = new JMenuItem("Menu principal");
		mntmPrincipale.setPreferredSize(new Dimension(100, 26));
		mntmPrincipale.setMaximumSize(new Dimension(200, 32767));

		mntmPrincipale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				contentPane.setVisible(true);
				panE.setVisible(false);
				panS.setVisible(false);
				setContentPane(contentPane);
				menuBar.setVisible(false);
			}

		});
		menuBar.add(mntmPrincipale);

		mntmSelection = new JMenuItem("Jouer");

		mntmSelection.setPreferredSize(new Dimension(100, 26));
		mntmSelection.setMaximumSize(new Dimension(200, 32767));

		mntmSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("lebronjames");
				contentPane.setVisible(false);
				panE.setVisible(false);
				panS.setVisible(true);
				setContentPane(panS);
				mntmSelection.setSelected(true);
				mntmEditeur.setSelected(false);

			}

		});
		menuBar.add(mntmSelection);
		mntmEditeur = new JMenuItem("Éditeur");
		mntmEditeur.setPreferredSize(new Dimension(100, 26));
		mntmEditeur.setMaximumSize(new Dimension(200, 32767));

		mntmEditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panE.setVisible(true);
				panS.setVisible(false);
				contentPane.setVisible(false);
				setContentPane(panE);
				mntmSelection.setSelected(false);
				mntmEditeur.setSelected(true);
			}
		});
		menuBar.add(mntmEditeur);

		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.setMaximumSize(new Dimension(200, 32767));
		mntmInstructions.setPreferredSize(new Dimension(100, 26));

		mntmInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenInstruction.setVisible(true);

			}

		});
		menuBar.add(mntmInstructions);

		JMenuItem mntmReglage = new JMenuItem("Réglage");
		mntmReglage.setMaximumSize(new Dimension(200, 32767));
		mntmReglage.setPreferredSize(new Dimension(100, 26));

		mntmReglage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenReglage.setVisible(true);

			}

		});
		menuBar.add(mntmReglage);

		JMenuItem mntmApropos = new JMenuItem("À propos");
		mntmApropos.setPreferredSize(new Dimension(100, 26));
		mntmApropos.setMaximumSize(new Dimension(200, 32767));
		mntmSelection.setMaximumSize(new Dimension(150, 300));

		mntmApropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenApropos.setVisible(true);

			}

		});
		menuBar.add(mntmApropos);

	}

	/**
	 * Methode privee pour lire le son et en faire un clip
	 * La méthode a éte trouvée dans le materiel d'appoint (de Caroline Houle) mais
	 * a été implementé et
	 * modifier pour notre code
	 * 
	 * @param fichier Le fichier son
	 */
	// Kitimir Yim
	private void chargerLeSon(String fichier) {

		try {
			// si ce n'est pas la premiere fois, on evite de reacceder au fichier sur disque
			// (consomme du temps)
			if (audioStr == null) {
				pathDeFichier = getClass().getClassLoader().getResource(fichier).getFile();
				objetFichier = new File(pathDeFichier);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incapable d'ouvrir le fichier de son ");
			e.printStackTrace();
			return;
		}
		try {
			audioStr = AudioSystem.getAudioInputStream(objetFichier);
			leClip = AudioSystem.getClip();
			leClip.open(audioStr);

			// ces 2 lignes sont necessaires seulement si on souhaite gerer le volume
			FloatControl volume = (FloatControl) leClip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(20f * (float) Math.log10((float) volumeEntre0Et1));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probl�me � la cr�ation du clip (son)! " + fichier);
			e.printStackTrace();
			return;
		}

	}// fin methode

	/**
	 * Pour la gestion du volume si désiré
	 * 
	 * @param valeurEntre0Et1 valeur du volume, 1=volume original du son 0=aucun
	 *                        volume
	 *                        La méthode a éte trouvée dans le materiel d'appoint
	 *                        (de Caroline Houle)
	 *                        mais a été implementé pour notre code
	 */
	// Kitimir Yim
	private void modifierVolume(double valeurEntre0Et1) {
		this.volumeEntre0Et1 = valeurEntre0Et1;
		if (leClip != null && leClip.isRunning()) {
			FloatControl volume = (FloatControl) leClip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(20f * (float) Math.log10((float) valeurEntre0Et1));
		}
	}

}
