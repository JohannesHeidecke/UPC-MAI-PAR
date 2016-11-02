package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.SinglePredicate;
import model.Variable;
import problem.coffee.pred.args.PositiveInteger;

public class ProbotLoaded extends SinglePredicate {

	public ProbotLoaded(PositiveInteger n) {
		super("robotLoaded", new ArrayList<Variable>(Arrays.asList(n)));
	}

}
