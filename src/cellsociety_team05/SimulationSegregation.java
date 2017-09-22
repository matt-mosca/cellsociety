package cellsociety_team05;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SimulationSegregation {
	private CellSegregation[][] array;      
	private double satisfactionPercentage;
	private int numberOfCells;
	private double emptyPercentage;
	private double redToBlueRatio;
	private double cellNumberHorizontal;
	private double cellNumberVertical;
	
	//0 is empty, 1 is red, 2 is blue
	
	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, 
			double satisfactionPercentage, double redToBlueRatio) {
		//set up instance variables, put 0s in every cell
		array=new CellSegregation [cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber=0;rowNumber<array.length;rowNumber++) {
			CellSegregation[] row=array[rowNumber];
			for (int columnNumber=0;columnNumber<row.length;columnNumber++) {
				CellSegregation cell=row[columnNumber];
				cell.setRowNumber(rowNumber);
				
				
			}
		}
		this.emptyPercentage=emptyPercentage;
		this.satisfactionPercentage=satisfactionPercentage;
		this.redToBlueRatio=redToBlueRatio;
		this.numberOfCells=cellNumberHorizontal*cellNumberVertical;
		
		
	}
	
	public void initializeScene() {
		//according to percentage, do random function
		//call cell to change type 
		int redNumber=findNumber(1);
		int blueNumber=findNumber(2);
		
		int[] redSlots = random(redNumber);
		int[] blueSlots = random(blueNumber);
		
		for (int i=0;i<redSlots.length;i++) {
			int position=redSlots[i];
			
		}
		
		
		
		
		

		
	}

	public int[] random(int Number) {
		return new Random().ints(1, numberOfCells).distinct().limit(Number).toArray();
	}
	
	public int findNumber(int state) {
		int empty=(int) (numberOfCells*emptyPercentage);

		int filled=numberOfCells-empty;
		
		if (state==0) {
			return empty;				
		}
		if (state==1) {
			return (int) (filled/(redToBlueRatio+1)*redToBlueRatio);	 
		}
		if (state==2) {
			return (int) (filled/(redToBlueRatio+1));
		}
		return 0;
	}
	
	public void update() {
		//set up a loop, go through every cell
		//call whetherSatisfied
		
	}
	
	

	public CellSegregation[][] getArray() {
		return array;
	}

	public void setArray(
			CellSegregation[][] array) {
		this.array = array;
	}
	
	

}
