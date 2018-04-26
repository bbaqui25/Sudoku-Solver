//
//Group Members: 
//	* Bushra Baqui - bbaqui2
//	* Aditya Sinha - asinha25
//	* Vignan Thmmu - vthmmu2
//
//CS342 - Project #3 
//Sudoku Solver
//
//**SudokuMenu.java**

/* SudokuMenu class creates the menu - File, Hints, and Help and the submenus
 * Contains the submenus event listener
 * sets up the dimension, color, font of window and button panel
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")

public class SudokuMenu  extends JFrame	{
	public JPanel selectButton;
	public SudokuGrid sudoGrid;

	// menu class..
	public SudokuMenu() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		// sets the dimension for the frame
		this.setMinimumSize(new Dimension(700,650));

		// adding a menu...
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		// adds the files menu and its corresponding items...
		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new newGame(SudokuSize.GRIDSIZE, 23));
		file.add(load);
		JMenuItem store = new JMenuItem("Store");
		file.add(store);
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);

		// adds the hints menu and its corresponding items...
		JMenu hints = new JMenu("Hints");
		menuBar.add(hints);
		JMenuItem single_algorithm = new JMenuItem("Single Algorithm");
		single_algorithm.addActionListener(new PerformSingle(SudokuSize.GRIDSIZE, 23));
		hints.add(single_algorithm);
		JMenuItem hidden_single = new JMenuItem("Hidden Single Algorithm");
		hidden_single.addActionListener(new PerformHiddenSingle(SudokuSize.GRIDSIZE, 23));
		hints.add(hidden_single);
		JMenuItem lock_candidate = new JMenuItem("Locked Candidate");
		lock_candidate.addActionListener(new PerformLockCanidate(SudokuSize.GRIDSIZE, 23));
		hints.add(lock_candidate);
		JMenuItem naked_pair = new JMenuItem("Naked Pair Algorithm");
		naked_pair.addActionListener(new PerformNakedPair(SudokuSize.GRIDSIZE, 23));
		hints.add(naked_pair);
		JMenuItem master_algorithm = new JMenuItem("Master Algorithm");
		hints.add(master_algorithm);

		// adds the help menu and its corresponding items...
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		JMenuItem howto_play = new JMenuItem("How to: Play Sudoku");
		help.add(howto_play);
		JMenuItem howto_interface = new JMenuItem("Using Interface");
		help.add(howto_interface);
		JMenuItem about = new JMenuItem("About");
		help.add(about);

		// adds an action listener for exit
		class exitAction implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		}
		exit.addActionListener(new exitAction());

		// implements the action listener for instructions on how to play sudoku...
		class howto_play implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				JFrame inst_frame = new JFrame("How to: Play Sudoku");
				inst_frame.setVisible(true);
				inst_frame.setSize(800, 400);
				JLabel label = new JLabel("<html>These are the instructions on how to play Sudoku:<br>" 				
					+ "The object of sudoku is to complete the puzzle so that the numbers 1 to 9 appear only once in each row, column and 3x3 box.<br>"
					+ "The same number must never appear twice in the same row, column or box.<br>" 
					+ "The numbers can appear in any order.<br>"
					+ "Don't worry about the diagonals as they are not used in sudoku.<br>" 
					+ "Each puzzle starts with some numbers already filled in to get you started.<br>" 
					+ "These numbers are called givens. You can't change the given numbers.<br>" 
					+ "Sudoku puzzles always have only one solution.<br></html>");				
				JPanel panel = new JPanel();
				inst_frame.add(panel);
				panel.add(label);
			}
		}
		howto_play.addActionListener(new howto_play() );

		// implements the action listener for instructions on how to use our sudoku interface...
		class howto_interface implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				JFrame inst_frame = new JFrame("Interface Help");
				inst_frame.setVisible(true);
				inst_frame.setSize(900, 400);
				JLabel label = new JLabel("<html>Sudoku Interface:<br>"
					+ "These are the instructions on how to use this interface:<br>" 
					+ "Our Sudoku program will display the 81 cells of the Sudoku grid with an additional 11 buttons to interact with the grid.<br>"
					+ "The 9 digit buttons allow the user to place digits into the cells.<br>"
					+ "This is done by first clicking on one of the digit buttons (which will activate that digit) and then clicking on one of the cells in the grid.<br>"
					+ "This places the active digit into cell clicked.<br>"
					+ "The user can place the digit into multiple cells without having to re-select the digit button every time.<br>"
					+ "The eraser button clears a cell in the grid.<br>"
					+ "The help button (a question mark) displays the candidate list for the particular cell.<br>"
					+ "* Menubar *<br>"
					+ "-> Under File Menu:<br>"
					+ "Load - searches the file system for a puzzle stored in a text file.<br>"
					+ "Store -  stores the current puzzle into a text file named by user.<br>"
					+ "Exit - quits the game <br>"
					+	"-> Hints menu contains 5 algorithms to assist in solving the puzzle<br>"
					+	"Single Algorithm - <br>"
					+	"Hidden Single Algorithm - <br>"
					+	"Locked Candidate Algorithm - <br>"
					+	"Naked Pair Algorithm - <br>"
					+	"Master Algorithm - <br></html>");											
				JPanel panel = new JPanel();
				inst_frame.add(panel);
				panel.add(label);
			}
		}
		howto_interface.addActionListener(new howto_interface() );

		// implements the action listener for "about" and lists the authors...
		class about implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				JFrame about_frame = new JFrame("About");
				about_frame.setVisible(true);
				about_frame.setSize(300, 300);
				JLabel label = new JLabel("<html>Sudoku Solver - made by:<br>"
						+ "Aditya Sinha<br>"
						+ "Bushra Baqui<br>"
						+ "Vignan Thmmu<br></html>");
				JPanel panel = new JPanel();
				about_frame.add(panel);
				panel.add(label);
			}
		}
		about.addActionListener(new about());
		
		// creating the sudoku panel...
		JPanel windowGrid = new JPanel();
		windowGrid.setLayout(new FlowLayout()); 	
		windowGrid.setPreferredSize(new Dimension(600, 650));
		// sets the background of the window to white
		windowGrid.setBackground(new Color(99,18,56));
		
		// creating the button panel...
		selectButton = new JPanel();
		selectButton.setPreferredSize(new Dimension(25, 500));
		selectButton.setBackground(new Color(99,18,56));
		
		// creating the actual sudoku grid
		sudoGrid = new SudokuGrid(); 
		// adds both the sudoku and the button grid to the window pane
		windowGrid.add(sudoGrid);
		windowGrid.add(selectButton);
		this.add(windowGrid);
		
		// sets font length to 26 and rebuilds the grid
		refreshGrid(SudokuSize.GRIDSIZE, 23, true); 
	}
	
	// sets up for a new game - grid size, layout...
	private class newGame implements ActionListener {
		private SudokuSize sudoSize;
		private int fontSize;
		
		public newGame(SudokuSize sudoSize,int fontSize) {
			this.sudoSize = sudoSize;
			this.fontSize = fontSize;
		}
		// refreshes for the new grid...
		@Override
		public void actionPerformed(ActionEvent e) {
			refreshGrid(sudoSize,fontSize, false);
		}
	}
	
	// action listener for single algorithm...
	private class PerformSingle implements ActionListener {
			private SudokuSize sudoSize;
			private int fontSize;

			public PerformSingle(SudokuSize sudoSize,int fontSize) {
				this.sudoSize = sudoSize;
				this.fontSize = fontSize;
			}
			// runs the single algorithm when user clicks on it...
			@Override
			public void actionPerformed(ActionEvent event) {
				sudoGrid.getGrid().game.Singles();
				sudoGrid.repaint();
			}
		}
	
	// action listener for hidden single algorithm...
	private class PerformHiddenSingle implements ActionListener {
		private SudokuSize sudoSize;
		private int fontSize;

		public PerformHiddenSingle(SudokuSize sudoSize,int fontSize) {
			this.sudoSize = sudoSize;
			this.fontSize = fontSize;
		}
		// runs the hidden singles algorithm when user clicks on it...
		@Override
		public void actionPerformed(ActionEvent event) {
			sudoGrid.getGrid().game.HiddenSingles();
			sudoGrid.repaint();
		}
	}

	// action listener for locked candidate algorithm...
	private class PerformLockCanidate implements ActionListener {
		private SudokuSize sudoSize;
		private int fontSize;

		public PerformLockCanidate (SudokuSize sudoSize,int fontSize) {
			this.sudoSize = sudoSize;
			this.fontSize = fontSize;
		}
		// runs the locked candidate algorithm when user clicks on it...
		@Override
		public void actionPerformed(ActionEvent event) {
			sudoGrid.getGrid().game.LockCanidate();
			sudoGrid.repaint();
		}
	}

	// action listener for naked pair algorithm...
	private class PerformNakedPair implements ActionListener {
		private SudokuSize sudoSize;
		private int fontSize;

		public PerformNakedPair(SudokuSize sudoSize,int fontSize) {
			this.sudoSize = sudoSize;
			this.fontSize = fontSize;
		}
		// runs the naked pair algorithm when user clicks on it...
		@Override
		public void actionPerformed(ActionEvent event) {
			sudoGrid.getGrid().game.NakedPairs();
			sudoGrid.repaint();
		}
	}

	// action listener for the master algorithm...
	private class PerformMaster implements ActionListener {
		//TODO: fix the master algorithm that checks through all the algorithms...
		private SudokuSize sudoSize;
		private int fontSize;

		public PerformMaster(SudokuSize sudoSize,int fontSize) {
			this.sudoSize = sudoSize;
			this.fontSize = fontSize;
		}
		// goes through all 4 algorithms...
		@Override
		public void actionPerformed(ActionEvent event) {
			sudoGrid.getGrid().game.Singles();
			sudoGrid.getGrid().game.LockCanidate();
			sudoGrid.getGrid().game.NakedPairs();
			sudoGrid.getGrid().game.HiddenSingles();
			sudoGrid.repaint();
		}
	}

	// creates a new grid...
	public void refreshGrid(SudokuSize sudoSize,int fontSize, boolean firstTime) {
		if(!firstTime) {
			SudokuSolver createSudoku = new CreateSudoku().createNewSudoku(sudoSize);
			sudoGrid.newSudoku(createSudoku);
		}
		sudoGrid.setFontSize(fontSize);
		selectButton.removeAll();
		int i = 1;
		// for all numbers less than 10 (1-9 buttons), click and place anywhere in grid...
		while (i < 10) {
			JToggleButton b = new JToggleButton(Integer.toString(i));
			b.setPreferredSize(new Dimension(80,40));
			b.addActionListener(sudoGrid.new NumberNotification());
			selectButton.add(b);
			i++;
		}

		// creates the eraser - button set to E
		JToggleButton eraser = new JToggleButton("e");
		try {
			Image eraserImage = ImageIO.read(getClass().getResource("resources/eraser.bmp"));
			eraser.setIcon(new ImageIcon(eraserImage));
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
		
		eraser.setPreferredSize(new Dimension(40,40));
		eraser.addActionListener(sudoGrid.new EraserActionListener());
		selectButton.add(eraser);

		JToggleButton quit = new JToggleButton("?");
		quit.setPreferredSize(new Dimension(40,40));
		quit.addActionListener(sudoGrid.new StatusActionListener());
		quit.setBorderPainted(true);

		selectButton.add(quit);
		sudoGrid.repaint();
		selectButton.revalidate();
		selectButton.repaint();
		if(sudoGrid.game.gridFilled()) {	
			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());
			frame.setSize(200, 200);
			
			// create the status bar panel and shove it down the bottom of the frame
			JPanel statusPanel = new JPanel();
			statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			frame.add(statusPanel, BorderLayout.CENTER);
			statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
			statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
			JLabel statusLabel = new JLabel("Congratulations");
			statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
			statusPanel.add(statusLabel);
			
			frame.setVisible(true);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SudokuMenu frame = new SudokuMenu();
				frame.setVisible(true);
			}
		}
		);
	}
	
}// end of SudokuMenu class
