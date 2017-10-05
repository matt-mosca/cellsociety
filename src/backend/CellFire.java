package backend;

import java.util.List;
import javafx.scene.paint.Color;

/**
 * CellFire.java
 * @author Matthew Mosca
 * Cell object subclass specific to the Fire simulation. Contains constants and methods that play roles
 * particular to this simulation.
 * @version 10.04.17
 */
public class CellFire extends Cell{
	
	public static final int EMPTY = 0;
	public static final int TREE = 1;
	public static final int BURNING = 2;
	
	/**
	 * Parameterized constructor for CellFire objects. Takes in only the parameters that feed into the 
	 * superclass constructor.
	 * @param state
	 * @param color
	 * @param neighborCells
	 * @param rowNumber
	 * @param columnNumber
	 */
	public CellFire(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, color, neighborCells, rowNumber, columnNumber);
	}
	
	/**
	 * Changes the state of a cell representing a tree to the value representing a fire.
	 */
	public void catchFire() {
			changeState(BURNING);
	}
	
	/**
	 * Changes the state of a cell representing a fire to the value representing an empty cell.
	 */
	public void burnDownToEmpty() {
		if(getState() == BURNING)
			changeState(EMPTY);
	}
}
