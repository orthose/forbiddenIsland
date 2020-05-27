package model;

/**
 * @author maxime
 * @apiNote Rôle de l'ingénieur qui peut
 * être endossé par un joueur. L'ingénieur
 * peut assécher 2 zones pour une seule action.
 */
public class Engineer extends Player {
	
	private int nbActionDry;
	private static final int nbActionDryAllowed = 2;
	
	/**
	 * @apiNote Constructeur à la fois léger et complet
	 * @param m: Référence au modèle
	 * @param name: Nome du joueur
	 * @param sexe: Sexe du joueur
	 */
	public Engineer(IslandModel m, String name, Sexe sexe) {
		super(m, name, sexe);
		this.nbActionDry = Engineer.nbActionDryAllowed;
	}
	
	@Override
	// L'ingénieur peut assécher 2 zones pour
	// seulement 1 action dépensée
	public boolean dry(Move move) {
		boolean res = super.dry(move);
		// On annule le coût de l'action
		// Dans l'autre cas le nombre d'actions
		// est automatiquement décrémenté
		if (res && this.nbActionDry > 0) {
			super.nbAction++;
		}
		return res;
	}
}
