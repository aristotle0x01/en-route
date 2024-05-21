package leetcode.dp;

import java.util.Arrays;

public class LongestStringChain1048 {
    // https://leetcode.com/problems/longest-string-chain/description/
    public int longestStrChain(String[] words) {
        if (words == null) {
            return 0;
        }
        int n = words.length;
        if (n <= 1) {
            return n;
        }

        Arrays.sort(words, (o1, o2) -> {
            if (o1.length() == o2.length()){
                return o1.compareTo(o2);
            } else {
                return o1.length() - o2.length();
            }
        });

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int max = 1;
        for (int i=1; i<n; i++) {
            int ni = words[i].length();
            for (int j=i-1; j>=0; j--) {
                if ((words[j].length()+1) < ni) {
                    break;
                }

                if (isPredecessor(words[j], words[i])) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            if (dp[i] > max) {
                max = dp[i];
            }
        }

        return max;
    }

    // if s1 is predecessor of s2
    private boolean isPredecessor(String s1, String s2) {
        if (s1.length() + 1 != s2.length()) {
            return false;
        }

        int n2 = s2.length();
        for (int i=0; i<n2; i++) {
            String tmp = "";
            if (i == 0) {
                tmp = s2.substring(1);
            } else if (i == (n2-1)) {
                tmp = s2.substring(0, i);
            } else {
                tmp = s2.substring(0,i) + s2.substring(i+1);
            }
            if (s1.equals(tmp)) {
                return true;
            }
        }

        return false;
    }

    private boolean isPredecessor2(String s1, String s2) {
        if (s1.length() + 1 != s2.length()) {
            return false;
        }

        int n2 = s2.length();
        int n1 = n2-1;
        int p1 = 0;
        int p2 = 0;
        int countNotEqual = 0;
        while (p1 < n1 && p2 < n2) {
            if (s1.charAt(p1) == s2.charAt(p2)) {
                p1++;
                p2++;
            } else {
                countNotEqual++;
                p2++;

                if (countNotEqual > 1) {
                    break;
                }
            }
        }

        return countNotEqual == 1 || countNotEqual == 0;
    }

    public static void main(String[] args) {
        String s = "123";
        String ss = s.substring(0, 0);
        int i = 0;
    }
}
