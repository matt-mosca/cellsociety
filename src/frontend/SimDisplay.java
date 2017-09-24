/*
 * Author: Venkat S.
 */
package frontend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import backend.Cell;
import backend.Simulation;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.*;


public class SimDisplay {
	private static final int VBOX_SPACING = 7;
	private Scene scene;
	private Cell[][] Cells;
	private int width;
	private int height;
	private GridPane myGrid;
	private ImageView[][] Images;
	private String simName;
	private Stage window;
	private Simulation sim;
	
	
	public SimDisplay(int x, int y, Stage s) {
		this.width=x;
		this.height=y;
		this.window = s;
	}
	
	private void makeSimulation(){
		BorderPane border = new BorderPane();
		Scene fun = new Scene(border,width, height);
		this.sim = new Simulation(1,2,3,4);
		this.Cells = sim.getArray();
		makeGrid();
		fillGrid();
		border.setCenter(myGrid);
		//This could use some serious refactoring, potentially creating a new class for just all of the buttons seems
		//to make sense.
		VBox controls = new VBox(VBOX_SPACING);
		Button play = playButton();
		Button pause = pauseButton();
		Button step = stepButton();
		Button reset = resetButton();
		controls.getChildren().addAll(play, pause, step, reset);
		border.setBottom(controls);
		controls.setAlignment(Pos.CENTER);
		this.scene = fun;
		window.setScene(scene);
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
		Button b4 = chooseScene("sim4");
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
			
			makeSimulation();
			changeSimName(s);
		});
		return b;
	}
	
	
	private Button pauseButton() {
		Button b = new Button("Pause");
		b.setOnAction(e -> {
			//do the pause stuff by pausing the animation I guess
		});
		return b;
	}
	
	private Button playButton() {
		Button b = new Button("Play");
		b.setOnAction(e ->{
			//do the play stuff by resuming the animation, starting the animation, idk.
		});
		return b;
	}
	
	private Button stepButton() {
		Button b = new Button("Step");
		b.setOnAction(e ->{
			//do the step stuff here by calling the step function wtf idk
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
		grid.getColumnConstraints().add(new ColumnConstraints(width/Cells[0].length));
		grid.getRowConstraints().add(new RowConstraints(height/Cells.length));
		myGrid=grid;
		return myGrid;
	}
	
	
	
	private void fillGrid() {
		for(int i=0;i<Cells.length;i++) {
			for (int j=0; j<Cells[i].length; j++) {
				myGrid.add(Images[i][j], j, i);
			}
		}
	}
	
	
//UNCOMMENT THIS IF YOU END UP NEEDING TO HAVE AN IMAGEVIEW ARRAY
	
	private ImageView[][] makeImageArray(Cell[][] Cells){
		for(int i=0; i<Cells.length; i++) {
			for (int j=0; j<Cells[i].length; j++) {
				Images[i][j] = new ImageView(Cells[i][j].getImage());
			}
		}
		return Images;
	}
	
	public void changeSimName(String s) {
		this.simName = s + "!";
		window.setTitle(simName);
	}
	
	
	
}
