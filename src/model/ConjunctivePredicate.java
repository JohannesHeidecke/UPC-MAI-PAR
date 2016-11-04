package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConjunctivePredicate extends STRIPElement {

	// The predicates that are part of the conjunction:
	private List<SinglePredicate> predicates;

	public ConjunctivePredicate() {
		predicates = new ArrayList<>();
	}

	public List<SinglePredicate> getSinglePredicates() {
		return this.predicates;
	}

	public void add(SinglePredicate singlePred) {
		this.predicates.add(singlePred);
	}

	public void remove(SinglePredicate singlePred) {
		// Remove first occurrence of a SinglePredicate that is compatible to
		// singlePred from conjunction:
		for (Iterator<SinglePredicate> iterator = predicates.iterator(); iterator.hasNext();) {
			SinglePredicate nextPred = iterator.next();
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
			for (SinglePredicate pred : this.predicates) {
				result += pred.toString() + ", ";
			}
			// delete ", " in the end:
			result = result.substring(0, result.length() - 2);
		}
		return result + "}";
	}

}
