package test;

import model.Monstre;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class TestMonstre {

	
	
	@Test
	public void testMonstrePosition() {
		Monstre monstre = new Monstre (1, 1, 0);
        assertEquals(1, monstre.getX());
        assertEquals(1, monstre.getY());
	}
	
	@Test
	public void testColor() {
		Monstre monstre = new Monstre (1, 1, 0);
		Color color = Color.RED;
		assertEquals(color, monstre.getColor());
	}
	
	@Test
	public void testsetDistanceThreshold() {
		Monstre monstre = new Monstre (1, 1, 0);
		monstre.setDistanceThreshold(3);
		assertEquals(3, monstre.distanceThreshold);
	}
	
	@Test
	public void test() {
		
	}
}
