package etatModeEditeur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Panel du mode éditeur
 * 
 * @author Kitimir Yim
 */

public class PanelModeEditeur extends JPanel {
	/**
	 * Grille du mode éditeur
	 */
	private Grille grille;

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
	 * 
	 */
	// Kitimir Yim
	public PanelModeEditeur() {
		setLayout(null);

		grille = new Grille();
		grille.setBackground(new Color(255, 255, 128));
		grille.setBounds(514, 39, 765, 703);
		add(grille);

		JButton btnVersMenu = new JButton("Passer au menu");
		btnVersMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				pcs.firePropertyChange("passerVersMenu", 0, -1);
			}
		});
		btnVersMenu.setBounds(178, 5, 145, 23);
		add(btnVersMenu);

		JButton btnGrille = new JButton("Afficher la grille");
		btnGrille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grille.afficherGrille();
			}
		});
		btnGrille.setBounds(858, 5, 421, 23);
		add(btnGrille);

		JSpinner spinnerQttCarre = new JSpinner();
		spinnerQttCarre.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Object objetInit = spinnerQttCarre.getValue();
				Number chiffreTransfo = (Number) objetInit;
				grille.changerQttCarre(chiffreTransfo.intValue());

			}
		});
		spinnerQttCarre.setModel(new SpinnerNumberModel(3, 2, 64, 1));
		spinnerQttCarre.setBounds(715, 6, 54, 20);
		add(spinnerQttCarre);

		JLabel lblQttCarre = new JLabel("Combien de carré par ligne:");
		lblQttCarre.setBounds(530, 9, 199, 14);
		add(lblQttCarre);

		ObjetEditeur objetEditeur = new ObjetEditeur();
		objetEditeur.setBounds(64, 49, 369, 703);
		add(objetEditeur);
	}
}