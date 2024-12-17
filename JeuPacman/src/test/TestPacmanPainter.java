package test;
import model.PacmanGame;
import model.PacmanPainter;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPacmanPainter {

	@Test
	void testDimensions() {
		PacmanGame game = new PacmanGame(19, 21);
		PacmanPainter paint = new PacmanPainter(game);
		assertEquals(30*19, paint.getWidth());
		assertEquals(30*21, paint.getHeight());
		
	}

}
