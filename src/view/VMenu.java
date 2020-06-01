package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import controller.MenuController;
import model.Menu;
import util.Observer;

public class VMenu extends JPanel implements Observer {
	private Menu menu;
	private MenuController menuController;

	public VMenu(Menu menu, MenuController menuController) {
		this.menu = menu;
		this.menuController = menuController;

		// On enregistre la vue en tant qu' observateur du menu
		menu.addObserver(this);

	}

	/**
	 * Met à jour le dessin
	 */
	public void update() {
		repaint();
	}

	/**
	 * Dessine le menu
	 * 
	 * @param g un graphic
	 */
	public void paintComponent(Graphics g) {
		super.repaint();
		if (menu.getStage() == 0) {
			menuStage1(g);
		} else if (menu.getStage() > 0 && menu.getStage() < menu.getNbPlayer() + 1) {
			menuStage2(g);
		}
	}

	/**
	 * Dessine l'étape 1 du menu
	 * 
	 * @param g un graphic
	 */
	private void menuStage1(Graphics g) {
		// Background
		g.setColor(new Color(255, 150, 30));
		g.fillRect(0, 0, MenuView.getWindowWidth(), MenuView.getWindowHeight());

		// Title
		g.setFont(new Font("TimesRoman", Font.PLAIN, MenuView.getWindowWidth() / 16));
		g.setColor(new Color(255, 255, 255));
		drawCenteredString("Forbidden island", MenuView.getWindowWidth(), MenuView.getWindowHeight() / 8, g);

		// Info
		g.setFont(new Font("TimesRoman", Font.BOLD, MenuView.getWindowWidth() / 60));
		g.setColor(new Color(255, 0, 0));
		drawCenteredString("At any moment of the game you can press 'h' to get help about keybinding", MenuView.getWindowWidth(), MenuView.getWindowHeight() / 3, g);
		drawCenteredString("A n'importe quel moment du jeu vous pouvez appuyer sur 'h' pour avoir de l'aide sur l'assignement des touches", MenuView.getWindowWidth(), MenuView.getWindowHeight() / 3 + MenuView.getWindowHeight() / 16, g);
		g.setColor(new Color(0, 0, 255));
		g.drawString("Up and down to increase or decrease the number of player", MenuView.getWindowWidth() / 16, 5 * MenuView.getWindowHeight() / 16);
		g.drawString("Right and left to increase or decrease the level of difficulty", MenuView.getWindowWidth() / 16, 5 * MenuView.getWindowHeight() / 16 + MenuView.getWindowHeight() / 32);
		g.drawString("Enter to valid all input", MenuView.getWindowWidth() / 16, 5 * MenuView.getWindowHeight() / 16 + MenuView.getWindowHeight() / 16);

		// Players
		g.setFont(new Font("TimesRoman", Font.BOLD, MenuView.getWindowWidth() / 32));
		g.setColor(new Color(255, 255, 255));
		g.drawString("How many players?", MenuView.getWindowWidth() / 8, MenuView.getWindowHeight() / 2);
		g.fillRoundRect(9 * MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 2 - MenuView.getWindowHeight() / 20, MenuView.getWindowWidth() / 12, MenuView.getWindowHeight() / 12, MenuView.getWindowWidth() / 32,
				MenuView.getWindowWidth() / 32);
		g.setColor(new Color(0, 0, 0));
		g.drawString(Integer.toString(menu.getNbPlayer()), 9 * MenuView.getWindowWidth() / 16 + MenuView.getWindowWidth() / 32, MenuView.getWindowHeight() / 2);

		// Niveau
		g.setColor(new Color(255, 255, 255));
		g.drawString("What level?", MenuView.getWindowWidth() / 8, 3 * MenuView.getWindowHeight() / 4);
		g.fillRoundRect(9 * MenuView.getWindowWidth() / 16, 3 * MenuView.getWindowHeight() / 4 - MenuView.getWindowHeight() / 20, MenuView.getWindowWidth() / 12, MenuView.getWindowHeight() / 12, MenuView.getWindowWidth() / 32,
				MenuView.getWindowWidth() / 32);
		g.setColor(new Color(0, 0, 0));
		g.drawString(Integer.toString(menu.getLevel()), 9 * MenuView.getWindowWidth() / 16 + MenuView.getWindowWidth() / 32, 3 * MenuView.getWindowHeight() / 4);
	}

	/**
	 * Dessine l'étape 2 du menu
	 * 
	 * @param g un graphic
	 */
	private void menuStage2(Graphics g) {
		// Background
		g.setColor(new Color(255, 150, 30));
		g.fillRect(0, 0, MenuView.getWindowWidth(), MenuView.getWindowHeight());

		// Title
		g.setFont(new Font("TimesRoman", Font.PLAIN, MenuView.getWindowWidth() / 16));
		g.setColor(new Color(255, 255, 255));
		drawCenteredString("Player Creation: " + menu.getStage(), MenuView.getWindowWidth(), MenuView.getWindowHeight() / 8, g);

		// Gender
		g.setFont(new Font("TimesRoman", Font.BOLD, MenuView.getWindowWidth() / 32));
		if (menuController.getStage2Line() == 0) {
			g.setColor(new Color(0, 255, 0));
		} else {
			g.setColor(new Color(255, 255, 255));
		}
		g.drawString("Gender:", MenuView.getWindowWidth() / 8, MenuView.getWindowHeight() / 4);
		// Male
		g.setColor(new Color(50, 50, 200));
		g.fillRoundRect(6 * MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 4 - MenuView.getWindowHeight() / 20, MenuView.getWindowWidth() / 12, MenuView.getWindowHeight() / 12, MenuView.getWindowWidth() / 32,
				MenuView.getWindowWidth() / 32);
		// Female
		g.setColor(new Color(200, 50, 150));
		g.fillRoundRect(10 * MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 4 - MenuView.getWindowHeight() / 20, MenuView.getWindowWidth() / 12, MenuView.getWindowHeight() / 12, MenuView.getWindowWidth() / 32,
				MenuView.getWindowWidth() / 32);
		if (menu.isMale()) {
			g.setColor(new Color(255, 255, 255));
		} else {
			g.setColor(new Color(0, 0, 0));
		}
		g.drawString("H", 6 * MenuView.getWindowWidth() / 16 + MenuView.getWindowWidth() / 32, MenuView.getWindowHeight() / 4);
		if (!menu.isMale()) {
			g.setColor(new Color(255, 255, 255));
		} else {
			g.setColor(new Color(0, 0, 0));
		}
		g.drawString("F", 10 * MenuView.getWindowWidth() / 16 + MenuView.getWindowWidth() / 32, MenuView.getWindowHeight() / 4);

		// Name
		if (menuController.getStage2Line() == 1) {
			g.setColor(new Color(0, 255, 0));
		} else {
			g.setColor(new Color(255, 255, 255));
		}
		g.drawString("Name:", MenuView.getWindowWidth() / 8, MenuView.getWindowHeight() / 2);
		g.setColor(new Color(255, 255, 255));
		g.fillRoundRect(6 * MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 2 - MenuView.getWindowHeight() / 20, MenuView.getWindowWidth() / 3, MenuView.getWindowHeight() / 12, MenuView.getWindowWidth() / 32,
				MenuView.getWindowWidth() / 32);
		g.setColor(new Color(0, 0, 0));
		g.drawString(menu.getName(), 6 * MenuView.getWindowWidth() / 16 + MenuView.getWindowWidth() / 32, MenuView.getWindowHeight() / 2);

		// Role
		if (menuController.getStage2Line() == 2) {
			g.setColor(new Color(0, 255, 0));
		} else {
			g.setColor(new Color(255, 255, 255));
		}
		g.drawString("Role:", MenuView.getWindowWidth() / 8, 3 * MenuView.getWindowHeight() / 4);
		g.setColor(new Color(255, 255, 255));
		g.fillRoundRect(7 * MenuView.getWindowWidth() / 16, 3 * MenuView.getWindowHeight() / 4 - MenuView.getWindowHeight() / 20, 3 * MenuView.getWindowWidth() / 16, MenuView.getWindowHeight() / 12, MenuView.getWindowWidth() / 32,
				MenuView.getWindowWidth() / 32);
		g.setColor(new Color(0, 0, 0));
		g.drawString(menu.getRoleName(), 7 * MenuView.getWindowWidth() / 16 + MenuView.getWindowWidth() / 32, 3 * MenuView.getWindowHeight() / 4);

	}

	private void drawCenteredString(String s, int w, int h, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (w - fm.stringWidth(s)) / 2;
		int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(s, x, y);
	}
}
