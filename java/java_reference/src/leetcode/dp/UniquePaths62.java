package leetcode.dp;

public class UniquePaths62 {

    private int uniquePathsNum = 0;
    public int uniquePaths(int m, int n) {
        dfs(1,1, m, n);
        return uniquePathsNum;
    }

    private void dfs(int i, int j, int m, int n) {
        if (i > m || j > n) {
            return;
        }
        if (i == m && j == n) {
            uniquePathsNum++;
            return;
        }
        dfs(i+1, j, m, n);
        dfs(i, j+1, m, n);
    }

    public int uniquePaths2(int m, int n) {
        int[][] dp = new int[m+1][n+1];
        for (int i=1; i<=n; i++) {
            dp[1][i] = 1;
        }
        for (int i=0; i<=m; i++) {
            dp[i][1] = 1;
        }
        for (int i=2; i<=m; i++) {
            for (int j=2; j<=n; j++) {
                dp[i][j] = dp[i][j-1] + dp[i-1][j];
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        UniquePaths62 s = new UniquePaths62();
        System.out.println(s.uniquePaths2(19, 13));
    }
}
