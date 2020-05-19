package view;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import model.IslandModel;

/**
 * @author maxime & baptiste
 * @apiNote Vue de l'île
 */
public class IslandView {
	// Contexte graphique
	private JFrame frame;

	// private VGrid grille;
	// private VControlleur commandes;

	public IslandView(IslandModel model) {
		/** Définition de la fenêtre principale. */
		frame = new JFrame();
		frame.setTitle("Jeu de la vie de Conway");

		frame.setLayout(new FlowLayout());

		/** Définition des deux vues et ajout à la fenêtre. */
		// grille = new VueGrille(modele);
		// frame.add(grille);
		// commandes = new VueCommandes(modele);
		// frame.add(commandes);

		/**
		 * Fin de la plomberie : - Ajustement de la taille de la fenêtre en fonction du
		 * contenu. - Indiquer qu'on quitte l'application si la fenêtre est fermée. -
		 * Préciser que la fenêtre doit bien apparaître à l'écran.
		 */
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
