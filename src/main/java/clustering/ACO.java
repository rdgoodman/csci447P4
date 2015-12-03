
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

	
	/**
	 * Creates new ACO instance 
	 * 
	 * @param k
	 * @param gens
	 * @param agents
	 * @param gridColSize
	 * @param gridRowSize
	 * @param neighborhoodSize
	 * @param kPlus
	 * @param kMinus
	 * @param alpha
	 * @param stepsize
	 */
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
		System.out.println("initializing grid");
		grid = new int[gridRowSize][gridColSize][2];
		for (int i = 0; i < gridRowSize; i++) {
			for (int j = 0; j < gridColSize; j++) {
				grid[i][j][0] = -1;// grid[i][j][0] is the position i, j, and the agent number
				grid[i][j][1] = -1;// grid[i][j][1] is the position i, j, and the data number
			}
		}
		ArrayList<ACOAgent> agents = initializeAgents(grid);
		ArrayList<Cluster> clusters = initializeClusters(data, grid);

		for (int i = 0; i < gens; i++) {// number of generations
			System.out.println("Beginning generation " + i);
			for (int j = 0; j < numAgents; j++) { // number of agents
				//System.out.println("Agent " + j);
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
						//System.out.println("Moving " + j);
						agents.get(j).setColumn(pc1);
						grid[pr][pc1][0] = j;
						grid[pr][pc][0] = -1;
					} else {
						dir = 1;
					}
				}
				if(dir == 1){
					if(grid[pr][pc2][0] == -1){
						//System.out.println("Moving " + j);
						agents.get(j).setColumn(pc2);
						grid[pr][pc2][0] = j;
						grid[pr][pc][0] = -1;
					}else {
						dir = 2;
					}
				}
				if(dir == 2){
					if(grid[pr1][pc][0] == -1){
						//System.out.println("Moving " + j);
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
						//System.out.println("Moving "+j);
						agents.get(j).setRow(pr2);
						grid[pr2][pc][0] = j;
						grid[pr][pc][0] = -1;
					}else{
						dir = 0;
						if (dir == 0) {
							if (grid[pr][pc1][0] == -1) {
								//System.out.println("Moving " + j);
								agents.get(j).setColumn(pc1);
								grid[pr][pc1][0] = j;
								grid[pr][pc][0] = -1;
							} else {
								dir = 1;
							}
						}
						if(dir == 1){
							if(grid[pr][pc2][0] == -1){
								//System.out.println("Moving " + j);
								agents.get(j).setColumn(pc2);
								grid[pr][pc2][0] = j;
								grid[pr][pc][0] = -1;
							}else {
								dir = 2;
							}
						}
						if(dir == 2){
							if(grid[pr1][pc][0] == -1){
								//System.out.println("Moving " + j);
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
				if (agents.get(j).getCarried() != null) {
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
					//System.out.println("Dropping?");
					double fi = evaluateFitness(data, agents.get(j).getCarried(), agents.get(j).getRow(), agents.get(j).getColumn());
					if (fi <= kMinus) {
						pDrop = 2 * fi;
					}
					if ((Math.random()) <= pDrop) {
						System.out.println("Dropping");
						drop(agents.get(j));
					}
				}
				if (!l && e) {
					// if l == False and e == True
					// i = object at position
					// boolean p = false;
					// pick up i if probability says so
					System.out.println("Picking Up?");
					double pPick = Math.pow((kPlus / (kPlus + evaluateFitness(data, data.get(grid[agents.get(j).getRow()][agents.get(j).getColumn()][1]), agents.get(j).getRow(), agents.get(j).getColumn()))), 2);
					if ((Math.random()) <= pPick) {
						System.out.println("PickingUp");
						pickUp(agents.get(j), data.get(grid[agents.get(j).getRow()][agents.get(j).getColumn()][1]));
					}

				}
			}
		}
		clusters = clusterAssignment(grid, clusters, data);// will take grid at some point
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
				//System.out.println("posRow: " + posRow + ", posCol:" + posCol);
				if (grid[posRow][posCol][1] != -1 && i != 0 && j != 0) {
					//System.out.println("Dat: " + dat.getData() + ", grid result: " + grid[posRow][posCol][1]);
					double tempF = d.calculateDistance(dat.getData(), data.get(grid[posRow][posCol][1]).getData(), dat.getData().size());
					tempF = tempF / alpha;
					tempF = 1 - tempF;
					sumF += tempF;
				}
			}
		}

		sumF = sumF / Math.pow(neighborhoodSize, 2);
		return sumF;
	}

	private ArrayList<Cluster> initializeClusters(ArrayList<Datum> data, int[][][] grid) {// TODO Random scatter in toroidal grid
		ArrayList<Cluster> clusters = new ArrayList<Cluster>(); // initialize cluster array
		for(int i = 0; i < data.size(); i++){
			int randRow = (int) (Math.random()*gridRowSize);
			int randCol = (int) (Math.random()*gridColSize);
			while(grid[randRow][randCol][1] != -1){
				randRow = (int) (Math.random()*gridRowSize);
				randCol = (int) (Math.random()*gridColSize);
			}
			grid[randRow][randCol][1] = i;
		}
		//clusters = clusterAssignment(grid, clusters);
		return clusters;
	}

	private ArrayList<ACOAgent> initializeAgents(int[][][] grid) {// TODO Randomly scatter agents in toroidal grid
		ArrayList<ACOAgent> agents = new ArrayList<ACOAgent>();
		for(int i = 0; i < numAgents; i++){
			int randRow = (int) (Math.random()*gridRowSize);
			int randCol = (int) (Math.random()*gridColSize);
			while(grid[randRow][randCol][0] != -1){
				randRow = (int) (Math.random()*gridRowSize);
				randCol = (int) (Math.random()*gridColSize);
			}
			grid[randRow][randCol][0] = i;
			agents.add(new ACOAgent(randRow, randCol, gridRowSize, gridColSize));
		}

		return agents;
	}

	private ArrayList<Cluster> clusterAssignment(int[][][] grid, ArrayList<Cluster> clusters, ArrayList<Datum> data) {// will take toroidal grid and assign data to clusters
		ArrayList<Datum> newData = new ArrayList<Datum>();
		for(int i = 0; i < gridRowSize; i++){
			for(int j = 0; j < gridColSize; j++){
				if(grid[i][j][1] != -1){
					ArrayList<Double> in = new ArrayList<Double>();
					in.add((double)i);
					in.add((double)j);
					in.add((double)grid[i][j][1]);
					//System.out.println(grid[i][j][1]);
					newData.add(new Datum(in));
				}
			}
		}
		KMeans kmeans = new KMeans(5, 300);
		clusters = kmeans.run(newData);
		ArrayList<Cluster> clusters2 = new ArrayList<Cluster>();
		for(int i = 0; i < clusters.size(); i++){
			clusters2.add(new Cluster(clusters.get(i).getCentroid(), i));
			for(int j = 0; j < clusters.get(i).getPts().size(); j++){
				clusters2.get(i).addPoint(data.get((int) Math.floor(clusters.get(i).getPts().get(j).getData().get(2))));
			}
		}
		return clusters2;
	}
}
