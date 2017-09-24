package backend;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CellGameOfLife extends Cell {
	public static final int ALIVE = 0;
	public static final int DEAD = 1;
	
	public CellGameOfLife(int state, Image image, ArrayList<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, image, neighborCells, rowNumber, columnNumber);
	}
	
	public void live() {
		changeState(ALIVE);
	}
	
	public void die() {
		changeState(DEAD);
	}
}
