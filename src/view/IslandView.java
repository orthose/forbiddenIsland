package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.Controller;
import model.IslandModel;

/**
 * @author maxime & baptiste
 * @apiNote Vue de l'île
 */
public class IslandView {
	private VGrid grille;
	// private Controlleur commandes;

	public IslandView(IslandModel model, Controller controller) {	
		JFrame frame = new JFrame();	

		frame.setTitle("ForbiddenIsland");

		Dimension windowSize = new Dimension( MenuView.getWindowWidth(),  MenuView.getWindowHeight());;
		frame.setPreferredSize(windowSize);
		
		this.grille = new VGrid(model, controller);
		frame.add(grille);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		// Obtention de la vrai taille de la zone d'affichage
		Dimension actualSize = frame.getContentPane().getSize();

		// Différence (taille théorique)/(vrai taille)
		int extraHeight = MenuView.getWindowHeight() - actualSize.height;

		// On met désormais la vrai taille
		frame.setSize(MenuView.getWindowWidth(), MenuView.getWindowHeight() + extraHeight);		
		
	}
}
