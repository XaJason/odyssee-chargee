package etatModeEditeur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * Panel du mode éditeur 
 * @author Kitimir Yim
 */


public class PanelModeEditeur extends JPanel{
	/**
	 * Grille du mode éditeur
	 */
	private Grille grille;

	/**
	 *  ajouter le support pour lancer des evenements de type PropertyChange
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/**
	 * methode qui permettra de s'ajouter en tant qu'ecouteur
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	//Kitimir Yim
	public PanelModeEditeur() {
		setLayout(null);

		grille = new Grille();
		grille.setBackground(new Color(255, 255, 128));
		grille.setBounds(52, 26, 897, 730);
		add(grille);
		
		JButton btnVersMenu = new JButton("Passer au menu");
		btnVersMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				pcs.firePropertyChange("passerVersMenu", 0, -1);
			}
		});
		btnVersMenu.setBounds(178, 5, 145, 23);
		add(btnVersMenu);
	}
	  
}