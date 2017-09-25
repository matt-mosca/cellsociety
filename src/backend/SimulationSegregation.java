package backend;

import java.util.ArrayList;


import javafx.scene.image.Image;

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
	protected Image chooseImage(int state) {
		Image image = null;

		if(state == 0)
			image = new Image(getClass().getClassLoader().getResourceAsStream(RED_IMAGE));

		if(state == 1)
			image = new Image(getClass().getClassLoader().getResourceAsStream(RED_IMAGE));
		if(state == 2)
			image = new Image(getClass().getClassLoader().getResourceAsStream(BLUE_IMAGE));
		return image;
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
				dissatisfied.get(needMove).setImage(chooseImage(0));
				int theEmptyReadyForFill=random(1,emptyCells.size())[0];
				emptyCells.get(theEmptyReadyForFill).changeState(previousState);
				emptyCells.get(theEmptyReadyForFill).setImage(chooseImage(previousState));
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
	
	

}
