package leetcode.pointer2.back;

import java.util.Arrays;

public class PalindromicSubstrings647 {

    // https://leetcode.com/problems/palindromic-substrings/

    // if palindromic sub string is odd, every element is possible as center
    // if palindromic sub string is even, every two elements are possible as center
    public int countSubstrings(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }

        int n = s.length();
        int num = n;
        for (int i=0; i<n; i++) {
            int p1 = i-1;
            int p2 = i+1;
            while (p1 >= 0 && p2<n && s.charAt(p1) == s.charAt(p2)) {
                num++;
                p1--;
                p2++;
            }

            if ((i+1) < n && s.charAt(i) == s.charAt(i+1)) {
                num++;
                p1 = i-1;
                p2 = i+2;
                while (p1 >= 0 && p2<n && s.charAt(p1) == s.charAt(p2)) {
                    num++;
                    p1--;
                    p2++;
                }
            }
        }

        return num;
    }

    public int countSubstrings_dp(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }

        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        int num = 0;
        for (int i=n-1; i>=0; i--) {
            for (int j=i; j<n; j++) {
                dp[i][j] = false;

                if (s.charAt(i) != s.charAt(j)) {
                    continue;
                }
                if ((j-i) <= 1) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = dp[i+1][j-1];
                }

                if (dp[i][j]) {
                    num++;
                }
            }
        }
        return num;
    }
}
