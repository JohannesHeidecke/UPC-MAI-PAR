package model;

import java.util.List;

public class ConjunctivePredicate extends Predicate {
	
	private List<SinglePredicate> predicates;
	
	public List<SinglePredicate> getSinglePredicates() {
		return this.predicates;
	}


}
