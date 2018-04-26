//
//Group Members: 
//	* Bushra Baqui - bbaqui2
//	* Aditya Sinha - asinha25
//	* Vignan Thmmu - vthmmu2
//
//CS342 - Project #3 
//Sudoku Solver
//
//**SudokuSize.java**

/* SudokuSize class declares the grid size 9*9 
 *  Contains getter functions
 */
 

public enum SudokuSize {

	GRIDSIZE(9,9,3,3,new String[] {"1","2","3","4","5","6","7","8","9"},"Sudoku");
	
	private final String text;
	private final String [] isValid;
	private final int subgridHeight;
	private final int subgridWidth;
	private final int rows;
	private final int columns;
	
	private SudokuSize(int rows,int columns,int subgridWidth,int subgridHeight,String [] isValid,String text) {
		this.text = text;
		this.isValid = isValid;
		this.subgridHeight = subgridHeight;
		this.subgridWidth = subgridWidth;
		this.rows = rows;
		this.columns = columns;
	}
	
	public String toString() {
		return text;
	}
	
	public String [] getIsValid() {
		return isValid;
	}
	
	public int getSubgridHeight() {
		return subgridHeight;
	}
	
	public int getSubgridWidth() {
		return subgridWidth;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
}// end of SudokuSize class
