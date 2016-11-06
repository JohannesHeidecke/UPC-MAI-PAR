package problem.coffee.pred;

import java.util.ArrayList;
import java.util.Arrays;

import model.Predicate;
import model.Variable;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;

public class Pmachine extends Predicate {
	
	public final static String ID = "Machine";
	
	public Pmachine(Location o, PositiveInteger n) {
		super(ID, new ArrayList<Variable>(Arrays.asList(o, n)));
	}
	
	public Location getLocation() {
		return (Location) super.getArgument(0);
	}

	public PositiveInteger getN()
	{
		return (PositiveInteger) super.getArgument(1);
	}
}
