package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import model.Player.InvalidPlayerId;

/**
 * @author maxime
 * @apiNote Modèle de l'île le plateau de jeu
 */
public class IslandModel extends Observable {
	public static final Random rand = new Random();
	public final int WIDTH, HEIGHT; 
	protected Zone[][] zones;
	private ArrayList<Player> players;
	private int currentIdPlayer;
	private int firstIdPlayer;
	private int turn;
	
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
		// Liste des joueurs
		this.players = new ArrayList<Player>();
		this.turn = 0;
		// Découpe des lignes
		String[] mapLine = map.split("\n");
		this.WIDTH = mapLine[0].length();
		this.HEIGHT = mapLine.length;
		this.zones = new Zone[this.WIDTH][this.HEIGHT];
		// Chargement des zones
		for (int j = 0; j < mapLine.length; j++) {
			String line = mapLine[j];
			int currentWidth = line.length();
			if (currentWidth != this.WIDTH) { 
				throw new IllegalArgumentException("Incorrect length line in IslandModel() constructor");
			}
			for (int i = 0; i < currentWidth; i++) {
				this.zones[i][j] = new Zone(this, String.valueOf(line.charAt(i)), i, j);
			}
		}
	}
	
	/**
	 * @apiNote Donne le tour en cours
	 * @return Entier à partir de 0
	 * pour le premier tour
	 */
	public int getTurn() {
		return this.turn;
	}
	
	/**
	 * @apiNote Donne l'identifiant du prochain
	 * joueur à jouer tout en modifiant en interne
	 * l'identifiant du joueur courant
	 * @return L'identifiant du joueur qui doit
	 * à présent jouer
	 */
	public int nextIdPlayer() {
		// Initialisation du premier joueur
		if (turn == 0) {
			this.firstIdPlayer = IslandModel.rand.nextInt(this.players.size());
		}
		this.currentIdPlayer = (turn + this.firstIdPlayer) % this.players.size();
		this.turn++;
		return this.currentIdPlayer;
	}
	
	/**
	 * @apiNote Donne l'identifiant du joueur
	 * qui est en train de joueur
	 * @return L'identifiant du joueur courant
	 */
	public int getCurrentIdPlayer() {
		return this.currentIdPlayer;
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
	 * @param id: Numéro du joueur
	 * @return le joueur spécifié
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public Player getPlayer(int id) throws Player.InvalidPlayerId {
		if(Player.isPlayerId(id)) {
			return this.players.get(id);
		}
		throw new Player.InvalidPlayerId(id);
	}
	
	/**
	 * @apiNote Donne la position du joueur spécifié
	 * @param id: Identifiant du joueur
	 * @return La zone sur laquelle est le joueur
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public Zone getPositionPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.position;
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
	 * @apiNote Vérifie que le joueur spécifié
	 * peut s'échapper sur l'une des cases adjacentes
	 * @param id: Identifiant du joueur
	 * @return true s'il peut d'échapper false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean canEscapePlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canEscape();
	}
	
	/**
	 * @apiNote Permet de déplacer un joueur
	 * @param id: Numéro du joueur
	 * @param move: Déplacement souhaité
	 * @return true si déplacement valide false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean movePlayer(int id, Move move) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		Boolean success = Boolean.valueOf(true);
		Zone newPosition = player.position.neighbour(move, success);
		if(newPosition.isCrossable()) {
			player.move(newPosition);
		}
		else {
			success = false;
		}
		if(success) { super.notifyObservers(); }
		return success;
	}
	
	/**
	 * @apiNote Donne les possibilités de déplacements
	 * pour le joueur spécifié
	 * @param id: Numéro du joueur
	 * @return Liste des déplacements possibles
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public ArrayList<Zone> movePossibilitiesPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return new ArrayList<Zone>(player.movePossibilities());
	}
	
	/**
	 * @apiNote Vérifie si le joueur spécifié peut
	 * assécher une zone
	 * @param id: Numéro du joueur
	 * @param move: Cible à assécher par rapport
	 * à la zone du joueur (peut être sa propre zone)
	 * @return true si la zone peut être asséchée false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean canDryPlayer(int id, Move move) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canDry(move);
	}
	
	/**
	 * @apiNote Le joueur spécifié assèche une zone
	 * @param id: Numéro du joueur
	 * @param move: Cible à assécher par rapport
	 * à la zone du joueur (peut être sa propre zone)
	 * @return true si la zone a été asséchée false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean dryPlayer(int id, Move move) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		boolean success = player.dry(move);
		super.notifyObservers();
		return success;
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
	 * @param id: Numéro du joueur
	 * @return true si la clé est recherchable false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean canFindKeyElementPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canFindKeyElement();
	}
	
	/**
	 * @apiNote Le joueur spécifié cherche une clé
	 * Cela peut mener à l'évènement spécial
	 * de la montée des eaux
	 * @param id: Numéro du joueur
	 * @return true si la clé a été trouvée false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean findKeyElementPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
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
	
	/**
	 * @apiNote Vérifie si le joueur peut trouver un artefact
	 * @param id: Numéro du joueur
	 * @return true si un artefact peut être trouvé false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean canFindArtefactPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canFindArtefact();
	}
	
	/**
	 * @apiNote Le joueur spécifié cherche un artefact
	 * @param id: Numéro du joueur
	 * @return true si l'artefact a été trouvé false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur
	 * n'existe pas
	 */
	public boolean findArtefactPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.findArtefact();
	}
	
	@Override
	public String toString() {
		String res = "";
		for(int j = 0; j < this.HEIGHT; j++) {
			for(int i = 0; i < this.WIDTH; i++) {
				res += this.zones[i][j].toString();
			}
			if(j != this.HEIGHT - 1) { res += "\n"; }
		}
		return res;
	}
	
	/**
	 * @apiNote Méthode à appeler avant de
	 * recommencer une partie
	 */
	public static void reset() {
		Player.reset();
		Artefact.reset();
	}

}
