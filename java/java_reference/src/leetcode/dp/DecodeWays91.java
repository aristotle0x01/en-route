package leetcode.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DecodeWays91 {
    // https://leetcode.com/problems/decode-ways/
    public int numDecodings(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }

        Set<String> validSet = new HashSet<>();
        for (int i=1; i<=26; i++) {
            validSet.add("" + i);
        }

        int n = s.length();
        int[] dp = new int[n];
        Arrays.fill(dp, 0);
        dp[0] = 1;
        if (validSet.contains(s.substring(1, 2))) {
            dp[1] = 1;
        }
        if (validSet.contains(s.substring(0, 2))) {
            dp[1] += 1;
        }
        for (int i=2; i<n; i++) {
            if (validSet.contains(s.substring(i, i+1))) {
                if (validSet.contains(s.substring(i-1, i+1))) {
                    dp[i] = dp[i-1] + dp[i-2];
                } else {
                    dp[i] = dp[i-1];
                }
            } else {
                if (validSet.contains(s.substring(i-1, i+1))) {
                    dp[i] = dp[i-2];
                } else {
                    break;
                }
            }
        }

        return dp[n-1];
    }

    public int numDecodings2(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }

        int n = s.length();
        int[] dp = new int[n];
        Arrays.fill(dp, 0);
        dp[0] = 1;
        if (isValid(s.charAt(1))) {
            dp[1] = 1;
        }
        if (isValid(s.charAt(0), s.charAt(1))) {
            dp[1] += 1;
        }
        for (int i=2; i<n; i++) {
            if (isValid(s.charAt(i))) {
                if (isValid(s.charAt(i-1), s.charAt(i))) {
                    dp[i] = dp[i-1] + dp[i-2];
                } else {
                    dp[i] = dp[i-1];
                }
            } else {
                if (isValid(s.charAt(i-1), s.charAt(i))) {
                    dp[i] = dp[i-2];
                } else {
                    break;
                }
            }
        }

        return dp[n-1];
    }

    private boolean isValid(char c) {
        return c >= '1' && c <= '9';
    }

    private boolean isValid(char c1, char c2) {
        if (c1 == '1') {
            return c2 >= '0' && c2 <= '9';
        } else if (c1 == '2') {
            return c2 >= '0' && c2 <= '6';
        } else {
            return false;
        }
    }
}
