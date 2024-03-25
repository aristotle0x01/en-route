package leetcode.common_structure;

import java.util.LinkedList;
import java.util.Queue;

public class ZigzagIterator281 {
    // Leetcode 281

    private final int[][] arrays;
    private Queue<Pair> pairs;

    private static class Pair {
        private int i;
        private int index;
        private final int length;

        public Pair(int i, int index, int size) {
            this.i = i;
            this.index = index;
            this.length = size;
        }
    }
    public ZigzagIterator281(int[][] arrays) {
        this.arrays = arrays;
        pairs = new LinkedList<>();
        for (int i=0; i<arrays.length; i++) {
            pairs.offer(new Pair(i, 0, arrays[i].length));
        }
    }

    public int next() {
        Pair pair = pairs.poll();
        int v = arrays[pair.i][pair.index++];
        if (pair.index < pair.length) {
            pairs.offer(pair);
        }
        return v;
    }

    public boolean hasNext() {
        return !pairs.isEmpty();
    }
}
