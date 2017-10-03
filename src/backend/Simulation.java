package backend;

import java.util.Random;

import javafx.scene.paint.Color;
import util.NeighborFinder;

/**
 * Simulation.java
 * @author matthewmosca
 * Super class for simulation objects. Contains variables and methods that all the simulation classes 
 * require. It is helpful to avoid repeated code and group similar classes in an inheritance structure.
 * @version 10.03.17
 */
public abstract class Simulation{
	private Cell[][] array;
	private int numberOfCells;
	private double emptyPercentage;
	private double redToBlueRatio;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	private int[][]specificLocation;
	private int initialSetting;
	private int[] number = new int[2];
	
	// 0 is empty, 1 is red, 2 is blue

	public Simulation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double redToBlueRatio) {
		// set up instance variables, put 0s in every cell
		this.emptyPercentage = emptyPercentage;
		this.redToBlueRatio = redToBlueRatio;
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		this.setInitialSetting(1);
		
	}
	
	public Simulation(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) {
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		this.specificLocation=specificLocation;
		this.setInitialSetting(2);
	}
	
	protected void initializeScene2(NeighborFinder neighborAssigner) {
		
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				array[i][j].changeState(specificLocation[i][j]);
			}
		}
		assignNeighbors(neighborAssigner);
	}
	
	
	
	protected void initializeScene(NeighborFinder neighborAssigner) {
		// according to percentage, do random function
		// call cell to change type
		int redNumber = findNumber(1);
		int blueNumber = findNumber(2);
        int[] randomSlots=random(
				redNumber+blueNumber,numberOfCells);
        //fill all to blue first, then change some to red
		fillInitialRedAndBlue(randomSlots,2);
		int[] redSlots = random(
				redNumber,randomSlots.length);
		for (int i=0;i<redSlots.length;i++) {
			redSlots[i]=randomSlots[redSlots[i]];
		}
		fillInitialRedAndBlue(redSlots, 1);
		assignNeighbors(neighborAssigner);
//		findNeighbors();
	}
	
	protected int findNumber(int state) {
		int empty = (int) (numberOfCells* emptyPercentage);
		int filled = numberOfCells- empty;
		if (state == 0) {
			return empty;
		}
		if (state == 1) {
			return (int) (filled/ (redToBlueRatio+ 1)* redToBlueRatio);
		}
		if (state == 2) {
			return (int) (filled/ (redToBlueRatio+ 1));
		}
		return 0;
	}
	
	protected void fillInitialRedAndBlue(int[] slots, int state) {
		for (int i = 0; i < slots.length; i++) {
			int position = slots[i];
			int rowNumber = (int) (position
					/ cellNumberVertical);
			int columnNumber = position
					% cellNumberVertical;
			array[rowNumber][columnNumber]
					.changeState(state);
			array[rowNumber][columnNumber].setColor(chooseColor(state));
		}
	}
	
	public abstract void update();

	//neighbors counted as 8 surrounding cells
	protected void assignNeighbors(NeighborFinder neighborAssigner) {
			neighborAssigner.setMyXPosition(0);
			neighborAssigner.setMyYPosition(0);
			for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
				for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
					Cell cell = array[rowNumber][columnNumber];
					neighborAssigner.setMyXPosition(columnNumber);
					neighborAssigner.setMyYPosition(rowNumber);
					cell.setNeighborCells(neighborAssigner.findNeighbors());
				}
			}
		}

	protected Color chooseColor(int state) {
		return null;
	}

	protected int[] random(int Number, int range) {
		return new Random().ints(0, range).distinct().limit(Number).toArray();
	}
	
	protected void updateColors() {
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				array[i][j].setColor(chooseColor(array[i][j].getState()));
			}
		}
	}

	public Cell[][] getArray() {
		return array;
	}
	
	protected void setArray(Cell[][] newArray) {
		array = newArray;
	}
	
	protected int getNumberOfCells() {
		return numberOfCells;
	}
	
	public double getEmptyPercentage() {
		return emptyPercentage;
	}
	
	public double getRedToBlueRatio() {
		return redToBlueRatio;
	}
	
	public int getCellNumberHorizontal() {
		return cellNumberHorizontal;
	}
	
	public int getCellNumberVertical() {
		return cellNumberVertical;
	}

	public int getInitialSetting() {
		return initialSetting;
	}

	public void setInitialSetting(
			int initialSetting) {
		this.initialSetting = initialSetting;
	}
	
	public int[]getCellProportion(){
		return number;
	}
	
	public void count(int first, int second) {
		number[0]=0;
		number[1]=0;
		for(int i=0; i<getArray().length; i++) {
			for(int j=0; j<getArray()[i].length; j++) {
				if (getArray()[i][j].getState()==first) {
					number[0]+=1;
				}
				else if(getArray()[i][j].getState()==second) {
					number[1]+=1;
				}
			}
		}
	}
}

