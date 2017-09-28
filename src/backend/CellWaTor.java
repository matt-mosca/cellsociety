package backend;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class CellWaTor extends Cell{
	private int starveDays;
	private int breedDays;
	

	public CellWaTor(int state, Color color, ArrayList<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, color, neighborCells, rowNumber, columnNumber);
		this.starveDays=0;
		this.setBreedDays(0);
	}
	
	
	public int getStarveDays() {
		return starveDays;
	}
	public void setStarveDays(
			int starveDays) {
		this.starveDays = starveDays;
	}
	public int getBreedDays() {
		return breedDays;
	}
	public void setBreedDays(
			int breedDays) {
		this.breedDays = breedDays;
	}


	

}

