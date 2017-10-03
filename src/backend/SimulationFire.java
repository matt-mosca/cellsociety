package backend;

import frontend.StyleUI;
import javafx.scene.paint.Color;
import util.FourNeighborFinder;
import util.NeighborFinder;
import util.TriangleNeighborFinder;

public class SimulationFire extends Simulation{
	private double probCatch;
	private NeighborFinder neighbors;
	private StyleUI style = new StyleUI();
	

	public SimulationFire(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio, double probCatch) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		specificSetUp(probCatch);
		initializeGridStates();
		assignNeighbors(neighbors);
	}
	
	public SimulationFire(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation,double probCatch) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		specificSetUp(probCatch);
        super.initializeScene2(neighbors);
		assignNeighbors(neighbors);
		updateColors();
	}
	
	public void specificSetUp(double probCatch) {
		this.probCatch = probCatch;
		setArray(new CellFire[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber] = new CellFire(CellFire.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		if(style.gridShape().equals("Triangle"))
			neighbors = new TriangleNeighborFinder(getArray(), 0, 0, style.getGridEdge());
		else
			neighbors = new FourNeighborFinder(getArray(), 0, 0, style.getGridEdge());
	}
	
	private void initializeGridStates() {
		fillGridStates();
		setRandomFire();
		updateColors();
	}
	
	private int findNumberEmpty() {
		int empty = (int) (getNumberOfCells() * getEmptyPercentage());
		return empty;
	}
	
	private void fillGridStates() {
		int rand = 0;
		int empty = findNumberEmpty();
		for(int i = getNumberOfCells(); i > 0; i--) {
			rand = (int) getRandomNum(i);
			if(empty > 0 && rand <= empty) {
					getArray()[(getNumberOfCells() - i) / getCellNumberVertical()][(getNumberOfCells() - i) % getCellNumberVertical()].changeState(CellFire.EMPTY);
					empty--;
			}
			else {
				getArray()[(getNumberOfCells() - i) / getCellNumberVertical()][(getNumberOfCells() - i) % getCellNumberVertical()].changeState(CellFire.TREE);
			}
		}
	}
	
	private void setRandomFire() {
		int rand = (int) getRandomNum(getNumberOfCells());
		if(getCellNumberVertical() != 0)
			getArray()[rand / getCellNumberVertical()][rand % getCellNumberVertical()].changeState(CellFire.BURNING);
	}
	
	public void update() {
		int[][] temp = new int[getCellNumberHorizontal()][getCellNumberVertical()];
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				temp[i][j] = getArray()[i][j].getState();
				if(getArray()[i][j].getState() == CellFire.BURNING)
					temp[i][j] = CellFire.EMPTY;
				if(getArray()[i][j].getState() == CellFire.TREE) {
					if(potentialForFire(getArray()[i][j]))
						if(getRandomNum(1) <= probCatch)
							temp[i][j] = CellFire.BURNING;
				}
			}
		}
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
				getArray()[i][j].changeState(temp[i][j]);
			}
		}
		assignNeighbors(neighbors);
		updateColors();
		super.count(1,2);
	}
	
	private boolean potentialForFire(Cell cell) {
		for(int i = 0; i < cell.getNeighborCells().size(); i++) {
			if(cell.getNeighborCells().get(i).getState() == CellFire.BURNING)
				return true;
		}
		return false;
	}
	
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}
	

	protected Color chooseColor(int state) {
		Color color = null;

		if(state == CellFire.EMPTY)
			color=style.emptyColor();
		if(state == CellFire.TREE)
			color=Color.GREEN;
		if(state == CellFire.BURNING) {
			color=Color.RED;
		}
		return color;
	}
	
	public double getProbCatch() {
		return probCatch;
	}
	
	public void setProbCatch(double prob) {
		probCatch = prob;
	}
	
	

}