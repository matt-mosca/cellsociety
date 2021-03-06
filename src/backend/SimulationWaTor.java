package backend;

import java.util.ArrayList;
import java.util.List;

import frontend.StyleUI;
import javafx.scene.paint.Color;
import util.FourNeighborFinder;
import util.NeighborFinder;
import util.TriangleNeighborFinder;

/**
 * SimulationWaTor.java
 * @author Yiqin Zhou
 * Simulation subclass specific to the WaTor simulation. In this simulation, shark will eat fish, both shark and fish  
 * will reproduce and shark will die if it stays hungry for too long
 */
public class SimulationWaTor extends Simulation {
	private static final String SHARKSTARVE = "starve";
	private static final String SHARKEAT = "eat";
	
	private int maxStarveDaysForSharks;
	private int minBreedDaysForSharks;
	private int minBreedDaysForFish;
	private NeighborFinder neighbors;
	private StyleUI style = new StyleUI();

	
	// 0 is empty, 1 is shark, 2 is fish
	/**
	 * Parameterized constructor for this class. Parameters are passed into the superclass constructor.
	 * @param cellNumberHorizontal
	 * @param cellNumberVertical
	 * @param emptyPercentage
	 * @param redToBlueRatio
	 * @param maxStarveDaysForSharks
	 * @param minBreedDaysForSharks
	 * @param minBreedDaysForFish
	 */

	public SimulationWaTor( int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage,double redToBlueRatio,
			int maxStarveDaysForSharks, int minBreedDaysForSharks, int minBreedDaysForFish) {
		// set up instance variables, put 0s in every cell
		super(cellNumberHorizontal, cellNumberVertical, emptyPercentage, redToBlueRatio);
		specificSetUp(
				maxStarveDaysForSharks,
				minBreedDaysForSharks,
				minBreedDaysForFish);
	   initializeScene(neighbors);
		updateColors();
	}
	
	/**
	 * Parameterized constructor that accepts as a parameter a 2D array of states, which allows the states
	 * of the grid to be set to predetermined values specified in the XML.
	 * @param cellNumberHorizontal
	 * @param cellNumberVertical
	 * @param specificLocation
	 * @param maxStarveDaysForSharks
	 * @param minBreedDaysForSharks
	 * @param minBreedDaysForFish
	 */
	
	public SimulationWaTor(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation, int maxStarveDaysForSharks,
			int minBreedDaysForSharks,int minBreedDaysForFish) {
		super(cellNumberHorizontal,cellNumberVertical,specificLocation);
		specificSetUp(
				maxStarveDaysForSharks,
				minBreedDaysForSharks,
				minBreedDaysForFish);
		super.initializeScene2(neighbors);
		updateColors();
	}
	/**
	 * Set up an empty array and set up some instance variables (common set up in both constructors)
	 * @param maxStarveDaysForSharks
	 * @param minBreedDaysForSharks
	 * @param minBreedDaysForFish
	 */

	public void specificSetUp(int maxStarveDaysForSharks, int minBreedDaysForSharks, int minBreedDaysForFish) {
		this.maxStarveDaysForSharks = maxStarveDaysForSharks;
		this.minBreedDaysForSharks = minBreedDaysForSharks;
		this.minBreedDaysForFish = minBreedDaysForFish;
		setArray(new CellWaTor[getCellNumberHorizontal()][getCellNumberVertical()]);
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				getArray()[rowNumber][columnNumber] = new CellWaTor(0, null, null, rowNumber, columnNumber);
			}
		}
		if(style.gridShape().equals("Triangle"))
			neighbors = new TriangleNeighborFinder(getArray(), 0, 0, style.getGridEdge());
		else
			neighbors = new FourNeighborFinder(getArray(), 0, 0, style.getGridEdge());
	}
	
	/**
	 * choose color according to states
	 */
	
	@Override
	protected Color chooseColor(int state) {
		Color color = null;
		if(state == 0)
			color = style.emptyColor();
		if(state == 1){
			color = Color.RED;
		}
		if(state == 2)
			color = Color.GREEN;
		return color;
	}
	/**
	 * update 2D array according to specific logic of die, breed, eat
	 */

	public void update() {
		// set up a loop, go through every cell
		//1 is shark, 2 is fish
	
		//finish all sharks, eat/breed/die, checked correct
		List<Cell> allSharks = findallType(1);	
		for (int sharkNumber = 0; sharkNumber < allSharks.size();sharkNumber++) {
			CellWaTor shark = (CellWaTor) allSharks.get(sharkNumber);
			List <Cell> allNeighborFish = Neighbortype(shark,2);
			List <Cell> allNeighborEmpty = Neighbortype(shark,0);
			//if there is fish, eat it
			if (allNeighborFish.size() > 0) {
				CellWaTor randomFish = findRandomNeighbor(allNeighborFish);
				sharkMove(shark,randomFish,SHARKEAT);
				shark = randomFish;
			}
			//no fish, move to an empty cell
			if(allNeighborFish.size() == 0 && allNeighborEmpty.size() > 0) {
				
				CellWaTor randomEmpty = findRandomNeighbor(allNeighborEmpty);
				sharkMove(shark,randomEmpty,SHARKSTARVE);
				shark = randomEmpty;
			}
			
			//cannot move, cannot eat
			if (allNeighborEmpty.size() == 0 && allNeighborFish.size() == 0) {
				shark.setBreedDays(shark.getBreedDays()+1);
				shark.setStarveDays(shark.getStarveDays()+1);
			}
			
			//check whether will die
			if (shark.getStarveDays() > maxStarveDaysForSharks) {	
				shark.changeState(0);
				shark.setBreedDays(0);
				shark.setStarveDays(0);
			}
			
			//check whether will breed
			if (shark.getBreedDays() >= minBreedDaysForSharks) {
				allNeighborEmpty = Neighbortype(shark, 0);
				if (allNeighborEmpty.size() > 0) {
				    CellWaTor potentialBreedCell = findRandomNeighbor(allNeighborEmpty);
				    potentialBreedCell.changeState(1);
				    shark.setBreedDays(0);
				}
			}
		}
		List<Cell> allFish = findallType(2);
		for (int fishNumber = 0; fishNumber < allFish.size();fishNumber++) {
			CellWaTor fish = (CellWaTor) allFish.get(fishNumber);
			List <Cell> allNeighborEmpty = Neighbortype(fish, 0);
			//fish move
			if (allNeighborEmpty.size() > 0) {
			    CellWaTor randomEmpty = findRandomNeighbor(allNeighborEmpty);
			    fishMove(fish, randomEmpty);
			    fish = randomEmpty;
			}
			//fish not move, but still breed days increase
			if (allNeighborEmpty.size() == 0) {
				fish.setBreedDays(fish.getBreedDays() + 1);
			}
			//check whether breed
			if (fish.getBreedDays() >= minBreedDaysForFish) {
				allNeighborEmpty = Neighbortype(fish, 0);
				if (allNeighborEmpty.size() > 0) {
				    CellWaTor potentialBreedCell = findRandomNeighbor(allNeighborEmpty);
				    potentialBreedCell.changeState(2);
				    fish.setBreedDays(0);
				}	
			}
		}
		updateColors();	
		super.count(1,2,0);
	}
	
	/**
	 * a fish will move to a random empty cell
	 * @param fish
	 * @param randomEmpty
	 */

	private void fishMove(CellWaTor fish,CellWaTor randomEmpty) {
		randomEmpty.changeState(2);
		randomEmpty.setColor(chooseColor(2));
		randomEmpty.setBreedDays(fish.getBreedDays()+1);
		fish.changeState(0);
		fish.setBreedDays(0);
		fish.setColor(chooseColor(0));
	}
	
	/**
	 * find a particular random neighbor among all neighbors of a cell
	 * @param allNeighbor
	 * @return
	 */

	public CellWaTor findRandomNeighbor(List<Cell> allNeighbor) {
		int randomIndex = random(1,allNeighbor.size())[0];
		CellWaTor random = (CellWaTor) allNeighbor.get(randomIndex);
		return random;
	}
	
	/**
	 * shark moves to a random cell, either fish (in this case eat the fish) or empty (in this case just move)
	 * @param shark
	 * @param randomSlot
	 * @param code
	 */
	
	public void sharkMove(CellWaTor shark, CellWaTor randomSlot, String code) {
		randomSlot.changeState(1);
		randomSlot.setColor(chooseColor(1));
		randomSlot.setBreedDays(shark.getBreedDays() + 1);
		if (code.equals(SHARKEAT)) {
			randomSlot.setStarveDays(0);	
		}
		if (code.equals(SHARKSTARVE)) {
			randomSlot.setStarveDays(shark.getStarveDays() + 1);	
		}
		shark.changeState(0);
		shark.setBreedDays(0);
		shark.setStarveDays(0);
		shark.setColor(chooseColor(0));
	}

	private List<Cell> findallType(int state) {
		List<Cell> allSharks = new ArrayList<Cell>();
		for (int rowNumber = 0; rowNumber < getCellNumberHorizontal(); rowNumber++) {
			for (int columnNumber = 0; columnNumber < getCellNumberVertical(); columnNumber++) {
				CellWaTor cell = (CellWaTor) super.getArray()[rowNumber][columnNumber];
				if (cell.getState() == state) {
					allSharks.add(cell);	
				}
			}
		}
		return allSharks;
	}

	private List<Cell> Neighbortype(CellWaTor cell, int type) {
		List<Cell> neighbors = cell.getNeighborCells();
		List<Cell> neighborFish = new ArrayList<Cell>();
		for (int i = 0;i<neighbors.size();i++) {
			if (neighbors.get(i).getState() == type) {
				neighborFish.add(neighbors.get(i));
			}
		}
		return neighborFish;
	}
	
	public int getStarveDays() {
		return maxStarveDaysForSharks;
	}
	
	public int getSharkBreed() {
		return minBreedDaysForSharks;
	}
	
	public int getFishBreed() {
		return minBreedDaysForFish;
	}

	


}
