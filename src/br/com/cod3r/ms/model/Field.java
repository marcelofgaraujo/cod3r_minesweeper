package br.com.cod3r.ms.model;

public class Field {
	
	private final int row;
	private final int column;
	
	private boolean open;
	private boolean undermined;
	private boolean marked;
	
	Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
