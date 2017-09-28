package backend;

import java.util.ArrayList;

import javafx.scene.paint.Color;




public class SimulationSegregation extends Simulation {
	private static final String EMPTY_IMAGE = "empty_block.gif";
	private static final String RED_IMAGE = "red_block.gif";
	private static final String BLUE_IMAGE = "blue_block.gif";
	
	//	private CellSegregation[][] array;
	private double satisfactionPercentage;
	// 0 is empty, 1 is red, 2 is blue

	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double satisfactionPercentage,
			double redToBlueRatio) {
		// set up instance variables, put 0s in every cell
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		array = new CellSegregation[cellNumberHorizontal][cellNumberVertical];
//		Image image = new Image(getClass().getClassLoader().getResourceAsStream(RED_IMAGE));
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {

				array[rowNumber][columnNumber]=new CellSegregation(0, null, null, rowNumber, columnNumber);

			}
		}
	
		this.satisfactionPercentage = satisfactionPercentage;
		initializeScene();
		
		
	    /*
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				System.out.println(super.array[rowNumber][columnNumber].getState());
			}
		}
		System.out.println("separate here");
		
		
		
		
		update();
		
	   
		
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				System.out.println(array[rowNumber][columnNumber].getState());
			
			}
		}
		
		*/
		
		
		
		
		
		
		
	}
	


	@Override
	protected Color chooseColor(int state) {
		Color color = null;

		if(state == 0)
			color=Color.WHITE;

		if(state == 1)
			color=Color.RED;
		if(state == 2)
			color=Color.BLUE;
		return color;
	}
	
	@Override
	public void update() {
		// set up a loop, go through every cell
		// call whetherSatisfied
		ArrayList<Cell> emptyCells=findAllEmpty();
		ArrayList<Cell> dissatisfied=new ArrayList<Cell>();
	
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell = (CellSegregation) array[rowNumber][columnNumber];
				if (cell.getState()==0) {continue;}
				else if (!whetherSatisfied(cell)) {
					dissatisfied.add(cell);	
				}
			}
		}
		
		for (int needMove=0;needMove<dissatisfied.size();needMove++) {
			if (emptyCells.size()>0){
				int previousState=dissatisfied.get(needMove).getState();
				dissatisfied.get(needMove).changeState(0);
				dissatisfied.get(needMove).setColor(chooseColor(0));
				int theEmptyReadyForFill=random(1,emptyCells.size())[0];
				emptyCells.get(theEmptyReadyForFill).changeState(previousState);
				emptyCells.get(theEmptyReadyForFill).setColor(chooseColor(previousState));
				emptyCells.remove(theEmptyReadyForFill);
				}
		}
	}

	private ArrayList<Cell> findAllEmpty() {
		ArrayList<Cell> empty= new ArrayList<Cell>();
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellSegregation cell=(CellSegregation) array[rowNumber][columnNumber];
				if (cell.getState()==0) {
					empty.add(cell);
				}
			}
		}
		return empty;
		//Are you actually updating the Cell array here? I can't find the point at which you do that. That 
		//might just be because I'm blind. -V
		
	}

	private boolean whetherSatisfied(CellSegregation cell) {
		ArrayList<Cell> neighbors=cell.getNeighborCells();
		int countFilled=0;
		int countSatisfied=0;
		for (int i=0;i<neighbors.size();i++) {
			if (neighbors.get(i).getState()!=0) {
				countFilled++;
				if (neighbors.get(i).getState()==cell.getState()) {
					countSatisfied++;
				}
			}
		}
		if (countFilled==0) {return true;}
		double satisfaction=(double)countSatisfied/(double)countFilled;
		if (satisfaction>=satisfactionPercentage) {
			return true;	
		}
		return false;
	}
	
	
	@Override
	public CellSegregation[][] getArray() {
		return (CellSegregation[][]) array;
	}

	public void setArray(CellSegregation[][] newArray) {
		array = newArray;
	}
	
	public static void main(String[] args) {
		SimulationSegregation test = new SimulationSegregation(5, 5, 0.2, 0.5, 0.5);
		testArrayPrinter(test.getArray());
//		System.out.println(test.getArray()[1][1].getNeighborCells());
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
	
	private static void testArrayPrinter(Cell[][] testArray) {
		
		
		
		
		for(int i = 0; i < testArray.length; i++) {
			for(int j = 0; j < testArray[0].length; j++) {
				System.out.print(testArray[i][j].getState() + " ");
			}
			System.out.println();
		}
		System.out.println();
		
	}
	

}
