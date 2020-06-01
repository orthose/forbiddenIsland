package model;

import model.Player.InvalidPlayerId;

/**
 * @author maxime
 * @apiNote Rôle du navigateur pouvant
 * être endossé par un joueur.
 * Le navigateur peut déplacer
 * un autre joueur.
 */
public class Sailor extends Player {
	
	/**
	 * @apiNote Constructeur à la fois léger et complet
	 * @param m: Référence au modèle
	 * @param name: Nome du joueur
	 * @param sexe: Sexe du joueur
	 */
	public Sailor(IslandModel m, String name, Sexe sexe) {
		super(m, name, sexe);
	}
	
	/**
	 * @apiNote Permet au navigateur de déplacer un joueur
	 * (autre que lui) d'une case adjacente.
	 * @param player: Joueur à déplacer
	 * @param zone: Zone sur laquelle déplacer le joueur
	 * @return true si déplacement réussi false sinon
	 * @throws InvalidPlayerId: Si le joueur n'existe pas
	 */
	public boolean movePlayerSailorPower(int id, Move move) throws InvalidPlayerId {
		boolean res = (super.m.getPlayer(id) != this && super.m.movePlayer(id, move));
		if (res) {
			super.nbAction--;
			// Compensation de la perte d'une
			// action du joueur déplacé
			super.m.getPlayer(id).nbAction++;
		}
		return res;
	}
	
	@Override
	public String pathImage() {
		switch (super.sexe) {
		case MALE: return "assets/player/sailor_M.png";
		case FEMALE: return "assets/player/sailor_F.png";
		}
		return "";
	}
}
