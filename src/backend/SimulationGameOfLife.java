package backend;

import javafx.scene.paint.Color;

public class SimulationGameOfLife extends Simulation {
	private static final int UPPERBOUNDARY = 3;
	private static final int LOWERBOUNDARY = 2;
	private NeighborFinder neighbors;
	
	public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double redToBlueRatio) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		specificSetUp();
		neighbors = new EightNeighborFinder(getArray(), 0, 0);
		
		initializeGridStates();
		
		
	
	
		
		assignNeighbors(neighbors);
	}
	
	public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		specificSetUp();
		neighbors = new EightNeighborFinder(getArray(), 0, 0);
		
	
		
		
	
	    super.initializeScene2(neighbors);
	
		
		assignNeighbors(neighbors);
		updateColors();
	}
	
	

	public void specificSetUp() {
		setArray(new CellGameOfLife[getCellNumberVertical()][getCellNumberHorizontal()]);
		for (int rowNumber = 0; rowNumber < getCellNumberVertical(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberHorizontal(); columnNumber++) {
				getArray()[rowNumber][columnNumber] = new CellGameOfLife(CellGameOfLife.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
	}
	
	private void initializeGridStates() {
		fillGridStates();
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
					getArray()[(getNumberOfCells() - i) % getCellNumberVertical()][(getNumberOfCells() - i) / getCellNumberHorizontal()].changeState(CellGameOfLife.EMPTY);
					empty--;
			}
			else {
				getArray()[(getNumberOfCells() - i) % getCellNumberVertical()][(getNumberOfCells() - i) / getCellNumberHorizontal()].changeState(CellGameOfLife.LIVE);
			}
		}
	}
	
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}

	@Override
	protected Color chooseColor(int state) {
		Color color = null;

		if(state == CellGameOfLife.EMPTY)
			color=Color.WHITE;
		if(state == CellGameOfLife.LIVE)
			color=Color.BLUE;
		return color;
	}

	public void update() {
		int[][] temp = new int[getCellNumberVertical()][getCellNumberHorizontal()];
		for(int i = 0; i < getCellNumberVertical(); i++) {
			for(int j = 0; j < getCellNumberHorizontal(); j++) {
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
		for(int i = 0; i < getCellNumberVertical(); i++) {
			for(int j = 0; j < getCellNumberHorizontal(); j++) {
				getArray()[i][j].changeState(temp[i][j]);
			}
		}
		assignNeighbors(neighbors);
		updateColors();
	}
}
