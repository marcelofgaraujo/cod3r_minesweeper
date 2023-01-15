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

}
