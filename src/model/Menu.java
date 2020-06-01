package model;

import forbiddenIsland.Game;
import util.LevelLoader;
import util.Observable;
import view.MenuView;

/**
 * @author baptiste
 * @apiNote Menu du jeu
 */
public class Menu extends Observable {
	private IslandModel model;
	private boolean menu;
	// Stage 1
	private int nbPlayer;
	private int level;
	private int stage;
	private boolean valid;
	// Stage 2
	private boolean male;
	private StringBuilder name;
	private int role;

	public Menu() {
		super();
		this.menu = true;
		this.stage = 0;
		this.valid = false;
		// Stage 1
		this.nbPlayer = 1;
		this.level = 0;
		// Stage 2
		this.male = false;
		this.name = new StringBuilder("");
		this.role = 0;
	}

	/**
	 * Renvoie si le menu est actif
	 * 
	 * @return true sie le menue st actif false sinon
	 */
	public boolean isMenu() {
		return this.menu;
	}

	/**
	 * 
	 * @param other un boolean qui détermine l'activation du boolean menu
	 */
	public void setMenu(boolean other) {
		this.menu = other;
	}

	/**
	 * Renvoie le nombre de joueur
	 * 
	 * @return renvoie un entier représentant le nombre de joueur
	 */
	public int getNbPlayer() {
		return this.nbPlayer;
	}

	/**
	 * Augemente le nombre de joueuer
	 */
	public void increaseNbPlayer() {
		if (nbPlayer < 4) {
			nbPlayer++;
		}
	}

	/**
	 * Diminue le nombre de joueur
	 */
	public void decreaseNbPlayer() {
		if (nbPlayer > 1) {
			nbPlayer--;
		}
	}

	/**
	 * Renvoie le niveau de difficulté
	 * 
	 * @return renvoie un entier représentant le niveau de difficulté
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * Augemente le niveau de difficulté
	 */
	public void increaseLevel() {
		if (level < 11) {
			level++;
		}
	}

	/**
	 * Diminue le niveau de difficulté
	 */
	public void decreaseLevel() {
		if (level > 0) {
			level--;
		}
	}

	/**
	 * Renvoie l'état du menu
	 * 
	 * @return l'état du menu
	 */
	public int getStage() {
		return this.stage;
	}

	/**
	 * Augemente l'état du menu
	 */
	public void increaseStage() {
		if (stage < nbPlayer + 1) {
			stage++;
			if (stage > 1) {
				Sexe s;
				if (male) {
					s = Sexe.MALE;
				} else {
					s = Sexe.FEMALE;
				}

				switch (Math.abs(this.role % 5)) {
				case 1:
					new Pilot(model, name.toString(), s);
					break;
				case 2:
					new Engineer(model, name.toString(), s);
					break;
				case 3:
					new Sailor(model, name.toString(), s);
					break;
				case 4:
					new Diver(model, name.toString(), s);
					break;
				default:
					new Player(model, name.toString(), s);
					break;
				}

				resetStage2();
			}
		}
		if (stage == nbPlayer + 1) {
			valid = true;
			MenuView.getFrame().dispose();
			Game.iniGame(model);
		}
		// Ini model
		if (stage == 1) {
			// Charge la carte du niveau spécifié
			String map = LevelLoader.load(level);
			// Création du model
			model = new IslandModel(map);
		}
	}

	/**
	 * Remet a zero les attributs de l'état 2 du menu
	 */
	private void resetStage2() {
		male = false;
		name = new StringBuilder("");
		role = 0;
	}

	/**
	 * Renvoie si le menu est validé (fini)
	 * 
	 * @return true si le menu est validé false sinon
	 */
	public boolean isValid() {
		return this.valid;
	}

	/**
	 * Renvoie le genre du personnage
	 * 
	 * @return true si homme false si femme
	 */
	public boolean isMale() {
		return this.male;
	}

	/**
	 * Renvoie le nom
	 * 
	 * @return renvoie un string qui représente le nom du joueur
	 */
	public String getName() {
		return this.name.toString();
	}

	/**
	 * Renvoie le rôle
	 * 
	 * @return un entier représentant le role
	 */
	public int getRole() {
		return this.role;
	}

	/**
	 * Renvoie le nom du rôle
	 * 
	 * @return un String représentant le role
	 */
	public String getRoleName() {
		switch (Math.abs(this.role % 5)) {
		case 1:
			return "Pilot";
		case 2:
			return "Engineer";
		case 3:
			return "Sailor";
		case 4:
			return "Diver";
		default:
			return "None";
		}
	}

	/**
	 * Précise le genre d'un joueur
	 * 
	 * @param other true pour homme, false pour femme
	 */
	public void setGender(boolean other) {
		this.male = other;
	}

	/**
	 * Ajoute une lettre au nom
	 * 
	 * @param other une lettre a ajouter
	 */
	public void addLetter(char other) {
		if (name.length() < 12) {
			this.name.append(other);
		}
	}

	/**
	 * Supprime une lettre au nom
	 */
	public void deleteLetter() {
		if (name.length() > 0) {
			this.name.deleteCharAt(name.length() - 1);
		}
	}

	/**
	 * Fait déffiler les rôles vers le haut
	 */
	public void increaseRole() {
		role++;
	}

	/**
	 * Fait déffiler les roles vers le bas
	 */
	public void decreaseRole() {
		role--;
	}

}
