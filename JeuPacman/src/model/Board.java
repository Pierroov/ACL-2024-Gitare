package model;


/**
 * @author Christoff da Silva
 *
 * Classe représentant le plateau du jeu avec des murs et un labyrinthe
 */
public class Board {

    private int width, height;
    private char[][] board;

    /**
     * Constructeur pour générer le plateau avec les murs extérieurs et un labyrinthe
     * 
     * @param width largeur du plateau
     * @param height hauteur du plateau
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = generateBoard(width, height);
    }

    /**
     * Génère le plateau avec des murs autour et à l'intérieur
     * 
     * @param width largeur du plateau
     * @param height hauteur du plateau
     * @return le plateau généré
     */
    private char[][] generateBoard(int width, int height) {
        char[][] board = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    board[y][x] = '#';  // Mur autour du plateau
                } else {
                    board[y][x] = ' ';  // Espace vide à l'intérieur
                }
            }
        }

        // Ajouter un labyrinthe interne
        //TODO: On va changer la façon dont le labyrinthe est créé  
        for (int y = 2; y < height - 2; y += 2) {
            for (int x = 2; x < width - 2; x++) {
                board[y][x] = '#';
            }
        }

        return board;
    }

    /**
     * Retourne le plateau
     * 
     * @return le plateau sous forme de tableau 2D
     */
    public char[][] getBoard() {
        return this.board;
    }

    /**
     * Vérifie si Pacman peut se déplacer à la position donnée (sans traverser les murs)
     * 
     * @param x position en X
     * @param y position en Y
     * @return vrai si le mouvement est possible, faux sinon
     */
    public boolean canMove(int x, int y) {
        return this.board[y][x] != '#';
    }
}
