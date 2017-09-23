package backend;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CellSegregation extends Cell{
	


	public CellSegregation(int state, Image image, ArrayList<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, image, neighborCells, rowNumber, columnNumber);
		// TODO Auto-generated constructor stub

	}
	public int getState() {
		return super.getState();
	}
	public void changeState(int state) {
		super.changeState(state);
	}
	public Image getImage() {
		return super.getImage();
	}
	public void setImage(
			Image image) {
		super.setImage(image);
	}
	
	public ArrayList<Cell> getNeighborCells() {
		return super.getNeighborCells();
	}
	public void setNeighborCells(
			ArrayList<Cell> neighborCells) {
		super.setNeighborCells(neighborCells);
	}

	public int getRowNumber() {
		return super.getRowNumber();
	}

	public void setRowNumber(
			int rowNumber) {
		super.setRowNumber(rowNumber);
	}

	public int getColumnNumber() {
		return super.getColumnNumber();
	}

	public void setColumnNumber(
			int columnNumber) {
		super.setColumnNumber(columnNumber);
	}


	

}
