package problem.simple.pred;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Predicate;
import model.Variable;

public class PisAt extends Predicate {

	public PisAt(Variable location) {
		super("isAt", new ArrayList<Variable>(Arrays.asList(location)));
	}
	
}
