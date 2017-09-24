package backend;

import java.util.ArrayList;
import java.util.Random;

public class SimulationSegregation extends Simulation {
//	private CellSegregation[][] array;
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
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		super.array = new CellSegregation[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				super.array[rowNumber][columnNumber]=new CellSegregation(0, null, null, rowNumber, columnNumber);
			}
		}
	
		this.satisfactionPercentage = satisfactionPercentage;
		super.initializeScene();
		
		
		
	    /*
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				System.out.println(super.array[rowNumber][columnNumber].getState());
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
    
	@Override
	public void update() {
		// set up a loop, go through every cell
		// call whetherSatisfied
		ArrayList<Cell> emptyCells=findAllEmpty();
		ArrayList<Cell> dissatisfied=new ArrayList<Cell>();
	
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell = (CellSegregation) super.array[rowNumber][columnNumber];
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
				CellSegregation cell=(CellSegregation) super.array[rowNumber][columnNumber];
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
		return (CellSegregation[][]) super.array;
	}

	public void setArray(
			CellSegregation[][] array) {
		super.array = array;
	}

}
