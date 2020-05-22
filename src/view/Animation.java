package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import forbiddenIsland.Game;

/**
 * @author baptiste
 * @apiNote Classe représentant une animation
 */
public class Animation {
	private static final int timeBetweenFrame = 500; // Temps entre deux frame de l'animation en milliseconde
	private ArrayList<BufferedImage> anim = new ArrayList<>();

	/**
	 * Constructeur de la classe Animation
	 * 
	 * @param path chemin de la forme "assets/animation/X/X"
	 */
	public Animation(String path) {
		loadImages(path);
	}

	public BufferedImage display() {
		int clock = (int) ((System.nanoTime() - Game.BEGINTIME) / 1000000);
		int actualFrame = (clock / timeBetweenFrame) % anim.size();
		return anim.get(actualFrame);

	}

	private void loadImages(String path) {
		try {
			int index = 1;
			while (new File(path + Integer.toString(index) + ".png").exists()) {
				anim.add(ImageIO.read(new File(path + Integer.toString(index) + ".png")));
				index++;
			}
		} catch (IOException e) {
			System.out.println("Failed to load wind animation");
		}
	}

}
