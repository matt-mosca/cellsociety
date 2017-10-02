package backend;

import java.util.ArrayList;
import java.util.List;

public class EightNeighborFinder extends NeighborFinder {
	public EightNeighborFinder(Cell[][] cells, int xPos, int yPos) {
		super(cells, xPos, yPos);
	}
	
	public List<Cell> findNeighbors() {
		Cell[][] cellArray = getMyCellArray();
		int xPos = getMyXPosition();
		int yPos = getMyYPosition();
		int cellWidth = cellArray[0].length;
		int cellHeight = cellArray.length;
	    List<Cell> neighbors = new ArrayList<Cell>();
		if (yPos - 1 >= 0) {
			neighbors.add(cellArray[yPos - 1][xPos]);
			if (xPos - 1 >= 0)
				neighbors.add(cellArray[yPos - 1][xPos - 1]);
			if (xPos + 1 <= cellWidth - 1)
				neighbors.add(cellArray[yPos - 1][xPos + 1]);
		}
		if (xPos - 1 >= 0)
			neighbors.add(cellArray[yPos][xPos - 1]);
		if (xPos + 1 <= cellWidth - 1)
			neighbors.add(cellArray[yPos][xPos + 1]);
		if (yPos + 1 <= cellHeight - 1) {
			neighbors.add(cellArray[yPos + 1][xPos]);
			if (xPos - 1 >= 0)
				neighbors.add(cellArray[yPos + 1][xPos - 1]);
			if (xPos + 1 <= cellWidth - 1)
				neighbors.add(cellArray[yPos + 1][xPos + 1]);
		}
		return neighbors;
	}
}
