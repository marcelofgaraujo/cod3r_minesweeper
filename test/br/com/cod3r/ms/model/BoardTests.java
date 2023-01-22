package br.com.cod3r.ms.model;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class BoardTests {

	private int mines = 5;
	private int rows = 5;
	private int columns = 5;
	private Board board = new Board(rows, columns, mines);
	private List<Field> fields = board.getFields();

	@Test
	void testGenerateFields() {
		boolean result = fields.size() == rows * columns;
		assertTrue(result);
	}

	@Test
	void testSortMines() {
		boolean result = board.getMines() == mines;
		assertTrue(result);
	}

	@Test
	void testOpenField() {
		board.openField(4, 3);
		boolean result = fields
			  .parallelStream()
			  .filter(f -> f.isOpen() && (f.getRow() == 4 && f.getColumn() == 3))
			  .findFirst()
			  .isPresent();
		
		assertTrue(result);
	}
}
