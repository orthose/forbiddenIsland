package controller;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;


import model.IslandModel;

/**
 * @author maxime et baptiste
 * @apiNote Le contrôleur chargé de modifier
 * le modèle en fonction des évènements reçus
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
             System.out.println("tester");
         } else if (e.getID() == KeyEvent.KEY_RELEASED) {
             System.out.println("2test2");
         } else if (e.getID() == KeyEvent.KEY_TYPED) {
             System.out.println("3test3");
         }
		return false;
	}

}
