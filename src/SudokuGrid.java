//
//Group Members: 
//	* Bushra Baqui - bbaqui2
//	* Aditya Sinha - asinha25
//	* Vignan Thmmu - vthmmu2
//
//CS342 - Project #3 
//Sudoku Solver
//
//**SudokuGrid.java**

/* SudokuGrid class sets up the grid size based on width and height.
 * Also contains the cell and buttons event listener
 */

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.font.FontRenderContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")

public class SudokuGrid extends JPanel {
	public SudokuSolver game;
	String currentValue;
	private int fontSize;
	private int currentRow;
	private int currentColumn;
	private int currentHeight;
	private int currentWidth;
	
	public SudokuGrid getGrid() {
		return this;
	}

	// an empty sudoku grid with no game loaded
	public SudokuGrid() {
		// dimensions of the grid panel...
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuGridMouseAdapter());
		// creates a new 9x9 grid...
		this.game = new CreateSudoku().createNewSudoku(SudokuSize.GRIDSIZE);
		// initialize rows and columns to -1 to start with the  (0,0) cell
		fontSize = 23;
		currentRow = -1;
		currentColumn = -1;
		currentHeight = 0;
		currentWidth = 0;
	}
	
	// sudoku grid with game loaded... 
	public SudokuGrid(SudokuSolver puzzle) {
		this.setPreferredSize(new Dimension(640,550));
		this.addMouseListener(new SudokuGridMouseAdapter());
		// this is the grid with the input placed in the grid...
		this.game = puzzle;
		// initialize rows and columns to -1 to start with the  (0,0) cell
		fontSize = 23;
		currentRow = -1;
		currentColumn = -1;
		currentHeight = 0;
		currentWidth = 0;
	}
	
	// sets the font size across the board...
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	// places a value into a cell...
	public void actionNotification(String buttonNumber) {
		if(currentColumn != -1 && currentRow != -1) {
			game.moveNumber(currentRow, currentColumn, buttonNumber, true);
			repaint();
		}
	}
	
	// using an action listener to ensure that button doesn't have to be reselected...
	public class NumberNotification implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JToggleButton mouseClick = (JToggleButton) event.getSource();
			currentValue = mouseClick.getText();
		}
	}
	
	// erases a cell that has a number...
	public void eraseFromActionListener() {
		if(currentColumn != -1 && currentRow != -1) {
			if(game.isCellChangable(currentRow, currentColumn)) {
				game.placeValue(currentRow, currentColumn,"");
			}
		}
	}

	// using an action listener for the eraser...
	public class EraserActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JToggleButton clicked = (JToggleButton) event.getSource();
			currentValue = clicked.getText();
		}
	}
	
	// mouse adapter for the grid, sets the slot height and width...
	private class SudokuGridMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			if(event.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = currentWidth / game.getColumns();
				int slotHeight = currentHeight / game.getRows();
				currentRow = event.getY() / slotHeight;
				currentColumn = event.getX() / slotWidth;
				event.getComponent().repaint();
				if(currentValue.equals("e")){
					eraseFromActionListener();
				}
				else {
					actionNotification(currentValue);
				}
			}
		}
	}
		
	@Override
	protected void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		Graphics2D gridGraphics = (Graphics2D) graphic;
		// sets the grid color...
		gridGraphics.setColor(new Color(239,232,206));
		
		// sets the cell width and height based on the height and width of the board - 81 even cells
		int cellWidth = this.getWidth() / game.getColumns();
		int cellHeight = this.getHeight() / game.getRows();
		
		currentWidth = (this.getWidth() / game.getColumns()) * game.getColumns();
		currentHeight = (this.getHeight() / game.getRows()) * game.getRows();
		
		// fills the individual cell with a color to mark that it is selected...
		gridGraphics.fillRect(0, 0, currentWidth ,currentHeight);
		
		// set the line color and draws the lines for the grid and the subgrid using the height...
		gridGraphics.setColor(new Color(79,56,41));
		for(int x = 0; x <= currentWidth; x += cellWidth) {			
			if((x/cellWidth) % game.getSubgridWidth() == 0) {
				gridGraphics.setStroke(new BasicStroke(2));
				gridGraphics.drawLine(x, 0, x, currentHeight);
			}
			else {
				gridGraphics.setStroke(new BasicStroke(1));
				gridGraphics.drawLine(x, 0, x, currentHeight);
			}
		}
		
		// set the line color and draws the lines for the grid and the subgrid using the width...
		for(int y = 0; y <= currentHeight;y += cellHeight) {
			if((y / cellHeight) % game.getSubgridHeight() == 0) {
				gridGraphics.setStroke(new BasicStroke(2));
				gridGraphics.drawLine(0, y, currentWidth, y);
			}
			else {
				gridGraphics.setStroke(new BasicStroke(1));
				gridGraphics.drawLine(0, y, currentWidth, y);
			}
		}
		
		// sets the font size and the font type
		Font fixedFont = new Font("Arial", Font.PLAIN, fontSize);
		gridGraphics.setFont(fixedFont);
		FontRenderContext fontContext = gridGraphics.getFontRenderContext();
		for(int row=0; row < game.getRows(); row++) {
			for(int col = 0; col < game.getColumns(); col++) {
				if(!game.isCellEmpty(row, col)) {
					// gets the position to set boundaries...
					int numWidth = (int) fixedFont.getStringBounds(game.getNum(row, col), fontContext).getWidth();
					int numHeight = (int) fixedFont.getStringBounds(game.getNum(row, col), fontContext).getHeight();
					gridGraphics.drawString(game.getNum(row, col), (col*cellWidth) + ((cellWidth/2) - (numWidth/2)), (row*cellHeight) + ((cellHeight/2) + (numHeight/2)));
				}
			}
		}
		// if there's a valid cell...
		if(currentColumn != -1 && currentRow != -1) {
			// sets the cell color 
			gridGraphics.setColor(new Color(0.51f,0f,0.076f,0.173f));
			gridGraphics.fillRect(currentColumn * cellWidth,currentRow * cellHeight,cellWidth,cellHeight);
		}
	}
	
	// new game
	public void newSudoku(SudokuSolver puzzle) {
			this.game = puzzle;
	}
	
	public class StatusActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JToggleButton clicked = (JToggleButton) e.getSource();
			
			if (clicked.isSelected()) {
				JFrame frame = new JFrame();
				frame.setLayout(new BorderLayout());
				frame.setSize(300, 300);
				
				// create the status bar panel and shove it down the bottom of the frame
				JPanel statusPanel = new JPanel();
				statusPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
				frame.add(statusPanel, BorderLayout.SOUTH);
				statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
				statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
				
				JLabel statusLabel = new JLabel("Candidate List:");
				statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
				statusPanel.add(statusLabel);
				
				frame.setVisible(true);
			}
		}
	}
	
}// end of SudokuGrid class
