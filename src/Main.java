/*
 * Author: Venkat
 */

import frontend.SimDisplay;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	private static final int SIZEx = 800;
	private static final int SIZEy = 800;
	private static final String INITIAL_TITLE = "CellSociety!";
	
	
	
	private Stage window;
	private Scene myScene;
	private SimDisplay s;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		s = new SimDisplay(SIZEx, SIZEy, window);
		window.setTitle(INITIAL_TITLE);
		myScene = getStartScene(s);
		window.setScene(myScene);
		window.show();
	}
	
	public Scene getStartScene(SimDisplay sim) {
		return sim.startScreen();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
