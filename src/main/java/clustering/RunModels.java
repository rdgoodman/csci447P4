package clustering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RunModels {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Please enter P if you wish to run PSO, or C if you wish to run Competitive Learning");
		String algo = input.nextLine();

		File file = new File("src/main/resources/iris.csv");
		ArrayList<Datum> data = new ArrayList<Datum>();

		try {
			Scanner s = new Scanner(file);
			int dataSize = 0;

			// create Datums
			while (s.hasNextLine()) {
				String str = s.nextLine();
				String[] numStr = str.split(",");

				double[] nums = new double[numStr.length];

				for (int i = 0; i < nums.length; i++) {
					nums[i] = Double.valueOf(numStr[i]);
				}

				nums = normalizeData(nums);
				dataSize = nums.length;
				ArrayList<Double> d = new ArrayList<Double>();
				for (int i = 0; i < nums.length; i++) {
					d.add(nums[i]);
				}
				data.add(new Datum(d));

			}
			System.out.println("Data: " + data.size());

			// 
			//
			// Sample runs
			//
			//
			
			if (algo.equals("P")) {
				
				// particle swarm optimization
				PSO psoSample = new PSO(0.2, 0.2, 0.2, 10, 3, dataSize, 0.5, .15);
				ClusterSet psoSoln = new ClusterSet(psoSample.run(data));
				System.out.println();
				System.out.println();
				System.out.println("****************************");
				System.out.println("*** Clustering Solution: ***");
				System.out.println("****************************");
				psoSoln.print();
				System.out.println();
				psoSoln.calcCohesion();
				psoSoln.calcSeparation();
				
			} else if (algo.equals("C")) {
				
				// Competitive learning neural network
				CompetitiveANN compSample = new CompetitiveANN(0.2, dataSize, 3);
				ArrayList<Datum> train = new ArrayList<Datum>();
				ArrayList<Datum> test = new ArrayList<Datum>();
				for (Datum d : data) {
					double p = Math.random();
					if (p < .2) {
						test.add(d);
					} else {
						train.add(d);
					}
				}
				ClusterSet compSoln = new ClusterSet(compSample.run(train, test));
				System.out.println();
				System.out.println();
				System.out.println("****************************");
				System.out.println("*** Clustering Solution: ***");
				System.out.println("****************************");
				compSoln.print();
				System.out.println();
				compSoln.calcCohesion();
				compSoln.calcSeparation();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		input.close();
	}

	private static double[] normalizeData(double[] nums) {
		double max = Double.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > max) {
				max = nums[i];
			}
		}
		for (int i = 0; i < nums.length; i++) {
			nums[i] = (nums[i] / max);
		}
		return nums;
	}
}
