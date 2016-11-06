package problem.simple;

import model.ConjunctivePredicate;
import model.Predicate;
import model.State;
import problem.simple.pred.PisAt;
import problem.simple.pred.args.Location;

public class InitialState extends State {
	
	
	public InitialState() {
		super(null);
		ConjunctivePredicate predicates = new ConjunctivePredicate();
		
		Predicate isAt = new PisAt(new Location("Paris"));
		predicates.add(isAt);
		
		this.predicates = predicates;
	}

}
