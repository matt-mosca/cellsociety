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
		array = new CellGameOfLife[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				array[rowNumber][columnNumber] = new CellGameOfLife(CellGameOfLife.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		findNeighbors();
		initializeGridStates();
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
	
	private Image chooseImage(int state) {
		Image image = new Image("");
		if(state == CellGameOfLife.EMPTY)
			image = new Image(getClass().getClassLoader().getResourceAsStream(EMPTY_IMAGE));
		if(state == CellGameOfLife.LIVE)
			image = new Image(getClass().getClassLoader().getResourceAsStream(LIVE_IMAGE));
		return image;
	}
	
	public void update() {
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				CellGameOfLife cell = (CellGameOfLife)array[i][j];
				int liveCount = 0;
				for(int k = 0; k < cell.getNeighborCells().size(); k++) {
					if(cell.getNeighborCells().get(k).getState() == CellGameOfLife.LIVE)
						liveCount++;
				}
				if(cell.getState() == CellGameOfLife.LIVE && liveCount < 2)
					cell.die();
				if(cell.getState() == CellGameOfLife.LIVE && liveCount > 3)
					cell.die();
				if(cell.getState() == CellGameOfLife.EMPTY && liveCount ==3)
					cell.live();
			}
		}
		updateImages();
	}
	
	private void updateImages() {
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				array[i][j].setImage(chooseImage(array[i][j].getState()));
			}
		}
	}
	
	public static void main(String[] args) {
		SimulationGameOfLife test = new SimulationGameOfLife(5, 5, 0.2, 0.2);
	}
}
