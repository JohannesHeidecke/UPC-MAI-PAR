package problem.simple.pred;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.SinglePredicate;
import model.Variable;

public class PisAt extends SinglePredicate {

	public PisAt(Variable location) {
		super("isAt", new ArrayList<Variable>(Arrays.asList(location)));
	}
	
}
