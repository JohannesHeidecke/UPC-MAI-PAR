package problem.coffee.operators;

import model.ConjunctivePredicate;
import model.Operator;
import model.Predicate;
import problem.coffee.pred.Ppetition;
import problem.coffee.pred.ProbotFree;
import problem.coffee.pred.ProbotLoaded;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.Pserved;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;

public class OServe extends Operator {
	
	private Location o;
	private PositiveInteger n;
	
	public OServe() {
		o = new Location(null);
		n = new PositiveInteger(null);
		
		ConjunctivePredicate preconditions = new ConjunctivePredicate();
		Predicate robotLocation = new ProbotLocation(o);
		preconditions.add(robotLocation);
		Predicate robotLoaded = new ProbotLoaded(n);
		preconditions.add(robotLoaded);
		Predicate petition = new Ppetition(o, n);
		preconditions.add(petition);
		this.preconditions = preconditions;
		
		ConjunctivePredicate add = new ConjunctivePredicate();
		Predicate served = new Pserved(o);
		add.add(served);
		Predicate robotFree = new ProbotFree();
		add.add(robotFree);
		this.add = add;
		
		ConjunctivePredicate delete = new ConjunctivePredicate();
		delete.add(petition);
		delete.add(robotLoaded);
		this.delete = delete;
		
		
	}
	
	@Override
	public String toString() {
		return "Serve "+n.toString()+" cups at office "+o.toString();
	}

}
