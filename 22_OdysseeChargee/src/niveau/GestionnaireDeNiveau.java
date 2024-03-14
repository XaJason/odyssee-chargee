package niveau;

import java.util.ArrayList;

public class GestionnaireDeNiveau {
	
	/** ArrayList contenant les niveaux **/
	ArrayList <Niveau> repertoireNiveau = new ArrayList<Niveau>();
	
	
	public void ajouter(Niveau niveauAAjouter) {
		repertoireNiveau.add(niveauAAjouter);
		System.out.println("Ajoue fait avec succès");
	}
	
	public Niveau getNiveau(String nom) {
		nom = nom.toLowerCase();
		for(int i = 0; i<repertoireNiveau.size(); i++) {
			if(repertoireNiveau.get(i).getNomNiveau()==nom) {
				
			}
			
		}
		return null;
		
	}
	

}
