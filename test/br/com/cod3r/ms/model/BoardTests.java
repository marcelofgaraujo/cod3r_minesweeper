package br.com.cod3r.ms.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
		board.openField(1, 2);
		board.openField(0, 0);
		
		boolean result1 = fields
			  .parallelStream()
			  .filter(f -> f.isOpen() && (f.getRow() == 4 && f.getColumn() == 3))
			  .findFirst()
			  .isPresent();
		
		boolean result2 = fields
				.parallelStream()
				.filter(f -> f.isOpen() && (f.getRow() == 1 && f.getColumn() == 2))
				.findFirst()
				.isPresent();
		
		boolean result3 = fields
				.parallelStream()
				.filter(f -> f.isOpen() && (f.getRow() == 0 && f.getColumn() == 0))
				.findFirst()
				.isPresent();
		
		assertTrue(result1);
		assertTrue(result2);
		assertTrue(result3);
	}
	
	@Test
	void testChangeMark() {
		board.changeMark(2, 2);
		board.changeMark(4, 3);
		board.changeMark(0, 2);
		board.changeMark(0, 2);
		
		boolean result1 = fields
				  .parallelStream()
				  .filter(f -> f.isMarked() && (f.getRow() == 2 && f.getColumn() == 2))
				  .findFirst()
				  .isPresent();
			
			boolean result2 = fields
					.parallelStream()
					.filter(f -> f.isMarked() && (f.getRow() == 4 && f.getColumn() == 3))
					.findFirst()
					.isPresent();
			
			boolean result3 = fields
					.parallelStream()
					.filter(f -> !f.isMarked() && (f.getRow() == 0 && f.getColumn() == 2))
					.findFirst()
					.isPresent();
			
		assertTrue(result1);
		assertTrue(result2);
		assertTrue(result3);
	}
	
	@Test
	void testResetGame() {
		board.resetGame();
		boolean result = fields.parallelStream().allMatch(f -> !f.isOpen());
		
		assertTrue(result);
	}
	
	@Test
	void testGoalReached() {
		board.resetGame();
		fields.parallelStream().forEach(f -> {
			if (f.isUndermined()) {
				f.toggleMark();
			} else {
				f.toOpen();
			}
		});
		
		assertTrue(board.goalReached());
	}
	
	@Test
	void testShowMines() {
		board.resetGame();
		board.showMines();
		
		int openFields = (int) fields.parallelStream().filter(f -> f.isUndermined() && f.isOpen()).count();
		int closedFields = (int) fields.parallelStream().filter(f -> !f.isUndermined() && !f.isOpen()).count();
		
		assertEquals(openFields, mines);
		assertEquals(closedFields, (rows * columns) - mines);
	}
}
