package leetcode.dfs;

public class StoneGameII1140 {
    // https://leetcode.com/problems/stone-game-ii/description/

    private int n;
    private int[] piles;
    private int[] sums;
    private Integer[][][] cache;

    public int stoneGameII(int[] piles) {
        this.n = piles.length;
        this.piles = piles;
        if (n <= 2) {
            int sum = 0;
            for (int i=0; i<n; i++) {
                sum += piles[i];
            }
            return sum;
        }

        cache = new Integer[n][2*n][2*n];
        sums = new int[n];
        sums[n-1] = piles[n-1];
        for (int j=n-2; j>=0; j--) {
            sums[j] = piles[j] + sums[j+1];
        }

        int M = 1;
        int max = 0;
        for (int x=1; x<=(2*M); x++) {
            max = Math.max(dfs(0, x, 1), max);
        }
        return max;
    }

    private int dfs(int i, int x, int M) {
        int acc = 0;
        if (i >= n) {
            return acc;
        }
        if (cache[i][x][M] != null) {
            return cache[i][x][M];
        }

        int loop = 0;
        int j = i;
        while (loop < x && j < n) {
            acc += piles[j];
            loop++;
            j++;
        }
        if (j == n) {
            cache[i][x][M] = acc;
            return acc;
        }

        int MBob = Math.max(M, x);
        // bob can pick num of piles: 1<=x<=2MBob
        int max = 0;
        for (int k=1; k<=(2*MBob); k++) {
            max = Math.max(dfs(j, k, MBob), max);
        }

        cache[i][x][M] = acc + (sums[j] - max);
        return cache[i][x][M];
    }

    public static void main(String[] args) {
        StoneGameII1140 s = new StoneGameII1140();
        // int[] arr = new int[]{1,1,1};
        int[] arr = new int[]{
                7678,8845,4908,113,5895,9508,6793,1075,1520,800,8935,7774,571,2042,9164,9886,2625,6733,7565,2940,1517,8138,1408,4936,2406,7851,2555,6883,2032,4572,7577,6060,870,1848,5442,8578,2161,6175,6069,5153,8786,4679,4320,3414,1180,4790,208,2269,7261,8187,4014,6908,7845,673,370,9753,4241,4871,25
        };
        int optimal = s.stoneGameII(arr);
        System.out.println(optimal);
    }
}
