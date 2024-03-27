package leetcode.pointer2;

import java.util.Arrays;

public class LongestPalindromicSubstring5 {

    // https://leetcode.com/problems/longest-palindromic-substring/description/

    // -1, 0, 1
    int[][] judge;

    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty() || s.length() == 1) {
            return s;
        }

        judge = new int[s.length()][s.length()];
        for (int i=0; i<s.length(); i++) {
            for (int j=0; j<s.length(); j++) {
                judge[i][j] = -1;
            }
        }

        int maxLen = 1;
        String longest = s.substring(0,1);
        for (int i=0; i<s.length(); i++) {
            for (int j=s.length()-1; j>i; j--) {
                boolean b = false;
                int strlen = j-i+1;
                while (true) {
                    if (judge[i][j] == 0) {
                        break;
                    }

                    if (judge[i][j] == 1) {
                        b = true;
                        break;
                    }

                    if (s.charAt(i) != s.charAt(j)) {
                        break;
                    }

                    if ((j-i) == 1) {
                        b = true;
                    } else {
                        b = setGetPalindrome(s, i+1, j-1);
                    }
                    break;
                }

                if (b) {
                    judge[i][j] = 1;
                    if (strlen > maxLen) {
                        maxLen = strlen;
                        longest = s.substring(i, j+1);
                    }
                } else {
                    judge[i][j] = 0;
                }
            }
        }

        return longest;
    }

    private boolean setGetPalindrome(String str, int s, int e) {
        if (judge[s][e] != -1) {
            return judge[s][e] == 1;
        }

        if (str.charAt(s) == str.charAt(e)) {
            if (s == e || (e-s) == 1) {
                judge[s][e] = 1;
                return true;
            }

            boolean b= setGetPalindrome(str, s+1, e-1);
            if (b) {
                judge[s][e] = 1;
                return true;
            } else{
                judge[s][e] = 0;
                return false;
            }
        } else {
            judge[s][e] = 0;
            return false;
        }
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
