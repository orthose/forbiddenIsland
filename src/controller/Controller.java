package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import model.IslandModel;

/**
 * @author maxime et baptiste
 * @apiNote Le contrôleur chargé de modifier
 * le modèle en fonction des évènements reçus
 */
public class Controller implements KeyListener, MouseListener/*ActionListener*/ {
	
	IslandModel m; // Référence au modèle
	
	/**
	 * @param m: Référence au modèle
	 */
	public Controller(IslandModel m) {
		this.m = m;
        /*setFocusable(true);
        setFocusTraversalKeysEnabled(false);*/
	}

	// Keyboard
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Maxime a appuyer sur la lettre 'a'");
		if(e.getKeyChar() == 'a') {
			System.out.println("Maxime a appuyer sur la lettre 'a'");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	// Mouse
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @apiNote Méthode appelée lorsqu'un évènement survient
	 * @param e: Évènement à traiter
	 */
	/*public void actionPerformed(ActionEvent e) {
    }*/
}
