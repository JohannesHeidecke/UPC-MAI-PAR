package problem;

import java.util.ArrayList;
import java.util.List;

import model.Operator;
import model.Plan;
import model.State;
import planner.LinearPlanner;
import problem.coffee.operators.OServe;
import problem.coffee.operators.Omake;
import problem.coffee.operators.Omove;
import problem.parser.coffee.CoffeeParser;

public class Solve {
	
	public static void main(String[] args) {
		// test
		String test = "InitialState=Robot-free;Steps(0);Robot-location(o1);Machine(o4,3);"
						   + "Machine(o8,1);Machine(o21,2);Machine(o23,1);Machine(o31,2);"
						   + "Petition(o3,1);Petition(o11,3);Petition(o12,1);"
						   + "Petition(o13,2);Petition(o25,1);"
					+ "GoalState=Robot-location(o7);Served(o3);Served(o11);Served(o12);"
						   + "Served(o13);Served(o25);";
		
		CoffeeParser parser = new CoffeeParser(6,6);
		// if parsing fails, exit the program
		if (!parser.parse(test)) {
			System.out.println("Parsing error...Bad formed test: " + test);
			return;
		}
			
		State initialState = parser.getInitialState(), finalState = parser.getGoalState();
		
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
