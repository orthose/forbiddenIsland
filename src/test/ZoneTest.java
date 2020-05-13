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
		z1 = new Zone(null, StringMap.encode("NormalLevel"));
		z2 = new Zone(null, StringMap.encode("FloodedLevel"));
		z3 = new Zone(null, StringMap.encode("SubmergedLevel"));
		z4 = new Zone(null, StringMap.encode("heliport"));
		z5 = new Zone(null, StringMap.encode("heliport&FloodedLevel"));
		z6 = new Zone(null, StringMap.encode("AIR"));
		z7 = new Zone(null, StringMap.encode("AIR&FloodedLevel"));
		z8 = new Zone(null, StringMap.encode("WATER"));
		z9 = new Zone(null, StringMap.encode("WATER&FloodedLevel"));
		z10 = new Zone(null, StringMap.encode("EARTH"));
		z11 = new Zone(null, StringMap.encode("EARTH&FloodedLevel"));
		z12 = new Zone(null, StringMap.encode("FIRE"));
		z13 = new Zone(null, StringMap.encode("FIRE&FloodedLevel"));
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
		
		assertEquals(NaturalElement.NONE, z1.getNaturalElement());
		z1.flood();
		assertEquals(StringMap.encode("FloodedLevel"), z1.toString());
		z1.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z1.toString());
		for (int i = 0; i < 10; i++) {
			z1.flood();
		}
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z1.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z1.getNaturalElement());
		
		assertEquals(NaturalElement.NONE, z2.getNaturalElement());
		z2.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z2.toString());
		z2.flood();
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z2.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z2.getNaturalElement());
		
		assertEquals(NaturalElement.NONE, z3.getNaturalElement());
		z3.flood();
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z3.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z3.getNaturalElement());
		
		assertEquals(NaturalElement.NONE, z4.getNaturalElement());
		assertTrue(z4.isHeliport());
		z4.flood();
		assertEquals(StringMap.encode("heliport&FloodedLevel"), z4.toString());
		z4.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z4.toString());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z4.toString());
		// Vérifie que l'héliport est toujours existant
		assertTrue(z4.isHeliport());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z4.getNaturalElement());
		
		assertEquals(NaturalElement.NONE, z5.getNaturalElement());
		assertTrue(z5.isHeliport());
		z5.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z5.toString());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z5.toString());
		// Vérifie que l'héliport est toujours existant
		assertTrue(z5.isHeliport());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.NONE, z5.getNaturalElement());
		
		assertEquals(NaturalElement.AIR, z6.getNaturalElement());
		z6.flood();
		assertEquals(StringMap.encode("AIR&FloodedLevel"), z6.toString());
		z6.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z6.toString());
		z6.flood();
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z6.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.AIR, z6.getNaturalElement());
		
		assertEquals(NaturalElement.AIR, z7.getNaturalElement());
		z7.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z7.toString());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z7.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.AIR, z7.getNaturalElement());
		
		assertEquals(NaturalElement.WATER, z8.getNaturalElement());
		z8.flood();
		assertEquals(StringMap.encode("WATER&FloodedLevel"), z8.toString());
		z8.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z8.toString());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z8.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.WATER, z8.getNaturalElement());
		
		assertEquals(NaturalElement.WATER, z9.getNaturalElement());
		z9.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z9.toString());
		z9.flood();
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z9.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.WATER, z9.getNaturalElement());
		
		assertEquals(NaturalElement.EARTH, z10.getNaturalElement());
		z10.flood();
		assertEquals(StringMap.encode("EARTH&FloodedLevel"), z10.toString());
		z10.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z10.toString());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z10.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.EARTH, z10.getNaturalElement());
		
		assertEquals(NaturalElement.EARTH, z11.getNaturalElement());
		z11.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z11.toString());
		z11.flood();
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z11.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.EARTH, z11.getNaturalElement());
		
		assertEquals(NaturalElement.FIRE, z12.getNaturalElement());
		z12.flood();
		assertEquals(StringMap.encode("FIRE&FloodedLevel"), z12.toString());
		z12.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z12.toString());
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z12.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.FIRE, z12.getNaturalElement());
		
		assertEquals(NaturalElement.FIRE, z13.getNaturalElement());
		z13.flood();
		assertEquals(StringMap.encode("SubmergedLevel"), z13.toString());
		z13.flood();
		// Inonder une zone submergée ne change pas le niveau d'eau
		assertEquals(StringMap.encode("SubmergedLevel"), z13.toString());
		// Vérifie que l'élément reste inchangé
		assertEquals(NaturalElement.FIRE, z13.getNaturalElement());
	}
	
	@Test
	public void dryTest() {
		
	}
	

}
