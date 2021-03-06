package clustering;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PSO {

	private ArrayList<Particle> swarm;
	private double omega;
	private double phi1;
	private double phi2;
	private double kappa;
	private ArrayList<Datum> data;
	private ArrayList<ArrayList<Double>> gbest_store;
	private double percent;

	/**
	 * 
	 * @param omega
	 *            multiplier on momentum/inertia
	 * @param phi1
	 *            upper bound on multiplier on social component
	 * @param phi2
	 *            upper bound on multiplier on cognitive component
	 * @param swarmSize
	 *            number of particles in swarm
	 * @param numClusters
	 *            number of clusters desired
	 * @param numDimensions
	 *            length of input vector
	 * @param kappa
	 * 			  used for constriction coefficient
	 * @param percent
	 * 			  minimum acceptable % of data per cluster
	 */
	public PSO(double omega, double phi1, double phi2, int swarmSize, int numClusters, int numDimensions, double kappa, double percent) {
		// According to tuning, best omega = .5, phi1 = .3, phi2 = .7, size = 15, percent = .1
		this.omega = omega;
		this.phi1 = phi1;
		this.phi2 = phi2;
		this.kappa = kappa;
		this.percent = percent;
		
		swarm = new ArrayList<Particle>();
		initSwarm(swarmSize, numClusters, numDimensions);
	}

	/**
	 * Initializes a swarm of particles
	 */
	private void initSwarm(int swarmSize, int numClusters, int numDimensions) {
		for (int i = 0; i < swarmSize; i++) {
			swarm.add(new Particle(numClusters, numDimensions));
		}
	}

	/**
	 * Runs the optimization given a set of data to cluster
	 * 
	 * @param data
	 *            the data vectors to cluster
	 * @return best clustering
	 */
	public ArrayList<Cluster> run(ArrayList<Datum> data) {
		this.data = data;
		
		// need an initial gbest
		gbest_store = swarm.get(0).copyBest();
		double minGlobalFitness = Double.MAX_VALUE;

		int iterations = 0;
		
		while (iterations < 100) {	
			System.out.println(">>> Iteration " + iterations );
			int pcount = 0;
			for (Particle p : swarm) {
				// Step 1: evaluate fitness
				for (Datum z : data) {
					p.findBestCluster(z);
				}
				double fit = p.calcFitness(data, percent);
				p.clearClusters();
				
				// Step 2: update global best
				// note: local best taken care of in fitness evaluation within particle
				if (fit < minGlobalFitness) {
					// this particle is the new global best
					gbest_store = p.copyBest();
					minGlobalFitness = fit;
					
					System.out.println("%%%%%%% NEW GLOBAL BEST %%%%%%%");
					evaluateGBest();
				}
				
				// Step 3: velocity update
				p.adjustPosition(calcVelocityUpdate(p), kappa, phi1 + phi2);	
				
				pcount++;
			}
			iterations++;
		}

		// reassign/reevaluate g_best & return
		Particle solution = evaluateGBest();
		System.out.println();
		solution.print();
		return solution.getClusters();
	}
	
	
	/**
	 * Constructs a full solution out of stored info on global best
	 */
	private Particle evaluateGBest(){
		ArrayList<Cluster> solution = new ArrayList<Cluster>();
		
		// rebuilds the global best particle from the stored centroids
		for (int c = 0; c < gbest_store.size(); c++){
			solution.add(new Cluster(gbest_store.get(c), c));
		}		
		Particle p = new Particle(solution);
		
		for (Datum z : data) {
			p.findBestCluster(z);
		}
		double fit = p.calcFitness(data, percent);
		p.print();
		System.out.println("Best fitness: " + fit);

		return p;
	}
	
	/**
	 * Calculates particle velocity update
	 */
	protected ArrayList<ArrayList<Double>> calcVelocityUpdate(Particle p){		
		ArrayList<ArrayList<Double>> velocity = new ArrayList<ArrayList<Double>>();		
		ArrayList<ArrayList<Double>> position = p.getPosition();
		ArrayList<ArrayList<Double>> v_old = p.getVelocity();
		ArrayList<ArrayList<Double>> pbest = p.getPersonalBest();
		
		// calculates position update for each dimension of each cluster
		for (int c = 0; c < position.size(); c++){
			ArrayList<Double> v = new ArrayList<Double>();
			for (int d = 0; d < position.get(c).size(); d++){
				// calculates momentum, social, global components
				double momentum = omega * v_old.get(c).get(d);
				double social = Math.random() * phi1 * (gbest_store.get(c).get(d) - position.get(c).get(d));
				double global = Math.random() * phi2 * (pbest.get(c).get(d) - position.get(c).get(d));
				v.add(momentum + social + global);
			}
			velocity.add(v);
		}
		return velocity;
	}

	/**
	 * Prints a representation of the swarm for testing purposes
	 */
	public void print() {
		for (int i = 0; i < swarm.size(); i++) {
			System.out.print(i + " ~ ");
			swarm.get(i).print();
			System.out.println();
		}
	}

}
