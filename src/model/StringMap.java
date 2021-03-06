package model;

/**
 * @author maxime
 * @apiNote Centralise le dictionnaire
 * des toString des classes de model
 */
public final class StringMap {
	
	private static final DoubleDirectionMap<String, String> map 
	= new DoubleDirectionMap<>();
	
	// Instanciation impossible depuis l'extérieur
	private StringMap() {
		// Modifier value de DoubleDirectionMap::put(key, value)
		// pour modifier le symbole du Class::toString() correspondant
		map.put("NormalLevel", "-");
		map.put("FloodedLevel", "~");
		map.put("SubmergedLevel", "*");
		map.put("heliport", "H");
		map.put("heliport&FloodedLevel", "h");
		map.put("AIR", "A");
		map.put("AIR&FloodedLevel", "a");
		map.put("WATER", "W");
		map.put("WATER&FloodedLevel", "w");
		map.put("EARTH", "E");
		map.put("EARTH&FloodedLevel", "e");
		map.put("FIRE", "F");
		map.put("FIRE&FloodedLevel", "f");
		map.put("NONE", "");
		map.put("Player", "P");
		map.put("Player&FloodedLevel", "p");
	}
	
	public static String encode(String key) {
		new StringMap();
		return map.encode(key);
	}
	
	public static String decode(String value) {
		new StringMap();
		return map.decode(value);
	}
}
