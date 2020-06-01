package forbiddenIsland;

import java.awt.KeyboardFocusManager;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import controller.Controller;
import controller.MenuController;
import model.IslandModel;
import model.Menu;
import model.Pilot;
import model.Player;
import model.Sailor;
import model.Sexe;
import util.LevelLoader;
import util.MusicPlayer;
import model.Sexe;
import view.IslandView;
import view.MenuView;

/**
 * @author maxime & baptiste
 * @apiNote Lance le jeu
 */
public class Game extends JFrame {
	// Clock
	public static final long BEGINTIME = System.nanoTime();
	// Command
	private static ArrayList<String> cmd;
    // Key manager 
	private static KeyboardFocusManager manager;

	public Game(MenuController menuController, String[] args) {
		manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(menuController);
	}

	public static void main(String[] args) {
		// Facilite le traitement de la ligne de commande
		cmd = new ArrayList<String>(args.length);
		for (int i = 0; i < args.length; i++) {
			cmd.add(args[i]);
		}

		Menu menu = new Menu();
		MenuController menuController = new MenuController(menu);

		Game game = new Game(menuController, args);

		MenuView vMenu = new MenuView(menu, menuController);
	}

	public static void iniGame(IslandModel model, int level) {
		// Joue la piste audio spécifiée
		if (!(cmd.contains("-ns") || cmd.contains("--no-sound"))) {
			MusicPlayer.play(level);
		}

		// Affichage console
		if (cmd.contains("-v") || cmd.contains("--verbose")) {
			model.setVerbose(true);
		}

		Controller controller = new Controller(model);

		IslandView view = new IslandView(model, controller);

		manager.addKeyEventDispatcher(controller);
	}

	public static KeyboardFocusManager getManager() {
		return manager;

	}
}
