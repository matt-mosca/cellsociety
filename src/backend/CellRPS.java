package backend;

import java.util.List;
import javafx.scene.paint.Color;

public class CellRPS extends Cell{
	public static final int EMPTY = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	
	//0 is darkest, 9 is lightest
	private int gradientLevel;
	
	public CellRPS(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber, int gradientLevel) {
		super(state, color, neighborCells, rowNumber, columnNumber);
		this.gradientLevel = gradientLevel;
	}
	
	public int getGradientLevel() {
		return gradientLevel;
	}
	
	public void setGradientLevel(int gradient) {
		gradientLevel = gradient;
	}
}
