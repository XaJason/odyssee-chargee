package application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import javax.swing.JPanel;
import constante.ConstanteComposantsSwing;
import fenetres.FenetreApropos;
import fenetres.FenetreInstruction;
import fenetres.FenetreReglage;

/**
 * 
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
	 * Lance l'application
	 * 
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

	/**
	 * 
	 */
	public AppPrincipale22() {
		setTitle("Odyssée Chargée");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 25, ConstanteComposantsSwing.DIM_HORIZONTALE_APP, ConstanteComposantsSwing.DIM_VERTICALE_APP);
	

		creerBoutons();
		creerFenetres();
	}

	/**
	 * Créer les boutons.
	 */
	// Kitimir Yim

	// Normalement ce sera des boutons
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
		
		JButton btnModePrincipal = new JButton("Mode principal");
		btnModePrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModePrincipal.setBounds(92, 457, 89, 23);
		contentPane.add(btnModePrincipal);
		
		JButton btnModeEditeur = new JButton("Mode éditeur");
		btnModeEditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModeEditeur.setBounds(372, 457, 89, 23);
		contentPane.add(btnModeEditeur);	
			
		

	}
	/**
	 * Créer les fenêtres pour les réglages, les instructions et les À propos
	 */
	//Kitimir Yim
	public void creerFenetres() {
	
	fenApropos = new FenetreApropos();
	fenInstruction = new FenetreInstruction();
	fenReglage = new FenetreReglage();
	
	
	}
	 /**
     * Bouton pour quitter l'application
     */
    // Kitimir Yim
    public void menuQuitter() {
        int option = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir quitter l'application?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
