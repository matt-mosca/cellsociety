package backend;

import javafx.scene.paint.Color;

public class SimulationRPS extends Simulation {
	private NeighborFinder neighbors;
	private int[] number = new int[2];
	
	public SimulationRPS(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
			double redToBlueRatio, double probCatch) {
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		specificSetUp();
		assignNeighbors(neighbors);
		updateColors();
	}
	
	public SimulationRPS(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation,double probCatch) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		specificSetUp();
        super.initializeScene2(neighbors);
		assignNeighbors(neighbors);
		updateColors();
		
	}
	
	public void specificSetUp() {
		setArray(new CellRPS[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber] = new CellRPS(0, null, null, rowNumber, columnNumber, 0);
			}
		}
		neighbors = new EightNeighborFinder(getArray(), 0, 0);
	}
	
	public void update() {
		int[][] temp = new int[getCellNumberHorizontal()][getCellNumberVertical()];
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
//				temp[i][j] = getArray()[i][j].getState();
//				if(getArray()[i][j].getState() == CellFire.BURNING)
//					temp[i][j] = CellFire.EMPTY;
//				if(getArray()[i][j].getState() == CellFire.TREE) {
//					if(potentialForFire(getArray()[i][j]))
//						if(getRandomNum(1) <= probCatch)
//							temp[i][j] = CellFire.BURNING;
//				}
			}
		}
		for(int i = 0; i < getCellNumberHorizontal(); i++) {
			for(int j = 0; j < getCellNumberVertical(); j++) {
//				getArray()[i][j].changeState(temp[i][j]);
			}
		}
		assignNeighbors(neighbors);
		updateColors();
		count();
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
		return 255 - gradientLevel * 25;
	}
	
	private void count() {
		number[0]=0;
		number[1]=0;
		for(int i=0; i<getArray().length; i++) {
			for(int j=0; j<getArray()[i].length; j++) {
				if (getArray()[i][j].getState()==1) {
					number[0]+=1;
				}
				else if(getArray()[i][j].getState()==2) {
					number[1]+=1;
				}
			}
		}
	}
}
