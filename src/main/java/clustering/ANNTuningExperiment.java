package clustering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ANNTuningExperiment {

	public ANNTuningExperiment(int dataSize, ArrayList<Datum> data, int numClusters, String datasetName) {

		String filePathOut1 = "src/main/resources/ANN" + datasetName + ".txt";
		String filePathOut2 = "src/main/resources/ANN_cl_" + datasetName + ".txt";
		
		File ratioOutput = new File(filePathOut1);
		if (!ratioOutput.exists()){
			try {
				ratioOutput.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File clusterOutput = new File(filePathOut2);
		if (!clusterOutput.exists()){
			try {
				clusterOutput.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		FileWriter writer1 = null;
		FileWriter writer2 = null;
		
		ArrayList<Datum> train = new ArrayList<Datum>();
		ArrayList<Datum> test = new ArrayList<Datum>();

		for (int i = 0; i < 10; i++) {
			// randomize training and test sets
			Collections.shuffle(data);
			train.clear();
			test.clear();
			for (Datum d : data) {
				double p = Math.random();
				if (p < .3) {
					test.add(d);
				} else {
					train.add(d);
				}
			}

			CompetitiveANN ann = new CompetitiveANN(.15, dataSize, numClusters);
			ClusterSet soln = new ClusterSet(ann.run(train, test));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			int c = soln.getNumClusters();


			try {
				writer1 = new FileWriter(ratioOutput, true);
				writer1.write(String.valueOf(ratio) + "\n");
				writer1.close();
				writer2 = new FileWriter(clusterOutput, true);
				writer2.write(String.valueOf(c + "\n"));
				writer2.close();

			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
