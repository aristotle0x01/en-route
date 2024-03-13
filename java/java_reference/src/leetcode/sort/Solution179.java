package leetcode.sort;

import java.util.Arrays;
import java.util.Comparator;

public class Solution179 {

    // 179
    // https://leetcode.com/problems/largest-number/description/

    // construct pair to sort by initial
    // sort by comparator

    static class Pair {
        public int initial;
        public int v;

        int power;

        Pair(int initial, int v, int power) {
            this.initial = initial;
            this.v = v;
            this.power = power;
        }
    }

    public String largestNumber1(int[] nums) {
        if (nums.length == 1) return String.format("%d", nums[0]);

        Pair[] pairs = new Pair[nums.length];
        for (int i=0; i<nums.length; i++) {
            int initial = nums[i];
            while (initial >= 10) {
                initial = initial/10;
            }
            pairs[i] = new Pair(initial, nums[i], 0);
        }
        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair s1, Pair s2) {
                if (s1.initial != s2.initial) {
                    return s1.initial - s2.initial;
                } else {
                    String t1 = String.format("%d%d", s1.v, s2.v);
                    String t2 = String.format("%d%d", s2.v, s1.v);
                    return t1.compareTo(t2);
                }
            }
        });
        if (pairs[pairs.length-1].initial == 0) {
            return "0";
        }

        int j = nums.length-1;
        StringBuilder sb = new StringBuilder();
        while (j >= 0) {
            sb.append(String.format("%d", pairs[j].v));
            j--;
        }

        return sb.toString();
    }

    public String largestNumber(int[] nums) {
        if (nums.length == 1) return String.format("%d", nums[0]);

        int[] powers = new int[10];
        powers[0] = 10;
        for (int i=1; i<10;i++) {
            powers[i] = powers[i-1]*10;
        }

        Pair[] pairs = new Pair[nums.length];
        for (int i=0; i<nums.length; i++) {
            int initial = nums[i];
            int power = 1;
            while (initial >= 10) {
                initial = initial/10;
                power++;
            }
            pairs[i] = new Pair(initial, nums[i], power);
        }
        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair s1, Pair s2) {
                if (s1.initial != s2.initial) {
                    return s1.initial - s2.initial;
                } else {
                    int t1 = s1.v * powers[s2.power-1];
                    int temp1 = t1 + s2.v;
                    int t2 = s2.v * powers[s1.power-1];
                    int temp2 = t2 + s1.v;

                    return temp1 - temp2;
                }
            }
        });
        if (pairs[pairs.length-1].initial == 0) {
            return "0";
        }

        int j = nums.length-1;
        StringBuilder sb = new StringBuilder();
        while (j >= 0) {
            sb.append(String.format("%d", pairs[j].v));
            j--;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Solution179 s = new Solution179();
        int[] a = new int[]{3,30,34,5,9};
        System.out.println(s.largestNumber(a));
    }
}
