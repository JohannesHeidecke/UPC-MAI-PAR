package problem.parser.coffee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.SinglePredicate;
import problem.coffee.pred.Pmachine;
import problem.coffee.pred.Ppetition;
import problem.coffee.pred.ProbotFree;
import problem.coffee.pred.ProbotLoaded;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.Pserved;
import problem.coffee.pred.Psteps;
import problem.coffee.pred.args.Coordinate;
import problem.coffee.pred.args.Location;
import problem.coffee.pred.args.PositiveInteger;
import problem.parser.Parser;

public class CoffeeParser extends Parser {

	private int rows, cols;

	public CoffeeParser(int rows, int columns) {
		// use for projecting linear position into 2D coordinates
		this.rows = rows;
		this.cols = columns;
	}
	
	@Override
	public SinglePredicate parsePredicate(String predName, String[] predArgs) {
		// Return SinglePredicate instance from the given name
		// and list of arguments. Return null if predName is not valid
		// TODO: add error checking for arguments
		SinglePredicate newPred = null;
		if (predName.equals("Robot-free")) {
			newPred = new ProbotFree();
		}
		if (predName.equals("Robot-location")) {
			newPred = new ProbotLocation(parseLocation(predArgs[0]));
		}
		if (predName.equals("Robot-loaded")) {
			int load = Integer.parseInt(predArgs[0]);
			newPred = new ProbotLoaded(new PositiveInteger(load));
		}
		if (predName.equals("Petition")) {
			int order = Integer.parseInt(predArgs[1]);
			newPred = new Ppetition(parseLocation(predArgs[0]), new PositiveInteger(order));								
		}
		if (predName.equals("Served")) {
			newPred = new Pserved(parseLocation(predArgs[0]));						
		}
		if (predName.equals("Machine")) {
			int capacity = Integer.parseInt(predArgs[1]);
			newPred = new Pmachine(parseLocation(predArgs[0]), new PositiveInteger(capacity));						
		}
		if (predName.equals("Steps")) {
			int steps = Integer.parseInt(predArgs[0]);
			newPred = new Psteps(new PositiveInteger(steps));						
		}
		
		return newPred;
	}
	
	private Location parseLocation(String locStr) {
		// first remove letter O and then parse string as integer
		int linearLoc = Integer.parseInt(locStr.replaceAll("(o|O)", "")) - 1;	
		// compute location at the grid 2D (starting at (1,1))
		return new Location(new Coordinate((linearLoc / rows) + 1, (linearLoc % rows) + 1));
	}
	
	public static void main(String[] args) {

		String test = "InitialState=Robot-location(o1);Machine(o4,3);Petition(o7,3);" + 
				  "GoalState=Robot-location(o1);Served(o7,3);"; 

		// test parser from string
		{	
			// parse string
			Parser parser = new CoffeeParser(6,6);
			parser.parse(test);
	
		    System.out.println("Testing parsing from string...");
			System.out.println("Initial" + parser.getInitialState());
			System.out.println("Goal" + parser.getGoalState());
		}
		// test parser from input file
		{
			try {
				// create and write temporary file
				File testFile = File.createTempFile("tempfile", ".tmp");
			    try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
			    	writer.write(test);
			    }
			    // parse file
				Parser fileParser = new CoffeeParser(6,6);
				fileParser.parse(testFile.toPath());
				
			    System.out.println("Testing parsing from file (temporary)...");
			    System.out.println("Initial" + fileParser.getInitialState());
				System.out.println("Goal" + fileParser.getGoalState());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
