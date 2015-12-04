
package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class KMeans {
	int k; 
	//String fileName;
	int gens;
	int dim;
	private Distance d = new Distance();
	
	public KMeans(int k, int gens){
		this.k = k;
		this.gens = gens;
	}
	
	public ArrayList<Cluster> run(ArrayList<Datum> data){
		dim = data.get(0).getData().size();
		ArrayList<Cluster> clusters = initializeClusters(data);
		System.out.println("dimensions of data set: " + dim);
		for (int i = 0; i < gens; i++) {//number of iterations
			System.out.println("Beginning generation " + i);
			for(int m = 0; m < k; m++){
                System.out.println("size of cluster " + m + ": " + clusters.get(m).getPts().size());
                
                }
			//need to assign each datapoint to the nearest cluster
            for (int j = 0; j < data.size(); j++) {//each datapoint
            	//System.out.println("Determining which cluster data element " + j + " belongs to");
                double curD = 8;
                int smallestCluster = 0;
                
                //ArrayList<Datum> tempDat = new ArrayList<Datum>();
                for (int c = 0; c < clusters.size(); c++) {//for each cluster
                    //System.out.println("iteration: "+ i + ", datapoint: " + j + ", cluster: " + c);
                	
                    double tempD = d.calculateDistance(data.get(j).getData(), clusters.get(c).getCentroid(), dim);//find closest cluster
                    //System.out.println(" i: " + i + ", j: " + j + ", c: " + c+", tempD: " + tempD + ", curD: " + curD + ", smallestCluster: " + smallestCluster + ", current cluster: " + data.get(j).getClusterIndex() + ", input: " + data.get(j).getData().get(0) + ", centroid: " + clusters.get(c).getCentroid().get(0));
                    if (tempD < curD) {
                        smallestCluster = c;
                        curD = tempD;
                        //System.out.println("----> " + curD + " and " + smallestCluster);
                    }
                    
                    //System.out.println();
                }
                int oldCluster = data.get(j).getClusterIndex();
                //data.get(j).assignToCluster(clusters.get(smallestCluster));
                if(oldCluster != smallestCluster){
                	//System.out.println("Changing cluster from " + oldCluster + " to " + smallestCluster + " for data element " + j);
                	clusters.get(smallestCluster).addPoint(data.get(j));
                	//ArrayList<Datum> tempPts = clusters.get(oldCluster).getPts();
                	//for(int l = 0; l < tempPts.size(); l++){
                		//if(data.get(j).equals(tempPts.get(l))){//TODO find a better way to check equality
                			//System.out.println("Removing point " + j + " from cluster " + oldCluster + " which matched point " + l);
                			//System.out.println("Removing data " + data.get(j).getData() + " which matched " + tempPts.get(l).getData());
                			clusters.get(oldCluster).getPts().remove(data.get(j));
                		//}
                	//}
                	//clusters.get(oldCluster).setPts(tempPts);
                }
                
//                //System.out.println("smallest Cluster : " + in.get(j).get(dim));
//                //System.out.println("cluster: " + clusters.get((int)in.get(j).get(dim)));
            }
            for (int c = 0; c < clusters.size(); c++) {//for each cluster
            	if(clusters.get(c).getPts().size() < 2){
            		//System.out.println("Number of points in cluster " + c + ": " + clusters.get(c).getPts().size());
            		ArrayList<Datum> tempPts = clusters.get(c).getPts();
            		//System.out.println("Reinitializing cluster");
            		clusters.set(c, reinitialize(clusters.get(c), data));
            		clusters.get(c).setPts(tempPts);
            	}else{
            	//System.out.println("Number of points in cluster " + c + ": " + clusters.get(c).getPts().size());
            	//System.out.println("Finding centroid of cluster " + c);
            	clusters.get(c).setCentroid(clusters.get(c).calcMidpoint());
            	//System.out.println("----> " + clusters.get(c).getCentroid());
            	}
                
            }
            //System.out.println(i + " : " + clusters);
        }
        double maxD = 0;
        double curD = 0;
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = 0; j < clusters.size(); j++) {
                if (i != j) {
                    curD = d.calculateDistance(clusters.get(i).getCentroid(), clusters.get(j).getCentroid(), dim);
                    //System.out.println(curD);
                    if (curD > maxD) {
                        maxD = curD;
                    }
                }
            }
        }
        System.out.println("maxD: " + maxD);
		
		return clusters;
	}
	
	private ArrayList<Cluster> initializeClusters(ArrayList<Datum> data){
		System.out.println("initializing clusters");
		Random rand = new Random();
        //int indim = in.get(0).size(); //figure out dimensionality of input
        ArrayList<Cluster> clusters = new ArrayList<Cluster>(); //initalize cluster array
        //clusters created randomly, refined through time
        for (int i = 0; i < k; i++) {//Create k clusters
        	System.out.println("----> creating " + i + "th cluster");
        	ArrayList<Double> center = new ArrayList<Double>();
        	
        	int randCentroid = (int)(Math.random()*data.size());

        	Cluster cluster = new Cluster(data.get(randCentroid).getData(), i);
        	System.out.println("Initial cluster " + i + ": " +cluster.getCentroid());
        	clusters.add(cluster);
        }
        for (int j = 0; j < data.size(); j++) {
        	
        	int cf = rand.nextInt(k);
        	//data.get(j).assignToCluster(clusters.get(cf));
        	clusters.get(cf).addPoint(data.get(j));
        	System.out.println("----> Adding data point " + j + " to cluster " + cf);
        }
        //System.out.println("begin clusters: " + clusters);
        //System.out.println("Clusters created: " + clusters);
        //clusters = trainClusters(in, clusters, 5, inDim);
        //System.out.println("end clusters: " + clusters);
        return clusters;
	}
	private Cluster reinitialize(Cluster c, ArrayList<Datum> data){
		//ArrayList<Double> center = new ArrayList<Double>();
		int randCentroid = (int)(Math.random()*data.size());

		c.setCentroid(data.get(randCentroid).getData());
		return c;
	}
	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getGens() {
		return gens;
	}

	public void setGens(int gens) {
		this.gens = gens;
	}
	
	
}
