package clustering;

import java.util.ArrayList;
import java.util.Random;

//TO DO: Change centroid to all -1s to show it does not apply here

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
//		ArrayList<Datum> noise = new ArrayList<Datum>();

		// Holds all visited points in the dataset, for constructing the core
		// set
		ArrayList<Datum> visited = new ArrayList<Datum>();

		// first train data
		System.out.println(">>>>> TRAINING - CHECK IF THIS REQUIRES TRAINING!");
		System.out.println(">>>>>>>>>>>>DBSCAN");
		// Build the set of all core points, then perform DB scan using the core
		// set
		// Select a data point at random to test as a core point
		Random rand = new Random();

		// generates a random number within the size of the dataset
		int randomPt = rand.nextInt((training.size() - 1) + 1) + 1;
		//gets a random point from the data set to serve as the first core candidate
		Datum currPt = training.get(randomPt);

		int iterator = 0;
		//while there are unvisited points in the dataset
		while (visited.size() < training.size() - 1) {
			// add this point to the list of visited points
			if (!visited.contains(currPt)) {
				visited.add(currPt);
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
			// clear the border set, because we are looking at a new core
			border.clear();
		} // end while, now all points are categorized

		// the label associated with the first cluster
		int currClustLbl = 0;
		
		// Now begin the DB scan portion of the algorithm
		// for each point in the core set
		for (int p = 0; p < core.size() - 1; p++) {
			//check if p is already assigned to a cluster
			if (clusters.contains(core.get(p))) {
				//do nothing, this is already assigned to a cluster
			} else {
				// make a new cluster
				clusters.add(new Cluster(core.get(p).getData(), currClustLbl));
				// add the point p to this cluster
				clusters.get(currClustLbl).addPoint(core.get(p));
			
				// for each point in the training set
				for (int q = 0; q < training.size() - 1; q++) {
					// if this point is already in a cluster, do not add again
					if (clusters.contains(training.get(q))) {
						//do nothing, this is already assigned to a cluster
					} else if (core.get(p).calcDist(training.get(q)) <= epsilon) {
						//getting here means point is in the neighborhood of p
						//so add it to the cluster
						clusters.get(currClustLbl).addPoint(training.get(q));
						//checks if this point is in the core set, if so - erases it so they do not 
						//get double counted
						if (core.contains(training.get(q))) {
							core.remove(training.get(q));
						} //end if for core checking
					} //end else if for neighborhood checking
				} //end inner for for dataset checking
			} //end else for cluster checking
			
			//all valid points in the cluster for p, increment for cluster p + 1
			currClustLbl++;
		} //end for
		
		return clusters;
	}

}
