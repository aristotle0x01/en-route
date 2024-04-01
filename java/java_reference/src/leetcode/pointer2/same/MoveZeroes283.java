package leetcode.pointer2.same;

public class MoveZeroes283 {
    // https://leetcode.com/problems/move-zeroes/
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j;
        while (i < n) {
            if (nums[i] != 0) {
                i++;
                continue;
            }

            j = i + 1;
            while (j < n && nums[j] == 0) j++;
            if (j == n) {
                break;
            }
            nums[i] = nums[j];
            nums[j] = 0;
            i++;
        }
    }
}
