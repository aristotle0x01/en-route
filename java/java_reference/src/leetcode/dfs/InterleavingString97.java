package leetcode.dfs;

public class InterleavingString97 {
    // https://medium.com/@_monitsharma/daily-leetcode-problems-problem-97-interleaving-string-b64ce057f850
    // https://www.youtube.com/watch?v=sfP64T6SmaY

    // 0 <= s1.length, s2.length <= 100
    // 0 <= s3.length <= 200
    // s1, s2, and s3 consist of lowercase English letters.

    int m, n;
    String s3;
    char[] array;
    Boolean[][] cache;

    public boolean isInterleave(String s1, String s2, String s3) {
        if (s3 == null) {
            return false;
        }
        if (s1 == null) {
            return s3.equals(s2);
        }
        if (s2 == null) {
            return s3.equals(s1);
        }

        int len = s1.length() + s2.length();
        if (len != s3.length()) {
            return false;
        }

        m = s1.length();
        n = s2.length();
        array = new char[m+n];
        cache = new Boolean[m+1][n+1];
        this.s3 = s3;

        return dfs(s1, 0, s2, 0);
    }

    private boolean dfs(String s1, int i1, String s2, int i2) {
        if (i1 < m && i2 < n) {
            array[i1 + i2] = s1.charAt(i1);
            if (dfs(s1, i1+1, s2, i2)) {
                return true;
            } else {
                array[i1+i2] = s2.charAt(i2);
                return dfs(s1, i1, s2, i2+1);
            }
        } else if (i1 < m) {
            array[i1+i2] = s1.charAt(i1);
            return dfs(s1, i1+1, s2, i2);
        } else if (i2 < n) {
            array[i1+i2] = s2.charAt(i2);
            return dfs(s1, i1, s2, i2+1);
        } else {
            return String.valueOf(array).equals(s3);
        }
    }
    private boolean dfs2(String s1, int i1, String s2, int i2) {
        if (i1 >= m && i2 >= n) {
            return String.valueOf(array).equals(s3);
        }
        if (cache[i1][i2] != null) {
            return cache[i1][i2];
        }

        if (i1 < m && i2 < n) {
            array[i1+i2] = s1.charAt(i1);
            cache[i1][i2] = dfs(s1, i1+1, s2, i2);
            if (!cache[i1][i2]) {
                array[i1+i2] = s2.charAt(i2);
                cache[i1][i2] = dfs(s1, i1, s2, i2+1);
            }
        } else if (i1 < m) {
            array[i1+i2] = s1.charAt(i1);
            cache[i1][i2] = dfs(s1, i1+1, s2, i2);
        } else {
            array[i1+i2] = s2.charAt(i2);
            cache[i1][i2] = dfs(s1, i1, s2, i2+1);
        }

        return cache[i1][i2];
    }

    public static void main(String[] args) {
        InterleavingString97 s = new InterleavingString97();
        System.out.println(s.isInterleave("aabcc", "dbbca", "aadbbcbcac"));
        // s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
        System.out.println(s.isInterleave("bbaabacacbabcbaa", "abccccbaccaca", "bbccccababaaccacaccbaababcbaa"));
        System.out.println(s.isInterleave("", "", ""));
    }


    public static class InterleavingString2 {
        int m, n;
        String s1, s2, s3;
        Boolean[][] cache;

        final char unknown = 0;

        public boolean isInterleave(String s1, String s2, String s3) {
            if (s3 == null) {
                return false;
            }
            if (s1 == null || s1.isEmpty()) {
                return s3.equals(s2);
            }
            if (s2 == null || s2.isEmpty()) {
                return s3.equals(s1);
            }
            int len = s1.length() + s2.length();
            if (len != s3.length()) {
                return false;
            }

            m = s1.length();
            n = s2.length();
            cache = new Boolean[m+1][n+1];
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;

            return dfs(0, 0);
        }

        private boolean dfs(int i, int j) {
            if (i == m && j == n) {
                return true;
            }
            if (cache[i][j] != null) {
                return cache[i][j];
            }

            char c3 = this.s3.charAt(i+j);
            char c1 = i == m ? unknown : s1.charAt(i);
            char c2 = j == n ? unknown : s2.charAt(j);
            if (c1 == c3 && c2 == c3) {
                cache[i][j] = dfs(i+1, j) || dfs(i, j+1);
            } else if (c1 == c3) {
                cache[i][j] = dfs(i+1, j);
            } else if (c2 == c3) {
                cache[i][j] = dfs(i, j+1);
            } else {
                cache[i][j] = false;
            }

            return cache[i][j];
        }
    }

    public static class InterleavingString3 {
        public boolean isInterleave(String s1, String s2, String s3) {
            if (s3 == null) {
                return false;
            }
            if (s1 == null || s1.isEmpty()) {
                return s3.equals(s2);
            }
            if (s2 == null || s2.isEmpty()) {
                return s3.equals(s1);
            }
            int len = s1.length() + s2.length();
            if (len != s3.length()) {
                return false;
            }

            int m = s1.length();
            int n = s2.length();
            Boolean[][] dp = new Boolean[m+1][n+1];
            // dp[i][j] 表示s1前i个字符与s2前j个字符组合是否可以等于s3的前(i+j)个字符

            // 表示空字符串，则可以设置为true
            dp[0][0] = true;
            for (int i = 1; i <= m; i++) {
                // 仅使用s1的情况
                dp[i][0] = s1.charAt(i-1) == s3.charAt(i-1) && dp[i-1][0];
            }
            for (int i = 1; i <= n; i++) {
                // 仅使用s2的情况
                dp[0][i] = s2.charAt(i-1) == s3.charAt(i-1) && dp[0][i-1];
            }

            for (int i=1; i<=m; i++) {
                for (int j=1; j<=n; j++) {
                    char c1 = s1.charAt(i-1);
                    char c2 = s2.charAt(j-1);
                    // s3.charAt(0)在初始化时dp[0][1]和dp[1][0]已经处理过了，故可以从下标1处开始
                    char c3 = s3.charAt(i+j-1);

                    boolean b1 = dp[i-1][j] && c1 == c3;
                    boolean b2 = dp[i][j-1] && c2 == c3;
                    dp[i][j] = b1 || b2;
                }
            }

            return dp[m][n];
        }
    }
}
