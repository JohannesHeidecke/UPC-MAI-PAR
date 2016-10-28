package planner;

import java.util.Stack;

import model.STRIPElement;

public class LinearPlannerStack {
	
	Stack<STRIPElement> stack;
	
	public LinearPlannerStack() {
		this.stack = new Stack<>();
	}
	
	
	public void push(STRIPElement el) {
		this.stack.push(el);
	}

	public boolean isEmpty() {
		return this.stack.isEmpty();
	}

	public STRIPElement pop() {
		return this.stack.pop();
	}
	
}
