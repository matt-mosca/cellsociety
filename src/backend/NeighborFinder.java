package backend;

import java.util.ArrayList;
import java.util.List;

public abstract class NeighborFinder {
	private Cell[][] myCellArray;
	private int myXPosition;
	private int myYPosition;
	private int arrayCellWidth;
	private int arrayCellHeight;
	private List<Cell> myNeighbors;
	
	public NeighborFinder(Cell[][] cells, int xPos, int yPos) {
		myCellArray = cells;
		myXPosition = xPos;
		myYPosition = yPos;
		arrayCellWidth = myCellArray[0].length;
		arrayCellHeight = myCellArray.length;
		myNeighbors = new ArrayList<Cell>();
	}
	
//	if (yPos - 1 >= 0) {
//		neighbors.add(cellArray[yPos - 1][xPos]);
//		if (xPos - 1 >= 0)
//			neighbors.add(cellArray[yPos - 1][xPos - 1]);
//		if (xPos + 1 <= cellWidth - 1)
//			neighbors.add(cellArray[yPos - 1][xPos + 1]);
//	}
//	if (xPos - 1 >= 0)
//		neighbors.add(cellArray[yPos][xPos - 1]);
//	if (xPos + 1 <= cellWidth - 1)
//		neighbors.add(cellArray[yPos][xPos + 1]);
//	if (yPos + 1 <= cellHeight - 1) {
//		neighbors.add(cellArray[yPos + 1][xPos]);
//		if (xPos - 1 >= 0)
//			neighbors.add(cellArray[yPos + 1][xPos - 1]);
//		if (xPos + 1 <= cellWidth - 1)
//			neighbors.add(cellArray[yPos + 1][xPos + 1]);
//	}
	protected void addNorthwest() {
		if(myYPosition - 1 >= 0 && myXPosition - 1 >= 0)
			myNeighbors.add(myCellArray[myYPosition - 1][myXPosition - 1]);
	}
	
	protected void addNorth() {
		if(myYPosition - 1 >= 0)
			myNeighbors.add(myCellArray[myYPosition - 1][myXPosition]);
	}
	
	protected void addNortheast() {
		if(myYPosition - 1 >= 0 && myXPosition + 1 <= arrayCellWidth - 1)
			myNeighbors.add(myCellArray[myYPosition - 1][myXPosition + 1]);
	}

	protected void addEast() {
		if(myXPosition + 1 <= arrayCellWidth - 1)
			myNeighbors.add(myCellArray[myYPosition][myXPosition + 1]);
	}

	protected void addSoutheast() {
		if(myYPosition + 1 <= arrayCellHeight - 1 && myXPosition + 1 <= arrayCellWidth - 1)
			myNeighbors.add(myCellArray[myYPosition + 1][myXPosition + 1]);
	}
	
	protected void addSouth() {
		if(myYPosition + 1 <= arrayCellHeight - 1)
			myNeighbors.add(myCellArray[myYPosition + 1][myXPosition]);
	}

	protected void addSouthwest() {
		if(myYPosition + 1 <= arrayCellHeight - 1 && myXPosition - 1 >= 0)
			myNeighbors.add(myCellArray[myYPosition + 1][myXPosition - 1]);
	}
	
	protected void addWest() {
		if(myXPosition - 1 >= 0)
			myNeighbors.add(myCellArray[myYPosition][myXPosition - 1]);
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
	
	public List<Cell> getMyNeighbors() {
		return myNeighbors;
	}
	
	public abstract List<Cell> findNeighbors();
}
