package backend;

import java.util.List;

import javafx.scene.paint.Color;

/**
 * CellWaTor.java
 * @author Yiqin Zhou and Matthew Mosca
 * Cell object subclass specific to the Wa-Tor simulation. Contains constants and methods that play 
 * roles particular to this simulation.
 * @version 10.04.17
 */
public class CellWaTor extends Cell{
	private int starveDays;
	private int breedDays;
	
	/**
	 * Parameterized constructor for CellWaTor objects. Takes in only the parameters that feed 
	 * into the superclass constructor.
	 * @param state
	 * @param color
	 * @param neighborCells
	 * @param rowNumber
	 * @param columnNumber
	 */
	public CellWaTor(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) {
		super(state, color, neighborCells, rowNumber, columnNumber);
		this.starveDays = 0;
		this.setBreedDays(0);
	}
	
	/**
	 * Getter method for int starveDays
	 * @return starveDays, the number of steps of the program a shark can go without eating before it dies
	 */
	public int getStarveDays() {
		return starveDays;
	}
	
	/**
	 * Setter method for int starveDays
	 * @param starveDays
	 */
	public void setStarveDays(
			int starveDays) {
		this.starveDays = starveDays;
	}
	
	/**
	 * Getter method for int breedDays
	 * @return breedDays, the number of steps of the program before a shark reproduces
	 */
	public int getBreedDays() {
		return breedDays;
	}
	
	/**
	 * Setter method for in breedDays
	 * @param breedDays
	 */
	public void setBreedDays(
			int breedDays) {
		this.breedDays = breedDays;
	}
}

