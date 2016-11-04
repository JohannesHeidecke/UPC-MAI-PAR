package planner;

import java.util.ArrayList;
import java.util.List;

import model.ConjunctivePredicate;
import model.Operator;
import model.Plan;
import model.STRIPElement;
import model.SinglePredicate;
import model.State;
import model.Variable;

public class LinearPlanner {

	private List<Operator> operators;
	private State initialState, finalState, currentState;
	private LinearPlannerStack stack;

	public LinearPlanner(State initialState, State finalState, List<Operator> operators) {

		this.initialState = initialState;
		this.currentState = initialState;
		this.finalState = finalState;
		this.operators = operators;

		stack = new LinearPlannerStack();

	}

	public Plan calculatePlan() throws Exception {

		// Calculate plan according to STRIP algorithm (linear planner with goal
		// stack):

		Plan plan = new Plan();

		// Push goal predicates from final state as list:
		stack.push(finalState.getPredicates());
		// Push individual goal predicates:
		for (SinglePredicate singlePred : finalState.getPredicates().getSinglePredicates()) {
			stack.push(singlePred);
		}

		while (!stack.isEmpty()) {
			System.out.println("-----");
			// Look at element on top of stack:
			STRIPElement currentElement = stack.pop();

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
				// TODO: add heuristic ordering
				// Push all predicates from list that are not true in current
				// state to the stack:
				for (SinglePredicate falsePred : currentState.getFalseSinglePredicates(conjPred)) {
					stack.push(falsePred);
				}
			} else if (currentElement instanceof SinglePredicate) {
				// If element is a single predicate:
				SinglePredicate singlePred = (SinglePredicate) currentElement;
				if (singlePred.isFullyInstantiated()) {
					// If predicate is fully instantiated:
					if (!currentState.isTrue(singlePred)) {
						// If predicate is not true in current state:
						// Find an operator to resolve the predicate
						Operator operator = findOperatorToResolve(singlePred);
						// Push the operator
						stack.push(operator);
						ConjunctivePredicate preconditions = operator.getPreconditions();
						// Push a list of preconditions of the operator:
						stack.push(preconditions);
						// Push each single precondition:
						// TODO: heuristic ordering
						for (SinglePredicate preconPart : preconditions.getSinglePredicates()) {
							stack.push(preconPart);
						}
						// if single predicate and true in current state: do
						// nothing
					}
				} else {
					// if single predicate not instantiated:
					// find constant to instantiate the variables and set this
					// constant in entire stack:
					instantiate(singlePred);
					// TODO: think about, if we actually need the next line:
					stack.push(currentElement);
				}
			}
		}
		System.out.println("-----");
		return plan;

	}

	private void instantiate(SinglePredicate singlePred) {
		
		//TODO: heuristic choice if more than one possible instantiation.

		for (SinglePredicate currPred : this.currentState.getPredicates().getSinglePredicates()) {
			// find a compatible predicate in current state:
			if (currPred.isCompatibleTo(singlePred)) {
				// instantiate with constants in predicate found in current
				// state:
				for (int i = 0; i < currPred.getValence(); i++) {
					if (!singlePred.getArgument(i).isInstantiated()) {
						singlePred.getArgument(i).instantiate(currPred.getArgument(i).getValue());
					}
				}
				return;
			}
		}

	}

	private Operator findOperatorToResolve(SinglePredicate predToResolve) throws Exception {
		// Finds an operator that has a compatible precondition in its add-list
		// to resolve predToResolve:
		for (Operator op : this.operators) {
			Operator opCopy = op.getClass().newInstance();
			for (SinglePredicate predCandidate : opCopy.getAdd().getSinglePredicates()) {
				if (predCandidate.isCompatibleTo(predToResolve)) {
					// instantiate opCopy with predToResolve
					for (int i = 0; i < predToResolve.getValence(); i++) {
						if (!predCandidate.getArgument(i).isInstantiated()) {
							predCandidate.getArgument(i).instantiate(predToResolve.getArgument(i).getValue());
						}
					}
					return opCopy;
				}

			}
		}
		throw new Exception("There was no operator found to resolve a predicate. There is no possible plan.");
	}

}
