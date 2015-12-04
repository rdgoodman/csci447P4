package clustering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class KMeansTuningExperiment {

	public KMeansTuningExperiment(int dataSize, ArrayList<Datum> data) {

		String filePathOut1 = "";

		FileWriter writer1 = null;

		int k = 2;
		int numGens = 10;

		for (int i = 0; i < 5; i++) {
			KMeans kmeans = new KMeans(k, numGens);
			ClusterSet soln = new ClusterSet(kmeans.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/10gens.txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (k == 2) {
				k = 5;
			} else if (k == 5) {
				k = 8;
			} else if (k == 8) {
				k = 15;
			} else if (k == 15) {
				k = 20;
			}
		} //end for
		
		k = 2;
		numGens = 100;

		for (int i = 0; i < 5; i++) {
			KMeans kmeans = new KMeans(k, numGens);
			ClusterSet soln = new ClusterSet(kmeans.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/100gens.txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (k == 2) {
				k = 5;
			} else if (k == 5) {
				k = 8;
			} else if (k == 8) {
				k = 15;
			} else if (k == 15) {
				k = 20;
			}
		} //end for
		
		k = 2;
		numGens = 1000;

		for (int i = 0; i < 5; i++) {
			KMeans kmeans = new KMeans(k, numGens);
			ClusterSet soln = new ClusterSet(kmeans.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/1000gens.txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (k == 2) {
				k = 5;
			} else if (k == 5) {
				k = 8;
			} else if (k == 8) {
				k = 15;
			} else if (k == 15) {
				k = 20;
			}
		} //end for
	
		k = 2;
		numGens = 2000;

		for (int i = 0; i < 5; i++) {
			KMeans kmeans = new KMeans(k, numGens);
			ClusterSet soln = new ClusterSet(kmeans.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/2000gens.txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (k == 2) {
				k = 5;
			} else if (k == 5) {
				k = 8;
			} else if (k == 8) {
				k = 15;
			} else if (k == 15) {
				k = 20;
			}
		} //end for
	}
}