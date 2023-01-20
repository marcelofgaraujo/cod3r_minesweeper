package br.com.cod3r.ms.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.cod3r.ms.model.Board;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = -1626447743158795895L;
	
	public BoardPanel(Board board) {
		
		setLayout(new GridLayout(board.getRows(), board.getColumns()));
		
		board.forEachField(f -> add(new FieldButton(f)));
		board.registerObserver(e -> {
			// TODO show game result
		});
	}

}
