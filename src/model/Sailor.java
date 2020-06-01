package model;

import java.util.ArrayList;

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
		Player player = super.m.getPlayer(id);
		// Le pilote ne doit pas pouvoir se déplacer
		// comme habituellement avec son pouvoir
		// Il faut juste donner changer l'ensemble
		// des déplacements possibles
		if (player instanceof Pilot) {
			((Pilot)player).isMovedBySailor = true;
		}
		boolean res = (player != this && super.m.movePlayer(id, move));
		if (player instanceof Pilot) {
			((Pilot)player).isMovedBySailor = false;
		}
		//if (player instanceof Pilot) {
		//	player.movePossibilities = new ArrayList<Zone>(((Pilot) player).basicMovePossibilities());
		//}
		if (res) {
			super.nbAction--;
			// Compensation de la perte d'une
			// action du joueur déplacé
			super.m.getPlayer(id).nbAction++;
		}
		return res;
	}
	
	/**
	 * @apiNote Méthode a appeler au début du tour
	 * du navigateur pour notamment régler les déplacements
	 * possibles des joueurs pilote 
	 */
	public void beginSailorTurn() {
		for (Player player : super.m.players) {
			if (player instanceof Pilot) {
				player.movePossibilities = new ArrayList<Zone>(((Pilot) player).basicMovePossibilities());
			}
		}
	}
	
	/**
	 * @apiNote Méthode a appeler à la fin du tour
	 * du navigateur pour notamment régler les déplacements
	 * possibles des joueurs pilote 
	 */
	public void endSailorTurn() {
		for (Player player : super.m.players) {
			if (player instanceof Pilot) {
				player.movePossibilities = new ArrayList<Zone>(player.movePossibilities());
			}
		}
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
