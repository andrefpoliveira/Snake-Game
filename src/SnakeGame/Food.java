package SnakeGame;

import java.util.Random;

public class Food {
	public int posX;
	public int posY;

	public int size = 15;

	public Food() {
		posX = new Random().nextInt(29) * 15 + 17;
		posY = new Random().nextInt(24) * 15 + 15;
	}
}
