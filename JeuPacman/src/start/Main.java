package start;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;

/**
 * lancement du moteur avec le jeu
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		// creation du jeu particulier et de son afficheur
		PacmanGame game = new PacmanGame(19, 21);
		PacmanPainter painter = new PacmanPainter(game);
		PacmanController controller = new PacmanController();

		// Iniciar o motor gráfico do jogo
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();

		/*char[][] board = new char[21][19];
		String[] sboard={"###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################",
				 "###################"};
for (int line = 0; line<21; line++)
{
	for (int col = 0; col<19; col++)
	{
		char c = sboard[line].charAt(col);
		board[line][col] = c;
	}
}
System.out.println(board[20][18]);
*/
	}

}
