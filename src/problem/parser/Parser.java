/**
 * 
 */
package problem.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import model.ConjunctivePredicate;
import model.SinglePredicate;
import model.State;

public abstract class Parser {

	private State initialState;
	private State goalState;
	
	public boolean parse(Path filePath) throws IOException {
		// read all the file and store it into a string
		String inputFromFile = new String(Files.readAllBytes(filePath));
		// call overloaded method for parsing string
		return parse(inputFromFile);
	}

	public boolean parse(String input) {
		// reset internal state
		initialState = goalState = null;
		// remove blank characters
		input = input.trim().replaceAll("\\s+", "");
		// look for initial and goal states keywords
		// GoalState is allowed to appear first
		String[] stateStringList = null;		
		if (input.startsWith("InitialState=")) {
			stateStringList = input.replaceAll("InitialState=", "").split("GoalState=");
		} else if (input.startsWith("GoalState=")) {
			stateStringList = input.replaceAll("GoalState=", "").split("InitialState=");
		} else {
			// TODO: throw an exception?
			return false;
		}

		ArrayList<State> states = new ArrayList<State>();
		for (String stateStr : stateStringList) {
			// create list of predicates
			String[] predStringList = stateStr.split(";");
			
			ConjunctivePredicate conjPred = new ConjunctivePredicate();
			for (String predStr : predStringList) {
				// parse predicate name and list of arguments
				// first get the name, then remove name  parenthesis 
				// and finally split list of arguments separated by semicolon
				String predName = predStr.split("\\(")[0];
				String[] predArgs = predStr.replace(predName, "").replace("(", "").replace(")", "").split(",");
				SinglePredicate pred = parsePredicate(predName, predArgs);
				
				if (pred == null) {
					// Invalid predicate found; exit the method
					// TODO: throw an exception?
					System.out.println("Invalid predicate: " + predStr);
					return false;
				} else {
					conjPred.add(pred);
				}
			}
			
			states.add(new State(conjPred));
		}

		initialState = states.get(0);
		goalState = states.get(1);
		return true;
	}

	public State getInitialState()
	{
		return initialState;
	}

	public State getGoalState()
	{
		return goalState;
	}

	protected abstract SinglePredicate parsePredicate(String predName, String[] predArgs);
}
