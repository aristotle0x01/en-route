package leetcode.pointer2.same;

public class MoveZeroes283 {
    // https://leetcode.com/problems/move-zeroes/
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 1) {
            return;
        }

        int lastZeroIndex = -1;
        int i = 0;
        int n = nums.length;
        while (i < n) {
            int v = nums[i];
            if (v == 0) {
                if (lastZeroIndex == -1) {
                    lastZeroIndex = i;
                }
            } else {
                if (lastZeroIndex != -1) {
                    nums[i] = 0;
                    nums[lastZeroIndex] = v;
                    lastZeroIndex++;
                }
            }
            i++;
        }
    }
}
