package model;

import java.util.List;

public class State {
	
	private ConjunctivePredicate predicates;
	
	
	public ConjunctivePredicate getPredicates() {
		return this.predicates;
	}
	
	public void applyOperator(Operator operator) {
		//TODO: implement
	}
	
	public List<SinglePredicate> getFalseSinglePredicates(ConjunctivePredicate conjPred) {
		return null;
		//return all single predicates in conj predicate, that are not true in this state
	}

	public boolean isTrue(Predicate pred) {
		// TODO Auto-generated method stub

		return false;
	}
	
}
