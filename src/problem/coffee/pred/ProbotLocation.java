package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.Predicate;
import model.Variable;
import problem.coffee.pred.args.Location;

public class ProbotLocation extends Predicate {

	public ProbotLocation(Location o) {
		super("robotLocation", new ArrayList<Variable>(Arrays.asList(o)));
	}

}
