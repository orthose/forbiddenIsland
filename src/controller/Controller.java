package controller;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import model.IslandModel;
import model.Move;
import model.Player;
import model.Player.InvalidPlayerId;
import model.Zone;

/**
 * @author maxime et baptiste
 * @apiNote Le contrôleur chargé de modifier le modèle en fonction des
 *          évènements reçus
 */
public class Controller implements KeyEventDispatcher {

	private IslandModel m; // Référence au modèle
	private static int nbAction;
	private static boolean runFromDeath = false;
	private int currentIdPlayer;
	private ArrayList<Player> playerInDanger;
	private int playerInDangerIndex;

	/**
	 * @param m: Référence au modèle
	 */
	public Controller(IslandModel m) {
		this.m = m;
		this.currentIdPlayer = m.nextIdPlayer();
		this.playerInDangerIndex = 0;
	}

	@Override
	/**
	 * @apiNote Méthode appelée lorsqu'un évènement survient
	 * @param e: Évènement à traiter
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		// Récupération du nombre d'actions du joueur courant
		try {
			Controller.nbAction = this.m.getPlayer(this.currentIdPlayer).getNbAction();
		}
		catch (InvalidPlayerId e1) {
			e1.printStackTrace();
			System.out.println("Error on player Id");
		}
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			// move
			if (nbAction > 0 || runFromDeath) {
				try {
					if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
						if (m.movePlayer(currentIdPlayer, Move.UP)) {
							//nbAction--;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
						if (m.movePlayer(currentIdPlayer, Move.DOWN)) {
							//nbAction--;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
						if (m.movePlayer(currentIdPlayer, Move.RIGHT)) {
							//nbAction--;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
						if (m.movePlayer(currentIdPlayer, Move.LEFT)) {
							//nbAction--;
						}
					}
					// Escape from death
					if (nbAction < 0) {
						playerInDangerIndex++;
						if (playerInDangerIndex >= playerInDanger.size()) {
							nbAction = 3;
							runFromDeath = false;
							currentIdPlayer = m.nextIdPlayer();
						} else {
							currentIdPlayer = playerInDanger.get(playerInDangerIndex).getId();
						}
					}

				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Error while moving player in controller");
				}
			}

			// Dry
			if (nbAction > 0) {
				try {
					if (e.getKeyChar() == '5') {
						if (m.dryPlayer(currentIdPlayer, Move.NONE)) {
							//nbAction--;
						}
					} else if (e.getKeyChar() == '8') {
						if (m.dryPlayer(currentIdPlayer, Move.UP)) {
							//nbAction--;
						}
					} else if (e.getKeyChar() == '2') {
						if (m.dryPlayer(currentIdPlayer, Move.DOWN)) {
							//nbAction--;
						}
					} else if (e.getKeyChar() == '6') {
						if (m.dryPlayer(currentIdPlayer, Move.RIGHT)) {
							//nbAction--;
						}
					} else if (e.getKeyChar() == '4') {
						if (m.dryPlayer(currentIdPlayer, Move.LEFT)) {
							//nbAction--;
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Error while drying a zone in controller");
				}
			}

			// Artefact
			if (nbAction > 0) {
				try {
					if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
						m.findArtefactPlayer(currentIdPlayer);
						//nbAction--;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Error while catching artefact in controller");
				}
			}

			// Skip turn
			if (e.getKeyCode() == KeyEvent.VK_ENTER && !runFromDeath) {
				nbAction = 0;
				// Chercher une clef
				try {
					m.findKeyElementPlayer(currentIdPlayer);
				} catch (InvalidPlayerId e1) {
					e1.printStackTrace();
					System.out.println("Error on player Id");
				}
				// Inondation
				m.floodRandom();
				// Liste des joueurs en danger
				playerInDanger = m.getPlayersToSave();
				if (playerInDanger.isEmpty()) {
					// Reset le nombre d'action
					//nbAction = 3;
					// Tour suivant
					currentIdPlayer = m.nextIdPlayer();
					runFromDeath = false;
				} else {
					currentIdPlayer = playerInDanger.get(0).getId();
					runFromDeath = true;
				}

			}
		}
		return false;
	}

	public static int getNbAction() {
		return Controller.nbAction;
	}

	public static boolean getRunFromDeath() {
		return Controller.runFromDeath;
	}

}
