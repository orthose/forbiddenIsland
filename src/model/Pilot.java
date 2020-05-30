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
	public String pathImage() {
		switch (super.sexe) {
		case MALE: return "assets/player/pilot_M.png";
		case FEMALE: return "assets/player/pilot_F.png";
		}
		return "";
	}

}
