package model;

/**
 * @author maxime
 * @apiNote Élément naturel (air, eau, terre, feu)
 * NONE pour spécifier n'être d'aucun élément
 */
public enum NaturalElement {
	AIR {
		@Override
		public String toString() {
			return "A";
		}
	}, 
	WATER {
		@Override
		public String toString() {
			return "W";
		}
		
	}, 
	EARTH {
		@Override
		public String toString() {
			return "E";
		}
	}, 
	FIRE {
		@Override
		public String toString() {
			return "F";
		}
	}, 
	NONE {
		@Override
		public String toString() {
			return "";
		}
	};
	
}
