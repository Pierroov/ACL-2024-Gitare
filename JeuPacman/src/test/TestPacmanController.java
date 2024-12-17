package test;

import model.PacmanController;
import engine.Cmd;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPacmanController {

	@Test
	void testgetCommand() {
		PacmanController pacmanc = new PacmanController();
		
		assertEquals(Cmd.IDLE, pacmanc.getCommand());
		pacmanc.commandeEnCours = Cmd.RIGHT;
		assertEquals(Cmd.RIGHT, pacmanc.getCommand());

	}
	
	@Test
	public void testkeyPressed() {
		PacmanController pacmanc = new PacmanController();
		
		
	}
	
	@Test
	public void testkeyReleased() {
		PacmanController pacmanc = new PacmanController();
		pacmanc.lastCommand = Cmd.RIGHT;
		pacmanc.keyReleased(null);
		assertEquals(pacmanc.commandeEnCours, Cmd.RIGHT);
		
	}

}
