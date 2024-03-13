import java.util.Arrays;
import java.util.HashMap;

public class Agent {
	
	// map: for each coordinate, provide a list. 0 or 1 for each of the choices 
	HashMap<Coordinate, int[]> policy;
	Model model;
	
	public Agent(Model model) {
		this.model = model;
		this.policy = new HashMap<>();
		initializePolicy();
	}
	
	// initialize target states to an empty action list
	// initialize non-target states to a value of 1 for each action
	// action list index corresponds to enum values
	public void initializePolicy() {
		for (Coordinate coord: model.getAllStates()) {
			if (model.isTarget(coord)) {
				policy.put(coord, new int[0]);
			} else {
				// for each non-target state, there is one int for each possible action
				// at initialization, each action has value 1 (making each action possible to choose)
				int actionCount = model.actionCount(coord);
				int[] actionList = new int[actionCount];
				Arrays.fill(actionList, 1);
				policy.put(coord, actionList);
			}
		}
	}
	
	public void setPolicy(Coordinate state, int[] actionList) {
		policy.put(state, actionList);
	}
	
	// non-stochastic implementation
	public Model.Action chooseAction(Coordinate state) throws Exception {
		int[] actionList = policy.get(state);
		
		// find the number of possible choices
		int sum = 0;
		for (int possibility: actionList) {
			sum += possibility; // adds a one for each possible choice
		}
		
		// pick a random number
		int choice = (int) (Math.random() * sum);
		
		// which choice does generated int correspond to?
		int choiceIndex = 0;
		while(actionList[choiceIndex] == 0) {
			choiceIndex ++;
		}
		for (int i = 0; i < choice; i++) {
			choiceIndex ++;
			// skip any non-possible actions
			while(actionList[choiceIndex] == 0) {
				choiceIndex ++;
			}
		}
		
		// return enum corresponding to index of the action we picked from actionList
		return Model.Action.values()[choiceIndex];
	}
}
