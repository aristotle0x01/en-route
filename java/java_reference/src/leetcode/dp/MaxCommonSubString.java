package leetcode.dp;

import java.util.Arrays;

public class MaxCommonSubString {
    // 最长公共子串（Longest Common Substring）： 是指两个字符串中最长连续相同的子串长度。
    // 例如：str1=“1AB2345CD”,str2=”12345EF”,则str1，str2的最长公共子串为2345。

    // for each char in s1, make it as the start, from which find the
    // largest common substring in s2
    public String maxCommonSubStr(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return "";
        }

        int max = 0;
        String maxSub = "";
        int m = s1.length();
        int n = s2.length();
        for (int i=0; i<m; i++) {
            char c1 = s1.charAt(i);
            int index = s2.indexOf(c1, 0);
            while (index != -1) {
                int pi = i+1;
                int pj = index+1;
                while (pi < m && pj < n && s1.charAt(pi) == s2.charAt(pj)) {
                    pi++;
                    pj++;
                }

                int len = pi - i;
                if (len > max) {
                    max = len;
                    maxSub = s1.substring(i, pi);
                }

                index = s2.indexOf(c1, index+1);
            }
        }

        return maxSub;
    }

    public String maxCommonSubStr_dp(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return "";
        }

        int max = 0;
        int startIndex = -1;
        int m = s1.length();
        int n = s2.length();

        // dp[i][j] 表示以s1[i],s2[j]结尾的最长公共子串
        int[][] dp = new int[m+1][n+1];
        for (int i=0; i<=m; i++) {
            Arrays.fill(dp[i], 0);
        }

        for (int i=1; i<=m; i++) {
            char c1 = s1.charAt(i-1);
            for (int j=1; j<=n; j++) {
                char c2 = s2.charAt(j-1);
                if (c1 == c2) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = 0;
                }

                if (dp[i][j] > max) {
                    max = dp[i][j];
                    startIndex = i-max;
                }
            }
        }

        if (startIndex == -1){
            return "";
        }
        return s1.substring(startIndex, startIndex+max);
    }

    public static void main(String[] args){
        MaxCommonSubString s = new MaxCommonSubString();
        System.out.println(s.maxCommonSubStr_dp("1AB2345CD", "12345EF"));
    }
}
