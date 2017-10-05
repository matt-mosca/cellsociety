package util;

import java.util.ArrayList;
import java.util.List;
import backend.Cell;

/**
 * NeighborFinder.java
 * @author Matthew Mosca
 * Super class for all NeighborFinder objects. General template for classes that finds the neighbors for a 
 * given cell in any simulation. The subclasses represent different possible neighborhoods for a cell.
 * @version 10.01.17
 */
public abstract class NeighborFinder {
	private Cell[][] myCellArray;
	private int myXPosition;
	private int myYPosition;
	private List<Cell> myNeighbors;
	private boolean toroidal;
	
	/**
	 * Parameterized constructor for a NeighborFinder object.
	 * @param cells - the array of cells
	 * @param xPos - the x-position of the cell in the grid
	 * @param yPos - the y-position of the cell in the grid
	 * @param tor - a boolean indicating whether or not the grid edge should be toroidal, such that if a 
	 * cell touches a wall of the grid, it has a neighbor or neighbors on the opposite wall
	 */
	public NeighborFinder(Cell[][] cells, int xPos, int yPos, boolean tor) {
		myCellArray = cells;
		myXPosition = xPos;
		myYPosition = yPos;
		myNeighbors = new ArrayList<Cell>();
		toroidal = tor;
	}
	
	/**
	 * Getter method for the 2D array of cells.
	 * @return myCellArray
	 */
	protected Cell[][] getMyCellArray() {
		return myCellArray;
	}
	
	/**
	 * Getter method for myXPosition.
	 * @return myXPosition
	 */
	protected int getMyXPosition() {
		return myXPosition;
	}
	
	/**
	 * Getter method for myYPosition.
	 * @return myYPosition
	 */
	protected int getMyYPosition() {
		return myYPosition;
	}
	
	/**
	 * Getter method for the boolean indicating the type of grid edge
	 * @return true if the grid edge is toroidal, false otherwise
	 */
	protected boolean getToroidal() {
		return toroidal;
	}
	
	/**
	 * Setter method for myXPosition.
	 * @param x
	 */
	public void setMyXPosition(int x) {
		myXPosition = x;
	}
	
	/**
	 * Setter method for myYPosition.
	 * @param y
	 */
	public void setMyYPosition(int y) {
		myYPosition = y;
	}
	
	/**
	 * Getter method for the list of neighbors.
	 * @return myNeighbors
	 */
	public List<Cell> getMyNeighbors() {
		return myNeighbors;
	}
	
	/**
	 * Method that will in the subclasses be used to find the neighbors of a cell at a given position.
	 * @return a list of neighbors
	 */
	public abstract List<Cell> findNeighbors();
}
