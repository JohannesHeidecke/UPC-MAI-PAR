package planner;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import model.ConjunctivePredicate;
import model.Heuristic;
import model.Operator;
import model.Plan;
import model.StripsElement;
import model.Predicate;
import model.State;

public class Strips {

	private List<Operator> operators;
	private State initialState, finalState, currentState;
	private PrintStream output;
	private StripsStack stack;

	public Strips(State initialState, State finalState,
			List<Operator> operators) {

		this.initialState = initialState;
		this.finalState = finalState;
		// currentState is initialized each time a plan is calculated
		this.currentState = null;
		this.operators = operators;

		stack = new StripsStack();

		setOutput(System.out);
	}
	
	public Plan calculatePlan(Heuristic heuristic) throws Exception {

		// Calculate plan according to STRIP algorithm (linear planner with goal
		// stack):
		// initialize each time a plan is calculated
		this.currentState = new State(this.initialState);
		Plan plan = new Plan();
		plan.setOutput(output);

		// Push goal predicates from final state as list:
		stack.push(finalState.getPredicates());
		// Push individual goal predicates:

		// HEURISTIC: order the offices with a traveling salesmen solution
		// for example: NN algorithm
		// https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm
		// start at served(o) with o closest to current position
		// then go on by choosing the nearest neighbor.
		List<Predicate> goalPredicates = heuristic.heuristicPushOrder(
				finalState.getPredicates().toList(), currentState);
		for (Predicate singlePred : goalPredicates) {
			stack.push(singlePred);
		}

		while (!stack.isEmpty()) {
			this.output.println("-----");
			// Look at element on top of stack:
			StripsElement currentElement = stack.pop();

			if (currentElement instanceof Operator) {
				// If element is an operator:
				Operator operator = (Operator) currentElement;
				// Apply operator to current state:
				currentState.applyOperator(operator);
				// Add operator to plan:
				plan.addOperator(operator);

			} else if (currentElement instanceof ConjunctivePredicate) {
				// If element is a list of predicates:
				ConjunctivePredicate conjPred = (ConjunctivePredicate) currentElement;
				// TODO: report: we don't add a heuristic here because in the
				// coffee
				// problem this never happens.

				// Push all predicates from list that are not true in current
				// state to the stack:
				for (Predicate falsePred : currentState
						.getFalseSinglePredicates(conjPred)) {
					stack.push(falsePred);
				}
			} else if (currentElement instanceof Predicate) {
				// If element is a single predicate:
				Predicate singlePred = (Predicate) currentElement;
				if (singlePred.isFullyInstantiated()) {
					// If predicate is fully instantiated:
					if (!currentState.isTrue(singlePred)) {
						// If predicate is not true in current state:
						// Find an operator to resolve the predicate
						Operator operator = findOperatorToResolve(singlePred);
						// Push the operator
						stack.push(operator);
						ConjunctivePredicate preconditions = operator
								.getPreconditions();
						// Push a list of preconditions of the operator:
						stack.push(preconditions);
						// Push each single precondition:
						// TODO: report: don't change order here because it
						// changes the semantics of the operator and can make
						// the problem unsolvable. The order of preconditions
						// should not be changed for our three operators.
						for (Predicate preconPart : preconditions.toList()) {
							stack.push(preconPart);
						}
						// if single predicate and true in current state: do
						// nothing
					}
				} else {
					// if single predicate not instantiated:
					// find constant to instantiate the variables and set this
					// constant in entire stack:
					instantiate(singlePred, heuristic);
					this.output.println(" --->\t" + singlePred);
				}
			}
		}
		this.output.println("-----");
		return plan;

	}

	private void instantiate(Predicate singlePred, Heuristic heuristic) {

		// TODO: heuristic choice if more than one possible instantiation.
		// choose the nearest machine using manhattan distance
		// second step: remember the next petition and use distance both to
		// machine and from machine to that petition.

		List<Predicate> compatiblePredicates = new ArrayList<>();
		for (Predicate currPred : this.currentState.getPredicates().toList()) {
			// find all compatible predicates in current state:
			if (currPred.isCompatibleTo(singlePred)) {
				compatiblePredicates.add(currPred);
			}
		}

		if (compatiblePredicates.isEmpty()) {
			throw new RuntimeException("There was no compatible predicate found to resolve the predicate "
							+ singlePred);
		}

		// Heuristic chooses one candidate from compatiblePredicates
		Predicate chosenPred = heuristic.choosePredicateForInstantiation(
				compatiblePredicates, currentState);

		// instantiate with singlePred with constants of chosenPred
		for (int i = 0; i < chosenPred.getValence(); i++) {
			if (!singlePred.getArgument(i).isInstantiated()) {
				// instantiate updates the java object of the variable.
				// all references to this variable in other predicates
				// will now reference to the instantiated variable.
				singlePred.getArgument(i).instantiate(
						chosenPred.getArgument(i).getValue());
			}
		}

	}

	private Operator findOperatorToResolve(Predicate predToResolve)
			throws Exception {
		// Finds an operator that has a compatible precondition in its add-list
		// to resolve predToResolve:
		for (Operator op : this.operators) {
			Operator opCopy = op.getClass().newInstance();
			for (Predicate predCandidate : opCopy.getAdd().toList()) {
				if (predCandidate.isCompatibleTo(predToResolve)) {
					// instantiate opCopy with predToResolve
					for (int i = 0; i < predToResolve.getValence(); i++) {
						if (!predCandidate.getArgument(i).isInstantiated()) {
							predCandidate.getArgument(i).instantiate(
									predToResolve.getArgument(i).getValue());
						}
					}
					return opCopy;
				}

			}
		}
		throw new RuntimeException(
				"There was no operator found to resolve a predicate. There is no possible plan.");
	}

	public void setOutput(OutputStream outputStream){
		output = new PrintStream(outputStream);
		stack.setOutput(output);
	}
}
