package backend;

import java.util.ArrayList;
import java.util.List;

public class FourNeighborFinder extends NeighborFinder {
	public FourNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) {
		super(cells, xPos, yPos, toroidal);
	}
	
	public List<Cell> findNeighbors() {
		Cell[][] cellArray = getMyCellArray();
		int xPos = getMyXPosition();
		int yPos = getMyYPosition();
		int cellNumberVertical = cellArray.length;
		int cellNumberHorizontal = cellArray[0].length;
		List<Cell> neighbors = new ArrayList<Cell>();
		if (yPos - 1 >= 0)
			neighbors.add(cellArray[yPos - 1][xPos]);
		if (xPos-1>=0)
			neighbors.add(cellArray[yPos][xPos - 1]);
		if (xPos+1<=cellNumberHorizontal - 1)
			neighbors.add(cellArray[yPos][xPos + 1]);
		if (yPos+1<=cellNumberVertical - 1)
			neighbors.add(cellArray[yPos + 1][xPos]);
		return neighbors;
//		addNorth();
//		addSouth();
//		addEast();
//		addWest();
//		return getMyNeighbors();
	}
}
