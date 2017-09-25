package backend;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CellGameOfLife extends Cell {
	public static final int EMPTY = 0;
	public static final int LIVE = 1;
	
	public CellGameOfLife(int state, Image image, ArrayList<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, image, neighborCells, rowNumber, columnNumber);
	}
	
	public void live() {
		changeState(LIVE);
	}
	
	public void die() {
		changeState(EMPTY);
	}
}
