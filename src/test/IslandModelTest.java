package test;

import model.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;

import model.Player.InvalidPlayerId;

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
		m0 = new IslandModel(map0);
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
		allModels.put(map0, m0);
		allModels.put(map1, m1);
		allModels.put(map2, m2);
		allModels.put(map3, m3);
		allModels.put(map4, m4);
		allModels.put(map5, m5);
		
		for (String map : allModels.allKeys()) {
			IslandModel m = allModels.encode(map);
			//System.out.println(m+"\n");
			assertEquals(mapWidth, m.WIDTH);
			assertEquals(mapHeight, m.HEIGHT);
			assertTrue(map.equals(m.toString()));
		}	
	}
	
	@Test
	public void addPlayerTest() throws InvalidPlayerId {
		
		// Ajout de joueurs au modèle m1
		Player p0 = new Player(m1, "Maxime", m1.getZone(10, 5));
		//System.out.println(m1+"\n");
		assertEquals(0, m1.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		Player p1 = new Player(m1, "Baptiste", m1.getZone(12, 5));
		//System.out.println(m1+"\n");
		assertEquals(1, m1.getPlayer(1).getId());
		assertEquals(1, p1.getId());
		Player p2 = new Player(m1, "Ludisia", m1.getZone(14, 5));
		//System.out.println(m1+"\n");
		assertEquals(2, m1.getPlayer(2).getId());
		assertEquals(2, p2.getId());
		
		// Accès à un joueur n'existant pas
		int res = 0;
		try {
			Player p3 = m1.getPlayer(3);
		}
		catch (InvalidPlayerId e) {
			//System.out.println(e+"\n");
			res++;
		}
		assertEquals(1, res);
		
		// Ajout d'un joueur au modèle m5
		// On constate que l'id suit ceux
		// des joueurs précédemment créés
		// Mais que le joueur est enregistré
		// comme premier joueur du modèle m5
		Player p3 = new Player(m5, "Amélie", m5.getZone(10, 5));
		//System.out.println(m5+"\n");
		assertEquals(3, m5.getPlayer(0).getId());
		assertEquals(3, p3.getId());
		
		// Pour remédier à cela si l'on recommence 
		// une partie on peut supprimer tous les joueurs
		// En utilisant l'interface statique de IslandModel
		// On se place dans le modèle m4
		IslandModel.reset();
		Player p4 = new Player(m4, "Amélie", m4.getZone(10, 5));
		//System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p4.getId());
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void movePlayerTest() throws InvalidPlayerId {
		
		// Ajout d'un joueur au modèle m4
		Player p0 = new Player(m4, "Amélie", m4.getZone(10, 5));
		//System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// Coordonnées du joueur
		int x = m4.getPositionPlayer(0).x;
		int y = m4.getPositionPlayer(0).y;
		
		// Déplacements possibles
		ArrayList<Zone> possibilities = m4.movePossibilitiesPlayer(0);
		assertEquals(5, possibilities.size());
		assertTrue(possibilities.contains(m4.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m4.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m4.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x, y)));
		
		// Réalisation de déplacements
		assertFalse(m4.getPositionPlayer(0).isHeliport());
		assertTrue(m4.movePlayer(0, Move.UP));
		assertTrue(m4.getZone(x, y - 1).equals(m4.getPositionPlayer(0)));
		assertTrue(m4.getPositionPlayer(0).isHeliport());
		//System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.DOWN));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		//System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.NONE));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		//System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.LEFT));
		assertTrue(m4.getZone(x - 1, y).equals(m4.getPositionPlayer(0)));
		//System.out.println(m4+"\n");
		assertTrue(m4.movePlayer(0, Move.RIGHT));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		//System.out.println(m4+"\n");
		
		// Téléportation du joueur en bas de l'île
		m4.getPlayer(0).move(m4.getZone(10, 8));
		x = 10; y = 8;
		//System.out.println(m4+"\n");
		
		// Déplacements possibles
		possibilities = m4.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m4.getZone(x, y - 1)));
		assertFalse(possibilities.contains(m4.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m4.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m4.getZone(x, y)));
		
		// On ne peut pas se déplacer dans la zone submergée
		assertFalse(m4.movePlayer(0, Move.DOWN));
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		//System.out.println(m4+"\n");
		
		// En revanche on peut se déplacer dans une zone inondée
		assertTrue(m4.movePlayer(0, Move.RIGHT)); x++;
		assertTrue(m4.getPositionPlayer(0).isNormalLevel());
		assertTrue(m4.movePlayer(0, Move.RIGHT)); x++;
		assertTrue(m4.getZone(x, y).equals(m4.getPositionPlayer(0)));
		assertTrue(m4.getPositionPlayer(0).isFloodedLevel());
		//System.out.println(m4+"\n");
		
		// Inondation de la case adjacente en haut
		m4.getZone(x, y - 1).flood();
		assertTrue(m4.getZone(x, y - 1).isFloodedLevel());
		m4.getZone(x, y - 1).flood();
		assertTrue(m4.getZone(x, y - 1).isSubmergedLevel());
		//System.out.println(m4+"\n");
		
		// Déplacements possibles
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
		//System.out.println(m4+"\n");
		
		// Déplacements possibles
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
		//System.out.println(m4+"\n");
		
		// Déplacements possibles
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
		
		// Ajout d'un joueur au modèle m0 en haut à gauche
		new Player(m0, "Amélie", m0.getZone(0, 0));
		//System.out.println(m0);
		x = 0; y = 0;
		
		// Déplacements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Téléportation du joueur en haut à droite
		m0.getPlayer(0).move(m0.getZone(mapWidth - 1, 0));
		//System.out.println(m0+"\n");
		x = mapWidth - 1; y = 0;
		
		// Déplacements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Téléportation du joueur en bas à gauche
		m0.getPlayer(0).move(m0.getZone(0, mapHeight - 1));
		//System.out.println(m0+"\n");
		x = 0; y = mapHeight - 1;
		
		// Déplacements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Téléportation du joueur en bas à droite
		m0.getPlayer(0).move(m0.getZone(mapWidth - 1, mapHeight - 1));
		//System.out.println(m0+"\n");
		x = mapWidth - 1; y = mapHeight - 1;
		
		// Déplacements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(3, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Téléportation du joueur sur le bord du haut
		m0.getPlayer(0).move(m0.getZone(mapWidth / 2, 0));
		//System.out.println(m0+"\n");
		x = mapWidth / 2; y = 0;
		
		// Déplacements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Téléportation du joueur sur le bord de gauche
		m0.getPlayer(0).move(m0.getZone(0, mapHeight / 2));
		//System.out.println(m0+"\n");
		x = 0; y = mapHeight / 2;
		
		// Déplacements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x + 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Téléportation du joueur sur le bord de droite
		m0.getPlayer(0).move(m0.getZone(mapWidth - 1, mapHeight / 2));
		//System.out.println(m0+"\n");
		x = mapWidth - 1; y = mapHeight / 2;
		
		// Déplacements possibles
		possibilities = m0.movePossibilitiesPlayer(0);
		assertEquals(4, possibilities.size());
		assertTrue(possibilities.contains(m0.getZone(x, y + 1)));
		assertTrue(possibilities.contains(m0.getZone(x - 1, y)));
		assertTrue(possibilities.contains(m0.getZone(x, y - 1)));
		assertTrue(possibilities.contains(m0.getZone(x, y)));
		
		// Téléportation du joueur sur le bord du bas
		m0.getPlayer(0).move(m0.getZone(mapWidth / 2, mapHeight - 1));
		//System.out.println(m0 + "\n");
		x = mapWidth / 2; y = mapHeight - 1;
		
		// Déplacements possibles
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
		
		// Ajout d'un joueur au modèle m4
		Player p0 = new Player(m4, "Amélie", m4.getZone(10, 5));
		//System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// Vérification des zones asséchables
		assertFalse(m4.canDryPlayer(0, Move.UP));
		assertFalse(m4.canDryPlayer(0, Move.DOWN));
		assertFalse(m4.canDryPlayer(0, Move.LEFT));
		assertFalse(m4.canDryPlayer(0, Move.RIGHT));
		assertFalse(m4.canDryPlayer(0, Move.NONE));
		
		// Déplacement du joueur sur sa gauche 4 fois
		for (int i = 0; i < 4; i++) {
			assertTrue(m4.movePlayer(0, Move.LEFT));
		}
		//System.out.println(m4 + "\n");
		
		// Vérification des zones asséchables
		assertFalse(m4.canDryPlayer(0, Move.UP));
		assertFalse(m4.canDryPlayer(0, Move.DOWN));
		assertTrue(m4.canDryPlayer(0, Move.LEFT));
		assertFalse(m4.canDryPlayer(0, Move.RIGHT));
		assertFalse(m4.canDryPlayer(0, Move.NONE));
		
		// On assèche la zone asséchable à gauche
		// On tente d'assécher des zones déjà asséchées
		assertFalse(m4.dryPlayer(0, Move.UP));
		assertFalse(m4.dryPlayer(0, Move.DOWN));
		assertTrue(m4.dryPlayer(0, Move.LEFT));
		assertFalse(m4.dryPlayer(0, Move.RIGHT));
		assertFalse(m4.dryPlayer(0, Move.NONE));
		//System.out.println(m4 + "\n");
		
		// On inonde certaines zones
		m4.getZone(6, 5).flood();
		m4.getZone(5, 5).flood();
		m4.getZone(5, 5).flood();
		m4.getZone(6, 6).flood();
		//System.out.println(m4 + "\n");
		
		// On assèche les zones asséchables
		// On tente d'assécher des zones non-asséchable
		assertFalse(m4.dryPlayer(0, Move.UP));
		assertTrue(m4.dryPlayer(0, Move.DOWN));
		assertFalse(m4.dryPlayer(0, Move.LEFT));
		assertFalse(m4.dryPlayer(0, Move.RIGHT));
		assertTrue(m4.dryPlayer(0, Move.NONE));
		//System.out.println(m4 + "\n");
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void floodRandomTest() throws InvalidPlayerId {
		
		// Ajout d'un joueur au modèle m0
		Player p0 = new Player(m1, "Maxime", m1.getZone(10, 5));
		//System.out.println(m1+"\n");
		assertEquals(0, m1.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// On inonde aléatoirement plusieurs fois
		for (int i = 0; i < 100; i++) {
			ArrayList<Player> threatenedPlayers = m1.floodRandom();
			//System.out.println(m1+"\n");
			if (! threatenedPlayers.isEmpty()) {
			    assertTrue(m1.getPositionPlayer(0).isSubmergeable());
			    assertTrue(m1.getPositionPlayer(0).isFloodedLevel());
				p0.kill();
				//System.out.println(p0.getName() + " was killed !");
				break;
			}
		}
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
	}
	
	@Test
	public void KeyAndArtefactTest() throws InvalidPlayerId {
		
		// Ajout d'un joueur au modèle m0
		Player p0 = new Player(m4, "Amélie", m4.getZone(10, 5));
		//System.out.println(m4+"\n");
		assertEquals(0, m4.getPlayer(0).getId());
		assertEquals(0, p0.getId());
		
		// On tente de récupérer plusieurs clés
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
			//System.out.println(m4);
			assertEquals(NaturalElement.AIR, m4.getPositionPlayer(0).getNaturalElement());
			assertTrue(m4.findArtefactPlayer(0));
			assertEquals(NaturalElement.AIR, Artefact.getFoundArtefacts().get(0).getNaturalElement());
			break;
			
		case WATER:
			for (int i = 0; i < 4; i++) {
				m4.movePlayer(0, Move.RIGHT);
			}
			//System.out.println(m4);
			assertEquals(NaturalElement.WATER, m4.getPositionPlayer(0).getNaturalElement());
			assertTrue(m4.findArtefactPlayer(0));
			assertEquals(NaturalElement.WATER, Artefact.getFoundArtefacts().get(0).getNaturalElement());
			break;
		
		case EARTH:
			for (int i = 0; i < 4; i++) {
				m4.movePlayer(0, Move.UP);
			}
			m4.movePlayer(0, Move.RIGHT);
			//System.out.println(m4);
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
			//System.out.println(m4);
			assertEquals(NaturalElement.FIRE, m4.getPositionPlayer(0).getNaturalElement());
			assertTrue(m4.findArtefactPlayer(0));
			assertEquals(NaturalElement.FIRE, Artefact.getFoundArtefacts().get(0).getNaturalElement());
			break;
		}
		
		// Pour ne pas perturber les autres tests
		IslandModel.reset();
		
	}

}











