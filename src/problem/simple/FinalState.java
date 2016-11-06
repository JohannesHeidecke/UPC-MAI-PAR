package problem.simple;

import model.Predicate;
import model.State;
import problem.simple.pred.PisAt;
import problem.simple.pred.args.Location;

public class FinalState extends State {
	
	
	public FinalState() {
		super();
		Predicate isAt = new PisAt(new Location("Barcelona"));
		predicates.add(isAt);		
	}

}
