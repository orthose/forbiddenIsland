package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import controller.Controller;
import model.IslandModel;
import model.Player.InvalidPlayerId;
import model.Zone;
import util.Observer;

/**
 * @author baptiste
 * @apiNote Vue de la grille
 */
public class VGrid extends JPanel implements Observer {
	private IslandModel model;
	private Controller controller;
	// Images importées
	private BufferedImage sand;
	private BufferedImage water;
	private BufferedImage deepWater;
	private BufferedImage heliport;
	private BufferedImage[] player;
	private BufferedImage border;
	private BufferedImage escapeBorder;
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
	public VGrid(IslandModel model, Controller controller) {
		this.model = model;
		this.controller = controller;
		this.zoneWidth = IslandView.windowWidth / model.WIDTH;
		this.zoneHeight = IslandView.windowHeight / model.HEIGHT;

		// Tableau représentant les images des joueurs
		player = new BufferedImage[model.getNbPlayer()];

		// Chargement des images
		try {
			sand = ImageIO.read(new File("assets/textures/sand.jpg"));
			water = ImageIO.read(new File("assets/textures/water.jpg"));
			deepWater = ImageIO.read(new File("assets/textures/deepWater.jpg"));
			heliport = ImageIO.read(new File("assets/heliport.png"));
			for (int i = 0; i < player.length; i++) {
				player[0] = ImageIO.read(new File(model.getPlayer(i).pathImage()));
			}
			border = ImageIO.read(new File("assets/border.png"));
			escapeBorder = ImageIO.read(new File("assets/escapeBorder.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load images");
			easyDraw = true;
		} catch (InvalidPlayerId e) {
			e.printStackTrace();
			System.out.println("Failed to load images due to bad player ID");
		}

		// Chargement des animations
		airAnim = new Animation("assets/animation/air/air");
		fireAnim = new Animation("assets/animation/fire/fire");
		waterAnim = new Animation("assets/animation/water/water");
		earthAnim = new Animation("assets/animation/earth/earth");

		// On enregistre la vue [this] en tant qu' observateur de [modele]
		model.addObserver(this);

	}

	/**
	 * Met à jour le dessin
	 */
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

		hud(g);
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
		try {
			if (model.getPlayer(model.getCurrentIdPlayer()).getNbAction() > 0) {
				if (model.getMovePossibilitiesCurrentPlayer().contains(model.getZone(x / zoneWidth, y / zoneHeight))) {
					if (!easyDraw) {
						g.drawImage(border, x, y, zoneWidth, zoneHeight, this);
					} else {
					}
				}
			}
		} catch (InvalidPlayerId e1) {
			System.out.println("Error on player ID");
			e1.printStackTrace();
		}

		// Border escape
		if (Controller.getRunFromDeath()) {
			if (model.getMovePossibilitiesPlayer(controller.getCurrentIdPlayer()).contains(model.getZone(x / zoneWidth, y / zoneHeight))) {
				if (!easyDraw) {
					g.drawImage(escapeBorder, x, y, zoneWidth, zoneHeight, this);
				} else {
				}
			}
		}

		// Player
		try {
			for (int i = 0; i < model.getNbPlayer(); i++) {
				if (model.getPlayer(i).isAlive()) {
					if (!easyDraw) {
						g.drawImage(player[i], model.getPositionPlayer(i).x * zoneWidth, model.getPositionPlayer(i).y * zoneHeight, zoneWidth, zoneHeight, this);
					} else {
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error on Player ID");
		}
	}

	private void hud(Graphics g) {
		paintTurn(g, zoneWidth / 4, zoneHeight / 4);
	}

	/**
	 * Draw the turn number on a specific position
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	private void paintTurn(Graphics g, int x, int y) {
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("TimesRoman", Font.PLAIN, zoneHeight / 4));
		g.drawString("Turn: " + Integer.toString(model.getTurn()), x, y);
	}

}
