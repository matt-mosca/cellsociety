package frontend;

import backend.Cell;
import backend.Simulation;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class GridDisplay {
	
	private static final int GRID_FIT_CONSTANT = 550;
	private GridPane myGrid;
	private Cell[][] Cells;
	private Rectangle[][] Rectangles;
	private Polygon[][] Triangles;
	private Simulation sim;
	
	public GridDisplay(Simulation s) {
		sim = s;
		myGrid=new GridPane();
		Cells = s.getArray();
		myGrid.setPrefHeight(GRID_FIT_CONSTANT);
		myGrid.setPrefWidth(GRID_FIT_CONSTANT);
		myGrid.getColumnConstraints().add(new ColumnConstraints(GRID_FIT_CONSTANT / Cells[0].length));
		myGrid.getRowConstraints().add(new RowConstraints(GRID_FIT_CONSTANT / Cells.length));
		makeTriangleArray();
		fillGrid();
		myGrid.setAlignment(Pos.CENTER);
	}
	
	private void makeRectangleArray() {
		Rectangles = new Rectangle[Cells.length][Cells[0].length];
		for(int i=0; i<Cells.length; i++) {
			for(int j=0; j<Cells[i].length; j++) {
				Rectangles[i][j] = new Rectangle();
				Rectangles[i][j].setStroke(Color.BLACK);
				Rectangles[i][j].setFill(Cells[i][j].getColor());
				Rectangles[i][j].setWidth(GRID_FIT_CONSTANT / Cells[0].length);
				Rectangles[i][j].setHeight(GRID_FIT_CONSTANT / Cells.length);
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
				tem.setFill(Cells[i][j].getColor());
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
				myGrid.add(Triangles[i][j], j, i);
			}
		}
	}
	
	private void updateColorArray() {
		for (int i=0; i<Cells.length; i++) {
			for(int j=0; j<Cells[i].length; j++) {
				this.Triangles[i][j].setFill(Cells[i][j].getColor()); //the n^2 algo is really starting to make me sad, but I'm not sure how to get it to be faster. Suggestions? -V
				
			}
		}
		
	}
	
	public GridPane getGrid() {
		return myGrid;
	}
	
	
	
}
