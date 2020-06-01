package controller;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import forbiddenIsland.Game;
import model.Menu;

/**
 * @author baptiste
 * @apiNote Le contrôleur chargé de modifier le menu en fonction des évènements
 *          reçus
 */
public class MenuController implements KeyEventDispatcher {
	private Menu menu;
	private int stage2Line;

	public MenuController(Menu menu) {
		this.menu = menu;
		this.stage2Line = 0;
	}

	@Override
	/**
	 * @apiNote Méthode appelée lorsqu'un évènement survient
	 * @param e: Évènement à traiter
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			if (menu.getStage() == 0) {
				selectPlayerAndLevel(e);
			} else {
				playerCreation(e);
			}
			validMenu(e);
		}

		return false;
	}

	/**
	 * Choisi le nombre joueur
	 * 
	 * @param e une interaction clavier
	 */
	private void selectPlayerAndLevel(KeyEvent e) {
		// Player
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			menu.increaseNbPlayer();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			menu.decreaseNbPlayer();
		}
		// Level
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			menu.increaseLevel();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			menu.decreaseLevel();
		}
	}

	/**
	 * Choisi le nombre joueur
	 * 
	 * @param e une interaction clavier
	 */
	private void playerCreation(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (stage2Line > 0) {
				stage2Line--;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (stage2Line < 2) {
				stage2Line++;
			}
		}

		// Gender
		if (stage2Line == 0) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				menu.setGender(false);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				menu.setGender(true);
			}
		}

		// Name
		if (stage2Line == 1) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				menu.deleteLetter();
			} else if ((e.getKeyCode() >= 65 && e.getKeyCode() <= 90) || (e.getKeyCode() >= 48 && e.getKeyCode() <= 57)) {
				menu.addLetter(e.getKeyChar());
			}
		}

		if (stage2Line == 2) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				menu.increaseRole();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				menu.decreaseRole();
			}
		}
	}

	/**
	 * Valide le Menu
	 * 
	 * @param e une interaction clavier
	 */
	private void validMenu(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			menu.increaseStage();
			stage2Line = 0;
			if (menu.isValid()) {
				Game.getManager().removeKeyEventDispatcher(this);

			}
		}
	}

	/**
	 * Renvoie la ligne actuellement sélectionné
	 * 
	 * @return la ligne de l'état 2 du menu
	 */
	public int getStage2Line() {
		return this.stage2Line;
	}

}
