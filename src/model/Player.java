package model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author maxime
 * @apiNote Joueur et ses actions
 * Correspond au rôle par défaut
 */
public class Player {
	protected IslandModel m;
	protected Sexe sexe;
	private String name;
	private int id;
	protected Zone position;
	protected boolean alive;
	private ArrayList<KeyElement> keys;
	private static ArrayList<Integer> allPlayersId =
		new ArrayList<Integer>();
	private float keyLuck = 0.5f; // Dans ]0.0;1.0[
	protected ArrayList<Zone> movePossibilities;
	protected int nbAction;
	public static final int nbActionMax = 3;
	
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
		this.sexe = Sexe.MALE; // Par défaut
		this.position = position;
		this.alive = true;
		this.m = m;
		this.m.addPlayer(this);
		this.keys = new ArrayList<KeyElement>();
		this.movePossibilities = new ArrayList<Zone>(this.movePossibilities());
		this.nbAction = Player.nbActionMax; // 3 actions par tour
	}
	
	/**
	 * @apiNote Constructeur allégé avec les coordonnées
	 * x et y à indiquer
	 * @param m: Référence au modèle
	 * @param name: Nom du joueur
	 * @param x: Coordonnée en x
	 * @param y: Coordonnée en y
	 */
	public Player(IslandModel m, String name, int x, int y) {
		this(m, name, m.getZone(x, y));
	}
	
	/**
	 * @apiNote Constructeur allégé avec les coordonnées
	 * x et y à indiquer et le sexe du joueur
	 * @param m: Référence au modèle
	 * @param name: Nom du joueur
	 * @param x: Coordonnée en x
	 * @param y: Coordonnée en y
	 * @param sexe: Sexe du joueur
	 */
	public Player(IslandModel m, String name, int x, int y, Sexe sexe) {
		this(m, name, x, y);
		this.sexe = sexe;
	}
	
	/**
	 * @apiNote Constructeur allégé qui initialise aléatoirement
	 * la position initiale du joueur (mais elle est valide)
	 * @param m: Référence au modèle
	 * @param name: Nom du joueur
	 */
	public Player(IslandModel m, String name) {
		this(m, name, 0, 0);
		Zone randomZone;
		do {
			int x = IslandModel.rand.nextInt(this.m.WIDTH);
			int y = IslandModel.rand.nextInt(this.m.HEIGHT);
			randomZone = this.m.getZone(x, y);
		} while (! this.validInitialPosition(randomZone));
	}
	
	/**
	 * @apiNote Constructeur allégé avec possibilité
	 * de choisir le sexe
	 * @param m: Référence au modèle
	 * @param name: Nom du joueur
	 * @param sexe: Sexe du joueur
	 */
	public Player(IslandModel m, String name, Sexe sexe) {
		this(m, name);
		this.sexe = sexe;
	}
	
	/**
	 * @apiNote Vérifie que la position initiale du joueur
	 * est valide c'est-à-dire que le joueur n'est pas piégé
	 * et n'atterrit pas sur une zone submergée.
	 * Le joueur est déplacé sur la zone en question.
	 * @param zone: zone sur laquelle est déplacé le joueur
	 * @return true si position valide false sinon
	 */
	private boolean validInitialPosition(Zone zone) {
		this.move(zone);
		// On annule l'action dépensée
		// car on est dans une phase d'initialisation
		this.nbAction++;
		return ! this.position.isSubmergedLevel() && this.canEscape();
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
	 * @apiNote Donne le sexe du joueur
	 * @return Sexe du joueur
	 */
	public Sexe getSexe() {
		return this.sexe;
	}
	
	/**
	 * @apiNote Permet d'obtenir le nombre
	 * d'actions du joueur 
	 * @return Entier correspondant au nombre
	 * d'actions restant
	 */
	public int getNbAction() {
		return this.nbAction;
	}
	
	
	/**
	 * @apiNote Donne les clés trouvées par le joueur
	 * @return Liste des clés
	 */
	public ArrayList<KeyElement> getKeys() {
		return this.keys;
	}
	
	/**
	 * @apiNote Donne la liste des zones de 
	 * déplacements possibles pour le joueur 
	 * sans la recalculer
	 * @return Liste de zones accessibles
	 */
	public ArrayList<Zone> getMovePossibilities() {
		return this.movePossibilities;
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
		if (this.m.verbose) System.out.println(this+" s'est noyé");
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
			this.position = newPosition;
			// Mise à jour de liste des déplacements possibles
			this.movePossibilities = new ArrayList<Zone>(this.movePossibilities());
			// Perd 1 action
			this.nbAction--;
			if (this.m.verbose) {
				System.out.println(this+" déplacé en ("+this.position.x+", "+this.position.y+")");
				System.out.println(this+" reste "+this.getNbAction()+" action(s)");
			}
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
			if (up.isCrossable()) {
				res.add(up);
			}
			if (down.isCrossable()) {
				res.add(down);
			}
			if (right.isCrossable()) {
				res.add(right);
			}
			if (left.isCrossable()) {
				res.add(left);
			}
			if (this.position.isCrossable()) {
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
		return ! (escapes.size() == 1 && escapes.contains(this.position))
			&& escapes.size() > 0;
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
		boolean res = this.canDry(move);
		if (this.alive && res) {
			Zone target = this.position.neighbour(move);
			target.dry();
			// Perd 1 action
			this.nbAction--;
			if (this.m.verbose) {
				System.out.println(this+" a asséché ("+target.x+", "+target.y+")");
				System.out.println(this+" reste "+this.getNbAction()+" action(s)");
			}
		}
		return res;
	}
	
	/**
	 * @apiNote Vérifie que le joueur peut
	 * rechercher une clé
	 * @return true s'il peut faire l'action
	 * false sinon
	 */
	public boolean canFindKeyElement() {
		return this.alive && this.keys.size() < 4;
	}
	
	/**
	 * @apiNote Vérifie si une clé a déjà été
	 * trouvée par le joueur
	 * @param keyFound: Clé à tester
	 * @return true si déjà trouvée false sinon
	 */
	private boolean keyAlreadyFound(KeyElement keyFound) {
		for (KeyElement key : this.keys) {
			if (key.getEl() == keyFound.getEl()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @apiNote Le joueur recherche une clé
	 * @return true si la clé est trouvée false sinon
	 */
	public boolean findKeyElement() {
		if (this.canFindKeyElement() && IslandModel.rand.nextFloat() < this.keyLuck) {
			int i = IslandModel.rand.nextInt(4);
			KeyElement key = new KeyElement(NaturalElement.values()[i]);
			// Si la clé a déjà été trouvée
			while (this.keyAlreadyFound(key)) {
				key = new KeyElement(NaturalElement.values()[++i]);
			}
			this.keys.add(key);
			// Perd 1 action
			this.nbAction--;
			if (this.m.verbose) {
				System.out.println(this+" a trouvé "+key);
				System.out.println(this+" reste "+this.getNbAction()+" action(s)");
			}
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
		// Perd 1 action
		this.nbAction--;
		if (this.m.verbose) {
			System.out.println("Artefacts trouvés:");
			for (Artefact a : Artefact.getFoundArtefacts()) {
				System.out.print(a+" ");
			}
			System.out.print("\n");
			System.out.println(this+" reste "+this.getNbAction()+" action(s)");
		}
		return res;
	}
	
	@Override
	public String toString() {
		return this.name + " id=" + this.id;
	}
	
	/**
	 * @apiNote Méthode pour connaître
	 * le chemin de l'image à utiliser
	 * pour l'affichage graphique
	 * @return chemin d'accès à la ressource
	 */
	public String pathImage() {
		switch (this.sexe) {
		case MALE: return "assets/player/bob.png";
		case FEMALE: return "assets/player/bobette.png";
		}
		return "";
	}
	
	/**
	 * @apiNote Supprime tous les identifiants
	 * de joueurs
	 */
	public static void reset() {
		Player.allPlayersId = new ArrayList<Integer>();
	}
	
}
