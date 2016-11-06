package problem.coffee.helpers;

import model.Operator;
import model.Plan;
import problem.coffee.operators.Omove;

public abstract class Helper {

	public static Integer getNumberOfSteps(Plan plan) {
		int steps = 0;
		for ( int k = plan.getSize()-1 ; k >= 0 ; --k) {
			Operator operator = plan.getOperators().get(k); 
			
			if (operator instanceof Omove) {
				steps = ((Omove) operator).getStepsSoFar();
				break;
			}
		}

		return steps;
	}

	public static int coordToNumb(int x, int y, int yDim) {
		
		return (x-1)*yDim + y;

	}
}
