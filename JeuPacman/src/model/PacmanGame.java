package model;

/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;*/

import engine.Cmd;
import engine.Game;

import javax.swing.Timer;  // Importation de la classe Timer
import java.awt.event.ActionEvent;  // Importation de la classe ActionEvent
import java.awt.event.ActionListener;  // Importation de la classe ActionListener
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
    private ArrayList<Monstre> monstres;
    private Cmd lastCommand; // Stocke la dernière commande pour mouvement continu
    private int etat;//1 si la bouche est ouverte et 0 si la bouche est fermée

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
        this.lastCommand = Cmd.IDLE;
        this.etat = 1;

        monstres.add(new Monstre(1, 1, 0));
        monstres.add(new Monstre(width - 2, 1, 2));
        monstres.add(new Monstre(1, height - 2, 1));
        monstres.add(new Monstre(width - 2, height - 2, 3));
    }
    
    public Pacman getPacman() {
        return this.pacman;
    }

    /**
     * Fait évoluer le jeu en fonction de la commande donnée
     * 
     * @param commande commande donnée par l'utilisateur
     */
    @Override
    public void evolve(Cmd commande) {
    	int comand = 0;
    	if (isWin()) {
            return;  // Si le jeu est terminé, on ne fait rien et on ignore les commandes
        }
    
    	
    	if (commande != Cmd.IDLE) {
            lastCommand = commande; // Mémorise la dernière commande active
        } else {
            commande = lastCommand; // Utilise la dernière commande si aucune nouvelle
        }
    	
        int newX = pacman.getX();
        int newY = pacman.getY();
        this.etat+=1;

        switch (commande) {
        case LEFT:
        	if(newX ==0 && newY == 9)
        	{
        		newX = 18;
        		
        	}
        	else {newX--;}
            comand = 3;
        	break;
        case RIGHT:
        	if(newX ==18 && newY == 9)
        	{
        		newX = 0;
        	}
        	else{newX++;}
            comand = 1;
            break;
        case UP:
            newY--;
            comand = 0;
            break;
        case DOWN:
            newY++;
            comand = 2;
            break;
        default:
            break;
        }

        // Vérifie si Pacman peut se déplacer à la nouvelle position
        if (board.canMove(newX, newY)) {
            pacman.move(newX, newY);
        }
        
        if (board.getBoard()[newY][newX] != '#') {
            // Si la case contient un point, Pacman le "mange"
            if (board.getBoard()[newY][newX] == '.') {
                board.setBoard(newX, newY, ' '); // Remplace le point par un espace vide
                pacman.setScore(10);
            }
        }
        if (board.getBoard()[newY][newX] == 'G') {
            board.setBoard(newX, newY, ' '); // Enlève l'item du plateau
            pacman.setScore(100); // Exemple : bonus de 50 points pour ramasser la guitare
        }
        if (board.getBoard()[newY][newX] == 'B') {
            board.setBoard(newX, newY, ' '); // Enlève l'item du plateau
            pacman.setMoveDelay(1);
            Timer speedResetTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pacman.setMoveDelay(0);  // Rétablit la vitesse normale
                }
            });
            speedResetTimer.setRepeats(false);  // Le timer ne se répète pas
            speedResetTimer.start();
        }
        
        if (board.getBoard()[newY][newX] == 'S') {
            board.setBoard(newX, newY, ' '); // Enlève l'item du platea
        }

        
        // Déplacement des monstres
        for (Monstre monstre : monstres) {
            monstre.moveMonstre(pacman.getX(), pacman.getY(), comand, board);
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
    	for (Monstre monstre : monstres) {
    		if(monstre.getX() == pacman.getX() && monstre.getY() == pacman.getY()) {
    			return true;
    		}
        }
    	
    	return false;
    }
    
    public boolean isWin() {
    	for (int y = 0; y < board.getBoard().length; y++) {
            for (int x = 0; x < board.getBoard()[y].length; x++) {
                if (board.getBoard()[y][x] == '.') {
                    return false; // Il reste des points à manger
                }
            }
    }
    	return true;
    }
    
    public List<Monstre> getMonstres() {
        return monstres;
    }

    public int getPacmanX() {
        return pacman.getX();
    }

    public int getPacmanY() {
        return pacman.getY();
    }
    
    public void reset() {
    	int width = 19;
    	int height = 21;
    	int recorD;
    	if (this.pacman.getScore() > this.pacman.getRecord()) {
    		recorD = this.pacman.getScore();
    	}
    	else {
    		recorD = this.pacman.getRecord();
    	}
    	this.board = new Board(width, height);
        this.pacman = new Pacman(width / 2, height / 2);  // Pacman commence au milieu du plateau
        this.monstres = new ArrayList<>();
        this.monstres.add(new Monstre(1, 1, 0));
        this.monstres.add(new Monstre(width - 2, 1, 2));
        this.monstres.add(new Monstre(1, height - 2, 1));
        this.monstres.add(new Monstre(width - 2, height - 2, 3));
        this.pacman.setRecord(recorD);
        this.pacman.setScore(0);
        this.lastCommand = Cmd.IDLE;

        // Réinitialiser le tableau du jeu (board)
          // Initialiser le tableau avec des points et des murs
    }
    
    public void next() {
    	int width = 19;
    	int height = 21;
    	this.board = new Board(width, height);
    	int scorE = this.pacman.getScore();
    	int recorD = this.pacman.getRecord();
    	int leveL = this.pacman.getLevel();
    	
    	
    	this.pacman = new Pacman(width / 2, height / 2);  // Pacman commence au milieu du plateau
    	this.pacman.setScore(scorE);
    	this.pacman.setRecord(recorD);
    	this.pacman.setLevel(leveL+1);
    	this.monstres = new ArrayList<>();
        
        this.monstres.add(new Monstre(1, 1, 0));
        this.monstres.add(new Monstre(width - 2, 1, 2));
        this.monstres.add(new Monstre(1, height - 2, 1));
        this.monstres.add(new Monstre(width - 2, height - 2, 3));
        for (int i =0; i<4 ;i++) {
        	//Distance à partir de laquelle le monstre détecte Pacman augmente en fonction du niveau
        	this.monstres.get(i).setDistanceThreshold(10 + this.pacman.getLevel()*2);
        }
        
        this.lastCommand = Cmd.IDLE;
    }
    public Cmd getLastCommand() {
    	return this.lastCommand;
    }
    
    public int getEtat() {
    	return this.etat;
    }
}
