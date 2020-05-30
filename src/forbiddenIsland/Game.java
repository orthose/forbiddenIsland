package forbiddenIsland;

import java.awt.KeyboardFocusManager;
import javax.swing.JFrame;
import javax.swing.JTextField;
import controller.Controller;
import model.IslandModel;
import model.Pilot;
import model.Player;
import model.Sailor;
import model.Sexe;
import view.IslandView;

/**
 * @author maxime & baptiste
 * @apiNote Lance le jeu
 */
public class Game extends JFrame {
	public static final long BEGINTIME = System.nanoTime();
	
	public Game(Controller controller) {
		  add(new JTextField());
	      //System.out.println("test");
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
		
		// Affichage console
		if (args.length == 1 && "-v".equals(args[0])) {
			model.setVerbose(true);
		}
		
		Player p0 = new Pilot(model, "Pilot", Sexe.MALE);
		Player p1 = new Sailor(model, "Alexis", Sexe.FEMALE);
		
		Controller controller = new Controller(model);	
		
		IslandView view = new IslandView(model, controller, 1000, 1000);
		
		Game game = new Game(controller);
	}
}
