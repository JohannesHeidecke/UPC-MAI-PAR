package problem.coffee;

import model.ConjunctivePredicate;
import model.Predicate;
import model.State;
import problem.coffee.pred.Pmachine;
import problem.coffee.pred.Ppetition;
import problem.coffee.pred.ProbotFree;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.Pserved;
import problem.coffee.pred.Psteps;
import problem.coffee.pred.args.Coordinate;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;

public class FinalState extends State {
	
//	TODO: delete?

	public FinalState() {
		super(null);
		
		ConjunctivePredicate predicates = new ConjunctivePredicate();

		Predicate served1 = new Pserved(new Location(new Coordinate(1, 3)));
		predicates.add(served1);
		Predicate served2 = new Pserved(new Location(new Coordinate(2, 5)));
		predicates.add(served2);
		Predicate served3 = new Pserved(new Location(new Coordinate(2, 6)));
		predicates.add(served3);
		Predicate served4 = new Pserved(new Location(new Coordinate(3, 1)));
		predicates.add(served4);
		Predicate served5 = new Pserved(new Location(new Coordinate(5, 1)));
		predicates.add(served5);

		this.predicates = predicates;
	}

}
