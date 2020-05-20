package view;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.IslandModel;
import model.Zone;
import util.Observer;

/**
 * @author baptiste
 * @apiNote Vue de la grille
 */
public class VGrid extends JPanel implements Observer {
	private IslandModel model;

	private final int zoneWidth;
	private final int zoneHeight;

	// Constructeur
	public VGrid(IslandModel model) {
		this.model = model;
		this.zoneWidth = IslandView.windowWidth / model.WIDTH;
		this.zoneHeight = IslandView.windowHeight / model.HEIGHT;
		
		// On enregistre la vue [this] en tant qu'observateur de [modele]
		model.addObserver(this);

	}

	public void update() {
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.repaint();
		for (int i = 0; i < model.WIDTH; i++) {
			for (int j = 0; j < model.HEIGHT; j++) {
				paint(g, model.getZone(i, j), i * zoneWidth, j * zoneHeight);
			}
		}
	}


	private void paint(Graphics g, Zone z, int x, int y) {

		if(z.isNormalLevel()) {
			g.setColor(new Color(255, 185, 30));
		} else if (z.isFloodedLevel()) {
			g.setColor(new Color(20, 30, 255));
		} else {
			g.setColor(new Color(20, 30, 150));
		}
		g.fillRect(x, y, zoneWidth, zoneHeight);
	}
}
