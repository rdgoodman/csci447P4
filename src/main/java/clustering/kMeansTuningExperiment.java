package clustering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class kMeansTuningExperiment {

	public kMeansTuningExperiment(int dataSize, ArrayList<Datum> data) {

		String filePathOut1 = "";

		FileWriter writer1 = null;

		int k = 2;
		int numGens = 10;

		for (int i = 0; i < 4; i++) {
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
				k = 3;
			} else if (k == 3) {
				k = 4;
			} else if (k == 4) {
				k = 6;
			} else if (k == 6) {
				k = 8;
			}
		} //end for
		
		k = 2;
		numGens = 100;

		for (int i = 0; i < 4; i++) {
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
				k = 3;
			} else if (k == 3) {
				k = 4;
			} else if (k == 4) {
				k = 6;
			} else if (k == 6) {
				k = 8;
			}
		} //end for
		
		k = 2;
		numGens = 1000;

		for (int i = 0; i < 4; i++) {
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
				k = 3;
			} else if (k == 3) {
				k = 4;
			} else if (k == 4) {
				k = 6;
			} else if (k == 6) {
				k = 8;
			}
		} //end for
	
		k = 2;
		numGens = 10000;

		for (int i = 0; i < 4; i++) {
			KMeans kmeans = new KMeans(k, numGens);
			ClusterSet soln = new ClusterSet(kmeans.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/10000gens.txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (k == 2) {
				k = 3;
			} else if (k == 3) {
				k = 4;
			} else if (k == 4) {
				k = 6;
			} else if (k == 6) {
				k = 8;
			}
		} //end for
	}
}