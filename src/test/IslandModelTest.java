package test;

import model.IslandModel;
import static org.junit.Assert.*;
import org.junit.*;

public class IslandModelTest {
	
	String map1 = "*********************\n"
		        + "**********---********\n"
		        + "*******--------******\n"
		        + "***--------------****\n"
		        + "*****-------------***\n"
		        + "***-----------------*\n"
		        + "******------------***\n"
		        + "******----------*****\n"
		        + "*********----********\n"
		        + "*********************\n";
	
	@Test
	public void constructor() {
		IslandModel m = new IslandModel(map1);
		
	}

}
