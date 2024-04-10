package panneaux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import niveau.GestionnaireDeNiveaux;
import niveau.Niveau;

/**
 * Panel du sélecteur de niveaux
 * 
 * @author Kitimir Yim
 */
public class PanelSelecteurNiveaux extends JPanel {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -8288362081093027798L;
	/**
	 * Ajouter le support pour lancer des évenements de type PropertyChange
	 */
	private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);
	private JButton btnNiveauBase1;
	private JButton btnNiveauBase2;
	private JButton btnNiveauBase3;

	/**
	 * Voici la méthode qui permettra à un objet de s'ajouter en tant qu'écouteur
	 * 
	 * @param listener L'objet PropertyChangeListener à ajouter comme écouteur de
	 *                 propriété.
	 */
	// Kitimir Yim
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}

	/**
	 * Implémente le panel et ses fonctionnalités
	 */
	// Kitimir Yim
	public PanelSelecteurNiveaux() {

		setLayout(null);

		JButton btnPasserModeJeu = new JButton("Bouton Play");
		btnPasserModeJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersJeu", 0, -1);
			}
		});
		btnPasserModeJeu.setBounds(47, 50, 200, 30);
		add(btnPasserModeJeu);

		btnNiveauBase1 = new JButton("Niveau de base 1");
		btnNiveauBase1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau1", 0, -1);
			}
		});
		btnNiveauBase1.setBounds(277, 50, 200, 30);
		add(btnNiveauBase1);

		btnNiveauBase2 = new JButton("Niveau de base 2");
		btnNiveauBase2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau2", 0, -1);
			}
		});
		btnNiveauBase2.setBounds(277, 103, 200, 30);
		add(btnNiveauBase2);

		btnNiveauBase3 = new JButton("Niveau de base 3");
		btnNiveauBase3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau3", 0, -1);
			}
		});
		btnNiveauBase3.setBounds(277, 153, 200, 30);
		add(btnNiveauBase3);

	}

	/**
	 * Actualise les boutons représentant les niveaux disponibles
	 */
	// Kitimir Yim
	public void actualiserNiveaux() {
		// removeAll(); // Supprimer tous les composants existants

		ArrayList<Niveau> niveaux = GestionnaireDeNiveaux.getRepertoireNiveau();

		int yPosition = 90;
		for (Niveau niveau : niveaux) {
			JButton btnNiveau = new JButton(niveau.getNom());
			btnNiveau.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PCS.firePropertyChange("niveauSelectionne", null, niveau);
				}
			});
			btnNiveau.setBounds(50, yPosition, 200, 30);
			add(btnNiveau);
			yPosition += 40;
		}

		repaint();
	}
}
