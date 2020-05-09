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
			return StringMap.encode("AIR");
		}
	}, 
	WATER {
		@Override
		public String toString() {
			return StringMap.encode("WATER");
		}
		
	}, 
	EARTH {
		@Override
		public String toString() {
			return StringMap.encode("EARTH");
		}
	}, 
	FIRE {
		@Override
		public String toString() {
			return StringMap.encode("FIRE");
		}
	}, 
	NONE {
		@Override
		public String toString() {
			return StringMap.encode("NONE");
		}
	};
	
}
