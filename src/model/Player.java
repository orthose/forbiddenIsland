package model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author maxime
 * @apiNote Joueur et ses actions
 */
public class Player {
	private IslandModel m;
	private String name;
	private int id;
	protected Zone position;
	private boolean alive;
	private ArrayList<KeyElement> keys;
	private static ArrayList<Integer> allPlayersId =
		new ArrayList<Integer>();
	
	/**
	 * @apiNote L'identifiant du premier joueur
	 * est 0 et chaque nouveau joueur
	 * aura l'identifiant id+1
	 * @param m: Référence au modèle qui permet
	 * l'ajout direct du joueur au modèle
	 * @param name: Nom du joueur
	 * @param position: zone de l'actuelle position
	 * du joueur
	 * @throws InvalidPlayerId: Si l'identifiant est déjà
	 * enregistré ou que le premier joueur n'a pas l'identifiant 0
	 */
	public Player(IslandModel m, String name, Zone position) {
		// Premier joueur
		if (Player.allPlayersId.isEmpty()) {
			this.id = 0;
			Player.allPlayersId.add(Integer.valueOf(0));
		}
		// Joueurs suivants
		else {
			Integer lastId = Player.allPlayersId.get(Player.allPlayersId.size() - 1);
			this.id = lastId + 1;
			Player.allPlayersId.add(Integer.valueOf(this.id));
		}
		this.name = name;
		this.position = position;
		this.position.addPlayer(this);
		this.alive = true;
		this.m = m;
		this.m.addPlayer(this);
		this.keys = new ArrayList<KeyElement>();
	}
	
	// Exception si le numéro de joueur est invalide
	public static class InvalidPlayerId extends Exception {
		private int id;
		
		public InvalidPlayerId(int id) {
			super();
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}
		
		@Override
		public String toString() {
			return "Invalid Player id for id="+this.id;
		}
		
	}
	
	/**
	 * @return Nom du joueur
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return Identifiant du joueur
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * @apiNote Vérifie que le numéro de joueur
	 * est bien enregistré
	 * @param id: Numéro de joueur
	 * @return true si ordre valide false sinon
	 */
	public static boolean isPlayerId(int id) {
		return Player.allPlayersId.contains(Integer.valueOf(id));
	}
	
	/**
	 * @apiNote Vérifie que le joueur est en vie
	 * @return true si en vie false sinon
	 */
	public boolean isAlive() {
		return this.alive;
	}
	
	/**
	 * @apiNote Tue le joueur de manière
	 * irréversible
	 */
	public void kill() {
		this.alive = false;
	}
	
	/**
	 * @apiNote Déplace le joueur vers
	 * une zone sans vérification
	 * (permet le téléport)
	 * @param newPosition: nouvelle position
	 * du joueur
	 */
	public void move(Zone newPosition) {
		if (this.alive) {
			this.position.removePlayer(this);
			this.position = newPosition;
			this.position.addPlayer(this);
		}
	}
	
	/**
	 * @apiNote Donne les possibilités de déplacement
	 * du joueur
	 * @return Liste des déplacements possibles
	 */
	public HashSet<Zone> movePossibilities() {
		HashSet<Zone> res = new HashSet<Zone>();
		if (this.alive) {
			Zone up = this.position.neighbour(Move.UP);
			Zone down = this.position.neighbour(Move.DOWN);
			Zone right = this.position.neighbour(Move.RIGHT);
			Zone left = this.position.neighbour(Move.LEFT);
			if (!up.isSubmergedLevel()) {
				res.add(up);
			}
			if (!down.isSubmergedLevel()) {
				res.add(down);
			}
			if (!right.isSubmergedLevel()) {
				res.add(right);
			}
			if (!left.isSubmergedLevel()) {
				res.add(left);
			}
			if (!this.position.isSubmergedLevel()) {
				res.add(this.position);
			}
		}
		return res;
	}
	
	/**
	 * @apiNote Vérifie que le joueur peut s'enfuir
	 * de sa position actuelle
	 * @return true s'il peut s'échapper false sinon
	 */
	public boolean canEscape() {
		ArrayList<Zone> escapes = new ArrayList<>(this.movePossibilities());
		return ! escapes.isEmpty();
	}
	
	/**
	 * @apiNote Vérifie si le joueur peut assécher une zone
	 * @param move: Zone cible à assécher par rapport
	 * à la zone du joueur (peut être sa propre zone)
	 * @return true si zone asséchable false sinon
	 */
	public boolean canDry(Move move) {
		return this.alive && this.position.neighbour(move).isFloodedLevel();
	}
	
	/**
	 * @apiNote Assèche une zone inondée uniquement
	 * @param move: Zone cible à assécher par rapport
	 * à la zone du joueur (peut être sa propre zone)
	 * @return true si la zone a été asséchée false sinon
	 */
	public boolean dry(Move move) {
		if (this.alive) {
			Zone target = this.position.neighbour(move);
			target.dry();
		}
		return this.canDry(move);
	}
	
	/**
	 * @apiNote Vérifie que le joueur peut
	 * rechercher une clé
	 * @return true s'il peut faire l'action
	 * false sinon
	 */
	public boolean canFindKeyElement() {
		return this.alive;
	}
	
	/**
	 * @apiNote Le joueur recherche une clé
	 * @return true si la clé est trouvée false sinon
	 */
	public boolean findKeyElement() {
		if (this.alive && IslandModel.rand.nextFloat() < this.m.keyLuck) {
			int i = IslandModel.rand.nextInt(4);
			KeyElement key = new KeyElement(NaturalElement.values()[i]);
			this.keys.add(key);
			return true;
		}
		return false;
	}
	
	/**
	 * @apiNote Vérifie si le joueur peut
	 * récupérer un artefact
	 * @return true si action possible false sinon
	 */
	public boolean canFindArtefact() {
		boolean res = false;
		if (! this.keys.isEmpty()) {
			for (KeyElement key : this.keys) {
				res = res || Artefact.canFindNewArtefact(key, this.position);
			}
		}
		return this.alive && res;
	}
	
	/**
	 * @apiNote Tente de rechercher un artefact
	 * @return true si artefact trouvé false sinon
	 */
	public boolean findArtefact() {
		boolean res = false;
		if (this.alive && ! this.keys.isEmpty()) {
			for (KeyElement key : this.keys) {
				res = res || Artefact.findNewArtefact(key, this.position);
			}
		}
		return res;
	}
	
	/**
	 * @apiNote Supprime tous les identifiants
	 * de joueurs
	 */
	public static void reset() {
		Player.allPlayersId = new ArrayList<Integer>();
	}
	
}
