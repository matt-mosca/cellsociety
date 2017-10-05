package backend;

import frontend.StyleUI;
import javafx.scene.paint.Color;
import util.EightNeighborFinder;
import util.NeighborFinder;
import util.TriangleNeighborFinder;

/**
 * SimulationRPS.java
 * @author matthewmosca
 * Simulation subclass specific to the Rock, Paper, Scissors simulation. In this simulation, red beats 
 * blue, blue beats green, and green beats red.
 */
public class SimulationRPS extends Simulation {
	private NeighborFinder neighbors;
	private StyleUI style = new StyleUI();
	
	/**
	 * Parameterized constructor for this class. Parameters are passed into the superclass constructor.
	 * @param cellNumberHorizontal - the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param emptyPercentage - the percentage of cells in the grid that should be initially empty
	 * @param redToBlueRatio
	 */
	public SimulationRPS(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		specificSetUp();
		assignNeighbors(neighbors);
		prepareGrid();
		setRandomRGBCells();
	}
	
	/**
	 * Parameterized constructor that accepts as a parameter a 2D array of states, which allows the states
	 * of the grid to be set to predetermined values specified in the XML.
	 * @param cellNumberHorizontal - the number of rows in the grid
	 * @param cellNumberVertical - the number of columns in the grid
	 * @param specificLocation - a 2D array of cell states, which can be used to set the initial states 
	 * of the grids
	 */
	public SimulationRPS(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		specificSetUp();
        super.initializeScene2(neighbors);
		assignNeighbors(neighbors);
		updateColors();
		setRandomRGBCells();
	}
	
	/**
	 * Creates the cells in the grid and determines the type of neighborhood that the cells will have.
	 */
	public void specificSetUp() {
		setArray(new CellRPS[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber] = new CellRPS(CellRPS.EMPTY, null, null, rowNumber, columnNumber, 0);
			}
		}
		if(style.gridShape().equals("Triangle"))
			neighbors = new TriangleNeighborFinder(getArray(), 0, 0, style.getGridEdge());
		else
			neighbors = new EightNeighborFinder(getArray(), 0, 0, style.getGridEdge());
	}
	
	/**
	 * Sets the state of every cell to be initially empty.
	 */
	private void prepareGrid() {
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber].changeState(CellRPS.EMPTY);
				getArray()[rowNumber][columnNumber].setColor(Color.WHITE);
				((CellRPS)getArray()[rowNumber][columnNumber]).setGradientLevel(0);
			}
		}
	}
	
	/**
	 * Changes the state of each cell based on the neighborhood of cells around it. In this simulation,
	 * empty cells can become colored cells (growing), and colored cells can "eat" or be "eaten" when they
	 * come into contact with other cells.
	 */
	public void update() {
		int[][] tempStates = new int[getCellNumberHorizontal()][getCellNumberVertical()];
		int[][] tempGradients = new int[getCellNumberHorizontal()][getCellNumberVertical()];
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				CellRPS cellOfFocus = (CellRPS)getArray()[i][j];
				tempStates[i][j] = cellOfFocus.getState();
				tempGradients[i][j] = cellOfFocus.getGradientLevel();
				CellRPS randomNeighbor = (CellRPS) cellOfFocus.getNeighborCells().get((int) getRandomNum(cellOfFocus.getNeighborCells().size()));
				//Growing
				if(cellOfFocus.getState() == CellRPS.EMPTY && randomNeighbor.getState() != CellRPS.EMPTY && 
						randomNeighbor.getGradientLevel() < 9 && tempGradients[i][j] < 9) {
					tempStates[i][j] = randomNeighbor.getState();
					tempGradients[i][j] = randomNeighbor.getGradientLevel() + 1;
				}
				//Eating
				if(cellOfFocus.getState() == CellRPS.RED) {
					//Red eats blue
					if(randomNeighbor.getState() == CellRPS.BLUE) {
						if(randomNeighbor.getGradientLevel() < 9 && tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] < 9)
							tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()]++;
						else
							tempStates[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] = CellRPS.RED;
						if(cellOfFocus.getGradientLevel() > 0 && tempGradients[i][j] > 0)
							tempGradients[i][j]--;
					}
					//Green eats red
					if(randomNeighbor.getState() == CellRPS.GREEN) {
						if(cellOfFocus.getGradientLevel() < 9 && tempGradients[i][j] < 9)
							tempGradients[i][j]++;
						else
							tempStates[i][j] = CellRPS.GREEN;
						if(randomNeighbor.getGradientLevel() > 0 && tempGradients[i][j] > 0)
							tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()]--;
					}
				}
				
				if(cellOfFocus.getState() == CellRPS.GREEN) {
					//Green eats red
					if(randomNeighbor.getState() == CellRPS.RED) {
						if(randomNeighbor.getGradientLevel() < 9 && tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] < 9)
							tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()]++;
						else
							tempStates[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] = CellRPS.GREEN;
						if(cellOfFocus.getGradientLevel() > 0 && tempGradients[i][j] > 0)
							tempGradients[i][j]--;
					}
					//Blue eats green
					if(randomNeighbor.getState() == CellRPS.BLUE) {
						if(cellOfFocus.getGradientLevel() < 9 && tempGradients[i][j] < 9)
							tempGradients[i][j]++;
						else
							tempStates[i][j] = CellRPS.BLUE;
						if(randomNeighbor.getGradientLevel() > 0 && tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] > 0)
							tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()]--;
					}
				}
				
				if(cellOfFocus.getState() == CellRPS.BLUE) {
					//Blue eats green
					if(randomNeighbor.getState() == CellRPS.GREEN) {
						if(randomNeighbor.getGradientLevel() < 9 && tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] < 9)
							tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()]++;
						else
							tempStates[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] = CellRPS.BLUE;
						if(cellOfFocus.getGradientLevel() > 0 && tempGradients[i][j] > 0)
							tempGradients[i][j]--;
					}
					//Red eats blue
					if(randomNeighbor.getState() == CellRPS.RED) {
						if(cellOfFocus.getGradientLevel() < 9 && tempGradients[i][j] < 9)
							tempGradients[i][j]++;
						else
							tempStates[i][j] = CellRPS.RED;
						if(randomNeighbor.getGradientLevel() > 0 && tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()] > 0)
							tempGradients[randomNeighbor.getRowNumber()][randomNeighbor.getColumnNumber()]--;
					}
				}
			}
		}
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				getArray()[i][j].changeState(tempStates[i][j]);
				((CellRPS)getArray()[i][j]).setGradientLevel(tempGradients[i][j]);
			}
		}
		assignNeighbors(neighbors);
		updateColors();
		count(1,2,3);
	}
	
	/**
	 * Determines and returns the color with which a cell should be represented based on its state and
	 * color brightness level.
	 * @param state
	 * @param gradientLevel - a number from 0 to 9 representing the brightness of the color, 0 being the 
	 * darkest
	 * @return the color that should be used to represent the cell 
	 */
	private Color chooseColor(int state, int gradientLevel) {
		Color color = null;
		if(state == CellRPS.EMPTY)
			color = Color.WHITE;
		if(state == CellRPS.RED)
			color = Color.rgb(calcIntensityValue(gradientLevel), 0, 0);
		if(state == CellRPS.GREEN)
			color = Color.rgb(0, calcIntensityValue(gradientLevel), 0);
		if(state == CellRPS.BLUE)
			color = Color.rgb(0, 0, calcIntensityValue(gradientLevel));
		return color;
	}
	
	/**
	 * Updates the colors of the cells in the grid. This method overrides a method in the superclass 
	 * because it must take into account the brightness gradient of the color.
	 */
	@Override
	protected void updateColors() {
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				getArray()[i][j].setColor(chooseColor(getArray()[i][j].getState(), ((CellRPS)getArray()[i][j]).getGradientLevel()));
			}
		}
	}
	
	/**
	 * Calculates the RGB intensity value from a given gradient level
	 * @param gradientLevel
	 * @return the RGB intensity
	 */
	private int calcIntensityValue(int gradientLevel) {
		return 125 + gradientLevel * 13;
	}
	
	/**
	 * Generates a random double from 0 to the upper bound specified as a parameter
	 * @param upperBound
	 * @return the random double
	 */
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}
	
	/**
	 * Sets three random cells to be initially colored, one of each color.
	 */
	private void setRandomRGBCells() {
		int randOne = (int) getRandomNum(getNumberOfCells());
		int randTwo = (int) getRandomNum(getNumberOfCells());
		int randThree = (int) getRandomNum(getNumberOfCells());
		if(getCellNumberVertical() != 0) {
			getArray()[randOne / getCellNumberVertical()][randOne % getCellNumberVertical()].changeState(CellRPS.RED);
			((CellRPS)getArray()[randOne / getCellNumberVertical()][randOne % getCellNumberVertical()]).setGradientLevel(0);
			getArray()[randTwo / getCellNumberVertical()][randTwo % getCellNumberVertical()].changeState(CellRPS.GREEN);
			((CellRPS)getArray()[randTwo / getCellNumberVertical()][randTwo % getCellNumberVertical()]).setGradientLevel(0);
			getArray()[randThree / getCellNumberVertical()][randThree % getCellNumberVertical()].changeState(CellRPS.BLUE);
			((CellRPS)getArray()[randThree / getCellNumberVertical()][randThree % getCellNumberVertical()]).setGradientLevel(0);
		}
	}
}
