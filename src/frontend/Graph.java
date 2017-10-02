package frontend;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
//import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.ResourceBundle;

import backend.Simulation;
public  class Graph {
	private static final double GRAPH_HEIGHT_CONSTANT = 200;
	private static final double GRAPH_WIDTH_CONSTANT = 750;
	private static final int SCALING_FACTOR = 100;
	private static final String WATOR_TITLE = "WaTor!";
	private static final String FIRE_TITLE  = "FIYAH!";
	private static final String SEGREGATION_TITLE = "SEGREGATION!";
	private static final String GAME_OF_LIFE_TITLE = "GAME OF LIFE!";
	private LineChart<Number, Number> myGraph;
	private int[] proportions;
	private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private Simulation sim;
    private ResourceBundle resources;
    private String simName;
    int time;
    
    public Graph(Simulation s, ResourceBundle rb, String name) {
		xAxis.setLabel("Time elapsed");
		yAxis.setLabel("Proportion");
		sim=s;
		simName=name;
		proportions = s.getCellProportion();
		resources=rb;
		myGraph = createGraph();
		myGraph.setPrefHeight(GRAPH_HEIGHT_CONSTANT);
		myGraph.setPrefWidth(GRAPH_WIDTH_CONSTANT);
			
    }
    
    public LineChart<Number, Number> createGraph() {
		LineChart<Number, Number> fun = new LineChart<Number, Number>(xAxis, yAxis);
		fun.getData().addAll(getLine(1), getLine(2));
		updateLine(fun.getData().get(0));
		updateLine(fun.getData().get(1));
		
		return fun;
	}
	
	
	private XYChart.Series<Number, Number> getLine(int state) {
		XYChart.Series<Number, Number> line = new XYChart.Series<Number, Number>();
		if (simName.equals(FIRE_TITLE)){
			if (state==1) {
			line.setName(resources.getString("treename"));
		}
			if(state==2) {
			line.setName(resources.getString("firename"));
		}
	}	
		if (simName.equals(WATOR_TITLE)) {
			if(state==1) {
				line.setName(resources.getString("sharkname"));
			}
			if(state==2) {
				line.setName(resources.getString("fishname"));
			}
		
		}
		if (simName.equals(SEGREGATION_TITLE)) {
			if(state==1) {
				line.setName(resources.getString("redname"));
			}
			if(state==2) {
				line.setName(resources.getString("bluename"));
			}
		}
		if (simName.equals(GAME_OF_LIFE_TITLE)) {
			if(state==1) {
				line.setName(resources.getString("lifename"));
			}
			if(state==0) {
				line.setName(resources.getString("deadname"));
			}
		}
		return line;
	}
	
//	private void nameLine(XYChart.Series<Number, Number> line, String s1, String s2, int state) {
//		if (state==0) {
//			line.setName(resources.getString(s2));
//		}
//		if(state==1) {
//			line.setName(resources.getString(s1));
//		}
//		if(state==2) {
//			line.setName(resources.getString(s2));
//		}
//	}
	
	
		
	
	
	public void update() {
		updateLine(myGraph.getData().get(0));
		updateLine(myGraph.getData().get(1));
		time+=1;
	}
	
	private void updateLine(XYChart.Series<Number, Number> line) {
		proportions = sim.getCellProportion();
		if (simName.equals(FIRE_TITLE)){
		if(line.getName().equals(resources.getString("treename"))) {
			line.getData().add(new Data<Number, Number>(time, (double)proportions[0]/SCALING_FACTOR));
		}
		if(line.getName().equals(resources.getString("firename"))) {
			line.getData().add(new Data<Number, Number>(time, (double)proportions[1]/SCALING_FACTOR));
		}
	}
		if (simName.equals(WATOR_TITLE)){
			if(line.getName().equals(resources.getString("sharkname"))) {
				line.getData().add(new Data<Number, Number>(time, (double)proportions[0]/SCALING_FACTOR));
			}
			if(line.getName().equals(resources.getString("fishname"))) {
				line.getData().add(new Data<Number, Number>(time, (double)proportions[1]/SCALING_FACTOR));
			}
		}
		if (simName.equals(SEGREGATION_TITLE)){
			if(line.getName().equals(resources.getString("redname"))) {
				line.getData().add(new Data<Number, Number>(time, (double)proportions[0]/SCALING_FACTOR));
			}
			if(line.getName().equals(resources.getString("bluename"))) {
				line.getData().add(new Data<Number, Number>(time, (double)proportions[1]/SCALING_FACTOR));
			}
		}
		if (simName.equals(GAME_OF_LIFE_TITLE)){
			if(line.getName().equals(resources.getString("deadname"))) {
				line.getData().add(new Data<Number, Number>(time, (double)proportions[0]/SCALING_FACTOR));
			}
			if(line.getName().equals(resources.getString("lifename"))) {
				line.getData().add(new Data<Number, Number>(time, (double)proportions[1]/SCALING_FACTOR));
			}
		}
		
	}
	
	public LineChart<Number, Number> getGraph(){
		return myGraph;
	}

}