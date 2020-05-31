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
	private boolean runFromDeath;
	private boolean specialcapacity;
	private boolean sailorMoving;
	private Zone specialZone;
	private int sailorPlayerId;
	private int currentIdPlayer;
	private ArrayList<Player> playerInDanger;
	private int playerInDangerIndex;

	/**
	 * @param m: Référence au modèle
	 */
	public Controller(IslandModel m) {
		this.m = m;
		this.currentIdPlayer = m.nextIdPlayer();
		try {
			specialZone = m.getPositionPlayer(currentIdPlayer);
		} catch (InvalidPlayerId e) {
			System.out.println("Error width player ID");
			e.printStackTrace();
		}
		this.runFromDeath = false;
		this.specialcapacity = false;
		this.sailorPlayerId = -1;
		this.playerInDangerIndex = 0;
	}

	@Override
	/**
	 * @apiNote Méthode appelée lorsqu'un évènement survient
	 * @param e: Évènement à traiter
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			if (!sailorMoving) {
				// Move
				try {
					if ((m.getPlayer(currentIdPlayer).getNbAction() > 0 || runFromDeath) && !specialcapacity) {
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

				// Skip turn && end special capacity
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !runFromDeath) {
					// Disable special capacity
					if (specialcapacity) {
						disableCapacity();
					}
					// Skip turn
					else {
						endTurn();
					}

				}
			} else {
				try {
					moveSailor(e);
				} catch (InvalidPlayerId e1) {
					System.out.println("Error width player ID");
					e1.printStackTrace();
				}
			}

			// Special capacity
			try {
				if (m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) {
					// Use
					if (specialcapacity && m.getPlayer(currentIdPlayer) instanceof Sailor) {
						selectPlayer(e);
					}

					// Active
					if (e.getKeyChar() == 'e' || e.getKeyChar() == 'E') {
						if (!specialcapacity) {
							// Sailor
							if (m.getPlayer(currentIdPlayer) instanceof Sailor) {
								specialcapacity = true;
							}
						} else {
							specialcapacity = false;
						}
						if(sailorMoving) {
							sailorMoving = false;
							specialZone = m.getPositionPlayer(currentIdPlayer);
						}
					}
				}
			} catch (InvalidPlayerId e2) {
				System.out.println("Error width player ID");
				e2.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * Fini le tour
	 */
	private void endTurn() {
		try {
			if (m.getPlayer(currentIdPlayer) instanceof Pilot && !specialZone.equals(m.getPositionPlayer(currentIdPlayer))) {
				m.getPlayer(currentIdPlayer).move(specialZone);
			} else {
				// Chercher une clef
				m.findKeyElementPlayer(currentIdPlayer);

				// Inondation
				m.floodRandom();
				// Liste des joueurs en danger
				playerInDanger = m.getPlayersToSave();
				if (playerInDanger.isEmpty()) {
					// Tour suivant
					currentIdPlayer = m.nextIdPlayer();
					// Reset zone
					specialZone = m.getPositionPlayer(currentIdPlayer);
					// get new id for sailor zone
					sailorPlayerId = m.getPlayer(currentIdPlayer).getId();
					// Disable fleeing
					runFromDeath = false;
				} else {
					currentIdPlayer = playerInDanger.get(0).getId();
					runFromDeath = true;
				}
			}
		} catch (InvalidPlayerId e1) {
			e1.printStackTrace();
			System.out.println("Error on player Id");
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
		// Pilot not fleeing
		if (m.getPlayer(currentIdPlayer) instanceof Pilot && !runFromDeath) {
			Zone tempZone = null;
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
				tempZone = specialZone.neighbour(Move.UP);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
				tempZone = specialZone.neighbour(Move.DOWN);
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
				tempZone = specialZone.neighbour(Move.RIGHT);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
				tempZone = specialZone.neighbour(Move.LEFT);
			}

			// Affect the new zone if valid
			if (m.getPlayer(currentIdPlayer).movePossibilities().contains(tempZone)) {
				specialZone = tempZone;
			}
		} else {
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
		if ((m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) && !specialcapacity) {
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
		if ((m.getPlayer(currentIdPlayer).getNbAction() > 0 && !runFromDeath) && !specialcapacity) {
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
			try {
				specialZone = m.getPositionPlayer(currentIdPlayer);
				sailorPlayerId = m.getPlayer(currentIdPlayer).getId();
			} catch (InvalidPlayerId e) {
				System.out.println("Error width player ID");
				e.printStackTrace();
			}
		} else {
			currentIdPlayer = playerInDanger.get(playerInDangerIndex).getId();
		}
	}

	/**
	 * Selectionne un joueur pour le sailor
	 * 
	 * @param e un évenement clavier
	 * @throws InvalidPlayerId
	 */
	private void selectPlayer(KeyEvent e) throws InvalidPlayerId {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
			sailorPlayerId++;
			specialZone = m.getPositionPlayer(Math.abs(sailorPlayerId % m.getNbPlayer()));
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
			sailorPlayerId--;
			specialZone = m.getPositionPlayer(Math.abs(sailorPlayerId % m.getNbPlayer()));
		}

	}

	/**
	 * Désactive le boolean de la capacité spéciale et active l'effet de la capacité
	 */
	private void disableCapacity() {
		// Sailor
		try {
			if (m.getPlayer(currentIdPlayer) instanceof Sailor) {
				if (sailorPlayerId != currentIdPlayer) {
					sailorMoving = true;
				}
			}
		} catch (InvalidPlayerId e1) {
			System.out.println("Error width player ID");
			e1.printStackTrace();
		}
		specialcapacity = false;
	}

	/**
	 * Déplace le joueur
	 * 
	 * @param e un évenement clavier
	 * @return true le joueur a bouger false sinon
	 * @throws InvalidPlayerId
	 */
	private boolean moveSailor(KeyEvent e) throws InvalidPlayerId {
		if (m.getPlayer(currentIdPlayer).getNbAction() > 0) {
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
				((Sailor) m.getPlayer(currentIdPlayer)).movePlayerSailorPower(sailorPlayerId % m.getNbPlayer(), Move.UP);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
				((Sailor) m.getPlayer(currentIdPlayer)).movePlayerSailorPower(sailorPlayerId % m.getNbPlayer(), Move.DOWN);
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
				((Sailor) m.getPlayer(currentIdPlayer)).movePlayerSailorPower(sailorPlayerId % m.getNbPlayer(), Move.RIGHT);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
				((Sailor) m.getPlayer(currentIdPlayer)).movePlayerSailorPower(sailorPlayerId % m.getNbPlayer(), Move.LEFT);
			}
		} else {
			sailorMoving = false;
		}
		return false;
	}

	public boolean getRunFromDeath() {
		return this.runFromDeath;
	}

	public int getCurrentIdPlayer() {
		return this.currentIdPlayer;
	}

	public boolean getSpecialcapacity() {
		return this.specialcapacity;
	}

	public boolean getPilotTurn() {
		try {
			return m.getPlayer(currentIdPlayer) instanceof Pilot;
		} catch (InvalidPlayerId e) {
			System.out.println("Error width player ID");
			e.printStackTrace();
			return false;
		}
	}

	public Zone getSpecialZone() {
		return this.specialZone;
	}

	public boolean getSailorMoving() {
		return this.sailorMoving;
	}

	public int getSailorPlayerId() {
		return this.sailorPlayerId;
	}

}
