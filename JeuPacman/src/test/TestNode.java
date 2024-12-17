package test;
import model.Node;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestNode {

	@Test
	void testgetParent() {
		Node rac = null;
		Node node0 = new Node(2, 3, 4, 5, rac);
		Node node = new Node(1, 1, 2, 2, node0);
		assertEquals(node0, node.getParent());
	}
	
	@Test
	void testCosts() {
		Node rac = null;
		Node node0 = new Node(2, 3, 4, 5, rac);
		Node node = new Node(1, 1, 2, 3, node0);
		assertEquals(2, node.getGCost());
		assertEquals(5, node.getFCost());
		
	}
	
	@Test
	void testCoord() {
		Node rac = null;
		Node node0 = new Node(2, 3, 4, 5, rac);
		Node node = new Node(1, 6, 2, 3, node0);
		assertEquals(1, node.getX());
		assertEquals(6, node.getY());
		
	}
	@Test
	void testcompareTo() {
		Node rac = null;
		Node node0 = new Node(2, 3, 4, 5, rac);
		Node node = new Node(1, 6, 2, 3, node0);
		assertTrue(node.compareTo(node0)<0);
	}
}
