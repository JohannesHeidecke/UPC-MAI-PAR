package problem.coffee.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Operator;
import model.Plan;
import model.State;
import planner.Strips;
import problem.coffee.CoffeeHeuristic;
import problem.coffee.helpers.Helper;
import problem.coffee.operators.OServe;
import problem.coffee.operators.Omake;
import problem.coffee.operators.Omove;
import problem.parser.Parser;
import problem.parser.coffee.CoffeeParser;

public class CoffeeExperiments {

	private Parser parser;
	private ExampleGenerator testGen;
	
	private List<Operator> operators;
		
	private boolean[] travelingSalesmanOrder;
	private boolean[] useClosestMachine;

	private PrintStream stepsOutput;
	private OutputStream plannerOutput;
	private PrintStream matrixReprOutput;
	private PrintStream stdReprOutput;
	
	public CoffeeExperiments() {		
		parser  = new CoffeeParser(6, 6);
		testGen = new ExampleGenerator(6, 6);
		// operator
		operators = new ArrayList<>();
		operators.add(new Omake());
		operators.add(new Omove());
		operators.add(new OServe());
	
		// heuristics parameters
		travelingSalesmanOrder = new boolean[]{true, true, false, false};
		useClosestMachine = new boolean[]{true, false, true, false};
	
		// set default output to standard output
		plannerOutput = System.out;
		stepsOutput = System.out;
		matrixReprOutput = System.out;		
		stdReprOutput = System.out;
	}

	public void runTestCase(String newTest) {
		
		parser.parse(newTest);
		State initialState = parser.getInitialState(), finalState = parser.getGoalState();
		
		CoffeeOptimalSolution solver = new CoffeeOptimalSolution(initialState, finalState);
		Strips planner = new Strips(initialState, finalState, operators);
		planner.setOutput(plannerOutput);
				
		int heurs_n = travelingSalesmanOrder.length;
		Integer[] stepsOutcome = new Integer[heurs_n+1];
		for (int k = 0; k < heurs_n ; k++) {
			// try differents heuristics
			CoffeeHeuristic heuristic = new CoffeeHeuristic(travelingSalesmanOrder[k], useClosestMachine[k]);
			heuristic.setOutput(plannerOutput);

			try {
				Plan plan = planner.calculatePlan(heuristic);			
				// steps for plan
				stepsOutcome[k] = Helper.getNumberOfSteps(plan);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		// steps for optimal solution
		stepsOutcome[heurs_n] = solver.getNumberOfSteps();
		// save results
		saveOutcome(newTest, stepsOutcome);
	}
	
	public void runNewRandomTestCase() {
		testGen.setOutput(matrixReprOutput);
		String newTest = testGen.generate();
		runTestCase(newTest);
	}
	
	private void saveOutcome(String newTest, Integer[] stepsOutcome) {
		
		for (int k = 0; k < stepsOutcome.length ; k++) {
			stepsOutput.print(stepsOutcome[k]);

			if (k < stepsOutcome.length-1){
				stepsOutput.print(";");
			} else {
				stepsOutput.println();
			}
		}
		
		stdReprOutput.println(newTest);
	
		matrixReprOutput.println();
	}

	public void setStepsOutput(OutputStream outputStream) {
		stepsOutput = new PrintStream(outputStream);
	}

	public void setStdReprOutput(OutputStream outputStream) {
		stdReprOutput = new PrintStream(outputStream);
	}

	public void setMatrixReprOutput(OutputStream outputStream) {
		matrixReprOutput = new PrintStream(outputStream);
	}

	public void setPlannerOutput(OutputStream outputStream) {
		plannerOutput = new PrintStream(outputStream);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {

		// use to discard logs
		OutputStream nullOutput = new OutputStream() { public void write(int b) throws IOException {} };

		
		// run random examples in files

		// timestamp string
		String strDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		// use to store logs
		String folder = "./data/generated/";
		OutputStream stepsOutput = new PrintStream(new File(folder + "steps_" + strDate + ".txt"));
		OutputStream standardReprOutput = new PrintStream(new File(folder + "test_" + strDate + ".txt"));
		OutputStream matrixReprOutput = new PrintStream(new File(folder + "test_matrix_" + strDate + ".txt"));

		CoffeeExperiments experiments = new CoffeeExperiments();
		experiments.setPlannerOutput(nullOutput);
		experiments.setStepsOutput(stepsOutput);
		experiments.setStdReprOutput(standardReprOutput);
		experiments.setMatrixReprOutput(matrixReprOutput);

		final int randomTestCases = 1000;
		for (int k = 0 ; k < randomTestCases ; k++)
		{
			System.out.println("Random Test #" + k);
			experiments.runNewRandomTestCase();
		}

		// run examples in files
		folder = "./data/examples/";

		stepsOutput = new PrintStream(new File(folder + "steps.txt"));
		matrixReprOutput = new PrintStream(new File(folder + "test_matrix.txt"));

		experiments.setPlannerOutput(nullOutput);
		experiments.setStdReprOutput(nullOutput);
		experiments.setStepsOutput(stepsOutput);
		experiments.setMatrixReprOutput(matrixReprOutput);
		
		final int fileTestCases = 4;		
		for (int k = 0 ; k < fileTestCases ; k++)
		{
			System.out.println("File Test #" + k);
			String fileTest = new String(Files.readAllBytes(new File(folder + "test" + k).toPath()));
			experiments.runTestCase(fileTest);
		}	
	}	
}