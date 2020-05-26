package controller;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import model.IslandModel;
import model.Move;
import model.Player.InvalidPlayerId;
import model.Zone;

/**
 * @author maxime et baptiste
 * @apiNote Le contrôleur chargé de modifier le modèle en fonction des
 *          évènements reçus
 */
public class Controller implements KeyEventDispatcher {

	private IslandModel m; // Référence au modèle
	private static int nbAction = 3;
	private int currentIdPlayer;

	/**
	 * @param m: Référence au modèle
	 */
	public Controller(IslandModel m) {
		this.m = m;
		this.currentIdPlayer = m.nextIdPlayer();
	}

	@Override
	/**
	 * @apiNote Méthode appelée lorsqu'un évènement survient
	 * @param e: Évènement à traiter
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			// move
			if (nbAction > 0) {
				try {
					if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
						if (m.movePlayer(currentIdPlayer, Move.UP)) {
							nbAction--;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
						if (m.movePlayer(currentIdPlayer, Move.DOWN)) {
							nbAction--;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
						if (m.movePlayer(currentIdPlayer, Move.RIGHT)) {
							nbAction--;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
						if (m.movePlayer(currentIdPlayer, Move.LEFT)) {
							nbAction--;
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Error while moving player in controller");
				}
			}
			
			// Dry
			if (nbAction > 0) {
				try {
					if (e.getKeyChar() == '5') {
						if (m.dryPlayer(currentIdPlayer, Move.NONE)) {
							nbAction--;
						}
					} else if (e.getKeyChar() == '8') {
						if (m.dryPlayer(currentIdPlayer, Move.UP)) {
							nbAction--;
						}
					} else if (e.getKeyChar() == '2') {
						if (m.dryPlayer(currentIdPlayer, Move.DOWN)) {
							nbAction--;
						}
					} else if (e.getKeyChar() == '6') {
						if (m.dryPlayer(currentIdPlayer, Move.RIGHT)) {
							nbAction--;
						}
					} else if (e.getKeyChar() == '4') {
						if (m.dryPlayer(currentIdPlayer, Move.LEFT)) {
							nbAction--;
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Error while drying a zone in controller");
				}
			}
			
			// Artefact
			if (nbAction > 0) {
				try {
					if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
						m.findArtefactPlayer(currentIdPlayer);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Error while catching artefact in controller");
				}
			}
			
			// Skip turn
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				nbAction = 3;
				m.floodRandom();
				try {
					m.findKeyElementPlayer(currentIdPlayer);
				} catch (InvalidPlayerId e1) {
					e1.printStackTrace();
					System.out.println("Error on player Id");
				}
				currentIdPlayer = m.nextIdPlayer();
			}
		}
		return false;
	}
	
	public static int getNbAction() {
		return Controller.nbAction;
	}

}
