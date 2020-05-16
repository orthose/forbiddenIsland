package model;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author maxime
 * @apiNote Modèle de l'île le plateau de jeu
 */
public class IslandModel extends Observable {
	public final int WIDTH, HEIGHT; 
	protected ArrayList<ArrayList<Zone>> zones;
	private ArrayList<Player> players;
	
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
				zones.get(i).add(new Zone(this, String.valueOf(line.charAt(j)), i, j));
			}
		}
		// Vérification que la grille est complète
		int lineSize = zones.get(0).size();
		for(ArrayList<Zone> lineZone : this.zones) {
			if(lineSize != lineZone.size()) {
				throw new IllegalArgumentException("Incorrect map size in IslandModel() constructor");
			}
		}
		this.WIDTH = lineSize;
		this.HEIGHT = this.zones.size();
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * @apiNote Ajoute un joueur à la partie
	 * @param player: Joueur à ajouter
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	/**
	 * @apiNote Permet de déplacer un joueur
	 * @param order: Numéro du joueur
	 * @param move: Déplacement souhaité
	 * @throws Player.InvalidOrder: Si le déplacement
	 * dépasse les bords de l'île
	 */
	public void movePlayer(int order, Move move) throws Player.InvalidOrder {
		if(Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			Zone newPosition = player.position;
			switch(move) {
				case UP: 
					if(player.position.y > 0) {
						newPosition = zones.get(player.position.y - 1).get(player.position.x);
					} break;
				case DOWN:
					if(player.position.y < this.HEIGHT - 1) {
						newPosition = zones.get(player.position.y + 1).get(player.position.x);
					} break;
				case RIGHT:
					if(player.position.x > 0) {
						newPosition = zones.get(player.position.y).get(player.position.x + 1);
					} break;
				case LEFT:
					if(player.position.x < this.WIDTH - 1) {
						newPosition = zones.get(player.position.y).get(player.position.x - 1);
					} break;
			}
			player.move(newPosition);
		}
		else {
			throw new Player.InvalidOrder(order);
		}
	}
	
	@Override
	public String toString() {
		String res = "";
		for(int i = 0; i < this.WIDTH; i++) {
			for(int j = 0; j < this.HEIGHT; j++) {
				res += zones.get(j).get(i).toString();
			}
			if(i != this.WIDTH - 1) { res += "\n"; }
		}
		return res;
	}

}
