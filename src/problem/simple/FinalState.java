package problem.simple;

import model.ConjunctivePredicate;
import model.SinglePredicate;
import model.State;
import problem.simple.pred.PisAt;
import problem.simple.pred.args.Location;

public class FinalState extends State {
	
	
	public FinalState() {
		super(null);
		ConjunctivePredicate predicates = new ConjunctivePredicate();
		
		SinglePredicate isAt = new PisAt(new Location("Barcelona"));
		predicates.add(isAt);
		
		this.predicates = predicates;
	}

}
