package leetcode.binary;

public class SearchInRotatedSortedArray33 {
    // https://leetcode.com/problems/search-in-rotated-sorted-array/

    // first find max index, then find in two sub sort array

    public int search(int[] nums, int target) {
        int index = - 1;
        if (nums == null || nums.length == 0) {
            return index;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : index;
        }

        int n = nums.length;
        boolean rotated = nums[0] > nums[n-1];
        if (!rotated) {
            return findTarget(nums, 0, n-1, target);
        }

        // rotated
        int low = 0;
        int high = n-1;
        int max = -1;
        while (low <= high) {
            int mid = low + (high-low) / 2;
            if (low == high){
                max = low;
                break;
            }
            if (nums[low] > nums[high]) {
                if (nums[mid] > nums[high]) {
                    high--;
                    low = mid;
                } else {
                    high--;
                }
            } else {
                low = mid+1;
            }
        }

        index = findTarget(nums, 0, max, target);
        if (index == -1) {
            index = findTarget(nums, max+1, n-1, target);
        }
        return index;
    }

    private int findTarget(int[] nums, int s, int e, int target) {
        int low = s;
        int high = e;
        int index = -1;

        while (low <= high) {
            int mid = low + (high-low) / 2;
            int vmid = nums[mid];
            if (vmid == target) {
                index = mid;
                break;
            } else if (vmid > target) {
                high--;
            } else {
                low++;
            }
        }

        return index;
    }

    public static void main(String[] args) {
        SearchInRotatedSortedArray33 s = new SearchInRotatedSortedArray33();
        int[] arr = new int[]{2,3,4,5,6,7,8,1};
        System.out.println(s.search(arr, 3));
    }
}
