package application;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import dessin.ZoneAnimationPhysique;
import fenetres.FenetreAPropos;
import fenetres.FenetreAideInstructions;
import fenetres.FenetreReglage;
import niveau.Niveau;
import niveau.Sauvegarder;
import panneaux.FondEcran;
import panneaux.PanelEditeur;
import panneaux.PanelJeu;
import panneaux.PanelSelecteurNiveaux;
import utilitaires.ConstanteComposantsSwing;
import utilitaires.OutilsImage;

/**
 * Application permettant d'accéder au jeu Odyssée chargée
 *
 * @author Kitimir Yim
 * @author Enuel René Valentin Kizozo Izia
 */
public class Main extends JFrame {

	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -506870656338933836L;

	/**
	 * Flux d'entrée audio.
	 */
	private AudioInputStream audioStr;

	/**
	 * Boolean de si dans le mode Editeur
	 */
	private boolean dansEditeur = false;
	/**
	 * Fenêtre des instructions
	 */
	private FenetreAideInstructions fenInstruction;
	/**
	 * Fenêtre des réglages
	 */
	private FenetreReglage fenReglage;
	/**
	 * Clip par défaut du son
	 */
	private Clip leClip = null;
	/**
	 * Barre de menus
	 */
	private JMenuBar menuBar;
	/**
	 * Item du menu pour le mode éditeur
	 */
	private JMenuItem mntmEditeur;
	/**
	 * Item du menu pour le mode sélection de niveau
	 */
	private JMenuItem mntmSelection;
	/**
	 * String du fichier de la musique de fond
	 */
	private final String NOM_FICHIER_SON_1 = "Musique_Fond.wav";
	/**
	 * Panneau du menu principal
	 */
	private JPanel panMenuPrincipal;
	/**
	 * Panneau de l'éditeur de niveau
	 */
	private PanelEditeur panModeEditeur;
	/**
	 * Panneau de jeu
	 */
	private PanelJeu panModeJeu;
	/**
	 * Panneau de sélection de niveau
	 */
	private PanelSelecteurNiveaux panSelecteurNiveau;
	/**
	 * Panneau d'information sur l'application
	 */
	private FenetreAPropos pnlAPropos;
	/**
	 * Url du fichier
	 */
	private URL urlFichier = null;
	/**
	 * Volume du son
	 */
	private double volumeEntre0Et1 = 1;

	/** Zone d'animation physique du panneau de jeu */
	private ZoneAnimationPhysique zoneAnimation;

	/**
	 * Créer la page principale.
	 */
	// Kitimir Yim
	public Main() {
		setTitle("Odyssée chargée");
		panMenuPrincipal = new JPanel();
		setContentPane(panMenuPrincipal);
		panMenuPrincipal.setLayout(null);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 25, ConstanteComposantsSwing.DIM_HORIZONTALE_APP, ConstanteComposantsSwing.DIM_VERTICALE_APP);

		creerBoutons();
		creerFenetres();
		creerPanels();
		creerMenu();

		if (leClip != null) {
			leClip.close();
		}
		chargerLeSon(NOM_FICHIER_SON_1);
		leClip.loop(Clip.LOOP_CONTINUOUSLY);
	}

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
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main frame = new Main();
					ImageIcon icon = new ImageIcon("ressources/vaisseau.png");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
					frame.panModeJeu.getZoneAnimationPhysique().requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
			@Override
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

		panModeEditeur.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("niveau essai")) {
					Niveau niveauEssai = (Niveau) evt.getNewValue();
					panModeJeu.modifierNiveauDeZoneAnimationPhysique(niveauEssai);
					panModeJeu.setVisible(true);
					panModeEditeur.setVisible(false);
					setContentPane(panModeJeu);
					panModeJeu.getZoneAnimationPhysique().requestFocusInWindow();
				}
			}
		});

		zoneAnimation = panModeJeu.getZoneAnimationPhysique();
		zoneAnimation.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("retournerNiveau")) {
					modifierEtatApplicationPourSelectionNiveau();
					panModeJeu.reinitialiserPanneauEtZoneAnimation();
				}
			}

		});

		panSelecteurNiveau.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getPropertyName().equals("passerVersNiveau1")) {
					panModeJeu.modifierNiveauDeZoneAnimationPhysiqueDeBase("Niveau_base1");
					panModeJeu.setModeJetpack(false);
					miseAJourChargementNiveau();
				}
				if (evt.getPropertyName().equals("passerVersNiveau2")) {
					panModeJeu.modifierNiveauDeZoneAnimationPhysiqueDeBase("Niveau_base2");
					panModeJeu.setModeJetpack(false);
					miseAJourChargementNiveau();

				}
				if (evt.getPropertyName().equals("passerVersNiveau3")) {
					panModeJeu.modifierNiveauDeZoneAnimationPhysiqueDeBase("Niveau_base3");
					panModeJeu.setModeJetpack(false);
					miseAJourChargementNiveau();
				}

				ajoutNiveauxBase(evt);

				if (evt.getPropertyName().equals("passerVersJeu")) {
					chargerNiveauVersZoneAnimationPhysique();

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
	 * Ajout de trois autres niveaux de base
	 *
	 * @param evt L'événement qui a été lancé
	 */
	// Enuel René Valentin Kizozo Izia
	private void ajoutNiveauxBase(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("passerVersNiveau4")) {
			panModeJeu.modifierNiveauDeZoneAnimationPhysiqueDeBase("Niveau_base4");
			panModeJeu.setModeJetpack(true);
			miseAJourChargementNiveau();
		}
		if (evt.getPropertyName().equals("passerVersNiveau5")) {
			panModeJeu.modifierNiveauDeZoneAnimationPhysiqueDeBase("Niveau_base5");
			panModeJeu.setModeJetpack(true);
			miseAJourChargementNiveau();
		}
		if (evt.getPropertyName().equals("passerVersNiveau6")) {
			panModeJeu.modifierNiveauDeZoneAnimationPhysiqueDeBase("Niveau_base6");
			panModeJeu.setModeJetpack(true);
			miseAJourChargementNiveau();
		}
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
			if (urlFichier == null) {
				urlFichier = getClass().getClassLoader().getResource(NOM_FICHIER_SON_1);

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incapable d'ouvrir le fichier de son ");
			e.printStackTrace();
			return;
		}
		try {
			if (audioStr != null) {
				audioStr.close();
				leClip.close();
			}
			audioStr = AudioSystem.getAudioInputStream(urlFichier);
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

	}

	/**
	 * Chargement des niveau avec un JFileChooser vers la zone d'animation physique
	 *
	 */
	// Kitimir Yim
	private void chargerNiveauVersZoneAnimationPhysique() {

		JFileChooser fichierNiveaux = new JFileChooser();
		String userHome = System.getProperty("user.home");
		String oneDrive = userHome + File.separator + "OneDrive";
		String documents = oneDrive + File.separator + "Documents";
		String mesTrucs = documents + File.separator + "MesTrucs";
		fichierNiveaux.setCurrentDirectory(new File(mesTrucs));

		int resultat = fichierNiveaux.showOpenDialog(Main.this);

		if (resultat == JFileChooser.APPROVE_OPTION) {

			File fichierChoisi = fichierNiveaux.getSelectedFile();
			String nomFichier = fichierChoisi.getName();
			Niveau niveau = Sauvegarder.chargerNiveauMesTrucs(nomFichier);
			panModeJeu.modifierNiveauDeZoneAnimationPhysique(niveau);
			miseAJourChargementNiveau();
		}

	}

	/**
	 * Créer les boutons.
	 */
	// Kitimir Yim
	private void creerBoutons() {

		FondEcran fondEcran = new FondEcran("fond.jpg", 1);
		fondEcran.setBounds(0, 0, 1920, 1080);
		panMenuPrincipal.add(fondEcran);
		fondEcran.setLayout(null);

		JButton btnModeEditeur = new JButton("Éditeur de niveau");
		btnModeEditeur.setFocusable(false);
		btnModeEditeur.setBounds(771, 304, 403, 104);
		OutilsImage.lireImageEtPlacerSurBouton("editeur.png", btnModeEditeur);
		fondEcran.add(btnModeEditeur);

		JButton btnReglages = new JButton("Réglages");
		btnReglages.setFocusable(false);
		btnReglages.setBounds(839, 510, 243, 100);
		OutilsImage.lireImageEtPlacerSurBouton("reglages.png", btnReglages);
		fondEcran.add(btnReglages);

		JButton btnAPropos = new JButton("À propos");
		btnAPropos.setFocusable(false);
		btnAPropos.setBounds(824, 621, 272, 104);
		OutilsImage.lireImageEtPlacerSurBouton("apropos.png", btnAPropos);
		fondEcran.add(btnAPropos);

		JButton btnInstructions = new JButton("Aide");
		btnInstructions.setFocusable(false);
		btnInstructions.setBounds(891, 419, 138, 80);
		OutilsImage.lireImageEtPlacerSurBouton("aide.png", btnInstructions);
		fondEcran.add(btnInstructions);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setFocusable(false);
		btnQuitter.setBounds(839, 736, 242, 70);
		OutilsImage.lireImageEtPlacerSurBouton("quitter.png", btnQuitter);
		fondEcran.add(btnQuitter);

		JButton btnSelectionDeNiveau = new JButton("Jouer");
		btnSelectionDeNiveau.setFocusable(false);
		btnSelectionDeNiveau.setBounds(891, 223, 138, 70);
		OutilsImage.lireImageEtPlacerSurBouton("jouer.png", btnSelectionDeNiveau);
		fondEcran.add(btnSelectionDeNiveau);

		JButton btnTitre = new JButton("New button");
		btnTitre.setFocusable(false);
		btnTitre.setBounds(699, 60, 522, 86);
		OutilsImage.lireImageEtPlacerSurBouton("titre.png", btnTitre);

		fondEcran.add(btnTitre);

		btnSelectionDeNiveau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifierEtatApplicationPourSelectionNiveau();
				dansEditeur = false;
			}
		});

		btnQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuQuitter();

			}
		});
		btnInstructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenInstruction.setVisible(true);

			}
		});
		btnAPropos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, pnlAPropos, "À propos de cette application",
						JOptionPane.PLAIN_MESSAGE);

			}
		});
		btnReglages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenReglage.setVisible(true);
			}
		});
		btnModeEditeur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifierEtatApplicationPourModeEditeur();
				dansEditeur = true;
				panModeEditeur.setSauvegarde(true);
			}
		});

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
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dansEditeur) {
					if (ouiOuNon()) {
						modifierEtatApplicationPourMenuPrincipal();
						panModeJeu.reinitialiserPanneauEtZoneAnimation();
					} else {
						dansEditeur = true;
					}
					if (panModeEditeur.getSauvegarde()) {
						modifierEtatApplicationPourMenuPrincipal();
						panModeJeu.reinitialiserPanneauEtZoneAnimation();

					}

				}

				if (!dansEditeur) {

					modifierEtatApplicationPourMenuPrincipal();
					panModeJeu.reinitialiserPanneauEtZoneAnimation();

				}

			}

		});
		menuBar.add(mntmPrincipale);

		mntmSelection = new JMenuItem("Jouer");

		mntmSelection.setPreferredSize(new Dimension(100, 26));
		mntmSelection.setMaximumSize(new Dimension(200, 32767));

		mntmSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dansEditeur) {
					if (ouiOuNon()) {
						modifierEtatApplicationPourSelectionNiveau();
						panModeJeu.reinitialiserPanneauEtZoneAnimation();

					} else {
						dansEditeur = true;

					}
					if (panModeEditeur.getSauvegarde()) {
						modifierEtatApplicationPourSelectionNiveau();
						panModeJeu.reinitialiserPanneauEtZoneAnimation();

					}

				}

				if (!dansEditeur) {

					modifierEtatApplicationPourSelectionNiveau();
					panModeJeu.reinitialiserPanneauEtZoneAnimation();

				}

			}

		});
		menuBar.add(mntmSelection);
		mntmEditeur = new JMenuItem("Éditeur de niveaux");
		mntmEditeur.setPreferredSize(new Dimension(100, 26));
		mntmEditeur.setMaximumSize(new Dimension(200, 32767));

		mntmEditeur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				modifierEtatApplicationPourModeEditeur();
				panModeJeu.reinitialiserPanneauEtZoneAnimation();
				dansEditeur = true;
				panModeEditeur.setSauvegarde(true);

			}
		});
		menuBar.add(mntmEditeur);

		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.setMaximumSize(new Dimension(200, 32767));
		mntmInstructions.setPreferredSize(new Dimension(100, 26));

		mntmInstructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenInstruction.setVisible(true);
			}

		});
		menuBar.add(mntmInstructions);

		JMenuItem mntmReglage = new JMenuItem("Réglages");
		mntmReglage.setMaximumSize(new Dimension(200, 32767));
		mntmReglage.setPreferredSize(new Dimension(100, 26));

		mntmReglage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenReglage.setVisible(true);
			}

		});
		menuBar.add(mntmReglage);

		JMenuItem mntmApropos = new JMenuItem("À propos");
		mntmApropos.setPreferredSize(new Dimension(100, 26));
		mntmApropos.setMaximumSize(new Dimension(200, 32767));

		mntmApropos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, pnlAPropos, "À propos de cette application",
						JOptionPane.PLAIN_MESSAGE);
			}

		});
		menuBar.add(mntmApropos);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuQuitter();
			}
		});
		mntmQuitter.setPreferredSize(new Dimension(100, 26));
		mntmQuitter.setMaximumSize(new Dimension(200, 32767));
		menuBar.add(mntmQuitter);

	}

	/**
	 * Mise à jour des panneaux de l'application lors du chargement d'un niveau
	 */
	// Enuel René Valentin Kizozo Izia
	private void miseAJourChargementNiveau() {
		panSelecteurNiveau.setVisible(false);
		panModeJeu.setVisible(true);
		setContentPane(panModeJeu);
		panModeJeu.getZoneAnimationPhysique().requestFocusInWindow();
	}

	/**
	 * Série d'action effectuée afin de modifier l'état de l'application, lorsqu'on
	 * accède au menu principal:
	 *
	 * Mise à jour de la visibilité de certains panneaux de regroupement et
	 * réinitialisation de l'état des boutons du Mode Jeu
	 */
	// Kitimir Yim
	private void modifierEtatApplicationPourMenuPrincipal() {
		panMenuPrincipal.setVisible(true);
		panModeEditeur.setVisible(false);
		panSelecteurNiveau.setVisible(false);
		setContentPane(panMenuPrincipal);
		menuBar.setVisible(false);
	}

	/**
	 * Série d'action effectuée afin de modifier l'état de l'application, lorsqu'on
	 * accède à l'édition de niveau :
	 *
	 * Mise à jour de la visibilité de certains panneaux de regroupement et
	 * réinitialisation de l'état des boutons du Mode Jeu
	 */
	// Kitimir Yim
	private void modifierEtatApplicationPourModeEditeur() {
		panMenuPrincipal.setVisible(false);
		panSelecteurNiveau.setVisible(false);
		panModeJeu.setVisible(false);
		panModeEditeur.setVisible(true);

		menuBar.setVisible(true);
		mntmSelection.setSelected(false);
		mntmEditeur.setSelected(true);
		setContentPane(panModeEditeur);

		panModeEditeur.getGrille().requestFocusInWindow();
		panModeEditeur.getGrille().setDansModeJeu(false);

	}

	/**
	 * Série d'action effectuée afin de modifier l'état de l'application, lorsqu'on
	 * accède à la sélection de niveau :
	 *
	 * Mise à jour de la visibilité de certains panneaux de regroupement et
	 * réinitialisation de l'état des boutons du Mode Jeu
	 */
	// Kitimir Yim
	private void modifierEtatApplicationPourSelectionNiveau() {
		panSelecteurNiveau.setVisible(true);
		panMenuPrincipal.setVisible(false);
		panModeEditeur.setVisible(false);
		panModeJeu.setVisible(false);

		menuBar.setVisible(true);
		mntmSelection.setSelected(true);
		mntmEditeur.setSelected(false);
		setContentPane(panSelecteurNiveau);
	}

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

	/**
	 * Vérifie si la sauvegarde a été effectué
	 *
	 * @return boolean vrai ou faux
	 */
	// Kitimir Yim
	private boolean ouiOuNon() {
		if (!panModeEditeur.getSauvegarde()) {
			int choix = JOptionPane.showConfirmDialog(null,
					"Vous n'avez pas sauvegardé vos modifications. Êtes-vous sûr de vouloir quitter ?", "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			dansEditeur = false;

			return choix == JOptionPane.YES_OPTION;
		} else {

			return false;
		}
	}
}
