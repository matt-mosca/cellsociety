package backend;

import frontend.StyleUI;
import javafx.scene.paint.Color;
import util.FourNeighborFinder;
import util.NeighborFinder;
import util.TriangleNeighborFinder;

/**
 * SimulationFire.java
 * @author Matthew Mosca
 * Simulation object subclass specific to the Fire simulation. Contains methods and variables needed 
 * for this simulation in particular.
 * @version 10.04.17
 */
public class SimulationFire extends Simulation {
	private double probCatch;
	private NeighborFinder neighbors;
	private StyleUI gridStyle = new StyleUI();
	
	/**
	 * Parameterized constructor for this class. All parameters, with the exception of probCatch, are 
	 * passed into the super class constructor.
	 * @param numRows - the number of rows in the grid
	 * @param numCols - the number of columns in the grid
	 * @param emptyPercentage - the percentage of cells in the grid that should be initially empty
	 * @param redToBlueRatio - the ratio of non-empty cells with two different states
	 * @param probCatch - the probability that a given tree cell will "catch fire," changing to the state
	 * representing fire
	 */
	public SimulationFire(int numRows, int  numCols, double emptyPercentage, double redToBlueRatio, double probCatch) {
		super(numRows, numCols, emptyPercentage, redToBlueRatio);
		initializeComponents(probCatch);
		initializeGridStates();
		assignNeighbors(neighbors);
	}
	
	/**
	 * Parameterized constructor that accepts as a parameter a 2D array of states, which allows the states
	 * of the grid to be set to predetermined values specified in the XML.
	 * @param cellNumberHorizontal -the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param specificLocation - a 2D array of cell states, which can be used to set the initial states 
	 * of the grids
	 * @param probCatch - the probability that a cell with a burning neighbor with catch fire
	 */
	public SimulationFire(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation,double probCatch) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		initializeComponents(probCatch);
        initializeScene2(neighbors);
		assignNeighbors(neighbors);
		updateColors();
	}
	
	/**
	 * Creates the cells in the grid and determines the type of neighborhood that the cells will have.
	 * @param probCatch
	 */
	private void initializeComponents(double probCatch) {
		this.probCatch = probCatch;
		setArray(new CellFire[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber] = new CellFire(CellFire.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		defineNeighbors();
	}
	
	/**
	 * Determines the default sets of neighbors used by the cells in this simulation.
	 */
	private void defineNeighbors() {
		if(gridStyle.gridShape().equals("Triangle"))
			neighbors = new TriangleNeighborFinder(getArray(), 0, 0, gridStyle.getGridEdge());
		else
			neighbors = new FourNeighborFinder(getArray(), 0, 0, gridStyle.getGridEdge());
	}
	
	/**
	 * Sets the cells in the grid to their initial states and colors with helper methods.
	 */
	private void initializeGridStates() {
		fillGridStates();
		setRandomFire();
		updateColors();
	}
	
	/**
	 * Calculates the number of cells in the grid that will be empty based on the size of the grid and the 
	 * percentage that should be empty
	 * @return the number of cells in the grid that will be empty
	 */
	private int findNumberEmpty() {
		int empty = (int) (getNumberOfCells() * getEmptyPercentage());
		return empty;
	}
	
	/**
	 * Sets the initial states of the cells in the grid randomly, taking into account the number of cells
	 * that should be empty.
	 */
	private void fillGridStates() {
		int rand = 0;
		int empty = findNumberEmpty();
		for(int i = getNumberOfCells(); i > 0; i--) {
			rand = (int) getRandomNum(i);
			Cell cellOfInterest = getArray()[(getNumberOfCells() - i) / getCellNumberVertical()][(getNumberOfCells() - i) % getCellNumberVertical()];
			if(empty > 0 && rand <= empty) {
					cellOfInterest.changeState(CellFire.EMPTY);
					empty--;
			}
			else
				cellOfInterest.changeState(CellFire.TREE);
		}
	}
	
	/**
	 * Sets a single cell in a random location in the grid to the fire state.
	 */
	private void setRandomFire() {
		int rand = (int) getRandomNum(getNumberOfCells());
		if(getCellNumberVertical() != 0)
			((CellFire) getArray()[rand / getCellNumberVertical()][rand % getCellNumberVertical()]).catchFire();
	}
	
	/**
	 * Changes the state of each cell based on the neighborhood of cells around it, then updates
	 * neighbors, colors, and the count of each state using helper methods.
	 */
	public void update() {
		int[][] temp = new int[getCellNumberHorizontal()][getCellNumberVertical()];
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				temp[i][j] = getArray()[i][j].getState();
				if(getArray()[i][j].getState() == CellFire.BURNING)
					temp[i][j] = CellFire.EMPTY;
				if(getArray()[i][j].getState() == CellFire.TREE && potentialForFire(getArray()[i][j]) && getRandomNum(1) <= probCatch)
							temp[i][j] = CellFire.BURNING;
			}
		}
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				getArray()[i][j].changeState(temp[i][j]);
			}
		}
		assignNeighbors(neighbors);
		updateColors();
		count(1,2, 0);
	}
	
	/**
	 * Determines if a given cell could potentially catch fire with its current neighbors, which 
	 * decides if probCatch should be used to determine it's probability of changing state.
	 * @param cell
	 * @return true if a cell could potentially catch fire with its current neighbors, false otherwise
	 */
	private boolean potentialForFire(Cell cell) {
		for(int i = 0; i < cell.getNeighborCells().size(); i++) {
			if(cell.getNeighborCells().get(i).getState() == CellFire.BURNING)
				return true;
		}
		return false;
	}
	
	/**
	 * Generates a random double between zero and the upper bound int parameter
	 * @param upperBound - the upper bound of the random number generated
	 * @return the random number
	 */
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}

	/**
	 * Determines and returns the color with which a given cell should be represented based on its state.
	 */
	protected Color chooseColor(int state) {
		Color color = null;
		if(state == CellFire.EMPTY)
			color = gridStyle.emptyColor();
		if(state == CellFire.TREE)
			color = Color.GREEN;
		if(state == CellFire.BURNING) {
			color = Color.RED;
		}
		return color;
	}
	
	/**
	 * Getter method for probCatch.
	 * @return propCatch - the probability that a given tree cell with an adjacent neighbor of state 
	 * fire will catch fire
	 */
	public double getProbCatch() {
		return probCatch;
	}
	
	/**
	 * Setter method for probCatch.
	 * @param prob
	 */
	public void setProbCatch(double prob) {
		probCatch = prob;
	}
}