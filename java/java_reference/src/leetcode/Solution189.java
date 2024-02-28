package leetcode;

import java.util.Arrays;

public class Solution189 {

    // 0189
    // https://leetcode.com/problems/rotate-array/description/

    // use another array to copy, just too brutal force
    public void rotate1(int[] nums, int k) {
        int size = nums.length;
        if (size <= 1) {
            return;
        }
        int mk = k % size;
        if (mk == 0) {
            return;
        }

        int[] copy = Arrays.copyOf(nums, size);
        for (int i=0; i < mk; i++) {
            nums[i] = copy[size-mk+i];
        }
        for (int i=mk; i < size; i++) {
            nums[i] = copy[i-mk];
        }
    }

    public void rotate(int[] nums, int k) {
        int size = nums.length;
        if (size <= 1) {
            return;
        }
        int mk = k % size;
        if (mk == 0) {
            return;
        }

        int[] copy = Arrays.copyOf(nums, size);
        for (int i=0; i < mk; i++) {
            nums[i] = copy[size-mk+i];
        }
        for (int i=mk; i < size; i++) {
            nums[i] = copy[i-mk];
        }
    }


    public static void main(String[] args) {
        Solution189 s = new Solution189();
        // int[] array = new int[]{1,0,1};
        // int[] array = new int[]{0,0,1};
        int[] array = new int[]{1,2,3,4,5,6,7};
        s.rotate(array, 3);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
