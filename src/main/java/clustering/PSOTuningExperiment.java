package clustering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PSOTuningExperiment {

	public PSOTuningExperiment(int dataSize, ArrayList<Datum> data) {

		String filePathOut1 = "";
		String filePathOut2 = "";
		String filePathOut3 = "";

		FileWriter writer1 = null;
		FileWriter writer2 = null;
		FileWriter writer3 = null;

		for (double omega = 0.2; omega <= 0.8; omega += 0.3) {
			for (double phi1 = 0.2; phi1 <= 0.8; phi1 += 0.3) {
				for (double phi2 = 0.2; phi2 <= 0.8; phi2 += 0.3) {
					// PSO pso = new PSO(0.2, 0.2, 0.2, 10, 5, dataSize,
					// 0.5, .15);
					System.out.println(omega+phi1+phi2);
					PSO pso = new PSO(omega, phi1, phi2, 10, 5, dataSize, .5, .1);
					ClusterSet soln = new ClusterSet(pso.run(data));
					double ratio = soln.calcSeparation() / soln.calcCohesion();

					if (omega == 0.2) {
						filePathOut1 = "src/main/resources/omega_3.txt";
					} else if (omega == 0.5) {
						filePathOut1 = "src/main/resources/omega_5.txt";
					} else {
						filePathOut1 = "src/main/resources/omega_7.txt";
					}

					if (phi1 == 0.2) {
						filePathOut2 = "src/main/resources/phi1_3.txt";
					} else if (phi1 == 0.5) {
						filePathOut2 = "src/main/resources/phi1_5.txt";
					} else {
						filePathOut2 = "src/main/resources/phi1_7.txt";
					}

					if (phi2 == 0.2) {
						filePathOut3 = "src/main/resources/phi2_3.txt";
					} else if (phi2 == 0.5) {
						filePathOut3 = "src/main/resources/phi2_5.txt";
					} else {
						filePathOut3 = "src/main/resources/phi2_7.txt";
					}

					try {
						writer1 = new FileWriter(filePathOut1, true);
						writer1.write(String.valueOf(ratio) + "\n");
						writer1.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						writer2 = new FileWriter(filePathOut2, true);
						writer2.write(String.valueOf(ratio) + "\n");
						writer2.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						writer3 = new FileWriter(filePathOut3, true);
						writer3.write(String.valueOf(ratio) + "\n");
						writer3.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
	}

}
