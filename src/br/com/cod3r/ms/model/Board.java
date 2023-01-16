package br.com.cod3r.ms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
		} while(armedMines < mines);
	}
	
	
}
