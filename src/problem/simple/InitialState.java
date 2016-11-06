package problem.simple;

import model.Predicate;
import model.State;
import problem.simple.pred.PisAt;
import problem.simple.pred.args.Location;

public class InitialState extends State {
	
	
	public InitialState() {
		super();
		
		Predicate isAt = new PisAt(new Location("Paris"));
		predicates.add(isAt);
	}
}
