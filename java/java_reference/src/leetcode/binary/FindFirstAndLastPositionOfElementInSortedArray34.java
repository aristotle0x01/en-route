package leetcode.binary;

public class FindFirstAndLastPositionOfElementInSortedArray34 {

    // https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
    public int[] searchRange(int[] nums, int target) {
        int[] arr = new int[]{-1, -1};
        if (nums == null || nums.length < 1) {
            return arr;
        }

        int n = nums.length;
        int low = 0;
        int high = n-1;
        int mid = -1;
        while (low <= high) {
            mid = low + (high-low) / 2;
            if (nums[mid] == target) {
                arr[0] = arr[1] = mid;
                break;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (low > high) {
            return arr;
        }
        int i = mid-1;
        int j = mid+1;
        while (i >= 0 && nums[i] == nums[mid]) {
            arr[0] = i;
            i--;
        }
        while (j < n && nums[j] == nums[mid]) {
            arr[1] = j;
            j++;
        }

        return arr;
    }
}
