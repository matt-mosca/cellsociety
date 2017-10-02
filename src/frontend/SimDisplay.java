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
import backend.SimulationRPS;
import backend.SimulationSegregation;
import backend.SimulationWaTor;

//import java.io.File;
//import java.io.FileNotFoundException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import javafx.application.Application;
//import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
	private static final String RPSTITLE = "Rock Paper Scissors";
	private static final int VBOX_SPACING = 7;
	private static final int MILLISECOND_DELAY = 1000;
	private static final String WATOR_TITLE = "WaTor!";
	private static final String FIRE_TITLE  = "FIYAH!";
	private static final String SEGREGATION_TITLE = "SEGREGATION!";
	private static final String GAME_OF_LIFE_TITLE = "GAME OF LIFE!";
	private static final String RPS_TITLE = "ROCK PAPER SCISSORS!";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String SPEED_LABEL = "Speed";
	
	private Scene scene;
	private int width;
	private int height;
	private String simName;
	private Stage window;
	private Simulation sim;
	private UserInput UI = new UserInput();
	private double[] inputArray;
	private Timeline animation;
	private BorderPane border;
	private ResourceBundle myResources;
	private GridDisplay myGrid;
	private Graph graph;
	//for save scene
	private UserSaveSimulation saveUI=new UserSaveSimulation();

	
	//for extension: set up the scene using fixed locations
	
	
	
	public SimDisplay(int x, int y, Stage s, String filename) {
		this.width=x;
		this.height=y;
		this.window = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + filename);
	}
	
//	public String getSimName() {
//		return simName;
//	}
	
	private Scene makeSimulation(){
		border = new BorderPane();
		myGrid = new GridDisplay(sim);
		graph = new Graph(sim, myResources, simName);
		Scene fun = new Scene(border, width, height);
		border.setCenter(myGrid.getGrid());
		border.setTop(graph.getGraph());
		//This could use some serious refactoring, potentially creating a new class for just all of the buttons seems
		//to make sense.
		HBox controls = new HBox(VBOX_SPACING);
		Button play = playButton();
		Button pause = pauseButton();
		Button step = stepButton();
		Button reset = resetButton();
		Button save=saveButton();
		Button resume=resumeButton();
		Button switcher=switchSim();
		controls.getChildren().addAll(play, pause, step, reset,save,resume, switcher);
		
		GridPane sliders = new GridPane();
		sliders.setVgap(10);
        sliders.setHgap(20);
		Slider speed = speedSlider();
		GridPane.setConstraints(speed, 1,1);
		sliders.getChildren().addAll(speed);//have to add slider names
		
		sliders.setAlignment(Pos.CENTER);
		border.setRight(sliders);
	
		border.setBottom(controls);
		controls.setAlignment(Pos.CENTER);
		return fun;
	}
	
	
	private Scene startScreen() {
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
		Button b5 = chooseScene(RPSTITLE);
		layout.getChildren().addAll(startMessage, b1,b2,b3,b4,b5);
		this.scene = startScene;
		return this.scene;
	}
	
	public Scene getStartScene() {
		return startScreen();
	}
	
	
	
	private Button chooseScene(String s){
		Button b = new Button(s);
		b.setPrefSize(100, 50);
		b.setOnAction(e -> {
			inputArray = UI.getArray(s);
			if(s.equals(WATORTITLE)) {
				waTorConstruct();
				changeSimName(WATOR_TITLE);
			}
			if(s.equals(FIRETITLE)) {
				fireConstruct();
				changeSimName(FIRE_TITLE);
			}
			if(s.equals(SEGREGATIONTITLE)) {
				segregationConstruct();
				changeSimName(SEGREGATION_TITLE);
			}
			if(s.equals(GAMEOFLIFETITLE)) {
				gameOfLifeConstruct();
				changeSimName(GAME_OF_LIFE_TITLE);
			}
			if(s.equals(RPSTITLE)) {
				RPSConstruct();
				changeSimName(RPS_TITLE);
			}
			playSim();
		});
		return b;
	}

	private void RPSConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationRPS((int)inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3]);
		}
		else {
			int[][] fixedInitial={{1,0,1,1,0},{1,0,1,0,0},{0,0,1,0,0},{1,0,1,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationRPS(fixedInitial.length,fixedInitial[0].length,fixedInitial);
		}
	}

	public void gameOfLifeConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationGameOfLife((int)inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3]);
		}
		else {
			int[][] fixedInitial={{1,0,1,1,0},{1,0,1,0,0},{0,0,1,0,0},{1,0,1,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationGameOfLife(fixedInitial.length,fixedInitial[0].length,fixedInitial);
		}
	}

	public void segregationConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationSegregation((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
		} else {
			int[][] fixedInitial={{1,0,1,1,0},{2,0,1,0,0},{0,0,1,0,0},{1,0,2,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationSegregation(fixedInitial.length,fixedInitial[0].length,fixedInitial, inputArray[4]);
		}
	}

	public void fireConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationFire((int) inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
		}else{
			int[][] fixedInitial={{1,0,1,1,0},{2,0,1,0,0},{0,0,1,0,0},{1,0,2,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationFire(fixedInitial.length,fixedInitial[0].length,fixedInitial, inputArray[4]);
		}
	}

	public void waTorConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationWaTor((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], (int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
		}else{
			int[][] fixedInitial={{1,0,1,1,0},{0,0,1,2,2},{1,0,2,0,0},{0,0,1,0,0}};
			this.sim = new SimulationWaTor(fixedInitial.length,fixedInitial[0].length,fixedInitial,(int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
		}
	}
	
	private Button switchSim() {
		Button b = new Button(myResources.getString("switchbutton"));
		b.setOnAction(e ->{
			animation.pause();
			getStartScene();
			window.setScene(scene);
			});
		return b;	
		
	}
	
	private Button saveButton() {
		Button b = new Button(myResources.getString("savebutton"));
		b.setOnAction(e -> {
			animation.pause();
			saveUI.save(sim.getArray(), simName);	
		});
		return b;
		
	}
	
	private Button resumeButton() {
		Button b = new Button(myResources.getString("resumebutton"));
		b.setOnAction(e -> {
			animation.pause();
			int[][] resumedArray=saveUI.getBack();
			
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
//			if(simName.equals(RPS_TITLE)) {
//				this.sim = new SimulationRPS(resumedArray.length, resumedArray[0].length, resumedArray)
//			}
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
	
				gameOfLifeConstruct();
			}
			if(simName.equals(FIRE_TITLE)) {
				fireConstruct();
			}
			if(simName.equals(SEGREGATION_TITLE)) {
				segregationConstruct();
			}
			if(simName.equals(WATOR_TITLE)) {
				waTorConstruct();
				
			}
			if(simName.equals(RPS_TITLE)) {
				RPSConstruct();
			}
			playSim();
		});
		return b;
	}

	
	private void changeSimName(String s) {
		this.simName = s;
		window.setTitle(simName);
	}
	
//	public Simulation getSimulation() {
//		return this.sim;
//	}
//	
//	public Scene getScene() {
//		return this.scene;
//	}
	
	private void step() {
		sim.update();
		myGrid.update();
		graph.update();
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
		animation.setRate(5);
	}
	
//	private void changeSpeed(double speed) {
//		animation.pause();
//		animation.getKeyFrames().clear();
//		KeyFrame frame = new KeyFrame(Duration.millis(speed), e->step());
//		animation.getKeyFrames().add(frame);
//	}
	
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
