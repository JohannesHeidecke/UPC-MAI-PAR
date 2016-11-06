package problem.coffee.validator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Predicate;
import model.State;
import problem.coffee.pred.Pmachine;
import problem.coffee.pred.Ppetition;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.args.Location;
import problem.parser.Parser;
import problem.parser.coffee.CoffeeParser;

public class CoffeeOptimalSolution {
	
	private Location initialPos;
	private Location finalPos;
	
	private List<Ppetition> petitions;
	private List<Pmachine> machines;

	private int bestStepsSoFar;
	private List<Ppetition> bestPetitionsPermutationSoFar;
	private List<Location> bestPath;
	
	public CoffeeOptimalSolution(State initialState, State goalState) {

		bestStepsSoFar = Integer.MAX_VALUE;
		// initialize starting and ending point
		initialPos = findRobotLocation(initialState);
		finalPos = findRobotLocation(goalState);
		// list of petitions and machines
		petitions = getPetitions(initialState);
		machines = getMachines(initialState);
	}

	public Integer getNumberOfSteps() {
		// compute optimal solution (just once)
		if (bestStepsSoFar == Integer.MAX_VALUE)
		{
			// static recursive method
			CoffeeOptimalSolution.permutation(petitions, this);
		}
		
		return bestStepsSoFar;
	}

	public List<Location> getPath() {
		// compute optimal solution (just once)
		if (bestStepsSoFar == Integer.MAX_VALUE)
		{
			CoffeeOptimalSolution.permutation(petitions, this);
		}
		// return the optimal path
		if (bestPath == null) {
			bestPath = new ArrayList<Location>();
			// add initial location
			bestPath.add(initialPos);
			for (Ppetition currentPetition : bestPetitionsPermutationSoFar) {
				// add best machine location between current location and next petition
				Pmachine currentBestMachine = findBestMachine(bestPath.get(bestPath.size()-1), currentPetition);
				bestPath.add(currentBestMachine.getLocation());
				// add next petition location
				bestPath.add(currentPetition.getLocation());
			}
			// add final location (if specified)
			if (finalPos != null) {
				bestPath.add(finalPos);
			}
		}

		return bestPath;
	}
	
	private Location findRobotLocation(State state) {
		Location currentPos = null;
		for (Predicate pred : state.getPredicates().toList()) {
			if (pred.getIdentifier().equals("robotLocation")) {
				currentPos = ((ProbotLocation) pred).getLocation();
				break;
			}
		}

		return currentPos;
	}

	private List<Pmachine> getMachines(State state) {
		List<Pmachine> machines = new ArrayList<Pmachine>();
		for (Predicate pred : state.getPredicates().toList()) {
			if (pred.getIdentifier().equals("machine")) {
				machines.add((Pmachine) pred);
			}
		}

		return machines;
	}

	private List<Ppetition> getPetitions(State state) {
		List<Ppetition> petitionPredicates = new ArrayList<>();
		for (Predicate pred : state.getPredicates().toList()) {
			if (pred.getIdentifier().equals("petition")) {
				petitionPredicates.add((Ppetition) pred);
			}
		}
		return petitionPredicates;
	}

	private static void permutation(List<Ppetition> petitions, CoffeeOptimalSolution solver) {
		// find the best petitions tour recursively
		// brute-force strategy
		// check every possible tour
		permutation(new LinkedList<Ppetition>(), petitions, solver);
	}
	
	private static void permutation(List<Ppetition> prefix, List<Ppetition> petitions, CoffeeOptimalSolution solver) {
		int n = petitions.size();
		if (n == 0) {
			// base case
			// test the permutation
			solver.updateBestSolution(prefix);
		} else {
			// recursive case
			for (int i = 0; i < n; i++) {
				// pick the petition
				List<Ppetition> newPetitions = new LinkedList<Ppetition>(petitions);
				Ppetition currentServed = newPetitions.remove(i);
				// extend the partial permutation
				List<Ppetition> newPrefix = new LinkedList<Ppetition>(prefix);
				newPrefix.add(currentServed);
				// recursively add the rest of the elements
				permutation(newPrefix, newPetitions, solver);
			}
		}
	}
	
	private void updateBestSolution(List<Ppetition> petitionPermutation) {
		// compute steps for the candidate solution
		int steps = computeNumberOfStepsForPath(petitionPermutation);
		// update the best solution so far
		if (steps < bestStepsSoFar) {
			bestStepsSoFar = steps;
			bestPetitionsPermutationSoFar = petitionPermutation;
		}
	}

	private int computeNumberOfStepsForPath(List<Ppetition> petitionPermutation) {
		int steps = 0;		
		// calculate steps in the tour
		Location currentPos = initialPos;
		for (Ppetition currentPetition : petitionPermutation) {
			// compute steps from current location to the best machine and from the machine to the petition office
			Pmachine currentBestMachine = findBestMachine(currentPos, currentPetition);
			steps += getDistance(currentPos, currentBestMachine.getLocation(), currentPetition.getLocation());
			currentPos = currentPetition.getLocation();
		}
		// the robot could stop at the last office
		if (finalPos != null)
		{
			steps += currentPos.distanceTo(finalPos);
		}
		
		return steps;
	}
	
	private Pmachine findBestMachine(Location currentPos, Ppetition petition) {

		Pmachine bestMachine = null;
		int bestDistanceSoFar = Integer.MAX_VALUE;
		for (Pmachine machine : machines) {
			// compatible machine
			if (machine.getN().equals(petition.getN())) {
				// keep track of the closest machine
				int distance = getDistance(currentPos, machine.getLocation(), petition.getLocation());
				if (distance < bestDistanceSoFar) {
					bestMachine = machine;
					bestDistanceSoFar = distance;
				}
			}
		}

		return bestMachine;
	}

	private Integer getDistance(Location initialPos, Location middlePos, Location finalPos)
	{
		// number of steps from initial to middle and from middle to final
		return initialPos.distanceTo(middlePos) + middlePos.distanceTo(finalPos);
	}
	
	public static void main(String[] args) throws IOException {
		
		Parser parser = new CoffeeParser(6, 6);
		// read test from file
		String filePath = "./data/test0";
		File testFile = new File(filePath);
		parser.parse(testFile.toPath());

		State initialState = parser.getInitialState(); 
		State finalState = parser.getGoalState();
		
		CoffeeOptimalSolution solver = new CoffeeOptimalSolution(initialState, finalState);
		System.out.println("Optimal number of steps: " + solver.getNumberOfSteps());
		System.out.println("Optimal path: " + solver.getPath());
	}
}
