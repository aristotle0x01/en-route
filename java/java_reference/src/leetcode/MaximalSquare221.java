package leetcode;

import java.util.HashMap;

public class MaximalSquare221 {
    // https://leetcode.com/problems/maximal-square/

    private HashMap<String, Boolean> cache;
    private char[][] matrix;

    private int maxSide;
    int m,n;

    private String formKey(int li, int lj, int ri, int rj){
        // top left -> bottom right
        return String.format("%d-%d:%d-%d", li, lj, ri, rj);
    }

    private boolean isSquare(int li, int lj, int ri, int rj) {
        if (li > ri || lj > rj) {
            return false;
        }
        if (li == ri && lj == rj) {
            if (matrix[li][lj] == '1') {
                return true;
            } else {
                return false;
            }
        }
        if (li == ri || lj == rj) {
            return false;
        }
        if ((ri-li) != (rj-lj)) {
            return false;
        }

        String key = formKey(li, lj, ri, rj);
        if (cache.containsKey(key) && cache.get(key)) {
            return cache.get(key);
        }
        int step = rj - lj;
        boolean ok = true;
        int count = 0;
        while (count < step) {
            if (matrix[li][lj+count+1] == '1' && matrix[li+count+1][lj] == '1') {
                count++;
            } else {
                ok = false;
                break;
            }
        }
        if (ok) {
            ok = isSquare(li+1, lj+1, ri, rj);
            cache.put(key, ok);
        } else {
            cache.put(key, false);
        }

        return cache.get(key);
    }

    public int maximalSquare(char[][] matrix) {
        this.m = matrix.length;
        this.n = matrix[0].length;

        this.cache = new HashMap<>();
        this.matrix = matrix;
        this.maxSide = 0;

        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                char c = matrix[i][j];
                if (c == '0') {
                    continue;
                }

                int step = 0;
                int maxStep = Math.min(n-j, m-i);
                while (step < maxStep) {
                    String key = formKey(i, j, i+step, j+step);
                    if (cache.get(key) != null) {
                        break;
                    }

                    if (isSquare(i, j, i+step, j+step)) {
                        if ((step+1) > maxSide) {
                            maxSide = step+1;
                        }
                        step++;
                    } else {
                        break;
                    }
                }
            }
        }

        return maxSide * maxSide;
    }

    public int maximalSquare2(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int maxSide = 0;
        for (int i=0; i<m; i++) {
            int j = 0;
            while (j < n) {
                if (matrix[i][j] == '0') {
                    j++;
                    continue;
                }

                int side = 0;
                int sj = j;
                while (j < n && matrix[i][j] == '1') {
                    j++;
                    side++;
                }

                int oldSide = side;
                int max_sj = sj + side - 1;
                while (oldSide > maxSide) {
                    side = oldSide;

                    int hstep = 1;
                    for (int k=i+1; k<m; k++) {
                        if (hstep >= side) {
                            break;
                        }
                        if (matrix[k][sj] == '1') {
                            hstep++;
                        } else {
                            break;
                        }
                    }

                    side = Math.min(side, hstep);
                    if (side > maxSide) {
                        boolean hasZeros = false;
                        for (int ti=i; ti<(i+side); ti++){
                            if (hasZeros) {
                                break;
                            }
                            for (int tj=sj; tj<(sj+side); tj++) {
                                if ('0' == matrix[ti][tj]) {
                                    hasZeros = true;
                                    break;
                                }
                            }
                        }
                        if (!hasZeros) {
                            maxSide = side;
                        }
                    }

                    oldSide--;
                    sj++;
                    if (sj > max_sj) {
                        break;
                    }
                }
            }
        }

        return maxSide * maxSide;
    }

    public static void main(String[] args) {
        MaximalSquare221 s = new MaximalSquare221();
//        char[][] matrix = {
//                {'0', '1'},
//                {'1', '0'}
//        };
        String[][] values = {
                {"0", "0", "1", "0"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "0"},
                {"1", "1", "0", "0"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "0"}
        };
        char[][] matrix = new char[values.length][values[0].length];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                matrix[i][j] = values[i][j].charAt(0);
            }
        }
        int square = s.maximalSquare(matrix);
        System.out.println(square);
    }
}
