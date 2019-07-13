package SnakeGame;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake Game");
		Gameplay gameplay = new Gameplay();
		frame.setBounds(0, 0, 500, 500);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gameplay);

	}

}
