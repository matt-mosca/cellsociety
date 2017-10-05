package frontend;


import backend.Cell;
import backend.Simulation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/* GridDisplay.java
 * @author Venkat Subramaniam
 * Class that initializes a GridDisplay object, which allows you to display different kinds of simulations
 * within a Pane. 
 * @version 10.04.17
 */
public class GridDisplay {
	
	private static final int GRID_FIT_CONSTANT = 400;
	private Pane myGrid;
	private Cell[][] Cells;
	private Rectangle[][] Rectangles;
	private Polygon[][] Triangles;
	private Simulation sim;
	private StyleUI style=new StyleUI();
	private String shape;
	/*
	 * Constructor for this class. Takes in a simulation s and creates the Pane, then populates it.
	 * @param s
	 */
	public GridDisplay(Simulation s) {
		sim = s;
		myGrid = new Pane();
		Cells = s.getArray();
		myGrid.setPrefHeight(GRID_FIT_CONSTANT);
		myGrid.setPrefWidth(GRID_FIT_CONSTANT);
		shape=style.gridShape();
		if (shape.equals("Square")){
			makeRectangleArray();
		}
		if (shape.equals("Triangle")) {
		makeTriangleArray();
		}
		fillGrid();
		myGrid.setOnMouseClicked(e-> handleClick(e.getX(),e.getY()));
	}
	
	/*
	 * This method creates and initializes a Rectangle array with Rectangles that have the required color.
	 */
	private void makeRectangleArray() {
		Rectangles = new Rectangle[Cells.length][Cells[0].length];
		double width = GRID_FIT_CONSTANT/Cells[0].length;
		double height = GRID_FIT_CONSTANT/Cells.length;
		for (int i =0; i<Cells.length; i++) {
			for (int j=0; j<Cells[i].length; j++) {
				Rectangle r = new Rectangle(j*width, i*height, width, height);
				if (style.gridVisibility()==true) {
				    r.setStroke(Color.BLACK);
				}
				r.setFill(Cells[i][j].getColor());
				Rectangles[i][j] = r;
			}
		}
	}
	
	/*
	 * This method creates and initializes a Triangle array with Triangles that have the required color.
	 */
	private void makeTriangleArray() {
		Triangles = new Polygon[Cells.length][Cells[0].length];
		double width = (GRID_FIT_CONSTANT / (Cells[0].length+1))*2;
		double height = GRID_FIT_CONSTANT / Cells.length; 
		for(int i=0; i<Cells.length; i++) {
			for(int j=0; j<Cells[i].length; j++) {
				Polygon tem = new Polygon();
				if (i%2==0 && j%2==0) {	
					tem.getPoints().addAll(new Double[] {
							(j/2)*width,(i+1)*height,
							(j/2)*width+width,(i+1)*height,
							(j/2)*width+0.5*width,i*height
						});
				}
				if (i%2==0 && j%2==1) {
					
					tem.getPoints().addAll(new Double[] {
							j*(width/2),i*height,
							j*(width/2)+width,i*height,
							(j/2)*width+width,(i+1)*height
						});
					}
                if (i%2==1 && j%2==0) {
					tem.getPoints().addAll(new Double[] {
							(j/2)*width,i*height,
							(j/2)*width+width,i*height,
							(j/2)*width+0.5*width,(i+1)*height
						});
				}
                if (i%2==1 && j%2==1) {
  					tem.getPoints().addAll(new Double[] {
  							j*(width/2),(i+1)*height,
  							j*(width/2)+width,(i+1)*height,
  							j*(width/2)+0.5*width,i*height
  						});
  				}
                if (style.gridVisibility()==true) {
				    tem.setStroke(Color.BLACK);
                }
				tem.setFill(Cells[i][j].getColor());
				Triangles[i][j]=tem;	
			}
		}
	}
	
	/*
	 * This method updates this object so that it can display the current version of the simulation.
	 */
	public void update(){
		Cells = sim.getArray();
		updateColorArray();
		fillGrid();
	}
	
	/*
	 * This method populates the grid with the required rectangles or triangles, as the case may be.
	 */
	private void fillGrid() {
		myGrid.getChildren().clear();
		for(int i=0;i<Cells.length;i++) {
			for (int j=0; j<Cells[i].length; j++) {
				if (shape.equals("Triangle")){
				    myGrid.getChildren().add(Triangles[i][j]);
				}
				if (shape.equals("Square")){
					myGrid.getChildren().add(Rectangles[i][j]);
				}
			}
		}
	}
	
	/*
	 * This method updates the colors of the Rectangles depending on what is required by the simulation.
	 */
	private void updateColorArray() {
		for (int i=0; i<Cells.length; i++) {
			for(int j=0; j<Cells[i].length; j++) {
				 
				if (shape.equals("Triangle")){
					this.Triangles[i][j].setFill(Cells[i][j].getColor());
				}
				if (shape.equals("Square")){
					this.Rectangles[i][j].setFill(Cells[i][j].getColor());
				}
				
			}
		}
		
	}
	
	/*
	 * This method returns the myGrid instance variable of this class.
	 */
	public Pane getGrid() {
		return myGrid;
	}

	/*
	 * This method handles a mouse input to change the state of a particular cell that is clicked on.
	 */
	public void handleClick(double x, double y) {
		if (shape.equals("Square")){
			for (int i=0; i<Rectangles.length; i++) {
				for(int j=0; j<Rectangles[i].length; j++) {
					if (Rectangles[i][j].contains(x,y)) {
						if (Cells[i][j].getState()==0) {
						Cells[i][j].changeState(1);
					}
						if(Cells[i][j].getState()==1) {
							Cells[i][j].changeState(2);
						}
						if (Cells[i][j].getState()==2) {
							Cells[i][j].changeState(0);
							Rectangles[i][j].setFill(Color.WHITE);
						}
				}
			}
	}
	}
}
	
	
	
	
	
}
