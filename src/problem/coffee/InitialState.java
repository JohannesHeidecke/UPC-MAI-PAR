package problem.coffee;

import model.ConjunctivePredicate;
import model.SinglePredicate;
import model.State;
import problem.coffee.pred.Pmachine;
import problem.coffee.pred.Ppetition;
import problem.coffee.pred.ProbotFree;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.Psteps;
import problem.coffee.pred.args.Coordinate;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;

public class InitialState extends State {

	public InitialState() {
		ConjunctivePredicate predicates = new ConjunctivePredicate();

		SinglePredicate robotLocation = new ProbotLocation(new Location(new Coordinate(1, 1)));
		SinglePredicate robotFree = new ProbotFree();
		SinglePredicate machine1 = new Pmachine(new Location(new Coordinate(2, 3)), 
				new PositiveInteger(new Integer(2)));
		SinglePredicate petition1 = new Ppetition(new Location(new Coordinate(4, 1)), 
				new PositiveInteger(new Integer(2)));
		SinglePredicate steps = new Psteps(new PositiveInteger(new Integer(0)));
		
		predicates.add(robotLocation);
		predicates.add(robotFree);
		predicates.add(machine1);
		predicates.add(petition1);
		predicates.add(steps);
		
		this.predicates = predicates;
	}

}
