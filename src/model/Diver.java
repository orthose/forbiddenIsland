package model;

import java.util.HashSet;

/**
 * @author maxime
 * @apiNote Rôle du plongeur pouvant
 * être endossé par un joueur.
 * Le plongeur peut traverser une zone submergée.
 */
public class Diver extends Player {
	
	/**
	 * @apiNote Constructeur à la fois léger et complet
	 * @param m: Référence au modèle
	 * @param name: Nome du joueur
	 * @param sexe: Sexe du joueur
	 */
	public Diver(IslandModel m, String name, Sexe sexe) {
		super(m, name, sexe);
	}
	
	@Override
	// Le plongeur peut traverser une zone submergée
	public HashSet<Zone> movePossibilities() {
		// Déplacements normaux possibles
		HashSet<Zone> res = super.movePossibilities();
		if (super.alive) {
			Zone up = this.position.neighbour(Move.UP);
			Zone down = this.position.neighbour(Move.DOWN);
			Zone right = this.position.neighbour(Move.RIGHT);
			Zone left = this.position.neighbour(Move.LEFT);
			
			// La zone du haut est submergée ?
			// On peut donc la franchir si la
			// zone 2 fois en haut n'est pas
			// submergée
			this.diverPower(res, up, Move.UP);
			
			// La zone du bas est submergée ?
			this.diverPower(res, down, Move.DOWN);
			
			// La zone de droite est submergée ?
			this.diverPower(res, right, Move.RIGHT);
			
			// La zone de gauche est submergée ?
			this.diverPower(res, left, Move.LEFT);
			
		}
		return res;
	}
	
	/**
	 * @apiNote Factorisation de movePossibilities
	 * Lorsque la zone est submergée on regarde la case
	 * selon voisine selon le déplacement et si elle n'est
	 * pas submergée elle est ajoutée au résultat des zones
	 * accessibles par le plongeur.
	 * @param res: Ensemble des zones accessibles par le plongeur
	 * @param zone: Zone possiblement submergée
	 * @param move: Déplacement depuis la zone
	 */
	private void diverPower(HashSet<Zone> res, Zone zone, Move move) {
		// La zone est submergée
		if (zone.isSubmergedLevel()) {
			Boolean success = false;
			// Déplacement 2 fois dans la même direction
			Zone zonezone = zone.neighbour(move, success);
			// Zone franchissable à l'arrivée
			if (success && zonezone.isCrossable()) {
				res.add(zonezone);
			}
		}
	}
	
	@Override
	public String pathImage() {
		switch (super.sexe) {
		case MALE: return "";
		case FEMALE: return "";
		}
		return "";
	}
}
