package model;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author maxime
 * @apiNote Modèle de l'île le plateau de jeu
 */
public class IslandModel extends Observable {
	public final int WIDTH, HEIGHT; 
	protected Zone[][] zones;
	private ArrayList<Player> players;
	
	/**
	 * @apiNote Crée une île selon une carte
	 * Si une des lignes est de taille différente
	 * de la première ligne les lignes suivantes
	 * ne sont pas chargées
	 * @param map: Carte initiale qui sera chargée
	 * Les symboles sont détaillés dans StringMap
	 */
	public IslandModel(String map) {
		super();
		// Découpe des lignes
		String[] mapLine = map.split("\n");
		this.WIDTH = mapLine[0].length();
		this.HEIGHT = mapLine.length;
		// Chargement des zones
		int i = 0;
		int currentWidth = 0;
		do {
			String line = mapLine[i];
			currentWidth = line.length();
			for(int j = 0; j < currentWidth; j++) {
				this.zones[i][j] = new Zone(this, String.valueOf(line.charAt(j)), i, j);
			}
			i++;
			
		} while(this.WIDTH == currentWidth && i < this.HEIGHT);
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * @apiNote Ajoute un joueur à la partie
	 * @param player: Joueur à ajouter
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
		super.notifyObservers();
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
			player.move(player.position.neighbour(move));
		}
		else {
			throw new Player.InvalidOrder(order);
		}
		super.notifyObservers();
	}
	
	@Override
	public String toString() {
		String res = "";
		for(int i = 0; i < this.WIDTH; i++) {
			for(int j = 0; j < this.HEIGHT; j++) {
				res += this.zones[i][j].toString();
			}
			if(i != this.WIDTH - 1) { res += "\n"; }
		}
		return res;
	}

}
