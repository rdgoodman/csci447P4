package clustering;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Datum {
	
	
	private ArrayList<Double> inputs;
	private Cluster cluster;
	private int cIndex;
	
	/**
	 * Stores an input vector and the cluster it belongs to
	 */
	public Datum(ArrayList<Double> inputs){
		this.inputs = inputs;
	}
	
	/** 
	 * Calculates pairwise distance to another Datum
	 */
	public double calcDist(Datum o){
		double dist = 0;
		for (int i = 0; i < inputs.size(); i++){
			//System.out.println("Added (" + this.inputs.get(i) + " - " + o.getData().get(i) + ")^2");
			dist += Math.pow(this.inputs.get(i) - o.getData().get(i), 2);
		}
		return dist;
	}
	
	public ArrayList<Double> getData(){
		return inputs;
	}
	
	public void assignToCluster(Cluster c){
		this.cluster = c;
		this.cIndex = c.getIndex();
	}
	
	public int getClusterIndex(){
		return cIndex;
	}
	
	public Cluster getCluster(){
		return cluster;
	}
	
	public void print(){
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		System.out.println("Cluster: " + cIndex);
		System.out.print("< ");
		for (int i = 0; i < inputs.size(); i++){
			System.out.print(Double.valueOf(twoDForm.format(inputs.get(i))) + "  ");
		}
		System.out.println(">");
	}
}
