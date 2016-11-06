package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConjunctivePredicate extends STRIPElement {

	// The predicates that are part of the conjunction:
	private List<Predicate> predicates;

	public ConjunctivePredicate() {
		super();
		predicates = new ArrayList<>();
	}

	public ConjunctivePredicate(ConjunctivePredicate conjuction) {
		// shallow copy
		this();
		predicates.addAll(conjuction.predicates);
	}
	
	public List<Predicate> toList() {
		return this.predicates;
	}

	public void add(Predicate singlePred) {
		this.predicates.add(singlePred);
	}

	public void remove(Predicate singlePred) {
		// Remove first occurrence of a SinglePredicate that is compatible to
		// singlePred from conjunction:
		for (Iterator<Predicate> iterator = predicates.iterator(); iterator.hasNext();) {
			Predicate nextPred = iterator.next();
			if (nextPred.isCompatibleTo(singlePred)) {
				iterator.remove();
				return;
			}
		}
	}

	@Override
	public String toString() {
		String result = "{";
		if (this.predicates != null) {
			for (Predicate pred : this.predicates) {
				result += pred.toString() + ", ";
			}
			// delete ", " in the end:
			result = result.substring(0, result.length() - 2);
		}
		return result + "}";
	}

}
