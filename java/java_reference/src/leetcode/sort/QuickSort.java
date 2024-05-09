package leetcode.sort;

public class QuickSort {
    public int[] sortArray(int[] nums) {
        sort(nums, 0, nums.length-1);
        return nums;
    }

    private static int partition(int[] nums, int start, int end) {
        int pivot = nums[end];

        int firstLargerIndex = -1;
        for (int i=start; i<end; i++) {
            if (nums[i] >= pivot) {
                if (firstLargerIndex == -1) {
                    firstLargerIndex = i;
                }
            } else {
                if (firstLargerIndex == -1) {
                    continue;
                }

                // swap
                int tmp = nums[firstLargerIndex];
                nums[firstLargerIndex] = nums[i];
                nums[i] = tmp;
                firstLargerIndex++;
            }
        }

        if (firstLargerIndex == -1) {
            return end;
        }

        // swap of this pos may make quicksort unstable
        // A stable sorting algorithm preserves the relative order of equivalent elements in the sorted output.
        // https://www.baeldung.com/cs/stable-sorting-algorithms
        nums[end] = nums[firstLargerIndex];
        nums[firstLargerIndex] = pivot;
        return firstLargerIndex;
    }
    private static void sort(int[] nums, int start, int end) {
        if (start >= end){
            return;
        }

        int pivot_index = partition(nums, start, end);
        sort(nums, start, pivot_index-1);
        sort(nums, pivot_index+1, end);
    }
}
