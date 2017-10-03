package frontend;

import backend.Simulation;
import backend.SimulationFire;
import backend.SimulationGameOfLife;
import backend.SimulationRPS;
import backend.SimulationSegregation;
import backend.SimulationWaTor;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class SimSlider {
	private GridPane layout = new GridPane();
	private static final String WATOR_TITLE = "WaTor!";
	private static final String FIRE_TITLE  = "FIYAH!";
	private static final String SEGREGATION_TITLE = "SEGREGATION!";
	private static final String GAME_OF_LIFE_TITLE = "GAME OF LIFE!";
	private static final String RPS_TITLE = "ROCK PAPER SCISSORS!";
	private double emptyPercentage;
	private double redToBlueRatio;
	private int cellNumberHorizontal;
	private int cellNumberVertical;
	private double probCatch;
	private int maxStarveDaysForSharks;
	private int minBreedDaysForSharks;
	private int minBreedDaysForFish;
	private double satisfactionPercentage;
//	Slider myRatioSlider;
//	Slider mySpeedSlider;
//	Slider myEmptySlider;
//	Slider mySizeSlider;
	private SimDisplay display;
	private Simulation sim;
	private Timeline animation;
	
	public SimSlider(SimDisplay s) {
		display = s;
		sim = s.getSimulation();
		animation = s.getAnimation();
		String name = s.getSimName();
		cellNumberHorizontal = sim.getCellNumberHorizontal();
		cellNumberVertical = sim.getCellNumberVertical();
		if (name.equals(FIRE_TITLE)) {
			probCatch =  ((SimulationFire) sim).getProbCatch();
		}
		if (name.equals(SEGREGATION_TITLE)) {
			satisfactionPercentage = ((SimulationSegregation) sim).getSatisfactionPercentage();
		}
		if (name.equals(WATOR_TITLE)) {
			maxStarveDaysForSharks = ((SimulationWaTor) sim).getStarveDays();
			minBreedDaysForSharks = ((SimulationWaTor) sim).getSharkBreed();
			minBreedDaysForFish = ((SimulationWaTor) sim).getFishBreed();
		}
		Slider s1 = speedSlider();
		Label l1 = new Label("Speed");

		Slider s2 = ratioSlider();
		Label l2 = new Label("Ratio");
		Slider s3 = EmptySlider();
		Label l3 = new Label("Empty");
		Slider s4 = SizeSlider();
		Label l4 = new Label("Size");
		layout.setVgap(10);
        layout.setHgap(20);
        GridPane.setConstraints(s1, 1, 1);
        GridPane.setConstraints(l1, 0, 1);
        GridPane.setConstraints(s2, 1, 2);
        GridPane.setConstraints(l2, 0, 2);
        GridPane.setConstraints(s3, 1, 3);
        GridPane.setConstraints(l3, 0, 3);
        GridPane.setConstraints(s4, 1, 4);
        GridPane.setConstraints(l4, 0, 4);
        layout.getChildren().addAll(s1,s2,s3,s4,l1,l2,l3,l4);
	}
	
	private Slider speedSlider() {
		Slider s = new Slider(0,10,5);
		s.setShowTickLabels(true);
		s.setShowTickMarks(true);
		s.setMajorTickUnit(1);
		s.setMinorTickCount(0);
		s.setBlockIncrement(1);
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	animation.setRate(new_val.doubleValue());
            }	
            });
		return s;
	}
	
	private Slider ratioSlider() {
		Slider s = new Slider(0.0, 1.0, 0.5);
		s.setShowTickLabels(true);
		s.setShowTickMarks(true);
		s.setMajorTickUnit(0.1);
		s.setMinorTickCount(10);
		s.setBlockIncrement(0.1);
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            		animation.pause();
            		if(display.getSimName().equals(FIRE_TITLE)) {
            			sim= new SimulationFire(cellNumberHorizontal, cellNumberVertical, emptyPercentage, new_val.doubleValue(), probCatch );
            		}
            		if(display.getSimName().equals(WATOR_TITLE)){
            			sim= new SimulationWaTor(cellNumberHorizontal, cellNumberVertical, emptyPercentage, new_val.doubleValue(), maxStarveDaysForSharks, minBreedDaysForSharks, minBreedDaysForFish);
            		}
            		if(display.getSimName().equals(SEGREGATION_TITLE)) {
            			sim= new SimulationSegregation(cellNumberHorizontal, cellNumberVertical, emptyPercentage, new_val.doubleValue(), satisfactionPercentage);
            		}
            		if(display.getSimName().equals(GAME_OF_LIFE_TITLE)) {
            			sim= new SimulationGameOfLife(cellNumberHorizontal, cellNumberVertical, emptyPercentage, new_val.doubleValue());
            		}
            		if(display.getSimName().equals(RPS_TITLE)) {
            			sim= new SimulationRPS(cellNumberHorizontal, cellNumberVertical, emptyPercentage, new_val.doubleValue());
            		}
            		redToBlueRatio = new_val.doubleValue();
            		display.setSimulation(sim);
            		display.playSim();
            }	
            });
		return s;
	}
	
	private Slider EmptySlider() {
		Slider s = new Slider(0.0, 1.0, 0.5);
		s.setShowTickLabels(true);
		s.setShowTickMarks(true);
		s.setMajorTickUnit(0.1);
		s.setMinorTickCount(10);
		s.setBlockIncrement(0.01);
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            		animation.pause();
            	if(display.getSimName().equals(FIRE_TITLE)) {
            			sim= new SimulationFire(cellNumberHorizontal, cellNumberVertical, new_val.doubleValue(), redToBlueRatio, probCatch );
            		}
            		if(display.getSimName().equals(WATOR_TITLE)){
            			sim= new SimulationWaTor(cellNumberHorizontal, cellNumberVertical, new_val.doubleValue(), redToBlueRatio, maxStarveDaysForSharks, minBreedDaysForSharks, minBreedDaysForFish);
            		}
            		if(display.getSimName().equals(SEGREGATION_TITLE)) {
            			sim= new SimulationSegregation(cellNumberHorizontal, cellNumberVertical, new_val.doubleValue(), redToBlueRatio, satisfactionPercentage);
            		}
            		if(display.getSimName().equals(GAME_OF_LIFE_TITLE)) {
            			sim= new SimulationGameOfLife(cellNumberHorizontal, cellNumberVertical, new_val.doubleValue(), redToBlueRatio);
            		}
            		if(display.getSimName().equals(RPS_TITLE)) {
            			sim= new SimulationRPS(cellNumberHorizontal, cellNumberVertical, new_val.doubleValue(), redToBlueRatio);
            		}
            		emptyPercentage = new_val.doubleValue();
            		display.setSimulation(sim);
            		display.playSim();
            }	
            });
		return s;
	}
	
	private Slider SizeSlider() {
		Slider s = new Slider(0.0, 100, 50);
		s.setShowTickLabels(true);
		s.setShowTickMarks(true);
		s.setMajorTickUnit(10);
		s.setMinorTickCount(10);
		s.setBlockIncrement(5);
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            		animation.pause();
            		if(display.getSimName().equals(FIRE_TITLE)) {
            			sim= new SimulationFire(new_val.intValue(), new_val.intValue(), emptyPercentage, redToBlueRatio, probCatch );
            		}
            		if(display.getSimName().equals(WATOR_TITLE)){
            			sim= new SimulationWaTor(new_val.intValue(), new_val.intValue(), emptyPercentage, redToBlueRatio, maxStarveDaysForSharks, minBreedDaysForSharks, minBreedDaysForFish);
            		}
            		if(display.getSimName().equals(SEGREGATION_TITLE)) {
            			sim= new SimulationSegregation(new_val.intValue(), new_val.intValue(), emptyPercentage, redToBlueRatio, satisfactionPercentage);
            		}
            		if(display.getSimName().equals(GAME_OF_LIFE_TITLE)) {
            			sim= new SimulationGameOfLife(new_val.intValue(), new_val.intValue(), emptyPercentage, redToBlueRatio);
            		}
            		if(display.getSimName().equals(RPS_TITLE)) {
            			sim= new SimulationRPS(new_val.intValue(), new_val.intValue(), emptyPercentage, redToBlueRatio);
            		}
            		cellNumberHorizontal = new_val.intValue();
            		cellNumberVertical = new_val.intValue();
            		display.setSimulation(sim);
            		display.playSim();
            }	
            });
		return s;
	}
	
	public GridPane getSliders() {
		return layout;
	}
	
}
