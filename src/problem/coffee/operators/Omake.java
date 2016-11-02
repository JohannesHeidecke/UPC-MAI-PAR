package problem.coffee.operators;

import model.ConjunctivePredicate;
import model.Operator;
import model.SinglePredicate;
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
		SinglePredicate robotLocation = new ProbotLocation(o);
		preconditions.add(robotLocation);
		SinglePredicate robotFree = new ProbotFree();
		preconditions.add(robotFree);
		SinglePredicate machine = new Pmachine(o, n);
		preconditions.add(machine);
		this.preconditions = preconditions;
		
		ConjunctivePredicate add = new ConjunctivePredicate();
		SinglePredicate robLoaded = new ProbotLoaded(n);
		add.add(robLoaded);
		this.add = add;
		
		ConjunctivePredicate delete = new ConjunctivePredicate();
		delete.add(robotFree);
		this.delete = delete;
		
		
	}

}