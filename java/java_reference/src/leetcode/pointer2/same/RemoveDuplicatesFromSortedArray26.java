package leetcode.pointer2.same;

public class RemoveDuplicatesFromSortedArray26 {

    // https://leetcode.com/problems/remove-duplicates-from-sorted-array/
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return n;
        }
        int count = 1;
        int i = 0;
        int j = i+1;

        while (i < n) {
            while (j < n && nums[j] == nums[i]) j++;
            if (j < n) {
                nums[count] = nums[j];
                count++;
            } else {
                break;
            }

            i = j;
            j++;
        }

        return count;
    }
}
