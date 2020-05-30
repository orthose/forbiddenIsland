package util;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @author maxime
 * @apiNote Permet de lancer une
 * musique à la fois
 * 
 * Inspiré du code de la page web suivante:
 * https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
 */
public class MusicPlayer {
	
	// Modifier si ajout ou suppression de musiques
	final static int nbMusic= 12;
	private static Clip clip;
	
	/**
	 * @apiNote Lance en boucle la musique
	 * @param music: Numéro de piste
	 */
	public static void play(int music) {
		try {
			if (!(0 <= music && music <= nbMusic)) {
				throw new Exception("Illegal level number for music " + music);
			}
			String path = "assets/music/level" + music + ".wav";
			File file = new File(path).getAbsoluteFile();
			AudioInputStream audio = AudioSystem.getAudioInputStream(file);
	        clip = AudioSystem.getClip();
	        clip.open(audio);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load music track " + music);
		}
	}
	
	/**
	 * @apiNote Arrête la musique en cours
	 */
	public static void stop() {
		clip.stop();
	}
	
}
