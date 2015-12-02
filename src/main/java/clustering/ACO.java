
package clustering;

import java.util.ArrayList;
import java.util.Random;

public class ACO {
	int k;
	int gens;
	int agents;
	public ACO(int k, int gens, int agents){
		this.k = k;
		this.gens = gens;
		this.agents = agents;
	}
	
	public ArrayList<Cluster> run(ArrayList<Datum> data){
		ArrayList<Cluster> clusters = initializeClusters(data);
		for(int i = 0; i < gens; i++){//number of generations
			for(int j = 0; j < agents; j++){ //number of agents
				
			}
		}
		
		return clusters;
	}
	private ArrayList<Cluster> initializeClusters(ArrayList<Datum> data){//TODO Random scatter in toroidal grid
		Random rand = new Random();
        //int indim = in.get(0).size(); //figure out dimensionality of input
        ArrayList<Cluster> clusters = new ArrayList(); //initalize cluster array
        //clusters created randomly, refined through time
        for (int i = 0; i < k; i++) {//Create k clusters
        	int randCentroid = rand.nextInt(data.size());
        	
        	Cluster cluster = new Cluster(data.get(randCentroid).getData(), i);
            clusters.add(cluster);
        }
            for (int j = 0; j < data.size(); j++) {
            	int cf = rand.nextInt(k);
            	//data.get(j).assignToCluster(clusters.get(cf));
                clusters.get(cf).addPoint(data.get(j));
            }
        //System.out.println("begin clusters: " + clusters);
        //System.out.println("Clusters created: " + clusters);
        //clusters = trainClusters(in, clusters, 5, inDim);
        //System.out.println("end clusters: " + clusters);
        return clusters;
	}
	
	private void initializeAgents(){//TODO Randomly scatter agents in toroidal grid
		
	}
	
}
