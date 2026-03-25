package physique;

import java.awt.geom.Point2D;

import interactif.PlaqueChargee;
import interactif.Vaisseau;

/**
 * Regroupe les méthodes de détection de collisions et de calcul des vitesses
 * après collision pour la simulation physique.
 *
 * @author Enuel René Valentin Kizozo Izia
 */
public class CollisionPhysique {

	/** Coefficient de restitution pour un vaisseau et une surface **/
	private static final double COEFF_E = 19.0 / 20.0;

	/** Tolérance utilisée dans les comparaisons réelles avec zéro **/
	private static final double EPSILON = 1e-10;

	/** Vecteur nul **/
	private static final Vecteur2D VEC_ZERO = new Vecteur2D();

	/**
	 * Détecte s'il y a une collision entre le vaisseau et un coin, puis calcule la
	 * vitesse du vaisseau après la collision (s'il y a lieu)
	 *
	 * @param vaisseau Objet représentant le vaisseau
	 * @param coin     Objet représentant le coin d'un bloc (un point)
	 * @return La nouvelle vitesse du vaisseau, après la collision (s'il y a lieu)
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionAvecCoinEtCalculeVitesse(Vaisseau vaisseau, Point2D.Double coin) {
		Vecteur2D vitApresCol = new Vecteur2D(vaisseau.getVitesse());
		Vecteur2D positionCoin = new Vecteur2D(coin.getX(), coin.getY());
		double distanceVaisseauCoin = positionCoin.soustrait(vaisseau.getPosition()).module();

		if (distanceVaisseauCoin < vaisseau.getRayon() + EPSILON) {
			vaisseau.setCollisionTrouvee(true);
			vitApresCol = vaisseau.getVitesse().multiplie(-COEFF_E);
			vaisseau.setPosition(vaisseau.getPositionPrecedente());
		}
		return vitApresCol;
	}

	/**
	 * Détecte s'il y a une collision entre le vaisseau et un mur, puis calcule la
	 * vitesse du vaisseau après la collision (s'il y a lieu)
	 *
	 * @param vaisseau Objet représentant le vaisseau
	 * @param plaque   Objet représentant une plaque chargée
	 * @return La nouvelle vitesse du vaisseau, après la collision (s'il y a lieu)
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionsAvecPlaqueEtCalculeVitesse(Vaisseau vaisseau, PlaqueChargee plaque) {
		Vecteur2D vitApresCol;
		Vecteur2D distanceVaisseauPointSurPlaque = vaisseau.getPosition().soustrait(plaque.getPosition());
		double plusPetiteDistanceVaisseauPlaque = Math
				.abs(distanceVaisseauPointSurPlaque.prodScalaire(plaque.getNormale()));

		Vecteur2D dVaisseauExtrA = plaque.getExtremiteA().soustrait(vaisseau.getPosition());
		Vecteur2D dVaisseauExtrB = plaque.getExtremiteB().soustrait(vaisseau.getPosition());

		double dVaisseauExtrASurAxe = Math.abs(dVaisseauExtrA.prodScalaire(plaque.getAxe()));
		double dVaisseauExtrBSurAxe = Math.abs(dVaisseauExtrB.prodScalaire(plaque.getAxe()));

		boolean vaisseauEntreExtremite = (dVaisseauExtrASurAxe + dVaisseauExtrBSurAxe > plaque.getLongueur() - EPSILON)
				& (dVaisseauExtrASurAxe + dVaisseauExtrBSurAxe < plaque.getLongueur() + EPSILON);
		boolean collisionPlaque = (plusPetiteDistanceVaisseauPlaque - plaque.getLargeur() / 2) < vaisseau.getRayon()
				& vaisseauEntreExtremite;

		boolean collisionExtremiteA = dVaisseauExtrA.module() < vaisseau.getRayon();
		boolean collisionExtremiteB = dVaisseauExtrB.module() < vaisseau.getRayon();

		if ((collisionExtremiteA | collisionExtremiteB) & !vaisseauEntreExtremite) {
			vitApresCol = calculVitesseApresCollisionExtremitePlaque(vaisseau, plaque, collisionExtremiteA);
		} else if (collisionPlaque) {
			vitApresCol = calculVitesseApresCollisionFaceLateralePlaque(vaisseau, plaque);
		} else {
			vitApresCol = vaisseau.getVitesse();
		}

		return vitApresCol;
	}

	/**
	 * Détecte s'il y a une collision entre le vaisseau et un segment, puis calcule
	 * la vitesse du vaisseau après la collision (s'il y a lieu)
	 *
	 * @param vaisseau Objet représentant le vaisseau
	 * @param segment  Objet représentant un segment
	 * @return La nouvelle vitesse du vaisseau, après la collision (s'il y a lieu)
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionsAvecSegmentEtCalculeVitesse(Vaisseau vaisseau, Segment segment) {
		Vecteur2D vitApresCol;
		Vecteur2D distanceVaisseauPointSurSegment = vaisseau.getPosition().soustrait(segment.getPointQuelconque());
		double plusPetiteDistanceVaisseauSegment = Math
				.abs(distanceVaisseauPointSurSegment.prodScalaire(segment.getNormale()));

		Vecteur2D dVaisseauExtrA = segment.getExtremiteA().soustrait(vaisseau.getPosition());
		Vecteur2D dVaisseauExtrB = segment.getExtremiteB().soustrait(vaisseau.getPosition());

		double dVaisseauExtrASurAxe = Math.abs(dVaisseauExtrA.prodScalaire(segment.getAxe()));
		double dVaisseauExtrBSurAxe = Math.abs(dVaisseauExtrB.prodScalaire(segment.getAxe()));

		boolean vaisseauEntreExtremite = (dVaisseauExtrASurAxe + dVaisseauExtrBSurAxe > segment.getLongueur() - EPSILON)
				& (dVaisseauExtrASurAxe + dVaisseauExtrBSurAxe < segment.getLongueur() + EPSILON);
		boolean collisionLateralSegment = (plusPetiteDistanceVaisseauSegment < vaisseau.getRayon() + EPSILON)
				& vaisseauEntreExtremite;

		if (collisionLateralSegment) {
			vaisseau.setEnCollision(true);
			vaisseau.setCollisionTrouvee(true);
			vaisseau.setForceNormale(MoteurPhysique.calculForceNormale(vaisseau, segment));
			vitApresCol = calculVitesseApresCollisionFaceLateraleSegment(vaisseau, segment);
		} else {
			vitApresCol = vaisseau.getVitesse();
		}

		return vitApresCol;
	}

	/**
	 * Détecte s'il y a une collision avec l'une des bordures, puis calcule la
	 * vitesse du vaisseau après la collision selon la bordure
	 *
	 * @param vaisseau         L'objet représentant un vaisseau
	 * @param largeurComposant La largeur de la zone d'animation, en mètre
	 * @param hauteurComposant La hauteur de la zone d'animation, en mètre
	 * @return La nouvelle vitesse du vaisseau, après la collision (s'il y a lieu)
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D detectionCollisionsBorduresEtCalculVitesse(Vaisseau vaisseau, double largeurComposant,
			double hauteurComposant) {
		Vecteur2D vitApresCol = vaisseau.getVitesse();
		double moduleVitApresCol = COEFF_E * vitApresCol.module();

		// Bordure Gauche
		if (vaisseau.getPosition().getX() - vaisseau.getRayon() <= 0) {
			vaisseau.setEnCollision(true);
			vaisseau.setCollisionTrouvee(true);
			Vecteur2D normaleGauche = new Vecteur2D(1, 0);
			vaisseau.setForceNormale(normaleGauche);

			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleGauche.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleGauche)));
			vitApresCol = vitApresCol.changerModule(moduleVitApresCol);

			double correctionPositionX = vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(correctionPositionX, vaisseau.getPosition().getY()));
		}

		// Bordure Droite
		else if (vaisseau.getPosition().getX() + vaisseau.getRayon() >= largeurComposant) {
			vaisseau.setEnCollision(true);
			vaisseau.setCollisionTrouvee(true);
			Vecteur2D normaleDroite = new Vecteur2D(-1, 0);
			vaisseau.setForceNormale(normaleDroite);

			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleDroite.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleDroite)));
			vitApresCol = vitApresCol.changerModule(moduleVitApresCol);

			double correctionPositionX = largeurComposant - vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(correctionPositionX, vaisseau.getPosition().getY()));
		}

		// Bordure Haut
		else if (vaisseau.getPosition().getY() + vaisseau.getRayon() >= hauteurComposant) {
			vaisseau.setEnCollision(true);
			vaisseau.setCollisionTrouvee(true);
			Vecteur2D normaleHaut = new Vecteur2D(0, -1);
			vaisseau.setForceNormale(normaleHaut);

			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleHaut.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleHaut)));
			vitApresCol = vitApresCol.changerModule(moduleVitApresCol);

			double correctionPositionY = hauteurComposant - vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(vaisseau.getPosition().getX(), correctionPositionY));
		}

		// Bordure Bas
		else if (vaisseau.getPosition().getY() - vaisseau.getRayon() <= 0) {
			vaisseau.setEnCollision(true);
			vaisseau.setCollisionTrouvee(true);
			Vecteur2D normaleBas = new Vecteur2D(0, 1);
			vaisseau.setForceNormale(normaleBas);

			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleBas.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleBas)));
			vitApresCol = vitApresCol.changerModule(moduleVitApresCol);

			double correctionPositionY = vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(vaisseau.getPosition().getX(), correctionPositionY));
		}

		return vitApresCol;
	}

	/**
	 * Calcule la vitesse du vaisseau après une collision contre une des extrémités
	 * de la plaque
	 *
	 * @param vaisseau            L'objet représentant un vaisseau
	 * @param plaque              L'objet représentant une plaque chargée
	 * @param collisionExtremiteA Booléen indiquant la collision à lieu à
	 *                            l'extrémité A de la plaque
	 * @return La nouvelle vitesse du vaisseau, après la collision
	 */
	// Enuel René Valentin Kizozo Izia
	static Vecteur2D calculVitesseApresCollisionExtremitePlaque(Vaisseau vaisseau, PlaqueChargee plaque,
			boolean collisionExtremiteA) {
		Vecteur2D vitApresCol = vaisseau.getVitesse().multiplie(-COEFF_E);

		try {
			Vecteur2D normaleCollisionExtremite = vaisseau.getVitesse().multiplie(-1).normalise();
			if (collisionExtremiteA) {
				vaisseau.setPosition(
						plaque.getExtremiteA().additionne(normaleCollisionExtremite.multiplie(vaisseau.getRayon())));
			} else {
				vaisseau.setPosition(
						plaque.getExtremiteB().additionne(normaleCollisionExtremite.multiplie(vaisseau.getRayon())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vitApresCol;
	}

	/**
	 * Calcule la vitesse du vaisseau après une collision contre une face latérale
	 * de la plaque
	 *
	 * @param vaisseau L'objet représentant un vaisseau
	 * @param plaque   L'objet représentant une plaque chargée
	 * @return La nouvelle vitesse du vaisseau, après la collision
	 */
	// Enuel René Valentin Kizozo Izia
	static Vecteur2D calculVitesseApresCollisionFaceLateralePlaque(Vaisseau vaisseau, PlaqueChargee plaque) {
		try {
			Vecteur2D vitApresCol;
			Vecteur2D orientationDistancePlaqueVaisseau;
			orientationDistancePlaqueVaisseau = vaisseau.getPosition().soustrait(plaque.getPosition()).normalise();

			Vecteur2D orientationVitesseInitiale = vaisseau.getVitesse().normalise();
			Vecteur2D invOrientationVitesseInitiale = orientationVitesseInitiale.multiplie(-1);
			Vecteur2D normaleSurface = plaque.getNormale();

			if (orientationDistancePlaqueVaisseau.prodScalaire(normaleSurface) < 0) {
				normaleSurface = normaleSurface.multiplie(-1);
			}
			Vecteur2D orientationVitesseFinale = orientationVitesseInitiale.additionne(
					normaleSurface.multiplie(2 * invOrientationVitesseInitiale.prodScalaire(normaleSurface)));
			double moduleVitApresCol = COEFF_E * vaisseau.getVitesse().module();
			vitApresCol = orientationVitesseFinale.multiplie(moduleVitApresCol);

			Vecteur2D distancePlaqueVaisseau = vaisseau.getPosition().soustrait(plaque.getPosition());
			Vecteur2D dVaisseauExtrA = plaque.getExtremiteA().soustrait(vaisseau.getPosition());
			double dVaisseauExtrASurAxe = Math.abs(dVaisseauExtrA.prodScalaire(plaque.getAxe()));
			Vecteur2D lieuCollision = plaque.getExtremiteA().soustrait(plaque.getAxe().multiplie(dVaisseauExtrASurAxe));
			if (distancePlaqueVaisseau.prodScalaire(plaque.getNormale()) > 0) {
				vaisseau.setPosition(lieuCollision
						.additionne(plaque.getNormale().multiplie(vaisseau.getRayon() + plaque.getLargeur() / 2)));
			} else {
				vaisseau.setPosition(lieuCollision.additionne(
						plaque.getNormale().multiplie(-1 * (vaisseau.getRayon() + plaque.getLargeur() / 2))));
			}

			return vitApresCol;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(
					"Le vaisseau est trop prêt de la plaque ou sa vitesse initiale est nulle, donc sa vitesse finale sera nulle");
			return VEC_ZERO;
		}
	}

	/**
	 * Calcule la vitesse du vaisseau après une collision contre une face latérale
	 * du segment
	 *
	 * @param vaisseau L'objet représentant un vaisseau
	 * @param segment  L'objet représentant un segment
	 * @return La nouvelle vitesse du vaisseau, après la collision
	 */
	// Enuel René Valentin Kizozo Izia
	static Vecteur2D calculVitesseApresCollisionFaceLateraleSegment(Vaisseau vaisseau, Segment segment) {
		try {
			Vecteur2D vitApresCol;
			Vecteur2D normaleSurfaceVersVaisseau = segment.getNormale();
			Vecteur2D orientationDistanceSegmentVaisseau;

			Vecteur2D orientationDistanceSegmentVaisseauAvantCollision = vaisseau.getPositionPrecedente()
					.soustrait(segment.getPointQuelconque()).normalise();
			Vecteur2D orientationDistanceSegmentVaisseauPendantCollision = vaisseau.getPosition()
					.soustrait(segment.getPointQuelconque()).normalise();

			if (Math.signum(
					orientationDistanceSegmentVaisseauAvantCollision.prodScalaire(normaleSurfaceVersVaisseau)) == Math
							.signum(orientationDistanceSegmentVaisseauPendantCollision
									.prodScalaire(normaleSurfaceVersVaisseau))) {

				orientationDistanceSegmentVaisseau = orientationDistanceSegmentVaisseauPendantCollision;
			} else {
				orientationDistanceSegmentVaisseau = orientationDistanceSegmentVaisseauAvantCollision;
			}

			if (orientationDistanceSegmentVaisseau.prodScalaire(normaleSurfaceVersVaisseau) < 0) {
				normaleSurfaceVersVaisseau = normaleSurfaceVersVaisseau.multiplie(-1);
			}

			Vecteur2D orientationVitesseInitiale = vaisseau.getVitesse().normalise();
			Vecteur2D invOrientationVitesseInitiale = orientationVitesseInitiale.multiplie(-1);

			Vecteur2D orientationVitesseFinale = orientationVitesseInitiale.additionne(normaleSurfaceVersVaisseau
					.multiplie(2 * invOrientationVitesseInitiale.prodScalaire(normaleSurfaceVersVaisseau)));

			double moduleVitApresCol = COEFF_E * vaisseau.getVitesse().module();
			vitApresCol = orientationVitesseFinale.multiplie(moduleVitApresCol);

			Vecteur2D dVaisseauExtrA = segment.getExtremiteA().soustrait(vaisseau.getPosition());
			double dVaisseauExtrASurAxe = Math.abs(dVaisseauExtrA.prodScalaire(segment.getAxe()));
			Vecteur2D lieuCollision = segment.getExtremiteA()
					.additionne(segment.getAxe().multiplie(dVaisseauExtrASurAxe));

			vaisseau.setPosition(
					lieuCollision.additionne(normaleSurfaceVersVaisseau.multiplie(vaisseau.getRayon() + EPSILON)));

			return vitApresCol;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(
					"Le vaisseau est trop prêt du segment ou sa vitesse initiale est nulle, donc sa vitesse finale sera nulle");
			return VEC_ZERO;
		}
	}
}
