package frontend;

import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SimSlider {
	VBox layout = new VBox();
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
		s.setMajorTickUnit(5);
		s.setMinorTickCount(1);
		s.setBlockIncrement(1);
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            		animation.setRate(new_val.doubleValue());
            }	
            });
		return s;
	}
	
	
}
