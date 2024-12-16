package model;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;


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
	private BufferedImage guitarImage;
	private BufferedImage batterieImage;
	private BufferedImage pacmanImage;
//	private BufferedImage monstreImage;
	

	public PacmanPainter(PacmanGame game) {
		this.game = game;
		try {
            guitarImage = ImageIO.read(new File("../../guitare.png")); // Chemin de l'image
            batterieImage=ImageIO.read(new File("../../batterie.png"));
            pacmanImage=ImageIO.read(new File("../../pacman.jpg"));
           // monstreImage=ImageIO.read(new File("../../monstre.png"));
            		} 
		catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur : Impossible de charger l'image de la guitare.");
        }
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
				if (board[y][x] == 'G') {
				    if (guitarImage != null) {
				        crayon.drawImage(guitarImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
				    }
				}
				if (board[y][x] == 'B') {
					 if (batterieImage != null) {
					     crayon.drawImage(batterieImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
					    }   	    
				}
			
		}

		//crayon.setColor(Color.YELLOW);
	    //crayon.fillOval(game.getPacmanX() * TILE_SIZE, game.getPacmanY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	    crayon.drawImage(pacmanImage, game.getPacmanX() * TILE_SIZE, game.getPacmanY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);

	    for (Monstre monstre : game.getMonstres()) {
	        crayon.setColor(monstre.getColor());
	        crayon.fillOval(monstre.getX() * TILE_SIZE, monstre.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	    }
	    
	    Font font = new Font("Arial", Font.PLAIN, 25);  // Créer une nouvelle police
        crayon.setFont(font);
        Graphics2D g2d = (Graphics2D) crayon;
        GradientPaint gradient = new GradientPaint(0, 0, Color.YELLOW, 100, 0, Color.RED, true);
        g2d.setPaint(gradient);

        // Ajout d'un contour
        crayon.setPaint(Color.BLACK);
        crayon.drawString("Score: " + game.getPacman().getScore(), 12, 28);
        crayon.setPaint(gradient);
        crayon.drawString("Score: " + game.getPacman().getScore(), 10, 26);
        
        crayon.setPaint(Color.BLACK);
        crayon.drawString("Record: " + game.getPacman().getRecord(), 402, 28);
        crayon.setPaint(gradient);
        crayon.drawString("Record: " + game.getPacman().getRecord(), 400, 26);
        
        
        if (game.isFinished()) {
            crayon.setColor(Color.RED); // Choisissez la couleur du texte "Game Over"
            crayon.setFont(new Font("Arial", Font.BOLD, 40)); // Taille de la police
            String message = "Game Over";
            // Calculer la position pour centrer le texte
            int xPos = (im.getWidth() - crayon.getFontMetrics().stringWidth(message)) / 2;
            int yPos = im.getHeight() / 2;
            crayon.drawString(message, xPos, yPos); // Afficher "Game Over"
        }
        
        if (game.isWin()) {
            crayon.setColor(Color.RED); // Choisissez la couleur du texte "Game Over"
            crayon.setFont(new Font("Arial", Font.BOLD, 40)); // Taille de la police
            String message = "Bravo vous avez gagné";
            // Calculer la position pour centrer le texte
            int xPos = (im.getWidth() - crayon.getFontMetrics().stringWidth(message)) / 2;
            int yPos = im.getHeight() / 2;
            crayon.drawString(message, xPos, yPos); 
        }
	}}

	@Override
	public int getWidth() {
		return game.getBoard()[0].length * TILE_SIZE;
	}

	@Override
	public int getHeight() {
		return game.getBoard().length * TILE_SIZE;
	}
}
