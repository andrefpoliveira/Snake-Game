package SnakeGame;

import java.util.Arrays;

public class Snake {
	public int sizeHead = 15;
	public int sizeSnake = 1;

	public int[] x;
	public int[] y;

	public int posX;
	public int posY;

	public int dirX;
	public int dirY;

	public int tempX;
	public int tempY;

	public Snake() {
		posX = 17;
		posY = 15;

		dirX = sizeHead;
		dirY = 0;

		x = new int[sizeSnake];
		y = new int[sizeSnake];

		x[0] = posX;
		y[0] = posY;
		
	}

	public void moveSidewais(int distance) {
		posX += distance;
		passCoords();
		x[0] = posX;
		y[0] = posY;
	}

	public void moveUpDown(int distance) {
		posY += distance;
		passCoords();
		x[0] = posX;
		y[0] = posY;
	}

	public void passCoords() {
		for (int i = sizeSnake - 1; i > 0; i--) {
			x[i] = x[(i - 1)];
			y[i] = y[(i - 1)];
		}
	}

	public void keepCoords() {
		tempX = x[sizeSnake - 1];
		tempY = y[sizeSnake - 1];
	}

	public boolean selfCollision() {
		for (int i = 1; i < sizeSnake; i++) {
			if (x[0] == x[i] && y[0] == y[i])
				return true;
		}
		return false;
	}

	public void addCoordsEnd() {
		x = Arrays.copyOf(x, x.length + 1);
		y = Arrays.copyOf(y, y.length + 1);
		x[x.length - 1] = tempX;
		y[y.length - 1] = tempY;
		sizeSnake++;
	}

	public boolean foodInsideSnake(int posX, int posY) {
		for (int i = 0; i<sizeSnake; i++) {
			if (posX == x[i] && posY == y[i]) return true;
		}
		return false;
	}
}
