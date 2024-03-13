import java.util.Arrays;
import java.util.HashMap;

public class Policy_Iteration {

	Model model;
	Agent agent;
	double discount_factor;
	
	// gamma is discount_factor (currently unused)
	public Policy_Iteration(int gridSize, Coordinate[] targets, double discount_factor) throws Exception {
		model = new Model(gridSize, targets);
		agent = new Agent(model);
		this.discount_factor = discount_factor;
	}
	
	// i'm honestly not clear on how this was supposed to be written.
	// based on Bellman Expectation Equation
	private double bellmanUpdateValue(Coordinate state) throws Exception {
		if (model.isTarget(state)) {
			return 0;
		} else {
			double total = model.getStateValue(state);
			for (Model.Action action: Model.Action.values()) { // for each possible move from this state
				Coordinate nextState = model.makeMove(state, action);
				total += model.getStateValue(nextState) / model.actionCount(state);
				// this equation, alternative to the one above, is so confusing to me and I have copied it wrong for sure:
				//total += (policy.get(state)[action.ordinal()] * model.getStateValue(nextState)) + model.getOldStateValue(nextState));
			}
			return total;
		}
	}
	
	// Update each state value based on Bellman Expectation Equation
	private void bellmanUpdateIteration() throws Exception {
		// temporarily store new values here, as the old ones are used for neighboring calculations
		HashMap <Coordinate, Double> newStateValues = new HashMap<>();
		// calculate Bellman update value
		for (Coordinate state: model.getAllStates()) {
			newStateValues.put(state, bellmanUpdateValue(state));
		}
		// save Bellman update values
		for (Coordinate state: newStateValues.keySet()) {
			model.setStateValue(state, newStateValues.get(state));
		}
	}
	
	private void policy_evaluation() throws Exception {
		// it's really not clear to me if policy evaluation should be one bellman update iteration
		bellmanUpdateIteration();
	}
	
	// return an actionList with a 1 for the most greedy actions
	public int[] greedyActions(Coordinate state) throws Exception {
		// find the state value for each of the next steps
		double[] actionResults = new double[model.actionCount(state)];
		for (Model.Action action: Model.Action.values()) {
			Coordinate nextState = model.makeMove(state, action);
			double nextStateValue = model.getStateValue(nextState);
			actionResults[action.ordinal()] = nextStateValue;
		}
		// which of the actions resulted in the highest state value?
		double max  = actionResults[0];
		for (double value: actionResults) {
			if (value > max) {
				max = value;
			}
		}
		// identify actions resulting in this value as greedy
		int[] actionList = new int[model.actionCount(state)];
		Arrays.fill(actionList, 0);
		for (Model.Action action: Model.Action.values()) {
			if (actionResults[action.ordinal()] == max) {
				actionList[action.ordinal()] = 1;
			}
		}
		return actionList;
	}
	
	private void policy_improvement() throws Exception {
		for (Coordinate state: model.getAllStates()) {
			if (model.isTarget(state)) {
				// do nothing
				// this is a terminal state
			} else {
				// update the greedy policy with updated state values
				agent.setPolicy(state, greedyActions(state));
			}
		}
	}

	public void iterate(int iterations) throws Exception {
		for (int i = 0; i < iterations; i ++) {
			policy_evaluation();
			policy_improvement();
		}
	}
	
	public void testPolicy(Coordinate start) throws Exception {
		
		Coordinate coord = start;
		while (!model.isTarget(coord)) {
			System.out.println(coord.showOnGrid());
			coord = model.makeMove(coord, agent.chooseAction(coord));
		}
		System.out.println(coord.showOnGrid() + "(end)");
		
	}
	
}
