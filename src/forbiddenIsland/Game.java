package forbiddenIsland;

import java.awt.KeyboardFocusManager;
import javax.swing.JFrame;
import javax.swing.JTextField;
import controller.Controller;
import model.IslandModel;
import model.Player;
import view.IslandView;

/**
 * @author maxime & baptiste
 * @apiNote Lance le jeu
 */
public class Game extends JFrame {
	public static final long BEGINTIME = System.nanoTime();
	
	public Game(Controller controller) {
		  add(new JTextField());
	      System.out.println("test");
	      KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	      manager.addKeyEventDispatcher(controller);
	}

	public static void main(String[] args) {		
		String map = "***********\n"
	        	   + "*****--****\n"
	        	   + "**~w-W-****\n"
	        	   + "**--EE--e~*\n"
	        	   + "*~a-A---~**\n"
	        	   + "*~~-H--~***\n"
	        	   + "**--FF--~**\n"
	        	   + "**~--F--***\n"
	        	   + "****~-f****\n"
	        	   + "***********\n";	
		
		IslandModel model = new IslandModel(map);
		
		Player p0 = new Player(model, "Bob", model.getZone(5, 5));
		Player p1 = new Player(model, "Alexis", model.getZone(3, 3));
		
		Controller controller = new Controller(model);	
		
		IslandView view = new IslandView(model, 1000, 1000);
		
		Game game = new Game(controller);
	}
}
