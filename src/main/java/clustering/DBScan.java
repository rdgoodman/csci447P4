package clustering;

import java.util.ArrayList;
import java.util.Random;

/*
 * This formulation of the DBScan algorithm largely relies on the 
 * formulation provided by Ester et. al in largely relies on formulation 
 * provided by Ester et. al in "A Density-Based Algorithm for Discovering Clusters"
 * (full citation provided in accompanying report). 
 */

public class DBScan {

	// distance value used to determine if a point is in or out of a cluster
	private double epsilon;
	// minimum number of points required to form a dense region
	private int minPoints;
	// the number of dimensions in the input data
	private int numDimensions;

	public DBScan(double epsilon, int minPoints, int numDimensions) {
		this.epsilon = epsilon;
		this.minPoints = minPoints;
		this.numDimensions = numDimensions;
	}

	public ArrayList<Cluster> run(ArrayList<Datum> training) {
		// The clusters returned at the end of the algorithm
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();

		// A Core point is one where there are at least MinPts points within
		// epsilon neighborhood
		ArrayList<Datum> core = new ArrayList<Datum>();

		// A Border point is one that falls within epsilon distance of some Core
		// point
		ArrayList<Datum> border = new ArrayList<Datum>();

		// A noise point does not belong to either of the above sets
		ArrayList<Datum> noise = new ArrayList<Datum>();

		// Holds all visited points in the dataset, for constructing the core
		// set
		ArrayList<Datum> coreVisited = new ArrayList<Datum>();

		// first train data
		System.out.println(">>>>> TRAINING");

		// Build the set of all core points, then perform DB scan using the core
		// set
		// Select a data point at random to test as a core point
		Random rand = new Random();

		// generates a random number within the size of the dataset
		int randomPt = rand.nextInt((training.size() - 1) + 1) + 1;

		Datum currPt = training.get(randomPt);

		int iterator = 0;

		// System.out.println("Curr pt starts as " + currPt.getData());

		while (coreVisited.size() < training.size() - 1) {
			// add this point to the list of visited points
			if (!coreVisited.contains(currPt)) {
				coreVisited.add(currPt);
			}

			for (Datum d : training) {
				// if the distance between currPt and d is within epsilon, add
				// to border set
				if (d.calcDist(currPt) <= epsilon) {
					border.add(d);
				}
			} // at end, have all points within epsilon of potential core point

			// if there are enough points in border set, add currPt to core set
			if (border.size() >= minPoints) {
				core.add(currPt);
			} // end if, we have added data point to core if appropriate

			// pick a new point in dataset to serve as core
			currPt = training.get(iterator);
			iterator++;
			//clear the border set, because we are looking at a new core
			border.clear();
		} // end while, now all points are categorized

		for (Datum d: core) {
			System.out.println(d);
		}

		return clusters;
	}

}
