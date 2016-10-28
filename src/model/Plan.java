package model;

import java.util.ArrayList;
import java.util.List;

public class Plan {
	
	private List<Operator> operators;
	
	public Plan() {
		this.operators = new ArrayList<Operator>();
	}

	public void addOperator(Operator operator) {
		
		this.operators.add(operator);
		
	}
	
	

}
