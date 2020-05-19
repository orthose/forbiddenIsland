package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.IslandModel;

/**
 * @author baptiste
 * @apiNote Vue de la grille
 */
public class VGrid extends JPanel implements Observer {
	private IslandModel model;

	// Définition d'une taille (en pixels) d'une zone 
	private final static int TAILLE = 12;

	// Constructeur 
	public VGrid(IslandModel model) {
		this.model = model;
		// On enregistre la vue [this] en tant qu'observateur de [modele]
		model.addObserver(this);
		
		/**
		 * Définition et application d'une taille fixe pour cette zone de l'interface,
		 * calculée en fonction du nombre de cellules et de la taille d'affichage.
		 */
		Dimension dim = new Dimension(TAILLE * model.WIDTH, TAILLE * model.HEIGHT);
		this.setPreferredSize(dim);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
		
	}


	/*public void paintComponent(Graphics g) {
		super.repaint();
		for (int i = 1; i <= model.LARGEUR; i++) {
			for (int j = 1; j <= model.HAUTEUR; j++) {
				paint(g, model.getCellule(i, j), (i - 1) * TAILLE, (j - 1) * TAILLE);
			}
		}
	}*/

	/**
	 * Fonction auxiliaire de dessin d'une cellule. Ici, la classe [Cellule] ne peut
	 * être désignée que par l'intermédiaire de la classe [CModele] à laquelle elle
	 * est interne, d'où le type [CModele.Cellule]. Ceci serait impossible si
	 * [Cellule] était déclarée privée dans [CModele].
	 */
	/*private void paint(Graphics g, Cellule c, int x, int y) {
		if (c.estVivante()) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillRect(x, y, TAILLE, TAILLE);
	}*/
}
