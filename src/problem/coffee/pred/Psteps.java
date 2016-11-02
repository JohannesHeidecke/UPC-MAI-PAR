package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.SinglePredicate;
import model.Variable;
import problem.coffee.pred.args.PositiveInteger;

public class Psteps extends SinglePredicate {
	
	public Psteps(PositiveInteger x) {
		super("machine", new ArrayList<Variable>(Arrays.asList(x)));
	}

}
