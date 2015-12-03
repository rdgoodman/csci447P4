package clustering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DBScanTuningExperiment {

	public DBScanTuningExperiment(int dataSize, ArrayList<Datum> data) {

		String filePathOut1 = "";

		FileWriter writer1 = null;

		double epsilon = 0.0001;
		int minNumPts = 2;

		for (int i = 0; i < 5; i++) {
			// minNumPts starts at 2
			DBScan dbs = new DBScan(epsilon, minNumPts, dataSize);
			ClusterSet soln = new ClusterSet(dbs.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/epsilon_0001.txt";

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write("minNumPts " + minNumPts);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (minNumPts == 2) {
				minNumPts = 3;
			} else if (minNumPts == 3) {
				minNumPts = 4;
			} else if (minNumPts == 4) {
				minNumPts = 6;
			} else if (minNumPts == 6) {
				minNumPts = 8;
			}
		} // end for

		epsilon = 0.001;
		minNumPts = 2;

		for (int i = 0; i < 5; i++) {
			// minNumPts starts at 2
			DBScan dbs = new DBScan(epsilon, minNumPts, dataSize);
			ClusterSet soln = new ClusterSet(dbs.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/epsilon_001.txt";;

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write("minNumPts " + minNumPts);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (minNumPts == 2) {
				minNumPts = 3;
			} else if (minNumPts == 3) {
				minNumPts = 4;
			} else if (minNumPts == 4) {
				minNumPts = 6;
			} else if (minNumPts == 6) {
				minNumPts = 8;
			}
		} // end for

		epsilon = 0.01;
		minNumPts = 2;
		
		for (int i = 0; i < 5; i++) {
			// minNumPts starts at 2
			DBScan dbs = new DBScan(epsilon, minNumPts, dataSize);
			ClusterSet soln = new ClusterSet(dbs.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/epsilon_01.txt";;

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write("minNumPts " + minNumPts);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (minNumPts == 2) {
				minNumPts = 3;
			} else if (minNumPts == 3) {
				minNumPts = 4;
			} else if (minNumPts == 4) {
				minNumPts = 6;
			} else if (minNumPts == 6) {
				minNumPts = 8;
			}
		} // end for
		
		epsilon = 0.1;
		minNumPts = 2;
		
		for (int i = 0; i < 5; i++) {
			// minNumPts starts at 2
			DBScan dbs = new DBScan(epsilon, minNumPts, dataSize);
			ClusterSet soln = new ClusterSet(dbs.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/epsilon_1.txt";;

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write("minNumPts " + minNumPts);
				writer1.write(printRatio + '\n');
				writer1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (minNumPts == 2) {
				minNumPts = 3;
			} else if (minNumPts == 3) {
				minNumPts = 4;
			} else if (minNumPts == 4) {
				minNumPts = 6;
			} else if (minNumPts == 6) {
				minNumPts = 8;
			}
		} // end for
		
		
		epsilon = 0.5;
		minNumPts = 2;
		
		for (int i = 0; i < 5; i++) {
			// minNumPts starts at 2
			DBScan dbs = new DBScan(epsilon, minNumPts, dataSize);
			ClusterSet soln = new ClusterSet(dbs.run(data));
			double ratio = soln.calcSeparation() / soln.calcCohesion();
			String printRatio = String.valueOf(ratio);
			
			filePathOut1 = "src/main/resources/epsilon_5.txt";;

			try {
				writer1 = new FileWriter(filePathOut1, true);
				writer1.write("minNumPts " + minNumPts);
				writer1.write(printRatio + '\n');
				writer1.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (minNumPts == 2) {
				minNumPts = 3;
			} else if (minNumPts == 3) {
				minNumPts = 4;
			} else if (minNumPts == 4) {
				minNumPts = 6;
			} else if (minNumPts == 6) {
				minNumPts = 8;
			}
		} // end for
	}
}