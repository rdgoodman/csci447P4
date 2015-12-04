package clustering;

import java.util.ArrayList;
import java.util.Random;

//Note: In DBScan there is no centroid, but the algorithm still requires a centroid value.
//Therefore, we feed in the data as a dummy centroid, but since it is not used in any of our 
//result calcuations, this does not impact our results.

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

	public ArrayList<Cluster> run(ArrayList<Datum> data) {
		// The clusters returned at the end of the algorithm
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();

		// A Core point is one where there are at least MinPts points within
		// epsilon neighborhood
		ArrayList<Datum> core = new ArrayList<Datum>();

		// A Border point is one that falls within epsilon distance of some Core
		// point
		ArrayList<Datum> border = new ArrayList<Datum>();

		// A noise point does not belong to either of the above sets
		// ArrayList<Datum> noise = new ArrayList<Datum>();

		// Holds all visited points in the dataset, for constructing the core
		// set
		ArrayList<Datum> visited = new ArrayList<Datum>();

		System.out.println(">>>>>>>>>>>>DBSCAN");
		System.out.println("Begin point classification");
		// Build the set of all core points, then perform DB scan using the core
		// set
		// Select a data point at random to test as a core point
		Random rand = new Random();

		// generates a random number within the size of the dataset
		int randomPt = rand.nextInt((data.size() - 1) + 1);
		// gets a random point from the data set to serve as the first core
		// candidate
		Datum currPt = data.get(randomPt);

		int iterator = 0;
		// while there are unvisited points in the dataset
		while (visited.size() < data.size() - 1) {
			// add this point to the list of visited points
			if (!visited.contains(currPt)) {
				visited.add(currPt);
			}

			for (Datum d : data) {
				// if the distance between currPt and d is within epsilon, add
				// to border set
				if (d.calcDist(currPt) <= epsilon) {
					border.add(d);
				}
			} // at end, have all points within epsilon of potential core point

			// if there are enough points in border set, add currPt to core set
			if (border.size() >= minPoints) {
				System.out.println("Added new core: " + currPt.getData());
				core.add(currPt);
			} // end if, we have added data point to core if appropriate

			// pick a new point in dataset to serve as core
			currPt = data.get(iterator);
			iterator++;
			// clear the border set, because we are looking at a new core
			border.clear();
		} // end while, now all points are categorized
		System.out.println("All points have been classified as core/border/noise");
		System.out.println(core.size() + " points in the core set");
		System.out.println();

		// **********************************************************
		// Now begin the DB scan portion of the algorithm

		System.out.println("Begin construction of clusters");
		// the label associated with the first cluster
		int currClustLbl = 0;

		// for each point in the core set
		for (int p = 0; p < core.size() - 1; p++) {
			// check if p is already assigned to a cluster
			if (clusters.contains(core.get(p))) {
				// do nothing, this is already assigned to a cluster
			} else {
				// make a new cluster
				clusters.add(new Cluster(core.get(p).getData(), currClustLbl));
				// add the point p to this cluster
				clusters.get(currClustLbl).addPoint(core.get(p));
				System.out.println("Cluster " + currClustLbl + " added point " + core.get(p).getData());

				// for each point in the training set
				for (int q = 0; q < data.size() - 1; q++) {
					// if this point is already in a cluster, do not add again
					if (clusters.contains(data.get(q))) {
						// do nothing, this is already assigned to a cluster
					} else {
						if (!core.isEmpty()) {
							System.out.println("Distance from p to q is " + core.get(p).calcDist(data.get(q)));
							if (core.get(p).calcDist(data.get(q)) <= epsilon) {
								// getting here means point is in the
								// neighborhood of p
								// so add it to the cluster
								clusters.get(currClustLbl).addPoint(data.get(q));
								System.out.println("Cluster " + currClustLbl + " added point " + data.get(q).getData());
								// checks if this point is in the core set,
								// if so, erases it so they do not
								// get double counted

								if (core.contains(data.get(q))) {
									if (core.size() > 0) {
										if (!core.get(p).equals(data.get(q))) {
											core.remove(data.get(q));
										}
									} // check that we can remove from the core
								} // end if for checking if q is in core
							} // end if for calculating data
						} // end else if for neighborhood checking
					} // end else for cluster checking data
				} // end for - data set checking
			} // end outer else for cluster checking core

			// all valid points in the cluster for p, increment for
			// cluster p +1
			currClustLbl++;
		} // end for core set checking

		System.out.println("End DBScan");
		return clusters;
	}
}
