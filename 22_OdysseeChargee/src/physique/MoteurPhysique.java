package physique;

import interactif.PlaqueChargee;
import interactif.Vaisseau;

/**
 * Cette classe regroupe les calculs physiques nécessaires au mouvement
 * des divers objets dans la scène.
 * Utilise entre autres la méthode d'intégration numérique d'Euler
 * semi-implicite.
 * 
 * @author Caroline Houle
 * @author Enuel René Valentin Kizozo Izia
 *
 */
public class MoteurPhysique {

	/** Accélération gravitationnelle de la Terre (en m/s^2) */
	private static final double ACCEL_G = 9.80665;

	/** Constante de Coulomb */
	private static final double K = 8.98755;

	/**
	 * Coefficient de restitution pour un vaisseau et une surface, tous deux en
	 * acier
	 */
	private static final double COEFF_E = 19.0 / 20.0;

	/** Tolérance utilisée dans les comparaisons réelles avec zéro */
	private static final double EPSILON = 1e-10;

	/** Rapport entre les radians et les degrés */
	private static final double RAPPORT_RADIANS_DEGRES = 2 * Math.PI / 360;

	/** Vecteur nul */
	private static final Vecteur2D VEC_ZERO = new Vecteur2D();

	/**
	 * Calcule et retourne l'accélération en utilisant F = ma
	 * 
	 * @param sommeDesForces Somme des forces appliquees
	 * @param masse          Masse de l'objet
	 * @return L'accélération F/m
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	// Caroline Houle
	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception {
		if (masse < EPSILON)
			throw new Exception(
					"Erreur MoteurPhysique: La masse étant nulle ou presque, l'accéleration ne peut pas etre calcul�e.");
		else
			return new Vecteur2D(sommeDesForces.getX() / masse, sommeDesForces.getY() / masse);
	}

	/**
	 * Calcule et retourne la nouvelle vitesse, deltaT secondes plus tard, en
	 * utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * 
	 * @param deltaT  L'intervalle de temps (petit!) en secondes
	 * @param vitesse La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @param accel   L'accélération initiale au début de l'intervalle de temps, en
	 *                m/s^2
	 * @return La nouvelle vitesse (à la fin de l'intervalle)
	 */
	// Caroline Houle
	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D vitesse, Vecteur2D accel) {
		Vecteur2D deltaVitesse = Vecteur2D.multiplie(accel, deltaT);
		Vecteur2D resultVit = vitesse.additionne(deltaVitesse);
		return new Vecteur2D(resultVit.getX(), resultVit.getY());

	}

	/**
	 * Calcule et retourne la nouvelle position, deltaT secondes plus tard, en
	 * utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * 
	 * @param deltaT   L'intervalle de temps (petit!) en secondes
	 * @param position La position initiale au début de l'intervalle de temps, en m
	 * @param vitesse  La vitesse initiale au début de l'intervalle de temps, en m/s
	 * @return La nouvelle position (a la fin de l'intervalle)
	 */
	// Caroline Houle
	public static Vecteur2D calculPosition(double deltaT, Vecteur2D position, Vecteur2D vitesse) {
		Vecteur2D deltaPosition = Vecteur2D.multiplie(vitesse, deltaT);
		Vecteur2D resultPos = position.additionne(deltaPosition);
		return new Vecteur2D(resultPos.getX(), resultPos.getY());

	}

	/**
	 * Calcule et retourne un vecteur exprimant la force gravitationnelle
	 * s'appliquant
	 * sur un objet dont la masse est passée en paramètre
	 * 
	 * @param masse Masse de l'objet
	 * @return Un vecteur représentant la force gravitationnelle exercée
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceGrav(double masse) {
		return new Vecteur2D(0, -ACCEL_G * masse);
	}

	/**
	 * Calcule et retourne un vecteur exprimant la composante en X de la force
	 * gravitationnelle
	 * s'appliquant sur un objet dont la masse est passée en parametre
	 * 
	 * @param forceGrav Vecteur de la force gravitationnelle agissant sur l'objet
	 * @param angleDeg  Angle de la surface avec l'horizontale
	 * @return Un vecteur représentant la force gravitationnelle exercée sur l'objet
	 *         selon l'axe x (incliné à angleDeg degré)
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceGravEnX(Vecteur2D forceGrav, double angleDeg) {
		double angleRad = angleDeg * RAPPORT_RADIANS_DEGRES;
		return new Vecteur2D(-forceGrav.module() * Math.sin(angleRad), 0);
	}

	/**
	 * Calcule et retourne un vecteur exprimant la force de frottement cinétique
	 * s'appliquant sur le vaisseau
	 * 
	 * @param masse               La masse de l'objet
	 * @param coeffFrottementCine Le coefficient de frottement cinétique de la
	 *                            surface
	 * @param vitesse             La vitesse du vaisseau
	 * @param angleDeg            L'angle de la surface avec l'horizontale, en degré
	 * @return Un vecteur représentant la force de frottement cinétique exercée sur
	 *         le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceFrotCine(double masse, double coeffFrottementCine, Vecteur2D vitesse,
			double angleDeg) {
		double angleRad = angleDeg * RAPPORT_RADIANS_DEGRES;
		Vecteur2D forceFrot;

		if (vitesse.module() < EPSILON) {
			forceFrot = VEC_ZERO;
		} else {
			forceFrot = new Vecteur2D(-coeffFrottementCine * masse * ACCEL_G * Math.cos(angleRad), 0);
		}
		return forceFrot;
	}

	/**
	 * Calcule et retourne un vecteur exprimant la force de frottement statique
	 * s'appliquant sur le vaisseau
	 * 
	 * @param masse                       La masse de l'objet
	 * @param coeffFrottementStat         Le coefficient de frottement statique de
	 *                                    la surface
	 * @param vitesse                     La vitesse du vaisseau
	 * @param angleDeg                    L'angle de la surface avec l'horizontale,
	 *                                    en degré
	 * @param sommeAutresForcesParalleles La somme de toutes les autres forces
	 *                                    parallèles au frottement statique
	 * @return Un vecteur représentant la force de frottement statique exercée sur
	 *         le vaisseau
	 */
	// Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceFrotStat(double masse, double coeffFrottementStat, Vecteur2D vitesse,
			double angleDeg, double sommeAutresForcesParalleles) {
		double angleRad = angleDeg * RAPPORT_RADIANS_DEGRES;
		double frottementStatiqueMax = -coeffFrottementStat * masse * ACCEL_G * Math.cos(angleRad);
		Vecteur2D forceFrot;

		if (vitesse.module() < EPSILON) {
			forceFrot = VEC_ZERO;
		} else if (sommeAutresForcesParalleles <= frottementStatiqueMax) {
			forceFrot = new Vecteur2D(-sommeAutresForcesParalleles, 0);
		} else {
			forceFrot = new Vecteur2D(frottementStatiqueMax, 0);
		}
		return forceFrot;
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
			forceElec = calculChampElectriqueSurAxe(vaisseau, plaque).multiplie(1 / vaisseau.getCharge());
		} else {
			forceElec = calculChampElectriqueHorsAxe(vaisseau, plaque).multiplie(vaisseau.getCharge());
		} // fin if

		return forceElec;
	}// fin méthode

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

			// détermine le vecteur orientation du champ électrique, unitaire (en assumant
			// que le vaisseau est attiré par la plaque)
			Vecteur2D orientationChamp;

			orientationChamp = plaque.getPosition().soustrait(vaisseau.getPosition()).normalise();

			// change l'orientation s'il y a répulsion
			if (Math.signum(vaisseau.getCharge()) == Math.signum(plaque.getCharge())) {
				orientationChamp.multiplie(-1);
			}

			return orientationChamp.multiplie(moduleChamp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(
					"Le vaisseau est trop près de la plaque, le champ électrique agissant sur la plaque est donc nul.");
			return VEC_ZERO;
		} // fin méthode
	}// fin try catch

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

			// Projection orthogonale de la distance entre le vaisseau et la plaque, sur la
			// normale de la plaque
			double plusPetiteDistanceVaisseauPlaque = Math
					.abs(distanceVaisseauPlaque.prodScalaire(plaque.getNormale()));

			// Angles délimitant les côtés des extrémités de la plaque
			double alphaA = Math.acos(plusPetiteDistanceVaisseauPlaque / distanceVaisseauExtremiteA.module());
			double alphaB = Math.acos(plusPetiteDistanceVaisseauPlaque / distanceVaisseauExtremiteB.module());

			// Détermine les signes des angles alpha
			double projDistanceVExtrmASurAxePlaque = Math.abs(distanceVaisseauExtremiteA.prodScalaire(plaque.getAxe()));
			double projDistanceVExtrmBSurAxePlaque = Math.abs(distanceVaisseauExtremiteB.prodScalaire(plaque.getAxe()));
			boolean vaisseauEntreExtremite = !(projDistanceVExtrmASurAxePlaque
					+ projDistanceVExtrmBSurAxePlaque > plaque.getLongueur());
			if (vaisseauEntreExtremite) {
				alphaB = -1 * alphaB;
			}

			// Calcul du module du champ électrique
			double moduleChamp = Math.sqrt(2) * K * Math.abs(densiteLineiqueCharge) / plusPetiteDistanceVaisseauPlaque
					* Math.sqrt(1 - Math.cos(alphaA - alphaB));

			/*
			 * Détermine le vecteur orientation du champ électrique, unitaire
			 * (en assumant que la plaque est chargée négativement)
			 */
			Vecteur2D orientationChamp = distanceVaisseauPlaque.normalise();

			// Change l'orientation si la plaque est chargée positivement
			if (Math.signum(plaque.getCharge()) > 0) {
				orientationChamp = orientationChamp.multiplie(-1);
			}

			System.out.println(
					"Champ électrique sur le vaisseau : " + orientationChamp.multiplie(moduleChamp).toString(3));

			return orientationChamp.multiplie(moduleChamp);
		} catch (Exception e) {
			/*
			 * Si la distance entre la plaque et le vaisseau est trop petite,
			 * nous ne pouvons pas normaliser ce vecteur, et donc le champ électrique est
			 * nul
			 */
			e.printStackTrace();
			System.out.println(
					"Le vaisseau est trop près de la plaque, le champ électrique agissant sur la plaque est donc nul.");
			return VEC_ZERO;
		} // fin try catch
	}// fin méthode

	/**
	 * Détecte s'il y a une collision entre le vaisseau et un mur,
	 * puis calcule la vitesse du vaisseau après la collision (s'il y a lieu)
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

		/*
		 * Détermine si le vaisseau est entre les extrémités de la plaque
		 * et s'il est en collision avec la plaque
		 */
		boolean vaisseauEntreExtremite = (dVaisseauExtrASurAxe + dVaisseauExtrBSurAxe > plaque.getLongueur() - EPSILON)
				& (dVaisseauExtrASurAxe + dVaisseauExtrBSurAxe < plaque.getLongueur() + EPSILON);
		boolean collisionPlaque = (plusPetiteDistanceVaisseauPlaque - plaque.getLargeur() / 2) < vaisseau.getRayon()
				& vaisseauEntreExtremite;

		// Déterminer si le vaisseau est en collision avec les extrémités
		boolean collisionExtremiteA = dVaisseauExtrA.module() < vaisseau.getRayon();
		boolean collisionExtremiteB = dVaisseauExtrB.module() < vaisseau.getRayon();

		// Collision aux extrémités
		if ((collisionExtremiteA | collisionExtremiteB) & !vaisseauEntreExtremite) {
			vitApresCol = calculVitesseApresCollisionExtremitePlaque(vaisseau, plaque, collisionExtremiteA);
			System.out.println("Collision aux extrémités !");
			// Collision entre les extrémités
		} else if (collisionPlaque) {
			vitApresCol = calculVitesseApresCollisionFaceLateralePlaque(vaisseau, plaque);
			System.out.println("Collision entre les extrémités !");
			System.out
					.println("Ajustements vaisseau dû à une collision avec la plaque : " + vaisseau.toString(3) + "\n");

			// Pas de collision
		} else {
			vitApresCol = vaisseau.getVitesse();
		}

		System.out.println("\nDistance vaisseau plaque : " + distanceVaisseauPointSurPlaque.module());
		System.out.println("Plus petite distance vaisseau plaque : " + plusPetiteDistanceVaisseauPlaque + "\n");

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
	private static Vecteur2D calculVitesseApresCollisionExtremitePlaque(Vaisseau vaisseau, PlaqueChargee plaque,
			boolean collisionExtremiteA) {
		Vecteur2D vitApresCol = vaisseau.getVitesse().multiplie(-COEFF_E);

		// Repositionner vaisseau après collision pour éviter bug
		try {
			Vecteur2D normaleCollisionExtremite = vaisseau.getVitesse().multiplie(-1).normalise();
			System.out.println("Orientation normale : " + normaleCollisionExtremite);
			if (collisionExtremiteA) {
				vaisseau.setPosition(
						plaque.getExtremiteA().additionne(normaleCollisionExtremite.multiplie(vaisseau.getRayon())));
				System.out
						.println("Ajustement vaisseau dû à une potentielle collision : " + vaisseau.toString(3) + "\n");
			} else {
				vaisseau.setPosition(
						plaque.getExtremiteB().additionne(normaleCollisionExtremite.multiplie(vaisseau.getRayon())));
				System.out.println(
						"Ajustement vaisseau dû à une collision avec la plaque : " + vaisseau.toString(3) + "\n");
			} // fin if
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("La vitesse du vaisseau est (presque) nulle, donc on ne le repositionne pas.");
		} // fin try/catch

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
	private static Vecteur2D calculVitesseApresCollisionFaceLateralePlaque(Vaisseau vaisseau, PlaqueChargee plaque) {
		try {
			Vecteur2D vitApresCol;
			Vecteur2D orientationDistancePlaqueVaisseau;
			orientationDistancePlaqueVaisseau = vaisseau.getPosition().soustrait(plaque.getPosition()).normalise();

			Vecteur2D orientationVitesseInitiale = vaisseau.getVitesse().normalise();
			Vecteur2D invOrientationVitesseInitiale = orientationVitesseInitiale.multiplie(-1);
			Vecteur2D normaleSurface = plaque.getNormale();

			// Permet de s'assurer que la normale est bien orientée vers l'extérieur de la
			// surface, donc vers le vaisseau
			if (orientationDistancePlaqueVaisseau.prodScalaire(normaleSurface) < 0) {
				normaleSurface = normaleSurface.multiplie(-1);
			}
			Vecteur2D orientationVitesseFinale = orientationVitesseInitiale.additionne(
					normaleSurface.multiplie(2 * invOrientationVitesseInitiale.prodScalaire(normaleSurface)));
			double moduleVitApresCol = COEFF_E * vaisseau.getVitesse().module();
			vitApresCol = orientationVitesseFinale.multiplie(moduleVitApresCol);

			// Repositionner vaisseau après collision pour éviter bug
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

			// System.out.println("Vitesse après collision : " + vitApresCol);
			// System.out.println("Ancienne pos : "+ vaisseau.getPosition());
			// System.out.println("Nouvelle pos : "+ vaisseau.getPosition());
			return vitApresCol;
		} catch (Exception e) {
			/*
			 * Si la vitesse initiale est nulle
			 * ou si le vaisseau est trop prêt de la plaque,
			 * alors la vitesse finale est nulle
			 */
			e.printStackTrace();
			System.out.println(
					"Le vaisseau est trop prêt de la plaque ou sa vitesse initiale est nulle, donc sa vitesse finale sera nulle");
			return VEC_ZERO;
		} // fin try catch
	}// fin méthode

	/**
	 * Détecte s'il y a une collision avec l'une des bordures,
	 * puis calcule la vitesse du vaisseau après la collision selon la bordure
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

		// Bordure Gauche
		if (vaisseau.getPosition().getX() - vaisseau.getRayon() <= 0) {
			Vecteur2D normaleGauche = new Vecteur2D(1, 0);
			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleGauche.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleGauche)));
			double correctionPositionX = vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(correctionPositionX, vaisseau.getPosition().getY()));
		}

		// Bordure Droite
		if (vaisseau.getPosition().getX() + vaisseau.getRayon() >= largeurComposant) {
			Vecteur2D normaleDroite = new Vecteur2D(-1, 0);
			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleDroite.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleDroite)));
			double correctionPositionX = largeurComposant - vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(correctionPositionX, vaisseau.getPosition().getY()));
		}

		// Bordure Haut
		if (vaisseau.getPosition().getY() + vaisseau.getRayon() >= hauteurComposant) {
			Vecteur2D normaleHaut = new Vecteur2D(0, -1);
			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleHaut.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleHaut)));
			double correctionPositionY = hauteurComposant - vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(vaisseau.getPosition().getX(), correctionPositionY));
		}

		// Bordure Bas
		if (vaisseau.getPosition().getY() - vaisseau.getRayon() <= 0) {
			Vecteur2D normaleBas = new Vecteur2D(0, 1);
			vitApresCol = vaisseau.getVitesse()
					.soustrait(normaleBas.multiplie(2 * vaisseau.getVitesse().prodScalaire(normaleBas)));
			double correctionPositionY = vaisseau.getRayon();
			vaisseau.setPosition(new Vecteur2D(vaisseau.getPosition().getX(), correctionPositionY));
		}

		return vitApresCol;
	}// fin méthode
}// fin classe
