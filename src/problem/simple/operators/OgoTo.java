package problem.simple.operators;

import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Variable;

import model.ConjunctivePredicate;
import model.Operator;
import model.SinglePredicate;
import problem.simple.pred.PisAt;
import problem.simple.pred.args.Location;

public class OgoTo extends Operator {
	
	private Location l1;
	private Location l2;
	
	public OgoTo () {
		
		// Go from l1 to l2:
		l1 = new Location(null);
		l2 = new Location(null);
		
		ConjunctivePredicate preconditions = new ConjunctivePredicate();
		SinglePredicate isAtL1 = new PisAt(l1);
		preconditions.add(isAtL1);
		
		
		ConjunctivePredicate add = new ConjunctivePredicate();
		SinglePredicate isAtL2 = new PisAt(l2);
		add.add(isAtL2);
		
		ConjunctivePredicate delete = new ConjunctivePredicate();
		delete.add(isAtL1);
		
		
		this.preconditions = preconditions;
		this.add = add;
		this.delete = delete;
	}
	
	@Override
	public String toString() {
		return "Go from "+l1.toString()+" to "+l2.toString();
	}

}
