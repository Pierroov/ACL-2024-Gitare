package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.GamePainter;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	private PacmanGame game;

	private static final int TILE_SIZE = 20;  // Tamanho de cada "casa" no tabuleiro

	public PacmanPainter(PacmanGame game) {
		this.game = game;
	}

	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		char[][] board = game.getBoard();

		// Desenhar o tabuleiro
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == '#') {
					crayon.setColor(Color.BLUE);  // Muro
					crayon.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				} else {
					crayon.setColor(Color.BLACK);  // Espaço vazio
					crayon.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
		}

		// Desenhar Pacman
		crayon.setColor(Color.YELLOW);
		int pacmanX = game.getPacmanX();
		int pacmanY = game.getPacmanY();
		//crayon.fillOval(pacmanX * TILE_SIZE, pacmanY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		int angle = (int)Math.round(50 * (Math.sin((pacmanX + pacmanY)*2*Math.PI/50) + 1)); 
		crayon.fillArc(pacmanX* TILE_SIZE, pacmanY* TILE_SIZE, TILE_SIZE, TILE_SIZE, angle/2, 360-angle);
	}

	@Override
	public int getWidth() {
		return game.getBoard()[0].length * TILE_SIZE;
	}

	@Override
	public int getHeight() {
		return game.getBoard().length * TILE_SIZE;
	}
}
