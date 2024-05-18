package leetcode.dp;

import java.util.Arrays;

public class BestTime2BuyAndSellStock121 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int n = prices.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 0);
        dp[0] = 0;
        int leastPrice = prices[0];
        for (int i=1; i<n; i++) {
            int profit = prices[i] - leastPrice;
            dp[i] = Math.max(profit, dp[i - 1]);
            if (prices[i] < leastPrice) {
                leastPrice = prices[i];
            }
        }
        return dp[n-1];
    }
}
