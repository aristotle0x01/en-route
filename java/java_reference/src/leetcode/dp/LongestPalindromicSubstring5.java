package leetcode.dp;

import java.util.Arrays;

// this time using dynamic programming
public class LongestPalindromicSubstring5 {

    // https://leetcode.com/problems/longest-palindromic-substring/description/

    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty() || s.length() == 1) {
            return s;
        }

        int n = s.length();
        // ** why 2d array?
        boolean[][] judge = new boolean[n][n];
        // ** why init to true is ok?
        for (boolean[] row: judge) {
            Arrays.fill(row, true);
        }

        int startIndex = 0;
        int maxLen = 1;
        for (int i=n-2; i>=0; i--) {
            for (int j=i+1; j<n; j++) {
                // ** set false
                judge[i][j] = false;

                if (s.charAt(i) != s.charAt(j)) {
                    continue;
                }

                if ((j-i) <= 1) {
                    judge[i][j] = true;
                } else{
                    // ** what this mean?
                    judge[i][j] = judge[i+1][j-1];
                }

                if (judge[i][j] && (j-i+1) > maxLen) {
                    maxLen = j-i+1;
                    startIndex = i;
                }
            }
        }

        return s.substring(startIndex, startIndex+maxLen);
    }

    public static void main(String[] args) {
        int n = "ab".length(); // Get the length of the string.
        boolean[][] dp = new boolean[n][n]; // Create a dynamic programming (DP) table.

        // Initialize all substrings of length 1 (single character) as a palindrome.
        for (boolean[] row : dp) {
            Arrays.fill(row, true);
        }

        LongestPalindromicSubstring5 ss = new LongestPalindromicSubstring5();
        System.out.println(ss.longestPalindrome("cbbd"));
    }
}
