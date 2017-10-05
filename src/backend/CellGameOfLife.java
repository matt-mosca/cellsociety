package backend;

import java.util.List;

import javafx.scene.paint.Color;

/**
 * CellGameOfLife.java
 * @author matthewmosca
 * Cell object subclass specific to the Game of Life simulation. Contains constants and methods that play 
 * roles particular to this simulation.
 * @version 10.04.17
 */
public class CellGameOfLife extends Cell {
	public static final int EMPTY = 0;
	public static final int LIVE = 1;
	
	/**
	 * Parameterized constructor for CellGameOfLife objects. Takes in only the parameters that feed into the 
	 * superclass constructor.
	 * @param state
	 * @param color
	 * @param neighborCells
	 * @param rowNumber
	 * @param columnNumber
	 */
	public CellGameOfLife(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, color, neighborCells, rowNumber, columnNumber);
	}
	
	/**
	 * Changes the state of an empty cell to make it live
	 */
	public void live() {
		changeState(LIVE);
	}
	
	/**
	 * Changes the state of a live cell to make it empty
	 */
	public void die() {
		changeState(EMPTY);
	}
}
