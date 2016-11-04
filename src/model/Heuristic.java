package model;

import java.util.List;

public abstract class Heuristic {
	
	public abstract List<Predicate> heuristicPushOrder(List<Predicate> predicates, State currentState);
	
	public abstract Predicate choosePredicateForInstantiation(List<Predicate> compatiblePredicates, State currentState);

}
