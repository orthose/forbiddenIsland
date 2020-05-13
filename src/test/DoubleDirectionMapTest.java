package test;

import model.DoubleDirectionMap;
import static org.junit.Assert.*;
import org.junit.*;

public class DoubleDirectionMapTest {
	
	DoubleDirectionMap<Integer, Integer> map1;
	DoubleDirectionMap<String, Integer> map2;
	DoubleDirectionMap<String, String> map3;
	
	@Before
	public void initializeMap() {
		
		map1 = new DoubleDirectionMap<>();
		for(int i = 0; i < 10; i++) {
			map1.put(i, i*2);
		}
		
		map2 = new DoubleDirectionMap<>();
		map2.put("Pâte", 5);
		map2.put("Poulet", 2);
		map2.put("Champignon", 15);
		map2.put("Farine", 3);
		map2.put("Lardon", 2);
		map2.put("Oeuf", 20);
		
		map3 = new DoubleDirectionMap<>();
		map3.put("NormalLevel", "-");
		map3.put("FloodedLevel", "~");
		map3.put("SubmergedLevel", "*");
		map3.put("heliport", "H");
		map3.put("heliport&FloodedLevel", "h");
		
	}
	
	@Test
	public void printTest() {
		System.out.println(map1);
		System.out.println(map2);
		System.out.println(map3);
	}
	
	@Test
	public void encodeTest() {
		
		for(int i = 0; i < 10; i++) {
			assertEquals(Integer.valueOf(i*2), map1.encode(i));
		}
		assertNull(map1.encode(-1));
		assertNull(map1.encode(10));
		
		assertEquals(Integer.valueOf(5), map2.encode("Pâte"));
		assertEquals(Integer.valueOf(2), map2.encode("Poulet"));
		assertEquals(Integer.valueOf(15), map2.encode("Champignon"));
		assertEquals(Integer.valueOf(3), map2.encode("Farine"));
		assertEquals(Integer.valueOf(2), map2.encode("Lardon"));
		assertEquals(Integer.valueOf(20), map2.encode("Oeuf"));
		assertNull(map2.encode("Lait"));
		assertNull(map2.encode("Café"));
		
		assertEquals("-", map3.encode("NormalLevel"));
		assertEquals("~", map3.encode("FloodedLevel"));
		assertEquals("*", map3.encode("SubmergedLevel"));
		assertEquals("H", map3.encode("heliport"));
		assertEquals("h", map3.encode("heliport&FloodedLevel"));
		assertNull(map3.encode("AIR"));
		assertNull(map3.encode("FIRE"));
		
	}
	
	@Test
	public void decodeTest() {
		
		for(int i = 0; i < 10; i++) {
			assertEquals(Integer.valueOf(i), map1.decode(i*2));
		}
		assertNull(map1.decode(-2));
		assertNull(map1.decode(20));
		
		assertEquals("Pâte", map2.decode(5));
		assertEquals("Poulet", map2.decode(2));
		assertEquals("Champignon", map2.decode(15));
		assertEquals("Farine", map2.decode(3));
		// Ne fonctionne pas car deuxième occurence de 2
		//assertEquals("Lardon", map2.decode(2));
		assertEquals("Oeuf", map2.decode(20));
		assertNull(map2.decode(21));
		assertNull(map2.decode(6));
		
		assertEquals("NormalLevel", map3.decode("-"));
		assertEquals("FloodedLevel", map3.decode("~"));
		assertEquals("SubmergedLevel", map3.decode("*"));
		assertEquals("heliport", map3.decode("H"));
		assertEquals("heliport&FloodedLevel", map3.decode("h"));
		String mean[] = map3.decode("h").split("&");
		assertEquals("heliport", mean[0]);
		assertEquals("FloodedLevel", mean[1]);
		assertNull(map3.decode("A"));
		assertNull(map3.decode("F"));
		
	}

}

















