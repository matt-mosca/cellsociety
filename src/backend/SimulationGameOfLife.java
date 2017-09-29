package backend;

import javafx.scene.paint.Color;

public class SimulationGameOfLife extends Simulation {
	private static final int UPPERBOUNDARY = 3;
	private static final int LOWERBOUNDARY = 2;
	
//	private CellGameOfLife[][] array;
	private int numberOfCells;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	private double initialEmptyPercentage;
	
	public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		this.initialEmptyPercentage = emptyPercentage;
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		array = new CellGameOfLife[cellNumberVertical][cellNumberHorizontal];
		for (int rowNumber = 0; rowNumber < cellNumberVertical; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberHorizontal; columnNumber++) {
				array[rowNumber][columnNumber] = new CellGameOfLife(CellGameOfLife.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		initializeGridStates();
		findNeighbors();
	}
	
	private void initializeGridStates() {
		fillGridStates();
		updateColors();
	}
	
	private int findNumberEmpty() {
		int empty = (int) (numberOfCells * initialEmptyPercentage);
		return empty;
	}
	
	private void fillGridStates() {
		int rand = 0;
		int empty = findNumberEmpty();
		for(int i = numberOfCells; i > 0; i--) {
			rand = (int) getRandomNum(i);
			if(empty > 0 && rand <= empty) {
					array[(numberOfCells - i) % cellNumberVertical][(numberOfCells - i) / cellNumberHorizontal].changeState(CellGameOfLife.EMPTY);
					empty--;
			}
			else {
				array[(numberOfCells - i) % cellNumberVertical][(numberOfCells - i) / cellNumberHorizontal].changeState(CellGameOfLife.LIVE);
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
		int[][] temp = new int[cellNumberVertical][cellNumberHorizontal];
		for(int i = 0; i < cellNumberVertical; i++) {
			for(int j = 0; j < cellNumberHorizontal; j++) {
				CellGameOfLife cell = (CellGameOfLife)array[i][j];
				int liveCount = 0;
				for(int k = 0; k < cell.getNeighborCells().size(); k++) {
					if(cell.getNeighborCells().get(k).getState() == CellGameOfLife.LIVE)
						liveCount++;
				}
				temp[i][j] = array[i][j].getState();
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
		for(int i = 0; i < cellNumberVertical; i++) {
			for(int j = 0; j < cellNumberHorizontal; j++) {
				array[i][j].changeState(temp[i][j]);
			}
		}
		findNeighbors();
		updateColors();
	}
	
	public void setArray(CellGameOfLife[][] array) {
		this.array = array;
	}
}
