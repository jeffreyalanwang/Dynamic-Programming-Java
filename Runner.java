import java.util.HashMap;
import java.util.Set;

public class Runner {

	public static void main(String[] args) throws Exception {
		
		// create the environment
		int gridSize = 5;
		Coordinate[] targets = {new Coordinate(0, 0),
							    new Coordinate(4, 4)};
		Policy_Iteration p_i = new Policy_Iteration(gridSize, targets, 1.0);
		
		// run the iterations
		int iterations = 100;
		p_i.iterate(iterations);
		
//		// view policy (debug)
//		for (Coordinate key: p_i.agent.policy.keySet()) {
//			System.out.println("Key: " + key.toString());
//			for (int i: p_i.agent.policy.get(key)) {
//				System.out.println(i);
//			}
//		}
		
		// test the policy
		Coordinate start = new Coordinate(2, 2);
		p_i.testPolicy(start);
		
	}

}
