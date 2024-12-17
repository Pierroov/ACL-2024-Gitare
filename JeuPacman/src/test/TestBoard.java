package test;


import model.Board;


import static org.junit.Assert.*;

import org.junit.Test;

public class TestBoard {

	@Test
	public void testgetBoard() {
		Board board = new Board(19,21);
		assertEquals(board.board, board.getBoard());
		
	}
	
	
	@Test
	public void testsetBoard() {
		Board board = new Board(19,21);
		board.setBoard(2, 5, 'o');
		assertEquals('o', board.board[5][2]);
		
	}
	
	@Test
	public void testcanMove() {
		Board board = new Board(19,21);
		board.setBoard(2, 5, '.');
		assertTrue(board.canMove(2,5));
		board.setBoard(2, 6, '#');
		assertFalse(board.canMove(2,6));
		}
	
	@Test
	public void testDimensions() {
		Board board = new Board(19,21);
		assertEquals(19,board.getWidth());
		assertEquals(21,board.getHeight());
		}
	
	@Test
	public void testplaceItem() {
		Board board = new Board(19,21);
		board.setBoard(2, 5, 'G');
		board.placeItem(2, 5, 'o');
		assertEquals('o', board.board[5][2]);
		board.setBoard(2, 6, 'B');
		board.placeItem(2, 6, 'd');
		assertEquals('d', board.board[6][2]);
		
	}
	
}
