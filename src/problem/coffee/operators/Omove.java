package problem.coffee.operators;

import model.ConjunctivePredicate;
import model.Operator;
import model.Predicate;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.Psteps;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;

public class Omove extends Operator {
	
	private Location o1;
	private Location o2;
	private PositiveInteger x, xNew;
	
	public Omove() {
		
		o1 = new Location(null);
		o2 = new Location(null);
		x = new PositiveInteger(null);
		xNew = new PositiveInteger(null);
		
		ConjunctivePredicate preconditions = new ConjunctivePredicate();
		Predicate robotLocation = new ProbotLocation(o1);
		preconditions.add(robotLocation);
		Predicate steps = new Psteps(x);
		preconditions.add(steps);
		this.preconditions = preconditions;
		
		ConjunctivePredicate add = new ConjunctivePredicate();
		Predicate robotLocationNew = new ProbotLocation(o2);
		add.add(robotLocationNew);
		Predicate stepsNew = new Psteps(xNew);
		add.add(stepsNew);
		this.add = add;
		
		ConjunctivePredicate delete = new ConjunctivePredicate();
		delete.add(robotLocation);
		delete.add(steps);
		this.delete = delete;
		
	}
	
	@Override
	public void apply() {
		xNew.instantiate(calculateSteps(x, o1, o2));
	}
	
	private static Integer calculateSteps(PositiveInteger x, Location o1, Location o2) {
		return new Integer(x.getIntValue() + o1.distanceTo(o2));
	}
	
	@Override
	public String toString() {
		return "Move from "+o1.toString()+" to "+o2.toString()+". Total steps: "+xNew.toString();
	}
	
	public Integer getStepsSoFar() {
		return xNew.getIntValue();
	}
}
