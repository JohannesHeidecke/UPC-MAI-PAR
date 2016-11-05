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
		initialPos = findRobotLocation(initialState);
		finalPos = findRobotLocation(goalState);
		
		petitions = getPetitions(initialState);
		machines = getMachines(initialState);
	}

	public Integer getNumberOfSteps() {

		if (bestStepsSoFar == Integer.MAX_VALUE)
		{
			CoffeeOptimalSolution.permutation(petitions, this);
		}
		
		return bestStepsSoFar;
	}

	public List<Location> getPath() {

		if (bestStepsSoFar == Integer.MAX_VALUE)
		{
			CoffeeOptimalSolution.permutation(petitions, this);
		}
		
		if (bestPath == null) {
			bestPath = new ArrayList<Location>();

			bestPath.add(initialPos);
			for (Ppetition petition : bestPetitionsPermutationSoFar) {
				bestPath.add(petition.getLocation());
			}
			
			bestPath.add(finalPos);
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
		permutation(new LinkedList<Ppetition>(), petitions, solver);
	}
	
	private static void permutation(List<Ppetition> prefix, List<Ppetition> petitions, CoffeeOptimalSolution solver) {
		int n = petitions.size();
		if (n == 0) {
			solver.updateBestSolution(prefix);
		} else {
			for (int i = 0; i < n; i++) {
				List<Ppetition> newPetitions = new LinkedList<Ppetition>(petitions);
				Ppetition currentServed = newPetitions.remove(i);

				List<Ppetition> newPrefix = new LinkedList<Ppetition>(prefix);
				newPrefix.add(currentServed);

				permutation(newPrefix, newPetitions, solver);
			}
		}
	}
	
	private void updateBestSolution(List<Ppetition> petitionPermutation) {
		
		int steps = computeNumberOfStepsForPath(petitionPermutation);
		
		if (steps < bestStepsSoFar) {
			bestStepsSoFar = steps;
			bestPetitionsPermutationSoFar = petitionPermutation;
		}
	}

	private int computeNumberOfStepsForPath(List<Ppetition> petitionPermutation) {
		
		int steps = 0;		
		Location currentPos = initialPos;
		
		for (Ppetition currentPetition : petitionPermutation) {
			Pmachine currentBestMachine = findBestMachine(currentPos, currentPetition);
			steps += getDistance(currentPos, currentBestMachine.getLocation(), currentPetition.getLocation());
			currentPos = currentPetition.getLocation();
		}
		
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
		return initialPos.distanceTo(middlePos) + middlePos.distanceTo(finalPos);
	}
	
	public static void main(String[] args) throws IOException {

		String filePath = "./data/test1";
		File testFile = new File(filePath);
		
		Parser parser = new CoffeeParser(6, 6);
		parser.parse(testFile.toPath());

		State initialState = parser.getInitialState(); 
		State finalState = parser.getGoalState();
		
		CoffeeOptimalSolution solver = new CoffeeOptimalSolution(initialState, finalState);
		System.out.println("Optimal number of steps: " + solver.getNumberOfSteps());
		System.out.println("Optimal path: " + solver.getPath());
	}
}
