package etatModeJeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel du mode de jeu
 * 
 * @author Kitimir Yim
 */
public class PanelModeJeu extends JPanel {

	/**
	 * ajouter le support pour lancer des evenements de type PropertyChange
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * methode qui permettra de s'ajouter en tant qu'ecouteur
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	/**
	 *  Implémente le panel et ses fonctionnalités
	 */
	// Kitimir Yim
	public PanelModeJeu() {
		setLayout(null);


	}

}
