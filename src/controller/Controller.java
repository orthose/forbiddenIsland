package controller;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import model.IslandModel;
import model.Move;
import model.Pilot;
import model.Player;
import model.Player.InvalidPlayerId;
import model.Sailor;
import model.Zone;

/**
 * @author maxime et baptiste
 * @apiNote Le contrôleur chargé de modifier le modèle en fonction des
 *          évènements reçus
 */
public class Controller implements KeyEventDispatcher {

	private IslandModel m; // Référence au modèle
	private static boolean runFromDeath = false;
	private static boolean Specialcapacity = false;
	private static Zone pilotZone;
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
				if ((m.getPlayer(currentIdPlayer).getNbAction() > 0 || runFromDeath) && !Specialcapacity) {
					// Is moving ?
					boolean move = move(e);

					// Escape from death
					if (runFromDeath && move) {
						flee();
					}
				}
			} catch (InvalidPlayerId e2) {
				System.out.println("Error width player ID");
				e2.printStackTrace();
			}

			// Dry
			try {
				dry(e);
			} catch (InvalidPlayerId e3) {
				System.out.println("Error width player ID");
				e3.printStackTrace();
			}

			// Artefact
			try {
				artefact(e);
			} catch (InvalidPlayerId e2) {
				System.out.println("Error width player ID");
				e2.printStackTrace();
			}

			// Special capacity
			try {
				if (m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) {
					// Use
					if (m.getPlayer(currentIdPlayer) instanceof Pilot) {
						movePilot(e);
					}

					// Active
					if (e.getKeyChar() == 'e' || e.getKeyChar() == 'E') {
						if (!Specialcapacity) {
							// Pilot
							if (m.getPlayer(currentIdPlayer) instanceof Pilot) {
								Specialcapacity = true;
								pilotZone = m.getPositionPlayer(currentIdPlayer);

							}
							// Sailor
							else if (m.getPlayer(currentIdPlayer) instanceof Sailor) {
								Specialcapacity = true;
							}
						} else {
							Specialcapacity = false;
						}
					}
				}
			} catch (InvalidPlayerId e2) {
				System.out.println("Error width player ID");
				e2.printStackTrace();
			}

			// Skip turn && end special capacity
			if (e.getKeyCode() == KeyEvent.VK_ENTER && !runFromDeath) {
				// Disable special capacity
				if (Specialcapacity) {
					disableCapacity();
				}
				// Skip turn
				else {
					endTurn();
				}

			}
		}
		return false;
	}

	/**
	 * Fini le tour
	 */
	private void endTurn() {
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

	/**
	 * Déplace le joueur
	 * 
	 * @param e un évenement clavier
	 * @return true le joueur a bouger false sinon
	 * @throws InvalidPlayerId
	 */
	private boolean move(KeyEvent e) throws InvalidPlayerId {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
			if (m.movePlayer(currentIdPlayer, Move.UP)) {
				return true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
			if (m.movePlayer(currentIdPlayer, Move.DOWN)) {
				return true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
			if (m.movePlayer(currentIdPlayer, Move.RIGHT)) {
				return true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
			if (m.movePlayer(currentIdPlayer, Move.LEFT)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Assecher une zone
	 * 
	 * @param e un évenement clavier
	 * @throws InvalidPlayerId
	 */
	private void dry(KeyEvent e) throws InvalidPlayerId {
		if ((m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) && !Specialcapacity) {
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
	}

	/**
	 * chercher un artefact
	 * 
	 * @param e un évenement clavier
	 * @throws InvalidPlayerId
	 */
	private void artefact(KeyEvent e) throws InvalidPlayerId {
		if ((m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) && !Specialcapacity) {
			try {
				if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
					m.findArtefactPlayer(currentIdPlayer);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("Error while catching artefact in controller");
			}
		}
	}

	/**
	 * S'enfuir
	 */
	private void flee() {
		playerInDangerIndex++;
		if (playerInDangerIndex >= playerInDanger.size()) {
			runFromDeath = false;
			currentIdPlayer = m.nextIdPlayer();
		} else {
			currentIdPlayer = playerInDanger.get(playerInDangerIndex).getId();
		}
	}

	/**
	 * Bouge la zone du pilot
	 * 
	 * @param e un évenement clavier
	 * @throws InvalidPlayerId
	 */
	private void movePilot(KeyEvent e) throws InvalidPlayerId {
		Zone tempZone = null;
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
			tempZone = m.getZone(pilotZone.x, pilotZone.y - 1);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
			tempZone = m.getZone(pilotZone.x, pilotZone.y + 1);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
			tempZone = m.getZone(pilotZone.x + 1, pilotZone.y);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
			tempZone = m.getZone(pilotZone.x - 1, pilotZone.y);
		}

		// Affect the new zone if in the map and submerged
		if (tempZone != null && !tempZone.isSubmergedLevel()) {
			if(tempZone.x >= 0 && tempZone.x <= m.WIDTH-1 && tempZone.y >= 0 && tempZone.y <= m.HEIGHT-1) {
				pilotZone = tempZone;
			}
		}
	}

	/**
	 * Désactive le boolean de la capacité spéciale et active l'effet de la capacité
	 */
	private void disableCapacity() {
		// Pilot
		try {
			if (m.getPlayer(currentIdPlayer) instanceof Pilot) {
				m.getPlayer(currentIdPlayer).move(pilotZone);
			}
			// Sailor
			else if (m.getPlayer(currentIdPlayer) instanceof Sailor) {

			}
		} catch (InvalidPlayerId e1) {
			System.out.println("Error width player ID");
			e1.printStackTrace();
		}
		Specialcapacity = false;
	}

	public static boolean getRunFromDeath() {
		return Controller.runFromDeath;
	}

	public int getCurrentIdPlayer() {
		return this.currentIdPlayer;
	}

	public static boolean getSpecialcapacity() {
		return Controller.Specialcapacity;
	}

	public static Zone getPilotZone() {
		return Controller.pilotZone;
	}

}
