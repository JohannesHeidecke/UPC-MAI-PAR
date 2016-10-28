package planner;

import model.ConjunctivePredicate;
import model.Operator;
import model.Plan;
import model.STRIPElement;
import model.SinglePredicate;
import model.State;

public class LinearPlanner {

	public static void main(String[] args) {

		State initialState = new State();
		State finalState = new State();

		Plan plan = new Plan();
		LinearPlannerStack stack = new LinearPlannerStack();
		// TODO: this should be a copy of initialState, not a pointer
		State currentState = initialState;

		stack.push(finalState.getPredicates());
		for (SinglePredicate singlePred : finalState.getPredicates()
				.getSinglePredicates()) {
			stack.push(singlePred);
		}

		while (!stack.isEmpty()) {
			STRIPElement currentElement = stack.pop();

			if (currentElement instanceof Operator) {
				Operator operator = (Operator) currentElement;
				currentState.applyOperator(operator);
				plan.addOperator(operator);
			} else if (currentElement instanceof ConjunctivePredicate) {
				ConjunctivePredicate conjPred = (ConjunctivePredicate) currentElement;
				// TODO: add heuristic ordering
				for (SinglePredicate falsePred : currentState
						.getFalseSinglePredicates(conjPred)) {
					stack.push(falsePred);
				}
			} else if (currentElement instanceof SinglePredicate) {
				SinglePredicate singlePred = (SinglePredicate) currentElement;
				if (singlePred.isFullyInstantiated()) {
					if(!currentState.isTrue(singlePred)) {
						Operator operator = findOperatorToResolve(singlePred);
						stack.push(operator);
						ConjunctivePredicate preconditions = operator.getPreconditions();
						stack.push(preconditions);
						for (SinglePredicate preconPart : preconditions.getSinglePredicates()) {
							stack.push(preconPart);
						}
						
					}
				} else {
					instantiate(currentState, stack, singlePred);
				}
			}

		}



	}

	private static void instantiate(State currentState,
			LinearPlannerStack stack, SinglePredicate singlePred) {
		// TODO Auto-generated method stub
		
	}

	private static Operator findOperatorToResolve(SinglePredicate singlePred) {
		// TODO Auto-generated method stub
		return null;
	}

}
