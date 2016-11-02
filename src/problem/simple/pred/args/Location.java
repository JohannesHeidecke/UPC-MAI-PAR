package problem.simple.pred.args;

import model.Variable;

public class Location extends Variable {

	public Location(String value) {
		super(value);
	}
	
	@Override
	public String toString() {
		if (this.value == null) {
			return "null";
		}
		return this.value.toString();
	}

}
