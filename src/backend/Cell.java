package backend;

import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Cell.java
 * @author Yiqin Zhou and Matthew Mosca
 * Super class for cell objects. Contains variables and methods that the cells for all the different 
 * simulations require.
 * @version 10.01.17
 */
public class Cell {
	private int state;
	private Color color; 
	private List<Cell> neighborCells;
	private int rowNumber;
	private int columnNumber;
	
	/**
	 * Parameterized constructor for Cell object. Sets Cell instances variables to initial states based
	 * on values passed in as parameters.
	 * @param state - the state of the cell represented as an int
	 * @param color - the color of the visual representation of the cell
	 * @param neighborCells - a list of the cells neighboring this cell, which will determine the next 
	 * value of this cell based on the rules of the active simulation
	 * @param rowNumber - the index number of the row in which this cell lies
	 * @param columnNumber - the index number of the column in which this cell lies
	 */
	public Cell(int state, Color color, List<Cell>neighborCells, int rowNumber, int columnNumber) {
		this.state = state;
		this.color = color;
		this.neighborCells = neighborCells;
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;	
	}
	
	/**
	 * Getter method for the int state instance variable
	 * @return the int state of the cell
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Setter method for the int state instance variable
	 * @param state - the int state of the cell
	 */
	public void changeState(int state) {
		this.state = state;
	}
	
	/**
	 * Getter method for the color of the visual representation of the cell
	 * @return the color of the cell
	 */
	public Paint getColor() {
		return this.color;
	}
	
	/**
	 * Setter method for the color of the cell
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Getter method for the list of this cell's neighbor cells
	 * @return the list of cells in the neighborhood of this cell
	 */
	public List<Cell> getNeighborCells() {
		return neighborCells;
	}
	
	/**
	 * Setter method for the list of this cell's neighbor cells
	 * @param neighborCells - the list of cells in the neighborhood of this cell
	 */
	public void setNeighborCells(List<Cell> neighborCells) {
		this.neighborCells = neighborCells;
	}

	/**
	 * Getter method for the index of the row in which this cell lies
	 * @return
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * Getter method for the index of the column in which this cell lies
	 * @return
	 */
	public int getColumnNumber() {
		return columnNumber;
	}	
}
