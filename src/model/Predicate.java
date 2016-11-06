package model;

import java.util.ArrayList;
import java.util.List;

public class Predicate extends StripsElement {

	private String identifier;
	private List<Variable> arguments;

	public Predicate(String identifier, List<Variable> arguments) {
		this.identifier = identifier;
		this.arguments = arguments;
	}

	public Predicate(String identifier) {
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

	public boolean isCompatibleTo(Predicate other) {
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

	public Variable getArgument(int index) {
		return this.arguments.get(index);
	}
	
	public String getIdentifier() {
		return this.identifier;
	}

	@Override
	public String toString() {
		String result = this.identifier + "(";
		if (arguments != null) {
			for(Variable arg : arguments) {
				result += arg.toString()+", ";
			}
			// delete ", " in the end:
			result = result.substring(0, result.length()-2);
		}
		result += ")";
		return result;
	}

}
