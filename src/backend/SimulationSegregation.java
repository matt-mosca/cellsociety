package backend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;


public class SimulationSegregation extends Simulation {

	//	private CellSegregation[][] array;
	private double satisfactionPercentage;
	private NeighborFinder neighbors;
	// 0 is empty, 1 is red, 2 is blue

	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double satisfactionPercentage,
			double redToBlueRatio) {
		// set up instance variables, put 0s in every cell
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		setArray(new CellSegregation[cellNumberVertical][cellNumberHorizontal]);
//		Image image = new Image(getClass().getClassLoader().getResourceAsStream(RED_IMAGE));
		for (int rowNumber = 0; rowNumber < cellNumberVertical; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberHorizontal; columnNumber++) {
				array[rowNumber][columnNumber]=new CellSegregation(0, null, null, rowNumber, columnNumber);
			}
		}
		neighbors = new EightNeighborFinder(array, 0, 0);
		this.satisfactionPercentage = satisfactionPercentage;
		initializeScene(neighbors);
//		initializeScene();		
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
	
	public void update() {
		// set up a loop, go through every cell
		// call whetherSatisfied
		ArrayList<Cell> emptyCells=findAllEmpty();
		ArrayList<Cell> dissatisfied=new ArrayList<Cell>();
		for (int rowNumber = 0; rowNumber < cellNumberVertical; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberHorizontal; columnNumber++) {
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
		for (int rowNumber = 0; rowNumber < cellNumberVertical; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberHorizontal; columnNumber++) {
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
		List<Cell> neighbors = cell.getNeighborCells();
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

	public void setArray(CellSegregation[][] newArray) {
		array = newArray;
	}
}
