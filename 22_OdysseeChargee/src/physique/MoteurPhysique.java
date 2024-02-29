package physique;

import interactif.PlaqueChargee;

/**
 * Cette classe regroupera les calculs physiques nécessaires au mouvement
 * des divers objets dans la scène.
 * Utilise la méthode d'intégration numérique d'Euler semi-implicite. 
 *  
 * @author Caroline Houle
 * @author Enuel René Valentin Kizozo Izia
 *
 */
public class MoteurPhysique {


	private static final double ACCEL_G = 9.80665; //Accélération gravitationnelle de la Terre
	private static final double K = 8.98755; //Constante de Coulomb
	private static final double EPSILON = 1e-10; //tolerance utilisée dans les comparaisons reelles avec zero
	private static final double RAPPORT_RADIANS_DEGRES = 2*Math.PI/360;
	private static final Vecteur2D VEC_ZERO = new Vecteur2D();
	
	/**
	 * Calcule et retourne l'accélération en utilisant F = ma
	 * @param sommeDesForces Somme des forces appliquees
	 * @param masse Masse de l'objet
	 * @return L'accélération F/m
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	//Caroline Houle
	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception { 
		if(masse < EPSILON) 
			throw new Exception("Erreur MoteurPhysique: La masse étant nulle ou presque, l'accéleration ne peut pas etre calcul�e.");
		else
			return new Vecteur2D( sommeDesForces.getX()/masse , sommeDesForces.getY()/masse );	
	}
	
	/**
	 * Calcule et retourne la nouvelle vitesse, deltaT secondes plus tard, en utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * @param deltaT L'intervalle de temps (petit!) en secondes
	 * @param vitesse La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @param accel L'accélération initiale au début de l'intervalle de temps, en m/s^2
	 * @return La nouvelle vitesse (à la fin de l'intervalle)
	 */
	//Caroline Houle
	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D vitesse, Vecteur2D accel) {
		Vecteur2D deltaVitesse = Vecteur2D.multiplie(accel, deltaT);
		Vecteur2D resultVit = vitesse.additionne( deltaVitesse );
		return new Vecteur2D(resultVit.getX(), resultVit.getY());
		
	}
	
	/**
	 * Calcule et retourne la nouvelle position, deltaT secondes plus tard, en utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * @param deltaT L'intervalle de temps (petit!) en secondes
	 * @param position La position initiale au début de l'intervalle de temps, en m
	 * @param vitesse La vitesse initiale au début de l'intervalle de temps, en m/s
	 * @return La nouvelle position (a la fin de l'intervalle)
	 */
	//Caroline Houle
	public static Vecteur2D calculPosition(double deltaT, Vecteur2D position, Vecteur2D vitesse) {
		Vecteur2D deltaPosition = Vecteur2D.multiplie(vitesse, deltaT);
		Vecteur2D resultPos = position.additionne(deltaPosition); 
		return new Vecteur2D(resultPos.getX(), resultPos.getY());
		
	}
	
	/**
	 * Forme et retourne un vecteur exprimant la force gravitationnelle s'appliquant sur un objet dont la masse est passée en paramètre
	 * @param masse Masse de l'objet
	 * @return Un vecteur représentant la force gravitationnelle exercée
	 */
	//Caroline Houle
	public static Vecteur2D calculForceGrav(double masse) {
		return new Vecteur2D( 0, ACCEL_G*masse);
	}
	
	/**
	 * Forme et retourne un vecteur exprimant la composante en X de la force gravitationnelle
	 * s'appliquant sur un objet dont la masse est passée en parametre
	 * @param masse Masse de l'objet
	 * @return Un vecteur repr�sentant la force gravitationnelle exercee
	 */
	//Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceGravEnX(Vecteur2D forceGrav, double angleDeg) {
		double angleRad = angleDeg*RAPPORT_RADIANS_DEGRES;
		return new Vecteur2D( -forceGrav.module()*Math.sin(angleRad) , 0 );
	}

	/**
	 * Forme et retourne un vecteur exprimant la force de frottement cinétique s'appliquant sur le vaisseau
	 * @param masse La masse de l'objet
	 * @param coeffFrottementCine Le coefficient de frottement cinétique de la surface
	 * @param vitesse La vitesse du vaisseau
	 * @param angleDeg L'angle de la surface avec l'horizontale, en degré
	 * @return Un vecteur représentant la force de frottement cinétique exercée sur le vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceFrotCine(double masse, double coeffFrottementCine, Vecteur2D vitesse, double angleDeg) {
		Vecteur2D forceFrot;
		double angleRad = angleDeg*RAPPORT_RADIANS_DEGRES;
		
		if (vitesse.module() < EPSILON) {
			forceFrot = VEC_ZERO;
		} else {
			forceFrot = new Vecteur2D (-coeffFrottementCine*masse*ACCEL_G*Math.cos(angleRad) , 0 );
		}
		return forceFrot;
	}
	
	/**
	 * Forme et retourne un vecteur exprimant la force de frottement statique s'appliquant sur le vaisseau
	 * @param masse La masse de l'objet
	 * @param coeffFrottementStat Le coefficient de frottement statique de la surface
	 * @param vitesse La vitesse du vaisseau
	 * @param angleDeg L'angle de la surface avec l'horizontale, en degré
	 * @param sommeAutresForcesParalleles La somme de toutes les autres forces parallèles au frottement statique
	 * @return Un vecteur représentant la force de frottement statique exercée sur le vaisseau
	 */
	//Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculForceFrotStat(double masse, double coeffFrottementStat, Vecteur2D vitesse, double angleDeg, double sommeAutresForcesParalleles) {
		Vecteur2D forceFrot;
		double angleRad = angleDeg*RAPPORT_RADIANS_DEGRES;
		double frottementStatiqueMax = -coeffFrottementStat*masse*ACCEL_G*Math.cos(angleRad);
		
		if (vitesse.module() < EPSILON) {
			forceFrot = VEC_ZERO;
		} else if (sommeAutresForcesParalleles <= frottementStatiqueMax){
			forceFrot = new Vecteur2D (-sommeAutresForcesParalleles, 0);
		} else {
			forceFrot = new Vecteur2D (frottementStatiqueMax , 0 );
		}
		return forceFrot;
	}
	
	/**
	 * Retourne le champ électrique généré par une plaque le long de son axe
	 * @return Le champ électrique sur l'axe
	 * @throws Exception 
	 */
	//Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculChampElectriqueSurAxe(Vecteur2D posVaisseau, double chargeVaisseau, Vecteur2D posPlaque, double chargePlaque, double longueurPlaque) throws Exception {
		double distanceVaisseauPlaque = posVaisseau.soustrait(posPlaque).module();
		double moduleChamp = K*Math.abs(chargePlaque) / (distanceVaisseauPlaque * (distanceVaisseauPlaque+longueurPlaque));
		
		//détermine le vecteur orientation du champ électrique, unitaire (en assumant que le vaisseau est attiré par la plaque)
		Vecteur2D orientationChamp = posPlaque.soustrait(posVaisseau).normalise();
		
		//change l'orientation s'il y a répulsion
		if (Math.signum(chargeVaisseau) == Math.signum(chargePlaque)) {
			orientationChamp.multiplie(-1);
		}
		
		return orientationChamp.multiplie(moduleChamp);
	}

	/**
	 * Retourne le champ électrique généré par une plaque hors de son axe
	 * @return Le champ électrique hors axe
	 * @throws Exception 
	 */
	//Enuel René Valentin Kizozo Izia
	public static Vecteur2D calculChampElectriqueHorsAxe(Vecteur2D posVaisseau, double chargeVaisseau, PlaqueChargee plaque) throws Exception {
		double densiteLineiqueCharge = plaque.getCharge()/plaque.getLongueur();
		Vecteur2D distanceVaisseauPlaque = plaque.getPosition().soustrait(posVaisseau);
		Vecteur2D distanceVaisseauExtremiteA = plaque.getExtremiteA().soustrait(posVaisseau);
		Vecteur2D distanceVaisseauExtremiteB = plaque.getExtremiteB().soustrait(posVaisseau);
		
		//Projection orthogonale de la distance entre le vaisseau et la plaque, sur la normale de la plaque
		double plusPetiteDistanceVaisseauPlaque = distanceVaisseauPlaque.prodScalaire(plaque.getNormale()) / plaque.getNormale().module();
		
		//Angles délimitant les côtés des extrémités de la plaque
		double alphaA = Math.acos(plusPetiteDistanceVaisseauPlaque / distanceVaisseauExtremiteA.module());
		double alphaB = Math.acos(plusPetiteDistanceVaisseauPlaque / distanceVaisseauExtremiteB.module());
		
		double moduleChamp = Math.sqrt(2) * K*Math.abs(densiteLineiqueCharge)/plusPetiteDistanceVaisseauPlaque * Math.sqrt(1 - Math.cos(alphaA-alphaB));
		
		//détermine le vecteur orientation du champ électrique, unitaire (en assumant que le vaisseau est attiré par la plaque)
		Vecteur2D orientationChamp = plaque.getPosition().soustrait(posVaisseau).normalise();

		//change l'orientation s'il y a répulsion
		if (Math.signum(chargeVaisseau) == Math.signum(plaque.getCharge())) {
			orientationChamp.multiplie(-1);
		}
		
		return orientationChamp.multiplie(moduleChamp);
	}
}
