package model;

public abstract class Operator extends STRIPElement {
	
	protected ConjunctivePredicate preconditions;
	protected ConjunctivePredicate add;
	protected ConjunctivePredicate delete;
	
	
	public ConjunctivePredicate getPreconditions() {
		return this.preconditions;
	}
	
	public ConjunctivePredicate getAdd() {
		return this.add;
	}

	public ConjunctivePredicate getDel() {
		return this.delete;
	}

	public void apply() {
		
	}
	

}
