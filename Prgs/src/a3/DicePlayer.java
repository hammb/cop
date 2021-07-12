package a3;

import java.util.Random;

public class DicePlayer {
	private Random random;
	private int id;
	private int faces;

	public int getId() {
		return id;
	}

	public DicePlayer(int seed, int id) {
		this.random = new Random(seed);
		this.id = id;
		this.faces = 6;
	}
	
	public DicePlayer(int seed, int id, int faces) {
		this.random = new Random(seed);
		this.id = id;
		this.faces = faces;
	}

	public int getDice() {
		return this.random.nextInt(this.faces) + 1;
	}
}
