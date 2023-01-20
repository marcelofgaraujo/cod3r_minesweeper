package br.com.cod3r.ms.view;

import javax.swing.JFrame;

import br.com.cod3r.ms.model.Board;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = -4879662604866799790L;
	
	public MainScreen() {
		Board board = new Board(16, 30, 50);
		add(new BoardPanel(board));
		
		setTitle("MINESWEEPER");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainScreen();
	}

}
