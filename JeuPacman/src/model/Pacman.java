package model;


/**
 * @author Christoff da Silva
 *
 * Classe représentant le personnage Pacman dans le jeu
 */
public class Pacman {
    
    // Position actuelle de Pacman
    private int x, y, moveDelayCounter, score, moveDelay;

    /**
     * Constructeur de Pacman
     * 
     * @param startX position initiale en X
     * @param startY position initiale en Y
     */
    public Pacman(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.moveDelayCounter=0;
        this.moveDelay=1;
        
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
    
    public int getScore() {
    	return this.score;
    }
    
    public void setScore(int bonus) {
    	this.score+= bonus;
    }
    
    /**
     * Déplace Pacman à la nouvelle position si elle est valide
     * 
     * @param newX nouvelle position en X
     * @param newY nouvelle position en Y
     */
    
    
    public void setMoveDelay(int delay) {
        this.moveDelay = delay;
    }
    
    
    public void move(int newX, int newY) {
    	if (moveDelayCounter > 0) {
            moveDelayCounter--;
            return;
        }
    	
    	//moveDelayCounter = 1;
    	//moveDelayCounter = moveDelayCounterValue;  
    	
    	this.x = newX;
        this.y = newY;
    }

   

}
