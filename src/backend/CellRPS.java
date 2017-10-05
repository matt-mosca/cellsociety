package backend;

import java.util.List;
import javafx.scene.paint.Color;

/**
 * CellRPS.java
 * @author Matthew Mosca
 * Cell object subclass specific to the Rock, Paper, Scissors simulation. Contains constants, variables, 
 * and methods that play roles particular to this simulation.
 * @version 10.04.17
 */
public class CellRPS extends Cell{
	public static final int EMPTY = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	
	private int gradientLevel;
	
	/**
	 * Parameterized constructor for CellRPS objects. Takes in parameters that feed into the 
	 * superclass constructor, as well as an additional parameter that is assigned to the gradient level
	 * of the cell.
	 * @param state
	 * @param color
	 * @param neighborCells
	 * @param rowNumber
	 * @param columnNumber
	 * @param gradientLevel - the brightness level of the visual representation of the cell; ranges from
	 * 0 to 9, with 0 being the darkest (lowest RGB value) and 9 being the lightest
	 */
	public CellRPS(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber, int gradientLevel) {
		super(state, color, neighborCells, rowNumber, columnNumber);
		this.gradientLevel = gradientLevel;
	}
	
	/**
	 * Getter method for the gradient level of the cell.
	 * @return gradientLevel - the brightness gradient level of the color of the cell
	 */
	public int getGradientLevel() {
		return gradientLevel;
	}
	
	/**
	 * Setter method for the gradient level of the cell.
	 * @param gradient
	 */
	public void setGradientLevel(int gradient) {
		gradientLevel = gradient;
	}
}
