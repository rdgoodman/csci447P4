package clustering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ANNTuningExperiment {

	public ANNTuningExperiment(int dataSize, ArrayList<Datum> data, int numClusters, String datasetName) {

		String filePathOut1 = "";

		FileWriter writer1 = null;
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

			CompetitiveANN ann = new CompetitiveANN(.2, dataSize, numClusters);
			ClusterSet soln = new ClusterSet(ann.run(train, test));
			double ratio = soln.calcSeparation() / soln.calcCohesion();

			filePathOut1 = "src/main/resources/" + datasetName + ".txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write(String.valueOf(ratio) + "\n");
				writer1.close();

			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
