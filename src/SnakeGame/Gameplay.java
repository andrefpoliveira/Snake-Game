package SnakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private Snake s = new Snake();
	private Food f = new Food();

	private int score = 0;
	private String highScore = "";

	private Timer timer;
	private int delay = 150;

	private boolean play = true;
	private boolean gameOver = false;
	private boolean gotFood = false;

	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	//Paints the background and the borders
	public void drawMap(Graphics g) {
		//Background
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);

		// Borders
		g.setColor(Color.green);
		g.fillRect(0, 0, 17, 500);
		g.fillRect(0, 0, 500, 15);
		g.fillRect(467, 0, 17, 500);
		g.fillRect(0, 375, 500, 15);
		g.fillRect(0, 446, 500, 15);
	}
	
	//Paints the Snake
	public void drawSnake(Graphics g) {
		g.setColor(Color.red);
		for (int i = 0; i < s.sizeSnake; i++) {
			g.fillRect(s.x[i], s.y[i], s.sizeHead, s.sizeHead);
		}
	}
	
	//Paints the Food
	public void drawFood(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(f.posX, f.posY, f.size, f.size);
	}
	
	//Paints the Scores
	public void drawScores(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: " + score, 30, 415);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Highscore: " + highScore, 30, 440);
	}
	
	//Paints the text of GameOver
	public void drawGameOver(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("Game Over", 160, 230);
	}

	public void paint(Graphics g) {
		System.out.println(delay);
		
		drawMap(g);

		if (gotFood) {
			s.addCoordsEnd();
			gotFood = false;
		}

		drawSnake(g);
		drawFood(g);

		if (highScore.equals("")) {
			highScore = this.GetHighScore();
		}
		
		drawScores(g);

		//Check if the snake eats the fruit
		if (new Rectangle(s.x[0], s.y[0], s.sizeHead, s.sizeHead).intersects(new Rectangle(f.posX, f.posY, f.size, f.size))) {
			score += 5;
			f = new Food();
			while (s.foodInsideSnake(f.posX, f.posY)) {
				f = new Food();
			}
			gotFood = true;
			s.keepCoords();
		}
		
		//Check self collision
		if (s.sizeSnake > 4) {
			if (s.selfCollision()) {
				gameOver = true;
				play = false;
			}
		}

		if (gameOver) {
			CheckScore();
			drawMap(g);
			drawGameOver(g);
			drawScores(g);

		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if (play) {
			if (s.posX > 460 || s.posX < 8) {
				play = false;
				gameOver = true;
			} else {
				if (s.dirX != 0)
					s.moveSidewais(s.dirX);
			}

			if ((s.posY + s.sizeHead) > 375 || s.posY < 8) {
				play = false;
				gameOver = true;
			} else {
				if (s.dirY != 0)
					s.moveUpDown(s.dirY);
			}

		}

		repaint();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && s.dirX == 0) {
			s.dirX = s.sizeHead;
			s.dirY = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT && s.dirX == 0) {
			s.dirX = -s.sizeHead;
			s.dirY = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN && s.dirY == 0) {
			s.dirX = 0;
			s.dirY = s.sizeHead;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP && s.dirY == 0) {
			s.dirX = 0;
			s.dirY = -s.sizeHead;
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
			s = new Snake();
			f = new Food();
			play = true;
			score = 0;
			gameOver = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_P) {
			play = !play;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public String GetHighScore() {
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("highscore.dat");
			reader = new BufferedReader(readFile);
			return reader.readLine();
		}

		catch (Exception e) {
			return "0";
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void CheckScore() {
		if (score > Integer.parseInt(highScore)) {
			highScore = String.valueOf(score);

			File scoreFile = new File("highscore.dat");
			if (!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore);

			} catch (Exception e) {

			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (Exception e) {

				}
			}
		}
	}
}
