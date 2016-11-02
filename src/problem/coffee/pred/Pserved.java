package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.SinglePredicate;
import model.Variable;
import problem.coffee.pred.args.Location;

public class Pserved extends SinglePredicate {
	
	public Pserved(Location o) {
		super("served", new ArrayList<Variable>(Arrays.asList(o)));
	}

}
