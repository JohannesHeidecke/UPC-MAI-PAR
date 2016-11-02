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
		System.out.println("PUSH: \t" +el.toString());
	}

	public boolean isEmpty() {
		return this.stack.isEmpty();
	}

	public STRIPElement pop() {
		STRIPElement currentElement = this.stack.pop();
		System.out.println(" POP: \t" +currentElement.toString());
		return currentElement;
	}
	
}
