package backend;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


//Super class of cell
public class Cell {
	private int state;
	private Color color; 
	private ArrayList<Cell> neighborCells;
	private int rowNumber;
	private int columnNumber;
	
	public Cell(int state, Color color, ArrayList<Cell>neighborCells, int rowNumber, int columnNumber) {
		this.state=state;
		this.color=color;
		this.neighborCells=neighborCells;
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
	public void setColor(
			Color color) {
		this.color = color;
	}
	
	public ArrayList<Cell> getNeighborCells() {
		return neighborCells;
	}
	public void setNeighborCells(
			ArrayList<Cell> neighborCells) {
		this.neighborCells = neighborCells;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(
			int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(
			int columnNumber) {
		this.columnNumber = columnNumber;
	}
	

	
	

}
