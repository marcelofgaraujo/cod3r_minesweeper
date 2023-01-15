package br.com.cod3r.ms.model;

public class Field {
	
	private final int row;
	private final int column;
	private boolean undermined;
	
	Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
