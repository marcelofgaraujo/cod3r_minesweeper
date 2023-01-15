package br.com.cod3r.ms.model;

import java.util.ArrayList;
import java.util.List;

public class Field {
	
	private final int row;
	private final int column;
	
	private boolean open;
	private boolean undermined;
	private boolean marked;
	
	private List<Field> neighbourhood = new ArrayList<>();
	
	Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	boolean addNeighbour(Field neighbour) {
		boolean differentRow = this.row != neighbour.row;
		boolean differentColumn = this.column != neighbour.column;
		boolean diagonal = differentRow && differentColumn;
		
		int deltaRow = Math.abs(this.row - neighbour.row);
		int deltaColumn = Math.abs(this.column- neighbour.column);
		int genDelta = deltaRow + deltaColumn;
		
		if(genDelta == 1 && !diagonal) {
			neighbourhood.add(neighbour);
			return true;
		} else if(genDelta == 2 && diagonal) {
			neighbourhood.add(neighbour);
			return true;
		} else {
			return false;
		}
	}
}
