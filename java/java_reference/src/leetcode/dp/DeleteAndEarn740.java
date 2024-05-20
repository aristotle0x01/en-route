package leetcode.dp;

import java.util.Arrays;

public class DeleteAndEarn740 {
    // https://leetcode.com/problems/delete-and-earn/description/
    // based on 198

    public int deleteAndEarn(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        int maxValue = 0;
        for (int v : nums) {
            if (v > maxValue){
                maxValue = v;
            }
        }
        int[] sumOfDiffValues = new int[maxValue+1];
        Arrays.fill(sumOfDiffValues, 0);
        for (int v : nums) {
            sumOfDiffValues[v] += v;
        }

        int[] dp = new int[maxValue+1];
        Arrays.fill(dp, 0);
        dp[0] = sumOfDiffValues[0];
        dp[1] = sumOfDiffValues[1];
        for (int i=2; i<=maxValue; i++) {
            dp[i] = Math.max(dp[i-2]+sumOfDiffValues[i], dp[i-1]);
        }

        return dp[maxValue];
    }
}
