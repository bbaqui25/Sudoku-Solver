//
//Group Members: 
//	* Bushra Baqui - bbaqui2
//	* Aditya Sinha - asinha25
//	* Vignan Thmmu - vthmmu2
//
//CS342 - Project #3 
//Sudoku Solver
//
//**CreateSudoku.java**

/* CreateSudoku class loads the file to start the game.
 * Also allows the user to select the files to play easy, medium, or hard level game
 */

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CreateSudoku extends JFrame {
	// creates a new sudoku puzzle...
	public SudokuSolver createNewSudoku(SudokuSize puzzleType) {
		// getting the rows, columns, height...
		SudokuSolver game = new SudokuSolver(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getSubgridWidth(), puzzleType.getSubgridHeight(), puzzleType.getIsValid());
		// lets the user choose input file...
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Load Puzzle File");
		int result = fileChooser.showDialog(this,"Load");
		
		if(result == fileChooser.APPROVE_OPTION) {
			// gets the selected file and inputs it into the grid...
			File file = fileChooser.getSelectedFile();
			Scanner input;
			// if file is valid, place values in cell...
			try {
				// all three parameters - row, col, val 
				input = new Scanner(file);
				int row = -1;
				int col = -1;
				int val = -1;
				
				// while the input is valid, moves through the grid and places the inputs in the appropriate cells...
				while (input.hasNextInt()) {
					row = input.nextInt();
					col = input.nextInt();
					val = input.nextInt();
					game.moveNumber(row-1, col-1, Integer.toString(val), false);
				}
			}
			catch (FileNotFoundException exception) {
				exception.printStackTrace();
			}
		}
		// else, file not valid and display error message...
		else{
			JOptionPane.showMessageDialog(null,"Error loading file");
		}
		return game;
	}
	
}// end of CreateSudoku class
