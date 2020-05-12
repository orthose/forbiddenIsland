package model;

/**
 * @author maxime
 * @apiNote Centralise le dictionnaire
 * des toString des classes de model
 */
public class StringMap {
	private static final DoubleDirectionMap<String, String> map 
	= new DoubleDirectionMap<>();
	// Modifier value de DoubleDirectionMap::put(key, value)
	// pour modifier le symbole du Class::toString() correspondant
	{ 
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
	}
	
	public static String encode(String key) {
		return map.encode(key);
	}
	
	public static String decode(String value) {
		return map.decode(value);
	}
}
