// a coordinate for the (one) grid
public class Coordinate {
	
	int row;
	int col;
	
	public Coordinate(int row, int col) throws Exception {
		
		if (row < 0 || row >= Grid.limitRow()) {
			throw new IndexOutOfBoundsException();
		}
		if (col < 0 || col >= Grid.limitCol()) {
			throw new IndexOutOfBoundsException();
		}
		
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public boolean equals(Object obj) {
		
		if (this==obj)
			return true;
		
		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		Coordinate coordinate = (Coordinate)obj;
		
		return this.row == coordinate.row && this.col == coordinate.col;
	}
	
	public int hashCode() {
		return row;
	}
	
	public String toString() {
		return "row:" + row + " col:" + col;
	}
	
	public String showOnGrid() throws Exception {
		String out = "";
		for (int i = 0; i < Grid.limitRow(); i ++) {
			for (int j = 0; j < Grid.limitCol(); j++) { 
				if (this.getRow() == i && this.getCol() == j) {
					out += "X";
				} else {
					out += "O";
				}
				out += " ";
			}
			out += "\n";
		}
		return out;
	}
}
