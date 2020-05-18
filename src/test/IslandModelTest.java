package test;

import model.IslandModel;
import static org.junit.Assert.*;
import org.junit.*;
import model.DoubleDirectionMap;

/**
 * @author maxime
 * @apiNote Tests unitaires automatiques
 * de la classe IslandModel
 */
public class IslandModelTest {
	
	final int mapWidth = 21, mapHeight = 10;
	IslandModel m1, m2, m3, m4, m5;
	
	String map1 = "*********************\n"
		        + "**********---********\n"
		        + "*******--------******\n"
		        + "***--------------****\n"
		        + "*****-------------***\n"
		        + "***-----------------*\n"
		        + "******------------***\n"
		        + "******----------*****\n"
		        + "*********----********\n"
		        + "*********************";
	
	String map2 = "*********************\n"
	        	+ "********~~---~*******\n"
	        	+ "*****~~--------~~****\n"
	        	+ "***~~~--------~~~****\n"
	        	+ "**~~~-----------~~~**\n"
	        	+ "*~~~~~-----------~~~*\n"
	        	+ "****~~------------~**\n"
	        	+ "****~~----------~~***\n"
	        	+ "*******~~---~~*******\n"
	        	+ "*********************";
	
	String map3 = "*********************\n"
        		+ "********~~---~*******\n"
        		+ "*****~~--------~~****\n"
        		+ "***~~~--------~~~****\n"
        		+ "**~~~-----H-----~~~**\n"
        		+ "*~~~~~-----------~~~*\n"
        		+ "****~~------------~**\n"
        		+ "****~~----------~~***\n"
        		+ "*******~~---~~*******\n"
        		+ "*********************";
	
	String map4 = "*********************\n"
    			+ "********~~-E-~*******\n"
    			+ "*****~~--------~~****\n"
    			+ "***~~~A-------~~~****\n"
    			+ "**~~~-----H-----~~~**\n"
    			+ "*~~~~~--------W--~~~*\n"
    			+ "****~~------------~**\n"
    			+ "****~~--F-------~~***\n"
    			+ "*******~~---~~*******\n"
    			+ "*********************";
	
	String map5 = "*********************\n"
				+ "********~~-E-~*******\n"
				+ "*****~~------e-~~****\n"
				+ "***~~~A-------~~~****\n"
				+ "**~~~a----H-----~~~**\n"
				+ "*~~~~~--f-----W--~~~*\n"
				+ "****~~-------h----~**\n"
				+ "****~~--F--w----~~***\n"
				+ "*******~~---~~*******\n"
				+ "*********************";
	
	// Doit renvoyer erreur
	String map6 = "*********************\n"
				+ "********~~---~*******\n"
				+ "*****~~--------~~****\n"
				+ "***~~~--------~~~****\n"
				+ "**~~~-----H-----~~~**\n"
				+ "*~~~~~-----------~~~*\n"
				+ "****~~-------h----~**\n"
				+ "****~~----------~~***\n"
				+ "*******~~---~~*******\n"
				+ "**************P******";
	
	// Doit renvoyer erreur
	String map7 = "*********************\n"
				+ "********~~---~*******\n"
				+ "*****~~--------~~****\n"
				+ "***~~~--------~~~****\n"
				+ "**~~~-----H-----~~~**\n"
				+ "*~~~~~-----------~~~*\n"
				+ "****~~-------h----~**\n"
				+ "****~~----------~~***\n"
				+ "*******~~---~~*******\n"
				+ "********************";
	
	// Doit renvoyer erreur
	String map8 = "*********************\n"
				+ "********~~---~*******\n"
				+ "*****~~--------~~****\n"
				+ "***~~~--------~~~****\n"
				+ "**~~~-----H-----~~~**\n"
				+ "*~~~~~-----------~~~*\n"
				+ "****~~-------h----~**\n"
				+ "****~~----------~~***\n"
				+ "*******~~---~~*******\n"
				+ "*******************X*";
	
	@Test
	public void invalidInitialization() {
		// On ne peut pas initialiser un joueur directement
		// La case avec "P" sera initialisée en tant  que
		// que zone standard sans renvoyer d'erreur
		IslandModel m6 = new IslandModel(map6);
		int res = 0;
		try {
			// La dernière ligne a une taille différente
			// de la taille des autres cela lève une exception
			IslandModel m7 = new IslandModel(map7);
		}
		catch (IllegalArgumentException e) {
			res++;
		}
		try {
			// La case "X" n'est pas reconnue ce qui lève
			// une exception
			IslandModel m8 = new IslandModel(map8);
		}
		catch (IllegalArgumentException e) {
			res++;
		}
		assertEquals(2, res);
	}
	
	@Before
	public void initializeModel() {
		m1 = new IslandModel(map1);
		m2 = new IslandModel(map2);
		m3 = new IslandModel(map3);
		m4 = new IslandModel(map4);
		m5 = new IslandModel(map5);
	}
	
	@Test
	public void verifyInitialization() {
		
		DoubleDirectionMap<String, IslandModel> allModels = 
				new DoubleDirectionMap<String, IslandModel>();
		allModels.put(map1, m1);
		allModels.put(map2, m2);
		allModels.put(map3, m3);
		allModels.put(map4, m4);
		allModels.put(map5, m5);
		
		for (String map : allModels.allKeys()) {
			IslandModel m = allModels.encode(map);
			System.out.println(m+"\n");
			assertEquals(mapWidth, m.WIDTH);
			assertEquals(mapHeight, m.HEIGHT);
			assertTrue(map.equals(m.toString()));
		}
		
	}

}











