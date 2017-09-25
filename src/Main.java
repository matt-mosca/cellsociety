/*
 * Author: Venkat
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import frontend.SimDisplay;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	public static final int SIZEx = 700;
	public static final int SIZEy = 700;
	public static final String INITIAL_TITLE = "CellSociety!";
	
	
	
	Stage window;
	Scene myScene;
	SimDisplay s;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		s = new SimDisplay(SIZEx, SIZEy, window);
		window.setTitle(INITIAL_TITLE);
		myScene = getStartScene(s);
		window.setScene(myScene);
		window.show();
	}
	
	
//	public Scene getSimulation(SimDisplay sim) {
//		return sim.makeSimulation();
//	}
	
	public Scene getStartScene(SimDisplay sim) {
		return sim.startScreen();
	}
	
//	public void step() {
//		s.getSimulation().update();
//	}
//	
//	public void playSim() {
//		
//	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
