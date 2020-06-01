package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import controller.MenuController;
import model.Menu;

/**
 * @author baptiste
 * @apiNote Vue du menu
 */
public class MenuView {
	// Contexte graphique
	private static JFrame frame;
	
	private VMenu vMenu;

	// Taille de la fenêtre
	private static int windowWidth;
	private static int windowHeight;

	public MenuView(Menu menu, MenuController menuController) {
		frame = new JFrame();
		
		// Définition de la fenêtre principale
		MenuView.windowWidth = 15*Toolkit.getDefaultToolkit().getScreenSize().height/16;
		MenuView.windowHeight = 15*Toolkit.getDefaultToolkit().getScreenSize().height/16;
		
		frame.setTitle("ForbiddenIsland-Menu");

		Dimension windowSize = new Dimension(windowWidth, windowHeight);;
		frame.setPreferredSize(windowSize);

		this.vMenu = new VMenu(menu, menuController);
		frame.add(vMenu);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		// Obtention de la vrai taille de la zone d'affichage
		Dimension actualSize = frame.getContentPane().getSize();

		// Différence (taille théorique)/(vrai taille)
		int extraHeight = windowHeight - actualSize.height;

		// On met désormais la vrai taille
		frame.setSize(windowWidth, windowHeight + extraHeight);		
	}
	
	public static int getWindowWidth() {
		return windowWidth;
	}
	
	public static int getWindowHeight() {
		return windowHeight;
	}
	
	public static JFrame getFrame() {
		return frame;
	}

}
