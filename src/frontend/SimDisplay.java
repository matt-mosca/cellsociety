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

/*
 * Author: Venkat S.
 */
public class SimDisplay {
	private Scene scene;
	//private ArrayList<ArrayList<Cell>> Cells;
	private Cell[][] Cells;
	private int width;
	private int height;
	private GridPane myGrid;
	ImageView[][] Images;
	
	public SimDisplay(int x, int y) {
		this.width=x;
		this.height=y;
	}
	
	public Scene makeSimulation(){
		
		return this.scene;
	}
	
	public Scene startScreen() {
		VBox layout = new VBox(10);
		Scene startScene= new Scene(layout, width, height);
		//Change the image here depending on the kind of image that we want.
		Image backGround = new Image(getClass().getClassLoader().getResourceAsStream("brickwall.jpeg"));
		ImagePattern pattern = new ImagePattern(backGround);
		startScene.setFill(pattern);
		//get some buttons to choose which of the four simulations they want to do
		//replace the following code with a for loop and a resource file.
		Button b1 = chooseScene("WaTor");
		Button b2 = chooseScene("Fire");
		Button b3 = chooseScene("Sim3");
		Button b4 = chooseScene("Sim4");
		layout.getChildren().addAll(b1,b2,b3,b4);
		this.scene = startScene;
		return this.scene;
	}
	
	
	private Button chooseScene(String s){
		Button b = new Button(s);
		b.setOnAction(e -> {
			makeSimulation();
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
	
	
}
