package frontend;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewSimulation {
	private static final String FILENAME = "English_Text";
	private Stage window;
	private SimDisplay display;
	private static final int SIZEx = 750;
	private static final int SIZEy = 750;
	private static final String INITIAL_TITLE = "Another Cell Society!";
	
	public NewSimulation(Stage s){
		window = s;
		display = new SimDisplay(SIZEx, SIZEy, s, FILENAME);
		window.setScene(display.getStartScene());
		window.show();
	}
	
}
