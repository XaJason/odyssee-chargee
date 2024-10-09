package tuile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import interactif.Vaisseau;
import physique.Vecteur2D;
import utilitaires.OutilsImage;

/**
 * Représente l'objet interactif fixe plaçable qui agit comme un portail
 *
 * @author Jason Xa
 * @author Giroux
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 */
public class Portail extends Tuile implements Serializable {
	/**
	 * Numéro d'identification pour la sérialisation
	 */
	private static final long serialVersionUID = -3752989336876220183L;
	/** l'image représentant un portail */
	private static transient Image image;
	/** chaine de caractères représentant la tuile de type portail */
	private static String type = "Portail";
	/** Position du centre du portail **/
	private Vecteur2D position;

	/** position du x pour délimiter les points **/
	private double xActuel;
	/** position du y pour délimiter les points **/
	private double yActuel;
	/** Coin haut-droit **/
	private Double coinHautDroit;
	/** Coin bas-droit **/
	private Double coinBasDroit;
	/** Coin bas-gauche **/
	private Double coinBasGauche;
	/** Représente la forme du portail **/
	private Ellipse2D cercle;
	/** Représente l'aire du portail **/
	private transient Area airePortail;
	/** Représente l'aire d'une case **/
	private transient Area aireCase;
	/** Couleur du portail **/
	private Color couleur;

	/** Association du portail **/
	private Portail portailAssocie = null;
	/** Durée de refroidissement initial des portails (en milliseconde) **/
	private static final double COOLDOWN_PORTAIL = 2000;
	/** Durée de refroidissement des portails (en milliseconde) **/
	private static double cooldownPortail = COOLDOWN_PORTAIL;
	/** Temps du dernier usage du portail (en milliseconde) **/
	private double tempsDernierUsage = 0;

	/**
	 * Constructeur
	 *
	 */
	// Jason Xa
	public Portail() {
		super(image, type);
		creerLaGeometrie();
	}

	/**
	 * Constructeur
	 *
	 * @param angleRotation l'angle de rotation de la tuile (rad)
	 */
	// Jason Xa
	public Portail(double angleRotation) {
		super(angleRotation, image, type);
		creerLaGeometrie();
	}

	/**
	 * Constructeur
	 *
	 * @param x l'abscisse gauche de la tuile (px)
	 * @param y l'ordonnée supérieure la tuile (px)
	 *
	 */
	// Jason Xa
	public Portail(int x, int y) {
		super(image, x, y, type);
		creerLaGeometrie();
	}

	/**
	 * Permet de créer la géométrie d'un portail.
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
	public void creerLaGeometrie() {
		cercle = new Ellipse2D.Double(x, y, largeurTuile, hauteurTuile);
		position = new Vecteur2D(x + largeurTuile / 2, y + hauteurTuile / 2);
	}

	/**
	 * Dessine l'image représentant la tuile selon ses coordonnées
	 *
	 * @param g2d Le contexte graphique
	 */
	// Enuel René Valentin Kizozo Izia
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setColor(couleur);
		g2dPrive.fill(cercle);

		g2dPrive.rotate(angleRotation, x + largeurTuile / 2.0, y + hauteurTuile / 2.0);
		/*
		 * Ajustement des paramètres pour dessiner l'image à cause des transformations
		 * du paintComponent de Grille permettant de mettre l'origine en bas à gauche
		 */
		double yImage = y + hauteurTuile;
		double hauteurTuileImage = -hauteurTuile;
		g2dPrive.drawImage(image, (int) x, (int) yImage, (int) largeurTuile, (int) hauteurTuileImage, null);
	}

	/**
	 * Téléportation le vaisseau du portail courant au portail associé
	 *
	 * @param vaisseau Le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public void teleportation(Vaisseau vaisseau) {
		Vecteur2D coinSupGauchePortailIni = new Vecteur2D(this.x, this.y);
		Vecteur2D posVaisseauDansPortail = vaisseau.getPosition().soustrait(coinSupGauchePortailIni);

		double tempsActuel = System.currentTimeMillis();

		if (tempsActuel - tempsDernierUsage >= cooldownPortail) {
			tempsDernierUsage = tempsActuel;
			portailAssocie.setTempsDernierUsage(tempsActuel);

			ajusterPositionVaisseau(vaisseau, posVaisseauDansPortail);
		} else {

		}
	}

	/**
	 * Vérifie si le vaisseau est complètement à l'intérieur du portail lors de la
	 * téléportation Si ce n'est pas le cas, modifie la position du vaisseau dans le
	 * portailpour éviter des bugs
	 *
	 * @param vaisseau               Le vaisseau
	 * @param posVaisseauDansPortail La position du vaisseau dans le référentiel
	 *                               d'un portail
	 */
	// Enuel René Valentin Kizozo Izia
	private void ajusterPositionVaisseau(Vaisseau vaisseau, Vecteur2D posVaisseauDansPortail) {
		Vecteur2D coinSupGauchePortailFinal = new Vecteur2D(portailAssocie.x, portailAssocie.y);
		Vecteur2D nouvellePosVaisseau = coinSupGauchePortailFinal.additionne(posVaisseauDansPortail);

		Area aireVaisseau = vaisseau.formerAireDuVaisseau();
		aireVaisseau.subtract(aireCase);
		boolean completementDansPortail = aireVaisseau.isEmpty();

		// Ajuste la position du vaisseau s'il n'est pas complètement dans le portail
		if (!completementDansPortail) {
			try {
				Vecteur2D orientationDistanceVaisseauPortail = this.position.soustrait(vaisseau.getPosition())
						.normalise();
				nouvellePosVaisseau = nouvellePosVaisseau
						.additionne(orientationDistanceVaisseauPortail.multiplie(2 * vaisseau.getRayon()));
				vaisseau.setPosition(nouvellePosVaisseau);
			} catch (Exception e) {
				nouvellePosVaisseau = vaisseau.getPosition();
				System.out.println(
						"Le vaisseau est sur le centre du portail, donc on ne truque pas son repositionnment.");
			}
		} else {
			vaisseau.setPosition(nouvellePosVaisseau);
		}
	}

	/**
	 * Méthode qui forme le Portail dans un area
	 *
	 * @return la forme du Portail dans un area
	 */
	// Kitimir Yim
	@Override
	public Area formerAireObjetSpecial() {
		aireCase = new Area(new Rectangle2D.Double(x, y, largeurTuile, hauteurTuile));
		airePortail = new Area(cercle);
		return airePortail;
	}

	/**
	 * Méthode qui affiche le type lorsqu'on le print
	 *
	 * @return Une chaine indiquant que l'objet est un portail
	 */
	// Giroux
	@Override
	public String toString() {
		return "Portail ";
	}

	/**
	 * Méthode qui ajoute les coins du carré dans l'arrayList points
	 */
	// Giroux
	@Override
	public void setPoint() {
		super.setPoint();
		prePointsCoin.clear();
		pointsCoin.clear();
		xActuel = 0;
		yActuel = 0;
		prePointsCoin.add(pointInitial);
		// Deuxième point(HautDroit)
		xActuel = pointInitial.getX() + largeurTuile;
		yActuel = pointInitial.getY();
		coinHautDroit = new Point2D.Double(xActuel, yActuel);
		// Troisième point(BasDroit)
		yActuel += hauteurTuile;
		coinBasDroit = new Point2D.Double(xActuel, yActuel);
		// Quatrième point(BasGauche)
		xActuel -= largeurTuile;
		coinBasGauche = new Point2D.Double(xActuel, yActuel);
		// Ajouter dans l'arrayList
		prePointsCoin.add(coinHautDroit);
		prePointsCoin.add(coinBasDroit);
		prePointsCoin.add(coinBasGauche);
		// Transformer
		for (Point2D.Double i : prePointsCoin) {
			// Prendre le centre
			i.setLocation(i.getX() - largeurTuile / 2, i.getY() - hauteurTuile / 2);
			i = rotation.rotationner(i);
			// Repositionner
			i.setLocation(i.getX() + largeurTuile / 2 + x, i.getY() + hauteurTuile / 2 + y);
			pointsCoin.add(i);
		}
	}

	/**
	 * Retourne l'image représentant le portail
	 *
	 * @return L'image représentant le portail
	 */
	// Enuel René Valentin Kizozo Izia
	public static Image getImageRef() {
		return image;
	}

	/**
	 * Définit l'image représentant le portail
	 *
	 * @param fichierImage Le fichier de l'image représentant le portail
	 * @param largeurTuile La largeur de la tuile
	 * @param hauteurTuile La hauteur de la tuile
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setImageRef(String fichierImage, int largeurTuile, int hauteurTuile) {
		image = OutilsImage.lireImageEtRedimensionner(fichierImage, largeurTuile, hauteurTuile);
	}

	/**
	 * Retourne le portail associé
	 *
	 * @return portailAssocie portail relié à un autre
	 */
	// Kitimir Yim
	public Portail getPortailAssocie() {
		return portailAssocie;
	}

	/**
	 * Modifie le portail associé
	 *
	 * @param portailAssocie Le nouveau portail associé
	 */
	// Kitimir Yim
	public void setPortailAssocie(Portail portailAssocie) {
		this.portailAssocie = portailAssocie;
	}

	/**
	 * Retourne la couleur du portail
	 *
	 * @return La couleur du portail
	 */
	// Enuel René Valentin Kizozo Izia
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Modifie la couleur du portail
	 *
	 * @param couleur La nouvelle couleur du portail
	 */
	// Enuel René Valentin Kizozo Izia
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * Retourne le temps du dernier usage du portail
	 *
	 * @return Le temps du dernier usage du portail
	 */
	// Enuel René Valentin Kizozo Izia
	public double getTempsDernierUsage() {
		return tempsDernierUsage;
	}

	/**
	 * Modifie le temps du dernier usage du portail
	 *
	 * @param tempsDernierUsage Le nouveau temps du dernier usage du portail
	 */
	// Enuel René Valentin Kizozo Izia
	public void setTempsDernierUsage(double tempsDernierUsage) {
		this.tempsDernierUsage = tempsDernierUsage;
	}

	/**
	 * Retourne la durée de refroidissement des portails
	 *
	 * @return La durée de refroidissement des portails
	 */
	// Enuel René Valentin Kizozo Izia
	public static double getCooldownPortail() {
		return cooldownPortail;
	}

	/**
	 * Modifie la durée de refroidissement des portails
	 *
	 * @param cooldownPortail La nouvelle durée de refroidissement des portails
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setCooldownPortail(double cooldownPortail) {
		Portail.cooldownPortail = cooldownPortail;
	}
}
