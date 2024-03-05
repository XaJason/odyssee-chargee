package application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

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
import utilis.OutilsImage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import java.awt.Dimension;

/**
 * Projet intégrateur : Odyssée chargée
 * 
 * @author Pierre Olivier-Giroux
 * @author Enuel René Valentin Kizozo Izia
 * @author Jason Xa
 * @author Kitimir Yim
 */
public class AppPrincipale22 extends JFrame {

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

	private Clip leClip = null;
	private final String NOM_FICHIER_SON_1 = "Musique_Fond.wav";
	private AudioInputStream audioStr;
	private double volumeEntre0Et1 = 1.0;
	private String pathDeFichier = null;
	private File objetFichier = null;

	private final int LARGEUR_TUILE = 64;
	private final int HAUTEUR_TUILE = 64;
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

		if (leClip != null)
			leClip.close();
		chargerLeSon(NOM_FICHIER_SON_1);
		leClip.loop(Clip.LOOP_CONTINUOUSLY);
	}

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
		btnQuitter.setBounds(92, 335, 89, 23);
		contentPane.add(btnQuitter);

		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenInstruction.setVisible(true);

			}
		});
		btnInstructions.setBounds(362, 335, 89, 23);
		contentPane.add(btnInstructions);

		JButton btnAPropos = new JButton("À propos");
		btnAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenApropos.setVisible(true);

			}
		});

		btnAPropos.setBounds(92, 397, 89, 23);
		contentPane.add(btnAPropos);

		JButton btnNewButton_3 = new JButton("Réglages");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenReglage.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(372, 397, 89, 23);
		contentPane.add(btnNewButton_3);

		JButton btnModePrincipal = new JButton("Sélection de niveaux");
		btnModePrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panS.setVisible(true);
				contentPane.setVisible(false);
				setContentPane(panS);
				menuBar.setVisible(true);
			}
		});
		btnModePrincipal.setBounds(92, 457, 133, 23);
		contentPane.add(btnModePrincipal);

		JButton btnModeEditeur = new JButton("Mode éditeur");
		btnModeEditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panE.setVisible(true);
				contentPane.setVisible(false);
				setContentPane(panE);
				menuBar.setVisible(true);
			}
		});
		btnModeEditeur.setBounds(362, 457, 168, 23);
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

		panE.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("passerVersMenu")) {
					panE.setVisible(false);
					contentPane.setVisible(true);
					setContentPane(contentPane);
					menuBar.setVisible(false);
				}
			}
		});

		panS.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("passerVersMenu")) {
					panS.setVisible(false);
					contentPane.setVisible(true);
					setContentPane(contentPane);
					menuBar.setVisible(false);
				} else if (evt.getPropertyName().equals("passerVersJeu")) {
					panS.setVisible(false);
					panJ.setVisible(true);
					setContentPane(panJ);

				}
			}
		});

		panJ.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("passerVersNiveaux")) {

					panJ.setVisible(false);
					panS.setVisible(true);
					setContentPane(panS);
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

		JMenuItem mntmSelection = new JMenuItem("Sélection de niveaux");
		mntmSelection.setMaximumSize(new Dimension(150, 32767));

		mntmSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("lebronjames");
				contentPane.setVisible(false);
				panE.setVisible(false);
				panS.setVisible(true);
				setContentPane(panS);

			}

		});
		menuBar.add(mntmSelection);
		JMenuItem mntmEditeur = new JMenuItem("Éditeur");
		mntmEditeur.setMaximumSize(new Dimension(150, 32767));

		mntmEditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panE.setVisible(true);
				panS.setVisible(false);
				contentPane.setVisible(false);
				setContentPane(panE);
			}
		});
		menuBar.add(mntmEditeur);

	}

	/**
	 * Methode privee pour lire le son et en faire un clip
	 * La méthode a éte trouvée dans le materiel d'appoint mais a été implementé et
	 * modifier pour notre code
	 */
	// Caroline Houle && Kitimir Yim
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
	 * Pour la gestion du volume si d�sire
	 * 
	 * @param valeurEntre0Et1 valeur du volume, 1=volume original du son 0=aucun
	 *                        volume
	 *                        La méthode a éte trouvée dans le materiel d'appoint
	 *                        mais a été implementé pour notre code
	 */
	// Caroline Houle && Kitimir Yim
	private void modifierVolume(double valeurEntre0Et1) {
		this.volumeEntre0Et1 = valeurEntre0Et1;
		if (leClip != null && leClip.isRunning()) {
			FloatControl volume = (FloatControl) leClip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(20f * (float) Math.log10((float) valeurEntre0Et1));
		}
	}

}
