package backend;

import javafx.scene.paint.Color;

public class SimulationFire extends Simulation{
	
//	private CellFire[][] array;
	private int numberOfCells;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	private double probCatch;
	private double initialEmptyPercentage;
	private NeighborFinder neighbors;

	public SimulationFire(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio, double probCatch) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		this.initialEmptyPercentage = emptyPercentage;
		this.probCatch = probCatch;
		this.numberOfCells = cellNumberHorizontal * cellNumberVertical;
		this.cellNumberHorizontal = cellNumberHorizontal;
		this.cellNumberVertical = cellNumberVertical;
//		initialEmptyPercentage = 0.25;
		array = new CellFire[cellNumberVertical][cellNumberHorizontal];
		for (int rowNumber = 0; rowNumber < cellNumberVertical; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberHorizontal; columnNumber++) {
				array[rowNumber][columnNumber] = new CellFire(CellFire.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		neighbors = new FourNeighborFinder(array, 0, 0);
//		System.out.println(numberOfCells);
		initializeGridStates();
//		findNeighbors();
		assignNeighbors(neighbors);
	}
	
	private void initializeGridStates() {
		fillGridStates();
		setRandomFire();
		updateColors();
	}
	
	private int findNumberEmpty() {
		int empty = (int) (numberOfCells * initialEmptyPercentage);
		return empty;
	}
	
	private void fillGridStates() {
		int rand = 0;
		int empty = findNumberEmpty();
//		System.out.println(empty);
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
		if(cellNumberVertical != 0)
			array[rand % cellNumberVertical][rand / cellNumberHorizontal].changeState(CellFire.BURNING);
	}
	
//	@Override
//	public void findNeighbors() {
//		for (int rowNumber = 0; rowNumber < cellNumberVertical; rowNumber++) {
//			for (int columnNumber = 0; columnNumber < cellNumberHorizontal; columnNumber++) {
//				Cell cell = array[rowNumber][columnNumber];
//			    ArrayList<Cell> neighbors=new ArrayList<Cell>();
//				if (rowNumber-1>=0) {
//					neighbors.add(array[rowNumber-1][columnNumber]);
//				}
//				if (columnNumber-1>=0) {
//					neighbors.add(array[rowNumber][columnNumber-1]);
//				}
//				if (columnNumber+1<=cellNumberHorizontal-1) {
//					neighbors.add(array[rowNumber][columnNumber+1]);
//				}
//				if (rowNumber+1<=cellNumberVertical-1) {
//					neighbors.add(array[rowNumber+1][columnNumber]);		
//				}
//				cell.setNeighborCells(neighbors);
//			}
//		}
//	}
	
	public void update() {
//		System.out.println("fire update");
		int[][] temp = new int[cellNumberVertical][cellNumberHorizontal];
		for(int i = 0; i < cellNumberVertical; i++) {
			for(int j = 0; j < cellNumberHorizontal; j++) {
				temp[i][j] = array[i][j].getState();
				if(array[i][j].getState() == CellFire.BURNING)
					temp[i][j] = CellFire.EMPTY;
				if(array[i][j].getState() == CellFire.TREE) {
					if(potentialForFire(array[i][j]))
						if(getRandomNum(1) <= probCatch)
							temp[i][j] = CellFire.BURNING;
				}
			}
		}
		for(int i = 0; i < cellNumberVertical; i++) {
			for(int j = 0; j < cellNumberHorizontal; j++) {
				array[i][j].changeState(temp[i][j]);
			}
		}
//		findNeighbors();
		assignNeighbors(neighbors);
		updateColors();
	}
	
//	//Should this be internal to the cell?
//	protected void updateImages() {
//		for(int i = 0; i < cellNumberVertical; i++) {
//			for(int j = 0; j < cellNumberHorizontal; j++) {
//				array[i][j].setColor(chooseColor(array[i][j].getState()));
//			}
//		}
//	}
	
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
			color=Color.WHITE;
		if(state == CellFire.TREE)
			color=Color.GREEN;
		if(state == CellFire.BURNING) {
			color=Color.RED;
		}
//		System.out.println(image);
		return color;
	}
	
	public double getProbCatch() {
		return probCatch;
	}
	
	public void setProbCatch(double prob) {
		probCatch = prob;
	}

	public void setArray(CellFire[][] array) {
		this.array = array;
	}
	
//	private static void testArrayPrinter(Cell[][] testArray) {
////		for(int i = 0; i < testArray.length; i++) {
////			for(int j = 0; j < testArray[0].length; j++) {
////				System.out.print(testArray[i][j].getImage() + " ");
////			}
////			System.out.println();
////		}
//		System.out.println();
//		for(int i = 0; i < testArray.length; i++) {
//			for(int j = 0; j < testArray[0].length; j++) {
//				System.out.print(testArray[i][j].getState() + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//	}
	
//	public static void main(String[] args) {
//		SimulationFire test = new SimulationFire(5, 5, 0.2, 0.5, 0.5);
//		testArrayPrinter(test.getArray());
////		System.out.println(test.getArray()[1][1].getNeighborCells());
////		System.out.println();
////		System.out.print(test.findNumberEmpty());
////		System.out.println();
//		int iterations = 5;
//		//Drives test simulation
//		for(int i = 0; i < iterations; i++) {
//			test.update();
//			System.out.println("Iteration " + (i + 1));
//			testArrayPrinter(test.getArray());
//		}
//	}
}
