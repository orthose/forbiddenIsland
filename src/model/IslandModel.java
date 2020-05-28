package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import model.Player.InvalidPlayerId;
import util.Observable;

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
	private int turn;
	protected boolean verbose = false;
	private HashSet<Player> playersToSave;

	/**
	 * @apiNote Crée une île selon une carte Si une des lignes est de taille
	 *          différente de la première ligne les lignes suivantes ne sont pas
	 *          chargées
	 * @param map: Carte initiale qui sera chargée Les symboles sont détaillés dans
	 *             StringMap
	 */
	public IslandModel(String map) {
		super();
		// Liste des joueurs
		this.players = new ArrayList<Player>();
		this.playersToSave = new HashSet<Player>();
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
	 * @apiNote Permet d'enclencher les affichages
	 * console. Par défaut, les affichages sont
	 * désactivés.
	 * @param verbose: booléen à true pour activer
	 * et false pour désactiver.
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * @apiNote Donne le tour en cours
	 * @return Entier à partir de 0 pour le premier tour
	 */
	public int getTurn() {
		return this.turn;
	}
	
	/**
	 * @apiNote Donne les joueurs à déplacer à la toute fin du tour
	 * lorsque IslandModel::floodRandom() a été appelée
	 * @return Liste des joueurs à déplacer pour les sauver
	 * de la noyade
	 */
	public ArrayList<Player> getPlayersToSave() {
		return new ArrayList<Player>(this.playersToSave);
	}

	/**
	 * @apiNote Donne l'identifiant du prochain joueur à jouer tout en modifiant en
	 *          interne l'identifiant du joueur courant et les voisins de celui-ci
	 * @return L'identifiant du joueur qui doit à présent jouer
	 */
	public int nextIdPlayer() {
		// Initialisation du premier joueur
		if (turn == 0) {
			this.currentIdPlayer = IslandModel.rand.nextInt(this.players.size());
			this.turn++;
			if (verbose) {
				System.out.println("id="+this.currentIdPlayer);
				System.out.println("turn="+this.turn);
				try {
					Player player = this.getPlayer(this.currentIdPlayer);
					System.out.println(player+" reste "+player.getNbAction()+" action(s)");
				}
				catch (InvalidPlayerId e) {
					e.printStackTrace();
					System.out.println("Error on player Id");
				}
				System.out.println(this+"\n");
			}
			return this.currentIdPlayer;
		}
		try {
			Player player = this.getPlayer(this.currentIdPlayer);
			// Joueur tué s'il ne s'est pas enfui d'une zone submergée
			if (player.position.isSubmergedLevel()) {
				player.kill();
				super.notifyObservers();
			}
			// On redonne ses actions au joueur courant
			else {
				player.nbAction = Player.nbActionMax;
			}
		}
		// Normalement impossible
		catch (InvalidPlayerId e) {
			e.printStackTrace();
			System.out.println("Error on player Id");
		}
		this.currentIdPlayer = (this.currentIdPlayer + 1) % this.players.size();
		this.turn++;
		if (verbose) {
			System.out.println("id="+this.currentIdPlayer);
			System.out.println("turn="+this.turn);
			try {
				Player player = this.getPlayer(this.currentIdPlayer);
				System.out.println(player+" reste "+player.getNbAction()+" action(s)");
			}
			catch (InvalidPlayerId e) {
				e.printStackTrace();
				System.out.println("Error on player Id");
			}
			System.out.println(this+"\n");
		}
		// Suppression des joueurs à sauver
		this.playersToSave = new HashSet<Player>(this.players.size());
		return this.currentIdPlayer;
	}

	/**
	 * @apiNote Permet d'accéder à une zone depuis la vue
	 * @param x: Coordonnée en x
	 * @param y: Coordonnée en y
	 * @return zone aux coordonnées spécifiées
	 */
	public Zone getZone(int x, int y) {
		return this.zones[x][y];
	}

	/**
	 * @apiNote Renvoie le nombre de joueur
	 * @return le nombre de joueur
	 */
	public int getNbPlayer() {
		return players.size();
	}

	/**
	 * @apiNote Permet d'accéder à un joueur depuis la vue
	 * @param id: Numéro du joueur
	 * @return le joueur spécifié
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public Player getPlayer(int id) throws Player.InvalidPlayerId {
		if (Player.isPlayerId(id)) {
			return this.players.get(id);
		}
		throw new Player.InvalidPlayerId(id);
	}

	/**
	 * @apiNote Donne la position du joueur spécifié
	 * @param id: Identifiant du joueur
	 * @return La zone sur laquelle est le joueur
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public Zone getPositionPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.position;
	}
	
	/**
	 * @apiNote Donne les zones des déplacements possibles
	 * pour le joueur courant sans recalculer à chaque appel
	 * @return La liste des zones de déplacements possibles
	 */
	public ArrayList<Zone> getMovePossibilitiesCurrentPlayer(){
		try {
			Player player = this.getPlayer(this.currentIdPlayer);
			return player.movePossibilities;
		}
		// Normalement impossible
		catch (InvalidPlayerId e) {
			e.printStackTrace();
			System.out.println("Error on player Id");
		}
		// Normalement inaccessible
		return null;
	}
	
	/**
	 * @apiNote Ajoute un joueur à la partie
	 * @param player: Joueur à ajouter
	 */
	protected void addPlayer(Player player) {
		this.players.add(player);
		if (verbose) System.out.println(player+" ajouté");
		super.notifyObservers();
	}

	/**
	 * @apiNote Vérifie que le joueur spécifié peut s'échapper sur l'une des cases
	 *          adjacentes
	 * @param id: Identifiant du joueur
	 * @return true s'il peut d'échapper false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean canEscapePlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canEscape();
	}

	/**
	 * @apiNote Permet de déplacer un joueur
	 * @param id:   Numéro du joueur
	 * @param move: Déplacement souhaité
	 * @return true si déplacement valide false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean movePlayer(int id, Move move) throws Player.InvalidPlayerId {	
		Player player = this.getPlayer(id);
		// Cas particulier du plongeur
		// pouvant sauter les cases submergées
		if (player instanceof Diver) {
			boolean res = ((Diver)player).move(move);
			if (res) {
				super.notifyObservers();
			}
			return res;
		}
		MutableBoolean success = new MutableBoolean(true);
		Zone newPosition = player.position.neighbour(move, success);
		if (newPosition.isCrossable()) {
			player.move(newPosition);
		} else {
			success.value = false;
		}
		if (success.value) {
			super.notifyObservers();
		}
		return success.value;
	}
	
	/**
	 * @apiNote Surcharge de la méthode plus souple
	 * @param id: Identifiant du joueur
	 * @param zone: Zone sur laquelle déplacer le joueur
	 * @return true si déplacement réussi false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean movePlayer(int id, Zone zone) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		boolean res = player.movePossibilities.contains(zone);
		if (res) {
			player.move(zone);
			super.notifyObservers();
		}
		return res;
	}

	/**
	 * @apiNote Donne les possibilités de déplacements pour le joueur spécifié
	 * @param id: Numéro du joueur
	 * @return Liste des déplacements possibles
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public ArrayList<Zone> movePossibilitiesPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return new ArrayList<Zone>(player.movePossibilities());
	}

	/**
	 * @apiNote Vérifie si le joueur spécifié peut assécher une zone
	 * @param id:   Numéro du joueur
	 * @param move: Cible à assécher par rapport à la zone du joueur (peut être sa
	 *              propre zone)
	 * @return true si la zone peut être asséchée false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean canDryPlayer(int id, Move move) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canDry(move);
	}

	/**
	 * @apiNote Le joueur spécifié assèche une zone
	 * @param id:   Numéro du joueur
	 * @param move: Cible à assécher par rapport à la zone du joueur (peut être sa
	 *              propre zone)
	 * @return true si la zone a été asséchée false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean dryPlayer(int id, Move move) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		boolean success = player.dry(move);
		if (success) {
			super.notifyObservers();
		}
		return success;
	}

	/**
	 * @apiNote Inonde 3 zones aléatoirement Si un joueur est piégé il est
	 *          automatiquement tué
	 */
	public void floodRandom() {
		ArrayList<Player> res = new ArrayList<Player>();
		for (int i = 0; i < 3; i++) {
			int x = IslandModel.rand.nextInt(this.WIDTH);
			int y = IslandModel.rand.nextInt(this.HEIGHT);
			// Inondation de la zone
			this.zones[x][y].flood();
			if (verbose) {
				System.out.println("Zone inondée en ("+x+", "+y+")");
			}
			// Si des joueurs sont sur cette zone
			for (Player player : this.players) {
				if (x == player.position.x && y == player.position.y && player.position.isSubmergeable()) {
					// Ajout du joueur à la liste des joueurs à sauver
					if (player.canEscape()) {
						this.playersToSave.add(player);
						if (verbose) {
							System.out.println(player+" doit s'échapper !");
						}
					}
					// Joueur tué car il ne peut pas s'échapper
					else {
						player.kill();
					}
				}
			}
		}
		super.notifyObservers();
		if (verbose) System.out.print("\n");
	}

	/**
	 * @apiNote Vérifie si le joueur peut rechercher une clé
	 * @param id: Numéro du joueur
	 * @return true si la clé est recherchable false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean canFindKeyElementPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canFindKeyElement();
	}

	/**
	 * @apiNote Le joueur spécifié cherche une clé Cela peut mener à l'évènement
	 *          spécial de la montée des eaux
	 * @param id: Numéro du joueur
	 * @return true si la clé a été trouvée false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean findKeyElementPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		boolean res = player.findKeyElement();
		// Évènement spécial : Montée des eaux
		// seulement si la clé n'a pas été trouvée
		// et qu'une clé pouvait être trouvée
		if (!res && player.canFindKeyElement()) {
			player.position.flood();
			// Le joueur peut être sauvé
			if (player.position.isSubmergedLevel() && player.canEscape()) {
				this.playersToSave.add(player);
				if (verbose) {
					System.out.println(player+" doit s'échapper !");
				}
			}
			// Joueur tué s'il n'a pas trouvé de clé et qu'il est piégé
			else if (player.position.isSubmergedLevel() && !player.canEscape()) {
				player.kill();
				super.notifyObservers();
			}
		}
		return res;
	}

	/**
	 * @apiNote Vérifie si le joueur peut trouver un artefact
	 * @param id: Numéro du joueur
	 * @return true si un artefact peut être trouvé false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean canFindArtefactPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.canFindArtefact();
	}

	/**
	 * @apiNote Le joueur spécifié cherche un artefact
	 * @param id: Numéro du joueur
	 * @return true si l'artefact a été trouvé false sinon
	 * @throws Player.InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean findArtefactPlayer(int id) throws Player.InvalidPlayerId {
		Player player = this.getPlayer(id);
		return player.findArtefact();
	}

	/**
	 * @apiNote Vérifie si le jeu est gagné
	 * @return true si gagné false sinon
	 */
	public boolean gameIsWon() {
		boolean firstCondition = true;
		// Tous les joueurs sont vivants et sur un héliport
		// pas forcément le même héliport
		for (Player player : this.players) {
			firstCondition = firstCondition && player.isAlive();
			firstCondition = firstCondition && player.position.isHeliport();
			if (!firstCondition) {
				break;
			}
		}
		// Tous les artefacts ont été trouvés
		boolean res = firstCondition && Artefact.allArtefactsFound();
		if (res && verbose) System.out.println("Le jeu est gagné !");
		return res;
	}

	/**
	 * @apiNote Vérifie si le jeu est perdu
	 * @return true si perdu false sinon
	 */
	public boolean gameIsLost() {
		boolean condition = false;
		// L'un des joueurs est mort ?
		for (Player player : this.players) {
			condition = condition || !player.isAlive();
			if (condition) {
				break;
			}
		}
		// Tous les héliports sont inaccessibles ?
		if (!condition) {
			int heliportZones = 0, heliportZonesSubmerged = 0;
			for (int i = 0; i < this.WIDTH; i++) {
				for (int j = 0; j < this.HEIGHT; j++) {
					if (this.zones[i][j].isHeliport()) {
						heliportZones++;
						if (this.zones[i][j].isSubmergedLevel()) {
							heliportZonesSubmerged++;
						}
					}
				}
			}
			condition = (heliportZones == heliportZonesSubmerged);
		}
		// Toutes les zones d'un artefact non-trouvé
		// sont submergées ?
		if (!condition) {
			boolean airArtefactFound = Artefact.isFound(NaturalElement.AIR);
			boolean waterArtefactFound = Artefact.isFound(NaturalElement.WATER);
			boolean earthArtefactFound = Artefact.isFound(NaturalElement.EARTH);
			boolean fireArtefactFound = Artefact.isFound(NaturalElement.FIRE);
			int airZones = 0, airZonesSubmerged = 0;
			int waterZones = 0, waterZonesSubmerged = 0;
			int earthZones = 0, earthZonesSubmerged = 0;
			int fireZones = 0, fireZonesSubmerged = 0;
			for (int i = 0; i < this.WIDTH; i++) {
				for (int j = 0; j < this.HEIGHT; j++) {
					NaturalElement zoneElement = this.zones[i][j].getNaturalElement();
					boolean submerged = this.zones[i][j].isSubmergedLevel();
					if (zoneElement == NaturalElement.AIR) {
						airZones++;
						if (submerged) {
							airZonesSubmerged++;
						}
					}
					if (zoneElement == NaturalElement.WATER) {
						waterZones++;
						if (submerged) {
							waterZonesSubmerged++;
						}
					}
					if (zoneElement == NaturalElement.EARTH) {
						earthZones++;
						if (submerged) {
							earthZonesSubmerged++;
						}
					}
					if (zoneElement == NaturalElement.FIRE) {
						fireZones++;
						if (submerged) {
							fireZonesSubmerged++;
						}
					}
				}
			}
			condition = (!airArtefactFound && airZones == airZonesSubmerged) || (!waterArtefactFound && waterZones == waterZonesSubmerged) || (!earthArtefactFound && earthZones == earthZonesSubmerged)
					|| (!fireArtefactFound && fireZones == fireZonesSubmerged);
		}
		if (condition && verbose) System.out.println("Le jeu est perdu !");
		return condition;
	}

	@Override
	public String toString() {
		String res = "";
		// Parcours des zones de l'île
		for (int j = 0; j < this.HEIGHT; j++) {
			for (int i = 0; i < this.WIDTH; i++) {
				res += this.zones[i][j].toString();
			}
			if (j != this.HEIGHT - 1) {
				res += "\n";
			}
		}
		// Buffer pour modifier un caractère à un index
		StringBuffer buffer = new StringBuffer(res);
		// Parcours des joueurs
		for (Player player : this.players) {
			int x = player.position.x;
			int y = player.position.y;
			// Niveau d'eau normal
			if (player.position.isNormalLevel()) {
				buffer.setCharAt((this.WIDTH + 1) * y + x, StringMap.encode("Player").charAt(0));
			}
			// Niveau d'eau inondé
			if (player.position.isFloodedLevel()) {
				buffer.setCharAt((this.WIDTH + 1) * y + x, StringMap.encode("Player&FloodedLevel").charAt(0));
			}
			// Dans le cas de la zone submergée le joueur n'est pas affiché
			// car de toute manière il se noie
		}
		return buffer.toString();
	}

	/**
	 * @apiNote Méthode à appeler avant de recommencer une partie
	 */
	public static void reset() {
		Player.reset();
		Artefact.reset();
	}

}
