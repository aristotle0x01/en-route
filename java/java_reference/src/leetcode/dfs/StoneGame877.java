package leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

public class StoneGame877 {
    // https://leetcode.com/problems/stone-game/description/

    private int sum = 0;
    private int half = 0;
    private List<Integer> list = new ArrayList<>();

    public boolean stoneGame(int[] piles) {
        for (int p : piles) {
            sum += p;
            list.add(p);
        }
        half = (sum-1)/2;

        return dfs(0);
    }

    private boolean dfs(int alice) {
        if (alice > half) {
            return true;
        }
        if (list.isEmpty()) {
            if (alice > half) {
                return true;
            } else {
                return false;
            }
        }
        // alice take head
        int head = list.remove(0);
        {
            // bob take head
            int bob_head = list.remove(0);
            boolean bh = dfs(alice + head);
            list.add(0, bob_head);
            if (bh) {
                int bob_tail = list.remove(list.size() - 1);
                boolean bt = dfs(alice + head);
                list.add(bob_tail);
                if (bt) {
                    list.add(0, head);
                    return true;
                }
            }
        }
        list.add(0, head);

        // alice take tail
        int tail = list.remove(list.size() - 1);
        {
            // bob take head
            int bob_head = list.remove(0);
            boolean bh = dfs(alice + tail);
            list.add(0, bob_head);
            if (bh) {
                int bob_tail = list.remove(list.size() - 1);
                boolean bt = dfs(alice + tail);
                list.add(bob_tail);

                list.add(tail);
                return bt;
            }
        }
        list.add(tail);
        return false;
    }

    public static void main(String[] args) {
        StoneGame877_2 s = new StoneGame877_2();
        System.out.println(s.stoneGame(new int[]{8,9,7,6,7,6}));
        System.out.println(s.stoneGame(new int[]{7,8,8,10}));
        System.out.println(s.stoneGame(new int[]{5,3,4,5}));
        System.out.println(s.stoneGame(new int[]{3,7,2,3}));
        System.out.println(s.stoneGame(new int[]{7,7,12,16,41,48,41,48,11,9,34,2,44,30,27,12,11,39,31,8,23,11,47,25,15,23,4,17,11,50,16,50,38,34,48,27,16,24,22,48,50,10,26,27,9,43,13,42,46,24}));
    }

    public static class StoneGame877_2 {
        private int sum = 0;
        private int half = 0;
        private int[] piles;

        private int n;

        private Integer[][] cache;

        public boolean stoneGame(int[] piles) {
            this.piles = piles;
            this.n = this.piles.length;
            this.sum = 0;
            for (int p : piles) {
                sum += p;
            }
            half = (sum-1)/2;
            cache = new Integer[n][n];

            return dfs(0, n-1) > half;
        }

        private int dfs(int i, int j) {
            if (i >= j) {
                return 0;
            }
            if (cache[i][j] != null) {
                return cache[i][j];
            }

            // alice take head
            int head = this.piles[i];
            // bob takes head
            int bh = dfs(i+2, j);
            // bob takes tail
            int bt = dfs(i+1, j-1);
            // using min because after alice's choice, bob can take the initiative
            // by using min, we check the optimal result no matter what bob chooses
            cache[i][j] = Math.min(head+bh, head+bt);

            // alice take tail
            int tail = this.piles[j];
            // bob takes head
            bh = dfs(i+1, j-1);
            // bob takes tail
            bt = dfs( i, j-2);

            cache[i][j] = Math.max(Math.min(tail+bh, tail+bt), cache[i][j]);

            return cache[i][j];
        }
    }
}
