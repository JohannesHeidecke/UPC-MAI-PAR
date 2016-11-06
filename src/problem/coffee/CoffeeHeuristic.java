package problem.coffee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import problem.coffee.pred.Pmachine;
import problem.coffee.pred.Ppetition;
import problem.coffee.pred.Pserved;
import problem.coffee.pred.args.Coordinate;
import problem.coffee.pred.args.Location;
import model.Heuristic;
import model.Predicate;
import model.State;

public class CoffeeHeuristic extends Heuristic {

	private boolean useNNforServeds;
	private boolean useClosestMachine;

	private Ppetition lastPetition;

	public CoffeeHeuristic(boolean useTravelingSalesmenServed,
			boolean useClosestMachine) {
		super();
		this.useNNforServeds = useTravelingSalesmenServed;
		this.useClosestMachine = useClosestMachine;
	}

	@Override
	public List<Predicate> heuristicPushOrder(List<Predicate> predicates,
			State currentState) {

		if (!useNNforServeds) {
			return predicates;
		}

		System.out.println("HEURISTIC: 'served' ordering");

		List<Predicate> result = new ArrayList<>();
		for (Predicate pred : predicates) {
			result.add(pred);
		}

		// ordering of served(o) predicates:

		List<Pserved> servedPredicates = new ArrayList<>();
		for (Predicate pred : predicates) {
			if (pred.getIdentifier().equals("served")) {
				servedPredicates.add((Pserved) pred);
			}
		}
		result.removeAll(servedPredicates);

		Location currentPos = null;
		for (Predicate pred : currentState.getPredicates().toList()) {
			if (pred.getIdentifier().equals("robotLocation")) {
				currentPos = (Location) pred.getArgument(0);
				break;
			}
		}

		List<Pserved> temp = new ArrayList<>();
		while (!servedPredicates.isEmpty()) {
			// find closest office with served
			Pserved closestServed = null;
			int bestDistanceSoFar = Integer.MAX_VALUE;
			for (Pserved pred : servedPredicates) {
				int distance = currentPos.distanceTo(pred.getLocation());
				if (distance < bestDistanceSoFar) {
					closestServed = pred;
					bestDistanceSoFar = distance;
				}
			}
			// add this served to temp
			temp.add(closestServed);
			currentPos = closestServed.getLocation();
			// remove this served from servedPredicates
			servedPredicates.remove(closestServed);

		}

		Collections.reverse(temp);
		servedPredicates = temp;

		result.addAll(servedPredicates);

		return result;
	}

	@Override
	public Predicate choosePredicateForInstantiation(
			List<Predicate> compatiblePredicates, State currentState) {

		if (!useClosestMachine) {
			return compatiblePredicates.get(0);
		}

		// MACHINE SELECTION HEURISTIC:
		// filter out all machines

		// Save the last instantiated petition to be included in the distance
		// caluclation:
		List<Ppetition> petitionsPred = new ArrayList<>();
		for (Predicate pred : compatiblePredicates) {
			if (pred.getIdentifier().equals("petition")) {
				petitionsPred.add((Ppetition) pred);
			}
		}
		if (!petitionsPred.isEmpty()) {
			lastPetition = petitionsPred.get(0);
		}

		List<Pmachine> machinePreds = new ArrayList<>();
		for (Predicate pred : compatiblePredicates) {
			if (pred.getIdentifier().equals("machine")) {
				machinePreds.add((Pmachine) pred);
			}
		}

		if (machinePreds.size() > 0) {
			System.out.println("HEURISTIC: 'machine' selection");
			Location currentPos = null;
			for (Predicate pred : currentState.getPredicates().toList()) {
				if (pred.getIdentifier().equals("robotLocation")) {
					currentPos = (Location) pred.getArgument(0);
					break;
				}
			}

			Pmachine closestMachine = null;
			int bestDistanceSoFar = Integer.MAX_VALUE;
			for (Pmachine pred : machinePreds) {
				// distance = distance from current position to machine +
				// distance from machine to last petition
				int distance = currentPos.distanceTo(pred.getLocation());
				distance += pred.getLocation().distanceTo(lastPetition.getLocation());
				if (distance < bestDistanceSoFar) {
					closestMachine = pred;
					bestDistanceSoFar = distance;
				}
			}
			return closestMachine;
		}

		// In case the instantiation is not about machines, don't use a
		// heuristic:
		return compatiblePredicates.get(0);
	}

}
