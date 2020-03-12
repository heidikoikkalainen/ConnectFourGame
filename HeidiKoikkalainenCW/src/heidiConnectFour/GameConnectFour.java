package heidiConnectFour;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class GameConnectFour {

	public static void main(String[] args) {

		final int ROWS = 6;
		final int COLUMNS = 7;

		Grid connectFourGrid = new Grid(ROWS, COLUMNS);
		printGrid(connectFourGrid,ROWS,COLUMNS);

		playConnectFour(connectFourGrid, ROWS, COLUMNS);	

	}//end main

	public static void playConnectFour(Grid connectFourGrid, int ROWS, int COLUMNS) {

		Scanner input = new Scanner(System.in);
		char player1Colour;
		char player2Colour;
		char red = 'R';
		char yellow = 'Y';
		Player nextPlayer;
		boolean gameOver = false;
		char pieceColour;
		int columnPosition = 0;
		int minColumn = 0;
		int maxColumn = 6;
		boolean columnState;

		//create player 1
		player1Colour = chooseColour();
		Player player1 = new Player(player1Colour);

		//create player2
		if(player1Colour == red) {
			player2Colour = yellow;
		} else {
			player2Colour = red;
		}
		Player player2 = new Player(player2Colour);

		//player1 starts the game
		nextPlayer = player1;

		while(!gameOver) {
			//choose a column
			if(nextPlayer == player1) {
				System.out.print("Player 1 - drop a piece in one of the columns (0-6): ");
				pieceColour = player1Colour;
			} else {
				System.out.print("Player 2 - drop a piece in one of the columns (0-6): ");
				pieceColour = player2Colour;
			}
			columnPosition = input.nextInt();

			//validate input
			while(columnPosition < minColumn || columnPosition > maxColumn) {
				System.out.println("Column position must be between 0 to 6: ");
				columnPosition = input.nextInt();
			}

			//check if the column is full
			columnState = isColumnFull(connectFourGrid, ROWS, columnPosition);
			if(columnState == true) {
				System.out.println("Column is full, try again!");
			} else {
				//if column not full, drop piece and reprint grid
				dropPiece(connectFourGrid, ROWS, COLUMNS, columnPosition, pieceColour);
				printGrid(connectFourGrid,ROWS,COLUMNS);

				//check for a win
				if(gameStatus(connectFourGrid, columnPosition, pieceColour, ROWS, COLUMNS) == true) {
					//if a win found, game over
					gameOver = true;
					if(pieceColour == player1Colour) {
						System.out.print("Player 1 won! Game Over!");
					} else {
						System.out.print("Player 2 won! Game Over!");
					}	
				} else {
					if(checkTie(connectFourGrid, ROWS, COLUMNS) == true) {
						//if a tie, game over
						gameOver = true;
						System.out.print("It's a tie! Game Over!");
					}
				}
				//if no win or tie, change player and repeat	
				if(nextPlayer == player1) {
					nextPlayer = player2;
				}else {
					nextPlayer = player1;
				}
			}
		}//end main while loop

		input.close();

	}//end playConnectFour


	public static char chooseColour() {
		int playerColour;

		Object[] options = {"RED", "YELLOW"};
		playerColour = JOptionPane.showOptionDialog(null, "Player 1 - please choose your colour", "Player Colour", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if(playerColour == 0) {
			return 'R';
		}else {
			return 'Y';
		}
	}

	public static boolean isColumnFull(Grid connectFourGrid, int ROWS, int columnPosition) {

		//check if the square in the top row and selected column is empty
		if(connectFourGrid.grid[ROWS-1][columnPosition].isEmpty() == true) {
			return false;
		}else {
			return true;	
		}
	}

	public static void dropPiece(Grid connectFourGrid, int ROWS, int COLUMNS, int columnPosition, char pieceColour) {

		//starting from the bottom row, find the first empty square in the selected column and create a new piece in the square
		for(int loop = 0; loop < ROWS; loop++) {
			if(connectFourGrid.grid[loop][columnPosition].isEmpty()) {
				Piece droppedPiece = new Piece(pieceColour);
				connectFourGrid.grid[loop][columnPosition].setPiece(droppedPiece);	
				break;
			}
		}
	}

	public static void printGrid(Grid connectFourGrid, int ROWS, int COLUMNS) {

		//loop through the grid, starting at the top row (6)
		for(int loop = ROWS-1; loop >= 0; loop--) {
			for(int loop2 = 0; loop2 < COLUMNS; loop2++) {
				System.out.print("|" + connectFourGrid.grid[loop][loop2]);
			}
			System.out.println("|");
		}

		for(int loop = 0; loop < COLUMNS - 2; loop++) {
			System.out.print("---");
		}
		System.out.println();
	}

	public static boolean gameStatus(Grid connectFourGrid, int columnPosition, char pieceColour, int ROWS, int COLUMNS) {

		int rowPosition = 0;

		//get the row position
		for(int loop = ROWS-1; loop >= 0; loop--) {
			if(connectFourGrid.grid[loop][columnPosition].isEmpty() == false) {
				rowPosition = loop;
				break;
			}
		}

		if(checkColumn(connectFourGrid, rowPosition, columnPosition, pieceColour, ROWS)) {
			return true;
		}
		if(checkRow(connectFourGrid, rowPosition, columnPosition, pieceColour, COLUMNS)) {
			return true;
		}
		if(checkMainDiagonal(connectFourGrid, rowPosition, columnPosition, pieceColour, ROWS, COLUMNS)) {
			return true;
		}
		if(checkAntidiagonal(connectFourGrid, rowPosition, columnPosition, pieceColour, ROWS, COLUMNS)) {
			return true;
		}
		return false;
	}

	//check if four matching pieces connect vertically
	public static boolean checkColumn(Grid connectFourGrid, int rowPosition, int columnPosition, char pieceColour, int ROWS) {

		int pieceCounter = 0;
		int similarCount = 4;

		for(int loop = 0; loop < ROWS; loop++) {
			Piece piece = connectFourGrid.grid[loop][columnPosition].getPiece();
			if (piece != null) {
				if(piece.getColour() == pieceColour) {
					pieceCounter++;
					if(pieceCounter == similarCount) {
						return true;
					}
				} else {
					pieceCounter = 0;
				}
			} else {
				break;
			}
		}
		return false;

	}

	//check if four matching pieces connect horizontally
	public static boolean checkRow(Grid connectFourGrid, int rowPosition, int columnPosition, char pieceColour, int COLUMNS) {

		int pieceCounter = 0;
		int similarCount = 4;

		for(int loop = 0; loop < COLUMNS; loop++) {
			Piece piece = connectFourGrid.grid[rowPosition][loop].getPiece();
			if(piece != null) {
				if(piece.getColour() == pieceColour) {
					pieceCounter++;
					if(pieceCounter >= similarCount) {
						return true;
					} 
				} else {
					pieceCounter = 0;
				}
			} else {
				pieceCounter = 0;
			}
		}		
		return false;
	}

	//check if four matching pieces connect in a main diagonal (from top left to bottom right)
	public static boolean checkMainDiagonal(Grid connectFourGrid, int rowPosition, int columnPosition, char pieceColour, int ROWS, int COLUMNS) {

		int pieceCounter = 1;
		int similarCount = 4;

		//check squares starting from the selected square towards bottom right 
		for(int loop = rowPosition - 1, loop2 = columnPosition + 1; loop >= 0 && loop2 < COLUMNS; loop--, loop2++) {
			Piece piece = connectFourGrid.grid[loop][loop2].getPiece();
			if(piece != null) {
				if(piece.getColour() == pieceColour) {
					pieceCounter++;
					if(pieceCounter >= similarCount) {
						return true;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}

		//check squares starting from the selected square towards top left
		for(int loop = rowPosition + 1, loop2 = columnPosition - 1; loop < ROWS && loop2 >= 0; loop++, loop2--) {
			Piece piece = connectFourGrid.grid[loop][loop2].getPiece();
			if(piece != null) {
				if(piece.getColour() == pieceColour) {
					pieceCounter++;
					if(pieceCounter >= similarCount) {
						return true;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}
		return false;

	}

	//check if four matching pieces connect in an anti-diagonal (bottom left to top right)
	public static boolean checkAntidiagonal(Grid connectFourGrid, int rowPosition, int columnPosition, int pieceColour, int ROWS, int COLUMNS) {

		int pieceCounter = 1;
		int similarCount = 4;

		//check squares starting from the selected square towards top right
		for(int loop = rowPosition + 1, loop2 = columnPosition + 1; loop < ROWS && loop2 < COLUMNS; loop++, loop2++) {
			Piece piece = connectFourGrid.grid[loop][loop2].getPiece();
			if(piece != null) {
				if(piece.getColour() == pieceColour) {
					pieceCounter++;
					if(pieceCounter >= similarCount) {
						return true;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}

		//check squares starting from the selected square towards bottom left
		for(int loop = rowPosition - 1, loop2 = columnPosition - 1; loop >= 0 && loop2 >= 0; loop--, loop2--) {
			Piece piece = connectFourGrid.grid[loop][loop2].getPiece();
			if(piece != null) {
				if(piece.getColour() == pieceColour) {
					pieceCounter++;
					if(pieceCounter >= similarCount) {
						return true;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}
		return false;

	}

	//check if the grid is full
	public static boolean checkTie(Grid columnFourGrid, int ROWS, int COLUMNS) {
		for (int loop = 0; loop < COLUMNS; loop++) {
			if (isColumnFull(columnFourGrid, ROWS, loop) == false) {
				return false;
			}
		}
		return true;
	}


}//end class
