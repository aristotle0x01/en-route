package leetcode.binary;

import java.util.ArrayList;
import java.util.List;

public class FindPeakElement162 {

    List<Integer> list;
    public int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return 0;
        }
        if (nums.length == 2) {
            if (nums[0] > nums[1]) {
                return 0;
            } else {
                return 1;
            }
        }

        list = new ArrayList<>();
        findPeak(nums, 0, nums.length-1);
        for (int i=0; i<list.size(); i++) {
            int index = list.get(i);
            if (index != 0 && index != (nums.length-1)) {
               return index;
            }
        }

        return list.get(0);
    }

    private void findPeak(int[] nums, int i, int j) {
        if (i>j) {
            return;
        }
        if (i == j) {
            if (i == 0 && nums[0] > nums[1]) {
                list.add(0);
            }
            if (i == (nums.length-1) && nums[i] > nums[i-1]) {
                list.add(i);
            }
            return;
        }
        if ((j-i+1) == 2) {
            if (i==0 && nums[0] > nums[1]) {
                list.add(0);
            }
            if (j == (nums.length-1) && nums[j] > nums[i]) {
                list.add(j);
            }
            return;
        }

        int mid = i + (j-i)/2;
        if (nums[mid] > nums[mid-1] && nums[mid] > nums[mid+1]) {
            list.add(mid);
            return;
        }

        if (nums[mid-1] < nums[mid]) {
            findPeak(nums, i, mid-1);
        } else {
            findPeak(nums, i, mid);
        }
        if (nums[mid+1] < nums[mid]) {
            findPeak(nums, mid+1, j);
        } else {
            findPeak(nums, mid, j);
        }
    }
}
