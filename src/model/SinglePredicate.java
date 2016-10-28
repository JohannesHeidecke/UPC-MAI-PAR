package model;

import java.util.List;

public class SinglePredicate extends Predicate {

	private String identifier;
	private List<Variable> arguments;

	public SinglePredicate(String identifier, List<Variable> arguments) {
		this.identifier = identifier;
		this.arguments = arguments;
	}

	public SinglePredicate(String identifier) {
		this.identifier = identifier;
	}

	public boolean isFullyInstantiated() {
		// Test if all arguments are instantiated (have a value):
		if (this.getValence() != 0) {
			for (Variable argument : arguments) {
				if (!argument.isInstantiated()) {
					return false;
				}
			}
		}
		return true;
	}

	public int getValence() {
		// Valence = number of arguments
		if (arguments == null) {
			return 0;
		}

		else {
			return arguments.size();
		}
	}

	public boolean isCompatibleTo(SinglePredicate other) {
		// Returns if predicates are equal or could be made equal via
		// instantiation of not instantiated arguments:
		if (!this.identifier.equals(other.identifier)) {
			return false;
		}
		if (this.getValence() != other.getValence()) {
			return false;
		}
		for (int i = 0; i < this.getValence(); i++) {
			Variable thisVar = this.arguments.get(i);
			Variable otherVar = other.arguments.get(i);
			if (thisVar.isInstantiated() && otherVar.isInstantiated()) {
				if (!thisVar.equals(otherVar)) {
					return false;
				}
			}
		}

		return true;
	}

}
