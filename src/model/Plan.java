package model;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Plan {
	
	private List<Operator> operators;
	private PrintStream output;
	
	public Plan() {
		this.operators = new ArrayList<Operator>();
		output = System.out;
	}

	public void addOperator(Operator operator) {
		
		this.operators.add(operator);
		output.println("PLAN:\t"+operator.toString());
		
	}

	public Integer getSize() {
		return operators.size();		
	}

	public List<Operator> getOperators() {
		return operators;
	}
	
	@Override
	public String toString() {
		String result = "PLAN:\n";
		
		for (Operator op : operators) {
			result += "\t"+op.toString()+"\n";
		}
		
		return result;
	}

	public void setOutput(OutputStream outputStream){
		output = new PrintStream(outputStream);
	}
}
