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
			Zone up = super.position.neighbour(Move.UP);
			Zone down = super.position.neighbour(Move.DOWN);
			Zone right = super.position.neighbour(Move.RIGHT);
			Zone left = super.position.neighbour(Move.LEFT);
			
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
			MutableBoolean success = new MutableBoolean(false);
			// Déplacement 2 fois dans la même direction
			Zone zonezone = zone.neighbour(move, success);
			// Zone franchissable à l'arrivée
			if (success.value && zonezone.isCrossable()) {
				res.add(zonezone);
			}
		}
	}
	
	// Surcharge pour permettre un
	// déplacement directionnel
	public boolean move(Move move) {
		MutableBoolean success = new MutableBoolean(false);
		Zone zone = super.position.neighbour(move, success);
		if (success.value && zone.isSubmergedLevel()) {
			Zone zonezone = zone.neighbour(move, success);
			success.value = success.value && zonezone.isCrossable();
			if (success.value) {
				super.move(zonezone);
			}
		}
		return success.value;
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
