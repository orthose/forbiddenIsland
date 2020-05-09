package model;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author maxime
 * @apiNote Modèle de l'île le plateau de jeu
 */
public class IslandModel extends Observable {
	public final int width, height; 
	private ArrayList<ArrayList<Zone>> zones;
	
	/**
	 * @apiNote Crée une île
	 * @param map: Carte initiale qui sera chargée
	 * Les symboles sont détaillés dans StringMap
	 */
	public IslandModel(String map) {
		// Chargement des zones
		String[] mapLine = map.split("\n");
		for(int i = 0; i < mapLine.length; i++) {
			String line = mapLine[i];
			zones.add(new ArrayList<Zone>());
			for(int j = 0; j < line.length(); j++) {
				zones.get(i).add(new Zone(this, String.valueOf(line.charAt(j))));
			}
		}
		// Vérification que la grille est complète
		int lineSize = zones.get(0).size();
		for(ArrayList<Zone> lineZone : this.zones) {
			if(lineSize != lineZone.size()) {
				throw new IllegalArgumentException("Incorrect map size in IslandModel() constructor");
			}
		}
		this.width = lineSize;
		this.height = this.zones.size();
	}

}
