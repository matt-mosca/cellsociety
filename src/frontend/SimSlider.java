package frontend;

import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SimSlider {
	VBox layout = new VBox();
	private static final String WATOR_TITLE = "WaTor!";
	private static final String FIRE_TITLE  = "FIYAH!";
	private static final String SEGREGATION_TITLE = "SEGREGATION!";
	private static final String GAME_OF_LIFE_TITLE = "GAME OF LIFE!";
	private static final String RPS_TITLE = "ROCK PAPER SCISSORS!";
//	Slider myRatioSlider;
//	Slider mySpeedSlider;
//	Slider myEmptySlider;
//	Slider mySizeSlider;
	SimDisplay sim;
	Timeline animation;
	public SimSlider(SimDisplay s) {
		sim = s;
		animation = s.getAnimation();
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
		s.setBlockIncrement(0.01);
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            		if(sim.getSimName().equals(FIRE_TITLE)) {
            			
            		}
            }	
            });
		return s;
	}
	
	
}
