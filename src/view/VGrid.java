package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import controller.Controller;
import model.IslandModel;
import model.Zone;
import util.Observer;

/**
 * @author baptiste
 * @apiNote Vue de la grille
 */
public class VGrid extends JPanel implements Observer {
	private IslandModel model;
	// Images importées
	private BufferedImage sand;
	private BufferedImage water;
	private BufferedImage deepWater;
	private BufferedImage heliport;
	private BufferedImage player;
	private BufferedImage border;
	// Animations
	private Animation airAnim;
	private Animation earthAnim;
	private Animation fireAnim;
	private Animation waterAnim;

	// Boolean determinant le succès du chargement des images et par conséquent la
	// méthode de dessin choisi
	private boolean easyDraw = false;

	private final int zoneWidth;
	private final int zoneHeight;

	// Constructeur
	public VGrid(IslandModel model) {
		this.model = model;
		this.zoneWidth = IslandView.windowWidth / model.WIDTH;
		this.zoneHeight = IslandView.windowHeight / model.HEIGHT;

		// Chargement des images
		try {
			sand = ImageIO.read(new File("assets/textures/sand.jpg"));
			water = ImageIO.read(new File("assets/textures/water.jpg"));
			deepWater = ImageIO.read(new File("assets/textures/deepWater.jpg"));
			heliport = ImageIO.read(new File("assets/heliport.png"));
			player = ImageIO.read(new File("assets/player/bob.png"));
			border = ImageIO.read(new File("assets/border.png"));
		} catch (IOException e) {
			System.out.println("Failed to load images");
			easyDraw = true;
		}

		// Chargement des animations
		airAnim = new Animation("assets/animation/air/air");
		fireAnim = new Animation("assets/animation/fire/fire");
		waterAnim = new Animation("assets/animation/water/water");
		earthAnim = new Animation("assets/animation/earth/earth");

		// On enregistre la vue [this] en tant qu' observateur de [modele]
		model.addObserver(this);

	}

	public void update() {
		repaint();
	}

	/**
	 * Dessine toute les zones via l'appel un appel de dessin unitaire
	 */
	public void paintComponent(Graphics g) {
		super.repaint();
		for (int i = 0; i < model.WIDTH; i++) {
			for (int j = 0; j < model.HEIGHT; j++) {
				paint(g, model.getZone(i, j), i * zoneWidth, j * zoneHeight);
			}
		}
	}

	/**
	 * Desine une Zone spécifique à une coordonée donnée
	 * 
	 * @param g un Graphic @param z une zone @param x la position des
	 *          abscisses @param y la position des ordonnées @throws
	 */
	private void paint(Graphics g, Zone z, int x, int y) {
		// Graphics2D est utilisé pour la transparance d'image
		Graphics2D g2d = (Graphics2D) g;

		// Water level
		if (!z.isSubmergedLevel()) {
			// Niveau d'eau: normal
			if (z.isNormalLevel()) {
				// Sand
				if (!easyDraw) {
					g2d.drawImage(sand, x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(255, 185, 30));
					g.fillRect(x, y, zoneWidth, zoneHeight);
				}
			}
			// Niveau d'eau: Inondé
			else if (z.isFloodedLevel()) {
				if (!easyDraw) {
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
					g2d.drawImage(sand, x, y, zoneWidth, zoneHeight, this);
					g2d.drawImage(water, x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(20, 30, 255));
					g.fillRect(x, y, zoneWidth, zoneHeight);
				}
			}

			// Element
			switch (z.getNaturalElement()) {
			case AIR:
				if (!easyDraw) {
					g.drawImage(airAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {

				}
				break;
			case EARTH:
				if (!easyDraw) {
					g.drawImage(earthAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {
				}
				break;
			case FIRE:
				if (!easyDraw) {
					g.drawImage(fireAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {
				}
				break;
			case WATER:
				if (!easyDraw) {
					g.drawImage(waterAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {
				}
				break;
			}

			// Heliport
			if (z.isHeliport()) {
				if (!easyDraw) {
					g.drawImage(heliport, x, y, zoneWidth, zoneHeight, this);
				} else {
				}
			}
		}
		// Niveau d'eau: Submergé
		else {
			if (!easyDraw) {
				g.drawImage(deepWater, x, y, zoneWidth, zoneHeight, this);
			} else {
				g.setColor(new Color(20, 30, 150));
				g.fillRect(x, y, zoneWidth, zoneHeight);
			}
		}

		// Border
		if (Controller.getNbAction() > 0) {
			if (model.getMovePossibilitiesCurrentPlayer().contains(model.getZone(x / zoneWidth, y / zoneHeight))) {
				if (!easyDraw) {
					g.drawImage(border, x, y, zoneWidth, zoneHeight, this);
				} else {
				}
			}
		}

		// Player
		try {
			for (int i = 0; i < model.getNbPlayer(); i++) {
				if (model.getPlayer(i).isAlive()) {
					if (!easyDraw) {
						g.drawImage(player, model.getPositionPlayer(i).x * zoneWidth, model.getPositionPlayer(i).y * zoneHeight, zoneWidth, zoneHeight, this);
					} else {
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error on Player ID");
		}

	}

}
