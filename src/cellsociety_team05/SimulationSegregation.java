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

	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double satisfactionPercentage,
			double redToBlueRatio) {
		// set up instance variables, put 0s in every cell
		array = new CellSegregation[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				array[rowNumber][columnNumber]=new CellSegregation(0, null, null, rowNumber, columnNumber);
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
		
	    /*
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				System.out.println(array[rowNumber][columnNumber].getState());
			}
		}
		System.out.println("separate here");
		
		
		
		
		update();
		
	   
		
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				System.out.println(array[rowNumber][columnNumber].getState());
			}
		}
		
		*/
		
	
		
		
		
		

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

	private void findNeighbors() {
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell = array[rowNumber][columnNumber];
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

	private int[] random(int Number, int range) {
		return new Random().ints(0, range).distinct().limit(Number).toArray();
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
		ArrayList<Cell> emptyCells=findAllEmpty();
		ArrayList<Cell> dissatisfied=new ArrayList<Cell>();
	
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell = array[rowNumber][columnNumber];
				if (cell.getState()==0) {continue;}
				else if (!whetherSatisfied(cell)) {
					dissatisfied.add(cell);
					
				}
				

			}
			
		}
		
		for (int needMove=0;needMove<dissatisfied.size();needMove++) {
			if (emptyCells.size()>0){
				int previousState=dissatisfied.get(needMove).getState();
				dissatisfied.get(needMove).changeState(0);
				int theEmptyReadyForFill=random(1,emptyCells.size())[0];
				emptyCells.get(theEmptyReadyForFill).changeState(previousState);
				emptyCells.remove(theEmptyReadyForFill);}

		}

	}

	private ArrayList<Cell> findAllEmpty() {
		ArrayList<Cell> empty= new ArrayList<Cell>();
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell=array[rowNumber][columnNumber];
				if (cell.getState()==0) {
					empty.add(cell);
				}
				
			}
		}
		return empty;
		
		
	}

	private boolean whetherSatisfied(CellSegregation cell) {
		ArrayList<Cell> neighbors=cell.getNeighborCells();
		int countFilled=0;
		int countSatisfied=0;
		for (int i=0;i<neighbors.size();i++) {
			if (neighbors.get(i).getState()!=0) {
				countFilled++;
				if (neighbors.get(i).getState()==cell.getState()) {
					countSatisfied++;
				}
			}
		}
		if (countFilled==0) {return true;}
		double satisfaction=(double)countSatisfied/(double)countFilled;
		
	
		
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
