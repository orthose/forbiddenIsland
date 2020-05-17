package model;
import java.util.ArrayList;
import java.util.Observable;

import model.Player.InvalidOrder;

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
	protected void addPlayer(Player player) {
		this.players.add(player);
		super.notifyObservers();
	}
	
	/**
	 * @apiNote Permet de déplacer un joueur
	 * @param order: Numéro du joueur
	 * @param move: Déplacement souhaité
	 * @return true si déplacement valide false sinon
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public boolean movePlayer(int order, Move move) throws Player.InvalidOrder {
		if(Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			Boolean success = Boolean.valueOf(true);
			Zone newPosition = player.position.neighbour(move, success);
			if(newPosition.isCrossable()) {
				player.move(newPosition);
			}
			else {
				success = false;
			}
			if(success) { super.notifyObservers(); };
			return success;
		}
		else {
			throw new Player.InvalidOrder(order);
		}
	}
	
	/**
	 * @apiNote Donne les possibilités de déplacements
	 * pour le joueur spécifié
	 * @param order: Numéro du joueur
	 * @return Liste des déplacements possibles
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public ArrayList<Zone> movePossibilitiesPlayer(int order) throws Player.InvalidOrder {
		if(Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			return new ArrayList<Zone>(player.movePossibilities());
		}
		else {
			throw new Player.InvalidOrder(order);
		}
	}
	
	/**
	 * @apiNote Le joueur spécifié assèche une zone
	 * @param order: Numéro du joueur
	 * @param move: Cible à assécher par rapport
	 * à la zone du joueur (peut être sa propre zone)
	 * @return true si la zone a été asséchée false sinon
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public boolean dryPlayer(int order, Move move) throws Player.InvalidOrder {
		if(Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			boolean success = player.dry(move);
			super.notifyObservers();
			return success;
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
				res += this.zones[i][j].toString();
			}
			if(i != this.WIDTH - 1) { res += "\n"; }
		}
		return res;
	}

}
