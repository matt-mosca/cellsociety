/*
 * Author: Venkat S.
 */
package frontend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import backend.Cell;

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
	private static final int VBOX_SPACING = 10;
	private Scene scene;
	private Cell[][] Cells;
	private int width;
	private int height;
	private GridPane myGrid;
	private ImageView[][] Images;
	private String simName;
	private Stage window;
	
	public SimDisplay(int x, int y, Stage s) {
		this.width=x;
		this.height=y;
		this.window = s;
	}
	
	public Scene makeSimulation(){
		BorderPane border = new BorderPane();
		Scene fun = new Scene(border,width, height);
//		border.setCenter(myGrid);
		this.scene = fun;
		return this.scene;
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
		this.simName = s + "!";
		b.setPrefSize(100, 50);
		b.setOnAction(e -> {
			//shouldn't this call the XML reader and start passing information to the backend?
			//I think that it should definitely do that. 
			makeSimulation();
			window.setScene(scene);
			changeSimName();
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
	
	
	
	private void fillGrid(GridPane grid) {
		for(int i=0;i<Cells.length;i++) {
			for (int j=0; j<Cells[i].length; j++) {
				grid.add(Images[i][j], j, i);
			}
		}
		this.myGrid=grid;
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
	
	public void changeSimName() {
		window.setTitle(simName);
	}
	
	
	
}
