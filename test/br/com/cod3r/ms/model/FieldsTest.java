package br.com.cod3r.ms.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldsTest {
	
	private Field field = new Field(3, 3);
	
	@Test
	void testRealNeighbourRange1() {
		Field neighbour = new Field(3, 2);
		boolean result = field.addNeighbour(neighbour);
		
		assertTrue(result);
	}
	
	@Test
	void testRealNeighbourRange2() {
		Field neighbour = new Field(2, 2);
		boolean result = field.addNeighbour(neighbour);
		
		assertTrue(result);
	}
	
	@Test
	void testNonNeighbour() {
		Field neighbour = new Field(1, 1);
		boolean result = field.addNeighbour(neighbour);
		
		assertFalse(result);
	}
	
	@Test
	void defaultMarkedValue() {
		assertFalse(field.isMarked());
	}
	
	@Test
	void testToggleMarking() {
		field.toggleMark();
		assertTrue(field.isMarked());
	}
	
	@Test
	void testTwiceToggleMarking() {
		field.toggleMark();
		field.toggleMark();
		assertFalse(field.isMarked());
	}
	

}
