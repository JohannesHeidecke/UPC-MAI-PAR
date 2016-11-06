package planner;

import java.util.Stack;

import model.StripsElement;

public class StripsStack {
	
	Stack<StripsElement> stack;
	
	public StripsStack() {
		this.stack = new Stack<>();
	}
	
	
	public void push(StripsElement el) {
		this.stack.push(el);
		System.out.println("PUSH: \t" +el.toString());
	}

	public boolean isEmpty() {
		return this.stack.isEmpty();
	}

	public StripsElement pop() {
		StripsElement currentElement = this.stack.pop();
		System.out.println(" POP: \t" +currentElement.toString());
		return currentElement;
	}
	
}
