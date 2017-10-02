package frontend;

import backend.Cell;
import backend.Simulation;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class GridDisplay {
	
	private static final int GRID_FIT_CONSTANT = 400;
	private Pane myGrid;
	private Cell[][] Cells;
	private Rectangle[][] Rectangles;
	private Polygon[][] Triangles;
	private Simulation sim;
	private StyleUI style=new StyleUI();
	private String shape;
	
	public GridDisplay(Simulation s) {
		sim = s;
		myGrid = new Pane();
		Cells = s.getArray();
		myGrid.setPrefHeight(GRID_FIT_CONSTANT);
		myGrid.setPrefWidth(GRID_FIT_CONSTANT);
//		myGrid.getColumnConstraints().add(new ColumnConstraints(GRID_FIT_CONSTANT / Cells[0].length));
//		myGrid.getRowConstraints().add(new RowConstraints(GRID_FIT_CONSTANT / Cells.length));
		shape=style.gridShape();
		if (shape.equals("Square")){
			makeRectangleArray();
		}
		if (shape.equals("Triangle")) {
		makeTriangleArray();
		}
		fillGrid();
//		myGrid.setAlignment(Pos.CENTER);
	}
	
//	private void makeRectangleArray() {
//		Rectangles = new Polygon[Cells.length][Cells[0].length];
//		double width=GRID_FIT_CONSTANT/Cells[0].length;
//		double height=GRID_FIT_CONSTANT/Cells.length;
//		for(int i=0; i<Cells.length; i++) {
//			for(int j=0; j<Cells[i].length; j++) {
//				Polygon tem=new Polygon();
//				tem.getPoints().addAll(new Double[] {
//						j*width, (i+1)*height,
//						j*width+width, (i+1)*height,
//						j*width, i*height,
//						j*width+width, i*height 
//						
//					});
//				tem.setStroke(Color.BLACK);
//				tem.setFill(Cells[i][j].getColor());
//				Rectangles[i][j]=tem;
//				
//				
//			}
//		}
//	}
	
	private void makeRectangleArray() {
		Rectangles = new Rectangle[Cells.length][Cells[0].length];
		double width = GRID_FIT_CONSTANT/Cells[0].length;
		double height = GRID_FIT_CONSTANT/Cells.length;
		for (int i =0; i<Cells.length; i++) {
			for (int j=0; j<Cells[i].length; j++) {
				Rectangle r = new Rectangle(j*width, i*height, width, height);
				r.setStroke(Color.BLACK);
				r.setFill(Cells[i][j].getColor());
				Rectangles[i][j] = r;
			}
		}
	}
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
				tem.setStroke(Color.BLACK);
//				tem.setFill(Cells[i][j].getColor());
				Triangles[i][j]=tem;	
			}
		}
	}
	
	public void update(){
		Cells = sim.getArray(); //I can't help but feel that this is stupidly inefficient. Is there an easier way? - V 
		//I don't think you guys are updating this array in the backend, because I'm not getting an animation. 
		updateColorArray();
		fillGrid();
	}
	
	private void fillGrid() {
		myGrid.getChildren().clear();
		for(int i=0;i<Cells.length;i++) {
			for (int j=0; j<Cells[i].length; j++) {
				if (shape.equals("Triangle")){
				    myGrid.getChildren().add(Triangles[i][j]);
				}
				if (shape.equals("Square")){
//					System.out.println("I'm here");
					myGrid.getChildren().add(Rectangles[i][j]);
				}
			}
		}
	}
	
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
	
	public Pane getGrid() {
		return myGrid;
	}
	
	
	
}
