package suduko_solver;

import javax.swing.*;
import java.awt.*;

public class SudokuSolverGUI extends JFrame 
{
    private JTextField[][] fields = new JTextField[SudokuSolver.GRID_SIZE][SudokuSolver.GRID_SIZE];
    private JTextArea instructionsArea = new JTextArea(3, 20);
    private JButton solveButton = new JButton("Solve");

    public SudokuSolverGUI() // Setting up the GUI
    { 
        setTitle("Sudoku Solver");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        instructionsArea.setEditable(false);
        instructionsArea.setText("Enter numbers (1-9) in the grid where known, leave empty for unknown.\n" + 
                                  "Press 'Solve' to find the solution.");
        add(new JScrollPane(instructionsArea), BorderLayout.NORTH);

        CustomGridPanel gridPanel = new CustomGridPanel(fields);
        add(gridPanel, BorderLayout.CENTER);

        solveButton.addActionListener(e -> solveSudoku()); // Action listener for solving by calling solveSudoku()
        add(solveButton, BorderLayout.SOUTH);
    }

    private void solveSudoku() 
    {
        int[][] input = getInputs(); // Get inputs as a 2D integer array
        boolean solved = SudokuSolver.solveSudoku(input); // Calls SudokuSolver for its solve logic

        if (solved)
        {
            updateGrid(input); // Update the GUI grid with the solved values
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "No solution exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int[][] getInputs() 
    {
        int[][] inputs = new int[SudokuSolver.GRID_SIZE][SudokuSolver.GRID_SIZE]; //To get a 2D arr of ints from the text fields
        for (int row = 0; row < SudokuSolver.GRID_SIZE; row++) 
        {
            for (int col = 0; col < SudokuSolver.GRID_SIZE; col++) 
            {
                String text = fields[row][col].getText();
                
                if (text.isEmpty()) 
                {
                    inputs[row][col] = 0; // If the text field is empty, set the input to 0
                } 
                else 
                {
                    inputs[row][col] = Integer.parseInt(text); // Otherwise, parse the integer value
                }
            }
        }
        return inputs;
    }

    private void updateGrid(int[][] solved)
    {
        for (int row = 0; row < SudokuSolver.GRID_SIZE; row++) 
        {
            for (int col = 0; col < SudokuSolver.GRID_SIZE; col++) 
            {
            	if (solved[row][col] == 0) 
            	{
            	    fields[row][col].setText(""); // Set text to empty if the value is 0
            	} 
            	else
            	{
            	    fields[row][col].setText(String.valueOf(solved[row][col]));
            	}
            }
        }
    }

    class CustomGridPanel extends JPanel 
    {
        public CustomGridPanel(JTextField[][] fields) 
        {
            setLayout(new GridLayout(9, 9)); // Create a 9x9 grid layout
            for (int row = 0; row < 9; row++) 
            {
                for (int col = 0; col < 9; col++) 
                {
                    fields[row][col] = new JTextField(2);
                    fields[row][col].setHorizontalAlignment(JTextField.CENTER);
                    add(fields[row][col]);
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            drawGrid((Graphics2D) g); // Cast to Graphics2D
        }

        private void drawGrid(Graphics2D g) 
        {
            int cellSize = getWidth() / 9;

            for (int i = 0; i <= 9; i++) 
            {
                g.drawLine(i * cellSize, 0, i * cellSize, getHeight()); // Vertical lines
                g.drawLine(0, i * cellSize, getWidth(), i * cellSize); // Horizontal lines

                if (i % 3 == 0) // Thicker lines for each 3x3 matrix
                { 
                    g.setStroke(new BasicStroke(3)); 
                    g.drawLine(i * cellSize, 0, i * cellSize, getHeight());
                    g.drawLine(0, i * cellSize, getWidth(), i * cellSize);
                    g.setStroke(new BasicStroke(1)); // Reset back to normal stroke
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuSolverGUI gui = new SudokuSolverGUI();
            gui.setVisible(true);
        });
    }
}
