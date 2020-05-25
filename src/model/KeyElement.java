package model;

/**
 * @author maxime
 * @apiNote Clé associée à un élément naturel
 * Aucune contrainte sur le nombre de clés créables
 */
public class KeyElement {
	private final NaturalElement el;
	
	public KeyElement(NaturalElement el) {
		this.el = el;
	}
	
	
	/**
	 * @apiNote Donne l'élément associé à la clé
	 * @return Élément naturel associé à la clé
	 */
	public NaturalElement getEl() {
		return this.el;
	}
	
	/**
	 * @apiNote Vérifie si la clé est compatible
	 * avec la zone
	 * @param zone: zone à tester
	 * @return true si la clé est compatible false sinon
	 */
	public boolean tryKeyElement(Zone zone) {
		return this.el == zone.getNaturalElement();
	}
}
