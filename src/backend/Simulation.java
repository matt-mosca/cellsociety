package backend;

//simulation super class



import java.util.ArrayList;
import java.util.Random;

public class Simulation{
	protected Cell[][] array;
	private int numberOfCells;
	private double emptyPercentage;
	private double redToBlueRatio;
	private int cellNumberHorizontal;
	private int cellNumberVertical;

	// 0 is empty, 1 is red, 2 is blue

	public Simulation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio) {
		// set up instance variables, put 0s in every cell

		this.emptyPercentage = emptyPercentage;
		this.redToBlueRatio = redToBlueRatio;
		this.numberOfCells = cellNumberHorizontal
				* cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		
		
		
		

	}

	public void initializeScene() {
		// according to percentage, do random function
		// call cell to change type
		int redNumber = findNumber(1);
		int blueNumber = findNumber(2);
        int[] randomSlots=random(
				redNumber+blueNumber,numberOfCells);
        //fill all to blue first, then change some to red
		fillInitialRedAndBlue(randomSlots,2);
		int[] redSlots = random(
				redNumber,randomSlots.length);
		for (int i=0;i<redSlots.length;i++) {
			redSlots[i]=randomSlots[redSlots[i]];
		}
	
		
		fillInitialRedAndBlue(redSlots,
				1);

		findNeighbors();

	}
    
	//neighbors counted as 8 surrounding cells
	public void findNeighbors() {
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				Cell cell = array[rowNumber][columnNumber];
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

	public void fillInitialRedAndBlue(
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

	public int[] random(int Number, int range) {
		return new Random().ints(0, range).distinct().limit(Number).toArray();
	}

	public int findNumber(int state) {
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
		
		
	
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				Cell cell = array[rowNumber][columnNumber];
				
				
				

			}
			
		}
		
		
	}

	

	

	public Cell[][] getArray() {
		return array;
	}

	public void setArray(
			Cell[][] array) {
		this.array = array;
	}

}

