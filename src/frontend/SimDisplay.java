package frontend;

import java.util.ResourceBundle;

import backend.Cell;
import backend.Simulation;
import backend.SimulationFire;
import backend.SimulationGameOfLife;
import backend.SimulationRPS;
import backend.SimulationSegregation;
import backend.SimulationWaTor;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.*;


/* SimDisplay.java
 * @author Venkat Subramaniam
 * Main GUI class for all simulations. Contains methods and variables that are needed to make,
 * interact with, and play any simulation.
 * @version 10.04.17
 */

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
	private SimSlider Sliders;
	private boolean first=true;
	private UserSaveSimulation saveUI=new UserSaveSimulation();

	
	
	/*
	 * Constructor for this class. The parameters are set to private instance variables for the class.
	 * @param x = the width of the required scene.
	 * @param y = the height of the required scene.
	 * @param s = the JavaFX stage upon which the scene is mounted.
	 * @param filename = the path to the resource file for the strings contained in this class.
	 */
	
	public SimDisplay(int x, int y, Stage s, String filename) {
		this.width=x;
		this.height=y;
		this.window = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + filename);
	}
	
	/*
	 * Returns the name of the simulation that is currently being played.
	 */
	public String getSimName() {
		return simName;
	}
	
	/*
	 * Returns a scene that contains the graph, the simulation, and all of the buttons to interact with the
	 * simulation.
	 */
	private Scene makeSimulation(){
		border = new BorderPane();
		myGrid = new GridDisplay(sim);
		
		graph = new Graph(sim, myResources, simName);
		Scene fun = new Scene(border, width, height);
		border.setCenter(myGrid.getGrid());
		border.setTop(graph.getGraph());
		HBox controls = new HBox(VBOX_SPACING);
		Button play = playButton();
		Button pause = pauseButton();
		Button step = stepButton();
		Button reset = resetButton();
		Button save=saveButton();
		Button resume=resumeButton();
		Button switcher=switchSim();
		Button another = newSim();
		controls.getChildren().addAll(play, pause, step, reset,save,resume, switcher, another);
		if (first) {
			Sliders = new SimSlider(this);
			first=false;
		}
		GridPane sliders = Sliders.getSliders();
		sliders.setAlignment(Pos.CENTER);
		border.setRight(sliders);
		border.setBottom(controls);
		controls.setAlignment(Pos.CENTER);
		return fun;
	}
	
	/*
	 * Creates and returns the initial scene which allows you to choose which simulation you would like to play.
	 */
	public Scene startScreen() {
		VBox layout = new VBox(VBOX_SPACING);
		Scene startScene= new Scene(layout, width, height);
		layout.setAlignment(Pos.CENTER);
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
	
	/*
	 * Depending on the string s that is input into this class, a button that, upon being pressed, will start 
	 * the process of playing a particular simulation will be returned.
	 * @param s
	 */
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

	/*
	 * This method, which is called by chooseScene, will get the required input for the RPS simulation from the 
	 * UserInput class, and will send that information to the backend for the simulation to be initialized.
	 */
	private void RPSConstruct() {
		
		if (UI.getType()==1) {
		
		    this.sim = new SimulationRPS((int)inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3]);
		
		}
		else {
			int[][] fixedInitial={{1,0,1,1,0},{1,0,1,0,0},{0,0,1,0,0},{1,0,1,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationRPS(fixedInitial.length,fixedInitial[0].length,fixedInitial);
		}
		
		
	}

	/*
	 * This method, which is called by chooseScene, will get the required input for the Game of Life simulation 
	 * from the UserInput class, and will send that information to the backend for the simulation to 
	 * be initialized.
	 */
	private void gameOfLifeConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationGameOfLife((int)inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3]);
		}
		else {
			int[][] fixedInitial={{1,0,1,1,0},{1,0,1,0,0},{0,0,1,0,0},{1,0,1,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationGameOfLife(fixedInitial.length,fixedInitial[0].length,fixedInitial);
		}
	}

	/*
	 * This method, which is called by chooseScene, will get the required input for the Segregation simulation 
	 * from the UserInput class, and will send that information to the backend for the simulation to 
	 * be initialized.
	 */
	
	private void segregationConstruct() {
		
		if (UI.getType()==1) {
		    this.sim = new SimulationSegregation((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], inputArray[4]);   
		} else {
			System.out.println(inputArray[4]);
			int[][] fixedInitial={{1,0,1,1,0},{2,0,1,0,0},{0,0,1,0,0},{1,0,2,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationSegregation(fixedInitial.length,fixedInitial[0].length,fixedInitial, inputArray[4]);
		}
	}

	/*
	 * This method, which is called by chooseScene, will get the required input for the Fire simulation 
	 * from the UserInput class, and will send that information to the backend for the simulation to 
	 * be initialized.
	 */
	
	private void fireConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationFire((int) inputArray[0], (int) inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
		}else{
			int[][] fixedInitial={{1,0,1,1,0},{2,0,1,0,0},{0,0,1,0,0},{1,0,2,0,0},{0,0,1,0,0},{1,1,1,1,1}};
			this.sim = new SimulationFire(fixedInitial.length,fixedInitial[0].length,fixedInitial, inputArray[4]);
		}
	}


	/*
	 * This method, which is called by chooseScene, will get the required input for the WaTor simulation 
	 * from the UserInput class, and will send that information to the backend for the simulation to 
	 * be initialized.
	 */
	
	private void waTorConstruct() {
		if (UI.getType()==1) {
		    this.sim = new SimulationWaTor((int)inputArray[0], (int)inputArray[1], inputArray[2], inputArray[3], (int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
		}else{
			int[][] fixedInitial={{1,0,1,1,0},{0,0,1,2,2},{1,0,2,0,0},{0,0,1,0,0}};
			this.sim = new SimulationWaTor(fixedInitial.length,fixedInitial[0].length,fixedInitial,(int)inputArray[4], (int)inputArray[5], (int)inputArray[6]);
		}
	}
	
	/*
	 * This method creates a button that allows you to switch simulations by returning you to the 
	 * first scene with all of the options.
	 */
	private Button switchSim() {
		Button b = new Button(myResources.getString("switchbutton"));
		b.setOnAction(e ->{
			animation.pause();
			startScreen();
			window.setScene(scene);
			});
		return b;	
		
	}
	
	/*
	 * This method creates a button allows you to save the current state of your simulation to an xml file.
	 */
	private Button saveButton() {
		Button b = new Button(myResources.getString("savebutton"));
		b.setOnAction(e -> {
			animation.pause();
			saveUI.save(sim.getArray(), simName);	
		});
		return b;
		
	}
	
	/*
	 * This method creates a button allows you to resume a previously saved simulation.
	 */
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
			if(simName.equals(RPS_TITLE)) {
			this.sim = new SimulationRPS(resumedArray.length, resumedArray[0].length, resumedArray);
			}
			playSim();	
		});
		return b;
		
	}
	
	/*
	 *  This method creates a button allows you to pause the simulation.
	 */
	private Button pauseButton() {
		Button b = new Button(myResources.getString("pausebutton"));
		b.setOnAction(e -> {
			animation.pause();
		});
		return b;
	}
	
	/*
	 * This method creates a button that allows you to play the simulation.
	 */
	private Button playButton() {
		Button b = new Button(myResources.getString("playbutton"));
		b.setOnAction(e ->{
			animation.play();
		});
		return b;
	}
	
	/*
	 * This method creates a button that allows you to step through the simulation.
	 */
	private Button stepButton() {
		Button b = new Button(myResources.getString("stepbutton"));
		b.setOnAction(e ->{
			animation.pause();
			step();
		});
		return b;
	}
	
	/*
	 * This method creates a button that allows you to reset the simulation.
	 */
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

	/*
	 * This method updates an instance variable in the class so that it can display a particular name in the 
	 * title bar of the stage.
	 * @param s
	 */
	
	private void changeSimName(String s) {
		this.simName = s;
		window.setTitle(simName);
	}
	
	/*
	 * This method iterates through one instance of the simulation, and updates the system.
	 */
	private void step() {
		sim.update();
		myGrid.update();
		graph.update();
	}

	/*
	 * This method will start off the process of playing the simulation.
	 */
	public void playSim() {
		createAnimation();
		this.scene = makeSimulation();
		window.setScene(scene);
	}

	/*
	 * This method will create the animation for the simulation and the graph.
	 */
	private void createAnimation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
		animation = new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.setRate(5);
	}
	
	
	/*
	 * This returns the animation instance variable of this class.
	 */
	public Timeline getAnimation() {
		return animation;
	}
	
	/*
	 * This returns the Simulation instance variable of this class.
	 */
	public Simulation getSimulation() {
		return sim;
	}
	
	/*
	 * This method sets the Simulation instance variable of this class to a new simulation.
	 * @param simulation
	 */
	public void setSimulation(Simulation simulation) {
		this.sim = simulation;
	}
	
	/*
	 * This method creates a button that allows you to create a new window and a new simulation 
	 * using the NewSimulation class, and allows you to see multiple simulations side by side.
	 */
	private Button newSim() {
		Button b = new Button(myResources.getString("newbutton"));
		b.setOnAction(e->{
			Stage t = new Stage();
			NewSimulation s = new NewSimulation(t);
		});
		
		return b;
	}
	
	/*
	 * This method returns the Scene instance variable of this class.
	 */
	public Scene getScene() {
		return scene;
	}
}
