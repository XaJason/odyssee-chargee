package application;




import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import constante.ConstanteComposantsSwing;


public class AppPrincipale22 extends JFrame{
	/**
	 * Zone des composants
	 */
	private JPanel contentPane;

	/**
	 * Lance l'application
	 * @param args Paramètre d'entrée de la commande de démarrage
	 */
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

	public AppPrincipale22(){
		setTitle("Odyssée Chargée");
		contentPane = new JPanel();
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,25, ConstanteComposantsSwing.DIM_HORIZONTALE_APP, ConstanteComposantsSwing.DIM_VERTICALE_APP);

		creerMenu();
	}


	/**
	 * Creer le menu.
	 */
	// Kitimir Yim
	
	//Normalement ce sera des boutons
	private void creerMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);

		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		mnAide.add(mntmInstructions);

		JMenuItem mntmApropos = new JMenuItem("À propos");
		mntmApropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


			}
		});
		mnAide.add(mntmApropos);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		mnAide.add(mntmQuitter);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenu mnPrincipale = new JMenu("Mode principale");
		menuBar.add(mnPrincipale);
		
		JMenu mnEditeur = new JMenu("Mode éditeur");
		menuBar.add(mnEditeur);
		
		


	}
}

