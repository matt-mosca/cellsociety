package frontend;

import java.util.ResourceBundle;

import backend.SimulationFire;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
//import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
public class GraphFire {
	private static final double GRAPH_HEIGHT_CONSTANT = 200;
	private static final double GRAPH_WIDTH_CONSTANT = 0;
	LineChart<Number, Number> myGraph;
	int[] proportions;
	final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    SimulationFire sim;
    ResourceBundle resources;
    int time;
    
    
    
	
	public GraphFire(SimulationFire s, ResourceBundle rb) {
		xAxis.setLabel("Time elapsed");
		yAxis.setLabel("Proportion");
		sim=s;
		proportions = s.getCellProportion();
		myGraph = createGraph();
		myGraph.setPrefHeight(GRAPH_HEIGHT_CONSTANT);
		myGraph.setPrefWidth(GRAPH_WIDTH_CONSTANT);
		resources=rb;	
	}




	private LineChart<Number, Number> createGraph() {
		LineChart<Number, Number> fun = new LineChart<Number, Number>(xAxis, yAxis);
		fun.getData().addAll(getLine(1), getLine(2));
		updateLine(fun.getData().get(0));
		updateLine(fun.getData().get(1));
		return fun;
	}
	
	
	private XYChart.Series<Number, Number> getLine(int state) {
		XYChart.Series<Number, Number> line = new XYChart.Series();
		if (state==1) {
			line.setName(resources.getString("treename"));
		}
		if(state==2) {
			line.setName(resources.getString("firename"));
		}
		return line;
	}
	
	public void update() {
		updateLine(myGraph.getData().get(0));
		updateLine(myGraph.getData().get(1));
	}
	
	private void updateLine(XYChart.Series<Number, Number> line) {
		proportions = sim.getCellProportion();
		if(line.getName().equals(resources.getString("treename"))) {
			line.getData().add(new Data<Number, Number>(time+=1, proportions[0]));
		}	
	}
}
