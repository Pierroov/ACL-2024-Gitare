package model;

import java.awt.Color;
import java.awt.Font;
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
				crayon.setColor(Color.BLACK);
				crayon.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				if (board[y][x] == '.') {
					crayon.setColor(Color.white);
					crayon.fillOval(x * TILE_SIZE + TILE_SIZE/4, y * TILE_SIZE+ TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
				} if(board[y][x]=='#') {
					crayon.setColor(Color.BLUE);
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
	    
	    crayon.setPaint(Color.WHITE);  // Définir la couleur du texte
        Font font = new Font("Arial", Font.PLAIN, 20);  // Créer une nouvelle police
        crayon.setFont(font);  // Appliquer la police au dessin
        crayon.drawString("Score: " + game.getPacman().getScore(), 10, 30);
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
