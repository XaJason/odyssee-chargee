package panneaux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JPanel;

import application.FondEcran;
import utilitaires.ConstanteComposantsSwing;
import utilitaires.OutilsImage;

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

	/** Bouton permettant d'accéder au niveau de base 1 **/
	private JButton btnNiveauBase1;
	/** Bouton permettant d'accéder au niveau de base 2 **/
	private JButton btnNiveauBase2;
	/** Bouton permettant d'accéder au niveau de base 3 **/
	private JButton btnNiveauBase3;
	/** Bouton permettant d'accéder au niveau de base 4 **/
	private JButton btnNiveauBase4;
	/** Bouton permettant d'accéder au niveau de base 5 **/
	private JButton btnNiveauBase5;
	/** Bouton permettant d'accéder au niveau de base 6 **/
	private JButton btnNiveauBase6;
	/** Le fond d'écran du panneau **/
	FondEcran fondEcran;

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
		setBounds(0, 25, ConstanteComposantsSwing.DIM_HORIZONTALE_APP, ConstanteComposantsSwing.DIM_VERTICALE_APP);
		fondEcran = new FondEcran("fondNiveau.png", 1);
		fondEcran.setBounds(0, 0, 1920, 1080);
		add(fondEcran);
		fondEcran.setLayout(null);

		JButton btnPasserModeJeu = new JButton("Sélectionnez vos niveaux");
		btnPasserModeJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersJeu", 0, -1);
			}
		});
		btnPasserModeJeu.setBounds(858, 430, 200, 30);
		fondEcran.add(btnPasserModeJeu);

		btnNiveauBase1 = new JButton("Niveau de base 1");
		btnNiveauBase1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau1", 0, -1);
			}
		});
		btnNiveauBase1.setBounds(313, 385, 224, 44);
		OutilsImage.lireImageEtPlacerSurBouton("niveaubase1.png", btnNiveauBase1);
		fondEcran.add(btnNiveauBase1);

		btnNiveauBase2 = new JButton("Niveau de base 2");
		btnNiveauBase2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau2", 0, -1);
			}
		});
		btnNiveauBase2.setBounds(313, 425, 224, 44);
		OutilsImage.lireImageEtPlacerSurBouton("niveaubase2.png", btnNiveauBase2);
		fondEcran.add(btnNiveauBase2);

		btnNiveauBase3 = new JButton("Niveau de base 3");
		btnNiveauBase3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau3", 0, -1);
			}
		});
		btnNiveauBase3.setBounds(313, 465, 224, 44);
		OutilsImage.lireImageEtPlacerSurBouton("niveaubase3.png", btnNiveauBase3);
		fondEcran.add(btnNiveauBase3);

		creerTroisBoutonsNiveaux();
	}

	/**
	 * Créer trois autres boutons permettant d'accéder aux niveaux de base 4, 5 et 6
	 */
	// Enuel René Valentin Kizozo Izia
	private void creerTroisBoutonsNiveaux() {
		btnNiveauBase4 = new JButton("Niveau de base 4");
		btnNiveauBase4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau4", 0, -1);
			}
		});
		btnNiveauBase4.setBounds(1365, 385, 224, 44);
		fondEcran.add(btnNiveauBase4);

		btnNiveauBase5 = new JButton("Niveau de base 5");
		btnNiveauBase5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau5", 0, -1);
			}
		});
		btnNiveauBase5.setBounds(1365, 425, 224, 44);
		fondEcran.add(btnNiveauBase5);

		btnNiveauBase6 = new JButton("Niveau de base 6");
		btnNiveauBase6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCS.firePropertyChange("passerVersNiveau6", 0, -1);
			}
		});
		btnNiveauBase6.setBounds(1365, 465, 224, 44);
		fondEcran.add(btnNiveauBase6);
	}
}
