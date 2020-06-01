package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import controller.Controller;
import model.Artefact;
import model.IslandModel;
import model.Player;
import model.Zone;
import model.Player.InvalidPlayerId;
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
	private BufferedImage specialBorder;
	private BufferedImage kSailor;
	private BufferedImage kPilot;
	private BufferedImage kUsual;
	private BufferedImage kEscape;
	private BufferedImage kEndTurn;
	private BufferedImage kMovingSailor;
	private BufferedImage kSailorCapacity;
	private BufferedImage kSelectplayer;
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
		this.zoneWidth = MenuView.getWindowWidth() / model.WIDTH;
		this.zoneHeight = MenuView.getWindowHeight() / model.HEIGHT;

		// Tableau représentant les images des joueurs
		player = new BufferedImage[model.getNbPlayer()];

		// Chargement des images
		try {
			sand = ImageIO.read(new File("assets/textures/sand.jpg"));
			water = ImageIO.read(new File("assets/textures/water.jpg"));
			deepWater = ImageIO.read(new File("assets/textures/deepWater.jpg"));
			heliport = ImageIO.read(new File("assets/heliport.png"));
			for (int i = 0; i < player.length; i++) {
				player[i] = ImageIO.read(new File(model.getPlayer(i).pathImage()));
			}
			border = ImageIO.read(new File("assets/border.png"));
			escapeBorder = ImageIO.read(new File("assets/escapeBorder.png"));
			specialBorder = ImageIO.read(new File("assets/specialBorder.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load images");
			easyDraw = true;
		} catch (InvalidPlayerId e) {
			e.printStackTrace();
			System.out.println("Failed to load images due to bad player ID");
		}

		// Load keyBinding images
		try {
			kSailor = ImageIO.read(new File("assets/keyBinding/kSailor.png"));
			kPilot = ImageIO.read(new File("assets/keyBinding/kPilot.png"));
			kUsual = ImageIO.read(new File("assets/keyBinding/kUsual.png"));
			kEscape = ImageIO.read(new File("assets/keyBinding/kEscape.png"));
			kEndTurn = ImageIO.read(new File("assets/keyBinding/kEndTurn.png"));
			kMovingSailor = ImageIO.read(new File("assets/keyBinding/kMovingSailor.png"));
			kSailorCapacity = ImageIO.read(new File("assets/keyBinding/kSailorCapacity.png"));
			kSelectplayer = ImageIO.read(new File("assets/keyBinding/kSelectPlayer.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load keyBinding");
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
		player(g);
		hud(g);
		if (model.getHelp()) {
			paintHelp(g, MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 6, 14 * MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 4);
		}
		if (model.gameIsLost() || model.gameIsWon()) {
			endGame(g);
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
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

			// Element
			switch (z.getNaturalElement()) {
			case AIR:
				if (!easyDraw) {
					g.drawImage(airAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(200, 200, 200));
					g.fillRect(x + zoneWidth / 4, y + zoneHeight / 4, zoneWidth / 2, zoneHeight / 2);
				}
				break;
			case EARTH:
				if (!easyDraw) {
					g.drawImage(earthAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(100, 60, 20));
					g.fillRect(x + zoneWidth / 4, y + zoneHeight / 4, zoneWidth / 2, zoneHeight / 2);
				}
				break;
			case FIRE:
				if (!easyDraw) {
					g.drawImage(fireAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(255, 0, 0));
					g.fillRect(x + zoneWidth / 4, y + zoneHeight / 4, zoneWidth / 2, zoneHeight / 2);
				}
				break;
			case WATER:
				if (!easyDraw) {
					g.drawImage(waterAnim.display(), x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(0, 255, 255));
					g.fillRect(x + zoneWidth / 4, y + zoneHeight / 4, zoneWidth / 2, zoneHeight / 2);
				}
				break;
			}

			// Heliport
			if (z.isHeliport()) {
				if (!easyDraw) {
					g.drawImage(heliport, x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(255, 255, 255));
					g.drawLine(x + zoneWidth / 4, y + zoneHeight / 8, x + zoneWidth / 4, y + 6 * zoneHeight / 4);
					g.drawLine(x + 3 * zoneWidth / 4, y + zoneHeight / 8, x + 3 * zoneWidth / 4, y + 6 * zoneHeight / 4);
					g.drawLine(x + zoneWidth / 4, y + zoneHeight / 2, x + 3 * zoneWidth / 4, y + zoneHeight / 2);
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
			if (controller.getCurrentIdPlayer() == model.getCurrentIdPlayer() && model.getPlayer(controller.getCurrentIdPlayer()).getNbAction() > 0 && !controller.getSpecialcapacity()) {
				if (model.getMovePossibilitiesCurrentPlayer().contains(model.getZone(x / zoneWidth, y / zoneHeight))) {
					if (!easyDraw) {
						g.drawImage(border, x, y, zoneWidth, zoneHeight, this);
					} else {
						g.setColor(new Color(0, 255, 0));
						g.drawLine(x, y, x + zoneWidth - 1, y);
						g.drawLine(x + zoneWidth - 1, y, x + zoneWidth - 1, y + zoneHeight - 1);
						g.drawLine(x + zoneWidth - 1, y + zoneHeight - 1, x, y + zoneHeight - 1);
						g.drawLine(x, y + zoneHeight - 1, x, y);
					}
				}
			}
		} catch (InvalidPlayerId e1) {
			System.out.println("Error on player ID");
			e1.printStackTrace();
		}

		// Border escape
		if (controller.getRunFromDeath()) {
			if (model.getMovePossibilitiesPlayer(controller.getCurrentIdPlayer()).contains(model.getZone(x / zoneWidth, y / zoneHeight))) {
				if (!easyDraw) {
					g.drawImage(escapeBorder, x, y, zoneWidth, zoneHeight, this);
				} else {
					g.setColor(new Color(255, 0, 255));
					g.drawLine(x, y, x + zoneWidth - 1, y);
					g.drawLine(x + zoneWidth - 1, y, x + zoneWidth - 1, y + zoneHeight - 1);
					g.drawLine(x + zoneWidth - 1, y + zoneHeight - 1, x, y + zoneHeight - 1);
					g.drawLine(x, y + zoneHeight - 1, x, y);
				}
			}
		}

		// Special border
		if (controller.getSpecialcapacity() || controller.getPilotTurn()) {
			Zone pilotZone = controller.getSpecialZone();
			if (!easyDraw) {
				if (pilotZone != null) {
					g.drawImage(specialBorder, pilotZone.x * zoneWidth, pilotZone.y * zoneHeight, zoneWidth, zoneHeight, this);
				}
			} else {
				if (pilotZone != null) {
					g.setColor(new Color(255, 0, 0));
					g.drawLine(x, y, x + zoneWidth - 1, y);
					g.drawLine(x + zoneWidth - 1, y, x + zoneWidth - 1, y + zoneHeight - 1);
					g.drawLine(x + zoneWidth - 1, y + zoneHeight - 1, x, y + zoneHeight - 1);
					g.drawLine(x, y + zoneHeight - 1, x, y);
				}
			}
		}

		// Sailor moving border
		try {
			if (model.getPlayer(controller.getCurrentIdPlayer()).getNbAction() > 0 && controller.getSailorMoving()) {
				if (model.getPlayer(controller.getSailorPlayerId() % model.getNbPlayer()).getMovePossibilities().contains(model.getZone(x / zoneWidth, y / zoneHeight))) {
					if (!easyDraw) {
						g.drawImage(specialBorder, x, y, zoneWidth, zoneHeight, this);
					} else {
						g.setColor(new Color(255, 0, 0));
						g.drawLine(x, y, x + zoneWidth - 1, y);
						g.drawLine(x + zoneWidth - 1, y, x + zoneWidth - 1, y + zoneHeight - 1);
						g.drawLine(x + zoneWidth - 1, y + zoneHeight - 1, x, y + zoneHeight - 1);
						g.drawLine(x, y + zoneHeight - 1, x, y);
					}
				}
			}
		} catch (InvalidPlayerId e1) {
			System.out.println("Error on player ID");
			e1.printStackTrace();
		}
	}

	/**
	 * Dessine les joueur
	 * 
	 * @param g un graphic
	 */
	private void player(Graphics g) {
		// Player
		try {
			for (int i = 0; i < model.getNbPlayer(); i++) {
				if (model.getPlayer(i).isAlive()) {
					if (!easyDraw) {
						g.drawImage(player[i], model.getPositionPlayer(i).x * zoneWidth, model.getPositionPlayer(i).y * zoneHeight, zoneWidth, zoneHeight, this);
					} else {
						g.setFont(new Font("TimesRoman", Font.PLAIN, zoneWidth / 12));
						g.setColor(new Color(255, 255, 255));
						g.drawString(model.getPlayer(i).getName(), model.getPositionPlayer(i).x * (zoneWidth - 1) + zoneWidth / 8, model.getPositionPlayer(i).y * (zoneHeight - 1) + zoneHeight / 4 + i * zoneHeight / 8);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error on Player ID");
			e.printStackTrace();
		}

	}

	/**
	 * Dessine tout l'hud
	 * 
	 * @param g un graphic
	 */
	private void hud(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("TimesRoman", Font.PLAIN, MenuView.getWindowWidth() / 32));
		paintTurn(g, MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 24);
		paintNbAction(g, 12 * MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 24);
		paintKey(g, MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 12);
		paintArtefact(g, MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 8);
	}

	/**
	 * Dessine le nombre d'action restante pour le joueur courant
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	private void paintNbAction(Graphics g, int x, int y) {
		try {
			g.drawString("Actions Left: " + Integer.toString(model.getPlayer(model.getCurrentIdPlayer()).getNbAction()), x, y);
		} catch (InvalidPlayerId e) {
			System.out.println("Error on Player ID");
			e.printStackTrace();
		}
	}

	/**
	 * Dessine le nombre de tour a une coordonnée specific
	 * 
	 * @param g un graphic
	 * @param x the x position
	 * @param y the y position
	 */
	private void paintTurn(Graphics g, int x, int y) {
		g.drawString("Turn: " + Integer.toString(model.getTurn()), x, y);
	}

	/**
	 * Dessine le nombre de tour a une coordonnée specific
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	private void paintKey(Graphics g, int x, int y) {
		try {
			g.drawString("Keys: " + model.getPlayer(model.getCurrentIdPlayer()).getKeys().toString(), x, y);
		} catch (InvalidPlayerId e) {
			System.out.println("Error on Player ID");
			e.printStackTrace();
		}
	}

	/**
	 * Dessine le nombre de tour a une coordonnée specific
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	private void paintArtefact(Graphics g, int x, int y) {
		g.drawString("Artefacts: " + Artefact.getFoundArtefacts().toString(), x, y);
	}

	/**
	 * Dessine l'affiche des touches
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param w la largeur
	 * @param h la hauteur
	 */
	private void paintHelp(Graphics g, int x, int y, int w, int h) {
		try {
			// Escape
			if (controller.getRunFromDeath()) {
				g.drawImage(kEscape, x, y, w, h, this);
			}
			// Sailor
			else if (controller.getSailorTurn()) {
				if (controller.getSpecialcapacity()) {
					g.drawImage(kSelectplayer, x, y, w, h, this);
				} else {
					if (model.getPlayer(model.getCurrentIdPlayer()).getNbAction() > 0) {
						if (controller.getSailorMoving()) {
							g.drawImage(kSailorCapacity, x, y, w, h, this);
						} else {
							g.drawImage(kSailor, x, y, w, h, this);
						}
					} else {
						if (controller.getSailorMoving()) {
							g.drawImage(kMovingSailor, x, y, w, h, this);
						} else {
							g.drawImage(kEndTurn, x, y, w, h, this);
						}
					}
				}

			}
			// Pilot
			else if (controller.getPilotTurn()) {
				if (model.getPlayer(model.getCurrentIdPlayer()).getNbAction() > 0) {
					g.drawImage(kPilot, x, y, w, h, this);
				} else {
					g.drawImage(kEndTurn, x, y, w, h, this);
				}
			}
			// Usual
			else {
				if (model.getPlayer(model.getCurrentIdPlayer()).getNbAction() > 0) {
					g.drawImage(kUsual, x, y, w, h, this);
				} else {
					g.drawImage(kEndTurn, x, y, w, h, this);
				}
			}

		} catch (InvalidPlayerId e) {
			System.out.println("Error on Player ID");
			e.printStackTrace();
		}
	}

	/**
	 * Dessine la fin du jeu en victoire ou en défaite
	 * 
	 * @param g un graphique
	 */
	public void endGame(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2d.setColor(new Color(100, 100, 255));
		g2d.fillRect(0, 0, MenuView.getWindowWidth(), MenuView.getWindowHeight());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g.setFont(new Font("TimesRoman", Font.BOLD, MenuView.getWindowWidth() / 8));
		if (model.gameIsLost()) {
			g.setColor(new Color(255, 50, 50));
			drawCenteredString("Game is Lost!", MenuView.getWindowWidth(), MenuView.getWindowHeight(), g);
		} else if (model.gameIsWon()) {
			g.setColor(new Color(50, 255, 50));
			drawCenteredString("Game is Won!", MenuView.getWindowWidth(), MenuView.getWindowHeight(), g);
		}
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("TimesRoman", Font.PLAIN, MenuView.getWindowWidth() / 24));
		drawCenteredString("Press Enter to quit ", MenuView.getWindowWidth(), (int) (1.2 * MenuView.getWindowHeight()), g);
	}

	private void drawCenteredString(String s, int w, int h, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (w - fm.stringWidth(s)) / 2;
		int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(s, x, y);
	}
}
