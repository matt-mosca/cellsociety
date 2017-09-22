package cellsociety_team05;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


//Super class of cell
public class Cell {
	private int state;
	private Image image; 
	private ArrayList<Cell> neighborCells;
	private int rowNumber;
	private int columnNumber;
	
	public Cell(int state, Image image, ArrayList<Cell>neighborCells, int rowNumber, int columnNumber) {
		this.state=state;
		this.image=image;
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
	public Image getImage() {
		return image;
	}
	public void setImage(
			Image image) {
		this.image = image;
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
