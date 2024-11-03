package model;

/**
 * @author Christoff da Silva
 *
 *         Classe représentant le plateau du jeu avec des murs et un labyrinthe
 */
public class Board {

	private int width, height;
	private char[][] board;

	/**
	 * Constructeur pour générer le plateau avec les murs extérieurs et un
	 * labyrinthe
	 * 
	 * @param width  largeur du plateau
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
        for (int i = 1; i<6; i++)
        {
        	board[i][width/2] = '#'; // Mur vertical en heut au milieu du plateau
        }
        for (int y = 2; y<width-2; y++)
        {
        	if (y == 7  || y == 14 || y == width-7 || y == width-14 || y == width/2-1 || y == width/2+1)
        	{
        	board[2][y] = ' ';
        	}
        
	        else 
	        {
	        	board[2][y] = '#';
	        }
        }
        for (int y = 0; y<width; y++)
        {
        	if (y == 3  || y == 10 || y == width-3 || y == width-10 || y == width/2-4 || y == width/2+4)
        	{
	        	board[4][y] = ' ';
	        	board[5][y] = ' ';
        	}
        	else
        	{
        		board[4][y] = '#';
	        	board[5][y] = '#';
        	}
        }
        board[10][0] = ' '; board[10][width-1] = ' ';
        board[9][1] = '#'; board[9][2] = '#';
        board[11][1] = '#'; board[11][2] = '#';
        board[9][width-2] = '#'; board[9][width-3] ='#';
        board[11][width-2] = '#'; board[11][width-3] = '#';
        board[7][1] = '#'; board[7][2] = '#';
        board[13][2] = '#'; board[14][2] = '#'; board[15][2] = '#'; board[15][3] = '#';
        board[7][width-2] = '#'; board[7][width-3] ='#';
        board[13][width-3] = '#'; board[14][width-3] = '#'; board[15][width-3] = '#'; board[15][width-4] = '#';
        board[4][10] = '#'; board[5][10] ='#';
        board[13][8] = '#'; board[13][9] = '#'; board[13][10] = '#'; 
        board[14][8] = '#'; board[14][9] = '#'; board[14][10] = '#';
        board[13][width-8] = '#'; board[13][width-9] = '#'; board[13][width-10] = '#'; 
        board[14][width-8] = '#'; board[14][width-9] = '#'; board[14][width-10] = '#';
        board[height-3][2] = '#'; board[height-4][2] = '#'; board[height-3][3] = '#';
        board[height-3][width-3] = '#'; board[height-4][width-3] = '#'; board[height-3][width-4] = '#';
        board[height-5][4] = '#'; board[height-5][width-5] = '#'; board[height-6][4] = '#'; board[height-6][width-5] = '#';
        board[height-5][5] = '#'; board[height-5][width-6] = '#';
        board[height-4][5] = '#'; board[height-4][width-6] = '#';
        board[height-3][5] = '#'; board[height-3][width-6] = '#';
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
	 * Vérifie si Pacman peut se déplacer à la position donnée (sans traverser les
	 * murs)
	 * 
	 * @param x position en X
	 * @param y position en Y
	 * @return vrai si le mouvement est possible, faux sinon
	 */
	public boolean canMove(int x, int y) {
		return this.board[y][x] != '#';
	}
}
