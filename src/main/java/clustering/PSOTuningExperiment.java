package clustering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PSOTuningExperiment {

	public PSOTuningExperiment(int dataSize, ArrayList<Datum> data, int numClusters, String datasetName) {

		String filePathOut1 = "";
		String filePathOut2 = "";

		FileWriter writer1 = null;
		FileWriter writer2 = null;

		for (int i = 0; i < 10; i++) {

			PSO pso = new PSO(0.5, 0.3, 0.7, 15, numClusters, dataSize, .5, .1);
			ClusterSet soln = new ClusterSet(pso.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			int c = soln.getNumClusters();

			filePathOut1 = "src/main/resources/PSO" + datasetName + ".txt";
			filePathOut2 = "src/main/resources/PSO_cl_" + datasetName + ".txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write(String.valueOf(ratio) + "\n");
				writer1.close();
				writer2 = new FileWriter(filePathOut2, true);
				writer2.write(String.valueOf(c + "\n"));
				writer2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
