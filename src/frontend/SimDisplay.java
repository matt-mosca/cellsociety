/*
 * Author: Venkat S.
 */
package frontend;

//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Scanner;
import java.util.ResourceBundle;

import backend.Cell;
import backend.Simulation;
import backend.SimulationFire;
import backend.SimulationGameOfLife;
import backend.SimulationSegregation;
import backend.SimulationWaTor;

//import java.io.File;
//import java.io.FileNotFoundException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
//import javafx.application.Application;
//import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
//import javafx.scene.paint.ImagePattern;
//import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.*;


public class SimDisplay {
	private static final String GAMEOFLIFETITLE = "Game of Life";
	private static final String SEGREGATIONTITLE = "Segregation";
	private static final String FIRETITLE = "Fire";
	private static final String WATORTITLE = "WaTor";
	private static final int VBOX_SPACING = 7;
	private static final int MILLISECOND_DELAY = 1000;
	private static final int GRID_FIT_CONSTANT = 650;
	private static final String WATOR_TITLE = "WaTor!";
	private static final String FIRE_TITLE  = "FIYAH!";
	private static final String SEGREGATION_TITLE = "SEGREGATION!";
	private static final String GAME_OF_LIFE_TITLE = "GAME OF LIFE!";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	private Scene scene;
//	private Cell[][] Cells;
	private int width;
	private int height;
//	private GridPane myGrid;
//	private Rectangle[][] Rectangles;
	private String simName;
	private Stage window;
	private Simulation sim;
	private UserInput UI = new UserInput();
	private double[] inputArray;
	private Timeline animation;
	private BorderPane border;
	private ResourceBundle myResources;
	private GridDisplay myGrid;
	//for save scene
	private UserSaveSimulation saveUI=new UserSaveSimulation();
	private Cell[][] savedCells;
	
	
	public SimDisplay(int x, int y, Stage s, String filename) {
		this.width=x;
		this.height=y;
		this.window = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + filename);
	}
	
	private Scene makeSimulation(){
		border = new BorderPane();
		myGrid = new GridDisplay(sim);
//		this.Cells = sim.getArray();
//		Rectangles = makeRectangleArray(Cells);
//		makeGrid();
//		fillGrid();
		Scene fun = new Scene(border, width, height);
		border.setCenter(myGrid.getGrid());
//		myGrid.setAlignment(Pos.CENTER);
		//This could use some serious refactoring, potentially creating a new class for just all of the buttons seems
		//to make sense.
		HBox controls = new HBox(VBOX_SPACING);
		Button play = playButton();
		Button pause = pauseButton();
		Button step = stepButton();
		Button reset = resetButton();
		Button save=saveButton();
		Button resume=resumeButton();
		controls.getChildren().addAll(play, pause, step, reset,save,resume);
		border.setBottom(controls);
		controls.setAlignment(Pos.CENTER);
		return fun;
	}
	
	
	public Scene startScreen() {
		VBox layout = new VBox(VBOX_SPACING);
		Scene startScene= new Scene(layout, width, height);
		layout.setAlignment(Pos.CENTER);
		
		//Change the image here depending on the kind of image that we want.
//		Image backGround = new Image(getClass().getClassLoader().getResourceAsStream("brickwall.jpeg"));
//		ImagePattern pattern = new ImagePattern(backGround);	
//		startScene.setFill(pattern);
		//get some buttons to choose which of the four simulations they want to do
		//replace the following code with a for loop and a resource file.
		
		Font f = new Font("Arial", 30);
		Label startMessage = new Label(myResources.getString("simprompt"));
		startMessage.setFont(f);
		Button b1 = chooseScene(WATORTITLE);
		Button b2 = chooseScene(FIRETITLE);
		Button b3 = chooseScene(SEGREGATIONTITLE);
		Button b4 = chooseScene(GAMEOFLIFETITLE);
		layout.getChildren().addAll(startMessage, b1,b2,b3,b4);
		this.scene = startScene;
		return this.scene;
	}
	
	
	private Button chooseScene(String s){
		Button b = new Button(s);
		b.setPrefSize(100, 50);
		b.setOnAction(e -> {
			inputArray = UI.getArray(s);
			if(s.equals(WATORTITLE)) {
				this.sim = new SimulationWaTor((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], (int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
				changeSimName(WATOR_TITLE);
			}
			if(s.equals(FIRETITLE)) {
				this.sim = new SimulationFire((int) inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
				changeSimName(FIRE_TITLE);
			}
			if(s.equals(SEGREGATIONTITLE)) {
				this.sim = new SimulationSegregation((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
				changeSimName(SEGREGATION_TITLE);
			}
			if(s.equals(GAMEOFLIFETITLE)) {
				this.sim = new SimulationGameOfLife((int)inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3]);
				changeSimName(GAME_OF_LIFE_TITLE);
			}
			playSim();
		});
		return b;
	}
	
	private Button saveButton() {
		Button b = new Button(myResources.getString("savebutton"));
		b.setOnAction(e -> {
			animation.pause();
//			saveUI.save(Cells,simName);
			saveUI.save(sim.getArray(), simName);
		
			
			
		});
		return b;
		
	}
	
	private Button resumeButton() {
		Button b = new Button(myResources.getString("resumebutton"));
		b.setOnAction(e -> {
			animation.pause();
			int[][] resumedArray=saveUI.getBack();
			System.out.println(resumedArray);
			if(simName.equals(GAME_OF_LIFE_TITLE)) {
				this.sim = new SimulationGameOfLife(resumedArray.length,resumedArray[0].length,resumedArray);
			}
			
			if(simName.equals(FIRE_TITLE)) {
				this.sim = new SimulationFire(resumedArray.length,resumedArray[0].length,resumedArray,inputArray[4]);
			}
			if(simName.equals(SEGREGATION_TITLE)) {
				this.sim = new SimulationSegregation(resumedArray.length,resumedArray[0].length,resumedArray,inputArray[4]);
			}
			if(simName.equalsIgnoreCase(WATOR_TITLE)) {
				this.sim = new SimulationWaTor(resumedArray.length,resumedArray[0].length,resumedArray,(int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
			}
			
			
			
			playSim();
			
			
			
		});
		return b;
		
	}
	
	private Button pauseButton() {
		Button b = new Button(myResources.getString("pausebutton"));
		b.setOnAction(e -> {
			animation.pause();
		});
		return b;
	}
	
	private Button playButton() {
		Button b = new Button(myResources.getString("playbutton"));
		b.setOnAction(e ->{
			animation.play();
		});
		return b;
	}
	
	private Button stepButton() {
		Button b = new Button(myResources.getString("stepbutton"));
		b.setOnAction(e ->{
			animation.pause();
			step();
		});
		return b;
	}
	
	private Button resetButton() {
		Button b = new Button(myResources.getString("resetbutton"));
		b.setOnAction(e-> {
			animation.pause();
			if(simName.equals(GAME_OF_LIFE_TITLE)) {
				this.sim = new SimulationGameOfLife((int)inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3]);
			}
			if(simName.equals(FIRE_TITLE)) {
				this.sim = new SimulationFire((int) inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
			}
			if(simName.equals(SEGREGATION_TITLE)) {
				this.sim = new SimulationSegregation((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
			}
			if(simName.equals(WATOR_TITLE)) {
				this.sim = new SimulationWaTor((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], (int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
			}
			playSim();
		});
		return b;
	}
	
//	private GridPane makeGrid(){
//		GridPane grid = new GridPane();
//		grid.setPrefHeight(GRID_FIT_CONSTANT);
//		grid.setPrefWidth(GRID_FIT_CONSTANT);
//		grid.getColumnConstraints().add(new ColumnConstraints(GRID_FIT_CONSTANT / Cells[0].length));
//		grid.getRowConstraints().add(new RowConstraints(GRID_FIT_CONSTANT / Cells.length));
//		myGrid=grid;
//		return myGrid;
//	}
//
//	
//	
//	
//	private void fillGrid() {
//		myGrid.getChildren().clear();
//		for(int i=0;i<Cells.length;i++) {
//			for (int j=0; j<Cells[i].length; j++) {
//				myGrid.add(Rectangles[i][j], j, i);
//			}
//		}
//	}

//	private Rectangle[][] makeRectangleArray(Cell[][] cells) {
//		Rectangle[][] recs = new Rectangle[cells.length][Cells[0].length];
//		for(int i=0; i<cells.length; i++) {
//			for(int j=0; j<cells[i].length; j++) {
//				recs[i][j] = new Rectangle();
//				recs[i][j].setStroke(Color.BLACK);
//				recs[i][j].setFill(cells[i][j].getColor());
//				recs[i][j].setWidth(GRID_FIT_CONSTANT / Cells[0].length);
//				recs[i][j].setHeight(GRID_FIT_CONSTANT / Cells.length);
//			}
//		}
//		this.Rectangles = recs;
//		return this.Rectangles;
//	}

	
	public void changeSimName(String s) {
		this.simName = s;
		window.setTitle(simName);
	}
	
	public Simulation getSimulation() {
		return this.sim;
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	private void step() {
		sim.update();
//		Cells = sim.getArray(); //I can't help but feel that this is stupidly inefficient. Is there an easier way? - V 
//		//I don't think you guys are updating this array in the backend, because I'm not getting an animation. 
//		updateColorArray(Cells);
//		fillGrid();
		myGrid.update();
	}
	
//	private void updateColorArray(Cell[][] cells) {
//		for (int i=0; i<cells.length; i++) {
//			for(int j=0; j<cells[i].length; j++) {
//				this.Rectangles[i][j].setFill(cells[i][j].getColor()); //the n^2 algo is really starting to make me sad, but I'm not sure how to get it to be faster. Suggestions? -V
//				
//			}
//		}
//		
//	}

	private void playSim() {
		this.scene = makeSimulation();
		window.setScene(scene);
		createAnimation();
	}

	private void createAnimation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
		animation = new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
//		animation.play();
	}
	
	
}
