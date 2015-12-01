package clustering;

import java.util.ArrayList;
import java.util.Random;

public class ACO {
	int k;
	int gens;
	public ACO(int k, int gens){
		this.k = k;
		this.gens = gens;
	}
	
	public ArrayList<Cluster> run(ArrayList<Datum> data){
		ArrayList<Cluster> clusters = initializeClusters(data);
		
		
		return clusters;
	}
	private ArrayList<Cluster> initializeClusters(ArrayList<Datum> data){
		Random rand = new Random();
        //int indim = in.get(0).size(); //figure out dimensionality of input
        ArrayList<Cluster> clusters = new ArrayList(); //initalize cluster array
        //clusters created randomly, refined through time
        for (int i = 0; i < k; i++) {//Create k clusters
        	int randCentroid = rand.nextInt(data.size());
        	
        	Cluster cluster = new Cluster(data.get(randCentroid), i);
            clusters.add(cluster);
        }
            for (int j = 0; j < data.size(); j++) {
            	int cf = rand.nextInt(k);
            	//data.get(j).assignToCluster(clusters.get(cf));
                clusters.get(cf).addPoint(j);
            }
        //System.out.println("begin clusters: " + clusters);
        //System.out.println("Clusters created: " + clusters);
        //clusters = trainClusters(in, clusters, 5, inDim);
        //System.out.println("end clusters: " + clusters);
        return clusters;
	}
	
}
