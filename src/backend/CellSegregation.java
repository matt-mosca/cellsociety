package backend;

import java.util.List;

import javafx.scene.paint.Color;

public class CellSegregation extends Cell{
	
	public CellSegregation(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, color, neighborCells, rowNumber, columnNumber);
		// TODO Auto-generated constructor stub
	}
	
	//For testing
	/*
	public static void main(String[] args) {
//		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		Image image = new Image("the");
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		CellSegregation test = new CellSegregation(0, image, neighbors, 1, 1);
		test.changeState(2);
	}
	*/
}
