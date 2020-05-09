package model;

/**
 * @author maxime
 * @apiNote Un couple de deux valeurs
 * @param <K> Type de la clé
 * @param <V> Type de la valeur
 */
public class Pair<K, V> {
	private final K key;
	private final V value;
	
	/**
	 * @apiNote Crée un couple (clé, valeur) constant
	 * @param key: Clé du couple
	 * @param value: Valeur du couple
	 */
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @return La clé du couple
	 */
	public K getKey() {
		return this.key;
	}
	
	/**
	 * @return La valeur du couple
	 */
	public V getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "(" + this.key.toString() + ", " + this.value.toString() + ")";
	}

}
