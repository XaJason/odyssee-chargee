package physique;

import java.awt.geom.Point2D;

import interactif.PlaqueChargee;
import interactif.Vaisseau;
import tuile.Tuile;

/**
 * Cette classe regroupe les calculs physiques nécessaires au mouvement des
 * divers objets dans la scène. Utilise entre autres la méthode d'intégration
 * numérique d'Euler semi-implicite.
 * <p>
 * Les méthodes de collision sont déléguées à {@link CollisionPhysique}.
 * Les méthodes de force sont déléguées à {@link ForcePhysique}.
 *
 * @author Caroline Houle
 * @author Enuel René Valentin Kizozo Izia
 * @author Kitimir Yim
 */
public class MoteurPhysique {

	/** Accélération gravitationnelle initiale (en m/s^2) **/
	private static final double ACCEL_GRAV_INITIALE = -10;

	/** Accélération gravitationnelle (en m/s^2) **/
	private static double accelGrav = ACCEL_GRAV_INITIALE;

	/** Coefficient de frottement cinétique initial **/
	private static final double COEFF_FROT_CINE_INITIAL = 0.55;

	/** Coefficient de frottement statique initial **/
	private static final double COEFF_FROT_STAT_INITIAL = 0.75;

	/** Coefficient de frottement cinétique (acier-acier par défaut) **/
	private static double coeffFrotCine = COEFF_FROT_CINE_INITIAL;

	/** Coefficient de frottement statique (acier-acier par défaut) **/
	private static double coeffFrotStat = COEFF_FROT_STAT_INITIAL;

	/** Tolérance utilisée dans les comparaisons réelles avec zéro **/
	private static final double EPSILON = 1e-10;

	// ========================= Cinématique =========================

	/**
	 * Calcule et retourne l'accélération en utilisant F = ma
	 *
	 * @param sommeDesForces Somme des forces appliquées
	 * @param masse          Masse de l'objet
	 * @return L'accélération F/m
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	// Caroline Houle
	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception {
		if (masse < EPSILON) {
			throw new Exception(
					"Erreur MoteurPhysique: La masse étant nulle ou presque, l'accéleration ne peut pas être calculée.");
		} else {
			return new Vecteur2D(sommeDesForces.getX() / masse, sommeDesForces.getY() / masse);
		}
	}

	/**
	 * Calcule et retourne la nouvelle position d'un objet selon la méthode d'Euler
	 * semi-implicite
	 *
	 * @param deltaT   Pas de la simulation (en secondes)
	 * @param position La position de l'objet
	 * @param vitesse  La vitesse de l'objet (après l'application de Euler
	 *                 semi-implicite et après la détection de collision)
	 * @return La nouvelle position
	 */
	// Caroline Houle
	public static Vecteur2D calculPosition(double deltaT, Vecteur2D position, Vecteur2D vitesse) {
		return new Vecteur2D(position.getX() + vitesse.getX() * deltaT,
				position.getY() + vitesse.getY() * deltaT);
	}

	/**
	 * Calcule et retourne la nouvelle vitesse d'un objet en utilisant F = ma et la
	 * méthode d'Euler semi-implicite
	 *
	 * @param deltaT         Pas de la simulation (en secondes)
	 * @param sommeDesForces Somme des forces appliquées
	 * @param masse          Masse de l'objet
	 * @param vitesse        La vitesse de l'objet
	 * @return La nouvelle vitesse
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	// Caroline Houle
	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D sommeDesForces, double masse,
			Vecteur2D vitesse) throws Exception {
		Vecteur2D accel = calculAcceleration(sommeDesForces, masse);
		return new Vecteur2D(vitesse.getX() + accel.getX() * deltaT,
				vitesse.getY() + accel.getY() * deltaT);
	}

	/**
	 * Calcule et retourne la nouvelle vitesse d'un objet en appliquant la méthode
	 * d'Euler semi-implicite à partir de l'accélération déjà calculée
	 *
	 * @param deltaT Pas de la simulation (en secondes)
	 * @param vitesse La vitesse actuelle de l'objet
	 * @param accel   L'accélération de l'objet
	 * @return La nouvelle vitesse
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D vitesse, Vecteur2D accel) {
		return new Vecteur2D(vitesse.getX() + accel.getX() * deltaT,
				vitesse.getY() + accel.getY() * deltaT);
	}

	// ========================= Délégation : Forces =========================

	/**
	 * Applique une force constante, vers le bas, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersBas(double masse) {
		return ForcePhysique.appliqueForceVersBas(masse);
	}

	/**
	 * Applique une force constante, vers la droite, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersDroite(double masse) {
		return ForcePhysique.appliqueForceVersDroite(masse);
	}

	/**
	 * Applique une force constante, vers la gauche, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersGauche(double masse) {
		return ForcePhysique.appliqueForceVersGauche(masse);
	}

	/**
	 * Applique une force constante, vers le haut, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersHaut(double masse) {
		return ForcePhysique.appliqueForceVersHaut(masse);
	}

	/**
	 * Calcule le force électrique d'une plaque sur un vaisseau
	 *
	 * @param vaisseau Objet représentant un vaisseau
	 * @param plaque   Objet représentant une plaque chargée
	 * @return La force électrique générée par la plaque
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceElectriqueGenereeParPlaque(Vaisseau vaisseau, PlaqueChargee plaque) {
		return ForcePhysique.calculForceElectriqueGenereeParPlaque(vaisseau, plaque);
	}

	/**
	 * Calcule et retourne un vecteur exprimant la force de frottement
	 *
	 * @param vaisseau               Objet représentant un vaisseau
	 * @param sommeForcesSurVaisseau Somme des forces parallèles
	 * @return La force de frottement
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceFrottement(Vaisseau vaisseau, Vecteur2D sommeForcesSurVaisseau) {
		return ForcePhysique.calculForceFrottement(vaisseau, sommeForcesSurVaisseau);
	}

	/**
	 * Calcule la composante en X de la force gravitationnelle
	 *
	 * @param forceGrav Vecteur de la force gravitationnelle
	 * @param angleDeg  Angle de la surface avec l'horizontale
	 * @return La force gravitationnelle selon l'axe x
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceGravEnX(Vecteur2D forceGrav, double angleDeg) {
		return ForcePhysique.calculForceGravEnX(forceGrav, angleDeg);
	}

	/**
	 * Calcule la force gravitationnelle
	 *
	 * @param masse Masse de l'objet
	 * @return La force gravitationnelle
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceGravEnY(double masse) {
		return ForcePhysique.calculForceGravEnY(masse);
	}

	/**
	 * Calcule la force normale s'appliquant sur le vaisseau
	 *
	 * @param vaisseau Objet représentant un vaisseau
	 * @param segment  Objet représentant un segment
	 * @return La force normale
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceNormale(Vaisseau vaisseau, Segment segment) {
		return ForcePhysique.calculForceNormale(vaisseau, segment);
	}

	// ========================= Délégation : Collisions =========================

	/**
	 * Détecte une collision entre le vaisseau et un coin
	 *
	 * @param vaisseau Objet représentant le vaisseau
	 * @param coin     Le coin d'un bloc
	 * @return La nouvelle vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionAvecCoinEtCalculeVitesse(Vaisseau vaisseau, Point2D.Double coin) {
		return CollisionPhysique.detectionCollisionAvecCoinEtCalculeVitesse(vaisseau, coin);
	}

	/**
	 * Détecte une collision entre le vaisseau et une plaque
	 *
	 * @param vaisseau Objet représentant le vaisseau
	 * @param plaque   Objet représentant une plaque chargée
	 * @return La nouvelle vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionsAvecPlaqueEtCalculeVitesse(Vaisseau vaisseau, PlaqueChargee plaque) {
		return CollisionPhysique.detectionCollisionsAvecPlaqueEtCalculeVitesse(vaisseau, plaque);
	}

	/**
	 * Détecte une collision entre le vaisseau et un segment
	 *
	 * @param vaisseau Objet représentant le vaisseau
	 * @param segment  Objet représentant un segment
	 * @return La nouvelle vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionsAvecSegmentEtCalculeVitesse(Vaisseau vaisseau, Segment segment) {
		return CollisionPhysique.detectionCollisionsAvecSegmentEtCalculeVitesse(vaisseau, segment);
	}

	/**
	 * Détecte une collision avec les bordures de la zone d'animation
	 *
	 * @param vaisseau         L'objet représentant un vaisseau
	 * @param largeurComposant La largeur de la zone d'animation
	 * @param hauteurComposant La hauteur de la zone d'animation
	 * @return La nouvelle vitesse du vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionsBorduresEtCalculVitesse(Vaisseau vaisseau, double largeurComposant,
			double hauteurComposant) {
		return CollisionPhysique.detectionCollisionsBorduresEtCalculVitesse(vaisseau, largeurComposant,
				hauteurComposant);
	}

	/**
	 * Vérifie si le vaisseau entre en collision avec un objet spécial
	 *
	 * @param vaisseau     Un objet représentant le vaisseau
	 * @param objetSpecial Une tuile représentant un objet spécial
	 * @return vrai si le vaisseau est en collision
	 */
	// Kitimir Yim
	public static boolean detecteCollisionsAvecObjetsSpeciaux(Vaisseau vaisseau, Tuile objetSpecial) {
		return ForcePhysique.detecteCollisionsAvecObjetsSpeciaux(vaisseau, objetSpecial);
	}

	// ========================= Accesseurs et mutateurs =========================

	/**
	 * Retourne l'accélération gravitationnelle
	 *
	 * @return L'accélération gravitationnelle
	 */
	// Enuel René Valentin Kizozo Izia
	public static double getAccelGrav() {
		return accelGrav;
	}

	/**
	 * Retourne l'accélération gravitationnelle initiale
	 *
	 * @return L'accélération gravitationnelle initiale
	 */
	// Enuel René Valentin Kizozo Izia
	public static double getAccelGravInitiale() {
		return ACCEL_GRAV_INITIALE;
	}

	/**
	 * Retourne le coefficient de frottement cinétique
	 *
	 * @return Le coefficient de frottement cinétique
	 */
	// Enuel René Valentin Kizozo Izia
	public static double getCoeffFrotCine() {
		return coeffFrotCine;
	}

	/**
	 * Retourne le coefficient de frottement cinétique initial
	 *
	 * @return Le coefficient de frottement cinétique initial
	 */
	// Enuel René Valentin Kizozo Izia
	public static double getCoeffFrotCineInitial() {
		return COEFF_FROT_CINE_INITIAL;
	}

	/**
	 * Retourne le coefficient de frottement statique
	 *
	 * @return Le coefficient de frottement statique
	 */
	// Enuel René Valentin Kizozo Izia
	public static double getCoeffFrotStat() {
		return coeffFrotStat;
	}

	/**
	 * Retourne le coefficient de frottement statique initial
	 *
	 * @return Le coefficient de frottement statique initial
	 */
	// Enuel René Valentin Kizozo Izia
	public static double getCoeffFrotStatInitial() {
		return COEFF_FROT_STAT_INITIAL;
	}

	/**
	 * Réinitialise les coefficients de frottement et l'accélération
	 * gravitationnelle
	 */
	// Enuel René Valentin Kizozo Izia
	public static void reinitialiser() {
		accelGrav = ACCEL_GRAV_INITIALE;
		coeffFrotStat = COEFF_FROT_STAT_INITIAL;
		coeffFrotCine = COEFF_FROT_CINE_INITIAL;
	}

	/**
	 * Modifie l'accélération gravitationnelle
	 *
	 * @param accelGrav La nouvelle accélération gravitationnelle
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setAccelGrav(double accelGrav) {
		MoteurPhysique.accelGrav = accelGrav;
	}

	/**
	 * Modifie le coefficient de frottement cinétique
	 *
	 * @param coeffFrotCine Le nouveau coefficient de frottement cinétique
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setCoeffFrotCine(double coeffFrotCine) {
		MoteurPhysique.coeffFrotCine = coeffFrotCine;
	}

	/**
	 * Modifie le coefficient de frottement statique
	 *
	 * @param coeffFrotStat Le nouveau coefficient de frottement statique
	 */
	// Enuel René Valentin Kizozo Izia
	public static void setCoeffFrotStat(double coeffFrotStat) {
		MoteurPhysique.coeffFrotStat = coeffFrotStat;
	}
}
