package backend;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class SimulationWaTor extends Simulation {
	
	private int maxStarveDaysForSharks;
	private int minBreedDaysForSharks;
	private int minBreedDaysForFish;
	
	// 0 is empty, 1 is shark, 2 is fish

	public SimulationWaTor(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage,double redToBlueRatio,
			int maxStarveDaysForSharks, int minBreedDaysForSharks, int minBreedDaysForFish) {
		// set up instance variables, put 0s in every cell
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		
		this.maxStarveDaysForSharks=maxStarveDaysForSharks;
		this.minBreedDaysForSharks=minBreedDaysForSharks;
		this.minBreedDaysForFish=minBreedDaysForFish;
		super.array = new CellWaTor[cellNumberHorizontal][cellNumberVertical];
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				super.array[rowNumber][columnNumber]=new CellWaTor(0, null, null, rowNumber, columnNumber);
			}
		}
		initializeScene();
		updateColors();
	}

	@Override
	protected void findNeighbors() {
		//only four neighbors
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
	
	@Override
	protected Color chooseColor(int state) {
		Color color = null;
		if(state == 0)
			color = Color.WHITE;
		if(state == 1){
			color = Color.PINK;
		}
		if(state == 2)
			color = Color.AQUA;
		return color;
	}

	public void update() {
		// set up a loop, go through every cell
		//1 is shark, 2 is fish
	
		//finish all sharks, eat/breed/die, checked correct
		ArrayList<Cell> allSharks=findallType(1);
		System.out.println(allSharks.size());
	
		for (int sharkNumber = 0; sharkNumber < allSharks.size();sharkNumber++) {
			CellWaTor shark=(CellWaTor) allSharks.get(sharkNumber);
			ArrayList <Cell> allNeighborFish= Neighbortype(shark,2);
			ArrayList <Cell> allNeighborEmpty= Neighbortype(shark,0);
			//if there is fish, eat it
			if (allNeighborFish.size()>0) {
				CellWaTor randomFish = findRandomNeighbor(allNeighborFish);
				sharkMove(shark,randomFish,"eat");
				shark=randomFish;
			}
			//no fish, move to an empty cell
			if(allNeighborFish.size()==0 && allNeighborEmpty.size()>0) {
				System.out.println("test");
				CellWaTor randomEmpty = findRandomNeighbor(allNeighborEmpty);
				sharkMove(shark,randomEmpty,"starve");
				shark=randomEmpty;
			}
			
			//cannot move, cannot eat
			if (allNeighborEmpty.size()==0 && allNeighborFish.size()==0) {
				shark.setBreedDays(shark.getBreedDays()+1);
				shark.setStarveDays(shark.getStarveDays()+1);
			}
			
			//check whether will die
			if (shark.getStarveDays()>maxStarveDaysForSharks) {	
				shark.changeState(0);
//				shark.setImage(chooseImage(0));
				shark.setBreedDays(0);
				shark.setStarveDays(0);
			}
			
			//check whether will breed
			if (shark.getBreedDays()>=minBreedDaysForSharks) {
				allNeighborEmpty= Neighbortype(shark,0);
				if (allNeighborEmpty.size()>0) {
				    CellWaTor potentialBreedCell = findRandomNeighbor(allNeighborEmpty);
				    potentialBreedCell.changeState(1);
//				    potentialBreedCell.setImage(chooseImage(1));
				    shark.setBreedDays(0);
				}
			}
			System.out.println(shark.getStarveDays());
		}
		ArrayList<Cell> allFish=findallType(2);
		for (int fishNumber = 0; fishNumber < allFish.size();fishNumber++) {
			CellWaTor fish=(CellWaTor) allFish.get(fishNumber);
			ArrayList <Cell> allNeighborEmpty= Neighbortype(fish,0);
			//fish move
			if (allNeighborEmpty.size()>0) {
			    CellWaTor randomEmpty = findRandomNeighbor(allNeighborEmpty);
			    fishMove(fish, randomEmpty);
			    fish=randomEmpty;
			}
			//fish not move, but still breed days increase
			if (allNeighborEmpty.size()==0) {
				fish.setBreedDays(fish.getBreedDays()+1);
			}
			//check whether breed
			if (fish.getBreedDays()>=minBreedDaysForFish) {
				allNeighborEmpty= Neighbortype(fish,0);
				if (allNeighborEmpty.size()>0) {
				    CellWaTor potentialBreedCell = findRandomNeighbor(allNeighborEmpty);
				    potentialBreedCell.changeState(2);
//				    potentialBreedCell.setImage(chooseImage(2));
				    fish.setBreedDays(0);
				}	
			}
		}
		updateColors();	
	}

	private void fishMove(CellWaTor fish,CellWaTor randomEmpty) {
		randomEmpty.changeState(2);
		randomEmpty.setColor(chooseColor(2));
		randomEmpty.setBreedDays(fish.getBreedDays()+1);
		fish.changeState(0);
		fish.setBreedDays(0);
		fish.setColor(chooseColor(0));
	}

	public CellWaTor findRandomNeighbor(
			ArrayList<Cell> allNeighbor) {
		int randomIndex=random(1,allNeighbor.size())[0];
		CellWaTor random=(CellWaTor) allNeighbor.get(randomIndex);
		return random;
	}
	
	public void sharkMove(
			CellWaTor shark,
			CellWaTor randomSlot, String code) {
		randomSlot.changeState(1);
		randomSlot.setColor(chooseColor(1));
		randomSlot.setBreedDays(shark.getBreedDays()+1);
		if (code.equals("eat")) {
			randomSlot.setStarveDays(0);	
		}
		if (code.equals("starve")) {
			randomSlot.setStarveDays(shark.getStarveDays()+1);	
		}
		shark.changeState(0);
		shark.setBreedDays(0);
		shark.setStarveDays(0);
		shark.setColor(chooseColor(0));
	}

	

	private ArrayList<Cell> findallType(int state) {
		ArrayList<Cell> allSharks=new ArrayList<Cell>();
		for (int rowNumber = 0; rowNumber < cellNumberHorizontal; rowNumber++) {
			for (int columnNumber = 0; columnNumber < cellNumberVertical; columnNumber++) {
				CellWaTor cell = (CellWaTor) super.array[rowNumber][columnNumber];
				if (cell.getState()==state) {
					allSharks.add(cell);	
				}
			}
		}
		return allSharks;
	}

	private ArrayList<Cell> Neighbortype(CellWaTor cell, int type) {
		ArrayList<Cell> neighbors=cell.getNeighborCells();
		ArrayList<Cell> neighborFish=new ArrayList<Cell>();
		for (int i=0;i<neighbors.size();i++) {
			if (neighbors.get(i).getState()==type) {
				neighborFish.add(neighbors.get(i));
			}
		}
		return neighborFish;
	}
	
	public void setArray(
			CellWaTor[][] array) {
		super.array = array;
	}
}
