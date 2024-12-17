package test;

import model.Pacman;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPacman {

	@Test
	void testCoord() {
		Pacman pacman = new Pacman(1, 2);
		assertEquals(1, pacman.getX());
		assertEquals(2, pacman.getY());
		
	}
	
	@Test
	void testScore() {
		Pacman pacman = new Pacman(1, 2);
		pacman.setScore(10);
		assertEquals(10, pacman.score);
		
		assertEquals(10, pacman.getScore());
		
	}
	
	@Test
	void testRecord() {
		Pacman pacman = new Pacman(1, 2);
		pacman.setRecord(10);
		assertEquals(10, pacman.record);
		
		assertEquals(10, pacman.getRecord());
		
	}
	
	
	@Test
	void testsetMoveDelay() {
		Pacman pacman = new Pacman(1, 2);
		pacman.setMoveDelay(0);
		assertEquals(0, pacman.moveDelay);
		
	}
	
	@Test
	void testMove() {
		Pacman pacman = new Pacman(1, 2);
		pacman.move(2,4);
		assertEquals(2, pacman.getX());
		assertEquals(4, pacman.getY());
		pacman.moveDelayCounter = 2;
		pacman.move(1,4);
		assertEquals(1, pacman.moveDelayCounter);
		
	}
	
	
	

}
