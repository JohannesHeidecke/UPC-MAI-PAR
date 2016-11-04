package problem.coffee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import problem.coffee.pred.Pmachine;
import problem.coffee.pred.Pserved;
import problem.coffee.pred.args.Coordinate;
import problem.coffee.pred.args.Location;
import model.Heuristic;
import model.Predicate;
import model.State;

public class CoffeeHeuristic extends Heuristic {
	
	private boolean useNNforServeds;
	private boolean useClosestMachine;
	
	
	
	public CoffeeHeuristic(boolean useTravelingSalesmenServed,
			boolean useClosestMachine) {
		super();
		this.useNNforServeds = useTravelingSalesmenServed;
		this.useClosestMachine = useClosestMachine;
	}

	@Override
	public List<Predicate> heuristicPushOrder(
			List<Predicate> predicates, State currentState) {
		
		if (!useNNforServeds) {
			return predicates;
		}
		
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
		List<Pmachine> machinePreds = new ArrayList<>();
		for (Predicate pred : compatiblePredicates) {
			if (pred.getIdentifier().equals("machine")) {
				machinePreds.add((Pmachine) pred);
			}
		}
		
		if(machinePreds.size() > 0) {
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
				int distance = currentPos.distanceTo(pred.getLocation());
				if (distance < bestDistanceSoFar) {
					closestMachine = pred;
					bestDistanceSoFar = distance;
				}
			}
			return closestMachine;
		}
		
		// In case the instantiation is not about machines, don't use a heuristic:
		return compatiblePredicates.get(0);
	}
	
}
