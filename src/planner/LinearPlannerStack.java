package planner;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Stack;

import model.STRIPElement;

public class LinearPlannerStack {
	
	private Stack<STRIPElement> stack;
	private PrintStream output;
	
	public LinearPlannerStack() {
		this.stack = new Stack<>();
		this.output = System.out;
	}
	
	
	public void push(STRIPElement el) {
		this.stack.push(el);
		output.println("PUSH: \t" +el.toString());
	}

	public boolean isEmpty() {
		return this.stack.isEmpty();
	}

	public STRIPElement pop() {
		STRIPElement currentElement = this.stack.pop();
		output.println(" POP: \t" +currentElement.toString());
		return currentElement;
	}
	
	public void setOutput(OutputStream outputStream){
		output = new PrintStream(outputStream);
	}
}
