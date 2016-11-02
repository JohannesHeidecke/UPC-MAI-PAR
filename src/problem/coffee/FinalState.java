package problem.coffee;

import model.ConjunctivePredicate;
import model.SinglePredicate;
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

	public FinalState() {
		ConjunctivePredicate predicates = new ConjunctivePredicate();

		SinglePredicate served1 = new Pserved(new Location(new Coordinate(4, 1)));
		predicates.add(served1);
		
		this.predicates = predicates;
	}

}
