package backend;

public class SimulationGameOfLife extends Simulation {
	private static final String EMPTY_IMAGE = "sjdfoijo";
	private static final String TREE_IMAGE = "sjdfoijo";
	private static final String BURNING_IMAGE = "sjdfoijo";
	
	private CellFire[][] array;
	private int numberOfCells;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	
	public SimulationGameOfLife(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		array = new CellFire[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				array[rowNumber][columnNumber] = new CellFire(CellFire.EMPTY, null, null, rowNumber, columnNumber);
			}
		}
		findNeighbors();
	}
}
