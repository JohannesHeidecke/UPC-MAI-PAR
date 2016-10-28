package model;

import java.util.Set;

public class Operator extends STRIPElement {
	
	private ConjunctivePredicate preconditions;
	private Set<Predicate> add;
	private Set<Predicate> delete;
	
	
	public ConjunctivePredicate getPreconditions() {
		// TODO Auto-generated method stub
		return preconditions;
	}

}
