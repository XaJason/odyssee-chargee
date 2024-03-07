package test;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ApplicationTest extends JFrame {

	private JPanel contentPane;
	private JButton btnDemarrer;
	private JButton btnArreter;
	private JButton btnProchaineImage;
	private JButton btnRedemarrer;
	private JButton btnReinitialiser;
	private ZoneAnimationPhysiqueTest zoneAnimationPhysiqueTest;

	/**
	 * Lance l'application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationTest frame = new ApplicationTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crée la fenêtre de l'application test 
	 */
	public ApplicationTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1127, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		zoneAnimationPhysiqueTest = new ZoneAnimationPhysiqueTest();
		zoneAnimationPhysiqueTest.setBounds(29, 31, 1046, 314);
		contentPane.add(zoneAnimationPhysiqueTest);
		
		btnDemarrer = new JButton("Démarrer");
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//debut
				zoneAnimationPhysiqueTest.demarrer();
				btnProchaineImage.setEnabled(false);
				btnDemarrer.setEnabled(false);
				//fin
			}
		});
		btnDemarrer.setBounds(87, 429, 89, 23);
		contentPane.add(btnDemarrer);
		
		btnArreter = new JButton("Arrêter");
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneAnimationPhysiqueTest.arreter();
				btnProchaineImage.setEnabled(true);
				btnDemarrer.setEnabled(true);
			}
		});
		btnArreter.setBounds(263, 429, 89, 23);
		contentPane.add(btnArreter);
		
		btnRedemarrer = new JButton("Redémarrer");
		btnRedemarrer.setBounds(680, 429, 129, 23);
		contentPane.add(btnRedemarrer);
		
		btnProchaineImage = new JButton("Prochaine image");
		btnProchaineImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneAnimationPhysiqueTest.prochaineImage();
			}
		});
		btnProchaineImage.setBounds(439, 429, 154, 23);
		contentPane.add(btnProchaineImage);
		
		btnReinitialiser = new JButton("Réinitialiser");
		btnReinitialiser.setBounds(896, 429, 129, 23);
		contentPane.add(btnReinitialiser);
	}
}
