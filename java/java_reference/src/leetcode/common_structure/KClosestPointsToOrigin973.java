package leetcode.common_structure;

import java.util.PriorityQueue;

public class KClosestPointsToOrigin973 {
    public int[][] kClosest(int[][] points, int k) {
        int[][] results = new int[k][2];
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for (int i=0; i<points.length; i++) {
            int d = points[i][0]*points[i][0] + points[i][1]*points[i][1];
            pq.offer(new Pair(d, i));
        }

        int i = 0;
        while (i < k) {
            Pair pair = pq.poll();
            results[i][0] = points[pair.index][0];
            results[i][1] = points[pair.index][1];
            i++;
        }
        return results;
    }

    private static class Pair implements Comparable {
        public int distance;
        public int index;

        public Pair(int d, int i) {
            this.distance = d;
            this.index = i;
        }

        @Override
        public int compareTo(Object o) {
            return this.distance - ((Pair)o).distance;
        }
    }
}
