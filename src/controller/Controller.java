package controller;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import model.IslandModel;
import model.Move;
import model.Player.InvalidPlayerId;

/**
 * @author maxime et baptiste
 * @apiNote Le contrôleur chargé de modifier le modèle en fonction des
 *          évènements reçus
 */
public class Controller implements KeyEventDispatcher {

	IslandModel m; // Référence au modèle

	/**
	 * @param m: Référence au modèle
	 */
	public Controller(IslandModel m) {
		this.m = m;
	}

	@Override
	/**
	 * @apiNote Méthode appelée lorsqu'un évènement survient
	 * @param e: Évènement à traiter
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			try {
				if (e.getKeyCode() == e.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
					m.movePlayer(m.getCurrentIdPlayer(), Move.UP);
				} else if (e.getKeyCode() == e.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
					m.movePlayer(m.getCurrentIdPlayer(), Move.DOWN);
				} else if (e.getKeyCode() == e.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
					m.movePlayer(m.getCurrentIdPlayer(), Move.RIGHT);
				} else if (e.getKeyCode() == e.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
					m.movePlayer(m.getCurrentIdPlayer(), Move.LEFT);
				}
			} catch (InvalidPlayerId e1) {
				e1.printStackTrace();
				System.out.println("Error while moving player in controller");
			}
		}
		return false;
	}

}
