package br.com.cod3r.ms.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.cod3r.ms.model.Board;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = -1626447743158795895L;

	public BoardPanel(Board board) {

		setLayout(new GridLayout(board.getRows(), board.getColumns()));

		board.forEachField(f -> add(new FieldButton(f)));

		SwingUtilities.invokeLater(() -> {
			board.registerObserver(e -> {
				if (Boolean.FALSE) {
					JOptionPane.showMessageDialog(this, "You win!! :D");
				} else {
					JOptionPane.showMessageDialog(this, "You lose!! :(");
				}
			});
		});
	}

}
