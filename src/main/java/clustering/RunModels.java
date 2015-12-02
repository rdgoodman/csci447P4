package clustering;
//test comment, to make sure I can push

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RunModels {

	public static void main(String[] args){
		File file = new File("src/main/resources/wineTest_first100.csv");
		ArrayList<Datum> data = new ArrayList<Datum>();
		
		try {
			Scanner s = new Scanner(file);
			int dataSize = 0;
			
			// create Datums
			while(s.hasNextLine()){
				String str = s.nextLine();
				String[] numStr = str.split(",");
				
				// TODO: -1 because we don't want to include index
				double[] nums = new double[numStr.length-1];
				
				for (int i = 0; i < nums.length; i++){
					nums[i] = Double.valueOf(numStr[i]);
				}
				
				nums = normalizeData(nums);
				dataSize = nums.length;
				ArrayList<Double> d = new ArrayList<Double>();
				for (int i = 0; i < nums.length; i++){
					d.add(nums[i]);
				}				
				data.add(new Datum(d));
				
			}
			System.out.println("Data: " + data.size());
			
			// try pso 			
			PSO pso = new PSO(0.2, 0.2, 0.2, 15, 5, dataSize, 0.5);
			ClusterSet soln = new ClusterSet(pso.run(data));
		
			//try DBScan
//			DBScan dbscan = new DBScan(.001, 6, dataSize);
//			ClusterSet soln = new ClusterSet(dbscan.run(data));
						
//			// try competitiveANN
//			ArrayList<Datum> train = new ArrayList<Datum>();
//			ArrayList<Datum> test = new ArrayList<Datum>();
//			for (Datum d : data){
//				double p = Math.random();
//				if (p < .33){
//					test.add(d);
//				} else {
//					train.add(d);
//				}
//			}
//			CompetitiveANN net = new CompetitiveANN(0.1, 10, 5);
//			ClusterSet soln = new ClusterSet(net.run(train, test));
					
			System.out.println();
			System.out.println();
			soln.print();
			System.out.println();
			soln.calcCohesion();
			soln.calcSeparation();
			
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private static double[] normalizeData(double[] nums){
		double max = Double.MIN_VALUE;
		for (int i = 0; i < nums.length; i++){
			if (nums[i] > max){
				max = nums[i];
			}
		}
		for (int i = 0; i < nums.length; i++){
			nums[i] = (nums[i]/max);
		}
		return nums;
	}
}
