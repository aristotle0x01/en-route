package leetcode;

import java.util.Arrays;

public class Solution283 {

    // 0283
    // https://leetcode.com/problems/move-zeroes/
    // generally speaking, using element shifting algo is tedious
    // and error-prone
    public void moveZeroes1(int[] nums) {
        int size = nums.length;
        if (size == 0) return;

        int loop = size - 1;
        int i = 0;
        int zeros = 0;
        // control times of loop
        while (i < (loop-zeros)) {
            if (nums[i] != 0) {
                i++;
                continue;
            };

            // consider the end of swap
            for (int j=i; j < (loop-zeros); j++) {
                int t = nums[j+1];
                nums[j+1] = nums[j];
                nums[j] = t;
            }

            zeros++;
            // if initial swap caused nums[i] still equals to zero
            // i shouldn't be incremented in this case
            if (nums[i] != 0) {
                i++;
            };
        }
    }
    // two pointer way
    // first pointer iterate array
    // second pointer points to first non-zero element(not swapped)
    public void moveZeroes2(int[] nums) {
        int non_zero_index = 0;
        int size = nums.length;
        for (int index=0; index < size; index++) {
            if (nums[index] != 0) {
                continue;
            }

            // always point to first non-zero un-swapped element
            for (non_zero_index=index+1;non_zero_index < size;non_zero_index++) {
                if (nums[non_zero_index] != 0) {
                    break;
                }
            }
            // finished: out of index
            if (non_zero_index == size) {
                break;
            }

            nums[index] = nums[non_zero_index];
            nums[non_zero_index] = 0;
        }
    }
    // use another array, trade space for time
    public void moveZeroes3(int[] nums) {
        int size = nums.length;
        if (size <= 1) return;

        int[] array = Arrays.copyOf(nums, size);
        // Arrays.fill(nums, 0);
        int index = 0;
        for (int i=0; i<size;i++) {
            if (array[i] != 0) {
                nums[index++] = array[i];
            }
        }
        // avoid fill to save time
        for (int i=index;i<size;i++) {
            nums[i] = 0;
        }
    }
    public void moveZeroes(int[] nums) {
        int size = nums.length;
        if (size <= 1) return;

        int index = 0;
        for (int i=0; i<size;i++) {
            if (nums[i] != 0) {
                nums[index++] = nums[i];
            }
        }
        for (int i=index;i<size;i++) {
            nums[i] = 0;
        }
    }

    public static void main(String[] args) {
        Solution283 s = new Solution283();
        // int[] array = new int[]{1,0,1};
        // int[] array = new int[]{0,0,1};
        int[] array = new int[]{0,1,0,3,12};
        s.moveZeroes(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
