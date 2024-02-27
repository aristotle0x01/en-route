package leetcode;

public class Solution {

    // 0283
    // https://leetcode.com/problems/move-zeroes/
    public void moveZeroes(int[] nums) {
        int size = nums.length;
        if (size == 0) return;

        int loop = size - 1;
        int i = 0;
        int zeros = 0;
        while (i < (loop-zeros)) {
            if (nums[i] != 0) {
                i++;
                continue;
            };

            for (int j=i; j < (loop-zeros); j++) {
                int t = nums[j+1];
                nums[j+1] = nums[j];
                nums[j] = t;
            }

            zeros++;
            if (nums[i] != 0) {
                i++;
            };
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] array = new int[]{0,0,1};
        s.moveZeroes(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
