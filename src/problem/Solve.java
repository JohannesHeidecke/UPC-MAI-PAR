package problem;

import java.util.ArrayList;
import java.util.List;

import model.Operator;
import model.Plan;
import model.State;
import planner.LinearPlanner;
import problem.coffee.FinalState;
import problem.coffee.InitialState;
import problem.coffee.operators.OServe;
import problem.coffee.operators.Omake;
import problem.coffee.operators.Omove;

public class Solve {
	
	public static void main(String[] args) {
		
		State initialState = new problem.coffee.InitialState();
		State finalState = new problem.coffee.FinalState();
		
		List<Operator> operators = new ArrayList<>();
		operators.add(new Omake());
		operators.add(new Omove());
		operators.add(new OServe());
		
		LinearPlanner planner = new LinearPlanner(initialState, finalState, operators);
		Plan plan = null;
		try {
			plan = planner.calculatePlan();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(plan);
		
	}

}
