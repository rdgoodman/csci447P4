package clustering;

public class ACOAgent {
	int row;
	int column;
	Datum carried;
	int dataIndex;
	
	int gridColSize;
	int gridRowSize;
	
	public ACOAgent(int row, int column, int gridColSize, int gridRowSize){
		this.row = row;
		this.column = column;
		carried = null;
		this.gridColSize = gridColSize;
		this.gridRowSize = gridRowSize;
	}
	
	public void moveAgent(){
		
	}
	
	public void pickUp(){
		
	}
	
	public void drop(){
		
	}
}
