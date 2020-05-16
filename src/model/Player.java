package model;

import java.util.ArrayList;

/**
 * @author maxime
 * @apiNote Joueur et ses actions
 */
public class Player {
	private String name;
	private int order;
	protected Zone position;
	private boolean alive;
	private static ArrayList<Integer> allOrders;
	
	/**
	 * @param order: Ordre de passage du joueur
	 * qui commence à 0 et chaque nouveau joueur
	 * doit avoir l'ordre order+1
	 * @param name: Nom du joueur
	 * @param position: zone de l'actuelle position
	 * du joueur
	 * @throws InvalidOrder: Si l'ordre de passage est déjà
	 * enregistré ou que le premier joueur n'a pas l'ordre 0
	 */
	public Player(int order, String name, Zone position) throws InvalidOrder {
		Integer lastOrder = Player.allOrders.get(Player.allOrders.size());
		if(Player.allOrders.contains(Integer.valueOf(order)) || lastOrder + 1 != order) {
			throw new InvalidOrder(order);
		}
		if(Player.allOrders.isEmpty() && order != 0) {
			throw new InvalidOrder(order);
		}
		this.order = order;
		Player.allOrders.add(Integer.valueOf(order));
		this.name = name;
		this.position = position;
		this.position.addPlayer(this);
		this.alive = true;
	}
	
	// Exception si le numéro de joueur est invalide
	public static class InvalidOrder extends Exception {
		private int order;
		public InvalidOrder(int order) {
			super();
			this.order = order;
		}
		public int getOrder() {
			return this.order;
		}
	}
	
	/**
	 * @apiNote Vérifie que le numéro de joueur
	 * est bien enregistré
	 * @param order: Numéro de joueur
	 * @return true si ordre valide false sinon
	 */
	public static boolean isPlayerOrder(int order) {
		return Player.allOrders.contains(Integer.valueOf(order));
	}
	
	/**
	 * @apiNote Vérifie que le joueur est en vie
	 * @return true si en vie false sinon
	 */
	public boolean isAlive() {
		return this.alive;
	}
	
	/**
	 * @apiNote Tue le joueur de manière
	 * irréversible
	 */
	public void kill() {
		this.alive = false;
	}
	
	/**
	 * @apiNote Déplace le joueur vers
	 * une zone sans vérification
	 * (permet le téléport)
	 * @param newPosition: nouvelle position
	 * du joueur
	 */
	public void move(Zone newPosition) {
		if(this.alive) {
			this.position.removePlayer(this);
			this.position = newPosition;
			this.position.addPlayer(this);
		}
	}
}
