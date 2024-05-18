package leetcode.dp;

import java.util.Arrays;

public class CoinChange322 {
    // https://leetcode.com/problems/coin-change/description/

    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }

        Arrays.sort(coins);

        int n = coins.length;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int a=1; a<=amount; a++) {
            int tmp = Integer.MAX_VALUE;
            for (int i=0; i<n; i++) {
                int coin = coins[i];
                int prev = a - coin;
                if (prev < 0) {
                    break;
                }
                if (dp[prev] != -1 && (dp[prev]+1) < tmp) {
                    tmp = dp[prev]+1;
                }
            }
            if (tmp != Integer.MAX_VALUE) {
                dp[a] = tmp;
            }
        }

        return dp[amount];
    }
}
