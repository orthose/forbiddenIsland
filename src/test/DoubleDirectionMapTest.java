package test;

import model.DoubleDirectionMap;
import static org.junit.Assert.*;
import org.junit.*;

public class DoubleDirectionMapTest {
	
	DoubleDirectionMap<Integer, Integer> map;
	{
		map = new DoubleDirectionMap<>();
		for(int i = 0; i < 10; i++) {
			map.put(i, i*2);
		}
	}
	
	@Test
	public void encodeTest() {
		for(int i = 0; i < 10; i++) {
			assertEquals(map.encode(i), Integer.valueOf(i*2));
		}
	}
}
