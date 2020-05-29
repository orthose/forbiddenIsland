package controller;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import model.IslandModel;
import model.Move;
import model.Player;
import model.Player.InvalidPlayerId;

/**
 * @author maxime et baptiste
 * @apiNote Le contrôleur chargé de modifier le modèle en fonction des
 *          évènements reçus
 */
public class Controller implements KeyEventDispatcher {

	private IslandModel m; // Référence au modèle
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
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			// Move
			try {
				if (m.getPlayer(currentIdPlayer).getNbAction() > 0/* nbAction > 0 */ || runFromDeath) {
					try {
						boolean move = false;
						if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
							if (m.movePlayer(currentIdPlayer, Move.UP)) {
								move = true;
							}
						} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
							if (m.movePlayer(currentIdPlayer, Move.DOWN)) {
								move = true;
							}
						} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
							if (m.movePlayer(currentIdPlayer, Move.RIGHT)) {
								move = true;
							}
						} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
							if (m.movePlayer(currentIdPlayer, Move.LEFT)) {
								move = true;
							}
						}
						// Escape from death
						if (runFromDeath && move) {
							playerInDangerIndex++;
							if (playerInDangerIndex >= playerInDanger.size()) {
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
			} catch (InvalidPlayerId e2) {
				System.out.println("Error width player ID");
				e2.printStackTrace();
			}

			// Dry
			try {
				if (m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) {
					try {
						if (e.getKeyChar() == '5') {
							m.dryPlayer(currentIdPlayer, Move.NONE);
						} else if (e.getKeyChar() == '8') {
							m.dryPlayer(currentIdPlayer, Move.UP);
						} else if (e.getKeyChar() == '2') {
							m.dryPlayer(currentIdPlayer, Move.DOWN);
						} else if (e.getKeyChar() == '6') {
							m.dryPlayer(currentIdPlayer, Move.RIGHT);
						} else if (e.getKeyChar() == '4') {
							m.dryPlayer(currentIdPlayer, Move.LEFT);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("Error while drying a zone in controller");
					}
				}
			} catch (InvalidPlayerId e3) {
				System.out.println("Error width player ID");
				e3.printStackTrace();
			}

			// Artefact
			try {
				if (m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) {
					try {
						if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
							m.findArtefactPlayer(currentIdPlayer);
							// nbAction--;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("Error while catching artefact in controller");
					}
				}
			} catch (InvalidPlayerId e2) {
				System.out.println("Error width player ID");
				e2.printStackTrace();
			}

			// Skip turn
			if (e.getKeyCode() == KeyEvent.VK_ENTER && !runFromDeath) {
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

	public static boolean getRunFromDeath() {
		return Controller.runFromDeath;
	}
	
	public int getCurrentIdPlayer() {
		return this.currentIdPlayer;
	}

}
