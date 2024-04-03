package leetcode.binary;

public class SingleElementInASortedArray540 {
    // https://leetcode.com/problems/single-element-in-a-sorted-array/
    public int singleNonDuplicate(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int low = 0;
        int high = nums.length-1;
        int single = 0;
        while (low <= high) {
            if (low == high) {
                single = nums[low];
                break;
            }

            int mid = low + (high-low)/2;
            int hmRange = high-mid+1;
            if (hmRange % 2 == 0) {
                if (nums[mid] == nums[mid+1]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                if (nums[mid] == nums[mid+1]) {
                    low = mid + 1;
                } else {
                    // be careful to not use mid - 1
                    high = mid;
                }
            }
        }

        return single;
    }

    public static void main(String[] args) {
        SingleElementInASortedArray540 s = new SingleElementInASortedArray540();
        System.out.println(s.singleNonDuplicate(new int[]{1,1,2,3,3,4,4,8,8}));
    }
}
