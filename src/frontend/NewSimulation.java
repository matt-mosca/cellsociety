package frontend;

import javafx.scene.control.Button;
import javafx.stage.Stage;

/* NewSimulation.java
 * @author Venkat Subramaniam
 * Very small class that basically allows you to create a new window and instance of the simDisplay class.
 * @version 10.04.17
 */
public class NewSimulation {
	private static final String FILENAME = "English_Text";
	private Stage window;
	private SimDisplay display;
	private static final int SIZEx = 750;
	private static final int SIZEy = 750;
	private static final String INITIAL_TITLE = "Another Cell Society!";
	
	/*
	 * Constructor for this class. The parameter is set to one of the instance variables for this class.
	 * @param stage 
	 */
	public NewSimulation(Stage stage){
		window = stage;
		display = new SimDisplay(SIZEx, SIZEy, stage, FILENAME);
		window.setScene(display.startScreen());
		window.show();
	}
	
}
