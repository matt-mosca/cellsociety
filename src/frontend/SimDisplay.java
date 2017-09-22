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
//	ImageView[][] Images; //Do we need this? 
	
	public SimDisplay(int x, int y) {
		this.width=x;
		this.height=y;
	}
	
	public Scene makeSimulation(){
		
		return this.scene;
	}
	
	public Scene startScreen() {
		
		return this.scene;
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
				grid.add(ImageV, columnIndex, rowIndex);
			}
		}
	}
	
	
//UNCOMMENT THIS IF YOU END UP NEEDING TO HAVE AN IMAGEVIEW ARRAY
	
//	private ImageView[][] makeImageArray(Cell[][] Cells){
//		for(int i=0; i<Cells.length; i++) {
//			for (int j=0; j<Cells[i].length; j++) {
//				Images[i][j] = new ImageView(Cells[i][j].getImage());
//			}
//		}
//		return Images;
//	}
	
	
}
