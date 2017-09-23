package backend;

import java.util.ArrayList;

public class SimulationFire{
	private CellFire[][] array;
	private int numberOfCells;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	private double probCatch;
	private double initialEmptyPercentage;

	public SimulationFire(int cellNumberHorizontal, int  cellNumberVertical) {
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		probCatch = 0.5;
		initialEmptyPercentage = 0.25;
		array = new CellFire[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				array[rowNumber][columnNumber]=new CellFire(0, null, null, rowNumber, columnNumber);
			}
		}
	}
	
	private void initializeGridStates() {
		
	}
	
	private void findNumberEmpty() {
		
	}
	
	private void fillGridStates() {
		
	}
	
	private void findNeighbors() {
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellFire cell = array[rowNumber][columnNumber];
			    ArrayList<Cell> neighbors=new ArrayList<Cell>();
				if (rowNumber-1>=0) {
					neighbors.add(array[rowNumber-1][columnNumber]);
					if (columnNumber-1>=0) {
						neighbors.add(array[rowNumber-1][columnNumber-1]);
					}
					if (columnNumber+1<=cellNumberVertical-1) {
						neighbors.add(array[rowNumber-1][columnNumber+1]);
					}
					
				}
				if (columnNumber-1>=0) {
					neighbors.add(array[rowNumber][columnNumber-1]);
				}
				if (columnNumber+1<=cellNumberVertical-1) {
					neighbors.add(array[rowNumber][columnNumber+1]);
				}
				if (rowNumber+1<=cellNumberHorizontal-1) {
					neighbors.add(array[rowNumber+1][columnNumber]);
					if (columnNumber-1>=0) {
						neighbors.add(array[rowNumber+1][columnNumber-1]);
					}
					if (columnNumber+1<=cellNumberVertical-1) {
						neighbors.add(array[rowNumber+1][columnNumber+1]);
					}
					
				}
				cell.setNeighborCells(neighbors);
			}
		}

	}
	
	public void update() {
		
	}
	
	public double getProbCatch() {
		return probCatch;
	}
	
	public void setProbCatch(double prob) {
		probCatch = prob;
	}
	
	public CellFire[][] getArray() {
		return array;
	}

	public void setArray(CellFire[][] array) {
		this.array = array;
	}
}
