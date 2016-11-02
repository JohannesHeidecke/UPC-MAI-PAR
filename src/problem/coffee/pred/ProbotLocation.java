package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.SinglePredicate;
import model.Variable;
import problem.coffee.pred.args.Location;

public class ProbotLocation extends SinglePredicate {

	public ProbotLocation(Location o) {
		super("robotLocation", new ArrayList<Variable>(Arrays.asList(o)));
	}

}
