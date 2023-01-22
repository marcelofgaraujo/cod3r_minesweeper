package br.com.cod3r.ms.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoardTests {
	
	private int mines = 5;
	private int rows = 5;
	private int columns = 5;
	private Board board = new Board(rows, columns, mines);
	
	@Test
	void testGenerateFields() {
		boolean result = board.getFieldsNumber() == rows * columns; // FIXME fields number getting duplicated
		assertTrue(result);
	}
	
	@Test
	void testSortMines() {
		board.sortMines();
		boolean result = board.getMines() == mines;
		assertTrue(result);
	}
}
