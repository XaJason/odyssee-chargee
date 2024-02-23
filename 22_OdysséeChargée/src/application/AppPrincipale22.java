package application;




import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;


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
		

	}
}
