package physique;

import java.awt.geom.Area;

import interactif.PlaqueChargee;
import interactif.Vaisseau;
import tuile.Tuile;

/**
 * Regroupe les méthodes de calcul des forces et des champs électriques pour
 * la simulation physique.
 *
 * @author Enuel René Valentin Kizozo Izia
 * @author Caroline Houle
 */
public class ForcePhysique {

	/** Accélération du réacteur dorsal (en m/s^2) **/
	private static final double ACCEL_JETPACK = 100;

	/** Tolérance utilisée dans les comparaisons réelles avec zéro **/
	private static final double EPSILON = 1e-10;

	/** Constante de Coulomb **/
	private static final double K = 8.98755;

	/** Vecteur nul **/
	private static final Vecteur2D VEC_ZERO = new Vecteur2D();

	/**
	 * Applique une force constante, vers le bas, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersBas(double masse) {
		return new Vecteur2D(0, -ACCEL_JETPACK * masse);
	}

	/**
	 * Applique une force constante, vers la droite, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersDroite(double masse) {
		return new Vecteur2D(ACCEL_JETPACK * masse, 0);
	}

	/**
	 * Applique une force constante, vers la gauche, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersGauche(double masse) {
		return new Vecteur2D(-ACCEL_JETPACK * masse, 0);
	}

	/**
	 * Applique une force constante, vers le haut, sur un vaisseau
	 *
	 * @param masse La masse du vaisseau
	 * @return La force appliquée sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D appliqueForceVersHaut(double masse) {
		return new Vecteur2D(0, ACCEL_JETPACK * masse);
	}

	/**
	 * Retourne le champ électrique généré par une plaque hors de son axe
	 *
	 * @param vaisseau Objet représentant un vaisseau
	 * @param plaque   Objet représentant une plaque chargée
	 * @return Le champ électrique hors axe
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculChampElectriqueHorsAxe(Vaisseau vaisseau, PlaqueChargee plaque) {
		try {
			double densiteLineiqueCharge = plaque.getCharge() / plaque.getLongueur();
			Vecteur2D distanceVaisseauPlaque = plaque.getPosition().soustrait(vaisseau.getPosition());
			Vecteur2D distanceVaisseauExtremiteA = plaque.getExtremiteA().soustrait(vaisseau.getPosition());
			Vecteur2D distanceVaisseauExtremiteB = plaque.getExtremiteB().soustrait(vaisseau.getPosition());

			double plusPetiteDistanceVaisseauPlaque = Math
					.abs(distanceVaisseauPlaque.prodScalaire(plaque.getNormale()));

			double alphaA = Math.acos(plusPetiteDistanceVaisseauPlaque / distanceVaisseauExtremiteA.module());
			double alphaB = Math.acos(plusPetiteDistanceVaisseauPlaque / distanceVaisseauExtremiteB.module());

			double projDistanceVExtrmASurAxePlaque = Math.abs(distanceVaisseauExtremiteA.prodScalaire(plaque.getAxe()));
			double projDistanceVExtrmBSurAxePlaque = Math.abs(distanceVaisseauExtremiteB.prodScalaire(plaque.getAxe()));
			boolean vaisseauEntreExtremite = !(projDistanceVExtrmASurAxePlaque
					+ projDistanceVExtrmBSurAxePlaque > plaque.getLongueur());
			if (vaisseauEntreExtremite) {
				alphaB = -1 * alphaB;
			}

			double moduleChamp = Math.sqrt(2) * K * Math.abs(densiteLineiqueCharge) / plusPetiteDistanceVaisseauPlaque
					* Math.sqrt(1 - Math.cos(alphaA - alphaB));

			Vecteur2D orientationChamp = distanceVaisseauPlaque.normalise();

			if (Math.signum(plaque.getCharge()) > 0) {
				orientationChamp = orientationChamp.multiplie(-1);
			}

			return orientationChamp.multiplie(moduleChamp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(
					"Le vaisseau est trop près de la plaque, le champ électrique agissant sur la plaque est donc nul.");
			return VEC_ZERO;
		}
	}

	/**
	 * Retourne le champ électrique généré par une plaque le long de son axe
	 *
	 * @param vaisseau Objet représentant un vaisseau
	 * @param plaque   Objet représentant une plaque chargée
	 * @return Le champ électrique sur l'axe
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculChampElectriqueSurAxe(Vaisseau vaisseau, PlaqueChargee plaque) {
		try {
			double distanceVaisseauPlaque = Math.abs(vaisseau.getPosition().soustrait(plaque.getPosition()).module());
			double moduleChamp = K * Math.abs(plaque.getCharge())
					/ (distanceVaisseauPlaque * (distanceVaisseauPlaque + plaque.getLongueur()));

			Vecteur2D orientationChamp;
			orientationChamp = plaque.getPosition().soustrait(vaisseau.getPosition()).normalise();

			if (Math.signum(plaque.getCharge()) > 0) {
				orientationChamp = orientationChamp.multiplie(-1);
			}

			return orientationChamp.multiplie(moduleChamp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(
					"Le vaisseau est trop près de la plaque, le champ électrique agissant sur la plaque est donc nul.");
			return VEC_ZERO;
		}
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
		double distanceVaisseauPlaque = Math.abs(vaisseau.getPosition().soustrait(plaque.getPosition()).module());
		double projectionDistanceVPSurAxePlaque = Math
				.abs(vaisseau.getPosition().soustrait(plaque.getPosition()).prodScalaire(plaque.getAxe()));
		Vecteur2D forceElec;

		if (distanceVaisseauPlaque <= projectionDistanceVPSurAxePlaque + EPSILON) {
			forceElec = calculChampElectriqueSurAxe(vaisseau, plaque).multiplie(vaisseau.getCharge());
		} else {
			forceElec = calculChampElectriqueHorsAxe(vaisseau, plaque).multiplie(vaisseau.getCharge());
		}

		return forceElec;
	}

	/**
	 * Calcule et retourne un vecteur exprimant la force de frottement s'appliquant
	 * sur le vaisseau
	 *
	 * @param vaisseau               Objet représentant un vaisseau
	 * @param sommeForcesSurVaisseau Somme de toutes les forces parallèles au
	 *                               frottement statique agissant sur le vaisseau
	 * @return Un vecteur représentant la force de frottement statique exercée sur
	 *         le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceFrottement(Vaisseau vaisseau, Vecteur2D sommeForcesSurVaisseau) {
		double coeffFrotStat = MoteurPhysique.getCoeffFrotStat();

		Vecteur2D orientationMouvementVaisseau = new Vecteur2D(-1 * vaisseau.getForceNormale().getY(),
				vaisseau.getForceNormale().getX());
		if (vaisseau.getVitesse().prodScalaire(orientationMouvementVaisseau) < 0) {
			orientationMouvementVaisseau = orientationMouvementVaisseau.multiplie(-1);
		}
		double sommeAutresForcesParalleles = Math.abs(sommeForcesSurVaisseau.prodScalaire(orientationMouvementVaisseau)
				/ orientationMouvementVaisseau.module());

		double moduleFrotStatiqueMax = vaisseau.getForceNormale().multiplie(coeffFrotStat).module();
		Vecteur2D forceFrot = VEC_ZERO;

		if (vaisseau.getVitesse().module() < EPSILON) {
			forceFrot = VEC_ZERO;
		} else if (sommeAutresForcesParalleles > moduleFrotStatiqueMax) {
			forceFrot = orientationMouvementVaisseau.multiplie(-1 * calculModuleForceFrotCine(vaisseau));
		} else if (sommeAutresForcesParalleles < moduleFrotStatiqueMax - EPSILON) {
			forceFrot = orientationMouvementVaisseau
					.multiplie(-1 * sommeAutresForcesParalleles / orientationMouvementVaisseau.module());
		} else if (sommeAutresForcesParalleles >= moduleFrotStatiqueMax - EPSILON
				& sommeAutresForcesParalleles <= moduleFrotStatiqueMax + EPSILON) {
			forceFrot = orientationMouvementVaisseau
					.multiplie(-1 * moduleFrotStatiqueMax / orientationMouvementVaisseau.module());
		}

		return forceFrot;
	}

	/**
	 * Calcule et retourne un vecteur exprimant la composante en X de la force
	 * gravitationnelle s'appliquant sur un objet dont la masse est passée en
	 * parametre
	 *
	 * @param forceGrav Vecteur de la force gravitationnelle agissant sur l'objet
	 * @param angleDeg  Angle de la surface avec l'horizontale
	 * @return Un vecteur représentant la force gravitationnelle exercée sur l'objet
	 *         selon l'axe x (incliné à angleDeg degré)
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceGravEnX(Vecteur2D forceGrav, double angleDeg) {
		double rapportRadiansDegres = 2 * Math.PI / 360;
		double angleRad = angleDeg * rapportRadiansDegres;
		return new Vecteur2D(-forceGrav.module() * Math.sin(angleRad), 0);
	}

	/**
	 * Calcule et retourne un vecteur exprimant la force gravitationnelle
	 * s'appliquant sur un objet dont la masse est passée en paramètre
	 *
	 * @param masse Masse de l'objet
	 * @return Un vecteur représentant la force gravitationnelle exercée
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceGravEnY(double masse) {
		return new Vecteur2D(0, MoteurPhysique.getAccelGrav() * masse);
	}

	/**
	 * Calcule et retourne un vecteur exprimant la force normale s'appliquant sur le
	 * vaisseau
	 *
	 * @param vaisseau Objet représentant un vaisseau
	 * @param segment  Objet représent la surface d'un bloc (soit un segment)
	 * @return Le vecteur de la force normale s'appliquant sur le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceNormale(Vaisseau vaisseau, Segment segment) {
		Vecteur2D forceNormale = segment.getNormale().multiplie(vaisseau.getMasse() * Math.abs(MoteurPhysique.getAccelGrav()));

		Vecteur2D distanceSegmentVaisseau = vaisseau.getPosition().soustrait(segment.getPointQuelconque());
		if (distanceSegmentVaisseau.prodScalaire(segment.getNormale()) < 0) {
			forceNormale.multiplie(-1);
		}
		return forceNormale;
	}

	/**
	 * Calcule et retourne le module de la force de frottement cinétique
	 * s'appliquant sur le vaisseau
	 *
	 * @param vaisseau Objet représentant un vaisseau
	 * @return Le module de la force de frottement cinétique s'appliquant sur le
	 *         vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static double calculModuleForceFrotCine(Vaisseau vaisseau) {
		return vaisseau.getForceNormale().multiplie(MoteurPhysique.getCoeffFrotCine()).module();
	}

	/**
	 * Méthode qui vérifie si le vaisseau entre en collision avec le Pic
	 *
	 * @param vaisseau     Un objet représant le vaisseau
	 * @param objetSpecial Une tuile représentant un objet spécial (drapeau, pics,
	 *                     tuile)
	 * @return boolean de si le vaisseau et en collision
	 */
	// Kitimir Yim
	public static boolean detecteCollisionsAvecObjetsSpeciaux(Vaisseau vaisseau, Tuile objetSpecial) {
		Area aireVaisseau = vaisseau.formerAireDuVaisseau();
		Area aireTuile = objetSpecial.formerAireObjetSpecial();
		aireVaisseau.intersect(aireTuile);
		return !aireVaisseau.isEmpty();
	}
}
