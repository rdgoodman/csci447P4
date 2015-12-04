package clustering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DBScanExperiment {

	public DBScanExperiment(int dataSize, ArrayList<Datum> data, String datasetName) {

		String filePathOut1 = "src/main/resources/DBS" + datasetName + ".txt";
		String filePathOut2 = "src/main/resources/DBS_cl_" + datasetName + ".txt";
		
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
		
		//best values as result of tuning experiments
		double epsilon = 0.28;
		int minNumPts = 4;
		
		for (int i = 0; i < 10; i++) {

			DBScan dbs = new DBScan(epsilon, minNumPts, dataSize);
			ClusterSet soln = new ClusterSet(dbs.run(data));
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
