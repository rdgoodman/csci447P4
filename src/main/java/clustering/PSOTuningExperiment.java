package clustering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PSOTuningExperiment {

	public PSOTuningExperiment(int dataSize, ArrayList<Datum> data, int numClusters, String datasetName) {

		String filePathOut1 = "src/main/resources/PSO" + datasetName + ".txt";
		String filePathOut2 = "src/main/resources/PSO_cl_" + datasetName + ".txt";
		
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

		for (int i = 0; i < 10; i++) {

			PSO pso = new PSO(0.5, 0.3, 0.7, 15, numClusters, dataSize, .5, .05);
			ClusterSet soln = new ClusterSet(pso.run(data));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
