package test;

import model.Zone;
import model.StringMap;
import model.NaturalElement;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * @author maxime
 * @apiNote Tests unitaires automatiques
 * de la classe Zone
 */
public class ZoneTest {
	
	Zone z1, z2, z3, z4, z5, z6, z7, z8, z9, z10, z11, z12, z13;
	
	@Before
	public void initializeZone() {
		z1 = new Zone(null, StringMap.encode("NormalLevel"), 0, 0);
		z2 = new Zone(null, StringMap.encode("FloodedLevel"), 0, 1);
		z3 = new Zone(null, StringMap.encode("SubmergedLevel"), 0, 2);
		z4 = new Zone(null, StringMap.encode("heliport"), 1, 0);
		z5 = new Zone(null, StringMap.encode("heliport&FloodedLevel"), 1, 1);
		z6 = new Zone(null, StringMap.encode("AIR"), 1, 2);
		z7 = new Zone(null, StringMap.encode("AIR&FloodedLevel"), 2, 0);
		z8 = new Zone(null, StringMap.encode("WATER"), 2, 1);
		z9 = new Zone(null, StringMap.encode("WATER&FloodedLevel"), 2, 2);
		z10 = new Zone(null, StringMap.encode("EARTH"), 3, 0);
		z11 = new Zone(null, StringMap.encode("EARTH&FloodedLevel"), 3, 1);
		z12 = new Zone(null, StringMap.encode("FIRE"), 3, 2);
		z13 = new Zone(null, StringMap.encode("FIRE&FloodedLevel"), 3, 3);
	}
	
	@Test
	public void toStringTest() {
		assertEquals(StringMap.encode("NormalLevel"), z1.toString());
		assertEquals(StringMap.encode("FloodedLevel"), z2.toString());
		assertEquals(StringMap.encode("SubmergedLevel"), z3.toString());
		assertEquals(StringMap.encode("heliport"), z4.toString());
		assertEquals(StringMap.encode("heliport&FloodedLevel"), z5.toString());
		assertEquals(StringMap.encode("AIR"), z6.toString());
		assertEquals(StringMap.encode("AIR&FloodedLevel"), z7.toString());
		assertEquals(StringMap.encode("WATER"), z8.toString());
		assertEquals(StringMap.encode("WATER&FloodedLevel"), z9.toString());
		assertEquals(StringMap.encode("EARTH"), z10.toString());
		assertEquals(StringMap.encode("EARTH&FloodedLevel"), z11.toString());
		assertEquals(StringMap.encode("FIRE"), z12.toString());
		assertEquals(StringMap.encode("FIRE&FloodedLevel"), z13.toString());
	}
	
	@Test
	public void floodTest() {
		
		//-------------------------------------------------------------//
		
		assertTrue(z1.isNormalLevel());
		assertEquals(NaturalElement.NONE, z1.getNaturalElement());
		z1.flood();
		assertTrue(z1.isFloodedLevel());
		assertEquals(StringMap.encode("FloodedLevel"), z1.toString());
		z1.flood();
		assertTrue(z1.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z1.toString());
		for (int i = 0; i < 10; i++) {
			z1.flood();
		}
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertTrue(z1.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z1.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z1.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z2.isFloodedLevel());
		assertEquals(NaturalElement.NONE, z2.getNaturalElement());
		z2.flood();
		assertTrue(z2.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z2.toString());
		z2.flood();
		assertTrue(z2.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z2.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z2.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z3.isSubmergedLevel());
		assertEquals(NaturalElement.NONE, z3.getNaturalElement());
		z3.flood();
		assertTrue(z3.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z3.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z3.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z4.isNormalLevel());
		assertEquals(NaturalElement.NONE, z4.getNaturalElement());
		assertTrue(z4.isHeliport());
		z4.flood();
		assertTrue(z4.isFloodedLevel());
		assertEquals(StringMap.encode("heliport&FloodedLevel"), z4.toString());
		z4.flood();
		assertTrue(z4.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z4.toString());
		z4.flood();
		assertTrue(z4.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z4.toString());
		// Vérifie que l'héliport est toujours existant
		assertTrue(z4.isHeliport());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z4.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z5.isFloodedLevel());
		assertEquals(NaturalElement.NONE, z5.getNaturalElement());
		assertTrue(z5.isHeliport());
		z5.flood();
		assertTrue(z5.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z5.toString());
		z5.flood();
		assertTrue(z5.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z5.toString());
		// Vérifie que l'héliport est toujours existant
		assertTrue(z5.isHeliport());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z5.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z6.isNormalLevel());
		assertEquals(NaturalElement.AIR, z6.getNaturalElement());
		z6.flood();
		assertTrue(z6.isFloodedLevel());
		assertEquals(StringMap.encode("AIR&FloodedLevel"), z6.toString());
		z6.flood();
		assertTrue(z6.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z6.toString());
		z6.flood();
		assertTrue(z6.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z6.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.AIR, z6.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z7.isFloodedLevel());
		assertEquals(NaturalElement.AIR, z7.getNaturalElement());
		z7.flood();
		assertTrue(z7.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z7.toString());
		z7.flood();
		assertTrue(z7.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z7.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.AIR, z7.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z8.isNormalLevel());
		assertEquals(NaturalElement.WATER, z8.getNaturalElement());
		z8.flood();
		assertTrue(z8.isFloodedLevel());
		assertEquals(StringMap.encode("WATER&FloodedLevel"), z8.toString());
		z8.flood();
		assertTrue(z8.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z8.toString());
		z8.flood();
		assertTrue(z8.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z8.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.WATER, z8.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z9.isFloodedLevel());
		assertEquals(NaturalElement.WATER, z9.getNaturalElement());
		z9.flood();
		assertTrue(z9.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z9.toString());
		z9.flood();
		assertTrue(z9.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z9.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.WATER, z9.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z10.isNormalLevel());
		assertEquals(NaturalElement.EARTH, z10.getNaturalElement());
		z10.flood();
		assertTrue(z10.isFloodedLevel());
		assertEquals(StringMap.encode("EARTH&FloodedLevel"), z10.toString());
		z10.flood();
		assertTrue(z10.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z10.toString());
		z10.flood();
		assertTrue(z10.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z10.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.EARTH, z10.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z11.isFloodedLevel());
		assertEquals(NaturalElement.EARTH, z11.getNaturalElement());
		z11.flood();
		assertTrue(z11.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z11.toString());
		z11.flood();
		assertTrue(z11.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z11.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.EARTH, z11.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z12.isNormalLevel());
		assertEquals(NaturalElement.FIRE, z12.getNaturalElement());
		z12.flood();
		assertTrue(z12.isFloodedLevel());
		assertEquals(StringMap.encode("FIRE&FloodedLevel"), z12.toString());
		z12.flood();
		assertTrue(z12.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z12.toString());
		z12.flood();
		assertTrue(z12.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z12.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.FIRE, z12.getNaturalElement());
		
		//-------------------------------------------------------------//
		
		assertTrue(z13.isFloodedLevel());
		assertEquals(NaturalElement.FIRE, z13.getNaturalElement());
		z13.flood();
		assertTrue(z13.isSubmergedLevel());
		assertEquals(StringMap.encode("SubmergedLevel"), z13.toString());
		z13.flood();
		assertTrue(z13.isSubmergedLevel());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z13.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.FIRE, z13.getNaturalElement());
		
		//-------------------------------------------------------------//
	}
	
	@Test
	public void dryTest() {
		
	}
	

}
