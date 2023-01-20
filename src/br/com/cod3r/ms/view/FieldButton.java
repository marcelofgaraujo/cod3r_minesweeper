package br.com.cod3r.ms.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.cod3r.ms.model.Field;
import br.com.cod3r.ms.model.FieldEvents;
import br.com.cod3r.ms.model.ObserverField;

public class FieldButton extends JButton implements ObserverField, MouseListener {
	
	private static final long serialVersionUID = 6532026837742045487L;
	
	private final Color DEFAULT_BG = new Color(184, 184, 184);
	private final Color MARKED_BG = new Color(8, 179, 247);
	private final Color EXPLODED_BG = new Color(189, 66, 68);
	private final Color GREEN_TEXT = new Color(0, 100, 0);
	
	private Field field;

	public FieldButton(Field field) {
		this.field = field;
		setBackground(DEFAULT_BG);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		field.registerObserver(this);
	}

	@Override
	public void eventOcurred(Field f, FieldEvents e) {
		switch(e) {
		case OPEN:
			applyOpenStyle();
			break;
		case MARK:
			applyMarkStyle();
			break;
		case EXPLODE:
			applyExplodeStyle();
			break;
		default:
			applyDefaultStyle();
		}
		
	}

	private void applyDefaultStyle() {
		setBackground(DEFAULT_BG);
		setText("");
		
	}

	private void applyOpenStyle() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(field.isUndermined()) {
			setBackground(EXPLODED_BG);
			return;
		}
		
		setBackground(DEFAULT_BG);
		
		switch(field.neighbourhoodMines()) {
		case 1:
			setForeground(GREEN_TEXT);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		String value = !field.safeNeighbourhood() ? field.neighbourhoodMines() + "" : "";
		setText(value);
		
	}
	
	private void applyMarkStyle() {
		setBackground(MARKED_BG);
		setForeground(Color.BLACK);
		setText("M");
		
	}
	
	private void applyExplodeStyle() {
		setBackground(EXPLODED_BG);
		setForeground(Color.WHITE);
		setText("X");
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			field.toOpen();
		} else {
			field.toggleMark();
		}
		
	}
	
	
	// i will not implement these methods below because i will not use them
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
