package leetcode;

public class Solution48 {

    // 0048
    // https://leetcode.com/problems/rotate-image/description/

    // treat the matrix like an onion, each "square" as a layer
    // then rotate it layer by layer

    // but how to rotate a layer?
    // only the first row (except last element) of each layer needs to be rotated
    // just loop the first row, for each round, do:
    // since -1000 <= matrix[i][j] <= 1000, use -1001 as a mark
    // substitute the first element e to this mark, rotate e to its destination d at (fi, fj)
    // continue to rotate d, until d == mark

    public void rotate(int[][] matrix) {
        int layers = matrix.length / 2;
        int layer = 0;
        while (layer < layers) {
            rotate(matrix, layer);
            layer++;
        }
    }

    void rotate(int[][] matrix, int layer) {
        int size = matrix.length;

        int top_left = layer;
        int bottom_right = size - 1 - layer;
        if (top_left == bottom_right) {
            return;
        }

        int[] indexes = new int[]{-1, -1};
        for (int k=0;k<(bottom_right-top_left);k++) {
            int i = top_left+k;
            int j = top_left;
            int t = special;
            do {
                t = rotate_to(matrix, t, i, j, indexes);
                i = indexes[0];
                j = indexes[1];
            } while ( t != special);
        }
    }

    // since -1000 <= matrix[i][j] <= 1000
    // used as a mark, the original element already rotated, but new element
    // not filled yet
    static int special = -1001;
    // rotate element in (i, j) to its final position, and store final pos in indexes
    int rotate_to(int[][] matrix, int t, Integer i, Integer j, int[] indexes) {
        int fi, fj;
        // m[i][j] => m[j][size-i-1]
        fi = j;
        fj = matrix.length - i - 1;

        int temp = matrix[fi][fj];
        if (t == special) {
            matrix[fi][fj] = matrix[i][j];
            matrix[i][j] = t;
        } else {
            matrix[fi][fj] = t;
        }

        t = temp;
        indexes[0] = fi;
        indexes[1] = fj;
        return t;
    }

    public static void main(String[] args) {
        Solution48 s = new Solution48();
        int[][] array = {
                {5, 1, 9, 11},
                {2, 4, 8, 10},
                {13, 3, 6, 7},
                {15, 14, 12, 16}
        };
        s.rotate(array);
        System.out.println("done");
    }
}
