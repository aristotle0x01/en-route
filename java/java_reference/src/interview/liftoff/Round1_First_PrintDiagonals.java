package interview.liftoff;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
Given an MxN matrix, write code which prints out the diagonals (from upper right to lower left) of the matrix. In this example where M = 4, N = 3:
[[9, 3, 2],
 [8, 6, 1],
 [5, 5, 6],
 [1, 2, 8]]

9
3 8
2 6 5
1 5 1
6 2
8
 */
public class Round1_First_PrintDiagonals {
    public void printDiagonals(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        // print first row
        for (int i=0; i<n; i++) {
            printDiagonal(matrix, 0, i, m, n);
        }
        // print last column
        for (int j=1;j<m; j++) {
            printDiagonal(matrix, j, n-1, m, n);
        }
    }

    private void printDiagonal(int[][] matrix, int row, int col, int m, int n){
        int totalSteps = Math.min(col+1, m-row);
        int step = 0;
        while (step < totalSteps) {
            System.out.print(matrix[row+step][col-step] + " ");
            step++;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Round1_First_PrintDiagonals s = new Round1_First_PrintDiagonals();
        int[][] matrix = new int[][] {
                {9,3,2},
                {8,6,1},
                {5,5,6},
                {1,2,8}
        };
        s.printDiagonals(matrix);
    }
}
