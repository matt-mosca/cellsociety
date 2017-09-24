package backend;

import java.util.ArrayList;

import javafx.scene.image.Image;

//Add images
public class SimulationFire extends Simulation{
	private static final String EMPTY_IMAGE = "empty_block.gif";
	private static final String TREE_IMAGE = "tree_block.gif";
	private static final String BURNING_IMAGE = "fire_block.gif";
	
	private CellFire[][] array;
	private int numberOfCells;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	private double probCatch;
	private double initialEmptyPercentage;

	public SimulationFire(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio, double probCatch) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
//		initialEmptyPercentage = 0.25;
		array = new CellFire[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				array[rowNumber][columnNumber] = new CellFire(CellFire.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		findNeighbors();
		initializeGridStates();
	}
	
	private void initializeGridStates() {
		fillGridStates();
		setRandomFire();
		updateImages();
	}
	
	private int findNumberEmpty() {
		int empty = (int) (numberOfCells* initialEmptyPercentage);
		return empty;
	}
	
	private void fillGridStates() {
		int rand = 0;
		int empty = findNumberEmpty();
		for(int i = numberOfCells; i > 0; i--) {
			rand = (int) getRandomNum(i);
			if(empty > 0 && rand <= empty) {
					array[(numberOfCells - i) % cellNumberHorizontal][(numberOfCells - i) / cellNumberVertical].changeState(CellFire.EMPTY);
					empty--;
			}
			else {
				array[(numberOfCells - i) % cellNumberHorizontal][(numberOfCells - i) / cellNumberVertical].changeState(CellFire.TREE);
			}
		}
	}
	
	private void setRandomFire() {
		int rand = (int) getRandomNum(numberOfCells);
		array[rand % cellNumberHorizontal][rand / cellNumberVertical].changeState(CellFire.BURNING);
	}
	
	public void findNeighbors() {
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				Cell cell = array[rowNumber][columnNumber];
			    ArrayList<Cell> neighbors=new ArrayList<Cell>();
				if (rowNumber-1>=0) {
					neighbors.add(array[rowNumber-1][columnNumber]);
				}
				if (columnNumber-1>=0) {
					neighbors.add(array[rowNumber][columnNumber-1]);
				}
				if (columnNumber+1<=cellNumberVertical-1) {
					neighbors.add(array[rowNumber][columnNumber+1]);
				}
				if (rowNumber+1<=cellNumberHorizontal-1) {
					neighbors.add(array[rowNumber+1][columnNumber]);		
				}
				cell.setNeighborCells(neighbors);
			}
		}

	}
	
	public void update() {
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				if(array[i][j].getState() == CellFire.BURNING)
					array[i][j].changeState(CellFire.EMPTY);
				if(array[i][j].getState() == CellFire.TREE) {
					if(potentialForFire(array[i][j]))
						if(getRandomNum(1) <= probCatch)
							array[i][j].changeState(CellFire.BURNING);
				}
			}
		}
		updateImages();
	}
	
	private boolean potentialForFire(CellFire cell) {
		for(int i = 0; i < cell.getNeighborCells().size(); i++) {
			if(cell.getNeighborCells().get(i).getState() == CellFire.BURNING)
				return true;
		}
		return false;
	}
	
	private double getRandomNum(int upperBound) {
		return Math.random() * upperBound;
	}
	
	private Image chooseImage(int state) {
		Image image = new Image("");
		if(state == CellFire.EMPTY)
			image = new Image(getClass().getClassLoader().getResourceAsStream(EMPTY_IMAGE));
		if(state == CellFire.TREE)
			image = new Image(getClass().getClassLoader().getResourceAsStream(TREE_IMAGE));
		if(state == CellFire.BURNING)
			image = new Image(getClass().getClassLoader().getResourceAsStream(BURNING_IMAGE));
		return image;
	}
	
	private void updateImages() {
		for(int i = 0; i < cellNumberHorizontal; i++) {
			for(int j = 0; j < cellNumberVertical; j++) {
				array[i][j].setImage(chooseImage(array[i][j].getState()));
			}
		}
	}
	
	public double getProbCatch() {
		return probCatch;
	}
	
	public void setProbCatch(double prob) {
		probCatch = prob;
	}
	
	public CellFire[][] getArray() {
		return array;
	}

	public void setArray(CellFire[][] array) {
		this.array = array;
	}
}
