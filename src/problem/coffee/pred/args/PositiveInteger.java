package problem.coffee.pred.args;

import model.Variable;

public class PositiveInteger extends Variable {

	public PositiveInteger(Integer value) {
		super(value);
	}
	
	public Integer getIntValue() {
		return (Integer) this.getValue();
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
		if (this.value != null) {
			return this.value.toString();
		} else {
			return "_";
		}
		
	}

}
