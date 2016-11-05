package problem.coffee.validator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.concurrent.ThreadLocalRandom;

public class ExampleGenerator {

	private static int sizeX = 6;
	private static int sizeY = 6;

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("./data/generated/example1"), "utf-8"))) {
	   writer.write(generateExample());
	}

		

	}
	
	private static String generateExample() {
		// Randomly set number of machines and petitions
				// For both the minimum and maximum is:
				// Minimum: 3
				// Maximum: half of available fields
				int nOMachines = ThreadLocalRandom.current().nextInt(3,
						(sizeX * sizeY) / 4);
				int nOPetitions = ThreadLocalRandom.current().nextInt(3,
						(sizeX * sizeY) / 4);

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
							System.out.print("|" + "  ");
						} else {
							System.out.print("|" + values[i][j]);
						}
					}
					System.out.println("|");
				}

				// convert to text file:
				String out = "InitialState=Robot-location(o1);Robot-free; Steps(0);";
				String servedOut = "";
				for (int i = 0; i < values.length; i++) {
					for (int j = 0; j < values[0].length; j++) {
						if (values[i][j] != null) {
							if (values[i][j].charAt(0) == 'm') {
								out += "Machine(o";
							} else {
								out += "Petition(o";
								servedOut += "Served(o"
										+ coordToNumb(i + 1, j + 1) + ");";
							}
							out += coordToNumb(i + 1, j + 1);
							out += "," + values[i][j].charAt(1) + ");";
						}
					}
				}
				
				out += "GoalState=Robot-location(o"+coordToNumb(2, 1)+");";
				out += servedOut;

				return out;
	}

	private static int coordToNumb(int x, int y) {
		
		return (x-1)*sizeY + y;

	}

}
