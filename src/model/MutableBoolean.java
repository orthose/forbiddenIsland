package model;

/**
 * @author maxime
 * @apiNote Un objet représentant un
 * booléen mutable qui est notamment
 * utilisé par Zone::neighbour()
 */
public class MutableBoolean {
	public boolean value;
	
	public MutableBoolean(boolean value) {
		this.value = value;
	}
}
