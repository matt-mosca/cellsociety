//package frontend;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.XYChart;
//import javafx.scene.chart.XYChart.Data;
////import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.NumberAxis;
//
//import java.util.ResourceBundle;
//
//import backend.Simulation;
//import backend.SimulationFire;
//public  class Graph {
//	private static final double GRAPH_HEIGHT_CONSTANT = 200;
//	private static final double GRAPH_WIDTH_CONSTANT = 750;
//	private LineChart<Number, Number> myGraph;
//	private int[] proportions;
//	private final NumberAxis xAxis = new NumberAxis();
//    private final NumberAxis yAxis = new NumberAxis();
//    private SimulationFire sim;
//    private ResourceBundle resources;
//    int time;
//    
//    public Graph(SimulationFire s, ResourceBundle rb) {
//		xAxis.setLabel("Time elapsed");
//		yAxis.setLabel("Proportion");
//		sim=s;
//		proportions = s.getCellProportion();
//		myGraph = createGraph();
//		myGraph.setPrefHeight(GRAPH_HEIGHT_CONSTANT);
//		myGraph.setPrefWidth(GRAPH_WIDTH_CONSTANT);
//		resources=rb;	
//    }
//
//	public LineChart<Number, Number> createGraph() {
//		return null;
//	}
//	
//	public LineChart<Number, Number> getGraph(){
//		return myGraph;
//	}
//    
//	protected int[] getProportions() {
//		return proportions;
//	}
//
//	protected NumberAxis getX() {
//		return xAxis;
//	}
//	
//	protected NumberAxis getY() {
//		return yAxis;
//	}
//	
//	protected ResourceBundle getResources() {
//		return resources;
//	}
//	
//	protected Simulation getSimulation() {
//		return sim;
//	}
//    
//}
