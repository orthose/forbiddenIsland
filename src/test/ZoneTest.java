package test;

import model.Zone;
import model.StringMap;
import static org.junit.Assert.*;
import org.junit.*;

public class ZoneTest {
	
	@Test
	public void floodTest() {
		Zone z = new Zone(null, "-");
		z.flood();
		assertEquals(z.toString(), StringMap.decode("FloodedLevel"));
		z.flood();
		assertEquals(z.toString(), StringMap.decode("SubmergedLevel"));
		
	}

}
