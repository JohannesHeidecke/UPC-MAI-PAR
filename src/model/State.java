package model;

import java.util.ArrayList;
import java.util.List;

public class State {

	protected ConjunctivePredicate predicates;

	public State() {
		// shallow copy
		this.predicates = new ConjunctivePredicate();
 	}
	
	public State(State state) {
		this();
		// shallow copy
		this.predicates = new ConjunctivePredicate(state.predicates);
 	}

	public State(ConjunctivePredicate predicates) {
		this.predicates = predicates;
 	}
	
	public ConjunctivePredicate getPredicates() {
		return this.predicates;
	}

	public void applyOperator(Operator operator) {
		
		operator.apply();
		
		// Add all predicates from operator's add list:
		for (Predicate addPred : operator.getAdd().toList()) {
			this.predicates.add(addPred);
		}

		// Remove all predicates from operator's delete list:
		for (Predicate delPred : operator.getDel().toList()) {
			this.predicates.remove(delPred);
		}
	}

	public List<Predicate> getFalseSinglePredicates(ConjunctivePredicate conjPred) {
		// returns all predicates from conjPred, that are not true in this
		// state:
		List<Predicate> falsePredicates = new ArrayList<Predicate>();
		for (Predicate testPred : conjPred.toList()) {
			if (!this.isTrue(testPred)) {
				falsePredicates.add(testPred);
			}
		}
		return falsePredicates;
	}

	public boolean isTrue(Predicate pred) {
		// Check if pred is true in this state:
		if (!pred.isFullyInstantiated()) {
			System.out.println("WARNING! Predicate "+pred.toString()+" is not fully instantiated.");
		}

		for (Predicate myPred : predicates.toList()) {
			if (myPred.isCompatibleTo(pred)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {		
		return "State: "+ predicates.toString();
	}
}