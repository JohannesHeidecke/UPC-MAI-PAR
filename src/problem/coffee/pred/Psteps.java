package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.Predicate;
import model.Variable;
import problem.coffee.pred.args.PositiveInteger;

public class Psteps extends Predicate {
	
	public Psteps(PositiveInteger x) {
		super("steps", new ArrayList<Variable>(Arrays.asList(x)));
	}

}
