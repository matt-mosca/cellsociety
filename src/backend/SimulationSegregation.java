package backend;

import java.util.ArrayList;
import java.util.List;

import frontend.StyleUI;
import javafx.scene.paint.Color;


public class SimulationSegregation extends Simulation {
	private double satisfactionPercentage;
	private NeighborFinder neighbors;
	private StyleUI style=new StyleUI();

	// 0 is empty, 1 is red, 2 is blue

	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio,double SatisfactionPercentage) {
		// set up instance variables, put 0s in every cell
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		satisfactionPercentage = SatisfactionPercentage;
		specificSetUp(
				satisfactionPercentage);
		initializeScene(neighbors);
		updateColors();
	}
	
	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation,double SatisfactionPercentage) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		satisfactionPercentage = SatisfactionPercentage;
		specificSetUp(
				satisfactionPercentage);
		super.initializeScene2(neighbors);
		updateColors();
	}

	public void specificSetUp(double satisfactionPercentage) {
		setArray(new CellSegregation[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber]=new CellSegregation(0, null, null, rowNumber, columnNumber);
			}
		}
		neighbors = new EightNeighborFinder(getArray(), 0, 0, false);
	}

	@Override
	protected Color chooseColor(int state) {
		Color color = null;
		if(state == 0)
			color = style.emptyColor();
		if(state == 1)
			color = Color.RED;
		if(state == 2)
			color = Color.YELLOW;
		return color;
	}
	
	public void update() {
		// set up a loop, go through every cell
		// call whetherSatisfied
		List<Cell> emptyCells = findAllEmpty();
		List<Cell> dissatisfied = new ArrayList<Cell>();
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				CellSegregation cell = (CellSegregation) getArray()[rowNumber][columnNumber];
				if (cell.getState() == 0) {continue;}
				else if (!whetherSatisfied(cell)) {
					dissatisfied.add(cell);	
					
				}
			}
		}
		
		for (int needMove = 0;needMove<dissatisfied.size();needMove++) {
			if (emptyCells.size() > 0){
				int previousState = dissatisfied.get(needMove).getState();
				dissatisfied.get(needMove).changeState(0);
				
				int theEmptyReadyForFill = random(1,emptyCells.size())[0];
				emptyCells.get(theEmptyReadyForFill).changeState(previousState);
				
				emptyCells.remove(theEmptyReadyForFill);
			}
		}
		updateColors();
		super.count(1,2);
	}

	private List<Cell> findAllEmpty() {
		List<Cell> empty = new ArrayList<Cell>();
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				CellSegregation cell = (CellSegregation) getArray()[rowNumber][columnNumber];
				if (cell.getState() == 0) {
					empty.add(cell);
				}
			}
		}
		return empty;
	}

	private boolean whetherSatisfied(CellSegregation cell) {
		List<Cell> neighbors = cell.getNeighborCells();
		int countFilled = 0;
		int countSatisfied = 0;
		for (int i = 0;i<neighbors.size();i++) {
			if (neighbors.get(i).getState() != 0) {
				countFilled++; 
				if (neighbors.get(i).getState() == cell.getState()) {
					countSatisfied++;
				}
			}
		}
		if (countFilled == 0) {return true;}
		double satisfaction = (double)countSatisfied / (double)countFilled;
		if (satisfaction >= satisfactionPercentage) {
			return true;	
		}
		return false;
	}
	
	private static void testArrayPrinter(Cell[][] testArray) {
	
		
		
		for(int i = 0; i < testArray.length; i++) {
			for(int j = 0; j < testArray[0].length; j++) {
				System.out.print(testArray[i][j].getState() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
	SimulationSegregation test = new SimulationSegregation(5, 5, 0.2, 0.5, 0.5);
	testArrayPrinter(test.getArray());
//	System.out.println(test.getArray()[1][1].getNeighborCells());
//	System.out.println();
//	System.out.print(test.findNumberEmpty());
//	System.out.println();
	int iterations = 10;
	//Drives test simulation
	for(int i = 0; i < iterations; i++) {
		test.update();
		System.out.println("Iteration " + (i + 1));
		testArrayPrinter(test.getArray());
	}
}



	
	
}
