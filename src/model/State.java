package model;

import java.util.ArrayList;
import java.util.List;

public class State {

	protected ConjunctivePredicate predicates;

	public ConjunctivePredicate getPredicates() {
		return this.predicates;
	}

	public void applyOperator(Operator operator) {
		
		operator.apply();
		
		// Add all predicates from operator's add list:
		for (SinglePredicate addPred : operator.getAdd().getSinglePredicates()) {
			this.predicates.add(addPred);
		}

		// Remove all predicates from operator's delete list:
		for (SinglePredicate delPred : operator.getDel().getSinglePredicates()) {
			this.predicates.remove(delPred);
		}
	}

	public List<SinglePredicate> getFalseSinglePredicates(ConjunctivePredicate conjPred) {
		// returns all predicates from conjPred, that are not true in this
		// state:
		List<SinglePredicate> falsePredicates = new ArrayList<SinglePredicate>();
		for (SinglePredicate testPred : conjPred.getSinglePredicates()) {
			if (!this.isTrue(testPred)) {
				falsePredicates.add(testPred);
			}
		}
		return falsePredicates;
	}

	public boolean isTrue(SinglePredicate pred) {
		// Check if pred is true in this state:
		// TODO: replace with exception or delete
		if (!pred.isFullyInstantiated()) {
			System.out.println("WARNING!");
		}

		for (SinglePredicate myPred : predicates.getSinglePredicates()) {
			if (myPred.isCompatibleTo(pred)) {
				return true;
			}
		}

		return false;
	}

}
