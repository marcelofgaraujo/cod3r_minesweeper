package br.com.cod3r.ms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.ms.exception.Explosion;

public class Board {
	
	private int rows;
	private int columns;
	private int mines;
	
	private final List<Field> fields = new ArrayList<>();

	public Board(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		hitchNeighbours();
		sortMines();
	}

	private void generateFields() {
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
				fields.add(new Field(row, column));
			}
		}
	}
	
	private void hitchNeighbours() {
		for(Field f1: fields) {
			for(Field f2: fields) {
				f1.addNeighbour(f2);
			}
		}
	}
	
	private void sortMines() {
		long armedMines = 0;
		Predicate<Field> undermined = f -> f.isUndermined();
		
		do {
			armedMines = fields.stream().filter(undermined).count();
			int random = (int) (Math.random() * fields.size());
			fields.get(random).undermine();
		} while(armedMines < mines);
	}
	
	public void openField(int row, int column) {
		Predicate<Field> position = f -> f.getRow() == row && f.getColumn() == column;
		
		try {
			fields.parallelStream()
				.filter(position)
				.findFirst()
				.ifPresent(f -> f.toOpen());
		} catch (Explosion e) {
			fields.forEach(f -> f.setOpen(true));
			throw e;
		}
	}
	
	public void changeMark(int row, int column) {
		Predicate<Field> position = f -> f.getRow() == row && f.getColumn() == column;
		
		fields.parallelStream()
			.filter(position)
			.findFirst()
			.ifPresent(f -> f.toggleMark());
	}
	
	public boolean goalReached() {
		return fields.stream().allMatch(f -> f.goalAchieved());
	}
	
	public void resetGame() {
		fields.stream().forEach(f -> f.reset());
		sortMines();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				sb.append(" ");
				sb.append(fields.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
