package leetcode.dp;

import java.util.Arrays;

public class ClimbingStairs70 {
    // https://leetcode.com/problems/climbing-stairs/

    public int climbStairs(int n) {
        if (n <= 1) {
            return n;
        }

        // dp[i] means number of ways for if input is i
        int[] dp = new int[n+1];
        Arrays.fill(dp, 0);
        dp[1] = 1;
        dp[2] = 2;
        for (int i=3; i<=n; i++) {
            dp[i] = dp[i-2] + dp[i-1];
        }

        return dp[n];
    }
}
