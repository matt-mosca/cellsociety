package backend;

import java.util.List;

import javafx.scene.paint.Color;

/**
 * CellSegregation.java
 * @author Yiqin Zhou and Matthew Mosca
 * Cell object subclass specific to the Segregation simulation. Contains constants and methods that play 
 * roles particular to this simulation.
 * @version 10.04.17
 */
public class CellSegregation extends Cell{
	
	/**
	 * Parameterized constructor for CellSegregation objects. Takes in only the parameters that feed 
	 * into the superclass constructor.
	 * @param state
	 * @param color
	 * @param neighborCells
	 * @param rowNumber
	 * @param columnNumber
	 */
	public CellSegregation(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, color, neighborCells, rowNumber, columnNumber);
	}
}
