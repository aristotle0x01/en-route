package leetcode.dp;

import java.util.Arrays;

public class MinimumPathSum64 {
    // https://leetcode.com/problems/minimum-path-sum/
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        for (int i=0; i<m; i++) {
            Arrays.fill(dp[i], 0);
        }
        dp[0][0] = grid[0][0];
        for (int i=1; i<n; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }
        for (int i=1; i<m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }

        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                int top = dp[i-1][j] + grid[i][j];
                int left = dp[i][j-1] + grid[i][j];
                if (top < left) {
                    dp[i][j] = top;
                } else {
                    dp[i][j] = left;
                }
            }
        }

        return dp[m-1][n-1];
    }
}
