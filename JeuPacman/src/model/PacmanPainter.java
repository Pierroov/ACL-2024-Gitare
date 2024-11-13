package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import engine.GamePainter;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	private PacmanGame game;

	private static final int TILE_SIZE = 30;

	public PacmanPainter(PacmanGame game) {
		this.game = game;
	}

	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		char[][] board = game.getBoard();

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == '#') {
					crayon.setColor(Color.BLUE);
					crayon.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				} else {
					crayon.setColor(Color.BLACK);
					crayon.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
		}

		crayon.setColor(Color.YELLOW);
	    crayon.fillOval(game.getPacmanX() * TILE_SIZE, game.getPacmanY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

	    for (Monstres monstre : game.getMonstres()) {
	        crayon.setColor(monstre.getColor());
	        crayon.fillOval(monstre.getX() * TILE_SIZE, monstre.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	    }
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
