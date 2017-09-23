package backend;

/**
 * State.java
 * Represents the state of a cell. Flexible to accommodate varying number of states in across
 * simulations. The actual types of states are created externally.
 * @author matthewmosca
 *
 */
public class State {
	
	private int stateVal;
	
	public State(int val) {
		stateVal = val;
	}
	
	public int getState() {
		return stateVal;
	}
	
	public void changeState(int newState) {
		stateVal = newState;
	}
}
