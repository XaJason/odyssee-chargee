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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dessin.ZoneAnimationPhysique;
import fenetres.FenetreAPropos;
import fenetres.FenetreAideInstructions;
import fenetres.FenetreReglage;
import niveau.Niveau;
import panneaux.PanelEditeur;
import panneaux.PanelJeu;
import panneaux.PanelSelecteurNiveaux;
import tuile.Carre;
import tuile.Drapeau;
import tuile.Pics;
import tuile.Portail;
import tuile.TriangleEquilateral;
import tuile.TriangleRectangle;
import tuile.Tuile;
import tuile.VaisseauImage;
import utilitaires.ConstanteComposantsSwing;

/**
 * Application permettant d'accéder au jeu Odyssée chargée
 * 
 * @author Jason Xa
 * @author Kitimir Yim
 * @author Enuel René Valentin Kizozo Izia
 */
public class AppPrincipale22 extends JFrame {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -506870656338933836L;
	/**
	 * Zone des dessin
	 */
	private JPanel panMenuPrincipal;
	/**
	 * Fenêtre Instructions
	 */
	private FenetreAideInstructions fenInstruction;
	/**
	 * Fenêtre Réglage
	 */
	private FenetreReglage fenReglage;
	/**
	 * Panel du mode éditeur
	 */
	private PanelEditeur panModeEditeur;
	/**
	 * Panel du mode jeu
	 */
	private PanelJeu panModeJeu;
	/**
	 * Panel À propos
	 */
	private FenetreAPropos pnlAPropos;
	/**
	 * Panel du sélecteur de niveaux
	 */
	private PanelSelecteurNiveaux panSelecteurNiveau;
	/**
	 * Barre du menu
	 */
	private JMenuBar menuBar;
	/**
	 * Item du menu pour le mode éditeur
	 */
	private JMenuItem mntmEditeur;
	/**
	 * Item du menu pour le mode sélection de niveaux
	 */
	private JMenuItem mntmSelection;
	/**
	 * Clip par défaut du son
	 */
	private Clip leClip = null;
	/**
	 * String du fichier de la musique de fond
	 */
	private final String NOM_FICHIER_SON_1 = "Musique_Fond.wav";
	/**
	 * Flux d'entrée audio.
	 */
	private AudioInputStream audioStr;
	/**
	 * Volume du son
	 */
	private double volumeEntre0Et1 = 1;
	/**
	 * ` Chemin vers le fichier
	 */
	private String pathDeFichier = null;
	/**
	 * Fichier
	 */
	private File objetFichier = null;

	/** largeur d'une tuile */
	private final int LARGEUR_TUILE = 60;
	/** hauteur d'une tuile */
	private final int HAUTEUR_TUILE = 60;
	/** hauteur d'une demi-tuile */
	private final int HAUTEUR_DEMI_TUILE = HAUTEUR_TUILE / 2;
	/** Diamètre du vaisseau **/
	private final int DIAMETRE_VAISSEAU = LARGEUR_TUILE / 2;

	/**
	 * Lance l'application
	 * 
	 * @param args Paramètre d'entrée de la commande de démarrage
	 */
	// Kitimir Yim
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppPrincipale22 frame = new AppPrincipale22();
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.panModeJeu.getZoneAnimationPhysique().requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Créer la page principale.
	 */
	// Kitimir Yim
	public AppPrincipale22() {
		setTitle("Odyssée chargée");
		panMenuPrincipal = new JPanel();
		setContentPane(panMenuPrincipal);
		panMenuPrincipal.setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 25, ConstanteComposantsSwing.DIM_HORIZONTALE_APP, ConstanteComposantsSwing.DIM_VERTICALE_APP);

		creerBoutons();
		creerFenetres();
		creerPanels();
		creerMenu();
		lireImages();
		gererConstantes();
	
//		 if (leClip != null)
//		 leClip.close();
//		 chargerLeSon(NOM_FICHIER_SON_1);
//		 leClip.loop(Clip.LOOP_CONTINUOUSLY);
		 
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
	// Enuel René Valentin Kizozo Izia
	private void lireImages() {
		Carre.setImageRef("carre.jpg", LARGEUR_TUILE, HAUTEUR_TUILE);
		TriangleEquilateral.setImageRef("triangle_equilateral.png", LARGEUR_TUILE, HAUTEUR_TUILE);
		TriangleRectangle.setImageRef("triangle_rectangle.png", LARGEUR_TUILE, HAUTEUR_TUILE);
		Portail.setImageRef("portail.png", LARGEUR_TUILE, HAUTEUR_TUILE);
		Drapeau.setImageRef("drapeau.png", LARGEUR_TUILE, HAUTEUR_TUILE);
		Pics.setImageRef("pics.png", LARGEUR_TUILE, HAUTEUR_DEMI_TUILE);
		VaisseauImage.setImageRef("vaisseau.png", DIAMETRE_VAISSEAU, DIAMETRE_VAISSEAU);
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
		panMenuPrincipal.add(btnQuitter);

		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenInstruction.setVisible(true);

			}
		});
		btnInstructions.setBounds(225, 470, 150, 100);
		panMenuPrincipal.add(btnInstructions);

		JButton btnAPropos = new JButton("À propos");
		btnAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, pnlAPropos, "À propos de cette application",
						JOptionPane.PLAIN_MESSAGE);

			}
		});

		btnAPropos.setBounds(975, 470, 150, 100);
		panMenuPrincipal.add(btnAPropos);

		JButton btnReglages = new JButton("Réglages");
		btnReglages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenReglage.setVisible(true);
			}
		});
		btnReglages.setBounds(600, 470, 150, 100);
		panMenuPrincipal.add(btnReglages);

		JButton btnModePrincipal = new JButton("Jouer");
		btnModePrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panSelecteurNiveau.setVisible(true);
				panMenuPrincipal.setVisible(false);
				setContentPane(panSelecteurNiveau);
				menuBar.setVisible(true);
				mntmSelection.setSelected(true);
				mntmEditeur.setSelected(false);
			}
		});
		btnModePrincipal.setBounds(225, 175, 150, 100);
		panMenuPrincipal.add(btnModePrincipal);

		JButton btnModeEditeur = new JButton("Éditeur de niveaux");
		btnModeEditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panModeEditeur.setVisible(true);
				panMenuPrincipal.setVisible(false);
				setContentPane(panModeEditeur);
				menuBar.setVisible(true);
				mntmSelection.setSelected(false);
				mntmEditeur.setSelected(true);
			}
		});
		btnModeEditeur.setBounds(600, 175, 150, 100);
		panMenuPrincipal.add(btnModeEditeur);

	}

	/**
	 * Créer les fenêtres pour les réglages, les instructions et les À propos
	 */
	// Kitimir Yim
	public void creerFenetres() {

		pnlAPropos = new FenetreAPropos();
		fenInstruction = new FenetreAideInstructions();
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
		panModeEditeur = new PanelEditeur();
		panSelecteurNiveau = new PanelSelecteurNiveaux();
		panModeJeu = new PanelJeu();

		ZoneAnimationPhysique zone = panModeJeu.getZone();

		zone.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("retournerNiveau")) {
					panModeJeu.setVisible(false);
					panSelecteurNiveau.setVisible(true);
					setContentPane(panSelecteurNiveau);
				}
			}

		});

		panSelecteurNiveau.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("passerVersNiveau1")) {

					panModeJeu.modifierNiveauDeZoneAnimationPhysique("Niveau_base1");
					panSelecteurNiveau.setVisible(false);
					panModeJeu.setVisible(true);
					setContentPane(panModeJeu);
					panModeJeu.getZoneAnimationPhysique().requestFocus();
				}

				if (evt.getPropertyName().equals("passerVersNiveau2")) {
					panModeJeu.modifierNiveauDeZoneAnimationPhysique("Niveau_base2");
					panSelecteurNiveau.setVisible(false);
					panModeJeu.setVisible(true);
					setContentPane(panModeJeu);
					panModeJeu.getZoneAnimationPhysique().requestFocus();

				}
				if (evt.getPropertyName().equals("passerVersNiveau3")) {
					panModeJeu.modifierNiveauDeZoneAnimationPhysique("Niveau_base3");
					panSelecteurNiveau.setVisible(false);
					panModeJeu.setVisible(true);
					setContentPane(panModeJeu);
					panModeJeu.getZoneAnimationPhysique().requestFocus();
				}
				if (evt.getPropertyName().equals("passerVersJeu")) {
					chargerNiveauVersZoneAnimationPhysique(evt);

					panSelecteurNiveau.setVisible(false);
					panModeJeu.setVisible(true);
					setContentPane(panModeJeu);
					panModeJeu.getZoneAnimationPhysique().requestFocus();
				}

				if (evt.getPropertyName().equals("niveauSelectionne")) {
					chargerNiveauVersZoneAnimationPhysique(evt);
					panSelecteurNiveau.setVisible(false);
					panModeJeu.setVisible(true);
					setContentPane(panModeJeu);
					panModeJeu.getZoneAnimationPhysique().requestFocus();

				}
			}
		});

		panModeEditeur.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("niveauCree")) {
					panSelecteurNiveau.actualiserNiveaux();
				}
			}
		});

	}

	/**
	 * Chargement des niveau vers la zone d'animation physique
	 * 
	 * @param evt L'événement qui a été lancé
	 */
	// Enuel René Valentin Kizozo Izia
	private void chargerNiveauVersZoneAnimationPhysique(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("passerVersJeu")) {
			String nomNiveau = JOptionPane.showInputDialog("Entrez le nom du niveau");
			panModeJeu.modifierNiveauDeZoneAnimationPhysique(nomNiveau);

		}

		if (evt.getPropertyName().equals("niveauSelectionne")) {
			Niveau niveauSelectionne = (Niveau) evt.getNewValue();
			panModeJeu.modifierNiveauDeZoneAnimationPhysique(niveauSelectionne.getNom());

		}
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

				panMenuPrincipal.setVisible(true);
				panModeEditeur.setVisible(false);
				panSelecteurNiveau.setVisible(false);
				setContentPane(panMenuPrincipal);
				menuBar.setVisible(false);
			}

		});
		menuBar.add(mntmPrincipale);

		mntmSelection = new JMenuItem("Jouer");

		mntmSelection.setPreferredSize(new Dimension(100, 26));
		mntmSelection.setMaximumSize(new Dimension(200, 32767));

		mntmSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panMenuPrincipal.setVisible(false);
				panModeEditeur.setVisible(false);
				panSelecteurNiveau.setVisible(true);
				setContentPane(panSelecteurNiveau);
				mntmSelection.setSelected(true);
				mntmEditeur.setSelected(false);

			}

		});
		menuBar.add(mntmSelection);
		mntmEditeur = new JMenuItem("Éditeur de niveaux");
		mntmEditeur.setPreferredSize(new Dimension(100, 26));
		mntmEditeur.setMaximumSize(new Dimension(200, 32767));

		mntmEditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panModeEditeur.setVisible(true);
				panSelecteurNiveau.setVisible(false);
				panMenuPrincipal.setVisible(false);
				setContentPane(panModeEditeur);
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

		JMenuItem mntmReglage = new JMenuItem("Réglages");
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
				JOptionPane.showMessageDialog(null, pnlAPropos, "À propos de cette application",
						JOptionPane.PLAIN_MESSAGE);

			}

		});
		menuBar.add(mntmApropos);

	}

	/**
	 * Methode privee pour lire le son et en faire un clip La méthode a éte trouvée
	 * dans le materiel d'appoint (de Caroline Houle) mais a été implementé et
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
	 *                        volume La méthode a éte trouvée dans le materiel
	 *                        d'appoint (de Caroline Houle) mais a été implementé
	 *                        pour notre code
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
