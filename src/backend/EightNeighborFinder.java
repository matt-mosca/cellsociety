package backend;

import java.util.ArrayList;

public class EightNeighborFinder extends NeighborFinder {
	public EightNeighborFinder(Cell[][] cells, int xPos, int yPos) {
		super(cells, xPos, yPos);
	}
	
	public ArrayList<Cell> findNeighbors() {
		Cell[][] cellArray = getMyCellArray();
		int xPos = getMyXPosition();
		int yPos = getMyYPosition();
		int cellNumberVertical = cellArray.length;
		int cellNumberHorizontal = cellArray[0].length;
	    ArrayList<Cell> neighbors = new ArrayList<Cell>();
	    
		if (xPos - 1 >= 0) {
			neighbors.add(cellArray[xPos - 1][yPos]);
			if (yPos - 1 >= 0) {
				neighbors.add(cellArray[xPos - 1][yPos - 1]);
			}
			if (yPos + 1 <= cellNumberVertical - 1) {
				neighbors.add(cellArray[xPos - 1][yPos + 1]);
			}	
		}
		if (yPos - 1 >= 0) {
			neighbors.add(cellArray[xPos][yPos - 1]);
		}
		if (yPos + 1 <= cellNumberVertical - 1) {
			neighbors.add(cellArray[xPos][yPos + 1]);
		}
		if (xPos + 1 <= cellNumberHorizontal - 1) {
			neighbors.add(cellArray[xPos + 1][yPos]);
			if (yPos - 1 >= 0) {
				neighbors.add(cellArray[xPos + 1][yPos - 1]);
			}
			if (yPos + 1 <= cellNumberVertical - 1) {
				neighbors.add(cellArray[xPos + 1][yPos + 1]);
			}
		}
		return neighbors;
	}
}
