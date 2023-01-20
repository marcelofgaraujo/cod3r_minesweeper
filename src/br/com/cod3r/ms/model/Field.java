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
	private List<ObserverField> observers = new ArrayList<>();
	
	Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	private void notifyObservers(FieldEvents event) {
		observers.stream()
			.forEach(obs -> obs.eventOcurred(this, event));
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
	
	void toggleMark() {
		if(!this.open) {
			this.marked = !marked;
			
			if(this.marked) {
				notifyObservers(FieldEvents.MARK);
			} else {
				notifyObservers(FieldEvents.MARKOFF);
			}
		}
	}
	
	boolean toOpen() {
		if(!open && !marked) {
			open = true;
			
			if(undermined) {
				// TODO implement new version
			}
			
			if(safeNeighbourhood()) {
				neighbourhood.forEach(n -> n.toOpen());
			}
			
			return true;
			
		} else {
			return false;			
		}
		
	}
	
	boolean safeNeighbourhood() {
		return neighbourhood.stream().noneMatch(n -> n.undermined);
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public void undermine() {
		this.undermined = true;
	}
	
	public boolean isUndermined() {
		return this.undermined;
	}
	
	void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isOpen() {
		return this.open;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	boolean goalAchieved() {
		boolean revealed = !undermined && marked;
		boolean protectedField = undermined && marked;
		
		return revealed || protectedField;
	}
	
	long neighbourhoodMines() {
		return neighbourhood.stream().filter(n -> n.undermined).count();
	}
	
	void reset() {
		this.open = false;
		this.undermined = false;
		this.marked = false;
	}
	
}
