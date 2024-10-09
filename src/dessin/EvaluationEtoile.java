package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe qui répresente l'évaluation en étoiles de l'application
 *
 * @author Kitimir Yim
 */
public class EvaluationEtoile extends JPanel {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Espace entre les étoiles
	 */
	private int espacementEtoile = 30;
	/**
	 * Statut de l'évaluation
	 */
	private boolean evaluationFini = false;
	/**
	 * Compteur de clics
	 */
	private int nombreClic = 0;
	/**
	 * Nombre d'étoiles
	 */
	private double nombreEtoiles = 5;
	/**
	 * Note de l'application
	 */
	private double note;
	/**
	 * Rayon extérieur de l'étoile
	 */
	private int rayonExterieur = 10;
	/**
	 * Rayon intérieur de l'étoile
	 */
	private int rayonInterieur = 5;
	/**
	 * Position de l'étoile en x
	 */
	private double x;
	/**
	 * Postion de l'étoile en y
	 */
	private double y = 0;

	/**
	 * Implémente l'évaluation de l'application
	 *
	 * @param note note de l'application
	 */
	// Kitimir Yim
	public EvaluationEtoile(double note) {

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				nombreClic++;
				if (nombreClic == 1) {
					evaluationFini = true;

				} else if (nombreClic == 2) {
					evaluationFini = false;
					nombreClic = 0;
				}

			}
		});
		this.note = note;
		setLayout(null);

		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 5, 0.5);

		JSpinner spinner = new JSpinner(spinnerModel);

		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				double nouvelleNote = (double) spinner.getValue();
				setNote(nouvelleNote);

			}
		});
		spinner.setBounds(56, 25, 53, 21);
		add(spinner);

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (evaluationFini) {
					double x = e.getX();
					double largeur = getWidth();

					double nouvelleNote = (x / largeur) * nombreEtoiles;
					setNote(nouvelleNote);

					if (nouvelleNote <= 0.5) {
						spinner.setValue(0.0);
					} else if (nouvelleNote <= 1.5) {
						spinner.setValue(1.0);
					} else if (nouvelleNote <= 2.5) {
						spinner.setValue(2.0);
					} else if (nouvelleNote <= 3.5) {
						spinner.setValue(3.0);
					} else if (nouvelleNote <= 4.5) {
						spinner.setValue(4.0);
					} else if (nouvelleNote <= 5.5) {
						spinner.setValue(5.0);
					}

				}
			}
		});

	}

	/**
	 * Méthode appelée pour dessiner le composant.
	 *
	 * @param g L'objet Graphics utilisé pour dessiner.
	 */
	// Kitimir Yim
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		dessinerEtoiles(g2d);

	}

	/**
	 * Permet la modification de la note
	 *
	 * @param note nouvelle note de l'application
	 */
	// Kitimir Yim
	public void setNote(double note) {

		this.note = note;
		repaint();
	}

	/**
	 * Dessine les étoiles
	 *
	 * @param g2d dessin
	 */
	// Kitimir Yim
	private void dessinerEtoiles(Graphics2D g2d) {
		for (int i = 0; i < nombreEtoiles; i++) {
			x = i * espacementEtoile + 10;

			Etoile etoile = new Etoile(x, y, rayonExterieur, rayonInterieur);

			g2d.setColor(Color.GRAY);
			etoile.dessiner(g2d);

			g2d.setColor(Color.YELLOW);

			if (i + 0.5 <= note) {
				if (i + 0.5 == note) {
					etoile.setDemiEtoile(true);
				}
				etoile.dessiner(g2d);
			}
		}
	}
}
