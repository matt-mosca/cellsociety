package backend;

import frontend.StyleUI;
import javafx.scene.paint.Color;
import util.EightNeighborFinder;
import util.NeighborFinder;
import util.TriangleNeighborFinder;

public class SimulationRPS extends Simulation {
	private NeighborFinder neighbors;
	private StyleUI style = new StyleUI();
	
	public SimulationRPS(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		specificSetUp();
		assignNeighbors(neighbors);
		prepareGrid();
		//updateColors();
		setRandomRGBCells();
	}
	
	public SimulationRPS(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		specificSetUp();
        super.initializeScene2(neighbors);
		assignNeighbors(neighbors);
		updateColors();
		setRandomRGBCells();
	}
	
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
	
	private void prepareGrid() {
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber].changeState(CellRPS.EMPTY);
				getArray()[rowNumber][columnNumber].setColor(Color.WHITE);
				((CellRPS)getArray()[rowNumber][columnNumber]).setGradientLevel(0);
			}
		}
	}
	
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
	
	@Override
	protected void updateColors() {
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				getArray()[i][j].setColor(chooseColor(getArray()[i][j].getState(), ((CellRPS)getArray()[i][j]).getGradientLevel()));
			}
		}
	}
	
	private int calcIntensityValue(int gradientLevel) {
		
		return 125 + gradientLevel * 13;
	}
	
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}
	
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
