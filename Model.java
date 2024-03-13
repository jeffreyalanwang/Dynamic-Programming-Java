import java.util.Arrays;

// an n x n grid model; the grid stores state values
// I should split this class into model (containing targets, rewards, states, actions) and another class to hold state values
public class Model {
	
	// confusing note: 
	public enum Action {
		goUp, goDown, goLeft, goRight;
	}
	
	Grid grid;
	int stateCount;
	Coordinate[] targets;
	Coordinate[] states;
	
	public Model(int gridSize, Coordinate[] targets) throws Exception {
		this.grid = new Grid(gridSize);
		this.stateCount = gridSize * gridSize;
		this.targets = targets;
		initializeStateList();
		initializeStateValues();
	}
	
	// populates a list of all the possible states
	private void initializeStateList() throws Exception {
		states = grid.getAllCoordinates();
		if (states.length != stateCount) {
			System.out.println("Error: state count != grid coordinate count");
		}
	}
	
	// to start with, set each state to have a state value of -1, except targets have a state value of 0
	private void initializeStateValues() {
		for (Coordinate coord: getAllStates()) {
			if (isTarget(coord)) {
				grid.setValue(coord, 0.0);
			} else {
				grid.setValue(coord, -1.0);
			}
		}
	}
	
	public int getGridSize() {
		return grid.getDimension();
	}
	
	public void setStateValue(Coordinate state, double value) {
		grid.setValue(state, value);
	}
	
	public double getStateValue(Coordinate state) {
		return grid.getValue(state);
	}
	
	private boolean moveForbidden(Coordinate start, Action move) throws Exception {
		switch(move) {
		case goUp:
			return start.getRow() <= 0;
		case goDown:
			return start.getRow() >= Grid.limitRow() - 1;
		case goLeft:
			return start.getCol() <= 0;
		case goRight:
			return start.getCol() >= Grid.limitCol() - 1;
		default:
			throw new Exception("invalid move");
		}
	}
	
	public Coordinate makeMove(Coordinate start, Action move)  throws Exception {
		// if move is forbidden, make no move
		if (moveForbidden(start, move)) {
			return start;
		}
		
		switch(move) {
		case goUp:
			return new Coordinate(start.getRow() - 1, start.getCol());
		case goDown:
			return new Coordinate(start.getRow() + 1, start.getCol());
		case goLeft:
			return new Coordinate(start.getRow(), start.getCol() - 1);
		case goRight:
			return new Coordinate(start.getRow(), start.getCol() + 1);
		default:
			throw new Exception("invalid move");
		}
	}
	
	public Coordinate[] getAllStates() {
		return Arrays.copyOf(states, stateCount);
	}
	
	// assumes that each coordinate has the same options for action
	// uses Action enum
	public int actionCount(Coordinate coord) {
		return Model.Action.values().length;
	}
	
	public boolean isTarget(Coordinate coord) {
		for (Coordinate target: targets) {
			if (target.equals(coord)) {
				return true;
			}
		}
		return false;
	}
}
