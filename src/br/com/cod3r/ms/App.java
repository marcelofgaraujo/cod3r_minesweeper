package br.com.cod3r.ms;

import br.com.cod3r.ms.model.Board;

public class App {

	public static void main(String[] args) {
		
		Board board = new Board(6, 6, 6);
		
		board.openField(3, 3);
		board.changeMark(4, 4);
		board.changeMark(4, 5);
		
		System.out.println(board);

	}

}
