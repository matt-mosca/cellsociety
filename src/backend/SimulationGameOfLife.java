package backend;

import javafx.scene.image.Image;

public class SimulationGameOfLife extends Simulation {
	private static final String EMPTY_IMAGE = "empty_block.gif";
	private static final String LIVE_IMAGE = "live_block.gif";
	
//	private CellGameOfLife[][] array;
	private int numberOfCells;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	private double initialEmptyPercentage;
	
	public SimulationGameOfLife(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		this.initialEmptyPercentage = emptyPercentage;
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
		array = new CellGameOfLife[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				array[rowNumber][columnNumber] = new CellGameOfLife(CellGameOfLife.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		initializeGridStates();
		findNeighbors();
	}
	
	private void initializeGridStates() {
		fillGridStates();
		updateImages();
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
					array[(numberOfCells - i) % cellNumberHorizontal][(numberOfCells - i) / cellNumberVertical].changeState(CellGameOfLife.EMPTY);
					empty--;
			}
			else {
				array[(numberOfCells - i) % cellNumberHorizontal][(numberOfCells - i) / cellNumberVertical].changeState(CellGameOfLife.LIVE);
			}
		}
	}
	
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}
	
    
	@Override
	protected Image chooseImage(int state) {
		Image image = null;
		if(state == CellGameOfLife.EMPTY)
			image = new Image(getClass().getClassLoader().getResourceAsStream(EMPTY_IMAGE));
		if(state == CellGameOfLife.LIVE)
			image = new Image(getClass().getClassLoader().getResourceAsStream(LIVE_IMAGE));
		return image;
	}

	@Override
	public void update() {
		int[][] temp = new int[cellNumberHorizontal][cellNumberVertical];
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				CellGameOfLife cell = (CellGameOfLife)array[i][j];
				int liveCount = 0;
				for(int k = 0; k < cell.getNeighborCells().size(); k++) {
					if(cell.getNeighborCells().get(k).getState() == CellGameOfLife.LIVE)
						liveCount++;
				}
				temp[i][j] = array[i][j].getState();
				//Any live cell with two or three live neighbors lives on to the next generation, so do nothing
				//Any live cell with fewer than two live neighbors dies
				if(cell.getState() == CellGameOfLife.LIVE && liveCount < 2)
					temp[i][j] = CellGameOfLife.EMPTY;
				//Any live cell with more than three live neighbors dies
				if(cell.getState() == CellGameOfLife.LIVE && liveCount > 3)
					temp[i][j] = CellGameOfLife.EMPTY;
				//Any dead cell with exactly three live neighbors becomes a live cell
				if(cell.getState() == CellGameOfLife.EMPTY && liveCount == 3)
					temp[i][j] = CellGameOfLife.LIVE;
			}
		}
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				array[i][j].changeState(temp[i][j]);
			}
		}
//		for(int i = 0; i < cellNumberHorizontal; i++) {
//			for(int j = 0; j < cellNumberVertical; j++) {
//				CellGameOfLife cell = (CellGameOfLife)array[i][j];
//				int liveCount = 0;
//				for(int k = 0; k < cell.getNeighborCells().size(); k++) {
//					if(cell.getNeighborCells().get(k).getState() == CellGameOfLife.LIVE)
//						liveCount++;
//				}
//				//Any live cell with two or three live neighbors lives on to the next generation, so do nothing
//				//Any live cell with fewer than two live neighbors dies
//				if(cell.getState() == CellGameOfLife.LIVE && liveCount < 2)
//					cell.die();
//				//Any live cell with more than three live neighbors dies
//				if(cell.getState() == CellGameOfLife.LIVE && liveCount > 3)
//					cell.die();
//				//Any dead cell with exactly three live neighbors becomes a live cell
//				if(cell.getState() == CellGameOfLife.EMPTY && liveCount == 3)
//					cell.live();
//			}
//		}
		findNeighbors();
		updateImages();
	}
	
	@Override
	public CellGameOfLife[][] getArray() {
		return (CellGameOfLife[][])array;
	}

	public void setArray(CellGameOfLife[][] array) {
		this.array = array;
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
		SimulationGameOfLife test = new SimulationGameOfLife(5, 5, 0.6, 0.2);
		testArrayPrinter(test.getArray());
//		System.out.println(test.getArray()[0][0].getNeighborCells());
//		System.out.println();
//		System.out.print(test.findNumberEmpty());
//		System.out.println();
		int iterations = 5;
		//Drives test simulation
		for(int i = 0; i < iterations; i++) {
			test.update();
			System.out.println("Iteration " + (i + 1));
			testArrayPrinter(test.getArray());
		}
	}
}
