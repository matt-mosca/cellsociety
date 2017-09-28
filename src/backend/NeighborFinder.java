package backend;

import java.util.ArrayList;

public abstract class NeighborFinder {
	private Cell[][] myCellArray;
	private int myXPosition;
	private int myYPosition;
	private ArrayList<Cell> myNeighbors;
	
	public NeighborFinder(Cell[][] cells, int xPos, int yPos) {
		myCellArray = cells;
		myXPosition = xPos;
		myYPosition = yPos;
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
	
	protected ArrayList<Cell> getMyNeighbors() {
		return myNeighbors;
	}
	
	public abstract void findNeighbors();
}
