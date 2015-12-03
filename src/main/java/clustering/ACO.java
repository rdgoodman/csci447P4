
package clustering;

import java.util.ArrayList;
import java.util.Random;

public class ACO {
	int k;
	int gens;
	int numAgents;
	int gridColSize;
	int gridRowSize;
	int neighborhoodSize;
	double kPlus;
	double kMinus;
	int[][][] grid;
	Distance d = new Distance();
	double alpha;
	int stepsize;

	public ACO(int k, int gens, int agents, int gridColSize, int gridRowSize, int neighborhoodSize, double kPlus,
			double kMinus, double alpha, int stepsize) {
		this.k = k;
		this.gens = gens;
		this.numAgents = agents;
		this.gridColSize = gridColSize;
		this.gridRowSize = gridRowSize;
		this.neighborhoodSize = neighborhoodSize;
		this.kPlus = kPlus;
		this.kMinus = kMinus;
		this.alpha = alpha;
		this.stepsize = stepsize;
	}

	public ArrayList<Cluster> run(ArrayList<Datum> data) {
		grid = new int[gridRowSize][gridColSize][2];
		for (int i = 0; i < gridRowSize; i++) {
			for (int j = 0; j < gridColSize; j++) {
				grid[i][j][0] = -1;// grid[i][j][0] is the position i, j, and the agent number
				grid[i][j][1] = -1;// grid[i][j][1] is the position i, j, and the data number
			}
		}
		ArrayList<ACOAgent> agents = initializeAgents();
		ArrayList<Cluster> clusters = initializeClusters(data);

		for (int i = 0; i < gens; i++) {// number of generations
			for (int j = 0; j < numAgents; j++) { // number of agents
				// move agent randomly
				ACOAgent cur = agents.get(j);
				int pr = cur.getRow();
				int pc = cur.getColumn();
				int pr1 = Math.floorMod((pr + stepsize), gridRowSize);// find all positions Agents can move to
				int pr2 = Math.floorMod((pr - stepsize), gridRowSize);
				int pc1 = Math.floorMod((pc + stepsize), gridColSize);
				int pc2 = Math.floorMod((pc - stepsize), gridColSize);
				//boolean move = true;
				int dir = (int) (Math.random() * 4);
				//while (move) {
				if (dir == 0) {
					if (grid[pr][pc1][0] == -1) {
						agents.get(j).setColumn(pc1);
						grid[pr][pc1][0] = j;
						grid[pr][pc][0] = -1;
					} else {
						dir = 1;
					}
				}
				if(dir == 1){
					if(grid[pr][pc2][0] == -1){
						agents.get(j).setColumn(pc2);
						grid[pr][pc2][0] = j;
						grid[pr][pc][0] = -1;
					}else {
						dir = 2;
					}
				}
				if(dir == 2){
					if(grid[pr1][pc][0] == -1){
						agents.get(j).setRow(pr1);
						grid[pr1][pc][0] = j;
						grid[pr][pc][0] = -1;
					}
					else{
						dir = 3;
					}
				}
				if(dir == 3){
					if(grid[pr2][pc][0] == -1){
						agents.get(j).setRow(pr2);
						grid[pr2][pc][0] = j;
						grid[pr][pc][0] = -1;
					}else{
						dir = 0;
						if (dir == 0) {
							if (grid[pr][pc1][0] == -1) {
								agents.get(j).setColumn(pc1);
								grid[pr][pc1][0] = j;
								grid[pr][pc][0] = -1;
							} else {
								dir = 1;
							}
						}
						if(dir == 1){
							if(grid[pr][pc2][0] == -1){
								agents.get(j).setColumn(pc2);
								grid[pr][pc2][0] = j;
								grid[pr][pc][0] = -1;
							}else {
								dir = 2;
							}
						}
						if(dir == 2){
							if(grid[pr1][pc][0] == -1){
								agents.get(j).setRow(pr1);
								grid[pr1][pc][0] = j;
								grid[pr][pc][0] = -1;
							}
						}
					}
				}

				//}

				// l = does agent j carry an item?
				boolean l = false;
				if (!agents.get(j).getCarried().equals(null)) {
					l = true;
				}
				// e = is current position occupied by item?
				boolean e = false;
				if (grid[agents.get(j).getRow()][agents.get(j).getColumn()][1] != -1) {
					e = true;
				}
				// if l == True and e == False
				if (l && !e) {
					// i = item carried by agent j
					boolean d = false;
					// drop i if probability says so
					double pDrop = 1;
					double fi = evaluateFitness(data, agents.get(j).getCarried(), agents.get(j).getRow(),
							agents.get(j).getColumn());
					if (fi <= kMinus) {
						pDrop = 2 * fi;
					}
					if ((Math.random()) <= pDrop) {
						drop(agents.get(j));
					}
				}
				if (!l && e) {
					// if l == False and e == True
					// i = object at position
					// boolean p = false;
					// pick up i if probability says so
					double pPick = Math.pow((kPlus / (kPlus + evaluateFitness(data, agents.get(j).getCarried(),
							agents.get(j).getRow(), agents.get(j).getColumn()))), 2);
					if ((Math.random()) <= pPick) {
						pickUp(agents.get(j), data.get(grid[agents.get(j).getRow()][agents.get(j).getColumn()][1]));
					}

				}
			}
		}
		clusters = clusterAssignment(clusters);// will take grid at some point
		return clusters;
	}

	private void drop(ACOAgent a) {
		grid[a.getRow()][a.getColumn()][1] = a.getDataIndex();
		a.drop();
	}

	private void pickUp(ACOAgent a, Datum dat) {
		int dataindex = grid[a.getRow()][a.getColumn()][1];
		grid[a.getRow()][a.getColumn()][1] = -1;
		a.pickUp(dataindex, dat);
	}

	private double evaluateFitness(ArrayList<Datum> data, Datum dat, int row, int col) {
		double sumF = 0.0;
		for (int i = 0 - neighborhoodSize; i <= neighborhoodSize; i++) {
			for (int j = 0 - neighborhoodSize; j <= neighborhoodSize; j++) {
				int posRow = Math.floorMod((row + i), gridRowSize);
				int posCol = Math.floorMod((col + j), gridRowSize);
				if (grid[posRow][posCol][1] != -1 && i != 0 && j != 0) {
					double tempF = d.calculateDistance(dat.getData(), data.get(grid[posRow][posCol][1]).getData(),
							dat.getData().size());
					tempF = tempF / alpha;
					tempF = 1 - tempF;
					sumF += tempF;
				}
			}
		}

		sumF = sumF / Math.pow(neighborhoodSize, 2);
		return sumF;
	}

	private ArrayList<Cluster> initializeClusters(ArrayList<Datum> data) {// TODO Random scatter in toroidal grid
		Random rand = new Random();
		// int indim = in.get(0).size(); //figure out dimensionality of input
		ArrayList<Cluster> clusters = new ArrayList<Cluster>(); // initalize cluster array
		// clusters created randomly, refined through time
		for (int i = 0; i < k; i++) {// Create k clusters
			int randCentroid = rand.nextInt(data.size());

			Cluster cluster = new Cluster(data.get(randCentroid).getData(), i);
			clusters.add(cluster);
		}
		for (int j = 0; j < data.size(); j++) {
			int cf = rand.nextInt(k);
			// data.get(j).assignToCluster(clusters.get(cf));
			clusters.get(cf).addPoint(data.get(j));
		}
		// System.out.println("begin clusters: " + clusters);
		// System.out.println("Clusters created: " + clusters);
		// clusters = trainClusters(in, clusters, 5, inDim);
		// System.out.println("end clusters: " + clusters);
		return clusters;
	}

	private ArrayList<ACOAgent> initializeAgents() {// TODO Randomly scatter agents in toroidal grid
		ArrayList<ACOAgent> agents = new ArrayList<ACOAgent>();

		return agents;
	}

	private ArrayList<Cluster> clusterAssignment(ArrayList<Cluster> clusters) {// will take toroidal grid and assign data to clusters
		return clusters;
	}
}
