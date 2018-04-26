//
//Group Members: 
//	* Bushra Baqui - bbaqui2
//	* Aditya Sinha - asinha25
//	* Vignan Thmmu - vthmmu2
//
//CS342 - Project #3 
//Sudoku Solver
//
//**SudokuSolver.java**
//	> Contains all the algorithms such as:	
//			> Single
//			> Hidden Single
//			> Locked Candidate
//			> Naked Pairs
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolver {
	// determine if the cell is changeable
	private final int xPos;
	private final int yPos;
	private final int SubGridHeight;
	private final int SubGridWidth;
	protected String [][] plainGrid;
	private final String [] acceptableValues;
	protected boolean [][] transform;
	ArrayList<ArrayList<String>> candidateList = new ArrayList<ArrayList<String>>(81);

	public SudokuSolver(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.xPos = rows;
		this.yPos = columns;
		this.SubGridHeight = boxHeight;
		this.SubGridWidth = boxWidth;
		this.plainGrid = new String[xPos][yPos];
		this.acceptableValues = validValues;
		this.transform = new boolean[xPos][yPos];
		initGrid();
		initCells();
	}
	
	public SudokuSolver(SudokuSolver puzzle) {
		this.xPos = puzzle.xPos;
		this.yPos = puzzle.yPos;
		this.SubGridHeight = puzzle.SubGridHeight;
		this.SubGridWidth = puzzle.SubGridWidth;
		this.plainGrid = new String[xPos][yPos];
		this.acceptableValues = puzzle.acceptableValues;
		
		for(int x = 0; x < xPos; x++) {
			for(int y = 0;y < yPos;y++) {
				plainGrid[x][y] = puzzle.plainGrid[x][y];
			}
		}
		
		this.transform = new boolean[xPos][yPos];
		
		for(int x = 0; x < xPos; x++) {
			for(int y = 0;y < yPos;y++) {
				this.transform[x][y] = puzzle.transform[x][y];
			}
		}
	}
	
	@Override
	public String toString() {
		String string = "Game Board:\n";
		for(int row = 0; row < this.xPos; row++) {
			for(int col = 0; col < this.yPos; col++) {
				string += this.plainGrid[row][col] + " ";
			}
			string += "\n";
		}
		return string + "\n";
	}
	
	// checks if the value already exists in the row...
	public boolean valueExistsRow(int row, String value) {
		if(row <= this.xPos) {
			for(int col = 0; col < this.yPos; col++) {
				if(this.plainGrid[row][col].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// checks if the value already exists in column...
	public boolean valueExistsColumn(int col,String value) {
		if(col <= this.yPos) {
			for(int row = 0; row < this.xPos; row++) {
				if(this.plainGrid[row][col].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String [][] getPlainGrid() {
		return this.plainGrid;
	}
	
	public boolean bound(int row,int col) {
		return row <= this.xPos && col <= this.yPos && row >= 0 && col >= 0;
	}
	
	public String getNum(int row, int col) {
		if(this.bound(row, col)) {
			return this.plainGrid[row][col];
		}
		return "";
	}
	
	public boolean isCellEmpty(int row, int col) {
		 return (this.bound(row,col) && this.plainGrid[row][col].equals("") && this.isCellChangable(row, col));
	}
	
	public boolean isCellChangable(int row, int col) {
		return this.transform[row][col];
	}
	
	public int getRows() {
		return this.xPos;
	}
	
	public int getColumns() {
		return this.yPos;
	}
	
	public int getSubgridHeight() {
		return this.SubGridHeight;
	}
	
	public int getSubgridWidth() {
		return this.SubGridWidth;
	}
	
	public String [] getValidValues() {
		return this.acceptableValues;
	}
	
	private void initCells() {
		for(int row = 0; row < this.xPos; row++) {
			for(int col = 0; col < this.yPos; col++) {
				this.transform[row][col] = true;
			}
		}
	}
	
	private void initGrid() {
		for(int row = 0; row < this.xPos; row++) {
			for(int col = 0; col < this.yPos; col++) {
				this.plainGrid[row][col] = "";
			}
		}
	}

	
	private boolean numAccepted(String value) {
		for(String string : this.acceptableValues) {
			if(string.equals(value)) return true;
		}
		return false;
	}
	
	public void moveNumber(int row,int col,String value,boolean isTransformed) {
		if(this.numAccepted(value) && this.moveAccepted(row,col,value) && this.isCellChangable(row, col)) {
			this.plainGrid[row][col] = value;
			this.transform[row][col] = isTransformed;
		}
	}
	
	public boolean moveAccepted(int row, int col, String value) {
		if(this.bound(row,col)) {
			if(!this.valueExistsColumn(col,value) && !this.valueExistsRow(row,value) && !this.valueExistsSubgrid(row,col,value)) {
				return true;
			}
		}
		return false;
	}
	
	public void placeValue(int row, int col, String s) {
		if(this.bound(row,col)) {
			this.plainGrid[row][col] = s ;
		}
	}
	
	public void emptyCell(int row,int col) {
		this.plainGrid[row][col] = "";
	}
	
	// checks if the value already exists in the subgrid...
	public boolean valueExistsSubgrid(int row,int col,String value) {
		if(this.bound(row, col)) {
			int subgridRow = row / this.SubGridHeight;
			int subgridColumn = col / this.SubGridWidth;
			int rowBegin = (subgridRow * this.SubGridHeight);
			int colBegin = (subgridColumn * this.SubGridWidth);
			// check the subgrid height and width...
			for(int x = rowBegin; x <= (rowBegin + this.SubGridHeight)-1; x++) {
				for(int y = colBegin; y <= (colBegin + this.SubGridWidth)-1; y++) {
					if(this.plainGrid[x][y].equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean gridFilled() {
		for(int x = 0; x < this.xPos; x++) {
			for(int y = 0; y < this.yPos; y++) {
				if(this.plainGrid[x][y].equals("")) return false;
			}
		}
		return true;
	}
	
	public int currentSubgrid(int row,int col,String value) {
		if(this.bound(row, col)) {
			int boxRow = row / this.SubGridHeight;
			int boxCol = col / this.SubGridWidth;

			int rowBegin = (boxRow*this.SubGridHeight);
			int colBegin = (boxCol*this.SubGridWidth);

			for(int x = rowBegin;x <= (rowBegin+this.SubGridHeight)-1;x++) {
				for(int y = colBegin;y <= (colBegin+this.SubGridWidth)-1;y++) {
					if(this.plainGrid[x][y].equals(value)) {
						return x+(y*9);
					}
				}
			}
		}
		return -1;
	}

	// gets the candidate list...
	public void candidateArrayList () {
		candidateList.clear();
		System.out.println("Performing Single Algo");
		SudokuSolver solver = this;
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (solver.plainGrid [row][col].isEmpty()) {
					String [] listSolver = {"1","2","3","4","5","6","7","8","9"};
					List<String> CanList = Arrays.<String>asList(listSolver);
					ArrayList<String> candidateArrayList = new ArrayList<String>(CanList);
					//System.out.println(candidateArrayList);
					
					for (int i = 1 ; i< 10 ; i++) {
						if(solver.valueExistsSubgrid(row, col, Integer.toString(i))) {
							candidateArrayList.remove(Integer.toString(i));
						}
						if(solver.valueExistsRow(row, Integer.toString(i))) {
							candidateArrayList.remove(Integer.toString(i));
						}
						if(solver.valueExistsColumn(col, Integer.toString(i))) {
							candidateArrayList.remove(Integer.toString(i));
						}
					}
					int index = (col * 9) + row;
					candidateList.add(index, candidateArrayList);
				}
			}
		}
	}

	// performs the hidden singles algorithm...
	public boolean HiddenSingles () {
		System.out.println("Performing HiddenSingle Algo");
		SudokuSolver s = this;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (s.plainGrid [row][col].isEmpty()) {
					String [] s1 = {"1","2","3","4","5","6","7","8","9"};
					List<String> CanList = Arrays.<String>asList(s1);
					ArrayList<String> CanAList = new ArrayList<String>(CanList);
					//System.out.println(CanAList);
					for (int i = 1 ; i < 10 ; i++){
						if(s.valueExistsSubgrid(row,col,Integer.toString(i))) {
							//System.out.println("box removing " + i);
							CanAList.remove(Integer.toString(i));

						}
						if(s.valueExistsRow(row, Integer.toString(i))) {
							//System.out.println("row removing " + i);
							CanAList.remove(Integer.toString(i));
						}
						if(s.valueExistsColumn(col, Integer.toString(i))) {
							//System.out.println(" col removing " + i);
							CanAList.remove(Integer.toString(i));
						}
						//System.out.println("row and col " + row + " " + col);
						//System.out.println(s.plainGrid[row][col]);
						//System.out.println(Integer.toString(i));
						//System.out.println(CanAList);
					}
					//System.out.println(CanAList.size());
					if(CanAList.size() == 1){
						s.moveNumber(row,col,CanAList.get(0),true);
						return true;
					}
				}
			}
		}
		return false;
	}

	// performs the singles algorithm...
	public boolean Singles(){
		//System.out.println("Performing Single Algo");
		SudokuSolver s = this;
		boolean found = false;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (s.plainGrid [row][col].isEmpty()) {
					String [] s1 = {"1","2","3","4","5","6","7","8","9"};
					List<String> CanList = Arrays.<String>asList(s1);
					ArrayList<String> CanAListRow = new ArrayList<String>(CanList);
					ArrayList<String> CanAListCol = new ArrayList<String>(CanList);
					ArrayList<String> CanAListBox = new ArrayList<String>(CanList);

					for (int i = 0 ; i < 10 ; i++){
						if(s.valueExistsRow(row, Integer.toString(i))) {
							//System.out.println("row removing " + i + " "+ row);
							CanAListRow.remove(Integer.toString(i));
						}
					}
					//System.out.println("Col A size" + CanAListRow.size());
					//System.out.println(CanAListRow);

					if(CanAListRow.size() == 1) {
						int j;
						for (j = 0; j < 10; j++) {
							found = s.isCellEmpty(row,j);
							if(found) break;
							System.out.println(found);
						}
						System.out.println(j);
						s.moveNumber(row,j,CanAListRow.get(0),true);
						found = false;
						return true;
					}

					for (int i = 0 ; i < 10 ; i++){
						if(s.valueExistsColumn(col, Integer.toString(i))) {
							System.out.println("Col removing "+ i + " "+ col);
							CanAListCol.remove(Integer.toString(i));
						}
					}
					//System.out.println("Col A size" + CanAListCol.size());
					//System.out.println(CanAListCol);

					if(CanAListCol.size()==1) {
						int j;
						for (j = 0; j < 10; j++) {
							found = s.isCellEmpty(j,col);
							if(found) break;
							System.out.println(found);
						}
						//System.out.println(j);
						s.moveNumber(j,col,CanAListCol.get(0),true);
						found = false;
						return true;
					}

					for (int i = 0 ; i < 10 ; i++){
						if(s.valueExistsSubgrid(row, col , Integer.toString(i))) {
							CanAListBox.remove(Integer.toString(i));
						}
					}

					int x = currentSubgrid(row, col, "");
					if(CanAListBox.size() == 1 && (s.isCellEmpty(x%9,x/9))) {
						s.moveNumber(x%9, x/9, CanAListBox.get(0), true);
						found = false;
						return true;
					}
				}
			}
		}
		return false;
	}

	// performs the locked candidate algorithm...
	public boolean LockCanidate() {
		System.out.println("Peforming Locked Candidate Algo");
		SudokuSolver s = this;
		boolean found = false;
		String candidateArray[][] = new String[9][9];

		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++) {
					candidateArray[row][col] = "123456789";
			}
		}

		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++) {
				if (!s.plainGrid[row][col].isEmpty()) {
					for(int i = 0; i < 9; i++)
					{
						if (candidateArray[row][i].length() > 1) {
							candidateArray[row][i] = candidateArray[row][i].replace(s.plainGrid[row][col], "");
						}
					}

					for(int i = 0; i < 9; i++)
					{
						if (candidateArray[i][col].length() > 1) {
							candidateArray[i][col] = candidateArray[i][col].replace(s.plainGrid[row][col], "");
						}
					}
					int subgridRow = row / this.SubGridHeight;
					int subgridColumn = col / this.SubGridWidth;

					int startingRow = (subgridRow*this.SubGridHeight);
					int startingCol = (subgridColumn*this.SubGridWidth);

					//System.out.println("ro" + row + "vol"+col);
					for(int r = startingRow;r <= (startingRow + this.SubGridHeight)-1; r++) {
						for(int c = startingCol;c <= (startingCol + this.SubGridWidth)-1; c++) {
							if (candidateArray[r][c].length() > 1) {
								candidateArray[r][c] = candidateArray[r][c].replace(s.plainGrid[row][col], "");
							}
						}
					}
					candidateArray[row][col] = s.plainGrid[row][col];
				}
			}
		}
		for(int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++){
				System.out.println("row : " + row + " " + "col : " + col + " " + candidateArray[row][col]);
			}
		}

		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9;col++) {
				if (candidateArray[row][col].length() > 1) {
					for(int i = 0; i < candidateArray[row][col].length(); i++) {
						char val = candidateArray[row][col].charAt(i);
						boolean existsRow = false;
						boolean existsColumn = false;
						int subgridRow = row / this.SubGridHeight;
						int subgridColumn = col / this.SubGridWidth;
						int startingRow = (subgridRow*this.SubGridHeight);
						int startingCol = (subgridColumn*this.SubGridWidth);
						int splitRow, splitColumn;

						if(startingRow == 0) {
							splitRow = 3;
						} 
						else {
							splitRow = 0;
						}

						if(startingCol == 0) {
							splitColumn = 3;
						} 
						else {
							splitColumn = 0;
						}

						for(int r = splitRow;r < splitRow + 3; r++) {
							int index = candidateArray[r][col].indexOf(val);
							if(index >= 0) {
								existsColumn = true;
							}
						}
						for(int c = splitColumn; c < splitColumn + 3; c++) {
							int index = candidateArray[row][c].indexOf(val);
							if(index >= 0) {
								existsRow = true;
							}
						}

						if(startingRow == 6) {
							splitRow = 3;
						} 
						else {
							splitRow = 6;
						}

						if(startingCol == 6) {
							splitColumn = 3;
						} 
						else {
							splitColumn = 6;
						}

						for(int r = splitRow; r < splitRow + 3; r++) {
							int index = candidateArray[r][col].indexOf(val);
							if(index >= 0) {
								existsColumn = true;
							}
						}
						for(int c = splitColumn; c < splitColumn + 3; c++) {
							int index = candidateArray[row][c].indexOf(val);
							if(index >= 0) {
								existsRow = true;
							}
						}

						// checks column...
						if(!existsColumn) {
							if(startingCol == col) {
								splitColumn = startingCol + 1;
							} 
							else {
								splitColumn = startingCol;
							}


							for(int r = startingRow; r < startingRow + 3; r++) {
								String s1 = "" + val;
								candidateArray[r][splitColumn] = candidateArray[r][splitColumn].replace(s1 , "");
							}

							if(startingCol + 2 == col) {
								splitColumn = startingCol + 1;
							} 
							else {
								splitColumn = startingCol + 2;
							}


							for(int r = startingRow; r < startingRow + 3; r++) {
								String s1 = "" + val;
								candidateArray[r][splitColumn] = candidateArray[r][splitColumn].replace(s1 , "");
							}
						}
						
						// checks row...
						if(!existsRow) {  
							if(startingRow == row) {
								splitRow = startingRow + 1;
							} 
							else {
								splitRow = startingRow;
							}

							for(int c = startingCol; c < startingCol + 3; c++){
								String s1 = "" + val;
								candidateArray[splitRow][c] = candidateArray[splitRow][c].replace(s1 , "");
							}

							if(startingRow + 2 == row) {
								splitRow = startingRow + 1;
							} 
							else {
								splitRow = startingRow + 2;
							}


							for(int c = startingCol; c < startingCol + 3; c++) {
								String s1 = "" + val;
								candidateArray[splitRow][c] = candidateArray[splitRow][c].replace(s1 , "");
							}
						}

						if(!existsColumn || !existsRow) {
							row = 0; col = 0;
						}
					}

					for(int r = 0; r < 9; r++){
						for(int  c = 0; c < 9; c++){
							//System.out.println(" r: " + r + " c: " + c + " candidate array : " + candidateArray[r][c]);
							if(candidateArray[r][c].length() < 2) {
								if ( s.plainGrid[r][c].isEmpty()) {
									s.moveNumber(r,c,candidateArray[r][c],true);
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	// performs the naked pairs algorithm...
	public boolean NakedPairs() { 
		//TODO:
		return false;
	}
	
}// end of SudokuSolver class
