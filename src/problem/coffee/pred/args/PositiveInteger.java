package problem.coffee.pred.args;

import model.Variable;

public class PositiveInteger extends Variable {

	public PositiveInteger(Integer value) {
		//TODO: check if positive
		super(value);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PositiveInteger) {
			PositiveInteger i = (PositiveInteger) o;
			if (this.value.equals(i.value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}

}
