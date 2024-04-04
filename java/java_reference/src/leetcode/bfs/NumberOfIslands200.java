package leetcode.bfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

public class NumberOfIslands200 {
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

        TreeMap<String, Pair> map = new TreeMap<>();
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

            String key = map.firstKey();
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

                String nkey;
                if ((j-1)>=0 && grid[i][j-1]=='1' && map.containsKey(nkey=key(i, j-1))) {
                    queue.offer(map.get(nkey));
                }
                if ((j+1)<n && grid[i][j+1]=='1' && map.containsKey(nkey=key(i, j+1))) {
                    queue.offer(map.get(nkey));
                }
                if ((i-1)>=0 && grid[i-1][j]=='1' && map.containsKey(nkey=key(i-1, j))) {
                    queue.offer(map.get(nkey));
                }
                if ((i+1)<m && grid[i+1][j]=='1' && map.containsKey(nkey=key(i+1, j))) {
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
