package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.Predicate;
import model.Variable;
import problem.coffee.pred.args.Location;

public class Pserved extends Predicate {
	
	public final static String ID = "Served";
	
	public Pserved(Location o) {
		super(ID, new ArrayList<Variable>(Arrays.asList(o)));
	}
	
	public Location getLocation() {
		return (Location) super.getArgument(0);
	}

}
