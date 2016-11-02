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

	@Override
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location l = (Location) o;
			if (this.value.equals(l.value)) {
				return true;
			}
		}
		return false;
	}

}
