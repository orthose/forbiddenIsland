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
		assertEquals(StringMap.decode("FloodedLevel"), z.toString());
		z.flood();
		assertEquals(StringMap.decode("SubmergedLevel"), z.toString());
	}

}
