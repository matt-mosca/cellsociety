/*
 * Author: Venkat S.
 */
package frontend;

//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Scanner;

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
import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.ImagePattern;
//import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.*;


public class SimDisplay {
	private static final int VBOX_SPACING = 7;
	private static final int MILLISECOND_DELAY = 1000;
	private static final int GRID_FIT_CONSTANT = 650;
	private static final String WATOR_TITLE = "WaTor!";
	private static final String FIRE_TITLE  = "FIYAH";
	private static final String SEGREGATION_TITLE = "SEGREGATION";
	private static final String GAME_OF_LIFE_TITLE = "GAME OF LIFE";
	
	private Scene scene;
	private Cell[][] Cells;
	private int width;
	private int height;
	private GridPane myGrid;
	private ImageView[][] Images;
	private String simName;
	private Stage window;
	private Simulation sim;
	private UserInput UI = new UserInput();
	double[] inputArray;
	private Timeline animation;
	
	public SimDisplay(int x, int y, Stage s) {
		this.width=x;
		this.height=y;
		this.window = s;
	}
	
	private Scene makeSimulation(){
		BorderPane border = new BorderPane();
		
		this.Cells = sim.getArray();
		Images = makeImageArray(Cells);
		makeGrid();
		fillGrid();
		Scene fun = new Scene(border, width, height);
		border.setCenter(myGrid);
		myGrid.setAlignment(Pos.CENTER);
		//This could use some serious refactoring, potentially creating a new class for just all of the buttons seems
		//to make sense.
		HBox controls = new HBox(VBOX_SPACING);
		Button play = playButton();
		Button pause = pauseButton();
		Button step = stepButton();
		Button reset = resetButton();
		controls.getChildren().addAll(play, pause, step, reset);
		border.setBottom(controls);
		controls.setAlignment(Pos.CENTER);
//		this.scene = fun;
//		window.setScene(scene);
		return fun;
		//return this.scene;
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
		
		Font f = new Font("Arial", 50);
		Label startMessage = new Label("Which Simulation would you like to see?");
		startMessage.setFont(f);
		Button b1 = chooseScene("WaTor");
		Button b2 = chooseScene("Fire");
		Button b3 = chooseScene("Segregation");
		Button b4 = chooseScene("Game of Life");
		layout.getChildren().addAll(startMessage, b1,b2,b3,b4);
		this.scene = startScene;
		return this.scene;
	}
	
	
	private Button chooseScene(String s){
		Button b = new Button(s);
		b.setPrefSize(100, 50);
		b.setOnAction(e -> {
			//shouldn't this call the XML reader and start passing information to the backend?
			//I think that it should definitely do that. 
			if(s.equals("WaTor")) {
				inputArray = UI.getWaTor();
				this.sim = new SimulationWaTor((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], (int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
				changeSimName(WATOR_TITLE);
			}
			if(s.equals("Fire")) {
				inputArray = UI.getFire();
				this.sim = new SimulationFire((int) inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
				changeSimName(FIRE_TITLE);
			}
			if(s.equals("Segregation")) {
				inputArray = UI.getSegregation();
				this.sim = new SimulationSegregation((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
				changeSimName(SEGREGATION_TITLE);
			}
			if(s.equals("Game of Life")) {
				inputArray = UI.getGameOfLife();
				this.sim = new SimulationGameOfLife((int)inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3]);
				changeSimName(GAME_OF_LIFE_TITLE);
			}
//			makeSimulation();
			playSim();
		});
		return b;
	}
	
	
	private Button pauseButton() {
		Button b = new Button("Pause");
		b.setOnAction(e -> {
			//do the pause stuff by pausing the animation I guess
			animation.pause();
		});
		return b;
	}
	
	private Button playButton() {
		Button b = new Button("Play");
		b.setOnAction(e ->{
			//do the play stuff by resuming the animation, starting the animation, idk.
			animation.play();
		});
		return b;
	}
	
	private Button stepButton() {
		Button b = new Button("Step");
		b.setOnAction(e ->{
			//do the step stuff here by calling the step function wtf idk
			animation.pause();
			step();
		});
		return b;
	}
	
	private Button resetButton() {
		Button b = new Button("Reset");
		b.setOnAction(e-> {
			//do the reset stuff here
			
		});
		return b;
	}
	
	private GridPane makeGrid(){
		GridPane grid = new GridPane();
//		grid.getColumnConstraints().add(new ColumnConstraints(width/Cells[0].length));
//		grid.getRowConstraints().add(new RowConstraints(height/Cells.length));
		grid.setPrefHeight(GRID_FIT_CONSTANT);
		grid.setPrefWidth(GRID_FIT_CONSTANT);
		grid.getColumnConstraints().add(new ColumnConstraints(GRID_FIT_CONSTANT / Cells[0].length));
		grid.getRowConstraints().add(new RowConstraints(GRID_FIT_CONSTANT / Cells.length));
//		grid.setGridLinesVisible(true);
//		grid.setStyle("-fx-background-color: black; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
//		grid.setSnapToPixel(false);
		myGrid=grid;
		return myGrid;
	}
	
	
	
	private void fillGrid() {
		myGrid.getChildren().clear();
		for(int i=0;i<Cells.length;i++) {
			for (int j=0; j<Cells[i].length; j++) {
				myGrid.add(Images[i][j], j, i);
//				Images[i][j].fitWidthProperty().bind(myGrid.widthProperty());
//				Images[i][j].fitHeightProperty().bind(myGrid.heightProperty());
			}
		}
	}
	
	
//UNCOMMENT THIS IF YOU END UP NEEDING TO HAVE AN IMAGEVIEW ARRAY
//Looks like we're using an ImageView array. Seems to make sense.
	
	private ImageView[][] makeImageArray(Cell[][] cells){
		ImageView[][] images = new ImageView[cells.length][Cells[0].length];
		for(int i=0; i<cells.length; i++) {
			for (int j=0; j<cells[i].length; j++) {
				images[i][j] = new ImageView(cells[i][j].getImage());
				images[i][j].setFitWidth(GRID_FIT_CONSTANT / Cells[0].length);
				images[i][j].setFitHeight(GRID_FIT_CONSTANT / Cells.length);
			}
		}
		this.Images = images;
		return this.Images;
	}
	
	public void changeSimName(String s) {
		this.simName = s + "!";
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
		Cells = sim.getArray(); //I can't help but feel that this is stupidly inefficient. Is there an easier way? - V 
		//I don't think you guys are updating this array in the backend, because I'm not getting an animation. 
		updateImageArray(Cells);
		fillGrid();
	}
	
	private void updateImageArray(Cell[][] cells) {
		for (int i=0; i<cells.length; i++) {
			for(int j=0; j<cells[i].length; j++) {
				this.Images[i][j].setImage(cells[i][j].getImage()); //the n^2 algo is really starting to make me sad, but I'm not sure how to get it to be faster. Suggestions? -V
			}
		}
		
	}

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
		animation.play();
	}
	
	
}
