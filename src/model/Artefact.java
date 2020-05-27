package model;

import java.util.ArrayList;

/**
 * @author maxime
 * @apiNote Artefact associé à un élément naturel
 * On ne peut en créer que 4 au maximum
 */
public class Artefact {
	private static int found = 0;
	private static Artefact artefacts[] = new Artefact[4];
	private NaturalElement el;
	
	private Artefact(NaturalElement el) {
		this.el = el;
		Artefact.artefacts[Artefact.found++] = this;
	}
	
	/**
	 * @return Élément naturel associé à l'artefact
	 */
	public NaturalElement getNaturalElement() {
		return this.el;
	}
	
	/**
	 * @apiNote Vérifie s'il est possible de trouver
	 * un nouvel artefact
	 * @param key: Clé du joueur
	 * @param zone: Zone sur laquelle est le joueur
	 * @return true si un artefact peut être trouver
	 * false sinon
	 */
	public static boolean canFindNewArtefact(KeyElement key, Zone zone) {
		if (key.tryKeyElement(zone)) {
			for (int i = 0; i < Artefact.found; i++) {
				if (Artefact.artefacts[i].el == zone.getNaturalElement()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * @apiNote Crée un nouvel artefact s'il n'a
	 * pas déjà était trouvé
	 * @param key: Clé du joueur
	 * @param zone: Zone sur laquelle est le joueur
	 */
	public static boolean findNewArtefact(KeyElement key, Zone zone) {
		if (Artefact.canFindNewArtefact(key, zone)) {
			new Artefact(zone.getNaturalElement());
			return true;
		}
		return false;
	}
	
	/**
	 * @apiNote Vérifie que tous les artefacts
	 * ont été trouvés
	 * @return true si tous les artefacts ont
	 * été trouvés false sinon
	 */
	public static boolean allArtefactsFound() {
		return Artefact.found == 4;
	}
	
	/**
	 * @apiNote Donne les artefacts déjà trouvés
	 * @return Liste des artefacts trouvés
	 */
	public static ArrayList<Artefact> getFoundArtefacts() {
		ArrayList<Artefact> res = new ArrayList<Artefact>(Artefact.found);
		for (int i = 0; i < Artefact.found; i++) {
			res.add(Artefact.artefacts[i]);
		}
		return res;
	}
	
	/**
	 * @apiNote Vérifie qu'un artefact a été trouvé
	 * @param el: Élément associé à l'artefact
	 * @return true si trouvé false sinon
	 */
	public static boolean isFound(NaturalElement el) {
		for (int i = 0; i < Artefact.found; i++) {
			if (Artefact.artefacts[i].getNaturalElement() == el) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @apiNote Supprime tous les artefacts trouvés
	 */
	public static void reset() {
		Artefact.found = 0;
		Artefact.artefacts = new Artefact[4];
	}
	
	@Override
	public String toString() {
		return "Artefact de "+this.el.name();
	}
}
