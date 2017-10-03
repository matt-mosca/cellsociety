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
	
	public Cell(int state, Color color, List<Cell>neighborCells, int rowNumber, int columnNumber) {
		this.state = state;
		this.color = color;
		this.neighborCells = neighborCells;
		this.setRowNumber(rowNumber);
		this.setColumnNumber(columnNumber);	
	}
	
	public int getState() {
		return state;
	}
	
	public void changeState(int state) {
		this.state = state;
	}
	
	public Paint getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public List<Cell> getNeighborCells() {
		return neighborCells;
	}
	
	public void setNeighborCells(List<Cell> neighborCells) {
		this.neighborCells = neighborCells;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	
}
