package backend;
import java.util.ArrayList;
import java.util.List;

import frontend.StyleUI;
import javafx.scene.paint.Color;
import util.EightNeighborFinder;
import util.NeighborFinder;
import util.TriangleNeighborFinder;
/**
 * SimulationSegregation.java
 * @author Yiqin Zhou
 * Simulation subclass specific to the Segregation simulation. In this simulation, red and blue will be relocated   
 * to empty cell if unsatisfied
 */
public class SimulationSegregation extends Simulation {
	private double satisfactionPercentage;
	private NeighborFinder neighbors;
	private StyleUI style = new StyleUI();

	// 0 is empty, 1 is red, 2 is blue
	/**
	 * Parameterized constructor for this class. Parameters are passed into the superclass constructor.
	 * @param cellNumberHorizontal - the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param emptyPercentage - the percentage of cells in the grid that should be initially empty
	 * @param redToBlueRatio
	 * @param satisfaction ratio - specific to this simlution
	 */

	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio,double SatisfactionPercentage) {
		// set up instance variables, put 0s in every cell
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		satisfactionPercentage = SatisfactionPercentage;
		specificSetUp(
				satisfactionPercentage);
		super.initializeScene(neighbors);
		updateColors();
	}
	
	/**
	 * Parameterized constructor that accepts as a parameter a 2D array of states, which allows the states
	 * of the grid to be set to predetermined values specified in the XML.
	 * @param cellNumberHorizontal - the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param specificLocation - a 2D array of cell states, which can be used to set the initial states 
	 * of the grids
	 * @param satisfactionPercentage - unique parameter
	 */
	
	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation,double SatisfactionPercentage) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		satisfactionPercentage = SatisfactionPercentage;
		specificSetUp(
				satisfactionPercentage);
		super.initializeScene2(neighbors);
		updateColors();
	}
	
	/**
	 * Set up an empty cell array
	 * @param satisfactionPercentage
	 */

	public void specificSetUp(double satisfactionPercentage) {
		setArray(new CellSegregation[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber]=new CellSegregation(0, null, null, rowNumber, columnNumber);
			}
		}
		if(style.gridShape().equals("Triangle"))
			neighbors = new TriangleNeighborFinder(getArray(), 0, 0, style.getGridEdge());
		else
			neighbors = new EightNeighborFinder(getArray(), 0, 0, style.getGridEdge());
	}
	
	/**
	 * choose color for different states
	 */

	@Override
	protected Color chooseColor(int state) {
		
		Color color = null;
		if(state == 0)
			color = style.emptyColor();
		if(state == 1)
			color = Color.RED;
		if(state == 2) {
			
			color = Color.GREEN;
		}
		return color;
	}
	
	/**
	 * update to new 2D array according to specific logic (whether cell has a higher satisfaction rate than expected)
	 */
	
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
		super.count(1,2,0);
	}
	/**
	 * find all empty cells so that unsatisfied cells can move to those positions
	 * @return
	 */

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
	/**
	 * check whether a particular cell is satisfied or not by using its neighbors and doing comparision to the satisfactionpercentage
	 * @param cell
	 * @return
	 */

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
	
	public double getSatisfactionPercentage() {
		return satisfactionPercentage;
	}
}
