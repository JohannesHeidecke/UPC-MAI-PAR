package model;

public class Variable {

	Object value;

	public Variable() {
	}

	public Variable(Object value) {
		this.value = value;
	}

	public boolean isInstantiated() {
		return !(value == null);
	}

	public void instantiate(Object value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		// Check if two variables are both uninstantiated or both have the same
		// value:
		if (o instanceof Variable) {
			if (this.value == null || ((Variable) o).value == null) {
				return this.value == ((Variable) o).value;
			}
			if (this.value.equals(((Variable) o).value)) {
				return true;
			}
		}

		return false;
	}

}
