package util;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * @author maxime
 * @apiNote Permet de charger une map
 * pour initialiser le modèle
 */
public class LevelLoader {
	
	// Modifier si ajout ou suppression de niveaux
	final static int nbLevel = 12;
	
	/**
	 * @apiNote Charge un niveau
	 * @param level: indice du niveau entre 0
	 * et nombre de niveaux - 1
	 * @return Chaîne de caractères de la carte
	 * du niveau
	 */
	public static String load(int level) {
		StringBuilder res = new StringBuilder();
		try {
			if (! (0 <= level && level <= nbLevel)) {
				throw new FileNotFoundException("Illegal level number");
			}
			String path = "assets/levels/level"+level;
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferFile = new BufferedReader(fileReader);
			String line = bufferFile.readLine();
			while (line != null) {
				res.append(line+"\n");
				line = bufferFile.readLine();
			}
			bufferFile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Failed to load level "+level);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load level "+level);
		}
		System.out.println(res);
		
		return res.toString();
	}

}
