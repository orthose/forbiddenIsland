package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import controller.Controller;
import model.IslandModel;

/**
 * @author maxime & baptiste
 * @apiNote Vue de l'île
 */
public class IslandView {
	// Contexte graphique
	private JFrame frame;

	// Taille de la fenêtre
	private static int windowWidth;
	private static int windowHeight;

	private VGrid grille;
	// private Controlleur commandes;

	public IslandView(IslandModel model, Controller controller, int WindowWidth, int WindowHeight) {
		// Définition de la fenêtre principale
		IslandView.windowWidth = WindowWidth;
		IslandView.windowHeight = WindowHeight;
		frame = new JFrame();
		frame.setTitle("ForbiddenIsland");
	    //frame.setLayout(new FlowLayout());
		// frame.setLayout(new GridLayout() );

		Dimension windowSize = new Dimension(WindowWidth, WindowHeight);
		// frame.setBounds(0, 0, WindowWidth, WindowHeight);
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
		int extraHeight = WindowHeight - actualSize.height;
		
		// On met désormais la vrai taille
		frame.setSize(WindowWidth, WindowHeight + extraHeight);
	}

	public IslandView(IslandModel model, Controller controller) {
		this(model, controller, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
	}
	
	public static int getWindowWidth() {
		return windowWidth;
	}
	
	public static int getWindowHeight() {
		return windowHeight;
	}
}
