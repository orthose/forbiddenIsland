package model;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author maxime
 * @apiNote Dictionnaire pour encoder
 * et décoder des données
 * @param <K> Type de la clé
 * @param <V> Type de la valeur
 */
public class DoubleDirectionMap<K, V> {
	
	private ArrayList<Pair> map;
	
	/**
	 * @apiNote Crée un dictionnaire vide
	 */
	public DoubleDirectionMap() {
		this.map = new ArrayList<Pair>();
	}
	
	/**
	 * @apiNote Ajoute Un couple au dictionnaire
	 * @param key: Clé du couple
	 * @param value: Valeur du couple
	 */
	public void put(K key, V value) {
		this.map.add(new Pair(key, value));
	}
	
	/**
	 * @apiNote Encode la clé donnée
	 * @param key: Clé à encoder
	 * @return value associée
	 * null si aucune valeur associée
	 */
	public V encode(K key) {
		for(Pair pair : this.map) {
			if (key.equals(pair.key)) {
				return pair.value;
			}
		}
		return null;
	}
	
	/**
	 * @apiNote Décode la valeur donnée
	 * @param value: Valeur à décoder
	 * @return key associée
	 * null si aucune clé associée
	 */
	public K decode(V value) {
		for(Pair pair : this.map) {
			if(value.equals(pair.value)) {
				return pair.key;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		String res = "";
		for(Pair pair : this.map) {
			res += pair.toString() + "\n";
		}
		return res;
	}
	
	/**
	 * @apiNote Donne la liste interne
	 * pour itérer dessus par exemple
	 * @return Liste des clés
	 */
	public ArrayList<K> allKeys() {
		ArrayList<K> res = new ArrayList<K>();
		for (Pair pair : this.map) {
			res.add(pair.key);
		}
		return res;
	}
	
	/**
	 * @apiNote Donne la liste interne
	 * pour itérer dessus par exemple
	 * @return Liste des valeurs
	 */
	public ArrayList<V> allValues() {
		ArrayList<V> res = new ArrayList<V>();
		for (Pair pair : this.map) {
			res.add(pair.value);
		}
		return res;
	}
	
	/**
	 * @author maxime
	 * @apiNote Un couple de deux valeurs
	 * @param <K> Type de la clé
	 * @param <V> Type de la valeur
	 */
	public class Pair {
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
		
		@Override
		public String toString() {
			return "(" + this.key.toString() + ", " + this.value.toString() + ")";
		}
	}
}
