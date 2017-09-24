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
	public static final int SIZEx = 900;
	public static final int SIZEy = 900;
	
	
	Stage window;
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		SimDisplay s = new SimDisplay(SIZEx, SIZEy);
		
	}
	
	
	public Scene getSimulation(SimDisplay sim) {
		return sim.makeSimulation();
	}
	
	public void step() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
