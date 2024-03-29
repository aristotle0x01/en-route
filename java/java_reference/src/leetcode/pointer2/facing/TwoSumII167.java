package leetcode.pointer2.facing;

public class TwoSumII167 {

    // https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/

    public int[] twoSum(int[] numbers, int target) {
        int i = 0;
        int j = numbers.length-1;
        while (i < j) {
            int v = numbers[i] + numbers[j];
            if (v > target) {
                j--;
            } else if (v < target) {
                i++;
            } else {
                return new int[]{i+1, j+1};
            }
        }

        return new int[]{-1, -1};
    }
}
