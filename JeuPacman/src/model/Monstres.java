package model;

import java.util.Random;

public class Monstres {
    private int x, y;
    private final int DISTANCE_THRESHOLD = 10;
    private int moveDelayCounter;
    private int lastDirection;

    public Monstres(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        new Random();
        this.moveDelayCounter = 0;
        this.lastDirection = -1;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void moveMonstre(int pacmanX, int pacmanY, Board board) {
        if (moveDelayCounter > 0) {
            moveDelayCounter--;
            return;
        }

        if (distanceToPacman(pacmanX, pacmanY) < DISTANCE_THRESHOLD) {
            chasePacman(pacmanX, pacmanY, board);
        } else {
            autonomousMove(board);
        }

        moveDelayCounter = 1;
    }

    private int distanceToPacman(int pacmanX, int pacmanY) {
        return Math.abs(this.x - pacmanX) + Math.abs(this.y - pacmanY);
    }

    private void chasePacman(int pacmanX, int pacmanY, Board board) {
        int dx = Integer.compare(pacmanX, this.x);
        int dy = Integer.compare(pacmanY, this.y);

        if (dx != 0 && board.canMove(this.x + dx, this.y)) {
            this.x += dx;
        } else if (dy != 0 && board.canMove(this.x, this.y + dy)) {
            this.y += dy;
        } else {
            autonomousMove(board);
        }
    }

    private void autonomousMove(Board board) {
        // Movimento em linha reta, mudando de direção quando um bloqueio é encontrado
        int[] directions = {0, 1, 2, 3}; // 0 - cima, 1 - direita, 2 - baixo, 3 - esquerda
        Random rand = new Random();

        // Embaralha as direções para garantir que o monstro escolha aleatoriamente
        for (int i = 0; i < 4; i++) {
            int direction = directions[rand.nextInt(4)];

            // Evitar voltar para a direção anterior
            if (direction != lastDirection) {
                move(direction, board);
                lastDirection = direction; // Atualiza a direção que o monstro veio
                break;
            }
        }
    }


/*  int newX = pacman.getX();
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
*/    
    
    private void move(int direction, Board board) {
    	int newX = this.x;
        int newY = this.y;
        
        switch (direction) {
            case 0: newY--; break;
            case 2: newY++; break;
            case 1:
            	if(newX ==18 && newY == 9)
            	{
            		newX = 0;
            	}
            	else{newX++;}
                break;
            case 3:
            	if(newX ==0 && newY == 9)
            	{
            		newX = 18;
            	}
            	else {newX--;}
                break;
            default:
                break;
        }
        
        if (board.canMove(newX, newY)) {
            this.x = newX;
            this.y = newY;
        }
    }
}
