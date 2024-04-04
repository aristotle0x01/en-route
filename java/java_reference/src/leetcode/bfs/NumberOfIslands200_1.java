package leetcode.bfs;

import java.util.*;

public class NumberOfIslands200_1 {
    private static class Pair {
        int i;
        int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private String key(int i, int j) {
        return String.format("%d-%d", i, j);
    }

    public int numIslands(char[][] grid) {
        if (grid == null) {
           return 0;
        }
        int m = grid.length;
        if (m == 0) {
           return 0;
        }
        int n = grid[0].length;
        if (n == 0) {
            return 0;
        }

        HashMap<String, Pair> map = new HashMap<>();
        for (int i=0;i<m;i++) {
            for (int j=0;j<n;j++) {
                if (grid[i][j] == '1'){
                    map.put(key(i, j), new Pair(i, j));
                }
            }
        }

        int numOfIslands = 0;
        while (!map.isEmpty()) {
            numOfIslands++;

            String key = null;
            for (String s: map.keySet()) {
                key = s;
                break;
            }

            Queue<Pair> queue = new LinkedList<>();
            queue.offer(map.get(key));
            while (!queue.isEmpty()) {
                Pair p = queue.poll();
                int i = p.i;
                int j = p.j;

                String ijkey = key(i, j);
                if (!map.containsKey(ijkey)) {
                    continue;
                }
                map.remove(ijkey);

                String nkey = key(i, j-1);
                if (map.containsKey(nkey)) {
                    queue.offer(map.get(nkey));
                }
                nkey = key(i, j+1);
                if (map.containsKey(nkey)) {
                    queue.offer(map.get(nkey));
                }
                nkey = key(i-1, j);
                if (map.containsKey(nkey)) {
                    queue.offer(map.get(nkey));
                }
                nkey = key(i+1, j);
                if (map.containsKey(nkey)) {
                    queue.offer(map.get(nkey));
                }
            }
        }

        return numOfIslands;
    }

    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("0-a", 1);
        map.put("1-2", 1);
        map.put("1-3", 2);
        System.out.println(map.size());
    }
}
