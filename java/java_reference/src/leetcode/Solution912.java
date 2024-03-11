package leetcode;

public class Solution912 {

    // 912
    // https://leetcode.com/problems/sort-an-array/description/

    // **note for 912 quicksort is not the ideal sort algorithm, since its worst case performance
    // would fail to meet the requirements of all 21 testcases

    public int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length-1);
        return nums;
    }

    private void mergeSort(int[] nums, int start, int end) {
        if (start >= end){
            return;
        }

        int mid = start + (end-start)/2;
        mergeSort(nums, start, mid);
        mergeSort(nums, mid+1, end);

        int[] ta = new int[end-start+1];
        int i=start;
        int j=mid+1;
        int k=0;
        while (i<=mid && j<=end) {
            if (nums[i]<nums[j]){
                ta[k++] = nums[i++];
            }else{
                ta[k++] = nums[j++];
            }
        }
        while (i<=mid) {
            ta[k++] = nums[i++];
        }
        while (j<=end) {
            ta[k++] = nums[j++];
        }
        for (i=start;i<=end;i++) {
            nums[i] = ta[i-start];
        }
    }

    private static int partition(int[] nums, int start, int end) {
        // pick the last as pivot
        int pivot = nums[end];
        // swap from 'start-1' is the clever part
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

        boolean b = true;
        for(int i=start; i<end; i++){
            if(nums[i] != nums[i+1]) {
                b = false;
                break;
            }
        }
        if (b){
            return;
        }

        // take care to take pivot off from each sub-array
        int pivot_index = partition(nums, start, end);
        sort(nums, start, pivot_index-1);
        sort(nums, pivot_index+1, end);
    }

    public static void main(String[] args) {
        Solution912 s = new Solution912();
        // 5,2,3,1
        // 5,1,1,2,0,0
        int[] array1 = new int[]{-4,0,7,4,9,-5,-1,0,-7,-1};
        int[] a = s.sortArray(array1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
