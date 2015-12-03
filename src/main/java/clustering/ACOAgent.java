package clustering;

public class ACOAgent {
	int row;
	int column;
	Datum carried;
	int dataIndex;

	int gridColSize;
	int gridRowSize;

	public ACOAgent(int row, int column, int gridColSize, int gridRowSize) {
		this.row = row;
		this.column = column;
		carried = null;
		this.gridColSize = gridColSize;
		this.gridRowSize = gridRowSize;
	}

	public void pickUp(int index, Datum tobeCarried) {
		carried = tobeCarried;
		dataIndex = index;
	}

	public void drop() {
		dataIndex = -1;
		carried = null;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Datum getCarried() {
		return carried;
	}

	public void setCarried(Datum carried) {
		this.carried = carried;
	}

	public int getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(int dataIndex) {
		this.dataIndex = dataIndex;
	}

}
