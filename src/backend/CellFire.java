package backend;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CellFire extends Cell{
	
	public static final int EMPTY = 0;
	public static final int TREE = 1;
	public static final int BURNING = 2;
	
	public CellFire(int state, Image image, ArrayList<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, image, neighborCells, rowNumber, columnNumber);
	}
	
	public void catchFire() {
		if(getState() == TREE)
			changeState(BURNING);
	}
	
	public void burnDownToEmpty() {
		if(getState() == BURNING)
			changeState(EMPTY);
	}

	//For testing
	public static void main(String[] args) {
//		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		Image image = new Image("the");
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		CellFire test = new CellFire(TREE, image, neighbors, 1, 1);
		test.changeState(BURNING);
	}
}
