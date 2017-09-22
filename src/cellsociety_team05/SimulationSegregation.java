package cellsociety_team05;

import java.util.ArrayList;
import java.util.Random;

public class SimulationSegregation {
	private CellSegregation[][] array;
	private double satisfactionPercentage;
	private int numberOfCells;
	private double emptyPercentage;
	private double redToBlueRatio;
	private int cellNumberHorizontal;
	private int cellNumberVertical;

	// 0 is empty, 1 is red, 2 is blue

	public SimulationSegregation(
			int cellNumberHorizontal,
			int cellNumberVertical,
			double emptyPercentage,
			double satisfactionPercentage,
			double redToBlueRatio) {
		// set up instance variables, put 0s in every cell
		array = new CellSegregation[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				array[rowNumber][columnNumber]=new CellSegregation(0, null, null, columnNumber, columnNumber);
			}
		}
		this.emptyPercentage = emptyPercentage;
		this.satisfactionPercentage = satisfactionPercentage;
		this.redToBlueRatio = redToBlueRatio;
		this.numberOfCells = cellNumberHorizontal
				* cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		initializeScene();
		
		
		

	}

	public void initializeScene() {
		// according to percentage, do random function
		// call cell to change type
		int redNumber = findNumber(1);
		int blueNumber = findNumber(2);

		int[] redSlots = random(
				redNumber);
		int[] blueSlots = random(
				blueNumber);

		
		fillInitialRedAndBlue(redSlots,
				1);
		fillInitialRedAndBlue(blueSlots,
				2);
		findNeighbors();

	}

	private void findNeighbors() {
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell = array[rowNumber][columnNumber];
			    ArrayList<CellSegregation> neighbors=new ArrayList<>();
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
				
				cell.setNeighborCells(neighbors.toArray(new CellSegregation[neighbors.size()]));
				
					

			}
		}

	}

	private void fillInitialRedAndBlue(
			int[] slots, int state) {
		for (int i = 0; i < slots.length; i++) {
			int position = slots[i];
			int rowNumber = (int) (position
					/ cellNumberHorizontal);
			int columnNumber = position
					% cellNumberHorizontal;
			array[rowNumber][columnNumber]
					.changeState(state);

		}
	}

	private int[] random(int Number) {
		return new Random().ints(0, numberOfCells).distinct().limit(Number).toArray();
	}

	private int findNumber(int state) {
		int empty = (int) (numberOfCells* emptyPercentage);

		int filled = numberOfCells- empty;

		if (state == 0) {
			return empty;
		}
		if (state == 1) {
			return (int) (filled/ (redToBlueRatio+ 1)* redToBlueRatio);
		}
		if (state == 2) {
			return (int) (filled/ (redToBlueRatio+ 1));
		}
		return 0;
	}

	public void update() {
		// set up a loop, go through every cell
		// call whetherSatisfied
		//CellSegregation[] emptyCells=findAllEmpty()
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell = array[rowNumber][columnNumber];
				System.out.println(cell.getNeighborCells().length);
				if (cell.getState()==0) {continue;}
				else if (whetherSatisfied(cell)==false) {
					
				}
				

			}
		}

	}

	private boolean whetherSatisfied(CellSegregation cell) {
		CellSegregation[] neighbors=(CellSegregation[]) cell.getNeighborCells();
		int countFilled=0;
		int countSatisfied=0;
		for (int i=0;i<neighbors.length;i++) {
			if (neighbors[i].getState()!=0) {
				countFilled++;
				if (neighbors[i].getState()==cell.getState()) {
					countSatisfied++;
				}
			}
		}
		double satisfaction=countSatisfied/countFilled;
		if (satisfaction>=satisfactionPercentage) {
			return true;
		}
		return false;
	}

	public CellSegregation[][] getArray() {
		return array;
	}

	public void setArray(
			CellSegregation[][] array) {
		this.array = array;
	}

}
