package leetcode;

public class Solution27 {
    // [3,2,2,3] 3
    public int removeElement(int[] nums, int val) {
        int size = nums.length;
        int i = 0;
        int high = size-1;
        int valCount = 0;
        while (i <= high) {
            if (nums[i] != val) {
                i++;
                continue;
            }
            valCount++;

            while (nums[high] == val && high > i) {
                valCount++;
                high--;
            }
            if (high == i) {
                break;
            }

            int temp = nums[i];
            nums[i] = nums[high];
            nums[high] = temp;
            high--;
            i++;
        }

        return size - valCount;
    }

    public static void main(String[] args) {
    }
}
