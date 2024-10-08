package suduko_solver;

public class SudokuSolver {

   public static final int GRID_SIZE = 9; // the standard sudoku row and col size

	public static boolean solveSudoku(int[][] board) 
	{
		for (int row = 0; row < GRID_SIZE; row++) 
		{
			for (int col = 0; col < GRID_SIZE; col++) 
			{
				if (board[row][col] == 0) 
				{
					for (int num = 1; num <= GRID_SIZE; num++) //Testing what possibilities (1-9) lead to a valid board
					{
						if (isValidPlacement(board, num, row, col)) 
						{
							board[row][col] = num;

							if (solveSudoku(board)) //Recursion
							{
								return true;
							} else {
								board[row][col] = 0; // Backtrack
							}
						}
					}
					return false; // No valid number was found. Therefore, it triggers backtracking
				}
			}
		}
		return true; // Puzzle solved
	}

	private static boolean isValidPlacement(int[][] board, int num, int row, int col) 
	{
		//There are 3 reasons why a number wouldn't be valid:
		//1. the number is already in the row
		//2. the number is already in the column
		//3. the number is already in the 3x3 square
		
		for (int i = 0; i < GRID_SIZE; i++) 
		{
			if (board[row][i] == num) 
			{
				return false; //The number is already in the row
			}
		}

		for (int i = 0; i < GRID_SIZE; i++) 
		{
			if (board[i][col] == num) //The number is already in the col
			{
				return false;
			}
		}

		int boxRowStart = row - row % 3;
		int boxColStart = col - col % 3;

		for (int i = boxRowStart; i < boxRowStart + 3; i++) 
		{
			for (int j = boxColStart; j < boxColStart + 3; j++) 
			{
				if (board[i][j] == num) 
				{
					return false; //The number is already in the 3x3 square matrix
				}
			}
		}

		return true; // Otherwise, the number is valid there
	}
}
