package test;

import model.PacmanGame;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPacmanGame {

	@Test
	void testCoordPacman() {
		PacmanGame game = new PacmanGame(19, 21);
		game.getPacman().x = 2;
		game.getPacman().y = 6;
		assertEquals(2, game.getPacmanX());
		assertEquals(6, game.getPacmanY());
		
	}
	
	@Test
	void testCoordPacma() {
		PacmanGame game = new PacmanGame(19, 21);
		game.getPacman().x = 2;
		game.getPacman().y = 6;
		assertEquals(2, game.getPacmanX());
		assertEquals(6, game.getPacmanY());

		
	}

}
