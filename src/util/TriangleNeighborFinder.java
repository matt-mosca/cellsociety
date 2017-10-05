package util;

import java.util.ArrayList;
import java.util.List;

import backend.Cell;

/**
 * TriangleNeighborFinder.java
 * @author Matthew Mosca
 * NeighborFinder subclass that finds the twelve neighbors surrounding a triangular cell. It most aptly 
 * applied when the cell of interest is triangular, though the functionality works for any cell.
 * 
 */
public class TriangleNeighborFinder extends NeighborFinder {
	/**
	 * Parameterized constructor for this class.
	 * @param cells
	 * @param xPos
	 * @param yPos
	 * @param toroidal
	 */
	public TriangleNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) {
		super(cells, xPos, yPos, toroidal);
	}
	
	/**
	 * Finds and returns the twelve neighbors of the cell of interest.
	 */
	public List<Cell> findNeighbors() {
		Cell[][] cellArray = getMyCellArray();
		int xPos = getMyXPosition();
		int yPos = getMyYPosition();
		int cellWidth = cellArray[0].length;
		int cellHeight = cellArray.length;
	    List<Cell> neighbors = new ArrayList<Cell>();
	    //triangle pointing up
	    if((yPos % 2 == 0 && xPos % 2 == 0) || (yPos % 2 == 1 && xPos % 2 == 1)) {
	    	if(yPos - 1 >= 0) {
	    		neighbors.add(cellArray[yPos - 1][xPos]);
	    		if(xPos - 1 >= 0)
	    			neighbors.add(cellArray[yPos - 1][xPos - 1]);
	    		if(xPos + 1 <= cellWidth - 1)
	    			neighbors.add(cellArray[yPos - 1][xPos + 1]);
	    	}
	    	if(xPos - 2 >= 0)
	    		neighbors.add(cellArray[yPos][xPos - 2]);
	    	if(xPos - 1 >= 0)
	    		neighbors.add(cellArray[yPos][xPos - 1]);
	    	if(xPos + 1 <= cellWidth - 1)
	    		neighbors.add(cellArray[yPos][xPos + 1]);
	    	if(xPos + 2 <= cellWidth - 1)
	    		neighbors.add(cellArray[yPos][xPos + 2]);
	    	if(yPos + 1 <= cellHeight - 1) {
	    		neighbors.add(cellArray[yPos + 1][xPos]);
	    		if(xPos - 2 >= 0)
	    			neighbors.add(cellArray[yPos + 1][xPos - 2]);
	    		if(xPos - 1 >= 0)
	    			neighbors.add(cellArray[yPos + 1][xPos - 1]);
	    		if(xPos + 1 <= cellWidth - 1)
		    		neighbors.add(cellArray[yPos + 1][xPos + 1]);
		    	if(xPos + 2 <= cellWidth - 1)
		    		neighbors.add(cellArray[yPos + 1][xPos + 2]);
	    	}
	    }
	    //triangle pointing down
	    else {
	    	if(yPos + 1 <= cellHeight - 1) {
	    		neighbors.add(cellArray[yPos + 1][xPos]);
	    		if(xPos - 1 >= 0)
	    			neighbors.add(cellArray[yPos + 1][xPos - 1]);
	    		if(xPos + 1 <= cellWidth - 1)
	    			neighbors.add(cellArray[yPos + 1][xPos + 1]);
	    	}
	    	if(xPos - 2 >= 0)
	    		neighbors.add(cellArray[yPos][xPos - 2]);
	    	if(xPos - 1 >= 0)
	    		neighbors.add(cellArray[yPos][xPos - 1]);
	    	if(xPos + 1 <= cellWidth - 1)
	    		neighbors.add(cellArray[yPos][xPos + 1]);
	    	if(xPos + 2 <= cellWidth - 1)
	    		neighbors.add(cellArray[yPos][xPos + 2]);
	    	if(yPos - 1 >= 0) {
	    		neighbors.add(cellArray[yPos - 1][xPos]);
	    		if(xPos - 2 >= 0)
	    			neighbors.add(cellArray[yPos - 1][xPos - 2]);
	    		if(xPos - 1 >= 0)
	    			neighbors.add(cellArray[yPos - 1][xPos - 1]);
	    		if(xPos + 1 <= cellWidth - 1)
		    		neighbors.add(cellArray[yPos - 1][xPos + 1]);
		    	if(xPos + 2 <= cellWidth - 1)
		    		neighbors.add(cellArray[yPos - 1][xPos + 2]);
	    	}
	    }
		return neighbors;
	}
}
