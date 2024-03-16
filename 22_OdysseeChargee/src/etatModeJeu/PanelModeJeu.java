package etatModeJeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextArea;

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
	private JSpinner spinnerGravité;

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

		JPanel panelEntree = new JPanel();
		panelEntree.setBorder(BorderFactory.createTitledBorder("Entrées"));

		panelEntree.setBounds(48, 53, 331, 340);
		add(panelEntree);
		panelEntree.setLayout(null);
		
		JLabel lblMasse = new JLabel("Masse (kg):");
        lblMasse.setBounds(10, 20, 100, 26);
        panelEntree.add(lblMasse);

        JLabel lblCharge = new JLabel("Charge (Coulombs):");
        lblCharge.setBounds(10, 88, 140, 26);
        panelEntree.add(lblCharge);

        JLabel lblGravite = new JLabel("Gravité (m/s²):");
        lblGravite.setBounds(10, 161, 120, 26);
        panelEntree.add(lblGravite);

        JLabel lblChargePlaque = new JLabel("Charge Plaque (Coulombs):");
        lblChargePlaque.setBounds(10, 224, 180, 26);
        panelEntree.add(lblChargePlaque);

        JLabel lblCoefFriction = new JLabel("Coefficient de friction:");
        lblCoefFriction.setBounds(10, 297, 160, 26);
        panelEntree.add(lblCoefFriction);

		JSpinner spinnerMasse = new JSpinner();
		spinnerMasse.setBounds(144, 12, 160, 43);
		panelEntree.add(spinnerMasse);

		JSpinner spinnerCharge = new JSpinner();
		spinnerCharge.setBounds(144, 80, 160, 43);
		panelEntree.add(spinnerCharge);

		spinnerGravité = new JSpinner();
		spinnerGravité.setBounds(144, 153, 160, 43);
		panelEntree.add(spinnerGravité);

		JSpinner spinnerChargePlaque = new JSpinner();
		spinnerChargePlaque.setBounds(144, 216, 160, 43);
		panelEntree.add(spinnerChargePlaque);

		JSpinner spinnerCoefFriction = new JSpinner();
		spinnerCoefFriction.setBounds(144, 289, 160, 43);
		panelEntree.add(spinnerCoefFriction);

		JPanel panelSortie = new JPanel();
		panelSortie.setLayout(null);
		panelSortie.setBorder(BorderFactory.createTitledBorder("Sorties"));
		panelSortie.setBounds(457, 53, 331, 340);
		add(panelSortie);

		JLabel lblVitesse = new JLabel("Vitesse (m/s):");
		lblVitesse.setBounds(35, 37, 100, 26);
		panelSortie.add(lblVitesse);

		JLabel lblAccélération = new JLabel("Accélération (m/s^2):");
		lblAccélération.setBounds(35, 73, 110, 26);
		panelSortie.add(lblAccélération);

		JLabel lblForceElectrique = new JLabel("Force électrique (N):");
        lblForceElectrique.setBounds(35, 113, 100, 26);
        panelSortie.add(lblForceElectrique);

        JLabel lblForceGravite = new JLabel("Force gravité (N):");
        lblForceGravite.setBounds(35, 153, 100, 26);
        panelSortie.add(lblForceGravite);

        JLabel lblChampElectrique = new JLabel("Champ électrique (N/m):");
        lblChampElectrique.setBounds(35, 197, 128, 26);
        panelSortie.add(lblChampElectrique);

        JLabel lblPosition = new JLabel("Position (m):");
        lblPosition.setBounds(35, 233, 100, 26);
        panelSortie.add(lblPosition);

        JTextArea textAreaVitesse = new JTextArea();
		textAreaVitesse.setBounds(160, 38, 150, 22);
		panelSortie.add(textAreaVitesse);
		
        JTextArea textAreaAccélération = new JTextArea();
		textAreaAccélération.setBounds(160, 74, 150, 22);
		panelSortie.add(textAreaAccélération);
		
		JTextArea textAreaForceElectrique = new JTextArea();
		textAreaForceElectrique.setEditable(false);
		textAreaForceElectrique.setBounds(160, 114, 150, 22);
		panelSortie.add(textAreaForceElectrique);

		JTextArea textAreaForceGravite = new JTextArea();
		textAreaForceGravite.setEditable(false);
		textAreaForceGravite.setBounds(160, 154, 150, 22);
		panelSortie.add(textAreaForceGravite);

		JTextArea textAreaChampElectrique = new JTextArea();
		textAreaChampElectrique.setEditable(false);
		textAreaChampElectrique.setBounds(160, 198, 150, 22);
		panelSortie.add(textAreaChampElectrique);

		JTextArea textAreaPosition = new JTextArea();
		textAreaPosition.setEditable(false);
		textAreaPosition.setBounds(160, 234, 150, 22);
		panelSortie.add(textAreaPosition);

	}
}
