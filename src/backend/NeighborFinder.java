package backend;

import java.util.List;

public abstract class NeighborFinder {
	private Cell[][] myCellArray;
	private int myXPosition;
	private int myYPosition;
	
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
	
	public void setMyXPosition(int x) {
		myXPosition = x;
	}
	
	public void setMyYPosition(int y) {
		myYPosition = y;
	}
	
	public abstract List<Cell> findNeighbors();
}
