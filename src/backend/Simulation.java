package backend;

import java.util.Random;

import javafx.scene.paint.Color;
import util.NeighborFinder;

/**
 * Simulation.java
 * @author Yiqin Zhou and Matthew Mosca
 * Super class for simulation objects. Contains variables and methods that all the simulation classes 
 * require. It is helpful to avoid repeated code and group similar classes in an inheritance structure.
 * @version 10.04.17
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
	private int[] stateFrequencies = new int[3];
	

	/**
	 * Parameterized constructor for Simulation objects. Sets up instance variables and puts 0s in 
	 * every cell.
	 * @param cellNumberHorizontal - the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param emptyPercentage - the percentage of cells that are initialized to an empty state
	 * @param redToBlueRatio - the ratio of two initial types of cells
	 */
	public Simulation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double redToBlueRatio) {
		this.emptyPercentage = emptyPercentage;
		this.redToBlueRatio = redToBlueRatio;
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		this.setInitialSetting(1);
		
	}
	
	/**
	 * Parameterized constructor that accepts a 2D array of initial states, which allows each cell in the
	 * grid to be set to a predetermined state using XML file values.
	 * @param cellNumberHorizontal
	 * @param cellNumberVertical
	 * @param specificLocation - a 2D array of states representing a predetermined setup of the grid.
	 */
	public Simulation(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) {
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		this.specificLocation = specificLocation;
		this.setInitialSetting(2);
	}
	
	/**
	 * Fills the initial states of the grid and sets the neighbors of each cell with a method call
	 * @param neighborAssigner - a NeighborFinder object that will find the neighbors of the cells in the
	 * grid
	 */
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
	}
	
	/**
	 * Initializes the states of the cells in the grid according to a predetermined setup in the XML file
	 * for the given simulation
	 * @param neighborAssigner
	 */
	protected void initializeScene2(NeighborFinder neighborAssigner) {
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				array[i][j].changeState(specificLocation[i][j]);
			}
		}
		assignNeighbors(neighborAssigner);
	}
	
	/**
	 * Finds the number of cells of a given state in the grid
	 * @param state - a state of a cell
	 * @return the number of cells of the state specified in the parameter
	 */
	protected int findNumber(int state) {
		int empty = (int) (numberOfCells * emptyPercentage);
		int filled = numberOfCells- empty;
		if (state == 0) {
			return empty;
		}
		if (state == 1) {
			return (int) (filled/ (redToBlueRatio + 1)* redToBlueRatio);
		}
		if (state == 2) {
			return (int) (filled/ (redToBlueRatio + 1));
		}
		return 0;
	}
	
	/**
	 * Sets the initial states of non-empty cells
	 * @param slots
	 * @param state
	 */
	protected void fillInitialRedAndBlue(int[] slots, int state) {
		for (int i = 0; i < slots.length; i++) {
			int position = slots[i];
			int rowNumber = (int) (position / cellNumberVertical);
			int columnNumber = position % cellNumberVertical;
			array[rowNumber][columnNumber].changeState(state);
			array[rowNumber][columnNumber].setColor(chooseColor(state));
		}
	}
	
	/**
	 * Determines how each cell in the grid changes in each step of the simulation. In each Simulation 
	 * subclass, this method essentially contains the "rules" of the simulation, determining how each 
	 * cell changes based on the cells currently in its neighborhood. Every Simulation subclass will have
	 * this method because every simulation has rules, which will be used to determine the new state of
	 * each cell.
	 */
	public abstract void update();

	/**
	 * Iterates through the grid and finds the neighbors of each cell with a method call, and assigns the
	 * determined neighbors to the cell.
	 * @param neighborAssigner
	 */
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

	/**
	 * In Simulation subclasses, this method will determine the appropriate color for a cell based on its
	 * state.
	 * @param state
	 * @return the determined color
	 */
	protected Color chooseColor(int state) {
		return null;
	}

	/**
	 * Provides an array of random int values
	 * @param number
	 * @param range
	 * @return the array of random values
	 */
	protected int[] random(int number, int range) {
		return new Random().ints(0, range).distinct().limit(number).toArray();
	}
	
	/**
	 * Updates the colors of the cells in the grid based on the current states of the cells.
	 */
	protected void updateColors() {
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				array[i][j].setColor(chooseColor(array[i][j].getState()));
			}
		}
	}

	/**
	 * Getter method for the 2D array representing the grid of cells
	 * @return the grid of cells, represented as a 2D array
	 */
	public Cell[][] getArray() {
		return array;
	}
	
	/**
	 * Setter method for the array representing the grid
	 * @param newArray
	 */
	protected void setArray(Cell[][] newArray) {
		array = newArray;
	}
	
	/**
	 * Getter method for the number of cells in the grid
	 * @return the number of cells in the grid
	 */
	protected int getNumberOfCells() {
		return numberOfCells;
	}
	
	/**
	 * Getter method for the empty percentage
	 * @return the percentage of cells in the grid that are initially empty
	 */
	public double getEmptyPercentage() {
		return emptyPercentage;
	}
	
	/**
	 * Getter method for double redToBlueRatio
	 * @return the ratio of the number of cells with two different states
	 */
	public double getRedToBlueRatio() {
		return redToBlueRatio;
	}
	
	/**
	 * Getter method for the number of rows in the grid
	 * @return the number of rows in the grid
	 */
	public int getCellNumberHorizontal() {
		return cellNumberHorizontal;
	}
	
	/**
	 * Getter method for the number of columns in the grid
	 * @return the number of columns in the grid
	 */
	public int getCellNumberVertical() {
		return cellNumberVertical;
	}

	/**
	 * Getter method for the initial setting of the grid
	 * @return the initial setting
	 */
	public int getInitialSetting() {
		return initialSetting;
	}

	/**
	 * Setter method for the initial setting of the grid
	 * @param initialSetting
	 */
	public void setInitialSetting(
			int initialSetting) {
		this.initialSetting = initialSetting;
	}
	
	/**
	 * Getter method for the proportion of cells of each type
	 * @return an array containing the proportion of cells in the grid that are of each type
	 */
	public int[]getCellProportion(){
		return stateFrequencies;
	}
	
	/**
	 * Counts the number of cells of each state in the grid 
	 * @param first - a state
	 * @param second - another state
	 * @param third - a third state
	 */
	public void count(int first, int second, int third) {
		stateFrequencies[0] = 0;
		stateFrequencies[1] = 0;
		stateFrequencies[2] = 0;
		for(int i = 0; i < getArray().length; i++) {
			for(int j = 0; j < getArray()[i].length; j++) {
				if (getArray()[i][j].getState() == first) {
					stateFrequencies[0] += 1;
				}
				else if(getArray()[i][j].getState() == second) {
					stateFrequencies[1] += 1;
				}
				if(third == 3 && getArray()[i][j].getState() == third) {
					stateFrequencies[2] += 1;
				}
			}
		}
	}
}

