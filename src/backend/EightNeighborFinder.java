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
		if (yPos - 1 >= 0) {
			neighbors.add(cellArray[yPos - 1][xPos]);
			if (xPos - 1 >= 0)
				neighbors.add(cellArray[yPos - 1][xPos - 1]);
			if (xPos + 1 <= cellNumberVertical - 1)
				neighbors.add(cellArray[yPos - 1][xPos + 1]);
		}
		if (xPos - 1 >= 0)
			neighbors.add(cellArray[yPos][xPos - 1]);
		if (xPos + 1 <= cellNumberVertical - 1)
			neighbors.add(cellArray[yPos][xPos + 1]);
		if (yPos + 1 <= cellNumberHorizontal - 1) {
			neighbors.add(cellArray[yPos + 1][xPos]);
			if (xPos - 1 >= 0)
				neighbors.add(cellArray[yPos + 1][xPos - 1]);
			if (xPos + 1 <= cellNumberVertical - 1)
				neighbors.add(cellArray[yPos + 1][xPos + 1]);
		}
		return neighbors;
	}
	
	public static void main(String[] args) {
		
	}
}
