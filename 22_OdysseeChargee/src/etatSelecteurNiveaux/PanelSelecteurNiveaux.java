package etatSelecteurNiveaux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * Panel du sélecteur de niveaux
 * @author Kitimir Yim
 */
public class PanelSelecteurNiveaux extends JPanel {

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
	public PanelSelecteurNiveaux() {
		
		setLayout(null);

		JButton btnVersMenu = new JButton("Passer au menu");
		btnVersMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				pcs.firePropertyChange("passerVersMenu", 0, -1);
			}
		});
		btnVersMenu.setBounds(178, 5, 109, 23);
		add(btnVersMenu);

	}

}
