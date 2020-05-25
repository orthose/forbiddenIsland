package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.IslandModel;

/**
 * @author maxime
 * @apiNote Le contrôleur chargé de modifier
 * le modèle en fonction des évènements reçus
 */
public class Controller implements ActionListener {
	
	IslandModel m; // Référence au modèle
	
	/**
	 * @param m: Référence au modèle
	 */
	public Controller(IslandModel m) {
		this.m = m;
	}
	
	/**
	 * @apiNote Méthode appelée lorsqu'un évènement survient
	 * @param e: Évènement à traiter
	 */
	public void actionPerformed(ActionEvent e) {
    }
}
