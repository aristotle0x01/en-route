package leetcode.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak139_dp {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        Arrays.fill(dp, true);
        return breakdown(s, new HashSet<>(wordDict), dp);
    }

    // how to build dp bottom up
    // dp[n] == true to facilitate calculation

    // sub(i, j) && dp(j)
    private boolean breakdown(String s, Set<String> wordDict, boolean[] dp) {
        for (int i=s.length()-1; i>=0; i--) {
            dp[i] = false;

            int j;
            for (j=s.length(); j > i; j--) {
                if (!dp[j]) {
                    continue;
                }

                String sub = s.substring(i, j);
                if (wordDict.contains(sub) && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }

            if (j == i) {
                dp[i] = false;
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        WordBreak139_dp w = new WordBreak139_dp();
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        boolean b = w.wordBreak(s, Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"));
        System.out.println(b);
    }
}
