/**
 * Main.java
 * @author Venkat Subramaniam
 * Main class for simulation-running program. The program is initiated through this class.
 * @version 10.01.17
 */

import frontend.SimDisplay;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	private static final String FILENAME = "English_Text";
	private static final int SIZEx = 750;
	private static final int SIZEy = 750;
	private static final String INITIAL_TITLE = "CellSociety!";
	
	
	private Stage window;
	private Scene myScene;
	private SimDisplay s;
	
	/**
	 * Initializes stage and scene and calls methods to display and launch simulation.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		s = new SimDisplay(SIZEx, SIZEy, window, FILENAME);
		window.setTitle(INITIAL_TITLE);
		myScene = s.startScreen();
		window.setScene(myScene);
		window.show();
	}
	
//	public Scene getStartScene(SimDisplay sim) {
//		return sim.startScreen();
//	}
	
	/**
	 * Main method for class Main.java.
	 * @param args
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
