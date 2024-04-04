package panneaux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import niveau.GestionnaireDeNiveau;
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
		btnPasserModeJeu.setBounds(192, 119, 162, 23);
		add(btnPasserModeJeu);

	}

	/**
	 * Actualise les boutons représentant les niveaux disponibles
	 */
	// Kitimir Yim
	public void actualiserNiveaux() {
		removeAll(); // Supprimer tous les composants existants

		ArrayList<Niveau> niveaux = GestionnaireDeNiveau.getRepertoireNiveau();

		int yPosition = 50;
		for (Niveau niveau : niveaux) {
			JButton btnNiveau = new JButton(niveau.getNomNiveau());
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
