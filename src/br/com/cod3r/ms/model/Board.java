package br.com.cod3r.ms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements ObserverField {

	private final int rows;
	private final int columns;
	private final int mines;

	private final List<Field> fields = new ArrayList<>();
	private final List<Consumer<ResultEvent>> observers = new ArrayList<>();

	public Board(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;

		generateFields();
		hitchNeighbours();
		sortMines();
	}
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public void forEachField(Consumer<Field> function) {
		fields.forEach(function);
	}

	public void registerObserver(Consumer<ResultEvent> observer) {
		observers.add(observer);
	}

	private void notifyObservers(boolean result) {
		observers.stream().forEach(obs -> obs.accept(new ResultEvent(result)));
	}

	private void generateFields() {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				Field field = new Field(row, column);
				field.registerObserver(this);
				fields.add(field);
			}
		}
	}

	private void hitchNeighbours() {
		for (Field f1 : fields) {
			for (Field f2 : fields) {
				f1.addNeighbour(f2);
			}
		}
	}

	private void sortMines() {
		long armedMines = 0;
		Predicate<Field> undermined = f -> f.isUndermined();

		do {
			int random = (int) (Math.random() * fields.size());
			fields.get(random).undermine();
			armedMines = fields.stream().filter(undermined).count();
		} while (armedMines < mines);
	}

	public void openField(int row, int column) {
		Predicate<Field> position = f -> f.getRow() == row && f.getColumn() == column;

		fields.parallelStream().filter(position).findFirst().ifPresent(f -> f.toOpen());
	}

	public void changeMark(int row, int column) {
		Predicate<Field> position = f -> f.getRow() == row && f.getColumn() == column;

		fields.parallelStream().filter(position).findFirst().ifPresent(f -> f.toggleMark());
	}

	public boolean goalReached() {
		return fields.stream().allMatch(f -> f.goalAchieved());
	}

	public void resetGame() {
		fields.stream().forEach(f -> f.reset());
		sortMines();
	}

	@Override
	public void eventOcurred(Field f, FieldEvents e) {
		if (e == FieldEvents.EXPLODE) {
			showMines();
			notifyObservers(false);
		} else if (this.goalReached()) {
			notifyObservers(true);
		}

	}
	
	private void showMines() {
		fields.stream()
			.filter(f -> f.isUndermined())
			.filter(f -> !f.isMarked())
			.forEach(f -> f.setOpen(true));
	}

}
