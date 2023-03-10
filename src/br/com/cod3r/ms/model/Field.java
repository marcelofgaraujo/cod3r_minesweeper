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
	
	public void registerObserver(ObserverField observer) {
		observers.add(observer);
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
	
	public void toggleMark() {
		if(!this.open) {
			this.marked = !marked;
			
			if(this.marked) {
				notifyObservers(FieldEvents.MARK);
			} else {
				notifyObservers(FieldEvents.MARKOFF);
			}
		}
	}
	
	public boolean toOpen() {
		if(!open && !marked) {
			open = true;
			
			if(undermined) {
				notifyObservers(FieldEvents.EXPLODE);
			}
			
			setOpen(open);
			
			if(safeNeighbourhood()) {
				neighbourhood.forEach(n -> n.toOpen());
			}
			
			return true;
			
		} else {
			return false;			
		}
		
	}
	
	public boolean safeNeighbourhood() {
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
		
		if(this.open) {
			notifyObservers(FieldEvents.OPEN);
		}
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
		boolean revealed = !undermined && open;
		boolean protectedField = undermined && marked;
		
		return revealed || protectedField;
	}
	
	public int neighbourhoodMines() {
		return (int) neighbourhood.stream().filter(n -> n.undermined).count();
	}
	
	void reset() {
		this.open = false;
		this.undermined = false;
		this.marked = false;
		
		notifyObservers(FieldEvents.RESET);
	}
	
}
