package leetcode.sort;

public class QuickSort {
    public int[] sortArray(int[] nums) {
        sort(nums, 0, nums.length-1);
        return nums;
    }

    private static int partition(int[] nums, int start, int end) {
        // pick the last as pivot
        int pivot = nums[end];
        int swap_index = start-1;
        for (int i=start; i<end; i++) {
            if (nums[i] < pivot) {
                swap_index++;

                int temp = nums[i];
                nums[i] = nums[swap_index];
                nums[swap_index] = temp;
            }
        }

        nums[end] = nums[swap_index+1];
        nums[swap_index+1] = pivot;
        return swap_index+1;
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
