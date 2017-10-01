package frontend;

import backend.SimulationFire;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
//import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
public class GraphFire {
	LineChart<Number, Number> myGraph;
	int[] proportions;
	final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    
    
    
	
	public GraphFire(SimulationFire s) {
		xAxis.setLabel("Time elapsed");
		yAxis.setLabel("Proportion");
		proportions = s.getCellProportion();
		myGraph = createGraph();
		
	}




	private LineChart<Number, Number> createGraph() {
		
		return null;
	}
	
	
	private XYChart.Series<Number, Number> getLine(int state) {
		XYChart.Series<Number, Number> line = new XYChart.Series();
		return line;
	}
	
	
	private void updateLine(XYChart.Series<Number, Number> line) {
		
	}

}
