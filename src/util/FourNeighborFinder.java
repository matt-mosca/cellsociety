package util;

import java.util.ArrayList;
import java.util.List;

import backend.Cell;

/**
 * FourNeighborFinder.java
 * @author Matthew Mosca
 * NeighborFinder subclass that finds the four neighbors to the north, south, east, and west of a cell.
 * @version 10.04.17
 */
public class FourNeighborFinder extends NeighborFinder {
	/**
	 * Parameterized constructor for this class.
	 * @param cells
	 * @param xPos
	 * @param yPos
	 * @param toroidal
	 */
	public FourNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) {
		super(cells, xPos, yPos, toroidal);
	}
	
	/**
	 * Finds the four neighbors in the cardinal directions from a cell, and returns a list of these 
	 * cells.
	 */
	public List<Cell> findNeighbors() {
		Cell[][] cellArray = getMyCellArray();
		int xPos = getMyXPosition();
		int yPos = getMyYPosition();
		int cellWidth = cellArray[0].length;
		int cellHeight = cellArray.length;
		List<Cell> neighbors = new ArrayList<Cell>();
		if (yPos - 1 >= 0)
			neighbors.add(cellArray[yPos - 1][xPos]);
		//Toroidal
		else if(yPos - 1 < 0 && getToroidal())
			neighbors.add(cellArray[cellHeight - 1][xPos]);
		if (xPos - 1 >= 0)
			neighbors.add(cellArray[yPos][xPos - 1]);
		//Toroidal
		else if(xPos - 1 < 0 && getToroidal())
			neighbors.add(cellArray[yPos][cellWidth - 1]);
		if (xPos + 1 <= cellWidth - 1)
			neighbors.add(cellArray[yPos][xPos + 1]);
		//Toroidal
		else if(xPos + 1 > cellWidth - 1 && getToroidal())
			neighbors.add(cellArray[yPos][0]);
		if (yPos + 1 <= cellHeight - 1)
			neighbors.add(cellArray[yPos + 1][xPos]);
		//Toroidal
		else if(yPos + 1 > cellHeight - 1 && getToroidal())
			neighbors.add(cellArray[0][xPos]);
		return neighbors;
	}
}
