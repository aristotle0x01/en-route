package leetcode.dp;

import java.util.Arrays;

public class LongestIncreasingSubsequence300 {
    // https://leetcode.com/problems/longest-increasing-subsequence/

    // there two kinds of dp
    // one you can simply return dp[n] (generally only depends on dp[n-1]), which would be the optimal
    // the other is more complex, dp[n] may depend on all previous dp[0...n-1]. plus, the final dp[n]
    // may not be the optimal, so you have to loop to get the max. And dp[n] will use element n, which
    // begets a certainty, will make it much easier to solve the problem. the same to leetcode 368
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int max = 1;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i=1; i<n; i++) {
            int vi = nums[i];
            for (int j=0; j<i; j++) {
                int vj = nums[j];
                if (vi > vj) {
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            if (dp[i] > max){
                max = dp[i];
            }
        }

        return max;
    }
}
