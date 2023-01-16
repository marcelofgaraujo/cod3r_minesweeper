package br.com.cod3r.ms.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.ms.exception.ExitGame;
import br.com.cod3r.ms.exception.Explosion;
import br.com.cod3r.ms.model.Board;

public class ConsoleBoard {
	
	private Board board;
	private Scanner enter = new Scanner(System.in);

	public ConsoleBoard(Board board) {
		this.board = board;
		
		executeGame();
	}

	private void executeGame() {
		
		try {
			boolean continueGame = true;
			
			while(continueGame) {
				gameCicle();
				
				System.out.print("Another game?? (Y/n)");
				String ans = enter.nextLine();
				
				if("n".equalsIgnoreCase(ans)) {
					continueGame = false;
				} else {
					board.resetGame();
				}
			}
		} catch(ExitGame ex) {
			System.out.println("Bye!!!");
		} finally {
			enter.close();
		}
		
	}

	private void gameCicle() {
		
		try {
			
			while(!board.goalReached()) {
				System.out.println(board);
				
				String typed = getTypedValue("Type (x, y): ");
				
				Iterator<Integer> xy = Arrays.stream(typed.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
				
				typed = getTypedValue("1 - open / 2 - mark: ");
				
				if("1".equals(typed)) {
					board.openField(xy.next(), xy.next());
				} else if ("2".equals(typed)) {
					board.changeMark(xy.next(), xy.next());
				}
			}
			
			System.out.println("You win!!");
		} catch(Explosion ex) {
			System.out.println("You lose...");
		}
		
	}
	
	private String getTypedValue(String text) {
		System.out.print(text);
		String typed = enter.nextLine();
		
		if("exit".equalsIgnoreCase(typed)) {
			throw new ExitGame();
		}
		
		return typed;
	}
	
}
