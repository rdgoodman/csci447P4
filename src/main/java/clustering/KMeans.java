//package clustering;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.Random;
//
//public class KMeans {
//	int k; 
//	//String fileName;
//	int gens;
//	
//	private Distance d = new Distance();
//	public KMeans(int k, int gens){
//		this.k = k;
//		this.gens = gens;
//	}
//	
//	public ArrayList<Cluster> run(ArrayList<Datum> data){
//		ArrayList<Cluster> clusters = initializeClusters(data);
//		int dim = data.get(0).inputs.size();
//		for (int i = 0; i < gens; i++) {//number of iterations
//			//need to assign each datapoint to the nearest cluster
//            for (int j = 0; j < data.size(); j++) {//each datapoint
////
//                double curD = Integer.MAX_VALUE;
//                int smallestCluster = 0;
//                ArrayList<Datum> tempDat = new ArrayList<Datum>();
//                for (int c = 0; c < clusters.size(); c++) {//for each cluster
//                    //System.out.println("iteration: "+ i + ", datapoint: " + j + ", cluster: " + c);
//
//                    double tempD = d.calculateDistance(data.get(j), clusters.get(c).getCentroid(), dim);//find closest cluster
//                    //System.out.println(" i: " + i + ", j: " + j + ", c: " + c+", tempD: " + tempD + ", curD: " + curD + ", input: " + in.get(j));
//                    if (tempD < curD) {
//                        smallestCluster = c;
//                        curD = tempD;
//                        //System.out.println(curD + " and " + smallestCluster);
//                    }
//                }
//                int oldCluster = data.get(j).getClusterIndex();
//                //data.get(j).assignToCluster(clusters.get(smallestCluster));
//                if(oldCluster != smallestCluster){
//                	clusters.get(smallestCluster).addPoint(j);
//                	ArrayList<Datum> tempPts = clusters.get(oldCluster).getPts();
//                	for(int l = 0; l < tempPts.size(); l++){
//                		if(d.calculateDistance(data.get(j).getData(), tempPts.get(l).getData(), dim) == 0){//TODO find a better way to check equality
//                			tempPts.remove(l);
//                		}
//                	}
//                	clusters.get(oldCluster).setPts(tempPts);
//                }
//                
////                //System.out.println("smallest Cluster : " + in.get(j).get(dim));
////                //System.out.println("cluster: " + clusters.get((int)in.get(j).get(dim)));
//            }
//            for (int c = 0; c < clusters.size(); c++) {//for each cluster
//            	clusters.get(c).setCentroid(clusters.get(c).calcMidpoint());
//                //System.out.println(c);
//                //double avg = 0;
//            	//need to find centroid
////                for (int l = 0; l < dim; l++) {//each dimension
////                    //System.out.println("c: " + c + ", l: " + l);
////                    double dimAvg = 0;
////                    int count = 0;
////                    for (int j = 0; j < insize; j++) {//each input
////                        //ystem.out.println(in.get(j).get(dim));
////                        double c3 = (double) in.get(j).get(dim);
////                        //System.out.println("c3: " + c3);
////                        int c2 = (int) (c3);
////                        //System.out.println("c2: " + c2);
////                        if (c2 == c) {
////                            int cast = (int) Math.round(in.get(j).get(l));
////                            //System.out.println("blergh: " + blergh);
////                            dimAvg += (double) cast;
////                            //System.out.println("Dim Avg: " + dimAvg);
////                            count++;
////                            //ystem.out.println(count);
////                        }
////                    }
////                    if (count != 0) {
////                        //System.out.println("DimAvg: " + dimAvg + ", count: " + count + ", result: " + dimAvg / count);
////                        dimAvg = dimAvg / count;
////                    } else {
////                        dimAvg = 0;
////                    }
////                    clusters.get(c).set(l, dimAvg);
////                }
//            }
//            //System.out.println(i + " : " + clusters);
//        }
//        double maxD = 0;
//        double curD = 0;
//        for (int i = 0; i < clusters.size(); i++) {
//            for (int j = 0; j < clusters.size(); j++) {
//                if (i != j) {
//                    curD = d.calculateDistance(clusters.get(i).getCentroid(), clusters.get(j).getCentroid(), dim);
//                    //System.out.println(curD);
//                    if (curD > maxD) {
//                        maxD = curD;
//                    }
//                }
//            }
//        }
//        System.out.println("maxD: " + maxD);
//		
//		return clusters;
//	}
//	
//	private ArrayList<Cluster> initializeClusters(ArrayList<Datum> data){
//		Random rand = new Random();
//        //int indim = in.get(0).size(); //figure out dimensionality of input
//        ArrayList<Cluster> clusters = new ArrayList(); //initalize cluster array
//        //clusters created randomly, refined through time
//        for (int i = 0; i < k; i++) {//Create k clusters
//        	int randCentroid = rand.nextInt(data.size());
//        	
//        	Cluster cluster = new Cluster(data.get(randCentroid), i);
//            clusters.add(cluster);
//        }
//            for (int j = 0; j < data.size(); j++) {
//            	int cf = rand.nextInt(k);
//            	//data.get(j).assignToCluster(clusters.get(cf));
//                clusters.get(cf).addPoint(j);
//            }
//        //System.out.println("begin clusters: " + clusters);
//        //System.out.println("Clusters created: " + clusters);
//        //clusters = trainClusters(in, clusters, 5, inDim);
//        //System.out.println("end clusters: " + clusters);
//        return clusters;
//	}
//	
//	public int getK() {
//		return k;
//	}
//
//	public void setK(int k) {
//		this.k = k;
//	}
//
//	public int getGens() {
//		return gens;
//	}
//
//	public void setGens(int gens) {
//		this.gens = gens;
//	}
//
//	public ArrayList<Datum> readData(String fname){
//		BufferedReader br = null; // read from data
//        String line = "";
//        String cvsSplitBy = ",";
//        ArrayList<Datum> data = new ArrayList<Datum>();
//        try {
//            br = new BufferedReader(new FileReader(fname));
//
//            while ((line = br.readLine()) != null) {
//                ArrayList<Double> inputs = new ArrayList<Double>();
//                ArrayList<Double> output = new ArrayList<Double>();
//                String[] example = line.split(cvsSplitBy);
//
//                // adds inputs (all but last number on line)
//                for (int i = 0; i < example.length - 1; i++) {
//                    Double in = Double.parseDouble(example[i]);
//                    inputs.add(in);
//                }
//
//                // puts input array into correctly-sized ArrayList of examples
//                
//                Datum datum = new Datum(inputs);
//                data.add(datum);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //System.out.println("Done reading in training data");
//
//        return data;
//	}
//	
//	
//}
