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
	
	/**
	 * @apiNote Instancie une zone normale (asséchée)
	 * @param m: Référence au modèle de l'île
	 */
	public Zone(IslandModel m) {
		this.m = m;
		this.wl = new NormalLevel();
		this.el = NaturalElement.NONE;
		this.heliport = false;
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
	
	public boolean isSubmergeable() {
		return this.wl.isSubmergeable();
	}
	
	@Override
	public String toString() {
		String wlString = this.wl.toString();
		if (wlString != "*") {
			if (this.heliport) {
				return "H";
			}
			else if (this.el != NaturalElement.NONE) {
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
		 * @apiNote Vérifie que le niveau de l'eau est viable
		 * @return true si viable false sinon
		 */
		public abstract boolean isSafe();
		
		/**
		 * @apiNote Vérifie si la zone est submergable
		 * (elle passe à l'état submergé lors du prochain
		 * appel Zone::flood())
		 * @return true si submergable false sinon
		 */
		public abstract boolean isSubmergeable();
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
		
		public boolean isSafe() {
			return true;
		}
		
		public boolean isSubmergeable() {
			return false;
		}
		
		@Override
		public String toString() {
			return "-";
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
		
		public boolean isSafe() {
			return true;
		}
		
		public boolean isSubmergeable() {
			return true;
		}
		
		@Override
		public String toString() {
			return "~";
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
		
		public boolean isSafe() {
			return false;
		}
		
		public boolean isSubmergeable() {
			return true;
		}
		
		@Override
		public String toString() {
			return "*";
		}
	}
}
