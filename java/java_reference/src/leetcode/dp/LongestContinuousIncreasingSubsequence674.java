package leetcode.dp;

public class LongestContinuousIncreasingSubsequence674 {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 1) {
            return nums.length;
        }

        int max = 1;
        int i = 0;
        int j = 1;
        while (i < nums.length) {
            while ((j < nums.length) && (nums[j] > nums[j-1])) {
                j++;
            }

            int gap = j - i;
            if (gap > max) {
                max = gap;
            }

            i = j;
            j++;
        }

        return max;
    }

    public static void main(String[] args) {
        LongestContinuousIncreasingSubsequence674 s = new LongestContinuousIncreasingSubsequence674();
        System.out.println(s.findLengthOfLCIS(new int[]{1,3,5,4,7}));
    }
}
