package br.com.cod3r.ms.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FieldsTest { // TODO update uniter tests to new version of functions
	
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
	
	@Test
	void testNonUnderminedNonMarkedField() {
		assertTrue(field.toOpen());
	}
	
	@Test
	void testMarkedNonUndermined() {
		field.toggleMark();
		assertFalse(field.toOpen());
	}
	
	@Test
	void testOpenUnderminedMarked() {
		field.toggleMark();
		field.undermine();
		assertFalse(field.toOpen());
	}
	
	@Test
	void testOpenUnderminedNonMarked() {
		field.undermine();
		field.toOpen();
		
		boolean result = field.isUndermined() && field.isOpen();
		assertTrue(result);
	}
	
	@Test
	void testOpenWithNeighbours() {
		
		Field field11 = new Field(1, 1);
		Field field22 = new Field(2, 2);
		
		field22.addNeighbour(field11);
		field.addNeighbour(field22);
		field.toOpen();
		
		assertTrue(field22.isOpen() && field11.isOpen());
	}
	
	@Test
	void testOpenWithNeighboursUndermined() {
		
		Field field11 = new Field(1, 1);
		Field field12 = new Field(1, 2);
		field12.undermine();
		
		Field field22 = new Field(2, 2);
		field22.addNeighbour(field11);
		field22.addNeighbour(field12);
		
		field.addNeighbour(field22);
		field.toOpen();
		
		assertTrue(field22.isOpen() && !field11.isOpen());
	}

}
