package leetcode.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Solution56 {

    // 56
    // https://leetcode.com/problems/merge-intervals/description/

    // construct pair to sort by first element
    // sort by comparator
    // arraylist then to array

    static class Pair {
        public int left;
        public int right;

        Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) return intervals;

        Pair[] pairs = new Pair[intervals.length];
        for (int i=0; i<intervals.length; i++) {
            pairs[i] = new Pair(intervals[i][0], intervals[i][1]);
        }
        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair s1, Pair s2) {
                if (s1.left == s2.left) {
                    return s1.right - s2.right;
                } else {
                    return s1.left - s2.left;
                }
            }
        });

        ArrayList<int[]> r = new ArrayList<>();
        int j = 1;
        int left = pairs[0].left;
        int right = pairs[0].right;
        while (j < pairs.length) {
            if (right < pairs[j].left) {
                r.add(new int[]{left, right});
                left = pairs[j].left;
                right = pairs[j].right;
                j++;
            } else if (right == pairs[j].left){
                right = pairs[j].right;
                j++;
            } else {
                if (right >= pairs[j].right) {
                    j++;
                } else {
                    right = pairs[j].right;
                    j++;
                }
            }

            // beware of this condition, lest omit last interval
            if (j == pairs.length) {
                r.add(new int[]{left, right});
            }
        }

        int[][] array = new int[r.size()][];
        for (int i = 0; i < r.size(); i++) {
            array[i] = r.get(i);
        }
        return array;
    }

    public static void main(String[] args) {
        Solution56 s = new Solution56();
        // [1,3],[2,6],[8,10],[15,18]
        int[][] array = new int[][]{{1,3}, {2,6}, {8,10}, {15,18}};
        int[][] a = s.merge(array);
        System.out.println();
    }
}
