package model;

public abstract class Variable {

	protected Object value;

	public Variable(Object value) {
		this.value = value;
	}

	public boolean isInstantiated() {
		return !(value == null);
	}

	public void instantiate(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return this.value;
	}

	@Override
	public abstract boolean equals(Object o);
	

}
