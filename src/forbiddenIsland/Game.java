package forbiddenIsland;
import model.IslandModel;
import model.Player;
import view.IslandView;

/**
 * @author maxime & baptiste
 * @apiNote Lance le jeu
 */
public class Game {
	public static final long BEGINTIME = System.nanoTime();
	
	public static void main(String[] args) {
		String map = "***********\n"
	        	   + "*****--****\n"
	        	   + "**~w-W-****\n"
	        	   + "**--EE--e~*\n"
	        	   + "*~a-A---~**\n"
	        	   + "*~~----~***\n"
	        	   + "**--FF--~**\n"
	        	   + "**~--F--***\n"
	        	   + "****~-f****\n"
	        	   + "***********\n";
				
		IslandModel model = new IslandModel(map);
		
		Player p0 = new Player(model, "Bob", model.getZone(5, 5));
		
		IslandView view = new IslandView(model, 1000, 1000);
	}
}
