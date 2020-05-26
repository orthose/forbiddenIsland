package model;

/**
 * @author maxime
 * @apiNote Une zone est une composante de l'île
 */
public class Zone {
	private IslandModel m;
	private WaterLevel wl;
	private NaturalElement el;
	private boolean heliport;
	public final int x, y;
	
	/**
	 * @apiNote Instancie une zone normale (asséchée)
	 * @param m: Référence au modèle de l'île
	 * @param x: Coordonnée en x
	 * @param y: Coordonnée en y
	 */
	public Zone(IslandModel m, int x, int y) {
		this.m = m;
		this.x = x;
		this.y = y;
		this.wl = new NormalLevel();
		this.el = NaturalElement.NONE;
		this.heliport = false;
	}
	
	/**
	 * @apiNote Instancie une zone selon un symbole
	 * @param m: Référence au modèle de l'île
	 * @param symbol: Symbole se référer à StringMap
	 * @param x: Coordonnée en x
	 * @param y: Coordonnée en y
	 */
	public Zone(IslandModel m, String symbol, int x, int y) {
		this(m, x, y);
		// Token potentiellement en 2 parties
		String mean[] = new String[2]; 
		mean[0] = StringMap.decode(symbol);
		// Partie 1 du token
		if(mean[0] != null) {
			mean = mean[0].split("&");
			switch(mean[0]) {
				case "NormalLevel": this.wl = new NormalLevel(); break;
				case "FloodedLevel": this.wl = new FloodedLevel(); break;
				case "SubmergedLevel": this.wl = new SubmergedLevel(); break;
				case "heliport": this.heliport = true; break;
				case "Player": break;
				default:
					for(NaturalElement el : NaturalElement.values()) {
						if(mean[0].equals(el.name())) {
							this.el = el;
						}
					} break;
			}
		}
		// Symbole non-reconnu
		else {
			throw new IllegalArgumentException("Incorrect symbol in Zone() constructor");
		}
		
		// Partie 2 du token
		if(mean.length > 1 && mean[1] != null) {
			switch(mean[1]) {
				case "FloodedLevel": this.wl = new FloodedLevel();
			}
		}
	}
	
	/**
	 * @return Élément de la zone
	 */
	public NaturalElement getNaturalElement() {
		return this.el;
	}
	
	/**
	 * @return true si zone héliport false sinon
	 */
	public boolean isHeliport() {
		return this.heliport;
	}
	
	/**
	 * @apiNote Modifie l'élément associé à la zone
	 * @param el: Élément naturel à associer
	 */
	public void setNaturalElement(NaturalElement el) {
		this.el = el;
	}
	
	/**
	 * @apiNote Transforme la zone en zone héliport
	 * @param heliport: true si zone héliport false sinon
	 */
	public void setHeliport(boolean heliport) {
		this.heliport = heliport;
	}
	
	/**
	 * @apiNote Inonde la zone
	 */
	public void flood() {
		this.wl = this.wl.increase();
	}
	
	/**
	 * @apiNote Assèche la zone
	 */
	public void dry() {
		this.wl = this.wl.reduce();
	}
	
	/**
	 * @apiNote Vérifie si la zone est traversable
	 * @return true si traversable false sinon
	 */
	public boolean isCrossable() {
		return this.wl.isSafe();
	}
	
	/**
	 * @apiNote Vérifie que le niveau d'eau
	 * de la zone est normal
	 * @return true si niveau normal false sinon
	 */
	public boolean isNormalLevel() {
		return this.wl.isNormalLevel();
	}
	
	/**
	 * @apiNote Vérifie que le niveau d'eau
	 * de la zone est inondé
	 * @return true si niveau inondé false sinon
	 */
	public boolean isFloodedLevel() {
		return this.wl.isFloodedLevel();
	}
	
	/**
	 * @apiNote Vérifie que le niveau d'eau
	 * de la zone est submergé
	 * @return true si niveau submergé false sinon
	 */
	public boolean isSubmergedLevel() {
		return this.wl.isSubmergedLevel();
	}
	
	/**
	 * @apiNote Vérifie si la zone est submergable
	 * (elle passe à l'état submergé lors du prochain
	 * appel Zone::flood())
	 * @return true si submergable false sinon
	 */
	public boolean isSubmergeable() {
		return this.wl.isSubmergeable();
	}
	
	/**
	 * @apiNote Renvoie la zone voisine
	 * de la zone courante
	 * Si le déplacement n'est pas possible
	 * c'est la zone courante qui est renvoyée
	 * @param move: Déplacement pour aller
	 * sur la zone voisine
	 * @param success: true si déplacement réussi
	 * false sinon
	 * @return zone voisine ou zone courante
	 */
	public Zone neighbour(Move move, Boolean success) {
		switch(move) {
		case UP: 
			if(this.y > 0) {
				success = true;
				return m.zones[this.x][this.y - 1];
			}
		case DOWN:
			if(this.y < this.m.HEIGHT - 1) {
				success = true;
				return m.zones[this.x][this.y + 1];
			}
		case RIGHT:
			if(this.x < this.m.WIDTH - 1) {
				success = true;
				return m.zones[this.x + 1][this.y];
			}
		case LEFT:
			if(this.x > 0) {
				success = true;
				return m.zones[this.x - 1][this.y];
			}
		case NONE:
			success = true;
			return this;
		default:
			success = false;
			return this;
		}
	}
	
	/**
	 * @apiNote Surcharge si l'on ne souhaite pas
	 * savoir si le déplacement est valide
	 */
	public Zone neighbour(Move move) {
		Boolean success = Boolean.valueOf(true);
		return this.neighbour(move, success);
	}
	
	@Override
	public String toString() {
		String wlString = this.wl.toString();
		if (wlString != StringMap.encode("SubmergedLevel")) {
			/*if(! this.players.isEmpty()) {
				if(wlString.equals(StringMap.encode("FloodedLevel"))) {
					return StringMap.encode("Player&FloodedLevel");
				}
				return StringMap.encode("Player");
			}*/
			if(this.heliport) {
				if(wlString.equals(StringMap.encode("FloodedLevel"))) {
					return StringMap.encode("heliport&FloodedLevel");
				}
				return StringMap.encode("heliport");
			}
			else if(this.el != NaturalElement.NONE) {
				if(wlString.equals(StringMap.encode("FloodedLevel"))) {
					return StringMap.encode(this.el.name() + "&FloodedLevel");
				}
				return this.el.toString();
			}
		}
		return wlString;
	}
	
	/**
	 * @author maxime
	 * @apiNote Niveau de l'eau de la zone
	 */
	public abstract class WaterLevel {
		
		/**
		 * @apiNote Augmente le niveau de l'eau
		 * @return Nouveau niveau de l'eau
		 */
		public abstract WaterLevel increase();
		
		/**
		 * @apiNote Réduit le niveau de l'eau
		 * @return Nouveau niveau de l'eau
		 */
		public abstract WaterLevel reduce();
		
		/**
		 * @apiNote Vérifie que le niveau d'eau
		 * de la zone est normal
		 * @return true si niveau normal false sinon
		 */
		public abstract boolean isNormalLevel();
		
		/**
		 * @apiNote Vérifie que le niveau d'eau
		 * de la zone est inondé
		 * @return true si niveau inondé false sinon
		 */
		public abstract boolean isFloodedLevel();
		
		/**
		 * @apiNote Vérifie que le niveau d'eau
		 * de la zone est submergé
		 * @return true si niveau submergé false sinon
		 */
		public abstract boolean isSubmergedLevel();
		
		/**
		 * @apiNote Vérifie que le niveau de l'eau est viable
		 * @return true si viable false sinon
		 */
		public boolean isSafe() {
			return this.isNormalLevel() || this.isFloodedLevel();
		}
		
		/**
		 * @apiNote Vérifie si la zone est submergable
		 * (elle passe à l'état submergé lors du prochain
		 * appel Zone::flood())
		 * @return true si submergable false sinon
		 */
		public boolean isSubmergeable() {
			return this.isFloodedLevel() || this.isSubmergedLevel();
		}
	}
	
	/**
	 * @author maxime
	 * @apiNote Niveau normal (zone asséchée)
	 */
	public class NormalLevel extends WaterLevel {
		
		public WaterLevel increase() {
			return new FloodedLevel();
		}
		
		public WaterLevel reduce() {
			return this;
		}
		
		public boolean isNormalLevel() {
			return true;
		}
		
		public boolean isFloodedLevel() {
			return false;
		}
		
		public boolean isSubmergedLevel() {
			return false;
		}
		
		@Override
		public String toString() {
			return StringMap.encode("NormalLevel");
		}
	}
	
	/**
	 * @author maxime
	 * @apiNote Niveau inondé (en sursis)
	 */
	public class FloodedLevel extends WaterLevel {
		
		public WaterLevel increase() {
			return new SubmergedLevel();
		}
		
		public WaterLevel reduce() {
			return new NormalLevel();
		}
		
		public boolean isNormalLevel() {
			return false;
		}
		
		public boolean isFloodedLevel() {
			return true;
		}
		
		public boolean isSubmergedLevel() {
			return false;
		}
		
		@Override
		public String toString() {
			return StringMap.encode("FloodedLevel");
		}
	}
	
	/**
	 * @author maxime
	 * @apiNote Niveau submergé (définitif)
	 */
	public class SubmergedLevel extends WaterLevel {
		
		public WaterLevel increase() {
			return this;
		}
		
		public WaterLevel reduce() {
			return this;
		}
		
		public boolean isNormalLevel() {
			return false;
		}
		
		public boolean isFloodedLevel() {
			return false;
		}
		
		public boolean isSubmergedLevel() {
			return true;
		}
		
		@Override
		public String toString() {
			return StringMap.encode("SubmergedLevel");
		}
	}
}
