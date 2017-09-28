package backend;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class CellGameOfLife extends Cell {
	public static final int EMPTY = 0;
	public static final int LIVE = 1;
	
	public CellGameOfLife(int state, Color color, ArrayList<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, color, neighborCells, rowNumber, columnNumber);
	}
	
	public void live() {
		changeState(LIVE);
	}
	
	public void die() {
		changeState(EMPTY);
	}
}
