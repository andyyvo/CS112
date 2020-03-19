/**
 * Sudoku.java
 * 
 * Implementation of a class that represents a Sudoku puzzle and solves
 * it using recursive backtracking.
 *
 * Computer Science 112, Boston University
 *
 * your name: Andy Vo
 */

import java.io.*;   // allows us to read from a file
import java.util.*;

public class Sudoku {    
    // The current contents of the cells of the puzzle. 
    private int[][] grid;
    
    /*
     * Indicates whether the value in a given cell is fixed 
     * (i.e., part of the initial configuration).
     * valIsFixed[r][c] is true if the value in the cell 
     * at row r, column c is fixed, and false otherwise.
     */
    private boolean[][] valIsFixed;
    
    /*
     * This 3-D array allows us to determine if a given subgrid (i.e.,
     * a given 3x3 region of the puzzle) already contains a given
     * value.  We use 2 indices to identify a given subgrid:
     *
     *    (0,0)   (0,1)   (0,2)
     *
     *    (1,0)   (1,1)   (1,2)
     * 
     *    (2,0)   (2,1)   (2,2)
     * 
     * For example, subgridHasVal[0][2][5] will be true if the subgrid
     * in the upper right-hand corner already has a 5 in it, and false
     * otherwise.
     */
    private boolean[][][] subgridHasVal;

    /*
     * 2-D array that allows us to determine if a given column
     * already contains a given value. True if contains; false otherwise.
     * Ex: colHasVal[0][5] will be true if the first column has 5 in it.
     */
    private boolean[][] colHasVal;

    /*
     * Array that allows us to determine if a given row
     * already contains a given value. True if contains; false otherwise.
     * Ex: rowHasVal[0][5] will be true if the first row has 5 in it.
     */
    private boolean[][] rowHasVal;
    
    /* 
     * Constructs a new Puzzle object, which initially
     * has all empty cells.
     */
    public Sudoku() {
        this.grid = new int[9][9];
        this.valIsFixed = new boolean[9][9];     
        
        /* 
         * Note that the third dimension of the following array is 10,
         * because we need to be able to use the possible values 
         * (1 through 9) as indices.
         */
        this.subgridHasVal = new boolean[3][3][10];        
        this.colHasVal = new boolean[9][10];
        this.rowHasVal = new boolean[9][10];
    }
    
    /*
     * Place the specified value in the cell with the specified
     * coordinates, and update the state of the puzzle accordingly.
     */
    public void placeVal(int val, int row, int col) {
        this.grid[row][col] = val;
        this.subgridHasVal[row/3][col/3][val] = true;
        this.colHasVal[col][val] = true;
        this.rowHasVal[row][val] = true;
    }
        
    /*
     * remove the specified value from the cell with the specified
     * coordinates, and update the state of the puzzle accordingly.
     */
    public void removeVal(int val, int row, int col) {
        this.grid[row][col] = 0;
        this.subgridHasVal[row/3][col/3][val] = false;
        this.colHasVal[col][val] = false;
        this.rowHasVal[row][val] = false;
    }  
        
    /*
     * read in the initial configuration of the puzzle from the specified 
     * Scanner, and use that config to initialize the state of the puzzle.  
     * The configuration should consist of one line for each row, with the
     * values in the row specified as integers separated by spaces.
     * A value of 0 should be used to indicate an empty cell.
     * 
     * You should not change this method.
     */
    public void readConfig(Scanner input) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = input.nextInt();
                this.placeVal(val, r, c);
                if (val != 0) {
                    this.valIsFixed[r][c] = true;
                }
            }
            input.nextLine();
        }
    }
                
    /*
     * Displays the current state of the puzzle.
     * You should not change this method.
     */        
    public void printGrid() {
        for (int r = 0; r < 9; r++) {
            this.printRowSeparator();
            for (int c = 0; c < 9; c++) {
                System.out.print("|");
                if (this.grid[r][c] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.print(" " + this.grid[r][c] + " ");
                }
            }
            System.out.println("|");
        }
        this.printRowSeparator();
    }
        
    // A private helper method used by display() 
    // to print a line separating two rows of the puzzle.
    private static void printRowSeparator() {
        for (int i = 0; i < 9; i++) {
            System.out.print("----");
        }
        System.out.println("-");
    }
    
    /*
     * Return "true" if num in subGrid. Otherwise, "false".
     */
    private boolean inBox(int row, int col, int num) {
        int r = row - row % 3;
        int c = col - col % 3;
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (this.grid[i][j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Returns "true" if num in row. Otherwise, "false".
     */
    private boolean inRow(int row, int num) {
        for (int i = 0; i < 9; i++) {
            if (this.grid[row][i] == num) {
                return true;
            }
        }
        return false;
    }

    /*
     * Returns "true" if num in col. Otherwise, "false".
     */
    private boolean inCol(int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (this.grid[i][col] == num) {
                return true;
            }
        }
        return false;
    }

    /*
     * Combines inBox, inRow, and inCol to check if a value is valid 
     * (false for all 3 criteria) in the Sudoku grid. Returns "true"
     * if all 3 criteria are false, "false" otherwise.
     */
    private boolean isValid(int row, int col, int num) {
        return !inBox(row,col,num) && !inRow(row,num) && !inCol(col,num);
    }
         
    /*
     * This is the key recursive-backtracking method.  Returns true if
     * a solution has already been found, and false otherwise.
     * 
     * Each invocation of the method is responsible for finding the
     * value of a single cell of the puzzle. The parameter n
     * is the number of the cell that a given invocation of the method
     * is responsible for. Consider the cells one row at a time,
     * from top to bottom and left to right, which means that
     * they would be numbered as follows:
     *
     *     0  1  2  3  4  5  6  7  8
     *     9 10 11 12 13 14 15 16 17
     *    18 ...
     */
    private boolean solveRB(int n) {
        int row = n / 9;
        int col = n % 9;
        if (row == this.grid.length) { // base case solution
            return true;
        }
        if (this.valIsFixed[row][col] == false) {
            for (int num = 1; num <= 9; num++) {
                if (this.isValid(row,col,num)) {
                    this.placeVal(num,row,col);
                    if (this.solveRB(n + 1)) {
                        return true;
                    }
                    this.removeVal(num,row,col);
                }
            }
        } else {
            return this.solveRB(n + 1);
        }
        return false;
    } 
    
    /*
     * public "wrapper" method for solveRB().
     * Makes the initial call to solveRB, and returns whatever it returns.
     */
    public boolean solve() { 
        boolean foundSol = this.solveRB(0);
        return foundSol;
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Sudoku puzzle = new Sudoku();
        
        System.out.print("Enter the name of the puzzle file: ");
        String filename = scan.nextLine();
        
        try {
            Scanner input = new Scanner(new File(filename));
            puzzle.readConfig(input);
        } catch (IOException e) {
            System.out.println("error accessing file " + filename);
            System.out.println(e);
            System.exit(1);
        }
        
        System.out.println();
        System.out.println("Here is the initial puzzle: ");
        puzzle.printGrid();
        System.out.println();
        
        if (puzzle.solve()) {
            System.out.println("Here is the solution: ");
        } else {
            System.out.println("No solution could be found.");
            System.out.println("Here is the current state of the puzzle:");
        }
        puzzle.printGrid();  
    }    
}
