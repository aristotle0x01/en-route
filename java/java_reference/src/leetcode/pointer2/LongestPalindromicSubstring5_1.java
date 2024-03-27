package leetcode.pointer2;

import java.util.Arrays;

// this time, use the two opposing pointer way
// for every possible Palindromic Substring, its length can be
// odd or even, if string length is n:
// for the odd cases: there are n cases
// for the even cases: there are n-1 cases
public class LongestPalindromicSubstring5_1 {

    // https://leetcode.com/problems/longest-palindromic-substring/description/

    int maxLen = 1;
    int startIndex = 0;

    int n;

    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        n = s.length();
        for (int i=0; i<n; i++) {
            fromPivot(s, i, i);
            if ((i+1)<n) {
                fromPivot(s, i, i+1);
            }
        }

        return s.substring(startIndex, startIndex+maxLen);
    }

    private void fromPivot(String s, int p1, int p2) {
        while (p1 >= 0 && p2 < n && s.charAt(p1) == s.charAt(p2)) {
            int len = p2 - p1 + 1;
            if (len > maxLen) {
                maxLen = len;
                startIndex = p1;
            }
            p1--;
            p2++;
        }
    }

    public static void main(String[] args) {
        int n = "ab".length(); // Get the length of the string.
        boolean[][] dp = new boolean[n][n]; // Create a dynamic programming (DP) table.

        // Initialize all substrings of length 1 (single character) as a palindrome.
        for (boolean[] row : dp) {
            Arrays.fill(row, true);
        }

        LongestPalindromicSubstring5_1 ss = new LongestPalindromicSubstring5_1();
        System.out.println(ss.longestPalindrome("cbbd"));
    }
}
