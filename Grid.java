import java.util.ArrayList;

// a square grid of double values
// to be used to store state values
public class Grid {
	
	// these two should really be uninitialized
	private static int gridDimension = 5;
	private static boolean gridDimensionInitialized = true;
	
	// row < limitRow
	public static int limitRow() throws Exception {
		if (!gridDimensionInitialized) {
			throw new Exception("grid size not set yet");
		}
		return gridDimension;
	}
	public static int limitCol() throws Exception {
		if (!gridDimensionInitialized) {
			throw new Exception("grid size not set yet");
		}
		return gridDimension;
	}
	
	int dimension;
	double [][] array;
	
	public Grid(int dimension) {
		gridDimension = dimension;
		gridDimensionInitialized = true;
		this.dimension = dimension;
		this.array = new double[dimension][dimension];
	}
	
	public Coordinate[] getAllCoordinates() throws Exception {
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				list.add(new Coordinate(i, j));
			}
		}
		return list.toArray(new Coordinate[0]);
	}
	
	public void setValue(Coordinate coord, double value) {
		array[coord.getRow()][coord.getCol()] = value;
	}
	
	public double getValue(Coordinate coord) {
		return array[coord.getRow()][coord.getCol()];
	}
	
	public int getDimension() {
		return this.dimension;
	}
}
