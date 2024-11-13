package model;


/**
 * @author Christoff da Silva
 *
 * Classe représentant le personnage Pacman dans le jeu
 */
public class Pacman {
    
    // Position actuelle de Pacman
    private int x, y;
    private int score;

    /**
     * Constructeur de Pacman
     * 
     * @param startX position initiale en X
     * @param startY position initiale en Y
     */
    public Pacman(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    /**
     * Retourne la position X actuelle de Pacman
     * 
     * @return la position X de Pacman
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retourne la position Y actuelle de Pacman
     * 
     * @return la position Y de Pacman
     */
    public int getY() {
        return this.y;
    }

    public int getscore() {
    	return this.score;
    }
    /**
     * Déplace Pacman à la nouvelle position si elle est valide
     * 
     * @param newX nouvelle position en X
     * @param newY nouvelle position en Y
     */
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
    
    public void setscore(int pt) {
    	this.score+= pt;
    }
}
