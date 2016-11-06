package problem.coffee.validator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import problem.coffee.helpers.Helper;
import problem.coffee.pred.Pmachine;
import problem.coffee.pred.Ppetition;
import problem.coffee.pred.ProbotFree;
import problem.coffee.pred.ProbotLocation;
import problem.coffee.pred.Pserved;
import problem.coffee.pred.Psteps;

public class ExampleGenerator {

	private int sizeX, sizeY;
	private PrintStream output;
	
	ExampleGenerator() {
		this(6, 6);
	}

	ExampleGenerator(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.output = System.out;
	}
		
	public String generate() {
		// Randomly set number of machines and petitions
				// For both the minimum and maximum is:
				// Minimum: 3
				// Maximum: half of available fields
						
				int nOPetitions = ThreadLocalRandom.current().nextInt(3,
								1 + (sizeX * sizeY) / 4);

				int nOMachines = ThreadLocalRandom.current().nextInt(3,
								1 + (sizeX * sizeY) / 4);

						
				// Split the machines into capacities 1, and 3:
				int cutPoint1 = 0, cutPoint2 = 0;
				boolean validCutpoints = false;
				while (!validCutpoints) {
					cutPoint1 = ThreadLocalRandom.current().nextInt(2, nOMachines + 1);
					cutPoint2 = ThreadLocalRandom.current().nextInt(2, nOMachines + 1);
					if (cutPoint1 != cutPoint2) {
						validCutpoints = true;
					}
				}
				int nOMachines1, nOMachines2, nOMachines3;
				nOMachines1 = Math.min(cutPoint1, cutPoint2) - 1;
				nOMachines2 = Math.max(cutPoint1, cutPoint2)
						- Math.min(cutPoint1, cutPoint2);
				nOMachines3 = nOMachines - nOMachines1 - nOMachines2;

				// split the petitions into numbers 1, 2 and 3:
				validCutpoints = false;
				while (!validCutpoints) {
					cutPoint1 = ThreadLocalRandom.current().nextInt(2, nOPetitions + 1);
					cutPoint2 = ThreadLocalRandom.current().nextInt(2, nOPetitions + 1);
					if (cutPoint1 != cutPoint2) {
						validCutpoints = true;
					}
				}
				int nOPetitions1, nOPetitions2, nOPetitions3;
				nOPetitions1 = Math.min(cutPoint1, cutPoint2) - 1;
				nOPetitions2 = Math.max(cutPoint1, cutPoint2)
						- Math.min(cutPoint1, cutPoint2);
				nOPetitions3 = nOPetitions - nOPetitions1 - nOPetitions2;

				// fill the board:
				String[][] values = new String[sizeX][sizeY];
				int x, y;
				for (int i = 0; i < nOMachines1; i++) {
					do {
						x = ThreadLocalRandom.current().nextInt(1, sizeX + 1);
						y = ThreadLocalRandom.current().nextInt(1, sizeY + 1);
					} while (values[x - 1][y - 1] != null);
					values[x - 1][y - 1] = "m1";
				}
				for (int i = 0; i < nOMachines2; i++) {
					do {
						x = ThreadLocalRandom.current().nextInt(1, sizeX + 1);
						y = ThreadLocalRandom.current().nextInt(1, sizeY + 1);
					} while (values[x - 1][y - 1] != null);
					values[x - 1][y - 1] = "m2";
				}
				for (int i = 0; i < nOMachines3; i++) {
					do {
						x = ThreadLocalRandom.current().nextInt(1, sizeX + 1);
						y = ThreadLocalRandom.current().nextInt(1, sizeY + 1);
					} while (values[x - 1][y - 1] != null);
					values[x - 1][y - 1] = "m3";
				}
				for (int i = 0; i < nOPetitions1; i++) {
					do {
						x = ThreadLocalRandom.current().nextInt(1, sizeX + 1);
						y = ThreadLocalRandom.current().nextInt(1, sizeY + 1);
					} while (values[x - 1][y - 1] != null);
					values[x - 1][y - 1] = "p1";
				}
				for (int i = 0; i < nOPetitions2; i++) {
					do {
						x = ThreadLocalRandom.current().nextInt(1, sizeX + 1);
						y = ThreadLocalRandom.current().nextInt(1, sizeY + 1);
					} while (values[x - 1][y - 1] != null);
					values[x - 1][y - 1] = "p2";
				}
				for (int i = 0; i < nOPetitions3; i++) {
					do {
						x = ThreadLocalRandom.current().nextInt(1, sizeX + 1);
						y = ThreadLocalRandom.current().nextInt(1, sizeY + 1);
					} while (values[x - 1][y - 1] != null);
					values[x - 1][y - 1] = "p3";
				}

				for (int i = 0; i < values.length; i++) {
					for (int j = 0; j < values[0].length; j++) {
						if (values[i][j] == null) {
							output.print("|" + "  ");
						} else {
							output.print("|" + values[i][j]);
						}
					}
					output.println("|");
				}

				List<String> machines = new ArrayList<>();
				List<String> petitions = new ArrayList<>();
				List<String> serveds = new ArrayList<>();
				
				for (int i = 0; i < values.length; i++) {
					for (int j = 0; j < values[0].length; j++) {
						if (values[i][j] != null) {
							String office = "o" + Helper.coordToNumb(i + 1, j + 1, sizeY);
							if (values[i][j].charAt(0) == 'm') {
								String machine = Pmachine.ID +"(" + office + "," + values[i][j].charAt(1) + ");";
								machines.add(machine);
							} else {
								String petition = Ppetition.ID +"(" + office + "," + values[i][j].charAt(1) + ");";
								petitions.add(petition);
								String served = Pserved.ID +"(" + office + ");";
								serveds.add(served);
							}
						}
					}
				}
				
				// shuffle lists
				Random random = new Random(System.nanoTime());
				Collections.shuffle(machines, random);
				Collections.shuffle(petitions, random);
				Collections.shuffle(serveds, random);

				// convert to text file:
				StringBuilder outBuilder = new StringBuilder();
				outBuilder.append("InitialState="+ProbotLocation.ID+"(o1);"+ProbotFree.ID+";"+Psteps.ID+"(0);");

				for(String s : machines) {
				    outBuilder.append(s);
				}
				for(String s : petitions) {
				    outBuilder.append(s);
				}				
				
				outBuilder.append("GoalState="+ProbotLocation.ID+"(o"+Helper.coordToNumb(2, 1, sizeY)+");");
				for(String s : serveds) {
				    outBuilder.append(s);
				}

				return outBuilder.toString();
	}
	
	public void setOutput(OutputStream outputStream){
		output = new PrintStream(outputStream);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		System.out.println(new ExampleGenerator().generate());
	}
}
