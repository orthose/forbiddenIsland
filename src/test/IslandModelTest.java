package test;

import model.*;
import model.Player.InvalidPlayerId;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;

/**
 * @author maxime
 * @apiNote Tests unitaires automatiques
 * de la classe IslandModel
 */
public class IslandModelTest {
	
	final int mapWidth = 21, mapHeight = 10;
	IslandModel m0, m1, m2, m3, m4, m5;
	
	String map0 = "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------\n"
	        	+ "---------------------";
	
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
	
	//----------------------------------------------------//
	// La valeur de verbose en d??but de m??thode de test   //
	// doit ??tre pass??e ?? true pour avoir l'affichage     //
	// console de la m??thode concern??e                    //
	//----------------------------------------------------//
	
	@Test
	public void invalidInitialization() {
		// On ne peut pas initialiser un joueur directement
		// La case avec "P" sera initialis??e en tant  que
		// que zone standard sans renvoyer d'erreur
		IslandModel m6 = new IslandModel(map6);
		int res = 0;
		try {
			// La derni??re ligne a une taille diff??rente
			// de la taille des autres cela l??ve une exception
			IslandModel m7 = new IslandModel(map7);
		}
		catch (IllegalArgumentException e) {
			res++;
		}
		try {
			// La case "X" n'est pas reconnue ce qui l??ve
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
		m0 = new IslandModel(map0);
		m1 = new IslandModel(map1);
		m2 = new IslandModel(map2);
		m3 = new IslandModel(map3);
		m4 = new IslandModel(map4);
		m5 = new IslandModel(map5);
	}
	
	@Test
	public void verifyInitialization() {
		
		boolean verbose = false;
		
		DoubleDirectionMap<String, IslandModel> allModels = 
				new DoubleDirectionMap<String, IslandModel>();
		allModels.put(map0, m0);
		allModels.put(map1, m1);
		allModels.put(map2, m2);
		allModels.put(map3, m3);
		allModels.put(map4, m4);
		allModels.put(map5, m5);
		
		for (String map : allModels.allKeys()) {
			IslandModel m = allModels.encode(map);
			if (verbose) System.out.println(m+"\n");
			assertEquals(mapWidth, m.WIDTH);
			assertEquals(mapHeight, m.HEIGHT);
			assertTrue(map.equals(m.toString()));
		}	
	}
	
	@Test
	public void addPlayerTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout de joueurs au mod??le m1
		Player p0 = new Player(m1, "Maxime", m1.getZone(10, 5));
		if (verbose) System.out.println(m1+"\n");
		assertEquals(0, m1.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		Player p1 = new Player(m1, "Baptiste", m1.getZone(12, 5));
		if (verbose) System.out.println(m1+"\n");
		assertEquals(1, m1.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		Player p2 = new Player(m1, "Ludisia", m1.getZone(14, 5));
		if (verbose) System.out.println(m1+"\n");
		assertEquals(2, m1.getPlayer(2).getId());
		assertEquals(2, p2.getId());
		
		// Acc??s ?? un joueur n'existant pas
		int res = 0;
		try {
			Player p3 = m1.getPlayer(3);
		}
		catch (InvalidPlayerId e) {
			if (verbose) System.out.println(e+"\n");
			res++;
		}
		assertEquals(1, res);
		
		// Ajout d'un joueur au mod??le m5
		// On constate que l'id suit ceux
		// des joueurs pr??c??demment cr????s
		// Mais que le joueur est enregistr??
		// comme premier joueur du mod??le m5
		Player p3 = new Player(m5, "Am??lie", m5.getZone(10, 5));
		if (verbose) System.out.println(m5+"\n");
		assertEquals(3, m5.getPlayer(0).getId());
		assertEquals(3, p3.getId());
		
		// Pour rem??dier ?? cela si l'on recommence 
		// une partie on peut supprimer tous les joueurs
		// En utilisant l'interface statique de IslandModel
		// On se place dans le mod??le m4
		IslandModel.reset();
		Player p4 = new Player(m4, "Am??lie", m4.getZone(10, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p4.getId());
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void movePlayerTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout d'un joueur au mod??le m4
		Player p0 = new Player(m4, "Am??lie", m4.getZone(10, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// Coordonn??es du joueur
		int x = m4.getPositionPlayer(0).x;
		int y = m4.getPositionPlayer(0).y;
		
		// D??placements possibles
		ArrayList<Zone> possibilities = m4.movePossibilitiesPlayer(0);
		assertEquals(5, possibilities.size());
		assertTrue(possibilities.contains(m4.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m4.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m4.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x, y)));
		
		// R??alisation de d??placements
		assertFalse(m4.getPositionPlayer(0).isHeliport());
		assertTrue(m4.movePlayer(0, Move.UP));
		assertTrue(m4.getZone(x, y - 1).equals(m4.getPositionPlayer(0)));
		assertTrue(m4.getPositionPlayer(0).isHeliport());
		if (verbose) System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.DOWN));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		if (verbose) System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.NONE));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		if (verbose) System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.LEFT));
		assertTrue(m4.getZone(x - 1, y).equals(m4.getPositionPlayer(0)));
		if (verbose) System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.RIGHT));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		if (verbose) System.out.println(m4+"\n");
		
		// T??l??portation du joueur en bas de l'??le
		m4.getPlayer(0).move(m4.getZone(10, 8));
		x = 10; y = 8;
		if (verbose) System.out.println(m4+"\n");
		
		// D??placements possibles
		possibilities = m4.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m4.getZone(x, y - 1)));
		assertFalse(possibilities.contains(m4.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m4.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x, y)));
		
		// On ne peut pas se d??placer dans la zone submerg??e
		assertFalse(m4.movePlayer(0, Move.DOWN));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		if (verbose) System.out.println(m4+"\n");
		
		// En revanche on peut se d??placer dans une zone inond??e
		assertTrue(m4.movePlayer(0, Move.RIGHT)); x++;
		assertTrue(m4.getPositionPlayer(0).isNormalLevel());
		assertTrue(m4.movePlayer(0, Move.RIGHT)); x++;
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		assertTrue(m4.getPositionPlayer(0).isFloodedLevel());
		if (verbose) System.out.println(m4+"\n");
		
		// Inondation de la case adjacente en haut
		m4.getZone(x, y - 1).flood();
		assertTrue(m4.getZone(x, y - 1).isFloodedLevel());
		m4.getZone(x, y - 1).flood();
		assertTrue(m4.getZone(x, y - 1).isSubmergedLevel());
		if (verbose) System.out.println(m4+"\n");
		
		// D??placements possibles
		possibilities = m4.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertFalse(possibilities.contains(m4.getZone(x, y - 1)));
		assertFalse(possibilities.contains(m4.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m4.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x, y)));
		assertTrue(m4.canEscapePlayer(0));
		
		// Inondation de la case adjacente gauche
		m4.getZone(x - 1, y).flood();
		assertTrue(m4.getZone(x - 1, y).isFloodedLevel());
		m4.getZone(x - 1, y).flood();
		assertTrue(m4.getZone(x - 1, y).isSubmergedLevel());
		if (verbose) System.out.println(m4+"\n");
		
		// D??placements possibles
		possibilities = m4.movePossibilitiesPlayer(0);
		assertEquals(2, possibilities.size());
		assertFalse(possibilities.contains(m4.getZone(x, y - 1)));
		assertFalse(possibilities.contains(m4.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m4.getZone(x + 1, y)));
		assertFalse(possibilities.contains(m4.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x, y)));
		assertTrue(m4.canEscapePlayer(0));
		
		// Inondation de la case adjacente droite
		m4.getZone(x + 1, y).flood();
		assertTrue(m4.getZone(x + 1, y).isSubmergedLevel());
		if (verbose) System.out.println(m4+"\n");
		
		// D??placements possibles
		possibilities = m4.movePossibilitiesPlayer(0);
		assertEquals(1, possibilities.size());
		assertFalse(possibilities.contains(m4.getZone(x, y - 1)));
		assertFalse(possibilities.contains(m4.getZone(x, y + 1)));
		assertFalse(possibilities.contains(m4.getZone(x + 1, y)));
		assertFalse(possibilities.contains(m4.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x, y)));
		assertFalse(m4.canEscapePlayer(0));
		
		IslandModel.reset();
		
		//------------------------------------------------------------//
		// Tests des cas limites sur les bords de la carte            //
		//------------------------------------------------------------//
		
		// Ajout d'un joueur au mod??le m0 en haut ?? gauche
		new Player(m0, "Am??lie", m0.getZone(0, 0));
		if (verbose) System.out.println(m0+"\n");
		x = 0; y = 0;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// T??l??portation du joueur en haut ?? droite
		m0.getPlayer(0).move(m0.getZone(mapWidth - 1, 0));
		if (verbose) System.out.println(m0+"\n");
		x = mapWidth - 1; y = 0;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// T??l??portation du joueur en bas ?? gauche
		m0.getPlayer(0).move(m0.getZone(0, mapHeight - 1));
		if (verbose) System.out.println(m0+"\n");
		x = 0; y = mapHeight - 1;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// T??l??portation du joueur en bas ?? droite
		m0.getPlayer(0).move(m0.getZone(mapWidth - 1, mapHeight - 1));
		if (verbose) System.out.println(m0+"\n");
		x = mapWidth - 1; y = mapHeight - 1;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// T??l??portation du joueur sur le bord du haut
		m0.getPlayer(0).move(m0.getZone(mapWidth / 2, 0));
		if (verbose) System.out.println(m0+"\n");
		x = mapWidth / 2; y = 0;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// T??l??portation du joueur sur le bord de gauche
		m0.getPlayer(0).move(m0.getZone(0, mapHeight / 2));
		if (verbose) System.out.println(m0+"\n");
		x = 0; y = mapHeight / 2;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// T??l??portation du joueur sur le bord de droite
		m0.getPlayer(0).move(m0.getZone(mapWidth - 1, mapHeight / 2));
		if (verbose) System.out.println(m0+"\n");
		x = mapWidth - 1; y = mapHeight / 2;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// T??l??portation du joueur sur le bord du bas
		m0.getPlayer(0).move(m0.getZone(mapWidth / 2, mapHeight - 1));
		if (verbose) System.out.println(m0 + "\n");
		x = mapWidth / 2; y = mapHeight - 1;
		
		// D??placements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void dryPlayerTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout d'un joueur au mod??le m4
		Player p0 = new Player(m4, "Am??lie", m4.getZone(10, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// V??rification des zones ass??chables
		assertFalse(m4.canDryPlayer(0, Move.UP));
		assertFalse(m4.canDryPlayer(0, Move.DOWN));
		assertFalse(m4.canDryPlayer(0, Move.LEFT));
		assertFalse(m4.canDryPlayer(0, Move.RIGHT));
		assertFalse(m4.canDryPlayer(0, Move.NONE));
		
		// D??placement du joueur sur sa gauche 4 fois
		for (int i = 0; i < 4; i++) {
			assertTrue(m4.movePlayer(0, Move.LEFT));
		}
		if (verbose) System.out.println(m4 + "\n");
		
		// V??rification des zones ass??chables
		assertFalse(m4.canDryPlayer(0, Move.UP));
		assertFalse(m4.canDryPlayer(0, Move.DOWN));
		assertTrue(m4.canDryPlayer(0, Move.LEFT));
		assertFalse(m4.canDryPlayer(0, Move.RIGHT));
		assertFalse(m4.canDryPlayer(0, Move.NONE));
		
		// On ass??che la zone ass??chable ?? gauche
		// On tente d'ass??cher des zones d??j?? ass??ch??es
		assertFalse(m4.dryPlayer(0, Move.UP));
		assertFalse(m4.dryPlayer(0, Move.DOWN));
		assertTrue(m4.dryPlayer(0, Move.LEFT));
		assertFalse(m4.dryPlayer(0, Move.RIGHT));
		assertFalse(m4.dryPlayer(0, Move.NONE));
		if (verbose) System.out.println(m4 + "\n");
		
		// On inonde certaines zones
		m4.getZone(6, 5).flood();
		m4.getZone(5, 5).flood();
		m4.getZone(5, 5).flood();
		m4.getZone(6, 6).flood();
		if (verbose) System.out.println(m4 + "\n");
		
		// On ass??che les zones ass??chables
		// On tente d'ass??cher des zones non-ass??chable
		assertFalse(m4.dryPlayer(0, Move.UP));
		assertTrue(m4.dryPlayer(0, Move.DOWN));
		assertFalse(m4.dryPlayer(0, Move.LEFT));
		assertFalse(m4.dryPlayer(0, Move.RIGHT));
		assertTrue(m4.dryPlayer(0, Move.NONE));
		if (verbose) System.out.println(m4 + "\n");
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void floodRandomTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout d'un joueur au mod??le m0
		Player p0 = new Player(m1, "Maxime", m1.getZone(10, 5));
		if (verbose) System.out.println(m1+"\n");
		assertEquals(0, m1.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// On inonde al??atoirement plusieurs fois
		for (int i = 0; i < 100; i++) {
			m1.floodRandom();
			ArrayList<Player> threatenedPlayers = m1.getPlayersToSave();
			if (verbose) System.out.println(m1+"\n");
			if (! threatenedPlayers.isEmpty()) {
			    assertTrue(m1.getPositionPlayer(0).isSubmergedLevel());
			    assertFalse(m1.getPositionPlayer(0).isFloodedLevel());
				p0.kill();
				if (verbose) System.out.println(p0.getName() + " was killed !");
				break;
			}
			else {
				assertFalse(m1.getPositionPlayer(0).isSubmergedLevel());
			}
		}
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void keyAndArtefactTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout d'un joueur au mod??le m0
		Player p0 = new Player(m4, "Am??lie", m4.getZone(10, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// On tente de r??cup??rer plusieurs cl??s
		boolean success = false;
		do {
			success = m4.findKeyElementPlayer(0);
			
		} while (! success);
		
		NaturalElement gotKey = p0.getKeys().get(0).getEl();
		switch (gotKey) {
		
		case AIR: 
			for (int i = 0; i < 2; i++) {
				m4.movePlayer(0, Move.UP);
			}
			for (int i = 0; i < 4; i++) {
				m4.movePlayer(0, Move.LEFT);
			}
			if (verbose) System.out.println(m4);
			assertEquals(NaturalElement.AIR, m4.getPositionPlayer(0).getNaturalElement());
			assertTrue(m4.findArtefactPlayer(0));
			assertEquals(NaturalElement.AIR, Artefact.getFoundArtefacts().get(0).getNaturalElement());
			break;
			
		case WATER:
			for (int i = 0; i < 4; i++) {
				m4.movePlayer(0, Move.RIGHT);
			}
			if (verbose) System.out.println(m4);
			assertEquals(NaturalElement.WATER, m4.getPositionPlayer(0).getNaturalElement());
			assertTrue(m4.findArtefactPlayer(0));
			assertEquals(NaturalElement.WATER, Artefact.getFoundArtefacts().get(0).getNaturalElement());
			break;
		
		case EARTH:
			for (int i = 0; i < 4; i++) {
				m4.movePlayer(0, Move.UP);
			}
			m4.movePlayer(0, Move.RIGHT);
			if (verbose) System.out.println(m4);
			assertEquals(NaturalElement.EARTH, m4.getPositionPlayer(0).getNaturalElement());
			assertTrue(m4.findArtefactPlayer(0));
			assertEquals(NaturalElement.EARTH, Artefact.getFoundArtefacts().get(0).getNaturalElement());
			break;
			
		case FIRE:
			for (int i = 0; i < 2; i++) {
				m4.movePlayer(0, Move.DOWN);
			}
			for (int i = 0; i < 2; i++) {
				m4.movePlayer(0, Move.LEFT);
			}
			if (verbose) System.out.println(m4);
			assertEquals(NaturalElement.FIRE, m4.getPositionPlayer(0).getNaturalElement());
			assertTrue(m4.findArtefactPlayer(0));
			assertEquals(NaturalElement.FIRE, Artefact.getFoundArtefacts().get(0).getNaturalElement());
			break;
		}
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
		
	}
	
	@Test
	public void turnTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout de joueurs au mod??le m4
		Player p0 = new Player(m4, "Maxime", m4.getZone(10, 5));
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		Player p1 = new Player(m4, "Baptiste", m4.getZone(12, 5));
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		Player p2 = new Player(m4, "Ludisia", m4.getZone(14, 5));
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(2, m4.getPlayer(2).getId());
		assertEquals(2, p2.getId());
		Player p3 = new Player(m4, "Am??lie", m4.getZone(8, 5));
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(3, m4.getPlayer(3).getId());
		assertEquals(3, p3.getId());
		
		// Le premier tour est le tour 0
		assertEquals(0, m4.getTurn());
		int id = m4.nextIdPlayer();
		assertTrue(0 <= id && id <= 3);

		// Test des tours successifs
		for (int i = 0; i < 10; i++) {
			if (verbose) System.out.println("id="+id);
			assertTrue(0 <= id && id <= 3);
			int nextId = m4.nextIdPlayer();
			assertTrue(nextId == id + 1 || nextId == 0);
			id = nextId;
		}
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void gameIsWonAndLostTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout de joueurs au mod??le m4
		Player p0 = new Player(m4, "Am??lie", m4.getZone(8, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		Player p1 = new Player(m4, "Maxime", m4.getZone(10, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		Player p2 = new Player(m4, "Baptiste", m4.getZone(12, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(2, m4.getPlayer(2).getId());
		assertEquals(2, p2.getId());
		Player p3 = new Player(m4, "Ludisia", m4.getZone(14, 5));
		if (verbose) System.out.println(m4+"\n");
		assertEquals(3, m4.getPlayer(3).getId());
		assertEquals(3, p3.getId());
		
		// Le jeu n'est ni perdu ni gagn??
		assertFalse(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		// On donne des cl??s de mani??re artificielle aux joueurs
		m4.getPlayer(0).getKeys().add(new KeyElement(NaturalElement.FIRE));
		m4.getPlayer(1).getKeys().add(new KeyElement(NaturalElement.AIR));
		m4.getPlayer(2).getKeys().add(new KeyElement(NaturalElement.EARTH));
		m4.getPlayer(3).getKeys().add(new KeyElement(NaturalElement.WATER));
		
		// Le jeu n'est ni perdu ni gagn??
		assertFalse(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		// On d??place les joueurs successivement
		for (int i = 0; i < 2; i++) {
			m4.movePlayer(0, Move.DOWN);
			if (verbose) System.out.println(m4+"\n");
		}
		for (int i = 0; i < 2; i++) {
			m4.movePlayer(1, Move.UP);
			if (verbose) System.out.println(m4+"\n");
		}
		for (int i = 0; i < 4; i++) {
			m4.movePlayer(1, Move.LEFT);
			if (verbose) System.out.println(m4+"\n");
		}
		for (int i = 0; i < 4; i++) {
			m4.movePlayer(2, Move.UP);
			if (verbose) System.out.println(m4+"\n");
		}
		m4.movePlayer(2, Move.LEFT);
		if (verbose) System.out.println(m4+"\n");
		
		// Le jeu n'est ni perdu ni gagn??
		assertFalse(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		// Les joueurs r??cup??rent les artefacts
		for (int i = 0; i < 4; i++) {
			m4.findArtefactPlayer(i);
		}
		
		// Le jeu n'est ni perdu ni gagn??
		assertFalse(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		// Les joueurs sont t??l??port??s ?? l'h??liport artificiellement
		Zone heliport = m4.getZone(10, 4);
		for (int i = 0; i < 4; i++) {
			m4.getPlayer(i).move(heliport);
		}
		if (verbose) System.out.println(m4);
		
		// Le jeu est gagn?? ?? ce stade !
		assertFalse(m4.gameIsLost());
		assertTrue(m4.gameIsWon());
		
		//----------------------------------------------------//
		// Perdre ?? cause des joueurs qui meurrent            //
		//----------------------------------------------------//
		
		// Tuer des joueurs fait perdre la partie ?
		for (int i = 0; i < 4; i++) {
			m4.getPlayer(i).kill();
			
			// Eh oui le jeu est perdu !
			assertTrue(m4.gameIsLost());
			assertFalse(m4.gameIsWon());
		}
		
		// R??initialisation du mod??le
		IslandModel.reset();
		m4 = new IslandModel(map4);
		
		// Ajout de joueurs au mod??le m4
		p0 = new Player(m4, "Am??lie", m4.getZone(8, 5));
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		p1 = new Player(m4, "Maxime", m4.getZone(10, 5));
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		p2 = new Player(m4, "Baptiste", m4.getZone(12, 5));
		assertEquals(2, m4.getPlayer(2).getId());
		assertEquals(2, p2.getId());
		p3 = new Player(m4, "Ludisia", m4.getZone(14, 5));
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(3, m4.getPlayer(3).getId());
		assertEquals(3, p3.getId());
		
		// Le jeu n'est ni perdu ni gagn??
		assertFalse(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		//----------------------------------------------------//
		// Perdre ?? cause de l'h??liport                       //
		//----------------------------------------------------//
		
		// On innonde l'h??liport artificiellement !
		heliport = m4.getZone(10, 4); // N??cessaire car nouvelle instance de m4
		heliport.flood();
		if (verbose) System.out.println(m4+"\n");
		heliport.flood();
		if (verbose) System.out.println(m4+"\n");
		assertTrue(heliport.isSubmergedLevel());
		
		// Le jeu est perdu on ne peut plus s'??chapper !
		assertTrue(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		// R??initialisation du mod??le
		IslandModel.reset();
		m4 = new IslandModel(map4);

		// Ajout de joueurs au mod??le m4
		p0 = new Player(m4, "Am??lie", m4.getZone(8, 5));
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		p1 = new Player(m4, "Maxime", m4.getZone(10, 5));
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		p2 = new Player(m4, "Baptiste", m4.getZone(12, 5));
		assertEquals(2, m4.getPlayer(2).getId());
		assertEquals(2, p2.getId());
		p3 = new Player(m4, "Ludisia", m4.getZone(14, 5));
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(3, m4.getPlayer(3).getId());
		assertEquals(3, p3.getId());

		// Le jeu n'est ni perdu ni gagn??
		assertFalse(m4.gameIsLost());
		assertFalse(m4.gameIsWon());

		//----------------------------------------------------//
		// Perdre ?? cause d'un artefact perdu                 //
		//----------------------------------------------------//
		
		// On inonde la zone de l'air
		Zone airZone = m4.getZone(6, 3);
		airZone.flood();
		if (verbose) System.out.println(m4+"\n");
		airZone.flood();
		if (verbose) System.out.println(m4+"\n");
		
		// Le jeu est perdu !
		assertTrue(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		// On inonde la zone de la terre
		Zone earthZone = m4.getZone(11, 1);
		earthZone.flood();
		if (verbose) System.out.println(m4+"\n");
		earthZone.flood();
		if (verbose) System.out.println(m4+"\n");
		
		// Le jeu est encore perdu !
		assertTrue(m4.gameIsLost());
		assertFalse(m4.gameIsWon());
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void pilotTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout de joueurs au mod??le m4
		// avec positions initialis??es al??atoirement
		Player p0 = new Pilot(m4, "Maxime", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		assertFalse(m4.getPositionPlayer(0).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(0).getNbAction());
		Player p1 = new Player(m4, "Baptiste", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		assertFalse(m4.getPositionPlayer(1).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(1).getNbAction());
		
		// V??rification des sp??cialit??s de pilote
		int nbMovePossibilities = m4.getPlayer(0).movePossibilities().size();
		assertTrue(nbMovePossibilities > 5);
		if (verbose) System.out.println("nbMovePossibilities="+nbMovePossibilities);
		nbMovePossibilities = m4.getPlayer(1).movePossibilities().size();
		assertTrue(nbMovePossibilities <= 5);
		if (verbose) System.out.println("nbMovePossibilities="+nbMovePossibilities);
		
		// D??placement du pilote par la m??thode de IslandModel
		Zone zone = m4.getZone(10, 3);
		assertTrue(m4.movePlayer(0, zone));
		assertEquals(Player.nbActionMax-1, m4.getPlayer(0).getNbAction());
		assertTrue(m4.getPositionPlayer(0).equals(zone));
		// Tandis qu'un joueur normal ne peut pas r??aliser cet exploit !
		if (! (m4.getPositionPlayer(1).equals(zone)
				|| m4.movePossibilitiesPlayer(1).contains(zone))) {
			Zone currentZone = m4.getPositionPlayer(1);
			assertTrue(m4.getPositionPlayer(1).equals(currentZone));
			assertFalse(m4.movePlayer(1, zone));
			assertTrue(m4.getPositionPlayer(1).equals(currentZone));
		}
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void engineerTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout de joueurs au mod??le m4
		// avec positions initialis??es al??atoirement
		Player p0 = new Engineer(m4, "Maxime", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		assertFalse(m4.getPositionPlayer(0).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(0).getNbAction());
		Player p1 = new Player(m4, "Baptiste", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		assertFalse(m4.getPositionPlayer(1).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(1).getNbAction());
		
		// V??rification des sp??cialit??s de l'ing??nieur
		int currentPlayerId = m4.nextIdPlayer();
		assertTrue(0 <= currentPlayerId && currentPlayerId <= 1);
		// On veut jouer avec l'ing??nieur
		if (currentPlayerId == 1) {
			currentPlayerId = m4.nextIdPlayer();
		}
		assertEquals(0, currentPlayerId);
		Player player = m4.getPlayer(currentPlayerId);
		Zone position = m4.getPositionPlayer(currentPlayerId);
		
		if (position.isNormalLevel()) {
			for (int i = 0; i < Engineer.nbActionDryAllowed; i++) {
				assertEquals(Player.nbActionMax, player.getNbAction());
				position.flood();
				assertTrue(m4.dryPlayer(currentPlayerId, Move.NONE));
				assertEquals(Player.nbActionMax, player.getNbAction());
			}
			position.flood();
			assertTrue(m4.dryPlayer(currentPlayerId, Move.NONE));
			assertEquals(Player.nbActionMax-1, player.getNbAction());
		}
		else if (position.isFloodedLevel()){
			for (int i = 0; i < Engineer.nbActionDryAllowed; i++) {
				assertEquals(Player.nbActionMax, player.getNbAction());
				assertTrue(m4.dryPlayer(currentPlayerId, Move.NONE));
				assertEquals(Player.nbActionMax, player.getNbAction());
				position.flood();
			}
			assertTrue(m4.dryPlayer(currentPlayerId, Move.NONE));
			assertEquals(Player.nbActionMax-1, player.getNbAction());
		}
		
		// Si on fait la m??me chose avec un joueur normal
		// chaque ass??chement co??te bien une action
		currentPlayerId = m4.nextIdPlayer();
		assertEquals(1, currentPlayerId);
		player = m4.getPlayer(currentPlayerId);
		position = m4.getPositionPlayer(currentPlayerId);
		assertEquals(Player.nbActionMax, player.getNbAction());
		if (position.isNormalLevel()) {
			position.flood();
			assertTrue(m4.dryPlayer(currentPlayerId, Move.NONE));
			assertEquals(Player.nbActionMax-1, player.getNbAction());
		}
		else if (position.isFloodedLevel()) {
			assertTrue(m4.dryPlayer(currentPlayerId, Move.NONE));
			assertEquals(Player.nbActionMax-1, player.getNbAction());
		}
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void sailorTest() throws InvalidPlayerId {
		
		boolean verbose = false;
		
		// Ajout de joueurs au mod??le m4
		// avec positions initialis??es al??atoirement
		Player p0 = new Sailor(m4, "Maxime", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		assertFalse(m4.getPositionPlayer(0).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(0).getNbAction());
		Player p1 = new Player(m4, "Baptiste", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		assertFalse(m4.getPositionPlayer(1).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(1).getNbAction());
		
		// On donne des postions fixes aux joueurs artificiellement
		// Cela leur retire 1 action chacun malheureusement
		p0.move(m4.getZone(10, 3));
		p1.move(m4.getZone(11, 3));
		assertEquals(Player.nbActionMax-1, m4.getPlayer(0).getNbAction());
		assertEquals(Player.nbActionMax-1, m4.getPlayer(1).getNbAction());
		if (verbose) System.out.println(m4 + "\n");
		
		// V??rification de la sp??cialit?? du navigateur
		assertTrue(p0 instanceof Sailor);
		assertFalse(((Sailor)p0).movePlayerSailorPower(0, Move.UP));
		assertEquals(Player.nbActionMax-1, m4.getPlayer(0).getNbAction());
		if (verbose) System.out.println(m4 + "\n");
		assertTrue(((Sailor)p0).movePlayerSailorPower(1, Move.UP));
		assertEquals(Player.nbActionMax-2, m4.getPlayer(0).getNbAction());
		if (verbose) System.out.println(m4 + "\n");
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void diverTest() throws InvalidPlayerId {
		
		boolean verbose = true;
		
		// Ajout de joueurs au mod??le m4
		// avec positions initialis??es al??atoirement
		Player p0 = new Diver(m4, "Maxime", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		assertFalse(m4.getPositionPlayer(0).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(0).getNbAction());
		Player p1 = new Player(m4, "Baptiste", Sexe.MALE);
		if (verbose) System.out.println(m4 + "\n");
		assertEquals(1, m4.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		assertFalse(m4.getPositionPlayer(1).isSubmergedLevel());
		assertEquals(Player.nbActionMax, m4.getPlayer(1).getNbAction());
		
		// On donne des postions fixes aux joueurs artificiellement
		// Cela leur retire 1 action chacun malheureusement
		p0.move(m4.getZone(10, 3));
		p1.move(m4.getZone(11, 3));
		assertEquals(Player.nbActionMax-1, m4.getPlayer(0).getNbAction());
		assertEquals(Player.nbActionMax-1, m4.getPlayer(1).getNbAction());
		if (verbose) System.out.println(m4 + "\n");
		
		// On inonde des zones autour du plongeur
		m4.getZone(10, 2).flood();
		assertTrue(m4.getZone(10, 2).isFloodedLevel());
		m4.getZone(10, 2).flood();
		assertTrue(m4.getZone(10, 2).isSubmergedLevel());
		m4.getZone(9, 3).flood();
		assertTrue(m4.getZone(9, 3).isFloodedLevel());
		m4.getZone(9, 3).flood();
		assertTrue(m4.getZone(9, 3).isSubmergedLevel());
		m4.getZone(8, 3).flood();
		assertTrue(m4.getZone(8, 3).isFloodedLevel());
		m4.getZone(8, 3).flood();
		assertTrue(m4.getZone(8, 3).isSubmergedLevel());
		if (verbose) System.out.println(m4 + "\n");
		
		// V??rification de la sp??cialit?? du plongeur
		assertEquals(4, m4.getPlayer(0).movePossibilities().size());
		if (verbose) {
			for (Zone zone : m4.getPlayer(0).movePossibilities()) {
				System.out.println("("+zone.x+", "+zone.y+")");
			}
			System.out.print("\n");
		}
		assertTrue(m4.getPlayer(0).movePossibilities().contains(m4.getZone(10, 1)));
		assertFalse(m4.getPlayer(0).movePossibilities().contains(m4.getZone(8, 3)));
		assertTrue(m4.getPlayer(0).movePossibilities().contains(m4.getZone(10, 3)));
		assertTrue(m4.getPlayer(0).movePossibilities().contains(m4.getZone(11, 3)));
		assertTrue(m4.getPlayer(0).movePossibilities().contains(m4.getZone(10, 4)));
		
		// On ne peut pas d??placer en (8, 3) le plongeur
		assertFalse(m4.movePlayer(0, Move.LEFT));
		assertFalse(m4.getPositionPlayer(0).equals(m4.getZone(8, 3)));
		assertTrue(m4.getPositionPlayer(0).equals(m4.getZone(10, 3)));
		assertEquals(Player.nbActionMax-1, m4.getPlayer(0).getNbAction());
		if (verbose) System.out.println(m4+"\n");
		
		// On d??place en (10, 1) le plongeur
		assertTrue(m4.movePlayer(0, Move.UP));
		assertTrue(m4.getPositionPlayer(0).equals(m4.getZone(10, 1)));
		assertEquals(Player.nbActionMax-2, m4.getPlayer(0).getNbAction());
		if (verbose) System.out.println(m4+"\n");
		
		// On d??place en (9, 1) le plongeur
		assertTrue(m4.movePlayer(0, Move.LEFT));;
		assertTrue(m4.getPositionPlayer(0).equals(m4.getZone(9, 1)));
		assertEquals(Player.nbActionMax-3, m4.getPlayer(0).getNbAction());
		if (verbose) System.out.println(m4+"\n");
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}

}










