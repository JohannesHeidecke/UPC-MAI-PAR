package problem.coffee.operators;

import model.ConjunctivePredicate;
import model.Operator;
import model.Predicate;
import problem.coffee.pred.Pmachine;
import problem.coffee.pred.ProbotFree;
import problem.coffee.pred.ProbotLoaded;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;

public class Omake extends Operator {
	
	private Location o;
	private PositiveInteger n;
	
	public Omake() {
		
		o = new Location(null);
		n = new PositiveInteger(null);
		
		ConjunctivePredicate preconditions = new ConjunctivePredicate();
		Predicate robotFree = new ProbotFree();
		preconditions.add(robotFree);
		Predicate robotLocation = new ProbotLocation(o);
		preconditions.add(robotLocation);
		Predicate machine = new Pmachine(o, n);
		preconditions.add(machine);
		this.preconditions = preconditions;
		
		ConjunctivePredicate add = new ConjunctivePredicate();
		Predicate robLoaded = new ProbotLoaded(n);
		add.add(robLoaded);
		this.add = add;
		
		ConjunctivePredicate delete = new ConjunctivePredicate();
		delete.add(robotFree);
		this.delete = delete;
	
	}
	
	@Override
	public String toString() {
		return "Make "+n.toString()+" cups at office "+o.toString();
	}


}
