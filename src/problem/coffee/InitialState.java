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

public class InitialState extends State {

	public InitialState() {
		super(null);
		
		ConjunctivePredicate predicates = new ConjunctivePredicate();

		Predicate robotLocation = new ProbotLocation(new Location(new Coordinate(1, 1)));
		Predicate robotFree = new ProbotFree();
		Predicate machine1 = new Pmachine(new Location(new Coordinate(1, 4)), 
				new PositiveInteger(new Integer(3)));
		Predicate machine2 = new Pmachine(new Location(new Coordinate(2, 2)), 
				new PositiveInteger(new Integer(1)));
		Predicate machine3 = new Pmachine(new Location(new Coordinate(4, 3)), 
				new PositiveInteger(new Integer(2)));
		Predicate machine4 = new Pmachine(new Location(new Coordinate(4, 5)), 
				new PositiveInteger(new Integer(1)));
		Predicate machine5 = new Pmachine(new Location(new Coordinate(6, 1)), 
				new PositiveInteger(new Integer(2)));
		
		Predicate petition1 = new Ppetition(new Location(new Coordinate(1, 3)), 
				new PositiveInteger(new Integer(1)));
		Predicate petition2 = new Ppetition(new Location(new Coordinate(2, 5)), 
				new PositiveInteger(new Integer(3)));
		Predicate petition3 = new Ppetition(new Location(new Coordinate(2, 6)), 
				new PositiveInteger(new Integer(1)));
		Predicate petition4 = new Ppetition(new Location(new Coordinate(3, 1)), 
				new PositiveInteger(new Integer(2)));
		Predicate petition5 = new Ppetition(new Location(new Coordinate(5, 1)), 
				new PositiveInteger(new Integer(2)));
		Predicate steps = new Psteps(new PositiveInteger(new Integer(0)));
		
		
		predicates.add(robotLocation);
		predicates.add(robotFree);
		predicates.add(machine1);
		predicates.add(machine2);
		predicates.add(machine3);
		predicates.add(petition1);
		predicates.add(petition2);
		predicates.add(petition3);
		predicates.add(petition4);
		predicates.add(petition5);
		predicates.add(steps);
		
		this.predicates = predicates;
	}

}
