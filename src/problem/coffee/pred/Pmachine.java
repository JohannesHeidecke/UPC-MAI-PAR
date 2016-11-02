package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.SinglePredicate;
import model.Variable;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;

public class Pmachine extends SinglePredicate {
	
	public Pmachine(Location o, PositiveInteger n) {
		super("machine", new ArrayList<Variable>(Arrays.asList(o, n)));
	}

}
