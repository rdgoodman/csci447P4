package clustering;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Particle {

	// number of cluster centroid vectors
	private int numClusters;
	// number of dimensions in input vectors
	private int numDimensions;

	// Arraylist(i)(j) is the jth cluster centroid vector of
	// the ith particle in cluster Cij
	private ArrayList<Cluster> centroids;
	private double fitness;

	// personal best position
	private ArrayList<ArrayList<Double>> pbest_store;
	private double bestFitness;
	
	ArrayList<ArrayList<Double>> velocity;

	/**
	 * Initializes a single candidate solution
	 * 
	 * @param numClusters
	 *            number of desired clusters
	 * @param numDimensions
	 *            size of input vector
	 */
	public Particle(int numClusters, int numDimensions) {
		this.numClusters = numClusters;
		centroids = new ArrayList<Cluster>();
		this.numDimensions = numDimensions;
		velocity = new ArrayList<ArrayList<Double>>();
		initVelocity();

		initialize();
		// initial best position
		pbest_store = copyBest();
		bestFitness = Double.MAX_VALUE;
	}
	
	/**
	 * Overloaded for reconstructing solution
	 */
	public Particle(ArrayList<Cluster> centroids){
		this.centroids = centroids;
		this.numClusters = centroids.size();
		this.numDimensions = centroids.get(0).getCentroid().size();
	}
	

	/**
	 * Randomly initializes particle position in the search space
	 */
	private void initialize() {
		for (int c = 0; c < numClusters; c++) {
			ArrayList<Double> vector = new ArrayList<Double>();
			for (int d = 0; d < numDimensions; d++) {
				// randomly initialize each element of each centroid vector
				double random = Math.random()/100;
				double p = Math.random();
				// allows some elements to be initially zero
				if (p < 0.1){
					vector.add(0.0);
				} else {
					vector.add(random);
				}
			}
			centroids.add(new Cluster(vector, c));
		}
	}
	
	/**
	 * Initializes velocities to zero
	 */
	private void initVelocity(){
		for (int c = 0; c < numClusters; c++){
			ArrayList<Double> v = new ArrayList<Double>();
			for (int d = 0; d < numDimensions; d++){
				double prob = Math.random();
				if(prob < .5){
					v.add(Math.random());
				} else {
					v.add(Math.random() * -1);
				}
			}
			velocity.add(v);
		}
	}

	/**
	 * Assigns a data vector to the cluster that minimizes the distance from z
	 * to the centroid
	 */
	protected int findBestCluster(Datum z) {
		// minimization of distance
		double min = Double.MAX_VALUE;
		int minIndex = 0;


		// iterates through all clusters in this particle
		for (int c = 0; c < numClusters; c++) {
			double dist = calcDistToCentroid(c, z.getData());
			// finds minimum distance
			if (dist < min) {
				min = dist;
				minIndex = c;
			}
		}

		// places this datum in the best cluster
		// (automatically assigns in other direction too)
		centroids.get(minIndex).addPoint(z);
		return minIndex;
	}

	/**
	 * Calculates Euclidean distance to the index-th centroid in this particle
	 * 
	 * @param index
	 *            centroid to calculate distance to
	 * @param z
	 *            data vector
	 */
	private double calcDistToCentroid(int index, ArrayList<Double> z) {
		double sum = 0;
		for (int i = 0; i < numDimensions; i++) {
			sum += Math.pow(z.get(i) - centroids.get(index).getCentroid().get(i), 2);
		}
		
		sum = Math.sqrt(sum);
		return sum;
	}

	/**
	 * Empties the list of points assigned to each cluster
	 */
	protected void clearClusters() {
		for (Cluster c : centroids) {
			c.clear();
		}
	}

	/**
	 * Calculates this particle's fitness based on quantization error
	 */
	protected double calcFitness(ArrayList<Datum> data, double percent) {
		// see "Data Clustering using Particle Swarm Optimization"
		// equation 8
		double sum = 0;
		for (Cluster c : centroids) {
			
			// penalize empty and very small clusters
			if (c.getPts().size() == 0){
				fitness = Double.MAX_VALUE;
				return fitness;
			} else if (c.getPts().size() < data.size()*percent){
				fitness = Double.MAX_VALUE/c.getPts().size();
				return fitness;
			}
			
			// otherwise, calculate quantization error as normal
			for (Datum d : c.getPts()) {
				sum += (calcDistToCentroid(c.getIndex(), d.getData()) / c.getPts().size());
			}
		}
		
		// divide by number of clusters
		fitness = sum / centroids.size();

		// handle personal best - recall, this is a min problem
		if (fitness < bestFitness) {
			bestFitness = fitness;
			pbest_store = copyBest();
		}

		return fitness;
	}
	
	
	/**
	 * Updates position based on velocity update
	 */
	protected void adjustPosition(ArrayList<ArrayList<Double>> velocityUpdate, double kappa, double phi){
		velocity = velocityUpdate;
		
		// don't wanna divide by 0
		if (phi < 4){
			phi = 4;
		} 
		if (kappa > 1){
			kappa = 1;
		} else if (kappa < 0){
			kappa = 0;
		}
		
		// constriction coefficient (velocity control)
		double chi = (2 * kappa);
		double denominator = 2 - phi - Math.sqrt(Math.sqrt(phi) * (phi-4));	
		denominator = Math.abs(denominator);
		chi = chi/denominator;

		for (int c = 0; c < numClusters; c++){
			for (int d = 0; d < numDimensions; d++){
				centroids.get(c).getCentroid().set(d, velocityUpdate.get(c).get(d) + centroids.get(c).getCentroid().get(d));// * chi);
				// don't go out of bounds
				if (centroids.get(c).getCentroid().get(d) < 0){
					centroids.get(c).getCentroid().set(d, 0.0);					
				} else if (centroids.get(c).getCentroid().get(d) > 1){
					centroids.get(c).getCentroid().set(d, 1.0);
				}
			}
		}
	}

	/**
	 * Copies the centroids associated with the best cluster
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<Double>> copyBest() {
		ArrayList<ArrayList<Double>> b = new ArrayList<ArrayList<Double>>();
		// copies one centroid at a time
		for (int c = 0; c < numClusters; c++) {
			ArrayList<Double> centroidCopy = new ArrayList<Double>();
			for (int d = 0; d < numDimensions; d++) {
				// copies dimension d from centroid c
				centroidCopy.add(centroids.get(c).getCentroid().get(d));
			}
			b.add(centroidCopy);
		}
		return b;
	}

	protected double getPersonalBestFitness() {
		return bestFitness;
	}

	protected ArrayList<ArrayList<Double>> getPersonalBest() {
		return pbest_store;
	}
	
	protected ArrayList<ArrayList<Double>> getPosition() {
		ArrayList<ArrayList<Double>> p = new ArrayList<ArrayList<Double>>();
		for (int c = 0; c < numClusters; c++){
			p.add(centroids.get(c).getCentroid());
		}
		return p;
	}
	
	protected ArrayList<Cluster> getClusters(){
		return centroids;
	}

	public void setPersonalBest(ArrayList<ArrayList<Double>> b) {
		this.pbest_store = b;
	}

	public ArrayList<ArrayList<Double>> getVelocity() {
		return velocity;
	}

	public void setVelocity(ArrayList<ArrayList<Double>> velocity) {
		this.velocity = velocity;
	}

	/**
	 * Prints a representation of the particle, for testing purposes only
	 */
	public void print() {
		DecimalFormat twoDForm = new DecimalFormat("#.####");
		System.out.println("PARTICLE: " + fitness);
		for (int i = 0; i < numClusters; i++) {
			System.out.print("Cluster " + i + ": ");
			for (int j = 0; j < numDimensions; j++) {
				System.out.print(Double.valueOf(twoDForm.format(centroids.get(i).getCentroid().get(j))) + "  ");
			}
			System.out.println();
		}
	}
}
