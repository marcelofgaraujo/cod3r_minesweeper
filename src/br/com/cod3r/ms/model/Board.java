package br.com.cod3r.ms.model;

import java.util.ArrayList;
import java.util.List;

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
	}

	private void generateFields() {
		// TODO Auto-generated method stub
		
	}
	
}
