package backend;

import frontend.StyleUI;
import javafx.scene.paint.Color;
import util.EightNeighborFinder;
import util.NeighborFinder;
import util.TriangleNeighborFinder;

/**
 * SimulationGameOfLife.java
 * @author Matthew Mosca
 * Simulation subclass specific to the Game of Life simulation.
 * @version 10.04.17
 */
public class SimulationGameOfLife extends Simulation {
	private static final int UPPERBOUNDARY = 3;
	private static final int LOWERBOUNDARY = 2;
	private NeighborFinder neighbors;
	private StyleUI style = new StyleUI();
	
	/**
	 * Parameterized constructor for this class. Parameters are passed into the superclass constructor.
	 * @param cellNumberHorizontal - the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param emptyPercentage - the percentage of cells in the grid that should be initially empty
	 * @param redToBlueRatio
	 */
	public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double redToBlueRatio) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		specificSetUp();
		initializeGridStates();
		assignNeighbors(neighbors);
	}

	/**
	 * Parameterized constructor that accepts as a parameter a 2D array of states, which allows the states
	 * of the grid to be set to predetermined values specified in the XML.
	 * @param cellNumberHorizontal - the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param specificLocation - a 2D array of cell states, which can be used to set the initial states 
	 * of the grids
	 */
	public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		specificSetUp();
	    super.initializeScene2(neighbors);
		assignNeighbors(neighbors);
		updateColors();
	}
	
	/**
	 * Creates the cells in the grid and determines the type of neighborhood that the cells will have.
	 */
	public void specificSetUp() {
		setArray(new CellGameOfLife[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber] = new CellGameOfLife(CellGameOfLife.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		if(style.gridShape().equals("Triangle"))
			neighbors = new TriangleNeighborFinder(getArray(), 0, 0, style.getGridEdge());
		else
			neighbors = new EightNeighborFinder(getArray(), 0, 0, style.getGridEdge());
	}
	
	/**
	 * Sets the initials states of the cells in the grid using helper methods.
	 */
	private void initializeGridStates() {
		fillGridStates();
		updateColors();
	}
	
	/**
	 * Finds the number of initially empty cells in the grid based on the number of cells and the 
	 * empty percentage
	 * @return the number of cells that should be initially empty
	 */
	private int findNumberEmpty() {
		int empty = (int) (getNumberOfCells() * getEmptyPercentage());
		return empty;
	}
	
	/**
	 * Sets the initial states of the cells int the grid
	 */
	private void fillGridStates() {
		int rand = 0;
		int empty = findNumberEmpty();
		for(int i = getNumberOfCells(); i > 0; i--) {
			rand = (int) getRandomNum(i);
			if(empty > 0 && rand <= empty) {
					getArray()[(getNumberOfCells() - i) / getCellNumberVertical()][(getNumberOfCells() - i) % getCellNumberVertical()].changeState(CellGameOfLife.EMPTY);
					empty--;
			}
			else {
				getArray()[(getNumberOfCells() - i) / getCellNumberVertical()][(getNumberOfCells() - i) % getCellNumberVertical()].changeState(CellGameOfLife.LIVE);
			}
		}
	}
	
	/**
	 * Generates a random double between zero and the upper bound specified in the parameter
	 * @param upperBound - the upper bound of the range in which the random number could fall
	 * @return the random number
	 */
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}

	/**
	 * Determines and returns the color with which a cell should be represented based on its state.
	 */
	@Override
	protected Color chooseColor(int state) {
		Color color = null;

		if(state == CellGameOfLife.EMPTY)
			color=style.emptyColor();
		if(state == CellGameOfLife.LIVE)
			color=Color.RED;
		return color;
	}

	/**
	 * Changes the state of each cell based on the neighborhood of cells around it.
	 */
	public void update() {
		int[][] temp = new int[getCellNumberHorizontal()][getCellNumberVertical()];
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				CellGameOfLife cell = (CellGameOfLife)getArray()[i][j];
				int liveCount = 0;
				for(int k = 0; k < cell.getNeighborCells().size(); k++) {
					if(cell.getNeighborCells().get(k).getState() == CellGameOfLife.LIVE)
						liveCount++;
				}
				temp[i][j] = getArray()[i][j].getState();
				//Any live cell with two or three live neighbors lives on to the next generation, so do nothing
				//Any live cell with fewer than two live neighbors dies
				if(cell.getState() == CellGameOfLife.LIVE && liveCount < LOWERBOUNDARY)
					temp[i][j] = CellGameOfLife.EMPTY;
				//Any live cell with more than three live neighbors dies
				if(cell.getState() == CellGameOfLife.LIVE && liveCount > UPPERBOUNDARY)
					temp[i][j] = CellGameOfLife.EMPTY;
				//Any dead cell with exactly three live neighbors becomes a live cell
				if(cell.getState() == CellGameOfLife.EMPTY && liveCount ==UPPERBOUNDARY)
					temp[i][j] = CellGameOfLife.LIVE;
			}
		}
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				getArray()[i][j].changeState(temp[i][j]);
			}
		}
		assignNeighbors(neighbors);
		updateColors();
		super.count(0,1,0);
	}
}
