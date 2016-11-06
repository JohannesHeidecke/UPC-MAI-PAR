package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.Predicate;
import model.Variable;
import problem.coffee.pred.args.PositiveInteger;

public class ProbotLoaded extends Predicate {

	public ProbotLoaded(PositiveInteger n) {
		super("robotLoaded", new ArrayList<Variable>(Arrays.asList(n)));
	}

}
