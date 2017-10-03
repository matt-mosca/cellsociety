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
	
	public NeighborFinder(Cell[][] cells, int xPos, int yPos, boolean tor) {
		myCellArray = cells;
		myXPosition = xPos;
		myYPosition = yPos;
		myNeighbors = new ArrayList<Cell>();
		toroidal = tor;
	}
	
	protected Cell[][] getMyCellArray() {
		return myCellArray;
	}
	
	protected int getMyXPosition() {
		return myXPosition;
	}
	
	protected int getMyYPosition() {
		return myYPosition;
	}
	
	protected boolean getToroidal() {
		return toroidal;
	}
	
	public void setMyXPosition(int x) {
		myXPosition = x;
	}
	
	public void setMyYPosition(int y) {
		myYPosition = y;
	}
	
	public List<Cell> getMyNeighbors() {
		return myNeighbors;
	}
	
	public abstract List<Cell> findNeighbors();
}
