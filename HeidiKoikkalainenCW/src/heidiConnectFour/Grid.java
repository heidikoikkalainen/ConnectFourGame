package heidiConnectFour;

public class Grid {

	public Square[][] grid;

	//initialises an empty grid
	public Grid(int ROWS, int COLUMNS) {
		grid = new Square[ROWS][COLUMNS];
		for (int loop = 0; loop < ROWS; loop++) {
			for (int loop2 = 0; loop2 < COLUMNS; loop2++) {
				grid[loop][loop2] = new Square();
			}
		} 
	}

}//end class
