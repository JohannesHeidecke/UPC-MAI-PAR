package problem.simple;

import model.ConjunctivePredicate;
import model.SinglePredicate;
import model.State;
import problem.simple.pred.PisAt;
import problem.simple.pred.args.Location;

public class InitialState extends State {
	
	
	public InitialState() {
		ConjunctivePredicate predicates = new ConjunctivePredicate();
		
		SinglePredicate isAt = new PisAt(new Location("Paris"));
		predicates.add(isAt);
		
		this.predicates = predicates;
	}

}
