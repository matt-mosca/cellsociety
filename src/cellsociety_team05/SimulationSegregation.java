package cellsociety_team05;

import java.util.Random;

public class SimulationSegregation {
	private CellSociety[][] array;      
	private double satisfactionPercentage;
	private int numberOfCells;
	private double emptyPercentage;
	private double redToBlueRatio;
	
	//0 is empty, 1 is red, 2 is blue
	
	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, 
			double satisfactionPercentage, double redToBlueRatio) {
		//set up instance variables, put 0s in every cell
		array=new CellSociety [cellNumberHorizontal][cellNumberVertical];
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
		Random rand=new Random();
		
		
		

		
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
	
	

	public CellSociety[][] getArray() {
		return array;
	}

	public void setArray(
			CellSociety[][] array) {
		this.array = array;
	}
	
	

}
