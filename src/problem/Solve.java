package problem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Heuristic;
import model.Operator;
import model.Plan;
import model.State;
import planner.Strips;
import problem.coffee.CoffeeHeuristic;
import problem.coffee.operators.OServe;
import problem.coffee.operators.Omake;
import problem.coffee.operators.Omove;
import problem.parser.Parser;
import problem.parser.coffee.CoffeeParser;

public class Solve {

	public static void main(String[] args) throws IOException {
		// test
		
		String filePath;
		if(args.length > 0) {
			filePath = args[0];
		} else {
			filePath = "./data/examples/test0";
		}
		
		File testFile = new File(filePath);
		Parser parser = new CoffeeParser(6, 6);

		// if parsing fails, exit the program
		if (!parser.parse(testFile.toPath())) {
			System.out.println("Parsing error...Badly formed test: " + testFile.getPath());
			return;
		}

		State initialState = parser.getInitialState(), finalState = parser
				.getGoalState();

		List<Operator> operators = new ArrayList<>();
		operators.add(new Omake());
		operators.add(new Omove());
		operators.add(new OServe());

		Strips planner = new Strips(initialState, finalState,
				operators);
		Plan plan = null;
		Heuristic heuristic = new CoffeeHeuristic(true, true);
		double startTime = System.currentTimeMillis();
		try {
			plan = planner.calculatePlan(heuristic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(plan);
		System.out.println("Time: "+(System.currentTimeMillis()-startTime)/1000+"s");
	}
}
