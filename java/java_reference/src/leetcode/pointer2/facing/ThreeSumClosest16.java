package leetcode.pointer2.facing;

import java.util.Arrays;

public class ThreeSumClosest16 {
    public int threeSumClosest(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        int minGap = Integer.MAX_VALUE;
        int minSum = 0;
        int i = 0;
        while (i < (n-2)) {
            int v1 = nums[i];

            int low = i+1;
            int high = n - 1;
            while (low < high) {
                int sum3 = v1 + nums[low] + nums[high];
                if (sum3 == target) {
                    return target;
                }

                int gap = Math.abs(sum3-target);
                if (gap < minGap) {
                    minGap = gap;
                    minSum = sum3;
                }
                if (sum3 > target) {
                    high--;
                } else {
                    low++;
                }
            }

            while (i < (n-2) && nums[i] == v1) i++;
        }

        return minSum;
    }
}
