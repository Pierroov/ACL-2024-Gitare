package model;

/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;*/

import engine.Cmd;
import engine.Game;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game {

    private Board board;
    private Pacman pacman;
    private List<Monstres> monstres;
    
    /**
     * Constructeur du jeu Pacman
     * 
     * @param width largeur du plateau
     * @param height hauteur du plateau
     */
    public PacmanGame(int width, int height) {
        this.board = new Board(width, height);
        this.pacman = new Pacman(width / 2, height / 2);  // Pacman commence au milieu du plateau
        this.monstres = new ArrayList<>();

        // Initialisation des monstres à des positions de départ
        monstres.add(new Monstres(1, 1));
        monstres.add(new Monstres(width - 2, 1));
        monstres.add(new Monstres(1, height - 2));
        monstres.add(new Monstres(width - 2, height - 2));
    }

    /**
     * Fait évoluer le jeu en fonction de la commande donnée
     * 
     * @param commande commande donnée par l'utilisateur
     */
    @Override
    public void evolve(Cmd commande) {
        int newX = pacman.getX();
        int newY = pacman.getY();

        switch (commande) {
        case LEFT:
        	if(newX ==0 && newY == 10)
        	{
        		newX = 26;
        	}
        	else {newX--;}
            break;
        case RIGHT:
        	if(newX ==26 && newY == 10)
        	{
        		newX = 0;
        	}
        	else{newX++;}
            break;
        case UP:
            newY--;
            break;
        case DOWN:
            newY++;
            break;
        default:
            break;
        }

        // Vérifie si Pacman peut se déplacer à la nouvelle position
        if (board.canMove(newX, newY)) {
            pacman.move(newX, newY);
        }
        
        // Déplacement des monstres
        for (Monstres monstre : monstres) {
            monstre.moveMonstre(pacman.getX(), pacman.getY(), board);
        }
    }

    /**
     * Retourne le plateau du jeu
     * 
     * @return le tableau 2D du plateau
     */
    public char[][] getBoard() {
        return this.board.getBoard();
    }

    /**
     * Vérifie si le jeu est terminé
     * 
     * @return faux, car le jeu n'est jamais terminé dans cette version
     */
    @Override
    public boolean isFinished() {
        return false;  // Le jeu continue indéfiniment
    }
    
    public List<Monstres> getMonstres() {
        return monstres;
    }

    public int getPacmanX() {
        return pacman.getX();
    }

    public int getPacmanY() {
        return pacman.getY();
    }
}
