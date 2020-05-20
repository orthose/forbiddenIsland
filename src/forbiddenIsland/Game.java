package forbiddenIsland;
import model.IslandModel;
import view.IslandView;

/**
 * @author maxime & baptiste
 * @apiNote Lance le jeu
 */
public class Game {
	public static void main(String[] args) {
		String map = "*********************\n"
	        	+ "********~~---~*******\n"
	        	+ "*****~~--------~~****\n"
	        	+ "***~~~--------~~~****\n"
	        	+ "**~~~-----------~~~**\n"
	        	+ "*~~~~~-----------~~~*\n"
	        	+ "****~~------------~**\n"
	        	+ "****~~----------~~***\n"
	        	+ "*******~~---~~*******\n"
	        	+ "*********************";
				
		IslandModel model = new IslandModel(map);
		IslandView view = new IslandView(model, 500, 500);
	}
}
