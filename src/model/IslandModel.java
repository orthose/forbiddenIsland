package model;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import model.Player.InvalidOrder;

/**
 * @author maxime
 * @apiNote Modèle de l'île le plateau de jeu
 */
public class IslandModel extends Observable {
	public static final Random rand = new Random();
	public final int WIDTH, HEIGHT; 
	protected Zone[][] zones;
	private ArrayList<Player> players;
	// Paramètres modifiables même à l'exécution
	protected float keyLuck = 0.2f; // Dans ]0.0;1.0[
	
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
	 * @apiNote Permet d'accéder à une zone
	 * depuis la vue
	 * @param x: Coordonnée en x
	 * @param y: Coordonnée en y
	 * @return zone aux coordonnées spécifiées
	 */
	public Zone getZone(int x, int y) {
		return this.zones[x][y];
	}
	
	/**
	 * @apiNote Permet d'accéder à un joueur
	 * depuis la vue
	 * @param order: Numéro du joueur
	 * @return le joueur spécifié
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public Player getPlayer(int order) throws Player.InvalidOrder {
		if(Player.isPlayerOrder(order)) {
			return this.players.get(order);
		}
		throw new Player.InvalidOrder(order);
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
		throw new Player.InvalidOrder(order);
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
		throw new Player.InvalidOrder(order);
	}
	
	/**
	 * @apiNote Vérifie si le joueur spécifié peut
	 * assécher une zone
	 * @param order: Numéro du joueur
	 * @param move: Cible à assécher par rapport
	 * à la zone du joueur (peut être sa propre zone)
	 * @return true si la zone peut être asséchée false sinon
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public boolean canDryPlayer(int order, Move move) throws Player.InvalidOrder {
		if(Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			return player.canDry(move);
		}
		throw new Player.InvalidOrder(order);
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
		throw new Player.InvalidOrder(order);
	}
	
	/**
	 * @apiNote Inonde 3 zones aléatoirement
	 * Si un joueur est piégé il est automatiquement tué
	 * @return Liste des joueurs sur les zones
	 * concernées et qui vont être submergées
	 */
	public ArrayList<Player> floodRandom() {
		ArrayList<Player> res = new ArrayList<Player>();
		for(int i = 0; i < 3; i++) {
			int x = IslandModel.rand.nextInt(this.WIDTH);
			int y = IslandModel.rand.nextInt(this.HEIGHT);
			// Inondation de la zone
			this.zones[x][y].flood();
			// Si des joueurs sont sur cette zone
			for (Player player : this.players) {
				if(x == player.position.x && y == player.position.y 
						&& player.position.isSubmergeable()) {
					// Ajout du joueur à la liste des joueurs à déplacer
					if (player.canEscape()) {
						res.add(player);
					}
					// Joueur tué car il ne peut pas s'échapper
					else {
						player.kill();;
					}
				}
			}
		}
		super.notifyObservers();
		return res;
	}
	
	/**
	 * @apiNote Vérifie si le joueur peut rechercher
	 * une clé
	 * @param order: Numéro du joueur
	 * @return true si la clé est recherchable false sinon
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public boolean canFindKeyElementPlayer(int order) throws Player.InvalidOrder {
		if (Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			return player.canFindKeyElement();
		}
		throw new Player.InvalidOrder(order);
	}
	
	/**
	 * @apiNote Le joueur spécifié cherche une clé
	 * Cela peut mener à l'évènement spécial
	 * de la montée des eaux
	 * @param order: Numéro du joueur
	 * @return true si la clé a été trouvée false sino
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public boolean findKeyElementPlayer(int order) throws Player.InvalidOrder {
		if (Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			boolean res = player.findKeyElement();
			// Évènement spécial : Montée des eaux
			if (! res) {
				player.position.flood();
				// Joueur tué s'il n'a pas trouvé de clé et qu'il est piégé
				if (! player.canEscape()) {
					player.kill();
				}
			}
			return res;
		}
		throw new Player.InvalidOrder(order);
	}
	
	/**
	 * @apiNote Vérifie si le joueur peut trouver un artefact
	 * @param order: Numéro du joueur
	 * @return true si un artefact peut être trouvé false sinon
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public boolean canFindArtefactPlayer(int order) throws Player.InvalidOrder {
		if (Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			return player.canFindArtefact();
		}
		throw new Player.InvalidOrder(order);
	}
	
	/**
	 * @apiNote Le joueur spécifié cherche un artefact
	 * @param order: Numéro du joueur
	 * @return true si l'artefact a été trouvé false sinon
	 * @throws Player.InvalidOrder: Si le joueur
	 * n'existe pas
	 */
	public boolean findArtefactPlayer(int order) throws Player.InvalidOrder {
		if (Player.isPlayerOrder(order)) {
			Player player = players.get(order);
			return player.findArtefact();
		}
		throw new Player.InvalidOrder(order);
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
