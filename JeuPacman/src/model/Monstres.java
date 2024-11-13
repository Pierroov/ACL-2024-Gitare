package model;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Monstres {
    private int x, y;
    private final int DISTANCE_THRESHOLD = 10;
    private int moveDelayCounter;
    private int lastDirection;
    private int type;
    private Color color;

    public Monstres(int startX, int startY, int type) {
        this.x = startX;
        this.y = startY;
        this.type = type;
        this.moveDelayCounter = 0;
        this.lastDirection = -1;

        switch (type) {
            case 0: color = Color.RED; break;
            case 1: color = Color.PINK; break; 
            case 2: color = Color.CYAN; break; 
            case 3: color = Color.ORANGE; break;
            default: color = Color.GRAY; break;
        }
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public Color getColor() { return this.color; }

    public void moveMonstre(int pacmanX, int pacmanY, Board board) {
        if (moveDelayCounter > 0) {
            moveDelayCounter--;
            return;
        }

        switch (type) {
            case 0:
                chasePacman(pacmanX, pacmanY, board);
                break;
            case 1:
                evadePacman(pacmanX, pacmanY, board);
                break;
            case 2:
                autonomousMove(board);
                break;
            case 3:
                patrol(board);
                break;
        }

        moveDelayCounter = 3;
    }

    private void chasePacman(int pacmanX, int pacmanY, Board board) {
        int dx = pacmanX - this.x;
        int dy = pacmanY - this.y;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0 && board.canMove(this.x + 1, this.y)) {
                this.x++;
            } else if (dx < 0 && board.canMove(this.x - 1, this.y)) {
                this.x--;
            } else {
                moveVertically(dy, board);
            }
        } else {
            moveVertically(dy, board);
        }
    }

    private void evadePacman(int pacmanX, int pacmanY, Board board) {
        int dx = Integer.compare(this.x, pacmanX);
        int dy = Integer.compare(this.y, pacmanY);
        
        if (dx != 0 && board.canMove(this.x + dx, this.y)) {
            this.x += dx;
        } else if (dy != 0 && board.canMove(this.x, this.y + dy)) {
            this.y += dy;
        } else {
            autonomousMove(board);
        }
    }

    private void autonomousMove(Board board) {
        int[] directions = {0, 1, 2, 3};
        Random rand = new Random();
        ArrayList<Integer> validDirections = new ArrayList<>();

        for (int direction : directions) {
            int newX = this.x;
            int newY = this.y;

            switch (direction) {
                case 0: newY--; break;
                case 1: newX++; break;
                case 2: newY++; break;
                case 3: newX--; break;
            }

            if (board.canMove(newX, newY) && direction != (lastDirection + 2) % 4) {
                validDirections.add(direction);
            }
        }

        if (!validDirections.isEmpty()) {
            int direction = validDirections.get(rand.nextInt(validDirections.size()));
            move(direction, board);
            lastDirection = direction;
        }
    }

    private void patrol(Board board) {
        Random rand = new Random();
        int[] directions = {-1, 0, 1};
        
        int dx = directions[rand.nextInt(3)];
        int dy = directions[rand.nextInt(3)];

        int newX = this.x + dx;
        int newY = this.y + dy;

        if (board.canMove(newX, newY)) {
            this.x = newX;
            this.y = newY;
        }
    }

    private void move(int direction, Board board) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case 0: newY--; break;
            case 2: newY++; break;
            case 1:
                if (newX == 18 && newY == 9) {
                    newX = 0;
                } else {
                    newX++; 
                }
                break;
            case 3:
                if (newX == 0 && newY == 9) {
                    newX = 18;
                } else {
                    newX--; 
                }
                break;
            default: break;
        }

        this.x = newX;
        this.y = newY;

        if ((this.x == 0 && this.y == 9) || (this.x == 18 && this.y == 9)) {
            if (direction == 1 && this.x == 18) {
                this.x = 0;
            } else if (direction == 3 && this.x == 0) {
                this.x = 18;
            }
        }
    }

    private void moveVertically(int dy, Board board) {
        if (dy > 0 && board.canMove(this.x, this.y + 1)) {
            this.y++;
        } else if (dy < 0 && board.canMove(this.x, this.y - 1)) {
            this.y--;
        }
    }
}
