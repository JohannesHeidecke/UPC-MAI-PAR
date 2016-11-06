package planner;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Stack;

import model.StripsElement;

public class StripsStack {
	
	Stack<StripsElement> stack;
	private PrintStream output;
	
	public StripsStack() {
		this.stack = new Stack<>();
		this.output = System.out;
	}
	
	
	public void push(StripsElement el) {
		this.stack.push(el);
		output.println("PUSH: \t" +el.toString());
	}

	public boolean isEmpty() {
		return this.stack.isEmpty();
	}

	public StripsElement pop() {
		StripsElement currentElement = this.stack.pop();
		output.println(" POP: \t" +currentElement.toString());
		return currentElement;
	}
	
	public void setOutput(OutputStream outputStream){
		output = new PrintStream(outputStream);
	}
}
