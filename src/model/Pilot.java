package model;

import java.util.HashSet;

/**
 * @author maxime
 * @apiNote Rôle de pilote qui peut être 
 * endossé par un joueur. Le pilote peut se
 * déplacer sur n'importe quelle zone qui n'est
 * pas submergée.
 */
public class Pilot extends Player {
	
	/**
	 * @apiNote Constructeur à la fois léger et complet
	 * @param m: Référence au modèle
	 * @param name: Nome du joueur
	 * @param sexe: Sexe du joueur
	 */
	public Pilot(IslandModel m, String name, Sexe sexe) {
		super(m, name, sexe);
	}
	
	@Override
	// Le pilote peut se déplacer sur n'importe quelle zone
	// qui n'est pas submergée
	public HashSet<Zone> movePossibilities() {
		HashSet<Zone> res = new HashSet<Zone>();
		for (int i = 0; i < super.m.WIDTH; i++) {
			for (int j = 0; j < super.m.HEIGHT; j++) {
				Zone zone = super.m.getZone(i, j);
				if (! zone.isSubmergedLevel()) {
					res.add(zone);
				}
			}
		}
		return res;
	}
	
	/**
	 * @apiNote Donne les déplacements possibles
	 * basiquement pour un simple joueur
	 * Cette méthode est utilisée par Sailor
	 * @return Ensemble des zones de déplacement
	 * possibles
	 */
	protected HashSet<Zone> basicMovePossibilities() {
		for (Zone zone : super.movePossibilities()) {
			System.out.println(zone.x+" "+zone.y);
		}
		return super.movePossibilities();
	}

	
	@Override
	public String pathImage() {
		switch (super.sexe) {
		case MALE: return "assets/player/pilot_M.png";
		case FEMALE: return "assets/player/pilot_F.png";
		}
		return "";
	}

}
